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
import cufy.util.PrimitiveNavigableMap;

import java.util.Map;
import java.util.Set;

/**
 * A navigable map specialized for {@code boolean} values.
 *
 * @param <ENTRY_SET> the type of the set of the entries.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.03
 */
public interface BooleanNavigableMap<
		ENTRY_SET extends Set<Map.Entry<Boolean, Boolean>>
		> extends PrimitiveNavigableMap<
		Boolean,
		Boolean,
		BooleanBiConsumer,
		BooleanBinaryOperator,
		BooleanComparator,
		BooleanMap.BooleanEntry,
		ENTRY_SET,
		BooleanSet,
		BooleanNavigableSet,
		BooleanCollection,
		BooleanMap<ENTRY_SET>,
		BooleanSortedMap<ENTRY_SET>,
		BooleanNavigableMap<ENTRY_SET>
		>, BooleanSortedMap<ENTRY_SET> {
	@Override
	default BooleanEntry ceilingEntry(Boolean key) {
		return this.ceilingEntry((boolean) key);
	}

	@Override
	default Boolean ceilingKey(Boolean key) {
		return this.hasCeilingKey(key) ?
			   this.ceilingBooleanKey(key) :
			   null;
	}

	@Override
	default BooleanEntry floorEntry(Boolean key) {
		return this.floorEntry((boolean) key);
	}

	@Override
	default Boolean floorKey(Boolean key) {
		return this.hasFloorKey(key) ?
			   this.floorBooleanKey(key) :
			   null;
	}

	@Override
	default BooleanNavigableMap<ENTRY_SET> headMap(Boolean endKey, boolean inclusive) {
		return this.headMap((boolean) endKey, inclusive);
	}

	@Override
	default BooleanEntry higherEntry(Boolean key) {
		return this.higherEntry((boolean) key);
	}

	@Override
	default Boolean higherKey(Boolean key) {
		return this.hasHigherKey(key) ?
			   this.higherBooleanKey(key) :
			   null;
	}

	@Override
	default BooleanEntry lowerEntry(Boolean key) {
		return this.lowerEntry((boolean) key);
	}

	@Override
	default Boolean lowerKey(Boolean key) {
		return this.hasLowerKey(key) ?
			   this.lowerBooleanKey(key) :
			   null;
	}

	@Override
	default BooleanNavigableMap<ENTRY_SET> subMap(Boolean beginKey, boolean beginInclusive, Boolean endKey, boolean endInclusive) {
		return this.subMap((boolean) beginKey, beginInclusive, (boolean) endKey, endInclusive);
	}

	@Override
	default BooleanNavigableMap<ENTRY_SET> tailMap(Boolean beginKey, boolean inclusive) {
		return this.tailMap((boolean) beginKey, inclusive);
	}

	/**
	 * Returns the least key greater than or equal to the given key, or {@code default boolean
	 * value} if there is no such key.
	 *
	 * @param key the key.
	 * @return the least key greater than or equal to {@code key}, or {@code default boolean value}
	 * 		if there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean ceilingBooleanKey(boolean key);

	/**
	 * Returns a key-value mapping associated with the least key greater than or equal to the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the least key greater than or equal to {@code key}, or {@code null} if
	 * 		there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	BooleanEntry ceilingEntry(boolean key);

	/**
	 * Returns the greatest key less than or equal to the given key, or {@code default boolean
	 * value} if there is no such key.
	 *
	 * @param key the key
	 * @return the greatest key less than or equal to {@code key}, or {@code default boolean value}
	 * 		if there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean floorBooleanKey(boolean key);

	/**
	 * Returns a key-value mapping associated with the greatest key less than or equal to the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the greatest key less than or equal to {@code key}, or {@code null} if
	 * 		there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	BooleanEntry floorEntry(boolean key);

	/**
	 * Return true if there is a least key in this map greater than or equal to the given {@code
	 * key}.
	 *
	 * @param key the key to match.
	 * @return true, if there is a least key in this map greater than or equal to the given {@code
	 * 		key}.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean hasCeilingKey(boolean key);

	/**
	 * Return true if there is a greatest key in this less than or equal to the given {@code key}.
	 *
	 * @param key the key to match.
	 * @return true, if there is a greatest key less than or equal to the given {@code key}.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean hasFloorKey(boolean key);

	/**
	 * Return true if there is a least key in this map strictly greater than the given {@code key}.
	 *
	 * @param key the key to match
	 * @return true, if there is a least key greater than teh given {@code key}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasHigherKey(boolean key);

	/**
	 * Return true if there is a greatest key in this map strictly less than the given {@code key}.
	 *
	 * @param key the key to match.
	 * @return true, if there is a greatest key less than the given {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasLowerKey(boolean key);

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
	BooleanNavigableMap<ENTRY_SET> headMap(boolean endKey, boolean inclusive);

	/**
	 * Returns the least key strictly greater than the given key, or {@code default boolean value}
	 * if there is no such key.
	 *
	 * @param key the key.
	 * @return the least key greater than {@code key}, or {@code default boolean value} if there is
	 * 		no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean higherBooleanKey(boolean key);

	/**
	 * Returns a key-value mapping associated with the least key strictly greater than the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the least key greater than {@code key}, or {@code null} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	BooleanEntry higherEntry(boolean key);

	/**
	 * Returns the greatest key strictly less than the given key, or {@code default boolean value}
	 * if there is no such key.
	 *
	 * @param key the key.
	 * @return the greatest key less than {@code key}, or {@code default boolean value} if there is
	 * 		no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean lowerBooleanKey(boolean key);

	/**
	 * Returns a key-value mapping associated with the greatest key strictly less than the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the greatest key less than {@code key}, or {@code null} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	BooleanEntry lowerEntry(boolean key);

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
	BooleanNavigableMap<ENTRY_SET> subMap(boolean beginKey, boolean beginInclusive, boolean endKey, boolean endInclusive);

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
	BooleanNavigableMap<ENTRY_SET> tailMap(boolean beginKey, boolean inclusive);
}
