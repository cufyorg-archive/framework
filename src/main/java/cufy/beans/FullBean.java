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

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A <font color="yellow"><b>{@link FullBean}</b></font> is a {@code Bean} that can store entries that it does not has a
 * {@link java.lang.reflect.Field fields} for it.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author LSafer
 * @version 0.1.5
 * @since 0.0.1 ~2019.12.09
 */
public interface FullBean<K, V> extends Bean<K, V> {
	@Override
	default void clear() {
		PersistingMethods.clear(this, this);
	}

	@Override
	default V compute(K key, BiFunction<? super K, ? super V, ? extends V> function) {
		return PersistingMethods.compute(this, this, key, function);
	}

	@Override
	default V computeIfAbsent(K key, Function<? super K, ? extends V> function) {
		return PersistingMethods.computeIfAbsent(this, this, key, function);
	}

	@Override
	default V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> function) {
		return PersistingMethods.computeIfPresent(this, this, key, function);
	}

	@Override
	default boolean containsKey(Object key) {
		return PersistingMethods.containsKey(this, this, key);
	}

	@Override
	default boolean containsValue(Object value) {
		return PersistingMethods.containsValue(this, this, value);
	}

	@Override
	default void forEach(BiConsumer<? super K, ? super V> consumer) {
		PersistingMethods.forEach(this, this, consumer);
	}

	@Override
	default V get(Object key) {
		return PersistingMethods.get(this, this, key);
	}

	@Override
	default V getOrDefault(Object key, V defaultValue) {
		return PersistingMethods.getOrDefault(this, this, key, defaultValue);
	}

	@Override
	default boolean isEmpty() {
		return PersistingMethods.isEmpty(this, this);
	}

	@Override
	default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> function) {
		return PersistingMethods.merge(this, this, key, value, function);
	}

	@Override
	default V put(K key, V value) {
		return PersistingMethods.put(this, this, key, value);
	}

	@Override
	default void putAll(Map<? extends K, ? extends V> map) {
		PersistingMethods.putAll(this, this, map);
	}

	@Override
	default V putIfAbsent(K key, V value) {
		return PersistingMethods.putIfAbsent(this, this, key, value);
	}

	@Override
	default V remove(Object key) {
		return PersistingMethods.remove(this, this, key);
	}

	@Override
	default boolean remove(Object key, Object value) {
		return PersistingMethods.remove(this, this, key, value);
	}

	@Override
	default boolean replace(K key, V oldValue, V newValue) {
		return PersistingMethods.replace(this, this, key, oldValue, newValue);
	}

	@Override
	default V replace(K key, V value) {
		return PersistingMethods.replace(this, this, key, value);
	}

	@Override
	default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		PersistingMethods.replaceAll(this, this, function);
	}

	@Override
	default int size() {
		return PersistingMethods.size(this, this);
	}

	@Override
		//the returned set should be constant
	EntrySet<K, V> entrySet();

	@Override
		//the returned set should be constant
	KeySet<K> keySet();

	@Override
		//the returned collection should be constant
	Values<V> values();
}
