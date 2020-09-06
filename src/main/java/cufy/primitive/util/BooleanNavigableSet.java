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

import cufy.primitive.util.function.*;
import cufy.util.PrimitiveNavigableSet;

/**
 * A navigable set for {@code boolean} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface BooleanNavigableSet extends BooleanSortedSet, PrimitiveNavigableSet<
		Boolean,
		BooleanConsumer,
		BooleanPredicate,
		BooleanUnaryOperator,
		BooleanToDoubleFunction,
		BooleanToIntFunction,
		BooleanToLongFunction,
		BooleanComparator,
		BooleanIterator,
		BooleanSpliterator,
		BooleanCollection,
		BooleanSet,
		BooleanSortedSet,
		BooleanNavigableSet
		> {
	@Override
	default Boolean ceiling(Boolean element) {
		return this.hasCeiling(element) ?
			   this.ceilingBoolean(element) :
			   null;
	}

	@Override
	default Boolean floor(Boolean element) {
		return this.hasFloor(element) ?
			   this.floorBoolean(element) :
			   null;
	}

	@Override
	default BooleanNavigableSet headSet(Boolean endElement, boolean inclusive) {
		return this.headSet((boolean) endElement, inclusive);
	}

	@Override
	default Boolean higher(Boolean element) {
		return this.hasHigher(element) ?
			   this.higherBoolean(element) :
			   null;
	}

	@Override
	default Boolean lower(Boolean element) {
		return this.hasLower(element) ?
			   this.lowerBoolean(element) :
			   null;
	}

	@Override
	default Boolean pollFirst() {
		return this.isEmpty() ?
			   null :
			   this.pollFirstBoolean();
	}

	@Override
	default Boolean pollLast() {
		return this.isEmpty() ?
			   null :
			   this.pollLastBoolean();
	}

	@Override
	default BooleanNavigableSet subSet(Boolean beginElement, boolean beginInclusive, Boolean endElement, boolean endInclusive) {
		return this.subSet((boolean) beginElement, beginInclusive, (boolean) endElement, endInclusive);
	}

	@Override
	default BooleanNavigableSet tailSet(Boolean beginElement, boolean inclusive) {
		return this.tailSet((boolean) beginElement, inclusive);
	}

	/**
	 * Returns the least element in this set greater than or equal to the given element, or the
	 * {@code default byte value} if no such element.
	 *
	 * @param element the value to match.
	 * @return the least element greater than or equal to {@code element}, or the {@code default
	 * 		byte value} if no such element.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean ceilingBoolean(boolean element);

	/**
	 * Returns the greatest element in this set less than or equal to the given element, or the
	 * {@code default byte value} if no such element.
	 *
	 * @param element the value to match.
	 * @return the greatest element less than or equal to {@code element}, or the {@code default
	 * 		byte value} if no such element.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean floorBoolean(boolean element);

	/**
	 * Return true if there is a least element in this set greater than or equal to the given {@code
	 * element}.
	 *
	 * @param element the element to match.
	 * @return true, if there is a least element in this set greater than or equal to the given
	 *        {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasCeiling(boolean element);

	/**
	 * Return true if there is a greatest element in this less than or equal to the given {@code
	 * element}.
	 *
	 * @param element the element to match.
	 * @return true, if there is a greatest element less than or equal to the given {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasFloor(boolean element);

	/**
	 * Return true if there is a least element in this set strictly greater than the given {@code
	 * element}.
	 *
	 * @param element the element to match
	 * @return true, if there is a least element greater than teh given {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasHigher(boolean element);

	/**
	 * Return true if there is a greatest element in this set strictly less than the given {@code
	 * element}.
	 *
	 * @param element the element to match.
	 * @return true, if there is a greatest element less than the given {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasLower(boolean element);

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
	BooleanNavigableSet headSet(boolean endElement, boolean inclusive);

	/**
	 * Returns the least element in this set strictly greater than the given element, or the {@code
	 * default byte value} if no such element.
	 *
	 * @param element the value to match.
	 * @return the least element greater than {@code element}, or the {@code default byte value} if
	 * 		no such element.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean higherBoolean(boolean element);

	/**
	 * Returns the greatest element in this set strictly less than the given element, or the {@code
	 * default byte value} if no such element.
	 *
	 * @param element the value to match.
	 * @return the greatest element less than {@code element}, or the {@code default byte value} if
	 * 		no such element.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean lowerBoolean(boolean element);

	/**
	 * Retrieves and removes the first (lowest) element, or the {@code default byte value} if no
	 * such element.
	 *
	 * @return the first element, or the {@code default byte value} if no such element.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean pollFirstBoolean();

	/**
	 * Retrieves and removes the last (highest) element, or the {@code default byte value} if no
	 * such element.
	 *
	 * @return the last element, or the {@code default byte value} if no such element.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean pollLastBoolean();

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
	BooleanNavigableSet subSet(boolean beginElement, boolean beginInclusive, boolean endElement, boolean endInclusive);

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
	BooleanNavigableSet tailSet(boolean beginElement, boolean inclusive);
}
