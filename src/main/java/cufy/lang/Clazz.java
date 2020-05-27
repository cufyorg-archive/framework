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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * An alternative representation for {@link Class classes}. This provides more data about the targeted type. About the component types and the family
 * of that class. The family is how the class should be treated.
 * <p>
 * Note: the class {@link Void} is the class of null
 * Note: some of the documentations in this class are the same as from the class {@link Class}
 *
 * @param <C> the "klass" this clazz is holding
 * @author lsafer
 * @version 0.1.5
 * @since 29-Mar-2020
 */
final public class Clazz<C> {
	/**
	 * The global clazzes. To not allow repeated clazzes for the same class.
	 */
	final private static Map<Class, Clazz> clazzes = new HashMap<>();
	/**
	 * The components specified to be held by the instance of this clazz.
	 */
	final private Clazz[] componentTypes;
	/**
	 * The family of this clazz. How this clazz should be treated.
	 */
	final private Class family;
	/**
	 * The class represented by this clazz.
	 */
	final private Class<C> klass;

	/**
	 * Construct a new clazz with the given parameters.
	 *
	 * @param family         how this clazz should be treated as
	 * @param klass          the class to be represented by this clazz
	 * @param componentTypes the components that can be held by the instances of this clazz
	 * @throws NullPointerException if the given 'family' or 'klass' or 'componentTypes' is null
	 */
	private Clazz(Class family, Class<C> klass, Clazz... componentTypes) {
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(componentTypes, "componentTypes");

		this.family = family;
		this.klass = klass;

		this.componentTypes = new Clazz[componentTypes.length];
		System.arraycopy(componentTypes, 0, this.componentTypes, 0, componentTypes.length);
	}

	/**
	 * Get the clazz that represents the given class with the given component types.
	 *
	 * @param klass          the class to be represented by this clazz
	 * @param componentTypes the components that can be held by the instances of this clazz
	 * @param <C>            the "klass" this clazz is holding
	 * @return a clazz represents the given class with the given component types
	 * @throws NullPointerException if the given 'klass' or 'componentTypes' is null
	 */
	public static <C> Clazz<C> of(Class<C> klass, Clazz... componentTypes) {
		if (componentTypes.length == 0) {
			if (klass.isArray()) {
				return ofa(klass);
			} else {
				return clazzes.computeIfAbsent(klass, k -> new Clazz(k, k));
			}
		} else {
			return new Clazz<>(klass, klass, componentTypes);
		}
	}

	/**
	 * Get the clazz that represents the given class with the given component types.
	 *
	 * @param family         how this clazz should be treated as
	 * @param klass          the class to be represented by this clazz
	 * @param componentTypes the components that can be held by the instances of this clazz
	 * @param <C>            the "klass" this clazz is holding
	 * @return a clazz represents the given class with the given component types
	 * @throws NullPointerException if the given 'family' or 'klass' or 'componentTypes' is null
	 */
	public static <C> Clazz<C> of(Class family, Class<C> klass, Clazz... componentTypes) {
		if (componentTypes.length == 0 && klass.isArray()) {
			return ofa(family, klass);
		} else {
			return new Clazz<>(family, klass, componentTypes);
		}
	}

	/**
	 * Get the clazz that represents the given instance with the given component types.
	 *
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(instance == null ? Void.class : instance.getClass, componentTypes);
	 * </pre>
	 *
	 * @param instance       to get the clazz that represents it
	 * @param componentTypes the components that can be held by the instances of this clazz
	 * @param <C>            the "klass" this clazz is holding
	 * @return a clazz represents the given class with the given component types
	 * @throws NullPointerException if the given 'componentTypes' is null
	 */
	public static <C> Clazz<C> ofi(C instance, Clazz... componentTypes) {
		return of((Class) (instance == null ? Void.class : instance.getClass()), componentTypes);
	}

