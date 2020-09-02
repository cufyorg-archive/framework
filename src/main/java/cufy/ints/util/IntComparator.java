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
package cufy.ints.util;

import cufy.util.PrimitiveComparator;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.*;

/**
 * A comparator specialized for {@code int} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
@SuppressWarnings({"InterfaceMayBeAnnotatedFunctional", "ComparatorNotSerializable"})
public interface IntComparator extends PrimitiveComparator<
		Integer,
		IntUnaryOperator,
		IntToDoubleFunction,
		IntUnaryOperator,
		IntToLongFunction,
		IntComparator
		> {
	/**
	 * A global instance of {@link NaturalOrder}.
	 *
	 * @since 0.1.5 ~2020.09.01
	 */
	IntComparator NATURAL = new NaturalOrder();
	/**
	 * A global instance of {@link ReverseOrder}.
	 *
	 * @since 0.1.5 ~2020.09.01
	 */
	IntComparator REVERSE = new ReverseOrder();

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
	static IntComparator comparing(IntUnaryOperator operator) {
		Objects.requireNonNull(operator, "operator");
		return (IntComparator & Serializable) (v, o) -> Integer.compare(
				operator.applyAsInt(v),
				operator.applyAsInt(o)
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
	static IntComparator comparing(IntUnaryOperator operator, IntComparator comparator) {
		Objects.requireNonNull(operator, "operator");
		Objects.requireNonNull(comparator, "comparator");
		return (IntComparator & Serializable) (v, o) -> comparator.compare(
				operator.applyAsInt(v),
				operator.applyAsInt(o)
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
	static IntComparator comparingDouble(IntToDoubleFunction function) {
		Objects.requireNonNull(function, "function");
		return (IntComparator & Serializable) (v, o) -> Double.compare(
				function.applyAsDouble(v),
				function.applyAsDouble(o)
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
	static IntComparator comparingLong(IntToLongFunction function) {
		Objects.requireNonNull(function, "function");
		return (IntComparator & Serializable) (v, o) -> Long.compare(
				function.applyAsLong(v),
				function.applyAsLong(o)
		);
	}

	/**
	 * Returns a comparator that compares {@code int}s in natural order.
	 * <p>
	 * The returned comparator is serializable.
	 *
	 * @return a comparator that imposes the <i>natural ordering</i> on {@code int}s.
	 * @since 0.1.5 ~2020.09.01
	 */
	static IntComparator naturalOrder() {
		return IntComparator.NATURAL;
	}

	/**
	 * Returns a comparator that imposes the reverse of the <em>natural ordering</em>.
	 * <p>
	 * The returned comparator is serializable..
	 *
	 * @return a comparator that imposes the reverse of the <i>natural ordering</i> on {@code int}s.
	 * @since 0.1.5 ~2020.09.01
	 */
	static IntComparator reverseOrder() {
		return IntComparator.REVERSE;
	}

	@Override
	default int compare(Integer value, Integer other) {
		return this.compare((int) value, (int) other);
	}

	@Override
	default IntComparator reversed() {
		return new Reverse(this);
	}

	@Override
	default IntComparator thenComparing(Comparator<? super Integer> other) {
		Objects.requireNonNull(other, "other");
		return this.thenComparing(
				other instanceof IntComparator ?
				(IntComparator) other :
				other::compare
		);
	}

	@Override
	default IntComparator thenComparing(IntComparator other) {
		Objects.requireNonNull(other, "other");
		return (IntComparator & Serializable) (v, o) -> {
			int r = this.compare(v, o);
			return r == 0 ? other.compare(v, o) : r;
		};
	}

	@Override
	default <R extends Comparable<? super R>> IntComparator thenComparing(Function<? super Integer, ? extends R> function) {
		//noinspection RedundantComparatorComparing
		return function instanceof IntUnaryOperator ?
			   this.thenComparing((IntUnaryOperator) function) :
			   this.thenComparing(Comparator.comparing(function));
	}

	@Override
	default IntComparator thenComparing(IntUnaryOperator operator) {
		return this.thenComparing(IntComparator.comparing(
				operator
		));
	}

	@Override
	default <R> IntComparator thenComparing(Function<? super Integer, ? extends R> function, Comparator<? super R> comparator) {
		//noinspection RedundantComparatorComparing
		return function instanceof IntUnaryOperator && comparator instanceof IntComparator ?
			   this.thenComparing((IntUnaryOperator) function, (IntComparator) comparator) :
			   this.thenComparing(Comparator.comparing(function, comparator));
	}

	@Override
	default IntComparator thenComparing(IntUnaryOperator operator, IntComparator comparator) {
		return this.thenComparing(IntComparator.comparing(
				operator,
				comparator
		));
	}

	@Override
	default IntComparator thenComparingDouble(ToDoubleFunction<? super Integer> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingDouble(
				function instanceof IntToDoubleFunction ?
				(IntToDoubleFunction) function :
				function::applyAsDouble
		);
	}

	@Override
	default IntComparator thenComparingDouble(IntToDoubleFunction function) {
		return this.thenComparing(IntComparator.comparingDouble(
				function
		));
	}

	@Override
	default IntComparator thenComparingInt(ToIntFunction<? super Integer> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparing(
				function instanceof IntUnaryOperator ?
				(IntUnaryOperator) function :
				function::applyAsInt
		);
	}

	@Override
	default IntComparator thenComparingInt(IntUnaryOperator function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparing(IntComparator.comparing(
				function
		));
	}

	@Override
	default IntComparator thenComparingLong(ToLongFunction<? super Integer> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingLong(
				function instanceof IntToLongFunction ?
				(IntToLongFunction) function :
				function::applyAsLong
		);
	}

	@Override
	default IntComparator thenComparingLong(IntToLongFunction function) {
		return this.thenComparing(IntComparator.comparingLong(
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
	int compare(int value, int other);

	/**
	 * An {@link IntComparator} that follows the natural ordering.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	class NaturalOrder implements IntComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -8066895119922404900L;

		@Override
		public int compare(int value, int other) {
			return Integer.compare(value, other);
		}

		@Override
		public IntComparator reversed() {
			return IntComparator.REVERSE;
		}
	}

	/**
	 * A reversed {@link IntComparator} based on another {@link IntComparator}.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	class Reverse implements IntComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -5427742021938589022L;

		/**
		 * The comparator backing this comparator. The comparator will be reversed when used.
		 *
		 * @since 0.1.5 ~2020.09.01
		 */
		protected final IntComparator comparator;

		/**
		 * Construct a new reversed comparator reversing the given {@code comparator}.
		 *
		 * @param comparator the comparator the constructed comparator is backed by the reverse of
		 *                   it.
		 * @throws NullPointerException if the given {@code comparator} is null.
		 * @since 0.1.5 ~2020.09.01
		 */
		public Reverse(IntComparator comparator) {
			Objects.requireNonNull(comparator, "comparator");
			this.comparator = comparator;
		}

		@Override
		public int compare(int value, int other) {
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
		public IntComparator reversed() {
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
	class ReverseOrder implements IntComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 2243904103573461375L;

		@Override
		public int compare(int value, int other) {
			return Integer.compare(other, value);
		}

		@Override
		public IntComparator reversed() {
			return IntComparator.NATURAL;
		}
	}
}
