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

import cufy.util.PrimitiveNavigableSet;
import cufy.util.floats.function.*;

/**
 * A navigable set for {@code float} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface FloatNavigableSet extends FloatSortedSet, PrimitiveNavigableSet<
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
		FloatSortedSet,
		FloatNavigableSet
		> {
	@Override
	default Float ceiling(Float element) {
		return this.hasCeiling(element) ?
			   this.ceilingFloat(element) :
			   null;
	}

	@Override
	default Float floor(Float element) {
		return this.hasFloor(element) ?
			   this.floorFloat(element) :
			   null;
	}

	@Override
	default FloatNavigableSet headSet(Float endElement, boolean inclusive) {
		return this.headSet((float) endElement, inclusive);
	}

	@Override
	default Float higher(Float element) {
		return this.hasHigher(element) ?
			   this.higherFloat(element) :
			   null;
	}

	@Override
	default Float lower(Float element) {
		return this.hasLower(element) ?
			   this.lowerFloat(element) :
			   null;
	}

	@Override
	default Float pollFirst() {
		return this.isEmpty() ?
			   null :
			   this.pollFirstFloat();
	}

	@Override
	default Float pollLast() {
		return this.isEmpty() ?
			   null :
			   this.pollLastFloat();
	}

	@Override
	default FloatNavigableSet subSet(Float beginElement, boolean beginInclusive, Float endElement, boolean endInclusive) {
		return this.subSet((float) beginElement, beginInclusive, (float) endElement, endInclusive);
	}

	@Override
	default FloatNavigableSet tailSet(Float beginElement, boolean inclusive) {
		return this.tailSet((float) beginElement, inclusive);
	}

	/**
	 * Returns the least element in this set greater than or equal to the given element.
	 *
	 * @param element the value to match.
	 * @return the least element greater than or equal to {@code element}.
	 * @throws java.util.NoSuchElementException if there is no such element.
	 * @see #hasCeiling(float)
	 * @since 0.1.5 ~2020.09.01
	 */
	float ceilingFloat(float element);

	/**
	 * Returns the greatest element in this set less than or equal to the given element.
	 *
	 * @param element the value to match.
	 * @return the greatest element less than or equal to {@code element}.
	 * @throws java.util.NoSuchElementException if there is no such element.
	 * @see #hasFloor(float)
	 * @since 0.1.5 ~2020.09.01
	 */
	float floorFloat(float element);

	/**
	 * Return true if there is a least element in this set greater than or equal to the given {@code
	 * element}.
	 *
	 * @param element the element to match.
	 * @return true, if there is a least element in this set greater than or equal to the given
	 *        {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasCeiling(float element);

	/**
	 * Return true if there is a greatest element in this less than or equal to the given {@code
	 * element}.
	 *
	 * @param element the element to match.
	 * @return true, if there is a greatest element less than or equal to the given {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasFloor(float element);

	/**
	 * Return true if there is a least element in this set strictly greater than the given {@code
	 * element}.
	 *
	 * @param element the element to match
	 * @return true, if there is a least element greater than teh given {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasHigher(float element);

	/**
	 * Return true if there is a greatest element in this set strictly less than the given {@code
	 * element}.
	 *
	 * @param element the element to match.
	 * @return true, if there is a greatest element less than the given {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasLower(float element);

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
	FloatNavigableSet headSet(float endElement, boolean inclusive);

	/**
	 * Returns the least element in this set strictly greater than the given element.
	 *
	 * @param element the value to match.
	 * @return the least element greater than {@code element}.
	 * @throws java.util.NoSuchElementException if there is no such element.
	 * @see #hasHigher(float)
	 * @since 0.1.5 ~2020.09.01
	 */
	float higherFloat(float element);

	/**
	 * Returns the greatest element in this set strictly less than the given element.
	 *
	 * @param element the value to match.
	 * @return the greatest element less than {@code element}.
	 * @throws java.util.NoSuchElementException if there is no such element.
	 * @see #hasLower(float)
	 * @since 0.1.5 ~2020.09.01
	 */
	float lowerFloat(float element);

	/**
	 * Retrieves and removes the first (lowest) element.
	 *
	 * @return the first element.
	 * @throws java.util.NoSuchElementException if this set is empty.
	 * @since 0.1.5
	 */
	float pollFirstFloat();

	/**
	 * Retrieves and removes the last (highest) element.
	 *
	 * @return the last element.
	 * @throws java.util.NoSuchElementException if this set is empty.
	 * @since 0.1.5
	 */
	float pollLastFloat();

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
	FloatNavigableSet subSet(float beginElement, boolean beginInclusive, float endElement, boolean endInclusive);

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
	FloatNavigableSet tailSet(float beginElement, boolean inclusive);
}
