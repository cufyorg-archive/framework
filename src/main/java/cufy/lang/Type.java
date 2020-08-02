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

import cufy.util.Arrays;
import cufy.util.Objects;
import cufy.util.Reflection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An alternative representation for {@link Class classes}. This provides more data about the targeted type. About the components
 * and the wildclass of that class. The wildclass is how the class should be treated. The componentType is a component of types
 * for each specific object in an instance this type is representing.
 * <br>
 * Note: the class {@link Void} is the class of null.
 *
 * @param <T> the type of the class represented by this type.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.0 ~2020.03.29
 */
public final class Type<T> implements java.lang.reflect.Type, Serializable {
	/**
	 * The pattern to validate a {@link #getName() name} of a {@link Type}.
	 * <pre>
	 *     Use the group {@code "TYPE"} to extract the {@link #typeclass}.
	 *     Use the group {@code "WILD"} to extract the {@link #wildclass}.
	 *     Use the group {@link "COMPONENTS"} to extract the {@link #components}.
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	private static final Pattern PATTERN = Pattern.compile("^(?<TYPE>[^,:<>]+)(?::(?<WILD>[^,:<>]+))?(?:<(?<COMPONENTS>(?:[^,:<>]+(?::[^,:<>]+)?(?:<(?:[^:,<>]+(?::[^:,<>]+)?(?:<(?:[^,<>]*(?:<.*>)?,?)*>)?(?:,(?=\\s*[^>]))?)*>)?(?:,(?=\\s*\\S))?)*)>)?\\s*$");
	/**
	 * A pattern to validate {@link #getName() names} of {@link Component}s.
	 *
	 * @since 0.1.5
	 */
	private static final Pattern PATTERNS = Pattern.compile("^(?:[^,:<>]+(?::[^,:<>]+)?(?:<(?:[^:,<>]+(?::[^:,<>]+)?(?:<(?:[^,<>]*(?:<.*>)?,?)*>)?(?:,(?=\\s*[^>]))?)*>)?(?:,(?=\\s*\\S))?)*$");
	/**
	 * A pattern to split {@link #getName() names} of {@link Component}s.
	 *
	 * @since 0.1.5
	 */
	private static final Pattern SPLIT = Pattern.compile("[^:,<>]+(?::[^:,<>]+)?(?:<(?:[^,<>]*(?:<.*>)?,?)*>)?(?=,(?=\\s*[^>]))?");

	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = -4030819563298464225L;

	/**
	 * A component of the types specified foreach component to be held by an instance of this type. This field should be treated
	 * as final field.
	 *
	 * @since 0.1.5
	 */
	private Component[] components;
	/**
	 * The class represented by this type. This field should be treated as final field.
	 *
	 * @since 0.1.5
	 */
	private Class<T> typeclass;
	/**
	 * The class that an instance of this type should be treated as if it was an instance of it. This field should be treated as
	 * final field.
	 *
	 * @since 0.1.5
	 */
	private Class wildclass;

