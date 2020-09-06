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

import cufy.primitive.util.function.*;
import cufy.util.Objects;
import cufy.util.PrimitiveMap;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A map specialized for {@code boolean} values.
 *
 * @param <ENTRY_SET> the type of the set of the entries.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.02
 */
public interface BooleanMap<
		ENTRY_SET extends Set<Map.Entry<Boolean, Boolean>>
		> extends PrimitiveMap<
		Boolean,
		Boolean,
		BooleanBiConsumer,
		BooleanBinaryOperator,
		ENTRY_SET,
		BooleanSet,
		BooleanCollection,
		BooleanMap<ENTRY_SET>
		> {
	@Override
	default Boolean compute(Boolean key, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = new boolean[1];
		boolean current = this.compute(
				key,
				(boolean k, Boolean v) -> {
					Boolean newValue = (Boolean) (
							function instanceof BooleanObjBiFunction ?
							((BooleanObjBiFunction) function).apply(k, v) :
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
	default Boolean computeIfAbsent(Boolean key, Function<? super Boolean, ? extends Boolean> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = new boolean[1];
		boolean current = this.computeIfAbsent(
				key,
				(boolean k) -> {
					Boolean newValue = (Boolean) (
							function instanceof BooleanFunction ?
							((BooleanFunction) function).apply(k) :
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
	default Boolean computeIfPresent(Boolean key, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = {this.containsKey(key)};
		boolean current = this.computeIfPresent(
				key,
				(boolean k, boolean v) -> {
					Boolean newValue = (Boolean) (
							function instanceof BooleanBiFunction ?
							((BooleanBiFunction) function).apply(k, v) :
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
		return key instanceof Boolean && this.containsKey((boolean) key);
	}

	@Override
	default boolean containsValue(Object value) {
		return value instanceof Boolean && this.containsValue((boolean) value);
	}

	@Override
	default void forEach(BiConsumer<? super Boolean, ? super Boolean> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		this.forEach(
				consumer instanceof BooleanBiConsumer ?
				(BooleanBiConsumer) consumer :
				consumer::accept
		);
	}

	@Override
	default Boolean get(Object key) {
		return key instanceof Boolean &&
			   this.containsKey((boolean) key) ?
			   this.getBoolean((boolean) key) :
			   null;
	}

	@Override
	default Boolean getOrDefault(Object key, Boolean defaultValue) {
		//avoid using getOrDefaultBoolean, to allow the 'defaultValue' to be null
		return key instanceof Boolean &&
			   this.containsKey((boolean) key) ?
			   this.getBoolean((boolean) key) :
			   defaultValue;
	}

	@Override
	default Boolean merge(Boolean key, Boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> function) {
		Objects.requireNonNull(function, "function");
		Objects.requireNonNull(value, "value");
		boolean[] notnull = {this.containsKey(key)};
		boolean current = this.merge(
				key,
				value,
				(boolean k, boolean v) -> {
					Boolean newValue = (Boolean) (
							function instanceof BooleanBiFunction ?
							((BooleanBiFunction) function).apply(k, v) :
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
	default Boolean put(Boolean key, Boolean value) {
		boolean notnull = this.containsKey((boolean) key);
		boolean old = this.putBoolean(key, value);
		return notnull ?
			   old :
			   null;
	}

	@Override
	default Boolean putIfAbsent(Boolean key, Boolean value) {
		if (this.containsKey((boolean) key))
			return this.getBoolean(key);

		this.putBoolean(key, value);
		return null;
	}

	@Override
	default Boolean remove(Object key) {
		return key instanceof Boolean &&
			   this.containsKey((boolean) key) ?
			   this.removeBoolean((boolean) key) :
			   null;
	}

	@Override
	default boolean remove(Object key, Object value) {
		return key instanceof Boolean &&
			   value instanceof Boolean &&
			   this.removeBoolean((boolean) key, (boolean) value);
	}

	@Override
	default Boolean replace(Boolean key, Boolean value) {
		return this.containsKey((boolean) key) ?
			   this.replaceBoolean(key, value) :
			   null;
	}

	@Override
	default boolean replace(Boolean key, Boolean oldValue, Boolean newValue) {
		return this.replaceBoolean(key, oldValue, newValue);
	}

	@Override
	default void replaceAll(BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> function) {
		Objects.requireNonNull(function, "function");
		this.replaceAll(
				function instanceof BooleanBinaryOperator ?
				(BooleanBinaryOperator) function :
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
	 * @return the new value associated with the specified key, or {@code default boolean value} if
	 * 		none.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default boolean compute(boolean key, BooleanObjBiFunction<Boolean, Boolean> function) {
		Objects.requireNonNull(function, "function");
		boolean oldNotnull = this.containsKey(key);
		Boolean oldValue = oldNotnull ?
						   this.getBoolean(key) :
						   null;
		Boolean newValue = function.apply(key, oldValue);

		if (newValue == null && oldNotnull) {
			this.removeBoolean(key);
			return false;
		}

		this.putBoolean(key, newValue);
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
	 * 		default boolean value} if the computed value is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default boolean computeIfAbsent(boolean key, BooleanFunction<Boolean> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key))
			return this.getBoolean(key);

		Boolean newValue = function.apply(key);

		if (newValue == null)
			return false;

		this.putBoolean(key, newValue);
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
	 * @return the new value associated with the specified key, or {@code default boolean value} if
	 * 		none.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default boolean computeIfPresent(boolean key, BooleanBiFunction<Boolean> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key)) {
			boolean oldValue = this.getBoolean(key);
			Boolean newValue = function.apply(key, oldValue);

			if (newValue == null) {
				this.removeBoolean(key);
				return false;
			}

			this.put(key, newValue);
			return newValue;
		}

		return false;
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
	default boolean getOrDefaultBoolean(boolean key, boolean defaultValue) {
		return this.containsKey(key) ?
			   this.getBoolean(key) :
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
	 * @return the new value associated with the specified key, or {@code default boolean value} if
	 * 		no value is associated with the key.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default boolean merge(boolean key, boolean value, BooleanBiFunction<Boolean> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key)) {
			Boolean newValue = function.apply(this.getBoolean(key), value);

			if (newValue == null) {
				this.removeBoolean(key);
				return false;
			}

			this.putBoolean(key, newValue);
			return newValue;
		}

		this.putBoolean(key, value);
		return false;
	}

	/**
	 * If the specified key is not already associated with a value, associates it with the given
	 * value and returns {@code default boolean value}, else returns the current value.
	 *
	 * @param key   key with which the specified value is to be associated.
	 * @param value value to be associated with the specified key.
	 * @return the previous value associated with the specified key, or {@code default boolean
	 * 		value} if there was no mapping for the key. (A {@code default boolean value} return can
	 * 		also indicate that the map previously associated {@code default boolean value} with the
	 * 		key.)
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.03
	 */
	default boolean putBooleanIfAbsent(boolean key, boolean value) {
		if (this.containsKey(key))
			return this.getBoolean(key);

		//will return `default boolean value`
		return this.putBoolean(key, value);
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
	default boolean removeBoolean(boolean key, boolean value) {
		if (this.containsKey(key)) {
			boolean current = this.getBoolean(key);

			if (current == value) {
				this.removeBoolean(key);
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
	default boolean replaceBoolean(boolean key, boolean oldValue, boolean newValue) {
		if (this.containsKey(key)) {
			boolean current = this.getBoolean(key);

			if (current == oldValue) {
				this.putBoolean(key, newValue);
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
	 * @return the previous value associated with the specified key, or {@code default boolean
	 * 		value} if there was no mapping for the key. (A {@code default boolean value} return can
	 * 		also indicate that the map previously associated {@code default boolean value} with the
	 * 		key).
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.02
	 */
	default boolean replaceBoolean(boolean key, boolean value) {
		return this.containsKey(key) &&
			   this.putBoolean(key, value);
	}

	/**
	 * Returns {@code true} if this map contains a mapping for the specified key.
	 *
	 * @param key key whose presence in this map is to be tested.
	 * @return {@code true} if this map contains a mapping for the specified key.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean containsKey(boolean key);

	/**
	 * Returns {@code true} if this map maps one or more keys to the specified value.
	 *
	 * @param value value whose presence in this map is to be tested.
	 * @return {@code true} if this map maps one or more keys to the specified value.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean containsValue(boolean value);

	/**
	 * Returns the value to which the specified key is mapped, or {@code default boolean value} if
	 * this map contains no mapping for the key.
	 * <p>
	 * A return value of {@code default boolean value} does not <i>necessarily</i> indicate that the
	 * map contains no mapping for the key; it's also possible that the map explicitly maps the key
	 * to {@code default boolean value}. The {@link #containsKey(boolean) containsKey} operation may
	 * be used to distinguish these two cases.
	 *
	 * @param key the key whose associated value is to be returned.
	 * @return the value to which the specified key is mapped, or {@code default boolean value} if
	 * 		this map contains no mapping for the key.
	 * @since 0.1.5 ~2020.09.02
	 */
	boolean getBoolean(boolean key);

	/**
	 * Associates the specified value with the specified key in this map (optional operation). If
	 * the map previously contained a mapping for the key, the old value is replaced by the
	 * specified value. (A map {@code m} is said to contain a mapping for a key <tt>k</tt> if and
	 * only if {@link #containsKey(boolean) m.containsKey(k)} would return {@code true}.).
	 *
	 * @param key   key with which the specified value is to be associated.
	 * @param value value to be associated with the specified key.
	 * @return the previous value associated with {@code key}, or {@code default boolean value} if
	 * 		there was no mapping for {@code key}.(A {@code default boolean value} return can also
	 * 		indicate that the map previously associated {@code default boolean value} with {@code
	 * 		key}).
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.02
	 */
	boolean putBoolean(boolean key, boolean value);

	/**
	 * Removes the mapping for a key from this map if it is present (optional operation).
	 * <p>
	 * Returns the value to which this map previously associated the key, or {@code default boolean
	 * value} if the map contained no mapping for the key.
	 * <p>
	 * A return value of {@code default boolean value} does not <i>necessarily</i> indicate that the
	 * map contained no mapping for the key; it's also possible that the map explicitly mapped the
	 * key to {@code default boolean value}.
	 * <p>
	 * The map will not contain a mapping for the specified key once the call returns.
	 *
	 * @param key key whose mapping is to be removed from the map.
	 * @return the previous value associated with {@code key}, or {@code default boolean value} if
	 * 		there was no mapping for {@code key}.
	 * @throws UnsupportedOperationException if the {@code remove} operation is not supported by
	 *                                       this map.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean removeBoolean(boolean key);

	/**
	 * An entry specialized for {@code boolean} values.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.02
	 */
	interface BooleanEntry extends PrimitiveEntry<
			Boolean,
			Boolean
			> {
		@Override
		default Boolean getKey() {
			return this.getBooleanKey();
		}

		@Override
		default Boolean getValue() {
			return this.getBooleanValue();
		}

		@Override
		default Boolean setValue(Boolean value) {
			return this.setBooleanValue(value);
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
		boolean getBooleanKey();

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
		boolean getBooleanValue();

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
		boolean setBooleanValue(boolean value);
	}
}
