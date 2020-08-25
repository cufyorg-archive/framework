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
package cufy.util.collection;

import cufy.util.Objects;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A class holding the nCopy collection classes. nCopy collections are unmodifiable collections.
 * <p>
 * Note: this class chosen to be an interface to allow inheritance in the utility classes.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.22
 */
public interface NCopiesCollections {
	/**
	 * Construct a new list from repeating the given {@code element} {@code n} many times.
	 *
	 * @param n       the number of repetition the constructed list will repeat the given {@code
	 *                n}.
	 * @param element the element to be repeated.
	 * @param <T>     the type of the element.
	 * @return a list from repeating the given {@code element} {@code n} many times.
	 * @throws IllegalArgumentException if {@code n < 0}.
	 * @see java.util.Collections#nCopies(int, Object)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> NCopiesList<T> nCopies(int n, T element) {
		return new NCopiesList(n, element);
	}

	/**
	 * Construct a new collection from repeating the given {@code element} {@code n} many times.
	 *
	 * @param n       the number of repetition the constructed collection will repeat the given
	 *                {@code n}.
	 * @param element the element to be repeated.
	 * @param <T>     the type of the element.
	 * @return a collection from repeating the given {@code element} {@code n} many times.
	 * @throws IllegalArgumentException if {@code n < 0}.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> NCopiesCollection<T> nCopiesCollection(int n, T element) {
		return new NCopiesCollection(n, element);
	}

	/**
	 * Construct a new enumeration from repeating the given {@code element} {@code n} many times.
	 *
	 * @param n       the number of repetition the constructed enumeration will repeat the given
	 *                {@code n}.
	 * @param element the element to be repeated.
	 * @param <T>     the type of the element.
	 * @return a enumeration from repeating the given {@code element} {@code n} many times.
	 * @throws IllegalArgumentException if {@code n < 0}.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> NCopiesEnumeration<T> nCopiesEnumeration(int n, T element) {
		return new NCopiesEnumeration(n, element);
	}

	/**
	 * Construct a new iterator from repeating the given {@code element} {@code n} many times.
	 *
	 * @param n       the number of repetition the constructed iterator will repeat the given {@code
	 *                n}.
	 * @param element the element to be repeated.
	 * @param <T>     the type of the element.
	 * @return a iterator from repeating the given {@code element} {@code n} many times.
	 * @throws IllegalArgumentException if {@code n < 0}.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> NCopiesIterator<T> nCopiesIterator(int n, T element) {
		return new NCopiesIterator(n, element);
	}

	/**
	 * Construct a new iterator from repeating the given {@code element} {@code n} many times.
	 *
	 * @param n       the number of repetition the constructed iterator will repeat the given {@code
	 *                n}.
	 * @param element the element to be repeated.
	 * @param <T>     the type of the element.
	 * @return a iterator from repeating the given {@code element} {@code n} many times.
	 * @throws IllegalArgumentException if {@code n < 0}.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> NCopiesListIterator<T> nCopiesListIterator(int n, T element) {
		return new NCopiesListIterator(n, element);
	}

	/**
	 * Construct a new spliterator from repeating the given {@code element} {@code n} many times.
	 *
	 * @param n       the number of repetition the constructed spliterator will repeat the given
	 *                {@code n}.
	 * @param element the element to be repeated.
	 * @param <T>     the type of the element.
	 * @return a spliterator from repeating the given {@code element} {@code n} many times.
	 * @throws IllegalArgumentException if {@code n < 0}.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> NCopiesSpliterator<T> nCopiesSpliterator(int n, T element) {
		return new NCopiesSpliterator(n, element);
	}

	/**
	 * A collection from repeating an element (n) number of times.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	class NCopiesCollection<E> implements Collection<E>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 2273830207474183967L;

		/**
		 * The repeated element.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		@SuppressWarnings("NonSerializableFieldInSerializableClass")
		protected final E element;
		/**
		 * The number of repetition of the element.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final int n;

		/**
		 * Construct a new collection from {@code n} copies of the given {@code element}.
		 *
		 * @param n       the number of repetition of the element.
		 * @param element the element to be repeated.
		 * @throws IllegalArgumentException if {@code n < 0}.
		 * @since 0.1.5 ~2020.08.22
		 */
		public NCopiesCollection(int n, E element) {
			if (n < 0)
				throw new IllegalArgumentException("n(" + n + ") < 0");
			this.element = element;
			this.n = n;
		}

