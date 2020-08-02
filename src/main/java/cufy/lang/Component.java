package cufy.lang;

import cufy.util.Objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A component types map that can't be modified and type consider it safe to be stored as its component.
 *
 * @param <T> the type of the class represented by this component.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.07.29
 */
public final class Component<T> implements java.lang.reflect.Type, Serializable {
	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = -7433454741119812315L;

	/**
	 * The mappings of the objects to their types in this component.
	 *
	 * @since 0.1.5
	 */
	private Map<Object, Type> componentMap;
	/**
	 * The general type for the objects that have no mapping in the constructed component.
	 *
	 * @since 0.1.5
	 */
	private Type<T> componentType;

	/**
	 * Construct a new component that its general type is the given {@code componentType}, with the mappings in the given {@code
	 * componentMap}.
	 *
	 * @param componentType the general type for the objects that have no mapping in the constructed component.
	 * @param componentMap  the mappings of the objects to their types in the constructed component.
	 * @throws NullPointerException if the given {@code componentMap} is null.
	 * @throws ClassCastException   if the given {@code componentMap} has a value that is not null neither an instance of {@link
	 *                              Type}.
	 * @since 0.1.5
	 */
	private Component(Type<T> componentType, Map<Object, Type> componentMap) {
		Objects.requireNonNull(componentMap, "componentMap");
		this.componentType = componentType;
		this.componentMap = new HashMap(componentMap);
		this.componentMap.values().forEach(Type.class::cast);
	}

	/**
	 * Create a new empty array of components.
	 *
	 * @return a new empty empty array of components.
	 * @since 0.1.5
	 */
	public static Component[] array() {
		return new Component[0];
	}

	/**
	 * Create a new array of components from the given {@code componentClasses}.
	 *
	 * @param componentClasses the source array.
	 * @return a new array of components from the given {@code componentTypes}. Or null if the given {@code componentClasses} is
	 * 		null.
	 * @since 0.1.5
	 */
	public static Component[] array(Class... componentClasses) {
		if (componentClasses == null)
			return null;

		Component[] components = new Component[componentClasses.length];

		for (int i = 0; i < components.length; i++) {
			Class componentClass = componentClasses[i];

			if (componentClass != null)
				components[i] = Component.of(componentClass);
		}

		return components;
	}

	/**
	 * Create a new array of components from the given {@code componentTypes}.
	 *
	 * @param componentTypes the source array.
	 * @return a new array of components from the given {@code componentTypes}. Or null if the given {@code componentTypes} is
	 * 		null.
	 * @since 0.1.5
	 */
	public static Component[] array(Type... componentTypes) {
		if (componentTypes == null)
			return null;

		Component[] components = new Component[componentTypes.length];

		for (int i = 0; i < components.length; i++) {
			Type componentType = componentTypes[i];

			if (componentType != null)
				components[i] = Component.of(componentType);
		}

		return components;
	}

	/**
	 * Get a component that have no componentType, nor mappings.
	 * <pre>
	 *     Component.of()
	 *     &lt;&gt;
	 * </pre>
	 *
	 * @return a component that have no componentType, nor mappings.
	 * @since 0.1.5
	 */
	public static Component<?> of() {
		return new Component(null, Collections.emptyMap());
	}

	/**
	 * Get a component that have no componentType, and have the mappings of the given {@code componentMap}.
	 * <pre>
	 *     Component.of(<font color="f39f9f">MAP</font>)
	 *     &lt;&gt;
	 * </pre>
	 *
	 * @param componentMap the mappings of the objects to their types in the returned component.
	 * @return a component that have no componentType, and have the mappings of the given {@code componentMap}.
	 * @throws NullPointerException if the given {@code componentMap} is null.
	 * @throws ClassCastException   if the given {@code componentMap} has a value that is not null neither an instance of {@link
	 *                              Type}.
	 * @since 0.1.5
	 */
	public static Component<?> of(Map<Object, Type> componentMap) {
		Objects.requireNonNull(componentMap, "componentMap");
		return new Component(null, componentMap);
	}

	/**
	 * Get a component that have the given {@code componentType}, and have no mappings.
	 * <pre>
	 *     Type.component(<font color="d3c4ff">COMPONENT</font>)
	 *     <font color="d3c4ff">COMPONENT</font>
	 * </pre>
	 *
	 * @param componentType the componentType of the returned component.
	 * @param <T>           the type of the class represented by the returned component.
	 * @return a component that have the given {@code componentType}, and have no mappings.
	 * @since 0.1.5
	 */
	public static <T> Component<T> of(Type<T> componentType) {
		return new Component(componentType, Collections.emptyMap());
	}

	/**
	 * Get a component that have the given {@code componentType}, and have the mappings of the given {@code componentMap}.
	 * <pre>
	 *     Type.component(<font color="d3c4ff">COMPONENT</font>, <font color="f39f9f">MAP</font>)
	 *     <font color="d3c4ff">COMPONENT</font>
	 * </pre>
	 *
	 * @param componentMap  the mappings of the objects to their types in the returned component.
	 * @param componentType the componentType of the returned component.
	 * @param <T>           the type of the class represented by the returned component.
	 * @return a component that have the given {@code componentType}, and have the mappings of the given {@code componentMap}.
	 * @throws NullPointerException if the given {@code componentMap} is null.
	 * @throws ClassCastException   if the given {@code componentMap} has a value that is not null neither an instance of {@link
	 *                              Type}.
	 * @since 0.1.5
	 */
	public static <T> Component<T> of(Type<T> componentType, Map<Object, Type> componentMap) {
		Objects.requireNonNull(componentMap, "componentMap");
		return new Component(componentType, componentMap);
	}

