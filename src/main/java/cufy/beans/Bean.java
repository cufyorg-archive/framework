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
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * An interface that changes the act of the fields of the class implementing it. The classes that implement this
 * interface change to be used as a map. All the fields of that class will be like {@link Map.Entry entries} on maps.
 * <br>
 * To enhance the security of the beans. Only the field that annotated with {@link Property} will be used as fields.
 * <br>
 * Since this is an interface. It can't have fields. So it can't store its entrySet, so it will be so heavy to create a
 * new entrySet each time it requires it. So all the methods have been designed not to use the entrySet. Witch is way
 * back in performance compared to do it with a final entrySet. Also since it can't have a final entrySet, so it can't
 * store keys that it haven't a field for it.
 *
 * @param <K> the type of keys maintained by this map.
 * @param <V> the type of mapped values.
 * @author LSafer
 * @version 0.1.5
 * @since 0.0.a ~2019.06.11
 */
public interface Bean<K, V> extends Map<K, V> {
	/**
	 * Get a {@link FieldEntry} for a field that have the given key in the given instance. This method don't trust the
	 * given {@code key}. The given {@code key} will not be used. The key constructed to match the given {@code key}
	 * will be used instead.
	 *
	 * @param instance the instance that the returned entry is for a field in it.
	 * @param key      the key that the returned entry is having.
	 * @param <K>      the type of the key of the returned entry.
	 * @param <V>      the type of the value of the returned entry.
	 * @return a {@link FieldEntry} for a field that have the given key in the given instance. Or null if there is no
	 * 		such field having the given {@code key} in the given {@code instance}.
	 * @throws NullPointerException if the given {@code instance} is null.
	 */
	static <K, V> FieldEntry<K, V> entry(Object instance, K key) {
		Objects.requireNonNull(instance, "instance");
		Class klass = instance.getClass();

		for (Field field : Reflection.getAllFields(klass))
			if (field.isAnnotationPresent(Property.class))
				for (K fieldKey : (K[]) Bean.Util.getKeys(field))
					if (Objects.equals(key, fieldKey))
						return new FieldEntry(instance, field, fieldKey);

		//noinspection ReturnOfNull
		return null;
	}

	/**
	 * Get a {@link FieldEntry} for a field that have the given key in the given instance. Then set the value of the
	 * given {@code field} to the given {@code value}. This method don't trust the given {@code key}. The given {@code
	 * key} will not be used. The key constructed to match the given {@code key} will be used instead.
	 *
	 * @param instance the instance that the returned entry is for a field in it.
	 * @param key      the key that the returned entry is having.
	 * @param value    the value to be set to the given {@code field}.
	 * @param <K>      the type of the key of the returned entry.
	 * @param <V>      the type of the value of the returned entry.
	 * @return a {@link FieldEntry} for a field that have the given key in the given instance. Or null if there is no
	 * 		such field having the given {@code key} in the given {@code instance}.
	 * @throws NullPointerException if the given {@code instance} is null.
	 */
	static <K, V> FieldEntry<K, V> entry(Object instance, K key, V value) {
		Objects.requireNonNull(instance, "instance");
		Class klass = instance.getClass();

		for (Field field : Reflection.getAllFields(klass))
			if (field.isAnnotationPresent(Property.class))
				for (K fieldKey : (K[]) Bean.Util.getKeys(field))
					if (Objects.equals(key, fieldKey))
						return new FieldEntry(instance, field, fieldKey, value);

		//noinspection ReturnOfNull
		return null;
	}

