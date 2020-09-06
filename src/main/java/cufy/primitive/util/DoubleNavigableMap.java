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
import cufy.util.PrimitiveNavigableMap;

import java.util.Map;
import java.util.Set;
import java.util.function.DoubleBinaryOperator;

/**
 * A navigable map specialized for {@code double} values.
 *
 * @param <ENTRY_SET> the type of the set of the entries.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.03
 */
public interface DoubleNavigableMap<
		ENTRY_SET extends Set<Map.Entry<Double, Double>>
		> extends PrimitiveNavigableMap<
		Double,
		Double,
		DoubleBiConsumer,
		DoubleBinaryOperator,
		DoubleComparator,
		DoubleMap.DoubleEntry,
		ENTRY_SET,
		DoubleSet,
		DoubleNavigableSet,
		DoubleCollection,
		DoubleMap<ENTRY_SET>,
		DoubleSortedMap<ENTRY_SET>,
		DoubleNavigableMap<ENTRY_SET>
		>, DoubleSortedMap<ENTRY_SET> {
	@Override
	default DoubleEntry ceilingEntry(Double key) {
		return this.ceilingEntry((double) key);
	}

	@Override
	default Double ceilingKey(Double key) {
		return this.hasCeilingKey(key) ?
			   this.ceilingDoubleKey(key) :
			   null;
	}

	@Override
	default DoubleEntry floorEntry(Double key) {
		return this.floorEntry((double) key);
	}

	@Override
	default Double floorKey(Double key) {
		return this.hasFloorKey(key) ?
			   this.floorDoubleKey(key) :
			   null;
	}

	@Override
	default DoubleNavigableMap<ENTRY_SET> headMap(Double endKey, boolean inclusive) {
		return this.headMap((double) endKey, inclusive);
	}

	@Override
	default DoubleEntry higherEntry(Double key) {
		return this.higherEntry((double) key);
	}

	@Override
	default Double higherKey(Double key) {
		return this.hasHigherKey(key) ?
			   this.higherDoubleKey(key) :
			   null;
	}

	@Override
	default DoubleEntry lowerEntry(Double key) {
		return this.lowerEntry((double) key);
	}

	@Override
	default Double lowerKey(Double key) {
		return this.hasLowerKey(key) ?
			   this.lowerDoubleKey(key) :
			   null;
	}

	@Override
	default DoubleNavigableMap<ENTRY_SET> subMap(Double beginKey, boolean beginInclusive, Double endKey, boolean endInclusive) {
		return this.subMap((double) beginKey, beginInclusive, (double) endKey, endInclusive);
	}

	@Override
	default DoubleNavigableMap<ENTRY_SET> tailMap(Double beginKey, boolean inclusive) {
		return this.tailMap((double) beginKey, inclusive);
	}

	/**
	 * Returns the least key greater than or equal to the given key, or {@code default double value}
	 * if there is no such key.
	 *
	 * @param key the key.
	 * @return the least key greater than or equal to {@code key}, or {@code default double value}
	 * 		if there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	double ceilingDoubleKey(double key);

	/**
	 * Returns a key-value mapping associated with the least key greater than or equal to the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the least key greater than or equal to {@code key}, or {@code null} if
	 * 		there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	DoubleEntry ceilingEntry(double key);

	/**
	 * Returns the greatest key less than or equal to the given key, or {@code default double value}
	 * if there is no such key.
	 *
	 * @param key the key
	 * @return the greatest key less than or equal to {@code key}, or {@code default double value}
	 * 		if there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	double floorDoubleKey(double key);

	/**
	 * Returns a key-value mapping associated with the greatest key less than or equal to the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the greatest key less than or equal to {@code key}, or {@code null} if
	 * 		there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	DoubleEntry floorEntry(double key);

	/**
	 * Return true if there is a least key in this map greater than or equal to the given {@code
	 * key}.
	 *
	 * @param key the key to match.
	 * @return true, if there is a least key in this map greater than or equal to the given {@code
	 * 		key}.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean hasCeilingKey(double key);

	/**
	 * Return true if there is a greatest key in this less than or equal to the given {@code key}.
	 *
	 * @param key the key to match.
	 * @return true, if there is a greatest key less than or equal to the given {@code key}.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean hasFloorKey(double key);

	/**
	 * Return true if there is a least key in this map strictly greater than the given {@code key}.
	 *
	 * @param key the key to match
	 * @return true, if there is a least key greater than teh given {@code key}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasHigherKey(double key);

	/**
	 * Return true if there is a greatest key in this map strictly less than the given {@code key}.
	 *
	 * @param key the key to match.
	 * @return true, if there is a greatest key less than the given {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasLowerKey(double key);

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
	DoubleNavigableMap<ENTRY_SET> headMap(double endKey, boolean inclusive);

	/**
	 * Returns the least key strictly greater than the given key, or {@code default double value} if
	 * there is no such key.
	 *
	 * @param key the key.
	 * @return the least key greater than {@code key}, or {@code default double value} if there is
	 * 		no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	double higherDoubleKey(double key);

	/**
	 * Returns a key-value mapping associated with the least key strictly greater than the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the least key greater than {@code key}, or {@code null} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	DoubleEntry higherEntry(double key);

	/**
	 * Returns the greatest key strictly less than the given key, or {@code default double value} if
	 * there is no such key.
	 *
	 * @param key the key.
	 * @return the greatest key less than {@code key}, or {@code default double value} if there is
	 * 		no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	double lowerDoubleKey(double key);

	/**
	 * Returns a key-value mapping associated with the greatest key strictly less than the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the greatest key less than {@code key}, or {@code null} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	DoubleEntry lowerEntry(double key);

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
	DoubleNavigableMap<ENTRY_SET> subMap(double beginKey, boolean beginInclusive, double endKey, boolean endInclusive);

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
	DoubleNavigableMap<ENTRY_SET> tailMap(double beginKey, boolean inclusive);
}
