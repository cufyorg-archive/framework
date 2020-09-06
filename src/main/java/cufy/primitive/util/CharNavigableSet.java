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
 * A navigable set for {@code char} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface CharNavigableSet extends CharSortedSet, PrimitiveNavigableSet<
		Character,
		CharConsumer,
		CharPredicate,
		CharUnaryOperator,
		CharToDoubleFunction,
		CharToIntFunction,
		CharToLongFunction,
		CharComparator,
		CharIterator,
		CharSpliterator,
		CharCollection,
		CharSet,
		CharSortedSet,
		CharNavigableSet
		> {
	@Override
	default Character ceiling(Character element) {
		return this.hasCeiling(element) ?
			   this.ceilingChar(element) :
			   null;
	}

	@Override
	default Character floor(Character element) {
		return this.hasFloor(element) ?
			   this.floorChar(element) :
			   null;
	}

	@Override
	default CharNavigableSet headSet(Character endElement, boolean inclusive) {
		return this.headSet((char) endElement, inclusive);
	}

	@Override
	default Character higher(Character element) {
		return this.hasHigher(element) ?
			   this.higherChar(element) :
			   null;
	}

	@Override
	default Character lower(Character element) {
		return this.hasLower(element) ?
			   this.lowerChar(element) :
			   null;
	}

	@Override
	default Character pollFirst() {
		return this.isEmpty() ?
			   null :
			   this.pollFirstChar();
	}

	@Override
	default Character pollLast() {
		return this.isEmpty() ?
			   null :
			   this.pollLastChar();
	}

	@Override
	default CharNavigableSet subSet(Character beginElement, boolean beginInclusive, Character endElement, boolean endInclusive) {
		return this.subSet((char) beginElement, beginInclusive, (char) endElement, endInclusive);
	}

	@Override
	default CharNavigableSet tailSet(Character beginElement, boolean inclusive) {
		return this.tailSet((char) beginElement, inclusive);
	}

	/**
	 * Returns the least element in this set greater than or equal to the given element, or the
	 * {@code default char value} if no such element.
	 *
	 * @param element the value to match.
	 * @return the least element greater than or equal to {@code element}, or the {@code default
	 * 		char value} if no such element.
	 * @since 0.1.5 ~2020.09.01
	 */
	char ceilingChar(char element);

	/**
	 * Returns the greatest element in this set less than or equal to the given element, or the
	 * {@code default char value} if no such element.
	 *
	 * @param element the value to match.
	 * @return the greatest element less than or equal to {@code element}, or the {@code default
	 * 		char value} if no such element.
	 * @since 0.1.5 ~2020.09.01
	 */
	char floorChar(char element);

	/**
	 * Return true if there is a least element in this set greater than or equal to the given {@code
	 * element}.
	 *
	 * @param element the element to match.
	 * @return true, if there is a least element in this set greater than or equal to the given
	 *        {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasCeiling(char element);

	/**
	 * Return true if there is a greatest element in this less than or equal to the given {@code
	 * element}.
	 *
	 * @param element the element to match.
	 * @return true, if there is a greatest element less than or equal to the given {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasFloor(char element);

	/**
	 * Return true if there is a least element in this set strictly greater than the given {@code
	 * element}.
	 *
	 * @param element the element to match
	 * @return true, if there is a least element greater than teh given {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasHigher(char element);

	/**
	 * Return true if there is a greatest element in this set strictly less than the given {@code
	 * element}.
	 *
	 * @param element the element to match.
	 * @return true, if there is a greatest element less than the given {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasLower(char element);

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
	CharNavigableSet headSet(char endElement, boolean inclusive);

	/**
	 * Returns the least element in this set strictly greater than the given element, or the {@code
	 * default char value} if no such element.
	 *
	 * @param element the value to match.
	 * @return the least element greater than {@code element}, or the {@code default char value} if
	 * 		no such element.
	 * @since 0.1.5 ~2020.09.01
	 */
	char higherChar(char element);

	/**
	 * Returns the greatest element in this set strictly less than the given element, or the {@code
	 * default char value} if no such element.
	 *
	 * @param element the value to match.
	 * @return the greatest element less than {@code element}, or the {@code default char value} if
	 * 		no such element.
	 * @since 0.1.5 ~2020.09.01
	 */
	char lowerChar(char element);

	/**
	 * Retrieves and removes the first (lowest) element, or the {@code default char value} if no
	 * such element.
	 *
	 * @return the first element, or the {@code default char value} if no such element.
	 * @since 0.1.5 ~2020.09.01
	 */
	char pollFirstChar();

	/**
	 * Retrieves and removes the last (highest) element, or the {@code default double value} if no
	 * such element.
	 *
	 * @return the last element, or the {@code default char value} if no such element.
	 * @since 0.1.5 ~2020.09.01
	 */
	char pollLastChar();

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
	CharNavigableSet subSet(char beginElement, boolean beginInclusive, char endElement, boolean endInclusive);

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
	CharNavigableSet tailSet(char beginElement, boolean inclusive);
}