		@Override
		public boolean add(E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends E> collection) {
			throw new UnsupportedOperationException("addAll");
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException("clear");
		}

		@Override
		public boolean contains(Object object) {
			return this.n != 0 && this.eq(object, this.element);
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			if (this.n != 0) {
				for (Object object : collection) {
					if (this.eq(object, this.element))
						continue;

					return false;
				}

				return true;
			}

			return false;
		}

		@Override
		public void forEach(Consumer<? super E> consumer) {
			for (int i = 0; i < this.n; i++)
				consumer.accept(this.element);
		}

		@Override
		public boolean isEmpty() {
			return this.n == 0;
		}

		@Override
		public NCopiesIterator<E> iterator() {
			return new NCopiesIterator(this.n, this.element);
		}

		@Override
		public Stream<E> parallelStream() {
			return StreamSupport.stream(new NCopiesSpliterator(
					this.n, this.element
			), true);
		}

		@Override
		public boolean remove(Object object) {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			throw new UnsupportedOperationException("removeAll");
		}

		@Override
		public boolean removeIf(Predicate<? super E> predicate) {
			if (predicate.test(this.element))
				throw new UnsupportedOperationException("remove");

			return false;
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			throw new UnsupportedOperationException("retainAll");
		}

		@Override
		public int size() {
			return this.n;
		}

		@Override
		public NCopiesSpliterator<E> spliterator() {
			return new NCopiesSpliterator(this.n, this.element);
		}

		@Override
		public Stream<E> stream() {
			return StreamSupport.stream(new NCopiesSpliterator(
					this.n, this.element
			), false);
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			T[] product = array;

			if (array.length < this.n)
				product = (T[]) Array.newInstance(array.getClass().getComponentType(), this.n);
			else if (product.length > this.n)
				product[this.n] = null;

			for (int i = 0; i < this.n; i++)
				product[i] = (T) this.element;

			return product;
		}

		@Override
		public Object[] toArray() {
			Object[] array = new Object[this.n];

			for (int i = 0; i < this.n; i++)
				array[i] = this.element;

			return array;
		}

		@Override
		public String toString() {
			if (this.n == 0)
				return "[]";

			StringJoiner joiner = new StringJoiner(", ", "[", "]");

			for (int i = 0; i < this.n; i++)
				joiner.add(String.valueOf(this.element));

			return joiner.toString();
		}

