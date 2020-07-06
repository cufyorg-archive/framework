package cufy.beans;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * An abstraction for the class {@link DelegateBean}.
 *
 * @param <K> the type of keys maintained by this map.
 * @param <V> the type of mapped values.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.07.03
 */
public class AbstractDelegateBean<K, V> extends DelegateBean<K, V> implements Serializable {
	/**
	 * The instance this bean is reading-from/writing-to.
	 */
	private Object instance;
	/**
	 * The entrySet of this map.
	 */
	private transient PersistentEntrySet<K, V> entrySet;
	/**
	 * The keySet of this map.
	 */
	private transient PersistentKeySet<K> keySet;
	/**
	 * The values-collection of this map.
	 */
	private transient PersistentValues<V> values;

	/**
	 * Construct a new bean that delegates to the given {@code instance}.
	 *
	 * @param instance the instance to be represented by the constructed anonymous-bean.
	 * @throws NullPointerException if the given {@code bean} is null.
	 */
	public AbstractDelegateBean(Object instance) {
		Objects.requireNonNull(instance, "instance");
		this.instance = instance;
	}

	@Override
	public EntrySet<K, V> entrySet() {
		if (this.entrySet == null)
			this.entrySet = PersistingMethods.entrySet(this, this.instance);

		return this.entrySet;
	}

	@Override
	public boolean equals(Object object) {
		return PersistingMethods.equals(this, this.instance, object);
	}

	@Override
	public int hashCode() {
		return PersistingMethods.hashCode(this, this.instance);
	}

	@Override
	public Object instance() {
		return this.instance;
	}

	@Override
	public KeySet<K> keySet() {
		if (this.keySet == null)
			this.keySet = PersistingMethods.keySet(this, this.instance);

		return this.keySet;
	}

	@Override
	public String toString() {
		return PersistingMethods.toString(this, this.instance);
	}

	@Override
	public Values<V> values() {
		if (this.values == null)
			this.values = PersistingMethods.values(this, this.instance);

		return this.values;
	}

	@SuppressWarnings("JavaDoc")
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		PersistingMethods.readObject(this, null, stream);
		//the PersistingMethods.readObject will make the method ready for us!
		this.instance = stream.readObject();
	}

	@SuppressWarnings("JavaDoc")
	private void writeObject(ObjectOutputStream stream) throws IOException {
		PersistingMethods.writeObject(this, this.instance, stream);
		stream.writeObject(this.instance);
		stream.writeBoolean(false);
	}
}
