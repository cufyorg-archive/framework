package cufy.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Predicate;

/**
 * This class implements the interface {@link Group}. Stores it's values by inheriting {@link HashSet} and it's subgroups using a {@link HashMap}.
 *
 * @param <E> the type of the elements in this group
 * @author LSaferSE
 * @version 0.1.3
 * @since 20-Apr-2020
 */
public class HashGroup<E> extends HashSet<E> implements Group<E> {
	/**
	 * The subgroups of this group.
	 */
	private HashMap<Object, Group<E>> subgroups = new HashMap<>();

	/**
	 * The default constructor.
	 */
	public HashGroup() {
	}

	/**
	 * Constructs a new group containing the elements in the specified
	 * collection.
	 *
	 * @param c the collection whose elements are to be placed into this set
	 * @throws NullPointerException if the specified collection is null
	 */
	public HashGroup(Collection<? extends E> c) {
		super(c);
	}

	/**
	 * Constructs a new, empty group.
	 *
	 * @param initialCapacity the initial capacity of the hash table
	 * @throws IllegalArgumentException if the initial capacity is less than zero
	 */
	public HashGroup(int initialCapacity) {
		super(initialCapacity);
	}

	/**
	 * Constructs a new, empty set.
	 *
	 * @param initialCapacity the initial capacity of the hash map
	 * @param loadFactor      the load factor of the hash map
	 * @throws IllegalArgumentException if the initial capacity is less than zero, or if the load factor is nonpositive
	 */
	public HashGroup(int initialCapacity, int loadFactor) {
		super(initialCapacity, loadFactor);
	}

	@Override
	public boolean equals(Object object) {
		return object instanceof Group && this.size() == ((Group) object).size() && this.containsAll((Group) object);
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
