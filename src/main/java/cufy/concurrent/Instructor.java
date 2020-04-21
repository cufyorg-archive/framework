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
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Used to be the communication method between 2 (or more) threads (loops). Used as a tasks list for unknown count unknown state loops.
 *
 * @author lsafer
 * @version 0.1.3
 * @since 18 May 2019
 */
public class Instructor {
	/**
	 * Loops that linked to this.
	 * <p>
	 * Note: synchronized use only.
	 */
	final protected List<Loop<?, ?>> loops = new ArrayList<>(10);
	/**
	 * All undone posts of this group.
	 * <p>
	 * Note: synchronized use only.
	 */
	final protected List<BiFunction<Instructor, Loop<?, ?>, Boolean>> posts = new ArrayList<>(10);
	/**
	 * The first position of any further loop get started by this.
	 * <p>
	 * Note: synchronized use only.
	 */
	final protected AtomicReference<String> state = new AtomicReference<>(Loop.CONTINUE);

	/**
	 * Get the {@link #loops} of this loop.
	 *
	 * @return the loops list of this group
	 * @see #getLoops(Consumer)
	 */
	public List<Loop<?, ?>> getLoops() {
		return this.loops;
	}

	/**
	 * Do the given action to the {@link #loops} of this. with a locked access.
	 * <p>
	 * Note: this may not be useful if this loop rapidly starts and finishes
	 *
	 * @param action to be done to the loops list of this
	 * @return this
	 * @throws NullPointerException if the given 'action' is null
	 * @see #getLoops()
	 */
	public Instructor getLoops(Consumer<List<Loop<?, ?>>> action) {
		Objects.requireNonNull(action, "action");

		synchronized (this.loops) {
			action.accept(this.loops);
		}
		return this;
	}

	/**
	 * Get the {@link #posts} of this loop.
	 *
	 * @return the posts list of this group
	 * @see #getPosts(Consumer)
	 */
	public List<BiFunction<Instructor, Loop<?, ?>, Boolean>> getPosts() {
		return this.posts;
	}

	/**
	 * Do the given action to the {@link #posts} of this. with a locked access.
	 *
	 * @param action to be done to the posts list of this
	 * @return this
	 * @throws NullPointerException if the given 'action' is null
	 * @see #getPosts()
	 */
	public Instructor getPosts(Consumer<List<BiFunction<Instructor, Loop<?, ?>, Boolean>>> action) {
		Objects.requireNonNull(action, "action");

		synchronized (this.posts) {
			action.accept(this.posts);
		}
		return this;
	}

	/**
	 * Get the {@link #state} of this loop.
	 *
	 * @return the state instance of this group
	 * @see #getState(Consumer)
	 */
	public AtomicReference<String> getState() {
		return this.state;
	}

	/**
	 * Do the given action to the {@link #state} of this. with a locked access.
	 *
	 * @param action to be done to the state instance of this
	 * @return this
	 * @throws NullPointerException if the given 'action' is null
	 * @see #getState()
	 */
	public Instructor getState(Consumer<String> action) {
		Objects.requireNonNull(action, "action");

		synchronized (this.state) {
			action.accept(this.state.get());
		}
		return this;
	}

	/**
	 * Get if this group is alive or not.
	 * <p>
	 * Note: this may not be useful if this loop rapidly starts and finishes
	 *
	 * @return whether this group is alive or not.
	 * @see Loop#isAlive()
	 */
	public boolean isAlive() {
		synchronized (this.posts) {
			return this.posts.size() != 0;
		}
	}

	/**
	 * Waits for all loops of this group to die.
	 * <p>
	 * Note: this may not be useful if this loop rapidly starts and finishes
	 *
	 * @return this
	 * @see Loop#join()
	 */
	public Instructor join() {
		//long time waiting method
		synchronized (this.loops) {
			this.loops.forEach(Loop::join);
		}
		//micro time waiting method
		boolean w;
		do {
			synchronized (this.loops) {
				w = this.loops.size() != 0;
			}
		} while (w);
		return this;
	}

	/**
	 * Waits at most millis milliseconds for all loops of this group to die.
	 * <p>
	 * Note: this may not be useful if this loop rapidly starts and finishes
	 *
	 * @param alter  what to do when the timeout is done
	 * @param millis the time to wait in milliseconds
	 * @return this
	 * @throws IllegalArgumentException if the value of millis is negative
	 * @throws NullPointerException     if the given alter is null
	 * @see Loop#join(Consumer, long)
	 */
	public Instructor join(Consumer<Instructor> alter, long millis) {
		Objects.requireNonNull(alter, "alter");
		if (millis > 0)
			throw new IllegalArgumentException("timeout value is negative");

		//the deadline
		long deadline = System.currentTimeMillis() + millis;
		synchronized (this.loops) {
			//foreach loop
			for (Loop<?, ?> loop : this.loops) {
				//time to wait on the next loop
				long wait = deadline - System.currentTimeMillis();

				//if the deadline passed
				if (wait <= 0) {
					alter.accept(this);
					return this;
				}

				//join the next loop within the allowed wait millis
				loop.join(ignored -> {
				}, wait);
			}
		}

		return this;
	}

