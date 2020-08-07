package cufy.util.array;

import cufy.util.Objects;

import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A holder for an array of {@link Object}s.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.03
 */
public class CharacterArray extends Array<char[], Character> {
	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = -407181299777988791L;

	/**
	 * Construct a new array backed by the given {@code array}.
	 *
	 * @param array the array to be backing the constructed array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.05
	 */
	public CharacterArray(char... array) {
		super(array);
	}

	/**
	 * Construct a new array backed by the specified range of the given {@code array}. The range starts at the given {@code
	 * beginIndex} and ends before the given {@code endIndex}.
	 *
	 * @param array      the array to be backing the constructed array.
	 * @param beginIndex the first index of the area at the given {@code array} to be backing the constructed array.
	 * @param endIndex   one past the last index of the area at the given {@code array} to be backing the constructed array.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex > array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.05
	 */
	public CharacterArray(char[] array, int beginIndex, int endIndex) {
		super(array, beginIndex, endIndex);
	}

	/**
	 * Determine the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * array}.
	 *
	 * @param array    the array to be checked.
	 * @param elements the elements to be matched to the elements of the given {@code array}.
	 * @return the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int all(char[] array, char... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int i = 0; i < elements.length; i++) {
			char e = elements[i];

			for (int j = 0; j < array.length; j++) {
				char a = array[j];

				if (e == a)
					continue for0;
			}

			return i;
		}

		return -1;
	}

	/**
	 * Determine the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * array}.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int any(char[] array, char... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int i = 0; i < elements.length; i++) {
			char e = elements[i];

			for (int j = 0; j < array.length; j++) {
				char a = array[j];

				if (e == a)
					return i;
			}
		}

		return -1;
	}

	@SuppressWarnings("JavaDoc")
	public static int binarySearch(char[] array, int beginIndex, int endIndex, char element) {
		return Arrays.binarySearch(array, beginIndex, endIndex, element);
	}

	/**
	 * Copy the elements from the given {@code src} to the given {@code dest}. Start reading from the given {@code src} at the
	 * given {@code srcPos}. Start writing to the given {@code dest} at the given {@code destPos}. Copy the specified number of
	 * elements {@code length}.
	 *
	 * @param src     the source array.
	 * @param srcPos  the index to start reading from the source array.
	 * @param dest    the destination array.
	 * @param destPos the index to start writing to the destination array.
	 * @param length  the number of elements to be copied.
	 * @throws NullPointerException      if the given {@code src} or {@code dest} is null.
	 * @throws IndexOutOfBoundsException if {@code srcPos < 0} or {@code destPos < 0} or {@code length < 0} or {@code srcPos +
	 *                                   length > src.length} or {@code destPos + length > dest.length}.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(char[] src, int srcPos, char[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		if (src != dest || destPos <= srcPos || srcPos + length <= destPos)
			for (int i = 0; i < length; i++)
				dest[di++] = src[si++];
		else {
			char[] clone = java.util.Arrays.copyOfRange(src, destPos, srcPos + length);

			for (int i = 0, l = destPos - srcPos; i < l; i++)
				dest[di++] = src[si++];
			for (int i = 0, l = srcPos + length - destPos; i < l; i++)
				dest[di++] = clone[i];
		}
	}

	/**
	 * Copy the elements from the given {@code src} to the given {@code dest}. Start reading from the given {@code src} at the
	 * given {@code srcPos}. Start writing to the given {@code dest} at the given {@code destPos}. Copy the specified number of
	 * elements {@code length}.
	 *
	 * @param src     the source array.
	 * @param srcPos  the index to start reading from the source array.
	 * @param dest    the destination array.
	 * @param destPos the index to start writing to the destination array.
	 * @param length  the number of elements to be copied.
	 * @throws NullPointerException      if the given {@code src} or {@code dest} is null.
	 * @throws IndexOutOfBoundsException if {@code srcPos < 0} or {@code destPos < 0} or {@code length < 0} or {@code srcPos +
	 *                                   length > src.length} or {@code destPos + length > dest.length}.
	 * @throws ArrayStoreException       if an element can not be stored in the given {@code dest}.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(Object[] src, int srcPos, char[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		for (int i = 0; i < length; i++)
			try {
				dest[di++] = (char) src[si++];
			} catch (ClassCastException e) {
				throw new ArrayStoreException(e.getMessage());
			}
	}

	/**
	 * Copy the elements from the given {@code src} to the given {@code dest}. Start reading from the given {@code src} at the
	 * given {@code srcPos}. Start writing to the given {@code dest} at the given {@code destPos}. Copy the specified number of
	 * elements {@code length}.
	 *
	 * @param src     the source array.
	 * @param srcPos  the index to start reading from the source array.
	 * @param dest    the destination array.
	 * @param destPos the index to start writing to the destination array.
	 * @param length  the number of elements to be copied.
	 * @throws NullPointerException      if the given {@code src} or {@code dest} is null.
	 * @throws IndexOutOfBoundsException if {@code srcPos < 0} or {@code destPos < 0} or {@code length < 0} or {@code srcPos +
	 *                                   length > src.length} or {@code destPos + length > dest.length}.
	 * @throws ArrayStoreException       if an element can not be stored in the given {@code dest}.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(char[] src, int srcPos, Object[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		for (int i = 0; i < length; i++)
			dest[di++] = src[si++];
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned from invoking the given {@code
	 * function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#parallelSetAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll(char[] array, IntFunction<Character> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		IntStream.range(0, array.length).parallel().forEach(i -> {
			Object element = function.apply(i);

			try {
				array[i] = (Character) element;
			} catch (ClassCastException | NullPointerException e) {
				throw new ArrayStoreException(e.getMessage());
			}
		});
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking the given {@code function}
	 * with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#setAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll(char[] array, IntFunction<Character> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		for (int i = 0; i < array.length; i++) {
			Object element = function.apply(i);

			try {
				array[i] = (Character) element;
			} catch (ClassCastException | NullPointerException e) {
				throw new ArrayStoreException(e.getMessage());
			}
		}
	}

	@Override
	public CharacterArrayKeySet asKeySet() {
		return new CharacterArrayKeySet();
	}

	@Override
	public CharacterArrayList asList() {
		return new CharacterArrayList();
	}

	@Override
	public CharacterArrayMap asMap() {
		return new CharacterArrayMap();
	}

	@Override
	public CharacterArraySet asSet() {
		return new CharacterArraySet();
	}

	@Override
	public CharacterArrayValues asValues() {
		return new CharacterArrayValues();
	}

	@Override
	public CharacterArrayEntry entryAt(int index) {
		return new CharacterArrayEntry(index);
	}

	@Override
	public CharacterArrayEntryIterator entryIterator() {
		return new CharacterArrayEntryIterator();
	}

	@Override
	public CharacterArrayEntryIterator entryIterator(int index) {
		return new CharacterArrayEntryIterator(index);
	}

	@Override
	public CharacterArrayEntrySet entrySet() {
		return new CharacterArrayEntrySet();
	}

	@Override
	public CharacterArrayEntrySpliterator entrySpliterator() {
		return new CharacterArrayEntrySpliterator();
	}

	@Override
	public CharacterArrayEntrySpliterator entrySpliterator(int index) {
		return new CharacterArrayEntrySpliterator(index);
	}

	@Override
	public boolean equals(Object object) {
		if (object == this)
			//same identity
			return true;
		if (object instanceof CharacterArray) {
			//same class
			CharacterArray array = (CharacterArray) object;

			if (array.endIndex - array.beginIndex == this.endIndex - this.beginIndex) {
				//same length

				for (int i = array.beginIndex, j = this.beginIndex; i < array.endIndex; i++, j++) {
					//for each element
					Object element = array.array[i];
					char e = this.array[j];

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
	public void forEach(Consumer<? super Character> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		for (int i = this.beginIndex; i < this.endIndex; i++) {
			char e = this.array[i];

			consumer.accept(e);
		}
	}

	@Override
	public Character get(int index) {
		this.requireIndex(index);
		int i = this.beginIndex + index;
		return this.array[i];
	}

	@Override
	public int hashCode() {
		int hashCode = 1;

		for (int i = this.beginIndex; i < this.endIndex; i++) {
			char e = this.array[i];

			hashCode = 31 * hashCode + Character.hashCode(e);
		}

		return hashCode;
	}

	@Override
	public CharacterArrayIterator iterator() {
		return new CharacterArrayIterator();
	}

	@Override
	public CharacterArrayIterator iterator(int index) {
		return new CharacterArrayIterator(index);
	}

	@Override
	public CharacterArrayKeyIterator keyIterator() {
		return new CharacterArrayKeyIterator();
	}

	@Override
	public CharacterArrayKeyIterator keyIterator(int index) {
		return new CharacterArrayKeyIterator(index);
	}

	@Override
	public CharacterArrayKeySpliterator keySpliterator() {
		return new CharacterArrayKeySpliterator();
	}

	@Override
	public CharacterArrayKeySpliterator keySpliterator(int index) {
		return new CharacterArrayKeySpliterator(index);
	}

	@Override
	public CharacterArrayListIterator listIterator() {
		return new CharacterArrayListIterator();
	}

	@Override
	public CharacterArrayListIterator listIterator(int index) {
		return new CharacterArrayListIterator(index);
	}

	@Override
	public Stream<Character> parallelStream() {
		return StreamSupport.stream(new CharacterArraySpliterator(), true);
	}

	@Override
	public void set(int index, Character element) {
		this.requireIndex(index);
		int i = this.beginIndex + index;
		this.array[i] = element;
	}

	@Override
	public void sort() {
		Arrays.sort(this.array, this.beginIndex, this.endIndex);
	}

	@Override
	public void sort(Comparator<? super Character> comparator) {
		int length = this.endIndex - this.beginIndex;
		Character[] temp = new Character[length];

		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			temp[j] = this.array[i];

		Arrays.sort(temp, comparator);

		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			this.array[i] = temp[j];
	}

	@Override
	public CharacterArraySpliterator spliterator() {
		return new CharacterArraySpliterator();
	}

	@Override
	public CharacterArraySpliterator spliterator(int index) {
		return new CharacterArraySpliterator(index);
	}

	@Override
	public Stream<Character> stream() {
		return StreamSupport.stream(new CharacterArraySpliterator(), false);
	}

	@Override
	public CharacterArray subArray(int beginIndex, int endIndex) {
		this.requireRange(beginIndex, endIndex);
		return new CharacterArray(
				this.array,
				this.beginIndex + beginIndex,
				this.beginIndex + endIndex
		);
	}

	@Override
	public String toString() {
		if (this.endIndex <= this.endIndex)
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = this.beginIndex;
		while (true) {
			char e = this.array[i];

			builder.append(e);

			i++;
			if (i >= this.endIndex)
				return builder.append("]")
						.toString();

			builder.append(", ");
		}
	}

	@Override
	public CharacterArrayValueIterator valueIterator() {
		return new CharacterArrayValueIterator();
	}

	@Override
	public CharacterArrayValueIterator valueIterator(int index) {
		return new CharacterArrayValueIterator(index);
	}

	@Override
	public CharacterArrayValueSpliterator valueSpliterator() {
		return new CharacterArrayValueSpliterator();
	}

	@Override
	public CharacterArrayValueSpliterator valueSpliterator(int index) {
		return new CharacterArrayValueSpliterator(index);
	}

	/**
	 * An entry backed by a range from {@code index} to {@code index + 1} in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public final class CharacterArrayEntry extends ArrayEntry<Character, Character> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -7098093446276533400L;

		/**
		 * Construct a new entry backed by a range from {@code index} to {@code index + 1} in the enclosing array.
		 *
		 * @param index the index to where the key (followed by the value) will be in the constructed entry.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index + 1 >= length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArrayEntry(int index) {
			super(index);
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof Map.Entry) {
				Map.Entry entry = (Map.Entry) object;
				Object key = entry.getKey();
				char k = CharacterArray.this.array[this.index];

				if (key != null && key.equals(k)) {
					Object value = entry.getValue();
					char v = CharacterArray.this.array[this.index + 1];

					return value != null && value.equals(v);
				}
			}

			return false;
		}

		@Override
		public Character getKey() {
			return CharacterArray.this.array[this.index];
		}

		@Override
		public Character getValue() {
			return CharacterArray.this.array[this.index + 1];
		}

		@Override
		public int hashCode() {
			char k = CharacterArray.this.array[this.index];
			char v = CharacterArray.this.array[this.index + 1];
			return Character.hashCode(k) ^
				   Character.hashCode(v);
		}

		@Override
		public Character setValue(Character value) {
			char v = CharacterArray.this.array[this.index + 1];
			CharacterArray.this.array[this.index + 1] = value;
			return v;
		}

		@Override
		public String toString() {
			char k = CharacterArray.this.array[this.index];
			char v = CharacterArray.this.array[this.index + 1];
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
	public final class CharacterArrayEntryIterator extends ArrayEntryIterator<Character, Character> {
		/**
		 * Construct a new iterator iterating the entries in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArrayEntryIterator() {
		}

		/**
		 * Construct a new iterator iterating the entries in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArrayEntryIterator(int index) {
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
	public final class CharacterArrayEntrySet extends ArrayEntrySet<Character, Character> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -1358655666106432532L;

		/**
		 * Construct a new set backed by the entries in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public CharacterArrayEntrySet() {
		}

		@Override
		public boolean add(Map.Entry<Character, Character> entry) {
			Character key = entry.getKey();

			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				if (key != null && key.equals(k)) {
					Character value = entry.getValue();
					char v = CharacterArray.this.array[i + 1];

					if (value != null && value.equals(v))
						//already exists
						return false;

					break;
				}
			}

			//can not add
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends Map.Entry<Character, Character>> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Map.Entry<Character, Character> entry : collection) {
				Character key = entry.getKey();

				for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
					char k = CharacterArray.this.array[i];

					if (key != null && key.equals(k)) {
						Character value = entry.getValue();
						char v = CharacterArray.this.array[i + 1];

						if (value != null && value.equals(v))
							//already exists
							continue for0;

						break;
					}
				}

				//can not add
				throw new UnsupportedOperationException("add");
			}

			//all already exists
			return false;
		}

		@Override
		public boolean contains(Object object) {
			if (object instanceof Map.Entry) {
				Map.Entry entry = (Map.Entry) object;
				Object key = entry.getKey();

				for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
					char k = CharacterArray.this.array[i];

					if (key != null && key.equals(k)) {
						Object value = entry.getValue();
						char v = CharacterArray.this.array[i + 1];

						if (value != null && value.equals(v))
							return true;

						break;
					}
				}
			}

			return false;
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Object object : collection) {
				if (object instanceof Map.Entry) {
					Map.Entry entry = (Map.Entry) object;
					Object key = entry.getKey();

					for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
						char k = CharacterArray.this.array[i];

						if (key != null && key.equals(k)) {
							Object value = entry.getValue();
							char v = CharacterArray.this.array[i + 1];

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

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof Set) {
				Set set = (Set) object;

				if (set.size() == CharacterArray.this.endIndex - CharacterArray.this.beginIndex >>> 1) {
					for0:
					for (Object object1 : set) {
						if (object1 instanceof Map.Entry) {
							Map.Entry entry = (Map.Entry) object1;
							Object key = entry.getKey();

							for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
								char k = CharacterArray.this.array[i];

								if (key != null && key.equals(k)) {
									Object value = entry.getValue();
									char v = CharacterArray.this.array[i + 1];

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

			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];
				char v = CharacterArray.this.array[i + 1];
				hashCode += Character.hashCode(k) ^
							Character.hashCode(v);
			}

			return hashCode;
		}

		@Override
		public boolean remove(Object object) {
			if (object instanceof Map.Entry) {
				Map.Entry entry = (Map.Entry) object;
				Object key = entry.getKey();

				for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
					char k = CharacterArray.this.array[i];

					if (key != null && key.equals(k)) {
						Object value = ((Map.Entry) object).getValue();
						char v = CharacterArray.this.array[i + 1];

						if (value != null && value.equals(v))
							//can not remove
							throw new UnsupportedOperationException("remove");

						break;
					}
				}
			}

			//no match
			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object object : collection)
				if (object instanceof Map.Entry) {
					Map.Entry entry = (Map.Entry) object;
					Object key = entry.getKey();

					for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
						char k = CharacterArray.this.array[i];

						if (key != null && key.equals(k)) {
							Object value = entry.getValue();
							char v = CharacterArray.this.array[i + 1];

							if (value != null && value.equals(v))
								//can not remove
								throw new UnsupportedOperationException("remove");

							break;
						}
					}
				}

			//no match
			return false;
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];
				char v = CharacterArray.this.array[i + 1];

				for (Object object : collection)
					if (object instanceof Map.Entry) {
						Map.Entry entry = (Map.Entry) object;
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
		public String toString() {
			if (CharacterArray.this.endIndex <= CharacterArray.this.beginIndex)
				return "[]";

			StringBuilder builder = new StringBuilder("[");

			int i = CharacterArray.this.beginIndex;
			while (true) {
				char k = CharacterArray.this.array[i];
				char v = CharacterArray.this.array[i + 1];

				builder.append(k)
						.append("=")
						.append(v);

				i += 2;
				if (i >= CharacterArray.this.endIndex)
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
	public final class CharacterArrayEntrySpliterator extends ArrayEntrySpliterator<Character, Character> {
		/**
		 * Construct a new spliterator iterating the entries in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArrayEntrySpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the entries in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArrayEntrySpliterator(int index) {
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
	public final class CharacterArrayIterator extends ArrayIterator {
		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArrayIterator() {
		}

		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArrayIterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super Character> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = CharacterArray.this.endIndex;

			for (int i = index; i < CharacterArray.this.endIndex; i++) {
				char e = CharacterArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public Character next() {
			int index = this.index;

			if (index < CharacterArray.this.endIndex) {
				this.index++;

				return CharacterArray.this.array[index];
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
	public final class CharacterArrayKeyIterator extends ArrayKeyIterator<Character> {
		/**
		 * Construct a new iterator iterating the keys in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArrayKeyIterator() {
		}

		/**
		 * Construct a new iterator iterating the keys in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArrayKeyIterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super Character> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = CharacterArray.this.endIndex;

			for (int i = index; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				consumer.accept(k);
			}
		}

		@Override
		public Character next() {
			int index = this.index;

			if (index < CharacterArray.this.endIndex) {
				this.index += 2;

				return CharacterArray.this.array[index];
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
	public final class CharacterArrayKeySet extends ArrayKeySet<Character> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 6047336320717832956L;

		/**
		 * Construct a new set backed by the keys in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public CharacterArrayKeySet() {
		}

		@Override
		public boolean add(Character key) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				if (key != null && key.equals(k))
					//already exists
					return false;
			}

			//can not add
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends Character> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Character key : collection) {
				for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
					char k = CharacterArray.this.array[i];

					if (key != null && key.equals(k))
						//already exists
						continue for0;
				}

				//can not add
				throw new UnsupportedOperationException("add");
			}

			//all already exists
			return false;
		}

		@Override
		public boolean contains(Object object) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				if (object != null && object.equals(k))
					return true;
			}

			return false;
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Object key : collection) {
				for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
					char k = CharacterArray.this.array[i];

					if (key != null && key.equals(k))
						continue for0;
				}

				return false;
			}

			return true;
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof Set) {
				Set set = (Set) object;

				if (set.size() == CharacterArray.this.endIndex - CharacterArray.this.beginIndex >>> 1) {
					for0:
					for (Object key : set) {
						for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
							char k = CharacterArray.this.array[i];

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
		public void forEach(Consumer<? super Character> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				consumer.accept(k);
			}
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				hashCode += Character.hashCode(k);
			}

			return hashCode;
		}

		@Override
		public boolean remove(Object object) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				if (object != null && object.equals(k))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//nothing to remove
			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object key : collection)
				for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
					char k = CharacterArray.this.array[i];

					if (key != null && key.equals(k))
						//can not remove
						throw new UnsupportedOperationException("remove");
				}

			//nothing to remove
			return false;
		}

		@Override
		public boolean removeIf(Predicate<? super Character> predicate) {
			Objects.requireNonNull(predicate, "predicate");

			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

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
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

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
		public Object[] toArray() {
			int length = CharacterArray.this.endIndex - CharacterArray.this.beginIndex >>> 1;
			Object[] product = new Object[length];

			for (int i = CharacterArray.this.beginIndex, j = 0; i < CharacterArray.this.endIndex; i += 2, j++) {
				char k = CharacterArray.this.array[i];

				product[j] = k;
			}

			return product;
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			int length = CharacterArray.this.endIndex - CharacterArray.this.beginIndex >>> 1;
			T[] product = array;

			if (array.length < length)
				product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
			else
				product[length] = null;

			for (int i = CharacterArray.this.beginIndex, j = 0; i < CharacterArray.this.endIndex; i += 2, j++) {
				char k = CharacterArray.this.array[i];

				product[j] = (T) (Character) k;
			}

			return product;
		}

		@Override
		public String toString() {
			if (CharacterArray.this.endIndex <= CharacterArray.this.beginIndex)
				return "[]";

			StringBuilder builder = new StringBuilder("[");

			int i = CharacterArray.this.beginIndex;
			while (true) {
				char k = CharacterArray.this.array[i];

				builder.append(k);

				i += 2;
				if (i >= CharacterArray.this.endIndex)
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
	public final class CharacterArrayKeySpliterator extends ArrayKeySpliterator<Character> {
		/**
		 * Construct a new spliterator iterating the keys in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArrayKeySpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the keys in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArrayKeySpliterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super Character> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = CharacterArray.this.endIndex;

			for (int i = index; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				consumer.accept(k);
			}
		}

		@Override
		public boolean tryAdvance(Consumer<? super Character> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < CharacterArray.this.endIndex) {
				this.index += 2;

				char k = CharacterArray.this.array[index];
				consumer.accept(k);
				return true;
			}

			return false;
		}
	}

	/**
	 * A list backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public final class CharacterArrayList extends ArrayList {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -6821775231116953767L;

		/**
		 * Construct a new list backed by the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public CharacterArrayList() {
		}

		@Override
		public boolean contains(Object object) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
				char e = CharacterArray.this.array[i];

				if (object != null && object.equals(e))
					return true;
			}

			return false;
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Object element : collection) {
				for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
					char e = CharacterArray.this.array[i];

					if (element != null && element.equals(e))
						continue for0;
				}

				return false;
			}

			return true;
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				//same identity
				return true;
			if (object instanceof List) {
				//same class
				List list = (List) object;

				if (list.size() == CharacterArray.this.endIndex - CharacterArray.this.beginIndex) {
					//same length

					int i = CharacterArray.this.beginIndex;
					for (Object element : list) {
						//for each element

						if (i < CharacterArray.this.endIndex) {
							//still same length
							char e = CharacterArray.this.array[i++];

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
		public void forEach(Consumer<? super Character> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
				char e = CharacterArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public Character get(int index) {
			CharacterArray.this.requireIndex(index);
			int i = CharacterArray.this.beginIndex + index;
			return CharacterArray.this.array[i];
		}

		@Override
		public int hashCode() {
			int hashCode = 1;

			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
				char e = CharacterArray.this.array[i];

				hashCode = 31 * hashCode + Character.hashCode(e);
			}

			return hashCode;
		}

		@Override
		public int indexOf(Object object) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
				char e = CharacterArray.this.array[i];

				if (object != null && object.equals(e))
					return i - CharacterArray.this.beginIndex;
			}

			return -1;
		}

		@Override
		public int lastIndexOf(Object object) {
			for (int i = CharacterArray.this.endIndex - 1; i >= CharacterArray.this.beginIndex; i--) {
				char e = CharacterArray.this.array[i];

				if (object != null && object.equals(e))
					return i - CharacterArray.this.beginIndex;
			}

			return -1;
		}

		@Override
		public boolean remove(Object object) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
				char e = CharacterArray.this.array[i];

				if (object != null && object.equals(e))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//nothing to remove
			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");
			for (Object element : collection)
				for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
					char e = CharacterArray.this.array[i];

					if (element != null && element.equals(e))
						//can not remove
						throw new UnsupportedOperationException("remove");
				}

			//nothing to remove
			return false;
		}

		@Override
		public boolean removeIf(Predicate<? super Character> predicate) {
			Objects.requireNonNull(predicate, "predicate");
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
				char e = CharacterArray.this.array[i];

				if (predicate.test(e))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//nothing to remove
			return false;
		}

		@Override
		public void replaceAll(UnaryOperator<Character> operator) {
			Objects.requireNonNull(operator, "operator");
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
				char e = CharacterArray.this.array[i];

				CharacterArray.this.array[i] = operator.apply(e);
			}
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
				char e = CharacterArray.this.array[i];

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
		public Character set(int index, Character element) {
			CharacterArray.this.requireIndex(index);
			int i = CharacterArray.this.beginIndex + index;
			char old = CharacterArray.this.array[i];
			CharacterArray.this.array[i] = element;
			return old;
		}

		@Override
		public Object[] toArray() {
			int length = CharacterArray.this.endIndex - CharacterArray.this.beginIndex;
			Object[] product = new Object[length];

			CharacterArray.hardcopy(
					CharacterArray.this.array,
					CharacterArray.this.beginIndex,
					product,
					0,
					length
			);

			return product;
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			int length = CharacterArray.this.endIndex - CharacterArray.this.beginIndex;
			T[] product = array;

			if (array.length < length)
				product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
			else
				product[length] = null;

			CharacterArray.hardcopy(
					CharacterArray.this.array,
					CharacterArray.this.beginIndex,
					product,
					0,
					length
			);

			return product;
		}

		@Override
		public String toString() {
			if (CharacterArray.this.endIndex <= CharacterArray.this.beginIndex)
				return "[]";

			StringBuilder builder = new StringBuilder("[");

			int i = CharacterArray.this.beginIndex;
			while (true) {
				char e = CharacterArray.this.array[i];

				builder.append(e);

				i++;
				if (i >= CharacterArray.this.endIndex)
					return builder.append("]")
							.toString();

				builder.append(", ");
			}
		}
	}

	/**
	 * A list iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public final class CharacterArrayListIterator extends ArrayListIterator {
		/**
		 * Construct a new list iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArrayListIterator() {
		}

		/**
		 * Construct a new list iterator iterating the elements in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArrayListIterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super Character> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = CharacterArray.this.endIndex;
			this.last = CharacterArray.this.endIndex - 1;

			for (int i = index; i < CharacterArray.this.endIndex; i++) {
				char e = CharacterArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public Character next() {
			int index = this.index;

			if (index < CharacterArray.this.endIndex) {
				this.index++;
				this.last = index;

				return CharacterArray.this.array[index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public Character previous() {
			int index = this.index - 1;

			if (index >= CharacterArray.this.beginIndex) {
				this.index--;
				this.last = index;

				return CharacterArray.this.array[index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public void set(Character element) {
			int index = this.last;

			if (index == -1)
				throw new IllegalStateException();

			CharacterArray.this.array[index] = element;
		}
	}

	/**
	 * A map backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public final class CharacterArrayMap extends ArrayMap<Character, Character> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 1924164470541987638L;

		/**
		 * Construct a new map backed by the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public CharacterArrayMap() {
		}

		@Override
		public Character compute(Character key, BiFunction<? super Character, ? super Character, ? extends Character> function) {
			Objects.requireNonNull(function, "function");

			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				if (key == k || key != null && key.equals(k)) {
					char v = CharacterArray.this.array[i + 1];
					Character value = function.apply(k, v);

					if (value == null)
						//old:notnull new:null
						throw new UnsupportedOperationException("remove");

					//old:found
					CharacterArray.this.array[i + 1] = value;
					return value;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public Character computeIfAbsent(Character key, Function<? super Character, ? extends Character> function) {
			Objects.requireNonNull(function, "function");

			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				if (key == k || key != null && key.equals(k))
					//old:notnull
					return CharacterArray.this.array[i + 1];
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public Character computeIfPresent(Character key, BiFunction<? super Character, ? super Character, ? extends Character> function) {
			Objects.requireNonNull(function, "function");

			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				if (key == k || key != null && key.equals(k)) {
					char v = CharacterArray.this.array[i + 1];
					Character value = function.apply(k, v);

					if (value == null)
						//old:notnull new:null
						throw new UnsupportedOperationException("remove");

					//old:notnull new:notnull
					CharacterArray.this.array[i + 1] = value;
					return value;
				}
			}

			//old:notfound
			return null;
		}

		@Override
		public boolean containsKey(Object key) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				if (key != null && key.equals(k))
					return true;
			}

			return false;
		}

		@Override
		public boolean containsValue(Object value) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char v = CharacterArray.this.array[i];

				if (value != null && value.equals(v))
					return true;
			}

			return false;
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof Map) {
				Map<?, ?> map = (Map) object;

				if (map.size() == CharacterArray.this.endIndex - CharacterArray.this.beginIndex >>> 1) {
					for0:
					for (Entry entry : map.entrySet()) {
						Object key = entry.getKey();

						for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
							char k = CharacterArray.this.array[i];

							if (key != null && key.equals(k)) {
								Object value = entry.getValue();
								char v = CharacterArray.this.array[i + 1];

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
		public void forEach(BiConsumer<? super Character, ? super Character> consumer) {
			Objects.requireNonNull(consumer, "consumer");

			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];
				char v = CharacterArray.this.array[i + 1];
				consumer.accept(k, v);
			}
		}

		@Override
		public Character get(Object key) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				if (key != null && key.equals(k))
					return CharacterArray.this.array[i + 1];
			}

			return null;
		}

		@Override
		public Character getOrDefault(Object key, Character defaultValue) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				if (key != null && key.equals(k))
					return CharacterArray.this.array[i + 1];
			}

			return defaultValue;
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];
				char v = CharacterArray.this.array[i + 1];
				hashCode += Character.hashCode(k) ^
							Character.hashCode(v);
			}

			return hashCode;
		}

		@Override
		public Character merge(Character key, Character value, BiFunction<? super Character, ? super Character, ? extends Character> function) {
			Objects.requireNonNull(function, "function");

			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				if (key != null && key.equals(k)) {
					char v = CharacterArray.this.array[i + 1];
					Character newValue = function.apply(v, value);

					if (newValue == null)
						//old:found new:null
						throw new UnsupportedOperationException("remove");

					//old:found new:notnull
					CharacterArray.this.array[i + 1] = newValue;
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
		public Character put(Character key, Character value) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				if (key != null && key.equals(k)) {
					//old:found
					char v = CharacterArray.this.array[i + 1];
					CharacterArray.this.array[i + 1] = value;
					return v;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public void putAll(Map<? extends Character, ? extends Character> map) {
			Objects.requireNonNull(map, "map");

			for0:
			for (Entry<? extends Character, ? extends Character> entry : map.entrySet()) {
				Character key = entry.getKey();

				for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
					char k = CharacterArray.this.array[i];

					if (key != null && key.equals(k)) {
						Character value = entry.getValue();
						CharacterArray.this.array[i + 1] = value;
						continue for0;
					}
				}

				//old:notfound
				throw new UnsupportedOperationException("put");
			}
		}

		@Override
		public Character putIfAbsent(Character key, Character value) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				if (key != null && key.equals(k))
					//old:found
					return CharacterArray.this.array[i + 1];
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public Character remove(Object key) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				if (key != null && key.equals(k))
					//old:found
					throw new UnsupportedOperationException("remove");
			}

			//old:notfound
			return null;
		}

		@Override
		public boolean remove(Object key, Object value) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				if (key != null && key.equals(k)) {
					char v = CharacterArray.this.array[i + 1];

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
		public boolean replace(Character key, Character oldValue, Character newValue) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				if (key != null && key.equals(k)) {
					char v = CharacterArray.this.array[i + 1];

					if (oldValue != null && oldValue.equals(v)) {
						//old:match
						CharacterArray.this.array[i + 1] = newValue;
						return true;
					}

					break;
				}
			}

			//old:nomatch
			return false;
		}

		@Override
		public Character replace(Character key, Character value) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];

				if (key != null && key.equals(k)) {
					//old:match
					char v = CharacterArray.this.array[i + 1];
					CharacterArray.this.array[i + 1] = value;
					return v;
				}
			}

			//old:nomatch
			return null;
		}

		@Override
		public void replaceAll(BiFunction<? super Character, ? super Character, ? extends Character> function) {
			Objects.requireNonNull(function, "function");

			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i += 2) {
				char k = CharacterArray.this.array[i];
				char v = CharacterArray.this.array[i + 1];

				CharacterArray.this.array[i + 1] = function.apply(k, v);
			}
		}

		@Override
		public String toString() {
			if (CharacterArray.this.endIndex <= CharacterArray.this.beginIndex)
				return "{}";

			StringBuilder builder = new StringBuilder("{");

			int i = CharacterArray.this.beginIndex;
			while (true) {
				char k = CharacterArray.this.array[i];
				char v = CharacterArray.this.array[i + 1];

				builder.append(k)
						.append("=")
						.append(v);

				i += 2;
				if (i >= CharacterArray.this.endIndex)
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
	public final class CharacterArraySet extends ArraySet {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -8265570214297191438L;

		/**
		 * Construct a new set backed by the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public CharacterArraySet() {
		}

		@Override
		public boolean add(Character element) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
				char e = CharacterArray.this.array[i];

				if (element != null && element.equals(e))
					//already exists
					return false;
			}

			//can not add
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends Character> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Character element : collection) {
				for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
					char e = CharacterArray.this.array[i];

					if (element != null && element.equals(e))
						//already exists
						continue for0;
				}

				//can not add
				throw new UnsupportedOperationException("add");
			}

			//all already exists
			return false;
		}

		@Override
		public boolean contains(Object object) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
				char e = CharacterArray.this.array[i];

				if (object != null && object.equals(e))
					return true;
			}

			return false;
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Object element : collection) {
				for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
					char e = CharacterArray.this.array[i];

					if (element != null && element.equals(e))
						continue for0;
				}

				return false;
			}

			return true;
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				//same identity
				return true;
			if (object instanceof Set) {
				//same class
				Set set = (Set) object;

				if (set.size() == CharacterArray.this.endIndex - CharacterArray.this.beginIndex) {
					//same length

					for0:
					for (Object element : set) {
						//for each element

						for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
							char e = CharacterArray.this.array[i];

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
		public void forEach(Consumer<? super Character> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
				char e = CharacterArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
				char e = CharacterArray.this.array[i];

				hashCode += Character.hashCode(e);
			}

			return hashCode;
		}

		@Override
		public boolean remove(Object object) {
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
				char e = CharacterArray.this.array[i];

				if (object != null && object.equals(e))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//nothing to remove
			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object element : collection)
				for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
					char e = CharacterArray.this.array[i];

					if (element != null && element.equals(e))
						//can not remove
						throw new UnsupportedOperationException("remove");
				}

			//nothing to remove
			return false;
		}

		@Override
		public boolean removeIf(Predicate<? super Character> predicate) {
			Objects.requireNonNull(predicate, "predicate");

			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
				char e = CharacterArray.this.array[i];

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
			for (int i = CharacterArray.this.beginIndex; i < CharacterArray.this.endIndex; i++) {
				char e = CharacterArray.this.array[i];

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
		public Object[] toArray() {
			int length = CharacterArray.this.endIndex - CharacterArray.this.beginIndex;
			Object[] product = new Object[length];

			CharacterArray.hardcopy(
					CharacterArray.this.array,
					CharacterArray.this.beginIndex,
					product,
					0,
					length
			);

			return product;
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			int length = CharacterArray.this.endIndex - CharacterArray.this.beginIndex;
			T[] product = array;

			if (array.length < length)
				product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
			else
				product[length] = null;

			CharacterArray.hardcopy(
					CharacterArray.this.array,
					CharacterArray.this.beginIndex,
					product,
					0,
					length
			);

			return product;
		}

		@Override
		public String toString() {
			if (CharacterArray.this.endIndex <= CharacterArray.this.beginIndex)
				return "[]";

			StringBuilder builder = new StringBuilder("[");

			int i = CharacterArray.this.beginIndex;
			while (true) {
				char e = CharacterArray.this.array[i];

				builder.append(e);

				i++;
				if (i >= CharacterArray.this.endIndex)
					return builder.append("]")
							.toString();

				builder.append(", ");
			}
		}
	}

	/**
	 * A spliterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public final class CharacterArraySpliterator extends ArraySpliterator {
		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from the given {@code index}.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArraySpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArraySpliterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super Character> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = CharacterArray.this.endIndex;

			for (int i = index; i < CharacterArray.this.endIndex; i++) {
				char e = CharacterArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public boolean tryAdvance(Consumer<? super Character> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < CharacterArray.this.endIndex) {
				this.index += 2;
				char e = CharacterArray.this.array[index];

				consumer.accept(e);
				return true;
			}

			return false;
		}
	}

	/**
	 * An iterator iterating the values in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public final class CharacterArrayValueIterator extends ArrayValueIterator<Character> {
		/**
		 * Construct a new iterator iterating the values in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArrayValueIterator() {
		}

		/**
		 * Construct a new iterator iterating the values in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArrayValueIterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super Character> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = CharacterArray.this.endIndex;

			for (int i = index + 1; i < CharacterArray.this.endIndex; i += 2) {
				char v = CharacterArray.this.array[i];

				consumer.accept(v);
			}
		}

		@Override
		public Character next() {
			int index = this.index;

			if (index < CharacterArray.this.endIndex) {
				this.index += 2;

				return CharacterArray.this.array[index + 1];
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
	public final class CharacterArrayValueSpliterator extends ArrayValueSpliterator<Character> {
		/**
		 * Construct a new spliterator iterating the values in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArrayValueSpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the values in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}
		 * @since 0.1.5 ~2020.08.06
		 */
		public CharacterArrayValueSpliterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super Character> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = CharacterArray.this.endIndex;

