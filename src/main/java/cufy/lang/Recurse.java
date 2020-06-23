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
package cufy.lang;

/**
 * A representation for a recursion occurred.
 *
 * @author LSafer
 * @version 0.1.3
 * @since 0.0.1 ~2019.11.25
 */
public final class Recurse {
	/**
	 * This is a representation class and should not be instanced.
	 *
	 * @throws AssertionError when called.
	 */
	private Recurse() {
		throw new AssertionError("No instance for you!");
	}
}
