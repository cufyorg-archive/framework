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
package cufy.util.array;

import cufy.util.Objects;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.function.*;
import java.util.stream.IntStream;

/**
 * A holder for an array of {@link Object}s.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.03
 */
public class DoubleArray extends Array<double[], Double> {
	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = 3201994039505608491L;

	/**
	 * Construct a new array backed by the given {@code array}.
	 *
	 * @param array the array to be backing the constructed array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.05
	 */
	public DoubleArray(double... array) {
		super(array);
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
	public DoubleArray(double[] array, int beginIndex, int endIndex) {
		super(array, beginIndex, endIndex);
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements,
	 * and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length,
	 * 		elements, and order.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(double[] array, double[] other) {
		if (array == other)
			return true;
		if (array.length == other.length)
			for (int i = 0; i < array.length; i++) {
				long element = Double.doubleToLongBits(other[i]);
				long e = Double.doubleToLongBits(array[i]);

				if (element == e)
					continue;

				return false;
			}

		return false;
	}

	/**
	 * Calculate the hash code of the elements of the given {@code array}.
	 *
	 * @param array the array to compute its hash code.
	 * @return the hash code of the elements of the given {@code array}.
	 * @see java.util.Arrays#hashCode(double[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int hashCode(double[] array) {
		if (array == null)
			return 0;

		int hashCode = 1;
		for (int i = 0; i < array.length; i++) {
			double e = array[i];
			hashCode = 31 * hashCode + Double.hashCode(e);
		}

		return hashCode;
	}

	/**
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the contents of the given {@code array}.
	 * @see java.util.Arrays#toString(double[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString(double[] array) {
		if (array == null)
			return "null";
		if (array.length == 0)
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = 0;
		while (true) {
			double e = array[i];

			builder.append(e);

			i++;
			if (i >= array.length)
				return builder.append("]")
						.toString();

			builder.append(", ");
		}
	}

	@Override
	public int all(double[] elements) {
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int i = 0; i < elements.length; i++) {
			long element = Double.doubleToLongBits(elements[i]);

			for (int j = this.beginIndex; j < this.endIndex; j++) {
				long e = Double.doubleToLongBits(this.array[j]);

				if (element == e)
					continue for0;
			}

			return i;
		}

		return -1;
	}

	@Override
	public int all(Double... elements) {
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int i = 0; i < elements.length; i++) {
			Double element = elements[i];

			for (int j = this.beginIndex; j < this.endIndex; j++) {
				double e = this.array[j];

				if (element != null && element.equals(e))
					continue for0;
			}

			return i;
		}

		return -1;
	}

	@Override
	public int any(double[] elements) {
		Objects.requireNonNull(elements, "elements");

		for (int i = 0; i < elements.length; i++) {
			long element = Double.doubleToLongBits(elements[i]);

			for (int j = this.beginIndex; j < this.endIndex; j++) {
				long e = Double.doubleToLongBits(this.array[j]);

				if (element == e)
					return i;
			}
		}

		return -1;
	}

	@Override
	public int any(Double... elements) {
		Objects.requireNonNull(elements, "elements");

		for (int i = 0; i < elements.length; i++) {
			Double element = elements[i];

			for (int j = this.beginIndex; j < this.endIndex; j++) {
				double e = this.array[j];

				if (element != null && element.equals(e))
					return i;
			}
		}

		return -1;
	}

	@Override
	public double[] array(int length) {
		if (length < 0)
			throw new NegativeArraySizeException("length(" + length + ") < 0");
		double[] array = new double[length];

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
	public <T extends Double> T[] array(int length, Class<? super T[]> klass) {
		Objects.requireNonNull(klass, "klass");
		if (length < 0)
			throw new NegativeArraySizeException("length(" + length + ") < 0");
		if (Object[].class.isAssignableFrom(klass))
			throw new IllegalArgumentException("klass");

		T[] array = (T[]) java.lang.reflect.Array.newInstance(klass.getComponentType(), length);

		this.sub(0, Math.min(this.length(), length))
				.hardcopy(array, 0);

		return array;
	}

	@Override
	public <T> T[] array(T[] array) {
		Objects.requireNonNull(array, "array");
		int length = this.length();
		T[] product = array;

		if (array.length < length)
			product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
		else
			product[length] = null;

		this.sub(0, Math.min(this.length(), length))
				.hardcopy(array, 0);

		return product;
	}

	@Override
	public void arraycopy(double[] array, int pos) {
		Objects.requireNonNull(array, "array");

		int length = this.length();

		if (pos < 0)
			throw new IndexOutOfBoundsException("pos(" + pos + ") < 0");
		if (pos + length < array.length)
			throw new IndexOutOfBoundsException(
					"pos(" + pos + ") + length(" + length + ") > array.length(" + array.length +
					")");

		System.arraycopy(
				this.array,
				this.beginIndex,
				array,
				pos,
				length
		);
	}

	@Override
	public int binarySearch(Double element) {
		int low = this.beginIndex;
		int high = this.endIndex - 1;

		while (low <= high) {
			int mid = low + high >>> 1;
			double midVal = this.array[mid];

			if (midVal < element)
				low = mid + 1;
			else if (midVal > element)
				high = mid - 1;
			else
				return this.lowerIndex(mid); // key found
		}
		return this.lowerIndex(-(low + 1));  // key not found.
	}

	@Override
	public boolean contains(Object element) {
		for (int i = this.beginIndex; i < this.endIndex; i++) {
			double e = this.array[i];

			if (element != null && element.equals(e))
				return true;
		}

		return false;
	}

	@Override
	public Entry entry(int index) {
		return new Entry(index);
	}

	@Override
	public EntrySet entrySet() {
		return new EntrySet();
	}

	@Override
	public boolean equals(Object object) {
		if (object == this)
			//same identity
			return true;
		if (object instanceof DoubleArray) {
			//same class
			DoubleArray array = (DoubleArray) object;

			if (array.length() == this.length()) {
				//same length

				for (int i = array.beginIndex, j = this.beginIndex; i < array.endIndex; i++, j++) {
					//for each element
					Object element = array.array[i];
					double e = this.array[j];

					if (element != null && element.equals(e))
						continue;

					return false;
				}

				//all elements are equal
				return true;
			}
		}

		//not equal
		return false;
	}

	@Override
	public void fill(Double element) {
		for (int i = this.beginIndex; i < this.endIndex; i++)
			this.array[i] = element;
	}

	@Override
	public void forEach(Consumer<? super Double> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		for (int i = this.beginIndex; i < this.endIndex; i++) {
			double e = this.array[i];

			consumer.accept(e);
		}
	}

	@Override
	public Double get(int index) {
		this.requireIndex(index);
		int i = this.upperIndex(index);
		return this.array[i];
	}

	@Override
	public void hardcopy(Object[] array, int pos) {
		Objects.requireNonNull(array, "array");

		int length = this.length();

		if (pos < 0)
			throw new IndexOutOfBoundsException("pos(" + pos + ") < 0");
		if (pos + length > array.length)
			throw new IndexOutOfBoundsException(
					"pos(" + pos + ") + length(" + length + ") > array.length(" + array.length +
					")");

		try {
			for (int i = this.beginIndex, j = pos; i < this.endIndex; i++, j++)
				array[j] = this.array[i];
		} catch (IllegalArgumentException e) {
			throw new ArrayStoreException(e.getMessage());
		}
	}

	@Override
	public int hashCode() {
		int hashCode = 1;

		for (int i = this.beginIndex; i < this.endIndex; i++) {
			double e = this.array[i];

			hashCode = 31 * hashCode + Double.hashCode(e);
		}

		return hashCode;
	}

	@Override
	public Iterator iterator() {
		return new Iterator();
	}

	@Override
	public KeySet keySet() {
		return new KeySet();
	}

	@Override
	public List list() {
		return new List();
	}

	@Override
	public Map map() {
		return new Map();
	}

	@Override
	public void parallelPrefix(BinaryOperator<Double> operator) {
		//manual
		java.util.Arrays.parallelPrefix(
				this.array,
				this.beginIndex,
				this.endIndex,
				operator::apply
		);
	}

	@Override
	public void parallelSetAll(IntFunction<? extends Double> function) {
		Objects.requireNonNull(function, "function");
		IntStream.range(this.beginIndex, this.endIndex)
				.parallel()
				.forEach(i -> this.array[i] = function.apply(this.lowerIndex(i)));
	}

	@Override
	public void parallelSort() {
		//manual
		java.util.Arrays.parallelSort(
				this.array,
				this.beginIndex,
				this.endIndex
		);
	}

	@Override
	public Double replace(int index, Double element) {
		this.requireIndex(index);
		int i = this.upperIndex(index);
		double old = this.array[i];
		this.array[i] = element;
		return old;
	}

	@Override
	public Set set() {
		return new Set();
	}

	@Override
	public void set(int index, Double element) {
		this.requireIndex(index);
		int i = this.upperIndex(index);
		this.array[i] = element;
	}

	@Override
	public void setAll(IntFunction<? extends Double> function) {
		Objects.requireNonNull(function, "function");
		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			this.array[i] = function.apply(j);
	}

	@Override
	public void sort() {
		//manual
		java.util.Arrays.sort(
				this.array,
				this.beginIndex,
				this.endIndex
		);
	}

	@Override
	public Spliterator spliterator() {
		return new Spliterator();
	}

	@Override
	public DoubleArray sub(int beginIndex, int endIndex) {
		this.requireRange(beginIndex, endIndex);
		return new DoubleArray(
				this.array,
				this.upperIndex(beginIndex),
				this.upperIndex(endIndex)
		);
	}

	@Override
	public String toString() {
		if (this.endIndex <= this.endIndex)
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = this.beginIndex;
		while (true) {
			double e = this.array[i];

			builder.append(e);

			i++;
			if (i >= this.endIndex)
				return builder.append("]")
						.toString();

			builder.append(", ");
		}
	}

	@Override
	public Values values() {
		return new Values();
	}

	/**
	 * An entry backed by a range from {@code index} to {@code index + 1} in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class Entry extends Array<double[], Double>.Entry<Double, Double> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 5973497615323125824L;

		/**
		 * Construct a new entry backed by a range from {@code index} to {@code index + 1} in the
		 * enclosing array.
		 *
		 * @param index the index to where the key (followed by the value) will be in the
		 *              constructed entry.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index + 1 >= length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public Entry(int index) {
			super(index);
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof java.util.Map.Entry) {
				java.util.Map.Entry entry = (java.util.Map.Entry) object;
				Object key = entry.getKey();
				double k = DoubleArray.this.array[this.index];

				if (key != null && key.equals(k)) {
					Object value = entry.getValue();
					double v = DoubleArray.this.array[this.index + 1];

					return value != null && value.equals(v);
				}
			}

			return false;
		}

		@Override
		public Double getKey() {
			return DoubleArray.this.array[this.index];
		}

		@Override
		public Double getValue() {
			return DoubleArray.this.array[this.index + 1];
		}

		@Override
		public int hashCode() {
			double k = DoubleArray.this.array[this.index];
			double v = DoubleArray.this.array[this.index + 1];
			return Double.hashCode(k) ^
				   Double.hashCode(v);
		}

		@Override
		public Double setValue(Double value) {
			double v = DoubleArray.this.array[this.index + 1];
			DoubleArray.this.array[this.index + 1] = value;
			return v;
		}

		@Override
		public String toString() {
			double k = DoubleArray.this.array[this.index];
			double v = DoubleArray.this.array[this.index + 1];
			return k + "=" + v;
		}
	}

	/**
	 * An iterator iterating the entries in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class EntryIterator extends Array<double[], Double>.EntryIterator<Double, Double> {
		/**
		 * Construct a new iterator iterating the entries in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public EntryIterator() {
		}

		/**
		 * Construct a new iterator iterating the entries in the enclosing array, starting from the
		 * given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public EntryIterator(int index) {
			super(index);
		}
	}

	/**
	 * A set backed by the entries in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class EntrySet extends Array<double[], Double>.EntrySet<Double, Double> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -4823635378224028987L;

		/**
		 * Construct a new set backed by the entries in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public EntrySet() {
		}

		@Override
		public boolean contains(Object object) {
			if (object instanceof java.util.Map.Entry) {
				java.util.Map.Entry entry = (java.util.Map.Entry) object;
				Object key = entry.getKey();

				for (int i = DoubleArray.this.beginIndex;
					 i < DoubleArray.this.endIndex; i += 2) {
					double k = DoubleArray.this.array[i];

					if (key != null && key.equals(k)) {
						Object value = entry.getValue();
						double v = DoubleArray.this.array[i + 1];

						if (value != null && value.equals(v))
							return true;

						break;
					}
				}
			}

			return false;
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof java.util.Set) {
				java.util.Set set = (java.util.Set) object;

				if (set.size() == this.size()) {
					for0:
					for (Object object1 : set) {
						if (object1 instanceof java.util.Map.Entry) {
							java.util.Map.Entry entry = (java.util.Map.Entry) object1;
							Object key = entry.getKey();

							for (int i = DoubleArray.this.beginIndex;
								 i < DoubleArray.this.endIndex; i += 2) {
								double k = DoubleArray.this.array[i];

								if (key != null && key.equals(k)) {
									Object value = entry.getValue();
									double v = DoubleArray.this.array[i + 1];

									if (value != null && value.equals(v))
										continue for0;

									break;
								}
							}
						}

						return false;
					}

					return true;
				}
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];
				double v = DoubleArray.this.array[i + 1];
				hashCode += Double.hashCode(k) ^
							Double.hashCode(v);
			}

			return hashCode;
		}

		@Override
		public EntryIterator iterator() {
			return new EntryIterator();
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];
				double v = DoubleArray.this.array[i + 1];

				for (Object object : collection)
					if (object instanceof java.util.Map.Entry) {
						java.util.Map.Entry entry = (java.util.Map.Entry) object;
						Object key = entry.getKey();

						if (key != null && key.equals(k)) {
							Object value = entry.getValue();

							if (value != null && value.equals(v))
								//retained
								continue for0;
						}
					}

				//can not remove
				throw new UnsupportedOperationException("remove");
			}

			//all retained
			return false;
		}

		@Override
		public EntrySpliterator spliterator() {
			return new EntrySpliterator();
		}

		@Override
		public String toString() {
			if (DoubleArray.this.isEmpty())
				return "[]";

			StringBuilder builder = new StringBuilder("[");

			int i = DoubleArray.this.beginIndex;
			while (true) {
				double k = DoubleArray.this.array[i];
				double v = DoubleArray.this.array[i + 1];

				builder.append(k)
						.append("=")
						.append(v);

				i += 2;
				if (i >= DoubleArray.this.endIndex)
					return builder.append("]")
							.toString();

				builder.append(", ");
			}
		}
	}

	/**
	 * A spliterator iterating the entries in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public class EntrySpliterator extends Array<double[], Double>.EntrySpliterator<Double, Double> {
		/**
		 * Construct a new spliterator iterating the entries in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public EntrySpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the entries in the enclosing array, starting from
		 * the given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public EntrySpliterator(int index) {
			super(index);
		}
	}

	/**
	 * An iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class Iterator extends Array<double[], Double>.Iterator implements PrimitiveIterator.OfDouble {
		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		public Iterator() {
		}

		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public Iterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super Double> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = DoubleArray.this.endIndex;

			for (int i = index; i < DoubleArray.this.endIndex; i++) {
				double e = DoubleArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public void forEachRemaining(DoubleConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = DoubleArray.this.endIndex;

			for (int i = index; i < DoubleArray.this.endIndex; i++) {
				double e = DoubleArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public Double next() {
			int index = this.index;

			if (index < DoubleArray.this.endIndex) {
				this.index++;

				return DoubleArray.this.array[index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public double nextDouble() {
			int index = this.index;

			if (index < DoubleArray.this.endIndex) {
				this.index++;

				return DoubleArray.this.array[index];
			}

			throw new NoSuchElementException();
		}
	}

	/**
	 * An iterator iterating the keys in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class KeyIterator extends Array<double[], Double>.KeyIterator<Double> implements PrimitiveIterator.OfDouble {
		/**
		 * Construct a new iterator iterating the keys in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public KeyIterator() {
		}

		/**
		 * Construct a new iterator iterating the keys in the enclosing array, starting from the
		 * given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public KeyIterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super Double> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = DoubleArray.this.endIndex;

			for (int i = index; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				consumer.accept(k);
			}
		}

		@Override
		public void forEachRemaining(DoubleConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = DoubleArray.this.endIndex;

			for (int i = index; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				consumer.accept(k);
			}
		}

		@Override
		public Double next() {
			int index = this.index;

			if (index < DoubleArray.this.endIndex) {
				this.index += 2;

				return DoubleArray.this.array[index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public double nextDouble() {
			int index = this.index;

			if (index < DoubleArray.this.endIndex) {
				this.index += 2;

				return DoubleArray.this.array[index];
			}

			throw new NoSuchElementException();
		}
	}

	/**
	 * A set backed by the keys in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class KeySet extends Array<double[], Double>.KeySet<Double> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 7793360078444812816L;

		/**
		 * Construct a new set backed by the keys in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public KeySet() {
		}

		@Override
		public boolean contains(Object object) {
			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				if (object != null && object.equals(k))
					return true;
			}

			return false;
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof java.util.Set) {
				java.util.Set set = (java.util.Set) object;

				if (set.size() == this.size()) {
					for0:
					for (Object key : set) {
						for (int i = DoubleArray.this.beginIndex;
							 i < DoubleArray.this.endIndex; i += 2) {
							double k = DoubleArray.this.array[i];

							if (key != null && key.equals(k))
								continue for0;
						}

						return false;
					}

					return true;
				}
			}

			return false;
		}

		@Override
		public void forEach(Consumer<? super Double> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				consumer.accept(k);
			}
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				hashCode += Double.hashCode(k);
			}

			return hashCode;
		}

		@Override
		public KeyIterator iterator() {
			return new KeyIterator();
		}

		@Override
		public boolean removeIf(Predicate<? super Double> predicate) {
			Objects.requireNonNull(predicate, "predicate");

			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				if (predicate.test(k))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//nothing to remove
			return false;
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				for (Object key : collection)
					if (key != null && key.equals(k))
						//retained
						continue for0;

				//can not remove
				throw new UnsupportedOperationException("remove");
			}

			//all retained
			return false;
		}

		@Override
		public KeySpliterator spliterator() {
			return new KeySpliterator();
		}

		@Override
		public Object[] toArray() {
			int length = this.size();
			Object[] product = new Object[length];

			for (int i = DoubleArray.this.beginIndex, j = 0;
				 i < DoubleArray.this.endIndex; i += 2, j++) {
				double k = DoubleArray.this.array[i];

				product[j] = k;
			}

			return product;
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			int length = this.size();
			T[] product = array;

			if (array.length < length)
				product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
			else
				product[length] = null;

			for (int i = DoubleArray.this.beginIndex, j = 0;
				 i < DoubleArray.this.endIndex; i += 2, j++) {
				double k = DoubleArray.this.array[i];

				product[j] = (T) (Double) k;
			}

			return product;
		}

		@Override
		public String toString() {
			if (DoubleArray.this.isEmpty())
				return "[]";

			StringBuilder builder = new StringBuilder("[");

			int i = DoubleArray.this.beginIndex;
			while (true) {
				double k = DoubleArray.this.array[i];

				builder.append(k);

				i += 2;
				if (i >= DoubleArray.this.endIndex)
					return builder.append("]")
							.toString();

				builder.append(", ");
			}
		}
	}

	/**
	 * A spliterator iterating the keys in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public class KeySpliterator extends Array<double[], Double>.KeySpliterator<Double> implements java.util.Spliterator.OfDouble {
		/**
		 * Construct a new spliterator iterating the keys in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public KeySpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the keys in the enclosing array, starting from the
		 * given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public KeySpliterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super Double> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = DoubleArray.this.endIndex;

			for (int i = index; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				consumer.accept(k);
			}
		}

		@Override
		public void forEachRemaining(DoubleConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = DoubleArray.this.endIndex;

			for (int i = index; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				consumer.accept(k);
			}
		}

		@Override
		public boolean tryAdvance(DoubleConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < DoubleArray.this.endIndex) {
				this.index += 2;

				double k = DoubleArray.this.array[index];
				consumer.accept(k);
				return true;
			}

			return false;
		}

		@Override
		public boolean tryAdvance(Consumer<? super Double> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < DoubleArray.this.endIndex) {
				this.index += 2;

				double k = DoubleArray.this.array[index];
				consumer.accept(k);
				return true;
			}

			return false;
		}

		@Override
		public KeySpliterator trySplit() {
			return (KeySpliterator) super.trySplit();
		}
	}

	/**
	 * A list backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class List extends Array<double[], Double>.List {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 848985287158674978L;

		/**
		 * Construct a new list backed by the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public List() {
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				//same identity
				return true;
			if (object instanceof java.util.List) {
				//same class
				java.util.List list = (java.util.List) object;

				if (list.size() == this.size()) {
					//same length

					int i = DoubleArray.this.beginIndex;
					for (Object element : list) {
						//for each element

						if (i < DoubleArray.this.endIndex) {
							//still same length
							double e = DoubleArray.this.array[i++];

							if (element != null && element.equals(e))
								continue;
						}

						return false;
					}

					//all elements are equal
					return true;
				}
			}

			//not equal
			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = 1;

			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i++) {
				double e = DoubleArray.this.array[i];

				hashCode = 31 * hashCode + Double.hashCode(e);
			}

			return hashCode;
		}

		@Override
		public int indexOf(Object object) {
			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i++) {
				double e = DoubleArray.this.array[i];

				if (object != null && object.equals(e))
					return i - DoubleArray.this.beginIndex;
			}

			return -1;
		}

		@Override
		public int lastIndexOf(Object object) {
			for (int i = DoubleArray.this.endIndex - 1;
				 i >= DoubleArray.this.beginIndex; i--) {
				double e = DoubleArray.this.array[i];

				if (object != null && object.equals(e))
					return i - DoubleArray.this.beginIndex;
			}

			return -1;
		}

		@Override
		public ListIterator listIterator(int index) {
			return new ListIterator();
		}

		@Override
		public boolean removeIf(Predicate<? super Double> predicate) {
			Objects.requireNonNull(predicate, "predicate");
			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i++) {
				double e = DoubleArray.this.array[i];

				if (predicate.test(e))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//nothing to remove
			return false;
		}

		@Override
		public void replaceAll(UnaryOperator<Double> operator) {
			Objects.requireNonNull(operator, "operator");
			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i++) {
				double e = DoubleArray.this.array[i];

				DoubleArray.this.array[i] = operator.apply(e);
			}
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i++) {
				double e = DoubleArray.this.array[i];

				for (Object element : collection)
					if (element != null && element.equals(e))
						//retained
						continue for0;

				//can not remove
				throw new UnsupportedOperationException("remove");
			}

			//all retained
			return false;
		}

		@Override
		public void sort(Comparator<? super Double> comparator) {
			//manual
			if (comparator == null)
				DoubleArray.this.sort();

			Double[] temp = new Double[this.size()];

			for (int i = DoubleArray.this.beginIndex, j = 0;
				 i < DoubleArray.this.endIndex; i++, j++)
				temp[j] = DoubleArray.this.array[i];

			java.util.Arrays.sort(temp, comparator);

			for (int i = DoubleArray.this.beginIndex, j = 0;
				 i < DoubleArray.this.endIndex; i++, j++)
				DoubleArray.this.array[i] = temp[j];
		}
	}

	/**
	 * A list iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class ListIterator extends Array<double[], Double>.ListIterator {
		/**
		 * Construct a new list iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		public ListIterator() {
		}

		/**
		 * Construct a new list iterator iterating the elements in the enclosing array, starting
		 * from the given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ListIterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super Double> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = DoubleArray.this.endIndex;
			this.last = DoubleArray.this.endIndex - 1;

			for (int i = index; i < DoubleArray.this.endIndex; i++) {
				double e = DoubleArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public Double next() {
			int index = this.index;

			if (index < DoubleArray.this.endIndex) {
				this.index++;
				this.last = index;

				return DoubleArray.this.array[index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public Double previous() {
			int index = this.index - 1;

			if (index >= DoubleArray.this.beginIndex) {
				this.index--;
				this.last = index;

				return DoubleArray.this.array[index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public void set(Double element) {
			int index = this.last;

			if (index == -1)
				throw new IllegalStateException();

			DoubleArray.this.array[index] = element;
		}
	}

	/**
	 * A map backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class Map extends Array<double[], Double>.Map<Double, Double> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -2840280796050057228L;

		/**
		 * Construct a new map backed by the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public Map() {
		}

		@Override
		public Double compute(Double key, BiFunction<? super Double, ? super Double, ? extends Double> function) {
			Objects.requireNonNull(function, "function");

			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				if (key != null && key.equals(k)) {
					double v = DoubleArray.this.array[i + 1];
					Double value = function.apply(k, v);

					if (value == null)
						//old:notnull new:null
						throw new UnsupportedOperationException("remove");

					//old:found
					DoubleArray.this.array[i + 1] = value;
					return value;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public Double computeIfAbsent(Double key, Function<? super Double, ? extends Double> function) {
			Objects.requireNonNull(function, "function");

			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				if (key != null && key.equals(k))
					//old:notnull
					return DoubleArray.this.array[i + 1];
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public Double computeIfPresent(Double key, BiFunction<? super Double, ? super Double, ? extends Double> function) {
			Objects.requireNonNull(function, "function");

			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				if (key != null && key.equals(k)) {
					double v = DoubleArray.this.array[i + 1];
					Double value = function.apply(k, v);

					if (value == null)
						//old:notnull new:null
						throw new UnsupportedOperationException("remove");

					//old:notnull new:notnull
					DoubleArray.this.array[i + 1] = value;
					return value;
				}
			}

			//old:notfound
			return null;
		}

		@Override
		public boolean containsKey(Object key) {
			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				if (key != null && key.equals(k))
					return true;
			}

			return false;
		}

		@Override
		public boolean containsValue(Object value) {
			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double v = DoubleArray.this.array[i];

				if (value != null && value.equals(v))
					return true;
			}

			return false;
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof java.util.Map) {
				java.util.Map<?, ?> map = (java.util.Map) object;

				if (map.size() == this.size()) {
					for0:
					for (java.util.Map.Entry entry : map.entrySet()) {
						Object key = entry.getKey();

						for (int i = DoubleArray.this.beginIndex;
							 i < DoubleArray.this.endIndex; i += 2) {
							double k = DoubleArray.this.array[i];

							if (key != null && key.equals(k)) {
								Object value = entry.getValue();
								double v = DoubleArray.this.array[i + 1];

								if (value != null && value.equals(v))
									continue for0;

								break;
							}
						}

						return false;
					}

					return true;
				}
			}

			return false;
		}

		@Override
		public void forEach(BiConsumer<? super Double, ? super Double> consumer) {
			Objects.requireNonNull(consumer, "consumer");

			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];
				double v = DoubleArray.this.array[i + 1];
				consumer.accept(k, v);
			}
		}

		@Override
		public Double get(Object key) {
			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				if (key != null && key.equals(k))
					return DoubleArray.this.array[i + 1];
			}

			return null;
		}

		@Override
		public Double getOrDefault(Object key, Double defaultValue) {
			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				if (key != null && key.equals(k))
					return DoubleArray.this.array[i + 1];
			}

			return defaultValue;
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];
				double v = DoubleArray.this.array[i + 1];
				hashCode += Double.hashCode(k) ^
							Double.hashCode(v);
			}

			return hashCode;
		}

		@Override
		public Double merge(Double key, Double value, BiFunction<? super Double, ? super Double, ? extends Double> function) {
			Objects.requireNonNull(function, "function");

			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				if (key != null && key.equals(k)) {
					double v = DoubleArray.this.array[i + 1];
					Double newValue = function.apply(v, value);

					if (newValue == null)
						//old:found new:null
						throw new UnsupportedOperationException("remove");

					//old:found new:notnull
					DoubleArray.this.array[i + 1] = newValue;
					return newValue;
				}
			}

			if (value == null)
				//old:notfound new:null
				return null;

			//old:notfound new:notnull
			throw new UnsupportedOperationException("put");
		}

		@Override
		public Double put(Double key, Double value) {
			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				if (key != null && key.equals(k)) {
					//old:found
					double v = DoubleArray.this.array[i + 1];
					DoubleArray.this.array[i + 1] = value;
					return v;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public void putAll(java.util.Map<? extends Double, ? extends Double> map) {
			Objects.requireNonNull(map, "map");

			for0:
			for (java.util.Map.Entry<? extends Double, ? extends Double> entry : map.entrySet()) {
				Double key = entry.getKey();

				for (int i = DoubleArray.this.beginIndex;
					 i < DoubleArray.this.endIndex; i += 2) {
					double k = DoubleArray.this.array[i];

					if (key != null && key.equals(k)) {
						Double value = entry.getValue();
						DoubleArray.this.array[i + 1] = value;
						continue for0;
					}
				}

				//old:notfound
				throw new UnsupportedOperationException("put");
			}
		}

		@Override
		public Double putIfAbsent(Double key, Double value) {
			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				if (key != null && key.equals(k))
					//old:found
					return DoubleArray.this.array[i + 1];
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public boolean remove(Object key, Object value) {
			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				if (key != null && key.equals(k)) {
					double v = DoubleArray.this.array[i + 1];

					if (value != null && value.equals(v))
						//old:match
						throw new UnsupportedOperationException("remove");

					break;
				}
			}

			//old:nomatch
			return false;
		}

		@Override
		public boolean replace(Double key, Double oldValue, Double newValue) {
			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				if (key != null && key.equals(k)) {
					double v = DoubleArray.this.array[i + 1];

					if (oldValue != null && oldValue.equals(v)) {
						//old:match
						DoubleArray.this.array[i + 1] = newValue;
						return true;
					}

					break;
				}
			}

			//old:nomatch
			return false;
		}

		@Override
		public Double replace(Double key, Double value) {
			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];

				if (key != null && key.equals(k)) {
					//old:match
					double v = DoubleArray.this.array[i + 1];
					DoubleArray.this.array[i + 1] = value;
					return v;
				}
			}

			//old:nomatch
			return null;
		}

		@Override
		public void replaceAll(BiFunction<? super Double, ? super Double, ? extends Double> function) {
			Objects.requireNonNull(function, "function");

			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i += 2) {
				double k = DoubleArray.this.array[i];
				double v = DoubleArray.this.array[i + 1];

				DoubleArray.this.array[i + 1] = function.apply(k, v);
			}
		}

		@Override
		public String toString() {
			if (DoubleArray.this.endIndex <= DoubleArray.this.beginIndex)
				return "{}";

			StringBuilder builder = new StringBuilder("{");

			int i = DoubleArray.this.beginIndex;
			while (true) {
				double k = DoubleArray.this.array[i];
				double v = DoubleArray.this.array[i + 1];

				builder.append(k)
						.append("=")
						.append(v);

				i += 2;
				if (i >= DoubleArray.this.endIndex)
					return builder.append("}")
							.toString();

				builder.append(", ");
			}
		}
	}

	/**
	 * A set backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class Set extends Array<double[], Double>.Set {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 4146929083993819823L;

		/**
		 * Construct a new set backed by the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public Set() {
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				//same identity
				return true;
			if (object instanceof java.util.Set) {
				//same class
				java.util.Set set = (java.util.Set) object;

				if (set.size() == DoubleArray.this.endIndex - DoubleArray.this.beginIndex) {
					//same length

					for0:
					for (Object element : set) {
						//for each element

						for (int i = DoubleArray.this.beginIndex;
							 i < DoubleArray.this.endIndex; i++) {
							double e = DoubleArray.this.array[i];

							if (element != null && element.equals(e))
								continue for0;
						}

						return false;
					}

					//all elements equal
					return true;
				}
			}

			//not equal
			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i++) {
				double e = DoubleArray.this.array[i];

				hashCode += Double.hashCode(e);
			}

			return hashCode;
		}

		@Override
		public boolean removeIf(Predicate<? super Double> predicate) {
			Objects.requireNonNull(predicate, "predicate");

			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i++) {
				double e = DoubleArray.this.array[i];

				if (predicate.test(e))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//nothing to remove
			return false;
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (int i = DoubleArray.this.beginIndex; i < DoubleArray.this.endIndex; i++) {
				double e = DoubleArray.this.array[i];

				for (Object element : collection)
					if (element != null && element.equals(e))
						//retained
						continue for0;

				//can not remove
				throw new UnsupportedOperationException("remove");
			}

			//all retained
			return false;
		}
	}

	/**
	 * A spliterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public class Spliterator extends Array<double[], Double>.Spliterator implements java.util.Spliterator.OfDouble {
		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from
		 * the given {@code index}.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		public Spliterator() {
		}

		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from
		 * the given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public Spliterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super Double> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = DoubleArray.this.endIndex;

			for (int i = index; i < DoubleArray.this.endIndex; i++) {
				double e = DoubleArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public void forEachRemaining(DoubleConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = DoubleArray.this.endIndex;

			for (int i = index; i < DoubleArray.this.endIndex; i++) {
				double e = DoubleArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public boolean tryAdvance(DoubleConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < DoubleArray.this.endIndex) {
				this.index += 2;
				double e = DoubleArray.this.array[index];

				consumer.accept(e);
				return true;
			}

			return false;
		}

		@Override
		public boolean tryAdvance(Consumer<? super Double> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < DoubleArray.this.endIndex) {
				this.index += 2;
				double e = DoubleArray.this.array[index];

				consumer.accept(e);
				return true;
			}

			return false;
		}

		@Override
		public Spliterator trySplit() {
			return (Spliterator) super.trySplit();
		}
	}

	/**
	 * An iterator iterating the values in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class ValueIterator extends Array<double[], Double>.ValueIterator<Double> implements PrimitiveIterator.OfDouble {
		/**
		 * Construct a new iterator iterating the values in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ValueIterator() {
		}

		/**
		 * Construct a new iterator iterating the values in the enclosing array, starting from the
		 * given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ValueIterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super Double> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = DoubleArray.this.endIndex;

			for (int i = index + 1; i < DoubleArray.this.endIndex; i += 2) {
				double v = DoubleArray.this.array[i];

				consumer.accept(v);
			}
		}

		@Override
		public void forEachRemaining(DoubleConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = DoubleArray.this.endIndex;

			for (int i = index + 1; i < DoubleArray.this.endIndex; i += 2) {
				double v = DoubleArray.this.array[i];

				consumer.accept(v);
			}
		}

		@Override
		public Double next() {
			int index = this.index;

			if (index < DoubleArray.this.endIndex) {
				this.index += 2;

				return DoubleArray.this.array[index + 1];
			}

			throw new NoSuchElementException();
		}

		@Override
		public double nextDouble() {
			int index = this.index;

			if (index < DoubleArray.this.endIndex) {
				this.index += 2;

				return DoubleArray.this.array[index + 1];
			}

			throw new NoSuchElementException();
		}
	}

	/**
	 * A spliterator iterating the values in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public class ValueSpliterator extends Array<double[], Double>.ValueSpliterator<Double> implements java.util.Spliterator.OfDouble {
		/**
		 * Construct a new spliterator iterating the values in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}
		 * @since 0.1.5 ~2020.08.06
		 */
		public ValueSpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the values in the enclosing array, starting from
		 * the given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}
		 * @since 0.1.5 ~2020.08.06
		 */
		public ValueSpliterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super Double> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = DoubleArray.this.endIndex;

			for (int i = index + 1; i < DoubleArray.this.endIndex; i += 2) {
				double v = DoubleArray.this.array[i];

				consumer.accept(v);
			}
		}