	/**
	 * Construct a new type that represents the given {@code typeclass}, and have the given {@code components}, and should be
	 * treated as if it was the given {@code wildclass}.
	 *
	 * @param typeclass  the class to be represented by the constructed type.
	 * @param wildclass  the class that an instance of the constructed type should be treated as if it was an instance of it.
	 * @param components an array of components of the types specified foreach component to be held by an instance of the
	 *                   constructed type.
	 * @throws NullPointerException if the given {@code typeclass} or {@code wildclass} or {@code components} or any of its
	 *                              elements is null.
	 * @since 0.1.5
	 */
	private Type(Class<T> typeclass, Class wildclass, Component[] components) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(wildclass, "wildclass");
		Objects.requireNonNull(components, "components");
		this.typeclass = typeclass;
		this.wildclass = wildclass;
		this.components = components.clone();
	}

	/**
	 * Get a new empty array of types.
	 *
	 * @return a new empty array of types.
	 * @since 0.1.5
	 */
	public static Type[] array() {
		return new Type[0];
	}

	/**
	 * Create a new array of types from the given {@code typeclasses}.
	 *
	 * @param typeclasses the source array.
	 * @return a new array of types from the given {@code typeclasses}. Or null if the given {@code typeclasses} is null.
	 * @since 0.1.5
	 */
	public static Type2[] array(Class... typeclasses) {
		if (typeclasses == null)
			return null;

		Type2[] types = new Type2[typeclasses.length];

		for (int i = 0; i < types.length; i++) {
			Class typeclass = typeclasses[i];

			if (typeclass != null)
				types[i] = Type2.of(typeclass);
		}

		return types;
	}

	/**
	 * Get a Type object associated with the type with the given {@code name}.
	 *
	 * @param name the fully qualified name of the desired type.
	 * @return a Type object for the type with the given {@code name}.
	 * @throws LinkageError                if the linkage failed.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws ClassNotFoundException      if no type can be associated to the given {@code name}.
	 * @throws NullPointerException        if the given {@code name} is null.
	 * @see Class#forName(String)
	 * @since 0.1.5
	 */
	public static Type<?> forName(String name) throws ClassNotFoundException {
		Objects.requireNonNull(name, "name");
		Matcher matcher = Type.PATTERN.matcher(name);

		if (!matcher.find())
			throw new ClassNotFoundException(name);

		String typeString = matcher.group("TYPE");
		String wildString = matcher.group("WILD");
		String componentsString = matcher.group("COMPONENTS");

		Class typeclass = Class.forName(typeString.trim());
		Class wildclass = wildString == null ? typeclass : Class.forName(wildString.trim());
		Type[] components = componentsString == null ? new Type[0] : Type.forNames(componentsString);

		return Type.of(typeclass, wildclass, components);
	}

	/**
	 * Get a Type object associated with the type with the given {@code name}, Using the given {@code loader}.
	 *
	 * @param name       the fully qualified name of the desired component.
	 * @param initialize if {@code true} the any non-initialized class will be initialized.
	 * @param loader     class loader from which the classes must be loaded.
	 * @return a Type object for the type with the given {@code name}.
	 * @throws LinkageError                if the linkage failed.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws ClassNotFoundException      if no type can be associated to the given {@code name}.
	 * @throws NullPointerException        if the given {@code name} or {@code loader} is null.
	 * @see Class#forName(String, boolean, ClassLoader)
	 * @since 0.1.5
	 */
	public static Type<?> forName(String name, boolean initialize, ClassLoader loader) throws ClassNotFoundException {
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(loader, "loader");
		Matcher matcher = Type.PATTERN.matcher(name);

		if (!matcher.find())
			throw new ClassNotFoundException(name);

		String typeString = matcher.group("TYPE");
		String wildString = matcher.group("WILD");
		String componentsString = matcher.group("COMPONENTS");

		Class typeclass = Class.forName(typeString.trim(), initialize, loader);
		Class wildclass = wildString == null ? typeclass : Class.forName(wildString.trim(), initialize, loader);
		Type[] components = componentsString == null ? new Type[0] : Type.forNames(componentsString, initialize, loader);

		return Type.of(typeclass, wildclass, components);
	}

	/**
	 * Get a Types array associated with the types with the given {@code names}.
	 *
	 * @param names the fully qualified names of the desired types array.
	 * @return a Types array for the types with the given {@code name}.
	 * @throws LinkageError                if the linkage failed.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws ClassNotFoundException      if no types can be associated to the given {@code name}.
	 * @throws NullPointerException        if the given {@code name} is null.
	 * @since 0.1.5
	 */
	public static Type[] forNames(String names) throws ClassNotFoundException {
		Objects.requireNonNull(names, "names");

		if (!Type.PATTERNS.matcher(names).matches())
			throw new ClassNotFoundException(names);

		List<Type> list = new ArrayList();

		Matcher matcher = Type.SPLIT.matcher(names.trim());
		while (matcher.find()) {
			String componentName = matcher.group().trim();
			list.add("?".equals(componentName) ? null : Type.forName(componentName));
		}

		return list.toArray(new Type[0]);
	}

	/**
	 * Get a Types array associated with the types with the given {@code names}. Using the given {@code loader}.
	 *
	 * @param names      the fully qualified names of the desired types array.
	 * @param initialize if {@code true} the any non-initialized class will be initialized.
	 * @param loader     class loader from which the classes must be loaded.
	 * @return a Types array for the types with the given {@code name}.
	 * @throws LinkageError                if the linkage failed.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws ClassNotFoundException      if no types can be associated to the given {@code name}.
	 * @throws NullPointerException        if the given {@code name} or {@code loader} is null.
	 * @since 0.1.5
	 */
	public static Type[] forNames(String names, boolean initialize, ClassLoader loader) throws ClassNotFoundException {
		Objects.requireNonNull(names, "names");
		Objects.requireNonNull(loader, "loader");

		if (!Type.PATTERNS.matcher(names).matches())
			throw new ClassNotFoundException(names);

		List<Type> list = new ArrayList();

		Matcher matcher = Type.SPLIT.matcher(names.trim());
		while (matcher.find()) {
			String componentName = matcher.group().trim();
			list.add("?".equals(componentName) ? null : Type.forName(componentName, initialize, loader));
		}

		return list.toArray(new Type[0]);
	}

	/**
	 * Get a type that represents the class of the given {@code instance}, and with the given {@code components}.
	 * <pre>
	 *     Type.of(<font color="a5edff">INSTANCE</font>, <font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">TYPE</font>&lt;<font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>&gt;
	 * </pre>
	 *
	 * @param instance   an instance that its class to be represented by the returned type, and its elements is the source of its
	 *                   components.
	 * @param components an array of components of the types specified foreach component to be held by an instance of the
	 *                   constructed type.
	 * @param <T>        the type of the class represented by the returned type.
	 * @return a type that represents the class of the given {@code instance}, and with the given {@code components}.
	 * @throws NullPointerException if the given {@code componentTypes} is null.
	 * @since 0.1.5
	 */
	public static <T> Type<T> from(T instance, Component... components) {
		Class typeclass = instance == null ? Void.class : instance.getClass();
		return Type.of(typeclass, components);
	}

	/**
	 * Get a type that represents the class of the given {@code instance}, and should be treated as if it was the given {@code
	 * wildclass}, and with the given {@code components}.
	 * <br>
	 * <pre>
	 *     Type.of(<font color="a5edff">INSTANCE</font>, <font color="fc8fbb">WILD</font>, <font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">TYPE</font>:<font color="fc8fbb">WILD</font>&lt;<font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>&gt;
	 * </pre>
	 *
	 * @param instance   an instance that its class to be represented by the returned type, and its elements is the source of its
	 *                   components.
	 * @param wildclass  the class that an instance of the returned type should be treated as if it was an instance of it.
	 * @param components an array of components of the types specified foreach component to be held by an instance of the
	 *                   constructed type.
	 * @param <T>        the type of the class represented by the returned type.
	 * @return a type that represents the class of the given {@code instance}, and should be treated as if it was the given {@code
	 * 		wildclass}, and with the given {@code components}.
	 * @throws NullPointerException if the given {@code wildclass} or {@code componentTypes} is null.
	 * @since 0.1.5
	 */
	public static <T> Type<T> from(T instance, Class wildclass, Component... components) {
		Class typeclass = instance == null ? Void.class : instance.getClass();
		return Type.of(typeclass, wildclass, components);
	}

	/**
	 * Get a type that represent the class {@link Object}, and have no components.
	 * <pre>
	 *     Type.of()
	 *     <font color="a5edff">Object</font>
	 * </pre>
	 *
	 * @return a type that represents the class {@link Object}, and have no components.
	 * @since 0.1.5
	 */
	public static Type<Object> of() {
		return new Type(Object.class, Object.class, new Component[0]);
	}

	/**
	 * Get a type that represents the class {@link Object}, and have the given {@code components}.
	 * <pre>
	 *     Type.of(<font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">Object</font>&lt;<font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>&gt;
	 * </pre>
	 *
	 * @param components the components of the returned type.
	 * @return a type that represents the class {@link Object}, and have the given {@code components}.
	 * @throws NullPointerException if the given {@code components} is null.
	 * @since 0.1.5
	 */
	public static Type<Object> of(Component... components) {
		Objects.requireNonNull(components, "components");
		return new Type(Object.class, Object.class, components);
	}

	/**
	 * Get a type that represents the class {@link Object}, and have the given {@code components}.
	 * <pre>
	 *     Type.of(<font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">Object</font>&lt;<font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>&gt;
	 * </pre>
	 *
	 * @param components the components of the returned type.
	 * @return a type that represents the class {@link Object}, and have the given {@code components}.
	 * @throws NullPointerException if the given {@code components} is null.
	 * @since 0.1.5
	 */
	public static Type<Object> of(Type... components) {
		Objects.requireNonNull(components, "components");
		return new Type(Object.class, Object.class, Component.array(components));
	}

	/**
	 * Get a type that represents the class {@link Object}, and have the given {@code components}.
	 * <pre>
	 *     Type.of(<font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">Object</font>&lt;<font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>&gt;
	 * </pre>
	 *
	 * @param components the components of the returned type.
	 * @return a type that represents the class {@link Object}, and have the given {@code components}.
	 * @throws NullPointerException if the given {@code components} is null.
	 * @since 0.1.5
	 */
	public static Type<Object> of(Class[] components) {
		Objects.requireNonNull(components, "components");
		return new Type(Object.class, Object.class, Component.array(components));
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have no components.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>)
	 *     <font color="a5edff">TYPE</font>
	 * </pre>
	 *
	 * @param typeclass the class to be represented by the returned type.
	 * @param <T>       the type of the class represented by the returned type.
	 * @return a type that represents the given {@code typeclass}, and have no components.
	 * @throws NullPointerException if the given {@code typeclass} is null.
	 * @since 0.1.5
	 */
	public static <T> Type<T> of(Class<T> typeclass) {
		Objects.requireNonNull(typeclass, "typeclass");
		return new Type(typeclass, typeclass, new Component[0]);
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have the given {@code components}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">TYPE</font>&lt;<font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>&gt;
	 * </pre>
	 *
	 * @param typeclass  the class to be represented by the returned type.
	 * @param components the components of the returned type.
	 * @param <T>        the type of the class represented by the returned type.
	 * @return a type that represents the given {@code typeclass}, and have the given {@code components}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code components} is null.
	 * @since 0.1.5
	 */
	public static <T> Type<T> of(Class<T> typeclass, Component... components) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(components, "components");
		return new Type(typeclass, typeclass, components);
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have the given {@code components}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">TYPE</font>&lt;<font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>&gt;
	 * </pre>
	 *
	 * @param typeclass  the class to be represented by the returned type.
	 * @param components the components of the returned type.
	 * @param <T>        the type of the class represented by the returned type.
	 * @return a type that represents the given {@code typeclass}, and have the given {@code components}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code components} is null.
	 * @since 0.1.5
	 */
	public static <T> Type<T> of(Class<T> typeclass, Type... components) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(components, "components");
		return new Type(typeclass, typeclass, Component.array(components));
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have the given {@code components}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">TYPE</font>&lt;<font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>&gt;
	 * </pre>
	 *
	 * @param typeclass  the class to be represented by the returned type.
	 * @param components the components of the returned type.
	 * @param <T>        the type of the class represented by the returned type.
	 * @return a type that represents the given {@code typeclass}, and have the given {@code components}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code components} is null.
	 * @since 0.1.5
	 */
	public static <T> Type<T> of(Class<T> typeclass, Class[] components) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(components, "components");
		return new Type(typeclass, typeclass, Component.array(components));
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have no components, and should be treated as if it was the
	 * given {@code wildclass}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="fc8fbb">WILD</font>)
	 *     <font color="a5edff">TYPE</font>:<font color="fc8fbb">WILD</font>
	 * </pre>
	 *
	 * @param typeclass the class to be represented by the returned type.
	 * @param wildclass the class that an instance of the returned type should be treated as if it was an instance of it.
	 * @param <T>       the type of the class represented by the returned type.
	 * @return a new type that represents the given {@code typeclass}, and have no components, and should be treated as if it was
	 * 		the given {@code wildclass}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code wildclass} is null.
	 * @since 0.1.5
	 */
	public static <T> Type<T> of(Class<T> typeclass, Class wildclass) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(wildclass, "wildclass");
		return new Type(typeclass, wildclass, new Component[0]);
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have the given {@code components}, and should be treated as if
	 * it was the given {@code wildclass}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="fc8fbb">WILD</font>, <font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">TYPE</font>:<font color="fc8fbb">WILD</font>&lt;<font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>&gt;
	 * </pre>
	 *
	 * @param typeclass  the class to be represented by the returned type.
	 * @param wildclass  the class that an instance of the returned type should be treated as if it was an instance of it.
	 * @param components the components of the returned type.
	 * @param <T>        the type of the class represented by the returned type.
	 * @return a new type that represents the given {@code typeclass}, and have the given {@code components}, and should be
	 * 		treated as if it was the given {@code wildclass}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code wildclass} or {@code components} is null.
	 * @since 0.1.5
	 */
	public static <T> Type<T> of(Class<T> typeclass, Class wildclass, Component... components) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(wildclass, "wildclass");
		Objects.requireNonNull(components, "component");
		return new Type(typeclass, wildclass, components);
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have the given {@code components}, and should be treated as if
	 * it was the given {@code wildclass}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="fc8fbb">WILD</font>, <font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">TYPE</font>:<font color="fc8fbb">WILD</font>&lt;<font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>&gt;
	 * </pre>
	 *
	 * @param typeclass  the class to be represented by the returned type.
	 * @param wildclass  the class that an instance of the returned type should be treated as if it was an instance of it.
	 * @param components the components of the returned type.
	 * @param <T>        the type of the class represented by the returned type.
	 * @return a new type that represents the given {@code typeclass}, and have the given {@code components}, and should be
	 * 		treated as if it was the given {@code wildclass}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code wildclass} or {@code components} is null.
	 * @since 0.1.5
	 */
	public static <T> Type<T> of(Class<T> typeclass, Class wildclass, Type... components) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(wildclass, "wildclass");
		Objects.requireNonNull(components, "component");
		return new Type(typeclass, wildclass, Component.array(components));
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have the given {@code components}, and should be treated as if
	 * it was the given {@code wildclass}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="fc8fbb">WILD</font>, <font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">TYPE</font>:<font color="fc8fbb">WILD</font>&lt;<font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>&gt;
	 * </pre>
	 *
	 * @param typeclass  the class to be represented by the returned type.
	 * @param wildclass  the class that an instance of the returned type should be treated as if it was an instance of it.
	 * @param components the components of the returned type.
	 * @param <T>        the type of the class represented by the returned type.
	 * @return a new type that represents the given {@code typeclass}, and have the given {@code components}, and should be
	 * 		treated as if it was the given {@code wildclass}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code wildclass} or {@code components} is null.
	 * @since 0.1.5
	 */
	public static <T> Type<T> of(Class<T> typeclass, Class wildclass, Class... components) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(wildclass, "wildclass");
		Objects.requireNonNull(components, "component");
		return new Type(typeclass, wildclass, Component.array(components));
	}

	/**
	 * Get a type that represents the {@code typeclass} of the given {@code typeclassSrc}, and have the {@code components} of the
	 * given {@code componentsSrc}, and should be treated as if it was the {@code wildclass} of the given {@code wildclassSrc}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE_SRC</font>, <font color="fc8fbb">WILD_SRC</font>, <font color="d3c4ff">COMPONENTS_SRC</font>)
	 *     <font color="a5edff">TYPE</font>:<font color="fc8fbb">WILD</font>&lt;<font color="d3c4ff">COMPONENTS…</font>&gt;
	 * </pre>
	 *
	 * @param typeclassSrc  the source of the class to be represented by the returned type.
	 * @param wildclassSrc  the source of the class that an instance of the returned type should be treated as if it was an
	 *                      instance of it.
	 * @param componentsSrc the source of the array of components of the returned type.
	 * @param <T>           the type of the class represented by the returned type.
	 * @return a type that represents the {@code typeclass} of the given {@code typeclassSrc}, and have the {@code component} of
	 * 		the given {@code componentsSrc}, and should be treated as if it was the {@code wildclass} of the given {@code
	 * 		wildclassSrc}.
	 * @throws NullPointerException if the given {@code wildclassSrc} or {@code typeclassSrc} or {@code componentsSrc} is null.
	 * @since 0.1.5
	 */
	public static <T> Type<T> of(Type<T> typeclassSrc, Type wildclassSrc, Type componentsSrc) {
		Objects.requireNonNull(typeclassSrc, "typeclassSrc");
		Objects.requireNonNull(wildclassSrc, "wildclassSrc");
		Objects.requireNonNull(componentsSrc, "componentsSrc");
		return Type.of(typeclassSrc.typeclass, wildclassSrc.wildclass, componentsSrc.components);
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj ||
			   obj instanceof Type &&
			   ((Type) obj).typeclass == this.typeclass &&
			   ((Type) obj).wildclass == this.wildclass &&
			   Arrays.equals(((Type) obj).components, this.components);
	}

	@Override
	public String getTypeName() {
		//TYPE:WILD<COMPONENTS>
		StringBuilder typeName = new StringBuilder(this.typeclass.getTypeName());

		if (this.wildclass != this.typeclass)
			typeName.append(":").append(this.wildclass.getTypeName());

		if (this.components.length != 0) {
			typeName.append("<");

			Iterator<Component> iterator = Arrays.iterator(this.components);
			while (iterator.hasNext()) {
				Component component = iterator.next();

				if (component == null)
					typeName.append("?");
				else
					typeName.append(component.getTypeName());

				if (iterator.hasNext())
					typeName.append(", ");
			}

			typeName.append(">");
		}

		return typeName.toString();
	}

	@Override
	public int hashCode() {
		return this.wildclass.hashCode() ^ this.typeclass.hashCode() ^ Arrays.hashCode(this.components);
	}

	@Override
	public String toString() {
		return "type " + this.getName();
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getTypeclass() getTypeclass()}{@link Class#cast(Object) .cast(instance)}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public T cast(Object instance) {
		//null is safe ;)
		return this.typeclass.cast(instance);
	}

	/**
	 * Get the component of the types specified foreach component to be held by an instance of this type that have the given
	 * {@code component} index.
	 * <br>
	 * Note: the returned component will always be unmodifiable. Trying to modify it will always throw an exception.
	 *
	 * @param component the index of the returned component at this type.
	 * @return the component of the types specified foreach component to be held by an instance of this type. Or null if no
	 * 		component have the given position.
	 * @since 0.1.5
	 */
	public Component getComponent(int component) {
		return component >= 0 && component < this.components.length ?
			   this.components[component] :
			   null;
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getTypeclass() getTypeclass()}{@link Class#getComponentType() .getComponentType()}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public Class getComponentType() {
		return this.typeclass.getComponentType();
	}

	/**
	 * Get the general type for all components in the specified {@code component} to be held by an instance of this type.
	 * <br>
	 * Note: This is like calling {@link Type#getComponentType(int, Object) getComponentType(}{@link Component#componentType
	 * Type.COMPONENT_TYPE)}.
	 *
	 * @param component the index of the component in this type that have the returned component type.
	 * @return the general type for all components in the specified {@code component} to be held by an instance of this type. Or
	 * 		null if no component have the given position, or that component does not have a general component type.
	 * @since 0.1.5
	 */
	public Type getComponentType(int component) {
		return component >= 0 && component < this.components.length &&
			   this.components[component] != null ?
			   this.components[component].getComponentType() :
			   null;
	}

	/**
	 * Get the component type of this type for the given {@code object} in the specified {@code component}.
	 *
	 * @param component the index of the component in this type that have the returned component type.
	 * @param object    the object associated to the returned type as a component in this type.
	 * @return the component type of this type for the given {@code object} in the specified {@code component}. Or null if no
	 * 		component have the given position, or that component does not have a type associated to the given {@code object} in it.
	 * @since 0.1.5
	 */
	public Type getComponentType(int component, Object object) {
		return component >= 0 && component < this.components.length &&
			   this.components[component] != null ?
			   this.components[component].getComponentType(object) :
			   null;
	}

	/**
	 * Get a clone of the array holding all the components of this type.
	 * <br>
	 * Note: all the returned components will always be unmodifiable. Trying to modify them will always throw an exception.
	 *
	 * @return a clone of the array holding all the components of this type.
	 * @since 0.1.5
	 */
	public Component[] getComponents() {
		return this.components.clone();
	}

	/**
	 * Get the name of this type. The name contains the name of the {@code wildclass}, {@code typeclass}, and each {@code
	 * componentType} of each {@code component} in this type.
	 *
	 * @return the name of this type.
	 * @since 0.1.5
	 */
	public String getName() {
		//TYPE:WILD<COMPONENTS>
		StringBuilder name = new StringBuilder(this.typeclass.getName());

		if (this.wildclass != this.typeclass)
			name.append(":").append(this.wildclass.getName());

		if (this.components.length != 0) {
			name.append("<");

			Iterator<Component> iterator = Arrays.iterator(this.components);
			while (iterator.hasNext()) {
				Component component = iterator.next();

				if (component == null)
					name.append("?");
				else
					name.append(component.getName());

				if (iterator.hasNext())
					name.append(", ");
			}

			name.append(">");
		}

		return name.toString();
	}

	/**
	 * Get the simple name of this type. The name contains the simple name of the {@code wildclass}, {@code typeclass}, and each
	 * {@code componentType} of each {@code component} in this type.
	 *
	 * @return the simple name of this type.
	 * @since 0.1.5
	 */
	public String getSimpleName() {
		//TYPE:WILD<COMPONENTS>
		StringBuilder simpleName = new StringBuilder(this.typeclass.getSimpleName());

		if (this.wildclass != this.typeclass)
			simpleName.append(":").append(this.wildclass.getSimpleName());

		if (this.components.length != 0) {
			simpleName.append("<");

			Iterator<Component> iterator = Arrays.iterator(this.components);
			while (iterator.hasNext()) {
				Component component = iterator.next();

				if (component == null)
					simpleName.append("?");
				else
					simpleName.append(component.getSimpleName());

				if (iterator.hasNext())
					simpleName.append(", ");
			}

			simpleName.append(">");
		}

		return simpleName.toString();
	}

	/**
	 * Get the class represented by this type.
	 *
	 * @return the class represented by this type.
	 * @since 0.1.5
	 */
	public Class<T> getTypeclass() {
		return this.typeclass;
	}

	/**
	 * Get the class that an instance of this type should be treated as if it was an instance of it.
	 *
	 * @return the class that an instance of this type should be treated as if it was an instance of it.
	 * @since 0.1.5
	 */
	public Class getWildclass() {
		return this.wildclass;
	}

	/**
	 * Determine if the {@code typeclass} of this type has a primitive type.
	 *
	 * @return true, if the {@code typeclass} of this type has a primitive type.
	 * @since 0.1.5
	 */
	public boolean hasPrimitive() {
		return Reflection.hasPrimitiveClass(this.typeclass);
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getTypeclass() getTypeclass()}{@link Class#isAnnotation() .isAnnotation()}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public boolean isAnnotation() {
		return this.typeclass.isAnnotation();
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getTypeclass() getTypeclass()}{@link Class#isArray() .isArray()}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public boolean isArray() {
		return this.typeclass.isArray();
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getTypeclass() getTypeclass()}{@link Class#isAssignableFrom(Class) .isAssignableFrom(typeclass)}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public boolean isAssignableFrom(Class typeclass) {
		return this.typeclass.isAssignableFrom(typeclass);
	}

	/**
	 * Determine if the {@code typeclass} of this type is assignable from the {@code typeclass} of the given type.
	 *
	 * @param type the type to be checked if its {@code typeclass} is assignable from the {@code typeclass} of this type.
	 * @return true, if the {@code typeclass} of this type is assignable from the {@code typeclass} of the given type.
	 * @throws NullPointerException if the given {@code type} is null.
	 * @see Class#isAssignableFrom(Class)
	 * @since 0.1.5
	 */
	public boolean isAssignableFrom(Type type) {
		Objects.requireNonNull(type, "type");
		return this.typeclass.isAssignableFrom(type.typeclass);
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getTypeclass() getTypeclass()}{@link Class#isEnum() .isEnum()}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public boolean isEnum() {
		return this.typeclass.isEnum();
	}

	/**
	 * Determine if the given {@code instance} is an instance of the {@code typeclass} of this type.
	 * <br>
	 * Note: null is instance of {@link Void}.
	 *
	 * @param instance the instance to be checked if it is an instance of the {@code typeclass} of this type.
	 * @return true, if the given {@code instance} is an instance of the {@code typeclass} of this type.
	 * @since 0.1.5
	 */
	public boolean isInstance(Object instance) {
		return instance == null && this.typeclass == Void.class || this.typeclass.isInstance(instance);
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getTypeclass() getTypeclass()}{@link Class#isInterface() .isInterface()}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public boolean isInterface() {
		return this.typeclass.isInterface();
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getTypeclass() getTypeclass()}{@link Class#isPrimitive() .isPrimitive()}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public boolean isPrimitive() {
		return this.typeclass.isPrimitive();
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getTypeclass() getTypeclass()}{@link Class#isSynthetic() .isSynthetic()}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public boolean isSynthetic() {
		return this.typeclass.isSynthetic();
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getTypeclass() getTypeclass()}{@link Class#newInstance() .newInstance()}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public T newInstance() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		return this.typeclass.getConstructor().newInstance();
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getTypeclass() getTypeclass()}{@link Class#getConstructor(Class[]) .getConstructor(AUTO)}{@link Class#newInstance() .newInstance(parameters)}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public T newInstance(Object... parameters) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		for0:
		for (Constructor<T> constructor : (Constructor<T>[]) this.typeclass.getDeclaredConstructors()) {
			Class[] parameterTypes = constructor.getParameterTypes();
			for (int i = 0; i < parameterTypes.length; i++)
				if (!parameterTypes[i].isInstance(parameters[i]))
					continue for0;

			return constructor.newInstance(parameters);
		}

		throw new NoSuchMethodException("No constructor found for the given parameters");
	}

	/**
	 * Get this type.
	 *
	 * @return this type.
	 * @since 0.1.5
	 */
	public Type<T> with() {
		return this;
	}

	/**
	 * Get a type that represents the {@code typeclass} of this type, and have the given {@code components}, and should be treated
	 * as if it was the {@code wildclass} of this type.
	 *
	 * @param components the components of the types specified foreach component to be held by an instance of the returned type.
	 * @return a type that represents the {@code typeclass} of this type, and have the given {@code components}, and should be
	 * 		treated as if it was the {@code wildclass} of this type.
	 * @throws NullPointerException if the given {@code component} is null.
	 * @since 0.1.5
	 */
	public Type<T> with(Component... components) {
		Objects.requireNonNull(components, "components");
		return Type.of(this.typeclass, this.wildclass, components);
	}

	/**
	 * Get a type that represents the {@code typeclass} of this type, and have the given {@code components}, and should be treated
	 * as if it was the {@code wildclass} of this type.
	 *
	 * @param components the components of the types specified foreach component to be held by an instance of the returned type.
	 * @return a type that represents the {@code typeclass} of this type, and have the given {@code components}, and should be
	 * 		treated as if it was the {@code wildclass} of this type.
	 * @throws NullPointerException if the given {@code component} is null.
	 * @since 0.1.5
	 */
	public Type<T> with(Type... components) {
		Objects.requireNonNull(components, "components");
		return Type.of(this.typeclass, this.wildclass, components);
	}

	/**
	 * Get a type that represents the {@code typeclass} of this type, and have the given {@code components}, and should be treated
	 * as if it was the {@code wildclass} of this type.
	 *
	 * @param components the components of the types specified foreach component to be held by an instance of the returned type.
	 * @return a type that represents the {@code typeclass} of this type, and have the given {@code components}, and should be
	 * 		treated as if it was the {@code wildclass} of this type.
	 * @throws NullPointerException if the given {@code component} is null.
	 * @since 0.1.5
	 */
	public Type<T> with(Class[] components) {
		Objects.requireNonNull(components, "components");
		return Type.of(this.typeclass, this.wildclass, components);
	}

	/**
	 * Get a type that represents the {@code typeclass} of this type, and have the {@code component} of this type, and should be
	 * treated as if it was the given {@code wildclass}.
	 *
	 * @param wildclass the class that an instance of the returned type should be treated as if it was an instance of it.
	 * @return a type that represents the {@code typeclass} of this type, and have the {@code component} of this type, and should
	 * 		be treated as if it was the given {@code wildclass}.
	 * @throws NullPointerException if the given {@code wildclass} is null.
	 * @since 0.1.5
	 */
	public Type<T> with(Class wildclass) {
		Objects.requireNonNull(wildclass, "wildclass");
		return Type.of(wildclass, this.typeclass, this.components);
	}

	/**
	 * Get a type that represents the {@code typeclass} of this type, and have the given {@code component}, and should be treated
	 * as if it was the given {@code wildclass}.
	 *
	 * @param wildclass  the class that an instance of the returned type should be treated as if it was an instance of it.
	 * @param components an array of components of the types specified foreach component to be held by an instance of the
	 *                   constructed type.
	 * @return a type that represents the {@code typeclass} of this type, and have the given {@code component}, and should be
	 * 		treated as if it was the given {@code wildclass}.
	 * @throws NullPointerException if the given {@code wildclass} or {@code component} is null.
	 * @since 0.1.5
	 */
	public Type<T> with(Class wildclass, Component... components) {
		Objects.requireNonNull(components, "components");
		Objects.requireNonNull(wildclass, "wildclass");
		return Type.of(this.typeclass, wildclass, components);
	}

	/**
	 * Get a type that represents the {@code typeclass} of this type, and have the given {@code component}, and should be treated
	 * as if it was the given {@code wildclass}.
	 *
	 * @param wildclass  the class that an instance of the returned type should be treated as if it was an instance of it.
	 * @param components an array of components of the types specified foreach component to be held by an instance of the
	 *                   constructed type.
	 * @return a type that represents the {@code typeclass} of this type, and have the given {@code component}, and should be
	 * 		treated as if it was the given {@code wildclass}.
	 * @throws NullPointerException if the given {@code wildclass} or {@code component} is null.
	 * @since 0.1.5
	 */
	public Type<T> with(Class wildclass, Type... components) {
		Objects.requireNonNull(components, "components");
		Objects.requireNonNull(wildclass, "wildclass");
		return Type.of(this.typeclass, wildclass, components);
	}

	/**
	 * Get a type that represents the {@code typeclass} of this type, and have the given {@code component}, and should be treated
	 * as if it was the given {@code wildclass}.
	 *
	 * @param wildclass  the class that an instance of the returned type should be treated as if it was an instance of it.
	 * @param components an array of components of the types specified foreach component to be held by an instance of the
	 *                   constructed type.
	 * @return a type that represents the {@code typeclass} of this type, and have the given {@code component}, and should be
	 * 		treated as if it was the given {@code wildclass}.
	 * @throws NullPointerException if the given {@code wildclass} or {@code component} is null.
	 * @since 0.1.5
	 */
	public Type<T> with(Class wildclass, Class... components) {
		Objects.requireNonNull(components, "components");
		Objects.requireNonNull(wildclass, "wildclass");
		return Type.of(this.typeclass, wildclass, components);
	}

	/**
	 * Get a type that represents the {@code Object} version of the {@code typeclass} of this type, and have the {@code component}
	 * of this type, and should be treated as if it was the {@code wildclass} of this type.
	 *
	 * @return a type that represents the {@code Object} version of the {@code typeclass} of this type, and have the {@code
	 * 		component} of this type, and should be treated as if it was the {@code wildclass} of this type.
	 * @since 0.1.5
	 */
	public Type<T> withObjectiveClass() {
		return this.typeclass.isPrimitive() ?
			   Type.of(Reflection.deepAsWrapperClass(this.typeclass), this.wildclass, this.components) :
			   this;
	}

	/**
	 * Get a type that represents the primitive version of the {@code typeclass} of this type, and have the {@code component} of
	 * this type, and should be treated as if it was the {@code wildclass} of this type.
	 *
	 * @return a type that represents the primitive version of the {@code typeclass} of this type, and have the {@code component}
	 * 		of this type, and should be treated as if it was the {@code wildclass} of this type. Or null if there is no primitive
	 * 		class of the {@code typeclass} of this type.
	 * @since 0.1.5
	 */
	public Type<T> withPrimitiveType() {
		return Reflection.hasPrimitiveClass(this.typeclass) ?
			   Type.of(Reflection.deepAsPrimitiveClass(this.typeclass), this.wildclass, this.components) :
			   null;
	}

	@SuppressWarnings("JavaDoc")
	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
		Objects.requireNonNull(stream, "stream");
		this.typeclass = (Class) stream.readObject();
		this.wildclass = (Class) stream.readObject();
		this.components = (Component[]) stream.readObject();
	}

	@SuppressWarnings("JavaDoc")
	private void writeObject(ObjectOutputStream stream) throws IOException {
		Objects.requireNonNull(stream, "stream");
		stream.writeObject(this.typeclass);
		stream.writeObject(this.wildclass);
		stream.writeObject(this.components);
	}
}
//
//		/**
//		 * Finalize the given {@code components} and make them unmodifiable.
//		 *
//		 * @param components the components to be finalized.
//		 * @return a copy of the given {@code components}.
//		 * @throws NullPointerException if the given {@code components} is null.
//		 */
//		static Component[] finalize(Component... components) {
//			Objects.requireNonNull(components, "components");
//			Component[] finalized = new Component[components.length];
//
//			System.arraycopy(components, 0, finalized, 0, components.length);
//
//			return finalized;
//		}
//
//		static Map<Object, Type> finalize(Map<Object, Type> map) {
//			Objects.requireNonNull(map, "map");
//			Map finalized = new HashMap(map.size());
//
//			return finalized;
//		}
//
//		/**
//		 * Construct a new component with no componentType nor any mappings.
//		 */
//		private Component() {
//		}
//
//		/**
//		 * Construct a new component with the mappings in the given {@code componentMap}.
//		 *
//		 * @param componentMap the mappings of the objects to their types in the constructed component.
//		 * @throws NullPointerException if the given {@code componentMap} is null.
//		 * @throws ClassCastException   if the given {@code componentMap} has a value that is not null neither an instance of
//		 *                              {@link Type}.
//		 */
//		private Component(Map<Object, Type> componentMap) {
//			Objects.requireNonNull(componentMap, "componentMap");
//			this.componentMap = Component.finalize(componentMap);
//		}
//
//		/**
//		 * Construct a new component that its general type is the given {@code componentType}, and have no mappings.
//		 *
//		 * @param componentType the general type for the objects that have no mapping in the constructed component.
//		 */
//		private Component(Type<T> componentType) {
//			this.componentType = componentType;
//			this.componentMap = Collections.emptyMap();
//		}

