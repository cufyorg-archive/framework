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

import cufy.util.Reflection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * An alternative representation for {@link Class classes}. This provides more data about the targeted type. About the components tree and the family
 * of that class. The family is how the class should be treated. The clazz-tree is a tree of clazzes for each specific component in an instance this
 * clazz is representing.
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
	 * The class that an instance of this clazz should be treated as if it was an instance of it. This field should be treated as final field.
	 */
	private Class family;
	/**
	 * The class represented by this clazz. This field should be treated as final field.
	 */
	private Class<T> klass;
	/**
	 * A tree of the clazzes specified foreach component to be held by an instance of this clazz. This field should be treated as final field.
	 */
	private ClazzTree tree;

	/**
	 * Construct a new clazz that represents the given {@code klass}.
	 *
	 * @param klass the class to be represented by the constructed clazz.
	 * @throws NullPointerException if the given {@code klass} is null.
	 */
	private Clazz(Class<T> klass) {
		Objects.requireNonNull(klass, "klass");
		this.family = klass;
		this.klass = klass;
		this.tree = ClazzTree.of();
	}

	/**
	 * Construct a new clazz that represents the given {@code klass}, and have the given {@code tree}.
	 *
	 * @param klass the class to be represented by the constructed clazz.
	 * @param tree  the tree of the clazzes specified foreach component to be held by an instances of the constructed clazz.
	 * @throws NullPointerException if the given {@code klass} or {@code tree} is null.
	 */
	private Clazz(Class<T> klass, ClazzTree tree) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(tree, "tree");
		this.family = klass;
		this.klass = klass;
		this.tree = tree;
	}

	/**
	 * Construct a new clazz that represents the given {@code klass}, and should be treated as if it was the given {@code family}.
	 *
	 * @param family the class that an instance of the constructed clazz should be treated as if it was an instance of it.
	 * @param klass  the class to be represented by the constructed clazz.
	 * @throws NullPointerException if the given {@code family} or {@code klass} is null.
	 */
	private Clazz(Class family, Class<T> klass) {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(klass, "klass");
		this.family = family;
		this.klass = klass;
		this.tree = ClazzTree.of();
	}

	/**
	 * Construct a new clazz that represents the given {@code klass}, and have the given {@code tree}, and should be treated as if it was the given
	 * {@code family}.
	 *
	 * @param family the class that an instance of the constructed clazz should be treated as if it was an instance of it.
	 * @param klass  the class to be represented by the constructed clazz.
	 * @param tree   the tree of the clazzes specified foreach component to be held by an instances of the constructed clazz.
	 * @throws NullPointerException if the given {@code family} or {@code klass} or {@code tree} is null.
	 */
	private Clazz(Class family, Class<T> klass, ClazzTree tree) {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(tree, "tree");
		this.family = family;
		this.klass = klass;
		this.tree = tree;
	}

	/**
	 * Get a clazz that represents the given {@code klass}.
	 *
	 * @param klass the class to be represented by the returned clazz.
	 * @param <C>   the type of the class represented by the returned clazz.
	 * @return a clazz that represents the given {@code klass}.
	 * @throws NullPointerException if the given {@code klass} is null.
	 */
	public static <C> Clazz<C> of(Class<C> klass) {
		Objects.requireNonNull(klass, "klass");
		return new Clazz(klass);
	}

	/**
	 * Get a clazz that represents the given {@code klass}, and have the given {@code tree}.
	 *
	 * @param klass the class to be represented by the returned clazz.
	 * @param tree  the tree of the clazzes specified foreach component to be held by an instances of the returned clazz.
	 * @param <C>   the type of the class represented by the returned clazz.
	 * @return a clazz that represents the given {@code klass}, and have the given {@code tree}.
	 * @throws NullPointerException if the given {@code klass} or {@code tree} is null.
	 */
	public static <C> Clazz<C> of(Class<C> klass, ClazzTree tree) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(tree, "tree");
		return new Clazz(klass, tree);
	}

	/**
	 * Get a clazz that represents the given {@code klass}, and should be treated as if it was the given {@code family}.
	 *
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param klass  the class to be represented by the returned clazz.
	 * @param <C>    the type of the class represented by the returned clazz.
	 * @return a clazz that represents the given {@code klass}, and should be treated as if it was the given {@code family}.
	 * @throws NullPointerException if the given {@code family} or {@code klass} is null.
	 */
	public static <C> Clazz<C> of(Class family, Class<C> klass) {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(klass, "klass");
		return new Clazz(family, klass);
	}

	/**
	 * Get a clazz that represents the given {@code klass}, and have the given {@code tree}, and should be treated as if it was the given {@code
	 * family}.
	 *
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param klass  the class to be represented by the returned clazz.
	 * @param tree   the tree of the clazzes specified foreach component to be held by an instances of the returned clazz.
	 * @param <C>    the type of the class represented by the returned clazz.
	 * @return a new clazz that represents the given {@code klass}, and have the given {@code tree}, and should be treated as if it was the given
	 *        {@code family}.
	 * @throws NullPointerException if the given {@code family} or {@code klass} or {@code tree} is null.
	 */
	public static <C> Clazz<C> of(Class family, Class<C> klass, ClazzTree tree) {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(tree, "tree");
		return new Clazz(family, klass, tree);
	}

	/**
	 * Get a clazz that represents the given {@code klass}, and with a {@code tree} auto generated from the given array's class.
	 *
	 * @param klass the array class to be represented by the returned clazz.
	 * @param <C>   the type of the class represented by the returned clazz.
	 * @return a clazz that represents the given {@code klass}, and with a {@code tree} auto generated from the given array's class.
	 * @throws NullPointerException     if the given {@code klass} is null.
	 * @throws IllegalArgumentException if the given {@code klass} is not an array's clazz.
	 */
	public static <C> Clazz<C> ofa(Class<C> klass) {
		Objects.requireNonNull(klass, "klass");
		if (!klass.isArray())
			throw new IllegalArgumentException("Not an array's class " + klass);

		Class component = klass.getComponentType();
		Clazz componentClazz = component.isArray() ? Clazz.ofa(component) : Clazz.of(component);

		return Clazz.of(klass, ClazzTree.of(componentClazz));
	}

	/**
	 * Get a clazz that represents the given {@code klass}, and with a {@code tree} auto generated from the given array's class, and should be treated
	 * as if it was the given {@code family}.
	 *
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param klass  the array class to be represented by the returned clazz.
	 * @param <C>    the type of the class represented by the returned clazz.
	 * @return a clazz that represents the given {@code klass}, and with a {@code tree} auto generated from the given array's class, and should be
	 * 		treated as if it was the given {@code family}.
	 * @throws NullPointerException     if the given {@code family} or {@code klass} is null.
	 * @throws IllegalArgumentException if the given {@code klass} is not an array's clazz.
	 */
	public static <C> Clazz<C> ofa(Class family, Class<C> klass) {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(klass, "klass");
		if (!klass.isArray())
			throw new IllegalArgumentException(klass + " is not an array-class");

		Class component = klass.getComponentType();
		Class componentFamily = family.isArray() ? family.getComponentType() : component;
		Clazz componentClazz = component.isArray() ? Clazz.ofa(componentFamily, component) : Clazz.of(componentFamily, component);

		return Clazz.of(family, klass, ClazzTree.of(componentClazz));
	}

	/**
	 * Get a clazz that represents the class of the given {@code instance}.
	 * <br>
	 * Note: {@link Void} is the class of null.
	 *
	 * @param instance an instance that its class to be represented by the returned clazz.
	 * @param <C>      the type of the class represented by the returned clazz.
	 * @return a clazz that represents the class of the given {@code instance}.
	 * @see Object#getClass()
	 */
	public static <C> Clazz<C> ofi(C instance) {
		return instance == null ? (Clazz<C>) Clazz.of(Void.class) : (Clazz<C>) Clazz.of(instance.getClass());
	}

	/**
	 * Get a clazz that represents the clazz of the given {@code instance}, and have the given {@code tree}.
	 * <br>
	 * Note: {@link Void} is the class of null.
	 *
	 * @param instance an instance that its class to be represented by the returned clazz.
	 * @param tree     the tree of the clazzes specified foreach component to be held by an instances of the returned clazz.
	 * @param <C>      the type of the class represented by the returned clazz.
	 * @return a clazz that represents the clazz of the given {@code instance}, and have the given {@code tree}.
	 * @throws NullPointerException if the given {@code tree} is null.
	 * @see Object#getClass()
	 */
	public static <C> Clazz<C> ofi(C instance, ClazzTree tree) {
		Objects.requireNonNull(tree, "tree");
		return instance == null ? (Clazz<C>) Clazz.of(Void.class, tree) : (Clazz<C>) Clazz.of(instance.getClass(), tree);
	}

	/**
	 * Get a clazz that represents the clazz of the given {@code instance}, and should be treated as if it was the given {@code family}.
	 * <br>
	 * Note: {@link Void} is the class of null.
	 *
	 * @param family   the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param instance an instance that its class to be represented by the returned clazz.
	 * @param <C>      the type of the class represented by the returned clazz.
	 * @return a clazz that represents the clazz of the given {@code instance}, and should be treated as if it was the given {@code family}.
	 * @throws NullPointerException if the given {@code family} is null.
	 * @see Object#getClass()
	 */
	public static <C> Clazz<C> ofi(Class family, C instance) {
		Objects.requireNonNull(family, "family");
		return instance == null ? (Clazz<C>) Clazz.of(family, Void.class) : (Clazz<C>) Clazz.of(family, instance.getClass());
	}

	/**
	 * Get a clazz that represents the clazz of the given {@code instance}, and have the given {@code tree}, and should be treated as if it was the
	 * given {@code family}.
	 * <br>
	 * Note: {@link Void} is the class of null.
	 *
	 * @param family   the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param instance an instance that its class to be represented by the returned clazz.
	 * @param tree     the tree of the clazzes specified foreach component to be held by an instances of the returned clazz.
	 * @param <C>      the type of the class represented by the returned clazz.
	 * @return a clazz that represents the clazz of the given {@code instance}, and have the given {@code tree}, and should be treated as if it was
	 * 		the given {@code family}.
	 * @throws NullPointerException if the given {@code family} or {@code tree} is null.
	 * @see Object#getClass()
	 */
	public static <C> Clazz<C> ofi(Class family, C instance, ClazzTree tree) {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(tree, "tree");
		return instance == null ? (Clazz<C>) Clazz.of(family, Void.class, tree) : (Clazz<C>) Clazz.of(family, instance.getClass(), tree);
	}

	/**
	 * Get a clazz that represents the class with the given {@code name}.
	 *
	 * @param name the name of the class that to be represented by the returned clazz.
	 * @return a clazz that represents the class with the given {@code name}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code name} is null.
	 * @see Class#forName(String)
	 */
	public static Clazz ofn(String name) throws ClassNotFoundException {
		Objects.requireNonNull(name, "name");
		return Clazz.of(Class.forName(name));
	}

	/**
	 * Get a clazz that represents the class with the given {@code name}, and have the given {@code tree}.
	 *
	 * @param name the name of the class that to be represented by the returned clazz.
	 * @param tree the tree of the clazzes specified foreach component to be held by an instances of the returned clazz.
	 * @return a clazz that represents the class with the given {@code name}, and have the given {@code tree}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code name} or {@code tree} is null.
	 * @see Class#forName(String)
	 */
	public static Clazz ofn(String name, ClazzTree tree) throws ClassNotFoundException {
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(tree, "tree");
		return Clazz.of(Class.forName(name), tree);
	}

	/**
	 * Get a clazz that represents the class with the given {@code name}, and should be treated as if it was the given {@code family}.
	 *
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param name   the name of the class that to be represented by the returned clazz.
	 * @return a clazz that represents the class with the given {@code name}, and should be treated as if it was the given {@code family}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code family} or {@code name} is null.
	 * @see Class#forName(String)
	 */
	public static Clazz ofn(Class family, String name) throws ClassNotFoundException {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(name, "name");
		return Clazz.of(family, Class.forName(name));
	}

	/**
	 * Get a clazz that represents the class with the given {@code name}, and have the given {@code tree}, and should be treated as if it was the
	 * given {@code family}.
	 *
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param name   the name of the class that to be represented by the returned clazz.
	 * @param tree   the tree of the clazzes specified foreach component to be held by an instances of the returned clazz.
	 * @return a clazz that represents the class with the given {@code name}, and have the given {@code tree}, and should be treated as if it was the
	 * 		given {@code family}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code family} or {@code name} or {@code tree} is null.
	 * @see Class#forName(String)
	 */
	public static Clazz ofn(Class family, String name, ClazzTree tree) throws ClassNotFoundException {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(tree, "tree");
		return Clazz.of(family, Class.forName(name), tree);
	}

	/**
	 * Get a clazz that represents the class with the given {@code name} loaded using the given {@code loader}.
	 *
	 * @param loader     class loader from which the class must be loaded.
	 * @param initialize if true the class will be initialized. See Section 12.4 of The Java Language.
	 * @param name       the name of the class that to be represented by the returned clazz.
	 * @return a clazz that represents the class with the given {@code name} loaded using the given {@code loader}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code loader} or {@code name} is null.
	 * @see Class#forName(String, boolean, ClassLoader)
	 */
	public static Clazz ofn(ClassLoader loader, boolean initialize, String name) throws ClassNotFoundException {
		Objects.requireNonNull(loader, "loader");
		Objects.requireNonNull(name, "name");
		return Clazz.of(Class.forName(name, initialize, loader));
	}

	/**
	 * Get a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and have the given {@code tree}.
	 *
	 * @param loader     class loader from which the class must be loaded.
	 * @param initialize if true the class will be initialized. See Section 12.4 of The Java Language.
	 * @param name       the name of the class that to be represented by the returned clazz.
	 * @param tree       the tree of the clazzes specified foreach component to be held by an instances of the returned clazz.
	 * @return a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and have the given {@code tree}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code loader} or {@code name} or {@code tree} is null.
	 * @see Class#forName(String, boolean, ClassLoader)
	 */
	public static Clazz ofn(ClassLoader loader, boolean initialize, String name, ClazzTree tree) throws ClassNotFoundException {
		Objects.requireNonNull(loader, "loader");
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(tree, "tree");
		return Clazz.of(Class.forName(name, initialize, loader), tree);
	}

	/**
	 * Get a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and should be treated as if it was the
	 * given {@code family}.
	 *
	 * @param loader     class loader from which the class must be loaded.
	 * @param initialize if true the class will be initialized. See Section 12.4 of The Java Language.
	 * @param family     the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param name       the name of the class that to be represented by the returned clazz.
	 * @return a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and should be treated as if it was
	 * 		the given {@code family}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code loader} or {@code family} or {@code name} is null.
	 * @see Class#forName(String, boolean, ClassLoader)
	 */
	public static Clazz ofn(ClassLoader loader, boolean initialize, Class family, String name) throws ClassNotFoundException {
		Objects.requireNonNull(loader, "loader");
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(name, "name");
		return Clazz.of(family, Class.forName(name, initialize, loader));
	}

	/**
	 * Get a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and have the given {@code tree}, and
	 * should be treated as if it was the given {@code family}.
	 *
	 * @param loader     class loader from which the class must be loaded.
	 * @param initialize if true the class will be initialized. See Section 12.4 of The Java Language.
	 * @param family     the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param name       the name of the class that to be represented by the returned clazz.
	 * @param tree       the tree of the clazzes specified foreach component to be held by an instances of the returned clazz.
	 * @return a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and have the given {@code tree},
	 * 		and should be treated as if it was the given {@code family}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code loader} or {@code family} or {@code name} or {@code tree} is null.
	 * @see Class#forName(String, boolean, ClassLoader)
	 */
	public static Clazz ofn(ClassLoader loader, boolean initialize, Class family, String name, ClazzTree tree) throws ClassNotFoundException {
		Objects.requireNonNull(loader, "loader");
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(tree, "tree");
		return Clazz.of(family, Class.forName(name, initialize, loader), tree);
	}

	/**
	 * Get a clazz that represents the class of the given {@code instance}, and with a {@code tree} auto generated from the elements of the given
	 * {@code instance}.
	 *
	 * @param instance an instance that its class to be represented by the returned clazz, and its elements is the source of its tree.
	 * @param <C>      the type of the class represented by the returned clazz.
	 * @return a clazz that represents the class of the given {@code instance}, and with a {@code tree} auto generated from the given {@code
	 * 		instance}.
	 */
	public static <C> Clazz<C> ofx(C instance) {
		return Clazz.ofi(instance, ClazzTree.ofx(instance));
	}

	/**
	 * Get a clazz that represents the class of the given {@code instance}, and with a {@code tree} auto generated from the elements of the given
	 * {@code instance} with the given {@code baseClazz}.
	 *
	 * @param instance  an instance that its class to be represented by the returned clazz, and its elements is the source of its tree.
	 * @param baseClazz the class for the elements its class isn't declared in the generated tree.
	 * @param <C>       the type of the class represented by the returned clazz.
	 * @return a clazz that represents the class of the given {@code instance}, and with a {@code tree} auto generated from the elements of the given
	 *        {@code instance} with the given {@code baseClazz}.
	 */
	public static <C> Clazz<C> ofx(C instance, Clazz baseClazz) {
		return Clazz.ofi(instance, ClazzTree.ofx(instance, baseClazz));
	}

	/**
	 * Get a clazz that represents the class of the given {@code instance}, and should be treated as if it was the given {@code family}, and with a
	 * {@code tree} auto generated from the elements of the given {@code instance}.
	 *
	 * @param family   the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param instance an instance that its class to be represented by the returned clazz, and its elements is the source of its tree.
	 * @param <C>      the type of the class represented by the returned clazz.
	 * @return a clazz that represents the class of the given {@code instance}, and should be treated as if it was the given {@code family}, and with
	 * 		a {@code tree} auto generated from the elements of the given {@code instance}.
	 * @throws NullPointerException if the given {@code family} is null.
	 */
	public static <C> Clazz<C> ofx(Class family, C instance) {
		Objects.requireNonNull(family, "family");
		return Clazz.ofi(family, instance, ClazzTree.ofx(instance));
	}

	/**
	 * Get a clazz that represents the class of the given {@code instance}, and should be treated as if it was the given {@code family}, and with a
	 * {@code tree} auto generated from the elements of the given {@code instance}, with the given {@code baseClazz}.
	 *
	 * @param family    the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param instance  an instance that its class to be represented by the returned clazz, and its elements is the source of its tree.
	 * @param baseClazz the class for the elements its class isn't declared in the generated tree.
	 * @param <C>       the type of the class represented by the returned clazz.
	 * @return a clazz that represents the class of the given {@code instance}, and should be treated as if it was the given {@code family}, and with
	 * 		a {@code tree} auto generated from the elements of the given {@code instance}, with the given {@code baseClazz}.
	 * @throws NullPointerException if the given {@code family} is null.
	 */
	public static <C> Clazz<C> ofx(Class family, C instance, Clazz baseClazz) {
		Objects.requireNonNull(family, "family");
		return Clazz.ofi(family, instance, ClazzTree.ofx(instance, baseClazz));
	}

	/**
	 * Get a clazz that represents the {@code klass} of the given {@code klassSrc}, and have the {@code tree} of the given {@code treeSrc}, and should
	 * be treated as if it was the {@code family} of the given {@code familySrc}.
	 *
	 * @param familySrc the source of the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param klassSrc  the source of the class to be represented by the returned clazz.
	 * @param treeSrc   the source of the tree of the clazzes specified foreach component to be held by an instances of the returned clazz.
	 * @param <C>       the type of the class represented by the returned clazz.
	 * @return a clazz that represents the {@code klass} of the given {@code klassSrc}, and have the {@code tree} of the given {@code treeSrc}, and
	 * 		should be treated as if it was the {@code family} of the given {@code familySrc}.
	 * @throws NullPointerException if the given {@code familySrc} or {@code klassSrc} or {@code treeSrc} is null.
	 */
	public static <C> Clazz<C> ofz(Clazz familySrc, Clazz<C> klassSrc, Clazz treeSrc) {
		Objects.requireNonNull(familySrc, "familySrc");
		Objects.requireNonNull(klassSrc, "klassSrc");
		Objects.requireNonNull(treeSrc, "treeSrc");
		return Clazz.of(familySrc.family, klassSrc.klass, treeSrc.tree);
	}

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

	@Override
	public boolean equals(Object that) {
		return this == that ||
			   that instanceof Clazz &&
			   ((Clazz) that).klass == this.klass &&
			   ((Clazz) that).family == this.family &&
			   ((Clazz) that).tree.equals(this.tree);
	}

	@Override
	public String toString() {
		return "clazz " + this.getName();
	}

	@Override
	public String getTypeName() {
		Clazz baseClazz = this.getTreeBaseClazz();
		return (this.family == this.klass ? "" : this.family.getTypeName()) + ":" +
			   this.klass.getTypeName() +
			   (baseClazz == null ? "" : "<" + baseClazz.getTypeName() + ">");
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
	 * Get the name of this clazz. The name contains the name of the {@code family}, {@code klass}, and {@code baseTreeClazz} of this clazz.
	 * <pre>
	 *     Example:
	 *     Clazz.of(
	 *     		Object.class,
	 *     		Void.class,
	 *     		ClazzTree.of(
	 *     			Clazz.of(
	 *     				Byte.class,
	 *     				Double.class,
	 *     				Tree.of(Float.class)
	 *     ))).getName()
	 *     Will be equal to:
	 *     "java.lang.Object:java.lang.Void[][][]&lt;java.lang.Integer&lt;java.lang.Byte:java.lang.Double&lt;java.lang.Float&gt;&gt;&gt;"
	 * </pre>
	 *
	 * @return the name of this clazz.
	 */
	public String getName() {
		Clazz baseClazz = this.getTreeBaseClazz();
		return (this.family == this.klass ? "" : this.family.getName()) + ":" +
			   this.klass.getName() +
			   (baseClazz == null ? "" : "<" + baseClazz.getName() + ">");
	}

	/**
	 * Get the simple name of this clazz. The name contains the simple name of the {@code family}, {@code klass}, and {@code baseTreeClazz} of this
	 * clazz.
	 * <pre>
	 *     Example:
	 *     Clazz.of(
	 *     		Object.class,
	 *     		Void.class,
	 *     		ClazzTree.of(
	 *     			Clazz.of(
	 *     				Byte.class,
	 *     				Double.class,
	 *     				Tree.of(Float.class)
	 *     ))).getSimpleName()
	 *     Will be equal to:
	 *     "Object:Void[][][]&lt;Integer&lt;Byte:Double&lt;Float&gt;&gt;&gt;"
	 * </pre>
	 *
	 * @return the simple name of this clazz.
	 */
	public String getSimpleName() {
		Clazz baseClazz = this.getTreeBaseClazz();
		return (this.family == this.klass ? "" : this.family.getSimpleName()) + ":" +
			   this.klass.getSimpleName() +
			   (baseClazz == null ? "" : "<" + baseClazz.getSimpleName() + ">");
	}

	/**
	 * Get the tree of the clazzes specified foreach component to be held by an instance of this clazz.
	 *
	 * @return the tree of the clazzes specified foreach component to be held by an instance of this clazz.
	 */
	public ClazzTree getTree() {
		return this.tree;
	}

	/**
	 * Get the {@code baseClazz} of the {@code tree} of this clazz. The {@code baseClazz} of a clazz is like the {@code componentType} of a class.
	 *
	 * @return the {@code baseClazz} of the {@code tree} of this. Or null if the {@code tree} of this has no {@code baseClazz}.
	 */
	public Clazz getTreeBaseClazz() {
		return this.tree.getBaseClazz();
	}

	/**
	 * Get a clazz in the {@code tree} of this clazz that is associated to the given {@code key}.
	 *
	 * @param key the key of the returned clazz at the {@code tree} of this clazz.
	 * @return a clazz in the {@code tree} of this clazz that is associated to the given {@code key}.
	 */
	public Clazz getTreeClazz(Object key) {
		return this.tree.getTreeClazz(key);
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
	 * Get a clazz that represents the {@code klass} of this clazz, and have the given {@code tree}, and should be treated as if it was the given
	 * {@code family}.
	 *
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param tree   the tree of the clazzes specified foreach component to be held by an instances of the returned clazz.
	 * @return a clazz that represents the {@code klass} of this clazz, and have the given {@code tree}, and should be treated as if it was the given
	 *        {@code family}.
	 * @throws NullPointerException if the given {@code family} or {@code tree} is null.
	 */
	public Clazz override(Class family, ClazzTree tree) {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(tree, "tree");
		return Clazz.of(family, this.klass, tree);
	}

	/**
	 * Get a clazz that represents the {@code klass} of this clazz, and have the {@code tree} of this clazz, and should be treated as if it was the
	 * given {@code family}.
	 *
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @return a clazz that represents the {@code klass} of this clazz, and have the {@code tree} of this clazz, and should be treated as if it was
	 * 		the given {@code family}.
	 * @throws NullPointerException if the given {@code family} is null.
	 */
	public Clazz override(Class family) {
		Objects.requireNonNull(family, "family");
		return Clazz.of(family, this.klass, this.tree);
	}

	/**
	 * Get a clazz that represents the {@code klass} of this clazz, and have the given {@code tree}, and should be treated as if it was the {@code
	 * family} of this clazz.
	 *
	 * @param tree the tree of the clazzes specified foreach component to be held by an instances of the returned clazz.
	 * @return a clazz that represents the {@code klass} of this clazz, and have the given {@code tree}, and should be treated as if it was the {@code
	 * 		family} of this clazz.
	 * @throws NullPointerException if the given {@code tree} is null.
	 */
	public Clazz override(ClazzTree tree) {
		Objects.requireNonNull(tree, "tree");
		return Clazz.of(this.family, this.klass, tree);
	}

	/**
	 * Get a clazz that represents the {@code Object} version of the {@code klass} of this clazz, and have the {@code tree} of this clazz, and should
	 * be treated as if it was the {@code family} of this clazz.
	 *
	 * @return a clazz that represents the {@code Object} version of the {@code klass} of this clazz, and have the {@code tree} of this clazz, and
	 * 		should be treated as if it was the {@code family} of this clazz.
	 */
	public Clazz toObjectClazz() {
		return Clazz.of(this.family, Reflection.asObjectClass(this.klass), this.tree);
	}

	/**
	 * Get a clazz that represents the primitive version of the {@code klass} of this clazz, and have the {@code tree} of this clazz, and should be
	 * treated as if it was the {@code family} of this clazz.
	 *
	 * @return a clazz that represents the primitive version of the {@code klass} of this clazz, and have the {@code tree} of this clazz, and should
	 * 		be treated as if it was the {@code family} of this clazz. Or null if there is no primitive class of the {@code klass} of this clazz.
	 */
	public Clazz toPrimitiveClazz() {
		return !Reflection.hasPrimitiveClass(this.klass) ? null :
			   Clazz.of(this.family, Reflection.asPrimitiveClass(this.klass), this.tree);
	}

	@SuppressWarnings("JavaDoc")
	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
		Objects.requireNonNull(stream, "stream");
		this.family = (Class<T>) stream.readObject();
		this.klass = (Class) stream.readObject();
		this.tree = (ClazzTree) stream.readObject();
		if (stream.readBoolean()) {
			//there is more
			System.err.println("Deserializing newer version of Clazz");
		}
	}

	@SuppressWarnings("JavaDoc")
	private void writeObject(ObjectOutputStream stream) throws IOException {
		Objects.requireNonNull(stream, "stream");
		stream.writeObject(this.family);
		stream.writeObject(this.klass);
		stream.writeObject(this.tree);
		stream.writeBoolean(false);
		//free to add more
	}
}
//getSuperclazz
//getComponentClazz
//isAnonymousClass
//isLocalClass
//isMemberClass
//asSubClazz
//
//getField
//getFields
//getMethod
//getMethods
//getConstructor
//getConstructors
//getEnumConstants
//asSubClass
//desiredAssertionStatus
//getAnnotatedInterfaces
//getAnnotatedSuperclass
//getAnnotation
//getAnnotations
//getAnnotationsByType
//getCanonicalName
//getClasses
//getClassLoader
//getDeclaredAnnotation
//getDeclaredAnnotations
//getDeclaredAnnotationsByType
//getDeclaredClasses
//getDeclaredConstructor
//getDeclaredConstructors
//getDeclaredField
//getDeclaredFields
//getDeclaredMethod
//getDeclaredMethods
//getDeclaringClass
//getEnclosingClass
//getEnclosingConstructor
//getEnclosingMethod
//getGenericInterfaces
//getGenericClasses
//getInterfaces
//getModifiers
//getPackage
//getProtectionDomain
//getResource
//getResourceAsStream
//getSigners
//getSuperclass
//getTypeParameters
//isAnnotationPresent
//toGenericString