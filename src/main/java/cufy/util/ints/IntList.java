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
package cufy.util.ints;

import cufy.util.PrimitiveList;

import java.util.Comparator;
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.*;

/**
 * A list specialized for {@code int} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface IntList extends IntCollection, PrimitiveList<
		Integer,
		IntConsumer,
		IntPredicate,
		IntUnaryOperator,
		IntToDoubleFunction,
		IntUnaryOperator,
		IntToLongFunction,
		IntComparator,
		PrimitiveIterator.OfInt,
		IntListIterator,
		Spliterator.OfInt,
		IntCollection,
		IntList
		> {
	@Override
	default void add(int index, Integer element) {
		this.addInt(index, element);
	}

	@Override
	default Integer get(int index) {
		return this.getInt(index);
	}

	@Override
	default int indexOf(Object object) {
		return object instanceof Integer ? this.indexOf((int) object) : -1;
	}

	@Override
	default int lastIndexOf(Object object) {
		return object instanceof Integer ? this.lastIndexOf((int) object) : -1;
	}

	@Override
	default Integer remove(int index) {
		return this.removeIntAt(index);
	}

	@Override
	default void replaceAll(IntUnaryOperator operator) {
		Objects.requireNonNull(operator, "operator");
		IntListIterator iterator = this.listIterator();
		while (iterator.hasNext())
			iterator.set(operator.applyAsInt(iterator.next()));
	}

	@Override
	default void replaceAll(UnaryOperator<Integer> operator) {
		Objects.requireNonNull(operator, "operator");
		this.replaceAll(
				operator instanceof IntUnaryOperator ?
				(IntUnaryOperator) operator :
				operator::apply
		);
	}

	@Override
	default Integer set(int index, Integer element) {
		return this.setInt(index, element);
	}

	@Override
	default void sort(Comparator<? super Integer> comparator) {
		this.sort(
				comparator == null ||
				comparator instanceof IntComparator ?
				(IntComparator) comparator :
				comparator::compare
		);
	}

	@Override
	default Spliterator.OfInt spliterator() {
		return IntCollection.super.spliterator();
	}

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
	void addInt(int index, int element);

	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @param index index of the element to return.
	 * @return the element at the specified position in this list.
	 * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >=
	 *                                   size()}).
	 * @since 0.1.5 ~2020.09.01
	 */
	int getInt(int index);

	/**
	 * Returns the index of the first occurrence of the specified element in this list, or -1 if
	 * this list does not contain the element.
	 *
	 * @param element element to search for.
	 * @return the index of the first occurrence of the specified element in this list, or -1 if
	 * 		this list does not contain the element.
	 * @since 0.1.5 ~2020.09.01
	 */
	int indexOf(int element);

	/**
	 * Returns the index of the last occurrence of the specified element in this list, or -1 if this
	 * list does not contain the element.
	 *
	 * @param element element to search for.
	 * @return the index of the last occurrence of the specified element in this list, or -1 if this
	 * 		list does not contain the element.
	 * @since 0.1.5 ~2020.09.01
	 */
	int lastIndexOf(int element);

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
	int removeIntAt(int index);

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
	int setInt(int index, int element);
}