//
//	/**
//	 * Construct a new type that represents the class {@link Object}, and have no components.
//	 */
//	private Type() {
//		this.typeclass = (Class) Object.class;
//		this.wildclass = Object.class;
//		this.components = new Component[0];
//	}
//
//	/**
//	 * Construct a new type that represents the class {@link Object}, and have the given {@code components}.
//	 *
//	 * @param components an array of components of the types specified foreach component to be held by an instance of the
//	 *                   constructed type.
//	 * @throws NullPointerException if the given {@code components} or any of its elements is null.
//	 */
//	private Type(Component... components) {
//		Objects.requireNonNull(components, "components");
//		this.typeclass = (Class) Object.class;
//		this.wildclass = Object.class;
//		this.components = Component.finalize(components);
//	}
//
//	/**
//	 * Construct a new type that represents the given {@code typeclass}, and have the given {@code components}.
//	 *
//	 * @param typeclass  the class to be represented by the constructed type.
//	 * @param components an array of components of the types specified foreach component to be held by an instance of the
//	 *                   constructed type.
//	 * @throws NullPointerException if the given {@code typeclass} or {@code components} or any of its elements is null.
//	 */
//	private Type(Class<T> typeclass, Component... components) {
//		Objects.requireNonNull(typeclass, "typeclass");
//		Objects.requireNonNull(components, "components");
//		this.typeclass = typeclass;
//		this.wildclass = typeclass;
//		this.components = Component.finalize(components);
//	}

