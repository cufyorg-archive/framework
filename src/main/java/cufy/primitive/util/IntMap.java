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
package cufy.primitive.util;

import cufy.primitive.util.function.IntBiConsumer;
import cufy.primitive.util.function.IntBiFunction;
import cufy.primitive.util.function.IntObjBiFunction;
import cufy.util.Objects;
import cufy.util.PrimitiveMap;

import java.util.Map;
import java.util.Set;
import java.util.function.*;

/**
 * A map specialized for {@code int} values.
 *
 * @param <ENTRY_SET> the type of the set of the entries.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.02
 */
public interface IntMap<
		ENTRY_SET extends Set<Map.Entry<Integer, Integer>>
		> extends PrimitiveMap<
		Integer,
		Integer,
		IntBiConsumer,
		IntBinaryOperator,
		ENTRY_SET,
		IntSet,
		IntCollection,
		IntMap<ENTRY_SET>
		> {
	@Override
	default Integer compute(Integer key, BiFunction<? super Integer, ? super Integer, ? extends Integer> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = new boolean[1];
		int current = this.compute(
				key,
				(int k, Integer v) -> {
					Integer newValue = (Integer) (
							function instanceof IntObjBiFunction ?
							((IntObjBiFunction) function).apply(k, v) :
							function.apply(k, v)
					);
					notnull[0] = newValue != null;
					return newValue;
				}
		);
		return notnull[0] ?
			   current :
			   null;
	}

	@Override
	default Integer computeIfAbsent(Integer key, Function<? super Integer, ? extends Integer> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = new boolean[1];
		int current = this.computeIfAbsent(
				key,
				(int k) -> {
					Integer newValue = (Integer) (
							function instanceof IntFunction ?
							((IntFunction) function).apply(k) :
							function.apply(k)
					);
					notnull[0] = newValue != null;
					return newValue;
				}
		);
		return notnull[0] ?
			   current :
			   null;
	}

	@Override
	default Integer computeIfPresent(Integer key, BiFunction<? super Integer, ? super Integer, ? extends Integer> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = {this.containsKey(key)};
		int current = this.computeIfPresent(
				key,
				(int k, int v) -> {
					Integer newValue = (Integer) (
							function instanceof IntBiFunction ?
							((IntBiFunction) function).apply(k, v) :
							function.apply(k, v)
					);
					notnull[0] = newValue != null;
					return newValue;
				}
		);
		return notnull[0] ?
			   current :
			   null;
	}

	@Override
	default boolean containsKey(Object key) {
		return key instanceof Integer && this.containsKey((int) key);
	}

	@Override
	default boolean containsValue(Object value) {
		return value instanceof Integer && this.containsValue((int) value);
	}

	@Override
	default void forEach(BiConsumer<? super Integer, ? super Integer> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		this.forEach(
				consumer instanceof IntBiConsumer ?
				(IntBiConsumer) consumer :
				consumer::accept
		);
	}

	@Override
	default Integer get(Object key) {
		return key instanceof Integer &&
			   this.containsKey((int) key) ?
			   this.getInt((int) key) :
			   null;
	}

	@Override
	default Integer getOrDefault(Object key, Integer defaultValue) {
		//avoid using getOrDefaultInt, to allow the 'defaultValue' to be null
		return key instanceof Integer &&
			   this.containsKey((int) key) ?
			   this.getInt((int) key) :
			   defaultValue;
	}

	@Override
	default Integer merge(Integer key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> function) {
		Objects.requireNonNull(function, "function");
		Objects.requireNonNull(value, "value");
		boolean[] notnull = {this.containsKey(key)};
		int current = this.merge(
				key,
				value,
				(int k, int v) -> {
					Integer newValue = (Integer) (
							function instanceof IntBiFunction ?
							((IntBiFunction) function).apply(k, v) :
							function.apply(k, v)
					);
					notnull[0] = newValue != null;
					return newValue;
				}
		);
		return notnull[0] ?
			   current :
			   null;
	}

	@Override
	default Integer put(Integer key, Integer value) {
		boolean notnull = this.containsKey((int) key);
		int old = this.putInt(key, value);
		return notnull ?
			   old :
			   null;
	}

	@Override
	default Integer putIfAbsent(Integer key, Integer value) {
		if (this.containsKey((int) key))
			return this.getInt(key);

		this.putInt(key, value);
		return null;
	}

	@Override
	default Integer remove(Object key) {
		return key instanceof Integer &&
			   this.containsKey((int) key) ?
			   this.removeInt((int) key) :
			   null;
	}

	@Override
	default boolean remove(Object key, Object value) {
		return key instanceof Integer &&
			   value instanceof Integer &&
			   this.removeInt((int) key, (int) value);
	}

	@Override
	default Integer replace(Integer key, Integer value) {
		return this.containsKey((int) key) ?
			   this.replaceInt(key, value) :
			   null;
	}

	@Override
	default boolean replace(Integer key, Integer oldValue, Integer newValue) {
		return this.replaceInt(key, oldValue, newValue);
	}

	@Override
	default void replaceAll(BiFunction<? super Integer, ? super Integer, ? extends Integer> function) {
		Objects.requireNonNull(function, "function");
		this.replaceAll(
				function instanceof IntBinaryOperator ?
				(IntBinaryOperator) function :
				function::apply
		);
	}

	/**
	 * Attempts to compute a mapping for the specified key and its current mapped value.
	 * <p>
	 * If the function returns {@code null}, the mapping is removed (or remains absent if initially
	 * absent). If the function itself throws an (unchecked) exception, the exception is rethrown,
	 * and the current mapping is left unchanged.
	 *
	 * @param key      key with which the specified value is to be associated.
	 * @param function the function to compute a value.
	 * @return the new value associated with the specified key, or {@code default int value} if
	 * 		none.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default int compute(int key, IntObjBiFunction<Integer, Integer> function) {
		Objects.requireNonNull(function, "function");
		boolean oldNotnull = this.containsKey(key);
		Integer oldValue = oldNotnull ?
						   this.getInt(key) :
						   null;
		Integer newValue = function.apply(key, oldValue);

		if (newValue == null && oldNotnull) {
			this.removeInt(key);
			return 0;
		}

		this.putInt(key, newValue);
		return newValue;
	}

	/**
	 * If the specified key is not already associated with a value, attempts to compute its value
	 * using the given mapping function and enters it into this map unless {@code null}.
	 * <p>
	 * If the function returns {@code null} no mapping is recorded. If the function itself throws an
	 * (unchecked) exception, the exception is rethrown, and no mapping is recorded.
	 *
	 * @param key      key with which the specified value is to be associated.
	 * @param function the function to compute a value
	 * @return the current (existing or computed) value associated with the specified key, or {@code
	 * 		default int value} if the computed value is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default int computeIfAbsent(int key, IntFunction<Integer> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key))
			return this.getInt(key);

		Integer newValue = function.apply(key);

		if (newValue == null)
			return 0;

		this.putInt(key, newValue);
		return newValue;
	}

	/**
	 * If the value for the specified key is present, attempts to compute a new mapping given the
	 * key and its current mapped value.
	 * <p>
	 * If the function returns {@code null}, the mapping is removed. If the function itself throws
	 * an (unchecked) exception, the exception is rethrown, and the current mapping is left
	 * unchanged.
	 *
	 * @param key      key with which the specified value is to be associated.
	 * @param function the function to compute a value.
	 * @return the new value associated with the specified key, or {@code default int value} if
	 * 		none.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default int computeIfPresent(int key, IntBiFunction<Integer> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key)) {
			int oldValue = this.getInt(key);
			Integer newValue = function.apply(key, oldValue);

			if (newValue == null) {
				this.removeInt(key);
				return 0;
			}

			this.put(key, newValue);
			return newValue;
		}

		return 0;
	}

	/**
	 * Returns the value to which the specified key is mapped, or {@code defaultValue} if this map
	 * contains no mapping for the key.
	 *
	 * @param key          the key whose associated value is to be returned.
	 * @param defaultValue the default mapping of the key.
	 * @return the value to which the specified key is mapped, or {@code defaultValue} if this map
	 * 		contains no mapping for the key.
	 * @since 0.1.5 ~2020.09.03
	 */
	default int getOrDefaultInt(int key, int defaultValue) {
		return this.containsKey(key) ?
			   this.getInt(key) :
			   defaultValue;
	}

	/**
	 * If the specified key is not already associated with a value, associates it with the given
	 * value. Otherwise, replaces the associated value with the results of the given remapping
	 * function, or removes if the result is {@code null}.
	 * <p>
	 * If the function returns {@code null} the mapping is removed. If the function itself throws an
	 * (unchecked) exception, the exception is rethrown, and the current mapping is left unchanged.
	 *
	 * @param key      key with which the resulting value is to be associated.
	 * @param value    the value to be merged with the existing value associated with the key or, if
	 *                 no existing value, to be associated with the key.
	 * @param function the function to recompute a value if present.
	 * @return the new value associated with the specified key, or {@code default int value} if no
	 * 		value is associated with the key.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default int merge(int key, int value, IntBiFunction<Integer> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key)) {
			Integer newValue = function.apply(this.getInt(key), value);

			if (newValue == null) {
				this.removeInt(key);
				return 0;
			}

			this.putInt(key, newValue);
			return newValue;
		}

		this.putInt(key, value);
		return 0;
	}

	/**
	 * If the specified key is not already associated with a value, associates it with the given
	 * value and returns {@code default int value}, else returns the current value.
	 *
	 * @param key   key with which the specified value is to be associated.
	 * @param value value to be associated with the specified key.
	 * @return the previous value associated with the specified key, or {@code default int value} if
	 * 		there was no mapping for the key. (A {@code default int value} return can also indicate
	 * 		that the map previously associated {@code default int value} with the key.)
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.03
	 */
	default int putIntIfAbsent(int key, int value) {
		if (this.containsKey(key))
			return this.getInt(key);

		//will return `default int value`
		return this.putInt(key, value);
	}

	/**
	 * Removes the entry for the specified key only if it is currently mapped to the specified
	 * value.
	 *
	 * @param key   key with which the specified value is associated.
	 * @param value value expected to be associated with the specified key.
	 * @return {@code true} if the value was removed.
	 * @throws UnsupportedOperationException if the {@code remove} operation is not supported by
	 *                                       this map.
	 * @since 0.1.5 ~2020.09.03
	 */
	default boolean removeInt(int key, int value) {
		if (this.containsKey(key)) {
			int current = this.getInt(key);

			if (current == value) {
				this.removeInt(key);
				return true;
			}
		}

		return false;
	}

	/**
	 * Replaces the entry for the specified key only if currently mapped to the specified value.
	 *
	 * @param key      key with which the specified value is associated.
	 * @param oldValue value expected to be associated with the specified key.
	 * @param newValue value to be associated with the specified key.
	 * @return {@code true} if the value was replaced.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of a specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.03
	 */
	default boolean replaceInt(int key, int oldValue, int newValue) {
		if (this.containsKey(key)) {
			int current = this.getInt(key);

			if (current == oldValue) {
				this.putInt(key, newValue);
				return true;
			}
		}

		return false;
	}

	/**
	 * Replaces the entry for the specified key only if it is currently mapped to some value.
	 *
	 * @param key   key with which the specified value is associated.
	 * @param value value to be associated with the specified key.
	 * @return the previous value associated with the specified key, or {@code default int value} if
	 * 		there was no mapping for the key. (A {@code default int value} return can also indicate
	 * 		that the map previously associated {@code default int value} with the key).
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.02
	 */
	default int replaceInt(int key, int value) {
		return this.containsKey(key) ?
			   this.putInt(key, value) :
			   0;
	}

	/**
	 * Returns {@code true} if this map contains a mapping for the specified key.
	 *
	 * @param key key whose presence in this map is to be tested.
	 * @return {@code true} if this map contains a mapping for the specified key.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean containsKey(int key);

	/**
	 * Returns {@code true} if this map maps one or more keys to the specified value.
	 *
	 * @param value value whose presence in this map is to be tested.
	 * @return {@code true} if this map maps one or more keys to the specified value.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean containsValue(int value);

	/**
	 * Returns the value to which the specified key is mapped, or {@code default int value} if this
	 * map contains no mapping for the key.
	 * <p>
	 * A return value of {@code default int value} does not <i>necessarily</i> indicate that the map
	 * contains no mapping for the key; it's also possible that the map explicitly maps the key to
	 * {@code default int value}. The {@link #containsKey(int) containsKey} operation may be used to
	 * distinguish these two cases.
	 *
	 * @param key the key whose associated value is to be returned.
	 * @return the value to which the specified key is mapped, or {@code default int value} if this
	 * 		map contains no mapping for the key.
	 * @since 0.1.5 ~2020.09.02
	 */
	int getInt(int key);

	/**
	 * Associates the specified value with the specified key in this map (optional operation). If
	 * the map previously contained a mapping for the key, the old value is replaced by the
	 * specified value. (A map {@code m} is said to contain a mapping for a key <tt>k</tt> if and
	 * only if {@link #containsKey(int) m.containsKey(k)} would return {@code true}.).
	 *
	 * @param key   key with which the specified value is to be associated.
	 * @param value value to be associated with the specified key.
	 * @return the previous value associated with {@code key}, or {@code default int value} if there
	 * 		was no mapping for {@code key}.(A {@code default int value} return can also indicate that
	 * 		the map previously associated {@code default int value} with {@code key}).
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.02
	 */
	int putInt(int key, int value);

	/**
	 * Removes the mapping for a key from this map if it is present (optional operation).
	 * <p>
	 * Returns the value to which this map previously associated the key, or {@code default int
	 * value} if the map contained no mapping for the key.
	 * <p>
	 * A return value of {@code default int value} does not <i>necessarily</i> indicate that the map
	 * contained no mapping for the key; it's also possible that the map explicitly mapped the key
	 * to {@code default int value}.
	 * <p>
	 * The map will not contain a mapping for the specified key once the call returns.
	 *
	 * @param key key whose mapping is to be removed from the map.
	 * @return the previous value associated with {@code key}, or {@code default int value} if there
	 * 		was no mapping for {@code key}.
	 * @throws UnsupportedOperationException if the {@code remove} operation is not supported by
	 *                                       this map.
	 * @since 0.1.5 ~2020.09.03
	 */
	int removeInt(int key);

	/**
	 * An entry specialized for {@code int} values.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.02
	 */
	interface IntEntry extends PrimitiveEntry<
			Integer,
			Integer
			> {
		@Override
		default Integer getKey() {
			return this.getIntKey();
		}

		@Override
		default Integer getValue() {
			return this.getIntValue();
		}

		@Override
		default Integer setValue(Integer value) {
			return this.setIntValue(value);
		}

		/**
		 * Returns the key corresponding to this entry.
		 *
		 * @return the key corresponding to this entry
		 * @throws IllegalStateException implementations may, but are not required to, throw this
		 *                               exception if the entry has been removed from the backing
		 *                               map.
		 * @since 0.1.5 ~2020.09.02
		 */
		int getIntKey();

		/**
		 * Returns the value corresponding to this entry. If the mapping has been removed from the
		 * backing map (by the iterator's {@code remove} operation), the results of this call are
		 * undefined.
		 *
		 * @return the value corresponding to this entry.
		 * @throws IllegalStateException implementations may, but are not required to, throw this
		 *                               exception if the entry has been removed from the backing
		 *                               map.
		 * @since 0.1.5 ~2020.09.02
		 */
		int getIntValue();

		/**
		 * Replaces the value corresponding to this entry with the specified value (optional
		 * operation). (Writes through to the map.) The behavior of this call is undefined if the
		 * mapping has already been removed from the map (by the iterator's {@code remove}
		 * operation).
		 *
		 * @param value new value to be stored in this entry.
		 * @return old value corresponding to the entry.
		 * @throws UnsupportedOperationException if the {@code put} operation is not supported by
		 *                                       the backing map.
		 * @throws IllegalArgumentException      if some property of this value prevents it from
		 *                                       being stored in the backing map.
		 * @throws IllegalStateException         implementations may, but are not required to, throw
		 *                                       this exception if the entry has been removed from
		 *                                       the backing map.
		 * @since 0.1.5 ~2020.09.02
		 */
		int setIntValue(int value);
	}
}
