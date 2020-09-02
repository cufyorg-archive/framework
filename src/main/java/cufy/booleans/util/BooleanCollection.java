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
import cufy.booleans.util.function.BooleanPredicate;
import cufy.util.PrimitiveCollection;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * A collection specialized for {@code boolean} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface BooleanCollection extends PrimitiveCollection<
		Boolean,
		BooleanConsumer,
		BooleanPredicate,
		BooleanIterator,
		BooleanSpliterator,
		BooleanCollection
		>, BooleanIterable {
	@Override
	default boolean add(Boolean element) {
		return this.addBoolean(element);
	}

	@Override
	default boolean contains(Object object) {
		return object instanceof Boolean && this.contains((boolean) object);
	}

	@Override
	default boolean remove(Object object) {
		return object instanceof Boolean && this.removeBoolean((boolean) object);
	}

	@Override
	default boolean removeIf(Predicate<? super Boolean> predicate) {
		Objects.requireNonNull(predicate, "predicate");
		return this.removeIf(
				predicate instanceof BooleanPredicate ?
				(BooleanPredicate) predicate :
				predicate::test
		);
	}

	@Override
	default boolean removeIf(BooleanPredicate predicate) {
		Objects.requireNonNull(predicate, "predicate");
		boolean modified = false;

		BooleanIterator iterator = this.iterator();
		while (iterator.hasNext())
			if (predicate.test(iterator.next())) {
				iterator.remove();
				modified = true;
			}

		return modified;
	}

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
	boolean addBoolean(boolean element);

	/**
	 * Returns {@code true} if this collection contains the specified element.
	 *
	 * @param element element whose presence in this collection is to be tested
	 * @return {@code true} if this collection contains the specified element.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean contains(boolean element);

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
	boolean removeBoolean(boolean element);

	/**
	 * Returns an array containing all of the elements in this collection. If this collection makes
	 * any guarantees as to what order its elements are returned by its iterator, this method must
	 * return the elements in the same order.
	 *
	 * @return an array containing all of the elements in this collection.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean[] toBooleanArray();

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
	boolean[] toBooleanArray(boolean[] array);
}
