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
 * There is always that position. When you want to pass a simple runnable or
 * consumer to some method. And that method will invoke it on the same thread.
 * And you don't need to catch exceptions. Since there is a try-catch covering
 * the calling context. So Throw Lambdas will be the saver.
 *
 * @author lsafer
 * @version 0.1.3
 * @see <a href="https://framework.cufy.org/site/util#throw-functions">framework.cufy.org/site/util#throw-functions</a>
 * @since 13-Feb-2020
 */
package cufy.util.function;