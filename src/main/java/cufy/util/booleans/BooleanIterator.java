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
package cufy.util.booleans;

import cufy.util.booleans.function.BooleanConsumer;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;

/**
 * An Iterator specialized for {@code boolean} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.30
 */
public interface BooleanIterator extends PrimitiveIterator<
		Boolean,
		BooleanConsumer
		> {
	@Override
	default void forEachRemaining(Consumer<? super Boolean> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		this.forEachRemaining(
				consumer instanceof BooleanConsumer ?
				(BooleanConsumer) consumer :
				consumer::accept
		);
	}

	@Override
	default void forEachRemaining(BooleanConsumer consumer) {
		Objects.requireNonNull(consumer, "consumer");
		while (this.hasNext())
			consumer.accept(this.nextBoolean());
	}

	@SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
	@Override
	default Boolean next() {
		return this.nextBoolean();
	}

	/**
	 * Returns the next {@code boolean} element in the iteration.
	 *
	 * @return the next {@code boolean} element in the iteration.
	 * @throws NoSuchElementException if the iteration has no more elements.
	 * @since 0.1.5 ~2020.08.30
	 */
	boolean nextBoolean();
}
