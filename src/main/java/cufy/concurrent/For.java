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
package cufy.concurrent;

import java.util.Objects;

/**
 * A {@link Loop} version of the typical 'for' statement.
 *
 * @param <I> The type of the first initialized variable
 * @author lsafer
 * @version 0.1.5
 * @since 07-Dec-2019
 */
public class For<I> extends Loop<For.Code<I>> {
	/**
	 * The function to be applied before each round on the loop to check whether the loop shall continue or break.
	 */
	protected final Condition<I> condition;
	/**
	 * A function to be applied after each round on the loop. The function focus on what to do on {@link #variable}.
	 */
	protected final Increment<I> increment;
	/**
	 * The variable first initialized.
	 */
	protected I variable;

	/**
	 * Construct a new for loop with the given arguments.
	 *
	 * @param variable  initial variable
	 * @param condition looping condition
	 * @param increment action to be applied to the variable on each round
	 * @throws NullPointerException if ether the given 'condition' or 'increment' is null
	 */
	public For(I variable, Condition<I> condition, Increment<I> increment) {
		Objects.requireNonNull(condition, "condition");
		Objects.requireNonNull(increment, "reducer");
		this.variable = variable;
		this.condition = condition;
		this.increment = increment;
	}

	/**
	 * Construct a new for loop with the given arguments.
	 *
	 * @param variable  initial variable
	 * @param condition looping condition
	 * @param increment action to be applied to the variable on each round
	 * @param code      first looping code
	 * @throws NullPointerException if one of the given 'condition' or 'reducer' or 'code' is null
	 */
	public For(I variable, Condition<I> condition, Increment<I> increment, Code<I> code) {
		Objects.requireNonNull(condition, "condition");
		Objects.requireNonNull(increment, "reducer");
		Objects.requireNonNull(code, "code");
		this.append(code);
		this.variable = variable;
		this.condition = condition;
		this.increment = increment;
	}

	@Override
	protected void loop() {
		while (this.condition.check(this.variable) && this.next(this.variable))
			this.variable = this.increment.increment(this.variable);
	}

	/**
	 * A loop-code for {@code For} loops.
	 *
	 * @param <I> the type of iterating items
	 */
	@FunctionalInterface
	public interface Code<I> extends Loop.Code<For> {
		@Override
		default void run(For loop, Object item) {
			this.onRun(loop, (I) item);
		}

		/**
		 * Perform this {@code For} loop-code with the given item. Get called when a {@code For} loop is executing its code and this code is added to
		 * its code.
		 *
		 * @param loop the loop that executed this code
		 * @param item the current item in the for-iteration
		 * @throws NullPointerException if the given 'loop' is null
		 */
		void onRun(For<Code<I>> loop, I item);
	}

	/**
	 * A condition check for {@code For} loops.
	 *
	 * @param <I> the type of the iterating items
	 */
	@FunctionalInterface
	public interface Condition<I> {
		/**
		 * Check if the loop should continue iterating or not.
		 *
		 * @param item the current iterating item
		 * @return true, to not stop the loop
		 */
		boolean check(I item);
	}

	/**
	 * The code that changes the iterating item for {@code For} loops.
	 *
	 * @param <I> the type of the iterating items
	 */
	@FunctionalInterface
	public interface Increment<I> {
		/**
		 * Increment the given item to be replaced in the {@code For} loop.
		 *
		 * @param item the current item
		 * @return the next item
		 */
		I increment(I item);
	}
}
