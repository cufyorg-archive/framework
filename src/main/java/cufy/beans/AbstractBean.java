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
package cufy.beans;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * An abstraction for the interface {@link FullBean}.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author LSafer
 * @version 0.1.3
 * @since 0.0.1 ~2019.06.11
 **/
public abstract class AbstractBean<K, V> implements FullBean<K, V>, Serializable {
	/**
	 * A set of the entries of this.
	 */
	protected transient Set<Entry<K, V>> entrySet;
	/**
	 * A set of the keys in this.
	 */
	protected transient Set<K> keySet;
	/**
	 * A set of the values in this.
	 */
	protected transient Collection<V> values;

	@Override
	public Set<Entry<K, V>> entrySet() {
		if (this.entrySet == null) {
			Object instance = this.getInstance();
			this.entrySet = Bean.entrySet(instance);
		}

		return this.entrySet;
	}

	@Override
	public Set<K> keySet() {
		if (this.keySet == null)
			this.keySet = FullBean.super.keySet();

		return this.keySet;
	}

	@Override
	public Collection<V> values() {
		if (this.values == null)
			this.values = FullBean.super.values();

		return this.values;
	}

	@Override
	public String toString() {
		Set<Entry<K, V>> entrySet = this.entrySet();
		Iterator<Entry<K, V>> iterator = entrySet.iterator();

		if (iterator.hasNext()) {
			StringBuilder builder = new StringBuilder("{");

			while (iterator.hasNext()) {
				Entry<K, V> entry = iterator.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				boolean next = iterator.hasNext();

				builder.append(key == this ? "(this Bean)" : key)
						.append('=')
						.append(value == this ? "(this Bean)" : value)
						.append(next ? ", " : "}");
			}

			return builder.toString();
		} else
			return "{}";
	}

	@SuppressWarnings("JavaDoc")
	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
		Objects.requireNonNull(stream, "stream");

		int length = stream.readInt();
		for (int i = 0; i < length; i++) {
			Object key = stream.readObject();
			Object value = stream.readObject();

			this.put((K) key, (V) value);
		}
	}

	@SuppressWarnings("JavaDoc")
	private void writeObject(ObjectOutputStream stream) throws IOException {
		Objects.requireNonNull(stream, "stream");
		int size = this.size();

		stream.writeInt(size);
		for (Entry<K, V> entry : this.entrySet()) {
			K key = entry.getKey();
			V value = entry.getValue();

			stream.writeObject(key);
			stream.writeObject(value);
		}
	}
}
