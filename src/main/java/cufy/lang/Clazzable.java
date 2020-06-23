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

import java.util.Map;
import java.util.Objects;

/**
 * An interface for the objects that can itself provide a {@link Clazz} representing the type of it, and the elements
 * stored in it.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 0.1.5 (21-Jun-2020)
 */
public interface Clazzable {
	/**
	 * Determine if the given {@code instance} can be clazzed automatically in the default methods of {@link Clazz} or
	 * not.
	 *
	 * @param instance the instance to be checked.
	 * @return true, if the given {@code instance} is clazzable.
	 */
	static boolean isClazzable(Object instance) {
		return instance instanceof Clazzable ||
			   instance instanceof Map ||
			   instance instanceof Iterable ||
			   instance != null && instance.getClass().isArray();
	}

	/**
	 * Determine if the instances of the given {@code klass} can be clazzed automatically in the default methods of
	 * {@link Clazz} or not.
	 *
	 * @param klass the class to be checked.
	 * @return true, if the instances of the given {@code klass} can be clazzed automatically in the default methods of
	 *        {@link Clazz}.
	 * @throws NullPointerException if the given {@code klass} is null.
	 */
	static boolean isClazzable(Class klass) {
		Objects.requireNonNull(klass, "klass");
		return Clazzable.class.isAssignableFrom(klass) ||
			   Map.class.isAssignableFrom(klass) ||
			   Iterable.class.isAssignableFrom(klass) ||
			   klass.isArray();
	}

	/**
	 * Get a {@link Clazz} represents the type of this {@code clazzable} and the elements stored in it.
	 *
	 * @return a {@link Clazz} represents the type of this {@code clazzable} and the elements stored within it.
	 */
	default Clazz getClazz() {
		return Clazz.of(this.getClass(), this.getComponentTrees());
	}

	/**
	 * Get a clazz component {@code tree} for the elements in this {@code clazzable}.
	 *
	 * @param componentClazzes the {@code componentClazzes} preferred to be used in the returned tree.
	 * @return a clazz component {@code tree} for the elements in this {@code clazzable}.
	 */
	default Map<Object, Clazz>[] getComponentTrees(Clazz... componentClazzes) {
		return Clazz.trees0(this, componentClazzes);
	}
}
