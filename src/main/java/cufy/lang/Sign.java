package cufy.lang;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.*;
import java.util.StringJoiner;
import java.util.function.BiConsumer;

/**
 * Append the values of the specified labels to the signature of the annotated element.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.07.27
 */
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Sign.Array.class)
public @interface Sign {
	/**
	 * The {@code abstract} value.
	 */
	String ABSTRACT = "abstract";
	/**
	 * The {@code final} value.
	 */
	String FINAL = "final";
	/**
	 * The {@code interface} value.
	 */
	String INTERFACE = "interface";
	/**
	 * The name of the modifiers label.
	 */
	String MODIFIERS = "modifiers";
	/**
	 * The label to declare the name in a signature.
	 */
	String NAME = "name";
	/**
	 * The {@code native} value.
	 */
	String NATIVE = "native";
	/**
	 * The {@code private} value.
	 */
	String PRIVATE = "private";
	/**
	 * The {@code protected value.
	 */
	String PROTECTED = "protected";
	/**
	 * The {@code public} value.
	 */
	String PUBLIC = "public";
	/**
	 * The {@code static} value.
	 */
	String STATIC = "static";
	/**
	 * The {@code strictfp} value.
	 */
	String STRICTFP = "strictfp";
	/**
	 * The {@code synchronized} value.
	 */
	String SYNCHRONIZED = "synchronized";
	/**
	 * The {@code transient} value.
	 */
	String TRANSIENT = "transient";
	/**
	 * The {@code volatile} value.
	 */
	String VOLATILE = "volatile";

	/**
	 * A function to {@link StringJoiner#add(CharSequence)} the default signature of the given
	 * {@code method} to the given {@code joiner}.
	 */
	@Signature.Standard(Method.class)
	BiConsumer<StringJoiner, Method> METHOD = (joiner, method) -> {
		joiner.add(Sign.MODIFIERS + Signature.SYNTAX_OVERRIDE);
		int modifiers = method.getModifiers();
		if (Modifier.isPublic(modifiers))
			joiner.add(Sign.PUBLIC);
		if (Modifier.isProtected(modifiers))
			joiner.add(Sign.PROTECTED);
		if (Modifier.isPrivate(modifiers))
			joiner.add(Sign.PRIVATE);
		if (Modifier.isAbstract(modifiers))
			joiner.add(Sign.ABSTRACT);
		if (Modifier.isStatic(modifiers))
			joiner.add(Sign.STATIC);
		if (Modifier.isFinal(modifiers))
			joiner.add(Sign.FINAL);
		if (Modifier.isTransient(modifiers))
			joiner.add(Sign.TRANSIENT);
		if (Modifier.isVolatile(modifiers))
			joiner.add(Sign.VOLATILE);
		if (Modifier.isSynchronized(modifiers))
			joiner.add(Sign.SYNCHRONIZED);
		if (Modifier.isNative(modifiers))
			joiner.add(Sign.NATIVE);
		if (Modifier.isStrict(modifiers))
			joiner.add(Sign.STRICTFP);
		if (Modifier.isInterface(modifiers))
			joiner.add(Sign.INTERFACE);

		joiner.add(Sign.NAME + Signature.SYNTAX_OVERRIDE);
		joiner.add(method.getName());
	};

	/**
	 * A function to {@link StringJoiner#add(CharSequence)} the default signature of the given
	 * {@code klass} to the given {@code joiner}.
	 */
	@Signature.Standard(Class.class)
	BiConsumer<StringJoiner, Class> CLASS = (joiner, klass) -> {
		joiner.add(Sign.MODIFIERS + Signature.SYNTAX_OVERRIDE);
		int modifiers = klass.getModifiers();
		if (Modifier.isPublic(modifiers))
			joiner.add(Sign.PUBLIC);
		if (Modifier.isProtected(modifiers))
			joiner.add(Sign.PROTECTED);
		if (Modifier.isPrivate(modifiers))
			joiner.add(Sign.PRIVATE);
		if (Modifier.isAbstract(modifiers))
			joiner.add(Sign.ABSTRACT);
		if (Modifier.isStatic(modifiers))
			joiner.add(Sign.STATIC);
		if (Modifier.isFinal(modifiers))
			joiner.add(Sign.FINAL);
		if (Modifier.isTransient(modifiers))
			joiner.add(Sign.TRANSIENT);
		if (Modifier.isVolatile(modifiers))
			joiner.add(Sign.VOLATILE);
		if (Modifier.isSynchronized(modifiers))
			joiner.add(Sign.SYNCHRONIZED);
		if (Modifier.isNative(modifiers))
			joiner.add(Sign.NATIVE);
		if (Modifier.isStrict(modifiers))
			joiner.add(Sign.STRICTFP);
		if (Modifier.isInterface(modifiers))
			joiner.add(Sign.INTERFACE);

		joiner.add(Sign.NAME + Signature.SYNTAX_OVERRIDE);
		joiner.add(klass.getName());
	};