	/**
	 * Get the clazz that represents the given instance with the given component types.
	 *
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(family, instance == null ? Void.class : instance.getClass, componentTypes);
	 * </pre>
	 *
	 * @param family         how this clazz should be treated as
	 * @param instance       to get the clazz that represents it
	 * @param componentTypes the components that can be held by the instances of this clazz
	 * @param <C>            the "klass" this clazz is holding
	 * @return a clazz represents the given class with the given component types
	 * @throws NullPointerException if the given 'family' or 'componentTypes' is null
	 */
	public static <C> Clazz<C> ofi(Class family, C instance, Clazz... componentTypes) {
		return of(family, (Class) (instance == null ? Void.class : instance.getClass()), componentTypes);
	}

	/**
	 * Get the clazz that represents the class of for the given name with the given componentTypes.
	 *
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(Class.forName(name), componentTypes);
	 * </pre>
	 *
	 * @param name           of the class to get the clazz that represents it
	 * @param componentTypes the components that can be held by the instances of this clazz
	 * @param <C>            the "klass" this clazz is holding
	 * @return a clazz represents the class for the given name with the given component types
	 * @throws ClassNotFoundException      if the class cannot be located
	 * @throws LinkageError                if the linkage fails
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails
	 * @throws NullPointerException        if the given 'name' or 'componentTypes' is null
	 */
	public static <C> Clazz<C> ofn(String name, Clazz... componentTypes) throws ClassNotFoundException {
		return of((Class) Class.forName(name), componentTypes);
	}

	/**
	 * Get the clazz that represents the class of for the given name with the given componentTypes.
	 *
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(family, Class.forName(name), componentTypes);
	 * </pre>
	 *
	 * @param family         how this clazz should be treated as
	 * @param name           of the class to get the clazz that represents it
	 * @param componentTypes the components that can be held by the instances of this clazz
	 * @param <C>            the "klass" this clazz is holding
	 * @return a clazz represents the class for the given name with the given component types
	 * @throws ClassNotFoundException      if the class cannot be located
	 * @throws LinkageError                if the linkage fails
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails
	 * @throws NullPointerException        if the given 'family' or 'name' or 'componentTypes' is null
	 */
	public static <C> Clazz<C> ofn(Class family, String name, Clazz... componentTypes) throws ClassNotFoundException {
		return of(family, (Class) Class.forName(name), componentTypes);
	}

	/**
	 * Get the clazz that represents the class of for the given name with the given componentTypes.
	 *
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(Class.forName(name, initialize, loader), componentTypes);
	 * </pre>
	 *
	 * @param loader         class loader from which the class must be loaded
	 * @param initialize     if true the class will be initialized. See Section 12.4 of The Java Language
	 * @param name           of the class to get the clazz that represents it
	 * @param componentTypes the components that can be held by the instances of this clazz
	 * @param <C>            the "klass" this clazz is holding
	 * @return a clazz represents the class for the given name with the given component types
	 * @throws ClassNotFoundException      if the class cannot be located
	 * @throws LinkageError                if the linkage fails
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails
	 * @throws NullPointerException        if the given 'loader' or 'name' or 'componentTypes' is null
	 * @throws SecurityException           if a security manager is present, and the loader is null, and the caller's class loader is not null, and
	 *                                     the caller does not have the RuntimePermission("getClassLoader")
	 */
	public static <C> Clazz<C> ofn(ClassLoader loader, boolean initialize, String name, Clazz... componentTypes) throws ClassNotFoundException {
		return of((Class) Class.forName(name, initialize, loader), componentTypes);
	}

	/**
	 * Get the clazz that represents the class of for the given name with the given componentTypes.
	 *
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(family, Class.forName(name, initialize, loader), componentTypes);
	 * </pre>
	 *
	 * @param loader         class loader from which the class must be loaded
	 * @param initialize     if true the class will be initialized. See Section 12.4 of The Java Language
	 * @param family         how this clazz should be treated as
	 * @param name           of the class to get the clazz that represents it
	 * @param componentTypes the components that can be held by the instances of this clazz
	 * @param <C>            the "klass" this clazz is holding
	 * @return a clazz represents the class for the given name with the given component types
	 * @throws ClassNotFoundException      if the class cannot be located
	 * @throws LinkageError                if the linkage fails
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails
	 * @throws NullPointerException        if the given 'loader' or 'family' or 'name' or 'componentTypes' is null
	 * @throws SecurityException           if a security manager is present, and the loader is null, and the caller's class loader is not null, and
	 *                                     the caller does not have the RuntimePermission("getClassLoader")
	 */
	public static <C> Clazz<C> ofn(ClassLoader loader, boolean initialize, Class family, String name, Clazz... componentTypes) throws ClassNotFoundException {
		return of(family, (Class) Class.forName(name, initialize, loader), componentTypes);
	}

