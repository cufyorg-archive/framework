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
import java.util.Set;
import java.util.Spliterator;

/**
 * A set specialized for primitive values.
 *
 * @param <E>           the type of the elements.
 * @param <CONSUMER>    the type of the consumer.
 * @param <PREDICATE>   the type of the predicate.
 * @param <ITERATOR>    the type of the iterator.
 * @param <SPLITERATOR> the type of the spliterator.
 * @param <COLLECTION>  the type of the collection.
 * @param <SET>         the type of the set.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface PrimitiveSet<
		E,
		CONSUMER,
		PREDICATE,
		ITERATOR extends PrimitiveIterator<E, CONSUMER>,
		SPLITERATOR extends Spliterator.OfPrimitive<E, CONSUMER, SPLITERATOR>,
		COLLECTION extends PrimitiveCollection<E, CONSUMER, PREDICATE, ITERATOR, SPLITERATOR, COLLECTION>,
		SET extends PrimitiveSet<E, CONSUMER, PREDICATE, ITERATOR, SPLITERATOR, COLLECTION, SET>
		> extends PrimitiveCollection<E, CONSUMER, PREDICATE, ITERATOR, SPLITERATOR, COLLECTION>, Set<E> {
	@Override
	SPLITERATOR spliterator();
}