	/**
	 * Update the state of every currently or further running loops in this group.
	 *
	 * @param state the new state
	 * @return this
	 * @throws NullPointerException if the given state is null
	 * @see Loop#notify(String)
	 */
	public Instructor notify(String state) {
		Objects.requireNonNull(state, "state");

		synchronized (this.loops) {
			synchronized (this.state) {
				this.state.set(state);
				this.loops.forEach(loop -> loop.notify(state));
			}
		}
		return this;
	}

	/**
	 * Wait until make sure that there is a running loop on this group. By adding a new post and wait until get executed. Useful when starting a new
	 * loop on a new thread. To make sure that that loop started running.
	 *
	 * @return this
	 * @see Loop#pair()
	 */
	public Instructor pair() {
		return this.synchronously((group, loop) -> {
		});
	}

	/**
	 * Make a loop of this group do the given action. Then remove that action if the action returns false.
	 *
	 * @param action to be done by a loop of this group (return false to remove the action)
	 * @return this
	 * @throws NullPointerException if the given action is null
	 * @see Loop#post(Function)
	 */
	public Instructor post(BiFunction<Instructor, Loop<?, ?>, Boolean> action) {
		Objects.requireNonNull(action, "action");

		synchronized (this.posts) {
			this.posts.add(action);
		}
		return this;
	}

	/**
	 * Make a loop of this group do the given action. hen remove that action if the action returns false. Or if this group don't have a running loop.
	 * <p>
	 * Note: no matter what. One (AND JUST ONE) of the given actions should be invoked.
	 *
	 * @param action to be done by a loop of this (return false to remove the action)
	 * @param alter  the action to be done if this group don't have a running loop
	 * @return this
	 * @throws NullPointerException if ether the given 'action' or the given 'alter' is null
	 * @see Loop#post(Function, Consumer)
	 */
	public Instructor post(BiFunction<Instructor, Loop<?, ?>, Boolean> action, Consumer<Instructor> alter) {
		Objects.requireNonNull(action, "action");
		Objects.requireNonNull(alter, "alter");

		synchronized (this.loops) {
			synchronized (this.posts) {
				if (this.isAlive()) {
					this.posts.add(action);
					return this;
				}
			}
		}
		alter.accept(this);
		return this;
	}

