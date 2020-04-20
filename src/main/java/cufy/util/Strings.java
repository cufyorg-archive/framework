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
package cufy.util;

import java.util.Objects;

/**
 * Useful utils for strings.
 *
 * @author LSafer
 * @version 0.1.3
 * @since 11 Jun 2019
 */
final public class Strings {
	/**
	 * This is a util class. And shall not be instanced as an object.
	 *
	 * @throws AssertionError when called
	 */
	private Strings() {
		throw new AssertionError("No instance for you!");
	}

	/**
	 * Return the first missing query in the given string.
	 *
	 * @param string  to check
	 * @param queries to check for
	 * @return the first missing query on the given string. Or null if no query missing
	 * @throws NullPointerException if the given string or any of the given queries is null. Or the queries array itself is null
	 */
	public static CharSequence all(String string, CharSequence... queries) {
		Objects.requireNonNull(string, "string");
		Objects.requireNonNull(queries, "queries");

		for (CharSequence query : queries) {
			Objects.requireNonNull(query, "query");

			if (!string.contains(query))
				return query;
		}

		return null;
	}

	/**
	 * Return the first query found on the given string. Or null if no query found.
	 *
	 * @param string  to check
	 * @param queries to check for
	 * @return the first found query on the given string. or null if no query found
	 * @throws NullPointerException if the given string or any of the given queries is null. Or the queries array itself is null
	 */
	public static CharSequence any(String string, CharSequence... queries) {
		Objects.requireNonNull(string, "string");
		Objects.requireNonNull(queries, "queries");

		for (CharSequence query : queries) {
			Objects.requireNonNull(query, "query");

			if (string.contains(query))
				return query;
		}

		return null;
	}

	/**
	 * Get given string repeated many times as given.
	 * <br><br><b>example</b>
	 * <pre>
	 * repeat("abc", " ", 3) == "abc abc abc"
	 * </pre>
	 *
	 * @param string    to repeat from
	 * @param delimiter to be in the middle of the repeated strings
	 * @param times     to repeat
	 * @return new string created from repeated given string
	 * @throws NullPointerException     if the given string or delimiter is null
	 * @throws IllegalArgumentException if the given 'times' is negative
	 */
	public static String repeat(String string, String delimiter, int times) {
		Objects.requireNonNull(string, "string");
		Objects.requireNonNull(delimiter, "delimiter");
		if (times < 0)
			throw new IllegalArgumentException("times is negative");

		if (times == 0)
			return "";

		final int sl = string.length();
		final int dl = delimiter.length();
		final int length = (sl + dl) * times - dl;
		final int lm = length - 1;

		char[] chars = new char[length];
		for (int i = 0; i < length; ) {
			string.getChars(0, sl, chars, i);
			i += sl;

			if (i < lm) {
				delimiter.getChars(0, dl, chars, i);
				i += dl;
			}
		}

		return new String(chars);
	}

	/**
	 * Get given string repeated many times as given.
	 * <br><br><b>example</b>
	 * <pre>
	 * repeat("abc_", 3) == "abc_abc_abc_"
	 * </pre>
	 *
	 * @param string to repeat from
	 * @param times  to repeat
	 * @return new string created from repeated given string
	 * @throws NullPointerException     if the given string or delimiter is null
	 * @throws IllegalArgumentException if the given 'times' is negative
	 */
	public static String repeat(String string, int times) {
		Objects.requireNonNull(string, "string");
		if (times < 0)
			throw new IllegalArgumentException("times is negative");

		if (times == 0)
			return "";

		final int sl = string.length();
		final int length = sl * times;

		char[] chars = new char[length];
		for (int i = 0; i < length; i += sl)
			string.getChars(0, sl, chars, i);

		return new String(chars);
	}
}
