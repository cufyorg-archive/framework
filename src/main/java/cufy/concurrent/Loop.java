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

/**
 * Controllable loop. The concept is to do a block. Check if must continue or not, do the posts. Then do the next block
 * and so on.
 *
 * @param <C> the code type
 * @author LSafer
 * @version 0.1.5
 * @since 0.0.a ~2019.05.18
 */
public abstract class Loop<C extends Loop.Code> {
	//TODO security
	/**
	 * A position for loops. Tells that the loop must be stopped.
	 */
	public static final String BREAK = "break";
	/**
	 * A position for loops. Tells that the loop must be resumed.
	 */
	public static final String CONTINUE = "continue";
	/**
	 * A position for loops. Tells that the loop must be paused.
	 */
	public static final String SLEEP = "sleep";

	/**
	 * The code to loop.
	 * <br>
	 * Note: synchronized use only.
	 */
	protected final List<Code> code = new ArrayList(10);
	/**
	 * All undone posts of this loop.
	 * <br>
	 * Note: synchronized use only.
	 */
	protected final List<Post> posts = new ArrayList<>(10);
	/**
	 * The state of this loop.
	 * <br>
	 * Note: synchronized use only.
	 */
	protected final AtomicReference<String> state = new AtomicReference(Loop.CONTINUE);
	/**
	 * The first caller of this loop.
	 * <br>
	 * Note: synchronized use only.
	 */
	protected final AtomicReference<Thread> thread = new AtomicReference();

	/**
	 * Append the given code to the end of the looping code of this.
	 *
	 * @param code to be appended.
	 * @return this.
	 * @throws NullPointerException if the given {@code code} is null.
	 */
	public Loop<C> append(C code) {
		Objects.requireNonNull(code, "code");
		synchronized (this.code) {
			this.code.add(code);
		}
		return this;
	}

	/**
	 * Get the {@link #code} of this loop.
	 *
	 * @return the code list of this.
	 * @see #getCode(Consumer)
	 */
	public List<Code> getCode() {
		return this.code;
	}

	/**
	 * Do the given {@code action} to the {@link #code} of this. With locked access.
	 *
	 * @param action to be done to the code list of this.
	 * @return this.
	 * @throws NullPointerException if the given {@code action} is null.
	 * @see #getCode()
	 */
	public Loop<C> getCode(Consumer<List<Code>> action) {
		Objects.requireNonNull(action, "action");

		synchronized (this.code) {
			action.accept(this.code);
			return this;
		}
	}

	/**
	 * Get the {@link #posts} of this loop.
	 *
	 * @return the posts list of this.
	 * @see #getPosts(Consumer)
	 */
	public List<Post> getPosts() {
		return this.posts;
	}

	/**
	 * Do the given {@code action} to the {@link #posts} of this. With locked access.
	 *
	 * @param action to be done to the posts list of this.
	 * @return this.
	 * @throws NullPointerException if the given {@code action} is null.
	 * @see #getPosts()
	 */
	public Loop<C> getPosts(Consumer<List<Post>> action) {
		Objects.requireNonNull(action, "action");

		synchronized (this.posts) {
			action.accept(this.posts);
			return this;
		}
	}

	/**
	 * Get the {@link #state} of this loop.
	 *
	 * @return the state instance of this.
	 * @see #getState(Consumer)
	 */
	public AtomicReference<String> getState() {
		return this.state;
	}

	/**
	 * Do the given {@code action} to the {@link #state} of this. With locked access.
	 *
	 * @param action to be done to the state of this.
	 * @return this.
	 * @throws NullPointerException if the given {@code action} is null.
	 * @see #getState()
	 */
	public Loop<C> getState(Consumer<String> action) {
		Objects.requireNonNull(action, "action");

		synchronized (this.state) {
			action.accept(this.state.get());
			return this;
		}
	}

	/**
	 * Get the {@link #thread} of this loop.
	 *
	 * @return the thread atomic reference of this.
	 * @see #getThread(Consumer)
	 */
	public AtomicReference<Thread> getThread() {
		return this.thread;
	}

