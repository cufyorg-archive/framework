package cufy.util.collection;

import cufy.util.Objects;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

/**
 * A class holding the reversed collection wrapper classes.
 * <p>
 * Note: this class chosen to be an interface to allow inheritance in the utility classes.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.25
 */
public interface ReversedCollections {
	/**
	 * An instance of the reversed natural comparator.
	 *
	 * @since 0.1.5 ~2020.08.25
	 */
	ReverseNaturalComparator REVERSE_NATURAL_COMPARATOR = new ReverseNaturalComparator();

	/**
	 * Return the reversed <i>natural ordering</i> comparator.
	 *
	 * @param <T> the type of the elements.
	 * @return the <i>natural ordering</i> comparator.
	 * @see Collections#reverseOrder()
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> Comparator<T> reverseOrder() {
		return ReversedCollections.REVERSE_NATURAL_COMPARATOR;
	}

	/**
	 * Return a reversed comparator for the given {@code comparator}.
	 *
	 * @param <T>        the type of the elements.
	 * @param comparator the comparator to be reversed. Null value will return the reverse of the
	 *                   <i>natural ordering</i>.
	 * @return a reversed comparator of the given {@code comparator}.
	 * @see Collections#reverseOrder(Comparator)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> Comparator<T> reverseOrder(Comparator<T> comparator) {
		//noinspection AccessingNonPublicFieldOfAnotherObject
		return comparator == null ?
			   ReversedCollections.REVERSE_NATURAL_COMPARATOR :
			   comparator instanceof ReverseComparator ?
			   ((ReverseComparator<T>) comparator).comparator :
			   new ReverseComparator(comparator);
	}

	/**
	 * Reversed comparator based on another comparator.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.25
	 */
	class ReverseComparator<E> implements Comparator<E>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -8830222155440983107L;

		/**
		 * The comparator backing this comparator. The comparator will be reversed when used.
		 *
		 * @since 0.1.5 ~2020.08.25
		 */
		protected final Comparator<E> comparator;

		/**
		 * Construct a new reversed comparator reversing the given {@code comparator}.
		 *
		 * @param comparator the comparator the constructed comparator is backed by the reverse of
		 *                   it.
		 * @throws NullPointerException if the given {@code comparator} is null.
		 * @since 0.1.5 ~2020.08.25
		 */
		public ReverseComparator(Comparator<E> comparator) {
			Objects.requireNonNull(comparator, "comparator");
			this.comparator = comparator;
		}

		@Override
		public int compare(E element, E other) {
			return this.comparator.compare(other, element);
		}

		@Override
		public boolean equals(Object object) {
			return object == this ||
				   object instanceof ReverseComparator &&
				   ((ReverseComparator) object).comparator.equals(this.comparator);
		}

		@Override
		public int hashCode() {
			return this.comparator.hashCode() ^ Integer.MIN_VALUE;
		}

		@Override
		public Comparator<E> reversed() {
			return this.comparator;
		}
	}

	/**
	 * Reversed <i>natural ordering</i> comparator.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.25
	 */
	class ReverseNaturalComparator<E extends Comparable<E>> implements Comparator<E>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -1204085588134449462L;

		@Override
		public int compare(E element, E other) {
			return other.compareTo(element);
		}

		@Override
		public Comparator<E> reversed() {
			return Comparator.naturalOrder();
		}
	}
}
