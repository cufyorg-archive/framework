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

import cufy.util.collection.*;

import java.util.*;

/**
 * An Utility class for collections. Supporting various kinds of collection operations.
 * <p>
 * This class includes all the methods in the standard {@link java.util.Collections} utility class
 * with the same behaviour. So switching to import this class will not make any changes to files
 * previously imported {@link java.util.Collections}.
 * <p>
 * Note: this class chosen to be an interface to allow inheritance in the utility classes.
 *
 * @author LSafer
 * @version 0.1.3
 * @since 0.0.b ~2019.06.11
 */
public interface CollectionUtil extends
		CheckedCollections,
		EmptyCollections,
		NCopiesCollections,
		ReversedCollections,
		SingletonCollections,
		SynchronizedCollections,
		TransformCollections,
		UnmodifiableCollections {
	//todo FilteredCollections (unmodifiable)
	//todo CombinedCollections (unmodifiable)

	/**
	 * Add all the given {@code elements} to the given {@code collection}.
	 *
	 * @param collection the collection to add the given {@code elements} to.
	 * @param elements   the elements to be added to the given {@code collection}.
	 * @param <T>        the type of the elements.
	 * @return true, if hte given {@code collection} has been changed as a result of this call.
	 * @throws NullPointerException if the given {@code collection} or {@code elements} is null.
	 * @see java.util.Collections#addAll(Collection, Object[])
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> boolean addAll(Collection<? super T> collection, T... elements) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(elements, "elements");
		boolean modified = false;
		for (T element : elements)
			modified |= collection.add(element);
		return modified;
	}

	/**
	 * Search the given {@code list} for the given {@code key} using the binarySearch algorithm. The
	 * list should be sorted for this method to work. If it is not sorted, the results are
	 * undefined. If the list contains multiple elements with the specified value, there is no
	 * guarantee witch one will be found.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#binarySearch(List, Object)} since
	 * the field {@link java.util.Collections#BINARYSEARCH_THRESHOLD} is not accessible.
	 *
	 * @param list the list to be searched.
	 * @param key  the key to search for.
	 * @param <T>  the type of the elements.
	 * @return the index of the search element, if is contained in the given {@code list};
	 * 		otherwise, (-(<i>insertion point</i>) - 1).
	 * @see java.util.Collections#binarySearch(List, Object)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> int binarySearch(List<? extends Comparable<? super T>> list, T key) {
		//can not access java.util.Collections.BINARYSEARCH_THRESHOLD
		return java.util.Collections.binarySearch(list, key);
	}

	/**
	 * Search the given {@code list} for the given {@code key} using the binarySearch algorithm. The
	 * list should be sorted for this method to work. If it is not sorted, the results are
	 * undefined. If the list contains multiple elements with the specified value, there is no
	 * guarantee witch one will be found.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#binarySearch(List, Object,
	 * Comparator)} since the field {@link java.util.Collections#BINARYSEARCH_THRESHOLD} is not
	 * accessible.
	 *
	 * @param list       the list to be searched.
	 * @param key        the key to search for.
	 * @param comparator the comparator by which the list is ordered. A null value indicates that
	 *                   the elements' natural ordering should be used.
	 * @param <T>        the type of the elements.
	 * @return the index of the search element, if is contained in the given {@code list};
	 * 		otherwise, (-(<i>insertion point</i>) - 1).
	 * @see java.util.Collections#binarySearch(List, Object, Comparator)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> int binarySearch(List<T> list, T key, Comparator<? super T> comparator) {
		//can not access java.util.Collections.BINARYSEARCH_THRESHOLD
		return java.util.Collections.binarySearch(list, key, comparator);
	}

	/**
	 * Copy all the element in the given {@code src} to exactly the same indexes at the given {@code
	 * dest}.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#copy(List, List)} since the field
	 * {@link java.util.Collections#COPY_THRESHOLD} is not accessible.
	 *
	 * @param dest the destination list.
	 * @param src  the source list.
	 * @param <T>  the type of the elements.
	 * @throws NullPointerException if the given {@code dest} or {@code src} is null.
	 * @see java.util.Collections#copy(List, List)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> void copy(List<? super T> dest, List<? extends T> src) {
		//can not access java.util.Collections.COPY_THRESHOLD
		java.util.Collections.copy(dest, src);
	}

	/**
	 * Determine if the given {@code collection} contains no element in the given {@code other}.
	 *
	 * @param collection the collection.
	 * @param other      the other collection.
	 * @param <T>        the type of the elements in the first collection.
	 * @param <U>        the type of the elements in the second collection.
	 * @return true, if the given {@code collection} contains no element in the given {@code other}.
	 * @throws NullPointerException if the given {@code collection} or {@code other} is null.
	 * @see java.util.Collections#disjoint(Collection, Collection)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T, U> boolean disjoint(Collection<T> collection, Collection<U> other) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(other, "other");

		if (collection.isEmpty() || other.isEmpty())
			//nothing to match
			return true;
		if (collection == other)
			//no need to match, since it is itself and itself is not empty
			return false;

		if (collection instanceof Set ||
			!(other instanceof Set) &&
			collection.size() > other.size()) {
			for (U u : other)
				if (collection.contains(u))
					return false;
		} else
			for (T t : collection)
				if (other.contains(t))
					return false;

		//no match
		return true;
	}

	/**
	 * Replace all the elements in the given {@code list} with the given {@code element}.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#fill(List, Object)} since the
	 * field {@link java.util.Collections#FILL_THRESHOLD} is not accessible.
	 *
	 * @param list    the list to be filled.
	 * @param element the element to fill the given {@code list}.
	 * @param <T>     the type of the elements.
	 * @throws NullPointerException if the given {@code list} is null.
	 * @see java.util.Collections#fill(List, Object)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> void fill(List<? super T> list, T element) {
		//can not access java.util.Collections.FILL_THRESHOLD
		java.util.Collections.fill(list, element);
	}

	/**
	 * Determine how many times the given {@code element} has been stored in the given {@code
	 * collection}.
	 *
	 * @param collection the collection that could have the given {@code element}.
	 * @param element    the element to find how many time it occurred in the given {@code
	 *                   collection}.
	 * @param <T>        the type of the elements in the collection.
	 * @param <U>        the type of the element.
	 * @return the number of how many times the given {@code element} appeared in the given {@code
	 * 		collection}.
	 * @throws NullPointerException if the given {@code collection is null}.
	 * @see java.util.Collections#frequency(Collection, Object)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T, U> int frequency(Collection<T> collection, U element) {
		Objects.requireNonNull(collection, "collection");
		int frequency = 0;

		if (element == null) {
			for (T e : collection)
				if (e == null)
					frequency++;
		} else
			for (T e : collection)
				if (element.equals(e))
					frequency++;

		return frequency;
	}

	/**
	 * Find the index where the first sequence of elements that equals the given {@code target}
	 * begins.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#indexOfSubList(List, List)} since
	 * the field {@link java.util.Collections#INDEXOFSUBLIST_THRESHOLD} is not accessible.
	 *
	 * @param source the source array.
	 * @param target the sequence to be found.
	 * @param <T>    the type of the elements in the source list.
	 * @param <U>    the type of the elements in the target list.
	 * @return the index where the first sequence of elements that equals the given {@code target}
	 * 		begins.
	 * @throws NullPointerException if the given {@code source} or {@code target} is null.
	 * @see java.util.Collections#indexOfSubList(List, List)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T, U> int indexOfSubList(List<T> source, List<U> target) {
		//can not access java.util.Collections.INDEXOFSUBLIST_THRESHOLD
		return java.util.Collections.indexOfSubList(source, target);
	}

	/**
	 * Find the index where the last sequence of elements that equals the given {@code target}
	 * begins.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#lastIndexOfSubList(List, List)}
	 * since the field {@link java.util.Collections#INDEXOFSUBLIST_THRESHOLD} is not accessible.
	 *
	 * @param source the source array.
	 * @param target the sequence to be found.
	 * @param <T>    the type of the elements in the source list.
	 * @param <U>    the type of the elements in the target list.
	 * @return the index where the last sequence of elements that equals the given {@code target}
	 * 		begins.
	 * @throws NullPointerException if the given {@code source} or {@code target} is null.
	 * @see java.util.Collections#lastIndexOfSubList(List, List)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T, U> int lastIndexOfSubList(List<T> source, List<U> target) {
		//can not access java.util.Collections.INDEXOFSUBLIST_THRESHOLD
		return java.util.Collections.lastIndexOfSubList(source, target);
	}

	/**
	 * Construct a new {@link ArrayList} then fill it with the remaining elements in the given
	 * {@code enumeration}.
	 *
	 * @param enumeration the enumeration to read from.
	 * @param <T>         the type of the elements.
	 * @return a new {@link ArrayList} filled with the remaining elements in the given {@code
	 * 		enumeration}.
	 * @throws NullPointerException if the given {@code enumeration} is null.
	 * @see java.util.Collections#list(Enumeration)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> ArrayList<T> list(Enumeration<T> enumeration) {
		Objects.requireNonNull(enumeration, "enumeration");
		ArrayList list = new ArrayList();

		while (enumeration.hasMoreElements())
			list.add(enumeration.nextElement());

		return list;
	}

	/**
	 * Find the maximum element in the given {@code collection} based on the <i>natural
	 * ordering</i>.
	 *
	 * @param collection the collection to find the maximum element stored in it.
	 * @param <T>        the type of the elements.
	 * @return the maximum element in the given {@code collection}.
	 * @throws NullPointerException   if the given {@code collection} is null.
	 * @throws NoSuchElementException if the given {@code collection} is empty.
	 * @see java.util.Collections#max(Collection)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T extends Comparable<? super T>> T max(Collection<? extends T> collection) {
		Objects.requireNonNull(collection, "collection");
		Iterator<? extends T> iterator = collection.iterator();

		T max = iterator.next();
		while (iterator.hasNext()) {
			T element = iterator.next();

			if (element.compareTo(max) > 0)
				max = element;
		}

		return max;
	}

	/**
	 * Find the maximum element in the given {@code collection} based on the given{@code
	 * comparator}.
	 *
	 * @param collection the collection to find the maximum element stored in it.
	 * @param comparator the comparator to be used to compare between the elements. A null value
	 *                   indicates that the elements' natural ordering should be used.
	 * @param <T>        the type of the elements.
	 * @return the maximum element in the given {@code collection}.
	 * @throws NullPointerException   if the given {@code collection} is null.
	 * @throws NoSuchElementException if the given {@code collection} is empty.
	 * @see java.util.Collections#max(Collection, Comparator)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> T max(Collection<? extends T> collection, Comparator<? super T> comparator) {
		Objects.requireNonNull(collection, "collection");
		if (comparator == null)
			return (T) CollectionUtil.max((Collection) collection);

		Iterator<? extends T> iterator = collection.iterator();

		T max = iterator.next();
		while (iterator.hasNext()) {
			T element = iterator.next();

			if (comparator.compare(element, max) > 0)
				max = element;
		}

		return max;
	}

	/**
	 * Find the minimum element in the given {@code collection} based on the <i>natural
	 * ordering</i>.
	 *
	 * @param collection the collection to find the minimum element stored in it.
	 * @param <T>        the type of the elements.
	 * @return the minimum element in the given {@code collection}.
	 * @throws NullPointerException   if the given {@code collection} is null.
	 * @throws NoSuchElementException if the given {@code collection} is empty.
	 * @see java.util.Collections#min(Collection)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T extends Comparable<? super T>> T min(Collection<? extends T> collection) {
		Objects.requireNonNull(collection, "collection");
		Iterator<? extends T> iterator = collection.iterator();

		T min = iterator.next();
		while (iterator.hasNext()) {
			T element = iterator.next();

			if (element.compareTo(min) < 0)
				min = element;
		}

		return min;
	}

	/**
	 * Find the minimum element in the given {@code collection} based on the given{@code
	 * comparator}.
	 *
	 * @param collection the collection to find the minimum element stored in it.
	 * @param comparator the comparator to be used to compare between the elements. A null value
	 *                   indicates that the elements' natural ordering should be used.
	 * @param <T>        the type of the elements.
	 * @return the minimum element in the given {@code collection}.
	 * @throws NullPointerException   if the given {@code collection} is null.
	 * @throws NoSuchElementException if the given {@code collection} is empty.
	 * @see java.util.Collections#min(Collection, Comparator)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> T min(Collection<? extends T> collection, Comparator<? super T> comparator) {
		Objects.requireNonNull(collection, "collection");
		if (comparator == null)
			return (T) CollectionUtil.max((Collection) collection);

		Iterator<? extends T> iterator = collection.iterator();

		T min = iterator.next();
		while (iterator.hasNext()) {
			T element = iterator.next();

			if (comparator.compare(element, min) < 0)
				min = element;
		}

		return min;
	}

	/**
	 * Replace all the elements that equals the given {@code oldValue} in the given {@code list}
	 * with the given {@code newValue}.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#replaceAll(List, Object, Object)}
	 * since the field {@link java.util.Collections#REPLACEALL_THRESHOLD} is not accessible.
	 *
	 * @param list     the list to be modified.
	 * @param oldValue the value to be replaced with the given {@code newValue}.
	 * @param newValue the new value to replace all the elements that equals the given {@code
	 *                 oldValue} with.
	 * @param <T>      the type of the elements.
	 * @return true, if the given {@code list} changed as a result of this call.
	 * @throws NullPointerException if the given {@code list} is null.
	 * @see java.util.Collections#replaceAll(List, Object, Object)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> boolean replaceAll(List<T> list, T oldValue, T newValue) {
		//can not access java.util.Collections.REPLACEALL_THRESHOLD
		return java.util.Collections.replaceAll(list, oldValue, newValue);
	}

	/**
	 * Reverse the given {@code list}.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#reverse(List)} since the field
	 * {@link java.util.Collections#REVERSE_THRESHOLD} is not accessible.
	 *
	 * @param list the list to be reversed.
	 * @param <T>  the type of the elements.
	 * @throws NullPointerException if the given {@code list} is null.
	 * @see java.util.Collections#reverse(List)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> void reverse(List<T> list) {
		//can not access java.util.Collections.REVERSE_THRESHOLD
		java.util.Collections.reverse(list);
	}

	/**
	 * Rotate teh given given by the given {@code degree}.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#rotate(List, int)} since the field
	 * {@link java.util.Collections#ROTATE_THRESHOLD} is not accessible.
	 *
	 * @param list   the list to be rotated.
	 * @param degree how much steps the elements will be shifted by.
	 * @param <T>    the type of the elements.
	 * @throws NullPointerException if the given {@code list} is null.
	 * @see java.util.Collections#rotate(List, int)
	 * @since 0.1.5 ~2020.08.25
	 */
	static <T> void rotate(List<T> list, int degree) {
		//can not access java.util.Collections.ROTATE_THRESHOLD
		java.util.Collections.rotate(list, degree);
	}

	/**
	 * Randomly swap the elements in the given {@code list}.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#shuffle(List)} since the field
	 * {@link java.util.Collections#SHUFFLE_THRESHOLD} is not accessible.
	 *
	 * @param list the list to be shuffled.
	 * @param <T>  the type of the elements.
	 * @throws NullPointerException if the given {@code list} is null.
	 * @see java.util.Collections#shuffle(List)
	 * @since 0.1.5 ~2020.08.25
	 */
	static <T> void shuffle(List<T> list) {
		//can not access java.util.Collections.SHUFFLE_THRESHOLD
		java.util.Collections.shuffle(list);
	}

	/**
	 * Randomly (depending on the given {@code random}) swap the elements in the given {@code
	 * list}.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#shuffle(List, Random)} since the
	 * field {@link java.util.Collections#ROTATE_THRESHOLD} is not accessible.
	 *
	 * @param list   the list to be shuffled.
	 * @param random the source of randomness.
	 * @param <T>    the type of the elements.
	 * @throws NullPointerException if the given {@code list} or {@code random} is null.
	 * @see java.util.Collections#shuffle(List, Random)
	 * @since 0.1.5 ~2020.08.25
	 */
	static <T> void shuffle(List<T> list, Random random) {
		//can not access java.util.Collections.SHUFFLE_THRESHOLD
		java.util.Collections.shuffle(list, random);
	}

	/**
	 * Sort the given {@code list} depending on the <i>natural ordering</i>.
	 *
	 * @param list the list to be sorted.
	 * @param <T>  the type of the elements.
	 * @throws NullPointerException if the given {@code list} is null.
	 * @see java.util.Collections#sort(List)
	 * @since 0.1.5 ~2020.08.25
	 */
	static <T extends Comparable<? super T>> void sort(List<T> list) {
		Objects.requireNonNull(list, "list");
		//as in java.util.Collections.sort(List)
		list.sort(null);
	}

	/**
	 * Sort the given {@code list} depending on the given {@code comparator}.
	 *
	 * @param list       the list to be sorted.
	 * @param comparator the comparator to be used to sort the given {@code list}. Null will make
	 *                   the method sort depending on the <i>natural ordering</i> of the elements.
	 * @param <T>        the type of the elements.
	 * @throws NullPointerException if the given {@code list} is null.
	 * @see java.util.Collections#sort(List, Comparator)
	 * @since 0.1.5 ~2020.08.25
	 */
	static <T> void sort(List<T> list, Comparator<? super T> comparator) {
		Objects.requireNonNull(list, "list");
		//as in java.util.Collections.sort(List, Comparator)
		list.sort(comparator);
	}

	/**
	 * Swap the element in the given {@code list} at the index {@code i} with the element in the
	 * index {@code j}.
	 *
	 * @param list the list to swap the two of its elements.
	 * @param i    the index of the first element in the given {@code list} to be swapped.
	 * @param j    the index of the second element in the given {@code list} to be swapped.
	 * @param <T>  the type of the elements.
	 * @throws NullPointerException      if the given {@code list} is null.
	 * @throws IndexOutOfBoundsException if {@code i < 0} or {@code j < 0} or {@code i >=
	 *                                   list.size()} or {@code j >= list.size()}.
	 * @see java.util.Collections#swap(List, int, int)
	 * @since 0.1.5 ~2020.08.25
	 */
	static <T> void swap(List<T> list, int i, int j) {
		Objects.requireNonNull(list, "list");
		list.set(i, list.set(j, list.get(i)));
	}
}