	/**
	 * Get a {@link FieldEntry} with the given {@code key} for the given {@code field}. This method don't trust the
	 * given {@code key}. This method will check if the given {@code field} actually have the given {@code key} or not.
	 * The given {@code key} will not be used. The key constructed to match the given {@code key} will be used instead.
	 *
	 * @param instance the instance that the returned entry is for a field in it.
	 * @param field    the field where the returned entry is reading/writing its value.
	 * @param key      the key that the returned entry is having.
	 * @param <K>      the type of the key of the returned entry.
	 * @param <V>      the type of the value of the returned entry.
	 * @return a {@link FieldEntry} with the given {@code key} for the given {@code field}.
	 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
	 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}. Or if the
	 *                                  given {@code field} is not in the given {@code instance}. Or if the given {@code
	 *                                  field} don't actually have the given {@code key} as a key of it.
	 */
	static <K, V> FieldEntry<K, V> entry(Object instance, Field field, K key) {
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(field, "field");

		Class<?> dKlass = field.getDeclaringClass();
		Class<?> iKlass = instance.getClass();
		if (!dKlass.isAssignableFrom(iKlass))
			throw new IllegalArgumentException(field + " isn't in the instance: " + instance);
		if (!field.isAnnotationPresent(Property.class))
			throw new IllegalArgumentException(field + " is not annotated with " + Property.class);

		for (K fieldKey : (K[]) Bean.Util.getKeys(field))
			if (Objects.equals(key, fieldKey))
				return new FieldEntry(instance, field, fieldKey);

		throw new IllegalArgumentException(field + " don't have the key: " + key);
	}

	/**
	 * Get a {@link FieldEntry} with the given {@code key} for the given {@code field}. Then set the value of the given
	 * {@code field} to the given {@code value}. This method don't trust the given {@code key}. This method will check
	 * if the given {@code field} actually have the given {@code key} or not. The given {@code key} will not be used.
	 * The key constructed to match the given {@code key} will be used instead.
	 *
	 * @param instance the instance that the returned entry is for a field in it.
	 * @param field    the field where the returned entry is reading/writing its value.
	 * @param key      the key that the returned entry is having.
	 * @param value    the value to be set to the given {@code field}.
	 * @param <K>      the type of the key of the returned entry.
	 * @param <V>      the type of the value of the returned entry.
	 * @return a {@link FieldEntry} with the given {@code key} for the given {@code field}.
	 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
	 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}. Or if the
	 *                                  given {@code field} is not in the given {@code instance}. Or if the given {@code
	 *                                  field} don't actually have the given {@code key} as a key of it.
	 */
	static <K, V> FieldEntry<K, V> entry(Object instance, Field field, K key, V value) {
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(field, "field");

		Class dKlass = field.getDeclaringClass();
		Class iKlass = instance.getClass();
		if (!dKlass.isAssignableFrom(iKlass))
			throw new IllegalArgumentException(field + " isn't in the instance: " + instance);
		if (!field.isAnnotationPresent(Property.class))
			throw new IllegalArgumentException(field + " is not annotated with " + Property.class);

		for (K fieldKey : (K[]) Bean.Util.getKeys(field))
			if (Objects.equals(key, fieldKey))
				return new FieldEntry(instance, field, fieldKey, value);

		throw new IllegalArgumentException(field + " don't have the key: " + key);
	}

	/**
	 * Get a set of {@link FieldEntry}s for the given {@code instance}.
	 *
	 * @param instance the instance that the returned set represents a set of its properties.
	 * @param <K>      the type of the keys of the entries in the returned set.
	 * @param <V>      the type of the values of the entries in the returned set.
	 * @return a set of {@link FieldEntry}s for the given {@code instance}.
	 * @throws NullPointerException if the given {@code instance} is null.
	 */
	static <K, V> Set<Map.Entry<K, V>> entrySet(Object instance) {
		Objects.requireNonNull(instance, "instance");
		Set<Map.Entry<K, V>> entrySet = new HashSet();
		Class klass = instance.getClass();

		for (Field field : Reflection.getAllFields(klass))
			if (field.isAnnotationPresent(Property.class))
				for (K key : (K[]) Bean.Util.getKeys(field))
					entrySet.add(new FieldEntry(instance, field, key));

		return entrySet;
	}

