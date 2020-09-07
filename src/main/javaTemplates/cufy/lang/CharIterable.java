/*
with char|boolean|byte|double|float|int|long|short primitive
*//*
define Iterator ////
if boolean|byte|char|float|short primitive //CharIterator//
elif double|int|long primitive //PrimitiveIterator.OfChar//
endif ////
enddefine
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
package cufy.lang;

/* if boolean|byte|char|float|short primitive */
import cufy.util.CharIterator;
import cufy.util.CharSpliterator;
import cufy.util.function.CharConsumer;
/* elif double|int|long primitive */
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.CharConsumer;
import java.util.Spliterators;
/* endif */

import java.util.function.Consumer;

/**
 * An iterator specialize for {@code char} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface CharIterable extends PrimitiveIterable<
		Character,
		CharConsumer,
		/* Iterator */,
		/* Spliterator */
		> {
	@Override
	default void forEach(Consumer<? super Character> consumer) {
		this.iterator().forEachRemaining(consumer);
	}

	@Override
	default void forEach(CharConsumer consumer) {
		this.iterator().forEachRemaining(consumer);
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