	/**
	 * Make a loop of this group do the given action. Then remove that action if the action returns false. Or the given timeout ended.
	 * <p>
	 * Note: no matter what. One (AND JUST ONE) of the given actions should be invoked.
	 *
	 * @param action  to be done by a loop of this group
	 * @param alter   to do when timeout and the action has not been done
	 * @param timeout the timeout (in milli seconds)
	 * @return this
	 * @throws NullPointerException     if ether the given 'action' or 'alter' is null
	 * @throws IllegalArgumentException if ether the given 'timeout' is negative
	 * @see Loop#post(Function, Consumer, long)
	 */
	public Instructor post(BiFunction<Instructor, Loop<?, ?>, Boolean> action, Consumer<Instructor> alter, long timeout) {
		Objects.requireNonNull(action, "action");
		Objects.requireNonNull(alter, "alter");
		if (timeout < 0)
			throw new IllegalArgumentException("timeout value is negative");

		AtomicBoolean state = new AtomicBoolean(true);
		AtomicBoolean applied = new AtomicBoolean(false);

		synchronized (state) {
			synchronized (this.posts) {
				this.posts.add((group, loop) -> {
					if (applied.get())
						return action.apply(group, loop);
					synchronized (state) {
						if (state.get()) {
							boolean w = action.apply(group, loop);
							state.set(false);
							state.notify();
							applied.set(true);
							return w;
						}
					}
					return false;
				});
				new Thread(() -> {
					synchronized (state) {
						if (state.get()) {
							try {
								state.wait(timeout);
							} catch (InterruptedException ignored) {
							}
							if (state.get()) {
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
	 * Start a loop using this {@link Instructor}.
	 *
	 * @param loop to be started
	 * @return this
	 * @throws NullPointerException if the given loop is null
	 * @see Loop#start()
	 */
	public Instructor start(Loop<?, ?> loop) {
		Objects.requireNonNull(loop, "loop");

		synchronized (this.loops) {
			this.loops.add(loop);
		}
		loop.start(this.state.get());
		synchronized (this.loops) {
			this.loops.remove(loop);
			//last tick
			this.tick(loop);
		}
		return this;
	}

	/**
	 * Make a loop of this group do the given action. And WAIT for that loop to do it; If the CALLER thread get interrupted while it's waiting on this
	 * method. Then the action will be canceled.
	 *
	 * @param action to be done by a loop of this group
	 * @return this
	 * @throws NullPointerException if the given 'action' is null
	 * @see Loop#synchronously(Consumer)
	 */
	public Instructor synchronously(BiConsumer<Instructor, Loop<?, ?>> action) {
		Objects.requireNonNull(action, "action");

		AtomicBoolean state = new AtomicBoolean(true);

		synchronized (state) {
			synchronized (this.posts) {
				this.posts.add((group, loop) -> {
					synchronized (state) {
						if (state.get()) {
							action.accept(group, loop);
							state.notify();
						}
						return false;
					}
				});
			}
			try {
				state.wait();
			} catch (InterruptedException ignored) {
			}

			state.set(false);
		}
		return this;
	}

	/**
	 * Make a loop of this group do the given action. And WAIT for that loop to do. Or if this group don't have a running loop (currently).
	 * <p>
	 * Note: this may not be useful if this group rapidly starts and finishes loops
	 * <p>
	 * Note: no matter what. One (AND JUST ONE) of the given actions should be invoked once (also, JUST ONCE).
	 * <p>
	 * Note: action SHOULDN'T be synchronously invoked on ANY non-local object
	 *
	 * @param action to be done by a loop of this group
	 * @param alter  to be done if there is no loop on this group (currently)
	 * @return this
	 * @throws NullPointerException if ether the given 'action' or 'alter' is null
	 * @see Loop#synchronously(Consumer, Consumer)
	 */
	public Instructor synchronously(BiConsumer<Instructor, Loop<?, ?>> action, Consumer<Instructor> alter) {
		Objects.requireNonNull(action, "action");
		Objects.requireNonNull(alter, "alter");

		AtomicBoolean state = new AtomicBoolean();

		synchronized (state) {
			boolean w;
			synchronized (this.loops) {
				synchronized (this.posts) {
					if (w = this.isAlive())
						this.posts.add((group, loop) -> {
							synchronized (state) {
								if (state.get()) {
									action.accept(group, loop);
									state.set(false);
									state.notify();
								}
							}
							return false;
						});
				}
				if (w)
					try {
						state.wait();
					} catch (InterruptedException ignored) {
					}
				if (state.get()) {
					state.set(false);
					alter.accept(this);
				}
			}
		}
		return this;
	}

	/**
	 * Make a loop of this group do the given action. And WAIT until that loop invokes it. Or the given timeout ended.
	 * <p>
	 * Note: no matter what. One (AND JUST ONE) of the given actions should be invoked once (also, JUST ONCE).
	 *
	 * @param action  to be done by a loop of this group
	 * @param alter   to be done if the timeout ended
	 * @param timeout to wait for the action to be invoked (milli seconds)
	 * @return this
	 * @throws NullPointerException     if ether the given 'action' or 'alter' is null
	 * @throws IllegalArgumentException if the given 'timeout' is negative
	 * @see Loop#synchronously(Consumer, Consumer, long)
	 */
	public Instructor synchronously(BiConsumer<Instructor, Loop<?, ?>> action, Consumer<Instructor> alter, long timeout) {
		Objects.requireNonNull(action, "action");
		Objects.requireNonNull(alter, "alter");
		if (timeout < 0)
			throw new IllegalArgumentException("timeout value is negative");

		AtomicBoolean state = new AtomicBoolean();

		synchronized (state) {
			synchronized (this.posts) {
				this.posts.add((group, loop) -> {
					synchronized (state) {
						if (state.get()) {
							action.accept(group, loop);
							state.set(false);
							state.notify();
						}
					}
					return false;
				});
			}
			try {
				state.wait();
			} catch (InterruptedException ignored) {
			}
			if (state.get()) {
				state.set(false);
				alter.accept(this);
			}
		}
		return this;
	}

	/**
	 * Start the given loop with a new thread.
	 *
	 * @param loop to be started
	 * @return the new started thread
	 * @throws NullPointerException if the given loop is null
	 * @see Loop#thread()
	 */
	public Instructor thread(Loop<?, ?> loop) {
		Objects.requireNonNull(loop, "loop");
		new Thread(() -> this.start(loop)).start();
		return this;
	}

	/**
	 * Do the posts posted on this group. With no caller.
	 * <p>
	 * Note: this designed to be called by a loop of this instructor
	 *
	 * @return this
	 * @throws NullPointerException when a post tris doing anything to the caller without doing a null check
	 */
	public Instructor tick() {
		return this.tick(null);
	}

	/**
	 * Do the posts posted on this group. With the caller param of the given loop.
	 *
	 * @param caller the caller loop
	 * @return this
	 * @throws NullPointerException   when the 'caller' is null and a post tris doing anything to the caller without doing a null check
	 * @throws IllegalThreadException if the 'caller' isn't null and the caller thread ins't the thread of the given loop
	 */
	public Instructor tick(Loop<?, ?> caller) {
		if (caller != null && caller.isAlive() && !caller.isCurrentThread())
			throw new IllegalThreadException("caller thread isn't the thread of the given loop");
		synchronized (this.posts) {
			this.posts.removeIf(post -> !post.apply(this, caller));
		}
		return this;
	}
}
