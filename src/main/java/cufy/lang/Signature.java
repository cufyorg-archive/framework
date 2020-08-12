package cufy.lang;

import cufy.util.Arrays;
import cufy.util.Objects;
import cufy.util.Reflection;
import cufy.util.array.Array;

import java.lang.annotation.*;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A signature declaring the properties of a reflected element. TODO
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.07.24
 */
public final class Signature {
	/**
	 * A pattern matching the points between the {@code name} and the {@code values} of a {@code
	 * label}.
	 * <pre>
	 *     ex: "label: value|value|value" -> "name$ value|value|value"
	 * </pre>
	 */
	public static final Pattern REGEX_LABEL = Pattern.compile("[+-]?[:=]");
	/**
	 * A pattern matching the points between each {@code label} in a {@code signature}.
	 * <pre>
	 *     ex: "label: value label: value label: value" -> "label: value$label: value$label: value"
	 * </pre>
	 */
	public static final Pattern REGEX_SIGNATURE = Pattern.compile("([\\s]+|(?<=[+-]?[:=]))(?=[\\S]*[+-]?[:=])(?<!^)");
	/**
	 * A pattern matching the points between each {@code value} in a {@code values}.
	 * <pre>
	 *     ex: "value|value|value" -> "value$value$value"
	 * </pre>
	 */
	public static final Pattern REGEX_VALUES = Pattern.compile("\\s*[|, ]\\s*");
	/**
	 * A pattern matching the whitespaces in a string.
	 */
	public static final Pattern REGEX_WHITESPACE = Pattern.compile("[\\s]+");

	/**
	 * A syntax to declare a label that appends the previous label with the same name.
	 * <pre>
	 *     labelName+:values
	 * </pre>
	 */
	public static final String SYNTAX_APPEND = "+:";
	/**
	 * A syntax to separate between label values.
	 * <pre>
	 *     value|value
	 * </pre>
	 */
	public static final String SYNTAX_OR = "|";
	/**
	 * A syntax to declare that the label overrides the values of the previous label with the same
	 * name.
	 * <pre>
	 *     labelName:values
	 * </pre>
	 */
	public static final String SYNTAX_OVERRIDE = ":";
	/**
	 * A syntax to declare a label that removes values from the previous label with the same name.
	 * <pre>
	 *     labelName-:values
	 * </pre>
	 */
	public static final String SYNTAX_REMOVE = "-:";
	/**
	 * A whitespace.
	 */
	public static final String SYNTAX_WS = " ";

	/**
	 * The element this signature is for.
	 */
	private final AnnotatedElement element;
	/**
	 * The standards used by this signature.
	 */
	private final Class[] standards;

	/**
	 * A map associating the name of each label in this signature to a set of its values.
	 */
	private Map<String, Set<String>> labelsMap;
	/**
	 * Cached value for {@link #toRawString()}.
	 */
	private String rawString;
	/**
	 * Cached value of the string representing this signature.
	 */
	private String string;

	/**
	 * Construct a new signature with the given parameter.
	 *
	 * @param element   the element.
	 * @param standards the standards to be used.
	 * @throws NullPointerException if the given {@code type} or {@code standards} is null.
	 */
	private Signature(AnnotatedElement element, Class... standards) {
		Objects.requireNonNull(element, "element");
		Objects.requireNonNull(standards, "standards");
		this.element = element;
		this.standards = standards;
	}

	/**
	 * Compute the given {@code labels} to a map associating the names of the labels to a sets of
	 * their values.
	 *
	 * @param labels the labels to be computed.
	 * @return a map associating the name of each label to a set of its values.
	 * @throws NullPointerException if the given {@code labels} is null.
	 */
	public static Map<String, Set<String>> computeLabelsMap(String[] labels) {
		Objects.requireNonNull(labels, "labels");

		Map<String, Set<String>> computed = new LinkedHashMap();

		for (String label : labels)
			if (label != null) {
				String labelName = Signature.extractLabelName(label);
				String labelOperator = Signature.extractLabelOperator(label);
				String[] labelValues = Signature.extractLabelValues(label);

				switch (labelOperator) {
					case Signature.SYNTAX_APPEND:
						computed.compute(labelName, (name, set) -> {
							if (set == null)
								return new LinkedHashSet(Arrays.asList(labelValues));

							set.addAll(Arrays.asList(labelValues));
							return set;
						});
						break;
					case Signature.SYNTAX_REMOVE:
						computed.computeIfPresent(labelName, (name, set) -> {
							set.removeAll(Arrays.asList(labelValues));
							return set;
						});
						break;
					case Signature.SYNTAX_OVERRIDE:
						computed.compute(labelName, (name, set) -> {
							if (set == null)
								return new LinkedHashSet(Arrays.asList(labelValues));

							set.clear();
							set.addAll(Arrays.asList(labelValues));
							return set;
						});
						break;
				}
			}

		return computed;
	}

