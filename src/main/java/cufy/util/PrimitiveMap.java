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

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Set;

/**
 * A map specialized for primitive values. Map view collections is kept for the sub class to provide
 * its own implementation to allow various mixtures of (primitive|object)-(primitive|object)
 * mappings.
 *
 * @param <K>           the type of the keys.
 * @param <V>           the type of the values.
 * @param <BI_CONSUMER> the type of the bi-consumer.
 * @param <BI_FUNCTION> the type of the bi-function.
 * @param <ENTRY_SET>   the type of the set of the entries.
 * @param <KEY_SET>     the type of the set of the keys.
 * @param <VALUES>      the type of the collection of the values.
 * @param <MAP>         the type of the map.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface PrimitiveMap<
		K,
		V,
		BI_CONSUMER,
		BI_FUNCTION,
		ENTRY_SET extends Set<Map.Entry<K, V>>,
		KEY_SET extends Set<K>,
		VALUES extends Collection<V>,
		MAP extends PrimitiveMap<K, V, BI_CONSUMER, BI_FUNCTION, ENTRY_SET, KEY_SET, VALUES, MAP>
		> extends Map<K, V> {
	@Override
	ENTRY_SET entrySet();

	@Override
	KEY_SET keySet();

	@Override
	VALUES values();

	/**
	 * Performs the given action for each entry in this map until all entries have been processed or
	 * the action throws an exception. Unless otherwise specified by the implementing class, actions
	 * are performed in the order of entry set iteration (if an iteration order is specified.)
	 * Exceptions thrown by the action are relayed to the caller.
	 *
	 * @param consumer the action to be performed for each entry.
	 * @throws NullPointerException            if the given {@code consumer} is null.
	 * @throws ConcurrentModificationException if an entry is found to be removed during iteration.
	 * @since 0.1.5 ~2020.09.01
	 */
	void forEach(BI_CONSUMER consumer);

	/**
	 * Copies all of the mappings from the specified map to this map (optional operation). The
	 * effect of this call is equivalent to that of calling {@link #put(Object, Object) put(k, v)}
	 * on this map once for each mapping from key <tt>k</tt> to value <tt>v</tt> in the specified
	 * map. The behavior of this operation is undefined if the specified map is modified while the
	 * operation is in progress.
	 *
	 * @param map mappings to be stored in this map.
	 * @throws UnsupportedOperationException if the {@code putAll} operation is not supported by
	 *                                       this map.
	 * @throws NullPointerException          if the given {@code map} is null.
	 * @throws IllegalArgumentException      if some property of a key or value in the specified map
	 *                                       prevents it from being stored in this map.
	 * @since 0.1.5 ~2020.09.01
	 */
	void putAll(MAP map);

	/**
	 * Replaces each entry's value with the result of invoking the given function on that entry
	 * until all entries have been processed or the function throws an exception. Exceptions thrown
	 * by the function are relayed to the caller.
	 *
	 * @param function the function to apply to each entry.
	 * @throws UnsupportedOperationException   if the {@code set} operation is not supported by this
	 *                                         map.
	 * @throws NullPointerException            if the given {@code function} is null.
	 * @throws IllegalArgumentException        if some property of a replacement value prevents it
	 *                                         from being stored in this map.
	 * @throws ConcurrentModificationException if an entry is found to be removed during iteration.
	 * @since 0.1.5 ~2020.09.01
	 */
	void replaceAll(BI_FUNCTION function);

	/**
	 * An entry specialized for primitive values.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.02
	 */
	interface PrimitiveEntry<
			K,
			V
			> extends Map.Entry<K, V> {
	}
}