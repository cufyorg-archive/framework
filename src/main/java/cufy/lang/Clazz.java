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
 * @version 0.1.2
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
	 * Construct a new clazz with the given arguments.
	 *
	 * @param family         how this clazz should be treated as
	 * @param klass          the class to be represented by this clazz
	 * @param componentTypes the components that can be held by the instances of this clazz
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
	 * @param instance       to get the clazz that represents it
	 * @param componentTypes the components that can be held by the instances of this clazz
	 * @param <C>            the "klass" this clazz is holding
	 * @return a clazz represents the given class with the given component types
	 */
	public static <C> Clazz<C> of(C instance, Clazz... componentTypes) {
		return of((Class) (instance == null ? Void.class : instance.getClass()), componentTypes);
	}

	/**
	 * Get the clazz that represents the given instance with the given component types.
	 *
	 * @param family         how this clazz should be treated as
	 * @param instance       to get the clazz that represents it
	 * @param componentTypes the components that can be held by the instances of this clazz
	 * @param <C>            the "klass" this clazz is holding
	 * @return a clazz represents the given class with the given component types
	 */
	public static <C> Clazz<C> of(Class family, C instance, Clazz... componentTypes) {
		return of(family, (Class) (instance == null ? Void.class : instance.getClass()), componentTypes);
	}

	/**
	 * Get the clazz that represents the given array class.
	 *
	 * @param family how this clazz should be treated as
	 * @param klass  the array class to be represented by this clazz
	 * @param <C>    the "klass" this clazz is holding
	 * @return a clazz represents the given array class
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
	 */
	private static <C> Clazz<C> ofa(Class<C> klass) {
		if (!klass.isArray())
			throw new IllegalArgumentException(klass + " is not a class for arrays");

		Class component = klass.getComponentType();
		Clazz componentClazz = component.isArray() ? ofa(component) : of(component);

		return new Clazz<>(klass, klass, componentClazz);
	}

	@Override
	public String toString() {
		return this.klass.getName() + " (" + this.family.getName() + ")";
	}

	/**
	 * Get how many component types this clazz does have.
	 *
	 * @return the count of the component types this clazz does have
	 */
	public int getComponentCount() {
		return this.componentTypes.length;
	}

	/**
	 * Get the i-th component type in this clazz.
	 *
	 * @param i the index of the component targeted
	 * @return the i-th component type in this clazz
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
}
