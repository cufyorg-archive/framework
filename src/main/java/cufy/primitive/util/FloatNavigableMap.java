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

import cufy.primitive.util.function.FloatBiConsumer;
import cufy.primitive.util.function.FloatBinaryOperator;
import cufy.util.PrimitiveNavigableMap;

import java.util.Map;
import java.util.Set;

/**
 * A navigable map specialized for {@code float} values.
 *
 * @param <ENTRY_SET> the type of the set of the entries.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.03
 */
public interface FloatNavigableMap<
		ENTRY_SET extends Set<Map.Entry<Float, Float>>
		> extends PrimitiveNavigableMap<
		Float,
		Float,
		FloatBiConsumer,
		FloatBinaryOperator,
		FloatComparator,
		FloatMap.FloatEntry,
		ENTRY_SET,
		FloatSet,
		FloatNavigableSet,
		FloatCollection,
		FloatMap<ENTRY_SET>,
		FloatSortedMap<ENTRY_SET>,
		FloatNavigableMap<ENTRY_SET>
		>, FloatSortedMap<ENTRY_SET> {
	@Override
	default FloatEntry ceilingEntry(Float key) {
		return this.ceilingEntry((float) key);
	}

	@Override
	default Float ceilingKey(Float key) {
		return this.hasCeilingKey(key) ?
			   this.ceilingFloatKey(key) :
			   null;
	}

	@Override
	default FloatEntry floorEntry(Float key) {
		return this.floorEntry((float) key);
	}

	@Override
	default Float floorKey(Float key) {
		return this.hasFloorKey(key) ?
			   this.floorFloatKey(key) :
			   null;
	}

	@Override
	default FloatNavigableMap<ENTRY_SET> headMap(Float endKey, boolean inclusive) {
		return this.headMap((float) endKey, inclusive);
	}

	@Override
	default FloatEntry higherEntry(Float key) {
		return this.higherEntry((float) key);
	}

	@Override
	default Float higherKey(Float key) {
		return this.hasHigherKey(key) ?
			   this.higherFloatKey(key) :
			   null;
	}

	@Override
	default FloatEntry lowerEntry(Float key) {
		return this.lowerEntry((float) key);
	}

	@Override
	default Float lowerKey(Float key) {
		return this.hasLowerKey(key) ?
			   this.lowerFloatKey(key) :
			   null;
	}

	@Override
	default FloatNavigableMap<ENTRY_SET> subMap(Float beginKey, boolean beginInclusive, Float endKey, boolean endInclusive) {
		return this.subMap((float) beginKey, beginInclusive, (float) endKey, endInclusive);
	}

	@Override
	default FloatNavigableMap<ENTRY_SET> tailMap(Float beginKey, boolean inclusive) {
		return this.tailMap((float) beginKey, inclusive);
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
	FloatEntry ceilingEntry(float key);

	/**
	 * Returns the least key greater than or equal to the given key, or {@code default float value}
	 * if there is no such key.
	 *
	 * @param key the key.
	 * @return the least key greater than or equal to {@code key}, or {@code default float value} if
	 * 		there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	float ceilingFloatKey(float key);

	/**
	 * Returns a key-value mapping associated with the greatest key less than or equal to the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the greatest key less than or equal to {@code key}, or {@code null} if
	 * 		there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	FloatEntry floorEntry(float key);

	/**
	 * Returns the greatest key less than or equal to the given key, or {@code default float value}
	 * if there is no such key.
	 *
	 * @param key the key
	 * @return the greatest key less than or equal to {@code key}, or {@code default float value} if
	 * 		there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	float floorFloatKey(float key);

	/**
	 * Return true if there is a least key in this map greater than or equal to the given {@code
	 * key}.
	 *
	 * @param key the key to match.
	 * @return true, if there is a least key in this map greater than or equal to the given {@code
	 * 		key}.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean hasCeilingKey(float key);

	/**
	 * Return true if there is a greatest key in this less than or equal to the given {@code key}.
	 *
	 * @param key the key to match.
	 * @return true, if there is a greatest key less than or equal to the given {@code key}.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean hasFloorKey(float key);

	/**
	 * Return true if there is a least key in this map strictly greater than the given {@code key}.
	 *
	 * @param key the key to match
	 * @return true, if there is a least key greater than teh given {@code key}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasHigherKey(float key);

	/**
	 * Return true if there is a greatest key in this map strictly less than the given {@code key}.
	 *
	 * @param key the key to match.
	 * @return true, if there is a greatest key less than the given {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasLowerKey(float key);

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
	FloatNavigableMap<ENTRY_SET> headMap(float endKey, boolean inclusive);

	/**
	 * Returns a key-value mapping associated with the least key strictly greater than the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the least key greater than {@code key}, or {@code null} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	FloatEntry higherEntry(float key);

	/**
	 * Returns the least key strictly greater than the given key, or {@code default float value} if
	 * there is no such key.
	 *
	 * @param key the key.
	 * @return the least key greater than {@code key}, or {@code default float value} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	float higherFloatKey(float key);

	/**
	 * Returns a key-value mapping associated with the greatest key strictly less than the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the greatest key less than {@code key}, or {@code null} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	FloatEntry lowerEntry(float key);

	/**
	 * Returns the greatest key strictly less than the given key, or {@code default float value} if
	 * there is no such key.
	 *
	 * @param key the key.
	 * @return the greatest key less than {@code key}, or {@code default float value} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	float lowerFloatKey(float key);

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
	FloatNavigableMap<ENTRY_SET> subMap(float beginKey, boolean beginInclusive, float endKey, boolean endInclusive);

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
	FloatNavigableMap<ENTRY_SET> tailMap(float beginKey, boolean inclusive);
}
