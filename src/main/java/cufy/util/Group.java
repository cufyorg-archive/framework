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

import java.util.Collection;
import java.util.function.Predicate;

/**
 * A way to elements and get subgroups of them. This class holds the elements of it as a final elements. And give the user the ability to
 * iterate the elements back. Or get a subgroup of the elements it has got. The subgroup will be saved by it for the next requests for that group.
 * {@link #equals} should return if this group contains all the of the elements of the other group. but the state of the subgroups will be ignored.
 *
 * @param <E> the type of the elements this group holds
 * @author lsafer
 * @version 0.1.3
 * @since 25-Jan-2020
 */
public interface Group<E> extends Collection<E> {
	/**
	 * Returns a subgroup of this group. The subgroup returned has the elements of this that satisfies the given predicate. The given predicate will
	 * be invoked only if the given key has not been resolved previously. Otherwise the results of that previous call will be returned. The functions
	 * of the returned group should be remotely effecting the content of this group. There is no guarantee that the previously resolved subgroup will
	 * be the returned subgroup, but the returned subgroup SHOULD effect the previously solved subgroup.
	 *
	 * @param key       the key of that group (saves the group for later)
	 * @param predicate the tester to be satisfied
	 * @return a subgroup of this group that has only the elements satisfied the given predicate
	 * @throws NullPointerException if the given 'predicate' is null
	 */
	Group<E> subGroup(Object key, Predicate<E> predicate);
	/**
	 * Returns the group with the given key. There is no guarantee that the previously resolved subgroup will
	 * be the returned subgroup, but the returned subgroup SHOULD effect the previously solved subgroup.
	 *
	 * @param key the key of the group
	 * @return the group with the given key. Or null if no group with that key have been stored.
	 */
	Group<E> subGroup(Object key);
}
