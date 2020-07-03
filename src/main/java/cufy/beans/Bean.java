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
import cufy.util.Collectionz;

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
	@Override
	default int size() {
		return Bean.Methods.size(this);
	}

	@Override
	default boolean isEmpty() {
		return Bean.Methods.isEmpty(this);
	}

	@Override
	default boolean containsKey(Object key) {
		return Bean.Methods.containsKey(this, key);
	}

	@Override
	default boolean containsValue(Object value) {
		return Bean.Methods.containsValue(this, value);
	}

	@Override
	default V get(Object key) {
		return Bean.Methods.get(this, key);
	}

	@Override
	default V put(K key, V value) {
		return Bean.Methods.put(this, key, value);
	}

	@Override
	default V remove(Object key) {
		return Bean.Methods.remove(this, key);
	}

	@Override
	default void putAll(Map<? extends K, ? extends V> map) {
		Bean.Methods.putAll(this, map);
	}

	@Override
	default void clear() {
		Bean.Methods.clear(this);
	}

	@Override
	default Set<K> keySet() {
		return Bean.Methods.keySet(this);
	}

	@Override
	default Collection<V> values() {
		return Bean.Methods.values(this);
	}

	@Override
	default Set<Entry<K, V>> entrySet() {
		return Bean.Methods.entrySet(this);
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
	 * The simplest entrySet for beans.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 */
	final class BeanEntrySet<K, V> extends AbstractSet<Map.Entry<K, V>> {
		/**
		 * The instance this entrySet is dealing with.
		 */
		private final Object instance;

		/**
		 * Construct a new entrySet for the given bean's {@code instance}.
		 *
		 * @param instance the instance the constructed entrySet is dealing with.
		 * @throws NullPointerException if the given {@code instance} is null.
		 */
		private BeanEntrySet(Object instance) {
			Objects.requireNonNull(instance, "instance");
			this.instance = instance;
		}

		@Override
		public Iterator<Map.Entry<K, V>> iterator() {
			return new BeanEntrySetIterator();
		}

		@Override
		public int size() {
			return Bean.Methods.size(this.instance);
		}

		/**
		 * An iterator for beans' entrySets. This iterator iterating over two levels. It iterates the fields of the
		 * class of the instance that its entrySet is representing. It sub-iterate over the keys of the field at a
		 * first-iterating-position.
		 * <pre>
		 *     for(Field field : instance.fields)
		 *         for(Object key : field.keys)
		 *             return new Entry(key);
		 * </pre>
		 */
		public final class BeanEntrySetIterator implements Iterator<Map.Entry<K, V>> {
			/**
			 * The backing iterator of this iterator for iterating the fields of the class of the instance of the
			 * entrySet of this iterator.
			 */
			private final Iterator<Field> iterator = Reflection.getPropertyFields(BeanEntrySet.this.instance).iterator();
			/**
			 * The position of the next key in the current {@link #property}.
			 */
			private int cursor;
			/**
			 * The current field.
			 */
			private Field field;
			/**
			 * The keys of the current {@link #property}.
			 */
			private Recipe[] keys;
			/**
			 * The property of the current field.
			 */
			private Property property;

			/**
			 * Construct a new iterator for the enclosing entrySet.
			 */
			private BeanEntrySetIterator() {
			}

			@Override
			public boolean hasNext() {
				//fields always have more than 1 key
				return this.iterator.hasNext() || this.keys != null && this.cursor < this.keys.length;
			}

			@Override
			public Map.Entry<K, V> next() {
				//the iterator.next() will do the job breaking the loop if no more fields available
				while (true) {
					//if there is a field previously partially solved
					if (this.keys != null && this.cursor < this.keys.length)
						//continue the to the next key of that field
						return new PropertyEntry(BeanEntrySet.this.instance, this.field, this.keys[this.cursor++]);

					//the next field!
					this.field = this.iterator.next();
					this.property = this.field.getAnnotation(Property.class);
					this.keys = this.property.key();
					this.cursor = 0;

					//don't forget "zero keys = singular key that is the name of the field"
					if (this.keys.length == 0)
						//that is the contract!; don't worry the next call will change the field,
						//since the keys size is zero while the cursor also zero!.
						return new PropertyEntry(BeanEntrySet.this.instance, this.field, this.field.getName());
				}
			}
		}
	}

	/**
	 * A keySet for beans.
	 *
	 * @param <K> the type of the keys.
	 */
	final class BeanKeySet<K> extends AbstractSet<K> {
		/**
		 * The instance this keySet is representing.
		 */
		private final Object instance;

		/**
		 * Construct a new keySet for the given {@code instance}.
		 *
		 * @param instance to be represented by this keySet.
		 * @throws NullPointerException if the given {@code instance} is null.
		 */
		private BeanKeySet(Object instance) {
			Objects.requireNonNull(instance, "instance");
			this.instance = instance;
		}

		@Override
		public Iterator<K> iterator() {
			return new BeanKeySetIterator();
		}

		@Override
		public int size() {
			return Bean.Methods.size(this.instance);
		}

		/**
		 * An iterator for beans' keySets.
		 */
		public final class BeanKeySetIterator implements Iterator<K> {
			/**
			 * The backing iterator of this iterator for iterating the fields of the class of the instance of the keySet
			 * of this iterator.
			 */
			private final Iterator<Field> iterator = Reflection.getPropertyFields(BeanKeySet.this.instance).iterator();
			/**
			 * The position of the next key at the current {@link #keys}.
			 */
			private int cursor;
			/**
			 * The keys of the current field.
			 */
			private Recipe[] keys;

			/**
			 * Construct a new iterator for the enclosing entrySet.
			 */
			private BeanKeySetIterator() {
			}

			@Override
			public boolean hasNext() {
				//fields always have more than 1 key
				return this.iterator.hasNext() || this.keys != null && this.cursor < this.keys.length;
			}

			@Override
			public K next() {
				//the iterator.next() will do the job breaking the loop if no more fields available
				while (true) {
					//if there is a field previously partially solved
					if (this.keys != null && this.cursor < this.keys.length)
						//continue the to the next key of that field
						return Recipe.Util.get(this.keys[this.cursor++]);

					//the next field!
					Field field = this.iterator.next();
					this.keys = field.getAnnotation(Property.class).key();
					this.cursor = 0;

					//don't forget "zero keys = singular key that is the name of the field"
					if (this.keys.length == 0)
						//that is the contract!; don't worry the next call will change the field,
						//since the keys size is zero while the cursor also zero!.
						return (K) field.getName();
				}
			}
		}
	}

	/**
	 * A values-collection for beans.
	 *
	 * @param <V> the type of the values.
	 */
	final class BeanValues<V> extends AbstractCollection<V> {
		/**
		 * The instance this keySet is representing.
		 */
		private final Object instance;

		/**
		 * Construct a new values-collection for the given {@code instance}.
		 *
		 * @param instance to be represented by this values-collection.
		 * @throws NullPointerException if the given {@code instance} is null.
		 */
		private BeanValues(Object instance) {
			Objects.requireNonNull(instance, "instance");
			this.instance = instance;
		}

		@Override
		public Iterator<V> iterator() {
			return new BeanValuesIterator();
		}

		@Override
		public int size() {
			return Bean.Methods.size(this.instance);
		}

		/**
		 * An iterator for beans' values-collections.
		 */
		public final class BeanValuesIterator implements Iterator<V> {
			/**
			 * The backing iterator of this iterator for iterating the fields of the class of the instance of the
			 * entrySet of this iterator.
			 */
			private final Iterator<Field> iterator = Reflection.getPropertyFields(BeanValues.this.instance).iterator();
			/**
			 * The current position in the keys of the current {@link #field}.
			 */
			private int cursor;
			/**
			 * The current field.
			 */
			private Field field;
			/**
			 * How many keys in the current {@link #field}.
			 */
			private int keys;

			/**
			 * Construct a new bean's values-collection iterator.
			 */
			private BeanValuesIterator() {
			}

			@Override
			public boolean hasNext() {
				//fields always have more than 1 key
				return this.iterator.hasNext() || this.field != null && this.cursor < this.keys;
			}

			@Override
			public V next() {
				//the iterator.next() will do the job breaking the loop if no more fields available
				while (true) {
					//if there is a field previously partially solved
					if (this.field != null && this.cursor++ < this.keys)
						//continue the to the next key of that field
						return Reflection.getValue0(BeanValues.this.instance, this.field);

					//the next field!
					this.field = this.iterator.next();
					//don't forget "zero keys = singular key that is the name of the field"
					//and since we don't care about the keys, we just max its length with 1
					this.keys = Math.max(1, this.field.getAnnotation(Property.class).key().length);
					this.cursor = 0;
				}
			}
		}
	}

	@SuppressWarnings("JavaDoc")
	final class Methods {
		public static void clear(Object instance) {
			Objects.requireNonNull(instance, "instance");
			throw new UnsupportedOperationException("clear");
		}

		public static boolean containsKey(Object instance, Object key) {
			Objects.requireNonNull(instance, "instance");

			for (Field field : Reflection.getPropertyFields(instance))
				if (field.isAnnotationPresent(Property.class))
					for (Object fieldKey : new PropertyKeySet(field))
						if (Objects.equals(key, fieldKey))
							return true;

			return false;
		}

		public static boolean containsValue(Object instance, Object value) {
			Objects.requireNonNull(instance, "instance");

			for (Field field : Reflection.getPropertyFields(instance))
				if (field.isAnnotationPresent(Property.class))
					if (Objects.equals(value, Reflection.getValue0(instance, field)))
						return true;

			return false;
		}

		public static <K, V> BeanEntrySet<K, V> entrySet(Object instance) {
			Objects.requireNonNull(instance, "instance");
			return new BeanEntrySet(instance);
		}

		public static <V> V get(Object instance, Object key) {
			Objects.requireNonNull(instance, "instance");

			for (Field field : Reflection.getPropertyFields(instance))
				if (field.isAnnotationPresent(Property.class))
					for (Object fieldKey : new PropertyKeySet(field))
						if (Objects.equals(key, fieldKey))
							return Reflection.getValue0(instance, field);

			//noinspection ReturnOfNull
			return null;
		}

		public static boolean isEmpty(Object instance) {
			Objects.requireNonNull(instance, "instance");

			for (Field field : Reflection.getPropertyFields(instance))
				if (field.isAnnotationPresent(Property.class))
					return false;

			return true;
		}

		public static <K> BeanKeySet<K> keySet(Object instance) {
			Objects.requireNonNull(instance, "instance");
			return new BeanKeySet(instance);
		}

		public static <V> V put(Object instance, Object key, V value) {
			Objects.requireNonNull(instance, "instance");

			for (Field field : Reflection.getPropertyFields(instance))
				if (field.isAnnotationPresent(Property.class))
					for (Object fieldKey : new PropertyKeySet(field))
						if (Objects.equals(key, fieldKey))
							return Reflection.setValue0(instance, field, value);

			throw new UnsupportedOperationException("Can't store the key: " + key);
		}

		public static void putAll(Object instance, Map map) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(map, "map");
			Set keySet = new HashSet(map.keySet());

			for (Field field : Reflection.getPropertyFields(instance))
				if (field.isAnnotationPresent(Property.class)) {
					//no need to reassign the same field
					boolean put = true;

					//first key in the Property annotation has more priority that other keys!
					for (Object key : new PropertyKeySet(field))
						if (keySet.remove(key) && put) {
							//found a key that can be put!
							//only if the field haven't been set by previous key.
							Reflection.setValue0(instance, field, map.get(key));
							//block next assignments in this field!
							put = false;
						}
				}

			//the bean can't handle the map! too large for it.
			if (!keySet.isEmpty())
				throw new UnsupportedOperationException("Can't store all the keys in: " + map);
		}

		public static <V> V remove(Object instance, Object key) {
			Objects.requireNonNull(instance, "instance");
			throw new UnsupportedOperationException("remove");
		}

		public static int size(Object instance) {
			Objects.requireNonNull(instance, "instance");

			int size = 0;
			for (Field field : Reflection.getPropertyFields(instance))
				if (field.isAnnotationPresent(Property.class))
					//don't forget "zero keys = singular key that is the name of the field"
					size += Math.max(1, field.getAnnotation(Property.class).key().length);

			return size;
		}

		public static <V> BeanValues<V> values(Object instance) {
			Objects.requireNonNull(instance, "instance");
			return new BeanValues(instance);
		}
	}

	/**
	 * An entry managing {@link Property} fields.
	 *
	 * @param <K> the type of the key in the entry.
	 * @param <V> the type of the value in the entry.
	 */
	final class PropertyEntry<K, V> implements Map.Entry<K, V> {
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
		 * The converter to be used when this entry is about to set a value that is not an instance of the specified
		 * type.
		 */
		private Converter converter;
		/**
		 * The metadata of this entry.
		 */
		private Property meta;
		/**
		 * The type of the value of this entry.
		 */
		private Clazz<V> type;

		/**
		 * Construct a new field entry that edits the given instance at the given field.
		 *
		 * @param instance the instance for the constructed entry to edit.
		 * @param field    the field where the constructed entry should edit in the given {@code instance}.
		 * @param recipe   the recipe to construct the key of the constructed entry.
		 * @throws NullPointerException if the given {@code instance} or {@code field} is null.
		 */
		private PropertyEntry(Object instance, Field field, Recipe recipe) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(field, "field");
			Objects.requireNonNull(recipe, "recipe");
			this.instance = instance;
			this.field = field;
			this.key = Recipe.Util.get(recipe);
		}

		/**
		 * Construct a new field entry that edits the given instance at the given field.
		 *
		 * @param instance the instance for the constructed entry to edit.
		 * @param field    the field where the constructed entry should edit in the given {@code instance}.
		 * @param key      the key of the constructed entry.
		 * @throws NullPointerException if the given {@code instance} or {@code field} is null.
		 */
		private PropertyEntry(Object instance, Field field, K key) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(field, "field");
			this.instance = instance;
			this.field = field;
			this.key = key;
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
		private PropertyEntry(Object instance, Field field, K key, V value) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(field, "field");
			this.instance = instance;
			this.field = field;
			this.key = key;
			this.setValue(value);
		}

		/**
		 * Get a {@link PropertyEntry} for a field that have the given key in the given instance. This method don't
		 * trust the given {@code key}. The given {@code key} will not be used. The key constructed to match the given
		 * {@code key} will be used instead.
		 *
		 * @param instance the instance that the returned entry is for a field in it.
		 * @param key      the key that the returned entry is having.
		 * @param <K>      the type of the key of the returned entry.
		 * @param <V>      the type of the value of the returned entry.
		 * @return a {@link PropertyEntry} for a field that have the given key in the given instance. Or null if there
		 * 		is no such field having the given {@code key} in the given {@code instance}.
		 * @throws NullPointerException if the given {@code instance} is null.
		 */
		static <K, V> PropertyEntry<K, V> from(Object instance, K key) {
			Objects.requireNonNull(instance, "instance");

			for (Field field : Reflection.getPropertyFields(instance))
				if (field.isAnnotationPresent(Property.class))
					for (K fieldKey : new PropertyKeySet<K>(field))
						if (Objects.equals(key, fieldKey))
							return new PropertyEntry(instance, field, fieldKey);

			return null;
		}

		/**
		 * Get a {@link PropertyEntry} for a field that have the given key in the given instance. Then set the value of
		 * the given {@code field} to the given {@code value}. This method don't trust the given {@code key}. The given
		 * {@code key} will not be used. The key constructed to match the given {@code key} will be used instead.
		 *
		 * @param instance the instance that the returned entry is for a field in it.
		 * @param key      the key that the returned entry is having.
		 * @param value    the value to be set to the given {@code field}.
		 * @param <K>      the type of the key of the returned entry.
		 * @param <V>      the type of the value of the returned entry.
		 * @return a {@link PropertyEntry} for a field that have the given key in the given instance. Or null if there
		 * 		is no such field having the given {@code key} in the given {@code instance}.
		 * @throws NullPointerException if the given {@code instance} is null.
		 */
		static <K, V> PropertyEntry<K, V> from(Object instance, K key, V value) {
			Objects.requireNonNull(instance, "instance");

			for (Field field : Reflection.getPropertyFields(instance))
				if (field.isAnnotationPresent(Property.class))
					for (K fieldKey : new PropertyKeySet<K>(field))
						if (Objects.equals(key, fieldKey))
							return new PropertyEntry(instance, field, fieldKey, value);

			return null;
		}

		/**
		 * Get a {@link PropertyEntry} with the given {@code key} for the given {@code field}. This method don't trust
		 * the given {@code key}. This method will check if the given {@code field} actually have the given {@code key}
		 * or not. The given {@code key} will not be used. The key constructed to match the given {@code key} will be
		 * used instead.
		 *
		 * @param instance the instance that the returned entry is for a field in it.
		 * @param field    the field where the returned entry is reading/writing its value.
		 * @param key      the key that the returned entry is having.
		 * @param <K>      the type of the key of the returned entry.
		 * @param <V>      the type of the value of the returned entry.
		 * @return a {@link PropertyEntry} with the given {@code key} for the given {@code field}.
		 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
		 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}. Or if the
		 *                                  given {@code field} is not in the given {@code instance}. Or if the given
		 *                                  {@code field} don't actually have the given {@code key} as a key of it.
		 */
		static <K, V> PropertyEntry<K, V> from(Object instance, Field field, K key) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(field, "field");

			Class dKlass = field.getDeclaringClass();
			Class iKlass = instance.getClass();
			if (!dKlass.isAssignableFrom(iKlass))
				throw new IllegalArgumentException(field + " isn't in the instance: " + instance);
			if (!field.isAnnotationPresent(Property.class))
				throw new IllegalArgumentException(field + " is not annotated with " + Property.class);

			for (K fieldKey : new PropertyKeySet<K>(field))
				if (Objects.equals(key, fieldKey))
					return new PropertyEntry(instance, field, fieldKey);

			throw new IllegalArgumentException(field + " don't have the key: " + key);
		}

		/**
		 * Get a {@link PropertyEntry} with the given {@code key} for the given {@code field}. Then set the value of the
		 * given {@code field} to the given {@code value}. This method don't trust the given {@code key}. This method
		 * will check if the given {@code field} actually have the given {@code key} or not. The given {@code key} will
		 * not be used. The key constructed to match the given {@code key} will be used instead.
		 *
		 * @param instance the instance that the returned entry is for a field in it.
		 * @param field    the field where the returned entry is reading/writing its value.
		 * @param key      the key that the returned entry is having.
		 * @param value    the value to be set to the given {@code field}.
		 * @param <K>      the type of the key of the returned entry.
		 * @param <V>      the type of the value of the returned entry.
		 * @return a {@link PropertyEntry} with the given {@code key} for the given {@code field}.
		 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
		 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}. Or if the
		 *                                  given {@code field} is not in the given {@code instance}. Or if the given
		 *                                  {@code field} don't actually have the given {@code key} as a key of it.
		 */
		static <K, V> PropertyEntry<K, V> from(Object instance, Field field, K key, V value) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(field, "field");

			Class dKlass = field.getDeclaringClass();
			Class iKlass = instance.getClass();
			if (!dKlass.isAssignableFrom(iKlass))
				throw new IllegalArgumentException(field + " isn't in the instance: " + instance);
			if (!field.isAnnotationPresent(Property.class))
				throw new IllegalArgumentException(field + " is not annotated with " + Property.class);

			for (K fieldKey : new PropertyKeySet<K>(field))
				if (Objects.equals(key, fieldKey))
					return new PropertyEntry(instance, field, fieldKey, value);

			throw new IllegalArgumentException(field + " don't have the key: " + key);
		}

		@SuppressWarnings("JavaDoc")
		static <K, V> PropertyEntry<K, V> from(Object instance, Field field, int key) {
			Objects.requireNonNull(instance, "instance");

			Class dKlass = field.getDeclaringClass();
			Class iKlass = instance.getClass();
			if (!dKlass.isAssignableFrom(iKlass))
				throw new IllegalArgumentException(field + " isn't in the instance: " + instance);
			if (!field.isAnnotationPresent(Property.class))
				throw new IllegalArgumentException(field + " is not annotated with " + Property.class);

			Recipe[] recipes = field.getAnnotation(Property.class).key();

			if (key < recipes.length)
				//if the field have custom keys, and the given key is within the bounds
				return new PropertyEntry(instance, field, recipes[key]);
			if (key == 0)
				//if the field don't have specified keys, and the given key is 0
				return new PropertyEntry(instance, field, field.getName());

			throw new IllegalArgumentException("Field does not have such key index: " + key);
		}

		@SuppressWarnings("JavaDoc")
		static <K, V> PropertyEntry<K, V> from(Object instance, Field field, int key, V value) {
			Objects.requireNonNull(instance, "instance");

			Class dKlass = field.getDeclaringClass();
			Class iKlass = instance.getClass();
			if (!dKlass.isAssignableFrom(iKlass))
				throw new IllegalArgumentException(field + " isn't in the instance: " + instance);
			if (!field.isAnnotationPresent(Property.class))
				throw new IllegalArgumentException(field + " is not annotated with " + Property.class);

			Recipe[] recipes = field.getAnnotation(Property.class).key();

			if (key < recipes.length)
				//if the field have custom keys, and the given key is within the bounds
				return new PropertyEntry(instance, field, recipes[key], value);
			if (key == 0)
				//if the field don't have specified keys, and the given key is 0
				return new PropertyEntry(instance, field, field.getName(), value);

			throw new IllegalArgumentException("Field does not have such key index: " + key);
		}

		@Override
		public K getKey() {
			return this.key;
		}

		@Override
		public V getValue() {
			return Reflection.getValue0(
					this.instance,
					this.field
			);
		}

		@Override
		public V setValue(V value) {
			return Reflection.setValue0(
					this.instance, this.field,
					value,
					this.getConverter(),
					this.getType()
			);
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(this.getKey()) ^
				   Objects.hashCode(this.getValue());
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this)
				//quick match
				return true;
			if (obj instanceof Bean.PropertyEntry)
				//field match
				return ((PropertyEntry) obj).instance == this.instance &&
					   ((PropertyEntry) obj).field == this.field &&
					   Objects.equals(((PropertyEntry) obj).key, this.key);
			//entry match
			return obj instanceof Map.Entry &&
				   Objects.equals(((Map.Entry) obj).getKey(), this.getKey()) &&
				   Objects.equals(((Map.Entry) obj).getValue(), this.getValue());
		}

		@Override
		public String toString() {
			return this.getKey() + "=" + this.getValue();
		}

		/**
		 * Get the converter to be used when this entry is about to set a value that is not an instance of the specified
		 * type.
		 *
		 * @return the converter used by this entry.
		 */
		public Converter getConverter() {
			return this.converter == null ?
				   (this.converter = Where.Util.getValue(this.getMeta().converter())) :
				   this.converter;
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
			return this.meta == null ?
				   (this.meta = this.field.getAnnotation(Property.class)) :
				   this.meta;
		}

		/**
		 * Get the type of the value of this entry.
		 *
		 * @return the type of the value of this entry.
		 */
		public Clazz<V> getType() {
			return this.type == null ?
				   (this.type = Reflection.getType0(this.field, this.getMeta().type())) :
				   this.type;
		}
	}

	/**
	 * A set containing the keys of a property.
	 *
	 * @param <K> the type of the keys.
	 */
	final class PropertyKeySet<K> extends AbstractSet<K> {
		/**
		 * The field this property-key-set is for.
		 */
		private final Field field;
		/**
		 * The recipes to construct the keys of the field of this key-set.
		 */
		private final Recipe[] recipes;

		/**
		 * Construct a new property-key-set for the given {@code field}.
		 *
		 * @param field the field the constructed set is for.
		 * @throws NullPointerException     if the given {@code field} is null.
		 * @throws IllegalArgumentException if the given {@code field} isn't annotated with {@link Property}.
		 */
		public PropertyKeySet(Field field) {
			Objects.requireNonNull(field, "field");
			if (!field.isAnnotationPresent(Property.class))
				throw new IllegalArgumentException("Field isn't annotated with @Property: " + field);
			this.field = field;
			this.recipes = field.getAnnotation(Property.class).key();
		}

		/**
		 * Construct a new property-key-set for the given {@code field} with the given {@code recipes}.
		 *
		 * @param field   the field the constructed set is for.
		 * @param recipes the recipes of the keys of the given {@code field}.
		 * @throws NullPointerException if the given {@code field} or {@code recipes} is null.
		 */
		private PropertyKeySet(Field field, Recipe[] recipes) {
			Objects.requireNonNull(field, "field");
			Objects.requireNonNull(recipes, "recipes");
			this.field = field;
			this.recipes = recipes;
		}

		@Override
		public Iterator<K> iterator() {
			return new PropertyKeySetIterator();
		}

		@Override
		public int size() {
			return Math.max(1, this.recipes.length);
		}

		/**
		 * An iterator for property-key-sets.
		 */
		private final class PropertyKeySetIterator implements Iterator<K> {
			/**
			 * The current position.
			 */
			private int cursor;

			@Override
			public boolean hasNext() {
				return this.cursor == 0 || this.cursor < PropertyKeySet.this.recipes.length;
			}

			@Override
			public K next() {
				if (this.cursor < PropertyKeySet.this.recipes.length)
					//if there is more keys
					return Recipe.Util.get(PropertyKeySet.this.recipes[this.cursor++]);

				if (this.cursor == 0) {
					//if the key's array is empty and it is the first next() invoke
					this.cursor = 1;
					return (K) PropertyKeySet.this.field.getName();
				}

				throw new NoSuchElementException("No more elements");
			}
		}
	}

	/**
	 * Utilities to deal with {@link Bean}s.
	 */
	final class Reflection {
		/**
		 * This is an util class. And must not be instanced as an object.
		 *
		 * @throws AssertionError when called.
		 */
		private Reflection() {
			throw new AssertionError("No instance for you!");
		}

		@SuppressWarnings("JavaDoc")
		public static Set<Field> getPropertyFields(Class klass) {
			Objects.requireNonNull(klass, "klass");
			return Collectionz.filteredSet(
					cufy.util.Reflection.fieldsSet(klass),
					field -> field.isAnnotationPresent(Property.class)
			);
		}

		@SuppressWarnings("JavaDoc")
		public static Set<Field> getPropertyFields(Object instance) {
			Objects.requireNonNull(instance, "instance");
			return Reflection.getPropertyFields(instance.getClass());
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

			return Reflection.getType0(field);
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
				throw new AssertionError(field + " isn't in the instance: " + instance);
			if (!field.isAnnotationPresent(Property.class))
				throw new IllegalArgumentException(field + " is not annotated with " + Property.class);

			return Reflection.getValue0(instance, field);
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
		 * @see #setValue0(Object, Field, Object)
		 * @see #setValue0(Object, Field, Object, Converter, Clazz)
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

			return Reflection.setValue0(instance, field, value);
		}

		@SuppressWarnings("JavaDoc")
		private static <V> Clazz<V> getType0(Field field) {
			Objects.requireNonNull(field, "field");
			return Reflection.getType0(field, field.getAnnotation(Property.class).type());
		}

		@SuppressWarnings("JavaDoc")
		private static <V> Clazz<V> getType0(Field field, Type[] type) {
			Objects.requireNonNull(field, "field");
			Objects.requireNonNull(type, "type");

			if (type.length == 0)
				return (Clazz<V>) Clazz.of(field.getType());
			if (type.length == 1)
				return (Clazz<V>) Type.Util.get(type[0]);

			throw new IllegalMetaException("Bean.Property.type().length > 1");
		}

		/**
		 * The backing method for the method {@link #getValue(Object, Field)}. Calling this method will skip all
		 * assertions and directly get the value. Skipping assertions does not mean skipping null-checks!.
		 *
		 * @param instance the instance that the returned value is stored at in the given {@code field}.
		 * @param field    the field that the returned value is stored at in the given {@code instance}.
		 * @param <V>      the type of the returned value.
		 * @return the value stored at the given {@code field} in the given {@code instance}.
		 * @throws NullPointerException if the given {@code instance} or {@code field} is null.
		 */
		private static <V> V getValue0(Object instance, Field field) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(field, "field");
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
		 * A method between {@link #setValue(Object, Field, Object)} and {@link #setValue0(Object, Field, Object,
		 * Converter, Clazz)}. Provides the automation of {@link #setValue(Object, Field, Object)} and skips the
		 * assertions.
		 *
		 * @param instance the instance for the given {@code value} to be set in at the given {@code field}.
		 * @param value    the value to be set in the given {@code instance} at the given {@code field}.
		 * @param field    the filed for the given {@code value} to be set at in the given {@code instance}.
		 * @param <V>      the type of the given {@code value}.
		 * @return the previous value stored in the given {@code instance} at the given {@code field}.
		 * @throws NullPointerException if the given {@code instance} or {@code field} is null.
		 */
		private static <V> V setValue0(Object instance, Field field, V value) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(field, "field");

			Property property = field.getAnnotation(Property.class);
			Where where = property.converter();
			Converter converter = Where.Util.getValue(where);
			Clazz<V> type = Reflection.getType0(field, property.type());

			return Reflection.setValue0(instance, field, value, converter, type);
		}

		/**
		 * The backing method for the method {@link #setValue(Object, Field, Object)}. Calling this method will skip all
		 * assertions and directly set the value. Skipping assertions does not mean skipping null-checks!.
		 *
		 * @param <V>       the type of the value
		 * @param instance  to set the value to
		 * @param field     that holds the value
		 * @param value     to be set
		 * @param converter the converter of the field
		 * @param type      the type of the field
		 * @return the previous value stored at that field in that instance
		 * @throws NullPointerException     if the given {@code field} or {@code instance} or {@code converter} or
		 *                                  {@code type} is null.
		 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}. Or if the
		 *                                  given {@code value} can't be set to the given {@code field}.
		 * @throws IllegalAccessError       if the given {@code field} object is enforcing Java language access control,
		 *                                  and the underlying field is either inaccessible or final.
		 */
		private static <V> V setValue0(Object instance, Field field, V value, Converter converter, Clazz<V> type) {
			Objects.requireNonNull(field, "field");
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(converter, "converter");
			Objects.requireNonNull(type, "type");
			try {
				field.setAccessible(true);

				int modifiers = field.getModifiers();
				Clazz<V> valueType = Clazz.Generate.from(value);
				V old = (V) field.get(instance);

				if (!Modifier.isFinal(modifiers))
					//direct converting then assign it
					field.set(instance, converter.convert(new ConvertToken(value, value, valueType, type)));
				else if (converter.convert(new ConvertToken(value, old, valueType, type)) != old)
					//skip unnecessary reassignment by convert remotely;
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
