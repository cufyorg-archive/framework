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
	private Map<Object, Clazz> tree;

	/**
	 * Construct a new clazz that represents the given {@code klass}.
	 *
	 * @param klass the class to be represented by the constructed clazz.
	 * @throws NullPointerException if the given {@code klass} is null.
	 */
	private Clazz(Class<T> klass) {
		Objects.requireNonNull(klass, "klass");
		this.klass = klass;
		this.family = klass;
		this.tree = Clazz.tree();
	}

	/**
	 * Construct a new clazz that represents the given {@code klass}, and have the given {@code tree}.
	 *
	 * @param klass the class to be represented by the constructed clazz.
	 * @param tree  the tree of the clazzes specified foreach component to be held by an instance of the constructed
	 *              clazz.
	 * @throws NullPointerException if the given {@code klass} or {@code tree} is null.
	 */
	private Clazz(Class<T> klass, Map<Object, Clazz> tree) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(tree, "tree");
		this.klass = klass;
		this.family = klass;
		this.tree = Clazz.tree(tree);
	}

	/**
	 * Construct a new clazz that represents the given {@code klass}, and should be treated as if it was the given
	 * {@code family}.
	 *
	 * @param klass  the class to be represented by the constructed clazz.
	 * @param family the class that an instance of the constructed clazz should be treated as if it was an instance of
	 *               it.
	 * @throws NullPointerException if the given {@code klass} or {@code family} is null.
	 */
	private Clazz(Class<T> klass, Class family) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(family, "family");
		this.klass = klass;
		this.family = family;
		this.tree = Clazz.tree();
	}

	/**
	 * Construct a new clazz that represents the given {@code klass}, and have the given {@code tree}, and should be
	 * treated as if it was the given {@code family}.
	 *
	 * @param klass  the class to be represented by the constructed clazz.
	 * @param family the class that an instance of the constructed clazz should be treated as if it was an instance of
	 *               it.
	 * @param tree   the tree of the clazzes specified foreach component to be held by an instance of the constructed
	 *               clazz.
	 * @throws NullPointerException if the given {@code klass} or {@code family} or {@code tree} is null.
	 */
	private Clazz(Class<T> klass, Class family, Map<Object, Clazz> tree) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(tree, "tree");
		this.klass = klass;
		this.family = family;
		this.tree = Clazz.tree(tree);
	}

	/**
	 * Get the standard component clazz for the given instance.
	 * <br>
	 * Note: the behaviour of this may change by time.
	 *
	 * @param instance the instance that have the returned component type.
	 * @return the standard component clazz for the give instance.
	 */
	public static Clazz component(Object instance) {
		if (instance instanceof Map || instance instanceof Collection) {
			return Clazz.of(Object.class);
		} else if (instance != null && instance.getClass().isArray()) {
			Class component = instance.getClass().getComponentType();
			return component.isArray() ? Clazz.ofa(component) : Clazz.of(component);
		}

		return null;
	}

	/**
	 * Get a clazz that represents the given {@code klass}.
	 *
	 * @param klass the class to be represented by the returned clazz.
	 * @param <T>   the type of the class represented by the returned clazz.
	 * @return a clazz that represents the given {@code klass}.
	 * @throws NullPointerException if the given {@code klass} is null.
	 */
	public static <T> Clazz<T> of(Class<T> klass) {
		Objects.requireNonNull(klass, "klass");
		return new Clazz(klass);
	}

	/**
	 * Get a clazz that represents the given {@code klass}, and have the given {@code tree}.
	 *
	 * @param klass the class to be represented by the returned clazz.
	 * @param tree  the tree of the clazzes specified foreach component to be held by an instance of the returned
	 *              clazz.
	 * @param <T>   the type of the class represented by the returned clazz.
	 * @return a clazz that represents the given {@code klass}, and have the given {@code tree}.
	 * @throws NullPointerException if the given {@code klass} or {@code tree} is null.
	 */
	public static <T> Clazz<T> of(Class<T> klass, Map<Object, Clazz> tree) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(tree, "tree");
		return new Clazz(klass, tree);
	}

	/**
	 * Get a clazz that represents the given {@code klass}, and should be treated as if it was the given {@code
	 * family}.
	 *
	 * @param klass  the class to be represented by the returned clazz.
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param <T>    the type of the class represented by the returned clazz.
	 * @return a clazz that represents the given {@code klass}, and should be treated as if it was the given {@code
	 * 		family}.
	 * @throws NullPointerException if the given {@code klass} or {@code family} is null.
	 */
	public static <T> Clazz<T> of(Class<T> klass, Class family) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(family, "family");
		return new Clazz(klass, family);
	}

	/**
	 * Get a clazz that represents the given {@code klass}, and have the given {@code tree}, and should be treated as if
	 * it was the given {@code family}.
	 *
	 * @param klass  the class to be represented by the returned clazz.
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param tree   the tree of the clazzes specified foreach component to be held by an instance of the returned
	 *               clazz.
	 * @param <T>    the type of the class represented by the returned clazz.
	 * @return a new clazz that represents the given {@code klass}, and have the given {@code tree}, and should be
	 * 		treated as if it was the given {@code family}.
	 * @throws NullPointerException if the given {@code klass} or {@code family} or {@code tree} is null.
	 */
	public static <T> Clazz<T> of(Class<T> klass, Class family, Map<Object, Clazz> tree) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(tree, "tree");
		return new Clazz(klass, family, tree);
	}

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

		return Clazz.of(klass, Clazz.tree(componentClazz));
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

		return Clazz.of(klass, family, Clazz.tree(componentClazz));
	}

	/**
	 * Get a clazz that represents the class of the given {@code instance}.
	 * <br>
	 * Note: {@link Void} is the class of null.
	 *
	 * @param instance an instance that its class to be represented by the returned clazz.
	 * @param <T>      the type of the class represented by the returned clazz.
	 * @return a clazz that represents the class of the given {@code instance}.
	 * @see Object#getClass()
	 */
	public static <T> Clazz<T> ofi(T instance) {
		return instance == null ? (Clazz<T>) Clazz.of(Void.class) : (Clazz<T>) Clazz.of(instance.getClass());
	}

	/**
	 * Get a clazz that represents the clazz of the given {@code instance}, and have the given {@code tree}.
	 * <br>
	 * Note: {@link Void} is the class of null.
	 *
	 * @param instance an instance that its class to be represented by the returned clazz.
	 * @param tree     the tree of the clazzes specified foreach component to be held by an instance of the returned
	 *                 clazz.
	 * @param <T>      the type of the class represented by the returned clazz.
	 * @return a clazz that represents the clazz of the given {@code instance}, and have the given {@code tree}.
	 * @throws NullPointerException if the given {@code tree} is null.
	 * @see Object#getClass()
	 */
	public static <T> Clazz<T> ofi(T instance, Map<Object, Clazz> tree) {
		Objects.requireNonNull(tree, "tree");
		return instance == null ? (Clazz<T>) Clazz.of(Void.class, tree)
								: (Clazz<T>) Clazz.of(instance.getClass(), tree);
	}

	/**
	 * Get a clazz that represents the clazz of the given {@code instance}, and should be treated as if it was the given
	 * {@code family}.
	 * <br>
	 * Note: {@link Void} is the class of null.
	 *
	 * @param instance an instance that its class to be represented by the returned clazz.
	 * @param family   the class that an instance of the returned clazz should be treated as if it was an instance of
	 *                 it.
	 * @param <T>      the type of the class represented by the returned clazz.
	 * @return a clazz that represents the clazz of the given {@code instance}, and should be treated as if it was the
	 * 		given {@code family}.
	 * @throws NullPointerException if the given {@code family} is null.
	 * @see Object#getClass()
	 */
	public static <T> Clazz<T> ofi(T instance, Class family) {
		Objects.requireNonNull(family, "family");
		return instance == null ? (Clazz<T>) Clazz.of(Void.class, family)
								: (Clazz<T>) Clazz.of(instance.getClass(), family);
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
	 * @param tree     the tree of the clazzes specified foreach component to be held by an instance of the returned
	 *                 clazz.
	 * @param <T>      the type of the class represented by the returned clazz.
	 * @return a clazz that represents the clazz of the given {@code instance}, and have the given {@code tree}, and
	 * 		should be treated as if it was the given {@code family}.
	 * @throws NullPointerException if the given {@code family} or {@code tree} is null.
	 * @see Object#getClass()
	 */
	public static <T> Clazz<T> ofi(T instance, Class family, Map<Object, Clazz> tree) {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(tree, "tree");
		return instance == null ? (Clazz<T>) Clazz.of(Void.class, family, tree)
								: (Clazz<T>) Clazz.of(instance.getClass(), family, tree);
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
	 * @param tree the tree of the clazzes specified foreach component to be held by an instance of the returned clazz.
	 * @return a clazz that represents the class with the given {@code name}, and have the given {@code tree}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code name} or {@code tree} is null.
	 * @see Class#forName(String)
	 */
	public static Clazz ofn(String name, Map<Object, Clazz> tree) throws ClassNotFoundException {
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(tree, "tree");
		return Clazz.of(Class.forName(name), tree);
	}

	/**
	 * Get a clazz that represents the class with the given {@code name}, and should be treated as if it was the given
	 * {@code family}.
	 *
	 * @param name   the name of the class that to be represented by the returned clazz.
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @return a clazz that represents the class with the given {@code name}, and should be treated as if it was the
	 * 		given {@code family}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code name} or {@code family} is null.
	 * @see Class#forName(String)
	 */
	public static Clazz ofn(String name, Class family) throws ClassNotFoundException {
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(family, "family");
		return Clazz.of(Class.forName(name), family);
	}

	/**
	 * Get a clazz that represents the class with the given {@code name}, and have the given {@code tree}, and should be
	 * treated as if it was the given {@code family}.
	 *
	 * @param name   the name of the class that to be represented by the returned clazz.
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param tree   the tree of the clazzes specified foreach component to be held by an instance of the returned
	 *               clazz.
	 * @return a clazz that represents the class with the given {@code name}, and have the given {@code tree}, and
	 * 		should be treated as if it was the given {@code family}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code name} or {@code family} or {@code tree} is null.
	 * @see Class#forName(String)
	 */
	public static Clazz ofn(String name, Class family, Map<Object, Clazz> tree) throws ClassNotFoundException {
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(tree, "tree");
		return Clazz.of(Class.forName(name), family, tree);
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
	 * Get a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and have
	 * the given {@code tree}.
	 *
	 * @param loader     class loader from which the class must be loaded.
	 * @param initialize if true the class will be initialized. See Section 12.4 of The Java Language.
	 * @param name       the name of the class that to be represented by the returned clazz.
	 * @param tree       the tree of the clazzes specified foreach component to be held by an instance of the returned
	 *                   clazz.
	 * @return a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and
	 * 		have the given {@code tree}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code loader} or {@code name} or {@code tree} is null.
	 * @see Class#forName(String, boolean, ClassLoader)
	 */
	public static Clazz ofn(ClassLoader loader, boolean initialize, String name, Map<Object, Clazz> tree) throws ClassNotFoundException {
		Objects.requireNonNull(loader, "loader");
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(tree, "tree");
		return Clazz.of(Class.forName(name, initialize, loader), tree);
	}

	/**
	 * Get a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and
	 * should be treated as if it was the given {@code family}.
	 *
	 * @param loader     class loader from which the class must be loaded.
	 * @param initialize if true the class will be initialized. See Section 12.4 of The Java Language.
	 * @param name       the name of the class that to be represented by the returned clazz.
	 * @param family     the class that an instance of the returned clazz should be treated as if it was an instance of
	 *                   it.
	 * @return a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and
	 * 		should be treated as if it was the given {@code family}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code loader} or {@code name} or {@code family} is null.
	 * @see Class#forName(String, boolean, ClassLoader)
	 */
	public static Clazz ofn(ClassLoader loader, boolean initialize, String name, Class family) throws ClassNotFoundException {
		Objects.requireNonNull(loader, "loader");
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(family, "family");
		return Clazz.of(Class.forName(name, initialize, loader), family);
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
	 * @param tree       the tree of the clazzes specified foreach component to be held by an instance of the returned
	 *                   clazz.
	 * @return a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and
	 * 		have the given {@code tree}, and should be treated as if it was the given {@code family}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code loader} or {@code name} or {@code family} or {@code tree}
	 *                                     is null.
	 * @see Class#forName(String, boolean, ClassLoader)
	 */
	public static Clazz ofn(ClassLoader loader, boolean initialize, String name, Class family, Map<Object, Clazz> tree) throws ClassNotFoundException {
		Objects.requireNonNull(loader, "loader");
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(tree, "tree");
		return Clazz.of(Class.forName(name, initialize, loader), family, tree);
	}

	/**
	 * Get a clazz that represents the class of the given {@code instance}, and with a {@code tree} auto generated from
	 * the elements of the given {@code instance}.
	 *
	 * @param instance an instance that its class to be represented by the returned clazz, and its elements is the
	 *                 source of its tree.
	 * @param <T>      the type of the class represented by the returned clazz.
	 * @return a clazz that represents the class of the given {@code instance}, and with a {@code tree} auto generated
	 * 		from the given {@code instance}.
	 */
	public static <T> Clazz<T> ofx(T instance) {
		return Clazz.ofi(instance, Clazz.tree(instance));
	}

	/**
	 * Get a clazz that represents the class of the given {@code instance}, and with a {@code tree} auto generated from
	 * the elements of the given {@code instance} with the given {@code componentClazz}.
	 *
	 * @param instance       an instance that its class to be represented by the returned clazz, and its elements is the
	 *                       source of its tree.
	 * @param componentClazz the class for the elements its class isn't declared in the generated tree.
	 * @param <T>            the type of the class represented by the returned clazz.
	 * @return a clazz that represents the class of the given {@code instance}, and with a {@code tree} auto generated
	 * 		from the elements of the given {@code instance} with the given {@code componentClazz}.
	 */
	public static <T> Clazz<T> ofx(T instance, Clazz componentClazz) {
		return Clazz.ofi(instance, Clazz.tree(instance, componentClazz));
	}

	/**
	 * Get a clazz that represents the class of the given {@code instance}, and should be treated as if it was the given
	 * {@code family}, and with a {@code tree} auto generated from the elements of the given {@code instance}.
	 *
	 * @param instance an instance that its class to be represented by the returned clazz, and its elements is the
	 *                 source of its tree.
	 * @param family   the class that an instance of the returned clazz should be treated as if it was an instance of
	 *                 it.
	 * @param <T>      the type of the class represented by the returned clazz.
	 * @return a clazz that represents the class of the given {@code instance}, and should be treated as if it was the
	 * 		given {@code family}, and with a {@code tree} auto generated from the elements of the given {@code instance}.
	 * @throws NullPointerException if the given {@code family} is null.
	 */
	public static <T> Clazz<T> ofx(T instance, Class family) {
		Objects.requireNonNull(family, "family");
		return Clazz.ofi(instance, family, Clazz.tree(instance));
	}

	/**
	 * Get a clazz that represents the class of the given {@code instance}, and should be treated as if it was the given
	 * {@code family}, and with a {@code tree} auto generated from the elements of the given {@code instance}, with the
	 * given {@code componentClazz}.
	 *
	 * @param instance       an instance that its class to be represented by the returned clazz, and its elements is the
	 *                       source of its tree.
	 * @param family         the class that an instance of the returned clazz should be treated as if it was an instance
	 *                       of it.
	 * @param componentClazz the class for the elements its class isn't declared in the generated tree.
	 * @param <T>            the type of the class represented by the returned clazz.
	 * @return a clazz that represents the class of the given {@code instance}, and should be treated as if it was the
	 * 		given {@code family}, and with a {@code tree} auto generated from the elements of the given {@code instance},
	 * 		with the given {@code componentClazz}.
	 * @throws NullPointerException if the given {@code family} is null.
	 */
	public static <T> Clazz<T> ofx(T instance, Class family, Clazz componentClazz) {
		Objects.requireNonNull(family, "family");
		return Clazz.ofi(instance, family, Clazz.tree(instance, componentClazz));
	}

	/**
	 * Get a clazz that represents the {@code klass} of the given {@code klassSrc}, and have the {@code tree} of the
	 * given {@code treeSrc}, and should be treated as if it was the {@code family} of the given {@code familySrc}.
	 *
	 * @param klassSrc  the source of the class to be represented by the returned clazz.
	 * @param familySrc the source of the class that an instance of the returned clazz should be treated as if it was an
	 *                  instance of it.
	 * @param treeSrc   the source of the tree of the clazzes specified foreach component to be held by an instances of
	 *                  the returned clazz.
	 * @param <T>       the type of the class represented by the returned clazz.
	 * @return a clazz that represents the {@code klass} of the given {@code klassSrc}, and have the {@code tree} of the
	 * 		given {@code treeSrc}, and should be treated as if it was the {@code family} of the given {@code familySrc}.
	 * @throws NullPointerException if the given {@code familySrc} or {@code klassSrc} or {@code treeSrc} is null.
	 */
	public static <T> Clazz<T> ofz(Clazz<T> klassSrc, Clazz familySrc, Clazz treeSrc) {
		Objects.requireNonNull(klassSrc, "klassSrc");
		Objects.requireNonNull(familySrc, "familySrc");
		Objects.requireNonNull(treeSrc, "treeSrc");
		return Clazz.of(klassSrc.klass, familySrc.family, treeSrc.tree);
	}

	/**
	 * Get a tree that have no general clazz, and have no mappings.
	 *
	 * @return a tree that have no general clazz, and have no mappings.
	 */
	public static Map<Object, Clazz> tree() {
		return Collections.emptyMap();
	}

	/**
	 * Get a tree that its general clazz is the given {@code componentClazz}, and have no mappings.
	 *
	 * @param componentClazz the general clazz for the components that have no mapping in the returned tree.
	 * @return a tree that its general clazz is the given {@code componentClazz}, and have no mappings.
	 */
	public static Map<Object, Clazz> tree(Clazz componentClazz) {
		return Collections.singletonMap(Clazz.COMPONENT, componentClazz);
	}

	/**
	 * Get a tree that its general clazz is a clazz represents the given {@code componentClass}, and have no mappings.
	 *
	 * @param componentClass the class that the clazz represents it is the general clazz for the components that have no
	 *                       mapping in the returned tree.
	 * @return a tree that its general clazz is the given {@code componentClazz}, and have no mappings.
	 */
	public static Map<Object, Clazz> tree(Class componentClass) {
		return Collections.singletonMap(Clazz.COMPONENT, Clazz.of(componentClass));
	}

	/**
	 * Get a tree that its general clazz is the standard component clazz of the given {@code instance}, and have
	 * components mappings auto generated for the given {@code instance}.
	 * <br>
	 * Note: the behaviour of this may change by time.
	 *
	 * @param instance that the returned tree is auto generated from it.
	 * @return a tree that its general clazz is the standard component clazz of the given {@code instance}, and have
	 * 		components mappings auto generated for the given {@code instance}.
	 */
	public static Map<Object, Clazz> tree(Object instance) {
		return Clazz.tree(instance, Clazz.component(instance));
	}

	/**
	 * Get a tree that its general clazz is the given {@code componentClazz}, and have components mappings auto
	 * generated for the given {@code instance}.
	 * <br>
	 * Note: the behaviour of this may change by time.
	 *
	 * @param instance       that the returned tree is auto generated from it.
	 * @param componentClazz the general clazz for the components that have no mapping in the returned tree.
	 * @return a tree that its general clazz is the standard component clazz of the given {@code instance}, and have
	 * 		components mappings auto generated for the given {@code instance}.
	 */
	public static Map<Object, Clazz> tree(Object instance, Clazz componentClazz) {
		if (instance instanceof Map) {
			Map map = (Map) instance;
			Map clazzes = new HashMap(map.size());
			Clazz componentComponentClazz = componentClazz == null ? null : componentClazz.getComponentClazz();

			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				Object key = entry.getKey();
				Object value = entry.getValue();

				clazzes.put(key, Clazz.ofx(value, componentComponentClazz));
			}

			return clazzes;
		}
		if (instance instanceof List) {
			List list = (List) instance;
			Map clazzes = new HashMap(list.size());
			Clazz componentComponentClazz = componentClazz == null ? null : componentClazz.getComponentClazz();

			int i = 0;
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				Object element = iterator.next();
				clazzes.put(i++, Clazz.ofx(element, componentComponentClazz));
			}

			return clazzes;
		}
		if (instance instanceof Object[]) {
			Object[] array = (Object[]) instance;
			Map clazzes = new HashMap(array.length);
			Clazz componentComponentClazz = componentClazz == null ? null : componentClazz.getComponentClazz();

			for (int i = 0; i < array.length; i++) {
				Object element = array[i];
				clazzes.put(i, Clazz.ofx(element, componentComponentClazz));
			}

			return clazzes;
		}

		return new HashMap();
	}

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
	public static Map<Object, Clazz> tree(Class... componentClasses) {
		Objects.requireNonNull(componentClasses, "componentClasses");
		return componentClasses.length == 0 ?
			   Clazz.tree() :
			   Clazz.tree(Clazz.of(
					   Objects.requireNonNull(componentClasses[0], "componentClasses[?]"),
					   Clazz.tree(Arrays.copyOfRange(
							   componentClasses,
							   1,
							   componentClasses.length
					   ))));
	}

	/**
	 * Sign the given {@code tree} as a {@link Clazz.Tree}. Once a {@code tree} is signed it can only be read. A signed
	 * {@code tree} will not be signed twice. Signing a {@code tree} means cloning all the content of it into a new
	 * {@link Clazz.Tree}. Singing a {@code tree} is unnecessary unless to prevent security holes, since any clazz will
	 * sign its {@code tree} anyway when it gets constructed. A clazz will never use an unsigned {@code tree}.
	 *
	 * @param tree the source mapping for the returned signed {@code tree}.
	 * @return a {@link Clazz.Tree} that have a clone of the mappings in the given {@code tree}.
	 * @throws NullPointerException if the given {@code tree} is null.
	 */
	public static Clazz.Tree tree(Map<Object, Clazz> tree) {
		Objects.requireNonNull(tree, "tree");
		return tree instanceof Clazz.Tree ? (Clazz.Tree) tree : new Clazz.Tree(tree);
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
		Clazz componentClazz = this.getComponentClazz();
		return (this.family == this.klass ? "" : this.family.getTypeName()) + ":" +
			   this.klass.getTypeName() +
			   (componentClazz == null ? "" : "<" + componentClazz.getTypeName() + ">");
	}

	/**
	 * Get the general clazz for all components to be held by an instance of this clazz.
	 * <br>
	 * Note: This is like calling {@link Clazz#getComponentClazz(Object) getComponentClazz(}{@link Clazz#COMPONENT
	 * Clazz.COMPONENT_TYPE)}
	 *
	 * @return the general clazz for all components to be held by an instance of this clazz.
	 */
	public Clazz getComponentClazz() {
		return this.tree.get(Clazz.COMPONENT);
	}

	/**
	 * Get the component clazz of this clazz that have the given {@code key}.
	 * <br>
	 * Note: the key {@link Clazz#COMPONENT} is the key for the general component clazz.
	 *
	 * @param key the key associated to the returned clazz as a component in this clazz.
	 * @return the component clazz of this clazz that have the given {@code key}. Or null if no clazz have the given
	 *        {@code key} associated to it as a component clazz in this clazz.
	 */
	public Clazz getComponentClazz(Object key) {
		return this.tree.get(key);
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
	 * Get the name of this clazz. The name contains the name of the {@code family}, {@code klass}, and {@code
	 * baseTreeClazz} of this clazz.
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
		Clazz componentClazz = this.getComponentClazz();
		return (this.family == this.klass ? "" : this.family.getName()) + ":" +
			   this.klass.getName() +
			   (componentClazz == null ? "" : "<" + componentClazz.getName() + ">");
	}

	/**
	 * Get the simple name of this clazz. The name contains the simple name of the {@code family}, {@code klass}, and
	 * {@code baseTreeClazz} of this clazz.
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
		Clazz componentClazz = this.getComponentClazz();
		return (this.family == this.klass ? "" : this.family.getSimpleName()) + ":" +
			   this.klass.getSimpleName() +
			   (componentClazz == null ? "" : "<" + componentClazz.getSimpleName() + ">");
	}

	/**
	 * Get the tree of the clazzes specified foreach component to be held by an instance of this clazz.
	 * <br>
	 * Note: the returned tree will always be unmodifiable. Trying to modify it will always throw an exception.
	 *
	 * @return the tree of the clazzes specified foreach component to be held by an instance of this clazz.
	 */
	public Map<Object, Clazz> getTree() {
		return this.tree;
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
		return Clazz.of(family, this.klass, this.tree);
	}

	/**
	 * Get a clazz that represents the {@code klass} of this clazz, and have the given {@code tree}, and should be
	 * treated as if it was the given {@code family}.
	 *
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @param tree   the tree of the clazzes specified foreach component to be held by an instance of the returned
	 *               clazz.
	 * @return a clazz that represents the {@code klass} of this clazz, and have the given {@code tree}, and should be
	 * 		treated as if it was the given {@code family}.
	 * @throws NullPointerException if the given {@code family} or {@code tree} is null.
	 */
	public Clazz override(Class family, Map<Object, Clazz> tree) {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(tree, "tree");
		return Clazz.of(family, this.klass, tree);
	}

	/**
	 * Get a clazz that represents the {@code Object} version of the {@code klass} of this clazz, and have the {@code
	 * tree} of this clazz, and should be treated as if it was the {@code family} of this clazz.
	 *
	 * @return a clazz that represents the {@code Object} version of the {@code klass} of this clazz, and have the
	 *        {@code tree} of this clazz, and should be treated as if it was the {@code family} of this clazz.
	 */
	public Clazz toObjectClazz() {
		return Clazz.of(this.family, Reflection.asObjectClass(this.klass), this.tree);
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
			   Clazz.of(this.family, Reflection.asPrimitiveClass(this.klass), this.tree);
	}

	@SuppressWarnings("JavaDoc")
	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
		Objects.requireNonNull(stream, "stream");
		this.klass = (Class) stream.readObject();
		this.family = (Class) stream.readObject();
		this.tree = (Map) stream.readObject();
		if (stream.readBoolean()) {
			//there is more
			System.err.println("Deserializing newer version of Clazz");
		}
	}

	@SuppressWarnings("JavaDoc")
	private void writeObject(ObjectOutputStream stream) throws IOException {
		Objects.requireNonNull(stream, "stream");
		stream.writeObject(this.klass);
		stream.writeObject(this.family);
		stream.writeObject(this.tree);
		stream.writeBoolean(false);
		//free to add more
	}

	/**
	 * A component clazzes map that can't be modified and clazz consider it safe to be stored as its tree.
	 */
	public static final class Tree extends AbstractMap<Object, Clazz> {
		/**
		 * An unmodifiable entry-set containing the {@link Map.Entry}s of this tree.
		 */
		private final Set<Entry<Object, Clazz>> entrySet;

		/**
		 * Construct a new clazzes tree that can't be modified and contains a clone of the mappings of the given tree.
		 *
		 * @param tree to clone the mappings of it to the constructed tree.
		 * @throws NullPointerException     if the given {@code tree} is null.
		 * @throws IllegalArgumentException if a value is stored in the given {@code tree} is not an instance of {@link
		 *                                  Clazz}.
		 */
		private Tree(Map<Object, Clazz> tree) {
			if (tree instanceof Tree) {
				this.entrySet = ((Tree) tree).entrySet;
			} else {
				Set entrySet = new HashSet();
				tree.forEach((key, klazz) -> {
					if (klazz instanceof Clazz)
						entrySet.add(new SimpleImmutableEntry(key, klazz));
					else throw new IllegalArgumentException("Not a clazz " + klazz);
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
