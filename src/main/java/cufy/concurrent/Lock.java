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
package cufy.concurrent;

import cufy.lang.IllegalThreadException;

import java.io.Closeable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A java lock locker thread. Designed to be a replacement of wrapping the code with 'synchronized' statement. By creating a thread on the background
 * to {@link #lock()} that lock and hold it until {@link #unlock()} get invoked.
 *
 * @param <T> the type of that lock
 * @author lsafer
 * @version 0.1.3
 * @since 07-Dec-2019
 */
public class Lock<T> extends Thread implements Closeable {
	/**
	 * The lock holder should end it's thread.
	 */
	final static protected int CLOSE = -1;
	/**
	 * The lock holder should gain it's targeted lock.
	 */
	final static protected int LOCK = 1;
	/**
	 * The lock holder should release it's targeted lock.
	 */
	final static protected int UNLOCK = 0;

	/**
	 * The targeted lock.
	 */
	final protected T lock;
	/**
	 * The thread that have created this. To avoid serving other threads.
	 */
	final protected Thread master;
	/**
	 * The reference to communicate with the lock holder thread. Representing the state integer code.
	 */
	final protected AtomicInteger state = new AtomicInteger(UNLOCK);

	/**
	 * Initialize a new lock holder.
	 *
	 * @param lock the targeted lock
	 * @throws NullPointerException if the given lock is null
	 */
	public Lock(T lock) {
		Objects.requireNonNull(lock, "lock");
		this.setDaemon(true);
		this.lock = lock;
		this.master = Thread.currentThread();
	}

	/**
	 * Initialise a new lock. Locking on itself.
	 */
	public Lock() {
		this.setDaemon(true);
		this.lock = (T) this;
		this.master = Thread.currentThread();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalThreadException if the caller thread isn't the owner thread of this lock
	 */
	@Override
	public void close() {
		this.assertMasterThread();
		synchronized (this.state) {
			try {
				//change the state
				this.state.set(CLOSE);
				//notify the thread
				this.state.notify();
				//wait for the thread to do its job
				this.state.wait();
				//wait for the thread to die
				this.join();
			} catch (InterruptedException ignored) {
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalThreadException if the caller thread is not this
	 */
	@Override
	public void run() {
		this.assertThisThread();
		while (true) {
			synchronized (this.state) {
				switch (this.state.get()) {
					case UNLOCK:
						this.unlock0();
						break;
					case LOCK:
						this.lock0();
						break;
					case CLOSE:
						this.close0();
						return;
					default:
						throw new IllegalStateException(this.state.toString());
				}
			}
		}
	}

	/**
	 * Hold the lock. Wait for the lock been gained.
	 *
	 * @throws IllegalThreadStateException if this lock already closed
	 * @throws IllegalThreadException      if the caller thread isn't the master of this
	 */
	public void lock() {
		this.assertMasterThread();
		synchronized (this.state) {
			if (!this.isAlive())
				this.start();
			try {
				//change the state
				this.state.set(LOCK);
				//notify the thread
				this.state.notify();
				//wait for the thread to do its job
				this.state.wait();
			} catch (InterruptedException ignored) {
			}
		}
	}

	/**
	 * Release the targeted lock by this.
	 *
	 * @throws IllegalThreadException if the caller thread isn't the master of this
	 */
	public void unlock() {
		this.assertMasterThread();
		synchronized (this.state) {
			if (!this.isAlive())
				return;
			try {
				//change the state
				this.state.set(UNLOCK);
				//notify the thread
				this.state.notify();
				//wait for the thread to do its job
				this.state.wait();
			} catch (InterruptedException ignored) {
			}
		}
	}

	/**
	 * Do code before closing the lock.
	 *
	 * @throws IllegalThreadException if the caller thread isn't this thread
	 */
	protected void close0() {
		this.assertThisThread();
		synchronized (this.state) {
			//notify the caller
			this.state.notify();
		}
	}

	/**
	 * Sleep with while owning the lock.
	 *
	 * @throws IllegalThreadException if the caller thread isn't this thread
	 */
	protected void lock0() {
		this.assertThisThread();
		synchronized (this.state) {
			synchronized (this.lock) {
				try {
					//notify the caller
					this.state.notify();
					//wait until the state changes
					this.state.wait();
				} catch (InterruptedException ignored) {
				}
			}
		}
	}

	/**
	 * Sleep until notified.
	 *
	 * @throws IllegalThreadException if the caller thread isn't this thread
	 */
	protected void unlock0() {
		this.assertThisThread();
		synchronized (this.state) {
			try {
				//notify the caller
				this.state.notify();
				//wait until the state changes
				this.state.wait();
			} catch (InterruptedException ignored) {
			}
		}
	}

	/**
	 * Assert that the caller thread is the master of this lock.
	 *
	 * @throws IllegalThreadException if the caller thread isn't the master of this
	 */
	private void assertMasterThread() {
		Thread current = Thread.currentThread();
		if (current != this.master)
			throw new IllegalThreadException(current + " isn't the master thread of " + this);
	}

	/**
	 * Assert that the caller thread is this lock.
	 *
	 * @throws IllegalThreadException if the caller thread isn't this thread
	 */
	private void assertThisThread() {
		Thread current = Thread.currentThread();
		if (current != this)
			throw new IllegalThreadException(current + " tries to invoke a method that is exclusive to " + this);
	}
}
