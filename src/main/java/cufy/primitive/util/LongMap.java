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

import cufy.primitive.util.function.LongBiConsumer;
import cufy.primitive.util.function.LongBiFunction;
import cufy.primitive.util.function.LongObjBiFunction;
import cufy.util.Objects;
import cufy.util.PrimitiveMap;

import java.util.Map;
import java.util.Set;
import java.util.function.*;

/**
 * A map specialized for {@code long} values.
 *
 * @param <ENTRY_SET> the type of the set of the entries.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.02
 */
public interface LongMap<
		ENTRY_SET extends Set<Map.Entry<Long, Long>>
		> extends PrimitiveMap<
		Long,
		Long,
		LongBiConsumer,
		LongBinaryOperator,
		ENTRY_SET,
		LongSet,
		LongCollection,
		LongMap<ENTRY_SET>
		> {
	@Override
	default Long compute(Long key, BiFunction<? super Long, ? super Long, ? extends Long> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = new boolean[1];
		long current = this.compute(
				key,
				(long k, Long v) -> {
					Long newValue = (Long) (
							function instanceof LongObjBiFunction ?
							((LongObjBiFunction) function).apply(k, v) :
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
	default Long computeIfAbsent(Long key, Function<? super Long, ? extends Long> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = new boolean[1];
		long current = this.computeIfAbsent(
				key,
				(long k) -> {
					Long newValue = (Long) (
							function instanceof LongFunction ?
							((LongFunction) function).apply(k) :
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
	default Long computeIfPresent(Long key, BiFunction<? super Long, ? super Long, ? extends Long> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = {this.containsKey(key)};
		long current = this.computeIfPresent(
				key,
				(long k, long v) -> {
					Long newValue = (Long) (
							function instanceof LongBiFunction ?
							((LongBiFunction) function).apply(k, v) :
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
		return key instanceof Long && this.containsKey((long) key);
	}

	@Override
	default boolean containsValue(Object value) {
		return value instanceof Long && this.containsValue((long) value);
	}

	@Override
	default void forEach(BiConsumer<? super Long, ? super Long> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		this.forEach(
				consumer instanceof LongBiConsumer ?
				(LongBiConsumer) consumer :
				consumer::accept
		);
	}

	@Override
	default Long get(Object key) {
		return key instanceof Long &&
			   this.containsKey((long) key) ?
			   this.getLong((long) key) :
			   null;
	}

	@Override
	default Long getOrDefault(Object key, Long defaultValue) {
		//avoid using getOrDefaultLong, to allow the 'defaultValue' to be null
		return key instanceof Long &&
			   this.containsKey((long) key) ?
			   this.getLong((long) key) :
			   defaultValue;
	}

	@Override
	default Long merge(Long key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> function) {
		Objects.requireNonNull(function, "function");
		Objects.requireNonNull(value, "value");
		boolean[] notnull = {this.containsKey(key)};
		long current = this.merge(
				key,
				value,
				(long k, long v) -> {
					Long newValue = (Long) (
							function instanceof LongBiFunction ?
							((LongBiFunction) function).apply(k, v) :
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
	default Long put(Long key, Long value) {
		boolean notnull = this.containsKey((long) key);
		long old = this.putLong(key, value);
		return notnull ?
			   old :
			   null;
	}

	@Override
	default Long putIfAbsent(Long key, Long value) {
		if (this.containsKey((long) key))
			return this.getLong(key);

		this.putLong(key, value);
		return null;
	}

	@Override
	default Long remove(Object key) {
		return key instanceof Long &&
			   this.containsKey((long) key) ?
			   this.removeLong((long) key) :
			   null;
	}

	@Override
	default boolean remove(Object key, Object value) {
		return key instanceof Long &&
			   value instanceof Long &&
			   this.removeLong((long) key, (long) value);
	}

	@Override
	default Long replace(Long key, Long value) {
		return this.containsKey((long) key) ?
			   this.replaceLong(key, value) :
			   null;
	}

	@Override
	default boolean replace(Long key, Long oldValue, Long newValue) {
		return this.replaceLong(key, oldValue, newValue);
	}

	@Override
	default void replaceAll(BiFunction<? super Long, ? super Long, ? extends Long> function) {
		Objects.requireNonNull(function, "function");
		this.replaceAll(
				function instanceof LongBinaryOperator ?
				(LongBinaryOperator) function :
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
	 * @return the new value associated with the specified key, or {@code default long value} if
	 * 		none.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default long compute(long key, LongObjBiFunction<Long, Long> function) {
		Objects.requireNonNull(function, "function");
		boolean oldNotnull = this.containsKey(key);
		Long oldValue = oldNotnull ?
						this.getLong(key) :
						null;
		Long newValue = function.apply(key, oldValue);

		if (newValue == null && oldNotnull) {
			this.removeLong(key);
			return 0L;
		}

		this.putLong(key, newValue);
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
	 * 		default long value} if the computed value is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default long computeIfAbsent(long key, LongFunction<Long> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key))
			return this.getLong(key);

		Long newValue = function.apply(key);

		if (newValue == null)
			return 0L;

		this.putLong(key, newValue);
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
	 * @return the new value associated with the specified key, or {@code default long value} if
	 * 		none.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default long computeIfPresent(long key, LongBiFunction<Long> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key)) {
			long oldValue = this.getLong(key);
			Long newValue = function.apply(key, oldValue);

			if (newValue == null) {
				this.removeLong(key);
				return 0L;
			}

			this.put(key, newValue);
			return newValue;
		}

		return 0L;
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
	default long getOrDefaultLong(long key, long defaultValue) {
		return this.containsKey(key) ?
			   this.getLong(key) :
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
	 * @return the new value associated with the specified key, or {@code default long value} if no
	 * 		value is associated with the key.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default long merge(long key, long value, LongBiFunction<Long> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key)) {
			Long newValue = function.apply(this.getLong(key), value);

			if (newValue == null) {
				this.removeLong(key);
				return 0L;
			}

			this.putLong(key, newValue);
			return newValue;
		}

		this.putLong(key, value);
		return 0L;
	}

	/**
	 * If the specified key is not already associated with a value, associates it with the given
	 * value and returns {@code default long value}, else returns the current value.
	 *
	 * @param key   key with which the specified value is to be associated.
	 * @param value value to be associated with the specified key.
	 * @return the previous value associated with the specified key, or {@code default long value}
	 * 		if there was no mapping for the key. (A {@code default long value} return can also indicate
	 * 		that the map previously associated {@code default long value} with the key.)
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.03
	 */
	default long putLongIfAbsent(long key, long value) {
		if (this.containsKey(key))
			return this.getLong(key);

		//will return `default long value`
		return this.putLong(key, value);
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
	default boolean removeLong(long key, long value) {
		if (this.containsKey(key)) {
			long current = this.getLong(key);

			if (current == value) {
				this.removeLong(key);
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
	default boolean replaceLong(long key, long oldValue, long newValue) {
		if (this.containsKey(key)) {
			long current = this.getLong(key);

			if (current == oldValue) {
				this.putLong(key, newValue);
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
	 * @return the previous value associated with the specified key, or {@code default long value}
	 * 		if there was no mapping for the key. (A {@code default long value} return can also indicate
	 * 		that the map previously associated {@code default long value} with the key).
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.02
	 */
	default long replaceLong(long key, long value) {
		return this.containsKey(key) ?
			   this.putLong(key, value) :
			   0L;
	}

	/**
	 * Returns {@code true} if this map contains a mapping for the specified key.
	 *
	 * @param key key whose presence in this map is to be tested.
	 * @return {@code true} if this map contains a mapping for the specified key.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean containsKey(long key);

	/**
	 * Returns {@code true} if this map maps one or more keys to the specified value.
	 *
	 * @param value value whose presence in this map is to be tested.
	 * @return {@code true} if this map maps one or more keys to the specified value.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean containsValue(long value);

	/**
	 * Returns the value to which the specified key is mapped, or {@code default long value} if this
	 * map contains no mapping for the key.
	 * <p>
	 * A return value of {@code default long value} does not <i>necessarily</i> indicate that the
	 * map contains no mapping for the key; it's also possible that the map explicitly maps the key
	 * to {@code default long value}. The {@link #containsKey(long) containsKey} operation may be
	 * used to distinguish these two cases.
	 *
	 * @param key the key whose associated value is to be returned.
	 * @return the value to which the specified key is mapped, or {@code default long value} if this
	 * 		map contains no mapping for the key.
	 * @since 0.1.5 ~2020.09.02
	 */
	long getLong(long key);

	/**
	 * Associates the specified value with the specified key in this map (optional operation). If
	 * the map previously contained a mapping for the key, the old value is replaced by the
	 * specified value. (A map {@code m} is said to contain a mapping for a key <tt>k</tt> if and
	 * only if {@link #containsKey(long) m.containsKey(k)} would return {@code true}.).
	 *
	 * @param key   key with which the specified value is to be associated.
	 * @param value value to be associated with the specified key.
	 * @return the previous value associated with {@code key}, or {@code default long value} if
	 * 		there was no mapping for {@code key}.(A {@code default long value} return can also indicate
	 * 		that the map previously associated {@code default long value} with {@code key}).
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.02
	 */
	long putLong(long key, long value);

	/**
	 * Removes the mapping for a key from this map if it is present (optional operation).
	 * <p>
	 * Returns the value to which this map previously associated the key, or {@code default long
	 * value} if the map contained no mapping for the key.
	 * <p>
	 * A return value of {@code default long value} does not <i>necessarily</i> indicate that the
	 * map contained no mapping for the key; it's also possible that the map explicitly mapped the
	 * key to {@code default long value}.
	 * <p>
	 * The map will not contain a mapping for the specified key once the call returns.
	 *
	 * @param key key whose mapping is to be removed from the map.
	 * @return the previous value associated with {@code key}, or {@code default long value} if
	 * 		there was no mapping for {@code key}.
	 * @throws UnsupportedOperationException if the {@code remove} operation is not supported by
	 *                                       this map.
	 * @since 0.1.5 ~2020.09.03
	 */
	long removeLong(long key);

	/**
	 * An entry specialized for {@code long} values.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.02
	 */
	interface LongEntry extends PrimitiveEntry<
			Long,
			Long
			> {
		@Override
		default Long getKey() {
			return this.getLongKey();
		}

		@Override
		default Long getValue() {
			return this.getLongValue();
		}

		@Override
		default Long setValue(Long value) {
			return this.setLongValue(value);
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
		long getLongKey();

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
		long getLongValue();

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
		long setLongValue(long value);
	}
}
