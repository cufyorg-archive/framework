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

import cufy.util.Reflection;

import java.lang.reflect.Field;
import java.util.*;

/**
 * A bean designed to have a final entrySet. Since the original {@link Bean} can't have a final entrySet.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author LSafer
 * @version 0.1.3
 * @since 0.0.1 ~2019.12.09
 */
public interface FullBean<K, V> extends Bean<K, V> {
	@Override
	default int size() {
		Set<Entry<K, V>> entrySet = this.entrySet();
		return entrySet.size();
	}

	@Override
	default boolean isEmpty() {
		Set<Entry<K, V>> entrySet = this.entrySet();
		return entrySet.isEmpty();
	}

	@Override
	default boolean containsKey(Object key) {
		for (Entry entry : this.entrySet()) {
			Object entryKey = entry.getKey();

			if (Objects.equals(key, entryKey))
				return true;
		}

		return false;
	}

	@Override
	default boolean containsValue(Object value) {
		for (Entry entry : this.entrySet()) {
			Object entryValue = entry.getValue();

			if (Objects.equals(value, entryValue))
				return true;
		}
		return false;
	}

	@Override
	default V get(Object key) {
		for (Entry<K, V> entry : this.entrySet()) {
			K entryKey = entry.getKey();

			if (Objects.equals(entryKey, key))
				return entry.getValue();
		}

		//noinspection ReturnOfNull
		return null;
	}

	@Override
	default V put(K key, V value) {
		Set<Entry<K, V>> entrySet = this.entrySet();

		//looking in the existing entries
		for (Entry<K, V> entry : entrySet) {
			K entryKey = entry.getKey();

			if (Objects.equals(entryKey, key))
				return entry.setValue(value);
		}

		//looking in the fields with removed entries, Or add a simple entry
		Object instance = this.getInstance();
		FieldEntry<K, V> entry = Bean.entry(instance, key, value);
		entrySet.add(entry != null ? entry : new AbstractMap.SimpleEntry<>(key, value));
		return null;
	}

	@Override
	default V remove(Object key) {
		Set<Entry<K, V>> entrySet = this.entrySet();

		Iterator<Entry<K, V>> iterator = entrySet.iterator();
		while (iterator.hasNext()) {
			Entry<K, V> entry = iterator.next();
			K entryKey = entry.getKey();

			if (Objects.equals(key, entryKey)) {
				V old = entry.getValue();
				iterator.remove();
				return old;
			}
		}

		//noinspection ReturnOfNull
		return null;
	}

	@Override
	default void putAll(Map<? extends K, ? extends V> map) {
		//DON'T replace it with something like map.forEach(this::put)!
		Objects.requireNonNull(map, "map");
		//noinspection NestedMethodCall
		Set<K> keys = new HashSet<>(map.keySet());
		Set<Entry<K, V>> entrySet = this.entrySet();

		//looking in the existing entries
		for (Entry<K, V> entry : entrySet) {
			K entryKey = entry.getKey();

			if (keys.remove(entryKey)) {
				V value = map.get(entryKey);
				entry.setValue(value);
			}
		}

		//looking in the fields with removed entries
		Object instance = this.getInstance();
		Class<?> klass = instance.getClass();

		for (Field field : Reflection.getAllFields(klass))
			if (field.isAnnotationPresent(Property.class))
				for (K key : (K[]) Bean.Util.getKeys(field))
					if (keys.remove(key)) {
						V value = map.get(key);
						FieldEntry<K, V> entry = Bean.entry(instance, field, key, value);
						entrySet.add(entry);
					}

		//adding simple entries
		for (K key : keys) {
			V value = map.get(key);
			entrySet.add(new AbstractMap.SimpleEntry(key, value));
		}
	}

	@Override
	default void clear() {
		Set<Entry<K, V>> entrySet = this.entrySet();
		entrySet.clear();
	}

	@Override
	Set<Entry<K, V>> entrySet();
}
