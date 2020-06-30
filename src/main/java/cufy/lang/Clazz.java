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
 * components component and the family of that class. The family is how the class should be treated. The clazz-component
 * is a component of clazzes for each specific component in an instance this clazz is representing.
 * <br>
 * Note: the class {@link Void} is the class of null.
 * <pre>
 *     clazz (root) -> component (component component) -> clazz (default clazz | new root) -> component -> clazz -> component ->...
 * </pre>
 *
 * @param <T> the type of the class represented by this clazz.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.0 ~2020.03.29
 */
public final class Clazz<T> implements Type, Serializable {
	/**
	 * A component of the clazzes specified foreach component to be held by an instance of this clazz. This field should
	 * be treated as final field.
	 */
	private Component[] components;
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
	 * Construct a new clazz that represents the class {@link Object}, and have the given {@code components}.
	 *
	 * @param components an array of components of the clazzes specified foreach component to be held by an instance of
	 *                   the constructed clazz.
	 * @throws NullPointerException if the given {@code components} or any of its elements is null.
	 */
	private Clazz(Component... components) {
		Objects.requireNonNull(components, "components");
		this.klass = (Class<T>) Object.class;
		this.family = Object.class;
		this.components = Component.finalize(components);
	}

	/**
	 * Construct a new clazz that represents the given {@code klass}, and have the given {@code components}.
	 *
	 * @param klass      the class to be represented by the constructed clazz.
	 * @param components an array of components of the clazzes specified foreach component to be held by an instance of
	 *                   the constructed clazz.
	 * @throws NullPointerException if the given {@code klass} or {@code components} or any of its elements is null.
	 */
	private Clazz(Class<T> klass, Component... components) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(components, "components");
		this.klass = klass;
		this.family = klass;
		this.components = Component.finalize(components);
	}

	/**
	 * Construct a new clazz that represents the given {@code klass}, and have the given {@code components}, and should
	 * be treated as if it was the given {@code family}.
	 *
	 * @param klass      the class to be represented by the constructed clazz.
	 * @param family     the class that an instance of the constructed clazz should be treated as if it was an instance
	 *                   of it.
	 * @param components an array of components of the clazzes specified foreach component to be held by an instance of
	 *                   the constructed clazz.
	 * @throws NullPointerException if the given {@code klass} or {@code family} or {@code components} or any of its
	 *                              elements is null.
	 */
	private Clazz(Class<T> klass, Class family, Component... components) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(components, "components");
		this.klass = klass;
		this.family = family;
		this.components = Component.finalize(components);
	}

	/**
	 * Get an array of clazzes from the given {@code klasses}.
	 *
	 * @param klasses the source klasses foreach clazz in the returned array.
	 * @return an array of clazzes from the given {@code klasses}.
	 * @throws NullPointerException if the given {@code klasses} is null.
	 * @since 0.1.5
	 */
	public static Clazz[] array(Class... klasses) {
		Objects.requireNonNull(klasses, "klasses");
		Clazz[] clazzes = new Clazz[klasses.length];

		for (int i = 0; i < klasses.length; i++)
			if (klasses[i] != null)
				clazzes[i] = Clazz.of(klasses[i]);

		return clazzes;
	}

	/**
	 * Get a clazz that represents teh given {@code klass}, and have components with the given {@code
	 * componentClasses}.
	 * <pre>
	 *     Clazz.as(<font color="a5edff">KLASS</font>, <font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">KLASS</font>&lt;<font color="d3c4ff">TREE0</font>, <font color="d3c4ff">TREE1</font>, <font color="d3c4ff">TREE2</font>, …&gt;
	 * </pre>
	 *
	 * @param klass            the class to be represented by the returned clazz.
	 * @param componentClasses the component clazzes of the components of the returned clazz.
	 * @param <T>              the type of the class represented by the returned clazz.
	 * @return a clazz that represents teh given {@code klass}, and have components with the given {@code
	 * 		componentClasses}.
	 * @throws NullPointerException if the given {@code klass} or {@code componentClasses} is null.
	 * @since 0.1.5
	 */
	public static <T> Clazz<T> as(Class<T> klass, Class... componentClasses) {
		return Clazz.of(klass, Component.of(Clazz.array(componentClasses)));
	}

	/**
	 * Get a clazz that represents teh given {@code klass}, and have components with the given {@code componentClasses},
	 * and should be treated as if it was the given {@code family}.
	 * <pre>
	 *     Clazz.as(<font color="a5edff">KLASS</font>, <font color="fc8fbb">FAMILY</font> <font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">KLASS</font>:<font color="fc8fbb">FAMILY</font>&lt;<font color="d3c4ff">TREE0</font>, <font color="d3c4ff">TREE1</font>, <font color="d3c4ff">TREE2</font>, …&gt;
	 * </pre>
	 *
	 * @param klass            the class to be represented by the returned clazz.
	 * @param family           the class that an instance of the returned clazz should be treated as if it was an
	 *                         instance of it.
	 * @param componentClasses the component clazzes of the components of the returned clazz.
	 * @param <T>              the type of the class represented by the returned clazz.
	 * @return a clazz that represents teh given {@code klass}, and have components with the given {@code
	 * 		componentClasses}, and should be treated as if it was the given {@code family}.
	 * @throws NullPointerException if the given {@code klass} or {@code family} or {@code componentClasses} is null.
	 * @since 0.1.5
	 */
	public static <T> Clazz<T> as(Class<T> klass, Class family, Class[] componentClasses) {
		return Clazz.of(klass, family, Component.of(Clazz.array(componentClasses)));
	}

	/**
	 * Get a clazz that represents the class of the given {@code instance}, and with the given {@code components}.
	 * <pre>
	 *     Clazz.in(<font color="a5edff">INSTANCE</font>, <font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">KLASS</font>&lt;<font color="d3c4ff">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param instance   an instance that its class to be represented by the returned clazz, and its elements is the
	 *                   source of its trees.
	 * @param components an array of components of the clazzes specified foreach component to be held by an instance of
	 *                   the constructed clazz.
	 * @param <T>        the type of the class represented by the returned clazz.
	 * @return a clazz that represents the class of the given {@code instance}, and with the given {@code components}.
	 * @throws NullPointerException if the given {@code componentClazzes} is null.
	 * @since 0.1.5
	 */
	public static <T> Clazz<T> from(T instance, Component... components) {
		Class klass = instance == null ? Void.class : instance.getClass();
		return Clazz.of(klass, components);
	}

	/**
	 * Get a clazz that represents the class of the given {@code instance}, and should be treated as if it was the given
	 * {@code family}, and with the given {@code components}.
	 * <br>
	 * <pre>
	 *     Clazz.in(<font color="a5edff">INSTANCE</font>, <font color="fc8fbb">FAMILY</font>, <font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
	 *     <font color="a5edff">KLASS</font>:<font color="fc8fbb">FAMILY</font>&lt;<font color="d3c4ff">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param instance   an instance that its class to be represented by the returned clazz, and its elements is the
	 *                   source of its tree.
	 * @param family     the class that an instance of the returned clazz should be treated as if it was an instance of
	 *                   it.
	 * @param components an array of components of the clazzes specified foreach component to be held by an instance of
	 *                   the constructed clazz.
	 * @param <T>        the type of the class represented by the returned clazz.
	 * @return a clazz that represents the class of the given {@code instance}, and should be treated as if it was the
	 * 		given {@code family}, and with the given {@code components}.
	 * @throws NullPointerException if the given {@code family} or {@code componentClazzes} is null.
	 * @since 0.1.5
	 */
	public static <T> Clazz<T> from(T instance, Class family, Component... components) {
		Class klass = instance == null ? Void.class : instance.getClass();
		return Clazz.of(klass, family, components);
	}

	/**
	 * Get a clazz that represents the class {@link Object}, and have the given {@code components}.
	 * <pre>
	 *     Clazz.of(<font color="f1fc8f">TREE0, TREE1, TREE2, …</font>)
	 *     <font color="a5edff">Object</font>&lt;<font color="f1fc8f">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param components an array of components of the clazzes specified foreach component to be held by an instance of
	 *                   the constructed clazz.
	 * @return a clazz that represents the class {@link Object}, and have the given {@code components}.
	 * @throws NullPointerException if the given {@code components} or any of its elements is null.
	 * @since 0.1.5
	 */
	public static Clazz<Object> of(Component... components) {
		Objects.requireNonNull(components, "components");
		return new Clazz(Object.class, components);
	}

	/**
	 * Get a clazz that represents the given {@code klass}, and have the given {@code components}.
	 * <pre>
	 *     Clazz.of(<font color="a5edff">KLASS</font>, <font color="f1fc8f">TREE0, TREE1, TREE2, …</font>)
	 *     <font color="a5edff">KLASS</font>&lt;<font color="f1fc8f">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param klass      the class to be represented by the returned clazz.
	 * @param components an array of components of the clazzes specified foreach component to be held by an instance of
	 *                   the constructed clazz.
	 * @param <T>        the type of the class represented by the returned clazz.
	 * @return a clazz that represents the given {@code klass}, and have the given {@code components}.
	 * @throws NullPointerException if the given {@code klass} or {@code components} or any of its elements is null.
	 * @since 0.1.5
	 */
	public static <T> Clazz<T> of(Class<T> klass, Component... components) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(components, "components");
		return new Clazz(klass, components);
	}

	/**
	 * Get a clazz that represents the given {@code klass}, and have the given {@code component}, and should be treated
	 * as if it was the given {@code family}.
	 * <pre>
	 *     Clazz.of(<font color="a5edff">KLASS</font>, <font color="fc8fbb">FAMILY</font>, <font color="f1fc8f">TREE0, TREE1, TREE2, …</font>)
	 *     <font color="a5edff">KLASS</font>:<font color="fc8fbb">FAMILY</font>&lt;<font color="f1fc8f">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param klass      the class to be represented by the returned clazz.
	 * @param family     the class that an instance of the returned clazz should be treated as if it was an instance of
	 *                   it.
	 * @param components an array of components of the clazzes specified foreach component to be held by an instance of
	 *                   the constructed clazz.
	 * @param <T>        the type of the class represented by the returned clazz.
	 * @return a new clazz that represents the given {@code klass}, and have the given {@code component}, and should be
	 * 		treated as if it was the given {@code family}.
	 * @throws NullPointerException if the given {@code klass} or {@code family} or {@code component} is null.
	 * @since 0.1.5
	 */
	public static <T> Clazz<T> of(Class<T> klass, Class family, Component... components) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(components, "component");
		return new Clazz(klass, family, components);
	}

	/**
	 * Get a clazz that represents the class with the given {@code name}, and have the given {@code component}.
	 * <pre>
	 *     Clazz.of(<font color="a5edff">NAME</font>, <font color="f1fc8f">TREE0, TREE1, TREE2, …</font>)
	 *     <font color="a5edff">KLASS</font>&lt;<font color="f1fc8f">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param name       the name of the class that to be represented by the returned clazz.
	 * @param components an array of components of the clazzes specified foreach component to be held by an instance of
	 *                   the constructed clazz.
	 * @return a clazz that represents the class with the given {@code name}, and have the given {@code component}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code name} or {@code component} is null.
	 * @see Class#forName(String)
	 * @since 0.1.5
	 */
	public static Clazz of(String name, Component... components) throws ClassNotFoundException {
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(components, "components");
		return Clazz.of(Class.forName(name), components);
	}

	/**
	 * Get a clazz that represents the class with the given {@code name}, and have the given {@code component}, and
	 * should be treated as if it was the given {@code family}.
	 * <pre>
	 *     Clazz.of(<font color="a5edff">NAME</font>, <font color="fc8fbb">FAMILY</font>, <font color="f1fc8f">TREE0, TREE1, TREE2, …</font>)
	 *     <font color="a5edff">KLASS</font>:<font color="fc8fbb">FAMILY</font>&lt;<font color="f1fc8f">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param name       the name of the class that to be represented by the returned clazz.
	 * @param family     the class that an instance of the returned clazz should be treated as if it was an instance of
	 *                   it.
	 * @param components an array of components of the clazzes specified foreach component to be held by an instance of
	 *                   the constructed clazz.
	 * @return a clazz that represents the class with the given {@code name}, and have the given {@code component}, and
	 * 		should be treated as if it was the given {@code family}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code name} or {@code family} or {@code component} is null.
	 * @see Class#forName(String)
	 * @since 0.1.5
	 */
	public static Clazz of(String name, Class family, Component... components) throws ClassNotFoundException {
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(components, "components");
		return Clazz.of(Class.forName(name), family, components);
	}

	/**
	 * Get a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and have
	 * the given {@code component}.
	 * <pre>
	 *     Clazz.of(LOADER, INITIALIZE, <font color="a5edff">NAME</font>, <font color="f1fc8f">TREE0, TREE1, TREE2, …</font>)
	 *     <font color="a5edff">KLASS</font>&lt;<font color="f1fc8f">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param loader     class loader from which the class must be loaded.
	 * @param initialize if true the class will be initialized. See Section 12.4 of The Java Language.
	 * @param name       the name of the class that to be represented by the returned clazz.
	 * @param components an array of components of the clazzes specified foreach component to be held by an instance of
	 *                   the constructed clazz.
	 * @return a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and
	 * 		have the given {@code component}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code loader} or {@code name} or {@code component} is null.
	 * @see Class#forName(String, boolean, ClassLoader)
	 * @since 0.1.5
	 */
	public static Clazz of(ClassLoader loader, boolean initialize, String name, Component... components) throws ClassNotFoundException {
		Objects.requireNonNull(loader, "loader");
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(components, "components");
		return Clazz.of(Class.forName(name, initialize, loader), components);
	}

	/**
	 * Get a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and have
	 * the given {@code component}, and should be treated as if it was the given {@code family}.
	 * <pre>
	 *     Clazz.of(LOADER, INITIALIZE, <font color="a5edff">NAME</font>, <font color="fc8fbb">FAMILY</font>, <font color="f1fc8f">TREE0, TREE1, TREE2, …</font>)
	 *     <font color="a5edff">KLASS</font>:<font color="fc8fbb">FAMILY</font>&lt;<font color="f1fc8f">TREE0, TREE1, TREE2, …</font>&gt;
	 * </pre>
	 *
	 * @param loader     class loader from which the class must be loaded.
	 * @param initialize if true the class will be initialized. See Section 12.4 of The Java Language.
	 * @param name       the name of the class that to be represented by the returned clazz.
	 * @param family     the class that an instance of the returned clazz should be treated as if it was an instance of
	 *                   it.
	 * @param components an array of components of the clazzes specified foreach component to be held by an instance of
	 *                   the constructed clazz.
	 * @return a clazz that represents the class with the given {@code name} loaded using the given {@code loader}, and
	 * 		have the given {@code component}, and should be treated as if it was the given {@code family}.
	 * @throws ClassNotFoundException      if the class cannot be located.
	 * @throws LinkageError                if the linkage fails.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 * @throws NullPointerException        if the given {@code loader} or {@code name} or {@code family} or {@code
	 *                                     component} is null.
	 * @see Class#forName(String, boolean, ClassLoader)
	 * @since 0.1.5
	 */
	public static Clazz of(ClassLoader loader, boolean initialize, String name, Class family, Component... components) throws ClassNotFoundException {
		Objects.requireNonNull(loader, "loader");
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(family, "family");
		Objects.requireNonNull(components, "components");
		return Clazz.of(Class.forName(name, initialize, loader), family, components);
	}

	/**
	 * Get a clazz that represents the {@code klass} of the given {@code klassSrc}, and have the {@code component} of
	 * the given {@code treeSrc}, and should be treated as if it was the {@code family} of the given {@code familySrc}.
	 * <pre>
	 *     Clazz.of(<font color="a5edff">KLASS_SRC</font>, <font color="fc8fbb">FAMILY_SRC</font>, <font color="f1fc8f">TREES_SRC</font>)
	 *     <font color="a5edff">KLASS</font>:<font color="fc8fbb">FAMILY</font>&lt;<font color="f1fc8f">TREES…</font>&gt;
	 * </pre>
	 *
	 * @param klassSrc  the source of the class to be represented by the returned clazz.
	 * @param familySrc the source of the class that an instance of the returned clazz should be treated as if it was an
	 *                  instance of it.
	 * @param treesSrc  the source of the array of components of the clazzes specified foreach component to be held by
	 *                  an instance of the constructed clazz.
	 * @param <T>       the type of the class represented by the returned clazz.
	 * @return a clazz that represents the {@code klass} of the given {@code klassSrc}, and have the {@code component}
	 * 		of the given {@code treeSrc}, and should be treated as if it was the {@code family} of the given {@code
	 * 		familySrc}.
	 * @throws NullPointerException if the given {@code familySrc} or {@code klassSrc} or {@code treeSrc} is null.
	 * @since 0.1.5
	 */
	public static <T> Clazz<T> of(Clazz<T> klassSrc, Clazz familySrc, Clazz treesSrc) {
		Objects.requireNonNull(klassSrc, "klassSrc");
		Objects.requireNonNull(familySrc, "familySrc");
		Objects.requireNonNull(treesSrc, "treesSrc");
		return Clazz.of(klassSrc.klass, familySrc.family, treesSrc.components);
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getKlass() getKlass()}{@link Class#cast(Object) .cast(instance)}
	 * </pre>
	 *
	 * @since 0.1.5
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
	 *
	 * @since 0.1.5
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
	 *
	 * @since 0.1.5
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
	 *
	 * @since 0.1.5
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
	 *
	 * @since 0.1.5
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
	 *
	 * @since 0.1.5
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
	 *
	 * @since 0.1.5
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
	 *
	 * @since 0.1.5
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
	 *
	 * @since 0.1.5
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
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public final T newInstance() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		return this.klass.getConstructor().newInstance();
	}

	/**
	 * Shortcut for:
	 * <pre>
	 *     {@link #getKlass() getKlass()}{@link Class#getConstructor(Class[]) .getConstructor(AUTO)}{@link Class#newInstance() .newInstance(parameters)}
	 * </pre>
	 *
	 * @since 0.1.5
	 */
	@SuppressWarnings("JavaDoc")
	public final T newInstance(Object... parameters) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		//noinspection LabeledStatement
		for0:
		for (Constructor<T> constructor : (Constructor<T>[]) this.klass.getDeclaredConstructors()) {
			Class[] parameterTypes = constructor.getParameterTypes();
			for (int i = 0; i < parameterTypes.length; i++)
				if (!parameterTypes[i].isInstance(parameters[i]))
					//noinspection ContinueStatementWithLabel
					continue for0;

			return constructor.newInstance(parameters);
		}

		throw new NoSuchMethodException("No constructor found for the given parameters");
	}

	@Override
	public String getTypeName() {
		//KLASS:FAMILY<COMPONENTS>
		StringBuilder typeName = new StringBuilder(this.klass.getTypeName());

		if (this.family != this.klass)
			typeName.append(":").append(this.family.getTypeName());

		if (this.components.length != 0) {
			typeName.append("<");
			Iterator<Component> iterator = Arrayz.iterator(this.components);
			while (iterator.hasNext())
				typeName.append(iterator.next().getTypeName())
						.append(iterator.hasNext() ? ", " : ">");
		}

		return typeName.toString();
	}

	@Override
	public int hashCode() {
		//noinspection ObjectInstantiationInEqualsHashCode
		return 31 * Objects.hash(this.family, this.klass) + Arrays.hashCode(this.components);
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj ||
			   obj instanceof Clazz &&
			   ((Clazz) obj).klass == this.klass &&
			   ((Clazz) obj).family == this.family &&
			   Arrays.equals(((Clazz) obj).components, this.components);
	}

	@Override
	public String toString() {
		return "clazz " + this.getName();
	}

	/**
	 * Get the component of the clazzes specified foreach component to be held by an instance of this clazz that have
	 * the given {@code component} index.
	 * <br>
	 * Note: the returned component will always be unmodifiable. Trying to modify it will always throw an exception.
	 *
	 * @param component the index of the returned component at this clazz.
	 * @return the component of the clazzes specified foreach component to be held by an instance of this clazz. Or null
	 * 		if no component have the given position.
	 * @since 0.1.5
	 */
	public Component getComponent(int component) {
		return component >= 0 && component < this.components.length ?
			   this.components[component] :
			   null;
	}

	/**
	 * Get the general clazz for all components in the specified {@code component} to be held by an instance of this
	 * clazz.
	 * <br>
	 * Note: This is like calling {@link Clazz#getComponentClazz(int, Object) getComponentClazz(}{@link
	 * Component#DEFAULT_CLAZZ Clazz.COMPONENT_TYPE)}.
	 *
	 * @param component the index of the component in this clazz that have the returned component clazz.
	 * @return the general clazz for all components in the specified {@code component} to be held by an instance of this
	 * 		clazz. Or null if no component have the given position, or that component don't have a general component
	 * 		clazz.
	 * @since 0.1.5
	 */
	public Clazz getComponentClazz(int component) {
		return component >= 0 && component < this.components.length ?
			   this.components[component] != null ?
			   this.components[component].get(Component.DEFAULT_CLAZZ) :
			   null :
			   null;
	}

	/**
	 * Get the component clazz of this clazz that have the given {@code key} in the specified {@code component}.
	 * <br>
	 * Note: the key {@link Component#DEFAULT_CLAZZ} is the key for the general component clazz.
	 *
	 * @param component the index of the component in this clazz that have the returned component clazz.
	 * @param key       the key associated to the returned clazz as a component in this clazz.
	 * @return the component clazz of this clazz that have the given {@code key} in the specified {@code component}. Or
	 * 		null if no component have the given position, or that component don't have a clazz associated to the given
	 *        {@code key} in it.
	 * @since 0.1.5
	 */
	public Clazz getComponentClazz(int component, Object key) {
		return component >= 0 && component < this.components.length ?
			   this.components[component] != null ?
			   this.components[component].containsKey(key) ?
			   this.components[component].get(key) :
			   this.components[component].get(Component.DEFAULT_CLAZZ) :
			   null :
			   null;
	}

	/**
	 * Get a clone of the array holding all the components of this clazz.
	 * <br>
	 * Note: all the returned components will always be unmodifiable. Trying to modify them will always throw an
	 * exception.
	 *
	 * @return a clone of the array holding all the components of this clazz.
	 * @since 0.1.5
	 */
	public Component[] getComponents() {
		return Arrays.copyOf(this.components, this.components.length);
	}

	/**
	 * Get the class that an instance of this clazz should be treated as if it was an instance of it.
	 *
	 * @return the class that an instance of this clazz should be treated as if it was an instance of it.
	 * @since 0.1.5
	 */
	public Class getFamily() {
		return this.family;
	}

	/**
	 * Get the class represented by this clazz.
	 *
	 * @return the class represented by this clazz.
	 * @since 0.1.5
	 */
	public Class<T> getKlass() {
		return this.klass;
	}

	/**
	 * Get the name of this clazz. The name contains the name of the {@code family}, {@code klass}, and each {@code
	 * componentClazz} of each {@code component} in this clazz.
	 *
	 * @return the name of this clazz.
	 * @since 0.1.5
	 */
	public String getName() {
		//KLASS:FAMILY<COMPONENTS>
		StringBuilder name = new StringBuilder(this.klass.getName());

		if (this.family != this.klass)
			name.append(":").append(this.family.getName());

		if (this.components.length != 0) {
			name.append("<");
			Iterator<Component> iterator = Arrayz.iterator(this.components);
			while (iterator.hasNext())
				name.append(iterator.next().getName())
						.append(iterator.hasNext() ? ", " : ">");
		}

		return name.toString();
	}

	/**
	 * Get the simple name of this clazz. The name contains the simple name of the {@code family}, {@code klass}, and
	 * each {@code componentClazz} of each {@code component} in this clazz.
	 *
	 * @return the simple name of this clazz.
	 * @since 0.1.5
	 */
	public String getSimpleName() {
		//KLASS:FAMILY<COMPONENTS>
		StringBuilder simpleName = new StringBuilder(this.klass.getSimpleName());

		if (this.family != this.klass)
			simpleName.append(":").append(this.family.getSimpleName());

		if (this.components.length != 0) {
			simpleName.append("<");
			Iterator<Component> iterator = Arrayz.iterator(this.components);
			while (iterator.hasNext())
				simpleName.append(iterator.next().getSimpleName())
						.append(iterator.hasNext() ? ", " : ">");
		}

		return simpleName.toString();
	}

	/**
	 * Determine if the {@code klass} of this clazz has a primitive type.
	 *
	 * @return true, if the {@code klass} of this clazz has a primitive type.
	 * @since 0.1.5
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
	 * @since 0.1.5
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
	 * @since 0.1.5
	 */
	public boolean isInstance(Object instance) {
		return instance == null && this.klass == Void.class || this.klass.isInstance(instance);
	}

	/**
	 * Get a clazz that represents the {@code Object} version of the {@code klass} of this clazz, and have the {@code
	 * component} of this clazz, and should be treated as if it was the {@code family} of this clazz.
	 *
	 * @return a clazz that represents the {@code Object} version of the {@code klass} of this clazz, and have the
	 *        {@code component} of this clazz, and should be treated as if it was the {@code family} of this clazz.
	 * @since 0.1.5
	 */
	public Clazz toObjectClazz() {
		//noinspection ReturnOfThis
		return this.klass.isPrimitive() ? Clazz.of(Reflection.asObjectClass(this.klass), this.family, this.components)
										: this;
	}

	/**
	 * Get a clazz that represents the primitive version of the {@code klass} of this clazz, and have the {@code
	 * component} of this clazz, and should be treated as if it was the {@code family} of this clazz.
	 *
	 * @return a clazz that represents the primitive version of the {@code klass} of this clazz, and have the {@code
	 * 		component} of this clazz, and should be treated as if it was the {@code family} of this clazz. Or null if there
	 * 		is no primitive class of the {@code klass} of this clazz.
	 * @since 0.1.5
	 */
	public Clazz toPrimitiveClazz() {
		//noinspection ReturnOfNull
		return Reflection.hasPrimitiveClass(this.klass)
			   ? Clazz.of(Reflection.asPrimitiveClass(this.klass), this.family, this.components) : null;
	}

	/**
	 * Get this clazz.
	 *
	 * @return this clazz.
	 */
	public Clazz with() {
		//noinspection ReturnOfThis
		return this;
	}

	/**
	 * Get a clazz that represents the {@code klass} of this clazz, and have the {@code component} of this clazz, and
	 * should be treated as if it was the given {@code family}.
	 *
	 * @param family the class that an instance of the returned clazz should be treated as if it was an instance of it.
	 * @return a clazz that represents the {@code klass} of this clazz, and have the {@code component} of this clazz,
	 * 		and should be treated as if it was the given {@code family}.
	 * @throws NullPointerException if the given {@code family} is null.
	 * @since 0.1.5
	 */
	public Clazz with(Class family) {
		Objects.requireNonNull(family, "family");
		return Clazz.of(family, this.klass, this.components);
	}

	/**
	 * Get a clazz that represents the {@code klass} of this clazz, and have the given {@code components}, and should be
	 * treated as if it was the {@code family} of this clazz.
	 *
	 * @param components the components of the clazzes specified foreach component to be held by an instance of the
	 *                   returned clazz.
	 * @return a clazz that represents the {@code klass} of this clazz, and have the given {@code components}, and
	 * 		should be treated as if it was the {@code family} of this clazz.
	 * @throws NullPointerException if the given {@code component} is null.
	 * @since 0.1.5
	 */
	public Clazz with(Component... components) {
		Objects.requireNonNull(components, "components");
		return Clazz.of(this.klass, this.family, components);
	}

	/**
	 * Get a clazz that represents the {@code klass} of this clazz, and have the given {@code component}, and should be
	 * treated as if it was the given {@code family}.
	 *
	 * @param family     the class that an instance of the returned clazz should be treated as if it was an instance of
	 *                   it.
	 * @param components an array of components of the clazzes specified foreach component to be held by an instance of
	 *                   the constructed clazz.
	 * @return a clazz that represents the {@code klass} of this clazz, and have the given {@code component}, and should
	 * 		be treated as if it was the given {@code family}.
	 * @throws NullPointerException if the given {@code family} or {@code component} is null.
	 * @since 0.1.5
	 */
	public Clazz with(Class family, Component... components) {
		Objects.requireNonNull(components, "components");
		Objects.requireNonNull(family, "family");
		return Clazz.of(this.klass, family, components);
	}

	@SuppressWarnings("JavaDoc")
	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
		Objects.requireNonNull(stream, "stream");
		int length = stream.readInt();
		if (length < 3) {
			this.klass = (Class) Object.class;
			this.family = Object.class;
			this.components = Component.EMPTY_ARRAY;
		} else {
			this.klass = (Class) stream.readObject();
			this.family = (Class) stream.readObject();
			this.components = (Component[]) stream.readObject();
		}
	}

	/**
	 * Backdoor to modify the {@code components} of this clazz.
	 *
	 * @param components the new {@code components} array to be set.
	 * @throws NullPointerException if the given {@code components} is null.
	 */
	private void setComponents(Component[] components) {
		Objects.requireNonNull(components, "components");
		this.components = Component.finalize(components);
	}

	/**
	 * Backdoor to modify the {@code family} of this clazz.
	 *
	 * @param family the new {@code family} to be set.
	 * @throws NullPointerException if the given {@code family} is null.
	 */
	private void setFamily(Class family) {
		Objects.requireNonNull(family, "family");
		this.family = family;
	}

	/**
	 * Backdoor to modify the {@code klass} of this clazz.
	 *
	 * @param klass the new {@code klass} to be set.
	 * @throws NullPointerException if the given {@code klass} is null.
	 */
	private void setKlass(Class klass) {
		Objects.requireNonNull(klass, "klass");
		this.klass = klass;
	}

	@SuppressWarnings("JavaDoc")
	private void writeObject(ObjectOutputStream stream) throws IOException {
		Objects.requireNonNull(stream, "stream");
		stream.writeInt(/*length*/3);
		stream.writeObject(this.klass);
		stream.writeObject(this.family);
		stream.writeObject(this.components);
	}

	/**
	 * A component clazzes map that can't be modified and clazz consider it safe to be stored as its component.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5
	 */
	public static final class Component extends AbstractMap<Object, Clazz> {
		/**
		 * A key for a general clazz for all components to be held by an instance of a clazz.
		 */
		public static final Object DEFAULT_CLAZZ = new Object();
		/**
		 * A final empty components array to reduce redundant array initializing.
		 */
		static final Component[] EMPTY_ARRAY = new Component[0];

		/**
		 * An unmodifiable entry-set containing the {@link Map.Entry}s of this component.
		 */
		private final EntrySet entrySet = new EntrySet();
		/**
		 * True, if this component has been finalized and can't be modified.
		 */
		private boolean finalized;

		/**
		 * Initialize a new empty component.
		 */
		private Component() {
		}

		/**
		 * Return an empty component.
		 *
		 * @return an empty component.
		 */
		public static Component linear() {
			return new Component();
		}

		/**
		 * Get a linear component of general component clazzes. The general clazzes specified in order representing the
		 * given {@code componentClasses}.
		 * <pre>
		 *     Clazz.component(<font color="d3c4ff">COMPONENT0, COMPONENT1, COMPONENT2, …</font>)
		 *     <font color="d3c4ff">TREE0</font>&lt;<font color="d3c4ff">TREE1</font>&lt;<font color="d3c4ff">TREE2…</font>&gt;&gt;
		 * </pre>
		 *
		 * @param componentClasses an ordered list of the classes represented by the nested general component clazzes in
		 *                         returned component.
		 * @return a linear component of general component clazzes. The general clazzes specified in order representing
		 * 		the given {@code componentClasses}.
		 * @throws NullPointerException if the given {@code componentClasses} or an element with index (? &gt; 0) in it
		 *                              is null.
		 * @see Component#linear(Class[][])
		 * @since 0.1.5
		 */
		public static Component linear(Class... componentClasses) {
			Objects.requireNonNull(componentClasses, "componentClasses");
			if (componentClasses.length == 0)
				//length == 0 ? empty
				return new Component();
			if (componentClasses.length == 1)
				//length == 1 ? singleton
				return componentClasses[0] == null ?
					   new Component() :
					   Component.of(Clazz.of(componentClasses[0]));
			else //otherwise ? recursive
				return Component.of(Clazz.of(
						//componentClazz's klass
						componentClasses[0],
						//componentClazz's component (subTree)
						Component.linear(Arrays.copyOfRange(componentClasses, 1, componentClasses.length))
				));
		}

		/**
		 * Get linear components of general component clazzes. The given {@code componentClasses} specifies each array
		 * for a component in the returned components array.
		 * <br>
		 * Note: this is just an array version of {@link Component#linear(Class[])}.
		 * <pre>
		 *     Clazz.components(<font color="d3c4ff">{TREE0, NESTED0, DEEP0, …}, {TREE1, NESTED1, DEEP1, …}, …</font>)
		 *     &lt;<font color="d3c4ff">TREE0</font>&lt;<font color="d3c4ff">NESTED0</font>&lt;<font color="d3c4ff">DEEP0</font>…&gt;&gt;, <font color="d3c4ff">TREE1</font>&lt;<font color="d3c4ff">NESTED1</font>&lt;<font color="d3c4ff">DEEP1</font>…&gt;&gt;, …&gt;
		 * </pre>
		 *
		 * @param componentClasses an array of component classes, each array goes to the same position component in the
		 *                         returned components array.
		 * @return linear components of general component clazzes. The given {@code componentClasses} specifies each
		 * 		array for a component *in the returned components array.
		 * @throws NullPointerException if the given {@code componentClasses} or any of its elements is null.
		 * @see Component#linear(Class[])
		 * @since 0.1.5
		 */
		public static Component[] linear(Class[]... componentClasses) {
			Objects.requireNonNull(componentClasses, "componentClasses");
			Component[] components = new Component[componentClasses.length];

			for (int i = 0; i < componentClasses.length; i++)
				components[i] = Component.linear(componentClasses[i]);

			return components;
		}

		/**
		 * Get linear components of general component clazzes. The given {@code componentClasses} specifies each array
		 * for a component in the returned components array.
		 * <br>
		 * Note: this is just an array version of {@link Component#linear(Class[])}.
		 * <pre>
		 *     Clazz.components(<font color="d3c4ff">{TREE0, NESTED0, DEEP0, …}, {TREE1, NESTED1, DEEP1, …}, …</font>)
		 *     &lt;<font color="d3c4ff">TREE0</font>&lt;<font color="d3c4ff">NESTED0</font>&lt;<font color="d3c4ff">DEEP0</font>…&gt;&gt;, <font color="d3c4ff">TREE1</font>&lt;<font color="d3c4ff">NESTED1</font>&lt;<font color="d3c4ff">DEEP1</font>…&gt;&gt;, …&gt;
		 * </pre>
		 *
		 * @param componentClasses an array of component classes, each array goes to the same position component in the
		 *                         returned components array.
		 * @param length           the minimum length the returned array will be.
		 * @return linear components of general component clazzes. The given {@code componentClasses} specifies each
		 * 		array for a component *in the returned components array.
		 * @throws NullPointerException if the given {@code componentClasses} or any of its elements is null.
		 * @see Component#linear(Class[])
		 * @since 0.1.5
		 */
		public static Component[] linear(Class[][] componentClasses, int length) {
			Objects.requireNonNull(componentClasses, "componentClasses");
			Component[] components = new Component[Math.max(componentClasses.length, length)];

			int i = 0;
			for (; i < componentClasses.length; i++)
				components[i] = Component.linear(componentClasses[i]);
			for (; i < length; i++)
				components[i] = new Component();

			return components;
		}

		/**
		 * Get an empty component.
		 *
		 * @return an empty component.
		 */
		public static Component of() {
			return new Component();
		}

		/**
		 * Get a component that its general clazz is the class of the given {@code componentClazz}, and have no
		 * mappings.
		 * <pre>
		 *     Clazz.component(<font color="d3c4ff">COMPONENT</font>)
		 *     <font color="d3c4ff">TREE</font>
		 * </pre>
		 *
		 * @param componentClazz the general clazz for the components that have no mapping in the returned component.
		 * @return a component that its general clazz is the class of the given {@code componentClazz}, and have no
		 * 		mappings.
		 * @see Component#of(Clazz[])
		 * @see Component#of(Clazz[], int)
		 * @since 0.1.5
		 */
		public static Component of(Class componentClazz) {
			Component component = new Component();
			component.put(Component.DEFAULT_CLAZZ, Clazz.of(componentClazz));
			return component;
		}

		/**
		 * Get an array of components that each one's general clazz is the clazz of the class at the same as its
		 * position in the given {@code componentClasses}.
		 * <pre>
		 *     Clazz.components(<font color="d3c4ff">COMPONENT0</font>, <font color="d3c4ff">COMPONENT1</font>, <font color="d3c4ff">COMPONENT2</font>, …)
		 *     &lt;<font color="d3c4ff">TREE0</font>, <font color="d3c4ff">TREE1</font>, <font color="d3c4ff">TREE2</font>, …&gt;
		 * </pre>
		 *
		 * @param componentClasses the general clazzes array for the components that have no mapping in the component at
		 *                         the same position as the clazz in returned components array.
		 * @return an array of components that each one's general clazz is the clazz of the class at the same as its *
		 * 		position in the given {@code componentClasses}.
		 * @throws NullPointerException if the given {@code componentClasses} is null.
		 * @see Component#of(Clazz[], int)
		 * @see Component#of(Clazz)
		 * @since 0.1.5
		 */
		public static Component[] of(Class... componentClasses) {
			Objects.requireNonNull(componentClasses, "componentClasses");
			Component[] components = new Component[componentClasses.length];

			for (int i = 0; i < componentClasses.length; i++)
				components[i] = Component.of(componentClasses[i]);

			return components;
		}

		/**
		 * Get an array of components that each one's general clazz is the clazz of the class at the same as its
		 * position in the given {@code componentClasses}.
		 * <pre>
		 *     Clazz.components(<font color="d3c4ff">COMPONENT0</font>, <font color="d3c4ff">COMPONENT1</font>, <font color="d3c4ff">COMPONENT2</font>, …)
		 *     &lt;<font color="d3c4ff">TREE0</font>, <font color="d3c4ff">TREE1</font>, <font color="d3c4ff">TREE2</font>, …&gt;
		 * </pre>
		 *
		 * @param componentClasses the general clazzes array for the components that have no mapping in the component at
		 *                         the same position as the clazz in returned components array.
		 * @param length           the minimum length the returned array will be.
		 * @return an array of components that each one's general clazz is the clazz of the class at the same as its *
		 * 		position in the given {@code componentClasses}.
		 * @throws NullPointerException if the given {@code componentClasses} is null.
		 * @see Component#of(Clazz[], int)
		 * @see Component#of(Clazz)
		 * @since 0.1.5
		 */
		public static Component[] of(Class[] componentClasses, int length) {
			Objects.requireNonNull(componentClasses, "componentClasses");
			Component[] components = new Component[Math.max(componentClasses.length, length)];

			int i = 0;
			for (; i < componentClasses.length; i++)
				components[i] = Component.of(componentClasses[i]);
			for (; i < length; i++)
				components[i] = new Component();

			return components;
		}

		/**
		 * Get a component that its general clazz is the given {@code componentClazz}, and have no mappings.
		 * <pre>
		 *     Clazz.component(<font color="d3c4ff">COMPONENT</font>)
		 *     <font color="d3c4ff">TREE</font>
		 * </pre>
		 *
		 * @param componentClazz the general clazz for the components that have no mapping in the returned component.
		 * @return a component that its general clazz is the given {@code componentClazz}, and have no mappings.
		 * @see Component#of(Clazz[])
		 * @see Component#of(Clazz[], int)
		 * @since 0.1.5
		 */
		public static Component of(Clazz componentClazz) {
			Component component = new Component();
			component.put(Component.DEFAULT_CLAZZ, componentClazz);
			return component;
		}

		/**
		 * Get an array of components that each one's general clazz is the clazz at the same as its position in the
		 * given {@code componentClazzes}.
		 * <pre>
		 *     Clazz.components(<font color="d3c4ff">COMPONENT0</font>, <font color="d3c4ff">COMPONENT1</font>, <font color="d3c4ff">COMPONENT2</font>, …)
		 *     &lt;<font color="d3c4ff">TREE0</font>, <font color="d3c4ff">TREE1</font>, <font color="d3c4ff">TREE2</font>, …&gt;
		 * </pre>
		 *
		 * @param componentClazzes the general clazzes array for the components that have no mapping in the component at
		 *                         the same position as the clazz in returned components array.
		 * @return an array of components that each one's general clazz is the clazz at the same as its position in the
		 * 		given {@code componentClazzes}.
		 * @throws NullPointerException if the given {@code componentClazzes} is null.
		 * @see Component#of(Clazz[], int)
		 * @see Component#of(Clazz)
		 * @since 0.1.5
		 */
		public static Component[] of(Clazz... componentClazzes) {
			Objects.requireNonNull(componentClazzes, "componentClazzes");
			Component[] components = new Component[componentClazzes.length];

			for (int i = 0; i < componentClazzes.length; i++)
				components[i] = Component.of(componentClazzes[i]);

			return components;
		}

		/**
		 * Get an array of components that each one's general clazz is the clazz at the same as its position in the
		 * given {@code componentClazzes}.
		 * <pre>
		 *     Clazz.components(<font color="fc888a">MIN_LENGTH</font>, <font color="d3c4ff">COMPONENT0</font>, <font color="d3c4ff">COMPONENT1</font>, <font color="d3c4ff">COMPONENT2</font>, …)
		 *     &lt;<font color="d3c4ff">TREE0</font>, <font color="d3c4ff">TREE1</font>, <font color="d3c4ff">TREE2</font>, <font color="fc888a">…</font>&gt;
		 * </pre>
		 *
		 * @param componentClazzes the general clazzes array for the components that have no mapping in the component at
		 *                         the same position as the clazz in returned components array.
		 * @param length           the minimum length the returned array will be.
		 * @return an array of components that each one's general clazz is the clazz at the same as its position in the
		 * 		given {@code componentClazzes}.
		 * @throws NullPointerException if the given {@code componentClazzes} is null.
		 * @see Component#of(Clazz[])
		 * @see Component#of(Clazz)
		 */
		public static Component[] of(Clazz[] componentClazzes, int length) {
			Objects.requireNonNull(componentClazzes, "componentClazzes");
			Component[] components = new Component[Math.max(componentClazzes.length, length)];

			int i = 0;
			for (; i < componentClazzes.length; i++)
				components[i] = Component.of(componentClazzes[i]);
			for (; i < length; i++)
				components[i] = new Component();

			return components;
		}

		/**
		 * Finalize the given {@code component} and make it unmodifiable.
		 *
		 * @param component the component to be finalized.
		 * @return the given {@code component}.
		 */
		static Component finalize(Component component) {
			if (component != null)
				component.finalized = true;
			return component;
		}

		/**
		 * Finalize the given {@code components} and make them unmodifiable.
		 *
		 * @param components the components to be finalized.
		 * @return a copy of the given {@code components}.
		 * @throws NullPointerException if the given {@code components} is null.
		 */
		static Component[] finalize(Component... components) {
			Objects.requireNonNull(components, "components");
			Component[] finalized = new Component[components.length];

			for (int i = 0; i < components.length; i++)
				Component.finalize(finalized[i] = components[i]);

			return finalized;
		}

		@Override
		public Clazz put(Object key, Clazz klazz) {
			if (this.finalized)
				throw new UnsupportedOperationException("Finalized Component");

			for (Entry entry : this.entrySet)
				if (entry.getKey().equals(key))
					return entry.setValue(klazz);

			this.entrySet.add(new Entry(key, klazz));
			//noinspection ReturnOfNull
			return null;
		}

		@Override
		public Set<Entry<Object, Clazz>> entrySet() {
			return Collections.unmodifiableSet(this.entrySet);
		}

		@Override
		public String toString() {
			return "component " + this.getName();
		}

		/**
		 * Get the {@link Component#DEFAULT_CLAZZ componentClazz} of this component. The {@link Component#DEFAULT_CLAZZ
		 * componentClazz} is the clazz for the elements that have no clazz in the component of the clazz representing
		 * it.
		 *
		 * @return the {@link Component#DEFAULT_CLAZZ componentClazz} of this component, Or null if this component has
		 * 		no {@link Component#DEFAULT_CLAZZ componentClazz}.
		 * @since 0.1.5
		 */
		public Clazz getComponentClazz() {
			return this.get(Component.DEFAULT_CLAZZ);
		}

		/**
		 * Get the {@code componentClazz} for the element with the given {@code key} in this component.
		 *
		 * @param key the key of the returned clazz in this component.
		 * @return the {@code componentClazz} for the element with the given {@code key} in this component, Or the
		 *        {@link Component#DEFAULT_CLAZZ componentClazz} of this component if there is no {@code componentClazz} with
		 * 		the given key in this component.
		 * @since 0.1.5
		 */
		public Clazz getComponentClazz(Object key) {
			Clazz klazz = this.get(key);
			return klazz == null ? this.get(Component.DEFAULT_CLAZZ) : klazz;
		}

		/**
		 * Get the {@code Name} of this component. The {@code Name} of a component is the {@link #getName() Name} of its
		 * {@link Component#DEFAULT_CLAZZ componentClazz}, or {@code "?"} if it has no {@link Component#DEFAULT_CLAZZ
		 * componentClazz}.
		 *
		 * @return the {@code Name} of this component, Or {@code "?"} if this component has no {@link
		 *        Component#DEFAULT_CLAZZ componentClazz}.
		 * @since 0.1.5
		 */
		public String getName() {
			Clazz component = this.get(Component.DEFAULT_CLAZZ);
			return component == null ? "?" : component.getName();
		}

		/**
		 * Get the {@code SimpleName} of this component. The {@code SimpleName} of a component is the {@link
		 * #getSimpleName() SimpleName} of its {@link Component#DEFAULT_CLAZZ componentClazz}, or {@code "?"} if it has
		 * no {@link Component#DEFAULT_CLAZZ componentClazz}.
		 *
		 * @return the {@code SimpleName} of this component, Or {@code "?"} if this component has no {@link
		 *        Component#DEFAULT_CLAZZ componentClazz}.
		 * @since 0.1.5
		 */
		public String getSimpleName() {
			Clazz component = this.get(Component.DEFAULT_CLAZZ);
			return component == null ? "?" : component.getSimpleName();
		}

		/**
		 * Get the {@code TypeName} of this component. The {@code TypeName} of a component is the {@link #getTypeName()
		 * TypeName} of its {@link Component#DEFAULT_CLAZZ componentClazz}, or {@code "?"} if it has no {@link
		 * Component#DEFAULT_CLAZZ componentClazz}.
		 *
		 * @return the {@code TypeName} of this component, Or {@code "?"} if this component has no {@link
		 *        Component#DEFAULT_CLAZZ componentClazz}.
		 * @since 0.1.5
		 */
		public String getTypeName() {
			Clazz component = this.get(Component.DEFAULT_CLAZZ);
			return component == null ? "?" : component.getTypeName();
		}

		/**
		 * Determine if modifications allowed in this component.
		 *
		 * @return true, if modifications are allowed in this component.
		 */
		public boolean isModifiable() {
			return this.modifiable;
		}

		/**
		 * Set whether modifications allowed in this component or not.
		 *
		 * @param modifiable the new modification state.
		 */
		private void setModifiable(boolean modifiable) {
			this.modifiable = modifiable;
		}

		/**
		 * An {@link Map.Entry} implementation for {@link Component}s.
		 */
		private final class ComponentEntry extends AbstractMap.SimpleEntry<Object, Clazz> {
			/**
			 * Construct a new component-entry.
			 *
			 * @param key   the key of this entry.
			 * @param klazz the klazz (value) to this entry.
			 */
			private ComponentEntry(Object key, Clazz klazz) {
				super(key, klazz);
			}

			@Override
			public Clazz setValue(Clazz value) {
				if (Component.this.isModifiable())
					return super.setValue(value);

				throw new UnsupportedOperationException("Unmodifiable Component");
			}
		}
	}

	/**
	 * A util class for auto-generating clazzes.
	 */
	@SuppressWarnings("JavaDoc")
	public static final class Generate {
		/**
		 * This is an util class and must not be instanced as an object.
		 *
		 * @throws AssertionError when called.
		 */
		private Generate() {
			throw new AssertionError("No instance for you!");
		}

		public static <T> Clazz<T> from(T instance, Clazz... componentClazzes) {
			return Generate.from0(new HashMap(), instance, componentClazzes);
		}

		public static <T> Clazz<T> from(T instance, Class family, Clazz... componentClazzes) {
			return Generate.from0(new HashMap(), instance, family, componentClazzes);
		}

		static <T> Clazz<T> from0(Map dejaVus, T instance, Clazz... componentClazzes) {
			Class family = instance == null ? Void.class : instance.getClass();
			return Generate.from0(dejaVus, instance, family, componentClazzes);
		}

		static <T> Clazz<T> from0(Map dejaVus, T instance, Class family, Clazz... componentClazzes) {
			Objects.requireNonNull(dejaVus, "dejaVus");
			Objects.requireNonNull(family, "family");
			Objects.requireNonNull(componentClazzes, "componentClazzes");

			if (instance != null)
				if (instance instanceof Map)
					return Generate.fromMap(dejaVus, (Map) instance, family, componentClazzes);
				else if (instance instanceof Iterable)
					return Generate.fromIterable(dejaVus, (Iterable) instance, family, componentClazzes);
				else if (instance.getClass().isArray())
					return Generate.fromArray(dejaVus, instance, family, componentClazzes);

			return Clazz.from(instance, family, Component.as(componentClazzes));
		}

		static <T> Clazz<T> fromArray(Map<Object, Clazz> dejaVus, Object array, Class family, Clazz[] componentClazzes) {
			Objects.requireNonNull(array, "array");
			Objects.requireNonNull(componentClazzes, "componentClazzes");

			Clazz klazz = new Clazz();
			return null;
		}

		static <T> Clazz<T> fromIterable(Map<Object, Clazz> dejaVus, Iterable iterable, Class family, Clazz[] componentClazzes) {
			Objects.requireNonNull(dejaVus, "dejaVus");
			Objects.requireNonNull(iterable, "iterable");
			Objects.requireNonNull(family, "family");
			Objects.requireNonNull(componentClazzes, "componentClazzes");

			Component[] components = Component.as(1, componentClazzes);
			Clazz klazz = Clazz.from(iterable, family);
			//assign for later use
			dejaVus.put(iterable, klazz);

			Iterator iterator = iterable.iterator();
			for (int i = 0; iterator.hasNext(); i++) {
				Object element = iterator.next();

				//if it has been solved before
				if (dejaVus.containsKey(element))
					components[0].put(i, dejaVus.get(element));
				else
					components[0].put(i, Generate.from0(dejaVus, element, componentClazzes));
			}

			//finalize everything
			Component.setModifiable(components, false);
			klazz.setComponents(components);
			return klazz;
		}

		static <T> Clazz<T> fromMap(Map<Object, Clazz> dejaVus, Map map, Class family, Clazz[] componentClazzes) {
			Objects.requireNonNull(map, "map");
			Objects.requireNonNull(componentClazzes, "componentClazzes");

			return null;
		}
	}
}
