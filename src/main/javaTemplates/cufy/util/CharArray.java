/*
with char|boolean|byte|double|float|int|long|short primitive
*//*
define DefaultValue ////
if boolean primitive //false//
elif byte|char|int|short primitive //0//
elif double primitive //0.0D//
elif float primitive //0.0F//
elif long primitive //0L//
endif ////
enddefine
*//*
define IntToFunction ////
if boolean|byte|char|double|float|long|short primitive //IntToCharFunction//
elif int primitive //IntUnaryOperator//
endif ////
enddefine
*//*
define ToDoubleFunction ////
if boolean|byte|char|float|int|long|short primitive //CharToDoubleFunction//
elif double primitive //DoubleUnaryOperator//
endif ////
enddefine
*//*
define ToIntFunction ////
if boolean|byte|char|double|float|long|short primitive //CharToIntFunction//
elif int primitive //IntUnaryOperator//
endif ////
enddefine
*//*
define ToLongFunction ////
if boolean|byte|char|double|float|int|short primitive //CharToLongFunction//
elif long primitive //LongUnaryOperator//
endif ////
enddefine
*//*
define Iterator ////
if boolean|byte|char|float|short primitive //CharIterator//
elif double|int|long primitive //PrimitiveIterator.OfChar//
endif ////
enddefine
*//*
define Spliterator ////
if boolean|byte|char|float|short primitive //CharSpliterator//
elif double|int|long primitive //Spliterator.OfChar//
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

/* if boolean|byte|char|float|short primitive */
import cufy.lang.CharIterable;
import cufy.util.function.*;

import java.util.Objects;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.IntStream;
/* elif double|int|long primitive */
import cufy.lang.CharIterable;
import cufy.util.function.CharBiConsumer;
import cufy.util.function.CharBiFunction;
import cufy.util.function.CharObjBiFunction;

import java.util.Objects;
import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;
/* if double|long primitive */
import java.util.stream.CharStream;
/* endif */
import java.util.stream.StreamSupport;
/* endif */

/**
 * An array specialized for {@code char} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.03
 */
