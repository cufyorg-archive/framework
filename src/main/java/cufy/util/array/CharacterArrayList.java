package cufy.util.array;

import cufy.util.Arrays;
import cufy.util.Objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * A list backed by an array.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.07.24
 */
public final class CharacterArrayList implements List<Character>, Serializable, RandomAccess {
	//todo parallelStream | sort | spliterator | stream

	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = -5690875689599256068L;

	/**
	 * The backing array.
	 */
	private final char[] array;
	/**
	 * The index where the area backing this list in the given {@code array} is starting.
	 */
	private final int index;
	/**
	 * The length of this list.
	 */
	private final int length;

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the constructed list.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public CharacterArrayList(char... array) {
		Objects.requireNonNull(array, "array");
		this.array = array;
		this.index = 0;
		this.length = array.length;
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param index the index where the area backing the constructed list in the given {@code array} is starting.
	 * @param array the array backing the constructed list.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index > array.length}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public CharacterArrayList(int index, char... array) {
		Objects.requireNonNull(array, "array");
		Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
		Objects.require(index, array.length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "index");
		this.array = array;
		this.index = index;
		this.length = array.length;
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param index  the index where the area backing the constructed list in the given {@code array} is starting.
	 * @param length the length of the constructed list.
	 * @param array  the array backing the constructed list.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code length < 0} or {@code index + length >
	 *                                        array.length}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public CharacterArrayList(int index, int length, char... array) {
		Objects.requireNonNull(array, "array");
		Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
		Objects.require(length, Objects::nonNegative, "length");
		Objects.require(index + length, array.length, Objects::nonGreater,
				ArrayIndexOutOfBoundsException.class, "index + length");
		this.array = array;
		this.index = index;
		this.length = length;
	}

	@Override
	public boolean add(Character element) {
		throw new UnsupportedOperationException("add");
	}

	@Override
	public void add(int index, Character element) {
		Objects.require(index, Objects::nonNegative, IndexOutOfBoundsException.class, "index");
		Objects.require(index, this.length, Objects::isLess, IndexOutOfBoundsException.class, "index");
		throw new UnsupportedOperationException("add");
	}

	@Override
	public boolean addAll(Collection<? extends Character> collection) {
		Objects.requireNonNull(collection, "collection");
		if (collection.isEmpty())
			return false;

		throw new UnsupportedOperationException("addAll");
	}

	@Override
	public boolean addAll(int index, Collection<? extends Character> collection) {
		Objects.requireNonNull(collection, "collection");
		Objects.require(index, Objects::nonNegative, "index");
		Objects.require(index, this.length, Objects::isLess, "index");
		if (collection.isEmpty())
			return false;

		throw new UnsupportedOperationException("addAll");
	}

	@Override
	public void clear() {
		if (this.length != 0)
			throw new UnsupportedOperationException("clear");
	}

	@Override
	public boolean contains(Object object) {
		if (object instanceof Character) {
			char primitive = (char) object;

			for (int i = 0; i < this.length; i++) {
				char o = this.array[this.index + i];

				if (primitive == o)
					return true;
			}
		}

		return false;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		Objects.requireNonNull(collection, "collection");

		for0:
		for (Object object : collection) {
			if (object instanceof Character) {
				char primitive = (char) object;

				for (int i = 0; i < this.length; i++) {
					char o = this.array[this.index + i];

					if (primitive == o)
						continue for0;
				}
			}

			return false;
		}

		return true;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object instanceof CharacterArrayList) {
			CharacterArrayList list = (CharacterArrayList) object;

			if (list.length != this.length)
				return false;
			if (list.array == this.array && this.index == list.index)
				return true;
			for (int i = 0; i < this.length; i++) {
				char o = list.array[list.index + i];
				char t = this.array[this.index + i];

				if (o != t)
					return false;
			}

			return true;
		}
		if (object instanceof List) {
			Iterator iterator = ((Iterable) object).iterator();

			int i = 0;
			while (iterator.hasNext()) {
				if (i < this.length) {
					Object o = iterator.next();

					if (o instanceof Character) {
						char p = (char) o;
						char t = this.array[this.index + i++];

						if (p == t)
							continue;
					}
				}

				return false;
			}

			return i == this.length;
		}

