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

import java.util.NavigableSet;
import java.util.PrimitiveIterator;

/**
 * A navigable set specialized for primitive values.
 *
 * @param <E> the type of the elements.
 * @param <C> the type of the consumer.
 * @param <P> the type of the predicate.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface PrimitiveNavigableSet
		<E, C, P>
		extends
		PrimitiveSortedSet<E, C, P>,
		NavigableSet<E> {
	@Override
	PrimitiveIterator<E, C> descendingIterator();

	@Override
	PrimitiveNavigableSet<E, C, P> descendingSet();

	@Override
	PrimitiveNavigableSet<E, C, P> headSet(E endElement, boolean inclusive);

	@Override
	PrimitiveNavigableSet<E, C, P> subSet(E beginElement, boolean beginInclusive, E endElement, boolean endInclusive);

	@Override
	PrimitiveNavigableSet<E, C, P> tailSet(E beginElement, boolean inclusive);

	//primitive ceilingPrimitive(primitive element)
	//primitive floorPrimitive(primitive element)
	//boolean hasCeiling(primitive element)
	//boolean hasFloor(primitive element)
	//boolean hasHigher(primitive element)
	//boolean hasLower(primitive element)
	//PrimitiveNavigableSet headSet(primitive endElement, boolean inclusive)
	//primitive higherPrimitive(primitive element)
	//primitive lowerPrimitive(primitive element)
	//primitive pollFirstPrimitive()
	//primitive pollLastPrimitive()
	//PrimitiveNavigableSet subSet(primitive beginElement, boolean beginInclusive, primitive endElement, boolean endInclusive)
	//PrimitiveNavigableSet tailSet(primitive beginElement, boolean inclusive)
}
