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

import cufy.primitive.util.function.CharConsumer;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;

/**
 * An Iterator specialized for {@code char} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.30
 */
public interface CharIterator extends PrimitiveIterator<
		Character,
		CharConsumer
		> {
	@Override
	default void forEachRemaining(Consumer<? super Character> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		this.forEachRemaining(
				consumer instanceof CharConsumer ?
				(CharConsumer) consumer :
				consumer::accept
		);
	}

	@Override
	default void forEachRemaining(CharConsumer consumer) {
		Objects.requireNonNull(consumer, "consumer");
		while (this.hasNext())
			consumer.accept(this.nextChar());
	}

	@SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
	@Override
	default Character next() {
		return this.nextChar();
	}

	/**
	 * Returns the next {@code char} element in the iteration.
	 *
	 * @return the next {@code char} element in the iteration.
	 * @throws NoSuchElementException if the iteration has no more elements.
	 * @since 0.1.5 ~2020.08.30
	 */
	char nextChar();
}
