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

import cufy.primitive.util.function.DoubleBiConsumer;
import cufy.util.PrimitiveSortedMap;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.DoubleBinaryOperator;

/**
 * A sorted map specialized for {@code double} values.
 *
 * @param <ENTRY_SET> the type of the set of the entries.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.03
 */
public interface DoubleSortedMap<
		ENTRY_SET extends Set<Map.Entry<Double, Double>>
		> extends PrimitiveSortedMap<
		Double,
		Double,
		DoubleBiConsumer,
		DoubleBinaryOperator,
		DoubleComparator,
		ENTRY_SET,
		DoubleSet,
		DoubleCollection,
		DoubleMap<ENTRY_SET>,
		DoubleSortedMap<ENTRY_SET>
		>, DoubleMap<ENTRY_SET> {
	@Override
	default Double firstKey() {
		return this.firstDoubleKey();
	}

	@Override
	default DoubleSortedMap<ENTRY_SET> headMap(Double endKey) {
		return this.headMap((double) endKey);
	}

	@Override
	default Double lastKey() {
		return this.lastDoubleKey();
	}

	@Override
	default DoubleSortedMap<ENTRY_SET> subMap(Double beginKey, Double endKey) {
		return this.subMap((double) beginKey, (double) endKey);
	}

	@Override
	default DoubleSortedMap<ENTRY_SET> tailMap(Double beginKey) {
		return this.tailMap((double) beginKey);
	}

	/**
	 * Returns the first (lowest) key currently in this map.
	 *
	 * @return the first (lowest) key currently in this map.
	 * @throws NoSuchElementException if this map is empty.
	 * @since 0.1.5 ~2020.09.03
	 */
	double firstDoubleKey();

	/**
	 * Returns a view of the portion of this map whose keys are strictly less than {@code endKey}.
	 * The returned map is backed by this map, so changes in the returned map are reflected in this
	 * map, and vice-versa. The returned map supports all optional map operations that this map
	 * supports.
	 * <p>
	 * The returned map will throw an {@code IllegalArgumentException} on an attempt to insert a key
	 * outside its range.
	 *
	 * @param endKey high endpoint (exclusive) of the keys in the returned map.
	 * @return a view of the portion of this map whose keys are strictly less than {@code endKey}.
	 * @throws IllegalArgumentException if this map itself has a restricted range, and {@code
	 *                                  endKey} lies outside the bounds of the range.
	 * @since 0.1.5 ~2020.09.03
	 */
	DoubleSortedMap<ENTRY_SET> headMap(double endKey);

	/**
	 * Returns the last (highest) key currently in this map.
	 *
	 * @return the last (highest) key currently in this map
	 * @throws NoSuchElementException if this map is empty.
	 * @since 0.1.5 ~2020.09.03
	 */
	double lastDoubleKey();

	/**
	 * Returns a view of the portion of this map whose keys range from {@code beginKey}, inclusive,
	 * to {@code endKey}, exclusive. (If {@code beginKey} and {@code endKey} are equal, the returned
	 * map is empty.) The returned map is backed by this map, so changes in the returned map are
	 * reflected in this map, and vice-versa. The returned map supports all optional map operations
	 * that this map supports.
	 * <p>
	 * The returned map will throw an {@code IllegalArgumentException} on an attempt to insert a key
	 * outside its range.
	 *
	 * @param beginKey low endpoint (inclusive) of the keys in the returned map.
	 * @param endKey   high endpoint (exclusive) of the keys in the returned map.
	 * @return a view of the portion of this map whose keys range from {@code beginKey}, inclusive,
	 * 		to {@code endKey}, exclusive.
	 * @throws IllegalArgumentException if {@code beginKey} is greater than {@code endKey}; or if
	 *                                  this map itself has a restricted range, and {@code beginKey}
	 *                                  or {@code endKey} lies outside the bounds of the range
	 * @since 0.1.5 ~2020.09.03
	 */
	DoubleSortedMap<ENTRY_SET> subMap(double beginKey, double endKey);

	/**
	 * Returns a view of the portion of this map whose keys are greater than or equal to {@code
	 * beginKey}. The returned map is backed by this map, so changes in the returned map are
	 * reflected in this map, and vice-versa. The returned map supports all optional map operations
	 * that this map supports.
	 * <p>
	 * The returned map will throw an {@code IllegalArgumentException} on an attempt to insert a key
	 * outside its range.
	 *
	 * @param beginKey low endpoint (inclusive) of the keys in the returned map
	 * @return a view of the portion of this map whose keys are greater than or equal to {@code
	 * 		beginKey}.
	 * @throws IllegalArgumentException if this map itself has a restricted range, and {@code
	 *                                  beginKey} lies outside the bounds of the range.
	 * @since 0.1.5 ~2020.09.03
	 */
	DoubleSortedMap<ENTRY_SET> tailMap(double beginKey);
}
