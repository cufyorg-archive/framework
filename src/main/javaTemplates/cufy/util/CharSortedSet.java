/*
with char|boolean|byte|double|float|int|long|short primitive
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

/* if double|int|long primitive */
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.CharConsumer;
import java.util.function.CharPredicate;
/* elif boolean|byte|char|float|short primitive */
import cufy.util.function.CharConsumer;
import cufy.util.function.CharPredicate;
/* endif */

import java.util.NoSuchElementException;

/**
 * a sorted set for {@code char} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface CharSortedSet
		extends
		CharSet,
		PrimitiveSortedSet<Character, CharConsumer, CharPredicate> {
	@Override
	default boolean add(Character element) {
		return this.addChar(element);
	}

	@Override
	default boolean contains(Object object) {
		return object instanceof Character && this.contains((char) object);
	}

	@Override
	default Character first() {
		return this.firstChar();
	}

	@Override
	default CharSortedSet headSet(Character endElement) {
		return this.headSet((char) endElement);
	}

	@Override
	default Character last() {
		return this.lastChar();
	}

	@Override
	default boolean remove(Object object) {
		return object instanceof Character && this.removeChar((char) object);
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
	default CharSortedSet subSet(Character beginElement, Character endElement) {
		return this.subSet((char) beginElement, (char) endElement);
	}

	@Override
	default CharSortedSet tailSet(Character beginElement) {
		return this.tailSet((char) beginElement);
	}

	@Override
	CharComparator comparator();

	/**
	 * Returns the first (lowest) element currently in this set.
	 *
	 * @return the first (lowest) element currently in this set.
	 * @throws NoSuchElementException if this set is empty.
	 * @since 0.1.5 ~2020.09.01
	 */
	char firstChar();

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
	CharSortedSet headSet(char endElement);

	/**
	 * Returns the last (highest) element currently in this set.
	 *
	 * @return the last (highest) element currently in this set.
	 * @throws NoSuchElementException if this set is empty.
	 * @since 0.1.5 ~2020.09.01
	 */
	char lastChar();

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
	CharSortedSet subSet(char beginElement, char endElement);

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
	CharSortedSet tailSet(char beginElement);
}
