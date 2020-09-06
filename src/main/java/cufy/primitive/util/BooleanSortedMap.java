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

import cufy.primitive.util.function.BooleanBiConsumer;
import cufy.primitive.util.function.BooleanBinaryOperator;
import cufy.util.PrimitiveSortedMap;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * A sorted map specialized for {@code boolean} values.
 *
 * @param <ENTRY_SET> the type of the set of the entries.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.03
 */
public interface BooleanSortedMap<
		ENTRY_SET extends Set<Map.Entry<Boolean, Boolean>>
		> extends PrimitiveSortedMap<
		Boolean,
		Boolean,
		BooleanBiConsumer,
		BooleanBinaryOperator,
		BooleanComparator,
		ENTRY_SET,
		BooleanSet,
		BooleanCollection,
		BooleanMap<ENTRY_SET>,
		BooleanSortedMap<ENTRY_SET>
		>, BooleanMap<ENTRY_SET> {
	@Override
	default Boolean firstKey() {
		return this.firstBooleanKey();
	}

	@Override
	default BooleanSortedMap<ENTRY_SET> headMap(Boolean endKey) {
		return this.headMap((boolean) endKey);
	}

	@Override
	default Boolean lastKey() {
		return this.lastBooleanKey();
	}

	@Override
	default BooleanSortedMap<ENTRY_SET> subMap(Boolean beginKey, Boolean endKey) {
		return this.subMap((boolean) beginKey, (boolean) endKey);
	}

	@Override
	default BooleanSortedMap<ENTRY_SET> tailMap(Boolean beginKey) {
		return this.tailMap((boolean) beginKey);
	}

	/**
	 * Returns the first (lowest) key currently in this map.
	 *
	 * @return the first (lowest) key currently in this map.
	 * @throws NoSuchElementException if this map is empty.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean firstBooleanKey();

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
	BooleanSortedMap<ENTRY_SET> headMap(boolean endKey);

	/**
	 * Returns the last (highest) key currently in this map.
	 *
	 * @return the last (highest) key currently in this map
	 * @throws NoSuchElementException if this map is empty.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean lastBooleanKey();

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
	BooleanSortedMap<ENTRY_SET> subMap(boolean beginKey, boolean endKey);

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
	BooleanSortedMap<ENTRY_SET> tailMap(boolean beginKey);
}
