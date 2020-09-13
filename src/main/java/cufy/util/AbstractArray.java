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

import java.io.Serializable;
import java.util.Objects;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * An abstraction of an array backed by an actual.
 *
 * @param <A> the type of the array.
 * @param <E> the type of the elements.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.03
 */
public abstract class AbstractArray<A, E> implements Array<A, E>, Serializable, Cloneable {
	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = 3238786977844647983L;

	/**
	 * The array backing this array.
	 *
	 * @since 0.1.5 ~2020.08.04
	 */
	@SuppressWarnings("NonSerializableFieldInSerializableClass")
	protected final A array;
	/**
	 * The first index of the area at the actual array backing this array.
	 *
	 * @since 0.1.5 ~2020.08.05
	 */
	protected final int beginIndex;
	/**
	 * One past the last index of the area at the actual array backing this array.
	 *
	 * @since 0.1.5 ~2020.08.05
	 */
	protected final int endIndex;

	/**
	 * Construct a new array backed by the given {@code array}.
	 *
	 * @param array the array to be backing the constructed array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.05
	 */
	protected AbstractArray(A array) {
		Objects.requireNonNull(array, "array");
		int length = java.lang.reflect.Array.getLength(array);

		this.array = array;
		this.beginIndex = 0;
		this.endIndex = length;
	}

	/**
	 * Construct a new array backed by the specified range of the given {@code array}. The range
	 * starts at the given {@code beginIndex} and ends before the given {@code endIndex}.
	 *
	 * @param array      the array to be backing the constructed array.
	 * @param beginIndex the first index of the area at the given {@code array} to be backing the
	 *                   constructed array.
	 * @param endIndex   one past the last index of the area at the given {@code array} to be
	 *                   backing the constructed array.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.05
	 */
	protected AbstractArray(A array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");
		int length = java.lang.reflect.Array.getLength(array);

		if (beginIndex < 0)
			throw new ArrayIndexOutOfBoundsException("beginIndex(" + beginIndex + ") < 0");
		if (endIndex > length)
			throw new ArrayIndexOutOfBoundsException(
					"endIndex(" + endIndex + ") > length(" + length + ")");
		if (beginIndex > endIndex)
			throw new IllegalArgumentException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");

		this.array = array;
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;
	}

	/**
	 * Get the thumb at the backing array where the given {@code thumb} is pointing at in this
	 * array.
	 *
	 * @param thumb the thumb at this array.
	 * @return the thumb the given {@code thumb} at this array is backed by at the backing array.
	 * @throws IndexOutOfBoundsException if {@code thumb < 0} or {@code thumb >= length}.
	 * @since 0.1.5 ~2020.08.13
	 */
	protected final int index(int thumb) {
		int length = this.length();

		if (thumb < 0)
			throw new IndexOutOfBoundsException("thumb(" + thumb + ") < 0");
		if (thumb >= length)
			throw new IndexOutOfBoundsException("thumb(" + thumb + ") >= length(" + length + ")");

		return this.beginIndex + thumb;
	}

	/**
	 * Insure that the specified range is a valid range in this array.
	 *
	 * @param beginThumb the first index in the range to be checked.
	 * @return the length of the range.
	 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb > length}.
	 * @since 0.1.5 ~2020.08.06
	 */
	protected final int range(int beginThumb) {
		int length = this.length();

		if (beginThumb < 0)
			throw new IndexOutOfBoundsException("beginThumb(" + beginThumb + ") < 0");
		if (beginThumb > length)
			throw new IndexOutOfBoundsException(
					"beginThumb(" + beginThumb + ") > length(" + length + ")");

		return length - beginThumb;
	}