		@Override
		public void forEachRemaining(DoubleConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = DoubleArray.this.endIndex;

			for (int i = index + 1; i < DoubleArray.this.endIndex; i += 2) {
				double v = DoubleArray.this.array[i];

				consumer.accept(v);
			}
		}

		@Override
		public boolean tryAdvance(DoubleConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < DoubleArray.this.endIndex) {
				this.index += 2;

				double v = DoubleArray.this.array[index + 1];
				consumer.accept(v);
				return true;
			}

			return false;
		}

		@Override
		public boolean tryAdvance(Consumer<? super Double> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < DoubleArray.this.endIndex) {
				this.index += 2;

				double v = DoubleArray.this.array[index + 1];
				consumer.accept(v);
				return true;
			}

			return false;
		}

		@Override
		public ValueSpliterator trySplit() {
			return (ValueSpliterator) super.trySplit();
		}
	}

	/**
	 * A collection backed by the values in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class Values extends Array<double[], Double>.Values<Double> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -7937502933699082438L;

		/**
		 * Construct a new collection backed by the values in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public Values() {
		}

		@Override
		public boolean contains(Object object) {
			for (int i = DoubleArray.this.beginIndex + 1;
				 i < DoubleArray.this.endIndex; i += 2) {
				double v = DoubleArray.this.array[i];

				if (object != null && object.equals(v))
					return true;
			}

			return false;
		}

		@Override
		public boolean equals(Object object) {
			return object == this;
		}

		@Override
		public void forEach(Consumer<? super Double> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			for (int i = DoubleArray.this.beginIndex + 1;
				 i < DoubleArray.this.endIndex; i += 2) {
				double v = DoubleArray.this.array[i];

				consumer.accept(v);
			}
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = DoubleArray.this.beginIndex + 1;
				 i < DoubleArray.this.endIndex; i += 2) {
				double v = DoubleArray.this.array[i];

				hashCode += Double.hashCode(v);
			}

			return hashCode;
		}

		@Override
		public ValueIterator iterator() {
			return new ValueIterator();
		}

		@Override
		public boolean removeIf(Predicate<? super Double> predicate) {
			Objects.requireNonNull(predicate, "predicate");

			for (int i = DoubleArray.this.beginIndex + 1;
				 i < DoubleArray.this.endIndex; i += 2) {
				double v = DoubleArray.this.array[i];

				if (predicate.test(v))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//nothing to remove
			return false;
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (int i = DoubleArray.this.beginIndex + 1;
				 i < DoubleArray.this.endIndex; i += 2) {
				double v = DoubleArray.this.array[i];

				for (Object value : collection)
					if (value != null && value.equals(v))
						//retained
						continue for0;

				//can not remove
				throw new UnsupportedOperationException("remove");
			}

			//all retained
			return false;
		}

		@Override
		public ValueSpliterator spliterator() {
			return new ValueSpliterator();
		}

		@Override
		public Object[] toArray() {
			int length = DoubleArray.this.endIndex - DoubleArray.this.beginIndex >>> 1;
			Object[] product = new Object[length];

			for (int i = DoubleArray.this.beginIndex + 1, j = 0;
				 i < DoubleArray.this.endIndex; i += 2, j++) {
				double v = DoubleArray.this.array[i];

				product[j] = v;
			}

			return product;
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			int length = DoubleArray.this.endIndex - DoubleArray.this.beginIndex >>> 1;
			T[] product = array;

			if (array.length < length)
				product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
			else
				product[length] = null;

			for (int i = DoubleArray.this.beginIndex + 1, j = 0;
				 i < DoubleArray.this.endIndex; i += 2, j++) {
				double v = DoubleArray.this.array[i];

				product[j] = (T) (Double) v;
			}

			return product;
		}

		@Override
		public String toString() {
			if (DoubleArray.this.endIndex <= DoubleArray.this.beginIndex)
				return "[]";

			StringBuilder builder = new StringBuilder("[");

			int i = DoubleArray.this.beginIndex + 1;
			while (true) {
				double v = DoubleArray.this.array[i];

				builder.append(v);

				i += 2;
				if (i >= DoubleArray.this.endIndex)
					return builder.append("]")
							.toString();

				builder.append(", ");
			}
		}
	}
}