	/**
	 * A function to {@link StringJoiner#add(CharSequence)} the default signature of the given
	 * {@code field} to the given {@code joiner}.
	 */
	@Signature.Standard(Field.class)
	BiConsumer<StringJoiner, Field> FIELD = (joiner, field) -> {
		joiner.add(Sign.MODIFIERS + Signature.SYNTAX_OVERRIDE);
		int modifiers = field.getModifiers();
		if (Modifier.isPublic(modifiers))
			joiner.add(Sign.PUBLIC);
		if (Modifier.isProtected(modifiers))
			joiner.add(Sign.PROTECTED);
		if (Modifier.isPrivate(modifiers))
			joiner.add(Sign.PRIVATE);
		if (Modifier.isAbstract(modifiers))
			joiner.add(Sign.ABSTRACT);
		if (Modifier.isStatic(modifiers))
			joiner.add(Sign.STATIC);
		if (Modifier.isFinal(modifiers))
			joiner.add(Sign.FINAL);
		if (Modifier.isTransient(modifiers))
			joiner.add(Sign.TRANSIENT);
		if (Modifier.isVolatile(modifiers))
			joiner.add(Sign.VOLATILE);
		if (Modifier.isSynchronized(modifiers))
			joiner.add(Sign.SYNCHRONIZED);
		if (Modifier.isNative(modifiers))
			joiner.add(Sign.NATIVE);
		if (Modifier.isStrict(modifiers))
			joiner.add(Sign.STRICTFP);
		if (Modifier.isInterface(modifiers))
			joiner.add(Sign.INTERFACE);

		joiner.add(Sign.NAME + Signature.SYNTAX_OVERRIDE);
		joiner.add(field.getName());
	};

	/**
	 * A function to {@link StringJoiner#add(CharSequence)} the default signature of the given
	 * {@code parameter} to the given {@code joiner}.
	 */
	@Signature.Standard(Parameter.class)
	BiConsumer<StringJoiner, Parameter> PARAMETER = (joiner, parameter) -> {
		joiner.add(Sign.MODIFIERS + Signature.SYNTAX_OVERRIDE);
		int modifiers = parameter.getModifiers();
		if (Modifier.isPublic(modifiers))
			joiner.add(Sign.PUBLIC);
		if (Modifier.isProtected(modifiers))
			joiner.add(Sign.PROTECTED);
		if (Modifier.isPrivate(modifiers))
			joiner.add(Sign.PRIVATE);
		if (Modifier.isAbstract(modifiers))
			joiner.add(Sign.ABSTRACT);
		if (Modifier.isStatic(modifiers))
			joiner.add(Sign.STATIC);
		if (Modifier.isFinal(modifiers))
			joiner.add(Sign.FINAL);
		if (Modifier.isTransient(modifiers))
			joiner.add(Sign.TRANSIENT);
		if (Modifier.isVolatile(modifiers))
			joiner.add(Sign.VOLATILE);
		if (Modifier.isSynchronized(modifiers))
			joiner.add(Sign.SYNCHRONIZED);
		if (Modifier.isNative(modifiers))
			joiner.add(Sign.NATIVE);
		if (Modifier.isStrict(modifiers))
			joiner.add(Sign.STRICTFP);
		if (Modifier.isInterface(modifiers))
			joiner.add(Sign.INTERFACE);

		joiner.add(Sign.NAME + Signature.SYNTAX_OVERRIDE);
		joiner.add(parameter.getName());
	};