	/**
	 * Extract the name of the given {@code label}.
	 *
	 * @param label the label to extract its name.
	 * @return the name of the given {@code label}. Or an empty string if the given {@code label}
	 * 		has no name.
	 * @throws NullPointerException if the given {@code label} is null.
	 */
	public static String extractLabelName(String label) {
		Objects.requireNonNull(label, "label");
		String[] split = Signature.REGEX_LABEL.split(label, 3);
		return split.length == 1 ? "" : split[0];
	}

	/**
	 * Extract the operator of the given {@code label}.
	 *
	 * @param label the label to extract its operator.
	 * @return the operator of the given {@code label}. Or an empty string if the given {@code
	 * 		label} has no label.
	 * @throws NullPointerException if the given {@code label} is null.
	 */
	public static String extractLabelOperator(String label) {
		Objects.requireNonNull(label, "label");
		Matcher matcher = Signature.REGEX_LABEL.matcher(label);
		return matcher.find() ? matcher.group() : "";
	}

	/**
	 * Extract an array of the values of the given {@code label}.
	 *
	 * @param label the label to extract an array of its values.
	 * @return an array of the values of the given {@code label}.
	 * @throws NullPointerException if the given {@code label} is null.
	 */
	public static String[] extractLabelValues(String label) {
		Objects.requireNonNull(label, "label");
		String[] split = Signature.REGEX_LABEL.split(label, 2);
		return Signature.REGEX_VALUES.split(split[split.length == 1 ? 0 : 1].trim());
	}

	/**
	 * Extract an array of the labels of the given {@code signature}.
	 *
	 * @param signature the signature to extract an array of the labels of it.
	 * @return an array of the labels of the given {@code signature}.
	 * @throws NullPointerException if the given {@code signature} is null.
	 */
	public static String[] extractLabels(String signature) {
		Objects.requireNonNull(signature, "signature");
		return Signature.REGEX_SIGNATURE.split(signature);
	}

	/**
	 * Get a signature representation for the given {@code element}.
	 *
	 * @param element   the element the constructed signature representation is for.
	 * @param standards the standards to be used.
	 * @return a signature representation for the given {@code element}.
	 * @throws NullPointerException if the given {@code element} or {@code standards} is null.
	 */
	public static Signature of(AnnotatedElement element, Class... standards) {
		Objects.requireNonNull(element, "element");
		Objects.requireNonNull(standards, "standards");
		return new Signature(element, standards);
	}

	/**
	 * Reformat the joint string of the given {@code signature} array into one standard formatted
	 * signature.
	 *
	 * @param signature the signature array to be joint then reformatted.
	 * @return the joint string of the given {@code signature} array into one standard formatted
	 * 		signature.
	 * @throws NullPointerException if the given {@code signature} is null.
	 */
	public static String reformat(String... signature) {
		Objects.requireNonNull(signature, "signature");

		String s = String.join(Signature.SYNTAX_WS, signature);

		//.*s+.* -> .* .*
		//"e : \n\t e \n\t e | e|e:" -> "e : e e | e|e:"
		//replace repeated whitespaces with one whitespace
		s = Signature.REGEX_WHITESPACE.matcher(s).replaceAll(Signature.SYNTAX_WS);

		//deprecated
		//.*W+s*W+.* -> .*W+W+.*
		//"e : e e |e:" -> "e:e e|e|e:"
		//remove whitespaces nearby any symbol (non-word characters)
		//regex: ([\s]+(?=\W|^))|((?<=\W|$)[\s]+)
		//s = Regex.MATCH_REDUNDANT_WHITESPACE.matcher(s).replaceAll("");

		//.*w+:.* -> .* w+:.*
		//"e:e e|e|e:" -> "e:e e|e| e:"
		//add whitespaces before any label-name (unless there is an escape character before it)
		s = Signature.REGEX_SIGNATURE.matcher(s).replaceAll(Signature.SYNTAX_WS);

		return s;
	}

	@Override
	public boolean equals(Object object) {
		return object == this ||
			   object instanceof Signature &&
			   //LinkedHashMap<String,    LinkedHashSet<String>>
			   //labelsMap    <labelName, labelValues  <value >>
			   ((Signature) object).getLabelsMap().equals(this.getLabelsMap());
	}

	@Override
	public int hashCode() {
		return this.getLabelsMap().hashCode();
	}

	@Override
	public String toString() {
		if (this.string == null) {
			StringJoiner string = new StringJoiner(Signature.SYNTAX_WS);

			this.getLabelsMap().forEach((labelName, values) -> {
				if (labelName != null && values != null)
					string.add(labelName + Signature.SYNTAX_OVERRIDE)
							.add(String.join(Signature.SYNTAX_OR, values));
			});

			this.string = string.toString();
		}

		return this.string;
	}

