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
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * A {@link Loop} version of the typical 'for' statement.
 *
 * @param <I> The type of the first initialized variable
 * @author lsafer
 * @version 0.1.2
 * @since 07-Dec-2019
 */
public class For<I> extends Loop<BiConsumer<For<I>, I>, I> {
	/**
	 * The function to be applied before each round on the loop to check whether the loop shall continue or break.
	 */
	final protected Function<I, Boolean> condition;
	/**
	 * A function to be applied after each round on the loop. The function focus on what to do on {@link #variable}.
	 */
	final protected Function<I, I> reducer;
	/**
	 * The variable first initialized.
	 */
	protected I variable;

	/**
	 * Construct a new for loop with the given arguments.
	 *
	 * @param variable  initial variable
	 * @param condition looping condition
	 * @param reducer   action to be applied to the variable on each round
	 * @throws NullPointerException if ether the given 'condition' or 'reducer' is null
	 */
	public For(I variable, Function<I, Boolean> condition, Function<I, I> reducer) {
		Objects.requireNonNull(condition, "condition");
		Objects.requireNonNull(reducer, "reducer");
		this.variable = variable;
		this.condition = condition;
		this.reducer = reducer;
	}

	/**
	 * Construct a new for loop with the given arguments.
	 *
	 * @param variable  initial variable
	 * @param condition looping condition
	 * @param reducer   action to be applied to the variable on each round
	 * @param code      first looping code
	 * @throws NullPointerException if one of the given 'condition' or 'reducer' or 'code' is null
	 */
	public For(I variable, Function<I, Boolean> condition, Function<I, I> reducer, BiConsumer<For<I>, I> code) {
		Objects.requireNonNull(condition, "condition");
		Objects.requireNonNull(reducer, "reducer");
		Objects.requireNonNull(code, "code");
		this.append(code);
		this.variable = variable;
		this.condition = condition;
		this.reducer = reducer;
	}

	@Override
	public For<I> append(BiConsumer<For<I>, I> code) {
		Objects.requireNonNull(code, "code");
		return (For<I>) this.append0(param -> code.accept(this, param));
	}

	@Override
	protected void loop() {
		while (this.condition.apply(this.variable) && this.next(this.variable))
			this.variable = this.reducer.apply(this.variable);
	}
}
