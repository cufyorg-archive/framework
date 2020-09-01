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

import java.util.function.*;

/**
 * A class holding custom functional interfaces' classes that could throw a must-catch throwables.
 * <p>
 * Note: this class chosen to be an interface to allow inheritance in the utility classes.
 * <p>
 * TODO: include the package {@code cufy.util.function}.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.30
 */
@Deprecated
public interface Throwing {
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
	static <T, U, E extends Throwable> ThrowingBiConsumer<T, U, E> biConsumer(ThrowingBiConsumer<T, U, E> consumer) {
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
	static <T, U, R, E extends Throwable> ThrowingBiFunction<T, U, R, E> biFunction(ThrowingBiFunction<T, U, R, E> function) {
		return Objects.requireNonNull(function, "function");
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
	static <T, U, E extends Throwable> ThrowingBiPredicate<T, U, E> biPredicate(ThrowingBiPredicate<T, U, E> predicate) {
		return Objects.requireNonNull(predicate, "predicate");
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
	static <T, E extends Throwable> ThrowingBinaryOperator<T, E> binaryOperator(ThrowingBinaryOperator<T, E> operator) {
		return Objects.requireNonNull(operator, "operator");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param supplier the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingBooleanSupplier<E> booleanSupplier(ThrowingBooleanSupplier<E> supplier) {
		return Objects.requireNonNull(supplier, "supplier");
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
	static <T, E extends Throwable> ThrowingConsumer<T, E> consumer(ThrowingConsumer<T, E> consumer) {
		return Objects.requireNonNull(consumer, "consumer");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param operator the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingDoubleBinaryOperator<E> doubleBinaryOperator(ThrowingDoubleBinaryOperator<E> operator) {
		return Objects.requireNonNull(operator, "operator");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param consumer the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingDoubleConsumer<E> doubleConsumer(ThrowingDoubleConsumer<E> consumer) {
		return Objects.requireNonNull(consumer, "consumer");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @param <R>      the type of the result of the function.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <R, E extends Throwable> ThrowingDoubleFunction<R, E> doubleFunction(ThrowingDoubleFunction<R, E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param predicate the lambda.
	 * @param <E>       the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingDoublePredicate<E> doublePredicate(ThrowingDoublePredicate<E> predicate) {
		return Objects.requireNonNull(predicate, "predicate");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param supplier the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingDoubleSupplier<E> doubleSupplier(ThrowingDoubleSupplier<E> supplier) {
		return Objects.requireNonNull(supplier, "supplier");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingDoubleToIntFunction<E> doubleToIntFunction(ThrowingDoubleToIntFunction<E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingDoubleToLongFunction<E> doubleToLongFunction(ThrowingDoubleToLongFunction<E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param operator the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingDoubleUnaryOperator<E> doubleUnaryOperator(ThrowingDoubleUnaryOperator<E> operator) {
		return Objects.requireNonNull(operator, "operator");
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
	static <T, R, E extends Throwable> ThrowingFunction<T, R, E> function(ThrowingFunction<T, R, E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param operator the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingIntBinaryOperator<E> intBinaryOperator(ThrowingIntBinaryOperator<E> operator) {
		return Objects.requireNonNull(operator, "operator");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param consumer the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingIntConsumer<E> intConsumer(ThrowingIntConsumer<E> consumer) {
		return Objects.requireNonNull(consumer, "consumer");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @param <R>      the type of the result of the function.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <R, E extends Throwable> ThrowingIntFunction<R, E> intFunction(ThrowingIntFunction<R, E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param predicate the lambda.
	 * @param <E>       the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingIntPredicate<E> intPredicate(ThrowingIntPredicate<E> predicate) {
		return Objects.requireNonNull(predicate, "predicate");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param supplier the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingIntSupplier<E> intSupplier(ThrowingIntSupplier<E> supplier) {
		return Objects.requireNonNull(supplier, "supplier");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingIntToDoubleFunction<E> intToDoubleFunction(ThrowingIntToDoubleFunction<E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingIntToLongFunction<E> intToLongFunction(ThrowingIntToLongFunction<E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param operator the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingIntUnaryOperator<E> intUnaryOperator(ThrowingIntUnaryOperator<E> operator) {
		return Objects.requireNonNull(operator, "operator");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param operator the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingLongBinaryOperator<E> longBinaryOperator(ThrowingLongBinaryOperator<E> operator) {
		return Objects.requireNonNull(operator, "operator");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param consumer the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingLongConsumer<E> longConsumer(ThrowingLongConsumer<E> consumer) {
		return Objects.requireNonNull(consumer, "consumer");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @param <R>      the type of the result of the function.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <R, E extends Throwable> ThrowingLongFunction<R, E> longFunction(ThrowingLongFunction<R, E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param predicate the lambda.
	 * @param <E>       the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingLongPredicate<E> longPredicate(ThrowingLongPredicate<E> predicate) {
		return Objects.requireNonNull(predicate, "predicate");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param supplier the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingLongSupplier<E> longSupplier(ThrowingLongSupplier<E> supplier) {
		return Objects.requireNonNull(supplier, "supplier");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingLongToDoubleFunction<E> longToDoubleFunction(ThrowingLongToDoubleFunction<E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingLongToIntFunction<E> longToIntFunction(ThrowingLongToIntFunction<E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param operator the lambda.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <E extends Throwable> ThrowingLongUnaryOperator<E> longUnaryOperator(ThrowingLongUnaryOperator<E> operator) {
		return Objects.requireNonNull(operator, "operator");
	}

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
	static <T, U, E extends Throwable> ThrowingObjDoubleConsumer<T, E> objDoubleConsumer(ThrowingObjDoubleConsumer<T, E> consumer) {
		return Objects.requireNonNull(consumer, "consumer");
	}

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
	static <T, U, E extends Throwable> ThrowingObjIntConsumer<T, E> objIntConsumer(ThrowingObjIntConsumer<T, E> consumer) {
		return Objects.requireNonNull(consumer, "consumer");
	}

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
	static <T, U, E extends Throwable> ThrowingObjLongConsumer<T, E> objLongConsumer(ThrowingObjLongConsumer<T, E> consumer) {
		return Objects.requireNonNull(consumer, "consumer");
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
	static <T, E extends Throwable> ThrowingPredicate<T, E> predicate(ThrowingPredicate<T, E> predicate) {
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
	static <E extends Throwable> ThrowingRunnable<E> runnable(ThrowingRunnable<E> runnable) {
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
	static <T, E extends Throwable> ThrowingSupplier<T, E> supplier(ThrowingSupplier<T, E> supplier) {
		return Objects.requireNonNull(supplier, "supplier");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <T>      the type of the first argument to the function.
	 * @param <U>      the type of the second argument to the function.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <T, U, E extends Throwable> ThrowingToDoubleBiFunction<T, U, E> toDoubleBiFunction(ThrowingToDoubleBiFunction<T, U, E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @param <T>      the type of the input to the function.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <T, E extends Throwable> ThrowingToDoubleFunction<T, E> toDoubleFunction(ThrowingToDoubleFunction<T, E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <T>      the type of the first argument to the function.
	 * @param <U>      the type of the second argument to the function.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <T, U, E extends Throwable> ThrowingToIntBiFunction<T, U, E> toIntBiFunction(ThrowingToIntBiFunction<T, U, E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @param <T>      the type of the input to the function.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <T, E extends Throwable> ThrowingToIntFunction<T, E> toIntFunction(ThrowingToIntFunction<T, E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <T>      the type of the first argument to the function.
	 * @param <U>      the type of the second argument to the function.
	 * @param <E>      the exception.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <T, U, E extends Throwable> ThrowingToLongBiFunction<T, U, E> toLongBiFunction(ThrowingToLongBiFunction<T, U, E> function) {
		return Objects.requireNonNull(function, "function");
	}

	/**
	 * A helper method to instantiate a new throw lambda.
	 *
	 * @param function the lambda.
	 * @param <E>      the exception.
	 * @param <T>      the type of the input to the function.
	 * @return the given lambda.
	 * @throws NullPointerException if the given lambda is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <T, E extends Throwable> ThrowingToLongFunction<T, E> toLongFunction(ThrowingToLongFunction<T, E> function) {
		return Objects.requireNonNull(function, "function");
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
	static <T, E extends Throwable> ThrowUnaryOperator<T, E> unaryOperator(ThrowUnaryOperator<T, E> operator) {
		return Objects.requireNonNull(operator, "operator");
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
	interface ThrowUnaryOperator<T, E extends Throwable> extends UnaryOperator<T> {
		@Override
		default T apply(T t) {
			try {
				return this.throwingApply(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		T throwingApply(T t) throws E;
	}

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
	interface ThrowingBiConsumer<T, U, E extends Throwable> extends BiConsumer<T, U> {
		@Override
		default void accept(T t, U u) {
			try {
				this.throwingAccept(t, u);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		void throwingAccept(T t, U u) throws E;
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
	interface ThrowingBiFunction<T, U, R, E extends Throwable> extends BiFunction<T, U, R> {
		@Override
		default R apply(T t, U u) {
			try {
				return this.throwingApply(t, u);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		R throwingApply(T t, U u) throws E;
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
	interface ThrowingBiPredicate<T, U, E extends Throwable> extends BiPredicate<T, U> {
		@Override
		default boolean test(T t, U u) {
			try {
				return this.throwingTest(t, u);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		boolean throwingTest(T t, U u) throws E;
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
	interface ThrowingBinaryOperator<T, E extends Throwable> extends BinaryOperator<T> {
		@Override
		default T apply(T t, T t2) {
			try {
				return this.throwingApply(t, t2);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		T throwingApply(T t, T t2) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingBooleanSupplier<E extends Throwable> extends BooleanSupplier {
		@Override
		default boolean getAsBoolean() {
			try {
				return this.throwingGetAsBoolean();
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Gets a result.
		 *
		 * @return a result.
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		boolean throwingGetAsBoolean() throws E;
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
	interface ThrowingConsumer<T, E extends Throwable> extends Consumer<T> {
		@Override
		default void accept(T t) {
			try {
				this.throwingAccept(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Performs this operation on the given argument.
		 *
		 * @param t the input argument.
		 * @throws E the exception that this runnable throws.
		 * @since 0.1.0 ~2020.02.13
		 */
		void throwingAccept(T t) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingDoubleBinaryOperator<E extends Throwable> extends DoubleBinaryOperator {
		@Override
		default double applyAsDouble(double left, double right) {
			try {
				return this.throwingApplyAsDouble(left, right);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Applies this operator to the given operands.
		 *
		 * @param left  the first operand
		 * @param right the second operand
		 * @return the operator result
		 * @throws E the exception
		 * @since 0.1.5 ~2020.08.30
		 */
		double throwingApplyAsDouble(double left, double right) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingDoubleConsumer<E extends Throwable> extends DoubleConsumer {
		@Override
		default void accept(double value) {
			try {
				this.throwingAccept(value);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Performs this operation on the given argument.
		 *
		 * @param value the input argument
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		void throwingAccept(double value) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <R> the type of the result of the function.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingDoubleFunction<R, E extends Throwable> extends DoubleFunction<R> {
		@Override
		default R apply(double t) {
			try {
				return this.throwingApply(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param t the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.08.30
		 */
		R throwingApply(double t) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingDoublePredicate<E extends Throwable> extends DoublePredicate {
		@Override
		default boolean test(double t) {
			try {
				return this.throwingTest(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		boolean throwingTest(double t) throws E;
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
	interface ThrowingDoubleSupplier<E extends Throwable> extends DoubleSupplier {
		@Override
		default double getAsDouble() {
			try {
				return this.throwingGetAsDouble();
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Gets a result.
		 *
		 * @return a result.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.02.13
		 */
		double throwingGetAsDouble() throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingDoubleToIntFunction<E extends Throwable> extends DoubleToIntFunction {
		@Override
		default int applyAsInt(double t) {
			try {
				return this.throwingApplyAsInt(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param t the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.08.30
		 */
		int throwingApplyAsInt(double t) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingDoubleToLongFunction<E extends Throwable> extends DoubleToLongFunction {
		@Override
		default long applyAsLong(double t) {
			try {
				return this.throwingApplyAsLong(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param t the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.08.30
		 */
		long throwingApplyAsLong(double t) throws E;
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
	interface ThrowingDoubleUnaryOperator<E extends Throwable> extends DoubleUnaryOperator {
		@Override
		default double applyAsDouble(double t) {
			try {
				return this.throwingApplyAsDouble(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		double throwingApplyAsDouble(double t) throws E;
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
	interface ThrowingFunction<T, R, E extends Throwable> extends Function<T, R> {
		@Override
		default R apply(T t) {
			try {
				return this.throwingApply(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		R throwingApply(T t) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingIntBinaryOperator<E extends Throwable> extends IntBinaryOperator {
		@Override
		default int applyAsInt(int left, int right) {
			try {
				return this.throwingApplyAsInt(left, right);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Applies this operator to the given operands.
		 *
		 * @param left  the first operand
		 * @param right the second operand
		 * @return the operator result
		 * @throws E the exception
		 * @since 0.1.5 ~2020.08.30
		 */
		int throwingApplyAsInt(int left, int right) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingIntConsumer<E extends Throwable> extends IntConsumer {
		@Override
		default void accept(int value) {
			try {
				this.throwingAccept(value);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Performs this operation on the given argument.
		 *
		 * @param value the input argument
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		void throwingAccept(int value) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <R> the type of the result of the function.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingIntFunction<R, E extends Throwable> extends IntFunction<R> {
		@Override
		default R apply(int t) {
			try {
				return this.throwingApply(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param t the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.08.30
		 */
		R throwingApply(int t) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingIntPredicate<E extends Throwable> extends IntPredicate {
		@Override
		default boolean test(int t) {
			try {
				return this.throwingTest(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		boolean throwingTest(int t) throws E;
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
	interface ThrowingIntSupplier<E extends Throwable> extends IntSupplier {
		@Override
		default int getAsInt() {
			try {
				return this.throwingGetAsInt();
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Gets a result.
		 *
		 * @return a result.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.02.13
		 */
		int throwingGetAsInt() throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingIntToDoubleFunction<E extends Throwable> extends IntToDoubleFunction {
		@Override
		default double applyAsDouble(int t) {
			try {
				return this.throwingApplyAsDouble(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param t the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.08.30
		 */
		double throwingApplyAsDouble(int t) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingIntToLongFunction<E extends Throwable> extends IntToLongFunction {
		@Override
		default long applyAsLong(int t) {
			try {
				return this.throwingApplyAsLong(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param t the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.08.30
		 */
		long throwingApplyAsLong(int t) throws E;
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
	interface ThrowingIntUnaryOperator<E extends Throwable> extends IntUnaryOperator {
		@Override
		default int applyAsInt(int t) {
			try {
				return this.throwingApplyAsInt(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		int throwingApplyAsInt(int t) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingLongBinaryOperator<E extends Throwable> extends LongBinaryOperator {
		@Override
		default long applyAsLong(long left, long right) {
			try {
				return this.throwingApplyAsLong(left, right);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Applies this operator to the given operands.
		 *
		 * @param left  the first operand
		 * @param right the second operand
		 * @return the operator result
		 * @throws E the exception
		 * @since 0.1.5 ~2020.08.30
		 */
		long throwingApplyAsLong(long left, long right) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingLongConsumer<E extends Throwable> extends LongConsumer {
		@Override
		default void accept(long value) {
			try {
				this.throwingAccept(value);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Performs this operation on the given argument.
		 *
		 * @param value the input argument
		 * @throws E the exception.
		 * @since 0.1.5 ~2020.08.30
		 */
		void throwingAccept(long value) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <R> the type of the result of the function.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingLongFunction<R, E extends Throwable> extends LongFunction<R> {
		@Override
		default R apply(long t) {
			try {
				return this.throwingApply(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param t the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.08.30
		 */
		R throwingApply(long t) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingLongPredicate<E extends Throwable> extends LongPredicate {
		@Override
		default boolean test(long t) {
			try {
				return this.throwingTest(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		boolean throwingTest(long t) throws E;
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
	interface ThrowingLongSupplier<E extends Throwable> extends LongSupplier {
		@Override
		default long getAsLong() {
			try {
				return this.throwingGetAsLong();
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Gets a result.
		 *
		 * @return a result.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.02.13
		 */
		long throwingGetAsLong() throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingLongToDoubleFunction<E extends Throwable> extends LongToDoubleFunction {
		@Override
		default double applyAsDouble(long t) {
			try {
				return this.throwingApplyAsDouble(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param t the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.08.30
		 */
		double throwingApplyAsDouble(long t) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.08.30
	 */
	@FunctionalInterface
	interface ThrowingLongToIntFunction<E extends Throwable> extends LongToIntFunction {
		@Override
		default int applyAsInt(long t) {
			try {
				return this.throwingApplyAsInt(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Applies this function to the given argument.
		 *
		 * @param t the function argument.
		 * @return the function result.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.08.30
		 */
		int throwingApplyAsInt(long t) throws E;
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
	interface ThrowingLongUnaryOperator<E extends Throwable> extends LongUnaryOperator {
		@Override
		default long applyAsLong(long t) {
			try {
				return this.throwingApplyAsLong(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		long throwingApplyAsLong(long t) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <T> the type of the object argument to the operation
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.02.13
	 */
	@FunctionalInterface
	interface ThrowingObjDoubleConsumer<T, E extends Throwable> extends ObjDoubleConsumer<T> {
		@Override
		default void accept(T t, double u) {
			try {
				this.throwingAccept(t, u);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		void throwingAccept(T t, double u) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <T> the type of the object argument to the operation
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.02.13
	 */
	@FunctionalInterface
	interface ThrowingObjIntConsumer<T, E extends Throwable> extends ObjIntConsumer<T> {
		@Override
		default void accept(T t, int u) {
			try {
				this.throwingAccept(t, u);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		void throwingAccept(T t, int u) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <T> the type of the object argument to the operation
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.02.13
	 */
	@FunctionalInterface
	interface ThrowingObjLongConsumer<T, E extends Throwable> extends ObjLongConsumer<T> {
		@Override
		default void accept(T t, long u) {
			try {
				this.throwingAccept(t, u);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		void throwingAccept(T t, long u) throws E;
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
	interface ThrowingPredicate<T, E extends Throwable> extends Predicate<T> {
		@Override
		default boolean test(T t) {
			try {
				return this.throwingTest(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		boolean throwingTest(T t) throws E;
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
	interface ThrowingRunnable<E extends Throwable> extends Runnable {
		@Override
		default void run() {
			try {
				this.throwingRun();
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		void throwingRun() throws E;
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
	interface ThrowingSupplier<T, E extends Throwable> extends Supplier<T> {
		@Override
		default T get() {
			try {
				return this.throwingGet();
			} catch (Throwable e) {
				throw Reflection.ignite(e);
			}
		}

		/**
		 * Gets a result.
		 *
		 * @return a result.
		 * @throws E the exception.
		 * @since 0.1.0 ~2020.02.13
		 */
		T throwingGet() throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <T> the type of the first argument to the function.
	 * @param <U> the type of the second argument to the function.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.02.13
	 */
	@FunctionalInterface
	interface ThrowingToDoubleBiFunction<T, U, E extends Throwable> extends ToDoubleBiFunction<T, U> {
		@Override
		default double applyAsDouble(T t, U u) {
			try {
				return this.throwingApplyAsDouble(t, u);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		double throwingApplyAsDouble(T t, U u) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <T> the type of the input to the function.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.02.13
	 */
	@FunctionalInterface
	interface ThrowingToDoubleFunction<T, E extends Throwable> extends ToDoubleFunction<T> {
		@Override
		default double applyAsDouble(T t) {
			try {
				return this.throwingApplyAsDouble(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		double throwingApplyAsDouble(T t) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <T> the type of the first argument to the function.
	 * @param <U> the type of the second argument to the function.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.02.13
	 */
	@FunctionalInterface
	interface ThrowingToIntBiFunction<T, U, E extends Throwable> extends ToIntBiFunction<T, U> {
		@Override
		default int applyAsInt(T t, U u) {
			try {
				return this.throwingApplyAsInt(t, u);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		int throwingApplyAsInt(T t, U u) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <T> the type of the input to the function.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.02.13
	 */
	@FunctionalInterface
	interface ThrowingToIntFunction<T, E extends Throwable> extends ToIntFunction<T> {
		@Override
		default int applyAsInt(T t) {
			try {
				return this.throwingApplyAsInt(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		int throwingApplyAsInt(T t) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <T> the type of the first argument to the function.
	 * @param <U> the type of the second argument to the function.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.02.13
	 */
	@FunctionalInterface
	interface ThrowingToLongBiFunction<T, U, E extends Throwable> extends ToLongBiFunction<T, U> {
		@Override
		default long applyAsLong(T t, U u) {
			try {
				return this.throwingApplyAsLong(t, u);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		long throwingApplyAsLong(T t, U u) throws E;
	}

	/**
	 * Functional Interface that can be specified to throw an exception.
	 *
	 * @param <E> the exception.
	 * @param <T> the type of the input to the function.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.0 ~2020.02.13
	 */
	@FunctionalInterface
	interface ThrowingToLongFunction<T, E extends Throwable> extends ToLongFunction<T> {
		@Override
		default long applyAsLong(T t) {
			try {
				return this.throwingApplyAsLong(t);
			} catch (Throwable e) {
				throw Reflection.ignite(e);
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
		long throwingApplyAsLong(T t) throws E;
	}
}