public class CharArray
		extends
		AbstractPrimitiveArray<
				char[],
				Character,
				CharConsumer,
				CharBiConsumer,
				/*IntToFunction*/,
				/*ToDoubleFunction*/,
				/*ToIntFunction*/,
				/*ToLongFunction*/,
				CharUnaryOperator,
				CharBinaryOperator,
				CharPredicate,
				CharComparator
				>
		implements
		CharIterable {
	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = 3201994039505608491L;

	/**
	 * Construct a new array backed by a new actual array that have the given {@code length}.
	 *
	 * @param length the length of the new actual array backing the construct array.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.lang.reflect.Array#newInstance(Class, int)
	 * @since 0.1.5 ~2020.08.13
	 */
	public CharArray(int length) {
		super(new char[length]);
	}

	/**
	 * Construct a new array backed by the given {@code array}.
	 *
	 * @param array the array to be backing the constructed array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.05
	 */
	public CharArray(char[] array) {
		super(array);
	}

	/**
	 * Construct a new array backed by the specified range of the given {@code array}. The range
	 * starts at the given {@code beginIndex} and ends before the given {@code endIndex}.
	 *
	 * @param array      the array to be backing the constructed array.
	 * @param beginIndex the first index of the area at the given {@code array} to be backing the
	 *                   constructed array.
	 * @param endIndex   one past the last index of the area at the given {@code array} to be
	 *                   backing the constructed array.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.05
	 */
	public CharArray(char[] array, int beginIndex, int endIndex) {
		super(array, beginIndex, endIndex);
	}

	/**
	 * Construct a new array backed by the given {@code array}.
	 *
	 * @param array the array to be backing the constructed array.
	 * @return a new array backed by the given {@code array}.
	 * @throws java.lang.NullPointerException if the given {@code array}.
	 * @since 0.1.5 ~2020.09.13
	 */
	public static CharArray of(char... array) {
		return new CharArray(array);
	}

	@Override
	public int binarySearch(Object object) {
		return object instanceof Character ?
			   this.binarySearch((char) object) :
			   -1;
	}

	@Override
	public int binarySearch(Character element, Comparator<? super Character> comparator) {
		return element == null ?
			   -1 :
			   this.binarySearch(
					   (char) element,
					   comparator == null ||
					   comparator instanceof CharComparator ?
					   (CharComparator) comparator :
					   comparator::compare
			   );
	}

	@Override
	public CharArray clone() {
		// noinspection OverridableMethodCallDuringObjectConstruction,CloneCallsConstructors
		return new CharArray(this.copy());
	}

	@Override
	public void copy(Object array, int pos, int length) {
		Objects.requireNonNull(array, "array");
		if (array instanceof char[])
			this.arraycopy((char[]) array, pos, length);
		else if (array instanceof Object[])
			this.hardcopy((Object[]) array, pos, length);
		else if (array.getClass().isArray())
			throw new ArrayStoreException(
					"copy: type mismatch: can not copy char[] into " +
					array.getClass().getSimpleName()
			);
		else
			throw new ArrayStoreException(
					"copy: destination type " + array.getClass().getName() + " is not an array"
			);
	}

	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
		if (object instanceof CharArray) {
			CharArray array = (CharArray) object;

			if (array.length() == this.length()) {
				for (int i = array.beginIndex, j = this.beginIndex; i < array.endIndex; i++, j++) {
					if (this.eq(array.array[i], this.array[j]))
						continue;
					return false;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void fill(Character element) {
		this.fill((char) element);
	}

	@Override
	public void forEach(Consumer<? super Character> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		this.forEach(
				consumer instanceof CharConsumer ?
				(CharConsumer) consumer :
				consumer::accept
		);
	}

	@Override
	public void forEach(CharConsumer consumer) {
		Objects.requireNonNull(consumer, "consumer");
		for (int i = this.beginIndex; i < this.endIndex; i++)
			consumer.accept(this.array[i]);
	}

	@Override
	public Character get(int thumb) {
		return this.getChar(thumb);
	}

	@Override
	public void hardcopy(Object[] array, int pos, int length) {
		Objects.requireNonNull(array, "array");
		if (length < 0)
			throw new IndexOutOfBoundsException("length(" + length + ") < 0");
		if (pos < 0)
			throw new IndexOutOfBoundsException("pos(" + pos + ") < 0");

		int thisLength = this.endIndex - this.beginIndex;
		if (length > thisLength)
			throw new ArrayIndexOutOfBoundsException(
					"length(" + length + ") > this.length(" + thisLength + ")"
			);
		if (pos + length > array.length)
			throw new IndexOutOfBoundsException(
					"pos(" + pos + ") + length(" + length + ") > array.length(" + array.length +
					")"
			);

		try {
			//set boundaries
			int beginIndex = this.beginIndex;
			int endIndex = this.beginIndex + length;

			for (int i = beginIndex, j = pos; i < endIndex; i++, j++)
				array[j] = this.array[i];
		} catch (IllegalArgumentException e) {
			throw new ArrayStoreException(e.getMessage());
		}
	}

	@Override
	public int hashCode() {
		int hashCode = 1;

		for (int i = this.beginIndex; i < this.endIndex; i++)
			hashCode = 31 * hashCode + this.hash(this.array[i]);

		return hashCode;
	}

	@Override
	public CharArrayIterator iterator() {
		return new CharArrayIterator();
	}

	@Override
	public CharArrayList list() {
		return new CharArrayList();
	}

	@Override
	public CharArrayListIterator listIterator() {
		return new CharArrayListIterator();
	}

	@Override
	public CharArrayMap map() {
		return new CharArrayMap();
	}

	@Override
	public void parallelPrefix(BinaryOperator<Character> operator) {
		Objects.requireNonNull(operator, "operator");
		this.parallelPrefix(
				operator instanceof CharBinaryOperator ?
				(CharBinaryOperator) operator :
				operator::apply
		);
	}

	@Override
	public void parallelPrefix(CharBinaryOperator operator) {
		Objects.requireNonNull(operator, "operator");
		/*
		if double|int|long primitive
		*/
		//fixme: -redirect
		java.util.Arrays.parallelPrefix(
				this.array,
				this.beginIndex,
				this.endIndex,
				operator
		);
		/*
		elif boolean|byte|char|float|short primitive
		*/
		//fixme: -redirect -temp
		Character[] temp = new Character[this.endIndex - this.beginIndex];
		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			temp[j] = this.array[i];
		java.util.Arrays.parallelPrefix(temp, operator::applyAsChar);
		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			this.array[i] = temp[j];
		/*
		endif
		*/
	}

	@Override
	public void parallelSetAll(IntFunction<? extends Character> function) {
		Objects.requireNonNull(function, "function");
		this.parallelSetAll(
				function instanceof /*IntToFunction*/ ?
				(/*IntToFunction*/) function :
				function::apply
		);
	}

	@Override
	public void parallelSetAll(/*IntToFunction*/ function) {
		Objects.requireNonNull(function, "function");
		IntStream.range(this.beginIndex, this.endIndex)
				.parallel()
				.forEach(
						i -> this.array[i] = function.applyAsChar(this.thumb(i))
				);
	}

	@Override
	public void parallelSort() {
		/*
		if boolean primitive
		*/
		//fixme: -redirect -temp
		Character[] temp = new Character[this.endIndex - this.beginIndex];
		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			temp[j] = this.array[i];
		java.util.Arrays.parallelSort(temp);
		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			this.array[i] = temp[j];
		/*
		elif byte|char|double|float|int|long|short primitive
		*/
		//fixme: -redirect
		java.util.Arrays.parallelSort(
				this.array,
				this.beginIndex,
				this.endIndex
		);
		/*
		endif
		*/
	}

	@Override
	public void parallelSort(Comparator<? super Character> comparator) {
		this.parallelSort(
				comparator == null ||
				comparator instanceof CharComparator ?
				(CharComparator) comparator :
				comparator::compare
		);
	}

	@Override
	public void parallelSort(CharComparator comparator) {
		if (comparator == null)
			this.parallelSort();
		else {
			//fixme: -redirect -temp
			Character[] temp = new Character[this.endIndex - this.beginIndex];
			for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
				temp[j] = this.array[i];
			java.util.Arrays.sort(temp, comparator);
			for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
				this.array[i] = temp[j];
		}
	}

	@Override
	public void set(int thumb, Character element) {
		this.setChar(thumb, element);
	}

	@Override
	public void setAll(IntFunction<? extends Character> function) {
		Objects.requireNonNull(function, "function");
		this.setAll(
				function instanceof /*IntToFunction*/ ?
				(/*IntToFunction*/) function :
				function::apply
		);
	}

	@Override
	public void setAll(/*IntToFunction*/ function) {
		Objects.requireNonNull(function, "function");
		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			this.array[i] = function.applyAsChar(j);
	}

	@Override
	public void sort() {
		/*
		if boolean primitive
		 */
		//fixme: -redirect -temp
		Character[] temp = new Character[this.endIndex - this.beginIndex];
		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			temp[j] = this.array[i];
		java.util.Arrays.sort(temp);
		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			this.array[i] = temp[j];
		/*
		elif byte|char|double|float|int|long|short primitive
		 */
		//fixme: -redirect
		java.util.Arrays.sort(
				this.array,
				this.beginIndex,
				this.endIndex
		);
		/*
		endif
		 */
	}

	@Override
	public void sort(Comparator<? super Character> comparator) {
		this.sort(
				comparator == null ||
				comparator instanceof CharComparator ?
				(CharComparator) comparator :
				comparator::compare
		);
	}

	@Override
	public void sort(CharComparator comparator) {
		if (comparator == null)
			this.sort();
		else {
			//fixme: -redirect -temp
			Character[] temp = new Character[this.endIndex - this.beginIndex];
			for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
				temp[j] = this.array[i];
			java.util.Arrays.sort(temp, comparator);
			for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
				this.array[i] = temp[j];
		}
	}

	@Override
	public CharArraySpliterator spliterator() {
		return new CharArraySpliterator();
	}

	@Override
	public CharArray sub(int beginThumb, int endThumb) {
		this.range(beginThumb, endThumb);
		return new CharArray(
				this.array,
				this.beginIndex + beginThumb,
				this.beginIndex + endThumb
		);
	}

	@Override
	public String toString() {
		if (this.endIndex <= this.beginIndex)
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = this.beginIndex;
		while (true) {
			builder.append(this.array[i]);

			if (++i >= this.endIndex)
				return builder.append("]")
						.toString();

			builder.append(", ");
		}
	}

	/**
	 * Searches the this array for the specified value using the binary search algorithm. This array
	 * must be sorted prior to making this call. If it is not sorted, the results are undefined. If
	 * the array contains multiple elements with the specified value, there is no guarantee which
	 * one will be found.
	 *
	 * @param element the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @see java.util.Arrays#binarySearch(char[], char)
	 * @since 0.1.5 ~2020.08.30
	 */
	public int binarySearch(char element) {
		int l = this.beginIndex;
		int h = this.endIndex - 1;
		while (l <= h) {
			int m = l + h >>> 1;
			char mv = this.array[m];
			int r = Character.compare(mv, element);

			if (r < 0)
				l = m + 1;
			else if (r > 0)
				h = m - 1;
			else
				return this.thumb(m);
		}

		return this.thumb(-(l + 1));
	}

	/**
	 * Searches the this array for the specified value using the binary search algorithm. This array
	 * must be sorted prior to making this call. If it is not sorted, the results are undefined. If
	 * the array contains multiple elements with the specified value, there is no guarantee which
	 * one will be found.
	 *
	 * @param element    the value to be searched for.
	 * @param comparator the comparator by which the array is ordered. A null value indicates that
	 *                   the elements' natural ordering should be used.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @since 0.1.5 ~2020.08.11
	 */
	public int binarySearch(char element, CharComparator comparator) {
		if (comparator == null)
			this.binarySearch(element);

		int l = this.beginIndex;
		int h = this.endIndex - 1;
		while (l <= h) {
			int m = l + h >>> 1;
			char mv = this.array[m];
			int r = comparator.compare(mv, element);

			if (r < 0)
				l = m + 1;
			else if (r > 0)
				h = m - 1;
			else
				return this.thumb(m);
		}

		return this.thumb(-(l + 1));
	}

	/**
	 * Assign the given {@code element} to each element of this array.
	 *
	 * @param element the element to fill this array with.
	 * @see java.util.Arrays#fill(char[], char)
	 * @since 0.1.5 ~2020.08.30
	 */
	public void fill(char element) {
		for (int i = this.beginIndex; i < this.endIndex; i++)
			this.array[i] = element;
	}

	/**
	 * Get the element at the given {@code thumb} in this array.
	 *
	 * @param thumb the thumb to get the element from.
	 * @return the element at the given {@code thumb} in this array.
	 * @throws ArrayIndexOutOfBoundsException if {@code thumb < 0} or {@code thumb >= length}.
	 * @see java.lang.reflect.Array#getChar(Object, int)
	 * @since 0.1.5 ~2020.08.13
	 */
	public char getChar(int thumb) {
		return this.array[this.index(thumb)];
	}
	/*
	if double|int|long primitive
	*/

	/**
	 * Get a {@link CharStream} streaming the elements in this array.
	 *
	 * @return a stream streaming the elements in this array.
	 * @see java.util.Arrays#stream(char[])
	 * @since 0.1.5 ~2020.08.11
	 */
	public CharStream charStream() {
		return StreamSupport.charStream(this.spliterator(), false);
	}

	/**
	 * Get a parallel {@link CharStream} streaming the elements in this array.
	 *
	 * @return a stream streaming the elements in this array.
	 * @see java.util.Arrays#stream(char[])
	 * @since 0.1.5 ~2020.08.11
	 */
	public CharStream parallelCharStream() {
		return StreamSupport.charStream(this.spliterator(), true);
	}
	/*
	endif
	*/

	/**
	 * Set the element at the given {@code thumb} in this array to the given {@code element}.
	 *
	 * @param thumb   the thumb to set the given {@code element} to.
	 * @param element the element to be set.
	 * @throws ArrayIndexOutOfBoundsException if {@code thumb < 0} or {@code thumb >= length}.
	 * @see java.lang.reflect.Array#setChar(Object, int, char)
	 * @since 0.1.5 ~2020.08.13
	 */
	public void setChar(int thumb, char element) {
		this.array[this.index(thumb)] = element;
	}

	/**
	 * Determine if the given two elements are equal or not. This is the base equality check (for
	 * object-primitive) in this class and it should be for its subclasses.
	 *
	 * @param element the first element.
	 * @param e       the second element.
	 * @return true, if the given {@code element} equals the given {@code e} in this class's
	 * 		standard.
	 * @since 0.1.5 ~2020.08.18
	 */
	protected boolean eq(Object element, char e) {
		/*
		if double primitive
		*/
		return element instanceof Character &&
			   Character.charToLongBits((char) element) ==
			   Character.charToLongBits(e);
		/*
		elif float primitive
		*/
		return element instanceof Character &&
			   Character.charToIntBits((char) element) ==
			   Character.charToIntBits(e);
		/*
		elif boolean|byte|char|int|long|short primitive
		*/
		return element instanceof Character &&
			   (char) element == e;
		/*
		endif
		*/
	}

	/**
	 * Determine if the given two elements are equal or not. This is the base equality check (for
	 * primitive-primitive) in this class and it should be for its subclasses.
	 *
	 * @param element the first element.
	 * @param e       the second element.
	 * @return true, if the given {@code element} equals the given {@code e} in this class's
	 * 		standard.
	 * @since 0.1.5 ~2020.08.18
	 */
	protected boolean eq(char element, char e) {
		/*
		if double primitive
		*/
		return Character.charToLongBits((char) element) ==
			   Character.charToLongBits(e);
		/*
		elif float primitive
		*/
		return Character.charToIntBits((char) element) ==
			   Character.charToIntBits(e);
		/*
		elif boolean|byte|char|int|long|short primitive
		*/
		return element == e;
		/*
		endif
		*/
	}

	/**
	 * Calculate the hash code of the given element. This is the base hash code algorithm in this
	 * class and it should be for its subclasses.
	 *
	 * @param e the element to calculate its hashCode.
	 * @return the calculated hash code of the given element.
	 * @since 0.1.5 ~2020.08.31
	 */
	protected int hash(char e) {
		return Character.hashCode(e);
	}

	/**
	 * An iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class CharArrayIterator
			extends
			AbstractPrimitiveArrayIterator
			implements
			/*Iterator*/ {
		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharArrayIterator() {
		}

		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @param beginThumb the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharArrayIterator(int beginThumb) {
			super(beginThumb);
		}

		@Override
		public void forEachRemaining(CharConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = CharArray.this.endIndex;
			for (int i = index; i < CharArray.this.endIndex; i++)
				consumer.accept(CharArray.this.array[i]);
		}

		@Override
		public char nextChar() {
			int index = this.index;

			if (index < CharArray.this.endIndex) {
				this.index++;
				return CharArray.this.array[index];
			}

			throw new NoSuchElementException();
		}
	}

	/**
	 * A list backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class CharArrayList
			extends
			AbstractPrimitiveArrayList
			implements
			CharList {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 848985287158674978L;

		@Override
		public void add(int thumb, Character element) {
			//redundand
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean add(Character element) {
			//redundand
			throw new UnsupportedOperationException("add");
		}

		@Override
		public void addChar(int index, char element) {
			throw new UnsupportedOperationException("addChar");
		}

		@Override
		public boolean addChar(char element) {
			throw new UnsupportedOperationException("addChar");
		}

		@Override
		public CharArrayList clone() {
			return new CharArray(CharArray.this.copy())
					.new CharArrayList();
		}

		@Override
		public boolean contains(char element) {
			for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i++)
				if (CharArray.this.eq(element, CharArray.this.array[i]))
					return true;

			return false;
		}

		@Override
		public boolean containsAll(Collection collection) {
			Objects.requireNonNull(collection, "collection");

			Iterator iterator = collection.iterator();

			if (iterator instanceof /*Iterator*/) {
				/*Iterator*/ charIterator = (/*Iterator*/) iterator;

				while (charIterator.hasNext()) {
					if (this.contains(charIterator.nextChar()))
						continue;

					return false;
				}
			} else
				while (iterator.hasNext()) {
					if (this.contains(iterator.next()))
						continue;

					return false;
				}

			return true;
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof List) {
				List list = (List) object;

				if (list.size() == this.size()) {
					int i = CharArray.this.beginIndex;
					for (Object element : list) {
						if (i < CharArray.this.endIndex &&
							CharArray.this.eq(element, CharArray.this.array[i++]))
							continue;
						return false;
					}
					return true;
				}
			}
			return false;
		}

		@SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
		@Override
		public char getChar(int thumb) {
			return CharArray.this.array[CharArray.this.index(thumb)];
		}

		@Override
		public int hashCode() {
			int hashCode = 1;

			for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i++)
				hashCode = 31 * hashCode + CharArray.this.hash(CharArray.this.array[i]);

			return hashCode;
		}

		@Override
		public int indexOf(char element) {
			for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i++)
				if (CharArray.this.eq(element, CharArray.this.array[i]))
					return CharArray.this.thumb(i);

			return -1;
		}

		@Override
		public CharArrayIterator iterator() {
			return new CharArrayIterator();
		}

		@Override
		public int lastIndexOf(char element) {
			for (int i = CharArray.this.endIndex - 1; i >= CharArray.this.beginIndex; i--)
				if (CharArray.this.eq(element, CharArray.this.array[i]))
					return CharArray.this.thumb(i);

			return -1;
		}

		@Override
		public CharArrayListIterator listIterator() {
			return new CharArrayListIterator();
		}

		@SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
		@Override
		public CharArrayListIterator listIterator(int beginThumb) {
			return new CharArrayListIterator(beginThumb);
		}

		@Override
		public Character remove(int thumb) {
			//redundand
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public boolean remove(Object object) {
			//redundand
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public boolean removeIf(CharPredicate predicate) {
			//redundand
			throw new UnsupportedOperationException("removeIf");
		}

		@Override
		public boolean removeIf(Predicate<? super Character> predicate) {
			//redundand
			throw new UnsupportedOperationException("removeIf");
		}

		@Override
		public boolean removeChar(char element) {
			throw new UnsupportedOperationException("removeChar");
		}

		@Override
		public char removeCharAt(int index) {
			throw new UnsupportedOperationException("removeCharAt");
		}

		@Override
		public void replaceAll(CharUnaryOperator operator) {
			Objects.requireNonNull(operator, "operator");
			for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i++)
				CharArray.this.array[i] = operator.applyAsChar(CharArray.this.array[i]);
		}

		@SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
		@Override
		public char setChar(int thumb, char element) {
			int i = CharArray.this.index(thumb);
			char old = CharArray.this.array[i];
			CharArray.this.array[i] = element;
			return old;
		}

		@Override
		public CharArraySpliterator spliterator() {
			return new CharArraySpliterator();
		}

		@Override
		public CharList subList(int beginIndex, int endIndex) {
			CharArray.this.range(beginIndex, endIndex);
			return new CharArray(
					CharArray.this.array,
					CharArray.this.beginIndex + beginIndex,
					CharArray.this.beginIndex + endIndex
			).new CharArrayList();
		}

		@Override
		public char[] toCharArray() {
			return CharArray.this.copy();
		}

		@Override
		public char[] toCharArray(char[] array) {
			Objects.requireNonNull(array, "array");
			int length = CharArray.this.endIndex - CharArray.this.beginIndex;

			if (array.length < length)
				return CharArray.this.copy();
			if (array.length > length)
				array[length] = /*DefaultValue*/;

			CharArray.this.arraycopy(array, 0, length);
			return array;
		}
	}

	/**
	 * A list iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class CharArrayListIterator
			extends
			AbstractPrimitiveArrayListIterator
			implements
			CharListIterator {
		/**
		 * Construct a new list iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharArrayListIterator() {
		}

		/**
		 * Construct a new list iterator iterating the elements in the enclosing array, starting
		 * from the given {@code index}.
		 *
		 * @param beginThumb the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharArrayListIterator(int beginThumb) {
			super(beginThumb);
		}

		@Override
		public void add(Character element) {
			//redundant
			throw new UnsupportedOperationException("add");
		}

		@Override
		public void addChar(char value) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public void forEachRemaining(CharConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = CharArray.this.endIndex;
			this.last = CharArray.this.endIndex - 1;

			for (int i = index; i < CharArray.this.endIndex; i++)
				consumer.accept(CharArray.this.array[i]);
		}

		@Override
		public char nextChar() {
			int index = this.index;

			if (index < CharArray.this.endIndex) {
				this.index++;
				return CharArray.this.array[this.last = index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public char previousChar() {
			int index = this.index - 1;

			if (index >= CharArray.this.beginIndex) {
				this.index--;
				return CharArray.this.array[this.last = index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public void setChar(char value) {
			int index = this.last;

			if (index == -1)
				throw new IllegalStateException();

			CharArray.this.array[index] = value;
		}
	}

	/**
	 * A map backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class CharArrayMap
			extends
			AbstractPrimitiveArrayMap
			implements
			CharMap {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -2840280796050057228L;

		@Override
		public CharArrayMap clone() {
			//noinspection OverridableMethodCallDuringObjectConstruction,CloneCallsConstructors
			return new CharArray(CharArray.this.copy())
					.new CharArrayMap();
		}

		@Override
		public char compute(char key, CharObjBiFunction<Character, Character> function) {
			Objects.requireNonNull(function, "function");
			for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i += 2) {
				char k = CharArray.this.array[i];

				if (CharArray.this.eq(key, k)) {
					char v = CharArray.this.array[i + 1];
					Character value = function.apply(k, v);

					if (value == null)
						throw new UnsupportedOperationException("remove");

					CharArray.this.array[i + 1] = value;
					return value;
				}
			}

			throw new IllegalArgumentException("Key not found");
		}

		@Override
		public char computeIfAbsent(char key, CharFunction<Character> function) {
			Objects.requireNonNull(function, "function");
			for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i += 2)
				if (CharArray.this.eq(key, CharArray.this.array[i]))
					return CharArray.this.array[i + 1];

			throw new IllegalArgumentException("Key not found");
		}

		@Override
		public char computeIfPresent(char key, CharBiFunction<Character> function) {
			Objects.requireNonNull(function, "function");
			for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i += 2) {
				char k = CharArray.this.array[i];

				if (CharArray.this.eq(key, k)) {
					char v = CharArray.this.array[i + 1];
					Character value = function.apply(k, v);

					if (value == null)
						throw new UnsupportedOperationException("remove");

					CharArray.this.array[i + 1] = value;
					return value;
				}
			}

			return /*DefaultValue*/;
		}

		@Override
		public boolean containsKey(char key) {
			for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i += 2)
				if (CharArray.this.eq(key, CharArray.this.array[i]))
					return true;

			return false;
		}

		@Override
		public boolean containsValue(char value) {
			for (int i = CharArray.this.beginIndex + 1; i < CharArray.this.endIndex; i += 2)
				if (CharArray.this.eq(value, CharArray.this.array[i]))
					return true;

			return false;
		}

		@Override
		public CharArrayEntrySet entrySet() {
			return new CharArrayEntrySet();
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof Map) {
				Map<?, ?> map = (Map) object;

				if (map.size() == this.size()) {
					for0:
					for (Entry entry : map.entrySet()) {
						Object key = entry.getKey();

						for (int i = CharArray.this.beginIndex;
							 i < CharArray.this.endIndex; i += 2)
							if (CharArray.this.eq(
									key,
									CharArray.this.array[i]
							)) {
								if (CharArray.this.eq(
										entry.getValue(),
										CharArray.this.array[i + 1]
								))
									continue for0;
								break;
							}
						return false;
					}
					return true;
				}
			}
			return false;
		}

		@Override
		public void forEach(CharBiConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i += 2)
				consumer.accept(CharArray.this.array[i], CharArray.this.array[i + 1]);
		}

		@Override
		public char getChar(char key) {
			for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i += 2)
				if (CharArray.this.eq(key, CharArray.this.array[i]))
					return CharArray.this.array[i + 1];

			return /*DefaultValue*/;
		}

		@Override
		public char getCharOrDefault(char key, char defaultValue) {
			for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i += 2)
				if (CharArray.this.eq(key, CharArray.this.array[i]))
					return CharArray.this.array[i + 1];

			return defaultValue;
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i += 2)
				hashCode += CharArray.this.hash(CharArray.this.array[i]) ^
							CharArray.this.hash(CharArray.this.array[i + 1]);

			return hashCode;
		}

		@Override
		public CharArrayKeySet keySet() {
			return new CharArrayKeySet();
		}

		@Override
		public char merge(char key, char value, CharBiFunction<Character> function) {
			Objects.requireNonNull(function, "function");
			for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i += 2)
				if (CharArray.this.eq(key, CharArray.this.array[i])) {
					char v = CharArray.this.array[i + 1];
					Character newValue = function.apply(v, value);

					if (newValue == null)
						throw new UnsupportedOperationException("remove");

					CharArray.this.array[i + 1] = newValue;
					return newValue;
				}

			throw new IllegalArgumentException("Key not found");
		}

		@Override
		public void putAll(Map<? extends Character, ? extends Character> map) {
			Objects.requireNonNull(map, "map");
			for0:
			for (Map.Entry<? extends Character, ? extends Character> entry : map.entrySet())
				if (entry instanceof CharEntry) {
					CharEntry charEntry = (CharEntry) entry;
					char key = charEntry.getCharKey();

					for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i += 2)
						if (CharArray.this.eq(key, CharArray.this.array[i])) {
							char value = charEntry.getCharValue();
							CharArray.this.array[i + 1] = value;
							continue for0;
						}

					throw new IllegalArgumentException("Key not found");
				} else {
					char key = entry.getKey();

					for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i += 2)
						if (CharArray.this.eq(key, CharArray.this.array[i])) {
							char value = entry.getValue();
							CharArray.this.array[i + 1] = value;
							continue for0;
						}

					throw new IllegalArgumentException("Key not found");
				}
		}

		@Override
		public char putChar(char key, char value) {
			for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i += 2)
				if (CharArray.this.eq(key, CharArray.this.array[i])) {
					char v = CharArray.this.array[i + 1];
					CharArray.this.array[i + 1] = value;
					return v;
				}

			throw new IllegalArgumentException("Key not found");
		}

		@Override
		public char putCharIfAbsent(char key, char value) {
			for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i += 2)
				if (CharArray.this.eq(key, CharArray.this.array[i]))
					return CharArray.this.array[i + 1];

			throw new IllegalArgumentException("Key not found");
		}

		@Override
		public Character remove(Object key) {
			//redundant
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public boolean remove(Object key, Object value) {
			//redundant
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public char removeChar(char key) {
			throw new UnsupportedOperationException("removeChar");
		}

		@Override
		public boolean removeChar(char key, char value) {
			throw new UnsupportedOperationException("removeChar");
		}

		@Override
		public void replaceAll(CharBinaryOperator function) {
			Objects.requireNonNull(function, "function");
			for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i += 2) {
				char n = function.applyAsChar(
						CharArray.this.array[i],
						CharArray.this.array[i + 1]
				);
				CharArray.this.array[i + 1] = n;
			}
		}

		@Override
		public boolean replaceChar(char key, char oldValue, char newValue) {
			for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i += 2)
				if (CharArray.this.eq(key, CharArray.this.array[i])) {
					if (CharArray.this.eq(oldValue, CharArray.this.array[i + 1])) {
						CharArray.this.array[i + 1] = newValue;
						return true;
					}

					break;
				}

			return false;
		}

		@Override
		public char replaceChar(char key, char value) {
			for (int i = CharArray.this.beginIndex; i < CharArray.this.endIndex; i += 2)
				if (CharArray.this.eq(key, CharArray.this.array[i])) {
					char v = CharArray.this.array[i + 1];
					CharArray.this.array[i + 1] = value;
					return v;
				}

			return /*DefaultValue*/;
		}

		@Override
		public String toString() {
			if (this.isEmpty())
				return "{}";

			StringBuilder builder = new StringBuilder("{");

			int i = CharArray.this.beginIndex;
			while (true) {
				builder.append(CharArray.this.array[i])
						.append("=")
						.append(CharArray.this.array[i + 1]);

				if ((i += 2) >= CharArray.this.endIndex)
					return builder.append("}")
							.toString();

				builder.append(", ");
			}
		}

		@Override
		public CharArrayValues values() {
			return new CharArrayValues();
		}

		/**
		 * An entry backed by a range from {@code index} to {@code index + 1} in the enclosing
		 * array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public class CharArrayEntry
				extends
				AbstractPrimitiveArrayEntry
				implements
				CharEntry {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 5973497615323125824L;

			/**
			 * Construct a new entry backed by a range from {@code index} to {@code index + 1} in
			 * the enclosing array.
			 *
			 * @param thumb the index to where the key (followed by the value) will be in the
			 *              constructed entry.
			 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index + 1 >=
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			public CharArrayEntry(int thumb) {
				super(thumb);
			}

			@Override
			public CharArrayEntry clone() {
				//noinspection CloneCallsConstructors
				return new CharArray(
						new CharArray(
								CharArray.this.array,
								CharArray.this.beginIndex + this.index,
								CharArray.this.beginIndex + this.index + 2
						).copy()
				).new CharArrayMap()
						.new CharArrayEntry(0);
			}

			@Override
			public boolean equals(Object object) {
				if (object == this)
					return true;
				if (object instanceof CharEntry) {
					CharEntry charEntry = (CharEntry) object;

					if (CharArray.this.eq(
							charEntry.getCharKey(),
							CharArray.this.array[this.index]
					))
						return CharArray.this.eq(
								charEntry.getCharValue(),
								CharArray.this.array[this.index + 1]
						);
				} else if (object instanceof Entry) {
					Entry entry = (Map.Entry) object;

					if (CharArray.this.eq(
							entry.getKey(),
							CharArray.this.array[this.index]
					))
						return CharArray.this.eq(
								entry.getValue(),
								CharArray.this.array[this.index + 1]
						);
				}

				return false;
			}

			@Override
			public char getCharKey() {
				return CharArray.this.array[this.index];
			}

			@Override
			public char getCharValue() {
				return CharArray.this.array[this.index + 1];
			}

			@Override
			public int hashCode() {
				return CharArray.this.hash(CharArray.this.array[this.index]) ^
					   CharArray.this.hash(CharArray.this.array[this.index + 1]);
			}

			@Override
			public char setCharValue(char value) {
				char v = CharArray.this.array[this.index + 1];
				CharArray.this.array[this.index + 1] = value;
				return v;
			}

			@Override
			public String toString() {
				return CharArray.this.array[this.index] + "=" +
					   CharArray.this.array[this.index + 1];
			}
		}

		/**
		 * An iterator iterating the entries in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public class CharArrayEntryIterator
				extends
				AbstractPrimitiveArrayEntryIterator {
			/**
			 * Construct a new iterator iterating the entries in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			public CharArrayEntryIterator() {
			}

			/**
			 * Construct a new iterator iterating the entries in the enclosing array, starting from
			 * the given {@code index}.
			 *
			 * @param beginThumb the initial position of the constructed iterator.
			 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			public CharArrayEntryIterator(int beginThumb) {
				super(beginThumb);
			}

			@Override
			public void forEachRemaining(Consumer<? super Entry<Character, Character>> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				int index = this.index;
				this.index = CharArray.this.endIndex;
				for (int t = CharArray.this.thumb(index),
					 l = CharArray.this.length();
					 t < l;
					 t += 2)
					consumer.accept(new CharArrayEntry(t));
			}

			@Override
			public CharArrayEntry next() {
				int index = this.index;

				if (index < CharArray.this.endIndex) {
					this.index += 2;
					return new CharArrayEntry(CharArray.this.thumb(index));
				}

				throw new NoSuchElementException();
			}
		}

		/**
		 * A set backed by the entries in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public class CharArrayEntrySet
				extends
				AbstractPrimitiveArrayEntrySet {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -4823635378224028987L;

			@Override
			public CharArrayEntrySet clone() {
				//noinspection CloneCallsConstructors,OverridableMethodCallDuringObjectConstruction
				return new CharArray(CharArray.this.copy())
						.new CharArrayMap()
						.new CharArrayEntrySet();
			}

			@Override
			public boolean contains(Object object) {
				if (object instanceof CharEntry) {
					CharEntry charEntry = (CharEntry) object;
					char key = charEntry.getKey();

					for (int i = CharArray.this.beginIndex;
						 i < CharArray.this.endIndex;
						 i += 2)
						if (CharArray.this.eq(
								key,
								CharArray.this.array[i]
						)) {
							if (CharArray.this.eq(
									charEntry.getValue(),
									CharArray.this.array[i + 1]
							))
								return true;

							break;
						}
				} else if (object instanceof Entry) {
					Entry entry = (java.util.Map.Entry) object;
					Object key = entry.getKey();

					for (int i = CharArray.this.beginIndex;
						 i < CharArray.this.endIndex;
						 i += 2)
						if (CharArray.this.eq(
								key,
								CharArray.this.array[i]
						)) {
							if (CharArray.this.eq(
									entry.getValue(),
									CharArray.this.array[i + 1]
							))
								return true;

							break;
						}
				}

				return false;
			}

			@Override
			public boolean equals(Object object) {
				if (object == this)
					return true;
				if (object instanceof Set) {
					Set set = (Set) object;

					if (set.size() == this.size()) {
						for0:
						for (Object obj : set) {
							if (obj instanceof CharEntry) {
								CharEntry entry = (CharEntry) obj;
								char key = entry.getCharKey();

								for (int i = CharArray.this.beginIndex;
									 i < CharArray.this.endIndex;
									 i += 2)
									if (CharArray.this.eq(
											key,
											CharArray.this.array[i]
									)) {
										if (CharArray.this.eq(
												entry.getValue(),
												CharArray.this.array[i + 1]
										))
											continue for0;
										break;
									}
							} else if (obj instanceof Entry) {
								Entry entry = (Entry) obj;
								Object key = entry.getKey();

								for (int i = CharArray.this.beginIndex;
									 i < CharArray.this.endIndex;
									 i += 2)
									if (CharArray.this.eq(
											key,
											CharArray.this.array[i]
									)) {
										if (CharArray.this.eq(
												entry.getValue(),
												CharArray.this.array[i + 1]
										))
											continue for0;
										break;
									}
							}
							return false;
						}
						return true;
					}
				}
				return false;
			}

			@Override
			public void forEach(Consumer<? super Entry<Character, Character>> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				for (int t = 0,
					 l = CharArray.this.length();
					 t < l;
					 t += 2)
					consumer.accept(new CharArrayEntry(t));
			}

			@Override
			public int hashCode() {
				int hashCode = 0;

				for (int i = CharArray.this.beginIndex;
					 i < CharArray.this.endIndex;
					 i += 2)
					hashCode += CharArray.this.hash(CharArray.this.array[i]) ^
								CharArray.this.hash(CharArray.this.array[i + 1]);

				return hashCode;
			}

			@Override
			public CharArrayEntryIterator iterator() {
				return new CharArrayEntryIterator();
			}

			@Override
			public CharArrayEntrySpliterator spliterator() {
				return new CharArrayEntrySpliterator();
			}

			@Override
			public Object[] toArray() {
				Object[] product = new Object[this.size()];

				for (int t = 0,
					 l = CharArray.this.length(),
					 j = 0;
					 t < l;
					 t += 2, j++)
					product[j] = new CharArrayEntry(t);

				return product;
			}

			@Override
			public <T> T[] toArray(T[] array) {
				Objects.requireNonNull(array, "array");
				int length = this.size();
				T[] product = array;

				if (array.length < length)
					product = (T[]) java.lang.reflect.Array.newInstance(
							array.getClass().getComponentType(),
							length
					);
				else if (array.length > length)
					product[length] = null;

				for (int t = 0,
					 l = CharArray.this.length(),
					 j = 0;
					 t < l;
					 t += 2, j++)
					product[j] = (T) new CharArrayEntry(t);

				return product;
			}

			@Override
			public String toString() {
				if (this.isEmpty())
					return "[]";

				StringBuilder builder = new StringBuilder("[");

				int i = CharArray.this.beginIndex;
				while (true) {
					builder.append(CharArray.this.array[i])
							.append("=")
							.append(CharArray.this.array[i + 1]);

					if ((i += 2) >= CharArray.this.endIndex)
						return builder.append("]")
								.toString();

					builder.append(", ");
				}
			}
		}

		/**
		 * A spliterator iterating the entries in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.02
		 */
		public class CharArrayEntrySpliterator
				extends
				AbstractPrimitiveArrayEntrySpliterator {
			/**
			 * Construct a new spliterator iterating the entries in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			public CharArrayEntrySpliterator() {
			}

			/**
			 * Construct a new spliterator iterating the entries in the enclosing array, starting
			 * from the given {@code index}.
			 *
			 * @param beginThumb the initial position of the constructed spliterator.
			 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			public CharArrayEntrySpliterator(int beginThumb) {
				super(beginThumb);
			}

			@Override
			public void forEachRemaining(Consumer<? super Entry<Character, Character>> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				int index = this.index;
				this.index = CharArray.this.endIndex;
				for (int t = 0,
					 l = CharArray.this.length();
					 t < l;
					 t += 2)
					consumer.accept(new CharArrayEntry(t));
			}

			@Override
			public boolean tryAdvance(Consumer<? super Entry<Character, Character>> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				int index = this.index;

				if (index < CharArray.this.endIndex) {
					this.index += 2;
					consumer.accept(new CharArrayEntry(CharArray.this.thumb(index)));
					return true;
				}

				return false;
			}

			@Override
			public CharArrayEntrySpliterator trySplit() {
				int index = this.index;
				int midIndex = index + CharArray.this.endIndex >>> 1;

				if (index < midIndex) {
					this.index = midIndex;
					return new CharArray(
							CharArray.this.array,
							CharArray.this.beginIndex + index,
							CharArray.this.beginIndex + midIndex
					).new CharArrayMap()
							.new CharArrayEntrySpliterator();
				}

				return null;
			}
		}

		/**
		 * An iterator iterating the keys in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public class CharArrayKeyIterator
				extends
				AbstractPrimitiveArrayKeyIterator
				implements
				/*Iterator*/ {
			/**
			 * Construct a new iterator iterating the keys in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			public CharArrayKeyIterator() {
			}

			/**
			 * Construct a new iterator iterating the keys in the enclosing array, starting from the
			 * given {@code index}.
			 *
			 * @param beginThumb the initial position of the constructed iterator.
			 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			public CharArrayKeyIterator(int beginThumb) {
				super(beginThumb);
			}

			@Override
			public void forEachRemaining(CharConsumer consumer) {
				Objects.requireNonNull(consumer, "consumer");
				int index = this.index;
				this.index = CharArray.this.endIndex;
				for (int i = index;
					 i < CharArray.this.endIndex;
					 i += 2)
					consumer.accept(CharArray.this.array[i]);
			}

			@Override
			public char nextChar() {
				int index = this.index;

				if (index < CharArray.this.endIndex) {
					this.index += 2;
					return CharArray.this.array[index];
				}

				throw new NoSuchElementException();
			}
		}

		/**
		 * A set backed by the keys in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public class CharArrayKeySet
				extends
				AbstractPrimitiveArrayKeySet
				implements
				CharSet {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 7793360078444812816L;

			@Override
			public boolean add(Character key) {
				//redundant
				throw new UnsupportedOperationException("add");
			}

			@Override
			public boolean addChar(char element) {
				throw new UnsupportedOperationException("addChar");
			}

			@Override
			public CharArrayKeySet clone() {
				//noinspection CloneCallsConstructors,OverridableMethodCallDuringObjectConstruction
				return new CharArray(CharArray.this.copy())
						.new CharArrayMap()
						.new CharArrayKeySet();
			}

			@Override
			public boolean contains(char element) {
				return CharArrayMap.this.containsKey(element);
			}

			@Override
			public boolean containsAll(Collection collection) {
				Objects.requireNonNull(collection, "collection");

				Iterator iterator = collection.iterator();
				if (iterator instanceof /*Iterator*/) {
					/*Iterator*/ charIterator = (/*Iterator*/) iterator;

					while (charIterator.hasNext()) {
						if (CharArrayMap.this.containsKey(charIterator.nextChar()))
							continue;

						return false;
					}
				} else
					while (iterator.hasNext()) {
						if (CharArrayMap.this.containsValue(iterator.next()))
							continue;

						return false;
					}

				return true;
			}

			@Override
			public boolean equals(Object object) {
				if (object == this)
					return true;
				if (object instanceof Set) {
					Set set = (Set) object;

					if (set.size() == this.size()) {
						for0:
						for (Object key : set) {
							for (int i = CharArray.this.beginIndex;
								 i < CharArray.this.endIndex;
								 i += 2)
								if (CharArray.this.eq(
										key,
										CharArray.this.array[i]
								))
									continue for0;
							return false;
						}
						return true;
					}
				}
				return false;
			}

			@Override
			public void forEach(CharConsumer consumer) {
				Objects.requireNonNull(consumer, "consumer");
				for (int i = CharArray.this.beginIndex;
					 i < CharArray.this.endIndex;
					 i += 2)
					consumer.accept(CharArray.this.array[i]);
			}

			@Override
			public int hashCode() {
				int hashCode = 0;

				for (int i = CharArray.this.beginIndex;
					 i < CharArray.this.endIndex;
					 i += 2)
					hashCode += CharArray.this.hash(CharArray.this.array[i]);

				return hashCode;
			}

			@Override
			public CharArrayKeyIterator iterator() {
				return new CharArrayKeyIterator();
			}

			@Override
			public boolean remove(Object object) {
				//redundant
				throw new UnsupportedOperationException("remove");
			}

			@Override
			public boolean removeChar(char element) {
				throw new UnsupportedOperationException("removeChar");
			}

			@Override
			public boolean removeIf(Predicate<? super Character> predicate) {
				//redundant
				throw new UnsupportedOperationException("removeIf");
			}

			@Override
			public boolean removeIf(CharPredicate predicate) {
				//redundant
				throw new UnsupportedOperationException("removeIf");
			}

			@Override
			public CharArrayKeySpliterator spliterator() {
				return new CharArrayKeySpliterator();
			}

			@Override
			public Object[] toArray() {
				Object[] product = new Object[this.size()];

				for (int i = CharArray.this.beginIndex,
					 j = 0;
					 i < CharArray.this.endIndex;
					 i += 2, j++)
					product[j] = CharArray.this.array[i];

				return product;
			}

			@Override
			public <T> T[] toArray(T[] array) {
				Objects.requireNonNull(array, "array");
				int length = this.size();
				T[] product = array;

				if (array.length < length)
					product = (T[]) java.lang.reflect.Array.newInstance(
							array.getClass().getComponentType(),
							length
					);
				else if (array.length > length)
					product[length] = null;

				for (int i = CharArray.this.beginIndex,
					 j = 0;
					 i < CharArray.this.endIndex;
					 i += 2, j++)
					product[j] = (T) (Character) CharArray.this.array[i];

				return product;
			}

			@Override
			public char[] toCharArray() {
				char[] product = new char[this.size()];

				for (int i = CharArray.this.beginIndex,
					 j = 0;
					 i < CharArray.this.endIndex;
					 i += 2, j++)
					product[j] = CharArray.this.array[i];

				return product;
			}

			@Override
			public char[] toCharArray(char[] array) {
				Objects.requireNonNull(array, "array");
				int length = this.size();
				char[] product = array;

				if (array.length < length)
					product = new char[length];
				else if (array.length > length)
					product[length] = /*DefaultValue*/;

				for (int i = CharArray.this.beginIndex,
					 j = 0;
					 i < CharArray.this.endIndex;
					 i += 2, j++)
					product[j] = CharArray.this.array[i];

				return product;
			}

			@Override
			public String toString() {
				if (this.isEmpty())
					return "[]";

				StringBuilder builder = new StringBuilder("[");

				int i = CharArray.this.beginIndex;
				while (true) {
					builder.append(CharArray.this.array[i]);

					if ((i += 2) >= CharArray.this.endIndex)
						return builder.append("]")
								.toString();

					builder.append(", ");
				}
			}
		}

		/**
		 * A spliterator iterating the keys in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.02
		 */
		public class CharArrayKeySpliterator
				extends
				AbstractPrimitiveArrayKeySpliterator</*Spliterator*/>
				implements
				/*Spliterator*/ {
			/**
			 * Construct a new spliterator iterating the keys in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			public CharArrayKeySpliterator() {
			}

			/**
			 * Construct a new spliterator iterating the keys in the enclosing array, starting from
			 * the given {@code index}.
			 *
			 * @param beginThumb the initial position of the constructed spliterator.
			 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			public CharArrayKeySpliterator(int beginThumb) {
				super(beginThumb);
			}

			@Override
			public void forEachRemaining(CharConsumer consumer) {
				Objects.requireNonNull(consumer, "consumer");
				int index = this.index;
				this.index = CharArray.this.endIndex;
				for (int i = index;
					 i < CharArray.this.endIndex;
					 i += 2)
					consumer.accept(CharArray.this.array[i]);
			}

			@Override
			public boolean tryAdvance(CharConsumer consumer) {
				Objects.requireNonNull(consumer, "consumer");
				int index = this.index;

				if (index < CharArray.this.endIndex) {
					this.index += 2;
					consumer.accept(CharArray.this.array[index]);
					return true;
				}

				return false;
			}

			@Override
			public CharArrayKeySpliterator trySplit() {
				int index = this.index;
				int midIndex = index + CharArray.this.endIndex >>> 1;

				if (index < midIndex) {
					this.index = midIndex;
					return new CharArray(
							CharArray.this.array,
							CharArray.this.beginIndex + index,
							CharArray.this.beginIndex + midIndex
					).new CharArrayMap()
							.new CharArrayKeySpliterator();
				}

				return null;
			}
		}

		/**
		 * An iterator iterating the values in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public class CharArrayValueIterator
				extends
				AbstractPrimitiveArrayValueIterator
				implements
				/*Iterator*/ {
			/**
			 * Construct a new iterator iterating the values in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			public CharArrayValueIterator() {
			}

			/**
			 * Construct a new iterator iterating the values in the enclosing array, starting from
			 * the given {@code index}.
			 *
			 * @param beginThumb the initial position of the constructed iterator.
			 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			public CharArrayValueIterator(int beginThumb) {
				super(beginThumb);
			}

			@Override
			public void forEachRemaining(CharConsumer consumer) {
				Objects.requireNonNull(consumer, "consumer");
				int index = this.index;
				this.index = CharArray.this.endIndex;
				for (int y = index + 1;
					 y < CharArray.this.endIndex;
					 y += 2)
					consumer.accept(CharArray.this.array[y]);
			}

			@Override
			public char nextChar() {
				int index = this.index;

				if (index < CharArray.this.endIndex) {
					this.index += 2;
					return CharArray.this.array[index + 1];
				}

				throw new NoSuchElementException();
			}
		}

		/**
		 * A spliterator iterating the values in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.02
		 */
		public class CharArrayValueSpliterator
				extends
				AbstractPrimitiveArrayValueSpliterator</*Spliterator*/>
				implements
				/*Spliterator*/ {
			/**
			 * Construct a new spliterator iterating the values in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			public CharArrayValueSpliterator() {
			}

			/**
			 * Construct a new spliterator iterating the values in the enclosing array, starting
			 * from the given {@code index}.
			 *
			 * @param beginThumb the initial position of the constructed spliterator.
			 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			public CharArrayValueSpliterator(int beginThumb) {
				super(beginThumb);
			}

			@Override
			public void forEachRemaining(CharConsumer consumer) {
				Objects.requireNonNull(consumer, "consumer");
				int index = this.index;
				this.index = CharArray.this.endIndex;

				for (int y = index + 1;
					 y < CharArray.this.endIndex;
					 y += 2)
					consumer.accept(CharArray.this.array[y]);
			}

			@Override
			public boolean tryAdvance(CharConsumer consumer) {
				Objects.requireNonNull(consumer, "consumer");
				int index = this.index;

				if (index < CharArray.this.endIndex) {
					this.index += 2;
					consumer.accept(CharArray.this.array[index + 1]);
					return true;
				}

				return false;
			}

			@Override
			public CharArrayValueSpliterator trySplit() {
				int index = this.index;
				int midIndex = index + CharArray.this.endIndex >>> 1;

				if (index < midIndex) {
					this.index = midIndex;
					return new CharArray(
							CharArray.this.array,
							CharArray.this.beginIndex + index,
							CharArray.this.beginIndex + midIndex
					).new CharArrayMap()
							.new CharArrayValueSpliterator();
				}

				return null;
			}
		}

		/**
		 * A collection backed by the values in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public class CharArrayValues
				extends
				AbstractPrimitiveArrayValues
				implements
				CharCollection {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -7937502933699082438L;

			@Override
			public boolean add(Character value) {
				//redundant
				throw new UnsupportedOperationException("add");
			}

			@Override
			public boolean addChar(char element) {
				throw new UnsupportedOperationException("addChar");
			}

			@Override
			public CharArrayValues clone() {
				//noinspection OverridableMethodCallDuringObjectConstruction,CloneCallsConstructors
				return new CharArray(CharArray.this.copy())
						.new CharArrayMap()
						.new CharArrayValues();
			}

			@Override
			public boolean contains(char element) {
				return CharArrayMap.this.containsValue(element);
			}

			@Override
			public boolean containsAll(Collection collection) {
				Objects.requireNonNull(collection, "collection");

				Iterator iterator = collection.iterator();
				if (iterator instanceof /*Iterator*/) {
					/*Iterator*/ charIterator = (/*Iterator*/) iterator;

					while (charIterator.hasNext()) {
						if (CharArrayMap.this.containsValue(charIterator.nextChar()))
							continue;

						return false;
					}
				} else
					while (iterator.hasNext()) {
						if (CharArrayMap.this.containsValue(iterator.next()))
							continue;

						return false;
					}

				return true;
			}

			@Override
			public boolean equals(Object object) {
				return object == this;
			}

			@Override
			public void forEach(CharConsumer consumer) {
				Objects.requireNonNull(consumer, "consumer");
				for (int i = CharArray.this.beginIndex + 1;
					 i < CharArray.this.endIndex;
					 i += 2)
					consumer.accept(CharArray.this.array[i]);
			}

			@Override
			public int hashCode() {
				int hashCode = 0;

				for (int i = CharArray.this.beginIndex + 1;
					 i < CharArray.this.endIndex;
					 i += 2)
					hashCode += CharArray.this.hash(CharArray.this.array[i]);

				return hashCode;
			}

			@Override
			public CharArrayValueIterator iterator() {
				return new CharArrayValueIterator();
			}

			@Override
			public boolean remove(Object object) {
				//redundant
				throw new UnsupportedOperationException("remove");
			}

			@Override
			public boolean removeChar(char element) {
				throw new UnsupportedOperationException("removeChar");
			}

			@Override
			public boolean removeIf(CharPredicate predicate) {
				//redundant
				throw new UnsupportedOperationException("removeIf");
			}

			@Override
			public boolean removeIf(Predicate<? super Character> predicate) {
				//redundant
				throw new UnsupportedOperationException("removeIf");
			}

			@Override
			public CharArrayValueSpliterator spliterator() {
				return new CharArrayValueSpliterator();
			}

			@Override
			public Object[] toArray() {
				Object[] product = new Object[this.size()];

				for (int i = CharArray.this.beginIndex + 1,
					 j = 0;
					 i < CharArray.this.endIndex;
					 i += 2, j++)
					product[j] = CharArray.this.array[i];

				return product;
			}

			@Override
			public <T> T[] toArray(T[] array) {
				Objects.requireNonNull(array, "array");
				int length = this.size();
				T[] product = array;

				if (array.length < length)
					product = (T[]) java.lang.reflect.Array.newInstance(
							array.getClass().getComponentType(),
							length
					);
				else if (array.length > length)
					product[length] = null;

				for (int i = CharArray.this.beginIndex + 1,
					 j = 0;
					 i < CharArray.this.endIndex;
					 i += 2, j++)
					product[j] = (T) (Character) CharArray.this.array[i];

				return product;
			}

			@Override
			public char[] toCharArray() {
				char[] product = new char[this.size()];

				for (int y = CharArray.this.beginIndex + 1,
					 j = 0;
					 y < CharArray.this.endIndex;
					 y += 2, j++)
					product[j] = CharArray.this.array[y];

				return product;
			}

			@Override
			public char[] toCharArray(char[] array) {
				Objects.requireNonNull(array, "array");
				int length = this.size();
				char[] product = array;

				if (array.length < length)
					product = new char[length];
				else if (array.length > length)
					array[length] = /*DefaultValue*/;

				for (int y = CharArray.this.beginIndex + 1,
					 j = 0;
					 y < CharArray.this.endIndex;
					 y += 2, j++)
					product[j] = CharArray.this.array[y];

				return product;
			}

			@Override
			public String toString() {
				if (this.isEmpty())
					return "[]";

				StringBuilder builder = new StringBuilder("[");

				int i = CharArray.this.beginIndex + 1;
				while (true) {
					builder.append(CharArray.this.array[i]);

					if ((i += 2) >= CharArray.this.endIndex)
						return builder.append("]")
								.toString();

					builder.append(", ");
				}
			}
		}
	}

	/**
	 * A spliterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public class CharArraySpliterator
			extends
			AbstractPrimitiveArraySpliterator</*Spliterator*/>
			implements
			/*Spliterator*/ {
		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from
		 * the given {@code index}.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharArraySpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from
		 * the given {@code index}.
		 *
		 * @param beginThumb the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharArraySpliterator(int beginThumb) {
			super(beginThumb);
		}

		@Override
		public void forEachRemaining(CharConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = CharArray.this.endIndex;

			for (int i = index; i < CharArray.this.endIndex; i++)
				consumer.accept(CharArray.this.array[i]);
		}

		@Override
		public boolean tryAdvance(CharConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < CharArray.this.endIndex) {
				this.index++;
				consumer.accept(CharArray.this.array[index]);
				return true;
			}

			return false;
		}

		@Override
		public CharArraySpliterator trySplit() {
			int index = this.index;
			int midIndex = index + CharArray.this.endIndex >>> 1;

			if (index < midIndex) {
				this.index = midIndex;
				return CharArray.this.sub(index, midIndex)
						.new CharArraySpliterator();
			}

			return null;
		}
	}
}
