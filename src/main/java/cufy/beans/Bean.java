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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

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
	//All in inner classes, for better security!

	@Override
	default int size() {
		return Methods.Raw.size(this);
	}

	@Override
	default boolean isEmpty() {
		return Methods.Raw.isEmpty(this);
	}

	@Override
	default boolean containsKey(Object key) {
		return Methods.Raw.containsKey(this, key);
	}

	@Override
	default boolean containsValue(Object value) {
		return Methods.Raw.containsValue(this, value);
	}

	@Override
	default V get(Object key) {
		return Methods.Raw.get(this, key);
	}

	@Override
	default V put(K key, V value) {
		return Methods.Raw.put(this, key, value);
	}

	@Override
	default V remove(Object key) {
		return Methods.Raw.remove(this, key);
	}

	@Override
	default void putAll(Map<? extends K, ? extends V> map) {
		Methods.Raw.putAll(this, map);
	}

	@Override
	default void clear() {
		Methods.Raw.clear(this);
	}

	@Override
	default Set<K> keySet() {
		return Methods.Raw.keySet(this);
	}

	@Override
	default Collection<V> values() {
		return Methods.Raw.values(this);
	}

	@Override
	default Set<Map.Entry<K, V>> entrySet() {
		return (Set) Methods.Raw.entrySet(this);
	}

	@Override
	default V getOrDefault(Object key, V defaultValue) {
		return Methods.Raw.getOrDefault(this, key, defaultValue);
	}

	@Override
	default void forEach(BiConsumer<? super K, ? super V> consumer) {
		Methods.Raw.forEach(this, consumer);
	}

	@Override
	default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		Methods.Raw.replaceAll(this, function);
	}

	@Override
	default V putIfAbsent(K key, V value) {
		return Methods.Raw.putIfAbsent(this, key, value);
	}

	@Override
	default boolean remove(Object key, Object value) {
		return Methods.Raw.remove(key, value);
	}

	@Override
	default boolean replace(K key, V oldValue, V newValue) {
		return Methods.Raw.replace(this, key, oldValue, newValue);
	}

	@Override
	default V replace(K key, V value) {
		return Methods.Raw.replace(this, key, value);
	}

	@Override
	default V computeIfAbsent(K key, Function<? super K, ? extends V> function) {
		return Methods.Raw.computeIfAbsent(this, key, function);
	}

	@Override
	default V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> function) {
		return Methods.Raw.computeIfPresent(this, key, function);
	}

	@Override
	default V compute(K key, BiFunction<? super K, ? super V, ? extends V> function) {
		return Methods.Raw.compute(this, key, function);
	}

	@Override
	default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> function) {
		return Methods.Raw.merge(this, key, value, function);
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
		Recipe[] keys() default {};

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

		/**
		 * A set containing the keys of a property.
		 *
		 * @param <K> the type of the keys.
		 */
		final class KeySet<K> extends AbstractSet<K> {
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
			 * @throws NullPointerException if the given {@code field} is null.
			 */
			private KeySet(Field field) {
				Objects.requireNonNull(field, "field");
				this.field = field;
				this.recipes = field.getAnnotation(Property.class).keys();
			}

			@Override
			public PropertyKeySetIterator iterator() {
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
					return this.cursor == 0 || this.cursor < KeySet.this.recipes.length;
				}

				@Override
				public K next() {
					if (this.cursor < KeySet.this.recipes.length)
						//if there is more keys
						return Recipe.Util.get(KeySet.this.recipes[this.cursor++]);

					if (this.cursor == 0) {
						//if the key's array is empty and it is the first next() invoke
						this.cursor = 1;
						return (K) KeySet.this.field.getName();
					}

					throw new NoSuchElementException("No more elements");
				}
			}
		}
	}

	/**
	 * An entrySet for fullBeans.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 */
	final class DelegateEntrySet<K, V> extends EntrySet<Entry<K, V>> {
		/**
		 * The bean this entrySet is for.
		 */
		private final Bean<K, V> delegate;
		/**
		 * The instance this entrySet is for.
		 */
		private final Object instance;
		/**
		 * The list backing this set.
		 */
		private final List list = new ArrayList();

		/**
		 * Construct a new entrySet for the given {@code delegate} that have the given {@code instance}.
		 *
		 * @param delegate the delegate this entrySet is for.
		 * @param instance the instance the given {@code delegate} is for.
		 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
		 */
		private DelegateEntrySet(Bean<K, V> delegate, Object instance) {
			Objects.requireNonNull(delegate, "delegate");
			Objects.requireNonNull(instance, "instance");
			this.delegate = delegate;
			this.instance = instance;
			this.addAll(Methods.Raw.entrySet(instance));
		}

		@Override
		public FullBeanEntrySetIterator iterator() {
			return new FullBeanEntrySetIterator();
		}

		@Override
		public int size() {
			return this.list.size();
		}

		@Override
		public boolean add(Entry<K, V> entry) {
			return !this.list.contains(entry) && this.list.add(entry);
		}

		/**
		 * An iterator for iterating the entries of a full-bean.
		 */
		private final class FullBeanEntrySetIterator implements Iterator<Entry<K, V>> {
			/**
			 * The iterator backing this iterator.
			 */
			private final Iterator<Entry<K, V>> iterator = DelegateEntrySet.this.list.iterator();

			@Override
			public boolean hasNext() {
				return this.iterator.hasNext();
			}

			@Override
			public Entry<K, V> next() {
				return this.iterator.next();
			}

			@Override
			public void remove() {
				this.iterator.remove();
			}
		}
	}

	/**
	 * A keySet for fullBeans.
	 *
	 * @param <K> the type of the keys.
	 */
	final class DelegateKeySet<K> extends KeySet<K> {
		/**
		 * The bean this keySet is for.
		 */
		private final Bean<K, ?> delegate;
		/**
		 * The instance this keySet is for.
		 */
		private final Object instance;

		/**
		 * Construct a new keySet for the given {@code delegate} that have the given {@code instance}.
		 *
		 * @param delegate the delegate this keySet is for.
		 * @param instance the instance the given {@code delegate} is for.
		 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
		 */
		private DelegateKeySet(Bean<K, ?> delegate, Object instance) {
			Objects.requireNonNull(delegate, "delegate");
			Objects.requireNonNull(instance, "instance");
			this.delegate = delegate;
			this.instance = instance;
		}

		@Override
		public FullBeanKeySetIterator iterator() {
			return new FullBeanKeySetIterator();
		}

		@Override
		public int size() {
			return this.delegate.size();
		}

		@Override
		public boolean isEmpty() {
			return this.delegate.isEmpty();
		}

		@Override
		public boolean contains(Object k) {
			return this.delegate.containsKey(k);
		}

		@Override
		public void clear() {
			this.delegate.clear();
		}

		/**
		 * An iterator for the keySet of a fullBean.
		 */
		public final class FullBeanKeySetIterator implements Iterator<K> {
			/**
			 * The iterator backing this iterator.
			 */
			private final Iterator<Map.Entry<K, ?>> iterator = (Iterator) DelegateKeySet.this.delegate.entrySet().iterator();

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
	final class DelegateValues<V> extends Values<V> {
		/**
		 * The bean this values-collection is for.
		 */
		private final Bean<?, V> delegate;
		/**
		 * The instance this values-collection is for.
		 */
		private final Object instance;

		/**
		 * Construct a new values-collection for the given {@code delegate} that have the given {@code instance}.
		 *
		 * @param delegate the bean this values-collection is for.
		 * @param instance the instance the given {@code delegate} is for.
		 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
		 */
		private DelegateValues(Bean<?, V> delegate, Object instance) {
			Objects.requireNonNull(delegate, "delegate");
			Objects.requireNonNull(instance, "instance");
			this.delegate = delegate;
			this.instance = instance;
		}

		@Override
		public FullBeanValuesIterator iterator() {
			return new FullBeanValuesIterator();
		}

		@Override
		public int size() {
			return this.delegate.size();
		}

		@Override
		public boolean isEmpty() {
			return this.delegate.isEmpty();
		}

		@Override
		public boolean contains(Object v) {
			return this.delegate.containsValue(v);
		}

		@Override
		public void clear() {
			this.delegate.clear();
		}

		/**
		 * An iterator that iterates the values of a fullBean.
		 */
		public final class FullBeanValuesIterator implements Iterator<V> {
			/**
			 * The iterator backing this iterator.
			 */
			private final Iterator<Map.Entry<?, V>> iterator = (Iterator) DelegateValues.this.delegate.entrySet().iterator();

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
	 * Utility methods for the interface {@link Bean}s.
	 */
	final class Methods {
		/**
		 * This is an util class. And must not be instanced as an object.
		 *
		 * @throws AssertionError when called.
		 */
		private Methods() {
			throw new AssertionError("No instance for you!");
		}

		/**
		 * The concrete methods.
		 */
		public static final class Concrete {
			/**
			 * This is an util class. And must not be instanced as an object.
			 *
			 * @throws AssertionError when called.
			 */
			private Concrete() {
				throw new AssertionError("No instance for you!");
			}

			/**
			 * Get the converter specified in the given {@code field}'s {@link Property} annotation.
			 *
			 * @param field to get its converter.
			 * @return the converter specified in the given {@code field}'s {@link Property} annotation.
			 * @throws NullPointerException     if the given {@code field} is null.
			 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}
			 *                                  annotation.
			 */
			public static Converter getConverter(Field field) {
				Objects.requireNonNull(field, "field");
				if (!field.isAnnotationPresent(Property.class))
					throw new IllegalArgumentException(field + " is not annotated with " + Property.class);

				return PrivateConcrete.getConverter(field);
			}

			/**
			 * Get a {@link PropertyEntry} for a field that have the given key in the given instance. Or a {@link
			 * PropertylessEntry} if there is no such field having the given {@code key} in the given {@code instance}.
			 * This method don't trust the given {@code key}. The given {@code key} will not be used. The key
			 * constructed to match the given {@code key} will be used instead.
			 *
			 * @param instance the instance that the returned entry is for a field in it.
			 * @param key      the key that the returned entry is having.
			 * @param <K>      the type of the key of the returned entry.
			 * @param <V>      the type of the value of the returned entry.
			 * @return a {@link PropertyEntry} for a field that have the given key in the given instance. Or null if
			 * 		there is no such field having the given {@code key} in the given {@code instance}.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			public static <K, V> Entry<K, V> getEntry(Object instance, K key) {
				Objects.requireNonNull(instance, "instance");
				return PrivateConcrete.getEntry(instance, key);
			}

			/**
			 * Get a {@link PropertyEntry} for a field that have the given key in the given instance. Or a {@link
			 * PropertylessEntry} if there is no such field having the given {@code key} in the given {@code instance}.
			 * Then set the value of it to teh given {@code value}. This method don't trust the given {@code key}. The
			 * given {@code key} will not be used. The key constructed to match the given {@code key} will be used
			 * instead.
			 *
			 * @param instance the instance that the returned entry is for a field in it.
			 * @param key      the key that the returned entry is having.
			 * @param value    the initial value the returned entry is having.
			 * @param <K>      the type of the key of the returned entry.
			 * @param <V>      the type of the value of the returned entry.
			 * @return a {@link PropertyEntry} for a field that have the given key in the given instance. Or a {@link
			 *        PropertylessEntry} if there is no such field having the given {@code key} in the given {@code
			 * 		instance}.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			public static <K, V> Entry<K, V> getEntry(Object instance, K key, V value) {
				Objects.requireNonNull(instance, "instance");
				return PrivateConcrete.getEntry(instance, key, value);
			}

			/**
			 * Get a set of all fields annotated with {@link Property} in the class of the given {@code instance}.
			 *
			 * @param instance the instance of the class to get all the property-fields of it.
			 * @return a set of all fields annotated with {@link Property} in the class of the given {@code instance}.
			 * @throws NullPointerException if the given {@code klass} is null.
			 */
			public static Set<Field> getFields(Object instance) {
				Objects.requireNonNull(instance, "instance");
				return Concrete.getFields(instance.getClass());
			}

			/**
			 * Get a set of all fields annotated with {@link Property} in the given {@code klass}.
			 *
			 * @param klass the class to get all the property-fields of it.
			 * @return a set of all fields annotated with {@link Property} in the given {@code klass}.
			 * @throws NullPointerException if the given {@code klass} is null.
			 */
			public static Set<Field> getFields(Class klass) {
				Objects.requireNonNull(klass, "klass");
				return Collectionz.filteredSet(
						cufy.util.Reflection.fieldsSet(klass),
						field -> field.isAnnotationPresent(Property.class)
				);
			}

			/**
			 * Get a set of the keys the given {@code field} does have.
			 *
			 * @param field the field to return the keys that it have.
			 * @param <K>   the type of the keys returned.
			 * @return a set of the keys the given {@code field} does have.
			 * @throws NullPointerException     if the given {@code field} is null.
			 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}.
			 */
			public static <K> Set<K> getKeys(Field field) {
				Objects.requireNonNull(field, "field");
				if (!field.isAnnotationPresent(Property.class))
					throw new IllegalArgumentException("Field is not annotated with @Property: " + field);
				return PrivateConcrete.getKeys(field);
			}

			/**
			 * Get a {@link PropertyEntry} with the key at with the {@code key} index in the given {@code field}.
			 *
			 * @param instance the instance that the returned entry is for a field in it.
			 * @param field    the field where the returned entry is reading/writing its value.
			 * @param key      the index of the key in the field to be the key of the returned entry.
			 * @param <K>      the type of the key of the returned entry.
			 * @param <V>      the type of the value of the returned entry.
			 * @return a {@link PropertyEntry} with the key at with the {@code key} index in the given {@code field}
			 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
			 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}. Or if
			 *                                  the given {@code field} is not in the given {@code instance}. Or if the
			 *                                  given {@code field} don't have a key at the given {@code key} index.
			 */
			public static <K, V> PropertyEntry<K, V> getPropertyEntry(Object instance, Field field, int key) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(field, "field");
				if (!field.getDeclaringClass().isAssignableFrom(instance.getClass()) ||
					!field.isAnnotationPresent(Property.class))
					throw new IllegalArgumentException("Field rejected: " + field);

				return PrivateConcrete.getPropertyEntry(instance, field, key);
			}

			/**
			 * Get a {@link PropertyEntry} with the key that have the given {@code key} index in the given {@code
			 * field}. Then set the value of the given {@code field} to the given {@code value}.
			 *
			 * @param instance the instance that the returned entry is for a field in it.
			 * @param field    the field where the returned entry is reading/writing its value.
			 * @param key      the index of the key in the field to be the key of the returned entry.
			 * @param value    the value to be set to the given {@code field}.
			 * @param <K>      the type of the key of the returned entry.
			 * @param <V>      the type of the value of the returned entry.
			 * @return a {@link PropertyEntry} with the key at with the {@code key} index in the given {@code field}
			 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
			 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}. Or if
			 *                                  the given {@code field} is not in the given {@code instance}. Or if the
			 *                                  given {@code field} don't have a key at the given {@code key} index.
			 */
			public static <K, V> PropertyEntry<K, V> getPropertyEntry(Object instance, Field field, int key, V value) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(field, "field");
				if (!field.getDeclaringClass().isAssignableFrom(instance.getClass()) ||
					!field.isAnnotationPresent(Property.class))
					throw new IllegalArgumentException("Field rejected: " + field);

				return PrivateConcrete.getPropertyEntry(instance, field, key, value);
			}

			/**
			 * Get a {@link PropertyEntry} that have the key from the given {@code key} recipe. This method does not
			 * trust the given {@code recipe} and it will check if really came from the given {@code field} or not.
			 *
			 * @param instance the instance that the returned entry is for a field in it.
			 * @param field    the field where the returned entry is reading/writing its value.
			 * @param key      the recipe to constructed the the key of the returned entry.
			 * @param <K>      the type of the key of the returned entry.
			 * @param <V>      the type of the value of the returned entry.
			 * @return a {@link PropertyEntry} that have the key from the given {@code key} recipe.
			 * @throws NullPointerException     if the given {@code instance} or {@code field} or {@code key} is null.
			 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}. Or if
			 *                                  the given {@code field} is not in the given {@code instance}. Or if the
			 *                                  given {@code field} don't have a the given {@code key} recipe.
			 */
			public static <K, V> PropertyEntry<K, V> getPropertyEntry(Object instance, Field field, Recipe key) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(field, "field");
				Objects.requireNonNull(key, "key");
				if (!field.getDeclaringClass().isAssignableFrom(instance.getClass()) ||
					!field.isAnnotationPresent(Property.class))
					throw new IllegalArgumentException("Field rejected: " + field);

				for (Recipe recipe : field.getAnnotation(Property.class).keys())
					if (key == recipe)
						return PrivateConcrete.getPropertyEntry(instance, field, recipe);

				throw new IllegalArgumentException("Key Recipe rejected: " + key);
			}

			/**
			 * Get a {@link PropertyEntry} that have the key from the given {@code key} recipe. Then set the value of
			 * the given {@code field} to the given {@code value}. This method does not trust the given {@code recipe}
			 * and it will check if really came from the given {@code field} or not.
			 *
			 * @param instance the instance that the returned entry is for a field in it.
			 * @param field    the field where the returned entry is reading/writing its value.
			 * @param key      the recipe to constructed the the key of the returned entry.
			 * @param value    the value to be set to the given {@code field}.
			 * @param <K>      the type of the key of the returned entry.
			 * @param <V>      the type of the value of the returned entry.
			 * @return a {@link PropertyEntry} that have the key from the given {@code key} recipe.
			 * @throws NullPointerException     if the given {@code instance} or {@code field} or {@code key} is null.
			 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}. Or if
			 *                                  the given {@code field} is not in the given {@code instance}. Or if the
			 *                                  given {@code field} don't have a the given {@code key} recipe.
			 */
			public static <K, V> PropertyEntry<K, V> getPropertyEntry(Object instance, Field field, Recipe key, V value) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(field, "field");
				Objects.requireNonNull(key, "key");
				if (!field.getDeclaringClass().isAssignableFrom(instance.getClass()) ||
					!field.isAnnotationPresent(Property.class))
					throw new IllegalArgumentException("Field rejected: " + field);

				for (Recipe recipe : field.getAnnotation(Property.class).keys())
					if (key == recipe)
						return PrivateConcrete.getPropertyEntry(instance, field, recipe, value);

				throw new IllegalArgumentException("Key Recipe rejected: " + key);
			}

			/**
			 * Get a {@link PropertyEntry} with the given {@code key} for the given {@code field}. This method don't
			 * trust the given {@code key}. This method will check if the given {@code field} actually have the given
			 * {@code key} or not. The given {@code key} will not be used. The key constructed to match the given {@code
			 * key} will be used instead.
			 *
			 * @param instance the instance that the returned entry is for a field in it.
			 * @param field    the field where the returned entry is reading/writing its value.
			 * @param key      the key that the returned entry is having.
			 * @param <K>      the type of the key of the returned entry.
			 * @param <V>      the type of the value of the returned entry.
			 * @return a {@link PropertyEntry} with the given {@code key} for the given {@code field}.
			 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
			 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}. Or if
			 *                                  the given {@code field} is not in the given {@code instance}. Or if the
			 *                                  given {@code field} don't actually have the given {@code key} as a key
			 *                                  of it.
			 */
			public static <K, V> PropertyEntry<K, V> getPropertyEntry(Object instance, Field field, K key) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(field, "field");
				if (!field.getDeclaringClass().isAssignableFrom(instance.getClass()) ||
					!field.isAnnotationPresent(Property.class))
					throw new IllegalArgumentException("Field rejected: " + field);

				for (K fieldKey : PrivateConcrete.<K>getKeys(field))
					if (Objects.equals(key, fieldKey))
						return PrivateConcrete.getPropertyEntry(instance, field, fieldKey);

				throw new IllegalArgumentException("Key rejected: " + key);
			}

			/**
			 * Get a {@link PropertyEntry} with the given {@code key} for the given {@code field}. Then set the value of
			 * the given {@code field} to the given {@code value}. This method don't trust the given {@code key}. This
			 * method will check if the given {@code field} actually have the given {@code key} or not. The given {@code
			 * key} will not be used. The key constructed to match the given {@code key} will be used instead.
			 *
			 * @param instance the instance that the returned entry is for a field in it.
			 * @param field    the field where the returned entry is reading/writing its value.
			 * @param key      the key that the returned entry is having.
			 * @param value    the value to be set to the given {@code field}.
			 * @param <K>      the type of the key of the returned entry.
			 * @param <V>      the type of the value of the returned entry.
			 * @return a {@link PropertyEntry} with the given {@code key} for the given {@code field}.
			 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
			 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}. Or if
			 *                                  the given {@code field} is not in the given {@code instance}. Or if the
			 *                                  given {@code field} don't actually have the given {@code key} as a key
			 *                                  of it.
			 */
			public static <K, V> PropertyEntry<K, V> getPropertyEntry(Object instance, Field field, K key, V value) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(field, "field");
				if (!field.getDeclaringClass().isAssignableFrom(instance.getClass()) ||
					!field.isAnnotationPresent(Property.class))
					throw new IllegalArgumentException("Field rejected: " + field);

				for (K fieldKey : PrivateConcrete.<K>getKeys(field))
					if (Objects.equals(key, fieldKey))
						return PrivateConcrete.getPropertyEntry(instance, field, fieldKey, value);

				throw new IllegalArgumentException("Key rejected: " + key);
			}

			/**
			 * Get a temporary entrySet containing for the given {@code instance} all and only the given {@code keys}.
			 *
			 * @param instance the instance the returned entrySet is for.
			 * @param keys     all and only keys in the returned entrySet.
			 * @param <K>      the type of the keys.
			 * @param <V>      the type of the values
			 * @return a temporary entrySet containing for the given {@code instance} all and only the given {@code
			 * 		keys}.
			 * @throws NullPointerException if the given {@code instance} or {@code keys} is null.
			 */
			public static <K, V> TemporaryEntrySet<K, V> getTemporaryEntrySet(Object instance, Set<K> keys) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(keys, "keys");
				return new TemporaryEntrySet(instance, keys);
			}

			/**
			 * Get a temporary entrySet containing for the given {@code instance} all and only the given {@code keys}.
			 *
			 * @param instance the instance the returned entrySet is for.
			 * @param keys     all and only keys in the returned entrySet.
			 * @param values   the initial value supplier for each entry in the returned entrySet.
			 * @param <K>      the type of the keys.
			 * @param <V>      the type of the values
			 * @return a temporary entrySet containing for the given {@code instance} all and only the given {@code
			 * 		keys}.
			 * @throws NullPointerException if the given {@code instance} or {@code keys} is null.
			 */
			public static <K, V> TemporaryEntrySet<K, V> getTemporaryEntrySet(Object instance, Set<K> keys, BiFunction<? super K, ? super V, ? extends V> values) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(keys, "keys");
				Objects.requireNonNull(values, "values");
				return new TemporaryEntrySet(instance, keys, values);
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

				return PrivateConcrete.getType(field);
			}

			/**
			 * Get the value stored at the given {@code field} in the given {@code instance}.
			 *
			 * @param instance the instance that the returned value is stored at in the given {@code field}.
			 * @param field    the field that the returned value is stored at in the given {@code instance}.
			 * @param <V>      the type of the returned value.
			 * @return the value stored at the given {@code field} in the given {@code instance}.
			 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
			 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}. Or if
			 *                                  the given {@code field} is not in the given {@code instance}.
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

				return PrivateConcrete.getValue(instance, field);
			}

			/**
			 * Set the value stored at the given {@code field} on the given {@code instance} to the given {@code
			 * value}.
			 *
			 * @param instance the instance for the given {@code value} to be set in at the given {@code field}.
			 * @param value    the value to be set in the given {@code instance} at the given {@code field}.
			 * @param field    the filed for the given {@code value} to be set at in the given {@code instance}.
			 * @param <V>      the type of the given {@code value}.
			 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
			 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}. Or if
			 *                                  the given {@code field} is not in the given {@code instance}.
			 * @see PrivateConcrete#setValue(Object, Field, Object)
			 * @see PrivateConcrete#setValue(Object, Field, Object, Converter, Clazz)
			 */
			public static <V> void setValue(Object instance, Field field, V value) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(field, "field");

				Class dKlass = field.getDeclaringClass();
				Class iKlass = instance.getClass();
				if (!dKlass.isAssignableFrom(iKlass))
					throw new IllegalArgumentException(field + " isn't in the instance: " + instance);
				if (!field.isAnnotationPresent(Property.class))
					throw new IllegalArgumentException(field + " is not annotated with " + Property.class);

				PrivateConcrete.setValue(instance, field, value);
			}
		}

		/**
		 * The methods for the {@link Bean}s that are delegating to an instance.
		 */
		public static final class Delegate {
			/**
			 * This is an util class and must not be instanced as an object.
			 *
			 * @throws AssertionError when called.
			 */
			private Delegate() {
				throw new AssertionError("No instance for you!");
			}

			/**
			 * Perform the default {@link Map#} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> void clear(Bean<K, V> delegate, Object instance) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");
				//full-beans depends on their entrySets
				delegate.entrySet().clear();
			}

			/**
			 * Perform the default {@link Map#compute(Object, BiFunction)} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param key      key with which the specified value is to be associated.
			 * @param function the function to compute a value.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @return the new value associated with the specified key, or null if none.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} or {@code function} is
			 *                              null.
			 */
			public static <K, V> V compute(Bean<K, V> delegate, Object instance, K key, BiFunction<? super K, ? super V, ? extends V> function) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(function, "function");

				Set<Map.Entry<K, V>> entrySet = delegate.entrySet();

				//looking in existing entries
				for (Map.Entry<K, V> entry : entrySet)
					//target a matching entry
					if (Objects.equals(key, entry.getKey())) {
						//the old value
						V entryValue = entry.getValue();
						//compute the new value
						V newValue = function.apply(key, entryValue);

						if (newValue == null) {
							//the user wants the entry to be removed.
							entrySet.remove(entry);
							return null;
						}

						//set the new value
						entry.setValue(newValue);
						return newValue;
					}

				//compute the value with null, no oldValue to pass!
				V value = function.apply(key, null);

				if (value == null)
					//the user wishes to not change the value
					return null;

				//add a new entry
				entrySet.add(Concrete.getEntry(instance, key, value));
				return value;
			}

			/**
			 * Perform the default {@link Map#computeIfAbsent(Object, Function)} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param key      key with which the specified value is to be associated.
			 * @param function the function to compute a value.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @return the current (existing or computed) value associated with the specified key, or null if the
			 * 		computed value is null.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} {@code function} is null.
			 */
			public static <K, V> V computeIfAbsent(Bean<K, V> delegate, Object instance, K key, Function<? super K, ? extends V> function) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(function, "function");

				Set<Map.Entry<K, V>> entrySet = delegate.entrySet();

				//searching for existing entry
				for (Map.Entry<K, V> entry : entrySet)
					//target a matching entry
					if (Objects.equals(key, entry.getKey())) {
						//the old value
						V entryValue = entry.getValue();

						if (entryValue == null) {
							//compute the new value
							V value = function.apply(key);

							if (value == null)
								//the user wishes to not change the value
								return null;

							//set the new value
							entry.setValue(value);
							return value;
						}

						//the oldValue is present
						return entryValue;
					}

				//compute the new value, since there is no matching entry
				V value = function.apply(key);

				if (value == null)
					//the user wishes to not change the value
					return null;

				//add a new entry
				entrySet.add(Concrete.getEntry(instance, key, value));
				return value;
			}

			/**
			 * Perform the default {@link Map#computeIfPresent(Object, BiFunction)} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param key      key with which the specified value is to be associated.
			 * @param function the function to compute a value.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @return the new value associated with the specified key, or null if none.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} or {@code function} is
			 *                              null.
			 */
			public static <K, V> V computeIfPresent(Bean<K, V> delegate, Object instance, K key, BiFunction<? super K, ? super V, ? extends V> function) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(function, "function");

				Set<Map.Entry<K, V>> entrySet = delegate.entrySet();

				//looking in existing entries
				for (Map.Entry<K, V> entry : entrySet)
					//target a matching entry
					if (Objects.equals(key, entry.getKey())) {
						//the old value
						V entryValue = entry.getValue();

						if (entryValue == null)
							//null value is an absent value
							return null;

						//compute the new value
						V value = function.apply(key, entryValue);

						if (value == null) {
							//the user wishes to remove the entry
							entrySet.remove(entry);
							return null;
						}

						//set the new value
						entry.setValue(value);
						return value;
					}

				//no matching entry
				return null;
			}

			/**
			 * Perform the default {@link Map#containsKey(Object)} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param key      key whose presence in the given {@code delegate} is to be tested.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @return true, if the given {@code delegate} contains a mapping for the specified key.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> boolean containsKey(Bean<K, V> delegate, Object instance, Object key) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");

				//looking in existing entries
				for (Map.Entry entry : delegate.entrySet())
					//target a matching entry
					if (Objects.equals(key, entry.getKey()))
						//matching entry found
						return true;

				//no matching entry has been found
				return false;
			}

			/**
			 * Perform the default {@link Map#containsValue(Object)} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param value    value whose presence in the given {@code delegate} is to be tested.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @return true, if the given {@code delegate} maps one or more keys to the specified value.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> boolean containsValue(Bean<K, V> delegate, Object instance, Object value) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");

				//looking in existing entries
				for (Map.Entry entry : delegate.entrySet())
					//target a matching entry
					if (Objects.equals(value, entry.getValue()))
						//matching entry found
						return true;

				//no matching entry has been found
				return false;
			}

			/**
			 * Perform the default {@link Map#entrySet()} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @return a set view of the mappings contained in the given {@code delegate}.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> DelegateEntrySet<K, V> entrySet(Bean<K, V> delegate, Object instance) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");
				return new DelegateEntrySet(delegate, instance);
			}

			/**
			 * Perform the default {@link Map#equals(Object)} to the given {@code instance}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param object   object to be compared for equality with the given {@code delegate}.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return true, if the specified {@code delegate} is equal to the given {@code delegate}.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> boolean equals(Bean<K, V> delegate, Object instance, Object object) {
				if (delegate == object || instance == object)
					//quick match
					return true;
				if (object == null)
					return false;
				if (object instanceof Map)
					//map match
					return Objects.equals(
							delegate.entrySet(),
							((Map) object).entrySet()
					);

				//delegate match
				Set<K> keys = new HashSet(delegate.keySet());

				for (K key : Raw.<K, Object>keySet(object))
					if (!keys.remove(key))
						return false;

				return keys.isEmpty();
			}

			/**
			 * Perform the default {@link Map#forEach(BiConsumer)} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param consumer The action to be performed for each entry.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} or {@code consumer} is
			 *                              null.
			 */
			public static <K, V> void forEach(Bean<K, V> delegate, Object instance, BiConsumer<? super K, ? super V> consumer) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(consumer, "consumer");

				for (Map.Entry<K, V> entry : delegate.entrySet())
					consumer.accept(entry.getKey(), entry.getValue());
			}

			/**
			 * Perform the default {@link Map#get(Object)} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param key      the key whose associated value is to be returned.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @return the value to which the specified key is mapped, or null if the given {@code delegate} contains no
			 * 		mapping for the key.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> V get(Bean<K, V> delegate, Object instance, Object key) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");

				for (Map.Entry<K, V> entry : delegate.entrySet())
					if (Objects.equals(key, entry.getKey()))
						return entry.getValue();

				return null;
			}

			/**
			 * Perform the default {@link Map#getOrDefault(Object, Object)} to the given {@code delegate}.
			 *
			 * @param delegate     the delegate to perform the method to.
			 * @param instance     the instance the delegate is having.
			 * @param key          the key whose associated value is to be returned.
			 * @param defaultValue the default mapping of the key.
			 * @param <K>          the type of the keys in the given {@code delegate}.
			 * @param <V>          the type of the values in the given {@code delegate}.
			 * @return the value to which the specified key is mapped, or {@code defaultValue} if the given {@code
			 * 		delegate} contains no mapping for the key.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> V getOrDefault(Bean<K, V> delegate, Object instance, Object key, V defaultValue) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");

				for (Map.Entry<K, V> entry : delegate.entrySet())
					if (Objects.equals(key, entry.getKey()))
						return entry.getValue();

				return defaultValue;
			}

			/**
			 * Perform the default {@link Map#hashCode()} to the given {@code instance}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return the hash code value for the given {@code delegate}.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> int hashCode(Bean<K, V> delegate, Object instance) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");

				int hash = 0;

				for (Map.Entry entry : delegate.entrySet())
					hash += entry.hashCode();

				return hash;
			}

			/**
			 * Perform the default {@link Map#isEmpty()} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @return true, if the given {@code delegate} contains no key-value mappings
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> boolean isEmpty(Bean<K, V> delegate, Object instance) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");
				return delegate.entrySet().isEmpty();
			}

			/**
			 * Perform the default {@link Map#keySet()} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @return a set view of the keys contained in the given {@code delegate}.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> DelegateKeySet<K> keySet(Bean<K, V> delegate, Object instance) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");
				return new DelegateKeySet(delegate, instance);
			}

			/**
			 * Perform the default {@link Map#} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param key      key with which the resulting value is to be associated.
			 * @param value    the non-null value to be merged with the existing value associated with the key or, if no
			 *                 existing value or a null value is associated with the key, to be associated with the
			 *                 key.
			 * @param function the function to recompute a value if present.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @return the new value associated with the specified key, or null if no value is associated with the key.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} or {@code function} is
			 *                              null.
			 */
			public static <K, V> V merge(Bean<K, V> delegate, Object instance, K key, V value, BiFunction<? super V, ? super V, ? extends V> function) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(function, "function");

				Set<Map.Entry<K, V>> entrySet = delegate.entrySet();

				for (Map.Entry<K, V> entry : entrySet)
					if (Objects.equals(key, entry.getKey())) {
						//the old value
						V entryValue = entry.getValue();
						//compute the new value
						V newValue = function.apply(entryValue, value);

						//check if the new value is null
						if (newValue == null) {
							//the user wishes to remove the entry
							entrySet.remove(entry);
							return null;
						}

						//set the new value
						entry.setValue(newValue);
						return newValue;
					}

				//compute the new value
				V newValue = function.apply(null, value);

				//check if the new value is null
				if (newValue == null)
					//the user wishes to not change anything
					return null;

				//add a new entry
				entrySet.add(Concrete.getEntry(instance, key, value));
				return value;
			}

			/**
			 * Perform the default {@link Map#put(Object, Object)} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param key      key with which the specified value is to be associated.
			 * @param value    value to be associated with the specified key.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @return the previous value associated with the given {@code key}, or null if there was no mapping for the
			 * 		given {@code key}. (A null return can also indicate that the given {@code delegate} previously
			 * 		associated null with the given {@code key}).
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> V put(Bean<K, V> delegate, Object instance, K key, V value) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");

				Set<Map.Entry<K, V>> entrySet = delegate.entrySet();

				//looking in the existing entries
				for (Map.Entry<K, V> entry : entrySet)
					//target a matching entry
					if (Objects.equals(key, entry.getKey()))
						//set the new value to the matching entry
						return entry.setValue(value);

				//no matching entry, add a new one
				entrySet.add(Concrete.getEntry(instance, key, value));
				return null;
			}

			/**
			 * Perform the default {@link Map#putAll(Map)} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param map      mappings to be stored in the given {@code delegate}.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} or {@code map} is null.
			 */
			public static <K, V> void putAll(Bean<K, V> delegate, Object instance, Map<? extends K, ? extends V> map) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(map, "map");

				Set<K> keys = new HashSet(map.keySet());
				Set<Map.Entry<K, V>> entrySet = delegate.entrySet();

				//looking in the existing entries
				for (Map.Entry<K, V> entry : entrySet) {
					K entryKey = entry.getKey();
					//register that the key has been seen
					if (keys.remove(entryKey))
						//set the new value
						entry.setValue(map.get(entryKey));
				}

				//add all the missing entries
				entrySet.addAll(
						Concrete.getTemporaryEntrySet(instance, keys, (k, v) -> map.get(k))
				);
			}

			/**
			 * Perform the default {@link Map#} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param key      key with which the specified value is to be associated.
			 * @param value    value to be associated with the specified key.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @return the previous value associated with the specified key, or null if there was no mapping for the
			 * 		key. (A null return can also indicate that the given {@code delegate} previously associated null with
			 * 		the key).
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> V putIfAbsent(Bean<K, V> delegate, Object instance, K key, V value) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");

				for (Map.Entry<K, V> entry : delegate.entrySet())
					if (Objects.equals(key, entry.getKey()))
						return entry.getValue();

				return delegate.put(key, value);
			}

			/**
			 * Perform the default {@code readObject} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			@SuppressWarnings("JavaDoc")
			public static <K, V> void readObject(Bean<K, V> delegate, Object instance, ObjectInputStream stream) throws IOException, ClassNotFoundException {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(stream, "stream");

				int length = stream.readInt();
				for (int i = 0; i < length; i++) {
					K key = (K) stream.readObject();
					V value = (V) stream.readObject();

					delegate.put(key, value);
				}

				if (stream.readInt() != 0)
					throw new IOException("Not null-terminated");
			}

			/**
			 * Perform the default {@link Map#remove(Object, Object)} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param key      key with which the specified value is associated.
			 * @param value    value expected to be associated with the specified key.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @return true, if the value was removed.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> boolean remove(Bean<K, V> delegate, Object instance, Object key, Object value) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");

				Set<Map.Entry<K, V>> entrySet = delegate.entrySet();

				for (Map.Entry<K, V> entry : entrySet)
					if (Objects.equals(key, entry.getKey()))
						if (Objects.equals(value, entry.getValue())) {
							entrySet.remove(entry);
							return true;
						} else
							return false;

				return false;
			}

			/**
			 * Perform the default {@link Map#remove(Object)} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param key      key whose mapping is to be removed from the given {@code delegate}.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @return the previous value associated with the given {@code key}, or null if there was no mapping for the
			 * 		given {@code key}.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> V remove(Bean<K, V> delegate, Object instance, Object key) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");

				Iterator<Map.Entry<K, V>> iterator = delegate.entrySet().iterator();

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
			 * Perform the default {@link Map#replace(Object, Object, Object)} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param key      key with which the specified value is associated.
			 * @param oldValue value expected to be associated with the specified key.
			 * @param newValue value to be associated with the specified key.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @return true, if the value was replaced.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> boolean replace(Bean<K, V> delegate, Object instance, K key, V oldValue, V newValue) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");

				for (Map.Entry entry : delegate.entrySet())
					if (Objects.equals(key, entry.getKey()))
						if (Objects.equals(oldValue, entry.getValue())) {
							entry.setValue(newValue);
							return true;
						} else
							return false;

				return false;
			}

			/**
			 * Perform the default {@link Map#replace(Object, Object)} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param key      key with which the specified value is associated.
			 * @param value    value to be associated with the specified key.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @return the previous value associated with the specified key, or null if there was no mapping for the
			 * 		key. (A null return can also indicate that the map previously associated null with the key).
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> V replace(Bean<K, V> delegate, Object instance, K key, V value) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");

				for (Map.Entry<K, V> entry : delegate.entrySet())
					if (Objects.equals(key, entry.getKey()))
						return entry.setValue(value);

				return null;
			}

			/**
			 * Perform the default {@link Map#} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param function the function to apply to each entry.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} or {@code function} is
			 *                              null.
			 */
			public static <K, V> void replaceAll(Bean<K, V> delegate, Object instance, BiFunction<? super K, ? super V, ? extends V> function) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(function, "function");

				for (Map.Entry<K, V> entry : delegate.entrySet())
					entry.setValue(function.apply(entry.getKey(), entry.getValue()));
			}

			/**
			 * Perform the default {@link Map#size()} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @return the number of key-value mappings in the given {@code delegate}.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> int size(Bean<K, V> delegate, Object instance) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");
				return delegate.entrySet().size();
			}

			/**
			 * Perform the default {@link Map#toString()} to the given {@code instance}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return a string representation of the given {@code delegate}.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> String toString(Bean<K, V> delegate, Object instance) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");

				Iterator<Map.Entry<K, V>> iterator = delegate.entrySet().iterator();

				if (iterator.hasNext()) {
					StringBuilder builder = new StringBuilder("{");

					while (iterator.hasNext()) {
						Map.Entry entry = iterator.next();
						Object key = entry.getKey();
						Object value = entry.getValue();

						String keyString = key == instance ? "(this Bean)" : String.valueOf(key);
						String valueString = value == instance ? "(this Bean)" : String.valueOf(value);

						builder.append(keyString)
								.append('=')
								.append(valueString);

						if (iterator.hasNext())
							builder.append(", ");
					}

					return builder.append("}").toString();
				}

				return "{}";
			}

			/**
			 * Perform the default {@link Map#values()} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @return a collection view of the values contained in the given {@code delegate}.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			public static <K, V> DelegateValues<V> values(Bean<K, V> delegate, Object instance) {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");
				return new DelegateValues(delegate, instance);
			}

			/**
			 * Perform the default {@code writeObject} to the given {@code delegate}.
			 *
			 * @param delegate the delegate to perform the method to.
			 * @param instance the instance the delegate is having.
			 * @param <K>      the type of the keys in the given {@code delegate}.
			 * @param <V>      the type of the values in the given {@code delegate}.
			 * @throws NullPointerException if the given {@code delegate} or {@code instance} is null.
			 */
			@SuppressWarnings("JavaDoc")
			public static <K, V> void writeObject(Bean<K, V> delegate, Object instance, ObjectOutputStream stream) throws IOException {
				Objects.requireNonNull(delegate, "delegate");
				Objects.requireNonNull(instance, "instance");

				Set<Map.Entry<K, V>> entrySet = delegate.entrySet();

				stream.writeInt(entrySet.size());
				for (Map.Entry entry : entrySet) {
					stream.writeObject(entry.getKey());
					stream.writeObject(entry.getValue());
				}

				//null-termination
				stream.writeInt(0);
			}
		}

		/**
		 * The methods for any instance that considered to be a {@link Bean}.
		 */
		public static final class Raw {
			/**
			 * This is an util class and must not be instanced as an object.
			 *
			 * @throws AssertionError when called.
			 */
			private Raw() {
				throw new AssertionError("No instance for you!");
			}

			/**
			 * Perform the default {@link Map#clear()} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @throws NullPointerException          if the given {@code instance} is null.
			 * @throws UnsupportedOperationException if the given {@code instance} has any property-field.
			 */
			public static <K, V> void clear(Object instance) {
				Objects.requireNonNull(instance, "instance");
				if (!Concrete.getFields(instance).isEmpty())
					throw new UnsupportedOperationException("clear");
			}

			/**
			 * Perform the default {@link Map#compute(Object, BiFunction)} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param key      key with which the specified value is to be associated.
			 * @param function the function to compute a value.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return the new value associated with the specified key, or null if none.
			 * @throws NullPointerException          if the given {@code instance} or {@code function} is null.
			 * @throws UnsupportedOperationException if the given {@code function} returned null, Or if the given {@code
			 *                                       instance} don't have the given {@code key} (bean-wise).
			 */
			public static <K, V> V compute(Object instance, K key, BiFunction<? super K, ? super V, ? extends V> function) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(function, "function");

				for (Field field : Concrete.getFields(instance))
					for (K fieldKey : PrivateConcrete.<K>getKeys(field))
						if (Objects.equals(key, fieldKey)) {
							V value = function.apply(key, PrivateConcrete.getValue(instance, field));

							if (value == null)
								throw new UnsupportedOperationException("remove");

							PrivateConcrete.setValue(instance, field, value);
							return value;
						}

				throw new UnsupportedOperationException("put: " + key);
			}

			/**
			 * Perform the default {@link Map#computeIfAbsent(Object, Function)} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param key      key with which the specified value is to be associated.
			 * @param function the function to compute a value.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return the current (existing or computed) value associated with the specified key, or null if the
			 * 		computed value is null.
			 * @throws NullPointerException          if the given {@code instance} or {@code function} is null.
			 * @throws UnsupportedOperationException if the given {@code instance} don't have the given {@code key}.
			 */
			public static <K, V> V computeIfAbsent(Object instance, K key, Function<? super K, ? extends V> function) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(function, "function");

				for (Field field : Concrete.getFields(instance))
					if (PrivateConcrete.getValue(instance, field) == null)
						for (K fieldKey : PrivateConcrete.<K>getKeys(field))
							if (Objects.equals(key, fieldKey)) {
								V value = function.apply(key);

								if (value != null)
									PrivateConcrete.setValue(instance, field, function.apply(key));

								return value;
							}

				throw new UnsupportedOperationException("put: " + key);
			}

			/**
			 * Perform the default {@link Map#computeIfPresent(Object, BiFunction)} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param key      key with which the specified value is to be associated.
			 * @param function the function to compute a value.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return the new value associated with the specified key, or null if none.
			 * @throws NullPointerException          if the given {@code instance} or {@code function} is null.
			 * @throws UnsupportedOperationException if the given {@code function} returned null.
			 */
			public static <K, V> V computeIfPresent(Object instance, K key, BiFunction<? super K, ? super V, ? extends V> function) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(function, "function");

				for (Field field : Concrete.getFields(instance)) {
					V fieldValue = PrivateConcrete.getValue(instance, field);

					if (fieldValue != null)
						for (K fieldKey : PrivateConcrete.<K>getKeys(field))
							if (Objects.equals(key, fieldKey)) {
								V value = function.apply(key, fieldValue);

								if (value == null)
									throw new UnsupportedOperationException("remove");

								PrivateConcrete.setValue(instance, field, value);
								return value;
							}
				}

				return null;
			}

			/**
			 * Perform the default {@link Map#containsKey(Object)} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param key      key whose presence in the given {@code instance} is to be tested.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return true, if the given {@code instance} contains a mapping for the specified key.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			public static <K, V> boolean containsKey(Object instance, Object key) {
				Objects.requireNonNull(instance, "instance");

				for (Field field : Concrete.getFields(instance))
					for (Object fieldKey : PrivateConcrete.getKeys(field))
						if (Objects.equals(key, fieldKey))
							return true;

				return false;
			}

			/**
			 * Perform the default {@link Map#containsValue(Object)} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param value    value whose presence in the given {@code instance} is to be tested.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return true, if the given {@code instance} maps one or more keys to the specified value.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			public static <K, V> boolean containsValue(Object instance, Object value) {
				Objects.requireNonNull(instance, "instance");

				for (Field field : Concrete.getFields(instance))
					if (Objects.equals(value, PrivateConcrete.getValue(instance, field)))
						return true;

				return false;
			}

			/**
			 * Perform the default {@link Map#entrySet()} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return a set view of the mappings contained in the given {@code instance}.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			public static <K, V> RawEntrySet<K, V> entrySet(Object instance) {
				Objects.requireNonNull(instance, "instance");
				return new RawEntrySet(instance);
			}

			/**
			 * Perform the default {@link Map#equals(Object)} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param object   object to be compared for equality with this map.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return true, if the specified object is equal to this map.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			public static <K, V> boolean equals(Object instance, Object object) {
				Objects.requireNonNull(instance, "instance");
				if (instance == object)
					return true;
				if (object == null)
					return false;
				if (object instanceof Map)
					//map match
					return Objects.equals(
							Raw.entrySet(instance),
							((Map) object).entrySet()
					);

				//bean match
				Set<K> keys = new HashSet(Raw.keySet(instance));

				for (K key : Raw.<K, Object>keySet(object))
					if (!keys.remove(key))
						return false;

				return keys.isEmpty();
			}

			/**
			 * Perform the default {@link Map#forEach(BiConsumer)} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param consumer the action to be performed for each entry.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @throws NullPointerException if the given {@code instance} or {@code consumer} is null.
			 */
			public static <K, V> void forEach(Object instance, BiConsumer<? super K, ? super V> consumer) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(consumer, "consumer");

				for (Field field : Concrete.getFields(instance)) {
					V value = PrivateConcrete.getValue(instance, field);

					for (K key : PrivateConcrete.<K>getKeys(field))
						consumer.accept(key, value);
				}
			}

			/**
			 * Perform the default {@link Map#get(Object)} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param key      the key whose associated value is to be returned.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return the value to which the specified key is mapped, or {@code null} if the given {@code instance}
			 * 		contains no mapping for the key.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			public static <K, V> V get(Object instance, Object key) {
				Objects.requireNonNull(instance, "instance");

				for (Field field : Concrete.getFields(instance))
					for (Object fieldKey : PrivateConcrete.getKeys(field))
						if (Objects.equals(key, fieldKey))
							return PrivateConcrete.getValue(instance, field);

				return null;
			}

			/**
			 * Perform the default {@link Map#getOrDefault(Object, Object)} to the given {@code instance}.
			 *
			 * @param instance     the instance the bean is having.
			 * @param key          the key whose associated value is to be returned.
			 * @param defaultValue the default mapping of the key.
			 * @param <K>          the type of the keys in the given {@code instance}.
			 * @param <V>          the type of the values in the given {@code instance}.
			 * @return the value to which the specified key is mapped, or {@code defaultValue} if the given {@code
			 * 		instance} contains no mapping for the key.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			public static <K, V> V getOrDefault(Object instance, K key, V defaultValue) {
				Objects.requireNonNull(instance, "instance");

				for (Field field : Concrete.getFields(instance))
					for (K fieldKey : PrivateConcrete.<K>getKeys(field))
						if (Objects.equals(key, fieldKey))
							return PrivateConcrete.getValue(instance, field);

				return defaultValue;
			}

			/**
			 * Perform the default {@link Map#hashCode()} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return the hash code value for the given {@code instance}.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			public static <K, V> int hashCode(Object instance) {
				Objects.requireNonNull(instance, "instance");

				int hashCode = 0;

				for (Field field : Concrete.getFields(instance)) {
					Object value = PrivateConcrete.getValue(instance, field);
					int valueHash = Objects.hashCode(value);

					for (K key : PrivateConcrete.<K>getKeys(field)) {
						int keyHash = Objects.hashCode(key);

						hashCode += keyHash ^ valueHash;
					}
				}

				return hashCode;
			}

			/**
			 * Perform the default {@link Map#isEmpty()} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return true, if the given {@code instance} contains no key-value mappings.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			public static <K, V> boolean isEmpty(Object instance) {
				Objects.requireNonNull(instance, "instance");
				return Concrete.getFields(instance).isEmpty();
			}

			/**
			 * Perform the default {@link Map#keySet()} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return a set view of the keys contained in the given {@code instance}.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			public static <K, V> RawKeySet<K> keySet(Object instance) {
				Objects.requireNonNull(instance, "instance");
				return new RawKeySet(instance);
			}

			/**
			 * Perform the default {@link Map#merge(Object, Object, BiFunction)} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param key      key with which the resulting value is to be associated.
			 * @param value    the non-null value to be merged with the existing value associated with the key or, if no
			 *                 existing value or a null value is associated with the key, to be associated with the
			 *                 key.
			 * @param function the function to recompute a value if present.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return the new value associated with the specified key, or null if no value is associated with the key.
			 * @throws NullPointerException          if the given {@code instance} or {@code function} is null.
			 * @throws UnsupportedOperationException if the given {@code function} returned null, Or if the given {@code
			 *                                       instance} don't have the given {@code key}.
			 */
			public static <V, K> V merge(Object instance, K key, V value, BiFunction<? super V, ? super V, ? extends V> function) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(function, "function");

				for (Field field : Concrete.getFields(instance))
					for (K fieldKey : PrivateConcrete.<K>getKeys(field))
						if (Objects.equals(key, fieldKey)) {
							V fieldValue = PrivateConcrete.getValue(instance, field);
							V newValue = fieldValue == null ? value : function.apply(fieldValue, value);

							if (newValue == null)
								throw new UnsupportedOperationException("remove");

							PrivateConcrete.setValue(instance, field, newValue);
							return newValue;
						}

				throw new UnsupportedOperationException("put: " + key);
			}

			/**
			 * Perform the default {@link Map#put(Object, Object)} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param key      key with which the specified value is to be associated.
			 * @param value    value to be associated with the specified key.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return the previous value associated with given {@code key}, or null if there was no mapping for the
			 * 		given {@code key}. (A null return can also indicate that the given {@code instance} previously
			 * 		associated null with the given {@code key}).
			 * @throws NullPointerException          if the given {@code instance} is null.
			 * @throws UnsupportedOperationException if the given {@code instance} don't have the given {@code key}.
			 */
			public static <K, V> V put(Object instance, K key, V value) {
				Objects.requireNonNull(instance, "instance");

				for (Field field : Concrete.getFields(instance))
					for (Object fieldKey : PrivateConcrete.getKeys(field))
						if (Objects.equals(key, fieldKey)) {
							V oldValue = PrivateConcrete.getValue(instance, field);
							PrivateConcrete.setValue(instance, field, value);
							return oldValue;
						}

				throw new UnsupportedOperationException("put: " + key);
			}

			/**
			 * Perform the default {@link Map#putAll(Map)} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param map      mappings to be stored in the given {@code instance}.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @throws NullPointerException          if the given {@code instance} or {@code map} is null.
			 * @throws UnsupportedOperationException if the given {@code map} has keys that the given {@code instance}
			 *                                       does not have.
			 */
			public static <K, V> void putAll(Object instance, Map<? extends K, ? extends V> map) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(map, "map");

				Set keySet = new HashSet(map.keySet());

				for (Field field : Concrete.getFields(instance)) {
					//no need to reassign the same field
					boolean put = true;

					//first key in the Property annotation has more priority that other keys!
					for (K key : PrivateConcrete.<K>getKeys(field))
						if (keySet.remove(key) && put) {
							//found a key that can be put!
							//only if the field haven't been set by previous key.
							PrivateConcrete.setValue(instance, field, map.get(key));
							//block next assignments in this field!
							put = false;
						}
				}

				//the bean can't handle the map! too large for it.
				if (!keySet.isEmpty())
					throw new UnsupportedOperationException("putAll: " + map);
			}

			/**
			 * Perform the default {@link Map#putIfAbsent(Object, Object)} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param key      key with which the specified value is to be associated.
			 * @param value    value to be associated with the specified key.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return the previous value associated with the specified key, or null if there was no mapping for the
			 * 		key. (A null return can also indicate that the given {@code instance} previously associated null with
			 * 		the key).
			 * @throws NullPointerException          if the given {@code instance} is null.
			 * @throws UnsupportedOperationException if the given {@code instance} don't have the given {@code key}.
			 */
			public static <K, V> V putIfAbsent(Object instance, K key, V value) {
				Objects.requireNonNull(instance, "instance");

				for (Field field : Concrete.getFields(instance))
					if (PrivateConcrete.getValue(instance, field) == null)
						for (K fieldKey : PrivateConcrete.<K>getKeys(field))
							if (Objects.equals(key, fieldKey)) {
								V oldValue = PrivateConcrete.getValue(instance, field);
								PrivateConcrete.setValue(instance, field, value);
								return oldValue;
							}

				throw new UnsupportedOperationException("put: " + key);
			}

			/**
			 * Perform the default {@code readObject} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			@SuppressWarnings("JavaDoc")
			public static <K, V> void readObject(Object instance, ObjectInputStream stream) throws IOException, ClassNotFoundException {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(stream, "stream");

				Set<Field> fields = Concrete.getFields(instance);

				int length = stream.readInt();
				for0:
				for (int i = 0; i < length; i++) {
					K key = (K) stream.readObject();
					V value = (V) stream.readObject();

					for (Field field : fields)
						if (Objects.equals(key, field.getName())) {
							PrivateConcrete.setValue(instance, field, value);
							continue for0;
						}

					throw new IllegalArgumentException("Invalid key: " + key);
				}

				if (stream.readInt() != 0)
					throw new IOException("Not null-terminated");
			}

			/**
			 * Perform the default {@link Map#remove(Object, Object)} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param key      key with which the specified value is associated.
			 * @param value    value expected to be associated with the specified key.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return true, if the value was removed.
			 * @throws NullPointerException          if the given {@code instance} is null.
			 * @throws UnsupportedOperationException if the given {@code key} is associated to the given {@code value}
			 *                                       in the given {@code instance}.
			 */
			public static <K, V> boolean remove(Object instance, K key, V value) {
				Objects.requireNonNull(instance, "instance");

				for (Field field : Concrete.getFields(instance)) {
					V fieldValue = PrivateConcrete.getValue(instance, field);

					for (K fieldKey : PrivateConcrete.<K>getKeys(field))
						if (Objects.equals(key, fieldKey))
							if (Objects.equals(value, fieldValue))
								throw new UnsupportedOperationException("remove");
							else
								return false;
				}

				return false;
			}

			/**
			 * Perform the default {@link Map#remove(Object)} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param key      key whose mapping is to be removed from the map
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return the previous value associated with the given {@code key}, or null if there was no mapping for the
			 * 		given {@code key}.
			 * @throws NullPointerException          if the given {@code instance} is null.
			 * @throws UnsupportedOperationException if the given {@code instance} has the given {@code key}.
			 */
			public static <K, V> V remove(Object instance, Object key) {
				Objects.requireNonNull(instance, "instance");

				for (Field field : Concrete.getFields(instance))
					for (K fieldKey : PrivateConcrete.<K>getKeys(field))
						if (Objects.equals(key, fieldKey))
							throw new UnsupportedOperationException("remove");

				return null;
			}

			/**
			 * Perform the default {@link Map#replace(Object, Object)} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param key      key with which the specified value is associated.
			 * @param value    value to be associated with the specified key.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return the previous value associated with the specified key, or null if there was no mapping for the
			 * 		key. (A null return can also indicate that the map previously associated null with the key).
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			public static <K, V> V replace(Object instance, K key, V value) {
				Objects.requireNonNull(instance, "instance");

				for (Field field : Concrete.getFields(instance))
					for (K fieldKey : PrivateConcrete.<K>getKeys(field))
						if (Objects.equals(key, fieldKey)) {
							V oldValue = PrivateConcrete.getValue(instance, field);
							PrivateConcrete.setValue(instance, field, value);
							return oldValue;
						}

				return null;
			}

			/**
			 * Perform the default {@link Map#replace(Object, Object, Object)} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param key      key with which the specified value is associated.
			 * @param oldValue value expected to be associated with the specified key.
			 * @param newValue value to be associated with the specified key.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return {@code true} if the value was replaced.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			public static <K, V> boolean replace(Object instance, K key, V oldValue, V newValue) {
				Objects.requireNonNull(instance, "instance");

				for (Field field : Concrete.getFields(instance)) {
					V fieldValue = PrivateConcrete.getValue(instance, field);

					for (K fieldKey : PrivateConcrete.<K>getKeys(field))
						if (Objects.equals(key, fieldKey))
							if (Objects.equals(oldValue, fieldValue)) {
								PrivateConcrete.setValue(instance, field, newValue);
								return true;
							} else
								return false;
				}

				return false;
			}

			/**
			 * Perform the default {@link Map#replaceAll(BiFunction)} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param function the function to apply to each entry.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @throws NullPointerException if the given {@code instance} or {@code function} is null.
			 */
			public static <K, V> void replaceAll(Object instance, BiFunction<? super K, ? super V, ? extends V> function) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(function, "function");

				for (Field field : Concrete.getFields(instance)) {
					V value = PrivateConcrete.getValue(instance, field);

					//redundant reassignment to the field will be skipped
					for (K key : PrivateConcrete.<K>getKeys(field))
						value = function.apply(key, value);

					PrivateConcrete.setValue(instance, field, value);
				}
			}

			/**
			 * Perform the default {@link Map#size()} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return the number of key-value mappings in the given {@code instance}.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			public static <K, V> int size(Object instance) {
				Objects.requireNonNull(instance, "instance");

				int size = 0;
				for (Field field : Concrete.getFields(instance))
					//don't forget "zero keys = singular key that is the name of the field"
					size += Math.max(1, field.getAnnotation(Property.class).keys().length);

				return size;
			}

			/**
			 * Perform the default {@link Map#toString()} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return a string representation of the given {@code instance}.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			public static <K, V> String toString(Object instance) {
				Objects.requireNonNull(instance, "instance");

				Iterator<Field> iterator = Concrete.getFields(instance).iterator();

				if (iterator.hasNext()) {
					StringBuilder builder = new StringBuilder("{");

					while (iterator.hasNext()) {
						Field field = iterator.next();
						Object value = PrivateConcrete.getValue(instance, field);
						String valueString = value == instance ? "(this Bean)" : String.valueOf(value);

						Iterator<K> keys = PrivateConcrete.<K>getKeys(field).iterator();

						while (keys.hasNext()) {
							Object key = iterator.next();
							String keyString = key == instance ? "(this Bean)" : String.valueOf(key);

							builder.append(keyString)
									.append('=')
									.append(valueString);

							if (keys.hasNext())
								builder.append(", ");
						}

						if (iterator.hasNext())
							builder.append(", ");
					}

					return builder.append("}").toString();
				}

				return "{}";
			}

			/**
			 * Perform the default {@link Map#values()} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @return a collection view of the values contained in the given {@code instance}.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			public static <K, V> RawValues<V> values(Object instance) {
				Objects.requireNonNull(instance, "instance");
				return new RawValues(instance);
			}

			/**
			 * Perform the default {@code writeObject} to the given {@code instance}.
			 *
			 * @param instance the instance the bean is having.
			 * @param <K>      the type of the keys in the given {@code instance}.
			 * @param <V>      the type of the values in the given {@code instance}.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			@SuppressWarnings("JavaDoc")
			public static <K, V> void writeObject(Object instance, ObjectOutputStream stream) throws IOException {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(stream, "stream");

				stream.writeInt(Raw.size(instance));

				for (Field field : Concrete.getFields(instance)) {
					Object value = PrivateConcrete.getValue(instance, field);

					for (K key : PrivateConcrete.<K>getKeys(field)) {
						stream.writeObject(key);
						stream.writeObject(value);
					}
				}

				//null-termination
				stream.writeInt(0);
			}
		}

		/**
		 * The concrete methods that is dangerous to be public.
		 */
		private static final class PrivateConcrete {
			/**
			 * This is an util class. And must not be instanced as an object.
			 *
			 * @throws AssertionError when called.
			 */
			private PrivateConcrete() {
				throw new AssertionError("No instance for you!");
			}

			/**
			 * Get the converter specified in the given {@code field}'s {@link Property} annotation.
			 *
			 * @param field to get its converter.
			 * @return the converter specified in the given {@code field}'s {@link Property} annotation.
			 * @throws NullPointerException if the given {@code field} is null.
			 */
			private static Converter getConverter(Field field) {
				Objects.requireNonNull(field, "field");
				return Where.Util.getValue(field.getAnnotation(Property.class).converter());
			}

			/**
			 * a {@link PropertyEntry} for a field that have the given key in the given instance. Or a {@link
			 * PropertylessEntry} if there is no such field having the given {@code key} in the given {@code instance}.
			 * This method don't trust the given {@code key} for a {@link PropertyEntry}. The given {@code key} will not
			 * be used to construct a {@link PropertyEntry}. The key constructed to match the given {@code key} will be
			 * used instead.
			 *
			 * @param instance the instance that the returned entry is for a field in it.
			 * @param key      the key that the returned entry is having.
			 * @param <K>      the type of the key of the returned entry.
			 * @param <V>      the type of the value of the returned entry.
			 * @return a {@link PropertyEntry} for a field that have the given key in the given instance. Or a {@link
			 *        PropertylessEntry} if there is no such field having the given {@code key} in the given {@code
			 * 		instance}.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			private static <K, V> Entry<K, V> getEntry(Object instance, K key) {
				Objects.requireNonNull(instance, "instance");

				for (Field field : Concrete.getFields(instance))
					for (K fieldKey : PrivateConcrete.<K>getKeys(field))
						if (Objects.equals(key, fieldKey))
							return new PropertyEntry(instance, field, fieldKey);

				return new PropertylessEntry(key);
			}

			/**
			 * a {@link PropertyEntry} for a field that have the given key in the given instance. Or a {@link
			 * PropertylessEntry} if there is no such field having the given {@code key} in the given {@code instance}.
			 * Then set the value of the given {@code field} to the given {@code value}. This method don't trust the
			 * given {@code key} for a {@link PropertyEntry}. The given {@code key} will not be used to construct a
			 * {@link PropertyEntry}. The key constructed to match the given {@code key} will be used instead.
			 *
			 * @param instance the instance that the returned entry is for a field in it.
			 * @param key      the key that the returned entry is having.
			 * @param value    the value to be set to the given {@code field}.
			 * @param <K>      the type of the key of the returned entry.
			 * @param <V>      the type of the value of the returned entry.
			 * @return a {@link PropertyEntry} for a field that have the given key in the given instance. Or a {@link
			 *        PropertylessEntry} if there is no such field having the given {@code key} in the given {@code
			 * 		instance}.
			 * @throws NullPointerException if the given {@code instance} is null.
			 */
			private static <K, V> Entry<K, V> getEntry(Object instance, K key, V value) {
				Objects.requireNonNull(instance, "instance");

				for (Field field : Concrete.getFields(instance))
					for (K fieldKey : PrivateConcrete.<K>getKeys(field))
						if (Objects.equals(key, fieldKey))
							return new PropertyEntry(instance, field, fieldKey, value);

				return new PropertylessEntry(key, value);
			}

			/**
			 * Get a set of the keys the given {@code property} does have.
			 *
			 * @param property the field to return the keys that it have.
			 * @param <K>      the type of the keys returned.
			 * @return a set of the keys the given {@code property} does have.
			 * @throws NullPointerException if the given {@code property} is null.
			 */
			private static <K> Set<K> getKeys(Field property) {
				Objects.requireNonNull(property, "property");
				return new Property.KeySet(property);
			}

			/**
			 * Get a {@link PropertyEntry} with the key at with the {@code key} index in the given {@code field}.
			 *
			 * @param instance the instance that the returned entry is for a field in it.
			 * @param field    the field where the returned entry is reading/writing its value.
			 * @param key      the index of the key in the field to be the key of the returned entry.
			 * @param <K>      the type of the key of the returned entry.
			 * @param <V>      the type of the value of the returned entry.
			 * @return a {@link PropertyEntry} with the key at with the {@code key} index in the given {@code field}
			 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
			 * @throws IllegalArgumentException if the given {@code field} don't have a key at the given {@code key}
			 *                                  index.
			 */
			private static <K, V> PropertyEntry<K, V> getPropertyEntry(Object instance, Field field, int key) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(field, "field");
				if (key < 0)
					throw new IllegalArgumentException("negative key index");

				Recipe[] recipes = field.getAnnotation(Property.class).keys();

				if (key < recipes.length)
					//if the field have custom keys, and the given key is within the bounds
					return new PropertyEntry(instance, field, recipes[key]);
				if (key == 0)
					//if the field don't have specified keys, and the given key is 0
					return new PropertyEntry(instance, field, field.getName());

				throw new IllegalArgumentException("Field does not have such key index: " + key);
			}

			/**
			 * Get a {@link PropertyEntry} with the key at with the {@code key} index in the given {@code field}. Then
			 * set the value of the given {@code field} to the given {@code value}.
			 *
			 * @param instance the instance that the returned entry is for a field in it.
			 * @param field    the field where the returned entry is reading/writing its value.
			 * @param key      the index of the key in the field to be the key of the returned entry.
			 * @param value    the value to be set to the given {@code field}.
			 * @param <K>      the type of the key of the returned entry.
			 * @param <V>      the type of the value of the returned entry.
			 * @return a {@link PropertyEntry} with the key at with the {@code key} index in the given {@code field}
			 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
			 * @throws IllegalArgumentException if the given {@code field} don't have a key at the given {@code key}
			 *                                  index.
			 */
			private static <K, V> PropertyEntry<K, V> getPropertyEntry(Object instance, Field field, int key, V value) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(field, "field");
				if (key < 0)
					throw new IllegalArgumentException("negative key index");

				Recipe[] recipes = field.getAnnotation(Property.class).keys();

				if (key < recipes.length)
					//if the field have custom keys, and the given key is within the bounds
					return new PropertyEntry(instance, field, recipes[key], value);
				if (key == 0)
					//if the field don't have specified keys, and the given key is 0
					return new PropertyEntry(instance, field, field.getName(), value);

				throw new IllegalArgumentException("Field does not have such key index: " + key);
			}

			/**
			 * Get a {@link PropertyEntry} that have the key from the given {@code key} recipe.
			 *
			 * @param instance the instance that the returned entry is for a field in it.
			 * @param field    the field where the returned entry is reading/writing its value.
			 * @param key      the recipe to constructed the the key of the returned entry.
			 * @param <K>      the type of the key of the returned entry.
			 * @param <V>      the type of the value of the returned entry.
			 * @return a {@link PropertyEntry} that have the key from the given {@code key} recipe.
			 * @throws NullPointerException if the given {@code instance} or {@code field} or {@code key} is null.
			 */
			private static <K, V> PropertyEntry<K, V> getPropertyEntry(Object instance, Field field, Recipe key) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(field, "field");
				Objects.requireNonNull(key, "key");
				return new PropertyEntry(instance, field, key);
			}

			/**
			 * Get a {@link PropertyEntry} that have the key from the given {@code key} recipe. Then set the value of
			 * the given {@code field} to the given {@code value}.
			 *
			 * @param instance the instance that the returned entry is for a field in it.
			 * @param field    the field where the returned entry is reading/writing its value.
			 * @param key      the recipe to constructed the the key of the returned entry.
			 * @param value    the value to be set to the given {@code field}.
			 * @param <K>      the type of the key of the returned entry.
			 * @param <V>      the type of the value of the returned entry.
			 * @return a {@link PropertyEntry} that have the key from the given {@code key} recipe.
			 * @throws NullPointerException if the given {@code instance} or {@code field} or {@code key} is null.
			 */
			private static <K, V> PropertyEntry<K, V> getPropertyEntry(Object instance, Field field, Recipe key, V value) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(field, "field");
				Objects.requireNonNull(key, "key");
				return new PropertyEntry(instance, field, key, value);
			}

			/**
			 * Get a {@link PropertyEntry} with the given {@code key} for the given {@code field}.
			 *
			 * @param instance the instance that the returned entry is for a field in it.
			 * @param field    the field where the returned entry is reading/writing its value.
			 * @param key      the key that the returned entry is having.
			 * @param <K>      the type of the key of the returned entry.
			 * @param <V>      the type of the value of the returned entry.
			 * @return a {@link PropertyEntry} with the given {@code key} for the given {@code field}.
			 * @throws NullPointerException if the given {@code instance} or {@code field} is null.
			 */
			private static <K, V> PropertyEntry<K, V> getPropertyEntry(Object instance, Field field, K key) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(field, "field");
				return new PropertyEntry(instance, field, key);
			}

			/**
			 * Get a {@link PropertyEntry} with the given {@code key} for the given {@code field}.
			 *
			 * @param instance the instance that the returned entry is for a field in it.
			 * @param field    the field where the returned entry is reading/writing its value.
			 * @param key      the key that the returned entry is having.
			 * @param value    the value to be set to the given {@code field}.
			 * @param <K>      the type of the key of the returned entry.
			 * @param <V>      the type of the value of the returned entry.
			 * @return a {@link PropertyEntry} with the given {@code key} for the given {@code field}.
			 * @throws NullPointerException if the given {@code instance} or {@code field} is null.
			 */
			private static <K, V> PropertyEntry<K, V> getPropertyEntry(Object instance, Field field, K key, V value) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(field, "field");
				return new PropertyEntry(instance, field, key, value);
			}

			/**
			 * Get a {@link PropertylessEntry} with the given {@code key}.
			 *
			 * @param key the key that the returned entry is having.
			 * @param <K> the type of the key of the returned entry.
			 * @param <V> the type of the value of the returned entry.
			 * @return a {@link PropertylessEntry} with the given {@code key}.
			 */
			private static <K, V> PropertylessEntry<K, V> getPropertylessEntry(K key) {
				return new PropertylessEntry(key);
			}

			/**
			 * Get a {@link PropertylessEntry} with the given {@code key}. Then set its value to the given {@code
			 * value}.
			 *
			 * @param key   the key that the returned entry is having.
			 * @param value the initial value the returned entry will have.
			 * @param <K>   the type of the key of the returned entry.
			 * @param <V>   the type of the value of the returned entry.
			 * @return a {@link PropertylessEntry} with the given {@code key}.
			 */
			private static <K, V> PropertylessEntry<K, V> getPropertylessEntry(K key, V value) {
				return new PropertylessEntry(key, value);
			}

			/**
			 * Get the type of the given {@code field}. The type means the {@link Clazz} specified for the values to be
			 * store at the given {@code field}.
			 *
			 * @param field the field that the returned type is required for a value to be stored to it using a bean.
			 * @param <V>   the type of the returned clazz.
			 * @return the type of the given {@code field}.
			 * @throws NullPointerException if the given {@code field} is null.
			 */
			private static <V> Clazz<V> getType(Field field) {
				Objects.requireNonNull(field, "field");

				Type[] type = field.getAnnotation(Property.class).type();

				if (type.length == 0)
					return (Clazz<V>) Clazz.of(field.getType());
				if (type.length == 1)
					return (Clazz<V>) Type.Util.get(type[0]);

				throw new IllegalMetaException("Bean.Property.type().length > 1");
			}

			/**
			 * Get the value stored at the given {@code field} in the given {@code instance}.
			 *
			 * @param instance the instance that the returned value is stored at in the given {@code field}.
			 * @param field    the field that the returned value is stored at in the given {@code instance}.
			 * @param <V>      the type of the returned value.
			 * @return the value stored at the given {@code field} in the given {@code instance}.
			 * @throws NullPointerException if the given {@code instance} or {@code field} is null.
			 */
			private static <V> V getValue(Object instance, Field field) {
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
			 * Set the value stored at the given {@code field} on the given {@code instance} to the given {@code
			 * value}.
			 *
			 * @param instance the instance for the given {@code value} to be set in at the given {@code field}.
			 * @param value    the value to be set in the given {@code instance} at the given {@code field}.
			 * @param field    the filed for the given {@code value} to be set at in the given {@code instance}.
			 * @param <V>      the type of the given {@code value}.
			 * @throws NullPointerException if the given {@code instance} or {@code field} is null.
			 */
			private static <V> void setValue(Object instance, Field field, V value) {
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(field, "field");

				Property property = field.getAnnotation(Property.class);
				Where where = property.converter();
				Converter converter = Where.Util.getValue(where);
				Clazz<V> type = PrivateConcrete.getType(field);

				PrivateConcrete.setValue(instance, field, value, converter, type);
			}

			/**
			 * Set the value stored at the given {@code field} on the given {@code instance} to the given {@code value}
			 * Using the given parameters.
			 *
			 * @param <V>       the type of the value
			 * @param instance  to set the value to
			 * @param field     that holds the value
			 * @param value     to be set
			 * @param converter the converter of the field
			 * @param type      the type of the field
			 * @throws NullPointerException     if the given {@code field} or {@code instance} or {@code converter} or
			 *                                  {@code type} is null.
			 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}. Or if
			 *                                  the given {@code value} can't be set to the given {@code field}.
			 * @throws IllegalAccessError       if the given {@code field} object is enforcing Java language access
			 *                                  control, and the underlying field is either inaccessible or final.
			 */
			private static <V> void setValue(Object instance, Field field, V value, Converter converter, Clazz<V> type) {
				Objects.requireNonNull(field, "field");
				Objects.requireNonNull(instance, "instance");
				Objects.requireNonNull(type, "type");
				try {
					field.setAccessible(true);

					int modifiers = field.getModifiers();
					Clazz<V> valueType = Clazz.Generate.from(value);

					if (Modifier.isFinal(modifiers)) {
						//skip unnecessary reassignment by convert remotely
						V old = (V) field.get(instance);

						if (converter.convert(new ConvertToken(value, old, valueType, type)) != old)
							//when the converter can't convert to the provided instance, it will replace it with a new one
							throw new IllegalAccessError("can't assign the value to the final field: " + field);
					}

					V newValue = converter.convert(new ConvertToken<>(value, value, valueType, type));
					//
					field.set(instance, newValue);
				} catch (IllegalAccessException e) {
					IllegalAccessError error = new IllegalAccessError();
					error.initCause(e);
					throw error;
				}
			}
		}
	}

	/**
	 * An entry managing {@link Property} fields.
	 *
	 * @param <K> the type of the key in the entry.
	 * @param <V> the type of the value in the entry.
	 */
	final class PropertyEntry<K, V> extends Entry<K, V> {
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
		 * Construct a new field entry that edits the given instance at the given field. Then set the value of the given
		 * {@code field} to the given {@code value}.
		 *
		 * @param instance the instance for the constructed entry to edit.
		 * @param field    the field where the constructed entry should edit in the given {@code instance}.
		 * @param recipe   the recipe to construct the key of the constructed entry.
		 * @param value    the value to be set to the given {@code field} after constructing the entry.
		 * @throws NullPointerException if the given {@code instance} or {@code field} is null.
		 */
		private PropertyEntry(Object instance, Field field, Recipe recipe, V value) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(field, "field");
			Objects.requireNonNull(recipe, "recipe");
			this.instance = instance;
			this.field = field;
			this.key = Recipe.Util.get(recipe);
			this.setValue(value);
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

		@Override
		public K getKey() {
			return this.key;
		}

		@Override
		public V getValue() {
			return Methods.PrivateConcrete.getValue(
					this.instance,
					this.field
			);
		}

		@Override
		public V setValue(V value) {
			V oldValue = this.getValue();
			Methods.PrivateConcrete.setValue(
					this.instance,
					this.field,
					value,
					this.getConverter(),
					this.getType()
			);
			return oldValue;
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
			if (this.converter == null)
				this.converter = Methods.PrivateConcrete.getConverter(this.field);

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
			if (this.meta == null)
				this.meta = this.field.getAnnotation(Property.class);

			return this.meta;
		}

		/**
		 * Get the type of the value of this entry.
		 *
		 * @return the type of the value of this entry.
		 */
		public Clazz<V> getType() {
			if (this.type == null)
				this.type = Methods.PrivateConcrete.getType(this.field);

			return this.type;
		}
	}

	/**
	 * An entry for mappings that has no {@link Property} fields.
	 *
	 * @param <K> the type of the key in the entry.
	 * @param <V> the type of the value in the entry.
	 */
	final class PropertylessEntry<K, V> extends Entry<K, V> {
		/**
		 * The key of this entry.
		 */
		private final K key;
		/**
		 * The current value of this entry.
		 */
		private V value;

		/**
		 * Construct a new entry that have the given {@code key}.
		 *
		 * @param key the key of the constructed entry.
		 */
		private PropertylessEntry(K key) {
			this.key = key;
		}

		/**
		 * Construct a new entry that have the given {@code key}, and initialize the value to the given {@code value}.
		 *
		 * @param key   the key of the constructed entry.
		 * @param value the initial value of the constructed entry.
		 */
		private PropertylessEntry(K key, V value) {
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
		public V setValue(V value) {
			V old = this.value;
			this.value = value;
			return old;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(this.getKey()) ^
				   Objects.hashCode(this.getValue());
		}

		@Override
		public boolean equals(Object o) {
			return o == this ||
				   o instanceof Map.Entry &&
				   Objects.equals(((Map.Entry) o).getKey(), this.getKey()) &&
				   Objects.equals(((Map.Entry) o).getValue(), this.getValue());
		}
	}

	/**
	 * The simplest entrySet for beans.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 */
	final class RawEntrySet<K, V> extends EntrySet<PropertyEntry<K, V>> {
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
		private RawEntrySet(Object instance) {
			Objects.requireNonNull(instance, "instance");
			this.instance = instance;
		}

		@Override
		public EntrySetIterator iterator() {
			return new EntrySetIterator();
		}

		@Override
		public int size() {
			return Methods.Raw.size(this.instance);
		}

		/**
		 * An iterator for beans' entrySets. This iterator iterating over two levels. It iterates the fields of the
		 * class of the instance that its entrySet is representing. It sub-iterate over the keys of the field at a
		 * first-iterating-position.
		 * <pre>
		 *     for(Field field : instance.fields)
		 *         for(Object key : field.keys)
		 *             next <- new Entry(key);
		 * </pre>
		 */
		public final class EntrySetIterator implements Iterator<PropertyEntry<K, V>> {
			/**
			 * The backing iterator of this iterator for iterating the fields of the class of the instance of the
			 * entrySet of this iterator.
			 */
			private final Iterator<Field> iterator = Methods.Concrete.getFields(RawEntrySet.this.instance).iterator();
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
			private EntrySetIterator() {
			}

			@Override
			public boolean hasNext() {
				//fields always have more than 1 key
				return this.iterator.hasNext() || this.keys != null && this.cursor < this.keys.length;
			}

			@Override
			public PropertyEntry<K, V> next() {
				//the iterator.next() will do the job breaking the loop if no more fields available
				while (true) {
					//if there is a field previously partially solved
					if (this.keys != null && this.cursor < this.keys.length)
						//continue the to the next key of that field
						return Methods.PrivateConcrete.getPropertyEntry(RawEntrySet.this.instance, this.field, this.keys[this.cursor++]);

					//the next field!
					this.field = this.iterator.next();
					this.property = this.field.getAnnotation(Property.class);
					this.keys = this.property.keys();
					this.cursor = 0;

					//don't forget "zero keys = singular key that is the name of the field"
					if (this.keys.length == 0)
						//that is the contract!; don't worry the next call will change the field,
						//since the keys size is zero while the cursor also zero!.
						return Methods.PrivateConcrete.getPropertyEntry(RawEntrySet.this.instance, this.field, (K) this.field.getName());
				}
			}
		}
	}

	/**
	 * A keySet for beans.
	 *
	 * @param <K> the type of the keys.
	 */
	final class RawKeySet<K> extends KeySet<K> {
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
		private RawKeySet(Object instance) {
			Objects.requireNonNull(instance, "instance");
			this.instance = instance;
		}

		@Override
		public KeySetIterator iterator() {
			return new KeySetIterator();
		}

		@Override
		public int size() {
			return Methods.Raw.size(this.instance);
		}

		/**
		 * An iterator for beans' keySets.
		 */
		public final class KeySetIterator implements Iterator<K> {
			/**
			 * The backing iterator of this iterator for iterating the fields of the class of the instance of the keySet
			 * of this iterator.
			 */
			private final Iterator<Field> iterator = Methods.Concrete.getFields(RawKeySet.this.instance).iterator();
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
			private KeySetIterator() {
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
					this.keys = field.getAnnotation(Property.class).keys();
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
	final class RawValues<V> extends Values<V> {
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
		private RawValues(Object instance) {
			Objects.requireNonNull(instance, "instance");
			this.instance = instance;
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (V value : this)
				hashCode += Objects.hashCode(value);

			return hashCode;
		}

		@Override
		public boolean equals(Object object) {
			return object == this ||
				   object instanceof Bean.RawValues &&
				   ((RawValues) object).instance == this.instance;
		}

		@Override
		public ValuesIterator iterator() {
			return new ValuesIterator();
		}

		@Override
		public int size() {
			return Methods.Raw.size(this.instance);
		}

		/**
		 * An iterator for beans' values-collections.
		 */
		public final class ValuesIterator implements Iterator<V> {
			/**
			 * The backing iterator of this iterator for iterating the fields of the class of the instance of the
			 * entrySet of this iterator.
			 */
			private final Iterator<Field> iterator = Methods.Concrete.getFields(RawValues.this.instance).iterator();
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
			private ValuesIterator() {
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
						return Methods.PrivateConcrete.getValue(RawValues.this.instance, this.field);

					//the next field!
					this.field = this.iterator.next();
					//don't forget "zero keys = singular key that is the name of the field"
					//and since we don't care about the keys, we just max its length with 1
					this.keys = Math.max(1, this.field.getAnnotation(Property.class).keys().length);
					this.cursor = 0;
				}
			}
		}
	}

	/**
	 * An entrySet containing {@link Entry}s for specific keys.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 */
	final class TemporaryEntrySet<K, V> extends EntrySet<Bean.Entry<K, V>> {
		/**
		 * The instance this entrySet is for.
		 */
		private final Object instance;
		/**
		 * The solved entries.
		 */
		private final Set<Entry<K, V>> solvedEntries = new HashSet();
		/**
		 * The fields that have not been checked by this.
		 */
		private final Iterator<Field> unsolvedFieldsIterator;
		/**
		 * The keys allowed in this entrySet.
		 */
		private final Set<K> unsolvedKeys;
		/**
		 * A function to get the value of a key in the {@link #unsolvedKeys} set.
		 */
		private final BiFunction<? super K, ? super V, ? extends V> values;
		/**
		 * The last field gotten by the {@link #unsolvedFieldsIterator} iterator.
		 */
		private Field unsolvedField;
		/**
		 * The keys iterator of the last field gotten by the {@link #unsolvedFieldsIterator} iterator.
		 */
		private Iterator<K> unsolvedFieldKeysIterator;

		/**
		 * Construct a new entrySet containing entries for the specified {@code keys}.
		 *
		 * @param instance the instance of the constructed entrySet.
		 * @param keys     the keys contained in the constructed entrySet (the constructed set will modify this set!).
		 * @throws NullPointerException if the given {@code keys} is null.
		 */
		private TemporaryEntrySet(Object instance, Set<K> keys) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(keys, "keys");
			this.instance = instance;
			this.unsolvedKeys = keys;
			this.values = null;
			this.unsolvedFieldsIterator = Methods.Concrete.getFields(instance).iterator();
		}

		/**
		 * Construct a new entrySet containing entries for the specified {@code keys}.
		 *
		 * @param instance the instance of the constructed entrySet.
		 * @param keys     the keys contained in the constructed entrySet (the constructed set will modify this set!).
		 * @param values   a function to get the value of a key in the returned entrySet.
		 * @throws NullPointerException if the given {@code keys} is null.
		 */
		private TemporaryEntrySet(Object instance, Set<K> keys, BiFunction<? super K, ? super V, ? extends V> values) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(keys, "keys");
			Objects.requireNonNull(values, "values");
			this.instance = instance;
			this.unsolvedKeys = keys;
			this.values = values;
			this.unsolvedFieldsIterator = Methods.Concrete.getFields(instance).iterator();
		}

		@Override
		public AbstractEntrySetIterator iterator() {
			return new AbstractEntrySetIterator();
		}

		@Override
		public int size() {
			return this.unsolvedKeys.size();
		}

		/**
		 * An iterator iterating a set of entries specified by a set of keys.
		 */
		public final class AbstractEntrySetIterator implements Iterator<Entry<K, V>> {
			/**
			 * An iterator for the previously solved entries.
			 */
			private Iterator<Entry<K, V>> solvedEntriesIterator;
			/**
			 * An iterator for the unsolved keys.
			 */
			private Iterator<K> unsolvedKeysIterator;

			@Override
			public boolean hasNext() {
				//if there is still keys not resolved
				if (!TemporaryEntrySet.this.unsolvedKeys.isEmpty())
					//there is keys not solved means it will be solved in the next invoke of next()
					return true;

				//100% dependent stage; once achieved, it will stay forever!
				//if it is the first-time no more unsolved-keys.
				if (this.solvedEntriesIterator == null)
					//start iterating the over solved-entries
					this.solvedEntriesIterator = TemporaryEntrySet.this.solvedEntries.iterator();

				//it depends now 100% on the solved-entries
				return this.solvedEntriesIterator.hasNext();
			}

			@Override
			public Entry<K, V> next() {
				//while there is unsolved-keys. Otherwise, this block is redundant!
				while (!TemporaryEntrySet.this.unsolvedKeys.isEmpty()) {
					//only if there is a leftover keys from the last field
					if (TemporaryEntrySet.this.unsolvedFieldKeysIterator != null &&
						TemporaryEntrySet.this.unsolvedFieldKeysIterator.hasNext()) {
						//if still there is unseen keys from the last field
						K fieldKey = TemporaryEntrySet.this.unsolvedFieldKeysIterator.next();

						//only if there is a matching key in the unsolvedKeys set
						if (TemporaryEntrySet.this.unsolvedKeys.remove(fieldKey)) {
							//there is a matching key
							PropertyEntry<K, V> entry =
									TemporaryEntrySet.this.values == null ?
									//without initial-value
									Methods.PrivateConcrete.getPropertyEntry(
											TemporaryEntrySet.this.instance,
											TemporaryEntrySet.this.unsolvedField,
											fieldKey
									) :
									//with initial-value
									Methods.PrivateConcrete.getPropertyEntry(
											TemporaryEntrySet.this.instance,
											TemporaryEntrySet.this.unsolvedField,
											fieldKey,
											TemporaryEntrySet.this.values.apply(
													fieldKey,
													Methods.Concrete.getValue(
															TemporaryEntrySet.this.instance,
															TemporaryEntrySet.this.unsolvedField
													)
											)
									);

							//add the new entry; don't worry, the key already removed above!
							TemporaryEntrySet.this.solvedEntries.add(entry);
							return entry;
						}

						//no key matched in the last field's keys; to the next field!
						continue;
					}
					//only if there is fields not have been solved
					if (TemporaryEntrySet.this.unsolvedFieldsIterator.hasNext()) {
						//if no keys remaining from the last field
						Field field = TemporaryEntrySet.this.unsolvedFieldsIterator.next();
						TemporaryEntrySet.this.unsolvedField = field;
						TemporaryEntrySet.this.unsolvedFieldKeysIterator = Methods.PrivateConcrete.<K>getKeys(field).iterator();
						//continue to consume the keys of the new unsolved-field
						continue;
					}

					//if it is the first-time no more fields nor last-field's keys.
					if (this.unsolvedKeysIterator == null)
						//start iterating over the unsolved-keys
						this.unsolvedKeysIterator = TemporaryEntrySet.this.unsolvedKeys.iterator();
					//if there is still unsolved-keys
					if (this.unsolvedKeysIterator.hasNext()) {
						//if no more fields to be resolved,
						//but still there is keys to be resolved
						K key = this.unsolvedKeysIterator.next();

						//create a new entry
						Entry<K, V> entry =
								TemporaryEntrySet.this.values == null ?
								//with initial value
								Methods.PrivateConcrete.getPropertylessEntry(
										key
								) :
								//without initial value
								Methods.PrivateConcrete.getPropertylessEntry(
										key,
										TemporaryEntrySet.this.values.apply(
												key,
												null
										)
								);

						//remove it, because it has been solved
						this.unsolvedKeysIterator.remove();
						//add the solved entry
						TemporaryEntrySet.this.solvedEntries.add(entry);
						return entry;
					}
				}

				//100% dependent stage; once achieved, it will stay forever!
				//if it is the first-time no more unsolved-keys.
				if (this.solvedEntriesIterator == null)
					//start iterating the over solved-entries
					this.solvedEntriesIterator = TemporaryEntrySet.this.solvedEntries.iterator();

				//it depends now 100% on the solved-entries
				return this.solvedEntriesIterator.next();
			}
		}
	}

	/**
	 * An abstraction for entries to be used by {@link Bean}s.
	 *
	 * @param <K> the type of the key in the entry.
	 * @param <V> the type of the value in the entry.
	 */
	abstract class Entry<K, V> implements Map.Entry<K, V> {
		/**
		 * Private access constructor.
		 */
		private Entry() {
		}
	}

	/**
	 * An abstraction for entrySets to be used by {@link Bean}s.
	 *
	 * @param <E> the type of the entries.
	 */
	abstract class EntrySet<E extends Entry> extends AbstractSet<E> {
		/**
		 * Private access constructor.
		 */
		private EntrySet() {
		}
	}

	/**
	 * An abstraction for keySets to be used by {@link Bean}s.
	 *
	 * @param <K> the type of the keys.
	 */
	abstract class KeySet<K> extends AbstractSet<K> {
		/**
		 * Private access constructor.
		 */
		private KeySet() {
		}
	}

	/**
	 * An abstraction for values to be used by {@link Bean}s.
	 *
	 * @param <V> the type of the values.
	 */
	abstract class Values<V> extends AbstractCollection<V> {
		/**
		 * Private access constructor.
		 */
		private Values() {
		}
	}
}
