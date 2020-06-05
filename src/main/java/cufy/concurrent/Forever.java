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
 * Looping until get broken manually.
 *
 * @author lsafer
 * @version 0.1.3
 * @since 07-Dec-2019
 */
public class Forever extends Loop<Forever.Code> {
	/**
	 * Construct a new forever loop.
	 */
	public Forever() {
	}

	/**
	 * Construct a new forever loop with the given parameters.
	 *
	 * @param code the first looping code
	 * @throws NullPointerException if the given code is null
	 */
	public Forever(Code code) {
		Objects.requireNonNull(code, "code");
		this.append(code);
	}

	@Override
	protected void loop() {
		while (this.next(null)) ;
	}

	/**
	 * A loop-code for {@code Forever} loops.
	 */
	@FunctionalInterface
	public interface Code extends Loop.Code<Forever> {
		@Override
		default void run(Forever loop, Object item) {
			this.onRun(loop);
		}

		/**
		 * Perform this {@code Forever} loop-code with the given item. Get called when a {@code Forever} loop is executing its code
		 * and this code is added to its code.
		 *
		 * @param loop the loop that executed this code
		 * @throws NullPointerException if the given 'loop' is null
		 */
		void onRun(Forever loop);
	}
}