		return false;
	}

	@Override
	public void forEach(Consumer<? super Character> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		for (int i = 0; i < this.length; i++)
			consumer.accept(this.array[this.index + i]);
	}

	@Override
	public Character get(int index) {
		Objects.require(index, Objects::nonNegative, "index");
		Objects.require(index, this.length, Objects::isLess, "index");
		return this.array[this.index + index];
	}

	@Override
	public int hashCode() {
		int hashCode = 1;

		for (int i = 0; i < this.length; i++) {
			char element = this.array[this.index + i];

			hashCode = 31 * hashCode + Character.hashCode(element);
		}

		return hashCode;
	}

	@Override
	public int indexOf(Object object) {
		if (object instanceof Character) {
			char primitive = (char) object;

			for (int i = 0; i < this.length; i++) {
				char o = this.array[this.index + i];

				if (primitive == o)
					return i;
			}
		}

		return -1;
	}

	@Override
	public boolean isEmpty() {
		return this.length == 0;
	}

	@Override
	public CharacterArrayIterator iterator() {
		return new CharacterArrayIterator(0, this.index, this.length, this.array);
	}

	@Override
	public int lastIndexOf(Object object) {
		if (object instanceof Character) {
			char primitive = (char) object;

			for (int i = this.length - 1; i >= 0; i--) {
				char o = this.array[this.index + i];

				if (primitive == o)
					return i;
			}
		}

		return -1;
	}

	@Override
	public CharacterArrayIterator listIterator() {
		return new CharacterArrayIterator(0, this.index, this.length, this.array);
	}

	@Override
	public CharacterArrayIterator listIterator(int index) {
		return new CharacterArrayIterator(index, this.index, this.length, this.array);
	}

	@Override
	public boolean remove(Object object) {
		if (object instanceof Character) {
			char primitive = (char) object;

			for (int i = 0; i < this.length; i++) {
				char o = this.array[this.index + i];

				if (primitive == o)
					throw new UnsupportedOperationException("remove");
			}
		}

		return false;
	}

	@Override
	public Character remove(int index) {
		Objects.require(index, Objects::nonNegative, "index");
		Objects.require(index, this.length, Objects::isLess, "index");
		throw new UnsupportedOperationException("remove");
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		Objects.requireNonNull(collection, "collection");
		for (Object object : collection)
			if (object instanceof Character) {
				char primitive = (char) object;

				for (int i = 0; i < this.length; i++) {
					char o = this.array[this.index + i];

					if (primitive == o)
						throw new UnsupportedOperationException("removeAll");
				}
			}

		return false;
	}

	@Override
	public boolean removeIf(Predicate<? super Character> predicate) {
		Objects.requireNonNull(predicate, "predicate");
		for (int i = 0; i < this.length; i++)
			if (predicate.test(this.array[this.index + i]))
				throw new UnsupportedOperationException("removeIf");

		return false;
	}

	@Override
	public void replaceAll(UnaryOperator<Character> operator) {
		Objects.requireNonNull(operator, "operator");
		for (int i = 0; i < this.length; i++)
			this.array[this.index + i] = operator.apply(this.array[this.index + i]);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		Objects.requireNonNull(collection, "collection");
		for0:
		for (int i = 0; i < this.length; i++) {
			char o = this.array[this.index + i];

			for (Object object : collection)
				if (object instanceof Character) {
					char primitive = (char) object;

					if (primitive == o)
						continue for0;
				}

			throw new UnsupportedOperationException("retainAll");
		}

		return false;
	}

	@Override
	public Character set(int index, Character element) {
		Objects.require(index, Objects::nonNegative, "index");
		Objects.require(index, this.length, Objects::isLess, "index");
		char old = this.array[this.index + index];
		this.array[this.index + index] = element;
		return old;
	}

	@Override
	public int size() {
		return this.length;
	}

	@Override
	public CharacterArrayList subList(int beginIndex, int endIndex) {
		Objects.require(beginIndex, Objects::nonNegative, IndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, "beginIndex");
		Objects.require(endIndex, this.length, Objects::nonGreater, IndexOutOfBoundsException.class, "endIndex");
		return new CharacterArrayList(this.index + beginIndex, this.index + endIndex, this.array);
	}

	@Override
	public Character[] toArray() {
		return (Character[]) Arrays.copyOfRange0(this.array, this.index, this.index + this.length, Character[].class);
	}

	@Override
	public <T> T[] toArray(T[] array) {
		Objects.requireNonNull(array, "array");

		if (array.length < this.length)
			return (T[]) Arrays.copyOfRange0(this.array, this.index, this.index + this.length, array.getClass());

		Arrays.hardcopy(this.array, this.index, array, 0, this.length);

		if (array.length > this.length)
			array[this.length] = null;

		return array;
	}

	@Override
	public String toString() {
		if (this.length == 0)
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = 0;
		while (true) {
			builder.append(this.array[this.index + i]);

			if (++i < this.length) {
				builder.append(", ");
				continue;
			}

			return builder.append("]")
					.toString();
		}
	}
}
