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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * This class implements the interface {@link Group}. Stores its values by inheriting {@link HashSet} and it's subgroups
 * using a {@link HashMap}.
 *
 * @param <E> the type of the elements in this group
 * @author LSafer
 * @version 0.1.3
 * @since 0.1.3 ~2020.04.20
 */
@SuppressWarnings({"UseOfClone", "CloneableClassInSecureContext"})
public class HashGroup<E> extends HashSet<E> implements Group<E> {
	/**
	 * The subgroups of this group.
	 */
	private final HashMap<Object, Group<E>> subgroups = new HashMap<>();

	/**
	 * The default constructor.
	 */
	public HashGroup() {
	}

	/**
	 * Constructs a new group containing the elements in the specified collection.
	 *
	 * @param collection the collection whose elements are to be placed into this set.
	 * @throws NullPointerException if the given {@code collection} is null.
	 */
	public HashGroup(Collection<? extends E> collection) {
		super(collection);
	}

	/**
	 * Constructs a new, empty group.
	 *
	 * @param initialCapacity the initial capacity of the hash table.
	 * @throws IllegalArgumentException if the given {@code initial capacity} is less than zero.
	 */
	public HashGroup(int initialCapacity) {
		super(initialCapacity);
	}

	/**
	 * Constructs a new, empty set.
	 *
	 * @param initialCapacity the initial capacity of the hash map.
	 * @param loadFactor      the load factor of the hash map.
	 * @throws IllegalArgumentException if the given {@code initial capacity} is less than zero, or if the given {@code
	 *                                  load factor} is nonpositive.
	 */
	public HashGroup(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	@Override
	public HashGroup<E> clone() {
		HashGroup group = (HashGroup) super.clone();
		group.subgroups.putAll(this.subgroups);
		return group;
	}

	@Override
	public boolean equals(Object object) {
		return object == this ||
			   object instanceof Group &&
			   ((Collection) object).size() == this.size() &&
			   this.containsAll((Collection) object);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), this.subgroups);
	}

	@Override
	public Group<E> subGroup(Object key, Predicate<E> predicate) {
		if (this.subgroups.containsKey(key))
			return this.subgroups.get(key);

		HashGroup group = new HashGroup();

		for (E element : this)
			if (predicate.test(element))
				group.add(element);

		this.subgroups.put(key, group);
		return group;
	}

	@Override
	public Group<E> subGroup(Object key) {
		return this.subgroups.get(key);
	}
}