		/**
		 * Determine if the given two elements are equal or not. This is the base equality check in
		 * this class and it should be for its subclasses.
		 *
		 * @param element the first element.
		 * @param e       the second element.
		 * @return true, if the given {@code element} equals the given {@code e} in this class's
		 * 		standard.
		 * @since 0.1.5 ~2020.08.18
		 */
		protected boolean eq(Object element, E e) {
			return element == e || element != null && element.equals(e);
		}
	}

	/**
	 * An enumeration from repeating an element (n) number of times.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	class NCopiesEnumeration<E> implements Enumeration<E> {
		/**
		 * The repeated element.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final E element;
		/**
		 * The number of repetition of the element.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final int n;
		/**
		 * The current index.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected int i;

		/**
		 * Construct a new enumeration from {@code n} copies of the given {@code element}.
		 *
		 * @param n       the number of repetition of the element.
		 * @param element the element to be repeated.
		 * @throws IllegalArgumentException if {@code n < 0}.
		 * @since 0.1.5 ~2020.08.22
		 */
		public NCopiesEnumeration(int n, E element) {
			if (n < 0)
				throw new IllegalArgumentException("n(" + n + ") < 0");
			this.element = element;
			this.n = n;
		}

		@Override
		public boolean hasMoreElements() {
			return this.i < this.n;
		}

		@Override
		public E nextElement() {
			int i = this.i;

			if (i < this.n) {
				this.i++;
				return this.element;
			}

			throw new NoSuchElementException();
		}
	}

	/**
	 * An iterator from repeating an element (n) number of times.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	class NCopiesIterator<E> implements Iterator<E> {
		/**
		 * The repeated element.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final E element;
		/**
		 * The number of repetition of the element.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final int n;
		/**
		 * The current index.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected int i;

		/**
		 * Construct a new iterator from {@code n} copies of the given {@code element}.
		 *
		 * @param n       the number of repetition of the element.
		 * @param element the element to be repeated.
		 * @throws IllegalArgumentException if {@code n < 0}.
		 * @since 0.1.5 ~2020.08.22
		 */
		public NCopiesIterator(int n, E element) {
			if (n < 0)
				throw new IllegalArgumentException("n(" + n + ") < 0");
			this.element = element;
			this.n = n;
		}

		@Override
		public void forEachRemaining(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int i = this.i;
			this.i = this.n;

			while (i++ < this.n)
				consumer.accept(this.element);
		}

		@Override
		public boolean hasNext() {
			return this.i < this.n;
		}

		@Override
		public E next() {
			int i = this.i;

			if (i < this.n) {
				this.i++;
				return this.element;
			}

			throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}
	}

	/**
	 * A list from repeating an element (n) number of times.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	class NCopiesList<E> extends NCopiesCollection<E> implements List<E>, RandomAccess {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -8959888285748864462L;

		/**
		 * Construct a new list from {@code n} copies of the given {@code element}.
		 *
		 * @param n       the number of repetition of the element.
		 * @param element the element to be repeated.
		 * @throws IllegalArgumentException if {@code n < 0}.
		 * @since 0.1.5 ~2020.08.22
		 */
		public NCopiesList(int n, E element) {
			super(n, element);
		}

		@Override
		public void add(int index, E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(int index, Collection<? extends E> collection) {
			throw new UnsupportedOperationException("addAll");
		}

		@Override
		public boolean equals(Object object) {
			return object == this ||
				   object instanceof List &&
				   ((List) object).size() == this.n &&
				   this.containsAll((List) object);
		}

		@Override
		public E get(int index) {
			if (index < 0)
				throw new IndexOutOfBoundsException("index(" + index + ") < 0");
			if (index >= this.n)
				throw new IndexOutOfBoundsException("index(" + index + ") >= n(" + this.n + ")");

			return this.element;
		}

		@Override
		public int hashCode() {
			int hashCode = 1;
			int h = this.element == null ? 0 : this.element.hashCode();

			for (int i = 0; i < this.n; i++)
				hashCode = 31 * hashCode + h;

			return h;
		}

		@Override
		public int indexOf(Object object) {
			return this.n != 0 && this.eq(object, this.element) ? 0 : -1;
		}

		@Override
		public int lastIndexOf(Object object) {
			return this.n != 0 && this.eq(object, this.element) ? this.n - 1 : -1;
		}

		@Override
		public NCopiesListIterator<E> listIterator() {
			return new NCopiesListIterator(this.n, this.element);
		}

		@Override
		public NCopiesListIterator<E> listIterator(int index) {
			return new NCopiesListIterator(this.n, this.element, index);
		}

		@Override
		public E remove(int index) {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public void replaceAll(UnaryOperator<E> operator) {
			throw new UnsupportedOperationException("replaceAll");
		}

		@Override
		public E set(int index, E element) {
			throw new UnsupportedOperationException("set");
		}

		@Override
		public void sort(Comparator<? super E> comparator) {
		}

		@Override
		public NCopiesList<E> subList(int beginIndex, int endIndex) {
			if (beginIndex < 0)
				throw new IndexOutOfBoundsException(
						"beginIndex(" + beginIndex + ") < 0"
				);
			if (endIndex > this.n)
				throw new IndexOutOfBoundsException(
						"endIndex(" + endIndex + ") > n(" + this.n + ")"
				);
			if (endIndex < beginIndex)
				throw new IllegalArgumentException(
						"endIndex(" + endIndex + ") < beginIndex(" + beginIndex + ")"
				);
			return new NCopiesList(endIndex - beginIndex, this.element);
		}
	}

	/**
	 * An iterator from repeating an element (n) number of times.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	class NCopiesListIterator<E> extends NCopiesIterator<E> implements ListIterator<E> {
		/**
		 * Construct a new iterator from {@code n} copies of the given {@code element}.
		 *
		 * @param n       the number of repetition of the element.
		 * @param element the element to be repeated.
		 * @throws IllegalArgumentException if {@code n < 0}.
		 * @since 0.1.5 ~2020.08.22
		 */
		public NCopiesListIterator(int n, E element) {
			super(n, element);
		}

		/**
		 * Construct a new iterator from {@code n} copies of the given {@code element}.
		 *
		 * @param n       the number of repetition of the element.
		 * @param element the element to be repeated.
		 * @param i       the initial position of the constructed iterator.
		 * @throws IllegalArgumentException  if {@code n < 0}.
		 * @throws IndexOutOfBoundsException if {@code i < 0} or {@code i > n}.
		 * @since 0.1.5 ~2020.08.22
		 */
		public NCopiesListIterator(int n, E element, int i) {
			super(n, element);
			if (i < 0)
				throw new IndexOutOfBoundsException("i(" + i + ") < 0");
			if (i > n)
				throw new IndexOutOfBoundsException("i(" + i + ") > n(" + n + ")");
			//noinspection AssignmentToSuperclassField
			this.i = i;
		}

		@Override
		public void add(E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasPrevious() {
			return this.i > 0;
		}

		@Override
		public int nextIndex() {
			return this.i;
		}

		@Override
		public E previous() {
			int i = this.i;

			if (i > 0) {
				this.i--;
				return this.element;
			}

			throw new NoSuchElementException();
		}

		@Override
		public int previousIndex() {
			return this.i - 1;
		}

		@Override
		public void set(E element) {
			throw new UnsupportedOperationException("set");
		}
	}

	//NCopiesMap
	//NCopiesNavigableMap
	//NCopiesNavigableSet
	//NCopiesQueue
	//NCopiesRandomAccessList
	//NCopiesSet
	//NCopiesSortedMap
	//NCopiesSortedSet

	/**
	 * A spliterator from repeating an element (n) number of times.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	class NCopiesSpliterator<E> implements Spliterator<E> {
		/**
		 * The characteristics of this spliterator.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		public static final int CHARACTERISTICS = Spliterator.SIZED |
												  Spliterator.SUBSIZED |
												  Spliterator.ORDERED |
												  Spliterator.IMMUTABLE;
		/**
		 * The repeated element.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final E element;
		/**
		 * The number of repetition of the element.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final int n;
		/**
		 * The current index.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected int i;

		/**
		 * Construct a new spliterator from {@code n} copies of the given {@code element}.
		 *
		 * @param n       the number of repetition of the element.
		 * @param element the element to be repeated.
		 * @throws IllegalArgumentException if {@code n < 0}.
		 * @since 0.1.5 ~2020.08.22
		 */
		public NCopiesSpliterator(int n, E element) {
			if (n < 0)
				throw new IllegalArgumentException("n(" + n + ") < 0");
			this.element = element;
			this.n = n;
		}

		@Override
		public int characteristics() {
			return NCopiesSpliterator.CHARACTERISTICS;
		}

		@Override
		public long estimateSize() {
			return this.n;
		}

		@Override
		public void forEachRemaining(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int i = this.i;
			this.i = this.n;

			while (i++ < this.n)
				consumer.accept(this.element);
		}

		@Override
		public Comparator<? super E> getComparator() {
			throw new IllegalStateException();
		}

		@Override
		public long getExactSizeIfKnown() {
			return this.n;
		}

		@Override
		public boolean hasCharacteristics(int characteristics) {
			return (NCopiesSpliterator.CHARACTERISTICS & characteristics) == characteristics;
		}

		@Override
		public boolean tryAdvance(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int i = this.i;

			if (i < this.n) {
				this.i++;
				consumer.accept(this.element);
				return true;
			}

			return false;
		}

		@Override
		public Spliterator<E> trySplit() {
			int i = this.i;
			int half = this.n - i >>> 1;

			if (half > 0) {
				this.i = i + half;
				return new NCopiesSpliterator(half, this.element);
			}

			return null;
		}
	}
}