	/**
	 * Get a clazz that is the same `class` and family as the given clazz. but different parameters.
	 *
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(klazz.getFamily(), klazz.getKlass(), componentTypes);
	 * </pre>
	 *
	 * @param klazz          to get the `class` from
	 * @param componentTypes the component types of the returned clazz
	 * @param <C>            the type of the returned clazz
	 * @return a clazz represents the given parameters
	 * @throws NullPointerException if the given 'klazz' or 'componentTypes' is null
	 */
	public static <C> Clazz<C> ofz(Clazz klazz, Clazz... componentTypes) {
		return of(klazz.family, klazz.klass, componentTypes);
	}

	/**
	 * Get a clazz that is the same `class` as the given clazz. but different parameters.
	 *
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(family, klazz.getKlass(), componentTypes);
	 * </pre>
	 *
	 * @param family         the family of the returned clazz
	 * @param klazz          to get the `class` from
	 * @param componentTypes the component types of the returned clazz
	 * @param <C>            the type of the returned clazz
	 * @return a clazz represents the given parameters
	 * @throws NullPointerException if the given 'family' or 'klazz' or 'componentTypes' is null
	 */
	public static <C> Clazz<C> ofz(Class family, Clazz klazz, Clazz... componentTypes) {
		return of(family, klazz.klass, componentTypes);
	}

