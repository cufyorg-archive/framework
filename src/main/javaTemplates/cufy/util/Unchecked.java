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

import cufy.util.function.*;

import java.util.function.*;

/**
 * A class holding custom functional interfaces' classes that could throw a must-catch throwables.
 * <p>
 * Note: this class chosen to be an interface to allow inheritance in the utility classes.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.30
 */
public interface Unchecked {
	//Misc

	/**
	 * A java syntax glitch to throw any throwable without the need to catch it.
	 *
	 * @param throwable to be ignite.
	 * @return nothing.
	 * @throws NullPointerException if the given throwable is null.
	 */
	static Error ignite(Throwable throwable) {
		return Unchecked.igniteAs(throwable);
	}

	/**
	 * A java syntax glitch to throw any throwable without the need to catch it.
	 *
	 * @param throwable to be ignite
	 * @param <T>       the type of the throwable to trick the compiler that it's the one thrown
	 * @return nothing.
	 * @throws T                    exactly the given throwable (unless if the given throwable is
	 *                              null. Then NullPointerException will be thrown).
	 * @throws NullPointerException if the given {@code throwable} is null.
	 */
	static <T extends Throwable> T igniteAs(Throwable throwable) throws T {
		Objects.requireNonNull(throwable, "throwable");
		throw (T) throwable;
	}