			for (int i = index + 1; i < CharacterArray.this.endIndex; i += 2) {
				char v = CharacterArray.this.array[i];

				consumer.accept(v);
			}
		}

		@Override
		public boolean tryAdvance(Consumer<? super Character> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < CharacterArray.this.endIndex) {
				this.index += 2;

				char v = CharacterArray.this.array[index + 1];
				consumer.accept(v);
				return true;
			}

			return false;
		}
	}

	/**
	 * A collection backed by the values in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public final class CharacterArrayValues extends ArrayValues<Character> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -6890162958501631510L;

		/**
		 * Construct a new collection backed by the values in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public CharacterArrayValues() {
		}

		@Override
		public boolean contains(Object object) {
			for (int i = CharacterArray.this.beginIndex + 1; i < CharacterArray.this.endIndex; i += 2) {
				char v = CharacterArray.this.array[i];

				if (object != null && object.equals(v))
					return true;
			}

			return false;
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Object value : collection) {
				for (int i = CharacterArray.this.beginIndex + 1; i < CharacterArray.this.endIndex; i += 2) {
					char v = CharacterArray.this.array[i];

					if (value != null && value.equals(v))
						continue for0;
				}

				return false;
			}

			return true;
		}

		@Override
		public boolean equals(Object object) {
			return object == this;
		}

		@Override
		public void forEach(Consumer<? super Character> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			for (int i = CharacterArray.this.beginIndex + 1; i < CharacterArray.this.endIndex; i += 2) {
				char v = CharacterArray.this.array[i];

				consumer.accept(v);
			}
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = CharacterArray.this.beginIndex + 1; i < CharacterArray.this.endIndex; i += 2) {
				char v = CharacterArray.this.array[i];

				hashCode += Character.hashCode(v);
			}

			return hashCode;
		}

		@Override
		public boolean remove(Object object) {
			for (int i = CharacterArray.this.beginIndex + 1; i < CharacterArray.this.endIndex; i += 2) {
				char v = CharacterArray.this.array[i];

				if (object != null && object.equals(v))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//nothing to remove
			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object value : collection)
				for (int i = CharacterArray.this.beginIndex + 1; i < CharacterArray.this.endIndex; i += 2) {
					char v = CharacterArray.this.array[i];

					if (value != null && value.equals(v))
						//can not remove
						throw new UnsupportedOperationException("remove");
				}

			//nothing to remove
			return false;
		}

		@Override
		public boolean removeIf(Predicate<? super Character> predicate) {
			Objects.requireNonNull(predicate, "predicate");

			for (int i = CharacterArray.this.beginIndex + 1; i < CharacterArray.this.endIndex; i += 2) {
				char v = CharacterArray.this.array[i];

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
			for (int i = CharacterArray.this.beginIndex + 1; i < CharacterArray.this.endIndex; i += 2) {
				char v = CharacterArray.this.array[i];

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
		public Object[] toArray() {
			int length = CharacterArray.this.endIndex - CharacterArray.this.beginIndex >>> 1;
			Object[] product = new Object[length];

			for (int i = CharacterArray.this.beginIndex + 1, j = 0; i < CharacterArray.this.endIndex; i += 2, j++) {
				char v = CharacterArray.this.array[i];

				product[j] = v;
			}

			return product;
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			int length = CharacterArray.this.endIndex - CharacterArray.this.beginIndex >>> 1;
			T[] product = array;

			if (array.length < length)
				product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
			else
				product[length] = null;

			for (int i = CharacterArray.this.beginIndex + 1, j = 0; i < CharacterArray.this.endIndex; i += 2, j++) {
				char v = CharacterArray.this.array[i];

				product[j] = (T) (Character) v;
			}

			return product;
		}

		@Override
		public String toString() {
			if (CharacterArray.this.endIndex <= CharacterArray.this.beginIndex)
				return "[]";

			StringBuilder builder = new StringBuilder("[");

			int i = CharacterArray.this.beginIndex + 1;
			while (true) {
				char v = CharacterArray.this.array[i];

				builder.append(v);

				i += 2;
				if (i >= CharacterArray.this.endIndex)
					return builder.append("]")
							.toString();

				builder.append(", ");
			}
		}
	}
}
