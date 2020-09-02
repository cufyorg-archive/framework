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
package cufy.booleans.util;

import cufy.booleans.util.function.BooleanConsumer;
import cufy.util.PrimitiveListIterator;

import java.util.NoSuchElementException;

/**
 * A ListIterator specialized for {@code boolean} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.31
 */
public interface BooleanListIterator extends BooleanIterator, PrimitiveListIterator<
		Boolean,
		BooleanConsumer
		> {
	@Override
	default void add(Boolean value) {
		this.addBoolean(value);
	}

	@SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
	@Override
	default Boolean next() {
		return this.nextBoolean();
	}

	@Override
	default Boolean previous() {
		return this.previousBoolean();
	}

	@Override
	default void set(Boolean value) {
		this.setBoolean(value);
	}

	/**
	 * Inserts the specified element into the list (optional operation). The element is inserted
	 * immediately before the element that would be returned by {@link #next}, if any, and after the
	 * element that would be returned by {@link #previous}, if any.  (If the list contains no
	 * elements, the new element becomes the sole element on the list.)  The new element is inserted
	 * before the implicit cursor: a subsequent call to {@code next} would be unaffected, and a
	 * subsequent call to {@code previous} would return the new element. (This call increases by one
	 * the value that would be returned by a call to {@code nextIndex} or {@code previousIndex}.)
	 *
	 * @param value the element to insert.
	 * @throws UnsupportedOperationException if the {@code add} method is not supported by this list
	 *                                       iterator.
	 * @throws IllegalArgumentException      if some aspect of the specified element prevents it
	 *                                       from being added to this list.
	 * @since 0.1.5 ~2020.08.31
	 */
	void addBoolean(boolean value);

	/**
	 * Returns the previous element in the list and moves the cursor position backwards.  This
	 * method may be called repeatedly to iterate through the list backwards, or intermixed with
	 * calls to {@link #next} to go back and forth.  (Note that alternating calls to {@code next}
	 * and {@code previous} will return the same element repeatedly.)
	 *
	 * @return the previous element in the list.
	 * @throws NoSuchElementException if the iteration has no previous element.
	 * @since 0.1.5 ~2020.08.31
	 */
	boolean previousBoolean();

	/**
	 * Replaces the last element returned by {@link #next} or {@link #previous} with the specified
	 * element (optional operation). This call can be made only if neither {@link #remove} nor
	 * {@link #add} have been called after the last call to {@code next} or {@code previous}.
	 *
	 * @param value the element with which to replace the last element returned by {@code next} or
	 *              {@code previous}.
	 * @throws UnsupportedOperationException if the {@code set} operation is not supported by this
	 *                                       list iterator.
	 * @throws IllegalArgumentException      if some aspect of the specified element prevents it
	 *                                       from being added to this list.
	 * @throws IllegalStateException         if neither {@code next} nor {@code previous} have been
	 *                                       called, or {@code remove} or {@code add} have been
	 *                                       called after the last call to {@code next} or {@code
	 *                                       previous}.
	 * @since 0.1.5 ~2020.08.31
	 */
	void setBoolean(boolean value);
}