	/**
	 * Get a bean for the given {@code instance}. The bean will remotely access the given instance. All the rules of
	 * {@link Bean} will be applied. Only the fields annotated with {@link Property} will be accessed. If a key got put
	 * while the given {@code instance} don't have a property for it, then it will be stored locally on the returned
	 * bean.
	 *
	 * @param instance the instance that the returned bean is reading/writing to its properties.
	 * @param <K>      the type of the keys in the returned bean.
	 * @param <V>      the type of the values in the returned bean.
	 * @return a bean for the given {@code instance}, Or the given {@code instance} itself if it is instance of {@link
	 *        Bean}.
	 * @throws NullPointerException if the given {@code instance} is null.
	 */
	static <K, V> Bean<K, V> forInstance(Object instance) {
		Objects.requireNonNull(instance, "instance");
		return instance instanceof Bean ? (Bean<K, V>) instance : new AbstractBean<K, V>() {
			@Override
			public Object getInstance() {
				return instance;
			}
		};
	}

	@Override
	default int size() {
		Object instance = this.getInstance();
		Class klass = instance.getClass();

		int size = 0;
		for (Field field : Reflection.getAllFields(klass))
			if (field.isAnnotationPresent(Property.class)) {
				Property property = field.getAnnotation(Property.class);

				size += property.key().length;
			}

		return size;
	}

	@Override
	default boolean isEmpty() {
		Object instance = this.getInstance();
		Class klass = instance.getClass();

		for (Field field : Reflection.getAllFields(klass))
			if (field.isAnnotationPresent(Property.class))
				return false;

		return true;
	}

	@Override
	default boolean containsKey(Object key) {
		Object instance = this.getInstance();
		Class klass = instance.getClass();

		for (Field field : Reflection.getAllFields(klass))
			if (field.isAnnotationPresent(Property.class))
				for (K fieldKey : (K[]) Bean.Util.getKeys(field))
					if (Objects.equals(key, fieldKey))
						return true;

		return false;
	}

	@Override
	default boolean containsValue(Object value) {
		Object instance = this.getInstance();
		Class klass = instance.getClass();

		for (Field field : Reflection.getAllFields(klass))
			if (field.isAnnotationPresent(Property.class)) {
				Object fieldValue = Util.getValue(instance, field);

				if (Objects.equals(value, fieldValue))
					return true;
			}

		return false;
	}

	@Override
	default V get(Object key) {
		Object instance = this.getInstance();
		Class klass = instance.getClass();

		for (Field field : Reflection.getAllFields(klass))
			if (field.isAnnotationPresent(Property.class))
				for (K fieldKey : (K[]) Bean.Util.getKeys(field))
					if (Objects.equals(key, fieldKey))
						return Bean.Util.getValue(instance, field);

		//noinspection ReturnOfNull
		return null;
	}

	@Override
	default V put(K key, V value) {
		Object instance = this.getInstance();
		Class klass = instance.getClass();

		for (Field field : Reflection.getAllFields(klass))
			if (field.isAnnotationPresent(Property.class))
				for (K fieldKey : (K[]) Bean.Util.getKeys(field))
					if (Objects.equals(key, fieldKey))
						return Bean.Util.setValue(instance, field, value);

		throw new UnsupportedOperationException("Can't store the key: " + key);
	}

	@Override
	default V remove(Object key) {
		throw new UnsupportedOperationException("remove");
	}