	/**
	 * Get a clazz that is the same `class` as the given clazz. but different parameters.
	 *
	 * <pre>
	 *     This is a shortcut for:
	 *     Clazz.of(family, klazz.getKlass(), klazz.getComponentTypes());
	 * </pre>
	 *
	 * @param family the family of the returned clazz
	 * @param klazz  to get the `class` from
	 * @param <C>    the type of the returned clazz
	 * @return a clazz represents the given parameters
	 * @throws NullPointerException if the given 'family' or 'klazz' is null
	 */
	public static <C> Clazz<C> ofz(Class family, Clazz klazz) {
		return of(family, klazz.klass, klazz.componentTypes);
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
	private static <C> Clazz<C> ofa(Class family, Class<C> klass) {
		if (!klass.isArray())
			throw new IllegalArgumentException(klass + " is not a class for arrays");

		Class component = klass.getComponentType();
		Class componentFamily = family.isArray() ? family.getComponentType() : component;
		Clazz componentClazz = component.isArray() ? ofa(componentFamily, component) : of(componentFamily, component);

		return new Clazz<>(family, klass, componentClazz);
	}

	/**
	 * Get the clazz that represents the given array class.
	 *
	 * @param klass the array class to be represented by this clazz
	 * @param <C>   the "klass" this clazz is holding
	 * @return a clazz represents the given array class
	 * @throws NullPointerException if the given 'klass' is null
	 */
	private static <C> Clazz<C> ofa(Class<C> klass) {
		if (!klass.isArray())
			throw new IllegalArgumentException(klass + " is not a class for arrays");

		Class component = klass.getComponentType();
		Clazz componentClazz = component.isArray() ? ofa(component) : of(component);

		return new Clazz<>(klass, klass, componentClazz);
	}

	@Override
	public boolean equals(Object that) {
		return that == this ||
			   that instanceof Clazz &&
			   this.klass == ((Clazz) that).klass &&
			   this.family == ((Clazz) that).family &&
			   Objects.deepEquals(this.componentTypes, ((Clazz) that).componentTypes);
	}

	@Override
	public String toString() {
		return "clazz " + this.klass.getName() + " (" + this.family.getName() + ")";
	}

	/**
	 * Casts an object to the class or interface represented by this Clazz object.
	 *
	 * @param object the object to be cast
	 * @return the object after casting, or null if obj is null
	 * @throws ClassCastException if the object is not null and is not assignable to the type T.
	 */
	public C cast(Object object) {
		return this.klass.cast(object);
	}

	/**
	 * Returns the Class representing the component type of an array. If this clazz does not represent an array class this method returns null.
	 * <p>
	 * Note: don't get confused between this and {@link #getComponentType(int)}
	 *
	 * @return the Class representing the component type of this class if this class is an array
	 */
	public Class getComponentType() {
		return this.klass.getComponentType();
	}

	/**
	 * Get the i-th component type in this clazz. Or null if no type for the given index.
	 *
	 * @param i the index of the component targeted
	 * @return the i-th component type in this clazz, Or null if no type for the given index
	 * @throws IndexOutOfBoundsException if the given index is less than 0
	 */
	public Clazz getComponentType(int i) {
		return this.componentTypes.length > i ? this.componentTypes[i] : null;
	}

	/**
	 * Get all the component types in this clazz in an array.
	 *
	 * @return all the component types in this clazz
	 */
	public Clazz[] getComponentTypes() {
		Clazz[] clone = new Clazz[this.componentTypes.length];
		System.arraycopy(this.componentTypes, 0, clone, 0, this.componentTypes.length);

		return clone;
	}

	/**
	 * Get how many component types this clazz does have.
	 *
	 * @return the count of the component types this clazz does have
	 */
	public int getComponentsCount() {
		return this.componentTypes.length;
	}

	/**
	 * Returns a {@code Constructor} object that reflects the specified
	 * public constructor of the class represented by this {@code Clazz}
	 * object. The {@code parameterTypes} parameter is an array of
	 * {@code Class} objects that identify the constructor's formal
	 * parameter types, in declared order.
	 *
	 * If this {@code Clazz} object represents an inner class
	 * declared in a non-static context, the formal parameter types
	 * include the explicit enclosing instance as the first parameter.
	 *
	 * <p> The constructor to reflect is the public constructor of the class
	 * represented by this {@code Clazz} object whose formal parameter
	 * types match those specified by {@code parameterTypes}.
	 *
	 * @param parameterTypes the parameter array
	 * @return the {@code Constructor} object of the public constructor that
	 * matches the specified {@code parameterTypes}
	 * @throws NoSuchMethodException if a matching method is not found.
	 * @throws SecurityException     If a security manager, <i>s</i>, is present and
	 *                               the caller's class loader is not the same as or an
	 *                               ancestor of the class loader for the current class and
	 *                               invocation of {@link SecurityManager#checkPackageAccess
	 *                               s.checkPackageAccess()} denies access to the package
	 *                               of this class.
	 */
	public Constructor<C> getConstructor(Class<?>... parameterTypes) throws NoSuchMethodException {
		return this.klass.getConstructor(parameterTypes);
	}

	/**
	 * Returns an array containing {@code Constructor} objects reflecting
	 * all the public constructors of the class represented by this
	 * {@code Clazz} object.  An array of length 0 is returned if the
	 * class has no public constructors, or if the class is an array class, or
	 * if the class reflects a primitive type or void.
	 *
	 * Note that while this method returns an array of {@code
	 * Constructor<T>} objects (that is an array of constructors from
	 * this class), the return type of this method is {@code
	 * Constructor<?>[]} and <em>not</em> {@code Constructor<T>[]} as
	 * might be expected.  This less informative return type is
	 * necessary since after being returned from this method, the
	 * array could be modified to hold {@code Constructor} objects for
	 * different classes, which would violate the type guarantees of
	 * {@code Constructor<T>[]}.
	 *
	 * @return the array of {@code Constructor} objects representing the
	 * public constructors of this class
	 * @throws SecurityException If a security manager, <i>s</i>, is present and
	 *                           the caller's class loader is not the same as or an
	 *                           ancestor of the class loader for the current class and
	 *                           invocation of {@link SecurityManager#checkPackageAccess
	 *                           s.checkPackageAccess()} denies access to the package
	 *                           of this class.
	 */
	public Constructor<?>[] getConstructors() {
		return this.klass.getConstructors();
	}

	/**
	 * Returns a {@code Constructor} object that reflects the specified
	 * constructor of the class or interface represented by this
	 * {@code Clazz} object.  The {@code parameterTypes} parameter is
	 * an array of {@code Class} objects that identify the constructor's
	 * formal parameter types, in declared order.
	 *
	 * If this {@code Clazz} object represents an inner class
	 * declared in a non-static context, the formal parameter types
	 * include the explicit enclosing instance as the first parameter.
	 *
	 * @param parameterTypes the parameter array
	 * @return The {@code Constructor} object for the constructor with the
	 * specified parameter list
	 * @throws NoSuchMethodException if a matching method is not found.
	 * @throws SecurityException     If a security manager, <i>s</i>, is present and any of the
	 *                               following conditions is met:
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
	 */
	public Constructor<C> getDeclaredConstructor(Class<?> parameterTypes) throws NoSuchMethodException {
		return this.klass.getDeclaredConstructor(parameterTypes);
	}

	/**
	 * Returns an array of {@code Constructor} objects reflecting all the
	 * constructors declared by the class represented by this
	 * {@code Clazz} object. These are public, protected, default
	 * (package) access, and private constructors.  The elements in the array
	 * returned are not sorted and are not in any particular order.  If the
	 * class has a default constructor, it is included in the returned array.
	 * This method returns an array of length 0 if this {@code Clazz}
	 * object represents an interface, a primitive type, an array class, or
	 * void.
	 *
	 * <p> See <em>The Java Language Specification</em>, section 8.2.
	 *
	 * @return the array of {@code Constructor} objects representing all the
	 * declared constructors of this class
	 * @throws SecurityException If a security manager, <i>s</i>, is present and any of the
	 *                           following conditions is met:
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
	 */
	public Constructor<?>[] getDeclaredConstructors() {
		return this.klass.getDeclaredConstructors();
	}

	/**
	 * If this {@code Class} object represents a local or anonymous
	 * class within a constructor, returns a {@link
	 * java.lang.reflect.Constructor Constructor} object representing
	 * the immediately enclosing constructor of the underlying
	 * class. Returns {@code null} otherwise.  In particular, this
	 * method returns {@code null} if the underlying class is a local
	 * or anonymous class immediately enclosed by a type declaration,
	 * instance initializer or static initializer.
	 *
	 * @return the immediately enclosing constructor of the underlying class, if
	 * that class is a local or anonymous class; otherwise {@code null}.
	 * @throws SecurityException If a security manager, <i>s</i>, is present and any of the
	 *                           following conditions is met:
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
	 */
	public Constructor getEnclosingConstructor() {
		return this.klass.getEnclosingConstructor();
	}

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
	 * Returns a {@code Method} object that reflects the specified public
	 * member method of the class or interface represented by this
	 * {@code Clazz} object. The {@code name} parameter is a
	 * {@code String} specifying the simple name of the desired method. The
	 * {@code parameterTypes} parameter is an array of {@code Class}
	 * objects that identify the method's formal parameter types, in declared
	 * order. If {@code parameterTypes} is {@code null}, it is
	 * treated as if it were an empty array.
	 *
	 * <p> If the {@code name} is "{@code <init>}" or "{@code <clinit>}" a
	 * {@code NoSuchMethodException} is raised. Otherwise, the method to
	 * be reflected is determined by the algorithm that follows.  Let C be the
	 * class or interface represented by this object:
	 * <OL>
	 * <LI> C is searched for a <I>matching method</I>, as defined below. If a
	 * matching method is found, it is reflected.</LI>
	 * <LI> If no matching method is found by step 1 then:
	 * <OL TYPE="a">
	 * <LI> If C is a class other than {@code Object}, then this algorithm is
	 * invoked recursively on the superclass of C.</LI>
	 * <LI> If C is the class {@code Object}, or if C is an interface, then
	 * the superinterfaces of C (if any) are searched for a matching
	 * method. If any such method is found, it is reflected.</LI>
	 * </OL></LI>
	 * </OL>
	 *
	 * <p> To find a matching method in a class or interface C:&nbsp; If C
	 * declares exactly one public method with the specified name and exactly
	 * the same formal parameter types, that is the method reflected. If more
	 * than one such method is found in C, and one of these methods has a
	 * return type that is more specific than any of the others, that method is
	 * reflected; otherwise one of the methods is chosen arbitrarily.
	 *
	 * <p>Note that there may be more than one matching method in a
	 * class because while the Java language forbids a class to
	 * declare multiple methods with the same signature but different
	 * return types, the Java virtual machine does not.  This
	 * increased flexibility in the virtual machine can be used to
	 * implement various language features.  For example, covariant
	 * returns can be implemented with {@linkplain
	 * java.lang.reflect.Method#isBridge bridge methods}; the bridge
	 * method and the method being overridden would have the same
	 * signature but different return types.
	 *
	 * <p> If this {@code Clazz} object represents an array type, then this
	 * method does not find the {@code clone()} method.
	 *
	 * <p> Static methods declared in superinterfaces of the class or interface
	 * represented by this {@code Clazz} object are not considered members of
	 * the class or interface.
	 *
	 * @param name           the name of the method
	 * @param parameterTypes the list of parameters
	 * @return the {@code Method} object that matches the specified
	 * {@code name} and {@code parameterTypes}
	 * @throws NoSuchMethodException if a matching method is not found
	 *                               or if the name is "&lt;init&gt;"or "&lt;clinit&gt;".
	 * @throws NullPointerException  if {@code name} is {@code null}
	 * @throws SecurityException     If a security manager, <i>s</i>, is present and
	 *                               the caller's class loader is not the same as or an
	 *                               ancestor of the class loader for the current class and
	 *                               invocation of {@link SecurityManager#checkPackageAccess
	 *                               s.checkPackageAccess()} denies access to the package
	 *                               of this class.
	 */
	public Method getMethod(String name, Class<?>... parameterTypes) throws NoSuchMethodException {
		return this.klass.getMethod(name, parameterTypes);
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
	 * Determines if this Clazz object represents an array class.
	 *
	 * @return true if this clazz represents an array class; false otherwise.
	 */
	public boolean isArray() {
		return this.klass.isArray();
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
	 * Determine if the class represented by this clazz is a super class for the given class.
	 *
	 * @param klass the class object to be checked
	 * @return if this clazz is assignable from the given class
	 * @throws NullPointerException if the specified Class parameter is null.
	 */
	public boolean isAssignableFrom(Class klass) {
		return this.klass.isAssignableFrom(klass);
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
	 * Determines if the specified {@code Clazz} object represents a
	 * primitive type.
	 *
	 * <p> There are nine predefined {@code Class} objects to represent
	 * the eight primitive types and void.  These are created by the Java
	 * Virtual Machine, and have the same names as the primitive types that
	 * they represent, namely {@code boolean}, {@code byte},
	 * {@code char}, {@code short}, {@code int},
	 * {@code long}, {@code float}, and {@code double}.
	 *
	 * <p> These objects may only be accessed via the following public static
	 * final variables, and are the only {@code Class} objects for which
	 * this method returns {@code true}.
	 *
	 * @return true if and only if this class represents a primitive type
	 * @see java.lang.Boolean#TYPE
	 * @see java.lang.Character#TYPE
	 * @see java.lang.Byte#TYPE
	 * @see java.lang.Short#TYPE
	 * @see java.lang.Integer#TYPE
	 * @see java.lang.Long#TYPE
	 * @see java.lang.Float#TYPE
	 * @see java.lang.Double#TYPE
	 * @see java.lang.Void#TYPE
	 */
	public boolean isPrimitive() {
		return this.klass.isPrimitive();
	}

	/**
	 * Get the clazz that its class extends Object that represent the class of this clazz.
	 *
	 * @return a clazz its class extends object from the class of this clazz
	 */
	public Clazz toObjectClazz() {
		return Clazz.of(this.family, Reflection.asObjectClass(this.klass), this.componentTypes);
	}

	/**
	 * Get the clazz that its class don't extends Object from the class of this clazz.
	 *
	 * @return the non-object clazz of this clazz
	 * @throws IllegalArgumentException when this clazz don't have a primitive type
	 */
	public Clazz toPrimitiveClazz() {
		return Clazz.of(this.family, Reflection.asPrimitiveClass(this.klass), this.componentTypes);
	}
}
