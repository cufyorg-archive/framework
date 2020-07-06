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
import java.util.Map;
import java.util.Set;

/**
 * An abstraction for the interface {@link Bean}.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author LSafer
 * @version 0.1.5
 * @since 0.0.1 ~2019.06.11
 **/
public abstract class AbstractBean<K, V> implements Bean<K, V>, Serializable {
	/**
	 * A set of the entries of this.
	 */
	private transient RawEntrySet<K, V> entrySet;
	/**
	 * A set of the keys in this.
	 */
	private transient RawKeySet<K> keySet;
	/**
	 * A set of the values in this.
	 */
	private transient RawValues<V> values;

	@Override
	public int hashCode() {
		return RawMethods.hashCode(this);
	}

	@Override
	public boolean equals(Object object) {
		return RawMethods.equals(this, object);
	}

	@Override
	public String toString() {
		return RawMethods.toString(this);
	}

	@Override
	public Set<K> keySet() {
		if (this.keySet == null)
			this.keySet = RawMethods.keySet(this);

		return this.keySet;
	}

	@Override
	public Collection<V> values() {
		if (this.values == null)
			this.values = RawMethods.values(this);

		return this.values;
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		if (this.entrySet == null)
			this.entrySet = RawMethods.entrySet(this);

		return (Set) this.entrySet;
	}

	@SuppressWarnings("JavaDoc")
	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
		RawMethods.readObject(this, stream);
	}

	@SuppressWarnings("JavaDoc")
	private void writeObject(ObjectOutputStream stream) throws IOException {
		RawMethods.writeObject(this, stream);
	}
}