	@Override
	default void putAll(Map<? extends K, ? extends V> map) {
		Objects.requireNonNull(map, "map");
		Object instance = this.getInstance();
		Class klass = instance.getClass();

		int size = map.size();
		int index = 0;
		for (Field field : Reflection.getAllFields(klass))
			if (field.isAnnotationPresent(Property.class))
				for (K key : (K[]) Bean.Util.getKeys(field))
					if (map.containsKey(key)) {
						index++;
						V value = map.get(key);
						Bean.Util.setValue(instance, field, value);
					}

		if (index != size)
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
					private final Iterator<Map.Entry<K, V>> iterator = Bean.this.entrySet().iterator();

					@Override
					public boolean hasNext() {
						return this.iterator.hasNext();
					}

					@Override
					public K next() {
						Map.Entry<K, V> next = this.iterator.next();
						return next.getKey();
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
					private final Iterator<Map.Entry<K, V>> iterator = Bean.this.entrySet().iterator();

					@Override
					public boolean hasNext() {
						return this.iterator.hasNext();
					}

					@Override
					public V next() {
						Map.Entry<K, V> next = this.iterator.next();
						return next.getValue();
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

			public boolean contains(Object o) {
				return Bean.this.containsValue(o);
			}

			public void clear() {
				Bean.this.clear();
			}
		};
	}

	@Override
	default Set<Map.Entry<K, V>> entrySet() {
		Object instance = this.getInstance();
		return Bean.entrySet(instance);
	}

	/**
	 * Get the instance this bean is representing.
	 *
	 * @return the instance this bean is representing.
	 */
	default Object getInstance() {
		//noinspection ReturnOfThis
		return this;
	}

	/**
	 * Declare that the annotated field is a bean property. Any field annotated with {@link Property} can be accessed
	 * even if it has {@code private} modifier, if its class extends or trust the class{@link Bean}.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	@interface Property {
		/**
		 * Get a reference to a converter for the {@code annotated field} to use when it is about to set a value that is
		 * not an instance of the specified type.
		 *
		 * @return a reference to the {@code annotated field}'s converter.
		 */
		Where converter() default @Where(BaseConverter.class);

		/**
		 * An array of keys to be used as the keys for the {@code annotated field}. If the returned array is empty, then
		 * the {@code annotated field} have one key, and that key is its name. Otherwise, the returned array is the keys
		 * of the {@code annotated field}.
		 *
		 * @return the keys of the {@code annotated field}.
		 */
		Recipe[] key() default {};

		/**
		 * An array containing the type of the {@code annotated field}. If the returned array is empty, then the {@code
		 * annotated field} have the type found in its signature. Otherwise, the first element in the returned array is
		 * the type of the {@code annotated field}.
		 * <br>
		 *
		 * @return the type of the {@code annotated field}
		 * @throws IllegalMetaException if the array's length is more than 1
		 */
		Type[] type() default {};
	}

	/**
	 * An entry managing {@link Property} fields. This entry can't be constructed directly. It could only be constructed
	 * using {@link Bean#entry}.
	 *
	 * @param <K> the type of the key in the entry.
	 * @param <V> the type of the value in the entry.
	 */
	final class FieldEntry<K, V> implements Map.Entry<K, V> {
		/**
		 * The converter to be used when this entry is about to set a value that is not an instance of the specified
		 * type.
		 */
		private final Converter converter;
		/**
		 * The field where this entry should edit in the {@link #instance}.
		 */
		private final Field field;
		/**
		 * The instance that this entry is editing.
		 */
		private final Object instance;
		/**
		 * The key of this entry.
		 */
		private final K key;
		/**
		 * The metadata of this entry.
		 */
		private final Property meta;
		/**
		 * The type of the value of this entry.
		 */
		private final Clazz<V> type;

		/**
		 * Construct a new field entry that edits the given instance at the given field.
		 *
		 * @param instance the instance for the constructed entry to edit.
		 * @param field    the field where the constructed entry should edit in the given {@code instance}.
		 * @param key      the key of the constructed entry.
		 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
		 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}.
		 */
		private FieldEntry(Object instance, Field field, K key) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(field, "field");
			if (!field.isAnnotationPresent(Property.class))
				throw new IllegalArgumentException(field + " haven't annotated with " + Property.class);
			this.field = field;
			this.instance = instance;
			this.key = key;
			this.type = Bean.Util.getType(field);
			this.meta = field.getAnnotation(Property.class);

			Where where = this.meta.converter();
			this.converter = Where.Util.getValue(where);
		}

		/**
		 * Construct a new field entry that edits the given instance at the given field. Then set the value of the given
		 * {@code field} to the given {@code value}.
		 *
		 * @param instance the instance for the constructed entry to edit.
		 * @param field    the field where the constructed entry should edit in the given {@code instance}.
		 * @param key      the key of the constructed entry.
		 * @param value    the value to be set to the given {@code field} after constructing the entry.
		 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
		 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}.
		 */
		private FieldEntry(Object instance, Field field, K key, V value) {
			this(instance, field, key);
			this.setValue(value);
		}

