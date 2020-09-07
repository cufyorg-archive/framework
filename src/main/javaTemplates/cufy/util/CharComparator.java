/*
with char|boolean|byte|double|float|int|long|short primitive
*//*
define ToDouble ////
if boolean|byte|char|float|int|long|short primitive //CharToDoubleFunction//
elif double primitive //DoubleUnaryOperator//
endif ////
enddefine
*//*
define ToInt ////
if boolean|byte|char|double|float|long|short primitive //CharToIntFunction//
elif int primitive //IntUnaryOperator//
endif ////
enddefine
*//*
define ToLong ////
if boolean|byte|char|double|float|int|short primitive //CharToLongFunction//
elif long primitive //LongUnaryOperator//
endif ////
enddefine
*//*
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

/* if double primitive */
import java.util.function.DoubleUnaryOperator;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
/* elif int primitive */
import java.util.function.IntToDoubleFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.IntToLongFunction;
/* elif long primitive */
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
/* elif boolean|byte|char|float|short primitive */
import cufy.util.function.CharToDoubleFunction;
import cufy.util.function.CharToIntFunction;
import cufy.util.function.CharToLongFunction;
import cufy.util.function.CharUnaryOperator;
/* endif */

import cufy.util.PrimitiveComparator;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * A comparator specialized for {@code char} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
@SuppressWarnings({"InterfaceMayBeAnnotatedFunctional", "ComparatorNotSerializable"})
public interface CharComparator extends PrimitiveComparator<
		Character,
		CharUnaryOperator,
		/*ToDouble*/,
		/*ToInt*/,
		/*ToLong*/,
		CharComparator
		> {
	/**
	 * A global instance of {@link NaturalOrder}.
	 *
	 * @since 0.1.5 ~2020.09.01
	 */
	CharComparator NATURAL = new NaturalOrder();
	/**
	 * A global instance of {@link ReverseOrder}.
	 *
	 * @since 0.1.5 ~2020.09.01
	 */
	CharComparator REVERSE = new ReverseOrder();

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
	static CharComparator comparing(CharUnaryOperator operator) {
		Objects.requireNonNull(operator, "operator");
		return (CharComparator & Serializable) (v, o) -> Character.compare(
				operator.applyAsChar(v),
				operator.applyAsChar(o)
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
	static CharComparator comparing(CharUnaryOperator operator, CharComparator comparator) {
		Objects.requireNonNull(operator, "operator");
		Objects.requireNonNull(comparator, "comparator");
		return (CharComparator & Serializable) (v, o) -> comparator.compare(
				operator.applyAsChar(v),
				operator.applyAsChar(o)
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
	static CharComparator comparingDouble(/*ToDouble*/ function) {
		Objects.requireNonNull(function, "function");
		return (CharComparator & Serializable) (v, o) -> Double.compare(
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
	static CharComparator comparingInt(/*ToInt*/ function) {
		Objects.requireNonNull(function, "function");
		return (CharComparator & Serializable) (v, o) -> Long.compare(
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
	static CharComparator comparingLong(/*ToLong*/ function) {
		Objects.requireNonNull(function, "function");
		return (CharComparator & Serializable) (v, o) -> Long.compare(
				function.applyAsLong(v),
				function.applyAsLong(o)
		);
	}

	/**
	 * Returns a comparator that compares {@code char}s in natural order.
	 * <p>
	 * The returned comparator is serializable.
	 *
	 * @return a comparator that imposes the <i>natural ordering</i> on {@code char}s.
	 * @since 0.1.5 ~2020.09.01
	 */
	static CharComparator naturalOrder() {
		return CharComparator.NATURAL;
	}

	/**
	 * Returns a comparator that imposes the reverse of the <em>natural ordering</em>.
	 * <p>
	 * The returned comparator is serializable..
	 *
	 * @return a comparator that imposes the reverse of the <i>natural ordering</i> on {@code
	 * 		char}s.
	 * @since 0.1.5 ~2020.09.01
	 */
	static CharComparator reverseOrder() {
		return CharComparator.REVERSE;
	}

	@Override
	default int compare(Character value, Character other) {
		return this.compare((char) value, (char) other);
	}

	@Override
	default CharComparator reversed() {
		return new Reverse(this);
	}

	@Override
	default CharComparator thenComparing(CharComparator other) {
		Objects.requireNonNull(other, "other");
		return (CharComparator & Serializable) (v, o) -> {
			int r = this.compare(v, o);
			return r == 0 ? other.compare(v, o) : r;
		};
	}

	@Override
	default CharComparator thenComparing(Comparator<? super Character> other) {
		Objects.requireNonNull(other, "other");
		return this.thenComparing(
				other instanceof CharComparator ?
				(CharComparator) other :
				other::compare
		);
	}

	@Override
	default <R extends Comparable<? super R>> CharComparator thenComparing(Function<? super Character, ? extends R> function) {
		//noinspection RedundantComparatorComparing
		return function instanceof CharUnaryOperator ?
			   this.thenComparing((CharUnaryOperator) function) :
			   this.thenComparing(Comparator.comparing(function));
	}

	@Override
	default CharComparator thenComparing(CharUnaryOperator operator) {
		return this.thenComparing(CharComparator.comparing(
				operator
		));
	}

	@Override
	default <R> CharComparator thenComparing(Function<? super Character, ? extends R> function, Comparator<? super R> comparator) {
		//noinspection RedundantComparatorComparing
		return function instanceof CharUnaryOperator && comparator instanceof CharComparator ?
			   this.thenComparing((CharUnaryOperator) function, (CharComparator) comparator) :
			   this.thenComparing(Comparator.comparing(function, comparator));
	}

	@Override
	default CharComparator thenComparing(CharUnaryOperator operator, CharComparator comparator) {
		return this.thenComparing(CharComparator.comparing(
				operator,
				comparator
		));
	}

	@Override
	default CharComparator thenComparingDouble(ToDoubleFunction<? super Character> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingDouble(
				function instanceof /*ToDouble*/ ?
		(/*ToDouble*/) function:
		function::applyAsDouble
		);
	}

	@Override
	default CharComparator thenComparingDouble(/*ToDouble*/ function) {
		return this.thenComparing(CharComparator.comparingDouble(
				function
		));
	}

	@Override
	default CharComparator thenComparingInt(ToIntFunction<? super Character> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingInt(
				function instanceof /*ToInt*/ ?
		(/*ToInt*/) function:
		function::applyAsInt
		);
	}

	@Override
	default CharComparator thenComparingInt(/*ToInt*/ function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparing(CharComparator.comparingInt(
				function
		));
	}

	@Override
	default CharComparator thenComparingLong(ToLongFunction<? super Character> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingLong(
				function instanceof /*ToLong*/ ?
		(/*ToLong*/) function:
		function::applyAsLong
		);
	}

	@Override
	default CharComparator thenComparingLong(/*ToLong*/ function) {
		return this.thenComparing(CharComparator.comparingLong(
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
	int compare(char value, char other);

	/**
	 * An {@link CharComparator} that follows the natural ordering.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	class NaturalOrder implements CharComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -8066895119922404900L;

		@Override
		public int compare(char value, char other) {
			return Character.compare(value, other);
		}

		@Override
		public CharComparator reversed() {
			return CharComparator.REVERSE;
		}
	}

	/**
	 * A reversed {@link CharComparator} based on another {@link CharComparator}.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	class Reverse implements CharComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -5427742021938589022L;

		/**
		 * The comparator backing this comparator. The comparator will be reversed when used.
		 *
		 * @since 0.1.5 ~2020.09.01
		 */
		protected final CharComparator comparator;

		/**
		 * Construct a new reversed comparator reversing the given {@code comparator}.
		 *
		 * @param comparator the comparator the constructed comparator is backed by the reverse of
		 *                   it.
		 * @throws NullPointerException if the given {@code comparator} is null.
		 * @since 0.1.5 ~2020.09.01
		 */
		public Reverse(CharComparator comparator) {
			Objects.requireNonNull(comparator, "comparator");
			this.comparator = comparator;
		}

		@Override
		public int compare(char value, char other) {
			return this.comparator.compare(other, value);
		}

		@Override
		public CharComparator reversed() {
			return this.comparator;
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
	}

	/**
	 * Reversed <i>natural ordering</i> comparator.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	class ReverseOrder implements CharComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 2243904103573461375L;

		@Override
		public int compare(char value, char other) {
			return Character.compare(other, value);
		}

		@Override
		public CharComparator reversed() {
			return CharComparator.NATURAL;
		}
	}
}
