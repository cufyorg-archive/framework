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

import java.util.*;

/**
 * A map specialized for primitive values. Map view collections is kept for the sub class to provide
 * its own implementation to allow various mixtures of (primitive|object)-(primitive|object)
 * mappings.
 *
 * @param <K>           the type of the keys.
 * @param <V>           the type of the values.
 * @param <BI_CONSUMER> the type of the bi-consumer.
 * @param <BI_FUNCTION> the type of the bi-function.
 * @param <COMPARATOR>  the type of the comparator.
 * @param <ENTRY_SET>   the type of the set of the entries.
 * @param <KEY_SET>     the type of the set of the keys.
 * @param <VALUES>      the type of the collection of the values.
 * @param <MAP>         the type of the map.
 * @param <SORTED_MAP>  the type of the sorted map.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
@SuppressWarnings("ComparatorNotSerializable")
public interface PrimitiveSortedMap<
		K,
		V,
		BI_CONSUMER,
		BI_FUNCTION,
		COMPARATOR extends Comparator<K>,
		ENTRY_SET extends Set<Map.Entry<K, V>>,
		KEY_SET extends Set<K>,
		VALUES extends Collection<V>,
		MAP extends PrimitiveMap<K, V, BI_CONSUMER, BI_FUNCTION, ENTRY_SET, KEY_SET, VALUES, MAP>,
		SORTED_MAP extends PrimitiveSortedMap<K, V, BI_CONSUMER, BI_FUNCTION, COMPARATOR, ENTRY_SET, KEY_SET, VALUES, MAP, SORTED_MAP>
		> extends PrimitiveMap<K, V, BI_CONSUMER, BI_FUNCTION, ENTRY_SET, KEY_SET, VALUES, MAP>, SortedMap<K, V> {
	@Override
	COMPARATOR comparator();

	@Override
	SORTED_MAP headMap(K endKey);

	@Override
	SORTED_MAP subMap(K beginKey, K endKey);

	@Override
	SORTED_MAP tailMap(K beginKey);
}
