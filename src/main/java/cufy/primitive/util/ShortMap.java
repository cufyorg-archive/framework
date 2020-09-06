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
 * A map specialized for {@code short} values.
 *
 * @param <ENTRY_SET> the type of the set of the entries.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.02
 */
public interface ShortMap<
		ENTRY_SET extends Set<Map.Entry<Short, Short>>
		> extends PrimitiveMap<
		Short,
		Short,
		ShortBiConsumer,
		ShortBinaryOperator,
		ENTRY_SET,
		ShortSet,
		ShortCollection,
		ShortMap<ENTRY_SET>
		> {
	@Override
	default Short compute(Short key, BiFunction<? super Short, ? super Short, ? extends Short> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = new boolean[1];
		short current = this.compute(
				key,
				(short k, Short v) -> {
					Short newValue = (Short) (
							function instanceof ShortObjBiFunction ?
							((ShortObjBiFunction) function).apply(k, v) :
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
	default Short computeIfAbsent(Short key, Function<? super Short, ? extends Short> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = new boolean[1];
		short current = this.computeIfAbsent(
				key,
				(short k) -> {
					Short newValue = (Short) (
							function instanceof ShortFunction ?
							((ShortFunction) function).apply(k) :
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
	default Short computeIfPresent(Short key, BiFunction<? super Short, ? super Short, ? extends Short> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = {this.containsKey(key)};
		short current = this.computeIfPresent(
				key,
				(short k, short v) -> {
					Short newValue = (Short) (
							function instanceof ShortBiFunction ?
							((ShortBiFunction) function).apply(k, v) :
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
		return key instanceof Short && this.containsKey((short) key);
	}

	@Override
	default boolean containsValue(Object value) {
		return value instanceof Short && this.containsValue((short) value);
	}

	@Override
	default void forEach(BiConsumer<? super Short, ? super Short> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		this.forEach(
				consumer instanceof ShortBiConsumer ?
				(ShortBiConsumer) consumer :
				consumer::accept
		);
	}

	@Override
	default Short get(Object key) {
		return key instanceof Short &&
			   this.containsKey((short) key) ?
			   this.getShort((short) key) :
			   null;
	}

	@Override
	default Short getOrDefault(Object key, Short defaultValue) {
		//avoid using getOrDefaultShort, to allow the 'defaultValue' to be null
		return key instanceof Short &&
			   this.containsKey((short) key) ?
			   this.getShort((short) key) :
			   defaultValue;
	}

	@Override
	default Short merge(Short key, Short value, BiFunction<? super Short, ? super Short, ? extends Short> function) {
		Objects.requireNonNull(function, "function");
		Objects.requireNonNull(value, "value");
		boolean[] notnull = {this.containsKey(key)};
		short current = this.merge(
				key,
				value,
				(short k, short v) -> {
					Short newValue = (Short) (
							function instanceof ShortBiFunction ?
							((ShortBiFunction) function).apply(k, v) :
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
	default Short put(Short key, Short value) {
		boolean notnull = this.containsKey((short) key);
		short old = this.putShort(key, value);
		return notnull ?
			   old :
			   null;
	}

	@Override
	default Short putIfAbsent(Short key, Short value) {
		if (this.containsKey((short) key))
			return this.getShort(key);

		this.putShort(key, value);
		return null;
	}

	@Override
	default Short remove(Object key) {
		return key instanceof Short &&
			   this.containsKey((short) key) ?
			   this.removeShort((short) key) :
			   null;
	}

	@Override
	default boolean remove(Object key, Object value) {
		return key instanceof Short &&
			   value instanceof Short &&
			   this.removeShort((short) key, (short) value);
	}

	@Override
	default Short replace(Short key, Short value) {
		return this.containsKey((short) key) ?
			   this.replaceShort(key, value) :
			   null;
	}

	@Override
	default boolean replace(Short key, Short oldValue, Short newValue) {
		return this.replaceShort(key, oldValue, newValue);
	}

	@Override
	default void replaceAll(BiFunction<? super Short, ? super Short, ? extends Short> function) {
		Objects.requireNonNull(function, "function");
		this.replaceAll(
				function instanceof ShortBinaryOperator ?
				(ShortBinaryOperator) function :
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
	 * @return the new value associated with the specified key, or {@code default short value} if
	 * 		none.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default short compute(short key, ShortObjBiFunction<Short, Short> function) {
		Objects.requireNonNull(function, "function");
		boolean oldNotnull = this.containsKey(key);
		Short oldValue = oldNotnull ?
						 this.getShort(key) :
						 null;
		Short newValue = function.apply(key, oldValue);

		if (newValue == null && oldNotnull) {
			this.removeShort(key);
			return 0;
		}

		this.putShort(key, newValue);
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
	 * 		default short value} if the computed value is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default short computeIfAbsent(short key, ShortFunction<Short> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key))
			return this.getShort(key);

		Short newValue = function.apply(key);

		if (newValue == null)
			return 0;

		this.putShort(key, newValue);
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
	 * @return the new value associated with the specified key, or {@code default short value} if
	 * 		none.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default short computeIfPresent(short key, ShortBiFunction<Short> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key)) {
			short oldValue = this.getShort(key);
			Short newValue = function.apply(key, oldValue);

			if (newValue == null) {
				this.removeShort(key);
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
	default short getOrDefaultShort(short key, short defaultValue) {
		return this.containsKey(key) ?
			   this.getShort(key) :
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
	 * @return the new value associated with the specified key, or {@code default short value} if no
	 * 		value is associated with the key.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default short merge(short key, short value, ShortBiFunction<Short> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key)) {
			Short newValue = function.apply(this.getShort(key), value);

			if (newValue == null) {
				this.removeShort(key);
				return 0;
			}

			this.putShort(key, newValue);
			return newValue;
		}

		this.putShort(key, value);
		return 0;
	}

	/**
	 * If the specified key is not already associated with a value, associates it with the given
	 * value and returns {@code default short value}, else returns the current value.
	 *
	 * @param key   key with which the specified value is to be associated.
	 * @param value value to be associated with the specified key.
	 * @return the previous value associated with the specified key, or {@code default short value}
	 * 		if there was no mapping for the key. (A {@code default short value} return can also
	 * 		indicate that the map previously associated {@code default short value} with the key.)
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.03
	 */
	default short putShortIfAbsent(short key, short value) {
		if (this.containsKey(key))
			return this.getShort(key);

		//will return `default short value`
		return this.putShort(key, value);
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
	default boolean removeShort(short key, short value) {
		if (this.containsKey(key)) {
			short current = this.getShort(key);

			if (current == value) {
				this.removeShort(key);
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
	default boolean replaceShort(short key, short oldValue, short newValue) {
		if (this.containsKey(key)) {
			short current = this.getShort(key);

			if (current == oldValue) {
				this.putShort(key, newValue);
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
	 * @return the previous value associated with the specified key, or {@code default short value}
	 * 		if there was no mapping for the key. (A {@code default short value} return can also
	 * 		indicate that the map previously associated {@code default short value} with the key).
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.02
	 */
	default short replaceShort(short key, short value) {
		return this.containsKey(key) ?
			   this.putShort(key, value) :
			   0;
	}

	/**
	 * Returns {@code true} if this map contains a mapping for the specified key.
	 *
	 * @param key key whose presence in this map is to be tested.
	 * @return {@code true} if this map contains a mapping for the specified key.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean containsKey(short key);

	/**
	 * Returns {@code true} if this map maps one or more keys to the specified value.
	 *
	 * @param value value whose presence in this map is to be tested.
	 * @return {@code true} if this map maps one or more keys to the specified value.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean containsValue(short value);

	/**
	 * Returns the value to which the specified key is mapped, or {@code default short value} if
	 * this map contains no mapping for the key.
	 * <p>
	 * A return value of {@code default short value} does not <i>necessarily</i> indicate that the
	 * map contains no mapping for the key; it's also possible that the map explicitly maps the key
	 * to {@code default short value}. The {@link #containsKey(short) containsKey} operation may be
	 * used to distinguish these two cases.
	 *
	 * @param key the key whose associated value is to be returned.
	 * @return the value to which the specified key is mapped, or {@code default short value} if
	 * 		this map contains no mapping for the key.
	 * @since 0.1.5 ~2020.09.02
	 */
	short getShort(short key);

	/**
	 * Associates the specified value with the specified key in this map (optional operation). If
	 * the map previously contained a mapping for the key, the old value is replaced by the
	 * specified value. (A map {@code m} is said to contain a mapping for a key <tt>k</tt> if and
	 * only if {@link #containsKey(short) m.containsKey(k)} would return {@code true}.).
	 *
	 * @param key   key with which the specified value is to be associated.
	 * @param value value to be associated with the specified key.
	 * @return the previous value associated with {@code key}, or {@code default short value} if
	 * 		there was no mapping for {@code key}.(A {@code default short value} return can also
	 * 		indicate that the map previously associated {@code default short value} with {@code key}).
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.02
	 */
	short putShort(short key, short value);

	/**
	 * Removes the mapping for a key from this map if it is present (optional operation).
	 * <p>
	 * Returns the value to which this map previously associated the key, or {@code default short
	 * value} if the map contained no mapping for the key.
	 * <p>
	 * A return value of {@code default short value} does not <i>necessarily</i> indicate that the
	 * map contained no mapping for the key; it's also possible that the map explicitly mapped the
	 * key to {@code default short value}.
	 * <p>
	 * The map will not contain a mapping for the specified key once the call returns.
	 *
	 * @param key key whose mapping is to be removed from the map.
	 * @return the previous value associated with {@code key}, or {@code default short value} if
	 * 		there was no mapping for {@code key}.
	 * @throws UnsupportedOperationException if the {@code remove} operation is not supported by
	 *                                       this map.
	 * @since 0.1.5 ~2020.09.03
	 */
	short removeShort(short key);

	/**
	 * An entry specialized for {@code short} values.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.02
	 */
	interface ShortEntry extends PrimitiveEntry<
			Short,
			Short
			> {
		@Override
		default Short getKey() {
			return this.getShortKey();
		}

		@Override
		default Short getValue() {
			return this.getShortValue();
		}

		@Override
		default Short setValue(Short value) {
			return this.setShortValue(value);
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
		short getShortKey();

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
		short getShortValue();

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
		short setShortValue(short value);
	}
}
