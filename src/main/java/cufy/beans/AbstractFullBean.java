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

/**
 * An abstraction for the interface {@link FullBean}.
 *
 * @param <K> the type of keys maintained by this map.
 * @param <V> the type of mapped values.
 * @author LSafer
 * @version 0.1.5
 * @since 0.0.1 ~2019.06.11
 **/
public abstract class AbstractFullBean<K, V> implements FullBean<K, V>, Serializable {
	/**
	 * A set of the entries of this.
	 */
	private transient PersistentEntrySet<K, V> entrySet;
	/**
	 * A set of the keys in this.
	 */
	private transient PersistentKeySet<K> keySet;
	/**
	 * A set of the values in this.
	 */
	private transient PersistentValues<V> values;

	@Override
	public EntrySet<K, V> entrySet() {
		if (this.entrySet == null)
			this.entrySet = PersistingMethods.entrySet(this, this);

		return this.entrySet;
	}

	@Override
	public boolean equals(Object object) {
		return PersistingMethods.equals(this, this, object);
	}

	@Override
	public int hashCode() {
		return PersistingMethods.hashCode(this, this);
	}

	@Override
	public KeySet<K> keySet() {
		if (this.keySet == null)
			this.keySet = PersistingMethods.keySet(this, this);

		return this.keySet;
	}

	@Override
	public String toString() {
		return PersistingMethods.toString(this, this);
	}

	@Override
	public Values<V> values() {
		if (this.values == null)
			this.values = PersistingMethods.values(this, this);

		return this.values;
	}

	@SuppressWarnings("JavaDoc")
	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
		PersistingMethods.readObject(this, this, stream);
	}

	@SuppressWarnings("JavaDoc")
	private void writeObject(ObjectOutputStream stream) throws IOException {
		PersistingMethods.writeObject(this, this, stream);
	}
}