	/**
	 * Get an unmodifiable set containing the values of a label in this signature that has the given
	 * {@code labelName}.
	 *
	 * @param labelName the name of the label to get an unmodifiable set of containing its values.
	 * @return an unmodifiable set containing the values of a label in this signature that has the
	 * 		given {@code labelName}. Or an empty set if there is no such label.
	 * @throws NullPointerException if the given {@code labelsName} is null.
	 */
	public Set<String> get(String labelName) {
		Objects.requireNonNull(labelName, "labelName");
		Set<String> values = this.getLabelsMap().get(labelName);
		return values == null ? Collections.emptySet() : values;
	}

	/**
	 * Get a map associating the name of each label in this signature to a set of its values.
	 *
	 * @return a map associating the name of each label in this signature to a set of its values.
	 */
	public Map<String, Set<String>> getLabelsMap() {
		if (this.labelsMap == null) {
			Map<String, Set<String>> labelsMap = Signature.computeLabelsMap(Signature.extractLabels(this.toRawString()));
			labelsMap.replaceAll((name, set) -> Collections.unmodifiableSet(set));
			this.labelsMap = Collections.unmodifiableMap(labelsMap);
		}

		return this.labelsMap;
	}

	/**
	 * Get the raw string from combining the sources of this signature.
	 *
	 * @return the raw string of this signature.
	 */
	public String toRawString() {
		if (this.rawString == null) {
			//init
			StringJoiner rawString = new StringJoiner(Signature.SYNTAX_WS);

			//standards
			for (Class standard : this.standards)
				if (standard != null)
					for (Field field : Reflection.fieldsSet(standard))
						if (field.isAnnotationPresent(Standard.class))
							for (Class klass : field.getAnnotation(Standard.class).value())
								if (klass.isAssignableFrom(this.element.getClass())) {
									try {
										Object value = field.get(null);

										if (value instanceof Function)
											rawString.add(Objects.toString(((Function<AnnotatedElement, Object>) value).apply(this.element), ""));
										else if (value instanceof Supplier)
											rawString.add(Objects.toString(((Supplier) value).get(), ""));
										else if (value instanceof BiConsumer)
											((BiConsumer<StringJoiner, AnnotatedElement>) value).accept(rawString, this.element);
										else
											rawString.add(Objects.toString(value, ""));
									} catch (Throwable ignored) {
										//readonly method should not fail for something has already been written!
									}

									//lets say we allowed multiple standards for one element, how should we order them?
									break;
								}

			//annotations
			Annotation[] array = this.element.getAnnotations();
			List<Annotation> annotations = new ArrayList(Arrays.asList(array));
			for (int i = 0, l = array.length; i < l; i++) {
				Annotation annotation = annotations.get(i);

				for (Method method : annotation.annotationType().getDeclaredMethods())
					if (method.isAnnotationPresent(Attribute.class)) {
						Attribute attribute = method.getAnnotation(Attribute.class);

						try {
							Object value = method.invoke(annotation);

							if (value instanceof Annotation) {
								//push it to do it later ;P
								Annotation a = (Annotation) value;
								annotations.add(a);
								l++;
							} else if (value instanceof Annotation[]) {
								//push all to do them later
								Annotation[] a = (Annotation[]) value;
								annotations.addAll(Arrays.asList(a));
								l += a.length;
							} else if (value != null && value.getClass().isArray()) {
								List a = Array.of(value).list();

								if (!a.isEmpty()) {
									StringJoiner joiner = new StringJoiner(Signature.SYNTAX_WS);

									for (Object element : a)
										joiner.add(Objects.toString(element, ""));

									rawString.add(String.format(attribute.value(), joiner));
								}
							} else {
								String string = Objects.toString(value, "");

								if (!string.isEmpty())
									rawString.add(String.format(attribute.value(), string));
							}
						} catch (Throwable ignored) {
							//readonly method should not fail for something has already been written!
						}
					}
			}

			//cache
			this.rawString = rawString.toString();
		}

		return this.rawString;
	}

	/**
	 * Declares that the annotated method (of an annotation) is a method that modifies the signature
	 * of the method that gets annotated with its enclosing annotation.
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Attribute {
		/**
		 * The format to be applied to the string returned from the annotated method before
		 * appending it to the signature of the element annotated by the annotation of the method.
		 *
		 * @return the format to be used on the returned string from the annotated method.
		 */
		String value() default "%s";
	}

	/**
	 * Declares that the annotated field (of an annotation) is a field that its value resolves the
	 * standard signature of an {@link AnnotatedElement} that inherits any of the {@link
	 * Standard#value()} classes.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface Standard {
		/**
		 * The classes that the annotated field can resolve its standard signature.
		 *
		 * @return the classes that the annotated field can resolve its standard signature.
		 */
		Class[] value() default AnnotatedElement.class;
	}
}
