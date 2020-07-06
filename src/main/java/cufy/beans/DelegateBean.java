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
 * A <font color="yellow"><b>{@link DelegateBean}</b></font> is a {@code bean} that delegates to another bean.
 *
 * @param <K> the type of the keys in this bean.
 * @param <V> the type of the values in this bean.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.07.06
 */
public abstract class DelegateBean<K, V> implements Bean<K, V> {
	@Override
	public void clear() {
		PersistingMethods.clear(this, this.instance());
	}

	@Override
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> function) {
		return PersistingMethods.compute(this, this.instance(), key, function);
	}

	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> function) {
		return PersistingMethods.computeIfAbsent(this, this.instance(), key, function);
	}

	@Override
	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> function) {
		return PersistingMethods.computeIfPresent(this, this.instance(), key, function);
	}

	@Override
	public boolean containsKey(Object key) {
		return PersistingMethods.containsKey(this, this.instance(), key);
	}

	@Override
	public boolean containsValue(Object value) {
		return PersistingMethods.containsValue(this, this.instance(), value);
	}

	@Override
	public void forEach(BiConsumer<? super K, ? super V> consumer) {
		PersistingMethods.forEach(this, this.instance(), consumer);
	}

	@Override
	public V get(Object key) {
		return PersistingMethods.get(this, this.instance(), key);
	}

	@Override
	public V getOrDefault(Object key, V defaultValue) {
		return PersistingMethods.getOrDefault(this, this.instance(), key, defaultValue);
	}

	@Override
	public boolean isEmpty() {
		return PersistingMethods.isEmpty(this, this);
	}

	@Override
	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> function) {
		return PersistingMethods.merge(this, this.instance(), key, value, function);
	}

	@Override
	public V put(K key, V value) {
		return PersistingMethods.put(this, this.instance(), key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		PersistingMethods.putAll(this, this.instance(), map);
	}

	@Override
	public V putIfAbsent(K key, V value) {
		return PersistingMethods.putIfAbsent(this, this.instance(), key, value);
	}

	@Override
	public V remove(Object key) {
		return PersistingMethods.remove(this, this.instance(), key);
	}

	@Override
	public boolean remove(Object key, Object value) {
		return PersistingMethods.remove(this, this.instance(), key, value);
	}

	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		return PersistingMethods.replace(this, this.instance(), key, oldValue, newValue);
	}

	@Override
	public V replace(K key, V value) {
		return PersistingMethods.replace(this, this.instance(), key, value);
	}

	@Override
	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		PersistingMethods.replaceAll(this, this.instance(), function);
	}

	@Override
	public int size() {
		return PersistingMethods.size(this, this);
	}

	@Override
	//the returned set should be constant
	public abstract EntrySet<K, V> entrySet();

	@Override
	//the returned set should be constant
	public abstract KeySet<K> keySet();

	@Override
	//the returned collection should be constant
	public abstract Values<V> values();

	/**
	 * Get the instance of the {@code Bean} this delegates to.
	 *
	 * @return the instance this delegate is delegating to.
	 */
	//the returned instance should be constant
	protected abstract Object instance();
}
