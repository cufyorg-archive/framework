/*
 *	Copyright 2020 Cufy
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package cufy.lang;

import cufy.util.Arrayz;
import cufy.util.Reflection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * An alternative representation for {@link Class classes}. This provides more data about the targeted type. About the
 * components tree and the family of that class. The family is how the class should be treated. The clazz-tree is a tree
 * of clazzes for each specific component in an instance this clazz is representing.
 * <br>
 * Note: the class {@link Void} is the class of null.
 * <pre>
 *     clazz (root) -> tree (component tree) -> clazz (default clazz | new root) -> tree -> clazz -> tree ->...
 * </pre>
 *
 * @param <T> the type of the class represented by this clazz.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.0 ~2020.03.29
 */
public final class Clazz<T> implements Type, Serializable {
	/**
	 * A key for a general clazz for all components to be held by an instance of a clazz.
	 */
	public static final Object COMPONENT = new Object();
	/**
	 * A final empty tree to reduce redundant tree initializing.
	 */
	private static final ComponentTree EMPTY_TREE = new ComponentTree();
	/**
	 * A final empty trees array to reduce redundant array initializing.
	 */
	private static final ComponentTree[] EMPTY_TREES = new ComponentTree[0];
	/**
	 * The class that an instance of this clazz should be treated as if it was an instance of it. This field should be
	 * treated as final field.
	 */
	private Class family;
	/**
	 * The class represented by this clazz. This field should be treated as final field.
	 */
	private Class<T> klass;
	/**
	 * A tree of the clazzes specified foreach component to be held by an instance of this clazz. This field should be
	 * treated as final field.
	 */
	private ComponentTree[] trees;

