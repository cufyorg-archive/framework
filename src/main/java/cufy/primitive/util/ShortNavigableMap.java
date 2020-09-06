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

import cufy.primitive.util.function.ShortBiConsumer;
import cufy.primitive.util.function.ShortBinaryOperator;
import cufy.util.PrimitiveNavigableMap;

import java.util.Map;
import java.util.Set;

/**
 * A navigable map specialized for {@code short} values.
 *
 * @param <ENTRY_SET> the type of the set of the entries.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.03
 */
public interface ShortNavigableMap<
		ENTRY_SET extends Set<Map.Entry<Short, Short>>
		> extends PrimitiveNavigableMap<
		Short,
		Short,
		ShortBiConsumer,
		ShortBinaryOperator,
		ShortComparator,
		ShortMap.ShortEntry,
		ENTRY_SET,
		ShortSet,
		ShortNavigableSet,
		ShortCollection,
		ShortMap<ENTRY_SET>,
		ShortSortedMap<ENTRY_SET>,
		ShortNavigableMap<ENTRY_SET>
		>, ShortSortedMap<ENTRY_SET> {
	@Override
	default ShortEntry ceilingEntry(Short key) {
		return this.ceilingEntry((short) key);
	}

	@Override
	default Short ceilingKey(Short key) {
		return this.hasCeilingKey(key) ?
			   this.ceilingShortKey(key) :
			   null;
	}

	@Override
	default ShortEntry floorEntry(Short key) {
		return this.floorEntry((short) key);
	}

	@Override
	default Short floorKey(Short key) {
		return this.hasFloorKey(key) ?
			   this.floorShortKey(key) :
			   null;
	}

	@Override
	default ShortNavigableMap<ENTRY_SET> headMap(Short endKey, boolean inclusive) {
		return this.headMap((short) endKey, inclusive);
	}

	@Override
	default ShortEntry higherEntry(Short key) {
		return this.higherEntry((short) key);
	}

	@Override
	default Short higherKey(Short key) {
		return this.hasHigherKey(key) ?
			   this.higherShortKey(key) :
			   null;
	}

	@Override
	default ShortEntry lowerEntry(Short key) {
		return this.lowerEntry((short) key);
	}

	@Override
	default Short lowerKey(Short key) {
		return this.hasLowerKey(key) ?
			   this.lowerShortKey(key) :
			   null;
	}

	@Override
	default ShortNavigableMap<ENTRY_SET> subMap(Short beginKey, boolean beginInclusive, Short endKey, boolean endInclusive) {
		return this.subMap((short) beginKey, beginInclusive, (short) endKey, endInclusive);
	}

	@Override
	default ShortNavigableMap<ENTRY_SET> tailMap(Short beginKey, boolean inclusive) {
		return this.tailMap((short) beginKey, inclusive);
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
	ShortEntry ceilingEntry(short key);

	/**
	 * Returns the least key greater than or equal to the given key, or {@code default short value}
	 * if there is no such key.
	 *
	 * @param key the key.
	 * @return the least key greater than or equal to {@code key}, or {@code default short value} if
	 * 		there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	short ceilingShortKey(short key);

	/**
	 * Returns a key-value mapping associated with the greatest key less than or equal to the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the greatest key less than or equal to {@code key}, or {@code null} if
	 * 		there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	ShortEntry floorEntry(short key);

	/**
	 * Returns the greatest key less than or equal to the given key, or {@code default short value}
	 * if there is no such key.
	 *
	 * @param key the key
	 * @return the greatest key less than or equal to {@code key}, or {@code default short value} if
	 * 		there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	short floorShortKey(short key);

	/**
	 * Return true if there is a least key in this map greater than or equal to the given {@code
	 * key}.
	 *
	 * @param key the key to match.
	 * @return true, if there is a least key in this map greater than or equal to the given {@code
	 * 		key}.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean hasCeilingKey(short key);

	/**
	 * Return true if there is a greatest key in this less than or equal to the given {@code key}.
	 *
	 * @param key the key to match.
	 * @return true, if there is a greatest key less than or equal to the given {@code key}.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean hasFloorKey(short key);

	/**
	 * Return true if there is a least key in this map strictly greater than the given {@code key}.
	 *
	 * @param key the key to match
	 * @return true, if there is a least key greater than teh given {@code key}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasHigherKey(short key);

	/**
	 * Return true if there is a greatest key in this map strictly less than the given {@code key}.
	 *
	 * @param key the key to match.
	 * @return true, if there is a greatest key less than the given {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasLowerKey(short key);

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
	ShortNavigableMap<ENTRY_SET> headMap(short endKey, boolean inclusive);

	/**
	 * Returns a key-value mapping associated with the least key strictly greater than the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the least key greater than {@code key}, or {@code null} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	ShortEntry higherEntry(short key);

	/**
	 * Returns the least key strictly greater than the given key, or {@code default short value} if
	 * there is no such key.
	 *
	 * @param key the key.
	 * @return the least key greater than {@code key}, or {@code default short value} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	short higherShortKey(short key);

	/**
	 * Returns a key-value mapping associated with the greatest key strictly less than the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the greatest key less than {@code key}, or {@code null} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	ShortEntry lowerEntry(short key);

	/**
	 * Returns the greatest key strictly less than the given key, or {@code default short value} if
	 * there is no such key.
	 *
	 * @param key the key.
	 * @return the greatest key less than {@code key}, or {@code default short value} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	short lowerShortKey(short key);

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
	ShortNavigableMap<ENTRY_SET> subMap(short beginKey, boolean beginInclusive, short endKey, boolean endInclusive);

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
	ShortNavigableMap<ENTRY_SET> tailMap(short beginKey, boolean inclusive);
}
