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

import java.util.*;

/**
 * A tree of a clazzes mapped by key-value pairs.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 14-Jun-2020
 */
final public class ClazzTree extends AbstractMap<Object, Clazz> {
	/**
	 * The default clazz for the elements in this tree.
	 */
	final private Clazz defaultClazz;
	/**
	 * The entry-set of this.
	 */
	final private Set<Entry<Object, Clazz>> entrySet;

	/**
	 * Construct a new empty clazz-tree.
	 */
	public ClazzTree() {
		this.entrySet = Collections.emptySet();
		this.defaultClazz = null;
	}

	/**
	 * Constructs a new clazz-tree that its defaultClazz is the given clazz.
	 *
	 * @param defaultClazz the default clazz of this.
	 */
	public ClazzTree(Clazz defaultClazz) {
		this.entrySet = Collections.emptySet();
		this.defaultClazz = defaultClazz;
	}

	/**
	 * Constructs a new clazz-tree with the given clazzes.
	 *
	 * @param clazzes the clazzes to copy.
	 * @throws NullPointerException     if the given 'clazzes' is null.
	 * @throws IllegalArgumentException if the given 'clazzes' has a non-clazz value.
	 */
	public ClazzTree(Map<Object, Clazz> clazzes) {
		Objects.requireNonNull(clazzes, "clazzes");
		for (Clazz v : clazzes.values())
			if (!(v instanceof Clazz))
				throw new IllegalArgumentException("Non-clazz value");

		this.entrySet = Collections.unmodifiableSet(new HashSet<>(clazzes.entrySet()));
		this.defaultClazz = null;
	}

	/**
	 * Construct a new clazz-tree with the given clazzes and defaultClazz.
	 *
	 * @param defaultClazz the default clazz of this.
	 * @param clazzes      the clazzes to copy.
	 * @throws NullPointerException if the given 'defaultClazz' or 'clazzes' is null.
	 */
	public ClazzTree(Clazz defaultClazz, Map<Object, Clazz> clazzes) {
		Objects.requireNonNull(clazzes, "clazzes");
		for (Clazz v : clazzes.values())
			if (!(v instanceof Clazz))
				throw new IllegalArgumentException("Non-clazz value");

		this.entrySet = Collections.unmodifiableSet(new HashSet<>(clazzes.entrySet()));
		this.defaultClazz = defaultClazz;
	}

	/**
	 * Construct a new clazz-tree depending on the given instance and its elements.
	 * <br>
	 * Note: Only {@link Map}s, {@link List}s and arrays are supported.
	 *
	 * @param defaultClazz the default clazz in the returned tree.
	 * @param instance     the instance to get a clazz-tree for.
	 * @return a clazz tree from the types of the given instance and its elements.
	 */
	public static ClazzTree of(Clazz defaultClazz, Object instance) {
		return new ClazzTree(defaultClazz, ClazzTree.collectClazzes(instance));
	}

	/**
	 * Construct a new clazz-tree depending on the given instance and its elements.
	 * <br>
	 * Note: Only {@link Map}s, {@link List}s and arrays are supported.
	 *
	 * @param instance the instance to get a clazz-tree for.
	 * @return a clazz tree from the types of the given instance and its elements.
	 */
	public static ClazzTree of(Object instance) {
		return new ClazzTree(ClazzTree.collectDefaultClazz(instance), ClazzTree.collectClazzes(instance));
	}

	/**
	 * Collect the clazzes of the elements in the given instance.
	 *
	 * @param instance to get the clazzes of its elements.
	 * @return the clazzes of the elements of the given instance
	 */
	private static Map<Object, Clazz> collectClazzes(Object instance) {
		if (instance instanceof Map) {
			Map map = (Map) instance;
			Map clazzes = new HashMap(map.size());

			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry entry = (Entry) iterator.next();
				clazzes.put(entry.getKey(), Clazz.ofx(entry.getValue()));
			}

			return clazzes;
		}
		if (instance instanceof List) {
			List list = (List) instance;
			Map clazzes = new HashMap(list.size());

			int i = 0;
			Iterator iterator = list.iterator();
			while (iterator.hasNext())
				clazzes.put(i++, iterator.next());

			return clazzes;
		}
		if (instance instanceof Object[]) {
			Object[] array = (Object[]) instance;
			Map clazzes = new HashMap(array.length);

			for (int i = 0; i < array.length; i++)
				clazzes.put(i, Clazz.ofx(array[i]));

			return clazzes;
		}

		return new HashMap();
	}

	/**
	 * Collect the defaultClazz of the elements of the given instance.
	 *
	 * @param instance the instance to get the defaultClazz of.
	 * @return the defaultClazz of the elements of the given instance.
	 */
	private static Clazz collectDefaultClazz(Object instance) {
		if (instance instanceof Map || instance instanceof List) {
			return Clazz.of(Object.class);
		}
		if (instance != null && instance.getClass().isArray()) {
			Class component = instance.getClass().getComponentType();
			return component.isArray() ? Clazz.ofa(component) : Clazz.of(component);
		}

		return null;
	}

	@Override
	public Set<Entry<Object, Clazz>> entrySet() {
		return this.entrySet;
	}
}
