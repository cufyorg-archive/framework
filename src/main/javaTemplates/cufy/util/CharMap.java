/*
with char|boolean|byte|double|float|int|long|short primitive
*//*
define DefaultValue ////
if boolean primitive //false//
elif byte|char|int|short primitive //0//
elif double primitive //0.0D//
elif float primitive //0.0F//
elif long primitive //0L//
endif ////
enddefine
*//*
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
package cufy.util;

/* if boolean|byte|char|float|short primitive*/
import cufy.util.function.CharBinaryOperator;
import cufy.util.function.CharFunction;
/* elif double|int|long primitive */
import java.util.function.CharBinaryOperator;
import java.util.function.CharFunction;
/* endif */

import cufy.util.function.CharBiConsumer;
import cufy.util.function.CharBiFunction;
import cufy.util.function.CharObjBiFunction;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A map specialized for {@code char} values. The map should implement (use)
 * {@link CharMap.CharEntry} on its entries.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.02
 */
public interface CharMap
		extends
		PrimitiveMap<Character, Character, CharBiConsumer, CharBinaryOperator> {
	@Override
	default Character compute(Character key, BiFunction<? super Character, ? super Character, ? extends Character> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = new boolean[1];
		char current = this.compute(
				key,
				(char k, Character v) -> {
					Character newValue = (Character) (
							function instanceof CharObjBiFunction ?
							((CharObjBiFunction) function).apply(k, v) :
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
	default Character computeIfAbsent(Character key, Function<? super Character, ? extends Character> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = new boolean[1];
		char current = this.computeIfAbsent(
				key,
				(char k) -> {
					Character newValue = (Character) (
							function instanceof CharFunction ?
							((CharFunction) function).apply(k) :
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
	default Character computeIfPresent(Character key, BiFunction<? super Character, ? super Character, ? extends Character> function) {
		Objects.requireNonNull(function, "function");
		boolean[] notnull = {this.containsKey(key)};
		char current = this.computeIfPresent(
				key,
				(char k, char v) -> {
					Character newValue = (Character) (
							function instanceof CharBiFunction ?
							((CharBiFunction) function).apply(k, v) :
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
		return key instanceof Character && this.containsKey((char) key);
	}

	@Override
	default boolean containsValue(Object value) {
		return value instanceof Character && this.containsValue((char) value);
	}

	@Override
	default void forEach(BiConsumer<? super Character, ? super Character> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		this.forEach(
				consumer instanceof CharBiConsumer ?
				(CharBiConsumer) consumer :
				consumer::accept
		);
	}

	@Override
	default Character get(Object key) {
		return key instanceof Character &&
			   this.containsKey((char) key) ?
			   this.getChar((char) key) :
			   null;
	}

	@Override
	default Character getOrDefault(Object key, Character defaultValue) {
		//avoid using getOrDefaultChar, to allow the 'defaultValue' to be null
		return key instanceof Character &&
			   this.containsKey((char) key) ?
			   this.getChar((char) key) :
			   defaultValue;
	}

	@Override
	default Character merge(Character key, Character value, BiFunction<? super Character, ? super Character, ? extends Character> function) {
		Objects.requireNonNull(function, "function");
		Objects.requireNonNull(value, "value");
		boolean[] notnull = {this.containsKey(key)};
		char current = this.merge(
				key,
				value,
				(char k, char v) -> {
					Character newValue = (Character) (
							function instanceof CharBiFunction ?
							((CharBiFunction) function).apply(k, v) :
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
	default Character put(Character key, Character value) {
		boolean notnull = this.containsKey((char) key);
		char old = this.putChar(key, value);
		return notnull ?
			   old :
			   null;
	}

	@Override
	default Character putIfAbsent(Character key, Character value) {
		if (this.containsKey((char) key))
			return this.getChar(key);

		this.putChar(key, value);
		return null;
	}

	@Override
	default Character remove(Object key) {
		return key instanceof Character &&
			   this.containsKey((char) key) ?
			   this.removeChar((char) key) :
			   null;
	}

	@Override
	default boolean remove(Object key, Object value) {
		return key instanceof Character &&
			   value instanceof Character &&
			   this.removeChar((char) key, (char) value);
	}

	@Override
	default Character replace(Character key, Character value) {
		return this.containsKey((char) key) ?
			   this.replaceChar(key, value) :
			   null;
	}

	@Override
	default boolean replace(Character key, Character oldValue, Character newValue) {
		return this.replaceChar(key, oldValue, newValue);
	}

	@Override
	default void replaceAll(BiFunction<? super Character, ? super Character, ? extends Character> function) {
		Objects.requireNonNull(function, "function");
		this.replaceAll(
				function instanceof CharBinaryOperator ?
				(CharBinaryOperator) function :
				function::apply
		);
	}

	@Override
	CharSet keySet();

	@Override
	CharCollection values();

	/**
	 * Attempts to compute a mapping for the specified key and its current mapped value.
	 * <p>
	 * If the function returns {@code null}, the mapping is removed (or remains absent if initially
	 * absent). If the function itself throws an (unchecked) exception, the exception is rethrown,
	 * and the current mapping is left unchanged.
	 *
	 * @param key      key with which the specified value is to be associated.
	 * @param function the function to compute a value.
	 * @return the new value associated with the specified key, or {@code //DefaultValue//} if
	 * 		none.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	default char compute(char key, CharObjBiFunction<Character, Character> function) {
		Objects.requireNonNull(function, "function");
		boolean oldNotnull = this.containsKey(key);
		Character oldValue = oldNotnull ?
							 this.getChar(key) :
							 null;
		Character newValue = function.apply(key, oldValue);

		if (newValue == null && oldNotnull) {
			this.removeChar(key);
			return /*DefaultValue*/;
		}

		this.putChar(key, newValue);
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
	 * 		//DefaultValue//} if the computed value is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	default char computeIfAbsent(char key, CharFunction<Character> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key))
			return this.getChar(key);

		Character newValue = function.apply(key);

		if (newValue == null)
			return /*DefaultValue*/;

		this.putChar(key, newValue);
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
	 * @return the new value associated with the specified key, or {@code //DefaultValue//} if
	 * 		none.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @since 0.1.5 ~2020.09.03
	 */
	default char computeIfPresent(char key, CharBiFunction<Character> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key)) {
			char oldValue = this.getChar(key);
			Character newValue = function.apply(key, oldValue);

			if (newValue == null) {
				this.removeChar(key);
				return /*DefaultValue*/;
			}

			this.put(key, newValue);
			return newValue;
		}

		return /*DefaultValue*/;
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
	default char getCharOrDefault(char key, char defaultValue) {
		return this.containsKey(key) ?
			   this.getChar(key) :
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
	 * @return the new value associated with the specified key, or {@code //DefaultValue//} if no
	 * 		value is associated with the key.
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws NullPointerException          if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.03
	 */
	default char merge(char key, char value, CharBiFunction<Character> function) {
		Objects.requireNonNull(function, "function");
		if (this.containsKey(key)) {
			Character newValue = function.apply(this.getChar(key), value);

			if (newValue == null) {
				this.removeChar(key);
				return /*DefaultValue*/;
			}

			this.putChar(key, newValue);
			return newValue;
		}

		this.putChar(key, value);
		return /*DefaultValue*/;
	}

	/**
	 * If the specified key is not already associated with a value, associates it with the given
	 * value and returns {@code //DefaultValue//}, else returns the current value.
	 *
	 * @param key   key with which the specified value is to be associated.
	 * @param value value to be associated with the specified key.
	 * @return the previous value associated with the specified key, or {@code //DefaultValue//}
	 * 		if there was no mapping for the key. (A {@code //DefaultValue//} return can also indicate
	 * 		that the map previously associated {@code //DefaultValue//} with the key.)
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.03
	 */
	default char putCharIfAbsent(char key, char value) {
		if (this.containsKey(key))
			return this.getChar(key);

		this.putChar(key, value);
		return /*DefaultValue*/;
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
	default boolean removeChar(char key, char value) {
		if (this.containsKey(key)) {
			char current = this.getChar(key);

			if (current == value) {
				this.removeChar(key);
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
	 * @return the previous value associated with the specified key, or {@code //DefaultValue//}
	 * 		if there was no mapping for the key. (A {@code //DefaultValue//} return can also indicate
	 * 		that the map previously associated {@code //DefaultValue//} with the key).
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.02
	 */
	default char replaceChar(char key, char value) {
		return this.containsKey(key) ?
			   this.putChar(key, value) :
				/*DefaultValue*/;
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
	default boolean replaceChar(char key, char oldValue, char newValue) {
		if (this.containsKey(key)) {
			char current = this.getChar(key);

			if (current == oldValue) {
				this.putChar(key, newValue);
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns {@code true} if this map contains a mapping for the specified key.
	 *
	 * @param key key whose presence in this map is to be tested.
	 * @return {@code true} if this map contains a mapping for the specified key.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean containsKey(char key);

	/**
	 * Returns {@code true} if this map maps one or more keys to the specified value.
	 *
	 * @param value value whose presence in this map is to be tested.
	 * @return {@code true} if this map maps one or more keys to the specified value.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean containsValue(char value);

	/**
	 * Returns the value to which the specified key is mapped, or {@code //DefaultValue//} if this
	 * map contains no mapping for the key.
	 * <p>
	 * A return value of {@code //DefaultValue//} does not <i>necessarily</i> indicate that the
	 * map contains no mapping for the key; it's also possible that the map explicitly maps the key
	 * to {@code //DefaultValue//}. The {@link #containsKey(char) containsKey} operation may be
	 * used to distinguish these two cases.
	 *
	 * @param key the key whose associated value is to be returned.
	 * @return the value to which the specified key is mapped, or {@code //DefaultValue//} if this
	 * 		map contains no mapping for the key.
	 * @since 0.1.5 ~2020.09.02
	 */
	char getChar(char key);

	/**
	 * Associates the specified value with the specified key in this map (optional operation). If
	 * the map previously contained a mapping for the key, the old value is replaced by the
	 * specified value. (A map {@code m} is said to contain a mapping for a key <tt>k</tt> if and
	 * only if {@link #containsKey(char) m.containsKey(k)} would return {@code true}.).
	 *
	 * @param key   key with which the specified value is to be associated.
	 * @param value value to be associated with the specified key.
	 * @return the previous value associated with {@code key}, or {@code //DefaultValue//} if
	 * 		there was no mapping for {@code key}.(A {@code //DefaultValue//} return can also indicate
	 * 		that the map previously associated {@code //DefaultValue//} with {@code key}).
	 * @throws UnsupportedOperationException if the {@code put} operation is not supported by this
	 *                                       map.
	 * @throws IllegalArgumentException      if some property of the specified key or value prevents
	 *                                       it from being stored in this map.
	 * @since 0.1.5 ~2020.09.02
	 */
	char putChar(char key, char value);

	/**
	 * Removes the mapping for a key from this map if it is present (optional operation).
	 * <p>
	 * Returns the value to which this map previously associated the key, or {@code default char
	 * value} if the map contained no mapping for the key.
	 * <p>
	 * A return value of {@code //DefaultValue//} does not <i>necessarily</i> indicate that the
	 * map contained no mapping for the key; it's also possible that the map explicitly mapped the
	 * key to {@code //DefaultValue//}.
	 * <p>
	 * The map will not contain a mapping for the specified key once the call returns.
	 *
	 * @param key key whose mapping is to be removed from the map.
	 * @return the previous value associated with {@code key}, or {@code //DefaultValue//} if
	 * 		there was no mapping for {@code key}.
	 * @throws UnsupportedOperationException if the {@code remove} operation is not supported by
	 *                                       this map.
	 * @since 0.1.5 ~2020.09.03
	 */
	char removeChar(char key);

	/**
	 * An entry specialized for {@code char} values.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.02
	 */
	interface CharEntry
			extends
			PrimitiveEntry<Character, Character> {
		@Override
		default Character getKey() {
			return this.getCharKey();
		}

		@Override
		default Character getValue() {
			return this.getCharValue();
		}

		@Override
		default Character setValue(Character value) {
			return this.setCharValue(value);
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
		char getCharKey();

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
		char getCharValue();

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
		char setCharValue(char value);
	}
}
