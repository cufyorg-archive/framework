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
 * A map specialized for {@code float} values.
 *
 * @param <ENTRY_SET> the type of the set of the entries.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.02
 */
public interface FloatMap<
		ENTRY_SET extends Set<Map.Entry<Float, Float>>
		> extends PrimitiveMap<
		Float,
		Float,
		FloatBiConsumer,
		FloatBinaryOperator,
		ENTRY_SET,
		FloatSet,
		FloatCollection,
		FloatMap<ENTRY_SET>
		> {
	@Override
	default Float compute(Float key, BiFunction<? super Float, ? super Float, ? extends Float> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = new boolean[1];
		float current = this.compute(
				key,
				(float k, Float v) -> {
					Float newValue = (Float) (
							function instanceof FloatObjBiFunction ?
							((FloatObjBiFunction) function).apply(k, v) :
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
	default Float computeIfAbsent(Float key, Function<? super Float, ? extends Float> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = new boolean[1];
		float current = this.computeIfAbsent(
				key,
				(float k) -> {
					Float newValue = (Float) (
							function instanceof FloatFunction ?
							((FloatFunction) function).apply(k) :
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
	default Float computeIfPresent(Float key, BiFunction<? super Float, ? super Float, ? extends Float> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = {this.containsKey(key)};
		float current = this.computeIfPresent(
				key,
				(float k, float v) -> {
					Float newValue = (Float) (
							function instanceof FloatBiFunction ?
							((FloatBiFunction) function).apply(k, v) :
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
		return key instanceof Float && this.containsKey((float) key);
	}

	@Override
	default boolean containsValue(Object value) {
		return value instanceof Float && this.containsValue((float) value);
	}

	@Override
	default void forEach(BiConsumer<? super Float, ? super Float> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		this.forEach(
				consumer instanceof FloatBiConsumer ?
				(FloatBiConsumer) consumer :
				consumer::accept
		);
	}

	@Override
	default Float get(Object key) {
		return key instanceof Float &&
			   this.containsKey((float) key) ?
			   this.getFloat((float) key) :
			   null;
	}

	@Override
	default Float getOrDefault(Object key, Float defaultValue) {
		//avoid using getOrDefaultFloat, to allow the 'defaultValue' to be null
		return key instanceof Float &&
			   this.containsKey((float) key) ?
			   this.getFloat((float) key) :
			   defaultValue;
	}

	@Override
	default Float merge(Float key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> function) {
		Objects.requireNonNull(function, "function");
		Objects.requireNonNull(value, "value");
		boolean[] notnull = {this.containsKey(key)};
		float current = this.merge(
				key,
				value,
				(float k, float v) -> {
					Float newValue = (Float) (
							function instanceof FloatBiFunction ?
							((FloatBiFunction) function).apply(k, v) :
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
	default Float put(Float key, Float value) {
		boolean notnull = this.containsKey((float) key);
		float old = this.putFloat(key, value);
		return notnull ?
			   old :
			   null;
	}

	@Override
	default Float putIfAbsent(Float key, Float value) {
		if (this.containsKey((float) key))
			return this.getFloat(key);

		this.putFloat(key, value);
		return null;
	}

	@Override
	default Float remove(Object key) {
		return key instanceof Float &&
			   this.containsKey((float) key) ?
			   this.removeFloat((float) key) :
			   null;
	}

	@Override
	default boolean remove(Object key, Object value) {
		return key instanceof Float &&
			   value instanceof Float &&
			   this.removeFloat((float) key, (float) value);
	}

	@Override
	default Float replace(Float key, Float value) {
		return this.containsKey((float) key) ?
			   this.replaceFloat(key, value) :
			   null;
	}

	@Override
	default boolean replace(Float key, Float oldValue, Float newValue) {
		return this.replaceFloat(key, oldValue, newValue);
	}

	@Override
	default void replaceAll(BiFunction<? super Float, ? super Float, ? extends Float> function) {
		Objects.requireNonNull(function, "function");
		this.replaceAll(
				function instanceof FloatBinaryOperator ?
				(FloatBinaryOperator) function :
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
	 * @return the new value associated with the specified key, or {@code default float value} if
	 * 		none.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default float compute(float key, FloatObjBiFunction<Float, Float> function) {
		Objects.requireNonNull(function, "function");
		boolean oldNotnull = this.containsKey(key);
		Float oldValue = oldNotnull ?
						 this.getFloat(key) :
						 null;
		Float newValue = function.apply(key, oldValue);

		if (newValue == null && oldNotnull) {
			this.removeFloat(key);
			return 0.0F;
		}

		this.putFloat(key, newValue);
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
	 * 		default float value} if the computed value is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default float computeIfAbsent(float key, FloatFunction<Float> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key))
			return this.getFloat(key);

		Float newValue = function.apply(key);

		if (newValue == null)
			return 0.0F;

		this.putFloat(key, newValue);
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
	 * @return the new value associated with the specified key, or {@code default float value} if
	 * 		none.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default float computeIfPresent(float key, FloatBiFunction<Float> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key)) {
			float oldValue = this.getFloat(key);
			Float newValue = function.apply(key, oldValue);

			if (newValue == null) {
				this.removeFloat(key);
				return 0.0F;
			}

			this.put(key, newValue);
			return newValue;
		}

		return 0.0F;
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
	default float getOrDefaultFloat(float key, float defaultValue) {
		return this.containsKey(key) ?
			   this.getFloat(key) :
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
	 * @return the new value associated with the specified key, or {@code default float value} if no
	 * 		value is associated with the key.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default float merge(float key, float value, FloatBiFunction<Float> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key)) {
			Float newValue = function.apply(this.getFloat(key), value);

			if (newValue == null) {
				this.removeFloat(key);
				return 0.0F;
			}

			this.putFloat(key, newValue);
			return newValue;
		}

		this.putFloat(key, value);
		return 0.0F;
	}

	/**
	 * If the specified key is not already associated with a value, associates it with the given
	 * value and returns {@code default float value}, else returns the current value.
	 *
	 * @param key   key with which the specified value is to be associated.
	 * @param value value to be associated with the specified key.
	 * @return the previous value associated with the specified key, or {@code default float value}
	 * 		if there was no mapping for the key. (A {@code default float value} return can also
	 * 		indicate that the map previously associated {@code default float value} with the key.)
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.03
	 */
	default float putFloatIfAbsent(float key, float value) {
		if (this.containsKey(key))
			return this.getFloat(key);

		//will return `default float value`
		return this.putFloat(key, value);
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
	default boolean removeFloat(float key, float value) {
		if (this.containsKey(key)) {
			float current = this.getFloat(key);

			if (Float.floatToIntBits(current) ==
				Float.floatToIntBits(value)) {
				this.removeFloat(key);
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
	default boolean replaceFloat(float key, float oldValue, float newValue) {
		if (this.containsKey(key)) {
			float current = this.getFloat(key);

			if (Float.floatToIntBits(current) ==
				Float.floatToIntBits(oldValue)) {
				this.putFloat(key, newValue);
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
	 * @return the previous value associated with the specified key, or {@code default float value}
	 * 		if there was no mapping for the key. (A {@code default float value} return can also
	 * 		indicate that the map previously associated {@code default float value} with the key).
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.02
	 */
	default float replaceFloat(float key, float value) {
		return this.containsKey(key) ?
			   this.putFloat(key, value) :
			   0;
	}

	/**
	 * Returns {@code true} if this map contains a mapping for the specified key.
	 *
	 * @param key key whose presence in this map is to be tested.
	 * @return {@code true} if this map contains a mapping for the specified key.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean containsKey(float key);

	/**
	 * Returns {@code true} if this map maps one or more keys to the specified value.
	 *
	 * @param value value whose presence in this map is to be tested.
	 * @return {@code true} if this map maps one or more keys to the specified value.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean containsValue(float value);

	/**
	 * Returns the value to which the specified key is mapped, or {@code default float value} if
	 * this map contains no mapping for the key.
	 * <p>
	 * A return value of {@code default float value} does not <i>necessarily</i> indicate that the
	 * map contains no mapping for the key; it's also possible that the map explicitly maps the key
	 * to {@code default float value}. The {@link #containsKey(float) containsKey} operation may be
	 * used to distinguish these two cases.
	 *
	 * @param key the key whose associated value is to be returned.
	 * @return the value to which the specified key is mapped, or {@code default float value} if
	 * 		this map contains no mapping for the key.
	 * @since 0.1.5 ~2020.09.02
	 */
	float getFloat(float key);

	/**
	 * Associates the specified value with the specified key in this map (optional operation). If
	 * the map previously contained a mapping for the key, the old value is replaced by the
	 * specified value. (A map {@code m} is said to contain a mapping for a key <tt>k</tt> if and
	 * only if {@link #containsKey(float) m.containsKey(k)} would return {@code true}.).
	 *
	 * @param key   key with which the specified value is to be associated.
	 * @param value value to be associated with the specified key.
	 * @return the previous value associated with {@code key}, or {@code default float value} if
	 * 		there was no mapping for {@code key}.(A {@code default float value} return can also
	 * 		indicate that the map previously associated {@code default float value} with {@code key}).
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.02
	 */
	float putFloat(float key, float value);

	/**
	 * Removes the mapping for a key from this map if it is present (optional operation).
	 * <p>
	 * Returns the value to which this map previously associated the key, or {@code default float
	 * value} if the map contained no mapping for the key.
	 * <p>
	 * A return value of {@code default float value} does not <i>necessarily</i> indicate that the
	 * map contained no mapping for the key; it's also possible that the map explicitly mapped the
	 * key to {@code default float value}.
	 * <p>
	 * The map will not contain a mapping for the specified key once the call returns.
	 *
	 * @param key key whose mapping is to be removed from the map.
	 * @return the previous value associated with {@code key}, or {@code default float value} if
	 * 		there was no mapping for {@code key}.
	 * @throws UnsupportedOperationException if the {@code remove} operation is not supported by
	 *                                       this map.
	 * @since 0.1.5 ~2020.09.03
	 */
	float removeFloat(float key);

	/**
	 * An entry specialized for {@code float} values.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.02
	 */
	interface FloatEntry extends PrimitiveEntry<
			Float,
			Float
			> {
		@Override
		default Float getKey() {
			return this.getFloatKey();
		}

		@Override
		default Float getValue() {
			return this.getFloatValue();
		}

		@Override
		default Float setValue(Float value) {
			return this.setFloatValue(value);
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
		float getFloatKey();

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
		float getFloatValue();

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
		float setFloatValue(float value);
	}
}