	/**
	 * Construct a new clazz that represents the given {@code klass}, and have the given {@code trees}.
	 *
	 * @param klass the class to be represented by the constructed clazz.
	 * @param trees an array of trees of the clazzes specified foreach component to be held by an instance of the
	 *              constructed clazz.
	 * @throws NullPointerException if the given {@code klass} or {@code trees} or any of its elements is null.
	 */
	private Clazz(Class<T> klass, Map<Object, Clazz>... trees) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(trees, "trees");
		this.klass = klass;
		this.family = klass;
		this.trees = Clazz.finalize(trees);
	}

	/**
	 * Construct a new clazz that represents the given {@code klass}, and have the given {@code trees}, and should be
	 * treated as if it was the given {@code family}.
	 *
	 * @param klass  the class to be represented by the constructed clazz.
	 * @param family the class that an instance of the constructed clazz should be treated as if it was an instance of
	 *               it.
	 * @param trees  an array of trees of the clazzes specified foreach component to be held by an instance of the
	 *               constructed clazz.
	 * @throws NullPointerException if the given {@code klass} or {@code family} or {@code trees} or any of its elements
	 *                              is null.
	 */
	private Clazz(Class<T> klass, Class family, Map<Object, Clazz>... trees) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(trees, "trees");
		this.klass = klass;
		this.family = family;
		this.trees = Clazz.finalize(trees);
	}

	/**
	 * Get an array of clazzes from the given {@code klasses}.
	 *
	 * @param klasses the source klasses foreach clazz in the returned array.
	 * @return an array of clazzes from the given {@code klasses}.
	 * @throws NullPointerException if the given {@code klasses} is null.
	 * @since 0.1.5
	 */
	public static Clazz[] array(Class... klasses) {
		Objects.requireNonNull(klasses, "klasses");
		Clazz[] clazzes = new Clazz[klasses.length];

		for (int i = 0; i < klasses.length; i++)
			if (klasses[i] != null)
				clazzes[i] = Clazz.of(klasses[i]);

		return clazzes;
	}

	/**
	 * Get a clazz that represents teh given {@code klass}, and have trees with the given {@code componentClasses}.
	 * <pre>
	 *     Clazz.as(<font color="a5edff">KLASS</font>, <font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">KLASS</font>&lt;<font color="d3c4ff">TREE0</font>, <font color="d3c4ff">TREE1</font>, <font color="d3c4ff">TREE2</font>, …&gt;
	 * </pre>
	 *
	 * @param klass            the class to be represented by the returned clazz.
	 * @param componentClasses the component clazzes of the trees of the returned clazz.
	 * @return a clazz that represents teh given {@code klass}, and have trees with the given {@code componentClasses}.
	 * @throws NullPointerException if the given {@code klass} or {@code componentClasses} is null.
	 * @since 0.1.5
	 */
	public static Clazz as(Class klass, Class... componentClasses) {
		return Clazz.of(klass, Clazz.trees(Clazz.array(componentClasses)));
	}

	/**
	 * Get a clazz that represents teh given {@code klass}, and have trees with the given {@code componentClasses}, and
	 * should be treated as if it was the given {@code family}.
	 * <pre>
	 *     Clazz.as(<font color="a5edff">KLASS</font>, <font color="fc8fbb">FAMILY</font> <font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">KLASS</font>:<font color="fc8fbb">FAMILY</font>&lt;<font color="d3c4ff">TREE0</font>, <font color="d3c4ff">TREE1</font>, <font color="d3c4ff">TREE2</font>, …&gt;
	 * </pre>
	 *
	 * @param klass            the class to be represented by the returned clazz.
	 * @param family           the class that an instance of the returned clazz should be treated as if it was an
	 *                         instance of it.
	 * @param componentClasses the component clazzes of the trees of the returned clazz.
	 * @return a clazz that represents teh given {@code klass}, and have trees with the given {@code componentClasses},
	 * 		and should be treated as if it was the given {@code family}.
	 * @throws NullPointerException if the given {@code klass} or {@code family} or {@code componentClasses} is null.
	 * @since 0.1.5
	 */
	public static Clazz as(Class klass, Class family, Class... componentClasses) {
		return Clazz.of(klass, family, Clazz.trees(Clazz.array(componentClasses)));
	}

	/**
	 * Get a clazz that represents the class of the given {@code instance}, and with a {@code trees} auto generated from
	 * the elements of the given {@code instance} with the given {@code componentClazzes}.
	 * <br>
	 * Note: If a component clazz is missing, the standard component clazz for the instance that miss it will be taken.
	 * <pre>
	 *     Clazz.in(<font color="a5edff">INSTANCE</font>, <font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">KLASS</font>&lt;<font color="d3c4ff">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param instance         an instance that its class to be represented by the returned clazz, and its elements is
	 *                         the source of its trees.
	 * @param componentClazzes the clazzes for the elements that can't construct a sub-tree for it. The clazzes should
	 *                         be ordered to its targeted auto-generated tree.
	 * @param <T>              the type of the class represented by the returned clazz.
	 * @return a clazz that represents the class of the given {@code instance}, and with a {@code trees} auto generated
	 * 		from the elements of the given {@code instance} with the given {@code componentClazzes}.
	 * @throws NullPointerException if the given {@code componentClazzes} is null.
	 * @since 0.1.5
	 */
	public static <T> Clazz<T> in(T instance, Clazz... componentClazzes) {
		Objects.requireNonNull(componentClazzes, "componentClazzes");
		Class klass = instance == null ? Void.class : instance.getClass();
		return Clazz.of(klass, Clazz.trees(instance, componentClazzes));
	}

	/**
	 * Get a clazz that represents the class of the given {@code instance}, and should be treated as if it was the given
	 * {@code family}, and with a {@code trees} auto generated from the elements of the given {@code instance} with the
	 * given {@code componentClazzes}.
	 * <br>
	 * Note: If a component clazz is missing, the standard component clazz for the instance that miss it will be taken.
	 * <pre>
	 *     Clazz.in(<font color="a5edff">INSTANCE</font>, <font color="fc8fbb">FAMILY</font>, <font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">KLASS</font>:<font color="fc8fbb">FAMILY</font>&lt;<font color="d3c4ff">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param instance         an instance that its class to be represented by the returned clazz, and its elements is
	 *                         the source of its tree.
	 * @param family           the class that an instance of the returned clazz should be treated as if it was an
	 *                         instance of it.
	 * @param componentClazzes the clazzes for the elements that can't construct a sub-tree for it. The clazzes should
	 *                         be ordered to its targeted auto-generated tree.
	 * @param <T>              the type of the class represented by the returned clazz.
	 * @return a clazz that represents the class of the given {@code instance}, and should be treated as if it was the
	 * 		given {@code family}, and with a {@code trees} auto generated from the elements of the given {@code instance}
	 * 		with the given {@code componentClazzes}.
	 * @throws NullPointerException if the given {@code family} or {@code componentClazzes} is null.
	 * @since 0.1.5
	 */
	public static <T> Clazz<T> in(T instance, Class family, Clazz... componentClazzes) {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(componentClazzes, "componentClazzes");
		Class klass = instance == null ? Void.class : instance.getClass();
		return Clazz.of(klass, family, Clazz.trees(instance, componentClazzes));
	}

	/**
	 * Get a clazz that represents the class {@link Object}, and have the given {@code trees}.
	 * <pre>
	 *     Clazz.of(<font color="f1fc8f">TREE0, TREE1, TREE2, …</font>)
	 *     <font color="a5edff">Object</font>&lt;<font color="f1fc8f">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param trees an array of trees of the clazzes specified foreach component to be held by an instance of the
	 *              constructed clazz.
	 * @return a clazz that represents the class {@link Object}, and have the given {@code trees}.
	 * @throws NullPointerException if the given {@code trees} or any of its elements is null.
	 * @since 0.1.5
	 */
	public static Clazz<Object> of(Map<Object, Clazz>... trees) {
		Objects.requireNonNull(trees, "trees");
		return new Clazz(Object.class, trees);
	}

	/**
	 * Get a clazz that represents the given {@code klass}, and have the given {@code trees}.
	 * <pre>
	 *     Clazz.of(<font color="a5edff">KLASS</font>, <font color="f1fc8f">TREE0, TREE1, TREE2, …</font>)
	 *     <font color="a5edff">KLASS</font>&lt;<font color="f1fc8f">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param klass the class to be represented by the returned clazz.
	 * @param trees an array of trees of the clazzes specified foreach component to be held by an instance of the
	 *              constructed clazz.
	 * @param <T>   the type of the class represented by the returned clazz.
	 * @return a clazz that represents the given {@code klass}, and have the given {@code trees}.
	 * @throws NullPointerException if the given {@code klass} or {@code trees} or any of its elements is null.
	 * @since 0.1.5
	 */
	public static <T> Clazz<T> of(Class<T> klass, Map<Object, Clazz>... trees) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(trees, "trees");
		return new Clazz(klass, trees);
	}

	/**
	 * Get a clazz that represents the given {@code klass}, and have the given {@code tree}, and should be treated as if
	 * it was the given {@code family}.
	 * <pre>
	 *     Clazz.of(<font color="a5edff">KLASS</font>, <font color="fc8fbb">FAMILY</font>, <font color="f1fc8f">TREE0, TREE1, TREE2, …</font>)
	 *     <font color="a5edff">KLASS</font>:<font color="fc8fbb">FAMILY</font>&lt;<font color="f1fc8f">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param klass  the class to be represented by the returned clazz.
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param trees  an array of trees of the clazzes specified foreach component to be held by an instance of the
	 *               constructed clazz.
	 * @param <T>    the type of the class represented by the returned clazz.
	 * @return a new clazz that represents the given {@code klass}, and have the given {@code tree}, and should be
	 * 		treated as if it was the given {@code family}.
	 * @throws NullPointerException if the given {@code klass} or {@code family} or {@code tree} is null.
	 * @since 0.1.5
	 */
	public static <T> Clazz<T> of(Class<T> klass, Class family, Map<Object, Clazz>... trees) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(trees, "tree");
		return new Clazz(klass, family, trees);
	}

	/**
	 * Get a clazz that represents the class with the given {@code name}, and have the given {@code tree}.
	 * <pre>
	 *     Clazz.of(<font color="a5edff">NAME</font>, <font color="f1fc8f">TREE0, TREE1, TREE2, …</font>)
	 *     <font color="a5edff">KLASS</font>&lt;<font color="f1fc8f">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param name  the name of the class that to be represented by the returned clazz.
	 * @param trees an array of trees of the clazzes specified foreach component to be held by an instance of the
	 *              constructed clazz.
	 * @return a clazz that represents the class with the given {@code name}, and have the given {@code tree}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code name} or {@code tree} is null.
	 * @see Class#forName(String)
	 * @since 0.1.5
	 */
	public static Clazz of(String name, Map<Object, Clazz>... trees) throws ClassNotFoundException {
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(trees, "trees");
		return Clazz.of(Class.forName(name), trees);
	}

	/**
	 * Get a clazz that represents the class with the given {@code name}, and have the given {@code tree}, and should be
	 * treated as if it was the given {@code family}.
	 * <pre>
	 *     Clazz.of(<font color="a5edff">NAME</font>, <font color="fc8fbb">FAMILY</font>, <font color="f1fc8f">TREE0, TREE1, TREE2, …</font>)
	 *     <font color="a5edff">KLASS</font>:<font color="fc8fbb">FAMILY</font>&lt;<font color="f1fc8f">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param name   the name of the class that to be represented by the returned clazz.
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param trees  an array of trees of the clazzes specified foreach component to be held by an instance of the
	 *               constructed clazz.
	 * @return a clazz that represents the class with the given {@code name}, and have the given {@code tree}, and
	 * 		should be treated as if it was the given {@code family}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code name} or {@code family} or {@code tree} is null.
	 * @see Class#forName(String)
	 * @since 0.1.5
	 */
	public static Clazz of(String name, Class family, Map<Object, Clazz>... trees) throws ClassNotFoundException {
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(trees, "trees");
		return Clazz.of(Class.forName(name), family, trees);
	}

	/**
	 * Get a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and have
	 * the given {@code tree}.
	 * <pre>
	 *     Clazz.of(LOADER, INITIALIZE, <font color="a5edff">NAME</font>, <font color="f1fc8f">TREE0, TREE1, TREE2, …</font>)
	 *     <font color="a5edff">KLASS</font>&lt;<font color="f1fc8f">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param loader     class loader from which the class must be loaded.
	 * @param initialize if true the class will be initialized. See Section 12.4 of The Java Language.
	 * @param name       the name of the class that to be represented by the returned clazz.
	 * @param trees      an array of trees of the clazzes specified foreach component to be held by an instance of the
	 *                   constructed clazz.
	 * @return a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and
	 * 		have the given {@code tree}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code loader} or {@code name} or {@code tree} is null.
	 * @see Class#forName(String, boolean, ClassLoader)
	 * @since 0.1.5
	 */
	public static Clazz of(ClassLoader loader, boolean initialize, String name, Map<Object, Clazz>... trees) throws ClassNotFoundException {
		Objects.requireNonNull(loader, "loader");
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(trees, "trees");
		return Clazz.of(Class.forName(name, initialize, loader), trees);
	}

	/**
	 * Get a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and have
	 * the given {@code tree}, and should be treated as if it was the given {@code family}.
	 * <pre>
	 *     Clazz.of(LOADER, INITIALIZE, <font color="a5edff">NAME</font>, <font color="fc8fbb">FAMILY</font>, <font color="f1fc8f">TREE0, TREE1, TREE2, …</font>)
	 *     <font color="a5edff">KLASS</font>:<font color="fc8fbb">FAMILY</font>&lt;<font color="f1fc8f">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param loader     class loader from which the class must be loaded.
	 * @param initialize if true the class will be initialized. See Section 12.4 of The Java Language.
	 * @param name       the name of the class that to be represented by the returned clazz.
	 * @param family     the class that an instance of the returned clazz should be treated as if it was an instance of
	 *                   it.
	 * @param trees      an array of trees of the clazzes specified foreach component to be held by an instance of the
	 *                   constructed clazz.
	 * @return a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and
	 * 		have the given {@code tree}, and should be treated as if it was the given {@code family}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code loader} or {@code name} or {@code family} or {@code tree}
	 *                                     is null.
	 * @see Class#forName(String, boolean, ClassLoader)
	 * @since 0.1.5
	 */
	public static Clazz of(ClassLoader loader, boolean initialize, String name, Class family, Map<Object, Clazz>... trees) throws ClassNotFoundException {
		Objects.requireNonNull(loader, "loader");
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(trees, "trees");
		return Clazz.of(Class.forName(name, initialize, loader), family, trees);
	}

	/**
	 * Get a clazz that represents the {@code klass} of the given {@code klassSrc}, and have the {@code tree} of the
	 * given {@code treeSrc}, and should be treated as if it was the {@code family} of the given {@code familySrc}.
	 * <pre>
	 *     Clazz.of(<font color="a5edff">KLASS_SRC</font>, <font color="fc8fbb">FAMILY_SRC</font>, <font color="f1fc8f">TREES_SRC</font>)
	 *     <font color="a5edff">KLASS</font>:<font color="fc8fbb">FAMILY</font>&lt;<font color="f1fc8f">TREES…</font>&gt;
	 * </pre>
	 *
	 * @param klassSrc  the source of the class to be represented by the returned clazz.
	 * @param familySrc the source of the class that an instance of the returned clazz should be treated as if it was an
	 *                  instance of it.
	 * @param treesSrc  the source of the array of trees of the clazzes specified foreach component to be held by an
	 *                  instance of the constructed clazz.
	 * @param <T>       the type of the class represented by the returned clazz.
	 * @return a clazz that represents the {@code klass} of the given {@code klassSrc}, and have the {@code tree} of the
	 * 		given {@code treeSrc}, and should be treated as if it was the {@code family} of the given {@code familySrc}.
	 * @throws NullPointerException if the given {@code familySrc} or {@code klassSrc} or {@code treeSrc} is null.
	 * @since 0.1.5
	 */
	public static <T> Clazz<T> of(Clazz<T> klassSrc, Clazz familySrc, Clazz treesSrc) {
		Objects.requireNonNull(klassSrc, "klassSrc");
		Objects.requireNonNull(familySrc, "familySrc");
		Objects.requireNonNull(treesSrc, "treesSrc");
		return Clazz.of(klassSrc.klass, familySrc.family, treesSrc.trees);
	}

	/**
	 * Get a tree that its general clazz is the given {@code componentClazz}, and have no mappings.
	 * <pre>
	 *     Clazz.tree(<font color="d3c4ff">COMPONENT</font>)
	 *     <font color="d3c4ff">TREE</font>
	 * </pre>
	 *
	 * @param componentClazz the general clazz for the components that have no mapping in the returned tree.
	 * @return a tree that its general clazz is the given {@code componentClazz}, and have no mappings.
	 * @see Clazz#trees(Clazz[])
	 * @see Clazz#trees(int, Clazz[])
	 * @since 0.1.5
	 */
	public static Map<Object, Clazz> tree(Clazz componentClazz) {
		return new HashMap<>(Collections.singletonMap(Clazz.COMPONENT, componentClazz));
	}

	/**
	 * Get a linear tree of general component clazzes. The general clazzes specified in order representing the given
	 * {@code componentClasses}.
	 * <pre>
	 *     Clazz.tree(<font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="d3c4ff">TREE0</font>&lt;<font color="d3c4ff">TREE1</font>&lt;<font color="d3c4ff">TREE2…</font>&gt;&gt;
	 * </pre>
	 *
	 * @param componentClasses an ordered list of the classes represented by the nested general component clazzes in
	 *                         returned tree.
	 * @return a linear tree of general component clazzes. The general clazzes specified in order representing the given
	 *        {@code componentClasses}.
	 * @throws NullPointerException if the given {@code componentClasses} or an element with index (? &gt; 0) in it is
	 *                              null.
	 * @see Clazz#trees(Class[][])
	 * @since 0.1.5
	 */
	public static Map<Object, Clazz> tree(Class... componentClasses) {
		Objects.requireNonNull(componentClasses, "componentClasses");
		if (componentClasses.length == 0)
			return new HashMap();
		if (componentClasses.length == 1)
			return componentClasses[0] == null ? new HashMap() : Clazz.tree(new Clazz(componentClasses[0]));
		else
			return Clazz.tree(Clazz.of(
					//componentClazz's klass
					componentClasses[0],
					//componentClazz's tree (subTree)
					Clazz.tree(Arrays.copyOfRange(componentClasses, 1, componentClasses.length))
			));
	}

	/**
	 * Get an array of trees that each one's general clazz is the clazz at the same as its position in the given {@code
	 * componentClazzes}.
	 * <pre>
	 *     Clazz.trees(<font color="d3c4ff">COMPONENT0</font>, <font color="d3c4ff">COMPONENT1</font>, <font color="d3c4ff">COMPONENT2</font>, …)
	 *     &lt;<font color="d3c4ff"><font color="d3c4ff">TREE0</font>, <font color="d3c4ff">TREE1</font>, <font color="d3c4ff">TREE2</font>, …&gt;
	 * </pre>
	 *
	 * @param componentClazzes the general clazzes array for the components that have no mapping in the tree at the same
	 *                         position as the clazz in returned trees array.
	 * @return an array of trees that each one's general clazz is the clazz at the same as its position in the given
	 *        {@code componentClazzes}.
	 * @throws NullPointerException if the given {@code componentClazzes} is null.
	 * @see Clazz#trees(int, Clazz[])
	 * @see Clazz#tree(Clazz)
	 * @since 0.1.5
	 */
	public static Map<Object, Clazz>[] trees(Clazz... componentClazzes) {
		Objects.requireNonNull(componentClazzes, "componentClazzes");
		Map<Object, Clazz>[] trees = new Map[componentClazzes.length];

		for (int i = 0; i < componentClazzes.length; i++)
			trees[i] = Clazz.tree(componentClazzes[i]);

		return trees;
	}

	/**
	 * Get linear trees of general component clazzes. The given {@code componentClasses} specifies each array for a tree
	 * in the returned trees array.
	 * <br>
	 * Note: this is just an array version of {@link Clazz#tree(Class[])}.
	 * <pre>
	 *     Clazz.trees(<font color="d3c4ff">{TREE0, NESTED0, DEEP0, …}, {TREE1, NESTED1, DEEP1, …}, …</font>)
	 *     &lt;<font color="d3c4ff">TREE0</font>&lt;<font color="d3c4ff">NESTED0</font>&lt;<font color="d3c4ff">DEEP0</font>…&gt;&gt;, <font color="d3c4ff">TREE1</font>&lt;<font color="d3c4ff">NESTED1</font>&lt;<font color="d3c4ff">DEEP1</font>…&gt;&gt;, …&gt;
	 * </pre>
	 *
	 * @param componentClasses an array of component classes, each array goes to the same position tree in the returned
	 *                         trees array.
	 * @return linear trees of general component clazzes. The given {@code componentClasses} specifies each array for a
	 * 		tree *in the returned trees array.
	 * @throws NullPointerException if the given {@code componentClasses} or any of its elements is null.
	 * @see Clazz#tree(Class[])
	 * @since 0.1.5
	 */
	public static Map<Object, Clazz>[] trees(Class[]... componentClasses) {
		Objects.requireNonNull(componentClasses, "componentClasses");
		Map<Object, Clazz>[] trees = new Map[componentClasses.length];

		for (int i = 0; i < componentClasses.length; i++)
			trees[i] = Clazz.tree(componentClasses[i]);

		return trees;
	}

	/**
	 * Get an array of trees auto-generated for the given {@code instance}, Containing general component clazzes from
	 * the given {@code componentClazzes}.
	 * <pre>
	 *     Clazz.trees(<font color="a5edff">INSTANCE</font>, <font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     &lt;<font color="d3c4ff">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param instance         the instance the returned trees array is auto-generated from.
	 * @param componentClazzes the general component clazzes for each tree in the returned tree array.
	 * @return an array of trees auto-generated for the given {@code instance}, Containing general component clazzes
	 * 		from the given {@code componentClazzes}.
	 * @throws NullPointerException if the given {@code componentClazzes} is null.
	 * @since 0.1.5
	 */
	public static Map<Object, Clazz>[] trees(Object instance, Clazz... componentClazzes) {
		if (instance instanceof Clazzable)
			return ((Clazzable) instance).getComponentTrees(componentClazzes);
		else if (Clazzable.isClazzable(instance))
			return Clazz.trees0(instance, componentClazzes);

		return Clazz.trees(componentClazzes);
	}

	/**
	 * Sign all the given {@code trees} as a {@link ComponentTree}. Once a {@code tree} is signed it can only be read. A
	 * signed {@code tree} will not be signed twice. Signing a {@code tree} means cloning all the content of it into a
	 * new {@link ComponentTree}. Singing a {@code tree} is unnecessary unless to prevent security holes, since any
	 * clazz will sign its {@code tree} anyway when it gets constructed. A clazz will never use an unsigned {@code
	 * tree}.
	 * <br>
	 * Note: a constructor of clazz will call this method anyway.
	 *
	 * @param trees the source mappings for the returned signed {@code trees}.
	 * @return {@link ComponentTree}s that have clones of the mappings in the given {@code trees} each {@link
	 *        ComponentTree} cloned a {@code tree} at the same order as the {@code trees} given.
	 * @throws NullPointerException if the given {@code trees} or any of its elements is null.
	 * @see Clazz#finalize(Map)
	 */
	static ComponentTree[] finalize(Map<Object, Clazz>... trees) {
		if (trees.length == 0)
			return Clazz.EMPTY_TREES;
		else {
			Objects.requireNonNull(trees, "trees");
			ComponentTree[] r = new ComponentTree[trees.length];

			for (int i = 0; i < trees.length; i++)
				r[i] = Clazz.finalize(trees[i]);

			return r;
		}
	}

	/**
	 * Sign the given {@code tree} as a {@link ComponentTree}. Once a {@code tree} is signed it can only be read. A
	 * signed {@code tree} will not be signed twice. Signing a {@code tree} means cloning all the content of it into a
	 * new {@link ComponentTree}. Singing a {@code tree} is unnecessary unless to prevent security holes, since any
	 * clazz will sign its {@code tree} anyway when it gets constructed. A clazz will never use an unsigned {@code
	 * tree}.
	 *
	 * @param tree the source mapping for the returned signed {@code tree}.
	 * @return a {@link ComponentTree} that have a clone of the mappings in the given {@code tree}.
	 * @throws NullPointerException if the given {@code tree} is null.
	 * @see Clazz#finalize(Map[])
	 */
	static ComponentTree finalize(Map<Object, Clazz> tree) {
		Objects.requireNonNull(tree, "tree");
		return tree instanceof Clazz.ComponentTree ?
			   (ComponentTree) tree :
			   tree.isEmpty() ?
			   Clazz.EMPTY_TREE :
			   new ComponentTree(tree);
	}

	/**
	 * Get a clazz that represents the given {@code klass}, and with a {@code tree} auto generated from the given
	 * array's class.
	 * <pre>
	 *     Clazz.ofArray(<font color="a5edff">KLASS[][][]</font>)
	 *     <font color="a5edff">KLASS[][][]</font>&lt;<font color="a5edff">KLASS[][]</font>&lt;<font color="a5edff">KLASS[]</font>&lt;<font color="a5edff">KLASS</font>&gt;&gt;&gt;
	 * </pre>
	 *
	 * @param klass the array class to be represented by the returned clazz.
	 * @param <T>   the type of the class represented by the returned clazz.
	 * @return a clazz that represents the given {@code klass}, and with a {@code tree} auto generated from the given
	 * 		array's class.
	 * @throws NullPointerException     if the given {@code klass} is null.
	 * @throws IllegalArgumentException if the given {@code klass} is not an array's clazz.
	 */
	static <T> Clazz<T> ofArray(Class<T> klass) {
		Objects.requireNonNull(klass, "klass");
		if (!klass.isArray())
			throw new IllegalArgumentException("Not an array's class " + klass);

		Class component = klass.getComponentType();
		Clazz componentClazz = component.isArray() ? Clazz.ofArray(component) : Clazz.of(component);

		return Clazz.of(klass, Clazz.tree(componentClazz));
	}

	/**
	 * Get a clazz that represents the given {@code klass}, and with a {@code tree} auto generated from the given
	 * array's class, and should be treated as if it was the given {@code family}.
	 * <pre>
	 *     Clazz.ofArray(<font color="a5edff">KLASS[][][]</font>, <font color="fc8fbb">FAMILY</font>)
	 *     <font color="a5edff">KLASS[][][]</font>:<font color="fc8fbb">FAMILY</font>&lt;<font color="a5edff">KLASS[][]</font>&lt;<font color="a5edff">KLASS[]</font>&lt;<font color="a5edff">KLASS</font>&gt;&gt;&gt;
	 * </pre>
	 *
	 * @param klass  the array class to be represented by the returned clazz.
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param <T>    the type of the class represented by the returned clazz.
	 * @return a clazz that represents the given {@code klass}, and with a {@code tree} auto generated from the given
	 * 		array's class, and should be treated as if it was the given {@code family}.
	 * @throws NullPointerException     if the given {@code klass} or {@code family} is null.
	 * @throws IllegalArgumentException if the given {@code klass} is not an array's clazz.
	 */
	static <T> Clazz<T> ofArray(Class<T> klass, Class family) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(family, "family");
		if (!klass.isArray())
			throw new IllegalArgumentException(klass + " is not an array-class");

		Class component = klass.getComponentType();
		Class componentFamily = family.isArray() ? family.getComponentType() : component;
		Clazz componentClazz =
				component.isArray() ? Clazz.ofArray(componentFamily, component) : Clazz.of(componentFamily, component);

		return Clazz.of(klass, family, Clazz.tree(componentClazz));
	}

	/**
	 * Get an array of trees that each one's general clazz is the clazz at the same as its position in the given {@code
	 * componentClazzes}.
	 * <pre>
	 *     Clazz.trees(<font color="fc888a">MIN_LENGTH</font>, <font color="d3c4ff">COMPONENT0</font>, <font color="d3c4ff">COMPONENT1</font>, <font color="d3c4ff">COMPONENT2</font>, …)
	 *     &lt;<font color="d3c4ff"><font color="d3c4ff">TREE0</font>, <font color="d3c4ff">TREE1</font>, <font color="d3c4ff">TREE2</font>, <font color="fc888a">…</font>&gt;
	 * </pre>
	 *
	 * @param ml               the minimum length the returned array will be
	 * @param componentClazzes the general clazzes array for the components that have no mapping in the tree at the same
	 *                         position as the clazz in returned trees array.
	 * @return an array of trees that each one's general clazz is the clazz at the same as its position in the given
	 *        {@code componentClazzes}.
	 * @throws NullPointerException if the given {@code componentClazzes} is null.
	 * @see Clazz#trees(Clazz[])
	 * @see Clazz#tree(Clazz)
	 */
	static Map<Object, Clazz>[] trees(int ml, Clazz... componentClazzes) {
		Objects.requireNonNull(componentClazzes, "componentClazzes");
		Map<Object, Clazz>[] trees = new Map[Math.max(componentClazzes.length, ml)];

		int i = 0;
		for (; i < componentClazzes.length; i++)
			trees[i] = Clazz.tree(componentClazzes[i]);
		for (; i < ml; i++)
			trees[i] = new HashMap();

		return trees;
	}

	/**
	 * Get an array of trees auto-generated for the given {@code instance}, Containing general component clazzes from
	 * the given {@code componentClazzes}. This is the base method for {@link #trees(Object, Clazz[])}.
	 * <pre>
	 *     Clazz.trees0(<font color="a5edff">INSTANCE</font>, <font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     &lt;<font color="d3c4ff">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param instance         the instance the returned trees array is auto-generated from.
	 * @param componentClazzes the general component clazzes for each tree in the returned tree array.
	 * @return an array of trees auto-generated for the given {@code instance}, Containing general component clazzes
	 * 		from the given {@code componentClazzes}.
	 * @throws NullPointerException if the given {@code componentClazzes} is null.
	 */
	static Map<Object, Clazz>[] trees0(Object instance, Clazz... componentClazzes) {
		Objects.requireNonNull(instance, "instance");
		if (instance instanceof Iterable)
			Clazz.treesForIterable((Iterable) instance, componentClazzes);
		if (instance instanceof Map)
			Clazz.treesForMap((Map) instance, componentClazzes);
		if (instance.getClass().isArray())
			Clazz.treesForArray(instance, componentClazzes);

		return Clazz.trees(componentClazzes);
	}

	/**
	 * Get an array of trees auto-generated for the given {@code array}, Containing general component clazzes from the
	 * given {@code componentClazzes}.
	 * <pre>
	 *     Clazz.treesForArray(<font color="a5edff">INSTANCE</font>, <font color="d3c4ff">ELEMENT_CLAZZ, …</font>)
	 *     &lt;<font color="d3c4ff">ELEMENT_TREE, …</font>&gt;
	 * </pre>
	 *
	 * @param array            the array the returned trees array is auto-generated from.
	 * @param componentClazzes the general component clazzes for each tree in the returned tree array.
	 * @return an array of trees auto-generated for the given {@code array}, Containing general component clazzes from
	 * 		the given {@code componentClazzes}.
	 * @throws NullPointerException     if the given {@code array} or {@code componentClazzes} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 */
	static Map<Object, Clazz>[] treesForArray(Object array, Clazz[] componentClazzes) {
		Objects.requireNonNull(array, "array");
		if (!array.getClass().isArray())
			throw new IllegalArgumentException("Not an array " + array);
		Class component;
		Map<Object, Clazz>[] trees = Clazz.trees(1, componentClazzes.length > 0 ? componentClazzes : new Clazz[]{
				(component = array.getClass().getComponentType()).isArray() ? Clazz.ofArray(component)
																			: Clazz.of(component)
		});

		Iterator iterator = Arrayz.iterator1(array);
		for (int i = 0; iterator.hasNext(); i++)
			trees[0].put(i, Clazz.in(iterator.next()));

		return trees;
	}

	/**
	 * Get an array of trees auto-generated for the given {@code iterable}, Containing general component clazzes from
	 * the given {@code componentClazzes}.
	 * <pre>
	 *     Clazz.treesForIterable(<font color="a5edff">INSTANCE</font>, <font color="d3c4ff">ITEM_CLAZZ, …</font>)
	 *     &lt;<font color="d3c4ff">ITEM_TREE, …</font>&gt;
	 * </pre>
	 *
	 * @param iterable         the iterable the returned trees array is auto-generated from.
	 * @param componentClazzes the general component clazzes for each tree in the returned tree array.
	 * @return an array of trees auto-generated for the given {@code iterable}, Containing general component clazzes
	 * 		from the given {@code componentClazzes}.
	 * @throws NullPointerException if the given {@code iterable} or {@code componentClazzes} is null.
	 */
	static Map<Object, Clazz>[] treesForIterable(Iterable iterable, Clazz[] componentClazzes) {
		Objects.requireNonNull(iterable, "iterable");
		Objects.requireNonNull(componentClazzes, "componentClazzes");
		Map<Object, Clazz>[] trees = Clazz.trees(1, componentClazzes);

		Iterator iterator = iterable.iterator();
		for (int i = 0; iterator.hasNext(); i++)
			trees[0].put(i, Clazz.in(iterator.next()));

		return trees;
	}

	/**
	 * Get an array of trees auto-generated for the given {@code map}, Containing general component clazzes from the
	 * given {@code componentClazzes}.
	 * <pre>
	 *     Clazz.treesForMap(<font color="a5edff">INSTANCE</font>, <font color="d3c4ff">KEY_CLAZZ, VALUE_CLAZZ, …</font>)
	 *     &lt;<font color="d3c4ff">KEY_TREE, VALUE_TREE, …</font>&gt;
	 * </pre>
	 *
	 * @param map              the map the returned trees array is auto-generated from.
	 * @param componentClazzes the general component clazzes for each tree in the returned tree array.
	 * @return an array of trees auto-generated for the given {@code map}, Containing general component clazzes from the
	 * 		given {@code componentClazzes}.
	 * @throws NullPointerException if the given {@code map} or {@code componentClazzes} is null.
	 */
	static Map<Object, Clazz>[] treesForMap(Map map, Clazz[] componentClazzes) {
		Objects.requireNonNull(map, "map");
		Objects.requireNonNull(componentClazzes, "componentClazzes");
		Map<Object, Clazz>[] trees = Clazz.trees(2, componentClazzes);

		Iterator<Map.Entry> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = iterator.next();
			Object key = entry.getKey();
			Object value = entry.getValue();

			trees[0].put(key, Clazz.in(key));
			trees[1].put(key, Clazz.in(value));
		}

		return trees;
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getKlass() getKlass()}{@link Class#cast(Object) .cast(instance)}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public final T cast(Object instance) {
		return this.klass.cast(instance);
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getKlass() getKlass()}{@link Class#getComponentType() .getComponentType()}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public final Class getComponentType() {
		return this.klass.getComponentType();
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getKlass() getKlass()}{@link Class#isAnnotation() .isAnnotation()}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public final boolean isAnnotation() {
		return this.klass.isAnnotation();
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getKlass() getKlass()}{@link Class#isArray() .isArray()}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public final boolean isArray() {
		return this.klass.isArray();
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getKlass() getKlass()}{@link Class#isAssignableFrom(Class) .isAssignableFrom(klass)}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public final boolean isAssignableFrom(Class klass) {
		return this.klass.isAssignableFrom(klass);
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getKlass() getKlass()}{@link Class#isEnum() .isEnum()}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public final boolean isEnum() {
		return this.klass.isEnum();
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getKlass() getKlass()}{@link Class#isInterface() .isInterface()}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public final boolean isInterface() {
		return this.klass.isInterface();
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getKlass() getKlass()}{@link Class#isPrimitive() .isPrimitive()}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public final boolean isPrimitive() {
		return this.klass.isPrimitive();
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getKlass() getKlass()}{@link Class#isSynthetic() .isSynthetic()}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public final boolean isSynthetic() {
		return this.klass.isSynthetic();
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getKlass() getKlass()}{@link Class#newInstance() .newInstance()}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public final T newInstance() throws InstantiationException, IllegalAccessException {
		return this.klass.newInstance();
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getKlass() getKlass()}{@link Class#getConstructor(Class[]) .getConstructor(AUTO)}{@link Class#newInstance() .newInstance(parameters)}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public final T newInstance(Object... parameters) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		for0:
		for (Constructor<T> constructor : (Constructor<T>[]) this.klass.getDeclaredConstructors()) {
			Class[] parameterTypes = constructor.getParameterTypes();
			for (int i = 0; i < parameterTypes.length; i++)
				if (!parameterTypes[i].isInstance(parameters[i]))
					continue for0;

			return constructor.newInstance(parameters);
		}

		throw new NoSuchMethodException();
	}

	@Override
	public boolean equals(Object that) {
		return this == that ||
			   that instanceof Clazz &&
			   ((Clazz) that).klass == this.klass &&
			   ((Clazz) that).family == this.family &&
			   Arrays.equals(((Clazz) that).trees, this.trees);
	}

	@Override
	public String toString() {
		return "clazz " + this.getName();
	}

	@Override
	public String getTypeName() {
		//KLASS:FAMILY<COMPONENTS>
		StringBuilder typeName = new StringBuilder(this.klass.getTypeName());

		if (this.family != this.klass)
			typeName.append(":").append(this.family.getTypeName());

		if (this.trees.length != 0) {
			typeName.append("<");
			Iterator<ComponentTree> iterator = Arrayz.iterator(this.trees);
			while (iterator.hasNext())
				typeName.append(iterator.next().getTypeName())
						.append(iterator.hasNext() ? ", " : ">");
		}

		return typeName.toString();
	}

	/**
	 * Get the general clazz for all components in the specified {@code tree} to be held by an instance of this clazz.
	 * <br>
	 * Note: This is like calling {@link Clazz#getComponentClazz(int, Object) getComponentClazz(}{@link Clazz#COMPONENT
	 * Clazz.COMPONENT_TYPE)}.
	 *
	 * @param tree the index of the tree in this clazz that have the returned component clazz.
	 * @return the general clazz for all components in the specified {@code tree} to be held by an instance of this
	 * 		clazz. Or null if no tree have the given position, or that tree don't have a general component clazz.
	 * @since 0.1.5
	 */
	public Clazz getComponentClazz(int tree) {
		return tree >= 0 && tree < this.trees.length ? this.trees[tree].getComponentClazz() : null;
	}

	/**
	 * Get the component clazz of this clazz that have the given {@code key} in the specified {@code tree}.
	 * <br>
	 * Note: the key {@link Clazz#COMPONENT} is the key for the general component clazz.
	 *
	 * @param tree the index of the tree in this clazz that have the returned component clazz.
	 * @param key  the key associated to the returned clazz as a component in this clazz.
	 * @return the component clazz of this clazz that have the given {@code key} in the specified {@code tree}. Or null
	 * 		if no tree have the given position, or that tree don't have a clazz associated to the given {@code key} in it.
	 * @since 0.1.5
	 */
	public Clazz getComponentClazz(int tree, Object key) {
		return tree >= 0 && tree < this.trees.length ? this.trees[tree].getComponentClazz(key) : null;
	}

	/**
	 * Get the tree of the clazzes specified foreach component to be held by an instance of this clazz that have the
	 * given {@code tree} index.
	 * <br>
	 * Note: the returned tree will always be unmodifiable. Trying to modify it will always throw an exception.
	 *
	 * @param tree the index of the returned tree at this clazz.
	 * @return the tree of the clazzes specified foreach component to be held by an instance of this clazz. Or null if
	 * 		no tree have the given position.
	 * @since 0.1.5
	 */
	public ComponentTree getComponentTree(int tree) {
		return tree >= 0 && tree < this.trees.length ? this.trees[tree] : null;
	}

	/**
	 * Get a clone of the array holding all the trees of this clazz.
	 * <br>
	 * Note: all the returned trees will always be unmodifiable. Trying to modify them will always throw an exception.
	 *
	 * @return a clone of the array holding all the trees of this clazz.
	 * @since 0.1.5
	 */
	public ComponentTree[] getComponentTrees() {
		return Arrays.copyOf(this.trees, this.trees.length);
	}

	/**
	 * Get the class that an instance of this clazz should be treated as if it was an instance of it.
	 *
	 * @return the class that an instance of this clazz should be treated as if it was an instance of it.
	 * @since 0.1.5
	 */
	public Class getFamily() {
		return this.family;
	}

	/**
	 * Get the class represented by this clazz.
	 *
	 * @return the class represented by this clazz.
	 * @since 0.1.5
	 */
	public Class<T> getKlass() {
		return this.klass;
	}

	/**
	 * Get the name of this clazz. The name contains the name of the {@code family}, {@code klass}, and each {@code
	 * componentClazz} of each {@code tree} in this clazz.
	 *
	 * @return the name of this clazz.
	 * @since 0.1.5
	 */
	public String getName() {
		//KLASS:FAMILY<COMPONENTS>
		StringBuilder name = new StringBuilder(this.klass.getName());

		if (this.family != this.klass)
			name.append(":").append(this.family.getName());

		if (this.trees.length != 0) {
			name.append("<");
			Iterator<ComponentTree> iterator = Arrayz.iterator(this.trees);
			while (iterator.hasNext())
				name.append(iterator.next().getName())
						.append(iterator.hasNext() ? ", " : ">");
		}

		return name.toString();
	}

	/**
	 * Get the simple name of this clazz. The name contains the simple name of the {@code family}, {@code klass}, and
	 * each {@code componentClazz} of each {@code tree} in this clazz.
	 *
	 * @return the simple name of this clazz.
	 * @since 0.1.5
	 */
	public String getSimpleName() {
		//KLASS:FAMILY<COMPONENTS>
		StringBuilder simpleName = new StringBuilder(this.klass.getSimpleName());

		if (this.family != this.klass)
			simpleName.append(":").append(this.family.getSimpleName());

		if (this.trees.length != 0) {
			simpleName.append("<");
			Iterator<ComponentTree> iterator = Arrayz.iterator(this.trees);
			while (iterator.hasNext())
				simpleName.append(iterator.next().getSimpleName())
						.append(iterator.hasNext() ? ", " : ">");
		}

		return simpleName.toString();
	}

	/**
	 * Determine if the {@code klass} of this clazz has a primitive type.
	 *
	 * @return true, if the {@code klass} of this clazz has a primitive type.
	 * @since 0.1.5
	 */
	public boolean hasPrimitive() {
		return Reflection.hasPrimitiveClass(this.klass);
	}

	/**
	 * Determine if the {@code klass} of this clazz is assignable from the {@code klass} of the given clazz.
	 *
	 * @param klazz the clazz to be checked if its {@code klass} is assignable from the {@code klass} of this clazz.
	 * @return true, if the {@code klass} of this clazz is assignable from the {@code klass} of the given clazz.
	 * @throws NullPointerException if the given {@code klazz} is null.
	 * @see Class#isAssignableFrom(Class)
	 * @since 0.1.5
	 */
	public boolean isAssignableFrom(Clazz klazz) {
		Objects.requireNonNull(klazz, "klazz");
		return this.klass.isAssignableFrom(klazz.klass);
	}

	/**
	 * Determine if the given {@code instance} is an instance of the {@code klass} of this clazz.
	 * <br>
	 * Note: null is instance of {@link Void}.
	 *
	 * @param instance the instance to be checked if it is an instance of the {@code klass} of this clazz.
	 * @return true, if the given {@code instance} is an instance of the {@code klass} of this clazz.
	 * @since 0.1.5
	 */
	public boolean isInstance(Object instance) {
		return instance == null && this.klass == Void.class || this.klass.isInstance(instance);
	}

	/**
	 * Get a clazz that represents the {@code Object} version of the {@code klass} of this clazz, and have the {@code
	 * tree} of this clazz, and should be treated as if it was the {@code family} of this clazz.
	 *
	 * @return a clazz that represents the {@code Object} version of the {@code klass} of this clazz, and have the
	 *        {@code tree} of this clazz, and should be treated as if it was the {@code family} of this clazz.
	 * @since 0.1.5
	 */
	public Clazz toObjectClazz() {
		return !this.klass.isPrimitive() ? this :
			   Clazz.of(this.family, Reflection.asObjectClass(this.klass), this.trees);
	}

	/**
	 * Get a clazz that represents the primitive version of the {@code klass} of this clazz, and have the {@code tree}
	 * of this clazz, and should be treated as if it was the {@code family} of this clazz.
	 *
	 * @return a clazz that represents the primitive version of the {@code klass} of this clazz, and have the {@code
	 * 		tree} of this clazz, and should be treated as if it was the {@code family} of this clazz. Or null if there is
	 * 		no primitive class of the {@code klass} of this clazz.
	 * @since 0.1.5
	 */
	public Clazz toPrimitiveClazz() {
		return !Reflection.hasPrimitiveClass(this.klass) ? null :
			   Clazz.of(this.family, Reflection.asPrimitiveClass(this.klass), this.trees);
	}

	/**
	 * Get a clazz that represents the {@code klass} of this clazz, and have the {@code tree} of this clazz, and should
	 * be treated as if it was the given {@code family}.
	 *
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @return a clazz that represents the {@code klass} of this clazz, and have the {@code tree} of this clazz, and
	 * 		should be treated as if it was the given {@code family}.
	 * @throws NullPointerException if the given {@code family} is null.
	 * @since 0.1.5
	 */
	public Clazz with(Class family) {
		Objects.requireNonNull(family, "family");
		return Clazz.of(family, this.klass, this.trees);
	}

	/**
	 * Get a clazz that represents the {@code klass} of this clazz, and have the given {@code tree}, and should be
	 * treated as if it was the {@code family} of this clazz.
	 *
	 * @param tree the tree of the clazzes specified foreach component to be held by an instance of the returned clazz.
	 * @return a clazz that represents the {@code klass} of this clazz, and have the given {@code tree}, and should be
	 * 		treated as if it was the {@code family} of this clazz.
	 * @throws NullPointerException if the given {@code tree} is null.
	 * @since 0.1.5
	 */
	public Clazz with(Map<Object, Clazz> tree) {
		Objects.requireNonNull(tree, "tree");
		return Clazz.of(this.family, this.klass, tree);
	}

	/**
	 * Get a clazz that represents the {@code klass} of this clazz, and have the given {@code tree}, and should be
	 * treated as if it was the given {@code family}.
	 *
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param trees  an array of trees of the clazzes specified foreach component to be held by an instance of the
	 *               constructed clazz.
	 * @return a clazz that represents the {@code klass} of this clazz, and have the given {@code tree}, and should be
	 * 		treated as if it was the given {@code family}.
	 * @throws NullPointerException if the given {@code family} or {@code tree} is null.
	 * @since 0.1.5
	 */
	public Clazz with(Class family, Map<Object, Clazz>... trees) {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(trees, "trees");
		return Clazz.of(family, this.klass, trees);
	}

	@SuppressWarnings("JavaDoc")
	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
		Objects.requireNonNull(stream, "stream");
		int length = stream.readInt();
		if (length < 3) {
			this.klass = (Class) Object.class;
			this.family = Object.class;
			this.trees = Clazz.EMPTY_TREES;
		} else {
			this.klass = (Class) stream.readObject();
			this.family = (Class) stream.readObject();
			this.trees = (ComponentTree[]) stream.readObject();
		}
	}

	@SuppressWarnings("JavaDoc")
	private void writeObject(ObjectOutputStream stream) throws IOException {
		Objects.requireNonNull(stream, "stream");
		stream.writeInt(/*length*/3);
		stream.writeObject(this.klass);
		stream.writeObject(this.family);
		stream.writeObject(this.trees);
	}

	/**
	 * A component clazzes map that can't be modified and clazz consider it safe to be stored as its tree.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5
	 */
	public static final class ComponentTree extends AbstractMap<Object, Clazz> {
		/**
		 * An unmodifiable entry-set containing the {@link Map.Entry}s of this tree.
		 */
		private final Set<Entry<Object, Clazz>> entrySet;

		/**
		 * Construct a new empty clazzes tree that can't be modified and contains nothing.
		 */
		private ComponentTree() {
			this.entrySet = Collections.emptySet();
		}

		/**
		 * Construct a new clazzes tree that can't be modified and contains a clone of the mappings of the given tree.
		 *
		 * @param tree to clone the mappings of it to the constructed tree.
		 * @throws NullPointerException     if the given {@code tree} is null.
		 * @throws IllegalArgumentException if a value is stored in the given {@code tree} is not an instance of {@link
		 *                                  Clazz}.
		 */
		private ComponentTree(Map<Object, Clazz> tree) {
			if (tree instanceof Clazz.ComponentTree)
				this.entrySet = ((ComponentTree) tree).entrySet;
			else {
				Set entrySet = new HashSet();
				tree.forEach((key, klazz) -> {
					if (!(klazz instanceof Clazz))
						throw new IllegalArgumentException("Not a clazz " + klazz);

					entrySet.add(new SimpleImmutableEntry(key, klazz));
				});
				this.entrySet = Collections.unmodifiableSet(entrySet);
			}
		}

		@Override
		public Set<Entry<Object, Clazz>> entrySet() {
			return this.entrySet;
		}

		@Override
		public String toString() {
			return "tree " + this.getName();
		}

		/**
		 * Get the {@link #COMPONENT componentClazz} of this tree. The {@link #COMPONENT componentClazz} is the clazz
		 * for the elements that have no clazz in the tree of the clazz representing it.
		 *
		 * @return the {@link #COMPONENT componentClazz} of this tree, Or null if this tree has no {@link #COMPONENT
		 * 		componentClazz}.
		 * @since 0.1.5
		 */
		public Clazz getComponentClazz() {
			return this.get(Clazz.COMPONENT);
		}

		/**
		 * Get the {@code componentClazz} for the element with the given {@code key} in this tree.
		 *
		 * @param key the key of the returned clazz in this tree.
		 * @return the {@code componentClazz} for the element with the given {@code key} in this tree, Or the {@link
		 *        #COMPONENT componentClazz} of this tree if there is no {@code componentClazz} with the given key in this
		 * 		tree.
		 * @since 0.1.5
		 */
		public Clazz getComponentClazz(Object key) {
			Clazz klazz = this.get(key);
			return klazz == null ? this.get(Clazz.COMPONENT) : klazz;
		}

		/**
		 * Get the {@code Name} of this tree. The {@code Name} of a tree is the {@link #getName() Name} of its {@link
		 * #COMPONENT componentClazz}, or {@code "?"} if it has no {@link #COMPONENT componentClazz}.
		 *
		 * @return the {@code Name} of this tree, Or {@code "?"} if this tree has no {@link #COMPONENT componentClazz}.
		 * @since 0.1.5
		 */
		public String getName() {
			Clazz component = this.get(Clazz.COMPONENT);
			return component == null ? "?" : component.getName();
		}

		/**
		 * Get the {@code SimpleName} of this tree. The {@code SimpleName} of a tree is the {@link #getSimpleName()
		 * SimpleName} of its {@link #COMPONENT componentClazz}, or {@code "?"} if it has no {@link #COMPONENT
		 * componentClazz}.
		 *
		 * @return the {@code SimpleName} of this tree, Or {@code "?"} if this tree has no {@link #COMPONENT
		 * 		componentClazz}.
		 * @since 0.1.5
		 */
		public String getSimpleName() {
			Clazz component = this.get(Clazz.COMPONENT);
			return component == null ? "?" : component.getSimpleName();
		}

		/**
		 * Get the {@code TypeName} of this tree. The {@code TypeName} of a tree is the {@link #getTypeName() TypeName}
		 * of its {@link #COMPONENT componentClazz}, or {@code "?"} if it has no {@link #COMPONENT componentClazz}.
		 *
		 * @return the {@code TypeName} of this tree, Or {@code "?"} if this tree has no {@link #COMPONENT
		 * 		componentClazz}.
		 * @since 0.1.5
		 */
		public String getTypeName() {
			Clazz component = this.get(Clazz.COMPONENT);
			return component == null ? "?" : component.getTypeName();
		}
	}
}
