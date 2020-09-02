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
package cufy.util.shorts;

import cufy.util.PrimitiveNavigableSet;
import cufy.util.shorts.function.*;

/**
 * A navigable set for {@code short} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface ShortNavigableSet extends ShortSortedSet, PrimitiveNavigableSet<
		Short,
		ShortConsumer,
		ShortPredicate,
		ShortUnaryOperator,
		ShortToDoubleFunction,
		ShortToIntFunction,
		ShortToLongFunction,
		ShortComparator,
		ShortIterator,
		ShortSpliterator,
		ShortCollection,
		ShortSet,
		ShortSortedSet,
		ShortNavigableSet
		> {
	@Override
	default Short ceiling(Short element) {
		return this.hasCeiling(element) ?
			   this.ceilingShort(element) :
			   null;
	}

	@Override
	default Short floor(Short element) {
		return this.hasFloor(element) ?
			   this.floorShort(element) :
			   null;
	}

	@Override
	default ShortNavigableSet headSet(Short endElement, boolean inclusive) {
		return this.headSet((short) endElement, inclusive);
	}

	@Override
	default Short higher(Short element) {
		return this.hasHigher(element) ?
			   this.higherShort(element) :
			   null;
	}

	@Override
	default Short lower(Short element) {
		return this.hasLower(element) ?
			   this.lowerShort(element) :
			   null;
	}

	@Override
	default Short pollFirst() {
		return this.isEmpty() ?
			   null :
			   this.pollFirstShort();
	}

	@Override
	default Short pollLast() {
		return this.isEmpty() ?
			   null :
			   this.pollLastShort();
	}

	@Override
	default ShortNavigableSet subSet(Short beginElement, boolean beginInclusive, Short endElement, boolean endInclusive) {
		return this.subSet((short) beginElement, beginInclusive, (short) endElement, endInclusive);
	}

	@Override
	default ShortNavigableSet tailSet(Short beginElement, boolean inclusive) {
		return this.tailSet((short) beginElement, inclusive);
	}

	/**
	 * Returns the least element in this set greater than or equal to the given element, or the
	 * {@code default short value} if no such element.
	 *
	 * @param element the value to match.
	 * @return the least element greater than or equal to {@code element}, or the {@code default
	 * 		short value} if no such element.
	 * @since 0.1.5 ~2020.09.01
	 */
	short ceilingShort(short element);

	/**
	 * Returns the greatest element in this set less than or equal to the given element, or the
	 * {@code default short value} if no such element.
	 *
	 * @param element the value to match.
	 * @return the greatest element less than or equal to {@code element}, or the {@code default
	 * 		short value} if no such element.
	 * @since 0.1.5 ~2020.09.01
	 */
	short floorShort(short element);

	/**
	 * Return true if there is a least element in this set greater than or equal to the given {@code
	 * element}.
	 *
	 * @param element the element to match.
	 * @return true, if there is a least element in this set greater than or equal to the given
	 *        {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasCeiling(short element);

	/**
	 * Return true if there is a greatest element in this less than or equal to the given {@code
	 * element}.
	 *
	 * @param element the element to match.
	 * @return true, if there is a greatest element less than or equal to the given {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasFloor(short element);

	/**
	 * Return true if there is a least element in this set strictly greater than the given {@code
	 * element}.
	 *
	 * @param element the element to match
	 * @return true, if there is a least element greater than teh given {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasHigher(short element);

	/**
	 * Return true if there is a greatest element in this set strictly less than the given {@code
	 * element}.
	 *
	 * @param element the element to match.
	 * @return true, if there is a greatest element less than the given {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasLower(short element);

	/**
	 * Returns a view of the portion of this set whose elements are less than (or equal to, if
	 * {@code inclusive} is true) {@code endElement}. The returned set is backed by this set, so
	 * changes in the returned set are reflected in this set, and vice-versa. The returned set
	 * supports all optional set operations that this set supports.
	 * <p>
	 * The returned set will throw an {@code IllegalArgumentException} on an attempt to insert an
	 * element outside its range.
	 *
	 * @param endElement high endpoint of the returned set.
	 * @param inclusive  {@code true} if the high endpoint is to be included in the returned view.
	 * @return a view of the portion of this set whose elements are less than (or equal to, if
	 *        {@code inclusive} is true) {@code endElement}.
	 * @throws IllegalArgumentException if this set itself has a restricted range, and {@code
	 *                                  endElement} lies outside the bounds of the range.
	 * @since 0.1.5 ~2020.09.01
	 */
	ShortNavigableSet headSet(short endElement, boolean inclusive);

	/**
	 * Returns the least element in this set strictly greater than the given element, or the {@code
	 * default short value} if no such element.
	 *
	 * @param element the value to match.
	 * @return the least element greater than {@code element}, or the {@code default short value} if
	 * 		no such element.
	 * @since 0.1.5 ~2020.09.01
	 */
	short higherShort(short element);

	/**
	 * Returns the greatest element in this set strictly less than the given element, or the {@code
	 * default short value} if no such element.
	 *
	 * @param element the value to match.
	 * @return the greatest element less than {@code element}, or the {@code default short value} if
	 * 		no such element.
	 * @since 0.1.5 ~2020.09.01
	 */
	short lowerShort(short element);

	/**
	 * Retrieves and removes the first (lowest) element, or the {@code default short value} if no
	 * such element.
	 *
	 * @return the first element, or the {@code default short value} if no such element.
	 * @since 0.1.5 ~2020.09.01
	 */
	short pollFirstShort();

	/**
	 * Retrieves and removes the last (highest) element, or the {@code default short value} if no
	 * such element.
	 *
	 * @return the last element, or the {@code default short value} if no such element.
	 * @since 0.1.5 ~2020.09.01
	 */
	short pollLastShort();

	/**
	 * Returns a view of the portion of this set whose elements range from {@code beginElement} to
	 * {@code endElement}. If {@code beginElement} and {@code endElement} are equal, the returned
	 * set is empty unless {@code beginInclusive} and {@code endInclusive} are both true. The
	 * returned set is backed by this set, so changes in the returned set are reflected in this set,
	 * and vice-versa. The returned set supports all optional set operations that this set
	 * supports.
	 * <p>
	 * The returned set will throw an {@code IllegalArgumentException} on an attempt to insert an
	 * element outside its range.
	 *
	 * @param beginElement   low endpoint of the returned set.
	 * @param beginInclusive {@code true} if the low endpoint is to be included in the returned
	 *                       view.
	 * @param endElement     high endpoint of the returned set.
	 * @param endInclusive   {@code true} if the high endpoint is to be included in the returned
	 *                       view.
	 * @return a view of the portion of this set whose elements range from {@code beginElement},
	 * 		inclusive, to {@code endElement}, exclusive.
	 * @throws IllegalArgumentException if {@code beginElement} is greater than {@code endElement};
	 *                                  or if this set itself has a restricted range, and {@code
	 *                                  beginElement} or {@code endElement} lies outside the bounds
	 *                                  of the range.
	 * @since 0.1.5 ~2020.09.01
	 */
	ShortNavigableSet subSet(short beginElement, boolean beginInclusive, short endElement, boolean endInclusive);

	/**
	 * Returns a view of the portion of this set whose elements are greater than (or equal to, if
	 * {@code inclusive} is true) {@code beginElement}. The returned set is backed by this set, so
	 * changes in the returned set are reflected in this set, and vice-versa. The returned set
	 * supports all optional set operations that this set supports.
	 * <p>
	 * The returned set will throw an {@code IllegalArgumentException} on an attempt to insert an
	 * element outside its range.
	 *
	 * @param beginElement low endpoint of the returned set.
	 * @param inclusive    {@code true} if the low endpoint is to be included in the returned view.
	 * @return a view of the portion of this set whose elements are greater than or equal to {@code
	 * 		beginElement}.
	 * @throws IllegalArgumentException if this set itself has a restricted range, and {@code
	 *                                  beginElement} lies outside the bounds of the range.
	 * @since 0.1.5 ~2020.09.01
	 */
	ShortNavigableSet tailSet(short beginElement, boolean inclusive);
}
