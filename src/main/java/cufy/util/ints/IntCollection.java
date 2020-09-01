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

import cufy.util.PrimitiveCollection;

import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * A collection specialized for {@code int} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface IntCollection extends PrimitiveCollection<
		Integer,
		IntConsumer,
		IntPredicate,
		PrimitiveIterator.OfInt,
		Spliterator.OfInt,
		IntCollection
		>, IntIterable {
	@Override
	default boolean add(Integer element) {
		return this.addInt(element);
	}

	@Override
	default boolean contains(Object object) {
		return object instanceof Integer && this.contains((int) object);
	}

	@Override
	default Stream<Integer> parallelStream() {
		return this.parallelIntStream().boxed();
	}

	@Override
	default boolean remove(Object object) {
		return object instanceof Integer && this.removeInt((int) object);
	}

	@Override
	default boolean removeIf(Predicate<? super Integer> predicate) {
		Objects.requireNonNull(predicate, "predicate");
		return this.removeIf(
				predicate instanceof IntPredicate ?
				(IntPredicate) predicate :
				predicate::test
		);
	}

	@Override
	default boolean removeIf(IntPredicate predicate) {
		Objects.requireNonNull(predicate, "predicate");
		boolean modified = false;

		PrimitiveIterator.OfInt iterator = this.iterator();
		while (iterator.hasNext())
			if (predicate.test(iterator.next())) {
				iterator.remove();
				modified = true;
			}

		return modified;
	}

	@Override
	default Spliterator.OfInt spliterator() {
		return IntIterable.super.spliterator();
	}

	@Override
	default Stream<Integer> stream() {
		return this.intStream().boxed();
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
	boolean addInt(int element);

	/**
	 * Returns {@code true} if this collection contains the specified element.
	 *
	 * @param element element whose presence in this collection is to be tested
	 * @return {@code true} if this collection contains the specified element.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean contains(int element);

	/**
	 * Returns a sequential {@code IntStream} with this collection as its source.
	 *
	 * @return a sequential {@code IntStream} over the elements in this collection.
	 * @since 0.1.5 ~2020.09.01
	 */
	IntStream intStream();

	/**
	 * Returns a possibly parallel {@code IntStream} with this collection as its source. It is
	 * allowable for this method to return a sequential stream.
	 *
	 * @return a possibly parallel {@code IntStream} over the elements in this collection.
	 * @since 0.1.5 ~2020.09.01
	 */
	IntStream parallelIntStream();

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
	boolean removeInt(int element);

	/**
	 * Returns an array containing all of the elements in this collection. If this collection makes
	 * any guarantees as to what order its elements are returned by its iterator, this method must
	 * return the elements in the same order.
	 *
	 * @return an array containing all of the elements in this collection.
	 * @since 0.1.5 ~2020.09.01
	 */
	int[] toIntArray();

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
	int[] toIntArray(int[] array);
}
