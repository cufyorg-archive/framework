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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Controllable loop. The concept is to do a block. Check if shall continue or not, do the posts. Then do the next block and so on.
 *
 * @param <I> the functional interface for the looping code
 * @param <P> the parameter type for invoking {@link #next(Object)}
 * @author lsafer
 * @version 0.1.3
 * @since 18 May 2019
 */
public abstract class Loop<I, P> {
	/**
	 * A position for loops. Tells that the loop shall be stopped.
	 */
	final public static String BREAK = "break";
	/**
	 * A position for loops. Tells that the loop shall be resumed
	 */
	final public static String CONTINUE = "continue";
	/**
	 * A position for loops. Tells that the loop shall be paused
	 */
	final public static String SLEEP = "sleep";

	/**
	 * The code to loop.
	 */
	final protected List<Consumer<P>> code = new ArrayList<>(10);
	/**
	 * All undone posts of this loop.
	 */
	final protected List<Function<Loop<I, P>, Boolean>> posts = new ArrayList<>(10);
	/**
	 * The state of this loop.
	 */
	final protected AtomicReference<String> state = new AtomicReference<>(CONTINUE);
	/**
	 * The first caller of this loop.
	 * <p>
	 * Note: having more than one caller is unpredictable and ILLEGAL!.
	 */
	final protected AtomicReference<Thread> thread = new AtomicReference<>();

	/**
	 * Append the given code to the end of the looping code of this.
	 *
	 * @param code to be appended
	 * @return this
	 * @throws NullPointerException if the given code is null
	 * @throws ClassCastException   if the given code isn't instance of {@link Consumer}{@literal <}{@link P}{@literal >}
	 */
	public Loop<I, P> append(I code) {
		Objects.requireNonNull(code, "code");
		return this.append0((Consumer<P>) code);
	}

	/**
	 * Append the given code to the end of the looping code of this.
	 *
	 * @param code to be appended
	 * @return this
	 * @throws NullPointerException if the given code is null
	 */
	public Loop<I, P> append0(Consumer<P> code) {
		Objects.requireNonNull(code, "code");

		synchronized (this.code) {
			this.code.add(code);
		}
		return this;
	}

	/**
	 * Get the {@link #code} of this loop.
	 *
	 * @return the code list of this
	 * @see #getCode(Consumer)
	 */
	public List<Consumer<P>> getCode() {
		return this.code;
	}

	/**
	 * Do the given action to the {@link #code} of this. With a locked access.
	 *
	 * @param action to be done to the code list of this
	 * @return this
	 * @throws NullPointerException if the given action is null
	 * @see #getCode()
	 */
	public Loop<I, P> getCode(Consumer<List<Consumer<P>>> action) {
		Objects.requireNonNull(action, "action");

		synchronized (this.code) {
			action.accept(this.code);
			return this;
		}
	}

	/**
	 * Get the {@link #posts} of this loop.
	 *
	 * @return the posts list of this
	 * @see #getPosts(Consumer)
	 */
	public List<Function<Loop<I, P>, Boolean>> getPosts() {
		return this.posts;
	}

	/**
	 * Do the given action to the {@link #posts} of this. With a locked access.
	 *
	 * @param action to be done to the posts list of this
	 * @return this
	 * @throws NullPointerException if the given action is null
	 * @see #getPosts()
	 */
	public Loop<I, P> getPosts(Consumer<List<Function<Loop<I, P>, Boolean>>> action) {
		Objects.requireNonNull(action, "action");

		synchronized (this.posts) {
			action.accept(this.posts);
			return this;
		}
	}

	/**
	 * Get the {@link #state} of this loop.
	 *
	 * @return the state instance of this
	 * @see #getState(Consumer)
	 */
	public AtomicReference<String> getState() {
		return this.state;
	}

	/**
	 * Do the given action to the {@link #state} of this. with a locked access.
	 *
	 * @param action to be done to the state of this
	 * @return this
	 * @throws NullPointerException if the given action is null
	 * @see #getState()
	 */
	public Loop<I, P> getState(Consumer<String> action) {
		Objects.requireNonNull(action, "action");

		synchronized (this.state) {
			action.accept(this.state.get());
			return this;
		}
	}

	/**
	 * Get the {@link #thread} of this loop.
	 *
	 * @return the thread atomic reference of this
	 * @see #getThread(Consumer)
	 */
	public AtomicReference<Thread> getThread() {
		return this.thread;
	}

	/**
	 * Do the given action to the {@link #thread} of this. with a locked access.
	 * <p>
	 * Note:  this may not be useful if this loop rapidly starts and finishes
	 *
	 * @param action to be done to the thread of this
	 * @return this
	 * @throws NullPointerException if the given action is null
	 * @see #getThread()
	 */
	public Loop<I, P> getThread(Consumer<Thread> action) {
		Objects.requireNonNull(action, "action");

		synchronized (this.thread) {
			action.accept(this.thread.get());
			return this;
		}
	}

	/**
	 * Get if this loop is alive or not.
	 * <p>
	 * Note: this may not be useful if this loop rapidly starts and finishes
	 *
	 * @return whether this loop is alive or not.
	 */
	public boolean isAlive() {
		synchronized (this.thread) {
			return this.thread.get() != null;
		}
	}

	/**
	 * Check if the caller thread is the current thread of this loop.
	 * <p>
	 * Note: this may not be useful if this loop rapidly starts and finishes
	 *
	 * @return whether the caller thread is the thread of this
	 */
	public boolean isCurrentThread() {
		synchronized (this.thread) {
			return this.thread.get() == Thread.currentThread();
		}
	}

	/**
	 * Waits for this loop to die.
	 * <p>
	 * Note: this may not be useful if this loop rapidly starts and finishes
	 *
	 * @return this
	 * @throws IllegalThreadException if the caller thread is the current thread of this loop
	 */
	public Loop<I, P> join() {
		this.assertNotRecursiveThreadCall();
		synchronized (this) {
			return this;
		}
	}

	/**
	 * Waits at most millis milliseconds for this loop to die.
	 * <p>
	 * Note: this may not be useful if this loop rapidly starts and finishes
	 *
	 * @param alter  what to do when the timeout is done
	 * @param millis the time to wait in milliseconds
	 * @return this
	 * @throws IllegalArgumentException if the value of millis is negative
	 * @throws NullPointerException     if the given alter is null
	 * @throws IllegalThreadException   if the caller thread is the current thread of this loop
	 */
	public Loop<I, P> join(Consumer<Loop<I, P>> alter, long millis) {
		this.assertNotRecursiveThreadCall();
		Objects.requireNonNull(alter, "alter");
		if (millis < 0)
			throw new IllegalArgumentException("timeout value is negative");

		AtomicBoolean state = new AtomicBoolean(true);

		synchronized (state) {
			new Thread(() -> {
				this.join();
				synchronized (state) {
					if (state.get()) {
						state.set(false);
						state.notify();
					}
				}
			}).start();

			try {
				state.wait(millis);
				if (state.get()) {
					state.set(false);
					alter.accept(this);
				}
			} catch (InterruptedException ignored) {
			}
		}

		return this;
	}

	/**
	 * Update the state of this loop.
	 *
	 * @param state the new state name
	 * @return this
	 * @throws NullPointerException if the given state is null
	 */
	public Loop<I, P> notify(String state) {
		Objects.requireNonNull(state, "state");

		//always gain the code lock before the state lock!
		synchronized (this.code) {
			synchronized (this.state) {
				this.state.set(state);
				this.code.notify();
			}
		}
		return this;
	}

	/**
	 * Wait until make sure that this loop is running. By adding a new post and wait until get executed. Useful when starting a new loop on a new
	 * thread. To make sure that this loop started running.
	 *
	 * @return this
	 */
	public Loop<I, P> pair() {
		return this.synchronously(loop -> {
		});
	}

	/**
	 * Make this loop (specifically the current thread looping) do the action given. Then remove that action if the action returns false.
	 *
	 * @param action to be done by this loop (return false to remove the action)
	 * @return this
	 * @throws NullPointerException if the given action is null
	 */
	public Loop<I, P> post(Function<Loop<I, P>, Boolean> action) {
		Objects.requireNonNull(action, "action");

		synchronized (this.posts) {
			this.posts.add(action);
		}
		return this;
	}

	/**
	 * Make this loop (specifically the current thread looping) do the function given. Then remove that action if the action returns false. Or if this
	 * loop don't have a running thread.
	 * <p>
	 * Note: no matter what. One (AND JUST ONE) of the given actions will be invoked.
	 * <p>
	 * Note: action SHOULDN'T be synchronously invoked on ANY non-local object
	 *
	 * @param action to be done by this loop (return false to remove the action)
	 * @param alter  the action to be done if this loop don't have a running thread
	 * @return this
	 * @throws NullPointerException if ether the given 'action' or the given 'alter' is null
	 */
	public Loop<I, P> post(Function<Loop<I, P>, Boolean> action, Consumer<Loop<I, P>> alter) {
		Objects.requireNonNull(action, "action");
		Objects.requireNonNull(alter, "alter");

		//stop the loop from finishing/starting
		synchronized (this.thread) {
			synchronized (this.posts) {
				if (this.isAlive()) {
					this.posts.add(action);
					return this;
				}
			}
		}
		new Thread(() -> alter.accept(this)).start();
		return this;
	}

	/**
	 * Make this loop (specifically the current thread looping) do the function given. Then remove that action if the action returns false. Or the
	 * given timeout ended.
	 * <p>
	 * Note: no matter what. One (AND JUST ONE) of the given actions should be invoked.
	 *
	 * @param action  to be done by this loop (return false to remove the action)
	 * @param alter   to do when timeout and the action has not been done
	 * @param timeout the timeout (in milli seconds)
	 * @return this
	 * @throws NullPointerException     if ether the given 'action' or 'alter' is null
	 * @throws IllegalArgumentException if ether the given 'timeout' is negative
	 */
	public Loop<I, P> post(Function<Loop<I, P>, Boolean> action, Consumer<Loop<I, P>> alter, long timeout) {
		Objects.requireNonNull(action, "action");
		Objects.requireNonNull(alter, "alter");
		if (timeout < 0)
			throw new IllegalArgumentException("timeout value is negative");

		//whether the timeout still not ended or not
		AtomicBoolean state = new AtomicBoolean(true);
		//whether the post have been posted or not
		AtomicBoolean applied = new AtomicBoolean(false);

		//nothing starts until this block ends
		synchronized (state) {
			synchronized (this.posts) {
				this.posts.add(loop -> {
					//if previously have been applied
					if (applied.get())
						return action.apply(loop);
					//wait for the sub thread start
					synchronized (state) {
						//check if the timout ended or not
						if (state.get()) {
							boolean w = action.apply(loop);
							//tell the sub thread that the post have been posted
							state.set(false);
							state.notify();
							applied.set(true);
							return w;
						}
					}
					//timeout ended
					return false;
				});
				//timer thread
				new Thread(() -> {
					synchronized (state) {
						//if the post haven't been posted within that short time
						if (state.get()) {
							try {
								//wait for the post
								state.wait(timeout);
							} catch (InterruptedException ignored) {
							}
							if (state.get()) {
								//tell the post that the timeout ended
								state.set(false);
								alter.accept(this);
							}
						}
					}
				}).start();
			}
		}
		return this;
	}

	/**
	 * Start this loop with the thread invoked the method. If this loop already running. Then the caller thread will wait until the previous thread
	 * ends the loop.
	 *
	 * @return this
	 * @throws IllegalStateException if this loop still alive
	 */
	public synchronized Loop<I, P> start() {
		synchronized (this.thread) {
			if (this.isAlive())
				throw new AssertionError("loop still alive");
			this.thread.set(Thread.currentThread());
		}
		this.loop();
		synchronized (this.thread) {
			this.thread.set(null);
			//do last tick
			this.tick();
		}
		return this;
	}

	/**
	 * Start this loop with the thread invoked the method. If this loop already running. Then the caller thread will wait until the previous thread
	 * ends the loop.
	 *
	 * @param state the initial state
	 * @return this
	 * @throws IllegalStateException if this loop still alive
	 * @throws NullPointerException  if the given state is null
	 */
	public synchronized Loop<I, P> start(String state) {
		Objects.requireNonNull(state, "state");
		this.getState().set(state);
		return this.start();
	}

	/**
	 * Make this loop (specifically the current thread looping) do the function given. And WAIT for the loop to do it; If the CALLER thread get
	 * interrupted while it's waiting on this method. Then the action will be canceled.
	 *
	 * @param action to be done by this loop
	 * @return this
	 * @throws NullPointerException   if the given 'action' is null
	 * @throws IllegalThreadException if the caller thread is the current thread of this loop
	 */
	public Loop<I, P> synchronously(Consumer<Loop<I, P>> action) {
		this.assertNotRecursiveThreadCall();
		Objects.requireNonNull(action, "action");

		//true => the action should be done | false => the action shouldn't be done
		AtomicBoolean state = new AtomicBoolean(true);

		//prevent lock leakage
		synchronized (state) {
			synchronized (this.posts) {
				this.posts.add(loop -> {
					//wait until the caller thread invokes 'wait()'
					synchronized (state) {
						if (state.get()) {
							//execute the action
							action.accept(loop);
							//prevent the post to be executed more than one
							state.set(false);
							//notify the caller thread
							state.notify();
						}
					}
					//remove the action
					return false;
				});
			}
			try {
				//until ether interrupted or notified
				state.wait();
			} catch (InterruptedException e) {
				//the action ended/canceled
				state.set(false);
			}
		}
		return this;
	}

	/**
	 * Make this loop (specifically the current thread looping) do the function given. And WAIT for the loop to do it. Or if this loop don't have a
	 * running thread (currently).
	 * <p>
	 * Note: this may not be useful if this loop rapidly starts and finishes
	 * <p>
	 * Note: no matter what. One (AND JUST ONE) of the given actions will be invoked once (also, JUST ONCE).
	 * <p>
	 * Note:  action SHOULDN'T be synchronously invoked on ANY non-local object
	 *
	 * @param action to be done by this loop
	 * @param alter  the action to be done if this loop don't have a running thread or if the action is canceled
	 * @return this
	 * @throws NullPointerException   if ether the given 'action' or the given 'alter' is null
	 * @throws IllegalThreadException if the caller thread is the current thread of this loop
	 */
	public Loop<I, P> synchronously(Consumer<Loop<I, P>> action, Consumer<Loop<I, P>> alter) {
		this.assertNotRecursiveThreadCall();
		Objects.requireNonNull(action, "action");
		Objects.requireNonNull(alter, "alter");

		//true => an action should be done | false => no action should be done
		AtomicBoolean state = new AtomicBoolean(true);

		//prevent lock leakage
		synchronized (state) {
			boolean w;
			//block the loop from finishing/starting
			synchronized (this.thread) {
				synchronized (this.posts) {
					if (w = this.isAlive()) {
						this.posts.add(loop -> {
							//wait until the caller thread invokes 'wait()'
							synchronized (state) {
								//if the thread not interrupted
								if (state.get()) {
									action.accept(this);
									//tell the post that we have finished the action
									state.set(false);
									state.notify();
								}
							}
							//remove the action
							return false;
						});
					}
				}
			}
			if (w) {
				try {
					//wait for the action to be posted
					state.wait();
				} catch (InterruptedException e) {
					//catch below
				}
			}
			//if no thread or if this thread got interrupted
			if (state.get()) {
				state.set(false);
				alter.accept(this);
			}
		}
		return this;
	}

	/**
	 * Make this loop (specifically the current thread looping) do the function given. And WAIT until the loop invokes it. Or the given timeout
	 * ended.
	 * <p>
	 * Note: no matter what. One (AND JUST ONE) of the given actions should be invoked.
	 *
	 * @param action  to do with the looping thread
	 * @param alter   to do when the time is out before the action done
	 * @param timeout to wait for the action to be invoked (milli seconds)
	 * @return this
	 * @throws NullPointerException     if ether the given 'action' or 'alter' is null
	 * @throws IllegalArgumentException if the given 'timeout' is negative
	 * @throws IllegalThreadException   if the caller thread is the current thread of this loop
	 */
	public Loop<I, P> synchronously(Consumer<Loop<I, P>> action, Consumer<Loop<I, P>> alter, long timeout) {
		this.assertNotRecursiveThreadCall();
		Objects.requireNonNull(action, "action");
		Objects.requireNonNull(alter, "alter");
		if (timeout < 0)
			throw new IllegalArgumentException("timeout value is negative");

		//true => the action should be done | false => the alter action shouldn't be done
		AtomicBoolean state = new AtomicBoolean(true);

		//prevent lock leakage
		synchronized (state) {
			synchronized (this.posts) {
				this.posts.add(loop -> {
					//Waiting for the caller thread invokes 'wait()'
					synchronized (state) {
						//Checking if the thread still waiting
						if (state.get()) {
							action.accept(this);
							//tell the caller thread that the post have been posted
							state.set(false);
							state.notify();
						}
						//remove the action
						return false;
					}
				});
			}
			try {
				//Waiting to the looping thread to interrupt us
				state.wait(timeout);
			} catch (InterruptedException e) {
				//catch below
			}
			//if the post still not posted
			if (state.get()) {
				//Telling the looping thread that we are leaving :(
				state.set(false);
				alter.accept(this);
			}
		}
		return this;
	}

	/**
	 * Start this loop on a new Thread.
	 *
	 * @return this
	 */
	public Loop<I, P> thread() {
		new Thread(this::start).start();
		return this;
	}

	/**
	 * Start this loop on a new Thread.
	 *
	 * @param state initial state
	 * @return this
	 * @throws NullPointerException if the given state is null
	 */
	public Loop<I, P> thread(String state) {
		Objects.requireNonNull(state, "state");
		new Thread(() -> this.start(state)).start();
		return this;
	}

	/**
	 * Invoke all codes of this loop (ONCE each). While calling {@link #tick()} before and after each code.
	 *
	 * @param params to pass it to the next code
	 * @return whether allowed to continue the loop or not
	 */
	protected boolean next(P params) {
		synchronized (this.code) {
			Iterator<Consumer<P>> iterator = this.code.iterator();
			while (this.tick() && iterator.hasNext())
				iterator.next().accept(params);
		}
		return this.tick();
	}

	/**
	 * Do things that should be done by this loop (except the loop code). This method is the base for any code invocation other than the looping
	 * code.
	 *
	 * @return whether the loop shall continue or break
	 */
	protected boolean tick() {
		//get the lock of the posts list
		synchronized (this.posts) {
			if (posts.size() != 0)
				//do the posts
				this.posts.removeIf(post -> !post.apply(this));
		}

		//get the lock of the state reference
		synchronized (this.state) {
			switch (this.state.get()) {
				case SLEEP:
					break;
				case BREAK:
					return false;
				case CONTINUE:
				default:
					return true;
			}
		}

		//sweet dreams
		synchronized (this.code) {
			try {
				this.code.wait();
			} catch (InterruptedException ignored) {
			}
		}

		return this.tick();
	}

	/**
	 * Make sure that the caller thread isn't the thread of this loop.
	 */
	private void assertNotRecursiveThreadCall() {
		synchronized (this.thread) {
			Thread current = Thread.currentThread();
			if (current == this.thread.get())
				throw new IllegalThreadException(current + " is the thread of this loop");
		}
	}

	/**
	 * The looping cod. call {@link #next(Object)} inside the loop to invoke the code of this loop. Break the loop if it returned false.
	 */
	protected abstract void loop();
}
