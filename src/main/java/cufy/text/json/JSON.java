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
package cufy.text.json;

import cufy.lang.Clazz;
import cufy.lang.Empty;
import cufy.meta.Filter;
import cufy.meta.Where;
import cufy.text.*;
import cufy.util.Arrayz;
import cufy.util.Readerz;
import cufy.util.Stringz;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A formatter/parser for JSON.
 * <p>
 * <b>Dynamic Methods:</b>
 * <ul>
 *     <li>
 *         <b>{@link Collection Array}</b>
 *         <ul>
 *             	<li>{@link #formatArray format}</li>
 *         		<li>{@link #isArray classify}</li>
 *         		<li>{@link #parseArray parse}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <b>{@link Boolean}</b>
 *     		<ul>
 *     		 	<li>{@link #formatBoolean format}</li>
 *     			<li>{@link #isBoolean classify}</li>
 *     			<li>{@link #parseBoolean parse}</li>
 *     		</ul>
 *     </li>
 *     <li>
 *         <b>{@link Number}</b>
 *         <ul>
 *             	<li>{@link #formatNumber format}</li>
 *             	<li>{@link #isNumber classify}</li>
 *             	<li>{@link #parseNumber parse}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <b>{@link Map Object}</b>
 *         <ul>
 *            	<li>{@link #formatObject format}</li>
 *            	<li>{@link #isObject classify}</li>
 *            	<li>{@link #parseObject parse}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <b>{@link CharSequence String}</b>
 *         <ul>
 *             	<li>{@link #formatString format}</li>
 *             	<li>{@link #isString classify}</li>
 *             	<li>{@link #parseString parse}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <b>{@link Object Else}</b>
 *         <ul>
 *             <li>{@link #formatElse format}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <b>{@link Void Null}</b>
 *         <ul>
 *         		<li>{@link #formatNull foramt}</li>
 *         		<li>{@link #isNull classify}</li>
 *         		<li>{@link #parseNull parse}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <b>{@link Empty}</b>
 *         <ul>
 *             <li>{@link  #isEmpty classify}</li>
 *         </ul>
 *     </li>
 * </ul>
 *
 * @author lsafer
 * @version 0.1.5
 * @see <a href="https://www.json.org/">json.org</a>
 * @since 09-Jul-2019
 */
public class JSON extends AbstractFormat {
	/**
	 * The global instance to avoid unnecessary instancing.
	 */
	@Where
	final public static JSON global = new JSON().setDefaults(new Syntax().setDefaults());
	/**
	 * A list of strings to be skipped when seen in a literal fence.
	 */
	final protected List<String> ESCAPABLE = new ArrayList<>();
	/**
	 * The literal symbols relationships for the syntax tracker.
	 */
	final protected Map<String, String> LITERAL = new HashMap<>();
	/**
	 * The nestable symbols relationships for the syntax tracker.
	 */
	final protected Map<String, String> NESTABLE = new HashMap<>();
	/**
	 * The number of characters expected for values.
	 */
	protected int BUFFER_SIZE;
	/**
	 * The number of whitespaces characters expected to be read continuously.
	 * <p>
	 * Note: larger number will effect the RAM. Lower number will effect the performance
	 */
	protected int MARK_LENGTH;
	/**
	 * The symbols of this.
	 */
	protected Syntax SYNTAX;

	@Override
	protected boolean formatPre(FormatToken token) throws IOException {
		//RECURSE DETECTION
		for (FormatToken parent = token.parent; parent != null; parent = parent.parent)
			if (parent.input == token.input)
				throw new FormatException("Recurse detected!");

		return true;
	}

	/**
	 * Format the given {@link Collection Array}. To a {@link JSON} text. Then {@link Writer#append} it to the given {@link Writer}.
	 *
	 * @param token the formatting instance that holds the variables of this formatting
	 * @throws FormatException      when any formatting errors occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' or 'token.input' is null
	 */
	@FormatMethod(@Filter(
			includeAll = {Collection.class,
						  Object[].class,
			},
			include = {boolean[].class,
					   byte[].class,
					   char[].class,
					   double[].class,
					   float[].class,
					   int[].class,
					   long[].class,
					   short[].class
			}))
	protected void formatArray(FormatToken token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		Iterator it = token.input instanceof Collection ? ((Collection) token.input).iterator() : Arrayz.asList0(token.input).iterator();

		String TAB = Stringz.repeat(SYNTAX.WS_TAB, token.depth);
		String SHIFT = TAB + SYNTAX.WS_TAB;

		if (it.hasNext()) {
			token.output.append(SYNTAX.FENCE_ARRAY[0])
					.append(SYNTAX.WS_LN)
					.append(SHIFT);

			while (true) {
				Object element = it.next();

				this.format(token.subToken(element, token.output, Clazz.ofi(element), 0));

				if (it.hasNext()) {
					token.output.append(SYNTAX.OPERATOR_SEPARATOR[0])
							.append(SYNTAX.WS_LN)
							.append(SHIFT);
				} else {
					token.output.append(SYNTAX.WS_LN)
							.append(TAB)
							.append(SYNTAX.FENCE_ARRAY[1]);
					break;
				}
			}
		} else {
			token.output.append(SYNTAX.FENCE_ARRAY[0])
					.append(SYNTAX.WS_LN)
					.append(TAB)
					.append(SYNTAX.FENCE_ARRAY[1]);
		}
	}

	/**
	 * Format the given {@link Boolean}. To a {@link JSON} text. Then {@link Writer#append} it to the given {@link Writer}.
	 *
	 * @param token the formatting instance that holds the variables of this formatting
	 * @throws FormatException      when any formatting errors occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' or 'token.input' is null
	 */
	@FormatMethod(@Filter({Boolean.class, boolean.class}))
	protected void formatBoolean(FormatToken<Boolean> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		token.output.append(SYNTAX.VALUE_BOOLEAN[token.input ? 0 : 1]);
	}

	/**
	 * Append null to the given writer.
	 *
	 * @param token the formatting instance that holds the variables of this formatting
	 * @throws FormatException      when any formatting errors occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' is null
	 */
	@FormatMethod(@Filter(Void.class))
	protected void formatNull(FormatToken<Void> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		token.output.append(SYNTAX.VALUE_NULL[0]);
	}

	/**
	 * Format the given {@link Number}. To a {@link JSON} text. Then {@link Writer#append} it to the given {@link Writer}.
	 *
	 * @param token the formatting instance that holds the variables of this formatting
	 * @throws FormatException      when any formatting errors occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' or 'token.input' is null
	 */
	@FormatMethod(@Filter(
			includeAll = Number.class,
			include = {
					byte.class,
					double.class,
					float.class,
					int.class,
					long.class,
					short.class
			}))
	protected void formatNumber(FormatToken<Number> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		Class family = token.klazz.getFamily();

		if (family == Byte.class || family == byte.class)
			token.output.append(Byte.toString(token.input.byteValue()));
		else if (family == Double.class || family == double.class)
			token.output.append(Double.toString(token.input.doubleValue()));
		else if (family == Float.class || family == float.class)
			token.output.append(Float.toString(token.input.floatValue()));
		else if (family == Long.class || family == long.class)
			token.output.append(Long.toString(token.input.longValue()));
		else if (family == Short.class || family == short.class)
			token.output.append(Short.toString(token.input.shortValue()));
		else if (family == Integer.class || family == int.class)
			token.output.append(Integer.toString(token.input.intValue()));
		else token.output.append(token.input.toString());
	}

	/**
	 * Format the given {@link Map Object}. To a {@link JSON} text. Then {@link Writer#append} it to the given {@link Writer}.
	 *
	 * @param token the formatting instance that holds the variables of this formatting
	 * @throws FormatException      when any formatting errors occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' or 'token.input' is null
	 */
	@FormatMethod(@Filter(includeAll = Map.class))
	protected void formatObject(FormatToken<Map> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		Iterator<Map.Entry> it = token.input.entrySet().iterator();

		String TAB = Stringz.repeat(SYNTAX.WS_TAB, token.depth);
		String SHIFT = TAB + SYNTAX.WS_TAB;

		if (it.hasNext()) {
			token.output.append(SYNTAX.FENCE_OBJECT[0])
					.append(SYNTAX.WS_LN)
					.append(SHIFT);

			while (true) {
				Map.Entry<?, ?> entry = it.next();

				Object key = entry.getKey();
				Object value = entry.getValue();

				this.format(token.subToken(key, token.output, Clazz.ofi(key), 0));

				token.output.append(SYNTAX.OPERATOR_DECLARATION[0]);

				this.format(token.subToken(value, token.output, Clazz.ofi(value), 1));

				if (it.hasNext()) {
					token.output.append(SYNTAX.OPERATOR_SEPARATOR[0])
							.append(SYNTAX.WS_LN)
							.append(SHIFT);
				} else {
					token.output.append(SYNTAX.WS_LN)
							.append(TAB)
							.append(SYNTAX.FENCE_OBJECT[1]);
					break;
				}
			}
		} else {
			token.output.append(SYNTAX.FENCE_OBJECT[0])
					.append(SYNTAX.WS_LN)
					.append(TAB)
					.append(SYNTAX.FENCE_OBJECT[1]);
		}
	}

	/**
	 * Format the given {@link CharSequence String}. To a {@link JSON} text. Then {@link Writer#append} it to the given {@link Writer}.
	 *
	 * @param token the formatting instance that holds the variables of this formatting
	 * @throws FormatException      when any formatting errors occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' or 'token.input' is null
	 */
	@FormatMethod(@Filter(includeAll = CharSequence.class))
	protected void formatString(FormatToken<CharSequence> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		String value = token.input.toString();

		for (Map.Entry<String, String> escapable : SYNTAX.ESCAPABLES.entrySet())
			value = value.replace(escapable.getKey(), escapable.getValue());

		token.output.append(SYNTAX.FENCE_STRING[0])
				.append(value)
				.append(SYNTAX.FENCE_STRING[1]);
	}

	/**
	 * Check if the given string should be parsed as {@link Collection Array} or not.
	 *
	 * @param token the classification instance that holds the variables of this classification
	 * @return whether the given string should be parsed as {@code array} or not.
	 * @throws ClassifyException    when any classification exception occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' is null
	 */
	@ClassifyMethod
	protected boolean isArray(ClassifyToken<Collection> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		token.input.mark(MARK_LENGTH);

		int r = Readerz.isRemainingEquals(token.input, true, false, false, SYNTAX.FENCE_ARRAY[0]);

		token.input.reset();

		if (r == -1) {
			return false;
		} else {
			token.output = Clazz.of(Collection.class);
			return true;
		}
	}

	/**
	 * Check if the given string should be parsed as {@link Boolean} or not.
	 *
	 * @param token the classification instance that holds the variables of this classification
	 * @return whether the given string should be parsed as {@code boolean} or not.
	 * @throws ClassifyException    when any classification exception occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' is null
	 */
	@ClassifyMethod
	protected boolean isBoolean(ClassifyToken<Boolean> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		token.input.mark(MARK_LENGTH);

		int r = Readerz.isRemainingEquals(token.input, true, true, true, SYNTAX.VALUE_BOOLEAN);

		token.input.reset();

		if (r == -1) {
			return false;
		} else {
			token.output = Clazz.of(Boolean.class);
			return true;
		}
	}

	/**
	 * Check if no value the reader contains.
	 *
	 * @param token the classification instance that holds the variables of this classification
	 * @return whether the given string is no value
	 * @throws ClassifyException    when any classification exception occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' is null
	 */
	@ClassifyMethod
	protected boolean isEmpty(ClassifyToken<Empty> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		token.input.mark(MARK_LENGTH);

		int i;

		while ((i = token.input.read()) != -1 && Character.isWhitespace(i)) ;

		token.input.reset();

		if (i == -1) {
			token.output = Clazz.of(Empty.class);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check if the remaining character on the given reader should be parsed as null or not.
	 *
	 * @param token the classification instance that holds the variables of this classification
	 * @return if the remaining characters on the given reader should be parsed as null or not.
	 * @throws ClassifyException    when any classification exception occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' is null
	 */
	@ClassifyMethod
	protected boolean isNull(ClassifyToken<Void> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		token.input.mark(MARK_LENGTH);

		int r = Readerz.isRemainingEquals(token.input, true, true, true, SYNTAX.VALUE_NULL);

		token.input.reset();

		if (r == -1) {
			return false;
		} else {
			token.output = Clazz.of(Void.class);
			return true;
		}
	}

	/**
	 * Check if the given string should be parsed as {@link Number} or not.
	 *
	 * @param token the classification instance that holds the variables of this classification
	 * @return whether the given string should be parsed as {@code number} or not.
	 * @throws ClassifyException    when any classification exception occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' is null
	 */
	@ClassifyMethod
	protected boolean isNumber(ClassifyToken<Number> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		token.input.mark(MARK_LENGTH);

		int r = Readerz.isRemainingEquals(token.input, true, false, false, SYNTAX.VALUE_NUMBER);

		token.input.reset();

		if (r == -1) {
			return false;
		} else {
			token.output = Clazz.of(Number.class);
			return true;
		}
	}

	/**
	 * Check if the given string should be parsed as {@link Map Object} or not.
	 *
	 * @param token the classification instance that holds the variables of this classification
	 * @return whether the given string should be parsed as {@code object} or not.
	 * @throws ClassifyException    when any classification exception occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' is null
	 */
	@ClassifyMethod
	protected boolean isObject(ClassifyToken<Map> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		token.input.mark(MARK_LENGTH);

		int r = Readerz.isRemainingEquals(token.input, true, false, false, SYNTAX.FENCE_OBJECT[0]);

		token.input.reset();

		if (r == -1) {
			return false;
		} else {
			token.output = Clazz.of(Map.class);
			return true;
		}
	}

	/**
	 * Check if the given string should be parsed as {@link CharSequence String} or not.
	 *
	 * @param token the classification instance that holds the variables of this classification
	 * @return whether the given string should be parsed as {@code string} or not.
	 * @throws ClassifyException    when any classification exception occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' is null
	 */
	@ClassifyMethod
	protected boolean isString(ClassifyToken<String> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		token.input.mark(MARK_LENGTH);

		int r = Readerz.isRemainingEquals(token.input, true, false, false, SYNTAX.FENCE_STRING[0]);

		token.input.reset();

		if (r == -1) {
			return false;
		} else {
			token.output = Clazz.of(CharSequence.class, String.class);
			return true;
		}
	}

	/**
	 * Parse the string from the given reader to an {@link Collection Array}. Then set it to the given {@link AtomicReference buffer}.
	 *
	 * @param token the parsing instance that holds the variables of this parsing
	 * @throws ParseException               when any parsing exception occurs
	 * @throws IOException                  when any I/O exception occurs
	 * @throws NullPointerException         if the given 'token' is null
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct from the clazz given
	 */
	@ParseMethod(@Filter(
			includeAll = {
					Collection.class,
					Object[].class
			},
			include = {boolean[].class,
					   byte[].class,
					   char[].class,
					   double[].class,
					   float[].class,
					   int[].class,
					   long[].class,
					   short[].class
			}))
	protected void parseArray(ParseToken token) throws IOException, ReflectiveOperationException {
		//sorry about the type mess, I am trying to dynamically deal with
		//three types (Collection, List, and Object[] or primitive[])
		//meanwhile listening to what the user wants
		//trying to apply any generics to the token might cause ClassCastException on the runtime
		//because the compiler might cast `token.output` to the generic applied
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		//If the requested type is array
		Object origin = token.output;

		//setup the output
		if (token.klazz.isArray())
			token.output = token.output.getClass().isArray() ? new ArrayList(Arrayz.asList(origin)) : new ArrayList();
		else if (!token.klazz.isInstance(token.output))
			token.output = token.klazz.isAssignableFrom(ArrayList.class) ? new ArrayList() :
						   token.klazz.getConstructor().newInstance();
		else if (!(token.output instanceof List))
			((Collection) token.output).clear();

		//reject more elements
		boolean closed = false;
		//comment mode
		boolean comment = false;
		//syntax manager
		SyntaxTracker tracker = new SyntaxTracker(NESTABLE, LITERAL, ESCAPABLE);
		//content reading buffer (for members)
		StringBuilder builder = new StringBuilder(BUFFER_SIZE);
		//short backtrace
		StringBuilder backtrace = new StringBuilder(BUFFER_SIZE);

		//last overwritten index
		int index = 0;
		//overwrite an existing element at the current index
		boolean overwrite = ((Collection) token.output).size() > index;

		//first run
		if (Readerz.isRemainingEquals(token.input, true, false, false, SYNTAX.FENCE_ARRAY[0]) != 0)
			throw new ParseException("array not started");

		for (int i; (i = token.input.read()) != -1; ) {
			char point = (char) i;

			if (closed)
				//only white spaces are allowed when closed
				if (Character.isWhitespace(i))
					continue;
				else throw new ParseException("array closed before text end");
			if (tracker.depth() == 0) {
				//the short past string
				String past = backtrace.append(point).toString();

				if ((closed = past.endsWith(SYNTAX.FENCE_ARRAY[1])) || Stringz.endsWith(past, SYNTAX.OPERATOR_SEPARATOR) != null) {
					//element chunk reader
					Reader elementReader = new StringReader(builder.toString());

					//classifying
					Clazz elementClazz = this.classify(new ClassifyToken(elementReader, null));

					//if the last element spot is empty. Then don't parse.
					if (closed && elementClazz.getFamily() == Empty.class)
						continue;

					//existing member
					Object element = overwrite ? ((List) token.output).get(index) : null;

					//parsing the member
					element = this.parse(token.subToken(elementReader, element, elementClazz, 0));

					//register results
					if (overwrite) {
						//replace the existing member with the new value
						((List) token.output).set(index, element);
						//update the overwrite position
						overwrite = ((Collection) token.output).size() > (++index);
					} else {
						//direct add
						((Collection) token.output).add(element);
					}

					//new builders
					builder = new StringBuilder(BUFFER_SIZE);
					backtrace = new StringBuilder(BUFFER_SIZE);
					continue;
				}
			} else if (backtrace.length() != 0) {
				//reset short backtrace
				backtrace = new StringBuilder(BUFFER_SIZE);
			}

			//notify syntax manager
			tracker.append(point);

			if (this.SYNTAX.FENCE_COMMENT.containsValue(tracker.fenceStart())) {
				//currently in comment mode
				if (!comment) {
					//delete the comment open symbol
					builder.delete(builder.length() - tracker.fenceEnd().length() + 1, builder.length());
					comment = true;
				}
			} else if (comment) {
				//comment mode is over (ignore the comment close symbol)
				comment = false;
			} else {
				//append the reading content buffer
				builder.append(point);
			}
		}

		if (!closed)
			throw new ParseException("Collection not closed");

		//delete unreached indexes, if it's a list and didn't reach it's limit
		if (overwrite)
			((List) token.output).subList(index, ((List) token.output).size()).clear();

		//convert to array
		if (token.klazz.isArray()) {
			if (origin == null || ((List) token.output).size() != Array.getLength(origin))
				//construct new
				origin = Array.newInstance(token.klazz.getComponentType(), ((List) token.output).size());
			//primitive arrays have to be treated in another way
			if (origin instanceof Object[]) {
				((List) token.output).toArray((Object[]) origin);
			} else {
				Object[] output = ((List) token.output).toArray();
				Arrayz.hardcopy(output, 0, origin, 0, output.length);
			}

			token.output = origin;
		}
	}

	/**
	 * Parse the string from the given reader to an {@link Boolean}. Then set it to the given {@link AtomicReference buffer}.
	 *
	 * @param token the parsing instance that holds the variables of this parsing
	 * @throws ParseException       when any parsing exception occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' is null
	 */
	@ParseMethod(@Filter({Boolean.class, boolean.class}))
	protected void parseBoolean(ParseToken<Boolean> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		String string = Readerz.getRemaining(token.input, this.BUFFER_SIZE, this.BUFFER_SIZE).trim();

		if (SYNTAX.VALUE_BOOLEAN[0].equals(string)) {
			token.output = true;
		} else if (SYNTAX.VALUE_BOOLEAN[1].equals(string)) {
			token.output = false;
		} else {
			throw new ParseException("Can't parse \"" + string + "\" as boolean");
		}
	}

	/**
	 * Set null to the given buffer.
	 *
	 * @param token the parsing instance that holds the variables of this parsing
	 * @throws ParseException       when any parsing exception occurs
	 * @throws NullPointerException if the given 'token' is null
	 * @throws IOException          if any I/O exception occurred
	 */
	@ParseMethod(@Filter(Void.class))
	protected void parseNull(ParseToken<Void> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		String string = Readerz.getRemaining(token.input, this.BUFFER_SIZE, BUFFER_SIZE).trim();

		if (Stringz.any(string, SYNTAX.VALUE_NULL) != null) {
			token.output = null;
		} else {
			throw new ParseException("can't parse " + string + " as null");
		}
	}

	/**
	 * Parse the string from the given reader to an {@link Number}. Then set it to the given {@link AtomicReference buffer}.
	 *
	 * @param token the parsing instance that holds the variables of this parsing
	 * @throws ParseException               when any parsing exception occurs
	 * @throws NullPointerException         if the given 'token' is null
	 * @throws IOException                  when any I/O exception occurs
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct the number
	 */
	@ParseMethod(@Filter(
			includeAll = Number.class,
			include = {
					byte.class,
					double.class,
					float.class,
					int.class,
					long.class,
					short.class
			}))
	protected void parseNumber(ParseToken<Number> token) throws IOException, ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		String string = Readerz.getRemaining(token.input, BUFFER_SIZE, BUFFER_SIZE).trim();

		if (token.klazz.isAssignableFrom(BigDecimal.class)) {
			token.output = new BigDecimal(string);
		} else {
			//make sure not using a primitive class on a reflective operation
			Class klass = token.klazz.toObjectClazz().getKlass();

			try {
				token.output = (Number) klass.getConstructor(String.class).newInstance(string);
			} catch (NoSuchMethodException | SecurityException e) {
				token.output = (Number) klass.getMethod("valueOf", String.class).invoke(null, string);
			}
		}
	}

	/**
	 * Parse the string from the given reader to an {@link Map Object}. Then set it to the given {@link AtomicReference buffer}.
	 *
	 * @param token the parsing instance that holds the variables of this parsing
	 * @throws ParseException               when any parsing exception occurs
	 * @throws IOException                  when any I/O exception occurs
	 * @throws NullPointerException         if the given 'token' is null
	 * @throws ReflectiveOperationException if any exception occurs while trying to construct the map
	 */
	@ParseMethod(@Filter(includeAll = Map.class))
	protected void parseObject(ParseToken<Map> token) throws IOException, ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		//setup the output
		if (!token.klazz.isInstance(token.output))
			token.output = token.klazz.isAssignableFrom(HashMap.class) ? new HashMap() :
						   token.klazz.getConstructor().newInstance();

		//reject more elements
		boolean closed = false;
		//comment mode
		boolean comment = false;
		//syntax manager
		SyntaxTracker tracker = new SyntaxTracker(NESTABLE, LITERAL, ESCAPABLE);
		//content reading buffer (for key and value)
		StringBuilder builder = new StringBuilder(BUFFER_SIZE);
		//short backtrace
		StringBuilder backtrace = new StringBuilder(BUFFER_SIZE);

		//the keys in the input
		Set keys = new HashSet();
		//key holder
		StringBuilder keyBuilder = null;

		//first read
		if (Readerz.isRemainingEquals(token.input, true, false, false, SYNTAX.FENCE_OBJECT[0]) != 0)
			throw new ParseException("Object not started");

		for (int i; (i = token.input.read()) != -1; ) {
			//reading character buffer
			char point = (char) i;

			if (closed)
				//only white spaces are allowed when closed
				if (Character.isWhitespace(i))
					continue;
				else throw new ParseException("Object closed before text end");
			if (tracker.depth() == 0) {
				//the short past string
				String past = backtrace.append(point).toString();

				if (Stringz.endsWith(past, SYNTAX.OPERATOR_DECLARATION) != null) {
					//key reading mode is over| value reading mode
					if (keyBuilder != null)
						throw new ParseException("Two equation symbol");

					//switch reading destination address
					keyBuilder = builder;

					builder = new StringBuilder(BUFFER_SIZE);
					backtrace = new StringBuilder(BUFFER_SIZE);
					continue;
				} else if ((closed = past.endsWith(SYNTAX.FENCE_OBJECT[1])) || Stringz.endsWith(past, SYNTAX.OPERATOR_SEPARATOR) != null) {
					//if the last element spot is empty. Then don't parse.
					if (closed && keyBuilder == null)
						continue;

					//readers
					Reader keyReader = new StringReader(keyBuilder.toString().trim());
					Reader valueReader = new StringReader(builder.toString().trim());

					//classifying
					Clazz keyClazz = this.classify(new ClassifyToken<>(keyReader, null));
					Clazz valueClazz = this.classify(new ClassifyToken(valueReader, null));

					//parsing the key
					Object key = this.parse(token.subToken(keyReader, null, keyClazz, 0));

					//duplicated key check
					if (keys.contains(key))
						throw new ParseException("duplicated key: " + key);

					//existing value
					Object value = token.output.get(key);

					//parsing the value
					value = this.parse(token.subToken(valueReader, value, valueClazz, 1));

					//register results
					keys.add(key);
					token.output.put(key, value);

					//new builders
					keyBuilder = null;
					builder = new StringBuilder(BUFFER_SIZE);
					backtrace = new StringBuilder(BUFFER_SIZE);
					continue;
				}
			} else if (backtrace.length() != 0) {
				//reset short backtrace
				backtrace = new StringBuilder(BUFFER_SIZE);
			}

			//notify syntax manager
			tracker.append(point);

			if (this.SYNTAX.FENCE_COMMENT.containsValue(tracker.fenceEnd())) {
				//currently in comment mode
				if (!comment) {
					//delete the comment open symbol
					builder.delete(builder.length() - tracker.fenceStart().length() + 1, builder.length());
					comment = true;
				}
			} else if (comment) {
				//comment mode is over (ignore the comment close symbol)
				comment = false;
			} else {
				//append the reading content buffer
				builder.append(point);
			}
		}

		if (!closed)
			throw new ParseException("Map not closed");

		//remove missing keys!
		token.output.keySet().retainAll(keys);
	}

	/**
	 * Parse the string from the given reader to an {@link String}. Then set it to the given {@link AtomicReference buffer}.
	 *
	 * @param token the parsing instance that holds the variables of this parsing
	 * @throws ParseException               when any parsing exception occurs
	 * @throws IOException                  when any I/O exception occurs
	 * @throws NullPointerException         if the given 'token' is null
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct the char-sequence
	 */
	@ParseMethod(@Filter(includeAll = CharSequence.class))
	protected void parseString(ParseToken<CharSequence> token) throws IOException, ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		String string = Readerz.getRemaining(token.input, BUFFER_SIZE, BUFFER_SIZE).trim();
		String value = string.substring(SYNTAX.FENCE_STRING[0].length(), string.length() - SYNTAX.FENCE_STRING[1].length());

		for (Map.Entry<String, String> escapable : SYNTAX.ESCAPABLES.entrySet())
			value = value.replace(escapable.getValue(), escapable.getKey());

		Class klass = token.klazz.getKlass();
		if (klass.isAssignableFrom(String.class)) {
			token.output = value;
		} else try {
			token.output = (CharSequence) klass.getMethod("valueOf", String.class).invoke(null, value);
		} catch (NoSuchMethodException | SecurityException e) {
			token.output = (CharSequence) klass.getConstructor(String.class).newInstance(value);
		}
	}

	/**
	 * Set the default values of JSON for this json format.
	 *
	 * @param syntax the syntax to be set
	 * @return this
	 */
	protected JSON setDefaults(Syntax syntax) {
		SYNTAX = syntax;
		BUFFER_SIZE = 20;
		MARK_LENGTH = 20;

		NESTABLE.put(SYNTAX.FENCE_OBJECT[0], SYNTAX.FENCE_OBJECT[1]);
		NESTABLE.put(SYNTAX.FENCE_ARRAY[0], SYNTAX.FENCE_ARRAY[1]);

		LITERAL.putAll(SYNTAX.FENCE_COMMENT);
		LITERAL.put(SYNTAX.FENCE_STRING[0], SYNTAX.FENCE_STRING[1]);

		ESCAPABLE.addAll(SYNTAX.ESCAPABLES.values());

		return this;
	}

	/**
	 * A structure holding the symbols of a JSON formatter.
	 */
	public static class Syntax {
		/**
		 * The relationships between strings and theirs escapes.
		 * <pre>
		 *     key = string
		 *     value = text
		 * </pre>
		 */
		public Map<String, String> ESCAPABLES = new HashMap<>();
		/**
		 * Array end char on JSON.
		 * <pre>
		 *     [0] = start
		 *     [1] = end
		 * </pre>
		 */
		public String[] FENCE_ARRAY;
		/**
		 * The comment symbols.
		 * <pre>
		 *     key = start
		 *     value = end
		 * </pre>
		 */
		public Map<String, String> FENCE_COMMENT = new HashMap<>();
		/**
		 * Object end char on JSON.
		 * <pre>
		 *     [0] = start
		 *     [1] = end
		 * </pre>
		 */
		public String[] FENCE_OBJECT;
		/**
		 * String start char on JSON.
		 * <pre>
		 *     [0] = start
		 *     [1] = end
		 * </pre>
		 */
		public String[] FENCE_STRING;

		/**
		 * Pair equation char on other JSON like formats.
		 * <pre>
		 *     [0] = primary
		 *     [&gt;0] = alternative
		 * </pre>
		 */
		public String[] OPERATOR_DECLARATION;
		/**
		 * Members separator char on JSON.
		 * <pre>
		 *     [0] = primary
		 *     [&gt;0] = alternative
		 * </pre>
		 */
		public String[] OPERATOR_SEPARATOR;

		/**
		 * The value false of the type boolean on JSON.
		 * <pre>
		 *     [0] = true
		 *     [1] = false
		 * </pre>
		 */
		public String[] VALUE_BOOLEAN;
		/**
		 * The value null on JSON.
		 * <pre>
		 *     [0] = primary
		 *     [&gt;0] = alternative
		 * </pre>
		 */
		public String[] VALUE_NULL;
		/**
		 * What json will identify as a part of a number.
		 */
		public String[] VALUE_NUMBER;

		/**
		 * A symbol used to shows a line. To make the code more readable.
		 */
		public String WS_LN = "\n";
		/**
		 * A symbol used to show a gap between characters. TO make the code more readable.
		 */
		public String WS_TAB = "\t";

		/**
		 * Set the defaults of this syntax.
		 *
		 * @return this
		 */
		public Syntax setDefaults() {
			ESCAPABLES.put("\\", "\\\\");
			ESCAPABLES.put("\"", "\\\"");
			ESCAPABLES.put("\n", "\\\n");
			ESCAPABLES.put("\r", "\\\r");
			ESCAPABLES.put("\t", "\\\t");

			FENCE_COMMENT.put("/*", "*/");
			FENCE_COMMENT.put("//", "\n");

			FENCE_ARRAY = new String[]{"[", "]"};
			FENCE_OBJECT = new String[]{"{", "}"};
			FENCE_STRING = new String[]{"\"", "\""};

			OPERATOR_DECLARATION = new String[]{":", "="};
			OPERATOR_SEPARATOR = new String[]{","};

			VALUE_BOOLEAN = new String[]{"true", "false"};
			VALUE_NULL = new String[]{"null"};
			VALUE_NUMBER = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-"};

			WS_LN = "\n";
			WS_TAB = "\t";

			return this;
		}
	}
}
