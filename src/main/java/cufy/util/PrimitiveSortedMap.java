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

import java.util.SortedMap;

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
public interface PrimitiveSortedMap
		<K, V, R, N>
		extends
		PrimitiveMap<K, V, R, N>,
		SortedMap<K, V> {
	@Override
	PrimitiveComparator<K, ?, ?, ?, ?, ?> comparator();

	@Override
	PrimitiveSortedMap<K, V, R, N> headMap(K endKey);

	@Override
	PrimitiveSortedMap<K, V, R, N> subMap(K beginKey, K endKey);

	@Override
	PrimitiveSortedMap<K, V, R, N> tailMap(K beginKey);

	//primitive firstPrimitiveKey()
	//PrimitiveSortedMap headMap(primitive endKey)
	//primitive lastPrimitiveKey()
	//PrimitiveSortedMap subMap(primitive beginKey, primitive endKey)
	//PrimitiveSortedMap tailMap(primitive beginKey)
}