	//Object

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param consumer the lambda.
	 * @param <T>      the type of the first argument to the operation.
	 * @param <U>      the type of the second argument to the operation.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <T, U, E extends Throwable> UncheckedBiConsumer<T, U, E> biConsumer(UncheckedBiConsumer<T, U, E> consumer) {
		return Objects.requireNonNull(consumer, "consumer");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <T>      the type of the first argument to the function.
	 * @param <U>      the type of the second argument to the function.
	 * @param <R>      the type of the result of the function.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <T, U, R, E extends Throwable> UncheckedBiFunction<T, U, R, E> biFunction(UncheckedBiFunction<T, U, R, E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param operator the lambda.
	 * @param <T>      the type of the operands and result of the operator.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <T, E extends Throwable> UncheckedBinaryOperator<T, E> binaryOperator(UncheckedBinaryOperator<T, E> operator) {
		return Objects.requireNonNull(operator, "operator");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param predicate the lambda.
	 * @param <T>       the type of the first argument to the predicate.
	 * @param <U>       the type of the second argument the predicate.
	 * @param <E>       the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <T, U, E extends Throwable> UncheckedBiPredicate<T, U, E> biPredicate(UncheckedBiPredicate<T, U, E> predicate) {
		return Objects.requireNonNull(predicate, "predicate");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param consumer the lambda.
	 * @param <E>      the exception.
	 * @param <T>      the type of the input to the operation.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <T, E extends Throwable> UncheckedConsumer<T, E> consumer(UncheckedConsumer<T, E> consumer) {
		return Objects.requireNonNull(consumer, "consumer");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @param <T>      the type of the input to the function.
	 * @param <R>      the type of the result of the function.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <T, R, E extends Throwable> UncheckedFunction<T, R, E> function(UncheckedFunction<T, R, E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param predicate the lambda.
	 * @param <T>       the type of the first argument to the predicate.
	 * @param <E>       the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <T, E extends Throwable> UncheckedPredicate<T, E> predicate(UncheckedPredicate<T, E> predicate) {
		return Objects.requireNonNull(predicate, "predicate");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param runnable the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> UncheckedRunnable<E> runnable(UncheckedRunnable<E> runnable) {
		return Objects.requireNonNull(runnable, "runnable");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param supplier the lambda.
	 * @param <T>      the type of results supplied by this supplier.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <T, E extends Throwable> UncheckedSupplier<T, E> supplier(UncheckedSupplier<T, E> supplier) {
		return Objects.requireNonNull(supplier, "supplier");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param operator the lambda.
	 * @param <T>      the type of the operand and result of the operator.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <T, E extends Throwable> UncheckedUnaryOperator<T, E> unaryOperator(UncheckedUnaryOperator<T, E> operator) {
		return Objects.requireNonNull(operator, "operator");
	}
	/*
	with char|boolean|byte|double|float|int|long|short primitive
	*/

	//Char

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param consumer the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <E extends Throwable> UncheckedCharBiConsumer<E> charBiConsumer(UncheckedCharBiConsumer<E> consumer) {
		return Objects.requireNonNull(consumer, "consumer");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @param <R> the type of the result of the function.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <R, E extends Throwable> UncheckedCharBiFunction<R, E> charBiFunction(UncheckedCharBiFunction<R, E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param operator the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <E extends Throwable> UncheckedCharBinaryOperator<E> charBinaryOperator(UncheckedCharBinaryOperator<E> operator) {
		return Objects.requireNonNull(operator, "operator");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param predicate the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <E extends Throwable> UncheckedCharBiPredicate<E> charBiPredicate(UncheckedCharBiPredicate<E> predicate) {
		return Objects.requireNonNull(predicate, "predicate");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param consumer the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <E extends Throwable> UncheckedCharConsumer<E> charConsumer(UncheckedCharConsumer<E> consumer) {
		return Objects.requireNonNull(consumer, "consumer");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @param <R> the type of the result of the function.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <R, E extends Throwable> UncheckedCharFunction<R, E> charFunction(UncheckedCharFunction<R, E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @param <U> the type of the second argument to the operation.
	 * @param <R> the type of the result of the function.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <U, R, E extends Throwable> UncheckedCharObjBiFunction<U, R, E> charObjBiFunction(UncheckedCharObjBiFunction<U, R, E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param predicate the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <E extends Throwable> UncheckedCharPredicate<E> charPredicate(UncheckedCharPredicate<E> predicate) {
		return Objects.requireNonNull(predicate, "predicate");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param supplier the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <E extends Throwable> UncheckedCharSupplier<E> charSupplier(UncheckedCharSupplier<E> supplier) {
		return Objects.requireNonNull(supplier, "supplier");
	}
	/*
	if boolean|byte|char|float|int|long|short primitive
	*/

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <E extends Throwable> UncheckedCharToDoubleFunction<E> charToDoubleFunction(UncheckedCharToDoubleFunction<E> function) {
		return Objects.requireNonNull(function, "function");
	}
	/*
	endif
	*/
	/*
	if boolean|byte|char|double|float|long|short primitive
	*/

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <E extends Throwable> UncheckedCharToIntFunction<E> charToIntFunction(UncheckedCharToIntFunction<E> function) {
		return Objects.requireNonNull(function, "function");
	}
	/*
	endif
	*/
	/*
	if boolean|byte|char|double|float|int|short primitive
	*/

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <E extends Throwable> UncheckedCharToLongFunction<E> charToLongFunction(UncheckedCharToLongFunction<E> function) {
		return Objects.requireNonNull(function, "function");
	}
	/*
	endif
	*/

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param operator the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <E extends Throwable> UncheckedCharUnaryOperator<E> charUnaryOperator(UncheckedCharUnaryOperator<E> operator) {
		return Objects.requireNonNull(operator, "operator");
	}
	/*
	if boolean|byte|char|float|short primitive
	*/

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <E extends Throwable> UncheckedDoubleToCharFunction<E> doubleToCharFunction(UncheckedDoubleToCharFunction<E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <E extends Throwable> UncheckedIntToCharFunction<E> intToCharFunction(UncheckedIntToCharFunction<E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <E extends Throwable> UncheckedLongToCharFunction<E> longToCharFunction(UncheckedLongToCharFunction<E> function) {
		return Objects.requireNonNull(function, "function");
	}
	/*
	endif
	*/

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param consumer the lambda.
	 * @param <E>      the exception.
	 * @param <T> the type of the object argument to the operation.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <T, E extends Throwable> UncheckedObjCharConsumer<T, E> objCharConsumer(UncheckedObjCharConsumer<T, E> consumer) {
		return Objects.requireNonNull(consumer, "consumer");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @param <T> the type of the first argument to the function.
	 * @param <U> the type of the second argument to the function.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <T, U, E extends Throwable> UncheckedToCharBiFunction<T, U, E> toCharBiFunction(UncheckedToCharBiFunction<T, U, E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @param <T> the type of the first argument to the function.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.09.13
	 */
	static <T, E extends Throwable> UncheckedToCharFunction<T, E> toCharFunction(UncheckedToCharFunction<T, E> function) {
		return Objects.requireNonNull(function, "function");
	}
	/*
	endwith
	*/

	//Object Interfaces

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <T> the type of the first argument to the operation.
	 * @param <U> the type of the second argument to the operation.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.02.13
	 */
	@FunctionalInterface
	interface UncheckedBiConsumer<T, U, E extends Throwable> extends BiConsumer<T, U> {
		@Override
		default void accept(T t, U u) {
			try {
				this.accept0(t, u);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Performs this operation on the given arguments.
		 *
		 * @param t the first input argument.
		 * @param u the second input argument.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.02.13
		 */
		void accept0(T t, U u) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <T> the type of the first argument to the function.
	 * @param <U> the type of the second argument to the function.
	 * @param <R> the type of the result of the function.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.02.13
	 */
	@FunctionalInterface
	interface UncheckedBiFunction<T, U, R, E extends Throwable> extends BiFunction<T, U, R> {
		@Override
		default R apply(T t, U u) {
			try {
				return this.apply0(t, u);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Applies this function to the given arguments.
		 *
		 * @param t the first function argument.
		 * @param u the second function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.02.13
		 */
		R apply0(T t, U u) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <T> the type of the operands and result of the operator.
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.02.13
	 */
	@FunctionalInterface
	interface UncheckedBinaryOperator<T, E extends Throwable> extends BinaryOperator<T> {
		@Override
		default T apply(T t, T t2) {
			try {
				return this.apply0(t, t2);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Applies this function to the given arguments.
		 *
		 * @param t  the first function argument.
		 * @param t2 the second function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.02.13
		 */
		T apply0(T t, T t2) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <T> the type of the first argument to the predicate.
	 * @param <U> the type of the second argument the predicate.
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.02.13
	 */
	@FunctionalInterface
	interface UncheckedBiPredicate<T, U, E extends Throwable> extends BiPredicate<T, U> {
		@Override
		default boolean test(T t, U u) {
			try {
				return this.test0(t, u);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Evaluates this predicate on the given arguments.
		 *
		 * @param t the first input argument.
		 * @param u the second input argument.
		 * @return true, if the input arguments match the predicate, otherwise false.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.02.13
		 */
		boolean test0(T t, U u) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <T> the type of the input to the operation.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.02.13
	 */
	@FunctionalInterface
	interface UncheckedConsumer<T, E extends Throwable> extends Consumer<T> {
		@Override
		default void accept(T t) {
			try {
				this.accept0(t);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Performs this operation on the given argument.
		 *
		 * @param t the input argument.
		 * @throws E the exception that this runnable throws.
		 * @since 0.1.0 ~2020.02.13
		 */
		void accept0(T t) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <T> the type of the input to the function.
	 * @param <R> the type of the result of the function.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.02.13
	 */
	@FunctionalInterface
	interface UncheckedFunction<T, R, E extends Throwable> extends Function<T, R> {
		@Override
		default R apply(T t) {
			try {
				return this.apply0(t);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param t the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.02.13
		 */
		R apply0(T t) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <T> the type of the first argument to the predicate.
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.08.30
	 */
	@FunctionalInterface
	interface UncheckedPredicate<T, E extends Throwable> extends Predicate<T> {
		@Override
		default boolean test(T t) {
			try {
				return this.test0(t);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Evaluates this predicate on the given argument.
		 *
		 * @param t the input argument
		 * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		boolean test0(T t) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.02.13
	 */
	@FunctionalInterface
	interface UncheckedRunnable<E extends Throwable> extends Runnable {
		@Override
		default void run() {
			try {
				this.run0();
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * When an object implementing interface Runnable is used to create a thread, starting the
		 * thread causes the object's run method to be called in that separately executing thread.
		 * The general contract of the method run is that it may take any action whatsoever.
		 *
		 * @throws E the exception that this runnable throws.
		 * @since 0.1.0 ~2020.02.13
		 */
		void run0() throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <T> the type of results supplied by this supplier.
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.02.13
	 */
	@FunctionalInterface
	interface UncheckedSupplier<T, E extends Throwable> extends Supplier<T> {
		@Override
		default T get() {
			try {
				return this.get0();
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Gets a result.
		 *
		 * @return a result.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.02.13
		 */
		T get0() throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <T> the type of the operand and result of the operator.
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.02.13
	 */
	@FunctionalInterface
	interface UncheckedUnaryOperator<T, E extends Throwable> extends UnaryOperator<T> {
		@Override
		default T apply(T t) {
			try {
				return this.apply0(t);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param t the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.02.13
		 */
		T apply0(T t) throws E;
	}
	/*
	with char|boolean|byte|double|float|int|long|short primitive
	*/

	//Char Interfaces

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedCharBiConsumer<E extends Throwable> extends CharBiConsumer {
		@Override
		default void accept(char value, char other) {
			try {
				this.accept0(value, other);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Performs this operation on the given arguments.
		 *
		 * @param value the first input argument.
		 * @param other the second input argument.
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.09.13
		 */
		void accept0(char value, char other) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <R> the type of the result of the function.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedCharBiFunction<R, E extends Throwable> extends CharBiFunction<R> {
		@Override
		default R apply(char value, char other) {
			try {
				return this.apply0(value, other);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Applies this function to the given arguments.
		 *
		 * @param value the first function argument.
		 * @param other the second function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.09.13
		 */
		R apply0(char value, char other) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedCharBinaryOperator<E extends Throwable> extends CharBinaryOperator {
		@Override
		default char applyAsChar(char left, char right) {
			try {
				return this.applyAsChar0(left, right);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Applies this function to the given arguments.
		 *
		 * @param left the first function argument.
		 * @param right the second function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.09.13
		 */
		char applyAsChar0(char left, char right) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedCharBiPredicate<E extends Throwable> extends CharBiPredicate {
		@Override
		default boolean test(char value, char other) {
			try {
				return this.test0(value, other);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Evaluates this predicate on the given arguments.
		 *
		 * @param value the first input argument.
		 * @param other the second input argument.
		 * @return {@code true} if the input arguments match the predicate, otherwise {@code false}.
		 * @since 0.1.5 ~2020.09.03
		 */
		boolean test0(char value, char other);
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedCharConsumer<E extends Throwable> extends CharConsumer {
		@Override
		default void accept(char value) {
			try {
				this.accept0(value);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Performs this operation on the given argument.
		 *
		 * @param value the input argument.
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		void accept0(char value) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <R> the type of the result of the function.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedCharFunction<R, E extends Throwable> extends CharFunction<R> {
		@Override
		default R apply(char value) {
			try {
				return this.apply0(value);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param value the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		R apply0(char value) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <U> the type of the second argument to the operation.
	 * @param <R> the type of the result of the function.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedCharObjBiFunction<U, R, E extends Throwable> extends CharObjBiFunction<U, R> {
		@Override
		default R apply(char value, U other) {
			try {
				return this.apply0(value, other);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Applies this function to the given arguments.
		 *
		 * @param value the first function argument.
		 * @param other the second function argument.
		 * @throws E the exception.
		 * @return the function result.
		 * @since 0.1.5 ~2020.09.03
		 */
		R apply0(char value, U other) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedCharPredicate<E extends Throwable> extends CharPredicate {
		@Override
		default boolean test(char value) {
			try {
				return this.test0(value);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Evaluates this predicate on the given argument.
		 *
		 * @param value the input argument.
		 * @return {@code true} if the input argument matches the predicate, otherwise {@code false}.
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		boolean test0(char value) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedCharSupplier<E extends Throwable> extends CharSupplier {
		@Override
		default char getAsChar() {
			try {
				return this.getAsChar0();
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Gets a result.
		 *
		 * @return a result.
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		char getAsChar0() throws E;
	}
	/*
	if boolean|byte|char|float|int|long|short primitive
	*/

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedCharToDoubleFunction<E extends Throwable> extends CharToDoubleFunction {
		@Override
		default double applyAsDouble(char value) {
			try {
				return this.applyAsDouble0(value);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param value the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		double applyAsDouble0(char value) throws E;
	}
	/*
	endif
	*/
	/*
	if boolean|byte|char|double|float|long|short primitive
	*/

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedCharToIntFunction<E extends Throwable> extends CharToIntFunction {
		@Override
		default int applyAsInt(char value) {
			try {
				return this.applyAsInt0(value);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param value the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		int applyAsInt0(char value) throws E;
	}
	/*
	endif
	*/
	/*
	if boolean|byte|char|double|float|int|short primitive
	*/

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedCharToLongFunction<E extends Throwable> extends CharToLongFunction {
		@Override
		default long applyAsLong(char value) {
			try {
				return this.applyAsLong0(value);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param value the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		long applyAsLong0(char value) throws E;
	}
	/*
	endif
	*/

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedCharUnaryOperator<E extends Throwable> extends CharUnaryOperator {
		@Override
		default char applyAsChar(char value) {
			try {
				return this.applyAsChar0(value);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Applies this operator to the given operand.
		 *
		 * @param value the operand.
		 * @return the operator result.
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		char applyAsChar0(char value) throws E;
	}
	/*
	if boolean|byte|char|float|short primitive
	*/

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedDoubleToCharFunction<E extends Throwable> extends DoubleToCharFunction {
		@Override
		default char applyAsChar(double value) {
			try {
				return this.applyAsChar0(value);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param value the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		char applyAsChar0(double value) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedIntToCharFunction<E extends Throwable> extends IntToCharFunction {
		@Override
		default char applyAsChar(int value) {
			try {
				return this.applyAsChar0(value);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param value the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		char applyAsChar0(int value) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedLongToCharFunction<E extends Throwable> extends LongToCharFunction {
		@Override
		default char applyAsChar(long value) {
			try {
				return this.applyAsChar0(value);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param value the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		char applyAsChar0(long value) throws E;
	}
	/*
	endif
	*/

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <T> the type of the object argument to the operation.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedObjCharConsumer<T, E extends Throwable> extends ObjCharConsumer<T> {
		@Override
		default void accept(T t, char value) {
			try {
				this.accept0(t, value);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Performs this operation on the given arguments.
		 *
		 * @param t     the first input argument.
		 * @param value the second input argument.
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		void accept0(T t, char value) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <T> the type of the first argument to the function.
	 * @param <U> the type of the second argument to the function.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedToCharBiFunction<T, U, E extends Throwable> extends ToCharBiFunction<T, U> {
		@Override
		default char applyAsChar(T t, U u) {
			try {
				return this.applyAsChar0(t, u);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Applies this function to the given arguments.
		 *
		 * @param t the first function argument.
		 * @param u the second function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		char applyAsChar0(T t, U u) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <T> the type of the input to the function.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.09.13
	 */
	@FunctionalInterface
	interface UncheckedToCharFunction<T, E extends Throwable> extends ToCharFunction<T> {
		@Override
		default char applyAsChar(T value) {
			try {
				return this.applyAsChar0(value);
			} catch (Throwable e) {
				throw Unchecked.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param value the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		char applyAsChar0(T value) throws E;
	}
	/*
	endwith
	*/
}
