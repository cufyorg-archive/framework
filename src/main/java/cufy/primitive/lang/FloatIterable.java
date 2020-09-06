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
package cufy.primitive.lang;

import cufy.lang.PrimitiveIterable;
import cufy.primitive.util.FloatIterator;
import cufy.primitive.util.FloatSpliterator;
import cufy.primitive.util.function.FloatConsumer;

import java.util.function.Consumer;

/**
 * An iterator specialize for {@code byte} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface FloatIterable extends PrimitiveIterable<
		Float,
		FloatConsumer,
		FloatIterator,
		FloatSpliterator
		> {
	@Override
	default void forEach(Consumer<? super Float> consumer) {
		this.iterator().forEachRemaining(consumer);
	}

	@Override
	default void forEach(FloatConsumer consumer) {
		this.iterator().forEachRemaining(consumer);
	}

	@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
	@Override
	FloatSpliterator spliterator();
}
