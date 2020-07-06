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
package cufy.beans;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A bean designed to have a final entrySet. Since the original {@link Bean} can't have a final entrySet.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author LSafer
 * @version 0.1.5
 * @since 0.0.1 ~2019.12.09
 */
public interface FullBean<K, V> extends Bean<K, V> {
	@Override
	default int size() {
		return DelegateMethods.size(this, this);
	}

	@Override
	default boolean isEmpty() {
		return DelegateMethods.isEmpty(this, this);
	}

	@Override
	default boolean containsKey(Object key) {
		return DelegateMethods.containsKey(this, this, key);
	}

	@Override
	default boolean containsValue(Object value) {
		return DelegateMethods.containsValue(this, this, value);
	}

	@Override
	default V get(Object key) {
		return DelegateMethods.get(this, this, key);
	}

	@Override
	default V put(K key, V value) {
		return DelegateMethods.put(this, this, key, value);
	}

	@Override
	default V remove(Object key) {
		return DelegateMethods.remove(this, this, key);
	}

	@Override
	default void putAll(Map<? extends K, ? extends V> map) {
		DelegateMethods.putAll(this, this, map);
	}

	@Override
	default void clear() {
		DelegateMethods.clear(this, this);
	}

	@Override
		//the returned set should be constant
	Set<K> keySet();

	@Override
		//the returned collection should be constant
	Collection<V> values();

	@Override
		//the returned set should be constant
	Set<Map.Entry<K, V>> entrySet();

	@Override
	default V getOrDefault(Object key, V defaultValue) {
		return DelegateMethods.getOrDefault(this, this, key, defaultValue);
	}

	@Override
	default void forEach(BiConsumer<? super K, ? super V> consumer) {
		DelegateMethods.forEach(this, this, consumer);
	}

	@Override
	default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		DelegateMethods.replaceAll(this, this, function);
	}

	@Override
	default V putIfAbsent(K key, V value) {
		return DelegateMethods.putIfAbsent(this, this, key, value);
	}

	@Override
	default boolean remove(Object key, Object value) {
		return DelegateMethods.remove(this, this, key, value);
	}

	@Override
	default boolean replace(K key, V oldValue, V newValue) {
		return DelegateMethods.replace(this, this, key, oldValue, newValue);
	}

	@Override
	default V replace(K key, V value) {
		return DelegateMethods.replace(this, this, key, value);
	}

	@Override
	default V computeIfAbsent(K key, Function<? super K, ? extends V> function) {
		return DelegateMethods.computeIfAbsent(this, this, key, function);
	}

	@Override
	default V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> function) {
		return DelegateMethods.computeIfPresent(this, this, key, function);
	}

	@Override
	default V compute(K key, BiFunction<? super K, ? super V, ? extends V> function) {
		return DelegateMethods.compute(this, this, key, function);
	}

	@Override
	default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> function) {
		return DelegateMethods.merge(this, this, key, value, function);
	}
}