	/**
	 * A function to {@link StringJoiner#add(CharSequence)} the default signature of the given
	 * {@code constructor} to the given {@code joiner}.
	 */
	@Signature.Standard(Constructor.class)
	BiConsumer<StringJoiner, Constructor> CONSTRUCTOR = (joiner, constructor) -> {
		joiner.add(Sign.MODIFIERS + Signature.SYNTAX_OVERRIDE);
		int modifiers = constructor.getModifiers();
		if (Modifier.isPublic(modifiers))
			joiner.add(Sign.PUBLIC);
		if (Modifier.isProtected(modifiers))
			joiner.add(Sign.PROTECTED);
		if (Modifier.isPrivate(modifiers))
			joiner.add(Sign.PRIVATE);
		if (Modifier.isAbstract(modifiers))
			joiner.add(Sign.ABSTRACT);
		if (Modifier.isStatic(modifiers))
			joiner.add(Sign.STATIC);
		if (Modifier.isFinal(modifiers))
			joiner.add(Sign.FINAL);
		if (Modifier.isTransient(modifiers))
			joiner.add(Sign.TRANSIENT);
		if (Modifier.isVolatile(modifiers))
			joiner.add(Sign.VOLATILE);
		if (Modifier.isSynchronized(modifiers))
			joiner.add(Sign.SYNCHRONIZED);
		if (Modifier.isNative(modifiers))
			joiner.add(Sign.NATIVE);
		if (Modifier.isStrict(modifiers))
			joiner.add(Sign.STRICTFP);
		if (Modifier.isInterface(modifiers))
			joiner.add(Sign.INTERFACE);

		joiner.add(Sign.NAME + Signature.SYNTAX_OVERRIDE);
		joiner.add(constructor.getName());
	};

	/**
	 * Override the {@link Sign#MODIFIERS} label of the annotated element.
	 *
	 * @return the new values of the {@link Sign#MODIFIERS} of the annotated element.
	 */
	@Signature.Attribute(Sign.MODIFIERS + Signature.SYNTAX_OVERRIDE + "%s")
	String[] modifiers() default {};

	/**
	 * Append the returned values to the {@link Sign#MODIFIERS} label of the annotated element.
	 *
	 * @return the values to be appended to the {@link Sign#MODIFIERS} label of the annotated
	 * 		element.
	 */
	@Signature.Attribute(Sign.MODIFIERS + Signature.SYNTAX_APPEND + "%s")
	String[] modifiersAppend() default {};

	/**
	 * Remove the returned values from the {@link Sign#MODIFIERS} label of the annotated element.
	 *
	 * @return the values to be removed from the {@link Sign#MODIFIERS} label of the annotated
	 * 		element.
	 */
	@Signature.Attribute(Sign.MODIFIERS + Signature.SYNTAX_REMOVE + "%s")
	String[] modifiersRemove() default {};

	/**
	 * Override the {@link Sign#NAME} label of the annotated element.
	 *
	 * @return the new values of the {@link Sign#NAME} label of the annotated element.
	 */
	@Signature.Attribute(Sign.NAME + Signature.SYNTAX_OVERRIDE + "%s")
	String[] name() default {};

	/**
	 * Append the returned values to the {@link Sign#NAME} label of the annotated element.
	 *
	 * @return the values to be appended to the {@link Sign#NAME} label of the annotated element.
	 */
	@Signature.Attribute(Sign.NAME + Signature.SYNTAX_APPEND + "%s")
	String[] nameAppend() default {};

	/**
	 * Remove the returned values from the {@link Sign#NAME} label of the annotated element.
	 *
	 * @return the values to be removed from the {@link Sign#NAME} label of the annotated element.
	 */
	@Signature.Attribute(Sign.NAME + Signature.SYNTAX_REMOVE + "%s")
	String[] nameRemove() default {};

	/**
	 * Append the returned strings to the signature of the annotated element.
	 *
	 * @return the strings to be appended to the signature of the annotated element.
	 */
	@Signature.Attribute
	String[] value() default {};

	/**
	 * An array of the {@link Sign} annotation.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@interface Array {
		/**
		 * Return an array of the {@link Sign} annotation.
		 *
		 * @return an array of the {@link Sign} annotation.
		 */
		@Signature.Attribute
		Sign[] value();
	}
}
