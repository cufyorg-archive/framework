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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A bean represents a class that has {@link Property}s but does not {@code extends} {@link Bean}.
 *
 * @param <K> the type of keys maintained by this map.
 * @param <V> the type of mapped values.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.07.03
 */
public class DelegateBean<K, V> implements FullBean<K, V>, Serializable {
	/**
	 * The instance this bean is reading-from/writing-to.
	 */
	protected final Object instance;
	/**
	 * The entrySet of this map.
	 */
	private transient DelegateEntrySet<K, V> entrySet;
	/**
	 * The keySet of this map.
	 */
	private transient DelegateKeySet<K> keySet;
	/**
	 * The values-collection of this map.
	 */
	private transient DelegateValues<V> values;

	/**
	 * Construct a new anonymous-bean that represents the given {@code instance}.
	 *
	 * @param instance the instance to be represented by the constructed anonymous-bean.
	 * @throws NullPointerException if the given {@code bean} is null.
	 */
	public DelegateBean(Object instance) {
		Objects.requireNonNull(instance, "instance");
		this.instance = instance;
	}

	@Override
	public int hashCode() {
		return Methods.Delegate.hashCode(this, this.instance);
	}

	@Override
	public boolean equals(Object object) {
		return Methods.Delegate.equals(this, this.instance, object);
	}

	@Override
	public String toString() {
		return Methods.Delegate.toString(this, this.instance);
	}

	@Override
	public int size() {
		return Methods.Delegate.size(this, this.instance);
	}

	@Override
	public boolean isEmpty() {
		return Methods.Delegate.isEmpty(this, this.instance);
	}

	@Override
	public boolean containsKey(Object key) {
		return Methods.Delegate.containsKey(this, this.instance, key);
	}

	@Override
	public boolean containsValue(Object value) {
		return Methods.Delegate.containsValue(this, this.instance, value);
	}

	@Override
	public V get(Object key) {
		return Methods.Delegate.get(this, this.instance, key);
	}

	@Override
	public V put(K key, V value) {
		return Methods.Delegate.put(this, this.instance, key, value);
	}

	@Override
	public V remove(Object key) {
		return Methods.Delegate.remove(this, this.instance, key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		Methods.Delegate.putAll(this, this.instance, map);
	}

	@Override
	public void clear() {
		Methods.Delegate.clear(this, this.instance);
	}

	@Override
	public Set<K> keySet() {
		if (this.keySet == null)
			this.keySet = Methods.Delegate.keySet(this, this.instance);

		return this.keySet;
	}

	@Override
	public Collection<V> values() {
		if (this.values == null)
			this.values = Methods.Delegate.values(this, this.instance);

		return this.values;
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		if (this.entrySet == null)
			this.entrySet = Methods.Delegate.entrySet(this, this.instance);

		return (Set) this.entrySet;
	}

	@Override
	public V getOrDefault(Object key, V defaultValue) {
		return Methods.Delegate.getOrDefault(this, this.instance, key, defaultValue);
	}

	@Override
	public void forEach(BiConsumer<? super K, ? super V> consumer) {
		Methods.Delegate.forEach(this, this.instance, consumer);
	}

	@Override
	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		Methods.Delegate.replaceAll(this, this.instance, function);
	}

	@Override
	public V putIfAbsent(K key, V value) {
		return Methods.Delegate.putIfAbsent(this, this.instance, key, value);
	}

	@Override
	public boolean remove(Object key, Object value) {
		return Methods.Delegate.remove(this, this.instance, key, value);
	}

	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		return Methods.Delegate.replace(this, this.instance, key, oldValue, newValue);
	}

	@Override
	public V replace(K key, V value) {
		return Methods.Delegate.replace(this, this.instance, key, value);
	}

	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> function) {
		return Methods.Delegate.computeIfAbsent(this, this.instance, key, function);
	}

	@Override
	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> function) {
		return Methods.Delegate.computeIfPresent(this, this.instance, key, function);
	}

	@Override
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> function) {
		return Methods.Delegate.compute(this, this.instance, key, function);
	}

	@Override
	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> function) {
		return Methods.Delegate.merge(this, this.instance, key, value, function);
	}

	@SuppressWarnings("JavaDoc")
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		Methods.Delegate.readObject(this, this, stream);
	}

	@SuppressWarnings("JavaDoc")
	private void writeObject(ObjectOutputStream stream) throws IOException {
		Methods.Delegate.writeObject(this, this, stream);
	}
}
