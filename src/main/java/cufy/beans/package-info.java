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
 * Objects have fields. Those fields can't be maneged easily when the class of the object is unknown (without
 * reflection). The beans establish that by treating the object as a map, and the fields of that object will work as if
 * they're the entries of the map. keeping the reflection part hidden from the user.
 *
 * @author LSafer
 * @version 0.1.5
 * @see <a href="https://framework.cufy.org/beans">framework.cufy.org/beans</a>
 * @since 0.0.a ~2019.06.11
 */
package cufy.beans;