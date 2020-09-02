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
package cufy.ints.util;

import cufy.util.PrimitiveIterable;

import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * An iterator specialize for {@code int} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface IntIterable extends PrimitiveIterable<
		Integer,
		IntConsumer,
		PrimitiveIterator.OfInt,
		Spliterator.OfInt
		> {
	@Override
	default void forEach(Consumer<? super Integer> consumer) {
		this.iterator().forEachRemaining(consumer);
	}

	@Override
	default void forEach(IntConsumer consumer) {
		this.iterator().forEachRemaining(consumer);
	}

	@Override
	default Spliterator.OfInt spliterator() {
		return Spliterators.spliteratorUnknownSize(this.iterator(), 0);
	}
}
