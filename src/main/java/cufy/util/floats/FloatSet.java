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
package cufy.util.floats;

import cufy.util.PrimitiveSet;
import cufy.util.floats.function.FloatConsumer;
import cufy.util.floats.function.FloatPredicate;

/**
 * A set specified for {@code float} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface FloatSet extends FloatCollection, PrimitiveSet<
		Float,
		FloatConsumer,
		FloatPredicate,
		FloatIterator,
		FloatSpliterator,
		FloatCollection,
		FloatSet
		> {
}
