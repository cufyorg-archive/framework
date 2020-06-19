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
 * When it comes to concurrent actions. Complexity is all over the place. Using instances that manages those concurrent
 * actions is the solution. Those utils helps to deal with concurrent actions and infinite loops. And many other
 * concurrent actions. And what makes it special that it depends on the logic rather than the timing.
 *
 * @author LSafer
 * @version 0.1.5
 * @see <a href="https://framework.cufy.org/concurrent">framework.cufy.org/concurrent</a>
 * @since 0.0.a ~2019.05.18
 */
package cufy.concurrent;