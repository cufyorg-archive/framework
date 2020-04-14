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

/**
 * A loop to do code just one time.
 *
 * @author lsafer
 * @version 0.1.3
 * @since 13-Feb-2020
 */
public class Do extends Loop<Consumer<Do>, Object> {
	/**
	 * Construct a new 'do' loop.
	 */
	public Do() {
	}

	/**
	 * Construct a new 'do' loop with the given parameters.
	 *
	 * @param code the first looping code
	 * @throws NullPointerException if the given code is null
	 */
	public Do(Consumer<Do> code) {
		Objects.requireNonNull(code, "code");
		this.append(code);
	}

	@Override
	protected void loop() {
		this.next(null);
	}
}
