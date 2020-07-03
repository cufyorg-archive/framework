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
import java.util.Objects;
import java.util.Set;

/**
 * A bean represents a class that has {@link Property}s but does not {@code extends} {@link Bean}.
 *
 * @param <K> the type of keys maintained by this map.
 * @param <V> the type of mapped values.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.07.03
 */
public class AnonymousBean<K, V> implements FullBean<K, V> {
	/**
	 * The instance this bean is reading-from/writing-to.
	 */
	private final Object instance;
	/**
	 * The entrySet of this map.
	 */
	private Set<Map.Entry<K, V>> entrySet;
	/**
	 * The keySet of this map.
	 */
	private Set<K> keySet;
	/**
	 * The values-collection of this map.
	 */
	private Collection<V> values;

	/**
	 * Construct a new anonymous-bean that represents the given {@code instance}.
	 *
	 * @param instance the instance to be represented by the constructed anonymous-bean.
	 * @throws NullPointerException if the given {@code bean} is null.
	 */
	public AnonymousBean(Object instance) {
		Objects.requireNonNull(instance, "instance");
		this.instance = instance;
	}

	@Override
	public int size() {
		return FullBean.Methods.size(this, this.instance);
	}

	@Override
	public boolean isEmpty() {
		return FullBean.Methods.isEmpty(this, this.instance);
	}

	@Override
	public boolean containsKey(Object key) {
		return FullBean.Methods.containsKey(this, this.instance, key);
	}

	@Override
	public boolean containsValue(Object value) {
		return FullBean.Methods.containsValue(this, this.instance, value);
	}

	@Override
	public V get(Object key) {
		return FullBean.Methods.get(this, this.instance, key);
	}

	@Override
	public V put(K key, V value) {
		return FullBean.Methods.put(this, this.instance, key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		FullBean.Methods.putAll(this, this.instance, map);
	}

	@Override
	public Set<K> keySet() {
		if (this.keySet == null)
			this.keySet = FullBean.Methods.keySet(this, this.instance);

		return this.keySet;
	}

	@Override
	public Collection<V> values() {
		if (this.values == null)
			this.values = FullBean.Methods.values(this, this.instance);

		return this.values;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		if (this.entrySet == null)
			this.entrySet = FullBean.Methods.entrySet(this, this.instance);

		return this.entrySet;
	}
}
