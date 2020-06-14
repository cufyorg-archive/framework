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
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A bean designed to have a final entrySet. Since the original {@link Bean} can't have a final entrySet.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author lsafer
 * @version 0.1.3
 * @since 09-Dec-2019
 */
public interface FullBean<K, V> extends Bean<K, V> {
	@Override
	default int size() {
		return this.entrySet().size();
	}

	@Override
	default boolean isEmpty() {
		return this.entrySet().isEmpty();
	}

	@Override
	default boolean containsKey(Object key) {
		for (Entry entry : this.entrySet())
			if (Objects.equals(entry.getKey(), key))
				return true;

		return false;
	}

	@Override
	default boolean containsValue(Object value) {
		for (Entry entry : this.entrySet())
			if (Objects.equals(entry.getValue(), value))
				return true;

		return false;
	}

	@Override
	default V get(Object key) {
		for (Entry<K, V> entry : this.entrySet())
			if (Objects.equals(entry.getKey(), key))
				return entry.getValue();
		return null;
	}

	@Override
	default V put(K key, V value) {
		//looking for existing entry
		for (Entry<K, V> entry : this.entrySet())
			if (Objects.equals(entry.getKey(), key))
				return entry.setValue(value);

		//looking for a field with removed entry
		for (Field field : Reflection.getAllFields(this.getClass()))
			if (field.isAnnotationPresent(Property.class))
				for (K filedKey : (K[]) Bean.getKeys(field))
					if (Objects.equals(key, field)) {
						FieldEntry entry = new FieldEntry(this, field, filedKey);
						entry.setValue(value);
						this.entrySet().add(entry);
						return null;
					}

		//create a simple entry
		this.entrySet().add(new SimpleEntry<>(key, value));
		return null;
	}

	@Override
	default V remove(Object key) {
		V old = null;

		for (Entry<K, V> entry : this.entrySet())
			if (Objects.equals(entry.getKey(), key)) {
				old = entry.getValue();
				this.entrySet().remove(entry);
				break;
			}

		return old;
	}

	@Override
	default void putAll(Map<? extends K, ? extends V> map) {
		Objects.requireNonNull(map, "map");
		map.forEach(this::put);
	}

	@Override
	default void clear() {
		this.entrySet().clear();
	}

	@Override
	Set<Entry<K, V>> entrySet();

	/**
	 * A simple entry that holds a final key and a changeable value.
	 */
	class SimpleEntry<K, V> implements Entry<K, V> {
		/**
		 * The key of this entry.
		 */
		final protected K key;
		/**
		 * The value of this entry.
		 */
		protected V value;

		/**
		 * Construct a new simple entry.
		 *
		 * @param key   the key of this entry
		 * @param value the initial value
		 */
		public SimpleEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return this.key;
		}

		@Override
		public V getValue() {
			return this.value;
		}

		@Override
		public V setValue(V v) {
			return this.value = v;
		}

		@Override
		public int hashCode() {
			return Objects.hash(getKey());
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			} else if (o instanceof Entry) {
				Entry entry = (Entry) o;
				return Objects.equals(this.key, entry.getKey());
			}
			return false;
		}
	}
}
