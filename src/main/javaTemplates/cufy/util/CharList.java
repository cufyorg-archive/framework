/*
with char|boolean|byte|double|float|int|long|short primitive
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
import cufy.util.function.*;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.UnaryOperator;
/* elif double|int|long primitive */
import java.util.Comparator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.*;
/* endif */

/**
 * A list specialized for {@code char} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface CharList
		extends
		CharCollection,
		PrimitiveList<Character, CharConsumer, /*ToDoubleFunction*/, /*ToIntFunction*/, /*ToLongFunction*/, CharUnaryOperator, CharPredicate, CharComparator> {
	@Override
	default boolean add(Character element) {
		return this.addChar(element);
	}

	@Override
	default void add(int index, Character element) {
		this.addChar(index, element);
	}

	@Override
	default boolean contains(Object object) {
		return object instanceof Character && this.contains((char) object);
	}

	@Override
	default Character get(int index) {
		return this.getChar(index);
	}

	@Override
	default int indexOf(Object object) {
		return object instanceof Character ? this.indexOf((char) object) : -1;
	}

	@Override
	default int lastIndexOf(Object object) {
		return object instanceof Character ? this.lastIndexOf((char) object) : -1;
	}

	@Override
	default boolean remove(Object object) {
		return object instanceof Character && this.removeChar((char) object);
	}

	@Override
	default Character remove(int index) {
		return this.removeCharAt(index);
	}

	@Override
	default void replaceAll(UnaryOperator<Character> operator) {
		Objects.requireNonNull(operator, "operator");
		this.replaceAll(
				operator instanceof CharUnaryOperator ?
				(CharUnaryOperator) operator :
				operator::apply
		);
	}

	@Override
	default void replaceAll(CharUnaryOperator operator) {
		Objects.requireNonNull(operator, "operator");
		CharListIterator iterator = this.listIterator();
		while (iterator.hasNext())
			iterator.set(operator.applyAsChar(iterator.next()));
	}

	@Override
	default Character set(int index, Character element) {
		return this.setChar(index, element);
	}

	@Override
	default void sort(Comparator<? super Character> comparator) {
		this.sort(
				comparator == null ||
				comparator instanceof CharComparator ?
				(CharComparator) comparator :
				comparator::compare
		);
	}
	/*
	if double|int|long primitive
	*/

	@Override
	default /* Spliterator */ spliterator() {
		return Spliterators.spliteratorUnknownSize(this.iterator(), 0);
	}
	/*
	endif
	*/

	@Override
	CharListIterator listIterator();

	@Override
	CharListIterator listIterator(int index);

	@Override
	CharList subList(int beginIndex, int endIndex);

	/**
	 * Inserts the specified element at the specified position in this list (optional operation).
	 * Shifts the element currently at that position (if any) and any subsequent elements to the
	 * right (adds one to their indices).
	 *
	 * @param index   index at which the specified element is to be inserted.
	 * @param element element to be inserted.
	 * @throws UnsupportedOperationException if the {@code add} operation is not supported by this
	 *                                       list.
	 * @throws IllegalArgumentException      if some property of the specified element prevents it
	 *                                       from being added to this list.
	 * @throws IndexOutOfBoundsException     if the index is out of range ({@code index < 0 || index
	 *                                       > size()}).
	 * @since 0.1.5 ~2020.09.01
	 */
	void addChar(int index, char element);

	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @param index index of the element to return.
	 * @return the element at the specified position in this list.
	 * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >=
	 *                                   size()}).
	 * @since 0.1.5 ~2020.09.01
	 */
	char getChar(int index);

	/**
	 * Returns the index of the first occurrence of the specified element in this list, or -1 if
	 * this list does not contain the element.
	 *
	 * @param element element to search for.
	 * @return the index of the first occurrence of the specified element in this list, or -1 if
	 * 		this list does not contain the element.
	 * @since 0.1.5 ~2020.09.01
	 */
	int indexOf(char element);

	/**
	 * Returns the index of the last occurrence of the specified element in this list, or -1 if this
	 * list does not contain the element.
	 *
	 * @param element element to search for.
	 * @return the index of the last occurrence of the specified element in this list, or -1 if this
	 * 		list does not contain the element.
	 * @since 0.1.5 ~2020.09.01
	 */
	int lastIndexOf(char element);

	/**
	 * Removes the element at the specified position in this list (optional operation). Shifts any
	 * subsequent elements to the left (subtracts one from their indices).  Returns the element that
	 * was removed from the list.
	 *
	 * @param index the index of the element to be removed.
	 * @return the element previously at the specified position.
	 * @throws UnsupportedOperationException if the {@code remove} operation is not supported by
	 *                                       this list.
	 * @throws IndexOutOfBoundsException     if the index is out of range ({@code index < 0 || index
	 *                                       >= size()}).
	 * @since 0.1.5 ~2020.09.01
	 */
	char removeCharAt(int index);

	/**
	 * Replaces the element at the specified position in this list with the specified element
	 * (optional operation).
	 *
	 * @param index   index of the element to replace.
	 * @param element element to be stored at the specified position.
	 * @return the element previously at the specified position.
	 * @throws UnsupportedOperationException if the {@code set} operation is not supported by this
	 *                                       list.
	 * @throws IllegalArgumentException      if some property of the specified element prevents it
	 *                                       from being added to this list.
	 * @throws IndexOutOfBoundsException     if the index is out of range ({@code index < 0 || index
	 *                                       >= size()}).
	 * @since 0.1.5 ~2020.09.01
	 */
	char setChar(int index, char element);
}
