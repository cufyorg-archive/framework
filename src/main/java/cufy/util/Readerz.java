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
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * Useful utils for {@link Reader}s.
 *
 * @author LSafer
 * @version 0.1.3
 * @since 0.0.5 ~2019.12.12
 */
public final class Readerz {
	/**
	 * This is an util class and must not be instanced as an object.
	 *
	 * @throws AssertionError when called.
	 */
	private Readerz() {
		throw new AssertionError("No instance for you!");
	}

	/**
	 * Read the remaining string from the given reader.
	 *
	 * @param reader          to read from.
	 * @param bufferCapacity  the capacity of the buffer (higher takes more RAM, lower takes more processing power and
	 *                        time).
	 * @param builderCapacity the initial capacity of the builder (higher takes more RAM, lower takes more processing
	 *                        power and time).
	 * @return all the characters from the given reader.
	 * @throws NullPointerException     if the given {@code reader} is null.
	 * @throws IllegalArgumentException if the given {@code bufferCapacity} or {@code builderCapacity} is less than 1.
	 * @throws IOException              if any I/O exception occurs.
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
	 * @param reader    to read from.
	 * @param readLimit the length limit of the returned string.
	 * @return all the characters from the given reader.
	 * @throws NullPointerException     if the given {@code reader} is null.
	 * @throws IllegalArgumentException if ether the given {@code readLimit} is negative.
	 * @throws IOException              if any I/O exception occurs.
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
	 * Determine if the remaining string in the given {@code reader} is equals to any of the given {@code strings}. This
	 * method will depend on the given rules on the equation. The position of the given {@code reader} after invoking
	 * this method is unspecified.
	 *
	 * @param reader     to read from.
	 * @param trim       when true, this method will ignore the first and last characters if it's whitespaces.
	 * @param fullRead   when true, this method will take the results once a string matches the read characters.
	 * @param ignoreCase when true, this method will match the characters even if they're different case(ex. 'A' and 'a'
	 *                   will match).
	 * @param strings    the strings to be matched.
	 * @return true, if any of the given {@code strings} does match the remaining string in the given {@code reader}
	 * 		depending on the given rules.
	 * @throws NullPointerException if the given {@code reader} or {@code strings} or any of the given strings is null.
	 * @throws IOException          if any I/O exception occurred.
	 */
	public static boolean isRemainingEquals(Reader reader, boolean trim, boolean fullRead, boolean ignoreCase, String... strings) throws IOException {
		Objects.requireNonNull(reader, "reader");
		Objects.requireNonNull(strings, "strings");

		List<String> list = new ArrayList<>(Arrayz.asList(strings));

		if (list.contains(""))
			//easy exit
			return true;

		if (ignoreCase || trim)
			list.replaceAll(s -> {
				if (trim)
					s = s.trim();
				return ignoreCase ? s.toLowerCase() : s;
			});

		int i = reader.read();

		if (trim)
			do {
				if (i == -1)
					//empty from the start
					return false;
				if (!Character.isWhitespace(i))
					break;

				i = reader.read();
			} while (true);

		do {
			char c = (char) (ignoreCase ? Character.toLowerCase(i) : i);

			ListIterator<String> iterator = list.listIterator();
			while (iterator.hasNext()) {
				String s = iterator.next();

				if (!s.isEmpty()) {
					if (s.charAt(0) == c) {
						String sub = s.substring(1);

						if (!fullRead && sub.isEmpty())
							//match!
							return true;

						iterator.set(sub);
						continue;
					}
				} else if (trim && Character.isWhitespace(c))
					continue;

				iterator.remove();
			}
		} while (!list.isEmpty() && (i = reader.read()) != -1);

		return list.contains("");
	}
}
