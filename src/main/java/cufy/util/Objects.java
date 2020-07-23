package cufy.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.function.*;

/**
 * An Utility class for objects.
 * <p>
 * This class includes all the methods in the standard {@link java.util.Objects} utility class with the same behaviour. So
 * switching to import this class will not make any changes to files previously imported {@link java.util.Objects}.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.07.21
 */
public final class Objects {
	/**
	 * This is an util class and must not be instanced as an object.
	 *
	 * @throws AssertionError when called.
	 */
	private Objects() {
		throw new AssertionError("No instance for you!");
	}

	/**
	 * Compare the given {@code object} to the given {@code other} using the given {@code comparator}. If the given {@code object}
	 * is the given {@code other}, then 0 will be returned. Otherwise, {@code comparator.compare(object, other)} will be
	 * returned.
	 *
	 * @param object     the object to be compared with the given {@code other}.
	 * @param other      the other object to be compared with the given {@code object}.
	 * @param comparator the comparator to be used to compare the given {@code object} to the given {@code other}.
	 * @param <T>        the type of the objects.
	 * @return 0 if the given {@code object} is the given {@code other}. Otherwise, {@code comparator.compare(object, other)}.
	 * @throws NullPointerException if the given {@code comparator} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> int compare(T object, T other, Comparator<? super T> comparator) {
		return object == other ? 0 : comparator.compare(object, other);
	}

	/**
	 * Determine if the given {@code object} and the given {@code other} are deeply equal. If both the given {@code object} and
	 * {@code other} are arrays, then determine if they are deeply equals each other. If both the given {@code object} and {@code
	 * other} are null, then they are equal. If the given {@code object} and only the given {@code object} is null, then they are
	 * not equal. Otherwise, determine if the given {@code object} {@link Object#equals(Object)} the given {@code other}.
	 *
	 * @param object the object to be deeply matched to the given {@code other}.
	 * @param other  the other object to be deeply matched to the given {@code object}.
	 * @return true, if the given {@code object} and the given {@code other} are deeply equal.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean deepEquals(Object object, Object other) {
		if (object == other)
			return true;
		if (object == null || other == null)
			return false;
		if (object.getClass().isArray() && other.getClass().isArray())
			return Arrays.deepEquals1(object, other);

		return object.equals(other);
	}

	/**
	 * Determine if the given {@code object} and the given {@code other} are equals. If both the given {@code object} and {@code
	 * other} are null, then they are equal. If the given {@code object} and only the given {@code object} is null, then they are
	 * not equal. Otherwise, determine if the given {@code object} {@link Object#equals(Object)} the given {@code other}.
	 *
	 * @param object the object to be matched to the given {@code other}.
	 * @param other  the other object to be matched to the given {@code object}.
	 * @return true, if the given {@code object} and the given {@code other} are equal.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(Object object, Object other) {
		return object == other || object != null && object.equals(other);
	}

	/**
	 * Calculate the hashCode of the given {@code objects}. If the given {@code objects} is null, then its hashCode is 0.
	 *
	 * @param objects the array to calculate the hashCode of its elements.
	 * @return the hashCode of the given {@code objects}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int hash(Object... objects) {
		return Arrays.hashCode(objects);
	}

	/**
	 * Calculate the hashCode of the given {@code object}. If the given {@code object} is null, then its hashCode is 0.
	 *
	 * @param object the object to calculate its hashCode.
	 * @return the hashCode of the given {@code object}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int hashCode(Object object) {
		return object == null ? 0 : object.hashCode();
	}

	/**
	 * Determine if the given {@code object} is an array. An array could be null. So, Null is an array.
	 *
	 * @param object the object to check if it is an array.
	 * @return true, if the given {@code object} is an array. Or if the given {@code object} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean isArray(Object object) {
		return object == null || object.getClass().isArray();
	}

	/**
	 * Determine if the given {@code i} is even.
	 *
	 * @param i the integer to check if it is even.
	 * @return true, if the given {@code i} is even.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean isEven(int i) {
		return i % 2 == 0;
	}

	/**
	 * Determine if the given {@code object} is greater than the given {@code other}. If the given {@code object} is null and the
	 * given {@code other} is a {@link Comparable}, then determine if the given {@code other} is less than the given {@code
	 * object}. If both the given {@code object} and {@code other} are null, then the given {@code object} is not greater than the
	 * given {@code other}. Otherwise determine if the given {@code object} is {@link Comparable#compareTo(Object) greater} than
	 * the given {@code other}.
	 *
	 * @param object the object to be checked if it is greater than the given {@code other}.
	 * @param other  the other object to be checked if the given {@code object} is greater than it.
	 * @return true, if the given {@code object} is greater than the given {@code other}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean isGrater(Comparable object, Object other) {
		return object == null ?
			   other instanceof Comparable &&
			   ((Comparable) other).compareTo(object) < 0 :
			   object.compareTo(other) > 0;
	}

	/**
	 * Determine if the given {@code object} is less than the given {@code other}. If the given {@code object} is null and the
	 * given {@code other} is a {@link Comparable}, then determine if the given {@code other} is greater than the given {@code
	 * object}. If both the given {@code object} and {@code other} are null, then the given {@code object} is not less than the
	 * given {@code other}. Otherwise determine if the given {@code object} is {@link Comparable#compareTo(Object) less} than the
	 * given {@code other}.
	 *
	 * @param object the object to be checked if it is less than the given {@code other}.
	 * @param other  the other object to be checked if the given {@code object} is less than it.
	 * @return true, if the given {@code object} is less than the given {@code other}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean isLess(Comparable object, Object other) {
		return object == null ?
			   other instanceof Comparable &&
			   ((Comparable) other).compareTo(object) > 0 :
			   object.compareTo(other) < 0;
	}

	/**
	 * Determine if the given {@code i} is negative.
	 *
	 * @param i to be checked if it is a negative integer.
	 * @return true, if the given {@code i} is negative.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean isNegative(int i) {
		return i < 0;
	}

	/**
	 * Determine if the given {@code object} is null.
	 *
	 * @param object to be checked if it is null.
	 * @return true, if the given {@code object} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean isNull(Object object) {
		return object == null;
	}

	/**
	 * Determine if the given {@code object} is an {@link Object} array. An {@link Object} array could be null. So, Null is an
	 * {@link Object} array.
	 *
	 * @param object the object to check if it is an {@link Object} array.
	 * @return true, if the given {@code object} is an {@link Object} array. Or if the given {@code object} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean isObjectArray(Object object) {
		return object == null || object instanceof Object[];
	}

	/**
	 * Determine if the given {@code klass} is a class representing an {@link Object} array.
	 *
	 * @param klass the class to check if it represents an {@link Object} array.
	 * @return true, if the given {@code klass} represents an {@link Object} array. Or false if the given {@code klass} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean isObjectArrayClass(Class klass) {
		return klass != null && Object[].class.isAssignableFrom(klass);
	}

	/**
	 * Determine if the given {@code i} is odd.
	 *
	 * @param i the integer to check if it is odd.
	 * @return true, if the given {@code i} is odd.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean isOdd(int i) {
		return i % 2 != 0;
	}

	/**
	 * Determine if the given {@code i} is positive.
	 *
	 * @param i to be checked if it is a positive integer.
	 * @return true, if the given {@code i} is positive.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean isPositive(int i) {
		return i > 0;
	}

	/**
	 * Determine if the given {@code object} is a {@code primitive} array. A {@code primitive} array could be null. So, Null is a
	 * {@code primitive} array.
	 *
	 * @param object the object to check if it is a {@code primitive} array.
	 * @return true, if the given {@code object} is a {@code primitive} array. Or if the given {@code object} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean isPrimitiveArray(Object object) {
		return object == null ||
			   object.getClass().isArray() &&
			   object.getClass().getComponentType().isPrimitive();
	}

	/**
	 * Determine if the given {@code klass} is a class representing a {@code primitive} array.
	 *
	 * @param klass the class to check if it represents a {@code primitive} array.
	 * @return true, if the given {@code klass} represents a {@code primitive} array. Or false if the given {@code klass} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean isPrimitiveArrayClass(Class klass) {
		return klass != null &&
			   klass.isArray() &&
			   klass.getComponentType().isPrimitive();
	}

	/**
	 * Determine if the given {@code object} is the given {@code other}.
	 *
	 * @param object the object to check if it is the given {@code other}.
	 * @param other  the other object to check if it is the given {@code object}.
	 * @return true, if the given {@code object} is the given {@code other}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean isSame(Object object, Object other) {
		return object == other;
	}

	/**
	 * Determine if the given {@code object} is not an array. An array could be null. So, Null is an array.
	 *
	 * @param object the object to check if it is not an array.
	 * @return true, if the given {@code object} is not an array. Or false, if the given {@code object} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean nonArray(Object object) {
		return object != null && !object.getClass().isArray();
	}

	/**
	 * Determine if the given {@code object} and the given {@code other} are not deeply equal. If both the given {@code object}
	 * and {@code other} are arrays, then determine if they are not deeply equals each other. If both the given {@code object} and
	 * {@code other} are null, then they are equal. If the given {@code object} and only the given {@code object} is null, then
	 * they are not equal. Otherwise, determine if the given {@code object} not {@link Object#equals(Object)} the given {@code
	 * other}.
	 *
	 * @param object the object to be deeply matched to the given {@code other}.
	 * @param other  the other object to be deeply matched to the given {@code object}.
	 * @return true, if the given {@code object} and the given {@code other} are not deeply equal.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean nonDeepEquals(Object object, Object other) {
		if (object == other)
			return false;
		if (object == null || other == null)
			return true;
		if (object.getClass().isArray() && other.getClass().isArray())
			return !Arrays.deepEquals1(object, other);

		return !object.equals(other);
	}

	/**
	 * Determine if the given {@code object} and the given {@code other} are not equals. If both the given {@code object} and
	 * {@code other} are null, then they are equal. If the given {@code object} and only the given {@code object} is null, then
	 * they are not equal. Otherwise, determine if the given {@code object} not {@link Object#equals(Object)} the given {@code
	 * other}.
	 *
	 * @param object the object to be matched to the given {@code other}.
	 * @param other  the other object to be matched to the given {@code object}.
	 * @return true, if the given {@code object} and the given {@code other} are not equal.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean nonEquals(Object object, Object other) {
		return object != other && (object == null || !object.equals(other));
	}

	/**
	 * Determine if the given {@code i} is not even.
	 *
	 * @param i the integer to check if it is even.
	 * @return true, if the given {@code i} is not even.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean nonEven(int i) {
		return i % 2 != 0;
	}

	/**
	 * Determine if the given {@code object} is not greater than the given {@code other}. If the given {@code object} is null and
	 * the given {@code other} is a {@link Comparable}, then determine if the given {@code other} is more than or equals the given
	 * {@code object}. If both the given {@code object} and {@code other} are null, then the given {@code object} is not greater
	 * than the given {@code other}. Otherwise determine if the given {@code object} is not {@link Comparable#compareTo(Object)
	 * greater} than the given {@code other}.
	 *
	 * @param object the object to be checked if it is greater than the given {@code other}.
	 * @param other  the other object to be checked if the given {@code object} is greater than it.
	 * @return true, if the given {@code object} is not greater than the given {@code other}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean nonGreater(Comparable object, Object other) {
		return object == null ?
			   !(other instanceof Comparable) ||
			   ((Comparable) other).compareTo(object) >= 0 :
			   object.compareTo(other) <= 0;
	}

	/**
	 * Determine if the given {@code object} is not less than the given {@code other}. If the given {@code object} is null and the
	 * given {@code other} is a {@link Comparable}, then determine if the given {@code other} is less than or equals the given
	 * {@code object}. If both the given {@code object} and {@code other} are null, then the given {@code object} is not less than
	 * the given {@code other}. Otherwise determine if the given {@code object} is not {@link Comparable#compareTo(Object) less}
	 * than the given {@code other}.
	 *
	 * @param object the object to be checked if it is less than the given {@code other}.
	 * @param other  the other object to be checked if the given {@code object} is less than it.
	 * @return true, if the given {@code object} is not less than the given {@code other}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean nonLess(Comparable object, Object other) {
		return object == null ?
			   !(other instanceof Comparable) ||
			   ((Comparable) other).compareTo(object) <= 0 :
			   object.compareTo(other) >= 0;
	}

	/**
	 * Determine if the given {@code i} is not negative.
	 *
	 * @param i to be checked if it is a negative integer.
	 * @return true, if the given {@code i} is not negative.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean nonNegative(int i) {
		return i >= 0;
	}

	/**
	 * Determine if the given {@code object} is not null.
	 *
	 * @param object to be checked if it is null.
	 * @return true, if the given {@code object} is not null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean nonNull(Object object) {
		return object != null;
	}

	/**
	 * Determine if the given {@code object} is not an {@link Object} array. An {@link Object} array could be null. So, Null is an
	 * {@link Object} array.
	 *
	 * @param object the object to check if it is an {@link Object} array.
	 * @return true, if the given {@code object} is not an {@link Object} array. Or false, if the given {@code object} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean nonObjectArray(Object object) {
		return object != null && !(object instanceof Object[]);
	}

	/**
	 * Determine if the given {@code klass} is not a class representing an {@link Object} array.
	 *
	 * @param klass the class to check if it represents an {@link Object} array.
	 * @return true, if the given {@code klass} is null or does not represent an {@link Object} array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean nonObjectArrayClass(Class klass) {
		return klass == null || !Object[].class.isAssignableFrom(klass);
	}

	/**
	 * Determine if the given {@code i} is not odd.
	 *
	 * @param i the integer to check if it is odd.
	 * @return true, if the given {@code i} is not odd.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean nonOdd(int i) {
		return i % 2 == 0;
	}

	/**
	 * Determine if the given {@code i} is not positive.
	 *
	 * @param i to be checked if it is not a positive integer.
	 * @return true, if the given {@code i} is not positive.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean nonPositive(int i) {
		return i <= 0;
	}

	/**
	 * Determine if the given {@code object} is not a {@code primitive} array. A {@code primitive} array could be null. So, Null
	 * is a {@code primitive} array.
	 *
	 * @param object the object to check if it is a {@code primitive} array.
	 * @return true, if the given {@code object} is not a {@code primitive} array. Or false, if the given {@code object} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean nonPrimitiveArray(Object object) {
		return object != null &&
			   !object.getClass().isArray() ||
			   !object.getClass().getComponentType().isPrimitive();
	}

	/**
	 * Determine if the given {@code klass} is not a class representing a {@code primitive} array.
	 *
	 * @param klass the class to check if it represents a {@code primitive} array.
	 * @return true, if the given {@code klass} is null or does not represent a {@code primitive} array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean nonPrimitiveArrayClass(Class klass) {
		return klass == null ||
			   !klass.isArray() ||
			   !klass.getComponentType().isPrimitive();
	}

	/**
	 * Determine if the given {@code object} is not the given {@code other}.
	 *
	 * @param object the object to check if it is the given {@code other}.
	 * @param other  the other object to check if it is the given {@code object}.
	 * @return true, if the given {@code object} is not the given {@code other}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean nonSame(Object object, Object other) {
		return object != other;
	}

	/**
	 * Return the given {@code object} if the given {@code condition} is true. Otherwise, throw a new {@link
	 * IllegalArgumentException}.
	 *
	 * @param object    to be returned.
	 * @param condition to return the given {@code object} if it is true, Or throw if it is false.
	 * @param <T>       the type of the object.
	 * @return the given {@code object}.
	 * @throws IllegalArgumentException if the given {@code condition} is false.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> T require(T object, boolean condition) {
		if (condition)
			return object;

		throw new IllegalArgumentException();
	}

	/**
	 * Return the given {@code object} if the given {@code condition} returned true. Otherwise, throw a new {@link
	 * IllegalArgumentException}.
	 *
	 * @param object    to be returned.
	 * @param condition to return the given {@code object} if it returned true, Or throw if it returned false.
	 * @param <T>       the type of the object.
	 * @return the given {@code object}.
	 * @throws IllegalArgumentException if the given {@code condition} is null or returned false.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> T require(T object, Predicate<T> condition) {
		if (condition != null && condition.test(object))
			return object;

		throw new IllegalArgumentException();
	}

	/**
	 * Return the given {@code object} if the given {@code condition} returned true. Otherwise, throw a new {@link
	 * IllegalArgumentException}.
	 *
	 * @param object    to be returned.
	 * @param other     a second parameter to be passed to the given {@code condition}.
	 * @param condition to return the given {@code object} if it returned true, Or throw if it returned false.
	 * @param <T>       the type of the object.
	 * @param <U>       the type of the other object.
	 * @return the given {@code object}.
	 * @throws IllegalArgumentException if the given {@code condition} is null or returned false.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T, U> T require(T object, U other, BiPredicate<T, U> condition) {
		if (condition != null && condition.test(object, other))
			return object;

		throw new IllegalArgumentException();
	}

	/**
	 * Return the given {@code object} if the given {@code condition} is true. Otherwise, throw a new {@link
	 * IllegalArgumentException} with the given {@code message}.
	 * <p>
	 * If the given {@code message} is null, then the exception will have no message if it got thrown.
	 *
	 * @param object    to be returned.
	 * @param condition to return the given {@code object} if it is true, Or throw if it is false.
	 * @param message   the message of the exception if it got thrown.
	 * @param <T>       the type of the object.
	 * @return the given {@code object}.
	 * @throws IllegalArgumentException if the given {@code condition} is false.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> T require(T object, boolean condition, String message) {
		if (condition)
			return object;

		throw message == null ?
			  new IllegalArgumentException() :
			  new IllegalArgumentException(message);
	}

	/**
	 * Return the given {@code object} if the given {@code condition} returned true. Otherwise, throw a new {@link
	 * IllegalArgumentException}.
	 * <p>
	 * If the given {@code message} is null, then the exception will have no message if it got thrown.
	 *
	 * @param object    to be returned.
	 * @param condition to return the given {@code object} if it returned true, Or throw if it returned false.
	 * @param message   the message of the exception if it got thrown.
	 * @param <T>       the type of the object.
	 * @return the given {@code object}.
	 * @throws IllegalArgumentException if the given {@code condition} is null or returned false.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> T require(T object, Predicate<T> condition, String message) {
		if (condition != null && condition.test(object))
			return object;

		throw message == null ?
			  new IllegalArgumentException() :
			  new IllegalArgumentException(message);
	}

	/**
	 * Return the given {@code object} if the given {@code condition} returned true. Otherwise, throw a new {@link
	 * IllegalArgumentException}.
	 * <p>
	 * If the given {@code message} is null, then the exception will have no message if it got thrown.
	 *
	 * @param object    to be returned.
	 * @param other     a second parameter to be passed to the given {@code condition}.
	 * @param condition to return the given {@code object} if it returned true, Or throw if it returned false.
	 * @param message   the message of the exception if it got thrown.
	 * @param <T>       the type of the object.
	 * @param <U>       the type of the other object.
	 * @return the given {@code object}.
	 * @throws IllegalArgumentException if the given {@code condition} is null or returned false.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T, U> T require(T object, U other, BiPredicate<T, U> condition, String message) {
		if (condition != null && condition.test(object, other))
			return object;

		throw message == null ?
			  new IllegalArgumentException() :
			  new IllegalArgumentException(message);
	}

	/**
	 * Return the given {@code object} if the given {@code condition} is true. Otherwise, throw a new instance of the given {@code
	 * exception} class, if the given {@code exception} is not null and does extend {@link Throwable}. Otherwise, throw new {@link
	 * IllegalArgumentException}.
	 *
	 * @param object    to be returned.
	 * @param condition to return the given {@code object} if it is true, Or throw if it is false.
	 * @param exception the class of the exception to be thrown if the given {@code condition} is false.
	 * @param <T>       the type of the object.
	 * @param <E>       the type of the exception.
	 * @return the given {@code object}.
	 * @throws E                        if the given {@code condition} is false and the given {@code exception} is not null and
	 *                                  extends {@link Throwable}.
	 * @throws IllegalArgumentException if the given {@code condition} is false and the given {@code exception} is null or does
	 *                                  not extend {@link Throwable}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T, E extends Throwable> T require(T object, boolean condition, Class<E> exception) throws E {
		if (condition)
			return object;

		if (exception != null && Throwable.class.isAssignableFrom(exception))
			try {
				throw exception.getConstructor().newInstance();
			} catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException ignored) {
			}

		throw new IllegalArgumentException();
	}

	/**
	 * Return the given {@code object} if the given {@code condition} returned true. Otherwise, throw a new instance of the given
	 * {@code exception} class, if the given {@code exception} is not null and does extend {@link Throwable}. Otherwise, throw new
	 * {@link IllegalArgumentException}.
	 *
	 * @param object    to be returned.
	 * @param condition to return the given {@code object} if it returned true, Or throw if it returned false.
	 * @param exception the class of the exception to be thrown if the given {@code condition} is null or returned false.
	 * @param <T>       the type of the object.
	 * @param <E>       the type of the exception.
	 * @return the given {@code object}.
	 * @throws E                        if the given {@code condition} is null or returned false and the given {@code exception}
	 *                                  is not null and extends {@link Throwable}.
	 * @throws IllegalArgumentException if the given {@code condition} is null or returned false and the given {@code exception}
	 *                                  is null or does not extend {@link Throwable}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T, E extends Throwable> T require(T object, Predicate<T> condition, Class<E> exception) throws E {
		if (condition != null && condition.test(object))
			return object;

		if (exception != null && Throwable.class.isAssignableFrom(exception))
			try {
				throw exception.getConstructor().newInstance();
			} catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException ignored) {
			}

		throw new IllegalArgumentException();
	}

	/**
	 * Return the given {@code object} if the given {@code condition} returned true. Otherwise, throw a new instance of the given
	 * {@code exception} class, if the given {@code exception} is not null and does extend {@link Throwable}. Otherwise, throw new
	 * {@link IllegalArgumentException}.
	 *
	 * @param object    to be returned.
	 * @param other     a second parameter to be passed to the given {@code condition}.
	 * @param condition to return the given {@code object} if it returned true, Or throw if it returned false.
	 * @param exception the class of the exception to be thrown if the given {@code condition} is null or returned false.
	 * @param <T>       the type of the object.
	 * @param <U>       the type of the other object.
	 * @param <E>       the type of the exception.
	 * @return the given {@code object}.
	 * @throws E                        if the given {@code condition} is null or returned false and the given {@code exception}
	 *                                  is not null and extends {@link Throwable}.
	 * @throws IllegalArgumentException if the given {@code condition} is null or returned false and the given {@code exception}
	 *                                  is null or does not extend {@link Throwable}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T, U, E extends Throwable> T require(T object, U other, BiPredicate<T, U> condition, Class<E> exception) throws E {
		if (condition != null && condition.test(object, other))
			return object;

		if (exception != null && Throwable.class.isAssignableFrom(exception))
			try {
				throw exception.getConstructor().newInstance();
			} catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException ignored) {
			}

		throw new IllegalArgumentException();
	}

	/**
	 * Return the given {@code object} if the given {@code condition} is true. Otherwise, throw a new instance of the given {@code
	 * exception} class with the given {@code message}, if the given {@code exception} is not null and does extend {@link
	 * Throwable}. Otherwise, throw new {@link IllegalArgumentException} with the given {@code message}.
	 * <p>
	 * If the given {@code message} is null or if the given {@code exception} dose not have a single string parameter constructor
	 * but has a no-args constructor, then the exception will have no message if it got thrown.
	 *
	 * @param object    to be returned.
	 * @param condition to return the given {@code object} if it is true, Or throw if it is false.
	 * @param exception the class of the exception to be thrown if the given {@code condition} is false.
	 * @param message   the message of the exception if it got thrown.
	 * @param <T>       the type of the object.
	 * @param <E>       the type of the exception.
	 * @return the given {@code object}.
	 * @throws E                        if the given {@code condition} is false and the given {@code exception} is not null and
	 *                                  extends {@link Throwable}.
	 * @throws IllegalArgumentException if the given {@code condition} is false and the given {@code exception} is null or does
	 *                                  not extend {@link Throwable}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T, E extends Throwable> T require(T object, boolean condition, Class<E> exception, String message) throws E {
		if (condition)
			return object;

		if (exception != null && Throwable.class.isAssignableFrom(exception)) {
			if (message != null)
				try {
					throw exception.getConstructor(String.class).newInstance(message);
				} catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException ignored) {
				}

			try {
				throw exception.getConstructor().newInstance();
			} catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ignored) {
			}
		}

		throw message == null ?
			  new IllegalArgumentException() :
			  new IllegalArgumentException(message);
	}

	/**
	 * Return the given {@code object} if the given {@code condition} returned true. Otherwise, throw a new instance of the given
	 * {@code exception} class with the given {@code message}, if the given {@code exception} is not null and does extend {@link
	 * Throwable}. Otherwise, throw new {@link IllegalArgumentException} with the given {@code message}.
	 * <p>
	 * If the given {@code message} is null or if the given {@code exception} dose not have a single string parameter constructor
	 * but has a no-args constructor, then the exception will have no message if it got thrown.
	 *
	 * @param object    to be returned.
	 * @param condition to return the given {@code object} if it returned true, Or throw if it returned false.
	 * @param exception the class of the exception to be thrown if the given {@code condition} is null or returned false.
	 * @param message   the message of the exception if it got thrown.
	 * @param <T>       the type of the object.
	 * @param <E>       the type of the exception.
	 * @return the given {@code object}.
	 * @throws E                        if the given {@code condition} is null or returned false and the given {@code exception}
	 *                                  is not null and extends {@link Throwable}.
	 * @throws IllegalArgumentException if the given {@code condition} is null or returned false and the given {@code exception}
	 *                                  is null or does not extend {@link Throwable}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T, E extends Throwable> T require(T object, Predicate<T> condition, Class<E> exception, String message) throws E {
		if (condition != null && condition.test(object))
			return object;

		if (exception != null && Throwable.class.isAssignableFrom(exception)) {
			if (message != null)
				try {
					throw exception.getConstructor(String.class).newInstance(message);
				} catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException ignored) {
				}

			try {
				throw exception.getConstructor().newInstance();
			} catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ignored) {
			}
		}

		throw message == null ?
			  new IllegalArgumentException() :
			  new IllegalArgumentException(message);
	}

	/**
	 * Return the given {@code object} if the given {@code condition} returned true. Otherwise, throw a new instance of the given
	 * {@code exception} class with the given {@code message}, if the given {@code exception} is not null and does extend {@link
	 * Throwable}. Otherwise, throw new {@link IllegalArgumentException} with the given {@code message}.
	 * <p>
	 * If the given {@code message} is null or if the given {@code exception} dose not have a single string parameter constructor
	 * but has a no-args constructor, then the exception will have no message if it got thrown.
	 *
	 * @param object    to be returned.
	 * @param other     a second parameter to be passed to the given {@code condition}.
	 * @param condition to return the given {@code object} if it returned true, Or throw if it returned false.
	 * @param exception the class of the exception to be thrown if the given {@code condition} is null or returned false.
	 * @param message   the message of the exception if it got thrown.
	 * @param <T>       the type of the object.
	 * @param <U>       the type of the other object.
	 * @param <E>       the type of the exception.
	 * @return the given {@code object}.
	 * @throws E                        if the given {@code condition} is null or returned false and the given {@code exception}
	 *                                  is not null and extends {@link Throwable}.
	 * @throws IllegalArgumentException if the given {@code condition} is null or returned false and the given {@code exception}
	 *                                  is null or does not extend {@link Throwable}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T, U, E extends Throwable> T require(T object, U other, BiPredicate<T, U> condition, Class<E> exception, String message) throws E {
		if (condition != null && condition.test(object, other))
			return object;

		if (exception != null && Throwable.class.isAssignableFrom(exception)) {
			if (message != null)
				try {
					throw exception.getConstructor(String.class).newInstance(message);
				} catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException ignored) {
				}

			try {
				throw exception.getConstructor().newInstance();
			} catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ignored) {
			}
		}

		throw message == null ?
			  new IllegalArgumentException() :
			  new IllegalArgumentException(message);
	}

	/**
	 * Return the given {@code object} if the given {@code condition} is true. Otherwise, throw the result of the given {@code
	 * exception} if it is not null and it returned an instance of {@link Throwable}. Otherwise, throw a new {@link
	 * IllegalArgumentException}.
	 *
	 * @param object    to be returned.
	 * @param condition to return the given {@code object} if it is true, Or throw if it is false.
	 * @param exception the supplier to get the exception to be thrown if the given {@code condition} is false.
	 * @param <T>       the type of the object.
	 * @param <E>       the type of the exception.
	 * @return the given {@code object}.
	 * @throws E                        if the given {@code condition} is false and the given {@code exception} is not null and
	 *                                  returned an instance of {@link Throwable}.
	 * @throws IllegalArgumentException if the given {@code condition} is false and the given {@code exception} is null or not
	 *                                  returned an instance of {@link Throwable}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T, E extends Throwable> T require(T object, boolean condition, Function<T, E> exception) throws E {
		if (condition)
			return object;

		if (exception != null) {
			Object e = exception.apply(object);

			if (e instanceof Throwable)
				throw (E) e;
		}

		throw new IllegalArgumentException();
	}

	/**
	 * Return the given {@code object} if the given {@code condition} returned true. Otherwise, throw the result of the given
	 * {@code exception} if it is not null and it returned an instance of {@link Throwable}. Otherwise, throw a new {@link
	 * IllegalArgumentException}.
	 *
	 * @param object    to be returned.
	 * @param condition to return the given {@code object} if it returned true, Or throw if it returned false.
	 * @param exception the supplier to get the exception to be thrown if the given {@code condition} is false.
	 * @param <T>       the type of the object.
	 * @param <E>       the type of the exception.
	 * @return the given {@code object}.
	 * @throws E                        if the given {@code condition} returned false and the given {@code exception} is not null
	 *                                  and returned an instance of {@link Throwable}.
	 * @throws IllegalArgumentException if the given {@code condition} returned false and the given {@code exception} is null or
	 *                                  not returned an instance of {@link Throwable}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T, E extends Throwable> T require(T object, Predicate<T> condition, Function<T, E> exception) throws E {
		if (condition != null && condition.test(object))
			return object;

		if (exception != null) {
			Object e = exception.apply(object);

			if (e instanceof Throwable)
				throw (E) e;
		}

		throw new IllegalArgumentException();
	}

	/**
	 * Return the given {@code object} if the given {@code condition} returned true. Otherwise, throw the result of the given
	 * {@code exception} if it is not null and it returned an instance of {@link Throwable}. Otherwise, throw a new {@link
	 * IllegalArgumentException}.
	 *
	 * @param object    to be returned.
	 * @param other     a second parameter to be passed to the given {@code condition}.
	 * @param condition to return the given {@code object} if it returned true, Or throw if it returned false.
	 * @param exception the supplier to get the exception to be thrown if the given {@code condition} is false.
	 * @param <T>       the type of the object.
	 * @param <U>       the type of the other object.
	 * @param <E>       the type of the exception.
	 * @return the given {@code object}.
	 * @throws E                        if the given {@code condition} returned false and the given {@code exception} is not null
	 *                                  and returned an instance of {@link Throwable}.
	 * @throws IllegalArgumentException if the given {@code condition} returned false and the given {@code exception} is null or
	 *                                  not returned an instance of {@link Throwable}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T, U, E extends Throwable> T require(T object, U other, BiPredicate<T, U> condition, BiFunction<T, U, E> exception) throws E {
		if (condition != null && condition.test(object, other))
			return object;

		if (exception != null) {
			Object e = exception.apply(object, other);

			if (e instanceof Throwable)
				throw (E) e;
		}

		throw new IllegalArgumentException();
	}

	/**
	 * Return the given {@code object} if it is not null. Otherwise, throw a new {@link NullPointerException}.
	 *
	 * @param object to be returned.
	 * @param <T>    the type of the object.
	 * @return the given {@code object}.
	 * @throws NullPointerException if the given {@code object} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> T requireNonNull(T object) {
		if (object != null)
			return object;

		throw new NullPointerException();
	}

	/**
	 * Return the given {@code object} if it is not null. Otherwise, throw a new {@link NullPointerException} with the given
	 * {@code message}.
	 * <p>
	 * If the given {@code message} is null, then the exception will have no message if it got thrown.
	 *
	 * @param object  to be returned.
	 * @param message the message of the exception if it got thrown.
	 * @param <T>     the type of the object.
	 * @return the given {@code object}.
	 * @throws NullPointerException if the given {@code object} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> T requireNonNull(T object, String message) {
		if (object != null)
			return object;

		throw new NullPointerException(message);
	}

	/**
	 * Return the given {@code object} if it is not null. Otherwise, throw a new {@link NullPointerException} with the returned
	 * string from the given {@code message}.
	 * <p>
	 * If the given {@code message} is null or did not returned an instance of {@link String}, then the exception will have no
	 * message if it got thrown.
	 *
	 * @param object  to be returned.
	 * @param message the supplier of the message of the exception if it got thrown.
	 * @param <T>     the type of the object.
	 * @return the given {@code object}.
	 * @throws NullPointerException if the given {@code object} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> T requireNonNull(T object, Supplier<String> message) {
		if (object != null)
			return object;

		if (message != null) {
			String s = message.get();

			if (s instanceof String)
				throw new NullPointerException(s);
		}

		throw new NullPointerException();
	}

	/**
	 * Return the string representation of the given {@code object}. Or {@code "null"} if the given {@code object} is null.
	 *
	 * @param object the object to get the string representation of it.
	 * @return the string string representation of the given {@code object}, Or {@code "null"} if the given {@code object} is
	 * 		null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString(Object object) {
		return String.valueOf(object);
	}

	/**
	 * Return the string representation of the given {@code object}. Or the given {@code nullDefault} if the given {@code object}
	 * is null.
	 *
	 * @param object      the object to get the string representation of it.
	 * @param nullDefault the string to be returned if the given {@code object} is null.
	 * @return the string string representation of the given {@code object}, Or the given {@code nullDefault} if the given {@code
	 * 		object} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString(Object object, String nullDefault) {
		return object == null ? nullDefault : object.toString();
	}
}
