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
		public Iterator<K> iterator() {
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
		public Iterator<V> iterator() {
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

	@SuppressWarnings("JavaDoc")
	final class Methods {
		public static void clear(FullBean bean, Object instance) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");
			bean.entrySet().clear();
		}

		public static boolean containsKey(FullBean bean, Object instance, Object key) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");

			for (Map.Entry entry : (Set<Map.Entry>) bean.entrySet())
				if (Objects.equals(key, entry.getKey()))
					return true;

			return false;
		}

		public static boolean containsValue(FullBean bean, Object instance, Object value) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");

			for (Map.Entry entry : (Set<Map.Entry>) bean.entrySet())
				if (Objects.equals(value, entry.getValue()))
					return true;

			return false;
		}

		public static <K, V> FullBeanEntrySet<K, V> entrySet(FullBean<K, V> bean, Object instance) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");
			return new FullBeanEntrySet(bean, instance);
		}

		public static <V> V get(FullBean<?, V> bean, Object instance, Object key) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");

			for (Map.Entry<?, V> entry : bean.entrySet())
				if (Objects.equals(key, entry.getKey()))
					return entry.getValue();

			return null;
		}

		public static boolean isEmpty(FullBean bean, Object instance) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");
			return bean.entrySet().isEmpty();
		}

		public static <K> FullBeanKeySet<K> keySet(FullBean<K, ?> bean, Object instance) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");
			return new FullBeanKeySet(bean, instance);
		}

		public static <V> V put(FullBean<?, V> bean, Object instance, Object key, V value) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");

			Set<Map.Entry<?, V>> entrySet = (Set) bean.entrySet();

			//looking in the existing entries
			for (Map.Entry<?, V> entry : entrySet) {
				Object entryKey = entry.getKey();

				if (Objects.equals(entryKey, key))
					return entry.setValue(value);
			}

			//looking in the fields with removed entries, Or add a simple entry
			Bean.PropertyEntry<Object, V> entry = Bean.PropertyEntry.from(instance, key, value);
			entrySet.add(entry != null ? entry : new AbstractMap.SimpleEntry(key, value));
			return null;
		}

		public static void putAll(FullBean bean, Object instance, Map map) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(map, "map");

			Set keys = new HashSet(map.keySet());
			Set<Map.Entry> entrySet = bean.entrySet();

			//looking in the existing entries
			for (Map.Entry entry : entrySet) {
				Object entryKey = entry.getKey();

				if (keys.remove(entryKey))
					entry.setValue(map.get(entryKey));
			}

			//looking in the fields with removed entries
			for (Field field : Bean.Reflection.getPropertyFields(instance))
				if (field.isAnnotationPresent(Bean.Property.class))
					for (Object key : new Bean.PropertyKeySet(field))
						if (keys.remove(key))
							entrySet.add(Bean.PropertyEntry.from(instance, field, key, map.get(key)));

			//adding simple entries
			for (Object key : keys)
				entrySet.add(new AbstractMap.SimpleEntry(key, map.get(key)));
		}

		public static <V> V remove(FullBean<?, V> bean, Object instance, Object key) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");

			Iterator<Map.Entry<?, V>> iterator = (Iterator) bean.entrySet().iterator();

			while (iterator.hasNext()) {
				Map.Entry<?, V> entry = iterator.next();

				if (Objects.equals(key, entry.getKey())) {
					V old = entry.getValue();
					iterator.remove();
					return old;
				}
			}

			return null;
		}

		public static int size(FullBean bean, Object instance) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");
			return bean.entrySet().size();
		}

		public static <V> FullBeanValues<V> values(FullBean<?, V> bean, Object instance) {
			Objects.requireNonNull(bean, "bean");
			Objects.requireNonNull(instance, "instance");
			return new FullBeanValues(bean, instance);
		}
	}
}