		@Override
		public K getKey() {
			return this.key;
		}

		@Override
		public V getValue() {
			try {
				this.field.setAccessible(true);
				return (V) this.field.get(this.instance);
			} catch (IllegalAccessException e) {
				IllegalAccessError error = new IllegalAccessError();
				error.initCause(e);
				throw error;
			}
		}

		@Override
		public V setValue(V value) {
			return Bean.Util.setValue(this.field, this.instance, value, this.converter, this.type);
		}

		@Override
		public int hashCode() {
			V value = this.getValue();
			//noinspection ObjectInstantiationInEqualsHashCode
			return Objects.hash(this.key, value);
		}

		@Override
		public boolean equals(Object obj) {
			//noinspection IfStatementWithTooManyBranches
			if (obj == this)
				return true;
			else if (obj instanceof FieldEntry)
				//noinspection AccessingNonPublicFieldOfAnotherObject
				return Objects.equals(((FieldEntry) obj).key, this.key) &&
					   ((FieldEntry) obj).field.equals(this.field);
			else if (obj instanceof Map.Entry)
				//noinspection NestedMethodCall
				return Objects.equals(((Map.Entry) obj).getKey(), this.key) &&
					   Objects.equals(((Map.Entry) obj).getValue(), this.getValue());
			else
				return false;
		}

		@Override
		public String toString() {
			return this.key + "=" + this.getValue();
		}

		/**
		 * Get the converter to be used when this entry is about to set a value that is not an instance of the specified
		 * type.
		 *
		 * @return the converter used by this entry.
		 */
		public Converter getConverter() {
			return this.converter;
		}

		/**
		 * Get the field where this entry should edit its {@code instance}.
		 *
		 * @return the field of this entry.
		 */
		public Field getField() {
			return this.field;
		}

		/**
		 * Get the {@link Property} annotation from the field of this entry.
		 *
		 * @return the metadata of this entry.
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

	/**
	 * Utilities to deal with {@link Bean}s.
	 */
	final class Util {
		/**
		 * This is an util class. And must not be instanced as an object.
		 *
		 * @throws AssertionError when called.
		 */
		private Util() {
			throw new AssertionError("No instance for you!");
		}

		/**
		 * Get the keys of the given {@code field}. This method returns an array of the keys of the given {@code field}.
		 * The array will not return an empty or null array.
		 *
		 * @param field the field that the returned keys represents the keys to access it using a bean that have it.
		 * @return the keys of the given field with a length more than zero.
		 * @throws NullPointerException     if the given {@code field} is null.
		 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}.
		 */
		public static Object[] getKeys(Field field) {
			Objects.requireNonNull(field, "field");
			if (!field.isAnnotationPresent(Property.class))
				throw new IllegalArgumentException(field + " is not annotated with " + Property.class);

			Property property = field.getAnnotation(Property.class);
			Recipe[] recipes = property.key();

			if (recipes.length == 0)
				return new Object[]{field.getName()};
			else {
				Object[] keys = new Object[recipes.length];

				for (int i = 0; i < recipes.length; i++)
					keys[i] = Recipe.Util.get(recipes[i]);

				return keys;
			}
		}

		/**
		 * Get the type of the given {@code field}. The type means the {@link Clazz} specified for the values to be
		 * store at the given {@code field}.
		 *
		 * @param field the field that the returned type is required for a value to be stored to it using a bean.
		 * @param <V>   the type of the returned clazz.
		 * @return the type of the given {@code field}.
		 * @throws NullPointerException     if the given {@code field} is null.
		 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}.
		 */
		public static <V> Clazz<V> getType(Field field) {
			Objects.requireNonNull(field, "field");
			if (!field.isAnnotationPresent(Property.class))
				throw new IllegalArgumentException(field + " is not annotated with " + Property.class);

			Property property = field.getAnnotation(Property.class);
			Type[] type = property.type();

			if (type.length == 0) {
				Class fieldType = field.getType();
				return (Clazz<V>) Clazz.of(fieldType);
			} else if (type.length == 1)
				return (Clazz<V>) Type.Util.get(type[0]);
			else
				throw new IllegalMetaException("Bean.Property.type().length > 1");
		}

