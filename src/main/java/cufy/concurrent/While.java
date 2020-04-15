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
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A {@link Loop} version of the typical 'while' statement.
 *
 * @author lsafer
 * @version 0.1.3
 * @since 07-Dec-2019
 */
public class While extends Loop<Consumer<While>, Object> {
	/**
	 * The function to be applied before each round on the loop to check whether the loop shall continue or break.
	 */
	final protected Supplier<Boolean> condition;

	/**
	 * Construct a new while loop with the given arguments.
	 *
	 * @param condition looping condition
	 * @throws NullPointerException if the given 'condition' is null
	 */
	public While(Supplier<Boolean> condition) {
		Objects.requireNonNull(condition, "condition");
		this.condition = condition;
	}

	/**
	 * Construct a new while loop with the given arguments.
	 *
	 * @param condition looping condition
	 * @param code      first looping code
	 * @throws NullPointerException if ether the given 'condition' or 'code' is null
	 */
	public While(Supplier<Boolean> condition, Consumer<While> code) {
		Objects.requireNonNull(condition, "null");
		Objects.requireNonNull(code, "code");
		this.append(code);
		this.condition = condition;
	}

	@Override
	public While append(Consumer<While> code) {
		Objects.requireNonNull(code, "code");
		return (While) this.append0(param -> code.accept(this));
	}

	@Override
	protected void loop() {
		while (this.condition.get() && this.next(null)) ;
	}
}
