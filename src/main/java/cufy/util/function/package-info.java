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
/**
 * There is always that position. When you want to pass a simple runnable or consumer to some method. That method will
 * invoke it on the same thread. You don't need to catch exceptions. Since there is a try-catch covering the calling
 * context. So Throw Lambdas will be the saver.
 *
 * @author LSafer
 * @version 0.1.3
 * @see <a href="https://framework.cufy.org/util#function">framework.cufy.org/util#function</a>
 * @since 0.0.4 ~2020.02.13
 */
package cufy.util.function;