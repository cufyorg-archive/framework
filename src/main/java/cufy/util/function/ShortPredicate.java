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
package cufy.util.function;

import cufy.util.Objects;

import java.util.function.Predicate;

/**
 * Represents a predicate (boolean-valued function) of one {@code short}-valued argument. This is
 * the {@code short}-consuming primitive type specialization of {@link Predicate}.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #test(short)}.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.30
 */
@FunctionalInterface
public interface ShortPredicate {
	/**
	 * Returns a composed predicate that represents a short-circuiting logical AND of this predicate
	 * and another.  When evaluating the composed predicate, if this predicate is {@code false},
	 * then the {@code other} predicate is not evaluated.
	 * <p>
	 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if
	 * evaluation of this predicate throws an exception, the {@code other} predicate will not be
	 * evaluated.
	 *
	 * @param other a predicate that will be logically-ANDed with this predicate.
	 * @return a composed predicate that represents the short-circuiting logical AND of this
	 * 		predicate and the {@code other} predicate.
	 * @throws NullPointerException if the given {@code other} is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	default ShortPredicate and(ShortPredicate other) {
		Objects.requireNonNull(other, "other");
		return value -> this.test(value) && other.test(value);
	}

	/**
	 * Returns a predicate that represents the logical negation of this predicate.
	 *
	 * @return a predicate that represents the logical negation of this predicate.
	 * @since 0.1.5 ~2020.08.30
	 */
	default ShortPredicate negate() {
		return value -> !this.test(value);
	}

	/**
	 * Returns a composed predicate that represents a short-circuiting logical OR of this predicate
	 * and another.  When evaluating the composed predicate, if this predicate is {@code true}, then
	 * the {@code other} predicate is not evaluated.
	 * <p>
	 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if
	 * evaluation of this predicate throws an exception, the {@code other} predicate will not be
	 * evaluated.
	 *
	 * @param other a predicate that will be logically-ORed with this predicate.
	 * @return a composed predicate that represents the short-circuiting logical OR of this
	 * 		predicate and the {@code other} predicate.
	 * @throws NullPointerException if the given {@code other} is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	default ShortPredicate or(ShortPredicate other) {
		return value -> this.test(value) || other.test(value);
	}

	/**
	 * Evaluates this predicate on the given argument.
	 *
	 * @param value the input argument.
	 * @return {@code true} if the input argument matches the predicate, otherwise {@code false}.
	 * @since 0.1.5 ~2020.08.30
	 */
	boolean test(short value);
}
