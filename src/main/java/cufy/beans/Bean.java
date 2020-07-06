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
import cufy.convert.ConvertException;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
		return RawMethods.size(this);
	}

	@Override
	default boolean isEmpty() {
		return RawMethods.isEmpty(this);
	}

	@Override
	default boolean containsKey(Object key) {
		return RawMethods.containsKey(this, key);
	}

	@Override
	default boolean containsValue(Object value) {
		return RawMethods.containsValue(this, value);
	}

	@Override
	default V get(Object key) {
		return RawMethods.get(this, key);
	}

	@Override
	default V put(K key, V value) {
		return RawMethods.put(this, key, value);
	}

	@Override
	default V remove(Object key) {
		return RawMethods.remove(this, key);
	}

	@Override
	default void putAll(Map<? extends K, ? extends V> map) {
		RawMethods.putAll(this, map);
	}

	@Override
	default void clear() {
		RawMethods.clear(this);
	}

	@Override
	default Set<K> keySet() {
		return RawMethods.keySet(this);
	}

	@Override
	default Collection<V> values() {
		return RawMethods.values(this);
	}

	@Override
	default Set<Map.Entry<K, V>> entrySet() {
		return (Set) RawMethods.entrySet(this);
	}

	@Override
	default V getOrDefault(Object key, V defaultValue) {
		return RawMethods.getOrDefault(this, key, defaultValue);
	}

	@Override
	default void forEach(BiConsumer<? super K, ? super V> consumer) {
		RawMethods.forEach(this, consumer);
	}

	@Override
	default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		RawMethods.replaceAll(this, function);
	}

	@Override
	default V putIfAbsent(K key, V value) {
		return RawMethods.putIfAbsent(this, key, value);
	}

	@Override
	default boolean remove(Object key, Object value) {
		return RawMethods.remove(key, value);
	}

	@Override
	default boolean replace(K key, V oldValue, V newValue) {
		return RawMethods.replace(this, key, oldValue, newValue);
	}

	@Override
	default V replace(K key, V value) {
		return RawMethods.replace(this, key, value);
	}

	@Override
	default V computeIfAbsent(K key, Function<? super K, ? extends V> function) {
		return RawMethods.computeIfAbsent(this, key, function);
	}

	@Override
	default V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> function) {
		return RawMethods.computeIfPresent(this, key, function);
	}

	@Override
	default V compute(K key, BiFunction<? super K, ? super V, ? extends V> function) {
		return RawMethods.compute(this, key, function);
	}

	@Override
	default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> function) {
		return RawMethods.merge(this, key, value, function);
	}

	/**
	 * Declare that the annotated field is a bean property. Any field annotated with {@link Property} can be accessed
	 * even if it has {@code private} modifier, if its class extends or trust the class{@link Bean}.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	@interface Property {
		/**
		 * Determine whether the {@code annotated field}'s value can be replaced or should be set remotely. If the
		 * returned array is empty, Then the constancy of the field will depend on if the {@code annotated field} is
		 * {@link Modifier#FINAL final} or not.
		 *
		 * @return true, if the {@code annotated field}'s value should be set remotely and not replaced.
		 * @throws IllegalMetaException if the array's length is more than 1.
		 */
		boolean[] constant() default {};

		/**
		 * Determine if a value should be converted to the annotated field's {@link #type()} before been {@link
		 * PropertyEntry#setValue(Object) set}.
		 *
		 * @return true, if the value should be converted before been set to the {@code annotated field}.
		 */
		boolean convert() default false;

		/**
		 * Get a reference to a converter for the {@code annotated field} to use when it is about to set a value that is
		 * not an instance of the specified type.
		 *
		 * @return a reference to the {@code annotated field}'s converter.
		 */
		Where converter() default @Where(BaseConverter.class);

		/**
		 * The name of the getter method for the {@code annotated field}. If the returned array is empty, Then the
		 * default {@link Bean}s getter will be used.
		 * <pre>
		 *     The signature of a real getter method should be like:
		 *     ANY_VISIBILITY NOT_VOID ANY_NAME({@link PropertyDescriptor} descriptor)
		 *     <br/>
		 *     The signature of a listener getter method should be like:
		 *     ANY_VISIBILITY VOID ANY_NAME({@link PropertyDescriptor} descriptor)
		 * </pre>
		 *
		 * @return the name of the getter method.
		 * @throws IllegalMetaException if the array's length is larger than 1.
		 */
		String[] get() default {};

		/**
		 * An array of keys to be used as the keys for the {@code annotated field}. If the returned array is empty, then
		 * the {@code annotated field} have one key, and that key is its name. Otherwise, the returned array is the keys
		 * of the {@code annotated field}.
		 *
		 * @return the keys of the {@code annotated field}.
		 */
		Recipe[] keys() default {};

		/**
		 * The name of the setter method for the {@code annotated field}. If the returned array is empty, Then the
		 * default {@link Bean}s setter will be used.
		 * <pre>
		 *     The signature of a real setter method should be like:
		 *     ANY_VISIBILITY boolean ANY_NAME({@link PropertyDescriptor} descriptor, Object value)
		 *     <br/>
		 *     The signature of a listener getter method should be like:
		 *     ANY_VISIBILITY VOID ANY_NAME({@link PropertyDescriptor} descriptor, Object value)
		 *     <br/>
		 *     Note that if a real setter returns {@code false},
		 *     then the invoker will treat the call as if the method was just a listener.
		 * </pre>
		 *
		 * @return the name of the setter method.
		 * @throws IllegalMetaException if the array's length is larger than 1.
		 */
		String[] set() default {};

		/**
		 * An array containing the type of the {@code annotated field}. If the returned array is empty, then the {@code
		 * annotated field} have the type found in its signature. Otherwise, the first element in the returned array is
		 * the type of the {@code annotated field}.
		 * <br>
		 *
		 * @return the type of the {@code annotated field}
		 * @throws IllegalMetaException if the array's length is more than 1.
		 */
		Type[] type() default {};
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
		public DelegateEntrySet(Bean<K, V> delegate, Object instance) {
			Objects.requireNonNull(delegate, "delegate");
			Objects.requireNonNull(instance, "instance");
			this.delegate = delegate;
			this.instance = instance;
			this.addAll(RawMethods.entrySet(instance));
		}

		@Override
		public Iterator iterator() {
			return new Iterator();
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
		private final class Iterator implements java.util.Iterator<Entry<K, V>> {
			/**
			 * The iterator backing this iterator.
			 */
			private final java.util.Iterator<Entry<K, V>> iterator = DelegateEntrySet.this.list.iterator();

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
		public DelegateKeySet(Bean<K, ?> delegate, Object instance) {
			Objects.requireNonNull(delegate, "delegate");
			Objects.requireNonNull(instance, "instance");
			this.delegate = delegate;
			this.instance = instance;
		}

		@Override
		public Iterator iterator() {
			return new Iterator();
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
		public final class Iterator implements java.util.Iterator<K> {
			/**
			 * The iterator backing this iterator.
			 */
			private final java.util.Iterator<Map.Entry<K, ?>> iterator = (java.util.Iterator) DelegateKeySet.this.delegate.entrySet().iterator();

			/**
			 * Construct a new fullBean keySet iterator.
			 */
			private Iterator() {
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
	 * The methods for the {@link Bean}s that are delegating to an instance.
	 */
	final class DelegateMethods {
		/**
		 * This is an util class and must not be instanced as an object.
		 *
		 * @throws AssertionError when called.
		 */
		private DelegateMethods() {
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
		 * @throws NullPointerException if the given {@code delegate} or {@code instance} or {@code function} is null.
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
			entrySet.add(Entry.from(instance, key, value));
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
		 * @return the current (existing or computed) value associated with the specified key, or null if the computed
		 * 		value is null.
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
			entrySet.add(Entry.from(instance, key, value));
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
		 * @throws NullPointerException if the given {@code delegate} or {@code instance} or {@code function} is null.
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

			for (K key : RawMethods.<K, Object>keySet(object))
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
		 * @throws NullPointerException if the given {@code delegate} or {@code instance} or {@code consumer} is null.
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
		 * @return the value to which the specified key is mapped, or {@code defaultValue} if the given {@code delegate}
		 * 		contains no mapping for the key.
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
		 *                 existing value or a null value is associated with the key, to be associated with the key.
		 * @param function the function to recompute a value if present.
		 * @param <K>      the type of the keys in the given {@code delegate}.
		 * @param <V>      the type of the values in the given {@code delegate}.
		 * @return the new value associated with the specified key, or null if no value is associated with the key.
		 * @throws NullPointerException if the given {@code delegate} or {@code instance} or {@code function} is null.
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
			entrySet.add(Entry.from(instance, key, value));
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
		 * 		given {@code key}. (A null return can also indicate that the given {@code delegate} previously associated
		 * 		null with the given {@code key}).
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
			entrySet.add(Entry.from(instance, key, value));
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
					new TemporaryEntrySet(instance, keys, (k, v) -> map.get(k))
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
		 * @return the previous value associated with the specified key, or null if there was no mapping for the key. (A
		 * 		null return can also indicate that the given {@code delegate} previously associated null with the key).
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
		 * @return the previous value associated with the specified key, or null if there was no mapping for the key. (A
		 * 		null return can also indicate that the map previously associated null with the key).
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
		 * @throws NullPointerException if the given {@code delegate} or {@code instance} or {@code function} is null.
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
		public DelegateValues(Bean<?, V> delegate, Object instance) {
			Objects.requireNonNull(delegate, "delegate");
			Objects.requireNonNull(instance, "instance");
			this.delegate = delegate;
			this.instance = instance;
		}

		@Override
		public Iterator iterator() {
			return new Iterator();
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
		public final class Iterator implements java.util.Iterator<V> {
			/**
			 * The iterator backing this iterator.
			 */
			private final java.util.Iterator<Map.Entry<?, V>> iterator = (java.util.Iterator) DelegateValues.this.delegate.entrySet().iterator();

			/**
			 * Construct a new fullBean values iterator.
			 */
			private Iterator() {
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
	 * A set of descriptors for the fields of an object or a class.
	 *
	 * @param <K> the type of the keys in the descriptors.
	 * @param <V> the type of the values in the descriptors.
	 */
	final class PropertiesDescriptors<K, V> extends AbstractSet<PropertyDescriptor<K, V>> {
		/**
		 * The fieldsSet backing this set.
		 */
		private final Set<Field> fields;

		/**
		 * Construct a new descriptors set for the fields of the given {@code instance}.
		 *
		 * @param instance the instance the constructed descriptor is for.
		 * @throws NullPointerException if the given {@code instance} is null.
		 */
		public PropertiesDescriptors(Object instance) {
			Objects.requireNonNull(instance, "instance");
			this.fields = Collectionz.filteredSet(
					cufy.util.Reflection.fieldsSet(instance.getClass()),
					field -> field.isAnnotationPresent(Property.class)
			);
		}

		/**
		 * Construct a new descriptors set for the fields of the given {@code klass}.
		 *
		 * @param klass the class the constructed descriptor is for.
		 * @throws NullPointerException if the given {@code klass} is null.
		 */
		public PropertiesDescriptors(Class klass) {
			Objects.requireNonNull(klass, "klass");
			this.fields = Collectionz.filteredSet(
					cufy.util.Reflection.fieldsSet(klass),
					field -> field.isAnnotationPresent(Property.class)
			);
		}

		@Override
		public Iterator iterator() {
			return new Iterator();
		}

		@Override
		public int size() {
			return this.fields.size();
		}

		/**
		 * An iterator that iterates properties descriptors for a set of fields.
		 */
		public final class Iterator implements java.util.Iterator<PropertyDescriptor<K, V>> {
			/**
			 * The iterator backing this iterator.
			 */
			private final java.util.Iterator<Field> iterator = PropertiesDescriptors.this.fields.iterator();

			/**
			 * Private access constructor.
			 */
			private Iterator() {
			}

			@Override
			public boolean hasNext() {
				return this.iterator.hasNext();
			}

			@Override
			public PropertyDescriptor<K, V> next() {
				return new PropertyDescriptor(this.iterator.next());
			}
		}
	}

	/**
	 * An object that gives a quicker access to a propery.
	 *
	 * @param <K> the type of the keys of the descriptor.
	 * @param <V> the type of the value of the descriptor.
	 */
	final class PropertyDescriptor<K, V> {
		/**
		 * The field that have been annotated with the {@code property} of this object.
		 */
		private final Field field;
		/**
		 * Cached value for {@link Property#constant()}.
		 */
		private Boolean constant;
		/**
		 * Cached value for {@link Property#convert()}.
		 */
		private Boolean convert;
		/**
		 * Cached value for {@link Property#converter()}.
		 */
		private Converter converter;
		/**
		 * Cached value for {@link Property#get()}.
		 */
		private Method get;
		/**
		 * Cached value for {@link Property#keys()}.
		 */
		private PropertyKeys<K> keys;
		/**
		 * The property this object is accessing.
		 */
		private Property property;
		/**
		 * Cached value for {@link Property#set()}.
		 */
		private Method set;
		/**
		 * Cached value for {@link Property#type()}.
		 */
		private Clazz<V> type;

		/**
		 * Construct a new object that givens a quicker access to the given {@code field}'s {@link Property}.
		 *
		 * @param field the field that have been annotated to the {@link Property} used by the constructed object.
		 * @throws NullPointerException     if the given {@code field} is null.
		 * @throws IllegalArgumentException if the given {@code field} is not annotated with {@link Property}.
		 */
		public PropertyDescriptor(Field field) {
			Objects.requireNonNull(field, "field");
			if (!field.isAnnotationPresent(Property.class))
				throw new IllegalArgumentException("Field rejected: " + field);
			this.field = field;
		}

		@Override
		public int hashCode() {
			return this.property().hashCode();
		}

		@Override
		public boolean equals(Object object) {
			return object == this ||
				   object instanceof Bean.PropertyDescriptor &&
				   ((PropertyDescriptor) object).property() == this.property();
		}

		@Override
		public String toString() {
			return "descriptor " + this.property();
		}

		/**
		 * Determine if the {@code annotated field} is {@link Property#constant() constant} or not.
		 *
		 * @return true, if the {@code annotated field} is {@link Property#constant() constant}.
		 */
		public boolean constant() {
			if (this.constant == null) {
				boolean[] array = this.property().constant();

				if (array.length == 0)
					this.constant = Modifier.isFinal(this.field().getModifiers());
				else if (array.length == 1)
					this.constant = array[0];
				else
					throw new IllegalMetaException("Bean.Property.constant().length > 1");
			}

			return this.constant;
		}

		/**
		 * Determine if a value should be converted to the annotated field's {@link Property#type()} before been {@link
		 * PropertyEntry#setValue(Object) set}.
		 *
		 * @return true, if the value should be converted before been set to teh annotated field.
		 */
		public boolean convert() {
			if (this.convert == null)
				this.convert = this.property().convert();

			return this.convert;
		}

		/**
		 * Get the converter specified in the {@code annotated field}'s {@link Property} annotation.
		 *
		 * @return the converter specified in the {@code annotated field}'s {@link Property} annotation.
		 */
		public Converter converter() {
			if (this.converter == null)
				this.converter = Where.Util.getValue(this.property().converter());

			return this.converter;
		}

		/**
		 * Get the {@link Field annotated field}.
		 *
		 * @return the annotated field.
		 */
		public Field field() {
			return this.field;
		}

		/**
		 * Get the getter method of the {@code annotated field}.
		 *
		 * @return the getter method of the {@code annotated field}, Or null if the {@code annotated field} does not
		 * 		specify a getter method.
		 */
		public Method get() {
			if (this.get == null) {
				String[] array = this.property().get();

				if (array.length == 0)
					this.get = null;
				else if (array.length == 1)
					try {
						this.get = this.field().getDeclaringClass().getMethod(array[0], PropertyDescriptor.class);
					} catch (NoSuchMethodException e) {
						throw new IllegalMetaException("Method not found: " + array[0], e);
					}
				else
					throw new IllegalMetaException("Bean.Property.get().length > 1");
			}

			return this.get;
		}

		/**
		 * Get an entry for the {@code property} of this. With the key that have the given {@code keyIndex}.
		 *
		 * @param instance the instance that the returned entry is for a field in it.
		 * @param keyIndex the index of the key in the field to be the key of the returned entry.
		 * @return an entry for the {@code property} of this. With the key that have the given {@code keyIndex}.
		 * @throws NullPointerException if the given {@code instance} is null.
		 */
		public PropertyEntry<K, V> getEntry(Object instance, int keyIndex) {
			Objects.requireNonNull(instance, "instance");
			if (!this.field.getDeclaringClass().isAssignableFrom(instance.getClass()))
				throw new IllegalArgumentException("Instance rejected: " + instance);

			return new PropertyEntry(instance, this, keyIndex);
		}

		/**
		 * Get an entry for the {@code property} of this. With the key that have the given {@code keyIndex}. Then set
		 * the value of it to the given {@code value}.
		 *
		 * @param instance the instance that the returned entry is for a field in it.
		 * @param keyIndex the index of the key in the field to be the key of the returned entry.
		 * @param value    the initial value to be set in the returned entry.
		 * @return an entry for the {@code property} of this. With the key that have the given {@code keyIndex}.
		 * @throws NullPointerException if the given {@code instance} is null.
		 */
		public PropertyEntry<K, V> getEntry(Object instance, int keyIndex, V value) {
			Objects.requireNonNull(instance, "instance");
			if (!this.field.getDeclaringClass().isAssignableFrom(instance.getClass()))
				throw new IllegalArgumentException("Instance rejected: " + instance);

			return new PropertyEntry(instance, this, keyIndex, value);
		}

		/**
		 * Get the value at the {@code annotated field} of this {@code descriptor} in the given {@code instance}.
		 *
		 * @param instance the instance to get the value of in the {@code annotated field} at that instance.
		 * @return the value at the {@code annotated field} of this {@code descriptor} in the given {@code instance}.
		 * @throws NullPointerException if the given {@code instance} is null.
		 */
		public V getValue(Object instance) {
			Objects.requireNonNull(instance, "instance");

			if (this.get() != null)
				try {
					if (this.get().getReturnType() == Void.class)
						//if the method has 'Void' type, then it is just a listener
						this.get().invoke(instance, this);
					else //otherwise, it is a getter
						return (V) this.get().invoke(instance, this);
				} catch (IllegalAccessException e) {
					IllegalAccessError error = new IllegalAccessError(e.getMessage());
					error.initCause(e);
					throw error;
				} catch (InvocationTargetException e) {
					throw new InternalError("Thrown in getter: " + e.getMessage(), e);
				}

			try {
				this.field().setAccessible(true);
				return (V) this.field().get(instance);
			} catch (IllegalAccessException e) {
				IllegalAccessError error = new IllegalAccessError();
				error.initCause(e);
				throw error;
			}
		}

		/**
		 * Get a set of the keys the {@code annotated field} does have.
		 *
		 * @return a set of the keys the {@code annotated field} does have.
		 */
		public PropertyKeys<K> keys() {
			if (this.keys == null)
				this.keys = new PropertyKeys(this.field());

			return this.keys;
		}

		/**
		 * Get the {@link Property} annotation that have been annotated to the {@code field} of this.
		 *
		 * @return the property annotation.
		 */
		public Property property() {
			if (this.property == null)
				this.property = this.field().getAnnotation(Property.class);

			return this.property;
		}

		/**
		 * Get the setter method of the {@code annotated field}.
		 *
		 * @return the setter method of the {@code annotated field}, Or null if the {@code annotated field} does not
		 * 		specify a setter method.
		 */
		public Method set() {
			if (this.set == null) {
				String[] array = this.property().set();

				if (array.length == 0)
					this.set = null;
				else if (array.length == 1)
					try {
						this.set = this.field().getDeclaringClass().getMethod(array[0], PropertyDescriptor.class, Object.class);
					} catch (NoSuchMethodException e) {
						throw new IllegalMetaException("Method not found: " + array[0], e);
					}
				else
					throw new IllegalMetaException("Bean.Property.set().length > 1");
			}

			return this.set;
		}

		/**
		 * Set the given {@code value} to the {@code annotated field} of this {@code descriptor} in the given {@code
		 * instance}.
		 *
		 * @param instance the instance for the value to be set in the {@code annotated field} at that instance.
		 * @param value    the new value to be set.
		 * @throws NullPointerException if the given {@code instance} is null.
		 */
		public void setValue(Object instance, V value) {
			Objects.requireNonNull(instance, "instance");

			if (this.set() != null)
				try {
					if (this.set().getReturnType() == Void.class)
						//if the method return type is 'Void', then it is just a listener.
						this.set().invoke(instance, this, value);
					if ((boolean) this.set().invoke(instance, this, value))
						//if the method returned 'false', then it wants this method to perform the default 'set' algorithm
						return;
				} catch (IllegalAccessException e) {
					IllegalAccessError error = new IllegalAccessError(e.getMessage());
					error.initCause(e);
					throw error;
				} catch (InvocationTargetException e) {
					throw new InternalError("Thrown in Setter: " + e.getMessage(), e);
				}

			try {
				this.field().setAccessible(true);
				Clazz<V> valueType = Clazz.Generate.from(value);

				if (this.constant())
					try {
						//set remotely
						V oldValue = (V) this.field().get(instance);

						if (oldValue !=
							this.converter().convert(new ConvertToken<>(value, oldValue, valueType, this.type())))
							//when the converter can't convert to the provided instance, it will replace it with a new one
							throw new IllegalArgumentException("Constant field!");
					} catch (ConvertException e) {
						throw new IllegalArgumentException("Constant field!", e);
					}
				else if (this.convert())
					try {
						//set after converting
						V newValue = this.converter().convert(new ConvertToken<>(value, value, valueType, this.type()));
						this.field().set(instance, newValue);
					} catch (ConvertException e) {
						//as specified in the Map interface
						ClassCastException exception = new ClassCastException(e.getMessage());
						exception.initCause(e);
						throw exception;
					}
				else //direct set
					this.field().set(instance, value);
			} catch (IllegalAccessException e) {
				IllegalAccessError error = new IllegalAccessError(e.getMessage());
				error.initCause(e);
				throw error;
			}
		}

		/**
		 * Get the type of the {@code annotated field}. The type means the {@link Clazz} specified for the values to be
		 * store at the {@code annotated field}.
		 *
		 * @return the type of the {@code annotated field}.
		 */
		public Clazz<V> type() {
			if (this.type == null) {
				Type[] array = this.property().type();

				if (array.length == 0)
					this.type = (Clazz<V>) Clazz.of(this.field().getType());
				else if (array.length == 1)
					this.type = Type.Util.get(array[0]);
				else
					throw new IllegalMetaException("Bean.Property.type().length > 1");
			}

			return this.type;
		}

		/**
		 * Get an entry for the {@code property} of this. With the given {@code key}.
		 *
		 * @param instance the instance that the returned entry is for a field in it.
		 * @param key      the computed key of the returned entry.
		 * @param keyIndex the index of the key in the field to be the key of the returned entry.
		 * @return an entry for the {@code property} of this. With the key that have the given {@code keyIndex}.
		 * @throws NullPointerException if the given {@code instance} is null.
		 */
		private PropertyEntry<K, V> getEntry(Object instance, K key, int keyIndex) {
			Objects.requireNonNull(instance, "instance");
			if (!this.field.getDeclaringClass().isAssignableFrom(instance.getClass()))
				throw new IllegalArgumentException("Instance rejected: " + instance);

			return new PropertyEntry(instance, this, key, keyIndex);
		}

		/**
		 * Get an entry for the {@code property} of this. With the key that have the given {@code keyIndex}. Then set
		 * the value of it to the given {@code value}.
		 *
		 * @param instance the instance that the returned entry is for a field in it.
		 * @param key      the computed key of the returned entry.
		 * @param keyIndex the index of the key in the field to be the key of the returned entry.
		 * @param value    the initial value to be set in the returned entry.
		 * @return an entry for the {@code property} of this. With the key that have the given {@code keyIndex}.
		 * @throws NullPointerException if the given {@code instance} is null.
		 */
		private PropertyEntry<K, V> getEntry(Object instance, K key, int keyIndex, V value) {
			Objects.requireNonNull(instance, "instance");
			if (!this.field.getDeclaringClass().isAssignableFrom(instance.getClass()))
				throw new IllegalArgumentException("Instance rejected: " + instance);

			return new PropertyEntry(instance, this, key, keyIndex, value);
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
		 * The descriptor of this entry.
		 */
		private final PropertyDescriptor<K, V> descriptor;
		/**
		 * The index of the key of this entry.
		 */
		private final int index;
		/**
		 * The instance that this entry is editing.
		 */
		private final Object instance;
		/**
		 * The key of this entry.
		 */
		private K key;

		/**
		 * Construct a new entry with the given parameters.
		 *
		 * @param instance the instance that the constructed entry is editing.
		 * @param field    the field where the constructed entry is editing in the given {@code instance}.
		 * @param index    the index of the key that the constructed entry is having.
		 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
		 * @throws IllegalArgumentException if the given {@code field} does not have such key at the given {@code
		 *                                  index}.
		 */
		private PropertyEntry(Object instance, Field field, int index) {
			this(instance, new PropertyDescriptor(field), index);
		}

		/**
		 * Construct a new entry with the given parameters. Then set the value of the constructed entry to the given
		 * {@code value}.
		 *
		 * @param instance the instance that the constructed entry is editing.
		 * @param field    the field where the constructed entry is editing in the given {@code instance}.
		 * @param index    the index of the key that the constructed entry is having.
		 * @param value    the initial value the constructed entry will have.
		 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
		 * @throws IllegalArgumentException if the given {@code field} does not have such key at the given {@code
		 *                                  index}.
		 */
		private PropertyEntry(Object instance, Field field, int index, V value) {
			this(instance, new PropertyDescriptor(field), index, value);
		}

		/**
		 * Construct a new entry with the given parameters.
		 *
		 * @param instance the instance that the constructed entry is editing.
		 * @param field    the field where the constructed entry is editing in the given {@code instance}.
		 * @param key      the computed key of the entry.
		 * @param index    the index of the key that the constructed entry is having.
		 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
		 * @throws IllegalArgumentException if the given {@code field} does not have such key at the given {@code
		 *                                  index}.
		 */
		private PropertyEntry(Object instance, Field field, K key, int index) {
			this(instance, new PropertyDescriptor(field), key, index);
		}

		/**
		 * Construct a new entry with the given parameters. Then set the value of the constructed entry to the given
		 * {@code value}.
		 *
		 * @param instance the instance that the constructed entry is editing.
		 * @param field    the field where the constructed entry is editing in the given {@code instance}.
		 * @param key      the computed key of the entry.
		 * @param index    the index of the key that the constructed entry is having.
		 * @param value    the initial value the constructed entry will have.
		 * @throws NullPointerException     if the given {@code instance} or {@code field} is null.
		 * @throws IllegalArgumentException if the given {@code field} does not have such key at the given {@code
		 *                                  index}.
		 */
		private PropertyEntry(Object instance, Field field, K key, int index, V value) {
			this(instance, new PropertyDescriptor(field), key, index, value);
		}

		/**
		 * Construct a new entry with the given parameters.
		 *
		 * @param instance   the instance that the constructed entry is editing.
		 * @param descriptor the descriptor of the field where the constructed entry is editing in the given {@code
		 *                   instance}.
		 * @param index      the index of the key that the constructed entry is having.
		 * @throws NullPointerException     if the given {@code instance} or {@code descriptor} is null.
		 * @throws IllegalArgumentException if the given {@code descriptor} does not have such key at the given {@code
		 *                                  index}.
		 */
		private PropertyEntry(Object instance, PropertyDescriptor<K, V> descriptor, int index) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(descriptor, "descriptor");
			if (index < 0)
				throw new IllegalArgumentException("Key Index is negative: " + index);
			if (index != 1 && index > descriptor.property().keys().length)
				throw new IllegalArgumentException("Key Index rejected: " + index);
			this.instance = instance;
			this.descriptor = descriptor;
			this.index = index;
		}

		/**
		 * Construct a new entry with the given parameters. Then set the value of the constructed entry to the given
		 * {@code value}.
		 *
		 * @param instance   the instance that the constructed entry is editing.
		 * @param descriptor the descriptor of the field where the constructed entry is editing in the given {@code
		 *                   instance}.
		 * @param index      the index of the key that the constructed entry is having.
		 * @param value      the initial value the constructed entry will have.
		 * @throws NullPointerException     if the given {@code instance} or {@code descriptor} is null.
		 * @throws IllegalArgumentException if the given {@code descriptor} does not have such key at the given {@code
		 *                                  index}.
		 */
		private PropertyEntry(Object instance, PropertyDescriptor<K, V> descriptor, int index, V value) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(descriptor, "descriptor");
			if (index < 0)
				throw new IllegalArgumentException("Key Index is negative: " + index);
			if (index != 1 && index > descriptor.property().keys().length)
				throw new IllegalArgumentException("Key Index rejected: " + index);
			this.instance = instance;
			this.descriptor = descriptor;
			this.index = index;
			this.setValue(value);
		}

		/**
		 * Construct a new entry with the given parameters.
		 *
		 * @param instance   the instance that the constructed entry is editing.
		 * @param descriptor the descriptor of the field where the constructed entry is editing in the given {@code
		 *                   instance}.
		 * @param key        the computed key of the entry.
		 * @param index      the index of the key that the constructed entry is having.
		 * @throws NullPointerException     if the given {@code instance} or {@code descriptor} is null.
		 * @throws IllegalArgumentException if the given {@code descriptor} does not have such key at the given {@code
		 *                                  index}.
		 */
		private PropertyEntry(Object instance, PropertyDescriptor<K, V> descriptor, K key, int index) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(descriptor, "descriptor");
			if (index < 0)
				throw new IllegalArgumentException("Key Index is negative: " + index);
			if (index != 1 && index > descriptor.property().keys().length)
				throw new IllegalArgumentException("Key Index rejected: " + index);
			this.instance = instance;
			this.descriptor = descriptor;
			this.index = index;
			this.key = key;
		}

		/**
		 * Construct a new entry with the given parameters. Then set the value of the constructed entry to the given
		 * {@code value}.
		 *
		 * @param instance   the instance that the constructed entry is editing.
		 * @param descriptor the descriptor of the field where the constructed entry is editing in the given {@code
		 *                   instance}.
		 * @param key        the computed key of the entry.
		 * @param index      the index of the key that the constructed entry is having.
		 * @param value      the initial value the constructed entry will have.
		 * @throws NullPointerException     if the given {@code instance} or {@code descriptor} is null.
		 * @throws IllegalArgumentException if the given {@code descriptor} does not have such key at the given {@code
		 *                                  index}.
		 */
		private PropertyEntry(Object instance, PropertyDescriptor<K, V> descriptor, K key, int index, V value) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(descriptor, "descriptor");
			if (index < 0)
				throw new IllegalArgumentException("Key Index is negative: " + index);
			if (index != 1 && index > descriptor.property().keys().length)
				throw new IllegalArgumentException("Key Index rejected: " + index);
			this.instance = instance;
			this.descriptor = descriptor;
			this.index = index;
			this.key = key;
			this.setValue(value);
		}

		@Override
		public boolean equals(Object obj) {
			return /*quick match*/ obj == this ||
					/*property match*/ obj instanceof Bean.PropertyEntry &&
									   ((PropertyEntry) obj).instance == this.instance &&
									   Objects.equals(((PropertyEntry) obj).index, this.index) &&
									   Objects.equals(((PropertyEntry) obj).descriptor, this.descriptor) ||
					/*entry match*/ obj instanceof Map.Entry &&
									Objects.equals(((Map.Entry) obj).getKey(), this.getKey()) &&
									Objects.equals(((Map.Entry) obj).getValue(), this.getValue());
		}

		@Override
		public K getKey() {
			if (this.key == null)
				this.key = this.descriptor.keys().get(this.index);

			return this.key;
		}

		@Override
		public V getValue() {
			return this.descriptor.getValue(this.instance);
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(this.getKey()) ^
				   Objects.hashCode(this.getValue());
		}

		@Override
		public V setValue(V value) {
			V oldValue = this.getValue();
			this.descriptor.setValue(this.instance, value);
			return oldValue;
		}

		@Override
		public String toString() {
			return this.getKey() + "=" + this.getValue();
		}

		/**
		 * Get the {@link PropertyDescriptor#convert() converting-preference} of this entry's {@link
		 * #getPropertyDescriptor() descriptor}.
		 *
		 * @return the {@code converting-preference} of this entry.
		 */
		public boolean doConvert() {
			return this.descriptor.convert();
		}

		/**
		 * Get the {@link PropertyDescriptor#converter() converter} of this entry's {@link #getPropertyDescriptor()
		 * descriptor}.
		 *
		 * @return the {@code converter} of this entry.
		 */
		public Converter getConverter() {
			return this.descriptor.converter();
		}

		/**
		 * Get the {@link PropertyDescriptor#field() field} of this entry's {@link #getPropertyDescriptor()
		 * descriptor}.
		 *
		 * @return the {@code field} of this entry.
		 */
		public Field getField() {
			return this.descriptor.field();
		}

		/**
		 * Get the {@link PropertyDescriptor#property() property} of this entry's {@link #getPropertyDescriptor()
		 * descriptor}.
		 *
		 * @return the {@code property} of this entry.
		 */
		public Property getProperty() {
			return this.descriptor.property();
		}

		/**
		 * Get the {@link Property} annotation from the field of this entry.
		 *
		 * @return the descriptor of this entry.
		 */
		public PropertyDescriptor<K, V> getPropertyDescriptor() {
			return this.descriptor;
		}

		/**
		 * Get the {@link PropertyDescriptor#type() type} of this entry's {@link #getPropertyDescriptor() descriptor}.
		 *
		 * @return the {@code type} of this entry.
		 */
		public Clazz<V> getType() {
			return this.descriptor.type();
		}

		/**
		 * Get the {@link PropertyDescriptor#constant() constancy} of this entry's {@link #getPropertyDescriptor()
		 * descriptor}.
		 *
		 * @return the {@code constancy} of this entry.
		 */
		public boolean isConstant() {
			return this.descriptor.constant();
		}
	}

	/**
	 * A set containing the keys of a property.
	 *
	 * @param <K> the type of the keys.
	 */
	final class PropertyKeys<K> extends AbstractList<K> {
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
		private PropertyKeys(Field field) {
			Objects.requireNonNull(field, "field");
			this.field = field;
			this.recipes = field.getAnnotation(Property.class).keys();
		}

		@Override
		public K get(int index) {
			if (this.recipes.length == 0) {
				if (index == 0)
					return (K) this.field.getName();

				throw new IndexOutOfBoundsException("size=1, index=" + index);
			}

			if (index < this.recipes.length)
				return Recipe.Util.get(this.recipes[index]);

			throw new IndexOutOfBoundsException("size=" + this.recipes.length + ", index=" + index);
		}

		@Override
		public int size() {
			return Math.max(1, this.recipes.length);
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
			return /*quick match*/ o == this ||
					/*entry match*/ o instanceof Map.Entry &&
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
		public RawEntrySet(Object instance) {
			Objects.requireNonNull(instance, "instance");
			this.instance = instance;
		}

		@Override
		public Iterator iterator() {
			return new Iterator();
		}

		@Override
		public int size() {
			return RawMethods.size(this.instance);
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
		public final class Iterator implements java.util.Iterator<PropertyEntry<K, V>> {
			/**
			 * The backing iterator of this iterator for iterating the fields of the class of the instance of the
			 * entrySet of this iterator.
			 */
			private final java.util.Iterator<PropertyDescriptor<K, V>> iterator =
					new PropertiesDescriptors<K, V>(RawEntrySet.this.instance).iterator();
			/**
			 * The position of the next key in the current {@link #descriptor}.
			 */
			private int cursor;
			/**
			 * The current field.
			 */
			private PropertyDescriptor<K, V> descriptor;
			/**
			 * How many keys the current {@link #descriptor} is having.
			 */
			private int keys;

			/**
			 * Construct a new iterator for the enclosing entrySet.
			 */
			private Iterator() {
			}

			@Override
			public boolean hasNext() {
				//fields always have more than 1 key
				return this.iterator.hasNext() || this.cursor < this.keys;
			}

			@Override
			public PropertyEntry<K, V> next() {
				//the iterator.next() will do the job breaking the loop if no more fields available
				while (true) {
					//if there is a field previously partially solved
					if (this.cursor < this.keys)
						//continue the to the next key of that field
						return this.descriptor.getEntry(
								RawEntrySet.this.instance,
								this.cursor++
						);

					//the next field!
					PropertyDescriptor<K, V> descriptor = this.iterator.next();
					this.descriptor = descriptor;
					this.keys = descriptor.keys().size();
					this.cursor = 0;
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
		public RawKeySet(Object instance) {
			Objects.requireNonNull(instance, "instance");
			this.instance = instance;
		}

		@Override
		public Iterator iterator() {
			return new Iterator();
		}

		@Override
		public int size() {
			return RawMethods.size(this.instance);
		}

		/**
		 * An iterator for beans' keySets.
		 */
		public final class Iterator implements java.util.Iterator<K> {
			/**
			 * The backing iterator of this iterator for iterating the fields of the class of the instance of the keySet
			 * of this iterator.
			 */
			private final java.util.Iterator<PropertyDescriptor<K, Object>> iterator =
					new PropertiesDescriptors<K, Object>(RawKeySet.this.instance).iterator();
			/**
			 * The position of the next key at the current {@link #keys}.
			 */
			private int cursor;
			/**
			 * The keys of the current field.
			 */
			private java.util.Iterator<K> keys;

			/**
			 * Construct a new iterator for the enclosing entrySet.
			 */
			private Iterator() {
			}

			@Override
			public boolean hasNext() {
				//fields always have more than 1 key
				return this.iterator.hasNext() || this.keys != null && this.keys.hasNext();
			}

			@Override
			public K next() {
				//the iterator.next() will do the job breaking the loop if no more fields available
				while (true) {
					//if there is a field previously partially solved
					if (this.keys != null && this.keys.hasNext())
						//continue the to the next key of that field
						return this.keys.next();

					//the next field!
					this.keys = this.iterator.next().keys().iterator();
					this.cursor = 0;
				}
			}
		}
	}

	/**
	 * The methods for any instance that considered to be a {@link Bean}.
	 */
	final class RawMethods {
		/**
		 * This is an util class and must not be instanced as an object.
		 *
		 * @throws AssertionError when called.
		 */
		private RawMethods() {
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
			if (!new PropertiesDescriptors(instance).isEmpty())
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

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance))
				for (K propertyKey : property.keys())
					if (Objects.equals(key, propertyKey)) {
						V value = function.apply(key, property.getValue(instance));

						if (value == null)
							throw new UnsupportedOperationException("remove");

						property.setValue(instance, value);
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
		 * @return the current (existing or computed) value associated with the specified key, or null if the computed
		 * 		value is null.
		 * @throws NullPointerException          if the given {@code instance} or {@code function} is null.
		 * @throws UnsupportedOperationException if the given {@code instance} don't have the given {@code key}.
		 */
		public static <K, V> V computeIfAbsent(Object instance, K key, Function<? super K, ? extends V> function) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(function, "function");

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance))
				if (property.getValue(instance) == null)
					for (K propertyKey : property.keys())
						if (Objects.equals(key, propertyKey)) {
							V value = function.apply(key);

							if (value != null)
								property.setValue(instance, value);

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

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance))
				for (K propertyKey : property.keys())
					if (Objects.equals(key, propertyKey)) {
						V propertyValue = property.getValue(instance);

						if (propertyValue != null) {
							V value = function.apply(key, propertyValue);

							if (value == null)
								throw new UnsupportedOperationException("remove");

							property.setValue(instance, value);
							return value;
						}

						return null;
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

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance))
				for (Object propertyKey : property.keys())
					if (Objects.equals(key, propertyKey))
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

			for (PropertyDescriptor<K, V> descriptor : new PropertiesDescriptors<K, V>(instance))
				if (Objects.equals(value, descriptor.getValue(instance)))
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
						RawMethods.entrySet(instance),
						((Map) object).entrySet()
				);

			//bean match
			Set<K> keys = new HashSet(RawMethods.keySet(instance));

			for (K key : RawMethods.<K, Object>keySet(object))
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

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance)) {
				V propertyValue = property.getValue(instance);

				for (K key : property.keys())
					consumer.accept(key, propertyValue);
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

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance))
				for (Object propertyKey : property.keys())
					if (Objects.equals(key, propertyKey))
						return property.getValue(instance);

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
		 * @return the value to which the specified key is mapped, or {@code defaultValue} if the given {@code instance}
		 * 		contains no mapping for the key.
		 * @throws NullPointerException if the given {@code instance} is null.
		 */
		public static <K, V> V getOrDefault(Object instance, K key, V defaultValue) {
			Objects.requireNonNull(instance, "instance");

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance))
				for (K propertyKey : property.keys())
					if (Objects.equals(key, propertyKey))
						return property.getValue(instance);

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

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance)) {
				Object value = property.getValue(instance);
				int valueHash = Objects.hashCode(value);

				for (K key : property.keys()) {
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
			return new PropertiesDescriptors(instance).isEmpty();
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
		 *                 existing value or a null value is associated with the key, to be associated with the key.
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

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance))
				for (K propertyKey : property.keys())
					if (Objects.equals(key, propertyKey)) {
						V propertyValue = property.getValue(instance);
						V newValue = propertyValue == null ? value : function.apply(propertyValue, value);

						if (newValue == null)
							throw new UnsupportedOperationException("remove");

						property.setValue(instance, newValue);
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
		 * @return the previous value associated with given {@code key}, or null if there was no mapping for the given
		 *        {@code key}. (A null return can also indicate that the given {@code instance} previously associated null
		 * 		with the given {@code key}).
		 * @throws NullPointerException          if the given {@code instance} is null.
		 * @throws UnsupportedOperationException if the given {@code instance} don't have the given {@code key}.
		 */
		public static <K, V> V put(Object instance, K key, V value) {
			Objects.requireNonNull(instance, "instance");

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance))
				for (Object propertyKey : property.keys())
					if (Objects.equals(key, propertyKey)) {
						V oldValue = property.getValue(instance);
						property.setValue(instance, value);
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
		 * @throws UnsupportedOperationException if the given {@code map} has keys that the given {@code instance} does
		 *                                       not have.
		 */
		public static <K, V> void putAll(Object instance, Map<? extends K, ? extends V> map) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(map, "map");

			Set keySet = new HashSet(map.keySet());

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance)) {
				//no need to reassign the same field
				boolean put = true;

				//first key in the Property annotation has more priority that other keys!
				for (K key : property.keys())
					if (keySet.remove(key) && put) {
						//found a key that can be put!
						//only if the field haven't been set by previous key.
						property.setValue(instance, map.get(key));
						//block next assignments to the field!
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
		 * @return the previous value associated with the specified key, or null if there was no mapping for the key. (A
		 * 		null return can also indicate that the given {@code instance} previously associated null with the key).
		 * @throws NullPointerException          if the given {@code instance} is null.
		 * @throws UnsupportedOperationException if the given {@code instance} don't have the given {@code key}.
		 */
		public static <K, V> V putIfAbsent(Object instance, K key, V value) {
			Objects.requireNonNull(instance, "instance");

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance))
				for (K propertyKey : property.keys())
					//found a matching entry
					if (Objects.equals(key, propertyKey)) {
						V propertyValue = property.getValue(instance);

						if (propertyValue == null)
							property.setValue(instance, value);

						return propertyValue;
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

			PropertiesDescriptors<K, V> properties = new PropertiesDescriptors(instance);

			int length = stream.readInt();
			for0:
			for (int i = 0; i < length; i++) {
				K key = (K) stream.readObject();
				V value = (V) stream.readObject();

				for (PropertyDescriptor<K, V> property : properties)
					for (K propertyKey : property.keys())
						if (Objects.equals(key, propertyKey)) {
							property.setValue(instance, value);
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
		 * @throws UnsupportedOperationException if the given {@code key} is associated to the given {@code value} in
		 *                                       the given {@code instance}.
		 */
		public static <K, V> boolean remove(Object instance, K key, V value) {
			Objects.requireNonNull(instance, "instance");

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance))
				for (K propertyKey : property.keys())
					if (Objects.equals(key, propertyKey))
						if (Objects.equals(value, property.getValue(instance)))
							throw new UnsupportedOperationException("remove");
						else
							return false;

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

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance))
				for (K fieldKey : property.keys())
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
		 * @return the previous value associated with the specified key, or null if there was no mapping for the key. (A
		 * 		null return can also indicate that the map previously associated null with the key).
		 * @throws NullPointerException if the given {@code instance} is null.
		 */
		public static <K, V> V replace(Object instance, K key, V value) {
			Objects.requireNonNull(instance, "instance");

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance))
				for (K propertyKey : property.keys())
					if (Objects.equals(key, propertyKey)) {
						V oldValue = property.getValue(instance);
						property.setValue(instance, value);
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

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance))
				for (K propertyKey : property.keys())
					if (Objects.equals(key, propertyKey))
						if (Objects.equals(oldValue, property.getValue(instance))) {
							property.setValue(instance, newValue);
							return true;
						} else
							return false;

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

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance)) {
				V value = property.getValue(instance);

				for (K key : property.keys())
					value = function.apply(key, value);

				property.setValue(instance, value);
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
			for (PropertyDescriptor<K, V> descriptor : new PropertiesDescriptors<K, V>(instance))
				//don't forget "zero keys = singular key that is the name of the field"
				size += descriptor.keys().size();

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

			Iterator<PropertyDescriptor<K, V>> iterator = new PropertiesDescriptors<K, V>(instance).iterator();

			if (iterator.hasNext()) {
				StringBuilder builder = new StringBuilder("{");

				while (iterator.hasNext()) {
					PropertyDescriptor<K, V> descriptor = iterator.next();
					Object value = descriptor.getValue(instance);
					String valueString = value == instance ? "(this Bean)" : String.valueOf(value);

					Iterator<K> keys = descriptor.keys().iterator();
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

			stream.writeInt(RawMethods.size(instance));

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance)) {
				V value = property.getValue(instance);

				for (K key : property.keys()) {
					stream.writeObject(key);
					stream.writeObject(value);
				}
			}

			//null-termination
			stream.writeInt(0);
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
		public RawValues(Object instance) {
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
		public Iterator iterator() {
			return new Iterator();
		}

		@Override
		public int size() {
			return RawMethods.size(this.instance);
		}

		/**
		 * An iterator for beans' values-collections.
		 */
		public final class Iterator implements java.util.Iterator<V> {
			/**
			 * The backing iterator of this iterator for iterating the fields of the class of the instance of the
			 * entrySet of this iterator.
			 */
			private final java.util.Iterator<PropertyDescriptor<Object, V>> iterator =
					new PropertiesDescriptors<Object, V>(RawValues.this.instance).iterator();
			/**
			 * The current position in the keys of the current {@link #descriptor}.
			 */
			private int cursor;
			/**
			 * The current field.
			 */
			private PropertyDescriptor<?, V> descriptor;
			/**
			 * How many keys in the current {@link #descriptor}.
			 */
			private int keys;

			/**
			 * Construct a new bean's values-collection iterator.
			 */
			private Iterator() {
			}

			@Override
			public boolean hasNext() {
				//fields always have more than 1 key
				return this.iterator.hasNext() || this.descriptor != null && this.cursor < this.keys;
			}

			@Override
			public V next() {
				//the iterator.next() will do the job breaking the loop if no more fields available
				while (true) {
					//if there is a field previously partially solved
					if (this.descriptor != null && this.cursor++ < this.keys)
						//continue the to the next key of that field
						return this.descriptor.getValue(RawValues.this.instance);

					//the next field!
					PropertyDescriptor<Object, V> descriptor = this.iterator.next();
					this.descriptor = descriptor;
					//don't forget "zero keys = singular key that is the name of the field"
					//and since we don't care about the keys, we just max its length with 1
					this.keys = descriptor.keys().size();
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
		 * The fields that have not been checked by this.
		 */
		private final PropertiesDescriptors<K, V>.Iterator descriptors;
		/**
		 * The solved entries.
		 */
		private final Set<Entry<K, V>> entries = new HashSet();
		/**
		 * The instance this entrySet is for.
		 */
		private final Object instance;
		/**
		 * The keys allowed in this entrySet.
		 */
		private final Set<K> keys;
		/**
		 * A function to get the value of a key in the {@link #keys} set.
		 */
		private final BiFunction<? super K, ? super V, ? extends V> values;
		/**
		 * The index of the next unsolved-key in the last unsolved-descriptor.
		 */
		private int cursor;
		/**
		 * The last field gotten by the {@link #descriptors} iterator.
		 */
		private PropertyDescriptor<K, V> descriptor;
		/**
		 * How many unsolved-keys are available.
		 */
		private int fieldKeys;

		/**
		 * Construct a new entrySet containing entries for the specified {@code keys}.
		 *
		 * @param instance the instance of the constructed entrySet.
		 * @param keys     the keys contained in the constructed entrySet (the constructed set will modify this set!).
		 * @throws NullPointerException if the given {@code keys} is null.
		 */
		public TemporaryEntrySet(Object instance, Set<K> keys) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(keys, "keys");
			this.instance = instance;
			this.keys = keys;
			this.values = null;
			this.descriptors = new PropertiesDescriptors<K, V>(instance).iterator();
		}

		/**
		 * Construct a new entrySet containing entries for the specified {@code keys}.
		 *
		 * @param instance the instance of the constructed entrySet.
		 * @param keys     the keys contained in the constructed entrySet (the constructed set will modify this set!).
		 * @param values   a function to get the value of a key in the returned entrySet.
		 * @throws NullPointerException if the given {@code keys} is null.
		 */
		public TemporaryEntrySet(Object instance, Set<K> keys, BiFunction<? super K, ? super V, ? extends V> values) {
			Objects.requireNonNull(instance, "instance");
			Objects.requireNonNull(keys, "keys");
			Objects.requireNonNull(values, "values");
			this.instance = instance;
			this.keys = keys;
			this.values = values;
			this.descriptors = new PropertiesDescriptors<K, V>(instance).iterator();
		}

		@Override
		public Iterator iterator() {
			return new Iterator();
		}

		@Override
		public int size() {
			return this.keys.size();
		}

		/**
		 * An iterator iterating a set of entries specified by a set of keys.
		 */
		public final class Iterator implements java.util.Iterator<Entry<K, V>> {
			/**
			 * An iterator for the previously solved entries.
			 */
			private java.util.Iterator<Entry<K, V>> entries;
			/**
			 * An iterator for the unsolved keys.
			 */
			private java.util.Iterator<K> keys;

			/**
			 * Private access constructor.
			 */
			private Iterator() {
			}

			@Override
			public boolean hasNext() {
				//if there is still keys not resolved
				if (!TemporaryEntrySet.this.keys.isEmpty())
					//there is keys not solved means it will be solved in the next invoke of next()
					return true;

				//100% dependent stage; once achieved, it will stay forever!
				//if it is the first-time no more unsolved-keys.
				if (this.entries == null)
					//start iterating the over solved-entries
					this.entries = TemporaryEntrySet.this.entries.iterator();

				//it depends now 100% on the solved-entries
				return this.entries.hasNext();
			}

			@Override
			public Entry<K, V> next() {
				//while there is unsolved-keys. Otherwise, this block is redundant!
				while (!TemporaryEntrySet.this.keys.isEmpty()) {
					int cursor = TemporaryEntrySet.this.cursor++;
					int fieldKeys = TemporaryEntrySet.this.fieldKeys;

					//only if there is a leftover keys from the last field
					if (cursor < fieldKeys) {
						PropertyDescriptor<K, V> descriptor = TemporaryEntrySet.this.descriptor;

						//if still there is unseen keys from the last field
						K fieldKey = descriptor.keys().get(cursor);

						//only if there is a matching key in the unsolvedKeys set
						if (TemporaryEntrySet.this.keys.remove(fieldKey)) {
							//there is a matching key
							PropertyEntry<K, V> entry =
									TemporaryEntrySet.this.values == null ?
									//without initial-value
									descriptor.getEntry(
											TemporaryEntrySet.this.instance,
											fieldKey,
											cursor
									) :
									//with initial-value
									descriptor.getEntry(
											TemporaryEntrySet.this.instance,
											fieldKey,
											cursor,
											TemporaryEntrySet.this.values.apply(
													fieldKey,
													descriptor.getValue(TemporaryEntrySet.this.instance)
											)
									);

							//add the new entry; don't worry, the key already removed above!
							TemporaryEntrySet.this.entries.add(entry);
							return entry;
						}

						//no key matched in the last field's keys; to the next field!
						continue;
					}
					//only if there is fields not have been solved
					if (TemporaryEntrySet.this.descriptors.hasNext()) {
						//if no keys remaining from the last field
						PropertyDescriptor<K, V> descriptor = TemporaryEntrySet.this.descriptors.next();
						TemporaryEntrySet.this.descriptor = descriptor;
						TemporaryEntrySet.this.fieldKeys = descriptor.keys().size();
						TemporaryEntrySet.this.cursor = 0;
						//continue to consume the keys of the new unsolved-field
						continue;
					}

					//if it is the first-time no more fields nor last-field's keys.
					if (this.keys == null)
						//start iterating over the unsolved-keys
						this.keys = TemporaryEntrySet.this.keys.iterator();
					//if there is still unsolved-keys
					if (this.keys.hasNext()) {
						//if no more fields to be resolved, but still there is keys to be resolved
						K key = this.keys.next();

						//create a new entry
						Entry<K, V> entry =
								TemporaryEntrySet.this.values == null ?
								//with initial value
								new PropertylessEntry(
										key
								) :
								//without initial value
								new PropertylessEntry(
										key,
										TemporaryEntrySet.this.values.apply(
												key,
												null
										)
								);

						//remove it, because it has been solved
						this.keys.remove();
						//add the solved entry
						TemporaryEntrySet.this.entries.add(entry);
						return entry;
					}
				}

				//100% dependent stage; once achieved, it will stay forever!
				//if it is the first-time no more unsolved-keys.
				if (this.entries == null)
					//start iterating the over solved-entries
					this.entries = TemporaryEntrySet.this.entries.iterator();

				//it depends now 100% on the solved-entries
				return this.entries.next();
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

		/**
		 * Get a {@link PropertyEntry} for a field that have the given key in the given instance. Or a {@link
		 * PropertylessEntry} if there is no such field having the given {@code key} in the given {@code instance}. This
		 * method don't trust the given {@code key} for a {@link PropertyEntry}. The given {@code key} will not be used
		 * to construct a {@link PropertyEntry}. The key constructed to match the given {@code key} will be used
		 * instead.
		 *
		 * @param instance the instance that the returned entry is for a field in it.
		 * @param key      the key that the returned entry is having.
		 * @param <K>      the type of the key of the returned entry.
		 * @param <V>      the type of the value of the returned entry.
		 * @return a {@link PropertyEntry} for a field that have the given key in the given instance. Or a {@link
		 *        PropertylessEntry} if there is no such field having the given {@code key} in the given {@code instance}.
		 * @throws NullPointerException if the given {@code instance} is null.
		 */
		public static <K, V> Entry<K, V> from(Object instance, K key) {
			Objects.requireNonNull(instance, "instance");

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance)) {
				Iterator<K> iterator = property.keys().iterator();

				for (int i = 0; iterator.hasNext(); i++) {
					K propertyKey = iterator.next();

					if (Objects.equals(key, propertyKey))
						return property.getEntry(instance, propertyKey, i);
				}
			}

			return new PropertylessEntry(key);
		}

		/**
		 * Get a {@link PropertyEntry} for a field that have the given key in the given instance. Or a {@link
		 * PropertylessEntry} if there is no such field having the given {@code key} in the given {@code instance}. Then
		 * set the value of the given {@code field} to the given {@code value}. This method don't trust the given {@code
		 * key} for a {@link PropertyEntry}. The given {@code key} will not be used to construct a {@link
		 * PropertyEntry}. The key constructed to match the given {@code key} will be used instead.
		 *
		 * @param instance the instance that the returned entry is for a field in it.
		 * @param key      the key that the returned entry is having.
		 * @param value    the value to be set to the given {@code field}.
		 * @param <K>      the type of the key of the returned entry.
		 * @param <V>      the type of the value of the returned entry.
		 * @return a {@link PropertyEntry} for a field that have the given key in the given instance. Or a {@link
		 *        PropertylessEntry} if there is no such field having the given {@code key} in the given {@code instance}.
		 * @throws NullPointerException if the given {@code instance} is null.
		 */
		public static <K, V> Entry<K, V> from(Object instance, K key, V value) {
			Objects.requireNonNull(instance, "instance");

			for (PropertyDescriptor<K, V> property : new PropertiesDescriptors<K, V>(instance)) {
				Iterator<K> iterator = property.keys().iterator();

				for (int i = 0; iterator.hasNext(); i++) {
					K propertyKey = iterator.next();

					if (Objects.equals(key, propertyKey))
						return property.getEntry(instance, propertyKey, i, value);
				}
			}

			return new PropertylessEntry(key, value);
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
