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

import cufy.convert.BaseConverter;
import cufy.convert.ConvertToken;
import cufy.convert.Converter;
import cufy.lang.Clazz;
import cufy.meta.IllegalMetaException;
import cufy.meta.Recipe;
import cufy.meta.Type;
import cufy.meta.Where;
import cufy.util.Reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.*;

/**
 * An interface that changes the act of the fields of the class implementing it. The classes that implement this interface change to be used as a map.
 * All of the fields of that class will be like {@link Entry entries} on maps.
 * <p>
 * To enhance the security of the beans. Only the field that annotated with {@link Property} will be used as fields.
 * <p>
 * Since this is an interface. It can't have fields. So it can't store it's entrySet, so it will be so heavy to create a new entrySet each time it
 * requires it. So all of the methods have been designed not to use the entrySet. Witch is way back in performance compared to do it with a final
 * entrySet. Also since it can't have a final entrySet, so it can't store keys that it haven't a field for it.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author lsafer
 * @version 0.1.5
 * @since 11-Jun-2019
 */
public interface Bean<K, V> extends Map<K, V> {
	//TODO check for errors in multiple key properties

	/**
	 * Get a set of field-entries for the given instance.
	 *
	 * @param instance to get the entry set for
	 * @param <K>      the type of the keys
	 * @param <V>      the type of the values
	 * @return a field-entry set for the given instance
	 * @throws NullPointerException if the given 'instance' is null
	 * @throws IllegalMetaException if the given 'instance' has duplicated key.
	 */
	static <K, V> Set<Entry<K, V>> entrySet(Object instance) {
		Objects.requireNonNull(instance, "instance");
		Set<Entry<K, V>> entrySet = new HashSet<>();
		Set<K> keys = new HashSet<>();

		for (Field field : Reflection.getAllFields(instance.getClass()))
			if (field.isAnnotationPresent(Property.class))
				for (K key : (K[]) Bean.getKeys(field))
					if (keys.add(key))
						entrySet.add(new FieldEntry<>(instance, field, key));
					else throw new IllegalMetaException("Duplicated Key: " + key);

		return entrySet;
	}

	/**
	 * Get a remote bean for the instance given. The remote bean will remotely access the given instance. All the rules of {@link Bean} will be
	 * applied. Only the fields annotated with {@link Property} will be accessed.
	 * <p>
	 * Note: if you write a key that is not in the instance, It will be stored locally on the returned bean
	 * <p>
	 * Note: If the given instance is a bean. Then the behaviour is essential and some features of the bean given will be lost.
	 *
	 * @param instance the target instance to get a bean of
	 * @param <K>      the type of the keys
	 * @param <V>      the type of the values
	 * @return a remote bean read and write from the given instance
	 * @throws NullPointerException if the given 'instance' is null
	 */
	static <K, V> Bean<K, V> forInstance(Object instance) {
		Objects.requireNonNull(instance, "instance");
		return new AbstractBean<K, V>() {
			@Override
			public Set<Entry<K, V>> entrySet() {
				if (this.entrySet == null) {
					this.entrySet = Bean.entrySet(instance);
				}

				return this.entrySet;
			}

			@Override
			public V put(K key, V value) {
				//looking for existing entry
				for (Entry<K, V> entry : this.entrySet())
					if (Objects.equals(entry.getKey(), key))
						return entry.setValue(value);

				//looking for a field with removed entry
				for (Field field : Reflection.getAllFields(instance.getClass()))
					if (field.isAnnotationPresent(Property.class))
						for (K fieldKey : (K[]) Bean.getKeys(field))
							if (Objects.equals(key, fieldKey)) {
								FieldEntry entry = new FieldEntry(instance, field, key);
								entry.setValue(value);
								this.entrySet().add(entry);
								return null;
							}

				//create a simple entry
				this.entrySet().add(new SimpleEntry<>(key, value));
				return null;
			}
		};
	}

