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
 * A loop that executes its code just one time.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.0 ~2020.02.13
 */
public class Once extends Loop<Once.Code> {
	/**
	 * Construct a new {@code once} loop.
	 */
	public Once() {
	}

	/**
	 * Construct a new {@code once} loop with the given parameters.
	 *
	 * @param code the first looping code.
	 * @throws NullPointerException if the given code is null.
	 */
	public Once(Code code) {
		Objects.requireNonNull(code, "code");
		this.append(code);
	}

	@Override
	protected void loop() {
		this.next(null);
	}

	/**
	 * A loop code for {@code Once} loops. Represents the {@code code block}.
	 */
	@FunctionalInterface
	public interface Code extends Loop.Code<Once> {
		@Override
		default void run(Once loop, Object item) {
			this.onRun(loop);
		}

		/**
		 * Perform this {@code Once} loop code with the given item. Get called when a {@code Once} loop is executing its
		 * code and this code is added to its code.
		 *
		 * @param loop the loop that executed this code.
		 * @throws NullPointerException if the given {@code loop} is null.
		 */
		void onRun(Once loop);
	}
}
