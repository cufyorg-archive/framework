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

import java.util.Comparator;
import java.util.function.Supplier;

/**
 * An Utility class for objects.
 * <p>
 * This class includes all the methods in the standard {@link java.util.Objects} utility class with
 * the same behaviour. So switching to import this class will not make any changes to files
 * previously imported {@link java.util.Objects}.
 * <p>
 * Note: this class chosen to be an interface to allow inheritance in the utility classes.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.07.21
 */
public interface Objects {
	/**
	 * Compare the given {@code object} to the given {@code other} using the given {@code
	 * comparator}. If the given {@code object} is the given {@code other}, then 0 will be returned.
	 * Otherwise, {@code comparator.compare(object, other)} will be returned.
	 *
	 * @param object     the object to be compared with the given {@code other}.
	 * @param other      the other object to be compared with the given {@code object}.
	 * @param comparator the comparator to be used to compare the given {@code object} to the given
	 *                   {@code other}.
	 * @param <T>        the type of the objects.
	 * @return 0 if the given {@code object} is the given {@code other}. Otherwise, {@code
	 * 		comparator.compare(object, other)}.
	 * @throws NullPointerException if the given {@code comparator} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	static <T> int compare(T object, T other, Comparator<? super T> comparator) {
		return object == other ? 0 : comparator.compare(object, other);
	}

	/**
	 * Determine if the given {@code object} and the given {@code other} are deeply equal. If both
	 * the given {@code object} and {@code other} are arrays, then determine if they are deeply
	 * equals each other. If both the given {@code object} and {@code other} are null, then they are
	 * equal. If the given {@code object} and only the given {@code object} is null, then they are
	 * not equal. Otherwise, determine if the given {@code object} {@link Object#equals(Object)} the
	 * given {@code other}.
	 *
	 * @param object the object to be deeply matched to the given {@code other}.
	 * @param other  the other object to be deeply matched to the given {@code object}.
	 * @return true, if the given {@code object} and the given {@code other} are deeply equal.
	 * @since 0.1.5 ~2020.07.24
	 */
	static boolean deepEquals(Object object, Object other) {
		if (object == other)
			return true;
		if (object == null || other == null)
			return false;
		if (object.getClass().isArray() && other.getClass().isArray())
			return Arrays.deepEquals0(object, other);

		return object.equals(other);
	}

	/**
	 * Determine if the given {@code object} and the given {@code other} are equals. If both the
	 * given {@code object} and {@code other} are null, then they are equal. If the given {@code
	 * object} and only the given {@code object} is null, then they are not equal. Otherwise,
	 * determine if the given {@code object} {@link Object#equals(Object)} the given {@code other}.
	 *
	 * @param object the object to be matched to the given {@code other}.
	 * @param other  the other object to be matched to the given {@code object}.
	 * @return true, if the given {@code object} and the given {@code other} are equal.
	 * @since 0.1.5 ~2020.07.24
	 */
	static boolean equals(Object object, Object other) {
		return object == other || object != null && object.equals(other);
	}

	/**
	 * Calculate the hashCode of the given {@code objects}. If the given {@code objects} is null,
	 * then its hashCode is 0.
	 *
	 * @param objects the array to calculate the hashCode of its elements.
	 * @return the hashCode of the given {@code objects}.
	 * @since 0.1.5 ~2020.07.24
	 */
	static int hash(Object... objects) {
		return Arrays.hashCode(objects);
	}

	/**
	 * Calculate the hashCode of the given {@code object}. If the given {@code object} is null, then
	 * its hashCode is 0.
	 *
	 * @param object the object to calculate its hashCode.
	 * @return the hashCode of the given {@code object}.
	 * @since 0.1.5 ~2020.07.24
	 */
	static int hashCode(Object object) {
		return object == null ? 0 : object.hashCode();
	}

	/**
	 * Determine if the given {@code object} is an array.
	 *
	 * @param object the object to check if it is an array.
	 * @return true, if the given {@code object} is an array.
	 * @since 0.1.5 ~2020.07.24
	 */
	static boolean isArray(Object object) {
		return object != null && object.getClass().isArray();
	}

	/**
	 * Determine if the given {@code object} is null.
	 *
	 * @param object to be checked if it is null.
	 * @return true, if the given {@code object} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	static boolean isNull(Object object) {
		return object == null;
	}

	/**
	 * Determine if the given {@code object} is not null.
	 *
	 * @param object to be checked if it is null.
	 * @return true, if the given {@code object} is not null.
	 * @since 0.1.5 ~2020.07.24
	 */
	static boolean nonNull(Object object) {
		return object != null;
	}

	/**
	 * Return the given {@code object} if it is not null. Otherwise, throw a new {@link
	 * NullPointerException}.
	 *
	 * @param object to be returned.
	 * @param <T>    the type of the object.
	 * @return the given {@code object}.
	 * @throws NullPointerException if the given {@code object} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	static <T> T requireNonNull(T object) {
		if (object != null)
			return object;

		throw new NullPointerException();
	}

	/**
	 * Return the given {@code object} if it is not null. Otherwise, throw a new {@link
	 * NullPointerException} with the given {@code message}.
	 * <p>
	 * If the given {@code message} is null, then the exception will have no message if it got
	 * thrown.
	 *
	 * @param object  to be returned.
	 * @param message the message of the exception if it got thrown.
	 * @param <T>     the type of the object.
	 * @return the given {@code object}.
	 * @throws NullPointerException if the given {@code object} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	static <T> T requireNonNull(T object, String message) {
		if (object != null)
			return object;

		throw new NullPointerException(message);
	}

	/**
	 * Return the given {@code object} if it is not null. Otherwise, throw a new {@link
	 * NullPointerException} with the returned string from the given {@code message}.
	 * <p>
	 * If the given {@code message} is null or did not returned an instance of {@link String}, then
	 * the exception will have no message if it got thrown.
	 *
	 * @param object  to be returned.
	 * @param message the supplier of the message of the exception if it got thrown.
	 * @param <T>     the type of the object.
	 * @return the given {@code object}.
	 * @throws NullPointerException if the given {@code object} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	static <T> T requireNonNull(T object, Supplier<String> message) {
		if (object != null)
			return object;

		if (message != null) {
			Object s = message.get();

			//noinspection ConstantConditions
			if (s instanceof String)
				throw new NullPointerException((String) s);
		}

		throw new NullPointerException();
	}

	/**
	 * Return the string representation of the given {@code object}. Or {@code "null"} if the given
	 * {@code object} is null.
	 *
	 * @param object the object to get the string representation of it.
	 * @return the string string representation of the given {@code object}, Or {@code "null"} if
	 * 		the given {@code object} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	static String toString(Object object) {
		return String.valueOf(object);
	}

	/**
	 * Return the string representation of the given {@code object}. Or the given {@code
	 * nullDefault} if the given {@code object} is null.
	 *
	 * @param object      the object to get the string representation of it.
	 * @param nullDefault the string to be returned if the given {@code object} is null.
	 * @return the string string representation of the given {@code object}, Or the given {@code
	 * 		nullDefault} if the given {@code object} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	static String toString(Object object, String nullDefault) {
		return object == null ? nullDefault : object.toString();
	}
}