	/**
	 * Get the key of the given field.
	 *
	 * @param field to get the key of
	 * @param <K>   the type of the key
	 * @return the key of the given key
	 * @throws NullPointerException     if the given 'field' is null
	 * @throws IllegalArgumentException if the given 'field' is not annotated with {@link Bean.Property}
	 */
	static <K> K[] getKeys(Field field) {
		Objects.requireNonNull(field, "field");
		if (!field.isAnnotationPresent(Bean.Property.class))
			throw new IllegalArgumentException(field + " is not annotated with " + Bean.Property.class);

		Recipe[] recipes = field.getAnnotation(Property.class).key();

		if (recipes.length == 0) {
			return (K[]) new Object[]{field.getName()};
		} else {
			K[] keys = (K[]) new Object[recipes.length];

			for (int i = 0; i < recipes.length; i++)
				keys[i] = Recipe.Util.get(recipes[i]);

			return keys;
		}
	}

	/**
	 * Get the clazz of the given field.
	 *
	 * @param field to get the clazz of
	 * @param <V>   the type of the clazz
	 * @return the clazz of the field given
	 * @throws NullPointerException     if the given 'field' is null
	 * @throws IllegalArgumentException if the given field is not annotated with {@link Property}
	 */
	static <V> Clazz<V> getType(Field field) {
		Objects.requireNonNull(field, "field");
		if (!field.isAnnotationPresent(Property.class))
			throw new IllegalArgumentException(field + " is not annotated with " + Property.class);

		Type[] type = field.getAnnotation(Property.class).type();

		if (type.length == 0) {
			return (Clazz<V>) Clazz.of(field.getType());
		} else if (type.length == 1) {
			return (Clazz<V>) Type.Util.get(type[0]);
		} else {
			throw new IllegalMetaException("Bean.Property.type.length > 1");
		}
	}

	/**
	 * Get the value currently stored at the given field on the given instance.
	 *
	 * @param instance to get the value from
	 * @param field    that holds that value
	 * @param <V>      the type of the value
	 * @return the value stored at the given field on the given instance
	 * @throws NullPointerException     if the given 'field' or 'instance' is null
	 * @throws IllegalArgumentException if the given 'field' is not annotated with {@link Bean.Property}
	 */
	static <V> V getValue(Field field, Object instance) {
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(field, "field");
		if (!field.isAnnotationPresent(Bean.Property.class))
			throw new IllegalArgumentException(field + " is not annotated with " + Bean.Property.class);

		try {
			field.setAccessible(true);
			return (V) field.get(instance);
		} catch (IllegalAccessException e) {
			throw (IllegalAccessError) new IllegalAccessError().initCause(e);
		}
	}

	/**
	 * Set the value of the given field on the given instance to the given value.
	 *
	 * @param instance to set the value to
	 * @param value    to be set
	 * @param field    that holds the value
	 * @param <V>      the type of the value
	 * @return the previous value stored at that field in that instance
	 * @throws NullPointerException     if the given 'field' or 'instance' is null
	 * @throws IllegalArgumentException if the given 'field' is not annotated with {@link Bean.Property}
	 */
	static <V> V setValue(Field field, Object instance, V value) {
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(field, "field");
		if (!field.isAnnotationPresent(Bean.Property.class))
			throw new IllegalArgumentException(field + " is not annotated with " + Bean.Property.class);

		Converter converter = Where.Util.getValue(field.getAnnotation(Property.class).converter());
		Clazz<V> type = Bean.getType(field);

		return Bean.FieldEntry.setValue(field, instance, value, converter, type);
	}

	@Override
	default int size() {
		int size = 0;

		for (Field field : Reflection.getAllFields(this.getClass()))
			if (field.isAnnotationPresent(Property.class))
				size += Bean.getKeys(field).length;

		return size;
	}

	@Override
	default boolean isEmpty() {
		for (Field field : Reflection.getAllFields(this.getClass()))
			if (field.isAnnotationPresent(Property.class))
				return false;

		return true;
	}