	/**
	 * Do the given {@code action} to the {@link #thread} of this. with locked access.
	 * <br>
	 * Note:  this may not be useful if this loop rapidly starts and finishes.
	 *
	 * @param action to be done to the thread of this.
	 * @return this.
	 * @throws NullPointerException if the given {@code action} is null.
	 * @see #getThread()
	 */
	public Loop<C> getThread(Consumer<Thread> action) {
		Objects.requireNonNull(action, "action");

		synchronized (this.thread) {
			action.accept(this.thread.get());
			return this;
		}
	}

	/**
	 * Determine if this loop is alive or not.
	 * <br>
	 * Note: this may not be useful if this loop rapidly starts and finishes.
	 *
	 * @return true, if this loop is alive.
	 */
	public boolean isAlive() {
		synchronized (this.thread) {
			return this.thread.get() != null;
		}
	}

	/**
	 * Determine if the {@code caller thread} is the current thread of this loop.
	 * <br>
	 * Note: this may not be useful if this loop rapidly starts and finishes.
	 *
	 * @return true, if the caller thread is the thread of this.
	 */
	public boolean isCurrentThread() {
		synchronized (this.thread) {
			return this.thread.get() == Thread.currentThread();
		}
	}

	/**
	 * Waits for this loop to die.
	 * <br>
	 * Note: this may not be useful if this loop rapidly starts and finishes.
	 *
	 * @return this.
	 * @throws IllegalThreadException if the {@code caller thread} is the current thread of this loop.
	 */
	public Loop<C> join() {
		this.assertNotRecursiveThreadCall();
		synchronized (this) {
			return this;
		}
	}