	/**
	 * Insure that the specified range is a valid range in this array.
	 *
	 * @param beginThumb the first index in the range to be checked.
	 * @param endThumb   one past the last index in the range to be checked.
	 * @return the length of the range between the given {@code beginThumb} and {@code endThumb}.
	 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code endThumb > length}.
	 * @throws IllegalArgumentException  if {@code beginThumb > endThumb}
	 * @since 0.1.5 ~2020.08.06
	 */
	protected final int range(int beginThumb, int endThumb) {
		int length = this.length();

		if (beginThumb < 0)
			throw new ArrayIndexOutOfBoundsException("beginThumb(" + beginThumb + ") < 0");
		if (endThumb > length)
			throw new ArrayIndexOutOfBoundsException(
					"endThumb(" + endThumb + ") > length(" + length + ")");
		if (beginThumb > endThumb)
			throw new IllegalArgumentException(
					"beginThumb(" + beginThumb + ") > endThumb(" + endThumb + ")");

		return length;
	}

	/**
	 * Get the boxed index for the given real {@code index}.
	 *
	 * @param index the real index to get a boxed index for.
	 * @return the boxed index for the given real {@code index}.
	 * @since 0.1.5 ~2020.08.11
	 */
	protected final int thumb(int index) {
		return index - this.beginIndex;
	}

	@Override
	public final int length() {
		return this.endIndex - this.beginIndex;
	}

	@Override
	public A array() {
		return this.endIndex - this.beginIndex ==
			   java.lang.reflect.Array.getLength(this.array) ?
			   this.array :
			   null;
	}

	@Override
	public void arraycopy(A array, int pos, int length) {
		Objects.requireNonNull(array, "array");
		if (length < 0)
			throw new ArrayIndexOutOfBoundsException("length(" + length + ") < 0");
		if (pos < 0)
			throw new ArrayIndexOutOfBoundsException("pos(" + pos + ") < 0");

		int thisLength = this.endIndex - this.beginIndex;
		int arrayLength = java.lang.reflect.Array.getLength(array);

		if (length > thisLength)
			throw new ArrayIndexOutOfBoundsException(
					"length(" + length + ") > this.length(" + thisLength + ")"
			);
		if (pos + length > arrayLength)
			throw new ArrayIndexOutOfBoundsException(
					"pos(" + pos + ") + length(" + length + ") > array.length(" + arrayLength +
					")"
			);

		System.arraycopy(
				this.array,
				this.beginIndex,
				array,
				pos,
				length
		);
	}

	@Override
	public A copy() {
		int length = this.endIndex - this.beginIndex;
		A array = (A) java.lang.reflect.Array.newInstance(
				this.array.getClass().getComponentType(),
				length
		);

		System.arraycopy(
				this.array,
				this.beginIndex,
				array,
				0,
				length
		);

		return array;
	}

	@Override
	public A copy(int length) {
		if (length < 0)
			throw new NegativeArraySizeException("length(" + length + ") < 0");
		A array = (A) java.lang.reflect.Array.newInstance(
				this.array.getClass().getComponentType(),
				length
		);

		System.arraycopy(
				this.array,
				this.beginIndex,
				array,
				0,
				Math.min(this.length(), length)
		);

		return array;
	}

	@Override
	public <T> T copy(Class<? extends T> klass) {
		Objects.requireNonNull(klass, "klass");
		if (!klass.isArray())
			throw new IllegalArgumentException("Not array class");
		int length = this.endIndex - this.beginIndex;

		T product = (T) java.lang.reflect.Array.newInstance(
				klass.getComponentType(),
				length
		);

		this.copy(
				product,
				0,
				length
		);

		return product;
	}

	@Override
	public <T> T copy(Class<? extends T> klass, int length) {
		Objects.requireNonNull(klass, "klass");
		if (!klass.isArray())
			throw new IllegalArgumentException("Not array class");

		T product = (T) java.lang.reflect.Array.newInstance(
				klass.getComponentType(),
				length
		);

		this.copy(
				product,
				0,
				Math.min(length, this.endIndex - this.beginIndex)
		);

		return product;
	}

	@Override
	public Stream<E> stream() {
		return StreamSupport.stream(this.spliterator(), false);
	}

