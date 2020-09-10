/*
with char|boolean|byte|double|float|int|long|short primitive
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
define Stream////
if boolean|byte|char|float|short primitive //Stream<Character>//
elif double|int|long primitive //CharStream//
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
import cufy.util.CharIterator;
import cufy.util.CharSpliterator;
import cufy.util.function.CharConsumer;
import cufy.util.function.CharPredicate;
/* elif double|int|long primitive */
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.CharConsumer;
import java.util.function.CharPredicate;
import java.util.Spliterators;
import java.util.stream.StreamSupport;
import java.util.stream.CharStream;
/* endif */

import cufy.util.PrimitiveCollection;
import cufy.lang.CharIterable;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * A collection specialized for {@code char} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface CharCollection
		extends
		CharIterable,
		PrimitiveCollection<Character, CharConsumer, CharPredicate> {
	@Override
	default boolean add(Character element) {
		return this.addChar(element);
	}

	@Override
	default boolean contains(Object object) {
		return object instanceof Character && this.contains((char) object);
	}

	@Override
	default boolean remove(Object object) {
		return object instanceof Character && this.removeChar((char) object);
	}

	@Override
	default boolean removeIf(Predicate<? super Character> predicate) {
		Objects.requireNonNull(predicate, "predicate");
		return this.removeIf(
				predicate instanceof CharPredicate ?
				(CharPredicate) predicate :
				predicate::test
		);
	}

	@Override
	default boolean removeIf(CharPredicate predicate) {
		Objects.requireNonNull(predicate, "predicate");
		boolean modified = false;

		/* Iterator */ iterator = this.iterator();
		while (iterator.hasNext())
			if (predicate.test(iterator.next())) {
				iterator.remove();
				modified = true;
			}

		return modified;
	}
	/*
	if double|int|long primitive
	*/

	@Override
	default /* Spliterator */ spliterator() {
		return Spliterators.spliteratorUnknownSize(this.iterator(), 0);
	}

	/**
	 * Get a {@link CharStream} streaming the elements in this collection.
	 *
	 * @return a stream streaming the elements in this collection.
	 * @since 0.1.5 ~2020.08.11
	 */
	default /*Stream*/ charStream() {
		return StreamSupport.charStream(this.spliterator(), false);
	}

	/**
	 * Get a parallel {@link CharStream} streaming the elements in this collection.
	 *
	 * @return a stream streaming the elements in this collection.
	 * @since 0.1.5 ~2020.08.11
	 */
	default /*Stream*/ parallelCharStream() {
		return StreamSupport.charStream(this.spliterator(), true);
	}
	/*
	endif
	*/

	/**
	 * Ensures that this collection contains the specified element (optional operation).  Returns
	 * {@code true} if this collection changed as a result of the call. (Returns {@code false} if
	 * this collection does not permit duplicates and already contains the specified element.)
	 *
	 * @param element element whose presence in this collection is to be ensured
	 * @return {@code true} if this collection changed as a result of the call.
	 * @throws UnsupportedOperationException if the {@code add} operation is not supported by this
	 *                                       collection.
	 * @throws IllegalArgumentException      if some property of the element prevents it from being
	 *                                       added to this collection.
	 * @throws IllegalStateException         if the element cannot be added at this time due to
	 *                                       insertion restrictions.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean addChar(char element);

	/**
	 * Returns {@code true} if this collection contains the specified element.
	 *
	 * @param element element whose presence in this collection is to be tested
	 * @return {@code true} if this collection contains the specified element.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean contains(char element);

	/**
	 * Removes a single instance of the specified element from this collection, if it is present
	 * (optional operation).
	 *
	 * @param element element to be removed from this collection, if present.
	 * @return {@code true} if an element was removed as a result of this call.
	 * @throws UnsupportedOperationException if the {@code remove} operation is not supported by
	 *                                       this collection.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean removeChar(char element);

	/**
	 * Returns an array containing all of the elements in this collection. If this collection makes
	 * any guarantees as to what order its elements are returned by its iterator, this method must
	 * return the elements in the same order.
	 *
	 * @return an array containing all of the elements in this collection.
	 * @since 0.1.5 ~2020.09.01
	 */
	char[] toCharArray();

	/**
	 * Returns an array containing all of the elements in this collection;
	 * <p>
	 * If this collection fits in the specified array with room to spare (i.e., the array has more
	 * elements than this collection), the element in the array immediately following the end of the
	 * collection is set to the {@code default primitive value}. (This is useful in determining the
	 * length of this collection <i>only</i> if the caller knows that this collection does not
	 * contain any {@code default primitive value} elements.)
	 *
	 * @param array the array into which the elements of this collection are to be stored, if it is
	 *              big enough; otherwise, a new array is allocated for this purpose.
	 * @return an array containing all of the elements in this collection.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	char[] toCharArray(char[] array);
}
