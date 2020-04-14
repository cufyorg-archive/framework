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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * An alternative representation for {@link Class classes}. This provides more data about the targeted type. About the component types and the family
 * of that class. The family is how the class should be treated.
 *
 * @param <C> the "klass" this clazz is holding
 * @author lsafer
 * @version 0.1.3
 * @apiNote the class {@link Void} is representing null
 * @since 29-Mar-2020
 */
final public class Clazz<C> {
	/**
	 * The global clazzes. To not allow repeated clazzes for the same class.
	 */
	final private static Map<Class, Clazz> clazzes = new HashMap<>();
	/**
	 * The components specified to be held by the instance of this clazz.
	 *
	 * @implSpec unmodifiable
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
	 * @apiNote {@link Void} is the class for null
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
	 * @apiNote {@link Void} is the class for null
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
	 *
	 * @return the Class representing the component type of this class if this class is an array
	 * @apiNote don't confuse between this and {@link #getComponentType(int)}
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
	 * @apiNote null is instance of {@link Void}
	 */
	public boolean isInstance(Object instance) {
		return instance == null ? this.klass == Void.class : this.klass.isInstance(instance);
	}
}
