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

import cufy.primitive.util.function.IntBiConsumer;
import cufy.util.PrimitiveNavigableMap;

import java.util.Map;
import java.util.Set;
import java.util.function.IntBinaryOperator;

/**
 * A navigable map specialized for {@code int} values.
 *
 * @param <ENTRY_SET> the type of the set of the entries.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.03
 */
public interface IntNavigableMap<
		ENTRY_SET extends Set<Map.Entry<Integer, Integer>>
		> extends PrimitiveNavigableMap<
		Integer,
		Integer,
		IntBiConsumer,
		IntBinaryOperator,
		IntComparator,
		IntMap.IntEntry,
		ENTRY_SET,
		IntSet,
		IntNavigableSet,
		IntCollection,
		IntMap<ENTRY_SET>,
		IntSortedMap<ENTRY_SET>,
		IntNavigableMap<ENTRY_SET>
		>, IntSortedMap<ENTRY_SET> {
	@Override
	default IntEntry ceilingEntry(Integer key) {
		return this.ceilingEntry((int) key);
	}

	@Override
	default Integer ceilingKey(Integer key) {
		return this.hasCeilingKey(key) ?
			   this.ceilingIntKey(key) :
			   null;
	}

	@Override
	default IntEntry floorEntry(Integer key) {
		return this.floorEntry((int) key);
	}

	@Override
	default Integer floorKey(Integer key) {
		return this.hasFloorKey(key) ?
			   this.floorIntKey(key) :
			   null;
	}

	@Override
	default IntNavigableMap<ENTRY_SET> headMap(Integer endKey, boolean inclusive) {
		return this.headMap((int) endKey, inclusive);
	}

	@Override
	default IntEntry higherEntry(Integer key) {
		return this.higherEntry((int) key);
	}

	@Override
	default Integer higherKey(Integer key) {
		return this.hasHigherKey(key) ?
			   this.higherIntKey(key) :
			   null;
	}

	@Override
	default IntEntry lowerEntry(Integer key) {
		return this.lowerEntry((int) key);
	}

	@Override
	default Integer lowerKey(Integer key) {
		return this.hasLowerKey(key) ?
			   this.lowerIntKey(key) :
			   null;
	}

	@Override
	default IntNavigableMap<ENTRY_SET> subMap(Integer beginKey, boolean beginInclusive, Integer endKey, boolean endInclusive) {
		return this.subMap((int) beginKey, beginInclusive, (int) endKey, endInclusive);
	}

	@Override
	default IntNavigableMap<ENTRY_SET> tailMap(Integer beginKey, boolean inclusive) {
		return this.tailMap((int) beginKey, inclusive);
	}

	/**
	 * Returns a key-value mapping associated with the least key greater than or equal to the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the least key greater than or equal to {@code key}, or {@code null} if
	 * 		there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	IntEntry ceilingEntry(int key);

	/**
	 * Returns the least key greater than or equal to the given key, or {@code default int value} if
	 * there is no such key.
	 *
	 * @param key the key.
	 * @return the least key greater than or equal to {@code key}, or {@code default int value} if
	 * 		there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	int ceilingIntKey(int key);

	/**
	 * Returns a key-value mapping associated with the greatest key less than or equal to the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the greatest key less than or equal to {@code key}, or {@code null} if
	 * 		there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	IntEntry floorEntry(int key);

	/**
	 * Returns the greatest key less than or equal to the given key, or {@code default int value} if
	 * there is no such key.
	 *
	 * @param key the key
	 * @return the greatest key less than or equal to {@code key}, or {@code default int value} if
	 * 		there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	int floorIntKey(int key);

	/**
	 * Return true if there is a least key in this map greater than or equal to the given {@code
	 * key}.
	 *
	 * @param key the key to match.
	 * @return true, if there is a least key in this map greater than or equal to the given {@code
	 * 		key}.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean hasCeilingKey(int key);

	/**
	 * Return true if there is a greatest key in this less than or equal to the given {@code key}.
	 *
	 * @param key the key to match.
	 * @return true, if there is a greatest key less than or equal to the given {@code key}.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean hasFloorKey(int key);

	/**
	 * Return true if there is a least key in this map strictly greater than the given {@code key}.
	 *
	 * @param key the key to match
	 * @return true, if there is a least key greater than teh given {@code key}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasHigherKey(int key);

	/**
	 * Return true if there is a greatest key in this map strictly less than the given {@code key}.
	 *
	 * @param key the key to match.
	 * @return true, if there is a greatest key less than the given {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasLowerKey(int key);

	/**
	 * Returns a view of the portion of this map whose keys are less than (or equal to, if {@code
	 * inclusive} is true) {@code beginKey}. The returned map is backed by this map, so changes in
	 * the returned map are reflected in this map, and vice-versa. The returned map supports all
	 * optional map operations that this map supports.
	 * <p>
	 * The returned map will throw an {@code IllegalArgumentException} on an attempt to insert a key
	 * outside its range.
	 *
	 * @param endKey    high endpoint of the keys in the returned map.
	 * @param inclusive {@code true} if the high endpoint is to be included in the returned view.
	 * @return a view of the portion of this map whose keys are less than (or equal to, if {@code
	 * 		inclusive} is true) {@code endKey}.
	 * @throws IllegalArgumentException if this map itself has a restricted range, and {@code
	 *                                  endKey} lies outside the bounds of the range.
	 * @since 0.1.5 ~2020.09.03
	 */
	IntNavigableMap<ENTRY_SET> headMap(int endKey, boolean inclusive);

	/**
	 * Returns a key-value mapping associated with the least key strictly greater than the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the least key greater than {@code key}, or {@code null} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	IntEntry higherEntry(int key);

	/**
	 * Returns the least key strictly greater than the given key, or {@code default int value} if
	 * there is no such key.
	 *
	 * @param key the key.
	 * @return the least key greater than {@code key}, or {@code default int value} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	int higherIntKey(int key);

	/**
	 * Returns a key-value mapping associated with the greatest key strictly less than the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the greatest key less than {@code key}, or {@code null} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	IntEntry lowerEntry(int key);

	/**
	 * Returns the greatest key strictly less than the given key, or {@code default int value} if
	 * there is no such key.
	 *
	 * @param key the key.
	 * @return the greatest key less than {@code key}, or {@code default int value} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	int lowerIntKey(int key);

	/**
	 * Returns a view of the portion of this map whose keys range from {@code beginKey} to {@code
	 * endKey}. If {@code beginKey} and {@code endKey} are equal, the returned map is empty unless
	 * {@code beginInclusive} and {@code endInclusive} are both true. The returned map is backed by
	 * this map, so changes in the returned map are reflected in this map, and vice-versa. The
	 * returned map supports all optional map operations that this map supports.
	 * <p>
	 * The returned map will throw an {@code IllegalArgumentException} on an attempt to insert a key
	 * outside of its range, or to construct a submap either of whose endpoints lie outside its
	 * range.
	 *
	 * @param beginKey       low endpoint of the keys in the returned map.
	 * @param beginInclusive {@code true} if the low endpoint is to be included in the returned
	 *                       view.
	 * @param endKey         high endpoint of the keys in the returned map.
	 * @param endInclusive   {@code true} if the high endpoint is to be included in the returned
	 *                       view.
	 * @return a view of the portion of this map whose keys range from {@code beginKey} to {@code
	 * 		endKey}.
	 * @throws IllegalArgumentException if {@code beginKey} is greater than {@code endKey}; or if
	 *                                  this map itself has a restricted range, and {@code beginKey}
	 *                                  or {@code endKey} lies outside the bounds of the range.
	 * @since 0.1.5 ~2020.09.03
	 */
	IntNavigableMap<ENTRY_SET> subMap(int beginKey, boolean beginInclusive, int endKey, boolean endInclusive);

	/**
	 * Returns a view of the portion of this map whose keys are greater than (or equal to, if {@code
	 * inclusive} is true) {@code beginKey}. The returned map is backed by this map, so changes in
	 * the returned map are reflected in this map, and vice-versa. The returned map supports all
	 * optional map operations that this map supports.
	 * <p>
	 * The returned map will throw an {@code IllegalArgumentException} on an attempt to insert a key
	 * outside its range.
	 *
	 * @param beginKey  low endpoint of the keys in the returned map.
	 * @param inclusive {@code true} if the low endpoint is to be included in the returned view.
	 * @return a view of the portion of this map whose keys are greater than (or equal to, if {@code
	 * 		inclusive} is true) {@code beginKey}.
	 * @throws IllegalArgumentException if this map itself has a restricted range, and {@code
	 *                                  beginKey} lies outside the bounds of the range.
	 * @since 0.1.5 ~2020.09.03
	 */
	IntNavigableMap<ENTRY_SET> tailMap(int beginKey, boolean inclusive);
}
