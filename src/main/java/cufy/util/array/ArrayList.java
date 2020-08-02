package cufy.util.array;

import cufy.util.Arrays;
import cufy.util.Objects;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * A list backed by an array.
 *
 * @param <E> the type of the elements.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.07.24
 */
public final class ArrayList<E> implements List<E>, Serializable, RandomAccess {
	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = -4250091141258397846L;

	/**
	 * The backing array.
	 */
	@SuppressWarnings("NonSerializableFieldInSerializableClass")
	private final E[] array;
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
	public ArrayList(E... array) {
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
	public ArrayList(int index, E... array) {
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
	public ArrayList(int index, int length, E... array) {
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
	public boolean add(E element) {
		throw new UnsupportedOperationException("add");
	}

	@Override
	public void add(int index, E element) {
		Objects.require(index, Objects::nonNegative, IndexOutOfBoundsException.class, "index");
		Objects.require(index, this.length, Objects::isLess, IndexOutOfBoundsException.class, "index");
		throw new UnsupportedOperationException("add");
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		Objects.requireNonNull(collection, "collection");
		if (collection.isEmpty())
			return false;

		throw new UnsupportedOperationException("addAll");
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> collection) {
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
		for (int i = 0; i < this.length; i++) {
			Object o = this.array[this.index + i];

			if (object == o || object != null && object.equals(o))
				return true;
		}

		return false;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		Objects.requireNonNull(collection, "collection");

		for0:
		for (Object object : collection) {
			for (int i = 0; i < this.length; i++) {
				Object o = this.array[this.index + i];

				if (object == o || object != null && object.equals(o))
					continue for0;
			}

			return false;
		}

		return true;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object instanceof ArrayList) {
			ArrayList list = (ArrayList) object;

			if (list.length != this.length)
				return false;
			if (list.array == this.array && this.index == list.index)
				return true;
			for (int i = 0; i < this.length; i++) {
				Object o = list.array[list.index + i];
				Object t = this.array[this.index + i];

				if (o != t && (o == null || !o.equals(t)))
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
					Object t = this.array[this.index + i++];

					if (o == t || o != null && o.equals(t))
						continue;
				}

				return false;
			}

			return i == this.length;
		}

		return false;
	}

	@Override
	public void forEach(Consumer<? super E> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		for (int i = 0; i < this.length; i++)
			consumer.accept(this.array[this.index + i]);
	}

	@Override
	public E get(int index) {
		Objects.require(index, Objects::nonNegative, "index");
		Objects.require(index, this.length, Objects::isLess, "index");
		return this.array[this.index + index];
	}

	@Override
	public int hashCode() {
		int hashCode = 1;

		for (int i = 0; i < this.length; i++) {
			Object element = this.array[this.index + i];

			hashCode = 31 * hashCode + (element == null ? 0 : element.hashCode());
		}

		return hashCode;
	}

	@Override
	public int indexOf(Object object) {
		for (int i = 0; i < this.length; i++) {
			Object o = this.array[this.index + i];

			if (object == o || object != null && object.equals(o))
				return i;
		}

		return -1;
	}

	@Override
	public boolean isEmpty() {
		return this.length == 0;
	}

	@Override
	public ArrayIterator<E> iterator() {
		return new ArrayIterator(0, this.index, this.length, this.array);
	}

	@Override
	public int lastIndexOf(Object object) {
		for (int i = this.length - 1; i >= 0; i--) {
			Object o = this.array[this.index + i];

			if (object == o || object != null && object.equals(o))
				return i;
		}

		return -1;
	}

	@Override
	public ArrayIterator<E> listIterator() {
		return new ArrayIterator(0, this.index, this.length, this.array);
	}

	@Override
	public ArrayIterator<E> listIterator(int index) {
		return new ArrayIterator(index, this.index, this.length, this.array);
	}

	@Override
	public Stream<E> parallelStream() {
		return Arrays.stream(this.array, this.index, this.index + this.length).parallel();
	}

	@Override
	public boolean remove(Object object) {
		for (int i = 0; i < this.length; i++) {
			Object o = this.array[this.index + i];

			if (object == o || object != null && object.equals(o))
				throw new UnsupportedOperationException("remove");
		}

		return false;
	}

	@Override
	public E remove(int index) {
		Objects.require(index, Objects::nonNegative, "index");
		Objects.require(index, this.length, Objects::isLess, "index");
		throw new UnsupportedOperationException("remove");
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		Objects.requireNonNull(collection, "collection");
		for (Object object : collection)
			for (int i = 0; i < this.length; i++) {
				Object o = this.array[this.index + i];

				if (object == o || object != null && object.equals(o))
					throw new UnsupportedOperationException("removeAll");
			}

		return false;
	}

	@Override
	public boolean removeIf(Predicate<? super E> predicate) {
		Objects.requireNonNull(predicate, "predicate");
		for (int i = 0; i < this.length; i++)
			if (predicate.test(this.array[this.index + i]))
				throw new UnsupportedOperationException("removeIf");

		return false;
	}

	@Override
	public void replaceAll(UnaryOperator<E> operator) {
		Objects.requireNonNull(operator, "operator");
		for (int i = 0; i < this.length; i++)
			this.array[this.index + i] = operator.apply(this.array[this.index + i]);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		Objects.requireNonNull(collection, "collection");
		for0:
		for (int i = 0; i < this.length; i++) {
			Object o = this.array[this.index + i];

			for (Object object : collection)
				if (object == o || object != null && object.equals(o))
					continue for0;

			throw new UnsupportedOperationException("retainAll");
		}

		return false;
	}

	@Override
	public E set(int index, E element) {
		Objects.require(index, Objects::nonNegative, "index");
		Objects.require(index, this.length, Objects::isLess, "index");
		E old = this.array[this.index + index];
		this.array[this.index + index] = element;
		return old;
	}

	@Override
	public int size() {
		return this.length;
	}

	@Override
	public void sort(Comparator<? super E> comparator) {
		Arrays.sort(this.array, this.index, this.index + this.length, comparator);
	}

	@Override
	public Spliterator<E> spliterator() {
		return Arrays.spliterator(this.array, this.index, this.index + this.length);
	}

	@Override
	public Stream<E> stream() {
		return Arrays.stream(this.array, this.index, this.index + this.length);
	}

	@Override
	public ArrayList<E> subList(int beginIndex, int endIndex) {
		Objects.require(beginIndex, Objects::nonNegative, IndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, "beginIndex");
		Objects.require(endIndex, this.length, Objects::nonGreater, IndexOutOfBoundsException.class, "endIndex");
		return new ArrayList(this.index + beginIndex, this.index + endIndex, this.array);
	}

	@Override
	public E[] toArray() {
		return Arrays.copyOfRange(this.array, this.index, this.index + this.length);
	}

	@Override
	public <T> T[] toArray(T[] array) {
		Objects.requireNonNull(array, "array");

		if (array.length < this.length)
			return (T[]) Arrays.copyOfRange(this.array, this.index, this.index + this.length, (Class) array.getClass());

		System.arraycopy(this.array, this.index, array, 0, this.length);

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
