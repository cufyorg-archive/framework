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
 * Looping until get broken manually.
 *
 * @author lsafer
 * @version 0.1.3
 * @since 07-Dec-2019
 */
public class Forever extends Loop<Consumer<Forever>, Object> {
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
	public Forever(Consumer<Forever> code) {
		Objects.requireNonNull(code, "code");
		this.append(code);
	}

	@Override
	public Forever append(Consumer<Forever> code) {
		Objects.requireNonNull(code, "code");
		return (Forever) this.append0(param -> code.accept(this));
	}

	@Override
	protected void loop() {
		while (this.next(null)) ;
	}
}
