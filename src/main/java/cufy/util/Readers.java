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

import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Useful utils for {@link Reader}s.
 *
 * @author lsafer
 * @version 0.1.3
 * @since 12-Dec-2019
 */
final public class Readers {
	/**
	 * This is a util class. And shall not be instanced as an object.
	 *
	 * @throws AssertionError when called
	 */
	private Readers() {
		throw new AssertionError("No instance for you!");
	}

	/**
	 * Read the remaining string from the given reader.
	 *
	 * @param reader          to read from
	 * @param bufferCapacity  the capacity of the buffer (higher takes more RAM, lower takes more processing power and time)
	 * @param builderCapacity the initial capacity of the builder (higher takes more RAM, lower takes more processing power and time)
	 * @return all the characters from the given reader
	 * @throws NullPointerException     if the given reader is null
	 * @throws IllegalArgumentException if ether the given 'bufferCapacity' or 'builderCapacity' is less than 1
	 * @throws IOException              if any I/O exception occurs
	 */
	public static String getRemaining(Reader reader, int bufferCapacity, int builderCapacity) throws IOException {
		Objects.requireNonNull(reader, "reader");
		if (bufferCapacity < 1)
			throw new IllegalArgumentException("bufferCapacity is less than 1");
		if (builderCapacity < 1)
			throw new IllegalArgumentException("builderCapacity is less than 1");

		StringBuilder builder = new StringBuilder(builderCapacity);
		char[] buffer = new char[bufferCapacity];

		int l;
		while ((l = reader.read(buffer)) != -1)
			builder.append(buffer, 0, l);

		return builder.toString();
	}

	/**
	 * Read the remaining string from the given reader. Or less if the remaining string is greater than 'readLimit'.
	 *
	 * @param reader    to read from
	 * @param readLimit the
	 * @return all the characters from the given reader
	 * @throws NullPointerException     if the given reader is null
	 * @throws IllegalArgumentException if ether the given 'readLimit' is negative
	 * @throws IOException              if any I/O exception occurs
	 */
	public static String getRemaining(Reader reader, int readLimit) throws IOException {
		Objects.requireNonNull(reader, "reader");
		if (readLimit < 0)
			throw new IllegalArgumentException("readLimit is negative");

		char[] buffer = new char[readLimit];

		reader.read(buffer);

		return new String(buffer);
	}

	/**
	 * This method will check if the remaining characters in the given reader is equals to any of the given strings. This method will depend on the
	 * given rules on the equation.
	 *
	 * @param reader     to read from
	 * @param trim       when true, this method will ignore the first and last characters if it's whitespaces
	 * @param fullRead   when true, this method will take the results once a string matches the read characters
	 * @param ignoreCase when true, this method will match the characters even if they're different case(ex. 'A' and 'a' will match)
	 * @param strings    the strings match
	 * @return the index of the string matched. Or -1 if no string matching
	 * @throws NullPointerException if the given 'reader' or 'strings' or any of the given strings is null
	 * @throws IOException          if any I/O exception occurred
	 * @apiNote can't predict after how many characters will this method stop reading from the given reader.
	 * @implSpec this method will not invoke {@link Reader#mark} or {@link Reader#reset()}
	 */
	public static int isRemainingEquals(Reader reader, boolean trim, boolean fullRead, boolean ignoreCase, String... strings) throws IOException {
		Objects.requireNonNull(reader, "reader");
		Objects.requireNonNull(strings, "strings");

		List<String> list = new ArrayList<>(Arrays.asList(strings));
		list.replaceAll(s -> {
			Objects.requireNonNull(s, "strings[?]");
			if (trim)
				s = s.trim();
			if (ignoreCase)
				s = s.toLowerCase();
			return s;
		});

		int i = reader.read();

		if (trim) {
			do {
				if (i == -1)
					return list.indexOf("");
				if (!Character.isWhitespace(i))
					break;

				i = reader.read();
			} while (true);
		}

		boolean allNulls;
		do {
			char c = (char) (ignoreCase ? Character.toLowerCase(i) : i);

			allNulls = false;
			ListIterator<String> iterator = list.listIterator();
			for (int index = 0; iterator.hasNext(); index++) {
				String s = iterator.next();

				if (s != null) {
					if (!s.isEmpty()) {
						if (s.charAt(0) == c) {
							String sub = s.substring(1);

							if (!fullRead && sub.isEmpty())
								return index;

							iterator.set(sub);
							allNulls = false;
							continue;
						}
					} else if (trim && Character.isWhitespace(c)) {
						allNulls = false;
						continue;
					}

					iterator.set(null);
				}
			}
		} while (!allNulls && (i = reader.read()) != -1);

		return list.indexOf("");
	}
}
