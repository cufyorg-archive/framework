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
import cufy.util.array.ObjectArray;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An alternative representation for {@link Class classes}. This provides more data about the
 * targeted type. About the components and the wildclass of that class. The wildclass is how the
 * class should be treated. The componentType is a component of types for each specific object in an
 * instance this type is representing.
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
	 * A pattern to validate {@link #getName() names} of {@code Component}s.
	 *
	 * @since 0.1.5 ~2020.08.11
	 */
	public static final Pattern COMPONENTS_PATTERN = Pattern.compile("^(?:[^,:<>*]+(?::[^,:<>*]+)?[*]?(?:<(?:[^,<>]*(?:<.*>)?,?)*>)?(?:,(?=\\s*\\S))?)*$");
	/**
	 * A pattern to split {@link #getName() names} of {@code Component}s.
	 *
	 * @since 0.1.5 ~2020.08.11
	 */
	public static final Pattern COMPONENTS_SPLIT = Pattern.compile("[^:,<>]+(?::[^:,<>]+)?(?:<(?:[^,<>]*(?:<.*>)?,?)*>)?(?=,(?=\\s*[^>]))?");
	/**
	 * The pattern to validate a {@link #getName() name} of a {@link Type}.
	 * <pre>
	 *     Use the group {@code "TYPE"} to extract the {@link #typeclass}.
	 *     Use the group {@code "WILD"} to extract the {@link #wildclass}.
	 *     Use the group {@code "COMPONENTS"} to extract the {@link #components}.
	 * </pre>
	 *
	 * @since 0.1.5 ~2020.08.11
	 */
	public static final Pattern PATTERN = Pattern.compile("^(?<TYPE>[^,:<>*]+)(?::(?<WILD>[^,:<>*]+))?(?<OBJECT>[*])?(?:<(?<COMPONENTS>(?:[^,<>]*(?:<.*>)?,?)*)>)?\\s*$");

	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = 3076231001299756142L;

	/**
	 * The components of this type.
	 *
	 * @since 0.1.5 ~2020.08.11
	 */
	private Type[] components;
	/**
	 * Mappings for other types for each specific instance.
	 *
	 * @since 0.1.5 ~2020.08.11
	 */
	private Map<Object, Type> objecttypes;
	/**
	 * The class represented by this type. This field should be treated as final field.
	 *
	 * @since 0.1.5 ~2020.08.11
	 */
	private Class<T> typeclass;
	/**
	 * The class that an instance of this type should be treated as if it was an instance of it.
	 * This field should be treated as final field.
	 *
	 * @since 0.1.5 ~2020.08.11
	 */
	private Class wildclass;

	/**
	 * Construct a new type without initializing it. It is illegal to construct a new {@link Type}
	 * but not initialize it before sharing it outside this class. yet, this class can't know such
	 * thing.
	 * <p>
	 * Note: this is a backdoor constructor only to be utilized by this class.
	 *
	 * @since 0.1.5 ~2020.08.11
	 */
	private Type() {
	}

	/**
	 * Construct a new type that represents the given {@code typeclass}, and have the given {@code
	 * components} and {@code objecttypes}, and should be treated as if it was the given {@code
	 * wildclass}.
	 *
	 * @param typeclass   the class to be represented by the constructed type.
	 * @param wildclass   the class that an instance of the constructed type should be treated as if
	 *                    was an instance of it.
	 * @param objecttypes mappings for other types for each specific instance.
	 * @param components  the components of the constructed type.
	 * @throws NullPointerException if the given {@code typeclass} or {@code wildclass} or {@code
	 *                              objecttypes} or {@code components} is null.
	 * @throws ClassCastException   if the given {@code objecttypes} has a value that is not an
	 *                              instance of {@link Type}.
	 * @since 0.1.5 ~2020.08.11
	 */
	private Type(Class<T> typeclass, Class wildclass, Map<Object, Type> objecttypes, Type[] components) {
		this.init(typeclass, wildclass, objecttypes, components);
	}

	/**
	 * Create a new array of types from the given {@code array}.
	 *
	 * @param array the source array.
	 * @return a new array of types from the given {@code array}. Or null if the given {@code array}
	 * 		is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static Type[] array(Class... array) {
		if (array == null)
			return null;

		Type[] product = new Type[array.length];

		for (int i = 0; i < product.length; i++) {
			Class element = array[i];

			if (element != null)
				product[i] = Type.of(element);
		}

		return product;
	}

	/**
	 * Get a Class object associated with the class with the given {@code name}, Using the given
	 * {@code loader}.
	 * <pre>
	 *     "boolean" -> boolean.class
	 *     "byte" -> byte.class
	 *     "char" -> char.class
	 *     "double" -> double.class
	 *     "float" -> float.class
	 *     "int" -> int.class
	 *     "long" -> long.class
	 *     "short" -> short.class
	 * </pre>
	 *
	 * @param name       the name of the targeted class.
	 * @param initialize if {@code true} the any non-initialized class will be initialized.
	 * @param loader     class loader from which the classes must be loaded.
	 * @return ass object associated with the class with the given {@code name}.
	 * @throws ClassNotFoundException      if no type can be associated to the given {@code name}.
	 * @throws LinkageError                if the linkage failed.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code name} or {@code loader} is null.
	 * @see Class#forName(String, boolean, ClassLoader)
	 * @since 0.1.5 ~2020.08.11
	 */
	public static Class classForName(String name, boolean initialize, ClassLoader loader) throws ClassNotFoundException {
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(loader, "loader");
		switch (name) {
			case "boolean":
				return boolean.class;
			case "byte":
				return byte.class;
			case "char":
				return char.class;
			case "double":
				return double.class;
			case "float":
				return float.class;
			case "int":
				return int.class;
			case "long":
				return long.class;
			case "short":
				return short.class;
			default:
				return Class.forName(name, initialize, loader);
		}
	}

	/**
	 * Get a Class object associated with the class with the given {@code name}.
	 * <pre>
	 *     "boolean" -> boolean.class
	 *     "byte" -> byte.class
	 *     "char" -> char.class
	 *     "double" -> double.class
	 *     "float" -> float.class
	 *     "int" -> int.class
	 *     "long" -> long.class
	 *     "short" -> short.class
	 * </pre>
	 *
	 * @param name the name of the targeted class.
	 * @return ass object associated with the class with the given {@code name}.
	 * @throws LinkageError                if the linkage failed.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws ClassNotFoundException      if no type can be associated to the given {@code name}.
	 * @throws NullPointerException        if the given {@code name} is null.
	 * @see Class#forName(String)
	 * @since 0.1.5 ~2020.08.11
	 */
	public static Class classForName(String name) throws ClassNotFoundException {
		Objects.requireNonNull(name, "name");
		switch (name) {
			case "boolean":
				return boolean.class;
			case "byte":
				return byte.class;
			case "char":
				return char.class;
			case "double":
				return double.class;
			case "float":
				return float.class;
			case "int":
				return int.class;
			case "long":
				return long.class;
			case "short":
				return short.class;
			default:
				return Class.forName(name);
		}
	}

	/**
	 * Get a type that represents the {@code typeclass} of the given {@code typeclassSrc}, and have
	 * the {@code components} of the given {@code componentsSrc}, and should be treated as if it was
	 * the {@code wildclass} of the given {@code wildclassSrc}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE_SRC</font>, <font color="fc8fbb">WILD_SRC</font>, <font color="#bea341">OBJECT_SRC</font>, <font color="d3c4ff">COMPONENTS_SRC</font>)
	 *     <font color="a5edff">TYPE</font>:<font color="fc8fbb">WILD</font>&lt;<font color="d3c4ff">COMPONENTS…</font>&gt;
	 * </pre>
	 *
	 * @param typeclassSrc   the source of the class to be represented by the returned type.
	 * @param wildclassSrc   the source of the class that an instance of the returned type should be
	 *                       treated as if it was an instance of it.
	 * @param objecttypesSrc the source of the mappings for other types for each specific instance.
	 * @param componentsSrc  the source of the array of components of the returned type.
	 * @param <T>            the type of the class represented by the returned type.
	 * @return a type that represents the {@code typeclass} of the given {@code typeclassSrc}, and
	 * 		have the {@code component} of the given {@code componentsSrc}, and should be treated as if
	 * 		it was the {@code wildclass} of the given {@code wildclassSrc}.
	 * @throws NullPointerException if the given {@code wildclassSrc} or {@code typeclassSrc} or
	 *                              {@code componentsSrc} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <T> Type<T> combine(Type<T> typeclassSrc, Type wildclassSrc, Type objecttypesSrc, Type componentsSrc) {
		Objects.requireNonNull(typeclassSrc, "typeclassSrc");
		Objects.requireNonNull(wildclassSrc, "wildclassSrc");
		Objects.requireNonNull(objecttypesSrc, "objecttypesSrc");
		Objects.requireNonNull(componentsSrc, "componentsSrc");
		return Type.of(typeclassSrc.typeclass, wildclassSrc.wildclass, objecttypesSrc.objecttypes, componentsSrc.components);
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
	 * @since 0.1.5 ~2020.08.11
	 */
	public static Type<?> forName(String name) throws ClassNotFoundException {
		Objects.requireNonNull(name, "name");
		Matcher matcher = Type.PATTERN.matcher(name);

		if (!matcher.find())
			throw new ClassNotFoundException(name);

		String typeString = matcher.group("TYPE");
		String wildString = matcher.group("WILD");
		String componentsString = matcher.group("COMPONENTS");

		Class typeclass = Type.classForName(typeString.trim());
		Class wildclass = wildString == null ?
						  typeclass :
						  Type.classForName(wildString.trim());
		Type[] components = componentsString == null ?
							new Type[0] :
							Type.forNames(componentsString);

		return Type.of(
				typeclass,
				wildclass,
				components
		);
	}

	/**
	 * Get a Type object associated with the type with the given {@code name}, Using the given
	 * {@code loader}.
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
	 * @since 0.1.5 ~2020.08.11
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

		Class typeclass = Type.classForName(typeString.trim(), initialize, loader);
		Class wildclass = wildString == null ?
						  typeclass :
						  Type.classForName(wildString.trim(), initialize, loader);
		Type[] components = componentsString == null ?
							new Type[0] :
							Type.forNames(componentsString, initialize, loader);

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
	 * @since 0.1.5 ~2020.08.11
	 */
	public static Type[] forNames(String names) throws ClassNotFoundException {
		Objects.requireNonNull(names, "names");

		if (!Type.COMPONENTS_PATTERN.matcher(names).matches())
			throw new ClassNotFoundException(names);

		List<Type> list = new ArrayList();

		Matcher matcher = Type.COMPONENTS_SPLIT.matcher(names.trim());
		while (matcher.find()) {
			String componentName = matcher.group().trim();
			list.add(
					"?".equals(componentName) ?
					null :
					Type.forName(componentName)
			);
		}

		return list.toArray(new Type[0]);
	}

	/**
	 * Get a Types array associated with the types with the given {@code names}. Using the given
	 * {@code loader}.
	 *
	 * @param names      the fully qualified names of the desired types array.
	 * @param initialize if {@code true} the any non-initialized class will be initialized.
	 * @param loader     class loader from which the classes must be loaded.
	 * @return a Types array for the types with the given {@code name}.
	 * @throws LinkageError                if the linkage failed.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws ClassNotFoundException      if no types can be associated to the given {@code name}.
	 * @throws NullPointerException        if the given {@code name} or {@code loader} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static Type[] forNames(String names, boolean initialize, ClassLoader loader) throws ClassNotFoundException {
		Objects.requireNonNull(names, "names");
		Objects.requireNonNull(loader, "loader");

		if (!Type.COMPONENTS_PATTERN.matcher(names).matches())
			throw new ClassNotFoundException(names);

		List<Type> list = new ArrayList();

		Matcher matcher = Type.COMPONENTS_SPLIT.matcher(names.trim());
		while (matcher.find()) {
			String componentName = matcher.group().trim();
			list.add(
					"?".equals(componentName) ?
					null :
					Type.forName(componentName, initialize, loader)
			);
		}

		return list.toArray(new Type[0]);
	}

	/**
	 * Create a new map of objects and types from the given {@code map}.
	 *
	 * @param map the source map.
	 * @return a new map of objects and types from the given {@code map}. Or null if the given
	 *        {@code map} is null.
	 * @throws ClassCastException if the given {@code map} has a value that is not an instance of
	 *                            {@link Class}.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static Map<Object, Type> map(Map<Object, Class> map) {
		if (map == null)
			return null;

		Map product = new HashMap(map.size());

		for (Map.Entry<Object, Class> entry : map.entrySet()) {
			Object key = entry.getKey();
			Class value = entry.getValue();

			if (value != null)
				product.put(key, Type.of(value));
		}

		return product;
	}

	/**
	 * Get a type that represents the class {@link Object}.
	 * <pre>
	 *     Type.of()
	 *     <font color="a5edff">Object</font>
	 * </pre>
	 *
	 * @return a type that represents the class {@link Object}.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static Type<Object> of() {
		return /*0000*/ new Type(
				Object.class,
				Object.class,
				Collections.emptyMap(),
				new Type[0]
		);
	}

	/**
	 * Get a type that represents the given {@code typeclass}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>)
	 *     <font color="a5edff">TYPE</font>
	 * </pre>
	 *
	 * @param typeclass the class to be represented by the returned type.
	 * @param <T>       the type of the class represented by the returned type.
	 * @return a type that represents the given {@code typeclass}.
	 * @throws NullPointerException if the given {@code typeclass} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <T> Type<T> of(Class<T> typeclass) {
		Objects.requireNonNull(typeclass, "typeclass");
		return /*1000*/ new Type(
				typeclass,
				typeclass,
				Collections.emptyMap(),
				new Type[0]
		);
	}

	/**
	 * Get a type that represents the class {@link Object}, and have the given {@code objecttypes}.
	 * <pre>
	 *     Type.of(<font color="#bea341">OBJECT</font>)
	 *     <font color="a5edff">Object</font><font color="#bea341">*</font>
	 * </pre>
	 *
	 * @param objecttypes mappings for other types for each specific instance.
	 * @return a type that represents the class {@link Object}, and have the given {@code
	 * 		objecttypes}.
	 * @throws NullPointerException if the given {@code objecttypes} is null.
	 * @throws ClassCastException   if the given {@code objecttypes} has a value that is not an
	 *                              instance of {@link Type}.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static Type<Object> of(Map<Object, Type> objecttypes) {
		Objects.requireNonNull(objecttypes, "objecttypes");
		return /*0010*/ new Type(
				Object.class,
				Object.class,
				objecttypes,
				new Type[0]
		);
	}

	/**
	 * Get a type that represents the class {@link Object}, and have the given {@code components}.
	 * <pre>
	 *     Type.of(<font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">Object</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param components the components of the returned type.
	 * @return a type that represents the class {@link Object}, and have the given {@code
	 * 		components}.
	 * @throws NullPointerException if the given {@code components} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static Type<Object> of(Type... components) {
		Objects.requireNonNull(components, "components");
		return /*0001*/ new Type(
				Object.class,
				Object.class,
				Collections.emptyMap(),
				components
		);
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and should be treated as if it was
	 * the given {@code wildclass}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="fc8fbb">WILD</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font>
	 * </pre>
	 *
	 * @param typeclass the class to be represented by the returned type.
	 * @param wildclass the class that an instance of the returned type should be treated as if it
	 *                  was an instance of it.
	 * @param <T>       the type of the class represented by the returned type.
	 * @return a type that represents the given {@code typeclass}, and should be treated as if it
	 * 		was the given {@code wildclass}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code wildclass} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <T> Type<T> of(Class<T> typeclass, Class wildclass) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(wildclass, "wildclass");
		return /*1100*/ new Type(
				typeclass,
				wildclass,
				Collections.emptyMap(),
				new Type[0]
		);
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have the given {@code
	 * objecttypes}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="#bea341">OBJECT</font>)
	 *     <font color="a5edff">TYPE</font><font color="#bea341">*</font>
	 * </pre>
	 *
	 * @param typeclass   the class to be represented by the returned type.
	 * @param objecttypes mappings for other types for each specific instance.
	 * @param <T>         the type of the class represented by the returned type.
	 * @return a type that represents the given {@code typeclass}, and have the given {@code
	 * 		objecttypes}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code objecttypes} is null.
	 * @throws ClassCastException   if the given {@code objecttypes} has a value that is not an
	 *                              instance of {@link Type}.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <T> Type<T> of(Class<T> typeclass, Map<Object, Type> objecttypes) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(objecttypes, "objecttypes");
		return /*1010*/ new Type(
				typeclass,
				typeclass,
				objecttypes,
				new Type[0]
		);
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have the given {@code
	 * components}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">TYPE</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass  the class to be represented by the returned type.
	 * @param components the components of the returned type.
	 * @param <T>        the type of the class represented by the returned type.
	 * @return a type that represents the given {@code typeclass}, and have the given {@code
	 * 		components}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code components} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <T> Type<T> of(Class<T> typeclass, Type... components) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(components, "components");
		return /*1001*/ new Type(
				typeclass,
				typeclass,
				Collections.emptyMap(),
				components
		);
	}

	/**
	 * Get a type that represents the class {@link Object}, and have the given {@code components}
	 * and {@code objecttypes}.
	 * <pre>
	 *     Type.of(<font color="#bea341">OBJECT</font>, <font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">Object</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param objecttypes mappings for other types for each specific instance.
	 * @param components  the components of the returned type.
	 * @return a type that represents the class {@link Object}, and have the given {@code
	 * 		components} and {@code objecttypes}.
	 * @throws NullPointerException if the given {@code objecttypes} or {@code components} is null.
	 * @throws ClassCastException   if the given {@code objecttypes} has a value that is not an
	 *                              instance of {@link Type}.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static Type<Object> of(Map<Object, Type> objecttypes, Type... components) {
		Objects.requireNonNull(objecttypes, "objecttypes");
		Objects.requireNonNull(components, "components");
		return /*0011*/ new Type(
				Object.class,
				Object.class,
				objecttypes,
				components
		);
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have the given {@code
	 * objecttypes}, and should be treated as if it was the given {@code wildclass}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="fc8fbb">WILD</font>, <font color="#bea341">OBJECT</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font>
	 * </pre>
	 *
	 * @param typeclass   the class to be represented by the returned type.
	 * @param wildclass   the class that an instance of the returned type should be treated as if it
	 *                    was an instance of it.
	 * @param objecttypes mappings for other types for each specific instance.
	 * @param <T>         the type of the class represented by the returned type.
	 * @return a type that represents the given {@code typeclass}, and have the given {@code
	 * 		objecttypes}, and should be treated as if it was the given {@code wildclass}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code wildclass} or {@code
	 *                              objecttypes} is null.
	 * @throws ClassCastException   if the given {@code objecttypes} has a value that is not an
	 *                              instance of {@link Type}.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <T> Type<T> of(Class<T> typeclass, Class wildclass, Map<Object, Type> objecttypes) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(wildclass, "wildclass");
		Objects.requireNonNull(objecttypes, "objecttypes");
		return /*1110*/ new Type(
				typeclass,
				wildclass,
				objecttypes,
				new Type[0]
		);
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have the given {@code
	 * components}, and should be treated as if it was the given {@code wildclass}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="fc8fbb">WILD</font>, <font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass  the class to be represented by the returned type.
	 * @param wildclass  the class that an instance of the returned type should be treated as if it
	 *                   was an instance of it.
	 * @param components the components of the returned type.
	 * @param <T>        the type of the class represented by the returned type.
	 * @return a type that represents the given {@code typeclass}, and have the given {@code
	 * 		components}, and should be treated as if it was the given {@code wildclass}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code wildclass} or {@code
	 *                              components} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <T> Type<T> of(Class<T> typeclass, Class wildclass, Type... components) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(wildclass, "wildclass");
		Objects.requireNonNull(components, "component");
		return /*1101*/ new Type(
				typeclass,
				wildclass,
				Collections.emptyMap(),
				components
		);
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have the given {@code components}
	 * and {@code objecttypes}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="#bea341">OBJECT</font>, <font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">TYPE</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass   the class to be represented by the returned type.
	 * @param objecttypes mappings for other types for each specific instance.
	 * @param components  the components of the returned type.
	 * @param <T>         the type of the class represented by the returned type.
	 * @return a type that represents the given {@code typeclass}, and have the given {@code
	 * 		components} and {@code objecttypes}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code objecttypes} or {@code
	 *                              components} is null.
	 * @throws ClassCastException   if the given {@code objecttypes} has a value that is not an
	 *                              instance of {@link Type}.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <T> Type<T> of(Class<T> typeclass, Map<Object, Type> objecttypes, Type... components) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(objecttypes, "objecttypes");
		Objects.requireNonNull(components, "components");
		return /*1011*/ new Type(
				typeclass,
				typeclass,
				objecttypes,
				components
		);
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have the given {@code components}
	 * and {@code objecttypes}, and should be treated as if it was the given {@code wildclass}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="fc8fbb">WILD</font>, <font color="#bea341">OBJECT</font>, <font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass   the class to be represented by the returned type.
	 * @param wildclass   the class that an instance of the returned type should be treated as if
	 *                    was an instance of it.
	 * @param objecttypes mappings for other types for each specific instance.
	 * @param components  the components of the returned type.
	 * @param <T>         the type of the class represented by the returned type.
	 * @return a type that represents the given {@code typeclass}, and have the given {@code
	 * 		components} and {@code objecttypes}, and should be treated as if it was the given {@code
	 * 		wildclass}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code wildclass} or {@code
	 *                              objecttypes} or {@code components} is null.
	 * @throws ClassCastException   if the given {@code objecttypes} has a value that is not an
	 *                              instance of {@link Type}.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <T> Type<T> of(Class<T> typeclass, Class wildclass, Map<Object, Type> objecttypes, Type... components) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(wildclass, "wildclass");
		Objects.requireNonNull(objecttypes, "objecttypes");
		Objects.requireNonNull(components, "components");
		return /*1111*/ new Type(
				typeclass,
				wildclass,
				objecttypes,
				components
		);
	}

	/**
	 * Get a type that represents the class {@link Object}, and have the given {@code objecttypes}.
	 * <pre>
	 *     Type.of(<font color="#bea341">OBJECT</font>)
	 *     <font color="a5edff">Object</font><font color="#bea341">*</font>
	 * </pre>
	 *
	 * @param objecttypes mappings for other types for each specific instance.
	 * @return a type that represents the class {@link Object}, and have the given {@code
	 * 		objecttypes}.
	 * @throws NullPointerException if the given {@code objecttypes} is null.
	 * @throws ClassCastException   if the given {@code objecttypes} has a value that is not an
	 *                              instance of {@link Class}.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static Type<Object> ofc(Map<Object, Class> objecttypes) {
		Objects.requireNonNull(objecttypes, "objecttypes");
		return /*0020*/ new Type(
				Object.class,
				Object.class,
				Type.map(objecttypes),
				new Type[0]
		);
	}

	/**
	 * Get a type that represents the class {@link Object}, and have the given {@code components}.
	 * <pre>
	 *     Type.ofc(<font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">Object</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param components the components of the returned type.
	 * @return a type that represents the class {@link Object}, and have the given {@code
	 * 		components}.
	 * @throws NullPointerException if the given {@code components} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static Type<Object> ofc(Class[] components) {
		Objects.requireNonNull(components, "components");
		return /*0002*/ new Type(
				Object.class,
				Object.class,
				Collections.emptyMap(),
				Type.array(components)
		);
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have the given {@code
	 * objecttypes}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="#bea341">OBJECT</font>)
	 *     <font color="a5edff">TYPE</font><font color="#bea341">*</font>
	 * </pre>
	 *
	 * @param typeclass   the class to be represented by the returned type.
	 * @param objecttypes mappings for other types for each specific instance.
	 * @param <T>         the type of the class represented by the returned type.
	 * @return a type that represents the given {@code typeclass}, and have the given {@code
	 * 		objecttypes}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code objecttypes} is null.
	 * @throws ClassCastException   if the given {@code objecttypes} has a value that is not an
	 *                              instance of {@link Class}.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <T> Type<T> ofc(Class<T> typeclass, Map<Object, Class> objecttypes) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(objecttypes, "objecttypes");
		return /*1020*/ new Type(
				typeclass,
				typeclass,
				Type.map(objecttypes),
				new Type[0]
		);
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have the given {@code
	 * components}.
	 * <pre>
	 *     Type.ofc(<font color="a5edff">TYPE</font>, <font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">TYPE</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass  the class to be represented by the returned type.
	 * @param components the components of the returned type.
	 * @param <T>        the type of the class represented by the returned type.
	 * @return a type that represents the given {@code typeclass}, and have the given {@code
	 * 		components}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code components} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <T> Type<T> ofc(Class<T> typeclass, Class[] components) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(components, "components");
		return /*1002*/ new Type(
				typeclass,
				typeclass,
				Collections.emptyMap(),
				Type.array(components)
		);
	}

	/**
	 * Get a type that represents the class {@link Object}, and have the given {@code components}
	 * and {@code objecttypes}.
	 * <pre>
	 *     Type.of(<font color="#bea341">OBJECT</font>, <font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">Object</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param objecttypes mappings for other types for each specific instance.
	 * @param components  the components of the returned type.
	 * @return a type that represents the class {@link Object}, and have the given {@code
	 * 		components} and {@code objecttypes}.
	 * @throws NullPointerException if the given {@code objecttypes} or {@code components} is null.
	 * @throws ClassCastException   if the given {@code objecttypes} has a value that is not an
	 *                              instance of {@link Class}.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static Type<Object> ofc(Map<Object, Class> objecttypes, Class... components) {
		Objects.requireNonNull(objecttypes, "objecttypes");
		Objects.requireNonNull(components, "components");
		return /*0022*/ new Type(
				Object.class,
				Object.class,
				Type.map(objecttypes),
				Type.array(components)
		);
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have the given {@code
	 * objecttypes}, and should be treated as if it was the given {@code wildclass}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="fc8fbb">WILD</font>, <font color="#bea341">OBJECT</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font>
	 * </pre>
	 *
	 * @param typeclass   the class to be represented by the returned type.
	 * @param wildclass   the class that an instance of the returned type should be treated as if it
	 *                    was an instance of it.
	 * @param objecttypes mappings for other types for each specific instance.
	 * @param <T>         the type of the class represented by the returned type.
	 * @return a type that represents the given {@code typeclass}, and have the given {@code
	 * 		objecttypes}, and should be treated as if it was the given {@code wildclass}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code wildclass} or {@code
	 *                              objecttypes} is null.
	 * @throws ClassCastException   if the given {@code objecttypes} has a value that is not an
	 *                              instance of {@link Class}.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <T> Type<T> ofc(Class<T> typeclass, Class wildclass, Map<Object, Class> objecttypes) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(wildclass, "wildclass");
		Objects.requireNonNull(objecttypes, "objecttypes");
		return /*1120*/ new Type(
				typeclass,
				wildclass,
				Type.map(objecttypes),
				new Type[0]
		);
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have the given {@code
	 * components}, and should be treated as if it was the given {@code wildclass}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="fc8fbb">WILD</font>, <font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass  the class to be represented by the returned type.
	 * @param wildclass  the class that an instance of the returned type should be treated as if it
	 *                   was an instance of it.
	 * @param components the components of the returned type.
	 * @param <T>        the type of the class represented by the returned type.
	 * @return a type that represents the given {@code typeclass}, and have the given {@code
	 * 		components}, and should be treated as if it was the given {@code wildclass}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code wildclass} or {@code
	 *                              components} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <T> Type<T> ofc(Class<T> typeclass, Class wildclass, Class... components) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(wildclass, "wildclass");
		Objects.requireNonNull(components, "component");
		return /*1102*/ new Type(
				typeclass,
				wildclass,
				Collections.emptyMap(),
				Type.array(components)
		);
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have the given {@code components}
	 * and {@code objecttypes}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="#bea341">OBJECT</font>, <font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">TYPE</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass   the class to be represented by the returned type.
	 * @param objecttypes mappings for other types for each specific instance.
	 * @param components  the components of the returned type.
	 * @param <T>         the type of the class represented by the returned type.
	 * @return a type that represents the given {@code typeclass}, and have the given {@code
	 * 		components} and {@code objecttypes}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code objecttypes} or {@code
	 *                              components} is null.
	 * @throws ClassCastException   if the given {@code objecttypes} has a value that is not an
	 *                              instance of {@link Class}.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <T> Type<T> ofc(Class<T> typeclass, Map<Object, Class> objecttypes, Class... components) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(objecttypes, "objecttypes");
		Objects.requireNonNull(components, "components");
		return /*1022*/ new Type(
				typeclass,
				typeclass,
				Type.map(objecttypes),
				Type.array(components)
		);
	}

	/**
	 * Get a type that represents the given {@code typeclass}, and have the given {@code components}
	 * and {@code objecttypes}, and should be treated as if it was the given {@code wildclass}.
	 * <pre>
	 *     Type.of(<font color="a5edff">TYPE</font>, <font color="fc8fbb">WILD</font>, <font color="#bea341">OBJECT</font>, <font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass   the class to be represented by the returned type.
	 * @param wildclass   the class that an instance of the returned type should be treated as if
	 *                    was an instance of it.
	 * @param objecttypes mappings for other types for each specific instance.
	 * @param components  the components of the returned type.
	 * @param <T>         the type of the class represented by the returned type.
	 * @return a type that represents the given {@code typeclass}, and have the given {@code
	 * 		components} and {@code objecttypes}, and should be treated as if it was the given {@code
	 * 		wildclass}.
	 * @throws NullPointerException if the given {@code typeclass} or {@code wildclass} or {@code
	 *                              objecttypes} or {@code components} is null.
	 * @throws ClassCastException   if the given {@code objecttypes} has a value that is not an
	 *                              instance of {@link Class}.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <T> Type<T> ofc(Class<T> typeclass, Class wildclass, Map<Object, Class> objecttypes, Class... components) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(wildclass, "wildclass");
		Objects.requireNonNull(objecttypes, "objecttypes");
		Objects.requireNonNull(components, "components");
		return /*1122*/ new Type(
				typeclass,
				wildclass,
				Type.map(objecttypes),
				Type.array(components)
		);
	}

	@Override
	public boolean equals(Object object) {
		return this == object ||
			   object instanceof Type &&
			   Objects.equals(((Type) object).typeclass, this.typeclass) &&
			   Objects.equals(((Type) object).wildclass, this.wildclass) &&
			   Objects.equals(((Type) object).objecttypes, this.objecttypes) &&
			   Arrays.equals(((Type) object).components, this.components);
	}

	@Override
	public String getTypeName() {
		StringBuilder builder = new StringBuilder();
		this.getTypeName0(builder, Collections.newSetFromMap(new IdentityHashMap()));
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return this.wildclass.hashCode() ^
			   this.typeclass.hashCode() ^
			   this.objecttypes.hashCode() ^
			   Arrays.hashCode(this.components);
	}

	@Override
	public String toString() {
		return "type " + this.getName();
	}

	/**
	 * Get the component of this type that has the given {@code component} index.
	 *
	 * @param component the index of the targeted component.
	 * @return the component of this type that has the given {@code component} index. or null if no
	 * 		such component in this type with the given {@code component} index.
	 * @since 0.1.5 ~2020.08.11
	 */
	public Type component(int component) {
		return component >= 0 &&
			   component < this.components.length ?
			   this.components[component] :
			   null;
	}

	/**
	 * Get a clone of the array holding all the components of this type.
	 *
	 * @return a clone of the array holding all the components of this type.
	 * @since 0.1.5 ~2020.08.11
	 */
	public Type[] components() {
		return this.components.clone();
	}

	/**
	 * Get the name of this type.
	 * <pre>
	 *     Type.getName()
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 * <pre>
	 *     Type.of(ArrayList.class, Set.class, Integer.class).getName()
	 *     <font color="a5edff">java.util.ArrayList</font><font color="fc8fbb">:java.util.Set</font><font color="d3c4ff">&lt;java.lang.Integer&gt;</font>
	 * </pre>
	 *
	 * @return the name of this type.
	 * @see Type#forName(String)
	 * @since 0.1.5 ~2020.08.11
	 */
	public String getName() {
		StringBuilder builder = new StringBuilder();
		this.getName0(builder, Collections.newSetFromMap(new IdentityHashMap()));
		return builder.toString();
	}

	/**
	 * Get the type instance of this type that has been specified for the given {@code object}.
	 *
	 * @param object the object to get the type instance of this that has been specified for it.
	 * @return the type instance of this type that has been specified for the given {@code object}.
	 * 		Or this type instance, if no type has been specified for the given {@code object}.
	 * @since 0.1.5 ~2020.08.11
	 */
	public Type getObjecttype(Object object) {
		Type type = this.objecttypes.get(object);
		return type == null ? this : type;
	}

	/**
	 * Get an unmodifiable view of the object-type mappings of this type.
	 *
	 * @return an unmodifiable view of the objecttypes of this type.
	 * @since 0.1.5 ~2020.08.11
	 */
	public Map<Object, Type> getObjecttypes() {
		return Collections.unmodifiableMap(this.objecttypes);
	}

	/**
	 * Get the simple name of this type. The name contains the simple name of the {@code wildclass},
	 * {@code typeclass}, and each {@code componentType} of each {@code component} in this type.
	 *
	 * @return the simple name of this type.
	 * @since 0.1.5 ~2020.08.11
	 */
	public String getSimpleName() {
		StringBuilder builder = new StringBuilder();
		this.getSimpleName0(builder, Collections.newSetFromMap(new IdentityHashMap()));
		return builder.toString();
	}

	/**
	 * Get the class represented by this type.
	 *
	 * @return the class represented by this type.
	 * @since 0.1.5 ~2020.08.11
	 */
	public Class<T> getTypeclass() {
		return this.typeclass;
	}

	/**
	 * Get the class that an instance of this type should be treated as if it was an instance of
	 * it.
	 *
	 * @return the class that an instance of this type should be treated as if it was an instance of
	 * 		it.
	 * @since 0.1.5 ~2020.08.11
	 */
	public Class getWildclass() {
		return this.wildclass;
	}

	/**
	 * Get a clone of this type.
	 * <pre>
	 *     type.with()
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @return a clone of this type.
	 * @see Type#of()
	 * @since 0.1.5 ~2020.08.11
	 */
	public Type<T> with() {
		return /*0000*/ new Type(
				this.typeclass,
				this.wildclass,
				this.objecttypes,
				this.components
		);
	}

	/**
	 * Get a clone of this type that represents the given {@code typeclass}.
	 * <pre>
	 *     type.with(<font color="a5edff">TYPE</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass the class to be represented by the returned type (null replaced by the
	 *                  typeclass of this type).
	 * @param <U>       the type of the class represented by the returned type.
	 * @return a clone of this type that represents the given {@code typeclass}.
	 * @see Type#of(Class)
	 * @since 0.1.5 ~2020.08.11
	 */
	public <U> Type<U> with(Class<U> typeclass) {
		return /*1000*/ new Type(
				typeclass == null ? this.typeclass : typeclass,
				this.wildclass,
				this.objecttypes,
				this.components
		);
	}

	/**
	 * Get a clone of this type that has the given {@code objecttypes}.
	 * <pre>
	 *     type.with(<font color="#bea341">OBJECT</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param objecttypes mappings for other types for each specific instance (null replaced by the
	 *                    objecttypes of this type).
	 * @return a clone of this type that have the given {@code objecttypes}.
	 * @throws ClassCastException if the given {@code objecttypes} has a value that is not an
	 *                            instance of {@link Type}.
	 * @see Type#of(Map)
	 * @since 0.1.5 ~2020.08.11
	 */
	public Type<T> with(Map<Object, Type> objecttypes) {
		return /*0010*/ new Type(
				this.typeclass,
				this.wildclass,
				objecttypes == null ? this.objecttypes : objecttypes,
				this.components
		);
	}

	/**
	 * Get a clone of this type that has the given {@code components}.
	 * <pre>
	 *     type.with(<font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param components the components of the returned type (null replaced by the components of
	 *                   this type).
	 * @return a clone of this type that have the given {@code components}.
	 * @see Type#of(Type[])
	 * @since 0.1.5 ~2020.08.11
	 */
	public Type<T> with(Type... components) {
		return /*0001*/ new Type(
				this.typeclass,
				this.wildclass,
				this.objecttypes,
				components == null ? this.components : components
		);
	}

	/**
	 * Get a clone of this type that represents the given {@code typeclass}, and should be treated
	 * as if it was the given {@code wildclass}.
	 * <pre>
	 *     type.with(<font color="a5edff">TYPE</font>, <font color="fc8fbb">WILD</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass the class to be represented by the returned type (null replaced by the
	 *                  typeclass of this type).
	 * @param wildclass the class that an instance of the returned type should be treated as if it
	 *                  was an instance of it (null replaced by the wildclass of this type).
	 * @param <U>       the type of the class represented by the returned type.
	 * @return a clone of this type that represents the given {@code typeclass}, and should be
	 * 		treated as if it was the given {@code wildclass}.
	 * @see Type#of(Class, Class)
	 * @since 0.1.5 ~2020.08.11
	 */
	public <U> Type<U> with(Class<U> typeclass, Class wildclass) {
		return /*1100*/ new Type(
				typeclass == null ? this.typeclass : typeclass,
				wildclass == null ? this.wildclass : wildclass,
				this.objecttypes,
				this.components
		);
	}

	/**
	 * Get a clone of this type that represents the given {@code typeclass}, and have the given
	 * {@code objecttypes}.
	 * <pre>
	 *     type.with(<font color="a5edff">TYPE</font>, <font color="#bea341">OBJECT</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass   the class to be represented by the returned type (null replaced by the
	 *                    typeclass of this type).
	 * @param objecttypes mappings for other types for each specific instance (null replaced by the
	 *                    objecttypes of this type).
	 * @param <U>         the type of the class represented by the returned type.
	 * @return a clone of this type that represents the given {@code typeclass}, and have the given
	 *        {@code objecttypes}.
	 * @throws ClassCastException if the given {@code objecttypes} has a value that is not an
	 *                            instance of {@link Type}.
	 * @see Type#of(Class, Map)
	 * @since 0.1.5 ~2020.08.11
	 */
	public <U> Type<U> with(Class<U> typeclass, Map<Object, Type> objecttypes) {
		return /*1010*/ new Type(
				typeclass == null ? this.typeclass : typeclass,
				this.wildclass,
				objecttypes == null ? this.objecttypes : objecttypes,
				this.components
		);
	}

	/**
	 * Get a clone of this type that represents the given {@code typeclass}, and have the given
	 * {@code components}.
	 * <pre>
	 *     type.with(<font color="a5edff">TYPE</font>, <font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass  the class to be represented by the returned type (null replaced by the
	 *                   typeclass of this type).
	 * @param components the components of the returned type (null replaced by the components of
	 *                   this type).
	 * @param <U>        the type of the class represented by the returned type.
	 * @return a clone of this type that represents the given {@code typeclass}, and have the given
	 *        {@code components}.
	 * @see Type#of(Class, Type[])
	 * @since 0.1.5 ~2020.08.11
	 */
	public <U> Type<U> with(Class<U> typeclass, Type... components) {
		return /*1001*/ new Type(
				typeclass == null ? this.typeclass : typeclass,
				this.wildclass,
				this.objecttypes,
				components == null ? this.components : components
		);
	}

	/**
	 * Get a clone of this type that has the given {@code components} and {@code objecttypes}.
	 * <pre>
	 *     type.with(<font color="#bea341">OBJECT</font>, <font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param objecttypes mappings for other types for each specific instance (null replaced by the
	 *                    objecttypes of this type).
	 * @param components  the components of the returned type (null replaced by the components of
	 *                    this type).
	 * @return a clone of this type that has the given {@code components} and {@code objecttypes}.
	 * @throws ClassCastException if the given {@code objecttypes} has a value that is not an
	 *                            instance of {@link Type}.
	 * @see Type#of(Map, Type[])
	 * @since 0.1.5 ~2020.08.11
	 */
	public Type<T> with(Map<Object, Type> objecttypes, Type... components) {
		return /*0011*/ new Type(
				this.typeclass,
				this.wildclass,
				objecttypes == null ? this.objecttypes : objecttypes,
				components == null ? this.components : components
		);
	}

	/**
	 * Get a clone of this type that represents the given {@code typeclass}, and have the given
	 * {@code objecttypes}, and should be treated as if it was the given {@code wildclass}.
	 * <pre>
	 *     type.with(<font color="a5edff">TYPE</font>, <font color="fc8fbb">WILD</font>, <font color="#bea341">OBJECT</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass   the class to be represented by the returned type (null replaced by the
	 *                    typeclass of this type).
	 * @param wildclass   the class that an instance of the returned type should be treated as if it
	 *                    was an instance of it (null replaced by the wildclass of this type).
	 * @param objecttypes mappings for other types for each specific instance (null replaced by the
	 *                    objecttypes of this type).
	 * @param <U>         the type of the class represented by the returned type.
	 * @return a clone of this type that represents the given {@code typeclass}, and have the given
	 *        {@code objecttypes}, and should be treated as if it was the given {@code wildclass}.
	 * @throws ClassCastException if the given {@code objecttypes} has a value that is not an
	 *                            instance of {@link Type}.
	 * @see Type#of(Class, Class, Map)
	 * @since 0.1.5 ~2020.08.11
	 */
	public <U> Type<U> with(Class<U> typeclass, Class wildclass, Map<Object, Type> objecttypes) {
		return /*1110*/ new Type(
				typeclass == null ? this.typeclass : typeclass,
				wildclass == null ? this.wildclass : wildclass,
				objecttypes == null ? this.objecttypes : objecttypes,
				this.components
		);
	}

	/**
	 * Get a clone of this type that represents the given {@code typeclass}, and have the given
	 * {@code components}, and should be treated as if it was the given {@code wildclass}.
	 * <pre>
	 *     type.with(<font color="a5edff">TYPE</font>, <font color="fc8fbb">WILD</font>, <font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass  the class to be represented by the returned type (null replaced by the
	 *                   typeclass of this type).
	 * @param wildclass  the class that an instance of the returned type should be treated as if it
	 *                   was an instance of it (null replaced by the wildclass of this type).
	 * @param components the components of the returned type (null replaced by the components of
	 *                   this type).
	 * @param <U>        the type of the class represented by the returned type.
	 * @return a clone of this type that represents the given {@code typeclass}, and have the given
	 *        {@code components}, and should be treated as if it was the given {@code wildclass}.
	 * @see Type#of(Class, Class, Type[])
	 * @since 0.1.5 ~2020.08.11
	 */
	public <U> Type<U> with(Class<U> typeclass, Class wildclass, Type... components) {
		return /*1101*/ new Type(
				typeclass == null ? this.typeclass : typeclass,
				wildclass == null ? this.wildclass : wildclass,
				this.objecttypes,
				components == null ? this.components : components
		);
	}

	/**
	 * Get a clone of this type that represents the given {@code typeclass}, and have the given
	 * {@code components} and {@code objecttypes}.
	 * <pre>
	 *     type.with(<font color="a5edff">TYPE</font>, <font color="#bea341">OBJECT</font>, <font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass   the class to be represented by the returned type (null replaced by the
	 *                    typeclass of this type).
	 * @param objecttypes mappings for other types for each specific instance (null replaced by the
	 *                    objecttypes of this type).
	 * @param components  the components of the returned type (null replaced by the components of
	 *                    this type).
	 * @param <U>         the type of the class represented by the returned type.
	 * @return a clone of this type that represents the given {@code typeclass}, and have the given
	 *        {@code components} and {@code objecttypes}.
	 * @throws ClassCastException if the given {@code objecttypes} has a value that is not an
	 *                            instance of {@link Type}.
	 * @see Type#of(Class, Map, Type[])
	 * @since 0.1.5 ~2020.08.11
	 */
	public <U> Type<U> with(Class<U> typeclass, Map<Object, Type> objecttypes, Type... components) {
		return /*1011*/ new Type(
				typeclass == null ? this.typeclass : typeclass,
				this.wildclass,
				objecttypes == null ? this.objecttypes : objecttypes,
				components == null ? this.components : components
		);
	}

	/**
	 * Get a clone of this type that has the given {@code objecttypes}.
	 * <pre>
	 *     type.with(<font color="#bea341">OBJECT</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param objecttypes mappings for other types for each specific instance (null replaced by the
	 *                    objecttypes of this type).
	 * @return a clone of this type that have the given {@code objecttypes}.
	 * @throws ClassCastException if the given {@code objecttypes} has a value that is not an
	 *                            instance of {@link Class}.
	 * @see Type#ofc(Map)
	 * @since 0.1.5 ~2020.08.11
	 */
	public Type<T> withc(Map<Object, Class> objecttypes) {
		return /*0020*/ new Type(
				this.typeclass,
				this.wildclass,
				objecttypes == null ? this.objecttypes : Type.map(objecttypes),
				this.components
		);
	}

	/**
	 * Get a clone of this type that has the given {@code components}.
	 * <pre>
	 *     type.with(<font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param components the components of the returned type (null replaced by the components of
	 *                   this type).
	 * @return a clone of this type that have the given {@code components}.
	 * @see Type#ofc(Class[])
	 * @since 0.1.5 ~2020.08.11
	 */
	public Type<T> withc(Class[] components) {
		return /*0002*/ new Type(
				this.typeclass,
				this.wildclass,
				this.objecttypes,
				components == null ? this.components : Type.array(components)
		);
	}

	/**
	 * Get a clone of this type that represents the given {@code typeclass}, and have the given
	 * {@code objecttypes}.
	 * <pre>
	 *     type.with(<font color="a5edff">TYPE</font>, <font color="#bea341">OBJECT</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass   the class to be represented by the returned type (null replaced by the
	 *                    typeclass of this type).
	 * @param objecttypes mappings for other types for each specific instance (null replaced by the
	 *                    objecttypes of this type).
	 * @param <U>         the type of the class represented by the returned type.
	 * @return a clone of this type that represents the given {@code typeclass}, and have the given
	 *        {@code objecttypes}.
	 * @throws ClassCastException if the given {@code objecttypes} has a value that is not an
	 *                            instance of {@link Class}.
	 * @see Type#ofc(Class, Map)
	 * @since 0.1.5 ~2020.08.11
	 */
	public <U> Type<U> withc(Class<U> typeclass, Map<Object, Class> objecttypes) {
		return /*1020*/ new Type(
				typeclass == null ? this.typeclass : typeclass,
				this.wildclass,
				objecttypes == null ? this.objecttypes : Type.map(objecttypes),
				this.components
		);
	}

	/**
	 * Get a clone of this type that represents the given {@code typeclass}, and have the given
	 * {@code components}.
	 * <pre>
	 *     type.with(<font color="a5edff">TYPE</font>, <font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass  the class to be represented by the returned type (null replaced by the
	 *                   typeclass of this type).
	 * @param components the components of the returned type (null replaced by the components of
	 *                   this type).
	 * @param <U>        the type of the class represented by the returned type.
	 * @return a clone of this type that represents the given {@code typeclass}, and have the given
	 *        {@code components}.
	 * @see Type#ofc(Class, Class[])
	 * @since 0.1.5 ~2020.08.11
	 */
	public <U> Type<U> withc(Class<U> typeclass, Class[] components) {
		return /*1002*/ new Type(
				typeclass == null ? this.typeclass : typeclass,
				this.wildclass,
				this.objecttypes,
				components == null ? this.components : Type.array(components)
		);
	}

	/**
	 * Get a clone of this type that has the given {@code components} and {@code objecttypes}.
	 * <pre>
	 *     type.with(<font color="#bea341">OBJECT</font>, <font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param objecttypes mappings for other types for each specific instance (null replaced by the
	 *                    objecttypes of this type).
	 * @param components  the components of the returned type (null replaced by the components of
	 *                    this type).
	 * @return a clone of this type that has the given {@code components} and {@code objecttypes}.
	 * @throws ClassCastException if the given {@code objecttypes} has a value that is not an
	 *                            instance of {@link Class}.
	 * @see Type#ofc(Map, Class[])
	 * @since 0.1.5 ~2020.08.11
	 */
	public Type<T> withc(Map<Object, Class> objecttypes, Class... components) {
		return /*0022*/ new Type(
				this.typeclass,
				this.wildclass,
				objecttypes == null ? this.objecttypes : Type.map(objecttypes),
				components == null ? this.components : Type.array(components)
		);
	}

	/**
	 * Get a clone of this type that represents the given {@code typeclass}, and have the given
	 * {@code objecttypes}, and should be treated as if it was the given {@code wildclass}.
	 * <pre>
	 *     type.with(<font color="a5edff">TYPE</font>, <font color="fc8fbb">WILD</font>, <font color="#bea341">OBJECT</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass   the class to be represented by the returned type (null replaced by the
	 *                    typeclass of this type).
	 * @param wildclass   the class that an instance of the returned type should be treated as if it
	 *                    was an instance of it (null replaced by the wildclass of this type).
	 * @param objecttypes mappings for other types for each specific instance (null replaced by the
	 *                    objecttypes of this type).
	 * @param <U>         the type of the class represented by the returned type.
	 * @return a clone of this type that represents the given {@code typeclass}, and have the given
	 *        {@code objecttypes}, and should be treated as if it was the given {@code wildclass}.
	 * @throws ClassCastException if the given {@code objecttypes} has a value that is not an
	 *                            instance of {@link Class}.
	 * @see Type#of(Class, Class, Map)
	 * @since 0.1.5 ~2020.08.11
	 */
	public <U> Type<U> withc(Class<U> typeclass, Class wildclass, Map<Object, Class> objecttypes) {
		return /*1120*/ new Type(
				typeclass == null ? this.typeclass : typeclass,
				wildclass == null ? this.wildclass : wildclass,
				objecttypes == null ? this.objecttypes : Type.map(objecttypes),
				this.components
		);
	}

	/**
	 * Get a clone of this type that represents the given {@code typeclass}, and have the given
	 * {@code components}, and should be treated as if it was the given {@code wildclass}.
	 * <pre>
	 *     type.with(<font color="a5edff">TYPE</font>, <font color="fc8fbb">WILD</font>, <font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass  the class to be represented by the returned type (null replaced by the
	 *                   typeclass of this type).
	 * @param wildclass  the class that an instance of the returned type should be treated as if it
	 *                   was an instance of it (null replaced by the wildclass of this type).
	 * @param components the components of the returned type (null replaced by the components of
	 *                   this type).
	 * @param <U>        the type of the class represented by the returned type.
	 * @return a clone of this type that represents the given {@code typeclass}, and have the given
	 *        {@code components}, and should be treated as if it was the given {@code wildclass}.
	 * @see Type#ofc(Class, Class, Class[])
	 * @since 0.1.5 ~2020.08.11
	 */
	public <U> Type<U> withc(Class<U> typeclass, Class wildclass, Class... components) {
		return /*1102*/ new Type(
				typeclass == null ? this.typeclass : typeclass,
				wildclass == null ? this.wildclass : wildclass,
				this.objecttypes,
				components == null ? this.components : Type.array(components)
		);
	}

	/**
	 * Get a clone of this type that represents the given {@code typeclass}, and have the given
	 * {@code components} and {@code objecttypes}.
	 * <pre>
	 *     type.with(<font color="a5edff">TYPE</font>, <font color="#bea341">OBJECT</font>, <font color="d3c4ff">COMPONENTS…</font>)
	 *     <font color="a5edff">TYPE</font><font color="fc8fbb">:WILD</font><font color="#bea341">*</font><font color="d3c4ff">&lt;COMPONENTS…&gt;</font>
	 * </pre>
	 *
	 * @param typeclass   the class to be represented by the returned type (null replaced by the
	 *                    typeclass of this type).
	 * @param objecttypes mappings for other types for each specific instance (null replaced by the
	 *                    objecttypes of this type).
	 * @param components  the components of the returned type (null replaced by the components of
	 *                    this type).
	 * @param <U>         the type of the class represented by the returned type.
	 * @return a clone of this type that represents the given {@code typeclass}, and have the given
	 *        {@code components} and {@code objecttypes}.
	 * @throws ClassCastException if the given {@code objecttypes} has a value that is not an
	 *                            instance of {@link Class}.
	 * @see Type#ofc(Class, Map, Class[])
	 * @since 0.1.5 ~2020.08.11
	 */
	public <U> Type<U> withc(Class<U> typeclass, Map<Object, Class> objecttypes, Class... components) {
		return /*1022*/ new Type(
				typeclass == null ? this.typeclass : typeclass,
				this.wildclass,
				objecttypes == null ? this.objecttypes : Type.map(objecttypes),
				components == null ? this.components : Type.array(components)
		);
	}

	/**
	 * Get the name of this type skipping the components of this type that has been seen before and
	 * stored at the given {@code dejaVu}.
	 *
	 * @param builder the builder to append the name of this type to.
	 * @param dejaVu  the components that has been seen before.
	 * @throws NullPointerException if the given {@code builder} is null.
	 * @since 0.1.5 ~2020.08.12
	 */
	private void getName0(StringBuilder builder, Set<Type> dejaVu) {
		Objects.requireNonNull(dejaVu, "dejaVu");

		dejaVu.add(this);

		builder.append(this.typeclass.getName());

		if (this.wildclass != this.typeclass)
			builder.append(":")
					.append(this.wildclass.getName());

		if (!this.objecttypes.isEmpty())
			builder.append("*");

		if (this.components.length == 0)
			return;

		builder.append("<");

		int i = 0;
		while (true) {
			Type component = this.components[i];

			if (component == null || dejaVu.contains(component))
				builder.append("?");
			else
				component.getName0(builder, dejaVu);

			if (++i < this.components.length) {
				builder.append(", ");
				continue;
			}

			builder.append(">");
			break;
		}

		dejaVu.remove(this);
	}

	/**
	 * Get the simple name of this type skipping the components of this type that has been seen
	 * before and stored at the given {@code dejaVu}.
	 *
	 * @param builder the builder to append the simple name of this type to.
	 * @param dejaVu  the components that has been seen before.
	 * @throws NullPointerException if the given {@code builder} is null.
	 * @since 0.1.5 ~2020.08.12
	 */
	private void getSimpleName0(StringBuilder builder, Set<Type> dejaVu) {
		Objects.requireNonNull(dejaVu, "dejaVu");

		dejaVu.add(this);

		builder.append(this.typeclass.getSimpleName());

		if (this.wildclass != this.typeclass)
			builder.append(":")
					.append(this.wildclass.getSimpleName());

		if (!this.objecttypes.isEmpty())
			builder.append("*");

		if (this.components.length == 0)
			return;

		builder.append("<");

		int i = 0;
		while (true) {
			Type component = this.components[i];

			if (component == null || dejaVu.contains(component))
				builder.append("?");
			else
				component.getSimpleName0(builder, dejaVu);

			if (++i < this.components.length) {
				builder.append(", ");
				continue;
			}

			builder.append(">");
			break;
		}

		dejaVu.remove(this);
	}

	/**
	 * Get the type name of this type skipping the components of this type that has been seen before
	 * and stored at the given {@code dejaVu}.
	 *
	 * @param builder the builder to append the type name of this type to.
	 * @param dejaVu  the components that has been seen before.
	 * @throws NullPointerException if the given {@code builder} is null.
	 * @since 0.1.5 ~2020.08.12
	 */
	private void getTypeName0(StringBuilder builder, Set<Type> dejaVu) {
		Objects.requireNonNull(dejaVu, "dejaVu");

		dejaVu.add(this);

		builder.append(this.typeclass.getTypeName());

		if (this.wildclass != this.typeclass)
			builder.append(":")
					.append(this.wildclass.getTypeName());

		if (!this.objecttypes.isEmpty())
			builder.append("*");

		if (this.components.length == 0)
			return;

		builder.append("<");

		int i = 0;
		while (true) {
			Type component = this.components[i];

			if (component == null || dejaVu.contains(component))
				builder.append("?");
			else
				component.getTypeName0(builder, dejaVu);

			if (++i < this.components.length) {
				builder.append(", ");
				continue;
			}

			builder.append(">");
			break;
		}

		dejaVu.remove(this);
	}

	/**
	 * Initialize this type with the given parameters. It is illegal to initialize a type twice, yet
	 * this method will never know such thing.
	 *
	 * @param typeclass   the class to be represented by this type.
	 * @param wildclass   the class that an instance of this type should be treated as if was an
	 *                    instance of it.
	 * @param objecttypes mappings for other types for each specific instance.
	 * @param components  the components of this type.
	 * @throws NullPointerException if the given {@code typeclass} or {@code wildclass} or {@code
	 *                              objecttypes} or {@code components} is null.
	 * @throws ClassCastException   if the given {@code objecttypes} has a value that is not an
	 *                              instance of {@link Type}.
	 * @since 2020.08.12
	 */
	private void init(Class<T> typeclass, Class wildclass, Map<Object, Type> objecttypes, Type[] components) {
		Objects.requireNonNull(typeclass, "typeclass");
		Objects.requireNonNull(wildclass, "wildclass");
		Objects.requireNonNull(objecttypes, "objecttypes");
		Objects.requireNonNull(components, "components");
		this.typeclass = typeclass;
		this.wildclass = wildclass;
		this.objecttypes = new ObjectArray(objecttypes).map();
		this.components = new ObjectArray<>(components).array();

		//no cheating ;P
		this.objecttypes.values().forEach(Type.class::cast);
	}

	/**
	 * A builder to allow taking full control over what a type should be like more easily and
	 * securely. Since the {@link Type} class should be a reliably constant type representation.
	 *
	 * @param <T> the type of the typeclass.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.11
	 */
	public static class Builder<T> implements Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 3541772414490825518L;

		/**
		 * The {@link Type#components} of the type returned by the next calls to {@link #build()}.
		 *
		 * @since 0.1.5 ~2020.08.11
		 */
		public List<Builder> components;
		/**
		 * The {@link Type#objecttypes} of the type returned by the next calls to {@link #build()}.
		 *
		 * @since 0.1.5 ~2020.08.11
		 */
		public Map<Object, Builder> objecttypes;
		/**
		 * The {@link Type#typeclass} of the type returned by the next calls to {@link #build()}.
		 *
		 * @since 0.1.5 ~2020.08.11
		 */
		public Class<T> typeclass;
		/**
		 * The {@link Type#wildclass} of the type returned by the next calls to {@link #build()}.
		 *
		 * @since 0.1.5 ~2020.08.11
		 */
		public Class wildclass;

		/**
		 * Construct a new builder with all null variables.
		 *
		 * @since 0.1.5 ~2020.08.11
		 */
		public Builder() {
		}

		/**
		 * Construct a new builder with the variables taken from the given {@code type}.
		 *
		 * @param type the type to take its variables as the variables of this builder.
		 * @throws NullPointerException if the given {@code type} is null.
		 * @since 0.1.5 ~2020.08.11
		 */
		public Builder(Type<T> type) {
			Objects.requireNonNull(type, "type");
			this.typeclass = type.typeclass;
			this.wildclass = type.wildclass;
			this.objecttypes = Builder.map(type.objecttypes);
			this.components = Arrays.asList(Builder.array(type.components));
		}

		/**
		 * Create a new array of builders from the given {@code array}.
		 *
		 * @param array the source array.
		 * @return a new array of builders from the given {@code array}. Or null if the given {@code
		 * 		array} is null.
		 * @since 0.1.5 ~2020.08.11
		 */
		public static Builder[] array(Type... array) {
			if (array == null)
				return null;

			Builder[] product = new Builder[array.length];

			for (int i = 0; i < array.length; i++) {
				Type element = array[i];

				if (element != null)
					product[i] = new Builder(element);
			}

			return product;
		}

		/**
		 * Create a new map of objects and builders from the given {@code map}.
		 *
		 * @param map the source map.
		 * @return a new map of objects and builders from the given {@code map}. Or null if the
		 * 		given {@code map} is null.
		 * @throws ClassCastException if the given {@code map} has a value that is not an instance
		 *                            of {@link Type}.
		 * @since 0.1.5 ~2020.08.11
		 */
		public static Map<Object, Builder> map(Map<Object, Type> map) {
			if (map == null)
				return null;

			Map product = new HashMap(map.size());

			for (Map.Entry<Object, Type> entry : map.entrySet()) {
				Object key = entry.getKey();
				Type value = entry.getValue();

				if (value != null)
					product.put(key, new Builder(value));
			}

			return product;
		}

		@Override
		public boolean equals(Object object) {
			return this == object ||
				   object instanceof Builder &&
				   Objects.equals(((Builder) object).typeclass, this.typeclass) &&
				   Objects.equals(((Builder) object).wildclass, this.wildclass) &&
				   Objects.equals(((Builder) object).objecttypes, this.objecttypes) &&
				   Objects.equals(((Builder) object).components, this.components);
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(this.wildclass) ^
				   Objects.hashCode(this.typeclass) ^
				   Objects.hashCode(this.objecttypes) ^
				   Objects.hashCode(this.components);
		}

		@Override
		public String toString() {
			return "Builder{" +
				   "components=" + Arrays.toString(this.components) +
				   ", objecttypes=" + this.objecttypes +
				   ", typeclass=" + this.typeclass +
				   ", wildclass=" + this.wildclass +
				   "}";
		}

		/**
		 * Build this builder.
		 *
		 * @return a type from building this builder.
		 * @throws ClassCastException if {@link Builder#objecttypes} has a value that is not an
		 *                            instance of {@link Builder}.
		 * @since 0.1.5 ~2020.08.11
		 */
		public Type<T> build() {
			return this.build(new IdentityHashMap());
		}

		/**
		 * Get the current set source for the components.
		 *
		 * @return the current set source for the components.
		 * @since 0.1.5 ~2020.08.11
		 */
		public List<Builder> getComponents() {
			return this.components;
		}

		/**
		 * Get the current set source for the objecttypes.
		 *
		 * @return the current set source for the objecttypes.
		 * @since 0.1.5 ~2020.08.11
		 */
		public Map<Object, Builder> getObjecttypes() {
			return this.objecttypes;
		}

		/**
		 * Get the current set typeclass.
		 *
		 * @return the current set typeclass.
		 * @since 0.1.5 ~2020.08.11
		 */
		public Class<T> getTypeclass() {
			return this.typeclass;
		}

		/**
		 * Get the current set wildclass.
		 *
		 * @return the current set wildclass.
		 * @since 0.1.5 ~2020.08.11
		 */
		public Class getWildclass() {
			return this.wildclass;
		}

		/**
		 * Set the {@link Type#components} for the type returned from the next {@link #build()}
		 * calls to be the result of computing the given {@code components}.
		 *
		 * @param components the source components for the components to be set.
		 * @return this.
		 * @since 0.1.5 ~2020.08.11
		 */
		public Builder<T> setComponents(List<Builder> components) {
			this.components = components;
			return this;
		}

		/**
		 * Set the {@link Type#objecttypes} for the type returned from the next {@link #build()}
		 * calls to be the result of computing the given {@code objecttypes}.
		 *
		 * @param objecttypes the source objecttypes for the objecttypes to be set.
		 * @return this.
		 * @since 0.1.5 ~2020.08.11
		 */
		public Builder<T> setObjecttypes(Map<Object, Builder> objecttypes) {
			this.objecttypes = objecttypes;
			return this;
		}

		/**
		 * Set the {@link Type#typeclass} for the type returned from the next {@link #build()} calls
		 * to be the given {@code typeclass}.
		 *
		 * @param typeclass the typeclass to be set.
		 * @return this.
		 * @since 0.1.5 ~2020.08.11
		 */
		public Builder<T> setTypeclass(Class<T> typeclass) {
			this.typeclass = typeclass;
			return this;
		}

		/**
		 * Set the {@link Type#wildclass} for the type returned from the next {@link #build()} calls
		 * to be the given {@code wildclass}.
		 *
		 * @param wildclass the wildclass to be set.
		 * @return this.
		 * @since 0.1.5 ~2020.08.11
		 */
		public Builder<T> setWildclass(Class wildclass) {
			this.wildclass = wildclass;
			return this;
		}

		/**
		 * Build this builder using the given {@code dejaVu} as a mapping for the results of
		 * previously built builders.
		 *
		 * @param dejaVu a map mapping the previously built builders and their results.
		 * @return a type from building this builder.
		 * @throws NullPointerException if the given {@code dejaVu} is null.
		 * @throws ClassCastException   if {@link Builder#objecttypes} has a value that is not an
		 *                              instance of {@link Builder}.
		 * @since 0.1.5 ~2020.08.11
		 */
		private Type<T> build(Map<Builder, Type> dejaVu) {
			Objects.requireNonNull(dejaVu, "dejaVu");

			//capture
			Class builderTypeclass = this.typeclass;
			Class builderWildclass = this.wildclass;
			Map<Object, Builder> builderObjecttypes = this.objecttypes;
			List<Builder> builderComponents = this.components;

			//construct
			Type type = new Type();

			//dejaVu effect
			dejaVu.put(this, type);

			//compute typeclass
			Class typeclass;
			if (builderTypeclass == null)
				typeclass = Object.class;
			else
				typeclass = builderTypeclass;

			//compute wildclass
			Class wildclass;
			if (builderWildclass == null)
				wildclass = typeclass;
			else
				wildclass = builderWildclass;

			//compute objecttypes
			Map<Object, Type> objecttypes;
			if (builderObjecttypes == null)
				objecttypes = Collections.emptyMap();
			else {
				objecttypes = new HashMap(builderObjecttypes.size());

				for (Map.Entry<Object, Builder> entry : builderObjecttypes.entrySet()) {
					Object key = entry.getKey();
					Builder value = entry.getValue();

					if (value != null) {
						Type built = dejaVu.get(value);

						if (built == null)
							built = value.build(dejaVu);

						objecttypes.put(key, built);
					}
				}
			}

			//compute components
			Type[] components;
			if (builderComponents == null)
				components = new Type[0];
			else {
				int length = builderComponents.size();
				components = new Type[length];

				for (int i = 0; i < length; i++) {
					Builder element = builderComponents.get(i);

					if (element != null) {
						Type built = dejaVu.get(element);

						if (built == null)
							built = element.build(dejaVu);

						components[i] = built;
					}
				}
			}

			//initialize
			type.init(typeclass, wildclass, objecttypes, components);
			return type;
		}
	}
}