		/**
		 * Get the value stored at the given {@code field} in the given {@code instance}.
		 *
		 * @param instance the instance that the returned value is stored at in the given {@code field}.
		 * @param field    the field that the returned value is stored at in the given {@code instance}.
		 * @param <V>      the type of the returned value.
		 * @return the value stored at the given {@code field} in the given {@code instance}.
		 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
		 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}. Or if the
		 *                                  given {@code field} is not in the given {@code instance}.
		 */
		public static <V> V getValue(Object instance, Field field) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(field, "field");

			Class dKlass = field.getDeclaringClass();
			Class iKlass = instance.getClass();
			if (!dKlass.isAssignableFrom(iKlass))
				throw new IllegalArgumentException(field + " isn't in the instance: " + instance);
			if (!field.isAnnotationPresent(Property.class))
				throw new IllegalArgumentException(field + " is not annotated with " + Property.class);

			try {
				field.setAccessible(true);
				return (V) field.get(instance);
			} catch (IllegalAccessException e) {
				IllegalAccessError error = new IllegalAccessError();
				error.initCause(e);
				throw error;
			}
		}

		/**
		 * Set the value stored at the given {@code field} on the given {@code instance} to the given {@code value}.
		 *
		 * @param instance the instance for the given {@code value} to be set in at the given {@code field}.
		 * @param value    the value to be set in the given {@code instance} at the given {@code field}.
		 * @param field    the filed for the given {@code value} to be set at in the given {@code instance}.
		 * @param <V>      the type of the given {@code value}.
		 * @return the previous value stored in the given {@code instance} at the given {@code field}.
		 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
		 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}. Or if the
		 *                                  given {@code field} is not in the given {@code instance}.
		 */
		public static <V> V setValue(Object instance, Field field, V value) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(field, "field");

			Class dKlass = field.getDeclaringClass();
			Class iKlass = instance.getClass();
			if (!dKlass.isAssignableFrom(iKlass))
				throw new IllegalArgumentException(field + " isn't in the instance: " + instance);
			if (!field.isAnnotationPresent(Property.class))
				throw new IllegalArgumentException(field + " is not annotated with " + Property.class);

			Property property = field.getAnnotation(Property.class);
			Where where = property.converter();
			Converter converter = Where.Util.getValue(where);

			Clazz<V> type = Util.getType(field);
			return Util.setValue(field, instance, value, converter, type);
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
		 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}. Or if the
		 *                                  given {@code value} can't be set to the given {@code field}.
		 * @throws IllegalAccessError       if the given {@code field} object is enforcing Java language access control,
		 *                                  and the underlying field is either inaccessible or final.
		 */
		private static <V> V setValue(Field field, Object instance, V value, Converter converter, Clazz<V> type) {
			//no need for assertions | covered by the private modifier
			try {
				field.setAccessible(true);

				int modifiers = field.getModifiers();
				Clazz<V> valueType = Clazz.from(value);
				V old = (V) field.get(instance);

				if (!Modifier.isFinal(modifiers)) {
					//direct converting then assign it
					Object converted = converter.convert(new ConvertToken(value, value, valueType, type));
					field.set(instance, converted);
				} else //skip unnecessary reassignment by convert remotely
					if (converter.convert(new ConvertToken(value, old, valueType, type)) != old)
						//when the converter can't convert to the provided instance, it will replace it with a new one
						throw new IllegalAccessError("can't assign the value to the final field: " + field);

				return old;
			} catch (IllegalAccessException e) {
				IllegalAccessError error = new IllegalAccessError();
				error.initCause(e);
				throw error;
			}
		}
	}
}
