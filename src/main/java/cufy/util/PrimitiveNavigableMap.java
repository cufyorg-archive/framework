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
package cufy.util;

import java.util.NavigableMap;

/**
 * A map specialized for primitive values. Map view collections is kept for the sub class to provide
 * its own implementation to allow various mixtures of (primitive|object)-(primitive|object)
 * mappings.
 *
 * @param <K> the type of the keys.
 * @param <V> the type of the values.
 * @param <R> the type of the bi-consumer.
 * @param <N> the type of the bi-function.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface PrimitiveNavigableMap
		<K, V, R, N>
		extends
		PrimitiveSortedMap<K, V, R, N>,
		NavigableMap<K, V> {
	@Override
	PrimitiveEntry<K, V> ceilingEntry(K key);

	@Override
	PrimitiveNavigableMap<K, V, R, N> descendingMap();

	@Override
	PrimitiveEntry<K, V> firstEntry();

	@Override
	PrimitiveEntry<K, V> floorEntry(K key);

	@Override
	PrimitiveNavigableMap<K, V, R, N> headMap(K endKey, boolean inclusive);

	@Override
	PrimitiveEntry<K, V> higherEntry(K key);

	@Override
	PrimitiveEntry<K, V> lastEntry();

	@Override
	PrimitiveEntry<K, V> lowerEntry(K key);

	@Override
	PrimitiveEntry<K, V> pollFirstEntry();

	@Override
	PrimitiveEntry<K, V> pollLastEntry();

	@Override
	PrimitiveNavigableMap<K, V, R, N> subMap(K beginKey, boolean beginInclusive, K endKey, boolean endInclusive);

	@Override
	PrimitiveNavigableMap<K, V, R, N> tailMap(K beginKey, boolean inclusive);

	//primitive ceilingPrimitiveKey(primitive key)
	//PrimitiveEntry ceilingEntry(primitive key)
	//primitive floorPrimitiveKey(primitive key)
	//PrimitiveEntry floorEntry(primitive key)
	//boolean hasCeilingKey(primitive key)
	//boolean hasFloorKey(primitive key)
	//boolean hasHigherKey(primitive key)
	//boolean hasLowerKey(primitive key)
	//PrimitiveNavigableMap headMap(primitive endKey, boolean inclusive)
	//primitive higherPrimitiveKey(primitive key)
	//PrimitiveEntry higherEntry(primitive key)
	//primitive lowerPrimitiveKey(primitive key)
	//PrimitiveEntry lowerEntry(primitive key)
	//PrimitiveNavigableMap subMap(primitive beginKey, boolean beginInclusive, primitive endKey, boolean endInclusive)
	//PrimitiveNavigableMap tailMap(primitive beginKey, boolean inclusive)
}
