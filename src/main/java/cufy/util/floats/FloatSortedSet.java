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

import cufy.util.PrimitiveSortedSet;
import cufy.util.floats.function.*;

import java.util.NoSuchElementException;

/**
 * a sorted set for {@code float} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface FloatSortedSet extends FloatSet, PrimitiveSortedSet<
		Float,
		FloatConsumer,
		FloatPredicate,
		FloatUnaryOperator,
		FloatToDoubleFunction,
		FloatToIntFunction,
		FloatToLongFunction,
		FloatComparator,
		FloatIterator,
		FloatSpliterator,
		FloatCollection,
		FloatSet,
		FloatSortedSet
		> {
	@Override
	default Float first() {
		return this.firstFloat();
	}

	@Override
	default FloatSortedSet headSet(Float endElement) {
		return this.headSet((float) endElement);
	}

	@Override
	default Float last() {
		return this.lastFloat();
	}

	@Override
	default FloatSortedSet subSet(Float beginElement, Float endElement) {
		return this.subSet((float) beginElement, (float) endElement);
	}

	@Override
	default FloatSortedSet tailSet(Float beginElement) {
		return this.tailSet((float) beginElement);
	}

	/**
	 * Returns the first (lowest) element currently in this set.
	 *
	 * @return the first (lowest) element currently in this set.
	 * @throws NoSuchElementException if this set is empty.
	 * @since 0.1.5 ~2020.09.01
	 */
	float firstFloat();

	/**
	 * Returns a view of the portion of this set whose elements are strictly less than {@code
	 * endElement}. The returned set is backed by this set, so changes in the returned set are
	 * reflected in this set, and vice-versa. The returned set supports all optional set operations
	 * that this set supports.
	 * <p>
	 * The returned set will throw an <tt>IllegalArgumentException</tt> on an attempt to insert an
	 * element outside its range.
	 *
	 * @param endElement high endpoint (exclusive) of the returned set.
	 * @return a view of the portion of this set whose elements are strictly less than {@code
	 * 		endElement}.
	 * @throws IllegalArgumentException if this set itself has a restricted range, and {@code
	 *                                  endElement} lies outside the bounds of the range.
	 * @since 0.1.5 ~2020.09.01
	 */
	FloatSortedSet headSet(float endElement);

	/**
	 * Returns the last (highest) element currently in this set.
	 *
	 * @return the last (highest) element currently in this set.
	 * @throws NoSuchElementException if this set is empty.
	 * @since 0.1.5 ~2020.09.01
	 */
	float lastFloat();

	/**
	 * Returns a view of the portion of this set whose elements range from {@code beginElement},
	 * inclusive, to {@code endElement}, exclusive.  (If {@code beginElement} and {@code endElement}
	 * are equal, the returned set is empty.) The returned set is backed by this set, so changes in
	 * the returned set are reflected in this set, and vice-versa. The returned set supports all
	 * optional set operations that this set supports.
	 * <p>
	 * The returned set will throw an <tt>IllegalArgumentException</tt> on an attempt to insert an
	 * element outside its range.
	 *
	 * @param beginElement low endpoint (inclusive) of the returned set.
	 * @param endElement   high endpoint (exclusive) of the returned set.
	 * @return a view of the portion of this set whose elements range from {@code beginElement},
	 * 		inclusive, to {@code endElement}, exclusive.
	 * @throws IllegalArgumentException if {@code beginElement} is greater than {@code endElement};
	 *                                  or if this set itself has a restricted range, and {@code
	 *                                  fromElement} or {@code toElement} lies outside the bounds of
	 *                                  the range.
	 * @since 0.1.5 ~2020.09.01
	 */
	FloatSortedSet subSet(float beginElement, float endElement);

	/**
	 * Returns a view of the portion of this set whose elements are greater than or equal to {@code
	 * beginElement}. The returned set is backed by this set, so changes in the returned set are
	 * reflected in this set, and vice-versa. The returned set supports all optional set operations
	 * that this set supports.
	 * <p>
	 * The returned set will throw an <tt>IllegalArgumentException</tt> on an attempt to insert an
	 * element outside its range.
	 *
	 * @param beginElement low endpoint (inclusive) of the returned set.
	 * @return a view of the portion of this set whose elements are greater than or equal to {@code
	 * 		beginElement}
	 * @throws IllegalArgumentException if this set itself has a restricted range, and {@code
	 *                                  beginElement} lies outside the bounds of the range.
	 * @since 0.1.5 ~2020.09.01
	 */
	FloatSortedSet tailSet(float beginElement);
}
