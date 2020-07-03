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

import java.lang.reflect.Field;
import java.util.*;

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
		return FullBean.Methods.size(this, this);
	}

	@Override
	default boolean isEmpty() {
		return FullBean.Methods.isEmpty(this, this);
	}

	@Override
	default boolean containsKey(Object key) {
		return FullBean.Methods.containsKey(this, this, key);
	}

	@Override
	default boolean containsValue(Object value) {
		return FullBean.Methods.containsValue(this, this, value);
	}

	@Override
	default V get(Object key) {
		return FullBean.Methods.get(this, this, key);
	}

	@Override
	default V put(K key, V value) {
		return FullBean.Methods.put(this, this, key, value);
	}

	@Override
	default V remove(Object key) {
		return FullBean.Methods.remove(this, this, key);
	}

	@Override
	default void putAll(Map<? extends K, ? extends V> map) {
		FullBean.Methods.putAll(this, this, map);
	}

	@Override
	default void clear() {
		FullBean.Methods.clear(this, this);
	}

	@Override
	Set<K> keySet();

	@Override
	Collection<V> values();

	@Override
	Set<Map.Entry<K, V>> entrySet();

	/**
	 * An entrySet for fullBeans.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 */
	final class FullBeanEntrySet<K, V> extends HashSet<Map.Entry<K, V>> {
		/**
		 * Construct a new entrySet for the given {@code bean} that have the given {@code instance}.
		 *
		 * @param bean     the bean this entrySet is for.
		 * @param instance the instance the given {@code bean} is for.
		 * @throws NullPointerException if the given {@code bean} or {@code instance} is null.
		 */
		private FullBeanEntrySet(FullBean<K, V> bean, Object instance) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");
			this.addAll(Bean.Methods.entrySet(instance));
		}
	}

	/**
	 * A keySet for fullBeans.
	 *
	 * @param <K> the type of the keys.
	 */
	final class FullBeanKeySet<K> extends AbstractSet<K> {
		/**
		 * The bean this keySet is for.
		 */
		private final FullBean<K, ?> bean;

		/**
		 * Construct a new keySet for the given {@code bean} that have the given {@code instance}.
		 *
		 * @param bean     the bean this keySet is for.
		 * @param instance the instance the given {@code bean} is for.
		 * @throws NullPointerException if the given {@code bean} or {@code instance} is null.
		 */
		private FullBeanKeySet(FullBean<K, ?> bean, Object instance) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");
			this.bean = bean;
		}

		@Override
		public FullBeanKeySetIterator iterator() {
			return new FullBeanKeySetIterator();
		}

		@Override
		public int size() {
			return this.bean.size();
		}

		@Override
		public boolean isEmpty() {
			return this.bean.isEmpty();
		}

		@Override
		public boolean contains(Object k) {
			return this.bean.containsKey(k);
		}

		@Override
		public void clear() {
			this.bean.clear();
		}

		/**
		 * An iterator for the keySet of a fullBean.
		 */
		public final class FullBeanKeySetIterator implements Iterator<K> {
			/**
			 * The iterator backing this iterator.
			 */
			private final Iterator<Map.Entry<K, ?>> iterator = (Iterator) FullBeanKeySet.this.bean.entrySet().iterator();

			/**
			 * Construct a new fullBean keySet iterator.
			 */
			private FullBeanKeySetIterator() {
			}

			@Override
			public boolean hasNext() {
				return this.iterator.hasNext();
			}

			@Override
			public K next() {
				return this.iterator.next().getKey();
			}

			@Override
			public void remove() {
				this.iterator.remove();
			}
		}
	}

	/**
	 * A values-collection for fullBeans.
	 *
	 * @param <V> the type of the values.
	 */
	final class FullBeanValues<V> extends AbstractCollection<V> {
		/**
		 * The bean this values-collection is for.
		 */
		private final FullBean<?, V> bean;

		/**
		 * Construct a new values-collection for the given {@code bean} that have the given {@code instance}.
		 *
		 * @param bean     the bean this values-collection is for.
		 * @param instance the instance the given {@code bean} is for.
		 * @throws NullPointerException if the given {@code bean} or {@code instance} is null.
		 */
		private FullBeanValues(FullBean<?, V> bean, Object instance) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");
			this.bean = bean;
		}

		@Override
		public FullBeanValuesIterator iterator() {
			return new FullBeanValuesIterator();
		}

		@Override
		public int size() {
			return this.bean.size();
		}

		@Override
		public boolean isEmpty() {
			return this.bean.isEmpty();
		}

		@Override
		public boolean contains(Object v) {
			return this.bean.containsValue(v);
		}

		@Override
		public void clear() {
			this.bean.clear();
		}

		/**
		 * An iterator that iterates the values of a fullBean.
		 */
		public final class FullBeanValuesIterator implements Iterator<V> {
			/**
			 * The iterator backing this iterator.
			 */
			private final Iterator<Map.Entry<?, V>> iterator = (Iterator) FullBeanValues.this.bean.entrySet().iterator();

			/**
			 * Construct a new fullBean values iterator.
			 */
			private FullBeanValuesIterator() {
			}

			@Override
			public boolean hasNext() {
				return this.iterator.hasNext();
			}

			@Override
			public V next() {
				return this.iterator.next().getValue();
			}

			@Override
			public void remove() {
				this.iterator.remove();
			}
		}
	}

	/**
	 * The default methods of the interface {@link FullBean}.
	 */
	final class Methods {
		/**
		 * This is an util class and must not be instanced as an object.
		 *
		 * @throws AssertionError when called.
		 */
		private Methods() {
			throw new AssertionError("No instance for you!");
		}

		/**
		 * Perform the default {@link Map#clear()} to the given {@code bean}.
		 *
		 * @param bean     the bean to perform the method to.
		 * @param instance the instance the bean is having.
		 * @param <K>      the type of the keys in the given {@code bean}.
		 * @param <V>      the type of the values in the given {@code bean}.
		 * @throws NullPointerException if the given {@code bean} or {@code instance} is null.
		 */
		public static <K, V> void clear(FullBean<K, V> bean, Object instance) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");
			bean.entrySet().clear();
		}

		/**
		 * Perform the default {@link Map#containsKey(Object)} to the given {@code bean}.
		 *
		 * @param bean     the bean to perform the method to.
		 * @param instance the instance the bean is having.
		 * @param key      the key to be checked
		 * @param <K>      the type of the keys in the given {@code bean}.
		 * @param <V>      the type of the values in the given {@code bean}.
		 * @return true, if the given {@code bean} has the given {@code key}.
		 * @throws NullPointerException if the given {@code bean} or {@code instance} is null.
		 */
		public static <K, V> boolean containsKey(FullBean<K, V> bean, Object instance, Object key) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");

			for (Map.Entry<K, V> entry : bean.entrySet())
				if (Objects.equals(key, entry.getKey()))
					return true;

			return false;
		}

		/**
		 * Perform the default {@link Map#containsValue(Object)} to the given {@code bean}.
		 *
		 * @param bean     the bean to perform the method to.
		 * @param instance the instance the bean is having.
		 * @param value    the value to be checked
		 * @param <K>      the type of the keys in the given {@code bean}.
		 * @param <V>      the type of the values in the given {@code bean}.
		 * @return true, if the given {@code bean} has the given {@code value}.
		 * @throws NullPointerException if the given {@code bean} or {@code instance} is null.
		 */
		public static <K, V> boolean containsValue(FullBean<K, V> bean, Object instance, Object value) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");

			for (Map.Entry<K, V> entry : bean.entrySet())
				if (Objects.equals(value, entry.getValue()))
					return true;

			return false;
		}

		/**
		 * Perform the default {@link Map#entrySet()} to the given {@code bean}.
		 *
		 * @param bean     the bean to perform the method to.
		 * @param instance the instance the bean is having.
		 * @param <K>      the type of the keys in the given {@code bean}.
		 * @param <V>      the type of the values in the given {@code bean}.
		 * @return an entrySet for the given {@code bean}.
		 * @throws NullPointerException if the given {@code bean} or {@code instance} is null.
		 */
		public static <K, V> FullBeanEntrySet<K, V> entrySet(FullBean<K, V> bean, Object instance) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");
			return new FullBeanEntrySet(bean, instance);
		}

		/**
		 * Perform the default {@link Map#get(Object)} to the given {@code bean}.
		 *
		 * @param bean     the bean to perform the method to.
		 * @param instance the instance the bean is having.
		 * @param key      the key of the returned value.
		 * @param <K>      the type of the keys in the given {@code bean}.
		 * @param <V>      the type of the values in the given {@code bean}.
		 * @return the value associated to the given {@code key} in the given {@code bean}.
		 * @throws NullPointerException if the given {@code bean} or {@code instance} is null.
		 */
		public static <K, V> V get(FullBean<K, V> bean, Object instance, Object key) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");

			for (Map.Entry<K, V> entry : bean.entrySet())
				if (Objects.equals(key, entry.getKey()))
					return entry.getValue();

			return null;
		}

		/**
		 * Perform the default {@link Map#isEmpty()} to the given {@code bean}.
		 *
		 * @param bean     the bean to perform the method to.
		 * @param instance the instance the bean is having.
		 * @param <K>      the type of the keys in the given {@code bean}.
		 * @param <V>      the type of the values in the given {@code bean}.
		 * @return true, if the given {@code bean} is empty.
		 * @throws NullPointerException if the given {@code bean} or {@code instance} is null.
		 */
		public static <K, V> boolean isEmpty(FullBean<K, V> bean, Object instance) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");
			return bean.entrySet().isEmpty();
		}

		/**
		 * Perform the default {@link Map#keySet()} to the given {@code bean}.
		 *
		 * @param bean     the bean to perform the method to.
		 * @param instance the instance the bean is having.
		 * @param <K>      the type of the keys in the given {@code bean}.
		 * @param <V>      the type of the values in the given {@code bean}.
		 * @return a keySet for the given {@code bean}.
		 * @throws NullPointerException if the given {@code bean} or {@code instance} is null.
		 */
		public static <K, V> FullBeanKeySet<K> keySet(FullBean<K, V> bean, Object instance) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");
			return new FullBeanKeySet(bean, instance);
		}

		/**
		 * Perform the default {@link Map#put(Object, Object)} to the given {@code bean}.
		 *
		 * @param bean     the bean to perform the method to.
		 * @param instance the instance the bean is having.
		 * @param key      the key to associate the given {@code value} in the given {@code bean}.
		 * @param value    the value to be associated to the given {@code key} in the given {@code bean}.
		 * @param <K>      the type of the keys in the given {@code bean}.
		 * @param <V>      the type of the values in the given {@code bean}.
		 * @return the previous value associated to the given {@code key} in the given {@code bean}.
		 * @throws NullPointerException if the given {@code bean} or {@code instance} is null.
		 */
		public static <K, V> V put(FullBean<K, V> bean, Object instance, K key, V value) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");

			Set<Map.Entry<K, V>> entrySet = bean.entrySet();

			//looking in the existing entries
			for (Map.Entry<K, V> entry : entrySet)
				if (Objects.equals(key, entry.getKey()))
					return entry.setValue(value);

			//looking in the fields with removed entries, Or add a simple entry
			Bean.PropertyEntry<K, V> entry = Bean.PropertyEntry.from(instance, key, value);
			entrySet.add(entry != null ? entry : new AbstractMap.SimpleEntry(key, value));
			return null;
		}

		/**
		 * Perform the default {@link Map#putAll(Map)} to the given {@code bean}.
		 *
		 * @param bean     the bean to perform the method to.
		 * @param instance the instance the bean is having.
		 * @param map      the mappings to be copied to the given {@code bean}.
		 * @param <K>      the type of the keys in the given {@code bean}.
		 * @param <V>      the type of the values in the given {@code bean}.
		 * @throws NullPointerException if the given {@code bean} or {@code instance} is null.
		 */
		public static <K, V> void putAll(FullBean<K, V> bean, Object instance, Map<? extends K, ? extends V> map) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(map, "map");

			Set keys = new HashSet(map.keySet());
			Set<Map.Entry<K, V>> entrySet = bean.entrySet();

			//looking in the existing entries
			for (Map.Entry entry : entrySet) {
				Object entryKey = entry.getKey();

				if (keys.remove(entryKey))
					entry.setValue(map.get(entryKey));
			}

			//looking in the fields with removed entries
			for (Field field : Bean.Reflection.getPropertyFields(instance))
				if (field.isAnnotationPresent(Bean.Property.class))
					for (K key : new Bean.PropertyKeySet<K>(field))
						if (keys.remove(key))
							entrySet.add(Bean.PropertyEntry.from(instance, field, key, map.get(key)));

			//adding simple entries
			for (Object key : keys)
				entrySet.add(new AbstractMap.SimpleEntry(key, map.get(key)));
		}

		/**
		 * Perform the default {@link Map#remove(Object)} to the given {@code bean}.
		 *
		 * @param bean     the bean to perform the method to.
		 * @param instance the instance the bean is having.
		 * @param key      the key to be removed from the given {@code bean}.
		 * @param <K>      the type of the keys in the given {@code bean}.
		 * @param <V>      the type of the values in the given {@code bean}.
		 * @return the previous value associated to the given {@code key} in the given {@code bean}.
		 * @throws NullPointerException if the given {@code bean} or {@code instance} is null.
		 */
		public static <K, V> V remove(FullBean<K, V> bean, Object instance, Object key) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");

			Iterator<Map.Entry<K, V>> iterator = bean.entrySet().iterator();

			while (iterator.hasNext()) {
				Map.Entry<K, V> entry = iterator.next();

				if (Objects.equals(key, entry.getKey())) {
					V old = entry.getValue();
					iterator.remove();
					return old;
				}
			}

			return null;
		}

		/**
		 * Perform the default {@link Map#size()} to the given {@code bean}.
		 *
		 * @param bean     the bean to perform the method to.
		 * @param instance the instance the bean is having.
		 * @param <K>      the type of the keys in the given {@code bean}.
		 * @param <V>      the type of the values in the given {@code bean}.
		 * @return the size of the given {@code bean}.
		 * @throws NullPointerException if the given {@code bean} or {@code instance} is null.
		 */
		public static <K, V> int size(FullBean<K, V> bean, Object instance) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");
			return bean.entrySet().size();
		}

		/**
		 * Perform the default {@link Map#values()} to the given {@code bean}.
		 *
		 * @param bean     the bean to perform the method to.
		 * @param instance the instance the bean is having.
		 * @param <K>      the type of the keys in the given {@code bean}.
		 * @param <V>      the type of the values in the given {@code bean}.
		 * @return a values-collection for the given {@code bean}.
		 * @throws NullPointerException if the given {@code bean} or {@code instance} is null.
		 */
		public static <K, V> FullBeanValues<V> values(FullBean<K, V> bean, Object instance) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");
			return new FullBeanValues(bean, instance);
		}
	}
}