	@Override
	default boolean containsKey(Object key) {
		for (Field field : Reflection.getAllFields(this.getClass()))
			if (field.isAnnotationPresent(Property.class))
				for (K fieldKey : (K[]) Bean.getKeys(field))
					if (Objects.equals(key, fieldKey))
						return true;

		return false;
	}

	@Override
	default boolean containsValue(Object value) {
		for (Field field : Reflection.getAllFields(this.getClass()))
			if (field.isAnnotationPresent(Property.class))
				if (Objects.equals(value, Bean.getValue(field, this)))
					return true;

		return false;
	}

	@Override
	default V get(Object key) {
		for (Field field : Reflection.getAllFields(this.getClass()))
			if (field.isAnnotationPresent(Property.class))
				for (K fieldKey : (K[]) Bean.getKeys(field))
					if (Objects.equals(key, fieldKey))
						return Bean.getValue(field, this);

		return null;
	}

	@Override
	default V put(K key, V value) {
		for (Field field : Reflection.getAllFields(this.getClass()))
			if (field.isAnnotationPresent(Property.class))
				for (K fieldKey : (K[]) Bean.getKeys(field))
					if (Objects.equals(key, fieldKey))
						return Bean.setValue(field, this, value);

		throw new UnsupportedOperationException("Can't store the key: " + key);
	}

	@Override
	default V remove(Object key) {
		throw new UnsupportedOperationException("remove");
	}

	@Override
	default void putAll(Map<? extends K, ? extends V> map) {
		Objects.requireNonNull(map, "map");
		int size = map.size();
		int put = 0;

		for (Field field : Reflection.getAllFields(this.getClass()))
			if (field.isAnnotationPresent(Property.class))
				for (K key : (K[]) Bean.getKeys(field))
					if (map.containsKey(key)) {
						put++;
						Bean.setValue(field, this, map.get(key));
					}

		if (put != size)
			throw new UnsupportedOperationException("Can't store all the keys in: " + map);
	}

	@Override
	default void clear() {
		throw new UnsupportedOperationException("clear");
	}

	@Override
	default Set<K> keySet() {
		return new AbstractSet<K>() {
			@Override
			public Iterator<K> iterator() {
				return new Iterator<K>() {
					/**
					 * The iterator of the entry set.
					 */
					private final Iterator<Entry<K, V>> iterator = Bean.this.entrySet().iterator();

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
				};
			}

			@Override
			public int size() {
				return Bean.this.size();
			}

			@Override
			public boolean isEmpty() {
				return Bean.this.isEmpty();
			}

			@Override
			public boolean contains(Object key) {
				return Bean.this.containsKey(key);
			}

			@Override
			public void clear() {
				Bean.this.clear();
			}
		};
	}

	@Override
	default Collection<V> values() {
		return new AbstractCollection<V>() {
			public Iterator<V> iterator() {
				return new Iterator<V>() {
					/**
					 * The iterator of the entry set.
					 */
					private final Iterator<Entry<K, V>> iterator = Bean.this.entrySet().iterator();

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
				};
			}

			public int size() {
				return Bean.this.size();
			}

			public boolean isEmpty() {
				return Bean.this.isEmpty();
			}

			public boolean contains(Object v) {
				return Bean.this.containsValue(v);
			}

			public void clear() {
				Bean.this.clear();
			}
		};
	}

	@Override
	default Set<Entry<K, V>> entrySet() {
		return Bean.entrySet(this);
	}

	/**
	 * Defines that the annotated field can be entry.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	@interface Property {
		/**
		 * The reference to the converter to use when a value to be set with different type that the allowed.
		 *
		 * @return the reference to the converter of the annotated field
		 */
		Where converter() default @Where(BaseConverter.class);

		/**
		 * The key of the annotated field. This will override the default key (the name of the field).
		 *
		 * @return the key of the annotated field
		 * @throws IllegalArgumentException when a duplicate key detected (don't rely on this exception)
		 */
		Recipe[] key() default {};