	/**
	 * Determine if the given two elements are equal or not. This is the base equality check in this
	 * class and it should be for its subclasses.
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

	/**
	 * Calculate the hash code of the given element. This is the base hash code algorithm in this
	 * class and it should be for its subclasses.
	 *
	 * @param e the element to calculate its hashCode.
	 * @return the calculated hash code of the given element.
	 * @since 0.1.5 ~2020.08.31
	 */
	protected int hash(E e) {
		return e == null ? 0 : e.hashCode();
	}

	@Override
	public abstract AbstractArray<A, E> clone();

	@Override
	public abstract boolean equals(Object object);

	@Override
	public abstract void forEach(Consumer<? super E> consumer);

	@Override
	public abstract E get(int thumb);

	@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
	@Override
	public abstract int hashCode();

	@Override
	public abstract AbstractArrayIterator iterator();

	@Override
	public abstract AbstractArrayList list();

	@Override
	public abstract AbstractArrayListIterator listIterator();

	@Override
	public abstract <K extends E, V extends E> AbstractArrayMap<K, V> map();

	@Override
	public abstract AbstractArraySpliterator spliterator();

	@Override
	public abstract AbstractArray<A, E> sub(int beginThumb, int endThumb);

	@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
	@Override
	public abstract String toString();

	/**
	 * An iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract class AbstractArrayIterator implements ArrayIterator<E> {
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index;

		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected AbstractArrayIterator() {
			this.index = AbstractArray.this.beginIndex;
		}

		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @param beginThumb the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
		 *                                   length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected AbstractArrayIterator(int beginThumb) {
			AbstractArray.this.range(beginThumb);
			this.index = AbstractArray.this.beginIndex + beginThumb;
		}

		@Override
		public boolean hasNext() {
			return this.index < AbstractArray.this.endIndex;
		}
	}

	/**
	 * A list backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract class AbstractArrayList implements ArrayList<E>, Serializable, RandomAccess, Cloneable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -5890878610114060287L;

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object object : collection) {
				if (this.contains(object))
					continue;

				return false;
			}

			return true;
		}

		@Override
		public void forEach(Consumer<? super E> consumer) {
			AbstractArray.this.forEach(consumer);
		}

		@Override
		public boolean isEmpty() {
			return AbstractArray.this.endIndex <= AbstractArray.this.beginIndex;
		}

		@Override
		public int size() {
			return AbstractArray.this.endIndex - AbstractArray.this.beginIndex;
		}

		@Override
		public void sort(Comparator<? super E> comparator) {
			AbstractArray.this.sort(comparator);
		}

		@Override
		public Object[] toArray() {
			return AbstractArray.this.copy(Object[].class);
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			int length = AbstractArray.this.endIndex - AbstractArray.this.beginIndex;

			if (array.length < length)
				return (T[]) AbstractArray.this.copy(array.getClass());
			if (array.length > length)
				array[length] = null;

			AbstractArray.this.copy(array, 0, length);
			return array;
		}

		@Override
		public String toString() {
			return AbstractArray.this.toString();
		}

		@Override
		public abstract AbstractArrayList clone();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@Override
		public abstract AbstractArrayIterator iterator();

		@Override
		public abstract AbstractArrayListIterator listIterator();

		@Override
		public abstract AbstractArrayListIterator listIterator(int beginThumb);

		@Override
		public abstract AbstractArraySpliterator spliterator();
	}

	/**
	 * A list iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract class AbstractArrayListIterator implements ArrayListIterator<E> {
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index;
		/**
		 * The last index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int last = -1;

		/**
		 * Construct a new list iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected AbstractArrayListIterator() {
			this.index = AbstractArray.this.beginIndex;
		}

		/**
		 * Construct a new list iterator iterating the elements in the enclosing array, starting
		 * from the given {@code beginThumb}.
		 *
		 * @param beginThumb the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
		 *                                   length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected AbstractArrayListIterator(int beginThumb) {
			AbstractArray.this.range(beginThumb);
			this.index = AbstractArray.this.index(beginThumb);
		}

		@Override
		public boolean hasNext() {
			return this.index < AbstractArray.this.endIndex;
		}

		@Override
		public boolean hasPrevious() {
			return this.index > AbstractArray.this.beginIndex;
		}

		@Override
		public int nextIndex() {
			return AbstractArray.this.thumb(this.index);
		}

		@Override
		public int previousIndex() {
			return AbstractArray.this.thumb(this.index - 1);
		}
	}

	/**
	 * A map backed by the enclosing array.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public abstract class AbstractArrayMap<K extends E, V extends E> implements ArrayMap<E, K, V>, Serializable, Cloneable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 7692195336903798598L;

		{
			int length = AbstractArray.this.endIndex - AbstractArray.this.beginIndex;
			if (length % 2 != 0)
				throw new IllegalArgumentException("length(" + length + ") % 2 != 0");
		}

		@Override
		public boolean isEmpty() {
			return AbstractArray.this.endIndex <= AbstractArray.this.beginIndex;
		}

		@Override
		public int size() {
			return AbstractArray.this.endIndex - AbstractArray.this.beginIndex >>> 1;
		}

		@Override
		public abstract AbstractArrayMap clone();

		@Override
		public abstract AbstractArrayEntrySet entrySet();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@Override
		public abstract AbstractArrayKeySet keySet();

		@Override
		public abstract String toString();

		@Override
		public abstract AbstractArrayValues values();

		/**
		 * An entry backed by a range from {@code index} to {@code index + 1} in the enclosing
		 * array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public abstract class AbstractArrayEntry implements ArrayEntry<E, K, V>, Serializable, Cloneable {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -1793396182662638233L;

			/**
			 * The index of the key of this entry in the backing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected final int index;

			/**
			 * Construct a new entry backed by a range from {@code thumb} to {@code thumb + 1} in
			 * the enclosing array.
			 *
			 * @param thumb the thumb to where the key (followed by the value) will be in the
			 *              constructed entry.
			 * @throws IndexOutOfBoundsException if {@code thumb < 0} or {@code thumb + 1 >=
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractArrayEntry(int thumb) {
				AbstractArray.this.range(thumb, thumb + 1);
				this.index = AbstractArray.this.index(thumb);
			}

			@Override
			public abstract AbstractArrayEntry clone();

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract boolean equals(Object object);

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract int hashCode();

			@Override
			public abstract String toString();
		}

		/**
		 * An iterator iterating the entries in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public abstract class AbstractArrayEntryIterator implements ArrayEntryIterator<E, K, V> {
			/**
			 * The next index.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected int index;

			/**
			 * Construct a new iterator iterating the entries in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractArrayEntryIterator() {
				this.index = AbstractArray.this.beginIndex;
			}

			/**
			 * Construct a new iterator iterating the entries in the enclosing array, starting from
			 * the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed iterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractArrayEntryIterator(int beginThumb) {
				AbstractArray.this.range(beginThumb);
				this.index = AbstractArray.this.index(beginThumb);
			}

			@Override
			public boolean hasNext() {
				return this.index < AbstractArray.this.endIndex;
			}

			@SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
			@Override
			public abstract AbstractArrayEntry next();
		}

		/**
		 * A set backed by the entries in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public abstract class AbstractArrayEntrySet implements ArrayEntrySet<E, K, V>, Serializable, Cloneable {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -7515045887948351373L;

			@Override
			public boolean containsAll(Collection<?> collection) {
				Objects.requireNonNull(collection, "collection");

				for (Object object : collection) {
					if (this.contains(object))
						continue;

					return false;
				}

				return true;
			}

			@Override
			public boolean isEmpty() {
				return AbstractArray.this.endIndex <= AbstractArray.this.beginIndex;
			}

			@Override
			public int size() {
				return AbstractArray.this.endIndex - AbstractArray.this.beginIndex >>> 1;
			}

			@Override
			public abstract AbstractArrayEntrySet clone();

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract boolean contains(Object object);

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract boolean equals(Object object);

			@Override
			public abstract void forEach(Consumer<? super Map.Entry<K, V>> consumer);

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract int hashCode();

			@Override
			public abstract AbstractArrayEntryIterator iterator();

			@Override
			public abstract AbstractArrayEntrySpliterator spliterator();

			@Override
			public abstract <T> T[] toArray(T[] array);

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract Object[] toArray();

			@Override
			public abstract String toString();
		}

		/**
		 * A spliterator iterating the entries in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.02
		 */
		public abstract class AbstractArrayEntrySpliterator implements ArrayEntrySpliterator<E, K, V> {
			/**
			 * The next index.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected int index;

			/**
			 * Construct a new spliterator iterating the entries in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractArrayEntrySpliterator() {
				this.index = AbstractArray.this.beginIndex;
			}

			/**
			 * Construct a new spliterator iterating the entries in the enclosing array, starting
			 * from the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed spliterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractArrayEntrySpliterator(int beginThumb) {
				AbstractArray.this.range(beginThumb);
				this.index = AbstractArray.this.index(beginThumb);
			}

			@Override
			public long estimateSize() {
				return AbstractArray.this.endIndex - this.index >>> 1;
			}

			@Override
			public long getExactSizeIfKnown() {
				return AbstractArray.this.endIndex - this.index >>> 1;
			}
		}

		/**
		 * An iterator iterating the keys in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public abstract class AbstractArrayKeyIterator implements ArrayKeyIterator<E, K> {
			/**
			 * The next index.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected int index;

			/**
			 * Construct a new iterator iterating the keys in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractArrayKeyIterator() {
				this.index = AbstractArray.this.beginIndex;
			}

			/**
			 * Construct a new iterator iterating the keys in the enclosing array, starting from the
			 * given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed iterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractArrayKeyIterator(int beginThumb) {
				AbstractArray.this.range(beginThumb);
				this.index = AbstractArray.this.index(beginThumb);
			}

			@Override
			public boolean hasNext() {
				return this.index < AbstractArray.this.endIndex;
			}
		}

		/**
		 * A set backed by the keys in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public abstract class AbstractArrayKeySet implements ArrayKeySet<E, K>, Serializable, Cloneable {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 4627018232494058734L;

			@Override
			public boolean contains(Object object) {
				return AbstractArrayMap.this.containsKey(object);
			}

			@Override
			public boolean containsAll(Collection collection) {
				Objects.requireNonNull(collection, "collection");

				for (Object object : collection) {
					if (AbstractArrayMap.this.containsKey(object))
						continue;

					return false;
				}

				return true;
			}

			@Override
			public boolean isEmpty() {
				return AbstractArray.this.endIndex <= AbstractArray.this.beginIndex;
			}

			@Override
			public int size() {
				return AbstractArray.this.endIndex - AbstractArray.this.beginIndex >>> 1;
			}

			@Override
			public abstract AbstractArrayKeySet clone();

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract boolean equals(Object object);

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract int hashCode();

			@Override
			public abstract AbstractArrayKeyIterator iterator();

			@Override
			public abstract AbstractArrayKeySpliterator spliterator();

			@Override
			public abstract <T> T[] toArray(T[] array);

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract Object[] toArray();

			@Override
			public abstract String toString();
		}

		/**
		 * A spliterator iterating the keys in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.02
		 */
		public abstract class AbstractArrayKeySpliterator implements ArrayKeySpliterator<E, K> {
			/**
			 * The next index.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected int index;

			/**
			 * Construct a new spliterator iterating the keys in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractArrayKeySpliterator() {
				this.index = AbstractArray.this.index(0);
			}

			/**
			 * Construct a new spliterator iterating the keys in the enclosing array, starting from
			 * the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed spliterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractArrayKeySpliterator(int beginThumb) {
				AbstractArray.this.range(beginThumb);
				this.index = AbstractArray.this.index(beginThumb);
			}

			@Override
			public long estimateSize() {
				return AbstractArray.this.endIndex - this.index >>> 1;
			}

			@Override
			public long getExactSizeIfKnown() {
				return AbstractArray.this.endIndex - this.index >>> 1;
			}
		}

		/**
		 * An iterator iterating the values in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public abstract class AbstractArrayValueIterator implements ArrayValueIterator<E, V> {
			/**
			 * The next index.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected int index;

			/**
			 * Construct a new iterator iterating the values in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractArrayValueIterator() {
				this.index = AbstractArray.this.beginIndex;
			}

			/**
			 * Construct a new iterator iterating the values in the enclosing array, starting from
			 * the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed iterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractArrayValueIterator(int beginThumb) {
				AbstractArray.this.range(beginThumb);
				this.index = AbstractArray.this.beginIndex + beginThumb;
			}

			@Override
			public boolean hasNext() {
				return this.index < AbstractArray.this.endIndex;
			}
		}

		/**
		 * A spliterator iterating the values in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.02
		 */
		public abstract class AbstractArrayValueSpliterator implements ArrayValueSpliterator<E, V> {
			/**
			 * The next index.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected int index;

			/**
			 * Construct a new spliterator iterating the values in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractArrayValueSpliterator() {
				this.index = AbstractArray.this.beginIndex;
			}

			/**
			 * Construct a new spliterator iterating the values in the enclosing array, starting
			 * from the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed spliterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractArrayValueSpliterator(int beginThumb) {
				AbstractArray.this.range(beginThumb);
				this.index = AbstractArray.this.beginIndex + beginThumb;
			}

			@Override
			public long estimateSize() {
				return AbstractArray.this.endIndex - this.index >>> 1;
			}

			@Override
			public long getExactSizeIfKnown() {
				return AbstractArray.this.endIndex - this.index >>> 1;
			}
		}

		/**
		 * A collection backed by the values in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public abstract class AbstractArrayValues implements ArrayValues<E, V>, Serializable, Cloneable {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 7634421013079755116L;

			@Override
			public boolean contains(Object object) {
				return AbstractArrayMap.this.containsValue(object);
			}

			@Override
			public boolean containsAll(Collection<?> collection) {
				Objects.requireNonNull(collection, "collection");

				for (Object object : collection) {
					if (AbstractArrayMap.this.containsValue(object))
						continue;

					return false;
				}

				return true;
			}

			@Override
			public boolean isEmpty() {
				return AbstractArray.this.endIndex <= AbstractArray.this.beginIndex;
			}

			@Override
			public int size() {
				return AbstractArray.this.endIndex - AbstractArray.this.beginIndex >>> 1;
			}

			@Override
			public abstract AbstractArrayValues clone();

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract boolean equals(Object object);

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract int hashCode();

			@Override
			public abstract AbstractArrayValueIterator iterator();

			@Override
			public abstract AbstractArrayValueSpliterator spliterator();

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract Object[] toArray();

			@Override
			public abstract <T> T[] toArray(T[] array);

			@Override
			public abstract String toString();
		}
	}

	/**
	 * A spliterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public abstract class AbstractArraySpliterator implements ArraySpliterator<E> {
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index;

		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from
		 * the given {@code index}.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected AbstractArraySpliterator() {
			this.index = AbstractArray.this.beginIndex;
		}

		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from
		 * the given {@code beginThumb}.
		 *
		 * @param beginThumb the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
		 *                                   length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected AbstractArraySpliterator(int beginThumb) {
			AbstractArray.this.range(beginThumb);
			this.index = AbstractArray.this.beginIndex + beginThumb;
		}

		@Override
		public long estimateSize() {
			return AbstractArray.this.endIndex - this.index;
		}

		@Override
		public long getExactSizeIfKnown() {
			return AbstractArray.this.endIndex - this.index;
		}
	}
}
