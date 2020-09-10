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

import java.util.ConcurrentModificationException;
import java.util.Map;

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
public interface PrimitiveMap
		<K, V, R, N>
		extends
		Map<K, V> {
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
	void forEach(R consumer);

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
	void replaceAll(N function);

	//default primitive compute(primitive key, PrimitiveObjBiFunction<Primitive, Primitive> function)
	//default primitive computeIfAbsent(primitive key, PrimitiveFunction<Primitive> function)
	//default primitive computeIfPresent(primitive key, PrimitiveBiFunction<Primitive> function)
	//default primitive getPrimitiveOrDefault(primitive key, primitive defaultValue)
	//default primitive merge(primitive key, primitive value, PrimitiveBiFunction<Primitive> function)
	//default primitive putPrimitiveIfAbsent(primitive key, primitive value)
	//default boolean removePrimitive(primitive key, primitive value)
	//default boolean replacePrimitive(primitive key, primitive value)
	//default boolean replacePrimitive(primitive key, primitive oldValue, primitive newValue)
	//boolean containsKey(primitive key)
	//boolean containsValue(primitive value)
	//primitive getPrimitive(primitive key)
	//primitive putPrimitive(primitive key, primitive value)
	//primitive removePrimitive(primitive key)

	/**
	 * An entry specialized for primitive values.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.02
	 */
	interface PrimitiveEntry
			<K, V>
			extends
			Map.Entry<K, V> {
		//primitive getPrimitiveKey();
		//primitive getPrimitiveValue();
		//primitive setPrimitiveValue(primitive value);
	}
}
