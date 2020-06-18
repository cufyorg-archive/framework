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
 *
 * @param <T> the type of the class represented by this clazz.
 * @author lsafer
 * @version 0.1.5
 * @since 29-Mar-2020
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

	//constructor

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
		this.trees = Clazz.trf(trees);
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
		this.trees = Clazz.trf(trees);
	}

	//isx: is tree-able

	/**
	 * Determine if it is able to auto-construct a tree for the given {@code instance} using the auto-generating methods
	 * of {@link Clazz#ofx} or {@link Clazz#trx}.
	 *
	 * @param instance to be checked if a tree can be auto-constructed for it.
	 * @return true, if the auto-generating methods of {@link Clazz#ofx} and {@link Clazz#trx} can auto-construct a tree
	 * 		for the given {@code instance}.
	 */
	public static boolean isx(Object instance) {
		return instance != null && Clazz.isx(instance.getClass());
	}

	/**
	 * Determine if it is able to auto-construct a tree for the instances of the given {@code klass} using the
	 * auto-generating methods of {@link Clazz#ofx} or {@link Clazz#trx}.
	 *
	 * @param klass to be checked if a tree can be auto-constructed for its instances.
	 * @return true, if the auto-generating methods of {@link Clazz#ofx} and {@link Clazz#trx} can auto-construct a tree
	 * 		for the instances of the given {@code klass}.
	 * @throws NullPointerException if the given {@code klass} is null.
	 */
	public static boolean isx(Class klass) {
		Objects.requireNonNull(klass, "klass");
		return Map.class.isAssignableFrom(klass) ||
			   List.class.isAssignableFrom(klass) ||
			   klass.isArray();
	}

	//of: of raw parameters

	/**
	 * Get a clazz that represents the given {@code klass}, and have the given {@code trees}.
	 *
	 * @param klass the class to be represented by the returned clazz.
	 * @param trees an array of trees of the clazzes specified foreach component to be held by an instance of the
	 *              constructed clazz.
	 * @param <T>   the type of the class represented by the returned clazz.
	 * @return a clazz that represents the given {@code klass}, and have the given {@code trees}.
	 * @throws NullPointerException if the given {@code klass} or {@code trees} or any of its elements is null.
	 */
	public static <T> Clazz<T> of(Class<T> klass, Map<Object, Clazz>... trees) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(trees, "trees");
		return new Clazz(klass, trees);
	}

	/**
	 * Get a clazz that represents the given {@code klass}, and have the given {@code tree}, and should be treated as if
	 * it was the given {@code family}.
	 *
	 * @param klass  the class to be represented by the returned clazz.
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param trees  an array of trees of the clazzes specified foreach component to be held by an instance of the
	 *               constructed clazz.
	 * @param <T>    the type of the class represented by the returned clazz.
	 * @return a new clazz that represents the given {@code klass}, and have the given {@code tree}, and should be
	 * 		treated as if it was the given {@code family}.
	 * @throws NullPointerException if the given {@code klass} or {@code family} or {@code tree} is null.
	 */
	public static <T> Clazz<T> of(Class<T> klass, Class family, Map<Object, Clazz>... trees) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(trees, "tree");
		return new Clazz(klass, family, trees);
	}

	//ofa: of an array class

	/**
	 * Get a clazz that represents the given {@code klass}, and with a {@code tree} auto generated from the given
	 * array's class.
	 *
	 * @param klass the array class to be represented by the returned clazz.
	 * @param <T>   the type of the class represented by the returned clazz.
	 * @return a clazz that represents the given {@code klass}, and with a {@code tree} auto generated from the given
	 * 		array's class.
	 * @throws NullPointerException     if the given {@code klass} is null.
	 * @throws IllegalArgumentException if the given {@code klass} is not an array's clazz.
	 */
	public static <T> Clazz<T> ofa(Class<T> klass) {
		Objects.requireNonNull(klass, "klass");
		if (!klass.isArray())
			throw new IllegalArgumentException("Not an array's class " + klass);

		Class component = klass.getComponentType();
		Clazz componentClazz = component.isArray() ? Clazz.ofa(component) : Clazz.of(component);

		return Clazz.of(klass, Clazz.tre(componentClazz));
	}

	/**
	 * Get a clazz that represents the given {@code klass}, and with a {@code tree} auto generated from the given
	 * array's class, and should be treated as if it was the given {@code family}.
	 *
	 * @param klass  the array class to be represented by the returned clazz.
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param <T>    the type of the class represented by the returned clazz.
	 * @return a clazz that represents the given {@code klass}, and with a {@code tree} auto generated from the given
	 * 		array's class, and should be treated as if it was the given {@code family}.
	 * @throws NullPointerException     if the given {@code klass} or {@code family} is null.
	 * @throws IllegalArgumentException if the given {@code klass} is not an array's clazz.
	 */
	public static <T> Clazz<T> ofa(Class<T> klass, Class family) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(family, "family");
		if (!klass.isArray())
			throw new IllegalArgumentException(klass + " is not an array-class");

		Class component = klass.getComponentType();
		Class componentFamily = family.isArray() ? family.getComponentType() : component;
		Clazz componentClazz =
				component.isArray() ? Clazz.ofa(componentFamily, component) : Clazz.of(componentFamily, component);

		return Clazz.of(klass, family, Clazz.tre(componentClazz));
	}

	//ofi: of an instance

	/**
	 * Get a clazz that represents the clazz of the given {@code instance}, and have the given {@code tree}.
	 * <br>
	 * Note: {@link Void} is the class of null.
	 *
	 * @param instance an instance that its class to be represented by the returned clazz.
	 * @param trees    an array of trees of the clazzes specified foreach component to be held by an instance of the
	 *                 constructed clazz.
	 * @param <T>      the type of the class represented by the returned clazz.
	 * @return a clazz that represents the clazz of the given {@code instance}, and have the given {@code tree}.
	 * @throws NullPointerException if the given {@code tree} is null.
	 * @see Object#getClass()
	 */
	public static <T> Clazz<T> ofi(T instance, Map<Object, Clazz>... trees) {
		Objects.requireNonNull(trees, "trees");
		return instance == null ? (Clazz<T>) Clazz.of(Void.class, trees)
								: (Clazz<T>) Clazz.of(instance.getClass(), trees);
	}

	/**
	 * Get a clazz that represents the clazz of the given {@code instance}, and have the given {@code tree}, and should
	 * be treated as if it was the given {@code family}.
	 * <br>
	 * Note: {@link Void} is the class of null.
	 *
	 * @param instance an instance that its class to be represented by the returned clazz.
	 * @param family   the class that an instance of the returned clazz should be treated as if it was an instance of
	 *                 it.
	 * @param trees    an array of trees of the clazzes specified foreach component to be held by an instance of the
	 *                 constructed clazz.
	 * @param <T>      the type of the class represented by the returned clazz.
	 * @return a clazz that represents the clazz of the given {@code instance}, and have the given {@code tree}, and
	 * 		should be treated as if it was the given {@code family}.
	 * @throws NullPointerException if the given {@code family} or {@code tree} is null.
	 * @see Object#getClass()
	 */
	public static <T> Clazz<T> ofi(T instance, Class family, Map<Object, Clazz>... trees) {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(trees, "trees");
		return instance == null ? (Clazz<T>) Clazz.of(Void.class, family, trees)
								: (Clazz<T>) Clazz.of(instance.getClass(), family, trees);
	}

	//ofn: of the name of a class

	/**
	 * Get a clazz that represents the class with the given {@code name}, and have the given {@code tree}.
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
	 */
	public static Clazz ofn(String name, Map<Object, Clazz>... trees) throws ClassNotFoundException {
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(trees, "trees");
		return Clazz.of(Class.forName(name), trees);
	}

	/**
	 * Get a clazz that represents the class with the given {@code name}, and have the given {@code tree}, and should be
	 * treated as if it was the given {@code family}.
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
	 */
	public static Clazz ofn(String name, Class family, Map<Object, Clazz>... trees) throws ClassNotFoundException {
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(trees, "trees");
		return Clazz.of(Class.forName(name), family, trees);
	}

	/**
	 * Get a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and have
	 * the given {@code tree}.
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
	 */
	public static Clazz ofn(ClassLoader loader, boolean initialize, String name, Map<Object, Clazz>... trees) throws ClassNotFoundException {
		Objects.requireNonNull(loader, "loader");
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(trees, "trees");
		return Clazz.of(Class.forName(name, initialize, loader), trees);
	}

	/**
	 * Get a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and have
	 * the given {@code tree}, and should be treated as if it was the given {@code family}.
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
	 */
	public static Clazz ofn(ClassLoader loader, boolean initialize, String name, Class family, Map<Object, Clazz>... trees) throws ClassNotFoundException {
		Objects.requireNonNull(loader, "loader");
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(trees, "trees");
		return Clazz.of(Class.forName(name, initialize, loader), family, trees);
	}

	//ofx: of a tree-able instance

	/**
	 * Get a clazz that represents the class of the given {@code instance}, and with a {@code trees} auto generated from
	 * the elements of the given {@code instance} with the given {@code componentClazzes}.
	 * <br>
	 * Note: If a component clazz is missing, the standard component clazz for the instance that miss it will be taken.
	 *
	 * @param instance         an instance that its class to be represented by the returned clazz, and its elements is
	 *                         the source of its trees.
	 * @param componentClazzes the clazzes for the elements that can't construct a sub-tree for it. The clazzes should
	 *                         be ordered to its targeted auto-generated tree.
	 * @param <T>              the type of the class represented by the returned clazz.
	 * @return a clazz that represents the class of the given {@code instance}, and with a {@code trees} auto generated
	 * 		from the elements of the given {@code instance} with the given {@code componentClazzes}.
	 * @throws NullPointerException if the given {@code componentClazzes} is null.
	 */
	public static <T> Clazz<T> ofx(T instance, Clazz... componentClazzes) {
		Objects.requireNonNull(componentClazzes, "componentClazzes");
		return Clazz.ofi(instance, Clazz.trx(instance, componentClazzes));
	}

	/**
	 * Get a clazz that represents the class of the given {@code instance}, and should be treated as if it was the given
	 * {@code family}, and with a {@code trees} auto generated from the elements of the given {@code instance} with the
	 * given {@code componentClazzes}.
	 * <br>
	 * Note: If a component clazz is missing, the standard component clazz for the instance that miss it will be taken.
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
	 */
	public static <T> Clazz<T> ofx(T instance, Class family, Clazz... componentClazzes) {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(componentClazzes, "componentClazzes");
		return Clazz.ofi(instance, family, Clazz.trx(instance, componentClazzes));
	}

	//ofz: of parts of three clazzes

	/**
	 * Get a clazz that represents the {@code klass} of the given {@code klassSrc}, and have the {@code tree} of the
	 * given {@code treeSrc}, and should be treated as if it was the {@code family} of the given {@code familySrc}.
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
	 */
	public static <T> Clazz<T> ofz(Clazz<T> klassSrc, Clazz familySrc, Clazz treesSrc) {
		Objects.requireNonNull(klassSrc, "klassSrc");
		Objects.requireNonNull(familySrc, "familySrc");
		Objects.requireNonNull(treesSrc, "treesSrc");
		return Clazz.of(klassSrc.klass, familySrc.family, treesSrc.trees);
	}

	//tre: generate tree

	/**
	 * Get a tree that have no general clazz, and have no mappings.
	 *
	 * @return a tree that have no general clazz, and have no mappings.
	 */
	public static Map<Object, Clazz> tre() {
		return new HashMap<>();
	}

	/**
	 * Get a tree that its general clazz is the given {@code componentClazz}, and have no mappings.
	 *
	 * @param componentClazz the general clazz for the components that have no mapping in the returned tree.
	 * @return a tree that its general clazz is the given {@code componentClazz}, and have no mappings.
	 */
	public static Map<Object, Clazz> tre(Clazz componentClazz) {
		return new HashMap<>(Collections.singletonMap(Clazz.COMPONENT, componentClazz));
	}

	/**
	 * Get a tree that its general clazz is a clazz represents the given {@code componentClass}, and have no mappings.
	 *
	 * @param componentClass the class that the clazz represents it is the general clazz for the components that have no
	 *                       mapping in the returned tree.
	 * @return a tree that its general clazz is the given {@code componentClazz}, and have no mappings.
	 */
	public static Map<Object, Clazz> tre(Class componentClass) {
		return componentClass == null ? Clazz.tre() : Clazz.tre(Clazz.of(componentClass));
	}

	//trf: finalize tree/s

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
	 */
	public static ComponentTree trf(Map<Object, Clazz> tree) {
		Objects.requireNonNull(tree, "tree");
		return tree instanceof Clazz.ComponentTree ?
			   (ComponentTree) tree :
			   tree.isEmpty() ?
			   Clazz.EMPTY_TREE :
			   new ComponentTree(tree);
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
	 */
	public static ComponentTree[] trf(Map<Object, Clazz>... trees) {
		if (trees.length == 0)
			return Clazz.EMPTY_TREES;
		else {
			Objects.requireNonNull(trees, "trees");
			ComponentTree[] r = new ComponentTree[trees.length];

			for (int i = 0; i < trees.length; i++)
				r[i] = Clazz.trf(trees[i]);

			return r;
		}
	}

	//trl: linear tree

	/**
	 * Get a linear tree of general component clazzes. The general clazzes specified in order representing the given
	 * {@code componentClasses}.
	 *
	 * @param componentClasses an ordered list of the classes represented by the nested general component clazzes in
	 *                         returned tree.
	 * @return a linear tree of general component clazzes. The general clazzes specified in order representing the given
	 *        {@code componentClasses}.
	 * @throws NullPointerException if the given {@code componentClasses} or any of its elements is null.
	 */
	public static Map<Object, Clazz> trl(Class... componentClasses) {
		Objects.requireNonNull(componentClasses, "componentClasses");
		return componentClasses.length == 0 ? Clazz.tre() :
			   Clazz.tre(Clazz.of(
					   Objects.requireNonNull(componentClasses[0], "componentClasses[?]"),
					   Clazz.trl(Arrays.copyOfRange(
							   componentClasses,
							   1,
							   componentClasses.length
					   ))));
	}

	/**
	 * Get linear trees of general component clazzes. The given {@code componentClasses} specifies each array for a tree
	 * in the returned trees array.
	 * <br>
	 * Note: this is just an array version of {@link Clazz#trl(Class[])}.
	 *
	 * @param componentClasses an array of component classes, each array goes to the same position tree in the returned
	 *                         trees array.
	 * @return linear trees of general component clazzes. The given {@code componentClasses} specifies each array for a
	 * 		tree *in the returned trees array.
	 * @throws NullPointerException if the given {@code componentClasses} or any of its elements is null.
	 */
	public static Map<Object, Clazz>[] trl(Class[]... componentClasses) {
		Objects.requireNonNull(componentClasses, "componentClasses");
		Map<Object, Clazz>[] trees = new Map[componentClasses.length];

		for (int i = 0; i < componentClasses.length; i++)
			trees[i] = Clazz.trl(componentClasses[i]);

		return trees;
	}

	//trs: generate trees

	/**
	 * Get an empty array of trees.
	 *
	 * @return an empty array of trees.
	 */
	public static ComponentTree[] trs() {
		return Clazz.EMPTY_TREES;
	}

	/**
	 * Get an array of trees with the given {@code length}.
	 * <br>
	 * Note: this is just an array version of {@link Clazz#tre()}.
	 *
	 * @param length how many trees to be returned.
	 * @return an array of trees with the given {@code length}.
	 * @throws IllegalArgumentException if the given {@code length} is negative.
	 */
	public static Map<Object, Clazz>[] trs(int length) {
		if (length < 0)
			throw new IllegalArgumentException("negative array size: " + length);
		Map<Object, Clazz>[] trees = new Map[length];

		for (int i = 0; i < length; i++)
			trees[i] = Clazz.tre();

		return trees;
	}

	/**
	 * Get an array of trees that each one's general clazz is the clazz at the same as its position in the given {@code
	 * componentClazzes}.
	 * <br>
	 * Note: this is just an array version of {@link Clazz#tre(Clazz)}.
	 *
	 * @param componentClazzes the general clazzes array for the components that have no mapping in the tree at the same
	 *                         position as the clazz in returned trees array.
	 * @return an array of trees that each one's general clazz is the clazz at the same as its position in the given
	 *        {@code componentClazzes}.
	 * @throws NullPointerException if the given {@code componentClazzes} is null.
	 */
	public static Map<Object, Clazz>[] trs(Clazz... componentClazzes) {
		Objects.requireNonNull(componentClazzes, "componentClazzes");
		Map<Object, Clazz>[] trees = new Map[componentClazzes.length];

		for (int i = 0; i < componentClazzes.length; i++)
			trees[i] = Clazz.tre(componentClazzes[i]);

		return trees;
	}

	/**
	 * Get an array of trees that each one's general clazz is the clazz at the same as its position in the given {@code
	 * componentClazzes}.
	 * <br>
	 * Note: this is just an array version of {@link Clazz#tre(Clazz)}.
	 *
	 * @param ml               the minimum length the returned array will be
	 * @param componentClazzes the general clazzes array for the components that have no mapping in the tree at the same
	 *                         position as the clazz in returned trees array.
	 * @return an array of trees that each one's general clazz is the clazz at the same as its position in the given
	 *        {@code componentClazzes}.
	 * @throws NullPointerException if the given {@code componentClazzes} is null.
	 */
	public static Map<Object, Clazz>[] trs(int ml, Clazz... componentClazzes) {
		Objects.requireNonNull(componentClazzes, "componentClazzes");
		Map<Object, Clazz>[] trees = new Map[Math.max(componentClazzes.length, ml)];

		int i = 0;
		for (; i < componentClazzes.length; i++)
			trees[i] = Clazz.tre(componentClazzes[i]);
		for (; i < trees.length; i++)
			trees[i] = Clazz.tre();

		return trees;
	}

	/**
	 * Get an array of trees that each one's general clazz is the clazz of the class at the same as its position in the
	 * given {@code componentClasses}.
	 * <br>
	 * Note: this is just an array version of {@link Clazz#tre(Class)}.
	 *
	 * @param componentClasses the general clazzes's classes array for the components that have no mapping in the tree
	 *                         at the same position as the class in returned trees array.
	 * @return an array of trees that each one's general clazz is the clazz of the class at the same as its position in
	 * 		the given {@code componentClasses}.
	 * @throws NullPointerException if the given {@code componentClazzes} is null.
	 */
	public static Map<Object, Clazz>[] trs(Class... componentClasses) {
		Objects.requireNonNull(componentClasses, "componentClasses");
		Map<Object, Clazz>[] trees = new Map[componentClasses.length];

		for (int i = 0; i < componentClasses.length; i++)
			trees[i] = Clazz.tre(componentClasses[i]);

		return trees;
	}

	/**
	 * Get an array of trees that each one's general clazz is the clazz of the class at the same as its position in the
	 * given {@code componentClasses}.
	 * <br>
	 * Note: this is just an array version of {@link Clazz#tre(Class)}.
	 *
	 * @param ml               the minimum length the returned array will be
	 * @param componentClasses the general clazzes's classes array for the components that have no mapping in the tree
	 *                         at the same position as the class in returned trees array.
	 * @return an array of trees that each one's general clazz is the clazz of the class at the same as its position in
	 * 		the given {@code componentClasses}.
	 * @throws NullPointerException if the given {@code componentClazzes} is null.
	 */
	public static Map<Object, Clazz>[] trs(int ml, Class... componentClasses) {
		Objects.requireNonNull(componentClasses, "componentClasses");
		Map<Object, Clazz>[] trees = new Map[Math.max(componentClasses.length, ml)];

		int i = 0;
		for (; i < componentClasses.length; i++)
			trees[i] = Clazz.tre(componentClasses[i]);
		for (; i < trees.length; i++)
			trees[i] = Clazz.tre();

		return trees;
	}

	//trx: tree a tree-able

	/**
	 * Get an array of trees auto-generated for the given {@code instance}, Containing general component clazzes from
	 * the given {@code componentClazzes}.
	 *
	 * @param instance         the instance the returned trees array is auto-generated from.
	 * @param componentClazzes the general component clazzes for each tree in the returned tree array.
	 * @return an array of trees auto-generated for the given {@code instance}, Containing general component clazzes
	 * 		from the given {@code componentClazzes}.
	 * @throws NullPointerException if the given {@code componentClazzes} is null.
	 */
	public static Map<Object, Clazz>[] trx(Object instance, Clazz... componentClazzes) {
		if (instance == null)
			return Clazz.trs();
		if (instance instanceof List)
			return Clazz.trx((List) instance, componentClazzes);
		if (instance instanceof Map)
			return Clazz.trx((Map) instance, componentClazzes);
		if (instance.getClass().isArray())
			return Clazz.trx(
					Arrayz.asList1(instance),
					componentClazzes.length > 0 ? componentClazzes :
					new Clazz[]{Clazz.of(instance.getClass().getComponentType())}
			);

		return Clazz.trs(componentClazzes);
	}

	/**
	 * Get an array of trees auto-generated for the given {@code instance}, Containing general component clazzes from
	 * the clazzes of the given {@code componentClasses}.
	 *
	 * @param instance         the instance the returned trees array is auto-generated from.
	 * @param componentClasses the classes of the general component clazzes for each tree in the returned tree array.
	 * @return an array of trees auto-generated for the given {@code instance}, Containing general component clazzes
	 * 		from the clazzes of the given {@code componentClasses}.
	 * @throws NullPointerException if the given {@code componentClasses} is null.
	 */
	public static Map<Object, Clazz>[] trx(Object instance, Class... componentClasses) {
		Objects.requireNonNull(componentClasses, "componentClasses");
		Clazz[] componentClazzes = new Clazz[componentClasses.length];

		for (int i = 0; i < componentClasses.length; i++)
			if (componentClasses[i] != null)
				componentClazzes[i] = Clazz.of(componentClasses[i]);

		return Clazz.trx(instance, componentClazzes);
	}

	/**
	 * Get an array of trees auto-generated for the given {@code map}, Containing general component clazzes from the
	 * given {@code componentClazzes}.
	 *
	 * @param map              the map the returned trees array is auto-generated from.
	 * @param componentClazzes the general component clazzes for each tree in the returned tree array.
	 * @return an array of trees auto-generated for the given {@code map}, Containing general component clazzes from the
	 * 		given {@code componentClazzes}.
	 * @throws NullPointerException if the given {@code map} or {@code componentClazzes} is null.
	 */
	private static Map<Object, Clazz>[] trx(Map map, Clazz... componentClazzes) {
		Objects.requireNonNull(map, "map");
		Objects.requireNonNull(componentClazzes, "componentClazzes");
		Map<Object, Clazz>[] trees = Clazz.trs(2, componentClazzes);

		map.forEach((key, value) -> {
			if (Clazz.isx(value))
				trees[1].put(key, Clazz.ofx(value));
		});

		return trees;
	}

	/**
	 * Get an array of trees auto-generated for the given {@code list}, Containing general component clazzes from the
	 * given {@code componentClazzes}.
	 *
	 * @param list             the list the returned trees array is auto-generated from.
	 * @param componentClazzes the general component clazzes for each tree in the returned tree array.
	 * @return an array of trees auto-generated for the given {@code list}, Containing general component clazzes from
	 * 		the given {@code componentClazzes}.
	 * @throws NullPointerException if the given {@code list} or {@code componentClazzes} is null.
	 */
	private static Map<Object, Clazz>[] trx(List list, Clazz... componentClazzes) {
		Objects.requireNonNull(list, "list");
		Objects.requireNonNull(componentClazzes, "componentClazzes");
		Map<Object, Clazz>[] trees = Clazz.trs(1, componentClazzes);

		int i = 0;
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			Object element = iterator.next();
			if (Clazz.isx(element))
				trees[0].put(i++, Clazz.ofx(element));
		}

		return trees;
	}

	//delegate

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getKlass() getKlass()}{@link Class#cast(Object) .cast(instance)}
	 * </pre>
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

	//clazz's

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
		StringBuilder name = new StringBuilder();

		if (this.family != this.klass)
			name.append(this.family.getTypeName()).append(":");

		name.append(this.klass.getTypeName());

		if (this.trees.length != 0) {
			name.append("<");
			Iterator<ComponentTree> iterator = Arrays.asList(this.trees).iterator();
			while (iterator.hasNext()) {
				Clazz componentClazz = iterator.next().get(Clazz.COMPONENT);
				name.append(componentClazz == null ? "?" : componentClazz.getTypeName())
						.append(iterator.hasNext() ? ", " : ">");
			}
		}

		return name.toString();
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
	 */
	public Clazz getComponentClazz(int tree) {
		ComponentTree t = this.getComponentTree(tree);
		return t == null ? null : t.get(Clazz.COMPONENT);
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
	 */
	public Clazz getComponentClazz(int tree, Object key) {
		ComponentTree t = this.getComponentTree(tree);
		Clazz k;
		return t == null ? null : (k = t.get(key)) == null ? t.get(Clazz.COMPONENT) : k;
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
	 */
	public ComponentTree[] getComponentTrees() {
		return Arrays.copyOf(this.trees, this.trees.length);
	}

	/**
	 * Get the class that an instance of this clazz should be treated as if it was an instance of it.
	 *
	 * @return the class that an instance of this clazz should be treated as if it was an instance of it.
	 */
	public Class getFamily() {
		return this.family;
	}

	/**
	 * Get the class represented by this clazz.
	 *
	 * @return the class represented by this clazz.
	 */
	public Class<T> getKlass() {
		return this.klass;
	}

	/**
	 * Get the name of this clazz. The name contains the name of the {@code family}, {@code klass}, and each {@code
	 * componentClazz} of each {@code tree} in this clazz.
	 *
	 * @return the name of this clazz.
	 */
	public String getName() {
		StringBuilder name = new StringBuilder();

		if (this.family != this.klass)
			name.append(this.family.getName()).append(":");

		name.append(this.klass.getName());

		if (this.trees.length != 0) {
			name.append("<");
			Iterator<ComponentTree> iterator = Arrays.asList(this.trees).iterator();
			while (iterator.hasNext()) {
				Clazz componentClazz = iterator.next().get(Clazz.COMPONENT);
				name.append(componentClazz == null ? "?" : componentClazz.getName())
						.append(iterator.hasNext() ? ", " : ">");
			}
		}

		return name.toString();
	}

	/**
	 * Get the simple name of this clazz. The name contains the simple name of the {@code family}, {@code klass}, and
	 * each {@code componentClazz} of each {@code tree} in this clazz.
	 *
	 * @return the simple name of this clazz.
	 */
	public String getSimpleName() {
		StringBuilder name = new StringBuilder();

		if (this.family != this.klass)
			name.append(this.family.getSimpleName()).append(":");

		name.append(this.klass.getSimpleName());

		if (this.trees.length != 0) {
			name.append("<");
			Iterator<ComponentTree> iterator = Arrays.asList(this.trees).iterator();
			while (iterator.hasNext()) {
				Clazz componentClazz = iterator.next().get(Clazz.COMPONENT);
				name.append(componentClazz == null ? "?" : componentClazz.getSimpleName())
						.append(iterator.hasNext() ? ", " : ">");
			}
		}

		return name.toString();
	}

	/**
	 * Determine if the {@code klass} of this clazz has a primitive type.
	 *
	 * @return true, if the {@code klass} of this clazz has a primitive type.
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
	 */
	public boolean isInstance(Object instance) {
		return instance == null ? this.klass == Void.class : this.klass.isInstance(instance);
	}

	/**
	 * Get a clazz that represents the {@code klass} of this clazz, and have the given {@code tree}, and should be
	 * treated as if it was the {@code family} of this clazz.
	 *
	 * @param tree the tree of the clazzes specified foreach component to be held by an instance of the returned clazz.
	 * @return a clazz that represents the {@code klass} of this clazz, and have the given {@code tree}, and should be
	 * 		treated as if it was the {@code family} of this clazz.
	 * @throws NullPointerException if the given {@code tree} is null.
	 */
	public Clazz override(Map<Object, Clazz> tree) {
		Objects.requireNonNull(tree, "tree");
		return Clazz.of(this.family, this.klass, tree);
	}

	/**
	 * Get a clazz that represents the {@code klass} of this clazz, and have the {@code tree} of this clazz, and should
	 * be treated as if it was the given {@code family}.
	 *
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @return a clazz that represents the {@code klass} of this clazz, and have the {@code tree} of this clazz, and
	 * 		should be treated as if it was the given {@code family}.
	 * @throws NullPointerException if the given {@code family} is null.
	 */
	public Clazz override(Class family) {
		Objects.requireNonNull(family, "family");
		return Clazz.of(family, this.klass, this.trees);
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
	 */
	public Clazz override(Class family, Map<Object, Clazz>... trees) {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(trees, "trees");
		return Clazz.of(family, this.klass, trees);
	}

	/**
	 * Get a clazz that represents the {@code Object} version of the {@code klass} of this clazz, and have the {@code
	 * tree} of this clazz, and should be treated as if it was the {@code family} of this clazz.
	 *
	 * @return a clazz that represents the {@code Object} version of the {@code klass} of this clazz, and have the
	 *        {@code tree} of this clazz, and should be treated as if it was the {@code family} of this clazz.
	 */
	public Clazz toObjectClazz() {
		return Clazz.of(this.family, Reflection.asObjectClass(this.klass), this.trees);
	}

	/**
	 * Get a clazz that represents the primitive version of the {@code klass} of this clazz, and have the {@code tree}
	 * of this clazz, and should be treated as if it was the {@code family} of this clazz.
	 *
	 * @return a clazz that represents the primitive version of the {@code klass} of this clazz, and have the {@code
	 * 		tree} of this clazz, and should be treated as if it was the {@code family} of this clazz. Or null if there is
	 * 		no primitive class of the {@code klass} of this clazz.
	 */
	public Clazz toPrimitiveClazz() {
		return !Reflection.hasPrimitiveClass(this.klass) ? null :
			   Clazz.of(this.family, Reflection.asPrimitiveClass(this.klass), this.trees);
	}

	@SuppressWarnings("JavaDoc")
	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
		Objects.requireNonNull(stream, "stream");
		this.klass = (Class) stream.readObject();
		this.family = (Class) stream.readObject();
		this.trees = (ComponentTree[]) stream.readObject();
		if (stream.readBoolean())
			//there is more
			System.err.println("Deserializing newer version of Clazz");
	}

	@SuppressWarnings("JavaDoc")
	private void writeObject(ObjectOutputStream stream) throws IOException {
		Objects.requireNonNull(stream, "stream");
		stream.writeObject(this.klass);
		stream.writeObject(this.family);
		stream.writeObject(this.trees);
		stream.writeBoolean(false);
		//free to add more
	}

	/**
	 * A component clazzes map that can't be modified and clazz consider it safe to be stored as its tree.
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
					if (klazz instanceof Clazz)
						entrySet.add(new SimpleImmutableEntry(key, klazz));
					else
						throw new IllegalArgumentException("Not a clazz " + klazz);
				});
				this.entrySet = Collections.unmodifiableSet(entrySet);
			}
		}

		@Override
		public Set<Entry<Object, Clazz>> entrySet() {
			return this.entrySet;
		}
	}
}
