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
 * An abstraction for the interface {@link Bean}.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author lsafer
 * @version 0.1.3
 * @since 11 Jun 2019
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
			this.entrySet = Bean.entrySet(this);
		}

		return this.entrySet;
	}

	@Override
	public Set<K> keySet() {
		if (this.keySet == null) {
			this.keySet = FullBean.super.keySet();
		}

		return this.keySet;
	}

	@Override
	public Collection<V> values() {
		if (this.values == null) {
			this.values = FullBean.super.values();
		}

		return this.values;
	}

	@Override
	public String toString() {
		Iterator<Entry<K, V>> entries = this.entrySet().iterator();

		if (!entries.hasNext()) {
			return "{}";
		} else {
			StringBuilder builder = new StringBuilder("{");

			while (true) {
				Entry<K, V> entry = entries.next();
				Object key = entry.getKey();
				Object value = entry.getValue();

				builder.append(key == this ? "(this Bean)" : key)
						.append('=')
						.append(value == this ? "(this Bean)" : value);

				if (!entries.hasNext()) {
					return builder.append('}').toString();
				}

				builder.append(", ");
			}
		}
	}

	/**
	 * Deserialization method.
	 *
	 * @param stream to initialize this using
	 * @throws ClassNotFoundException if the class of a serialized object could not be found.
	 * @throws IOException            if an I/O error occurs.
	 * @throws NullPointerException   if the given 'stream' is null
	 */
	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
		Objects.requireNonNull(stream, "stream");

		int length = stream.readInt();
		for (int i = 0; i < length; i++)
			this.put((K) stream.readObject(), (V) stream.readObject());
	}

	/**
	 * Serialization method.
	 *
	 * @param stream to use to serialize this
	 * @throws IOException          if an I/O error occurs
	 * @throws NullPointerException if the given 'stream' is null
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException {
		Objects.requireNonNull(stream, "stream");

		stream.writeInt(this.size());
		for (Entry<K, V> entry : this.entrySet()) {
			stream.writeObject(entry.getKey());
			stream.writeObject(entry.getValue());
		}
	}
}
