/*
with char|boolean|byte|double|float|int|long|short primitive
*//*
define Spliterator ////
if boolean|byte|char|float|short primitive //CharSpliterator//
elif double|int|long primitive //Spliterator.OfChar//
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

/* if boolean|byte|char|float|short primitive */
import cufy.util.CharIterator;
import cufy.util.CharSpliterator;
import cufy.util.function.CharConsumer;
import cufy.util.function.CharPredicate;
/* elif double|int|long primitive */
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.CharConsumer;
import java.util.function.CharPredicate;
import java.util.Spliterators;
/* endif */

/**
 * A set specified for {@code char} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface CharSet
		extends
		CharCollection,
		PrimitiveSet<Character, CharConsumer, CharPredicate> {
	@Override
	default boolean remove(Object object) {
		return object instanceof Character && this.removeChar((char) object);
	}

	@Override
	default boolean contains(Object object) {
		return object instanceof Character && this.contains((char) object);
	}

	@Override
	default boolean add(Character element) {
		return this.addChar(element);
	}
	/*
	if double|int|long primitive
	*/

	@Override
	default /* Spliterator */ spliterator() {
		return Spliterators.spliteratorUnknownSize(this.iterator(), 0);
	}
	/*
	endif
	*/
}
