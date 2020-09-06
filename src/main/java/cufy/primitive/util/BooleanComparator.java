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
package cufy.primitive.util;

import cufy.primitive.util.function.BooleanToDoubleFunction;
import cufy.primitive.util.function.BooleanToIntFunction;
import cufy.primitive.util.function.BooleanToLongFunction;
import cufy.primitive.util.function.BooleanUnaryOperator;
import cufy.util.PrimitiveComparator;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * A comparator specialized for {@code boolean} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
@SuppressWarnings({"InterfaceMayBeAnnotatedFunctional", "ComparatorNotSerializable"})
public interface BooleanComparator extends PrimitiveComparator<
		Boolean,
		BooleanUnaryOperator,
		BooleanToDoubleFunction,
		BooleanToIntFunction,
		BooleanToLongFunction,
		BooleanComparator
		> {
	/**
	 * A global instance of {@link NaturalOrder}.
	 *
	 * @since 0.1.5 ~2020.09.01
	 */
	BooleanComparator NATURAL = new NaturalOrder();
	/**
	 * A global instance of {@link ReverseOrder}.
	 *
	 * @since 0.1.5 ~2020.09.01
	 */
	BooleanComparator REVERSE = new ReverseOrder();

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
	static BooleanComparator comparing(BooleanUnaryOperator operator) {
		Objects.requireNonNull(operator, "operator");
		return (BooleanComparator & Serializable) (v, o) -> Boolean.compare(
				operator.applyAsBoolean(v),
				operator.applyAsBoolean(o)
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
	static BooleanComparator comparing(BooleanUnaryOperator operator, BooleanComparator comparator) {
		Objects.requireNonNull(operator, "operator");
		Objects.requireNonNull(comparator, "comparator");
		return (BooleanComparator & Serializable) (v, o) -> comparator.compare(
				operator.applyAsBoolean(v),
				operator.applyAsBoolean(o)
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
	static BooleanComparator comparingDouble(BooleanToDoubleFunction function) {
		Objects.requireNonNull(function, "function");
		return (BooleanComparator & Serializable) (v, o) -> Double.compare(
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
	static BooleanComparator comparingInt(BooleanToIntFunction function) {
		Objects.requireNonNull(function, "function");
		return (BooleanComparator & Serializable) (v, o) -> Long.compare(
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
	static BooleanComparator comparingLong(BooleanToLongFunction function) {
		Objects.requireNonNull(function, "function");
		return (BooleanComparator & Serializable) (v, o) -> Long.compare(
				function.applyAsLong(v),
				function.applyAsLong(o)
		);
	}

	/**
	 * Returns a comparator that compares {@code boolean}s in natural order.
	 * <p>
	 * The returned comparator is serializable.
	 *
	 * @return a comparator that imposes the <i>natural ordering</i> on {@code boolean}s.
	 * @since 0.1.5 ~2020.09.01
	 */
	static BooleanComparator naturalOrder() {
		return BooleanComparator.NATURAL;
	}

	/**
	 * Returns a comparator that imposes the reverse of the <em>natural ordering</em>.
	 * <p>
	 * The returned comparator is serializable..
	 *
	 * @return a comparator that imposes the reverse of the <i>natural ordering</i> on {@code
	 * 		boolean}s.
	 * @since 0.1.5 ~2020.09.01
	 */
	static BooleanComparator reverseOrder() {
		return BooleanComparator.REVERSE;
	}

	@Override
	default int compare(Boolean value, Boolean other) {
		return this.compare((boolean) value, (boolean) other);
	}

	@Override
	default BooleanComparator reversed() {
		return new Reverse(this);
	}

	@Override
	default BooleanComparator thenComparing(BooleanComparator other) {
		Objects.requireNonNull(other, "other");
		return (BooleanComparator & Serializable) (v, o) -> {
			int r = this.compare(v, o);
			return r == 0 ? other.compare(v, o) : r;
		};
	}

	@Override
	default BooleanComparator thenComparing(Comparator<? super Boolean> other) {
		Objects.requireNonNull(other, "other");
		return this.thenComparing(
				other instanceof BooleanComparator ?
				(BooleanComparator) other :
				other::compare
		);
	}

	@Override
	default <R extends Comparable<? super R>> BooleanComparator thenComparing(Function<? super Boolean, ? extends R> function) {
		//noinspection RedundantComparatorComparing
		return function instanceof BooleanUnaryOperator ?
			   this.thenComparing((BooleanUnaryOperator) function) :
			   this.thenComparing(Comparator.comparing(function));
	}

	@Override
	default BooleanComparator thenComparing(BooleanUnaryOperator operator) {
		return this.thenComparing(BooleanComparator.comparing(
				operator
		));
	}

	@Override
	default <R> BooleanComparator thenComparing(Function<? super Boolean, ? extends R> function, Comparator<? super R> comparator) {
		//noinspection RedundantComparatorComparing
		return function instanceof BooleanUnaryOperator && comparator instanceof BooleanComparator ?
			   this.thenComparing((BooleanUnaryOperator) function, (BooleanComparator) comparator) :
			   this.thenComparing(Comparator.comparing(function, comparator));
	}

	@Override
	default BooleanComparator thenComparing(BooleanUnaryOperator operator, BooleanComparator comparator) {
		return this.thenComparing(BooleanComparator.comparing(
				operator,
				comparator
		));
	}

	@Override
	default BooleanComparator thenComparingDouble(ToDoubleFunction<? super Boolean> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingDouble(
				function instanceof BooleanToDoubleFunction ?
				(BooleanToDoubleFunction) function :
				function::applyAsDouble
		);
	}

	@Override
	default BooleanComparator thenComparingDouble(BooleanToDoubleFunction function) {
		return this.thenComparing(BooleanComparator.comparingDouble(
				function
		));
	}

	@Override
	default BooleanComparator thenComparingInt(ToIntFunction<? super Boolean> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingInt(
				function instanceof BooleanToIntFunction ?
				(BooleanToIntFunction) function :
				function::applyAsInt
		);
	}

	@Override
	default BooleanComparator thenComparingInt(BooleanToIntFunction function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparing(BooleanComparator.comparingInt(
				function
		));
	}

	@Override
	default BooleanComparator thenComparingLong(ToLongFunction<? super Boolean> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingLong(
				function instanceof BooleanToLongFunction ?
				(BooleanToLongFunction) function :
				function::applyAsLong
		);
	}

	@Override
	default BooleanComparator thenComparingLong(BooleanToLongFunction function) {
		return this.thenComparing(BooleanComparator.comparingLong(
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
	int compare(boolean value, boolean other);

	/**
	 * An {@link BooleanComparator} that follows the natural ordering.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	class NaturalOrder implements BooleanComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -8066895119922404900L;

		@Override
		public int compare(boolean value, boolean other) {
			return Boolean.compare(value, other);
		}

		@Override
		public BooleanComparator reversed() {
			return BooleanComparator.REVERSE;
		}
	}

	/**
	 * A reversed {@link BooleanComparator} based on another {@link BooleanComparator}.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	class Reverse implements BooleanComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -5427742021938589022L;

		/**
		 * The comparator backing this comparator. The comparator will be reversed when used.
		 *
		 * @since 0.1.5 ~2020.09.01
		 */
		protected final BooleanComparator comparator;

		/**
		 * Construct a new reversed comparator reversing the given {@code comparator}.
		 *
		 * @param comparator the comparator the constructed comparator is backed by the reverse of
		 *                   it.
		 * @throws NullPointerException if the given {@code comparator} is null.
		 * @since 0.1.5 ~2020.09.01
		 */
		public Reverse(BooleanComparator comparator) {
			Objects.requireNonNull(comparator, "comparator");
			this.comparator = comparator;
		}

		@Override
		public int compare(boolean value, boolean other) {
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
		public BooleanComparator reversed() {
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
	class ReverseOrder implements BooleanComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 2243904103573461375L;

		@Override
		public int compare(boolean value, boolean other) {
			return Boolean.compare(other, value);
		}

		@Override
		public BooleanComparator reversed() {
			return BooleanComparator.NATURAL;
		}
	}
}