//
//	/**
//	 * An util class for auto-generating types.
//	 */
//	public static final class Generate {
//		/**
//		 * This is an util class and must not be instanced as an object.
//		 *
//		 * @throws AssertionError when called.
//		 */
//		private Generate() {
//			throw new AssertionError("No instance for you!");
//		}
//
//		/**
//		 * Get a type auto-generated to be representing the given {@code instance}. The {@link Component#componentType componentType}s
//		 * of the {@link Component components} of the returned type are the given {@code componentType}, each type is for the
//		 * component at the same position as its position.
//		 *
//		 * @param instance       the instance the returned type are representing.
//		 * @param componentTypes the general types array for the components that have no mapping in the component at the same
//		 *                       position as the type in the components array of the returned type.
//		 * @param <T>            the type of the class represented by the returned type.
//		 * @return a type auto-generated to be representing the given {@code instance}. The {@link Component#componentType
//		 * 		componentType}s of the {@link Component components} of the returned type are the given {@code componentType}, each
//		 * 		type is for the component at the same position as its position.
//		 * @throws NullPointerException if the given {@code componentTypes} is null.
//		 */
//		public static <T> Type<T> from(T instance, Type... componentTypes) {
//			Objects.requireNonNull(componentTypes, "componentTypes");
//			return Generate.from(new HashMap(), instance, componentTypes);
//		}
//
//		/**
//		 * Get a type auto-generated to be representing the given {@code instance}, and should be treated as if it was the given
//		 * {@code wildclass}. The {@link Component#componentType componentType}s of the {@link Component components} of the returned
//		 * type are the given {@code componentType}, each type is for the component at the same position as its position.
//		 *
//		 * @param instance       the instance the returned type are representing.
//		 * @param wildclass      the class that an instance of the returned type should be treated as if it was an instance of
//		 *                       it.
//		 * @param componentTypes the general types array for the components that have no mapping in the component at the same
//		 *                       position as the type in the components array of the returned type.
//		 * @param <T>            the type of the class represented by the returned type.
//		 * @return a type auto-generated to be representing the given {@code instance}, and should be treated as if it was the
//		 * 		given {@code wildclass}. The {@link Component#componentType componentType}s of the {@link Component components} of the
//		 * 		returned type are the given {@code componentType}, each type is for the component at the same position as its
//		 * 		position.
//		 * @throws NullPointerException if the given {@code wildclass} or {@code componentTypes} is null.
//		 */
//		public static <T> Type<T> from(T instance, Class wildclass, Type... componentTypes) {
//			Objects.requireNonNull(wildclass, "wildclass");
//			Objects.requireNonNull(componentTypes, "componentTypes");
//			return Generate.from(new HashMap(), instance, wildclass, componentTypes);
//		}
//
//		/**
//		 * The backing method for the method {@link Generate#from(Object, Type...)}.
//		 *
//		 * @param dejaVus        a map associates instances to their types, so any instance included in the map will not be
//		 *                       classified, instead this method will take the type associated to it in the map.
//		 * @param instance       the instance the returned type are representing.
//		 * @param componentTypes the general types array for the components that have no mapping in the component at the same
//		 *                       position as the type in the components array of the returned type.
//		 * @param <T>            the type of the class represented by the returned type.
//		 * @return a type auto-generated to be representing the given {@code instance}. The {@link Component#componentType
//		 * 		componentType}s of the {@link Component components} of the returned type are the given {@code componentType}, each
//		 * 		type is for the component at the same position as its position.
//		 * @throws NullPointerException if the given {@code dejaVus} or {@code componentTypes} is null.
//		 */
//		static <T> Type<T> from(Map dejaVus, T instance, Type... componentTypes) {
//			Objects.requireNonNull(dejaVus, "dejaVus");
//			Objects.requireNonNull(componentTypes, "componentTypes");
//			Class wildclass = instance == null ? Void.class : instance.getClass();
//			return Generate.from(dejaVus, instance, wildclass, componentTypes);
//		}
//
//		/**
//		 * The backing method for the method {@link Generate#from(Object, Class, Type[])}.
//		 *
//		 * @param dejaVus        a map associates instances to their types, so any instance included in the map will not be
//		 *                       classified, instead this method will take the type associated to it in the map.
//		 * @param instance       the instance the returned type are representing.
//		 * @param wildclass      the class that an instance of the returned type should be treated as if it was an instance of
//		 *                       it.
//		 * @param componentTypes the general types array for the components that have no mapping in the component at the same
//		 *                       position as the type in the components array of the returned type.
//		 * @param <T>            the type of the class represented by the returned type.
//		 * @return a type auto-generated to be representing the given {@code instance}, and should be treated as if it was the
//		 * 		given {@code wildclass}. The {@link Component#componentType componentType}s of the {@link Component components} of the
//		 * 		returned type are the given {@code componentType}, each type is for the component at the same position as its
//		 * 		position.
//		 * @throws NullPointerException if the given {@code dejaVus} or {@code wildclass} or {@code componentTypes} is null.
//		 */
//		static <T> Type<T> from(Map dejaVus, T instance, Class wildclass, Type... componentTypes) {
//			Objects.requireNonNull(dejaVus, "dejaVus");
//			Objects.requireNonNull(wildclass, "wildclass");
//			Objects.requireNonNull(componentTypes, "componentTypes");
//
//			if (instance != null)
//				if (instance.getClass().isArray())
//					return Generate.fromArray(dejaVus, instance, wildclass, componentTypes);
//				else if (instance instanceof Iterable)
//					return Generate.fromIterable(dejaVus, (Iterable) instance, wildclass, componentTypes);
//				else if (instance instanceof Map)
//					return Generate.fromMap(dejaVus, (Map) instance, wildclass, componentTypes);
//
//			return Type.from(instance, wildclass, Component.array(componentTypes));
//		}
//
//		/**
//		 * Get a type auto-generated to be representing the given {@code array}, and should be treated as if it was the given
//		 * {@code wildclass}. The {@link Component#componentType componentType}s of the {@link Component components} of the returned
//		 * type are the given {@code componentType}, each type is for the component at the same position as its position.
//		 *
//		 * @param dejaVus        a map associates instances to their types, so any instance included in the map will not be
//		 *                       classified, instead this method will take the type associated to it in the map.
//		 * @param array          the array the returned type are representing.
//		 * @param wildclass      the class that an instance of the returned type should be treated as if it was an instance of
//		 *                       it.
//		 * @param componentTypes the general types array for the components that have no mapping in the component at the same
//		 *                       position as the type in the components array of the returned type.
//		 * @param <T>            the type of the class represented by the returned type.
//		 * @return a type auto-generated to be representing the given {@code array}, and should be treated as if it was the given
//		 *        {@code wildclass}. The {@link Component#componentType componentType}s of the {@link Component components} of the returned
//		 * 		type are the given {@code componentType}, each type is for the component at the same position as its position.
//		 * @throws NullPointerException if the given {@code dejaVus} or {@code array} or {@code wildclass} or {@code
//		 *                              componentTypes} is null.
//		 */
//		static <T> Type<T> fromArray(Map<Object, Type> dejaVus, Object array, Class wildclass, Type[] componentTypes) {
//			Objects.requireNonNull(dejaVus, "dejaVus");
//			Objects.requireNonNull(array, "array");
//			Objects.requireNonNull(wildclass, "wildclass");
//			Objects.requireNonNull(componentTypes, "componentTypes");
//
//			Type elementComponentType;
//			Component[] components;
//			if (componentTypes.length < 1) {
//				elementComponentType = Generate.ofArray(array.getClass().getComponentType());
//				components = Component.array(elementComponentType);
//			} else if (componentTypes[0] != null) {
//				elementComponentType = componentTypes[0];
//				components = Component.array(componentTypes);
//			} else {
//				elementComponentType = Generate.ofArray(array.getClass().getComponentType());
//				Type[] modified = Arrays.copyOf(componentTypes, componentTypes.length);
//				modified[0] = elementComponentType;
//				components = Component.array(modified);
//			}
//
//			Type type = Type.from(array, wildclass);
//			dejaVus.put(array, type);
//
//			Iterator iterator = Arrays.iterator(array);
//			for (int i = 0; iterator.hasNext(); i++) {
//				Object element = iterator.next();
//
//				if (dejaVus.containsKey(element))
//					//if it has been solved before
//					components[0].put(i, dejaVus.get(element));
//				else
//					components[0].put(i, Generate.from(dejaVus, element, elementComponentType));
//			}
//
//			//finalize everything
//			type.setComponents(components);
//			return type;
//		}
//
//		/**
//		 * Get a type auto-generated to be representing the given {@code iterable}, and should be treated as if it was the given
//		 * {@code wildclass}. The {@link Component#componentType componentType}s of the {@link Component components} of the returned
//		 * type are the given {@code componentType}, each type is for the component at the same position as its position.
//		 *
//		 * @param dejaVus        a map associates instances to their types, so any instance included in the map will not be
//		 *                       classified, instead this method will take the type associated to it in the map.
//		 * @param iterable       the iterable the returned type are representing.
//		 * @param wildclass      the class that an instance of the returned type should be treated as if it was an instance of
//		 *                       it.
//		 * @param componentTypes the general types array for the components that have no mapping in the component at the same
//		 *                       position as the type in the components array of the returned type.
//		 * @param <T>            the type of the class represented by the returned type.
//		 * @return a type auto-generated to be representing the given {@code iterable}, and should be treated as if it was the
//		 * 		given {@code wildclass}. The {@link Component#componentType componentType}s of the {@link Component components} of the
//		 * 		returned type are the given {@code componentType}, each type is for the component at the same position as its
//		 * 		position.
//		 * @throws NullPointerException if the given {@code dejaVus} or {@code iterable} or {@code wildclass} or {@code
//		 *                              componentTypes} is null.
//		 */
//		static <T> Type<T> fromIterable(Map<Object, Type> dejaVus, Iterable iterable, Class wildclass, Type[] componentTypes) {
//			Objects.requireNonNull(dejaVus, "dejaVus");
//			Objects.requireNonNull(iterable, "iterable");
//			Objects.requireNonNull(wildclass, "wildclass");
//			Objects.requireNonNull(componentTypes, "componentTypes");
//
//			Type elementComponentType = componentTypes.length < 1 ? null : componentTypes[0];
//			Component[] components = Component.array(1, componentTypes);
//
//			Type type = Type.from(iterable, wildclass);
//			dejaVus.put(iterable, type);
//
//			Iterator iterator = iterable.iterator();
//			for (int i = 0; iterator.hasNext(); i++) {
//				Object element = iterator.next();
//
//				if (dejaVus.containsKey(element))
//					//if it has been solved before
//					components[0].put(i, dejaVus.get(element));
//				else
//					components[0].put(i, Generate.from(dejaVus, element, elementComponentType));
//			}
//
//			//finalize everything
//			type.setComponents(components);
//			return type;
//		}
//
//		/**
//		 * Get a type auto-generated to be representing the given {@code map}, and should be treated as if it was the given {@code
//		 * wildclass}. The {@link Component#componentType componentType}s of the {@link Component components} of the returned type are
//		 * the given {@code componentType}, each type is for the component at the same position as its position.
//		 *
//		 * @param dejaVus        a map associates instances to their types, so any instance included in the map will not be
//		 *                       classified, instead this method will take the type associated to it in the map.
//		 * @param map            the map the returned type are representing.
//		 * @param wildclass      the class that an instance of the returned type should be treated as if it was an instance of
//		 *                       it.
//		 * @param componentTypes the general types array for the components that have no mapping in the component at the same
//		 *                       position as the type in the components array of the returned type.
//		 * @param <T>            the type of the class represented by the returned type.
//		 * @return a type auto-generated to be representing the given {@code map}, and should be treated as if it was the given
//		 *        {@code wildclass}. The {@link Component#componentType componentType}s of the {@link Component components} of the returned
//		 * 		type are the given {@code componentType}, each type is for the component at the same position as its position.
//		 * @throws NullPointerException if the given {@code dejaVus} or {@code map} or {@code wildclass} or {@code componentTypes}
//		 *                              is null.
//		 */
//		static <T> Type<T> fromMap(Map<Object, Type> dejaVus, Map map, Class wildclass, Type[] componentTypes) {
//			Objects.requireNonNull(dejaVus, "dejaVus");
//			Objects.requireNonNull(map, "map");
//			Objects.requireNonNull(wildclass, "wildclass");
//			Objects.requireNonNull(componentTypes, "componentTypes");
//
//			Type keyComponentType = componentTypes.length < 1 ? null : componentTypes[0];
//			Type valueComponentType = componentTypes.length < 2 ? null : componentTypes[1];
//			Component[] components = Component.array(2, componentTypes);
//
//			Type type = Type.from(map, wildclass);
//			dejaVus.put(map, type);
//
//			for (Map.Entry entry : (Set<Map.Entry>) map.entrySet()) {
//				Object key = entry.getKey();
//				Object value = entry.getValue();
//
//				if (dejaVus.containsKey(key))
//					//if it has been solved before
//					components[0].put(key, dejaVus.get(key));
//				else
//					components[0].put(key, Generate.from(dejaVus, key, keyComponentType));
//
//				if (dejaVus.containsKey(value))
//					//if it has been solved before
//					components[1].put(key, dejaVus.get(value));
//				else
//					components[1].put(key, Generate.from(dejaVus, value, valueComponentType));
//			}
//
//			//finalize everything
//			type.setComponents(components);
//			return type;
//		}
//
//		/**
//		 * Get a type that represents the given {@code typeclass}, and with a {@code components} auto generated from the given
//		 * array's class.
//		 * <pre>
//		 *     Type.ofArray(<font color="a5edff">TYPE[][][]</font>)
//		 *     <font color="a5edff">TYPE[][][]</font>&lt;<font color="a5edff">TYPE[][]</font>&lt;<font color="a5edff">TYPE[]</font>&lt;<font color="a5edff">TYPE</font>&gt;&gt;&gt;
//		 * </pre>
//		 *
//		 * @param typeclass the array class to be represented by the returned type.
//		 * @param <T>       the type of the class represented by the returned type.
//		 * @return a type that represents the given {@code typeclass}, and with a {@code components} auto generated from the given
//		 * 		array's class.
//		 * @throws NullPointerException if the given {@code typeclass} is null.
//		 */
//		static <T> Type<T> ofArray(Class<T> typeclass) {
//			Objects.requireNonNull(typeclass, "typeclass");
//			return typeclass.isArray() ?
//				   Type.of(typeclass, Type.Component.of(Generate.ofArray(typeclass.getComponentType()))) :
//				   Type.of(typeclass);
//		}
//
//		/**
//		 * Get a type that represents the given {@code typeclass}, and with a {@code components} auto generated from the given
//		 * array's class, and should be treated as if it was the given {@code wildclass}.
//		 * <pre>
//		 *     Type.ofArray(<font color="a5edff">TYPE[][][]</font>, <font color="fc8fbb">WILD</font>)
//		 *     <font color="a5edff">TYPE[][][]</font>:<font color="fc8fbb">WILD</font>&lt;<font color="a5edff">TYPE[][]</font>&lt;<font color="a5edff">TYPE[]</font>&lt;<font color="a5edff">TYPE</font>&gt;&gt;&gt;
//		 * </pre>
//		 *
//		 * @param typeclass the array class to be represented by the returned type.
//		 * @param wildclass the class that an instance of the returned type should be treated as if it was an instance of it.
//		 * @param <T>       the type of the class represented by the returned type.
//		 * @return a type that represents the given {@code typeclass}, and with a {@code components} auto generated from the given
//		 * 		array's class, and should be treated as if it was the given {@code wildclass}.
//		 * @throws NullPointerException if the given {@code typeclass} or {@code wildclass} is null.
//		 */
//		static <T> Type<T> ofArray(Class<T> typeclass, Class wildclass) {
//			Objects.requireNonNull(typeclass, "typeclass");
//			Objects.requireNonNull(wildclass, "wildclass");
//			return typeclass.isArray() ?
//				   Type.of(typeclass, wildclass, Type.Component.of(Generate.ofArray(typeclass.getComponentType()))) :
//				   Type.of(typeclass, wildclass);
//		}
//	}
//		/**
//		 * Return an empty component.
//		 *
//		 * @return an empty component.
//		 */
//		public static Component linear() {
//			return new Component();
//		}
//
//		/**
//		 * Get a linear component of general component types. The general types specified in order representing the given {@code
//		 * componentClasses}.
//		 * <pre>
//		 *     Type.component(<font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
//		 *     <font color="d3c4ff">COMPONENT0</font>&lt;<font color="d3c4ff">COMPONENT1</font>&lt;<font color="d3c4ff">COMPONENT2…</font>&gt;&gt;
//		 * </pre>
//		 *
//		 * @param componentClasses an ordered list of the classes represented by the nested general component types in returned
//		 *                         component.
//		 * @return a linear component of general component types. The general types specified in order representing the given
//		 *        {@code componentClasses}.
//		 * @throws NullPointerException if the given {@code componentClasses} or an element with index (? &gt; 0) in it is null.
//		 * @since 0.1.5
//		 */
//		public static Component linear(Class... componentClasses) {
//			Objects.requireNonNull(componentClasses, "componentClasses");
//			if (componentClasses.length == 0)
//				//length == 0 ? empty
//				return new Component();
//			if (componentClasses.length == 1)
//				//length == 1 ? singleton
//				return componentClasses[0] == null ?
//					   new Component() :
//					   Component.of(Type.of(componentClasses[0]));
//			//otherwise ? recursive
//			return Component.of(Type.of(
//					//componentType's typeclass
//					componentClasses[0],
//					//componentType's component (subComponents)
//					Component.linear(Arrays.copyOfRange(componentClasses, 1, componentClasses.length))
//			));
//		}
//
//		/**
//		 * Get linear components of general component types. The given {@code componentClasses} specifies each array for a
//		 * component in the returned components array.
//		 * <br>
//		 * Note: this is just an array version of {@link Component#linear(Class[])}.
//		 * <pre>
//		 *     Type.components(<font color="d3c4ff">{COMPONENT0, NESTED0, DEEP0, …}, {COMPONENT1, NESTED1, DEEP1, …}, …</font>)
//		 *     &lt;<font color="d3c4ff">COMPONENT0</font>&lt;<font color="d3c4ff">NESTED0</font>&lt;<font color="d3c4ff">DEEP0</font>…&gt;&gt;, <font color="d3c4ff">COMPONENT1</font>&lt;<font color="d3c4ff">NESTED1</font>&lt;<font color="d3c4ff">DEEP1</font>…&gt;&gt;, …&gt;
//		 * </pre>
//		 *
//		 * @param componentClasses an array of component classes, each array goes to the same position component in the returned
//		 *                         components array.
//		 * @return linear components of general component types. The given {@code componentClasses} specifies each array for a
//		 * 		component *in the returned components array.
//		 * @throws NullPointerException if the given {@code componentClasses} or any of its elements is null.
//		 * @see Component#linear(Class[])
//		 * @since 0.1.5
//		 */
//		public static Component[] linear(Class[]... componentClasses) {
//			Objects.requireNonNull(componentClasses, "componentClasses");
//			Component[] components = new Component[componentClasses.length];
//
//			for (int i = 0; i < componentClasses.length; i++)
//				components[i] = Component.linear(componentClasses[i]);
//
//			return components;
//		}
//
//		/**
//		 * Get linear components of general component types. The given {@code componentClasses} specifies each array for a
//		 * component in the returned components array.
//		 * <br>
//		 * Note: this is just an array version of {@link Component#linear(Class[])}.
//		 * <pre>
//		 *     Type.components(<font color="d3c4ff">{COMPONENT0, NESTED0, DEEP0, …}, {COMPONENT1, NESTED1, DEEP1, …}, …</font>)
//		 *     &lt;<font color="d3c4ff">COMPONENT0</font>&lt;<font color="d3c4ff">NESTED0</font>&lt;<font color="d3c4ff">DEEP0</font>…&gt;&gt;, <font color="d3c4ff">COMPONENT1</font>&lt;<font color="d3c4ff">NESTED1</font>&lt;<font color="d3c4ff">DEEP1</font>…&gt;&gt;, …&gt;
//		 * </pre>
//		 *
//		 * @param componentClasses an array of component classes, each array goes to the same position component in the returned
//		 *                         components array.
//		 * @param length           the minimum length the returned array will be.
//		 * @return linear components of general component types. The given {@code componentClasses} specifies each array for a
//		 * 		component *in the returned components array.
//		 * @throws NullPointerException if the given {@code componentClasses} or any of its elements is null.
//		 * @see Component#linear(Class[])
//		 * @since 0.1.5
//		 */
//		public static Component[] linear(Class[][] componentClasses, int length) {
//			Objects.requireNonNull(componentClasses, "componentClasses");
//			Component[] components = new Component[Math.max(componentClasses.length, length)];
//
//			int i = 0;
//			for (; i < componentClasses.length; i++)
//				components[i] = Component.linear(componentClasses[i]);
//			for (; i < length; i++)
//				components[i] = new Component();
//
//			return components;
//		}
//		 this is the same as array(Class[])
//				/**
//				 * Get an array of components that each one's general type is the type of the class at the same as its position in the
//				 * given {@code componentClasses}.
//				 * <pre>
//				 *     Type.components(<font color="d3c4ff">COMPONENT0</font>, <font color="d3c4ff">COMPONENT1</font>, <font color="d3c4ff">COMPONENT2</font>, …)
//				 *     &lt;<font color="d3c4ff">COMPONENT0</font>, <font color="d3c4ff">COMPONENT1</font>, <font color="d3c4ff">COMPONENT2</font>, …&gt;
//				 * </pre>
//				 *
//				 * @param componentClasses the general types array for the components that have no mapping in the component at the same
//				 *                         position as the type in returned components array.
//				 * @return an array of components that each one's general type is the type of the class at the same as its * position in
//				 * 		the given {@code componentClasses}.
//				 * @throws NullPointerException if the given {@code componentClasses} is null.
//				 * @see Component#of(Type[], int)
//				 * @see Component#of(Type)
//				 * @since 0.1.5
//				 */
//				public static Component[] of(Class... componentClasses) {
//					Objects.requireNonNull(componentClasses, "componentClasses");
//					Component[] components = new Component[componentClasses.length];
//
//					for (int i = 0; i < componentClasses.length; i++)
//						components[i] = Component.of(componentClasses[i]);
//
//					return components;
//				}
//
//		/**
//		 * Get an array of components that each one's general type is the type of the class at the same as its position in the
//		 * given {@code componentClasses}.
//		 * <pre>
//		 *     Type.components(<font color="d3c4ff">COMPONENT0</font>, <font color="d3c4ff">COMPONENT1</font>, <font color="d3c4ff">COMPONENT2</font>, …)
//		 *     &lt;<font color="d3c4ff">COMPONENT0</font>, <font color="d3c4ff">COMPONENT1</font>, <font color="d3c4ff">COMPONENT2</font>, …&gt;
//		 * </pre>
//		 *
//		 * @param componentClasses the general types array for the components that have no mapping in the component at the same
//		 *                         position as the type in returned components array.
//		 * @param length           the minimum length the returned array will be.
//		 * @return an array of components that each one's general type is the type of the class at the same as its * position in
//		 * 		the given {@code componentClasses}.
//		 * @throws NullPointerException if the given {@code componentClasses} is null.
//		 * @see Component#of(Type[], int)
//		 * @see Component#of(Type)
//		 * @since 0.1.5
//		 */
//		public static Component[] of(Class[] componentClasses, int length) {
//			Objects.requireNonNull(componentClasses, "componentClasses");
//			Component[] components = new Component[Math.max(componentClasses.length, length)];
//
//			int i = 0;
//			for (; i < componentClasses.length; i++)
//				components[i] = Component.of(componentClasses[i]);
//			for (; i < length; i++)
//				components[i] = new Component();
//
//			return components;
//		}
//
//		/**
//		 * Get an array of components that each one's general type is the type at the same as its position in the given {@code
//		 * componentTypes}.
//		 * <pre>
//		 *     Type.components(<font color="d3c4ff">COMPONENT0</font>, <font color="d3c4ff">COMPONENT1</font>, <font color="d3c4ff">COMPONENT2</font>, …)
//		 *     &lt;<font color="d3c4ff">COMPONENT0</font>, <font color="d3c4ff">COMPONENT1</font>, <font color="d3c4ff">COMPONENT2</font>, …&gt;
//		 * </pre>
//		 *
//		 * @param componentTypes the general types array for the components that have no mapping in the component at the same
//		 *                       position as the type in returned components array.
//		 * @return an array of components that each one's general type is the type at the same as its position in the given {@code
//		 * 		componentTypes}.
//		 * @throws NullPointerException if the given {@code componentTypes} is null.
//		 * @see Component#of(Type[], int)
//		 * @see Component#of(Type)
//		 * @since 0.1.5
//		 */
//		public static Component[] of(Type... componentTypes) {
//			Objects.requireNonNull(componentTypes, "componentTypes");
//			Component[] components = new Component[componentTypes.length];
//
//			for (int i = 0; i < componentTypes.length; i++)
//				components[i] = Component.of(componentTypes[i]);
//
//			return components;
//		}
//
//		/**
//		 * Get an array of components that each one's general type is the type at the same as its position in the given {@code
//		 * componentTypes}.
//		 * <pre>
//		 *     Type.components(<font color="fc888a">MIN_LENGTH</font>, <font color="d3c4ff">COMPONENT0</font>, <font color="d3c4ff">COMPONENT1</font>, <font color="d3c4ff">COMPONENT2</font>, …)
//		 *     &lt;<font color="d3c4ff">COMPONENT0</font>, <font color="d3c4ff">COMPONENT1</font>, <font color="d3c4ff">COMPONENT2</font>, <font color="fc888a">…</font>&gt;
//		 * </pre>
//		 *
//		 * @param componentTypes the general types array for the components that have no mapping in the component at the same
//		 *                       position as the type in returned components array.
//		 * @param length         the minimum length the returned array will be.
//		 * @return an array of components that each one's general type is the type at the same as its position in the given {@code
//		 * 		componentTypes}.
//		 * @throws NullPointerException if the given {@code componentTypes} is null.
//		 */
//		public static Component[] of(Type[] componentTypes, int length) {
//			Objects.requireNonNull(componentTypes, "componentTypes");
//			Component[] components = new Component[Math.max(componentTypes.length, length)];
//
//			int i = 0;
//			for (; i < componentTypes.length; i++)
//				components[i] = Component.of(componentTypes[i]);
//			for (; i < length; i++)
//				components[i] = new Component();
//
//			return components;
//		}
//
//	/**
//	 * Backdoor to modify the {@code components} of this type.
//	 *
//	 * @param components the new {@code components} array to be set.
//	 * @throws NullPointerException if the given {@code components} is null.
//	 */
//	private void setComponents(Component[] components) {
//		Objects.requireNonNull(components, "components");
//		this.components = Component.finalize(components);
//	}
//
//	/**
//	 * Backdoor to modify the {@code typeclass} of this type.
//	 *
//	 * @param typeclass the new {@code typeclass} to be set.
//	 * @throws NullPointerException if the given {@code typeclass} is null.
//	 */
//	private void setTypeclass(Class typeclass) {
//		Objects.requireNonNull(typeclass, "typeclass");
//		this.typeclass = typeclass;
//	}
//
//	/**
//	 * Backdoor to modify the {@code wildclass} of this type.
//	 *
//	 * @param wildclass the new {@code wildclass} to be set.
//	 * @throws NullPointerException if the given {@code wildclass} is null.
//	 */
//	private void setWildclass(Class wildclass) {
//		Objects.requireNonNull(wildclass, "wildclass");
//		this.wildclass = wildclass;
//	}