	/**
	 * Get a component that have the given {@code componentType}, and have no mappings.
	 * <pre>
	 *     Type.component(<font color="d3c4ff">COMPONENT</font>)
	 *     <font color="d3c4ff">COMPONENT</font>
	 * </pre>
	 *
	 * @param componentType the componentType of the returned component.
	 * @param <T>           the type of the class represented by the returned component.
	 * @return a component that have the given {@code componentType}, and have no mappings.
	 * @since 0.1.5
	 */
	public static <T> Component<T> of(Class<T> componentType) {
		return new Component(Type.of(componentType), Collections.emptyMap());
	}

	/**
	 * Get a component that have the given {@code componentType}, and have the mappings of the given {@code componentMap}.
	 * <pre>
	 *     Type.component(<font color="d3c4ff">COMPONENT</font>, <font color="f39f9f">MAP</font>)
	 *     <font color="d3c4ff">COMPONENT</font>
	 * </pre>
	 *
	 * @param componentMap  the mappings of the objects to their types in the returned component.
	 * @param componentType the componentType of the returned component.
	 * @param <T>           the type of the class represented by the returned component.
	 * @return a component that have the given {@code componentType}, and have the mappings of the given {@code componentMap}.
	 * @throws NullPointerException if the given {@code componentMap} is null.
	 * @throws ClassCastException   if the given {@code componentMap} has a value that is not null neither an instance of {@link
	 *                              Type}.
	 * @since 0.1.5
	 */
	public static <T> Component<T> of(Class<T> componentType, Map<Object, Type> componentMap) {
		Objects.requireNonNull(componentMap, "componentMap");
		return new Component(Type.of(componentType), componentMap);
	}

	@Override
	public boolean equals(Object o) {
		return o == this ||
			   o instanceof Component &&
			   Objects.equals(((Component) o).componentType, this.componentType) &&
			   Objects.equals(((Component) o).componentMap, this.componentMap);
	}

	@Override
	public String getTypeName() {
		return this.componentType == null ? "?" : this.componentType.getTypeName();
	}

	@Override
	public int hashCode() {
		return this.componentType.hashCode() ^ this.componentMap.hashCode();
	}

	@Override
	public String toString() {
		return "component " + this.getName();
	}

	/**
	 * Get the {@link Component#componentType componentType} of this component. The {@link Component#componentType componentType}
	 * is the type for the elements that have no type in the component of the type representing it.
	 *
	 * @return the {@link Component#componentType componentType} of this component, Or null if this component has no {@link
	 *        Component#componentType componentType}.
	 * @since 0.1.5
	 */
	public Type<T> getComponentType() {
		return this.componentType;
	}

	/**
	 * Get the {@code componentType} that this component specifies for the given {@code object}.
	 *
	 * @param object the object of the returned type in this component.
	 * @return the {@code componentType} that this component specifies for the given {@code object}, Or the {@link
	 *        Component#componentType componentType} of this component if there is no {@code componentType} in this component specified
	 * 		for the given {@code object}.
	 * @since 0.1.5
	 */
	public Type getComponentType(Object object) {
		return this.componentMap.containsKey(object) ?
			   this.componentMap.get(object) :
			   this.componentType;
	}

	/**
	 * Get the {@code Name} of this component. The {@code Name} of a component is the {@link Type#getName() Name} of its {@link
	 * Component#componentType componentType}, or {@code "?"} if it has no {@link Component#componentType componentType}.
	 *
	 * @return the {@code Name} of this component, Or {@code "?"} if this component has no {@link Component#componentType
	 * 		componentType}.
	 * @since 0.1.5
	 */
	public String getName() {
		return this.componentType == null ? "?" : this.componentType.getName();
	}

	/**
	 * Get the {@code SimpleName} of this component. The {@code SimpleName} of a component is the {@link Type#getSimpleName()
	 * SimpleName} of its {@link Component#componentType componentType}, or {@code "?"} if it has no {@link
	 * Component#componentType componentType}.
	 *
	 * @return the {@code SimpleName} of this component, Or {@code "?"} if this component has no {@link Component#componentType
	 * 		componentType}.
	 * @since 0.1.5
	 */
	public String getSimpleName() {
		return this.componentType == null ? "?" : this.componentType.getSimpleName();
	}

	@SuppressWarnings("JavaDoc")
	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
		Objects.requireNonNull(stream, "stream");
		this.componentType = (Type) stream.readObject();
		this.componentMap = (Map) stream.readObject();
	}

	@SuppressWarnings("JavaDoc")
	private void writeObject(ObjectOutputStream stream) throws IOException {
		Objects.requireNonNull(stream, "stream");
		stream.writeObject(this.componentType);
		stream.writeObject(this.componentMap);
	}
}
