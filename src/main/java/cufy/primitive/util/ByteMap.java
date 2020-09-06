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
 * A map specialized for {@code byte} values.
 *
 * @param <ENTRY_SET> the type of the set of the entries.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.02
 */
public interface ByteMap<
		ENTRY_SET extends Set<Map.Entry<Byte, Byte>>
		> extends PrimitiveMap<
		Byte,
		Byte,
		ByteBiConsumer,
		ByteBinaryOperator,
		ENTRY_SET,
		ByteSet,
		ByteCollection,
		ByteMap<ENTRY_SET>
		> {
	@Override
	default Byte compute(Byte key, BiFunction<? super Byte, ? super Byte, ? extends Byte> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = new boolean[1];
		byte current = this.compute(
				key,
				(byte k, Byte v) -> {
					Byte newValue = (Byte) (
							function instanceof ByteObjBiFunction ?
							((ByteObjBiFunction) function).apply(k, v) :
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
	default Byte computeIfAbsent(Byte key, Function<? super Byte, ? extends Byte> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = new boolean[1];
		byte current = this.computeIfAbsent(
				key,
				(byte k) -> {
					Byte newValue = (Byte) (
							function instanceof ByteFunction ?
							((ByteFunction) function).apply(k) :
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
	default Byte computeIfPresent(Byte key, BiFunction<? super Byte, ? super Byte, ? extends Byte> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = {this.containsKey(key)};
		byte current = this.computeIfPresent(
				key,
				(byte k, byte v) -> {
					Byte newValue = (Byte) (
							function instanceof ByteBiFunction ?
							((ByteBiFunction) function).apply(k, v) :
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
		return key instanceof Byte && this.containsKey((byte) key);
	}

	@Override
	default boolean containsValue(Object value) {
		return value instanceof Byte && this.containsValue((byte) value);
	}

	@Override
	default void forEach(BiConsumer<? super Byte, ? super Byte> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		this.forEach(
				consumer instanceof ByteBiConsumer ?
				(ByteBiConsumer) consumer :
				consumer::accept
		);
	}

	@Override
	default Byte get(Object key) {
		return key instanceof Byte &&
			   this.containsKey((byte) key) ?
			   this.getByte((byte) key) :
			   null;
	}

	@Override
	default Byte getOrDefault(Object key, Byte defaultValue) {
		//avoid using getOrDefaultByte, to allow the 'defaultValue' to be null
		return key instanceof Byte &&
			   this.containsKey((byte) key) ?
			   this.getByte((byte) key) :
			   defaultValue;
	}

	@Override
	default Byte merge(Byte key, Byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> function) {
		Objects.requireNonNull(function, "function");
		Objects.requireNonNull(value, "value");
		boolean[] notnull = {this.containsKey(key)};
		byte current = this.merge(
				key,
				value,
				(byte k, byte v) -> {
					Byte newValue = (Byte) (
							function instanceof ByteBiFunction ?
							((ByteBiFunction) function).apply(k, v) :
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
	default Byte put(Byte key, Byte value) {
		boolean notnull = this.containsKey((byte) key);
		byte old = this.putByte(key, value);
		return notnull ?
			   old :
			   null;
	}

	@Override
	default Byte putIfAbsent(Byte key, Byte value) {
		if (this.containsKey((byte) key))
			return this.getByte(key);

		this.putByte(key, value);
		return null;
	}

	@Override
	default Byte remove(Object key) {
		return key instanceof Byte &&
			   this.containsKey((byte) key) ?
			   this.removeByte((byte) key) :
			   null;
	}

	@Override
	default boolean remove(Object key, Object value) {
		return key instanceof Byte &&
			   value instanceof Byte &&
			   this.removeByte((byte) key, (byte) value);
	}

	@Override
	default Byte replace(Byte key, Byte value) {
		return this.containsKey((byte) key) ?
			   this.replaceByte(key, value) :
			   null;
	}

	@Override
	default boolean replace(Byte key, Byte oldValue, Byte newValue) {
		return this.replaceByte(key, oldValue, newValue);
	}

	@Override
	default void replaceAll(BiFunction<? super Byte, ? super Byte, ? extends Byte> function) {
		Objects.requireNonNull(function, "function");
		this.replaceAll(
				function instanceof ByteBinaryOperator ?
				(ByteBinaryOperator) function :
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
	 * @return the new value associated with the specified key, or {@code default byte value} if
	 * 		none.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default byte compute(byte key, ByteObjBiFunction<Byte, Byte> function) {
		Objects.requireNonNull(function, "function");
		boolean oldNotnull = this.containsKey(key);
		Byte oldValue = oldNotnull ?
						this.getByte(key) :
						null;
		Byte newValue = function.apply(key, oldValue);

		if (newValue == null && oldNotnull) {
			this.removeByte(key);
			return 0;
		}

		this.putByte(key, newValue);
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
	 * 		default byte value} if the computed value is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default byte computeIfAbsent(byte key, ByteFunction<Byte> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key))
			return this.getByte(key);

		Byte newValue = function.apply(key);

		if (newValue == null)
			return 0;

		this.putByte(key, newValue);
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
	 * @return the new value associated with the specified key, or {@code default byte value} if
	 * 		none.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default byte computeIfPresent(byte key, ByteBiFunction<Byte> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key)) {
			byte oldValue = this.getByte(key);
			Byte newValue = function.apply(key, oldValue);

			if (newValue == null) {
				this.removeByte(key);
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
	default byte getOrDefaultByte(byte key, byte defaultValue) {
		return this.containsKey(key) ?
			   this.getByte(key) :
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
	 * @return the new value associated with the specified key, or {@code default byte value} if no
	 * 		value is associated with the key.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.03
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	default byte merge(byte key, byte value, ByteBiFunction<Byte> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key)) {
			Byte newValue = function.apply(this.getByte(key), value);

			if (newValue == null) {
				this.removeByte(key);
				return 0;
			}

			this.putByte(key, newValue);
			return newValue;
		}

		this.putByte(key, value);
		return 0;
	}

	/**
	 * If the specified key is not already associated with a value, associates it with the given
	 * value and returns {@code default byte value}, else returns the current value.
	 *
	 * @param key   key with which the specified value is to be associated.
	 * @param value value to be associated with the specified key.
	 * @return the previous value associated with the specified key, or {@code default byte value}
	 * 		if there was no mapping for the key. (A {@code default byte value} return can also indicate
	 * 		that the map previously associated {@code default byte value} with the key.)
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.03
	 */
	default byte putByteIfAbsent(byte key, byte value) {
		if (this.containsKey(key))
			return this.getByte(key);

		//will return `default byte value`
		return this.putByte(key, value);
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
	default boolean removeByte(byte key, byte value) {
		if (this.containsKey(key)) {
			byte current = this.getByte(key);

			if (current == value) {
				this.removeByte(key);
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
	default boolean replaceByte(byte key, byte oldValue, byte newValue) {
		if (this.containsKey(key)) {
			byte current = this.getByte(key);

			if (current == oldValue) {
				this.putByte(key, newValue);
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
	 * @return the previous value associated with the specified key, or {@code default byte value}
	 * 		if there was no mapping for the key. (A {@code default byte value} return can also indicate
	 * 		that the map previously associated {@code default byte value} with the key).
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.02
	 */
	default byte replaceByte(byte key, byte value) {
		return this.containsKey(key) ?
			   this.putByte(key, value) :
			   0;
	}

	/**
	 * Returns {@code true} if this map contains a mapping for the specified key.
	 *
	 * @param key key whose presence in this map is to be tested.
	 * @return {@code true} if this map contains a mapping for the specified key.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean containsKey(byte key);

	/**
	 * Returns {@code true} if this map maps one or more keys to the specified value.
	 *
	 * @param value value whose presence in this map is to be tested.
	 * @return {@code true} if this map maps one or more keys to the specified value.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean containsValue(byte value);

	/**
	 * Returns the value to which the specified key is mapped, or {@code default byte value} if this
	 * map contains no mapping for the key.
	 * <p>
	 * A return value of {@code default byte value} does not <i>necessarily</i> indicate that the
	 * map contains no mapping for the key; it's also possible that the map explicitly maps the key
	 * to {@code default byte value}. The {@link #containsKey(byte) containsKey} operation may be
	 * used to distinguish these two cases.
	 *
	 * @param key the key whose associated value is to be returned.
	 * @return the value to which the specified key is mapped, or {@code default byte value} if this
	 * 		map contains no mapping for the key.
	 * @since 0.1.5 ~2020.09.02
	 */
	byte getByte(byte key);

	/**
	 * Associates the specified value with the specified key in this map (optional operation). If
	 * the map previously contained a mapping for the key, the old value is replaced by the
	 * specified value. (A map {@code m} is said to contain a mapping for a key <tt>k</tt> if and
	 * only if {@link #containsKey(byte) m.containsKey(k)} would return {@code true}.).
	 *
	 * @param key   key with which the specified value is to be associated.
	 * @param value value to be associated with the specified key.
	 * @return the previous value associated with {@code key}, or {@code default byte value} if
	 * 		there was no mapping for {@code key}.(A {@code default byte value} return can also indicate
	 * 		that the map previously associated {@code default byte value} with {@code key}).
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.02
	 */
	byte putByte(byte key, byte value);

	/**
	 * Removes the mapping for a key from this map if it is present (optional operation).
	 * <p>
	 * Returns the value to which this map previously associated the key, or {@code default byte
	 * value} if the map contained no mapping for the key.
	 * <p>
	 * A return value of {@code default byte value} does not <i>necessarily</i> indicate that the
	 * map contained no mapping for the key; it's also possible that the map explicitly mapped the
	 * key to {@code default byte value}.
	 * <p>
	 * The map will not contain a mapping for the specified key once the call returns.
	 *
	 * @param key key whose mapping is to be removed from the map.
	 * @return the previous value associated with {@code key}, or {@code default byte value} if
	 * 		there was no mapping for {@code key}.
	 * @throws UnsupportedOperationException if the {@code remove} operation is not supported by
	 *                                       this map.
	 * @since 0.1.5 ~2020.09.03
	 */
	byte removeByte(byte key);

	/**
	 * An entry specialized for {@code byte} values.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.02
	 */
	interface ByteEntry extends PrimitiveEntry<
			Byte,
			Byte
			> {
		@Override
		default Byte getKey() {
			return this.getByteKey();
		}

		@Override
		default Byte getValue() {
			return this.getByteValue();
		}

		@Override
		default Byte setValue(Byte value) {
			return this.setByteValue(value);
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
		byte getByteKey();

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
		byte getByteValue();

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
		byte setByteValue(byte value);
	}
}
