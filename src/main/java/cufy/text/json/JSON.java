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
import cufy.lang.Recurse;
import cufy.meta.Filter;
import cufy.meta.Where;
import cufy.text.*;
import cufy.util.Arrayu;
import cufy.util.Inputu;
import cufy.util.Reflectionu;
import cufy.util.Stringu;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A formatter/parser for JSON.
 *
 * <ul>
 *     <font color="orange" size="4"><b>Dynamic Methods:</b></font>
 *     <li>
 *         <font color="yellow">{@link Collection Array}</font>
 *         <ul>
 *             	<li>{@link #formatArray format}</li>
 *         		<li>{@link #isArray classify}</li>
 *         		<li>{@link #parseArray parse}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <font color="yellow">{@link Boolean}</font>
 *     		<ul>
 *     		 	<li>{@link #formatBoolean format}</li>
 *     			<li>{@link #isBoolean classify}</li>
 *     			<li>{@link #parseBoolean parse}</li>
 *     		</ul>
 *     </li>
 *     <li>
 *         <font color="yellow">{@link Number}</font>
 *         <ul>
 *             	<li>{@link #formatNumber format}</li>
 *             	<li>{@link #isNumber classify}</li>
 *             	<li>{@link #parseNumber parse}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <font color="yellow">{@link Map Object}</font>
 *         <ul>
 *            	<li>{@link #formatObject format}</li>
 *            	<li>{@link #isObject classify}</li>
 *            	<li>{@link #parseObject parse}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <font color="yellow">{@link CharSequence String}</font>
 *         <ul>
 *             	<li>{@link #formatString format}</li>
 *             	<li>{@link #isString classify}</li>
 *             	<li>{@link #parseString parse}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <font color="yellow">{@link Object Else}</font>
 *         <ul>
 *             <li>{@link #formatElse format}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <font color="yellow">{@link Void Null}</font>
 *         <ul>
 *         		<li>{@link #formatNull foramt}</li>
 *         		<li>{@link #isNull classify}</li>
 *         		<li>{@link #parseNull parse}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <font color="yellow">{@link Empty}</font>
 *         <ul>
 *             <li>{@link  #isEmpty classify}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <font color="yellow">{@link Recurse}</font>
 *         <ul>
 *             <li>{@link #formatRecurse format}</li>
 *             <li>{@link #isRecurse classify}</li>
 *             <li>{@link #parseRecurse parse}</li>
 *         </ul>
 *     </li>
 * </ul>
 *
 * @author lsafer
 * @version 0.1.3
 * @see <a href="http://www.json.org/">json.org</a> for more about JSON
 * @since 09-Jul-2019
 */
public class JSON extends AbstractFormat {
	/**
	 * The global instance to avoid unnecessary instancing.
	 */
	@Where
	final public static JSON global = new JSON().setDefaults();

	/**
	 * The expected number of members on objects or arrays.
	 */
	protected int DEFAULT_MEMBERS_COUNT;
	/**
	 * The expected depth for nested arrays and object.
	 */
	protected int DEFAULT_NESTING_DEPTH;
	/**
	 * The number of characters expected for values.
	 */
	protected int DEFAULT_VALUE_LENGTH;
	/**
	 * The number of whitespaces characters expected to be read continuously.
	 *
	 * @implNote larger number will effect the RAM. Lower number will effect the performance
	 */
	protected int DEFAULT_WHITE_SPACE_LENGTH;
	/**
	 * The relationships between strings and theirs escapes.
	 */
	protected Map<String, String> STRING_ESCAPABLES;
	/**
	 * The symbols of this.
	 */
	protected Syntax SYNTAX;
	/**
	 * The comment symbols.
	 *
	 * @apiNote final after initialization
	 */
	protected Map<String, String> SYNTAX_COMMENT;
	/**
	 * The literal symbols relationships for the syntax tracker.
	 *
	 * @apiNote final after initialization
	 */
	protected Map<String, String> SYNTAX_LITERAL;
	/**
	 * The nestable symbols relationships for the syntax tracker.
	 *
	 * @apiNote final after initialization
	 */
	protected Map<String, String> SYNTAX_NESTABLE;

	/**
	 * Format Array
	 * <br/>
	 * Format the given {@link Collection Array}. To a {@link JSON} text. Then {@link Writer#append} it to the given {@link Writer}.
	 *
	 * @param token the formatting instance that holds the variables of this formatting
	 * @throws FormatException      when any formatting errors occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' or 'token.input' is null
	 */
	@FormatMethod(@Filter(
			subIn = {Collection.class,
					 Object[].class,
			},
			in = {boolean[].class,
				  byte[].class,
				  char[].class,
				  double[].class,
				  float[].class,
				  int[].class,
				  long[].class,
				  short[].class
			}))
	protected void formatArray(FormatToken<Collection> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		Iterator it = token.input instanceof Collection ? token.input.iterator() : Arrayu.asList(token.input).iterator();

		String TAB = Stringu.repeat(SYNTAX.TAB, token.depth);
		String SHIFT = TAB + SYNTAX.TAB;

		if (it.hasNext()) {
			token.output.append(SYNTAX.ARRAY_START)
					.append(SYNTAX.LINE)
					.append(SHIFT);

			while (true) {
				Object element = it.next();

				this.format(token.subToken(element, token.output, Clazz.ofi(element), 0));

				if (it.hasNext()) {
					token.output.append(SYNTAX.MEMBER_END)
							.append(SYNTAX.LINE)
							.append(SHIFT);
				} else {
					token.output.append(SYNTAX.LINE)
							.append(TAB)
							.append(SYNTAX.ARRAY_END);
					break;
				}
			}
		} else {
			token.output.append(SYNTAX.ARRAY_START)
					.append(SYNTAX.LINE)
					.append(TAB)
					.append(SYNTAX.ARRAY_END);
		}
	}

	/**
	 * Format Boolean
	 * <br/>
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

		token.output.append(token.input ? SYNTAX.TRUE : SYNTAX.FALSE);
	}

	/**
	 * Format Null
	 * <br/>
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

		token.output.append(SYNTAX.NULL);
	}

	/**
	 * Format Number
	 * <br/>
	 * Format the given {@link Number}. To a {@link JSON} text. Then {@link Writer#append} it to the given {@link Writer}.
	 *
	 * @param token the formatting instance that holds the variables of this formatting
	 * @throws FormatException      when any formatting errors occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' or 'token.input' is null
	 */
	@FormatMethod(@Filter(
			subIn = Number.class,
			in = {
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
			token.output.append(Byte.toString(token.input.byteValue())).append("B");
		else if (family == Double.class || family == double.class)
			token.output.append(Double.toString(token.input.doubleValue())).append("D");
		else if (family == Float.class || family == float.class)
			token.output.append(Float.toString(token.input.floatValue())).append("F");
		else if (family == Long.class || family == long.class)
			token.output.append(Long.toString(token.input.longValue())).append("L");
		else if (family == Short.class || family == short.class)
			token.output.append(Short.toString(token.input.shortValue())).append("S");
		else token.output.append(Integer.toString(token.input.intValue()));
	}

	/**
	 * Format Object
	 * <br/>
	 * Format the given {@link Map Object}. To a {@link JSON} text. Then {@link Writer#append} it to the given {@link Writer}.
	 *
	 * @param token the formatting instance that holds the variables of this formatting
	 * @throws FormatException      when any formatting errors occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' or 'token.input' is null
	 */
	@FormatMethod(@Filter(subIn = Map.class))
	protected void formatObject(FormatToken<Map> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		Iterator<Map.Entry> it = token.input.entrySet().iterator();

		String TAB = Stringu.repeat(SYNTAX.TAB, token.depth);
		String SHIFT = TAB + SYNTAX.TAB;

		if (it.hasNext()) {
			token.output.append(SYNTAX.OBJECT_START)
					.append(SYNTAX.LINE)
					.append(SHIFT);

			while (true) {
				Map.Entry<?, ?> entry = it.next();

				Object key = entry.getKey();
				Object value = entry.getValue();

				this.format(token.subToken(key, token.output, Clazz.ofi(key), 0));

				token.output.append(SYNTAX.DECLARATION);

				this.format(token.subToken(value, token.output, Clazz.ofi(value), 1));

				if (it.hasNext()) {
					token.output.append(SYNTAX.MEMBER_END)
							.append(SYNTAX.LINE)
							.append(SHIFT);
				} else {
					token.output.append(SYNTAX.LINE)
							.append(TAB)
							.append(SYNTAX.OBJECT_END);
					break;
				}
			}
		} else {
			token.output.append(SYNTAX.OBJECT_START)
					.append(SYNTAX.LINE)
					.append(TAB)
					.append(SYNTAX.OBJECT_END);
		}
	}

	/**
	 * Format Recurse
	 * <br/>
	 * Format the given {@link Recurse}. To a {@link JSON} text. Then {@link Writer#append} it to the given {@link Writer}.
	 *
	 * @param token the formatting instance that holds the variables of this formatting
	 * @throws FormatException          when any formatting errors occurs
	 * @throws IOException              when any I/O exception occurs
	 * @throws NullPointerException     if the given 'token' is null
	 * @throws IllegalArgumentException if the given 'input' didn't recurred
	 */
	@FormatMethod(@Filter(Recurse.class))
	protected void formatRecurse(FormatToken<Recurse> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		int i = 1;
		int index = -1;
		for (FormatToken grand = token.parent; grand != null; grand = grand.parent, i++)
			if (grand.input == token.input)
				index = i;

		if (index == -1) {
			throw new IllegalArgumentException(token + " didn't recurred");
		} else {
			token.output.append(SYNTAX.RECURSE).append(String.valueOf(index));
		}
	}

	/**
	 * Format String
	 * <br/>
	 * Format the given {@link CharSequence String}. To a {@link JSON} text. Then {@link Writer#append} it to the given {@link Writer}.
	 *
	 * @param token the formatting instance that holds the variables of this formatting
	 * @throws FormatException      when any formatting errors occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' or 'token.input' is null
	 */
	@FormatMethod(@Filter(subIn = CharSequence.class))
	protected void formatString(FormatToken<CharSequence> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
			Objects.requireNonNull(token.input, "token.input");
		}

		String value = token.input.toString();

		for (Map.Entry<String, String> escapable : STRING_ESCAPABLES.entrySet())
			value = value.replace(escapable.getKey(), escapable.getValue());

		token.output.append(SYNTAX.STRING_START)
				.append(value)
				.append(SYNTAX.STRING_END);
	}

	/**
	 * Classify Array
	 * <br/>
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

		token.input.mark(DEFAULT_WHITE_SPACE_LENGTH + SYNTAX.ARRAY_START.length());

		int r = Inputu.isRemainingEquals(token.input, true, false, false, SYNTAX.ARRAY_START);

		token.input.reset();

		if (r == -1) {
			return false;
		} else {
			token.output = Clazz.of(Collection.class);
			return true;
		}
	}

	/**
	 * Classify Boolean
	 * <br/>
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

		token.input.mark(DEFAULT_WHITE_SPACE_LENGTH + Math.max(SYNTAX.TRUE.length(), SYNTAX.FALSE.length()));

		int r = Inputu.isRemainingEquals(token.input, true, true, true, SYNTAX.TRUE, SYNTAX.FALSE);

		token.input.reset();

		if (r == -1) {
			return false;
		} else {
			token.output = Clazz.of(Boolean.class);
			return true;
		}
	}

	/**
	 * Classify Empty
	 * <br/>
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

		token.input.mark(DEFAULT_WHITE_SPACE_LENGTH);

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
	 * Classify Null
	 * <br/>
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

		token.input.mark(DEFAULT_WHITE_SPACE_LENGTH + SYNTAX.NULL.length());

		int r = Inputu.isRemainingEquals(token.input, true, true, true, SYNTAX.NULL);

		token.input.reset();

		if (r == -1) {
			return false;
		} else {
			token.output = Clazz.of(Void.class);
			return true;
		}
	}

	/**
	 * Classify Number
	 * <br/>
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

		token.input.mark(DEFAULT_WHITE_SPACE_LENGTH + 1);

		int r = Inputu.isRemainingEquals(token.input, true, false, false, "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

		token.input.reset();

		if (r == -1) {
			return false;
		} else {
			token.output = Clazz.of(Number.class);
			return true;
		}
	}

	/**
	 * Classify Object
	 * <br/>
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

		token.input.mark(DEFAULT_WHITE_SPACE_LENGTH + SYNTAX.OBJECT_START.length());

		int r = Inputu.isRemainingEquals(token.input, true, false, false, SYNTAX.OBJECT_START);

		token.input.reset();

		if (r == -1) {
			return false;
		} else {
			token.output = Clazz.of(Map.class);
			return true;
		}
	}

	/**
	 * Classify Recurse
	 * <br/>
	 * Check if the given string should be parsed as {@link Recurse} or not.
	 *
	 * @param token the classification instance that holds the variables of this classification
	 * @return whether the given string should be parsed as {@code recurse} or not.
	 * @throws ClassifyException    when any classification exception occurs
	 * @throws IOException          when any I/O exception occurs
	 * @throws NullPointerException if the given 'token' is null
	 */
	@ClassifyMethod
	protected boolean isRecurse(ClassifyToken<Recurse> token) throws IOException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		token.input.mark(DEFAULT_WHITE_SPACE_LENGTH + SYNTAX.RECURSE.length());

		int r = Inputu.isRemainingEquals(token.input, true, false, true, SYNTAX.RECURSE);

		token.input.reset();

		if (r == -1) {
			return false;
		} else {
			token.output = (Clazz) Clazz.of(Recurse.class, Object.class);
			return true;
		}
	}

	/**
	 * Classify String
	 * <br/>
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

		token.input.mark(DEFAULT_WHITE_SPACE_LENGTH + SYNTAX.STRING_START.length());

		int r = Inputu.isRemainingEquals(token.input, true, false, false, SYNTAX.STRING_START);

		token.input.reset();

		if (r == -1) {
			return false;
		} else {
			token.output = Clazz.of(CharSequence.class, String.class);
			return true;
		}
	}

	/**
	 * Parse Array
	 * <br/>
	 * Parse the string from the given reader to an {@link Collection Array}. Then set it to the given {@link AtomicReference buffer}.
	 *
	 * @param token the parsing instance that holds the variables of this parsing
	 * @throws ParseException               when any parsing exception occurs
	 * @throws IOException                  when any I/O exception occurs
	 * @throws NullPointerException         if the given 'token' is null
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct from the clazz given
	 */
	@ParseMethod(@Filter(
			subIn = {
					Collection.class,
					Object[].class
			},
			in = {boolean[].class,
				  byte[].class,
				  char[].class,
				  double[].class,
				  float[].class,
				  int[].class,
				  long[].class,
				  short[].class
			}))
	protected void parseArray(ParseToken<Collection> token) throws IOException, ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		//If the requested type is array
		Object array = token.output;

		//setup the output
		if (token.klazz.isArray())
			token.output = token.klazz.isInstance(token.output) ? new ArrayList(Arrayu.asList(array)) : new ArrayList();
		else if (!token.klazz.isInstance(token.output))
			token.output = token.klazz.isAssignableFrom(List.class) ? new ArrayList() : token.klazz.getKlass().getConstructor().newInstance();
		else if (!(token.output instanceof List))
			token.output.clear();

		//reject more elements
		boolean closed = false;
		//comment mode
		boolean comment = false;
		//syntax manager
		SyntaxTracker tracker = new SyntaxTracker(SYNTAX_NESTABLE, SYNTAX_LITERAL);
		//content reading buffer (for members)
		StringBuilder builder = new StringBuilder(DEFAULT_VALUE_LENGTH);
		//short backtrace
		StringBuilder backtrace = new StringBuilder(DEFAULT_VALUE_LENGTH);

		//last overwritten index
		int index = 0;
		//overwrite an existing element at the current index
		boolean overwrite = token.output.size() > index;

		//first run
		if (Inputu.isRemainingEquals(token.input, true, false, false, SYNTAX.ARRAY_START) != 0)
			throw new ParseException("array not started");

		for (int i; (i = token.input.read()) != -1; ) {
			char point = (char) i;

			if (closed)
				//only white spaces are allowed when closed
				if (Character.isWhitespace(i))
					continue;
				else throw new ParseException("array closed before text end");
			if (tracker.length() == 0) {
				//the short past string
				String past = backtrace.append(point).toString();

				if ((closed = past.endsWith(SYNTAX.ARRAY_END)) || past.endsWith(SYNTAX.MEMBER_END)) {
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
						overwrite = token.output.size() > (++index);
					} else {
						//direct add
						token.output.add(element);
					}

					//new builders
					builder = new StringBuilder(DEFAULT_VALUE_LENGTH);
					backtrace = new StringBuilder(DEFAULT_VALUE_LENGTH);
					continue;
				}
			} else if (backtrace.length() != 0) {
				//reset short backtrace
				backtrace = new StringBuilder(DEFAULT_VALUE_LENGTH);
			}

			//notify syntax manager
			tracker.append(point);

			if (this.SYNTAX_COMMENT.containsValue(tracker.getUnwrap())) {
				//currently in comment mode
				if (!comment) {
					//delete the comment open symbol
					builder.delete(builder.length() - 1, builder.length());
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
			((List) token.output).subList(index, token.output.size()).clear();

		//convert to array
		if (token.klazz.isArray()) {
			if (array == null || token.output.size() != Array.getLength(array))
				//construct new
				array = Array.newInstance(token.klazz.getComponentType(), token.output.size());

			//primitive arrays have to be treated in another way
			if (array instanceof Object[]) {
				token.output.toArray((Object[]) array);
			} else {
				Object[] output = token.output.toArray();
				Arrayu.hardcopy(output, 0, array, 0, output.length);
			}

			//noinspection RedundantCast
			((ParseToken) token).output = array;
		}
	}

	/**
	 * Parse Boolean
	 * <br/>
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

		int l = Math.max(SYNTAX.TRUE.length(), SYNTAX.FALSE.length());
		String string = Inputu.getRemaining(token.input, l, l).trim();

		if (SYNTAX.TRUE.equals(string)) {
			token.output = true;
		} else if (SYNTAX.FALSE.equals(string)) {
			token.output = false;
		} else {
			throw new ParseException("Can't parse \"" + string + "\" as boolean");
		}
	}

	/**
	 * Parse Null
	 * <br/>
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

		int l = SYNTAX.NULL.length();
		String string = Inputu.getRemaining(token.input, l, l).trim();

		if (string.equals(SYNTAX.NULL)) {
			token.output = null;
		} else {
			throw new ParseException("can't parse " + string + " as null");
		}
	}

	/**
	 * Parse Number
	 * <br/>
	 * Parse the string from the given reader to an {@link Number}. Then set it to the given {@link AtomicReference buffer}.
	 *
	 * @param token the parsing instance that holds the variables of this parsing
	 * @throws ParseException               when any parsing exception occurs
	 * @throws NullPointerException         if the given 'token' is null
	 * @throws IOException                  when any I/O exception occurs
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct the number
	 */
	@ParseMethod(@Filter(
			subIn = Number.class,
			in = {
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

		Class klass = token.klazz.getKlass();

		if (!Number.class.isAssignableFrom(klass))
			if (klass.isAssignableFrom(Number.class))
				klass = Number.class;
			else throw new IllegalArgumentException(token.klazz + " can't be satisfied with a number");

		int l = DEFAULT_VALUE_LENGTH;
		String string = Inputu.getRemaining(token.input, l, l).trim().toUpperCase();
		char suffix = string.charAt(string.length() - 1);
		String number = Character.isDigit(suffix) ? string : string.substring(0, string.length() - 1);

		//use the type that have been presented in the input
		if (klass == Number.class) {
			switch (suffix) {
				case 'B':
					token.output = Byte.valueOf(number);
					return;
				case 'D':
					token.output = Double.valueOf(number);
					return;
				case 'F':
					token.output = Float.valueOf(number);
					return;
				case 'L':
					token.output = Long.valueOf(number);
					return;
				case 'S':
					token.output = Short.valueOf(number);
					return;
				default:
					token.output = Integer.valueOf(number);
			}
		} else {
			//int, float, etc... => Integer, Float, etc...
			Class klassObjective = Reflectionu.asObjectClass(klass);
			try {
				//Using 'valueOf' method
				token.output = (Number) klassObjective.getMethod("valueOf", String.class).invoke(null, number);
			} catch (ReflectiveOperationException e) {
				//Using constructor
				token.output = (Number) klassObjective.getConstructor(String.class).newInstance(number);
			}
		}
	}

	/**
	 * Parse Object
	 * <br/>
	 * Parse the string from the given reader to an {@link Map Object}. Then set it to the given {@link AtomicReference buffer}.
	 *
	 * @param token the parsing instance that holds the variables of this parsing
	 * @throws ParseException               when any parsing exception occurs
	 * @throws IOException                  when any I/O exception occurs
	 * @throws NullPointerException         if the given 'token' is null
	 * @throws ReflectiveOperationException if any exception occurs while trying to construct the map
	 */
	@ParseMethod(@Filter(subIn = Map.class))
	protected void parseObject(ParseToken<Map> token) throws IOException, ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		//setup the output
		if (!token.klazz.isInstance(token.output))
			token.output = token.klazz.isAssignableFrom(Map.class) ?
						   new HashMap() : token.klazz.getKlass().getConstructor().newInstance();

		//reject more elements
		boolean closed = false;
		//comment mode
		boolean comment = false;
		//syntax manager
		SyntaxTracker tracker = new SyntaxTracker(SYNTAX_NESTABLE, SYNTAX_LITERAL);
		//content reading buffer (for key and value)
		StringBuilder builder = new StringBuilder(DEFAULT_VALUE_LENGTH);
		//short backtrace
		StringBuilder backtrace = new StringBuilder(DEFAULT_VALUE_LENGTH);

		//the keys in the input
		Set keys = new HashSet();
		//key holder
		StringBuilder keyBuilder = null;

		//first read
		if (Inputu.isRemainingEquals(token.input, true, false, false, SYNTAX.OBJECT_START) != 0)
			throw new ParseException("Object not started");

		for (int i; (i = token.input.read()) != -1; ) {
			//reading character buffer
			char point = (char) i;

			if (closed)
				//only white spaces are allowed when closed
				if (Character.isWhitespace(i))
					continue;
				else throw new ParseException("Object closed before text end");
			if (tracker.length() == 0) {
				//the short past string
				String past = backtrace.append(point).toString();

				if (past.endsWith(SYNTAX.DECLARATION) || past.endsWith(SYNTAX.EQUATION)) {
					//key reading mode is over| value reading mode
					if (keyBuilder != null)
						throw new ParseException("Two equation symbol");

					//switch reading destination address
					keyBuilder = builder;

					builder = new StringBuilder(DEFAULT_VALUE_LENGTH);
					backtrace = new StringBuilder(DEFAULT_VALUE_LENGTH);
					continue;
				} else if ((closed = past.endsWith(SYNTAX.OBJECT_END)) || past.endsWith(SYNTAX.MEMBER_END)) {
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
					builder = new StringBuilder(DEFAULT_VALUE_LENGTH);
					backtrace = new StringBuilder(DEFAULT_VALUE_LENGTH);
					continue;
				}
			} else if (backtrace.length() != 0) {
				//reset short backtrace
				backtrace = new StringBuilder(DEFAULT_VALUE_LENGTH);
			}

			//notify syntax manager
			tracker.append(point);

			if (this.SYNTAX_COMMENT.containsValue(tracker.getUnwrap())) {
				//currently in comment mode
				if (!comment) {
					//delete the comment open symbol
					builder.deleteCharAt(builder.length() - 1);
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
	 * Parse Recurse
	 * <br/>
	 * Parse the string from the given reader to an {@link Recurse}. Then set it to the given {@link AtomicReference buffer}.
	 *
	 * @param token the parsing instance that holds the variables of this parsing
	 * @throws ParseException           when any parsing exception occurs
	 * @throws IOException              when any I/O exception occurs
	 * @throws NullPointerException     if the given 'token' is null
	 * @throws java.text.ParseException when any parsing exception occurs
	 */
	@ParseMethod(@Filter(Recurse.class))
	protected void parseRecurse(ParseToken<Recurse> token) throws IOException, java.text.ParseException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		int l = DEFAULT_VALUE_LENGTH + SYNTAX.RECURSE.length();
		String string = Inputu.getRemaining(token.input, l, l).trim().substring(SYNTAX.RECURSE.length());

		int index = Integer.parseInt(string);
		int i = 0;

		for (ParseToken<Recurse> grand = token.parent; grand != null; grand = grand.parent, i++)
			if (i == index) {
				token.output = grand.output;
				return;
			}

		throw new ParseException(token + " didn't recurse");
	}

	/**
	 * Parse String
	 * <br/>
	 * Parse the string from the given reader to an {@link String}. Then set it to the given {@link AtomicReference buffer}.
	 *
	 * @param token the parsing instance that holds the variables of this parsing
	 * @throws ParseException               when any parsing exception occurs
	 * @throws IOException                  when any I/O exception occurs
	 * @throws NullPointerException         if the given 'token' is null
	 * @throws ReflectiveOperationException if any exception occurred while trying to construct the char-sequence
	 */
	@ParseMethod(@Filter(subIn = CharSequence.class))
	protected void parseString(ParseToken<CharSequence> token) throws IOException, ReflectiveOperationException {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		Class klass = token.klazz.getKlass();

		int l = DEFAULT_VALUE_LENGTH;
		String string = Inputu.getRemaining(token.input, l, l).trim();
		String value = string.substring(SYNTAX.STRING_START.length(), string.length() - SYNTAX.STRING_END.length());

		for (Map.Entry<String, String> escapable : STRING_ESCAPABLES.entrySet())
			value.replace(escapable.getValue(), escapable.getKey());

		if (klass == String.class)
			token.output = value;
		else if (CharSequence.class.isAssignableFrom(klass))
			//if we can construct a new of that clazz
			try {
				token.output = (CharSequence) klass.getMethod("valueOf", String.class).invoke(null, value);
			} catch (ReflectiveOperationException e) {
				token.output = (CharSequence) klass.getConstructor(String.class).newInstance(value);
			}
		else if (klass.isAssignableFrom(CharSequence.class))
			//if the results can satisfied the clazz
			token.output = value;
		else throw new IllegalArgumentException(klass + " can't be satisfied with a string");
	}

	/**
	 * Set the default values of JSON for this json format.
	 *
	 * @return this
	 */
	protected JSON setDefaults() {
		this.DEFAULT_MEMBERS_COUNT = 10;
		this.DEFAULT_NESTING_DEPTH = 5;
		this.DEFAULT_VALUE_LENGTH = 20;
		this.DEFAULT_WHITE_SPACE_LENGTH = 20;

		this.STRING_ESCAPABLES = new HashMap<>();
		this.STRING_ESCAPABLES.put("\\", "\\\\");
		this.STRING_ESCAPABLES.put("\"", "\\\"");
		this.STRING_ESCAPABLES.put("\n", "\\\n");
		this.STRING_ESCAPABLES.put("\r", "\\\r");
		this.STRING_ESCAPABLES.put("\t", "\\\t");

		this.SYNTAX = new Syntax();

		this.SYNTAX_NESTABLE = new HashMap<>();
		this.SYNTAX_NESTABLE.put(this.SYNTAX.OBJECT_START, this.SYNTAX.OBJECT_END);
		this.SYNTAX_NESTABLE.put(this.SYNTAX.ARRAY_START, this.SYNTAX.ARRAY_END);

		this.SYNTAX_LITERAL = new HashMap<>();
		this.SYNTAX_LITERAL.put(this.SYNTAX.STRING_START, this.SYNTAX.STRING_END);
		this.SYNTAX_LITERAL.put(this.SYNTAX.COMMENT_START, this.SYNTAX.COMMENT_END);
		this.SYNTAX_LITERAL.put(this.SYNTAX.LINE_COMMENT_START, this.SYNTAX.LINE_COMMENT_END);

		this.SYNTAX_COMMENT = new HashMap<>();
		this.SYNTAX_COMMENT.put(this.SYNTAX.COMMENT_START, this.SYNTAX.COMMENT_END);
		this.SYNTAX_COMMENT.put(this.SYNTAX.LINE_COMMENT_START, this.SYNTAX.LINE_COMMENT_END);

		return this;
	}

	/**
	 * A structure holding the symbols of a JSON formatter.
	 *
	 * @implSpec all values should be used as final
	 */
	public static class Syntax {
		/**
		 * Array end char on JSON.
		 */
		public String ARRAY_END = "]";
		/**
		 * Array start char on JSON.
		 */
		public String ARRAY_START = "[";
		/**
		 * Declare that the comment ended.
		 */
		public String COMMENT_END = "*/";
		/**
		 * Declare that further characters are commented. Until the {@link #COMMENT_END} cancel it.
		 */
		public String COMMENT_START = "/*";
		/**
		 * Pair mapping char on JSON.
		 */
		public String DECLARATION = ":";
		/**
		 * Pair equation char on other JSON like formats.
		 */
		public String EQUATION = "=";
		/**
		 * The value false of the type boolean on JSON.
		 */
		public String FALSE = "false";
		/**
		 * A symbol used to shows a line. To make the code more readable.
		 */
		public String LINE = "\n";
		/**
		 * Declare that the line comment ended.
		 */
		public String LINE_COMMENT_END = "\n";
		/**
		 * Declare that the next characters are commented. Until the {@link #LINE_COMMENT_END} cancel it.
		 */
		public String LINE_COMMENT_START = "//";
		/**
		 * Member end char on JSON.
		 */
		public String MEMBER_END = ",";
		/**
		 * The value null on JSON.
		 */
		public String NULL = "null";
		/**
		 * Object end char on JSON.
		 */
		public String OBJECT_END = "}";
		/**
		 * Object start char on JSON.
		 */
		public String OBJECT_START = "{";
		/**
		 * Recurse reference on JSON.
		 */
		public String RECURSE = "this";
		/**
		 * String start char on JSON.
		 */
		public String STRING_END = "\"";
		/**
		 * String end char on JSON.
		 */
		public String STRING_START = "\"";
		/**
		 * A symbol used to show a gap between characters. TO make the code more readable.
		 */
		public String TAB = "\t";
		/**
		 * The value true of the type boolean on JSON.
		 */
		public String TRUE = "true";
	}
}
