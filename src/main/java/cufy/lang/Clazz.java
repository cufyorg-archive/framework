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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * An alternative representation for {@link Class classes}. This provides more data about the targeted type. About the component types and the family
 * of that class. The family is how the class should be treated.
 * <p>
 * Note: the class {@link Void} is the class of null Note: some of the documentations in this class are the same as from the class {@link Class}
 *
 * @param <C> the "klass" this clazz is holding
 * @author lsafer
 * @version 0.1.5
 * @since 29-Mar-2020
 */
final public class Clazz<C> {
	//fields

	/**
	 * The family of this clazz. How this clazz should be treated.
	 */
	final private Class family;
	/**
	 * The class represented by this clazz.
	 */
	final private Class<C> klass;
	/**
	 * The components specified to be held by the instance of this clazz.
	 */
	final private ClazzTree tree;

	//constructors

	/**
	 * Construct a new clazz with the given parameters.
	 *
	 * @param klass the class to be represented by this clazz.
	 * @throws NullPointerException if the given 'klass' is null.
	 */
	private Clazz(Class<C> klass) {
		Objects.requireNonNull(klass, "klass");
		this.family = klass;
		this.klass = klass;
		this.tree = new ClazzTree();
	}

	/**
	 * Construct a new clazz with the given parameters.
	 *
	 * @param klass the class to be represented by this clazz.
	 * @param tree  the components that can be held by the instances of this clazz.
	 * @throws NullPointerException if the given 'klass' or 'tree' is null.
	 */
	private Clazz(Class<C> klass, ClazzTree tree) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(tree, "tree");
		this.family = klass;
		this.klass = klass;
		this.tree = tree;
	}

	/**
	 * Construct a new clazz with the given parameters.
	 *
	 * @param family how this clazz should be treated as.
	 * @param klass  the class to be represented by this clazz.
	 * @throws NullPointerException if the given 'family' or 'klass' is null.
	 */
	private Clazz(Class family, Class<C> klass) {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(klass, "klass");
		this.family = family;
		this.klass = klass;
		this.tree = new ClazzTree();
	}

	/**
	 * Construct a new clazz with the given parameters.
	 *
	 * @param family how this clazz should be treated as.
	 * @param klass  the class to be represented by this clazz.
	 * @param tree   the components that can be held by the instances of this clazz.
	 * @throws NullPointerException if the given 'family' or 'klass' or 'tree' is null.
	 */
	private Clazz(Class family, Class<C> klass, ClazzTree tree) {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(tree, "tree");
		this.family = family;
		this.klass = klass;
		this.tree = tree;
	}

	//ofs

	/**
	 * Get the clazz that represents the given class with no component types.
	 *
	 * @param klass the class to be represented by this clazz
	 * @param <C>   the "klass" this clazz is holding
	 * @return a clazz represents the given class with no component types
	 * @throws NullPointerException if the given 'klass' is null
	 */
	public static <C> Clazz<C> of(Class<C> klass) {
		return new Clazz(klass);
	}

	/**
	 * Get the clazz that represents the given class with the given component types.
	 *
	 * @param klass the class to be represented by this clazz
	 * @param tree  the components that can be held by the instances of this clazz
	 * @param <C>   the "klass" this clazz is holding
	 * @return a clazz represents the given class with the given component types
	 * @throws NullPointerException if the given 'klass' or 'tree' is null
	 */
	public static <C> Clazz<C> of(Class<C> klass, ClazzTree tree) {
		return new Clazz(klass, tree);
	}

	/**
	 * Get the clazz that represents the given class with no component types.
	 *
	 * @param family how this clazz should be treated as
	 * @param klass  the class to be represented by this clazz
	 * @param <C>    the "klass" this clazz is holding
	 * @return a clazz represents the given class with no component types
	 * @throws NullPointerException if the given 'family' or 'klass' is null
	 */
	public static <C> Clazz<C> of(Class family, Class<C> klass) {
		return new Clazz(family, klass);
	}

	/**
	 * Get the clazz that represents the given class with the given component types.
	 *
	 * @param family how this clazz should be treated as
	 * @param klass  the class to be represented by this clazz
	 * @param tree   the components that can be held by the instances of this clazz
	 * @param <C>    the "klass" this clazz is holding
	 * @return a clazz represents the given class with the given component types
	 * @throws NullPointerException if the given 'family' or 'klass' or 'tree' is null
	 */
	public static <C> Clazz<C> of(Class family, Class<C> klass, ClazzTree tree) {
		return new Clazz(family, klass, tree);
	}

	/**
	 * Get the clazz that represents the given instance with no component types.
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(instance == null ? Void.class : instance.getClass);
	 * </pre>
	 *
	 * @param instance to get the clazz that represents it
	 * @param <C>      the "klass" this clazz is holding
	 * @return a clazz represents the given class with no component types
	 */
	public static <C> Clazz<C> ofi(C instance) {
		return instance == null ? (Clazz<C>) Clazz.of(Void.class) : (Clazz<C>) Clazz.of(instance.getClass());
	}

	/**
	 * Get the clazz that represents the given instance with the given component types.
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(instance == null ? Void.class : instance.getClass, tree);
	 * </pre>
	 *
	 * @param instance to get the clazz that represents it
	 * @param tree     the components that can be held by the instances of this clazz
	 * @param <C>      the "klass" this clazz is holding
	 * @return a clazz represents the given class with the given component types
	 * @throws NullPointerException if the given 'tree' is null
	 */
	public static <C> Clazz<C> ofi(C instance, ClazzTree tree) {
		return instance == null ? (Clazz<C>) Clazz.of(Void.class, tree) : (Clazz<C>) Clazz.of(instance.getClass(), tree);
	}

	/**
	 * Get the clazz that represents the given instance with no component types.
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(family, instance == null ? Void.class : instance.getClass, componentTypes);
	 * </pre>
	 *
	 * @param family   how this clazz should be treated as
	 * @param instance to get the clazz that represents it
	 * @param <C>      the "klass" this clazz is holding
	 * @return a clazz represents the given class with no component types
	 * @throws NullPointerException if the given 'family' is null
	 */
	public static <C> Clazz<C> ofi(Class family, C instance) {
		return instance == null ? (Clazz<C>) Clazz.of(family, Void.class) : (Clazz<C>) Clazz.of(family, instance.getClass());
	}

	/**
	 * Get the clazz that represents the given instance with the given component types.
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(family, instance == null ? Void.class : instance.getClass, tree);
	 * </pre>
	 *
	 * @param family   how this clazz should be treated as
	 * @param instance to get the clazz that represents it
	 * @param tree     the components that can be held by the instances of this clazz
	 * @param <C>      the "klass" this clazz is holding
	 * @return a clazz represents the given class with the given component types
	 * @throws NullPointerException if the given 'family' or 'tree' is null
	 */
	public static <C> Clazz<C> ofi(Class family, C instance, ClazzTree tree) {
		return instance == null ? (Clazz<C>) Clazz.of(family, Void.class, tree) : (Clazz<C>) Clazz.of(family, instance.getClass(), tree);
	}

	/**
	 * Get the clazz that represents the class of for the given name with no componentTypes.
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(Class.forName(name), componentTypes);
	 * </pre>
	 *
	 * @param name of the class to get the clazz that represents it
	 * @param <C>  the "klass" this clazz is holding
	 * @return a clazz represents the class for the given name with no component types
	 * @throws ClassNotFoundException      if the class cannot be located
	 * @throws LinkageError                if the linkage fails
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails
	 * @throws NullPointerException        if the given 'name' is null
	 */
	public static <C> Clazz<C> ofn(String name) throws ClassNotFoundException {
		return (Clazz<C>) Clazz.of(Class.forName(name));
	}

	/**
	 * Get the clazz that represents the class of for the given name with the given tree.
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(Class.forName(name), tree);
	 * </pre>
	 *
	 * @param name of the class to get the clazz that represents it
	 * @param tree the components that can be held by the instances of this clazz
	 * @param <C>  the "klass" this clazz is holding
	 * @return a clazz represents the class for the given name with the given component types
	 * @throws ClassNotFoundException      if the class cannot be located
	 * @throws LinkageError                if the linkage fails
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails
	 * @throws NullPointerException        if the given 'name' or 'tree' is null
	 */
	public static <C> Clazz<C> ofn(String name, ClazzTree tree) throws ClassNotFoundException {
		return (Clazz<C>) Clazz.of(Class.forName(name), tree);
	}

	/**
	 * Get the clazz that represents the class of for the given name with no componentTypes.
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(family, Class.forName(name));
	 * </pre>
	 *
	 * @param family how this clazz should be treated as
	 * @param name   of the class to get the clazz that represents it
	 * @param <C>    the "klass" this clazz is holding
	 * @return a clazz represents the class for the given name with no component types
	 * @throws ClassNotFoundException      if the class cannot be located
	 * @throws LinkageError                if the linkage fails
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails
	 * @throws NullPointerException        if the given 'family' or 'name' is null
	 */
	public static <C> Clazz<C> ofn(Class family, String name) throws ClassNotFoundException {
		return (Clazz<C>) Clazz.of(family, Class.forName(name));
	}

	/**
	 * Get the clazz that represents the class of for the given name with the given tree.
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(family, Class.forName(name), tree);
	 * </pre>
	 *
	 * @param family how this clazz should be treated as
	 * @param name   of the class to get the clazz that represents it
	 * @param tree   the components that can be held by the instances of this clazz
	 * @param <C>    the "klass" this clazz is holding
	 * @return a clazz represents the class for the given name with the given component types
	 * @throws ClassNotFoundException      if the class cannot be located
	 * @throws LinkageError                if the linkage fails
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails
	 * @throws NullPointerException        if the given 'family' or 'name' or 'tree' is null
	 */
	public static <C> Clazz<C> ofn(Class family, String name, ClazzTree tree) throws ClassNotFoundException {
		return (Clazz<C>) Clazz.of(family, Class.forName(name), tree);
	}

	/**
	 * Get the clazz that represents the class of for the given name with no componentTypes.
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(Class.forName(name, initialize, loader));
	 * </pre>
	 *
	 * @param loader     class loader from which the class must be loaded
	 * @param initialize if true the class will be initialized. See Section 12.4 of The Java Language
	 * @param name       of the class to get the clazz that represents it
	 * @param <C>        the "klass" this clazz is holding
	 * @return a clazz represents the class for the given name with no component types
	 * @throws ClassNotFoundException      if the class cannot be located
	 * @throws LinkageError                if the linkage fails
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails
	 * @throws NullPointerException        if the given 'loader' or 'name' is null
	 * @throws SecurityException           if a security manager is present, and the loader is null, and the caller's class loader is not null, and
	 *                                     the caller does not have the RuntimePermission("getClassLoader")
	 */
	public static <C> Clazz<C> ofn(ClassLoader loader, boolean initialize, String name) throws ClassNotFoundException {
		return (Clazz<C>) Clazz.of(Class.forName(name, initialize, loader));
	}

	/**
	 * Get the clazz that represents the class of for the given name with the given tree.
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(Class.forName(name, initialize, loader), tree);
	 * </pre>
	 *
	 * @param loader     class loader from which the class must be loaded
	 * @param initialize if true the class will be initialized. See Section 12.4 of The Java Language
	 * @param name       of the class to get the clazz that represents it
	 * @param tree       the components that can be held by the instances of this clazz
	 * @param <C>        the "klass" this clazz is holding
	 * @return a clazz represents the class for the given name with the given component types
	 * @throws ClassNotFoundException      if the class cannot be located
	 * @throws LinkageError                if the linkage fails
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails
	 * @throws NullPointerException        if the given 'loader' or 'name' or 'tree' is null
	 * @throws SecurityException           if a security manager is present, and the loader is null, and the caller's class loader is not null, and
	 *                                     the caller does not have the RuntimePermission("getClassLoader")
	 */
	public static <C> Clazz<C> ofn(ClassLoader loader, boolean initialize, String name, ClazzTree tree) throws ClassNotFoundException {
		return (Clazz<C>) Clazz.of(Class.forName(name, initialize, loader), tree);
	}

	/**
	 * Get the clazz that represents the class of for the given name with no componentTypes.
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(family, Class.forName(name, initialize, loader));
	 * </pre>
	 *
	 * @param loader     class loader from which the class must be loaded
	 * @param initialize if true the class will be initialized. See Section 12.4 of The Java Language
	 * @param family     how this clazz should be treated as
	 * @param name       of the class to get the clazz that represents it
	 * @param <C>        the "klass" this clazz is holding
	 * @return a clazz represents the class for the given name with no component types
	 * @throws ClassNotFoundException      if the class cannot be located
	 * @throws LinkageError                if the linkage fails
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails
	 * @throws NullPointerException        if the given 'loader' or 'family' or 'name' is null
	 * @throws SecurityException           if a security manager is present, and the loader is null, and the caller's class loader is not null, and
	 *                                     the caller does not have the RuntimePermission("getClassLoader")
	 */
	public static <C> Clazz<C> ofn(ClassLoader loader, boolean initialize, Class family, String name) throws ClassNotFoundException {
		return (Clazz<C>) Clazz.of(family, Class.forName(name, initialize, loader));
	}

	/**
	 * Get the clazz that represents the class of for the given name with the given tree.
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(family, Class.forName(name, initialize, loader), tree);
	 * </pre>
	 *
	 * @param loader     class loader from which the class must be loaded
	 * @param initialize if true the class will be initialized. See Section 12.4 of The Java Language
	 * @param family     how this clazz should be treated as
	 * @param name       of the class to get the clazz that represents it
	 * @param tree       the components that can be held by the instances of this clazz
	 * @param <C>        the "klass" this clazz is holding
	 * @return a clazz represents the class for the given name with the given component types
	 * @throws ClassNotFoundException      if the class cannot be located
	 * @throws LinkageError                if the linkage fails
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails
	 * @throws NullPointerException        if the given 'loader' or 'family' or 'name' or 'tree' is null
	 * @throws SecurityException           if a security manager is present, and the loader is null, and the caller's class loader is not null, and
	 *                                     the caller does not have the RuntimePermission("getClassLoader")
	 */
	public static <C> Clazz<C> ofn(ClassLoader loader, boolean initialize, Class family, String name, ClazzTree tree) throws ClassNotFoundException {
		return (Clazz<C>) Clazz.of(family, Class.forName(name, initialize, loader), tree);
	}

	/**
	 * Get a clazz with a clazz-tree auto-generated from the given instance.
	 *
	 * @param instance to get the clazz that represents it.
	 * @param <C>      the "klass" this clazz is holding.
	 * @return a clazz that have a klass of the given instance and have a clazz-tree auto-generated from the given instance.
	 */
	public static <C> Clazz<C> ofx(Object instance) {
		return (Clazz<C>) Clazz.ofi(instance, ClazzTree.of(instance));
	}

	/**
	 * Get a clazz with a clazz-tree auto-generated from the given instance.
	 *
	 * @param family   how this clazz should be treated as.
	 * @param instance to get the clazz that represents it.
	 * @param <C>      the "klass" this clazz is holding.
	 * @return a clazz that have the given family and have a klass of the given instance and have a clazz-tree auto-generated from the given instance.
	 * @throws NullPointerException if the given 'family' is null.
	 */
	public static <C> Clazz<C> ofx(Class family, Object instance) {
		return (Clazz<C>) Clazz.ofi(family, instance, ClazzTree.of(instance));
	}

	/**
	 * Get the clazz that have family of the given {@code familySrc} and klass of the given {@code klassSrc} and componentTypes of the given {@code
	 * treeSrc}.
	 *
	 * @param familySrc the source of the family
	 * @param klassSrc  the source of the klass
	 * @param treeSrc   the source of the componentTypes
	 * @param <C>       the type of the returned clazz's klass
	 * @return a clazz represents the given parameters
	 * @throws NullPointerException if the given 'familySrc' or 'klassSrc' or 'treeSrc' is null
	 */
	public static <C> Clazz<C> ofz(Clazz familySrc, Clazz<C> klassSrc, Clazz treeSrc) {
		return Clazz.of(familySrc.family, klassSrc.klass, treeSrc.tree);
	}

	/**
	 * Get the clazz that represents the given array class.
	 *
	 * @param family how this clazz should be treated as
	 * @param klass  the array class to be represented by this clazz
	 * @param <C>    the "klass" this clazz is holding
	 * @return a clazz represents the given array class
	 * @throws NullPointerException if the given 'family' or 'klass' is null
	 */
	static <C> Clazz<C> ofa(Class family, Class<C> klass) {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(klass, "klass");
		if (!klass.isArray())
			throw new IllegalArgumentException(klass + " is not a class for arrays");

		Class component = klass.getComponentType();
		Class componentFamily = family.isArray() ? family.getComponentType() : component;
		Clazz componentClazz = component.isArray() ? Clazz.ofa(componentFamily, component) : Clazz.of(componentFamily, component);

		return Clazz.of(family, klass, new ClazzTree(componentClazz));
	}

	/**
	 * Get the clazz that represents the given array class.
	 *
	 * @param klass the array class to be represented by this clazz
	 * @param <C>   the "klass" this clazz is holding
	 * @return a clazz represents the given array class
	 * @throws NullPointerException if the given 'klass' is null
	 */
	static <C> Clazz<C> ofa(Class<C> klass) {
		Objects.requireNonNull(klass, "klass");
		if (!klass.isArray())
			throw new IllegalArgumentException(klass + " is not a class for arrays");

		Class component = klass.getComponentType();
		Clazz componentClazz = component.isArray() ? Clazz.ofa(component) : Clazz.of(component);

		return Clazz.of(klass, new ClazzTree(componentClazz));
	}

	//delegates

	/**
	 * Shortcut for: {@code getKlass().cast(instance)}.
	 *
	 * @param instance the instance to be cast
	 * @return the instance after casting, or null if the instance is null
	 * @throws ClassCastException if the instance is not null and is not assignable to the type T.
	 * @see Class#cast(Object)
	 */
	final public C cast(Object instance) {
		return this.klass.cast(instance);
	}

	/**
	 * Shortcut for: {@code getKlass.getComponentType()}.
	 *
	 * @return the Class representing the component type of this class if this class is an array
	 * @see Class#getComponentType()
	 */
	final public Class getComponentType() {
		return this.klass.getComponentType();
	}

	/**
	 * Shortcut for: {@code getKlass().getConstructor(parameterTypes)}.
	 *
	 * @param parameterTypes the parameter array
	 * @return the {@code Constructor} object of the public constructor that matches the specified {@code parameterTypes}
	 * @throws NoSuchMethodException if a matching method is not found.
	 * @throws SecurityException     If a security manager, <i>s</i>, is present and the caller's class loader is not the same as or an ancestor of
	 *                               the class loader for the current class and invocation of {@link SecurityManager#checkPackageAccess
	 *                               s.checkPackageAccess()} denies access to the package of this class.
	 * @see Class#getConstructor(Class[])
	 */
	final public Constructor<C> getConstructor(Class<?>... parameterTypes) throws NoSuchMethodException {
		return this.klass.getConstructor(parameterTypes);
	}

	/**
	 * Shortcut for: {@code getKlass().getConstructors()}.
	 *
	 * @return the array of {@code Constructor} objects representing the public constructors of this class
	 * @throws SecurityException If a security manager, <i>s</i>, is present and the caller's class loader is not the same as or an ancestor of the
	 *                           class loader for the current class and invocation of {@link SecurityManager#checkPackageAccess
	 *                           s.checkPackageAccess()} denies access to the package of this class.
	 * @see Class#getConstructors()
	 */
	final public Constructor<?>[] getConstructors() {
		return this.klass.getConstructors();
	}

	/**
	 * Shortcut for: {@code getKlass().getDeclaredConstructor(parameterTypes)}.
	 *
	 * @param parameterTypes the parameter array
	 * @return The {@code Constructor} object for the constructor with the specified parameter list
	 * @throws NoSuchMethodException if a matching method is not found.
	 * @throws SecurityException     If a security manager, <i>s</i>, is present and any of the following conditions is met:
	 *                               <ul>
	 *                               <li> the caller's class loader is not the same as the
	 *                               class loader of this class and invocation of
	 *                               {@link SecurityManager#checkPermission
	 *                               s.checkPermission} method with
	 *                               {@code RuntimePermission("accessDeclaredMembers")}
	 *                               denies access to the declared constructor
	 *                               <li> the caller's class loader is not the same as or an
	 *                               ancestor of the class loader for the current class and
	 *                               invocation of {@link SecurityManager#checkPackageAccess
	 *                               s.checkPackageAccess()} denies access to the package
	 *                               of this class
	 * @see Class#getDeclaredConstructor(Class[])
	 */
	final public Constructor<C> getDeclaredConstructor(Class<?> parameterTypes) throws NoSuchMethodException {
		return this.klass.getDeclaredConstructor(parameterTypes);
	}

	/**
	 * Shortcut for: {@code getKlass().getDeclaredConstructors()}.
	 *
	 * @return the array of {@code Constructor} objects representing all the declared constructors of this class
	 * @throws SecurityException If a security manager, <i>s</i>, is present and any of the following conditions is met:
	 *                           <ul>
	 *                           <li> the caller's class loader is not the same as the
	 *                           class loader of this class and invocation of
	 *                           {@link SecurityManager#checkPermission
	 *                           s.checkPermission} method with
	 *                           {@code RuntimePermission("accessDeclaredMembers")}
	 *                           denies access to the declared constructors within this class
	 *                           <li> the caller's class loader is not the same as or an
	 *                           ancestor of the class loader for the current class and
	 *                           invocation of {@link SecurityManager#checkPackageAccess
	 *                           s.checkPackageAccess()} denies access to the package
	 *                           of this class
	 * @see Class#getDeclaredConstructors()
	 */
	final public Constructor<?>[] getDeclaredConstructors() {
		return this.klass.getDeclaredConstructors();
	}

	/**
	 * Shortcut for: {@code getKlass.getDeclaredMethod(name, parameterTypes)}.
	 *
	 * @param name           the name of the method
	 * @param parameterTypes the parameter array
	 * @return the {@code Method} object for the method of this class matching the specified name and parameters
	 * @throws NoSuchMethodException if a matching method is not found.
	 * @throws NullPointerException  if {@code name} is {@code null}
	 * @throws SecurityException     If a security manager, <i>s</i>, is present and any of the following conditions is met:
	 *                               <ul>
	 *                               <li> the caller's class loader is not the same as the
	 *                               class loader of this class and invocation of
	 *                               {@link SecurityManager#checkPermission
	 *                               s.checkPermission} method with
	 *                               {@code RuntimePermission("accessDeclaredMembers")}
	 *                               denies access to the declared method
	 *                               <li> the caller's class loader is not the same as or an
	 *                               ancestor of the class loader for the current class and
	 *                               invocation of {@link SecurityManager#checkPackageAccess
	 *                               s.checkPackageAccess()} denies access to the package
	 *                               of this class
	 *                               </ul>
	 * @see Class#getDeclaredMethod(String, Class[])
	 */
	final public Method getDeclaredMethod(String name, Class<?>... parameterTypes) throws NoSuchMethodException {
		return this.klass.getDeclaredMethod(name, parameterTypes);
	}

	/**
	 * Shortcut for: {@code getKlass().getEnclosingConstructor()}.
	 *
	 * @return the immediately enclosing constructor of the underlying class, if that class is a local or anonymous class; otherwise {@code null}.
	 * @throws SecurityException If a security manager, <i>s</i>, is present and any of the following conditions is met:
	 *                           <ul>
	 *                           <li> the caller's class loader is not the same as the
	 *                           class loader of the enclosing class and invocation of
	 *                           {@link SecurityManager#checkPermission
	 *                           s.checkPermission} method with
	 *                           {@code RuntimePermission("accessDeclaredMembers")}
	 *                           denies access to the constructors within the enclosing class
	 *                           <li> the caller's class loader is not the same as or an
	 *                           ancestor of the class loader for the enclosing class and
	 *                           invocation of {@link SecurityManager#checkPackageAccess
	 *                           s.checkPackageAccess()} denies access to the package
	 *                           of the enclosing class
	 * @see Class#getEnclosingConstructor()
	 */
	final public Constructor getEnclosingConstructor() {
		return this.klass.getEnclosingConstructor();
	}

	/**
	 * Shortcut for: {@code getKlass().getMethod(name, parameterTypes)}.
	 *
	 * @param name           the name of the method
	 * @param parameterTypes the list of parameters
	 * @return the {@code Method} object that matches the specified {@code name} and {@code parameterTypes}
	 * @throws NoSuchMethodException if a matching method is not found or if the name is "&lt;init&gt;"or "&lt;clinit&gt;".
	 * @throws NullPointerException  if {@code name} is {@code null}
	 * @throws SecurityException     If a security manager, <i>s</i>, is present and the caller's class loader is not the same as or an ancestor of
	 *                               the class loader for the current class and invocation of {@link SecurityManager#checkPackageAccess
	 *                               s.checkPackageAccess()} denies access to the package of this class.
	 * @see Class#getMethod(String, Class[])
	 */
	final public Method getMethod(String name, Class<?>... parameterTypes) throws NoSuchMethodException {
		return this.klass.getMethod(name, parameterTypes);
	}

	/**
	 * Shortcut for: {@code getKlass().isAnnotation()}.
	 *
	 * @return {@code true} if this class object represents an annotation type; {@code false} otherwise
	 * @see Class#isAnnotation()
	 */
	final public boolean isAnnotation() {
		return this.klass.isAnnotation();
	}

	/**
	 * Shortcut for: {@code getKlass().isArray()}.
	 *
	 * @return true if this clazz represents an array class; false otherwise.
	 * @see Class#isArray()
	 */
	final public boolean isArray() {
		return this.klass.isArray();
	}

	/**
	 * Shortcut for: {@code getKlass().isAssignableFrom(klass)}.
	 *
	 * @param klass the class object to be checked
	 * @return if this clazz is assignable from the given class
	 * @throws NullPointerException if the specified Class parameter is null.
	 * @see Class#isAssignableFrom(Class)
	 */
	final public boolean isAssignableFrom(Class klass) {
		return this.klass.isAssignableFrom(klass);
	}

	/**
	 * Shortcut for: {@code getKlass().isInterface}.
	 *
	 * @return {@code true} if this object represents an interface; {@code false} otherwise.
	 * @see Class#isInterface()
	 */
	final public boolean isInterface() {
		return this.klass.isInterface();
	}

	/**
	 * Shortcut for: {@code getKlass().isPrimitive()}
	 *
	 * @return true if and only if this class represents a primitive type
	 * @see Class#isPrimitive()
	 */
	final public boolean isPrimitive() {
		return this.klass.isPrimitive();
	}

	/**
	 * Shortcut for: {@code getKlass().isSynthetic()}.
	 *
	 * @return {@code true} if and only if this class is a synthetic class as defined by the Java Language Specification.
	 * @see Class#isSynthetic()
	 */
	final public boolean isSynthetic() {
		return this.klass.isSynthetic();
	}

	/**
	 * Shortcut for: {@code getKlass().newInstance()}.
	 *
	 * @return a newly allocated instance of the class represented by this object.
	 * @throws IllegalAccessException      if the class or its nullary constructor is not accessible.
	 * @throws InstantiationException      if this {@code Class} represents an abstract class, an interface, an array class, a primitive type, or
	 *                                     void; or if the class has no nullary constructor; or if the instantiation fails for some other reason.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws SecurityException           If a security manager, <i>s</i>, is present and the caller's class loader is not the same as or an ancestor
	 *                                     of the class loader for the current class and invocation of {@link SecurityManager#checkPackageAccess
	 *                                     s.checkPackageAccess()} denies access to the package of this class.
	 * @see Class#newInstance()
	 */
	final public C newInstance() throws IllegalAccessException, InstantiationException {
		return this.klass.newInstance();
	}

	/**
	 * Shortcut for: {@code getKlass().toGenericString()}.
	 *
	 * @return a string describing this {@code Clazz}, including information about modifiers and type parameters
	 * @see Class#toGenericString()
	 */
	final public String toGenericString() {
		return this.klass.toGenericString();
	}

	//override

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
		return "clazz " + this.klass.getName() + " [" + this.family.getName() + "](" + this.tree.size() + ")";
	}

	//methods

	/**
	 * Get the class that this clazz should be treated as.
	 *
	 * @return how this clazz should be treated
	 */
	public Class getFamily() {
		return this.family;
	}

	/**
	 * Get the class represented by this clazz.
	 *
	 * @return the class represented by this clazz.
	 */
	public Class<C> getKlass() {
		return this.klass;
	}

	/**
	 * Get all the component types in this clazz (except the klass's componentType).
	 *
	 * @return all the component types in this clazz
	 */
	public ClazzTree getTree() {
		return this.tree;
	}

	/**
	 * Check if the class of this clazz is or has a primitive class or not.
	 *
	 * @return whether this class is or has a primitive class or not
	 */
	public boolean hasPrimitive() {
		return Reflection.hasPrimitiveClass(this.klass);
	}

	/**
	 * Determine if the class represented by this clazz is a super class for the class represented by the given clazz.
	 *
	 * @param klazz the Clazz object to be checked
	 * @return if this clazz is assignable from the given clazz
	 * @throws NullPointerException if the specified Clazz parameter is null.
	 */
	public boolean isAssignableFrom(Clazz klazz) {
		return this.klass.isAssignableFrom(klazz.klass);
	}

	/**
	 * Determine if the given object is instance of this clazz.
	 *
	 * @param instance the object to check
	 * @return true if obj is an instance of this clazz
	 */
	public boolean isInstance(Object instance) {
		return instance == null ? this.klass == Void.class : this.klass.isInstance(instance);
	}

	/**
	 * Get a clazz that have the same {@code klass} of this, but different {@code family}.
	 *
	 * @param family of the returned clazz.
	 * @param tree   of the returned clazz.
	 * @return a clazz the same as this, but different family and family.
	 * @throws NullPointerException if the given 'family' or 'tree' is null.
	 */
	public Clazz override(Class family, ClazzTree tree) {
		return Clazz.of(family, this.klass, tree);
	}

	/**
	 * Get a clazz that have the same {@code klass} and {@code tree} of this, but different {@code family}.
	 *
	 * @param family of the returned clazz.
	 * @return a clazz the same as this, but different family.
	 * @throws NullPointerException if the given 'family' is null.
	 */
	public Clazz override(Class family) {
		return Clazz.of(family, this.klass, this.tree);
	}

	/**
	 * Get z clazz that have the same {@code family} and {@code klass} of this, but different {@code tree}.
	 *
	 * @param tree of the returned clazz.
	 * @return a clazz the same as this, but different tree.
	 * @throws NullPointerException if the given 'tree' is null.
	 */
	public Clazz override(ClazzTree tree) {
		return Clazz.of(this.family, this.klass, tree);
	}

	/**
	 * Get the clazz that its class extends Object that represent the class of this clazz.
	 *
	 * @return a clazz its class extends object from the class of this clazz
	 */
	public Clazz toObjectClazz() {
		return Clazz.of(this.family, Reflection.asObjectClass(this.klass), this.tree);
	}

	/**
	 * Get the clazz that its class don't extends Object from the class of this clazz.
	 *
	 * @return the non-object clazz of this clazz
	 * @throws IllegalArgumentException when this clazz don't have a primitive type
	 */
	public Clazz toPrimitiveClazz() {
		return Clazz.of(this.family, Reflection.asPrimitiveClass(this.klass), this.tree);
	}
}

//
//	/**
//	 * Get the component type that have been associated to the given key.
//	 *
//	 * @param key the key of the component type.
//	 * @return the component type that have been associated to the given key.
//	 */
//	public Clazz getComponentType(Object key) {
//		return this.tree.get(key);
//	}
//
//	/**
//	 * Get how many component types this clazz does have.
//	 *
//	 * @return the count of the component types this clazz does have
//	 */
//	public int getComponentsCount() {
//		return this.tree.size();
//	}