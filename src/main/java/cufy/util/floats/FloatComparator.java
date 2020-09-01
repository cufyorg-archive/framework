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
package cufy.util.floats;

import cufy.util.PrimitiveComparator;
import cufy.util.floats.function.FloatToDoubleFunction;
import cufy.util.floats.function.FloatToIntFunction;
import cufy.util.floats.function.FloatToLongFunction;
import cufy.util.floats.function.FloatUnaryOperator;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * A comparator specialized for {@code float} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
@SuppressWarnings({"InterfaceMayBeAnnotatedFunctional", "ComparatorNotSerializable"})
public interface FloatComparator extends PrimitiveComparator<
		Float,
		FloatUnaryOperator,
		FloatToDoubleFunction,
		FloatToIntFunction,
		FloatToLongFunction,
		FloatComparator
		> {
	/**
	 * A global instance of {@link NaturalOrder}.
	 *
	 * @since 0.1.5 ~2020.09.01
	 */
	FloatComparator NATURAL = new NaturalOrder();
	/**
	 * A global instance of {@link ReverseOrder}.
	 *
	 * @since 0.1.5 ~2020.09.01
	 */
	FloatComparator REVERSE = new ReverseOrder();

	/**
	 * Accepts a function that extracts a key, and returns a {@code Comparator<T>} that compares by
	 * that key.
	 * <p>
	 * The returned comparator is serializable if the specified function is serializable.
	 *
	 * @param operator the function used to extract the key.
	 * @return a comparator that compares by an extracted key.
	 * @throws NullPointerException if the given {@code operator} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	static FloatComparator comparing(FloatUnaryOperator operator) {
		Objects.requireNonNull(operator, "operator");
		return (FloatComparator & Serializable) (v, o) -> Float.compare(
				operator.applyAsFloat(v),
				operator.applyAsFloat(o)
		);
	}

	/**
	 * Accepts a function that extracts a key and returns a {@code Comparator<T>} that compares by
	 * that key using the specified {@link Comparator}.
	 * <p>
	 * The returned comparator is serializable if the specified function and comparator are both
	 * serializable.
	 *
	 * @param operator   the function used to extract the key.
	 * @param comparator the {@code Comparator} used to compare the key.
	 * @return a comparator that compares by an extracted key using the specified {@code
	 * 		Comparator}.
	 * @throws NullPointerException if the given {@code operator} or {@code comparator} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	static FloatComparator comparing(FloatUnaryOperator operator, FloatComparator comparator) {
		Objects.requireNonNull(operator, "operator");
		Objects.requireNonNull(comparator, "comparator");
		return (FloatComparator & Serializable) (v, o) -> comparator.compare(
				operator.applyAsFloat(v),
				operator.applyAsFloat(o)
		);
	}

	/**
	 * Accepts a function that extracts a {@code double} key, and returns a {@code Comparator<T>}
	 * that compares by that key.
	 * <p>
	 * The returned comparator is serializable if the specified function is serializable.
	 *
	 * @param function the function used to extract the double key.
	 * @return a comparator that compares by an extracted key.
	 * @throws NullPointerException if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	static FloatComparator comparingDouble(FloatToDoubleFunction function) {
		Objects.requireNonNull(function, "function");
		return (FloatComparator & Serializable) (v, o) -> Double.compare(
				function.applyAsDouble(v),
				function.applyAsDouble(o)
		);
	}

	/**
	 * Accepts a function that extracts a {@code int} key, and returns a {@code Comparator<T>} that
	 * compares by that key.
	 * <p>
	 * The returned comparator is serializable if the specified function is serializable.
	 *
	 * @param function the function used to extract the int key.
	 * @return a comparator that compares by an extracted key.
	 * @throws NullPointerException if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	static FloatComparator comparingInt(FloatToIntFunction function) {
		Objects.requireNonNull(function, "function");
		return (FloatComparator & Serializable) (v, o) -> Long.compare(
				function.applyAsInt(v),
				function.applyAsInt(o)
		);
	}

	/**
	 * Accepts a function that extracts a {@code long} key, and returns a {@code Comparator<T>} that
	 * compares by that key.
	 * <p>
	 * The returned comparator is serializable if the specified function is serializable.
	 *
	 * @param function the function used to extract the long key.
	 * @return a comparator that compares by an extracted key.
	 * @throws NullPointerException if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	static FloatComparator comparingLong(FloatToLongFunction function) {
		Objects.requireNonNull(function, "function");
		return (FloatComparator & Serializable) (v, o) -> Long.compare(
				function.applyAsLong(v),
				function.applyAsLong(o)
		);
	}

	/**
	 * Returns a comparator that compares {@code float}s in natural order.
	 * <p>
	 * The returned comparator is serializable.
	 *
	 * @return a comparator that imposes the <i>natural ordering</i> on {@code float}s.
	 * @since 0.1.5 ~2020.09.01
	 */
	static FloatComparator naturalOrder() {
		return FloatComparator.NATURAL;
	}

	/**
	 * Returns a comparator that imposes the reverse of the <em>natural ordering</em>.
	 * <p>
	 * The returned comparator is serializable..
	 *
	 * @return a comparator that imposes the reverse of the <i>natural ordering</i> on {@code
	 * 		float}s.
	 * @since 0.1.5 ~2020.09.01
	 */
	static FloatComparator reverseOrder() {
		return FloatComparator.REVERSE;
	}

	@Override
	default int compare(Float value, Float other) {
		return this.compare((float) value, (float) other);
	}

	@Override
	default FloatComparator reversed() {
		return new Reverse(this);
	}

	@Override
	default FloatComparator thenComparing(FloatComparator other) {
		Objects.requireNonNull(other, "other");
		return (FloatComparator & Serializable) (v, o) -> {
			int r = this.compare(v, o);
			return r == 0 ? other.compare(v, o) : r;
		};
	}

	@Override
	default FloatComparator thenComparing(Comparator<? super Float> other) {
		Objects.requireNonNull(other, "other");
		return this.thenComparing(
				other instanceof FloatComparator ?
				(FloatComparator) other :
				other::compare
		);
	}

	@Override
	default <R extends Comparable<? super R>> FloatComparator thenComparing(Function<? super Float, ? extends R> function) {
		//noinspection RedundantComparatorComparing
		return function instanceof FloatUnaryOperator ?
			   this.thenComparing((FloatUnaryOperator) function) :
			   this.thenComparing(Comparator.comparing(function));
	}

	@Override
	default FloatComparator thenComparing(FloatUnaryOperator operator) {
		return this.thenComparing(FloatComparator.comparing(
				operator
		));
	}

	@Override
	default <R> FloatComparator thenComparing(Function<? super Float, ? extends R> function, Comparator<? super R> comparator) {
		//noinspection RedundantComparatorComparing
		return function instanceof FloatUnaryOperator && comparator instanceof FloatComparator ?
			   this.thenComparing((FloatUnaryOperator) function, (FloatComparator) comparator) :
			   this.thenComparing(Comparator.comparing(function, comparator));
	}

	@Override
	default FloatComparator thenComparing(FloatUnaryOperator operator, FloatComparator comparator) {
		return this.thenComparing(FloatComparator.comparing(
				operator,
				comparator
		));
	}

	@Override
	default FloatComparator thenComparingDouble(ToDoubleFunction<? super Float> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingDouble(
				function instanceof FloatToDoubleFunction ?
				(FloatToDoubleFunction) function :
				function::applyAsDouble
		);
	}

	@Override
	default FloatComparator thenComparingDouble(FloatToDoubleFunction function) {
		return this.thenComparing(FloatComparator.comparingDouble(
				function
		));
	}

	@Override
	default FloatComparator thenComparingInt(ToIntFunction<? super Float> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingInt(
				function instanceof FloatToIntFunction ?
				(FloatToIntFunction) function :
				function::applyAsInt
		);
	}

	@Override
	default FloatComparator thenComparingInt(FloatToIntFunction function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparing(FloatComparator.comparingInt(
				function
		));
	}

	@Override
	default FloatComparator thenComparingLong(ToLongFunction<? super Float> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingLong(
				function instanceof FloatToLongFunction ?
				(FloatToLongFunction) function :
				function::applyAsLong
		);
	}

	@Override
	default FloatComparator thenComparingLong(FloatToLongFunction function) {
		return this.thenComparing(FloatComparator.comparingLong(
				function
		));
	}

	/**
	 * Compares its two arguments for order.  Returns a negative integer, zero, or a positive
	 * integer as the first argument is less than, equal to, or greater than the second.
	 *
	 * @param value the first argument to be compared.
	 * @param other the second argument to be compared.
	 * @return a negative integer, zero, or a positive integer as the first argument is less than,
	 * 		equal to, or greater than the second.
	 * @since 0.1.5 ~2020.09.01
	 */
	int compare(float value, float other);

	/**
	 * An {@link FloatComparator} that follows the natural ordering.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	class NaturalOrder implements FloatComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -8066895119922404900L;

		@Override
		public int compare(float value, float other) {
			return Float.compare(value, other);
		}

		@Override
		public FloatComparator reversed() {
			return FloatComparator.REVERSE;
		}
	}

	/**
	 * A reversed {@link FloatComparator} based on another {@link FloatComparator}.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	class Reverse implements FloatComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -5427742021938589022L;

		/**
		 * The comparator backing this comparator. The comparator will be reversed when used.
		 *
		 * @since 0.1.5 ~2020.09.01
		 */
		protected final FloatComparator comparator;

		/**
		 * Construct a new reversed comparator reversing the given {@code comparator}.
		 *
		 * @param comparator the comparator the constructed comparator is backed by the reverse of
		 *                   it.
		 * @throws NullPointerException if the given {@code comparator} is null.
		 * @since 0.1.5 ~2020.09.01
		 */
		public Reverse(FloatComparator comparator) {
			Objects.requireNonNull(comparator, "comparator");
			this.comparator = comparator;
		}

		@Override
		public int compare(float value, float other) {
			return this.comparator.compare(other, value);
		}

		@Override
		public boolean equals(Object object) {
			return object == this ||
				   object instanceof Reverse &&
				   ((Reverse) object).comparator.equals(this.comparator);
		}

		@Override
		public int hashCode() {
			return this.comparator.hashCode() ^ Integer.MIN_VALUE;
		}

		@Override
		public FloatComparator reversed() {
			return this.comparator;
		}
	}

	/**
	 * Reversed <i>natural ordering</i> comparator.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	class ReverseOrder implements FloatComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 2243904103573461375L;

		@Override
		public int compare(float value, float other) {
			return Float.compare(other, value);
		}

		@Override
		public FloatComparator reversed() {
			return FloatComparator.NATURAL;
		}
	}
}