	/**
	 * Waits at most {@code millis} milliseconds for this loop to die.
	 * <br>
	 * Note: this may not be useful if this loop rapidly starts and finishes
	 *
	 * @param alter  what to do when the timeout is done.
	 * @param millis the time to wait in milliseconds.
	 * @return this.
	 * @throws IllegalArgumentException if the value of {@code millis} is negative
	 * @throws NullPointerException     if the given {@code alter} is null.
	 * @throws IllegalThreadException   if the {@code caller thread} is the current thread of this loop.
	 */
	public Loop<C> join(Consumer<Loop<C>> alter, long millis) {
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
	 * @param state the new state name.
	 * @return this.
	 * @throws NullPointerException if the given {@code state} is null.
	 */
	public Loop<C> notify(String state) {
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
	 * Wait until make sure that this loop is running. By adding a new post and wait until get executed. Useful when
	 * starting a new loop on a new thread. To make sure that this loop started running.
	 *
	 * @return this
	 */
	public Loop<C> pair() {
		return this.synchronously((l, t) -> {
		});
	}

	/**
	 * Make this loop (specifically the current thread looping) do the post given. Then remove that post if the post
	 * returns false.
	 *
	 * @param post to be done by this loop (return false to remove the post).
	 * @return this.
	 * @throws NullPointerException if the given {@code post} is null.
	 */
	public Loop<C> post(Post<Loop<C>> post) {
		Objects.requireNonNull(post, "post");

		synchronized (this.posts) {
			this.posts.add(post);
		}
		return this;
	}

	/**
	 * Post the given 'post' to this loop if this loop is alive. And make this loop (specifically the current thread
	 * looping) do the post given. If the loop is not running then the post will be invoked in a new thread with the
	 * argument 'loopingThread' passed as false.
	 * <br>
	 * Note: no matter what, the post given should be ether added or invoked.
	 *
	 * @param post the post to be posted.
	 * @return this.
	 * @throws NullPointerException if the given {@code post} is null.
	 */
	public Loop<C> postIfAlive(Post<Loop<C>> post) {
		Objects.requireNonNull(post, "post");

		//stop the loop from finishing/starting
		synchronized (this.thread) {
			synchronized (this.posts) {
				if (this.isAlive()) {
					this.posts.add(post);
					return this;
				}
			}
		}
		new Thread(() -> post.post(this, false)).start();
		return this;
	}

	/**
	 * Post the given 'post' to this loop and remove it if the loop didn't execute it within the timeout given. If the
	 * timeout ended before executing it, the post will be invoked in a new thread with the argument 'loopingThread'
	 * passed as false.
	 * <br>
	 * Note: no matter what, the post given should be invoked.
	 *
	 * @param timeout the timeout (in milli seconds).
	 * @param post    the post to be posted.
	 * @return this.
	 * @throws NullPointerException     if the given {@code post} is null.
	 * @throws IllegalArgumentException if the given {@code timeout} is negative.
	 */
	public Loop<C> postWithin(long timeout, Post<Loop<C>> post) {
		Objects.requireNonNull(post, "post");
		if (timeout < 0)
			throw new IllegalArgumentException("timeout value is negative");

		//whether the timeout still not ended or not
		AtomicBoolean state = new AtomicBoolean(true);
		//whether the post have been posted or not
		AtomicBoolean applied = new AtomicBoolean(false);

		//nothing starts until this block ends
		synchronized (state) {
			synchronized (this.posts) {
				this.posts.add((loop, loopingThread) -> {
					//if previously have been applied
					if (applied.get())
						return post.post(loop, loopingThread);
					//wait for the sub thread start
					synchronized (state) {
						//check if the timout ended or not
						if (state.get()) {
							boolean w = post.post(loop, loopingThread);
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
								post.post(this, false);
							}
						}
					}
				}).start();
			}
		}
		return this;
	}

	/**
	 * Start this loop with the {@code caller thread}. If this loop already running. Then the {@code caller thread} will
	 * wait until the previous thread ends the loop.
	 *
	 * @return this.
	 */
	public synchronized Loop<C> start() {
		synchronized (this.thread) {
			if (this.isAlive())
				throw new InternalError("loop still alive");
			this.thread.set(Thread.currentThread());
		}
		this.loop();
		synchronized (this.thread) {
			this.thread.set(null);
		}
		return this;
	}

	/**
	 * Start this loop with the {@code caller thread}. If this loop already running. Then the {@code caller thread} will
	 * wait until the previous thread ends the loop.
	 *
	 * @param state the initial state.
	 * @return this.
	 * @throws NullPointerException if the given {@code state} is null.
	 */
	public synchronized Loop<C> start(String state) {
		Objects.requireNonNull(state, "state");
		this.notify(state);
		return this.start();
	}

	/**
	 * Make this loop (specifically the current thread looping) do the post given. And WAIT for the loop to do it.
	 *
	 * @param post to be done by this loop.
	 * @return this.
	 * @throws NullPointerException   if the given {@code post} is null.
	 * @throws IllegalThreadException if the {@code caller thread} is the current thread of this loop.
	 */
	public Loop<C> synchronously(SynchronizedPost<Loop<C>> post) {
		this.assertNotRecursiveThreadCall();
		Objects.requireNonNull(post, "post");

		//true → the post should be done | false → the post shouldn't be done
		AtomicBoolean state = new AtomicBoolean(true);

		//prevent lock leakage
		synchronized (state) {
			synchronized (this.posts) {
				this.posts.add((SynchronizedPost) (l, t) -> {
					//wait until the caller thread invokes 'wait()'
					synchronized (state) {
						if (state.get()) {
							//execute the post
							post.post(l, t);
							//prevent the post to be executed more than one
							state.set(false);
							//notify the caller thread
							state.notify();
						}
					}
				});
			}
			try {
				//until ether interrupted or notified
				state.wait();
			} catch (InterruptedException ignored) {
			}

			//if this thread got interrupted
			if (state.get()) {
				post.post(this, false);
				state.set(false);
			}
		}
		return this;
	}

	/**
	 * Make this loop (specifically the current thread looping) do the post given If this loop is alive. And WAIT for
	 * the loop to do it. If this loop isn't alive then the given {@code post} will be invoked in the caller thread with
	 * {@code loopingThread} argument passed as false.
	 * <br>
	 * Note: this may not be useful if this loop rapidly starts and finishes.
	 * <br>
	 * Note: no matter what. The given post will be invoked.
	 *
	 * @param post to be done by this loop.
	 * @return this.
	 * @throws NullPointerException   if the given {@code post} is null.
	 * @throws IllegalThreadException if the {@code caller thread} is the current thread of this loop.
	 */
	public Loop<C> synchronouslyIfAlive(SynchronizedPost<Loop<C>> post) {
		this.assertNotRecursiveThreadCall();
		Objects.requireNonNull(post, "post");

		//true → a post should be done | false → no post should be done
		AtomicBoolean state = new AtomicBoolean(true);

		//prevent lock leakage
		synchronized (state) {
			boolean w;
			//block the loop from finishing/starting
			synchronized (this.thread) {
				synchronized (this.posts) {
					if (w = this.isAlive())
						this.posts.add((SynchronizedPost) (loop, loopingThread) -> {
							//wait until the caller thread invokes 'wait()'
							synchronized (state) {
								//if the thread not interrupted
								if (state.get()) {
									post.post(loop, loopingThread);
									//tell the post that we have finished the post
									state.set(false);
									state.notify();
								}
							}
						});
				}
			}
			if (w)
				try {
					//wait for the post to be posted
					state.wait();
				} catch (InterruptedException ignored) {
				}

			//if no thread or if this thread got interrupted
			if (state.get()) {
				state.set(false);
				post.post(this, false);
			}
		}
		return this;
	}

	/**
	 * Make this loop (specifically the current thread looping) do the post given and WAIT for the loop to do it. If the
	 * loop didn't execute the given post within the given timeout, the post will be removed from this loop and invoked
	 * by the caller thread with {@code loopingThread} argument passed as false.
	 * <br>
	 * Note: no matter what, the given {@code post} should be invoked.
	 *
	 * @param timeout to wait for the post to be invoked (milli seconds).
	 * @param post    to do with the looping thread.
	 * @return this.
	 * @throws NullPointerException     if the given {@code post} is null.
	 * @throws IllegalArgumentException if the given {@code timout} is negative.
	 * @throws IllegalThreadException   if the {@code caller thread} is the current thread of this loop.
	 */
	public Loop<C> synchronouslyWithin(long timeout, SynchronizedPost<Loop<C>> post) {
		this.assertNotRecursiveThreadCall();
		Objects.requireNonNull(post, "post");
		if (timeout < 0)
			throw new IllegalArgumentException("timeout value is negative");

		//true → the post should be done | false → the alter post shouldn't be done
		AtomicBoolean state = new AtomicBoolean(true);

		//prevent lock leakage
		synchronized (state) {
			synchronized (this.posts) {
				this.posts.add((SynchronizedPost) (loop, loopingThread) -> {
					//Waiting for the caller thread invokes 'wait()'
					synchronized (state) {
						//Checking if the thread still waiting
						if (state.get()) {
							post.post(loop, loopingThread);
							//tell the caller thread that the post have been posted
							state.set(false);
							state.notify();
						}
					}
				});
			}

			try {
				//Waiting to the looping thread to interrupt us
				state.wait(timeout);
			} catch (InterruptedException ignored) {
			}

			//if the post still not posted
			if (state.get()) {
				//Telling the looping thread that we are leaving :(
				state.set(false);
				post.post(this, false);
			}
		}
		return this;
	}

	/**
	 * Start this loop with a new thread. If this loop already running. Then the new thread will wait until the previous
	 * thread ends the loop.
	 *
	 * @return this.
	 */
	public Loop<C> thread() {
		new Thread(this::start).start();
		return this;
	}

	/**
	 * Start this loop with a new thread. If this loop already running. Then the new thread will wait until the previous
	 * thread ends the loop.
	 *
	 * @param state initial state.
	 * @return this.
	 * @throws NullPointerException if the given {@code state} is null.
	 */
	public Loop<C> thread(String state) {
		Objects.requireNonNull(state, "state");
		new Thread(() -> this.start(state)).start();
		return this;
	}

	/**
	 * Invoke all codes of this loop (ONCE each). While calling {@link #tick()} before and after each code and terminate
	 * if {@link #tick()} returns false.
	 *
	 * @param item to pass it to the next code.
	 * @return true, if this loop still allowed to be alive.
	 */
	protected boolean next(Object item) {
		synchronized (this.code) {
			Iterator<Code> iterator = this.code.iterator();
			while (this.tick() && iterator.hasNext())
				iterator.next().run(this, item);
			//invoker.invoke(iterator.next());, but, yeah, it will make a lot of type mismatches
		}
		return this.tick();
	}

	/**
	 * Do things that should be done by this loop (except the loop code). This method is the base for any code
	 * invocation other than the looping code.
	 *
	 * @return true, if this loop still allowed to be alive.
	 */
	protected boolean tick() {
		//get the lock of the posts list
		synchronized (this.posts) {
			if (!this.posts.isEmpty())
				//do the posts
				this.posts.removeIf(post -> !post.post(this, true));
		}

		//get the lock of the state reference
		synchronized (this.state) {
			switch (this.state.get()) {
				case Loop.SLEEP:
					break;
				case Loop.BREAK:
					return false;
				case Loop.CONTINUE:
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
	 * Make sure that the {@code caller thread} isn't the thread of this loop.
	 */
	private void assertNotRecursiveThreadCall() {
		synchronized (this.thread) {
			Thread current = Thread.currentThread();
			if (current == this.thread.get())
				throw new IllegalThreadException(current + " is the thread of this loop");
		}
	}

	/**
	 * The looping cod. call {@link #next(Object)} inside the loop to invoke the code of this loop. Break the loop when
	 * it returns false.
	 */
	protected abstract void loop();

	/**
	 * A functional interface for creating a code for a loop.
	 *
	 * @param <L> the type of the loop this code is targeting.
	 */
	@FunctionalInterface
	public interface Code<L extends Loop> {
		/**
		 * Perform this loop code with the given item. Get called when a loop is executing its code and this code is
		 * added to its code.
		 *
		 * @param loop the loop executing this code.
		 * @param item the item that the loop is iterating (maybe null).
		 * @throws NullPointerException if the given {@code loop} is null.
		 */
		void run(L loop, Object item);
	}

	/**
	 * A functional interface for creating a one-time executable code for a loop.
	 *
	 * @param <L> the type of the loop this post is targeting.
	 */
	@FunctionalInterface
	public interface Post<L extends Loop> {
		/**
		 * Perform this post. Get called when this post is posted at a loop and that loop executed this post.
		 *
		 * @param loop          the loop executing this code.
		 * @param loopingThread true, if this post is executed by the thread of a loop this post is posted at, false
		 *                      otherwise.
		 * @return true, to not remove the post from the loop after it get executed.
		 * @throws NullPointerException if the given {@code loop} is null while the given {@code loopingThread} is
		 *                              true.
		 */
		boolean post(L loop, boolean loopingThread);
	}

	/**
	 * A post that should be invoked synchronously between the requester-thread and the loop-thread.
	 * <br>
	 * Note: the synchronization stuff is depends on the loop not the post. This is just an interface to categorize the
	 * types of posts.
	 *
	 * @param <L> the type of the loop that will execute this post.
	 */
	@FunctionalInterface
	public interface SynchronizedPost<L extends Loop> extends Post<L> {
		@Override
		default boolean post(L loop, boolean loopingThread) {
			this.onPost(loop, loopingThread);
			return false;
		}

		/**
		 * Perform this post. It should be invoked synchronously between the requester-thread and the loop-thread. Get
		 * called when this post is posted at a loop and that loop start executing this post.
		 *
		 * @param loop          the loop executing this code.
		 * @param loopingThread true, if this post is executed by the thread of a loop this post is posted at, false
		 *                      otherwise.
		 * @throws NullPointerException if the given {@code loop} is null while the given {@code loopingThread} is
		 *                              true.
		 */
		void onPost(L loop, boolean loopingThread);
	}
}
