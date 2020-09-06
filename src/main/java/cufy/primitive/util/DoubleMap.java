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

import cufy.primitive.util.function.DoubleBiConsumer;
import cufy.primitive.util.function.DoubleBiFunction;
import cufy.primitive.util.function.DoubleObjBiFunction;
import cufy.util.Objects;
import cufy.util.PrimitiveMap;

import java.util.Map;
import java.util.Set;
import java.util.function.*;

/**
 * A map specialized for {@code double} values.
 *
 * @param <ENTRY_SET> the type of the set of the entries.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.02
 */
public interface DoubleMap<
		ENTRY_SET extends Set<Map.Entry<Double, Double>>
		> extends PrimitiveMap<
		Double,
		Double,
		DoubleBiConsumer,
		DoubleBinaryOperator,
		ENTRY_SET,
		DoubleSet,
		DoubleCollection,
		DoubleMap<ENTRY_SET>
		> {
	@Override
	default Double compute(Double key, BiFunction<? super Double, ? super Double, ? extends Double> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = new boolean[1];
		double current = this.compute(
				key,
				(double k, Double v) -> {
					Double newValue = (Double) (
							function instanceof DoubleObjBiFunction ?
							((DoubleObjBiFunction) function).apply(k, v) :
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
	default Double computeIfAbsent(Double key, Function<? super Double, ? extends Double> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = new boolean[1];
		double current = this.computeIfAbsent(
				key,
				(double k) -> {
					Double newValue = (Double) (
							function instanceof DoubleFunction ?
							((DoubleFunction) function).apply(k) :
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
	default Double computeIfPresent(Double key, BiFunction<? super Double, ? super Double, ? extends Double> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = {this.containsKey(key)};
		double current = this.computeIfPresent(
				key,
				(double k, double v) -> {
					Double newValue = (Double) (
							function instanceof DoubleBiFunction ?
							((DoubleBiFunction) function).apply(k, v) :
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
		return key instanceof Double && this.containsKey((double) key);
	}

	@Override
	default boolean containsValue(Object value) {
		return value instanceof Double && this.containsValue((double) value);
	}

	@Override
	default void forEach(BiConsumer<? super Double, ? super Double> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		this.forEach(
				consumer instanceof DoubleBiConsumer ?
				(DoubleBiConsumer) consumer :
				consumer::accept
		);
	}

	@Override
	default Double get(Object key) {
		return key instanceof Double &&
			   this.containsKey((double) key) ?
			   this.getDouble((double) key) :
			   null;
	}

	@Override
	default Double getOrDefault(Object key, Double defaultValue) {
		//avoid using getOrDefaultDouble, to allow the 'defaultValue' to be null
		return key instanceof Double &&
			   this.containsKey((double) key) ?
			   this.getDouble((double) key) :
			   defaultValue;
	}

	@Override
	default Double merge(Double key, Double value, BiFunction<? super Double, ? super Double, ? extends Double> function) {
		Objects.requireNonNull(function, "function");
		Objects.requireNonNull(value, "value");
		boolean[] notnull = {this.containsKey(key)};
		double current = this.merge(
				key,
				value,
				(double k, double v) -> {
					Double newValue = (Double) (
							function instanceof DoubleBiFunction ?
							((DoubleBiFunction) function).apply(k, v) :
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
	default Double put(Double key, Double value) {
		boolean notnull = this.containsKey((double) key);
		double old = this.putDouble(key, value);
		return notnull ?
			   old :
			   null;
	}

	@Override
	default Double putIfAbsent(Double key, Double value) {
		if (this.containsKey((double) key))
			return this.getDouble(key);

		this.putDouble(key, value);
		return null;
	}

	@Override
	default Double remove(Object key) {
		return key instanceof Double &&
			   this.containsKey((double) key) ?
			   this.removeDouble((double) key) :
			   null;
	}

	@Override
	default boolean remove(Object key, Object value) {
		return key instanceof Double &&
			   value instanceof Double &&
			   this.removeDouble((double) key, (double) value);
	}

	@Override
	default Double replace(Double key, Double value) {
		return this.containsKey((double) key) ?
			   this.replaceDouble(key, value) :
			   null;
	}

	@Override
	default boolean replace(Double key, Double oldValue, Double newValue) {
		return this.replaceDouble(key, oldValue, newValue);
	}

	@Override
	default void replaceAll(BiFunction<? super Double, ? super Double, ? extends Double> function) {
		Objects.requireNonNull(function, "function");
		this.replaceAll(
				function instanceof DoubleBinaryOperator ?
				(DoubleBinaryOperator) function :
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
	 * @return the new value associated with the specified key, or {@code default double value} if
	 * 		none.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default double compute(double key, DoubleObjBiFunction<Double, Double> function) {
		Objects.requireNonNull(function, "function");
		boolean oldNotnull = this.containsKey(key);
		Double oldValue = oldNotnull ?
						  this.getDouble(key) :
						  null;
		Double newValue = function.apply(key, oldValue);

		if (newValue == null && oldNotnull) {
			this.removeDouble(key);
			return 0.0D;
		}

		this.putDouble(key, newValue);
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
	 * 		default double value} if the computed value is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default double computeIfAbsent(double key, DoubleFunction<Double> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key))
			return this.getDouble(key);

		Double newValue = function.apply(key);

		if (newValue == null)
			return 0.0D;

		this.putDouble(key, newValue);
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
	 * @return the new value associated with the specified key, or {@code default double value} if
	 * 		none.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default double computeIfPresent(double key, DoubleBiFunction<Double> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key)) {
			double oldValue = this.getDouble(key);
			Double newValue = function.apply(key, oldValue);

			if (newValue == null) {
				this.removeDouble(key);
				return 0.0D;
			}

			this.put(key, newValue);
			return newValue;
		}

		return 0.0D;
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
	default double getOrDefaultDouble(double key, double defaultValue) {
		return this.containsKey(key) ?
			   this.getDouble(key) :
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
	 * @return the new value associated with the specified key, or {@code default double value} if
	 * 		no value is associated with the key.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default double merge(double key, double value, DoubleBiFunction<Double> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key)) {
			Double newValue = function.apply(this.getDouble(key), value);

			if (newValue == null) {
				this.removeDouble(key);
				return 0.0D;
			}

			this.putDouble(key, newValue);
			return newValue;
		}

		this.putDouble(key, value);
		return 0.0D;
	}

	/**
	 * If the specified key is not already associated with a value, associates it with the given
	 * value and returns {@code default double value}, else returns the current value.
	 *
	 * @param key   key with which the specified value is to be associated.
	 * @param value value to be associated with the specified key.
	 * @return the previous value associated with the specified key, or {@code default double value}
	 * 		if there was no mapping for the key. (A {@code default double value} return can also
	 * 		indicate that the map previously associated {@code default double value} with the key.)
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.03
	 */
	default double putDoubleIfAbsent(double key, double value) {
		if (this.containsKey(key))
			return this.getDouble(key);

		//will return `default double value`
		return this.putDouble(key, value);
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
	default boolean removeDouble(double key, double value) {
		if (this.containsKey(key)) {
			double current = this.getDouble(key);

			if (Double.doubleToLongBits(current) ==
				Double.doubleToLongBits(value)) {
				this.removeDouble(key);
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
	default boolean replaceDouble(double key, double oldValue, double newValue) {
		if (this.containsKey(key)) {
			double current = this.getDouble(key);

			if (Double.doubleToLongBits(current) ==
				Double.doubleToLongBits(oldValue)) {
				this.putDouble(key, newValue);
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
	 * @return the previous value associated with the specified key, or {@code default double value}
	 * 		if there was no mapping for the key. (A {@code default double value} return can also
	 * 		indicate that the map previously associated {@code default double value} with the key).
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.02
	 */
	default double replaceDouble(double key, double value) {
		return this.containsKey(key) ?
			   this.putDouble(key, value) :
			   0.0D;
	}

	/**
	 * Returns {@code true} if this map contains a mapping for the specified key.
	 *
	 * @param key key whose presence in this map is to be tested.
	 * @return {@code true} if this map contains a mapping for the specified key.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean containsKey(double key);

	/**
	 * Returns {@code true} if this map maps one or more keys to the specified value.
	 *
	 * @param value value whose presence in this map is to be tested.
	 * @return {@code true} if this map maps one or more keys to the specified value.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean containsValue(double value);

	/**
	 * Returns the value to which the specified key is mapped, or {@code default double value} if
	 * this map contains no mapping for the key.
	 * <p>
	 * A return value of {@code default double value} does not <i>necessarily</i> indicate that the
	 * map contains no mapping for the key; it's also possible that the map explicitly maps the key
	 * to {@code default double value}. The {@link #containsKey(double) containsKey} operation may
	 * be used to distinguish these two cases.
	 *
	 * @param key the key whose associated value is to be returned.
	 * @return the value to which the specified key is mapped, or {@code default double value} if
	 * 		this map contains no mapping for the key.
	 * @since 0.1.5 ~2020.09.02
	 */
	double getDouble(double key);

	/**
	 * Associates the specified value with the specified key in this map (optional operation). If
	 * the map previously contained a mapping for the key, the old value is replaced by the
	 * specified value. (A map {@code m} is said to contain a mapping for a key <tt>k</tt> if and
	 * only if {@link #containsKey(double) m.containsKey(k)} would return {@code true}.).
	 *
	 * @param key   key with which the specified value is to be associated.
	 * @param value value to be associated with the specified key.
	 * @return the previous value associated with {@code key}, or {@code default double value} if
	 * 		there was no mapping for {@code key}.(A {@code default double value} return can also
	 * 		indicate that the map previously associated {@code default double value} with {@code
	 * 		key}).
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.02
	 */
	double putDouble(double key, double value);

	/**
	 * Removes the mapping for a key from this map if it is present (optional operation).
	 * <p>
	 * Returns the value to which this map previously associated the key, or {@code default double
	 * value} if the map contained no mapping for the key.
	 * <p>
	 * A return value of {@code default double value} does not <i>necessarily</i> indicate that the
	 * map contained no mapping for the key; it's also possible that the map explicitly mapped the
	 * key to {@code default double value}.
	 * <p>
	 * The map will not contain a mapping for the specified key once the call returns.
	 *
	 * @param key key whose mapping is to be removed from the map.
	 * @return the previous value associated with {@code key}, or {@code default double value} if
	 * 		there was no mapping for {@code key}.
	 * @throws UnsupportedOperationException if the {@code remove} operation is not supported by
	 *                                       this map.
	 * @since 0.1.5 ~2020.09.03
	 */
	double removeDouble(double key);

	/**
	 * An entry specialized for {@code double} values.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.02
	 */
	interface DoubleEntry extends PrimitiveEntry<
			Double,
			Double
			> {
		@Override
		default Double getKey() {
			return this.getDoubleKey();
		}

		@Override
		default Double getValue() {
			return this.getDoubleValue();
		}

		@Override
		default Double setValue(Double value) {
			return this.setDoubleValue(value);
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
		double getDoubleKey();

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
		double getDoubleValue();

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
		double setDoubleValue(double value);
	}
}
