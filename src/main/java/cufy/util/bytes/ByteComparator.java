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
package cufy.util.bytes;

import cufy.util.PrimitiveComparator;
import cufy.util.bytes.function.ByteToDoubleFunction;
import cufy.util.bytes.function.ByteToIntFunction;
import cufy.util.bytes.function.ByteToLongFunction;
import cufy.util.bytes.function.ByteUnaryOperator;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * A comparator specialized for {@code byte} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
@SuppressWarnings({"InterfaceMayBeAnnotatedFunctional", "ComparatorNotSerializable"})
public interface ByteComparator extends PrimitiveComparator<
		Byte,
		ByteUnaryOperator,
		ByteToDoubleFunction,
		ByteToIntFunction,
		ByteToLongFunction,
		ByteComparator
		> {
	/**
	 * A global instance of {@link NaturalOrder}.
	 *
	 * @since 0.1.5 ~2020.09.01
	 */
	ByteComparator NATURAL = new NaturalOrder();
	/**
	 * A global instance of {@link ReverseOrder}.
	 *
	 * @since 0.1.5 ~2020.09.01
	 */
	ByteComparator REVERSE = new ReverseOrder();

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
	static ByteComparator comparing(ByteUnaryOperator operator) {
		Objects.requireNonNull(operator, "operator");
		return (ByteComparator & Serializable) (v, o) -> Byte.compare(
				operator.applyAsByte(v),
				operator.applyAsByte(o)
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
	static ByteComparator comparing(ByteUnaryOperator operator, ByteComparator comparator) {
		Objects.requireNonNull(operator, "operator");
		Objects.requireNonNull(comparator, "comparator");
		return (ByteComparator & Serializable) (v, o) -> comparator.compare(
				operator.applyAsByte(v),
				operator.applyAsByte(o)
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
	static ByteComparator comparingDouble(ByteToDoubleFunction function) {
		Objects.requireNonNull(function, "function");
		return (ByteComparator & Serializable) (v, o) -> Double.compare(
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
	static ByteComparator comparingInt(ByteToIntFunction function) {
		Objects.requireNonNull(function, "function");
		return (ByteComparator & Serializable) (v, o) -> Long.compare(
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
	static ByteComparator comparingLong(ByteToLongFunction function) {
		Objects.requireNonNull(function, "function");
		return (ByteComparator & Serializable) (v, o) -> Long.compare(
				function.applyAsLong(v),
				function.applyAsLong(o)
		);
	}

	/**
	 * Returns a comparator that compares {@code byte}s in natural order.
	 * <p>
	 * The returned comparator is serializable.
	 *
	 * @return a comparator that imposes the <i>natural ordering</i> on {@code byte}s.
	 * @since 0.1.5 ~2020.09.01
	 */
	static ByteComparator naturalOrder() {
		return ByteComparator.NATURAL;
	}

	/**
	 * Returns a comparator that imposes the reverse of the <em>natural ordering</em>.
	 * <p>
	 * The returned comparator is serializable..
	 *
	 * @return a comparator that imposes the reverse of the <i>natural ordering</i> on {@code
	 * 		byte}s.
	 * @since 0.1.5 ~2020.09.01
	 */
	static ByteComparator reverseOrder() {
		return ByteComparator.REVERSE;
	}

	@Override
	default int compare(Byte value, Byte other) {
		return this.compare((byte) value, (byte) other);
	}

	@Override
	default ByteComparator reversed() {
		return new Reverse(this);
	}

	@Override
	default ByteComparator thenComparing(ByteComparator other) {
		Objects.requireNonNull(other, "other");
		return (ByteComparator & Serializable) (v, o) -> {
			int r = this.compare(v, o);
			return r == 0 ? other.compare(v, o) : r;
		};
	}

	@Override
	default ByteComparator thenComparing(Comparator<? super Byte> other) {
		Objects.requireNonNull(other, "other");
		return this.thenComparing(
				other instanceof ByteComparator ?
				(ByteComparator) other :
				other::compare
		);
	}

	@Override
	default <R extends Comparable<? super R>> ByteComparator thenComparing(Function<? super Byte, ? extends R> function) {
		//noinspection RedundantComparatorComparing
		return function instanceof ByteUnaryOperator ?
			   this.thenComparing((ByteUnaryOperator) function) :
			   this.thenComparing(Comparator.comparing(function));
	}

	@Override
	default ByteComparator thenComparing(ByteUnaryOperator operator) {
		return this.thenComparing(ByteComparator.comparing(
				operator
		));
	}

	@Override
	default <R> ByteComparator thenComparing(Function<? super Byte, ? extends R> function, Comparator<? super R> comparator) {
		//noinspection RedundantComparatorComparing
		return function instanceof ByteUnaryOperator && comparator instanceof ByteComparator ?
			   this.thenComparing((ByteUnaryOperator) function, (ByteComparator) comparator) :
			   this.thenComparing(Comparator.comparing(function, comparator));
	}

	@Override
	default ByteComparator thenComparing(ByteUnaryOperator operator, ByteComparator comparator) {
		return this.thenComparing(ByteComparator.comparing(
				operator,
				comparator
		));
	}

	@Override
	default ByteComparator thenComparingDouble(ToDoubleFunction<? super Byte> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingDouble(
				function instanceof ByteToDoubleFunction ?
				(ByteToDoubleFunction) function :
				function::applyAsDouble
		);
	}

	@Override
	default ByteComparator thenComparingDouble(ByteToDoubleFunction function) {
		return this.thenComparing(ByteComparator.comparingDouble(
				function
		));
	}

	@Override
	default ByteComparator thenComparingInt(ToIntFunction<? super Byte> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingInt(
				function instanceof ByteToIntFunction ?
				(ByteToIntFunction) function :
				function::applyAsInt
		);
	}

	@Override
	default ByteComparator thenComparingInt(ByteToIntFunction function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparing(ByteComparator.comparingInt(
				function
		));
	}

	@Override
	default ByteComparator thenComparingLong(ToLongFunction<? super Byte> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingLong(
				function instanceof ByteToLongFunction ?
				(ByteToLongFunction) function :
				function::applyAsLong
		);
	}

	@Override
	default ByteComparator thenComparingLong(ByteToLongFunction function) {
		return this.thenComparing(ByteComparator.comparingLong(
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
	int compare(byte value, byte other);

	/**
	 * An {@link ByteComparator} that follows the natural ordering.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	class NaturalOrder implements ByteComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -8066895119922404900L;

		@Override
		public int compare(byte value, byte other) {
			return Byte.compare(value, other);
		}

		@Override
		public ByteComparator reversed() {
			return ByteComparator.REVERSE;
		}
	}

	/**
	 * A reversed {@link ByteComparator} based on another {@link ByteComparator}.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	class Reverse implements ByteComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -5427742021938589022L;

		/**
		 * The comparator backing this comparator. The comparator will be reversed when used.
		 *
		 * @since 0.1.5 ~2020.09.01
		 */
		protected final ByteComparator comparator;

		/**
		 * Construct a new reversed comparator reversing the given {@code comparator}.
		 *
		 * @param comparator the comparator the constructed comparator is backed by the reverse of
		 *                   it.
		 * @throws NullPointerException if the given {@code comparator} is null.
		 * @since 0.1.5 ~2020.09.01
		 */
		public Reverse(ByteComparator comparator) {
			Objects.requireNonNull(comparator, "comparator");
			this.comparator = comparator;
		}

		@Override
		public int compare(byte value, byte other) {
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
		public ByteComparator reversed() {
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
	class ReverseOrder implements ByteComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 2243904103573461375L;

		@Override
		public int compare(byte value, byte other) {
			return Byte.compare(other, value);
		}

		@Override
		public ByteComparator reversed() {
			return ByteComparator.NATURAL;
		}
	}
}
