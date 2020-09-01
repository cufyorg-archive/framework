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

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * A Spliterator specialized for {@code boolean} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.30
 */
public interface BooleanSpliterator extends Spliterator.OfPrimitive<
		Boolean,
		BooleanConsumer,
		BooleanSpliterator
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
		while (this.tryAdvance(consumer))
			;
	}

	@Override
	default boolean tryAdvance(Consumer<? super Boolean> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		return this.tryAdvance(
				consumer instanceof BooleanConsumer ?
				(BooleanConsumer) consumer :
				consumer::accept
		);
	}

	@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
	@Override
	boolean tryAdvance(BooleanConsumer consumer);

	@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
	@Override
	BooleanSpliterator trySplit();
}
