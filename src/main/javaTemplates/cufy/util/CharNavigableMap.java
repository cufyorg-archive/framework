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

import cufy.util.CharComparator;
import cufy.util.function.CharBiConsumer;
import cufy.util.function.CharBiFunction;
import cufy.util.function.CharObjBiFunction;

import java.util.Map;
import java.util.Set;

/**
 * A navigable map specialized for {@code char} values.
 *
 * @param <ENTRY_SET> the type of the set of the entries.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.03
 */
public interface CharNavigableMap extends PrimitiveNavigableMap<
		Character,
		Character,
		CharBiConsumer,
		CharBinaryOperator,
		CharComparator,
		CharMap.CharEntry,
		Set<Map.Entry<Character, Character>>,
		CharSet,
		CharNavigableSet,
		CharCollection,
		CharMap,
		CharSortedMap,
		CharNavigableMap
		>, CharSortedMap {
	@Override
	default CharEntry ceilingEntry(Character key) {
		return this.ceilingEntry((char) key);
	}

	@Override
	default Character ceilingKey(Character key) {
		return this.hasCeilingKey(key) ?
			   this.ceilingCharKey(key) :
			   null;
	}

	@Override
	default CharEntry floorEntry(Character key) {
		return this.floorEntry((char) key);
	}

	@Override
	default Character floorKey(Character key) {
		return this.hasFloorKey(key) ?
			   this.floorCharKey(key) :
			   null;
	}

	@Override
	default CharSortedMap headMap(Character endKey) {
		return this.headMap((char) endKey);
	}

	@Override
	default CharNavigableMap headMap(Character endKey, boolean inclusive) {
		return this.headMap((char) endKey, inclusive);
	}

	@Override
	default CharEntry higherEntry(Character key) {
		return this.higherEntry((char) key);
	}

	@Override
	default Character higherKey(Character key) {
		return this.hasHigherKey(key) ?
			   this.higherCharKey(key) :
			   null;
	}

	@Override
	default CharEntry lowerEntry(Character key) {
		return this.lowerEntry((char) key);
	}

	@Override
	default Character lowerKey(Character key) {
		return this.hasLowerKey(key) ?
			   this.lowerCharKey(key) :
			   null;
	}

	@Override
	default CharSortedMap subMap(Character beginKey, Character endKey) {
		return this.subMap((char) beginKey, (char) endKey);
	}

	@Override
	default CharNavigableMap subMap(Character beginKey, boolean beginInclusive, Character endKey, boolean endInclusive) {
		return this.subMap((char) beginKey, beginInclusive, (char) endKey, endInclusive);
	}

	@Override
	default CharSortedMap tailMap(Character beginKey) {
		return this.tailMap((char) beginKey);
	}

	@Override
	default CharNavigableMap tailMap(Character beginKey, boolean inclusive) {
		return this.tailMap((char) beginKey, inclusive);
	}

	/**
	 * Returns the least key greater than or equal to the given key, or {@code //DefaultValue//}
	 * if there is no such key.
	 *
	 * @param key the key.
	 * @return the least key greater than or equal to {@code key}, or {@code //DefaultValue//} if
	 * 		there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	char ceilingCharKey(char key);

	/**
	 * Returns a key-value mapping associated with the least key greater than or equal to the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the least key greater than or equal to {@code key}, or {@code null} if
	 * 		there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	CharEntry ceilingEntry(char key);

	/**
	 * Returns the greatest key less than or equal to the given key, or {@code //DefaultValue//}
	 * if there is no such key.
	 *
	 * @param key the key
	 * @return the greatest key less than or equal to {@code key}, or {@code //DefaultValue//} if
	 * 		there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	char floorCharKey(char key);

	/**
	 * Returns a key-value mapping associated with the greatest key less than or equal to the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the greatest key less than or equal to {@code key}, or {@code null} if
	 * 		there is no such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	CharEntry floorEntry(char key);

	/**
	 * Return true if there is a least key in this map greater than or equal to the given {@code
	 * key}.
	 *
	 * @param key the key to match.
	 * @return true, if there is a least key in this map greater than or equal to the given {@code
	 * 		key}.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean hasCeilingKey(char key);

	/**
	 * Return true if there is a greatest key in this less than or equal to the given {@code key}.
	 *
	 * @param key the key to match.
	 * @return true, if there is a greatest key less than or equal to the given {@code key}.
	 * @since 0.1.5 ~2020.09.03
	 */
	boolean hasFloorKey(char key);

	/**
	 * Return true if there is a least key in this map strictly greater than the given {@code key}.
	 *
	 * @param key the key to match
	 * @return true, if there is a least key greater than teh given {@code key}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasHigherKey(char key);

	/**
	 * Return true if there is a greatest key in this map strictly less than the given {@code key}.
	 *
	 * @param key the key to match.
	 * @return true, if there is a greatest key less than the given {@code element}.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean hasLowerKey(char key);

	/**
	 * Returns a view of the portion of this map whose keys are less than (or equal to, if {@code
	 * inclusive} is true) {@code beginKey}. The returned map is backed by this map, so changes in
	 * the returned map are reflected in this map, and vice-versa. The returned map supports all
	 * optional map operations that this map supports.
	 * <p>
	 * The returned map will throw an {@code IllegalArgumentException} on an attempt to insert a key
	 * outside its range.
	 *
	 * @param endKey    high endpoint of the keys in the returned map.
	 * @param inclusive {@code true} if the high endpoint is to be included in the returned view.
	 * @return a view of the portion of this map whose keys are less than (or equal to, if {@code
	 * 		inclusive} is true) {@code endKey}.
	 * @throws IllegalArgumentException if this map itself has a restricted range, and {@code
	 *                                  endKey} lies outside the bounds of the range.
	 * @since 0.1.5 ~2020.09.03
	 */
	CharNavigableMap headMap(char endKey, boolean inclusive);

	/**
	 * Returns the least key strictly greater than the given key, or {@code //DefaultValue//} if
	 * there is no such key.
	 *
	 * @param key the key.
	 * @return the least key greater than {@code key}, or {@code //DefaultValue//} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	char higherCharKey(char key);

	/**
	 * Returns a key-value mapping associated with the least key strictly greater than the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the least key greater than {@code key}, or {@code null} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	CharEntry higherEntry(char key);

	/**
	 * Returns the greatest key strictly less than the given key, or {@code //DefaultValue//} if
	 * there is no such key.
	 *
	 * @param key the key.
	 * @return the greatest key less than {@code key}, or {@code //DefaultValue//} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	char lowerCharKey(char key);

	/**
	 * Returns a key-value mapping associated with the greatest key strictly less than the given
	 * key, or {@code null} if there is no such key.
	 *
	 * @param key the key.
	 * @return an entry with the greatest key less than {@code key}, or {@code null} if there is no
	 * 		such key.
	 * @since 0.1.5 ~2020.09.03
	 */
	CharEntry lowerEntry(char key);

	/**
	 * Returns a view of the portion of this map whose keys range from {@code beginKey} to {@code
	 * endKey}. If {@code beginKey} and {@code endKey} are equal, the returned map is empty unless
	 * {@code beginInclusive} and {@code endInclusive} are both true. The returned map is backed by
	 * this map, so changes in the returned map are reflected in this map, and vice-versa. The
	 * returned map supports all optional map operations that this map supports.
	 * <p>
	 * The returned map will throw an {@code IllegalArgumentException} on an attempt to insert a key
	 * outside of its range, or to construct a submap either of whose endpoints lie outside its
	 * range.
	 *
	 * @param beginKey       low endpoint of the keys in the returned map.
	 * @param beginInclusive {@code true} if the low endpoint is to be included in the returned
	 *                       view.
	 * @param endKey         high endpoint of the keys in the returned map.
	 * @param endInclusive   {@code true} if the high endpoint is to be included in the returned
	 *                       view.
	 * @return a view of the portion of this map whose keys range from {@code beginKey} to {@code
	 * 		endKey}.
	 * @throws IllegalArgumentException if {@code beginKey} is greater than {@code endKey}; or if
	 *                                  this map itself has a restricted range, and {@code beginKey}
	 *                                  or {@code endKey} lies outside the bounds of the range.
	 * @since 0.1.5 ~2020.09.03
	 */
	CharNavigableMap subMap(char beginKey, boolean beginInclusive, char endKey, boolean endInclusive);

	/**
	 * Returns a view of the portion of this map whose keys are greater than (or equal to, if {@code
	 * inclusive} is true) {@code beginKey}. The returned map is backed by this map, so changes in
	 * the returned map are reflected in this map, and vice-versa. The returned map supports all
	 * optional map operations that this map supports.
	 * <p>
	 * The returned map will throw an {@code IllegalArgumentException} on an attempt to insert a key
	 * outside its range.
	 *
	 * @param beginKey  low endpoint of the keys in the returned map.
	 * @param inclusive {@code true} if the low endpoint is to be included in the returned view.
	 * @return a view of the portion of this map whose keys are greater than (or equal to, if {@code
	 * 		inclusive} is true) {@code beginKey}.
	 * @throws IllegalArgumentException if this map itself has a restricted range, and {@code
	 *                                  beginKey} lies outside the bounds of the range.
	 * @since 0.1.5 ~2020.09.03
	 */
	CharNavigableMap tailMap(char beginKey, boolean inclusive);
}
