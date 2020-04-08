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

import java.util.*;
import java.util.function.Predicate;

/**
 * A way to store final elements and get subgroups of them. This class holds the elements of it as a final elements. And give the user the ability to
 * iterate the elements back. Or get a subgroup of the elements it has got. The subgroup will be saved by it for the next requests for that group.
 *
 * @param <E> the type of the elements this group holds
 * @author lsafer
 * @version 0.1.2
 * @since 25-Jan-2020
 */
public class UnmodifiableGroup<E> extends AbstractSet<E> implements Group<E> {
	/**
	 * The elements of this group.
	 *
	 * @implSpec shouldn't be edited after the constructor
	 */
	final protected Object[] elements;
	/**
	 * The mappings of the subgroups of this group.
	 */
	final protected HashMap<Object, UnmodifiableGroup<E>> subgroups = new HashMap<>();

	/**
	 * Construct a new constant group holding the given elements as it's elements.
	 *
	 * @param elements the elements to be hold
	 * @throws NullPointerException if the given elements is null
	 */
	public UnmodifiableGroup(E... elements) {
		Objects.requireNonNull(elements, "elements");
		this.elements = Arrayu.copyOf(elements, elements.length, Object[].class);
	}

	/**
	 * Construct a new constant group holding the given elements as it's elements.
	 *
	 * @param elements the elements to be hold
	 * @throws NullPointerException if the given elements is null
	 */
	public UnmodifiableGroup(Collection<E> elements) {
		Objects.requireNonNull(elements, "elements");
		this.elements = elements.toArray();
	}

	@Override
	public Iterator<E> iterator() {
		return (Iterator<E>) Arrayu.asList(this.elements).iterator();
	}

	@Override
	public int size() {
		return this.elements.length;
	}

	@Override
	public Object[] toArray() {
		return Arrayu.copyOf(this.elements, this.elements.length, Object[].class);
	}

	@Override
	public <U> U[] toArray(U[] a) {
		Objects.requireNonNull(a, "a");
		int length = a.length;

		if (length < this.elements.length) {
			return (U[]) Arrayu.copyOf(this.elements, this.elements.length, (Class<Object[]>) a.getClass());
		} else {
			if (a.getClass().isAssignableFrom(this.elements.getClass()))
				System.arraycopy(this.elements, 0, a, 0, this.elements.length);
			else Arrayu.hardcopy(this.elements, 0, a, 0, this.elements.length);

			if (length > this.elements.length) {
				a[this.elements.length] = null;
			}

			return a;
		}
	}

	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException("add");
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("remove");
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		throw new UnsupportedOperationException("addAll");
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		throw new UnsupportedOperationException("retainAll");
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("clear");
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		throw new UnsupportedOperationException("removeAll");
	}

	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		throw new UnsupportedOperationException("removeIf");
	}

	@Override
	public UnmodifiableGroup<E> subGroup(Object groupKey, Predicate<E> predicate) {
		Objects.requireNonNull(groupKey, "groupKey");
		Objects.requireNonNull(predicate, "predicate");

		return this.subgroups.computeIfAbsent(groupKey, k -> {
			Set<E> set = new HashSet<>(this.elements.length);

			for (Object element : this.elements)
				if (predicate.test((E) element))
					set.add((E) element);

			return new UnmodifiableGroup<>(set);
		});
	}

	@Override
	public Group<E> subgroup(Object key) {
		return this.subgroups.get(key);
	}
}
