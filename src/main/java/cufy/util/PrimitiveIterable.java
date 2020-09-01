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
package cufy.util;

import java.util.PrimitiveIterator;
import java.util.Spliterator;

/**
 * An iterator specialized for primitive values.
 *
 * @param <E>           the type of the elements.
 * @param <CONSUMER>    the type of the consumer.
 * @param <ITERATOR>    the type of the iterator.
 * @param <SPLITERATOR> the type of the spliterator.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface PrimitiveIterable<
		E,
		CONSUMER,
		ITERATOR extends PrimitiveIterator<E, CONSUMER>,
		SPLITERATOR extends Spliterator.OfPrimitive<E, CONSUMER, SPLITERATOR>
		> extends Iterable<E> {
	@Override
	ITERATOR iterator();

	@Override
	SPLITERATOR spliterator();

	/**
	 * Performs the given {@code consumer} for each element of the {@code Iterable} until all
	 * elements have been processed or the given {@code consumer} throws an exception. Unless
	 * otherwise specified by the implementing class, actions are performed in the order of
	 * iteration (if an iteration order is specified).  Exceptions thrown by the action are relayed
	 * to the caller.
	 *
	 * @param consumer The action to be performed for each element.
	 * @throws NullPointerException if the given {@code consumer} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	void forEach(CONSUMER consumer);
}