		/**
		 * The type of the annotated field. This will override the default type (The type of the field)
		 *
		 * @return the type of the annotated field's property
		 * @throws cufy.meta.IllegalMetaException if the array's length is more than 1
		 */
		Type[] type() default {};
	}

	/**
	 * An entry uses a field instance to hold it's value.
	 *
	 * @param <K> the type of the key
	 * @param <V> the type of the value
	 */
	class FieldEntry<K, V> implements Entry<K, V> {
		/**
		 * The converter of this entry.
		 *
		 * @see Property#converter
		 */
		protected final Converter converter;
		/**
		 * The field that holds the value of this entry.
		 */
		protected final Field field;
		/**
		 * The instance that this entry is editing.
		 */
		protected final Object instance;
		/**
		 * The key of this entry.
		 *
		 * @see Property#key
		 */
		protected final K key;
		/**
		 * The meta-data of this entry.
		 */
		protected final Property meta;
		/**
		 * The type of the value of this entry.
		 *
		 * @see Property#type
		 */
		protected final Clazz<V> type;

		/**
		 * Construct a new field entry.
		 *
		 * @param instance the instance where the targeted field is
		 * @param field    the field to be edited
		 * @param key      the key (directly set)
		 * @throws NullPointerException     if the given 'instance' or 'field' is null
		 * @throws IllegalArgumentException if the given field is not annotated with {@link Property}
		 */
		protected FieldEntry(Object instance, Field field, K key) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(field, "field");
			if (!field.isAnnotationPresent(Property.class))
				throw new IllegalArgumentException(field + " haven't annotated with " + Property.class);

			this.field = field;
			this.instance = instance;
			this.key = key;
			this.type = Bean.getType(field);
			this.meta = field.getAnnotation(Property.class);
			this.converter = Where.Util.getValue(this.meta.converter());
		}

		/**
		 * Set the value of the given field on the given instance to the given value.
		 *
		 * @param instance  to set the value to
		 * @param converter the converter of the field
		 * @param type      the type of the field
		 * @param value     to be set
		 * @param field     that holds the value
		 * @param <V>       the type of the value
		 * @return the previous value stored at that field in that instance
		 * @throws NullPointerException     if the given 'field' or 'instance' is null
		 * @throws IllegalArgumentException if the given 'field' is not annotated with {@link Bean.Property}
		 */
		private static <V> V setValue(Field field, Object instance, V value, Converter converter, Clazz<V> type) {
			//covered by the private modifier
			try {
				//to increase code-quality
				Object toBeSet = value;

				if (!type.isInstance(value))
					//apply formula
					toBeSet = converter.convert(new ConvertToken<>(value, value, Clazz.ofi(value), type));

				field.setAccessible(true);
				V old = (V) field.get(instance);
				field.set(instance, toBeSet);
				return old;
			} catch (IllegalAccessException e) {
				throw (IllegalAccessError) new IllegalAccessError().initCause(e);
			}
		}

		@Override
		public K getKey() {
			return this.key;
		}

		@Override
		public V getValue() {
			try {
				this.field.setAccessible(true);
				return (V) this.field.get(instance);
			} catch (IllegalAccessException e) {
				throw (IllegalAccessError) new IllegalAccessError().initCause(e);
			}
		}

		@Override
		public V setValue(V value) {
			return setValue(this.field, this.instance, value, this.converter, this.type);
		}

		@Override
		public int hashCode() {
			return Objects.hash(this.getKey(), this.getValue());
		}

		@Override
		public boolean equals(Object that) {
			return that == this ||
				   that instanceof Entry &&
				   Objects.equals(((Entry) that).getKey(), this.key);
		}

		/**
		 * Get the field that holds the value of this entry.
		 *
		 * @return the field that holds the value of this entry.
		 */
		public Field getField() {
			return this.field;
		}

		/**
		 * Get the meta-data of this entry.
		 *
		 * @return the meta-data of this entry.
		 */
		public Property getMeta() {
			return this.meta;
		}

		/**
		 * Get the type of the value of this entry.
		 *
		 * @return the type of the value of this entry.
		 */
		public Clazz<V> getType() {
			return this.type;
		}
	}
}
