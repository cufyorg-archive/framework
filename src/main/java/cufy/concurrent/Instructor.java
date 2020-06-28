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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * Used to be the communication method between 2 (or more) threads (loops). Used as tasks list for unknown count unknown
 * state loops.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.0.a ~2019.05.18
 */
@SuppressWarnings("ReturnOfThis")
public class Instructor {
	/**
	 * Loops that linked to this.
	 * <br>
	 * Note: synchronized use only.
	 */
	protected final List<Loop> loops = new ArrayList<>(10);
	/**
	 * All undone posts of this instructor.
	 * <br>
	 * Note: synchronized use only.
	 */
	protected final List<Post> posts = new ArrayList<>(10);
	/**
	 * The first position of any further loop get started by this.
	 * <br>
	 * Note: synchronized use only.
	 */
	protected final AtomicReference<String> state = new AtomicReference<>(Loop.CONTINUE);

	@Override
	public String toString() {
		return super.toString() + " " + this.loops;
	}

	/**
	 * Get the {@link #loops} of this loop.
	 *
	 * @return the loops list of this instructor.
	 * @see #getLoops(Consumer)
	 */
	public List<Loop> getLoops() {
		return this.loops;
	}

	/**
	 * Do the given {@code action} to the {@link #loops} of this. With locked access.
	 * <br>
	 * Note: this may not be useful if this loop rapidly starts and finishes
	 *
	 * @param action to be done to the loops list of this.
	 * @return this.
	 * @throws NullPointerException if the given {@code action} is null.
	 * @see #getLoops()
	 */
	public Instructor getLoops(Consumer<List<Loop>> action) {
		Objects.requireNonNull(action, "action");

		synchronized (this.loops) {
			action.accept(this.loops);
		}

		return this;
	}

	/**
	 * Get the {@link #posts} of this loop.
	 *
	 * @return the posts list of this instructor.
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
	public Instructor getPosts(Consumer<List<Post>> action) {
		Objects.requireNonNull(action, "action");

		synchronized (this.posts) {
			action.accept(this.posts);
		}

		return this;
	}

	/**
	 * Get the {@link #state} of this loop.
	 *
	 * @return the state instance of this instructor.
	 * @see #getState(Consumer)
	 */
	public AtomicReference<String> getState() {
		return this.state;
	}

	/**
	 * Do the given {@code action} to the {@link #state} of this. With locked access.
	 *
	 * @param action to be done to the state instance of this.
	 * @return this.
	 * @throws NullPointerException if the given {@code action} is null.
	 * @see #getState()
	 */
	public Instructor getState(Consumer<String> action) {
		Objects.requireNonNull(action, "action");

		synchronized (this.state) {
			String state = this.state.get();
			action.accept(state);
		}

		return this;
	}

	/**
	 * Determine if this instructor is alive or not.
	 * <br>
	 * Note: this may not be useful if this loop rapidly starts and finishes.
	 *
	 * @return true, if this instructor is alive.
	 * @see Loop#isAlive()
	 */
	public boolean isAlive() {
		synchronized (this.posts) {
			return !this.posts.isEmpty();
		}
	}

	/**
	 * Waits for all loops of this instructor to die.
	 * <br>
	 * Note: this may not be useful if this loop rapidly starts and finishes.
	 *
	 * @return this.
	 * @see Loop#join()
	 */
	public Instructor join() {
		//long time waiting method
		synchronized (this.loops) {
			this.loops.forEach(Loop::join);
		}

		//micro time waiting method
		boolean w;
		do
			synchronized (this.loops) {
				w = !this.loops.isEmpty();
			} while (w);

		return this;
	}

	/**
	 * Waits at most {@code millis} milliseconds for all loops of this instructor to die.
	 * <br>
	 * Note: this may not be useful if this loop rapidly starts and finishes.
	 *
	 * @param alter  what to do when the timeout is done.
	 * @param millis the time to wait in milliseconds.
	 * @return this.
	 * @throws IllegalArgumentException if the given {@code millis} is negative.
	 * @throws NullPointerException     if the given {@code alter} is null.
	 * @see Loop#join(Consumer, long)
	 */
	public Instructor join(Consumer<Instructor> alter, long millis) {
		Objects.requireNonNull(alter, "alter");
		if (millis > 0L)
			throw new IllegalArgumentException("timeout value is negative");

		//the deadline
		long deadline = System.currentTimeMillis() + millis;
		synchronized (this.loops) {
			//foreach loop
			for (Loop loop : this.loops) {
				//time to wait on the next loop
				long wait = deadline - System.currentTimeMillis();

				//if the deadline passed
				if (wait <= 0L) {
					alter.accept(this);
					return this;
				}

				//join the next loop within the allowed wait millis
				loop.join(l -> {
				}, wait);
			}
		}

		return this;
	}

	/**
	 * Update the state of all the running loops, and the loops that will run in the future in this instructor.
	 *
	 * @param state the new state.
	 * @return this.
	 * @throws NullPointerException if the given {@code state} is null.
	 * @see Loop#notify(String)
	 */
	public Instructor notify(String state) {
		Objects.requireNonNull(state, "state");

		synchronized (this.loops) {
			synchronized (this.state) {
				this.state.set(state);
				//noinspection NestedMethodCall
				this.loops.forEach(loop -> loop.notify(state));
			}
		}
		return this;
	}

	/**
	 * Wait until make sure that there is a running loop on this instructor. By adding a new post and wait until get
	 * executed. Useful when starting a new loop on a new thread. To make sure that that loop started running.
	 *
	 * @return this.
	 * @see Loop#pair()
	 */
	public Instructor pair() {
		return this.synchronously((i, l, t) -> {
		});
	}

	/**
	 * Make a loop of this instructor do the given post.
	 *
	 * @param post to be done by a loop of this instructor.
	 * @return this.
	 * @throws NullPointerException if the given {@code post} is null.
	 * @see Loop#post(Loop.Post)
	 */
	public Instructor post(Post post) {
		Objects.requireNonNull(post, "post");

		synchronized (this.posts) {
			this.posts.add(post);
		}
		return this;
	}

	/**
	 * Post the given {@code post} to this instructor if this instructor has any alive loop. And make that loop
	 * (specifically the current thread looping) do the post given. If there is no running then the post will be invoked
	 * in a new thread with the argument {@code loopingThread} passed as false.
	 * <br>
	 * Note: no matter what, the post given should be ether added or invoked.
	 *
	 * @param post to be done by a loop of this instructor.
	 * @return this.
	 * @throws NullPointerException if the given {@code post} is null.
	 * @see Loop#postIfAlive(Loop.Post)
	 */
	public Instructor postIfAlive(Post post) {
		Objects.requireNonNull(post, "post");

		synchronized (this.loops) {
			synchronized (this.posts) {
				if (this.isAlive()) {
					this.posts.add(post);
					return this;
				}
			}
		}
		//noinspection NestedMethodCall,ChainedMethodCall
		new Thread(() -> post.post(this, null, false)).start();
		return this;
	}

	/**
	 * Post the given {@code post} to this instructor and remove it if no loop executed it within the timeout given. If
	 * the timeout ended before executing it, the post will be invoked in a new thread with the argument {@code
	 * loopingThread} passed as false.
	 * <br>
	 * Note: no matter what, the post given should be invoked.
	 *
	 * @param timeout the timeout (in milli seconds).
	 * @param post    to be done by a loop of this instructor.
	 * @return this.
	 * @throws NullPointerException     if the given {@code post} is null.
	 * @throws IllegalArgumentException if the given {@code timeout} is negative.
	 * @see Loop#postWithin(long, Loop.Post)
	 */
	public Instructor postWithin(long timeout, Post post) {
		Objects.requireNonNull(post, "post");
		if (timeout < 0L)
			throw new IllegalArgumentException("timeout value is negative");

		AtomicBoolean state = new AtomicBoolean(true);
		AtomicBoolean applied = new AtomicBoolean(false);

		synchronized (state) {
			synchronized (this.posts) {
				this.posts.add((instructor, loop, loopingThread) -> {
					if (applied.get())
						return post.post(instructor, loop, loopingThread);
					synchronized (state) {
						if (state.get()) {
							boolean w = post.post(instructor, loop, loopingThread);
							state.set(false);
							state.notifyAll();
							applied.set(true);
							return w;
						}
					}
					return false;
				});
				//noinspection ChainedMethodCall
				new Thread(() -> {
					synchronized (state) {
						if (state.get()) {
							try {
								state.wait(timeout);
							} catch (InterruptedException ignored) {
							}
							if (state.get()) {
								state.set(false);
								post.post(this, null, false);
							}
						}
					}
				}).start();
			}
		}
		return this;
	}

	/**
	 * Start a loop using this instructor.
	 *
	 * @param loop to be started.
	 * @return this.
	 * @throws NullPointerException if the given {@code loop} is null.
	 * @see Loop#start()
	 */
	public Instructor start(Loop loop) {
		Objects.requireNonNull(loop, "loop");

		synchronized (this.loops) {
			synchronized (this.state) {
				this.loops.add(loop);
			}
		}

		String state = this.state.get();
		loop.start(state);

		synchronized (this.loops) {
			this.loops.remove(loop);
		}
		return this;
	}

	/**
	 * Make a loop of this instructor do the given post. And WAIT for that loop to do it.
	 *
	 * @param post to be done by a loop of this instructor.
	 * @return this.
	 * @throws NullPointerException if the given {@code post} is null.
	 * @see Loop#synchronously(Loop.SynchronizedPost)
	 */
	public Instructor synchronously(SynchronizedPost post) {
		Objects.requireNonNull(post, "post");

		AtomicBoolean state = new AtomicBoolean(true);

		synchronized (state) {
			synchronized (this.posts) {
				this.posts.add((SynchronizedPost) (instructor, loop, loopingThread) -> {
					synchronized (state) {
						if (state.get()) {
							post.post(instructor, loop, loopingThread);
							state.notifyAll();
						}
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
	 * Make a loop in this instructor do the post given If there is loop alive in this instructor. And WAIT for the loop
	 * to do it. If there is no loop in this instructor then the given {@code post} will be invoked in the caller thread
	 * with the argument {@code loopingThread} passed as false.
	 * <br>
	 * Note: this may not be useful if loops rapidly starts and finishes in this instructor.
	 * <br>
	 * Note: no matter what. The given post will be invoked.
	 *
	 * @param post to be done by a loop of this instructor.
	 * @return this.
	 * @throws NullPointerException if the given {@code post} is null.
	 * @see Loop#synchronouslyIfAlive(Loop.SynchronizedPost)
	 */
	public Instructor synchronouslyIfAlive(SynchronizedPost post) {
		Objects.requireNonNull(post, "post");

		AtomicBoolean state = new AtomicBoolean();

		synchronized (state) {
			boolean w;
			synchronized (this.loops) {
				synchronized (this.posts) {
					//noinspection AssignmentUsedAsCondition,NestedAssignment
					if (w = this.isAlive())
						this.posts.add((SynchronizedPost) (instructor, loop, loopingThread) -> {
							synchronized (state) {
								if (state.get()) {
									post.post(instructor, loop, loopingThread);
									state.set(false);
									state.notifyAll();
								}
							}
						});
				}
				if (w)
					try {
						state.wait();
					} catch (InterruptedException ignored) {
					}
				if (state.get()) {
					state.set(false);
					post.post(this, null, false);
				}
			}
		}
		return this;
	}

	/**
	 * Make a loop in this instructor do the post given and WAIT for the loop to do it. If no loop executed the given
	 * {@code post} within the given timeout, the post will be removed from this instructor and invoked by the caller
	 * thread with the argument {@code loopingThread} passed as false.
	 * <br>
	 * Note: no matter what, the given {@code post} should be invoked.
	 *
	 * @param timeout to wait for the post to be invoked (milli seconds).
	 * @param post    to be done by a loop of this instructor.
	 * @return this.
	 * @throws NullPointerException     if the given {@code post} is null.
	 * @throws IllegalArgumentException if the given {@code timeout} is negative.
	 * @see Loop#synchronouslyWithin(long, Loop.SynchronizedPost)
	 */
	public Instructor synchronouslyWithin(long timeout, SynchronizedPost post) {
		Objects.requireNonNull(post, "post");
		if (timeout < 0L)
			throw new IllegalArgumentException("timeout value is negative");

		AtomicBoolean state = new AtomicBoolean();

		synchronized (state) {
			synchronized (this.posts) {
				this.posts.add((SynchronizedPost) (instructor, loop, loopingThread) -> {
					synchronized (state) {
						if (state.get()) {
							post.post(instructor, loop, loopingThread);
							state.set(false);
							state.notifyAll();
						}
					}
				});
			}
			try {
				state.wait();
			} catch (InterruptedException ignored) {
			}
			if (state.get()) {
				state.set(false);
				post.post(this, null, false);
			}
		}
		return this;
	}

	/**
	 * Start the given {@code loop} with a new thread.
	 *
	 * @param loop to be started.
	 * @return the new started thread.
	 * @throws NullPointerException if the given {@code loop} is null.
	 * @see Loop#thread()
	 */
	public Instructor thread(Loop loop) {
		Objects.requireNonNull(loop, "loop");
		new Thread(() -> this.start(loop)).start();
		return this;
	}

	/**
	 * Do the posts posted on this instructor. With the loop param of the given loop.
	 *
	 * @param loop the ticker loop.
	 * @return this.
	 * @throws NullPointerException   if the given {@code loop} is null.
	 * @throws IllegalThreadException if the given {@code loop} is not in this instructor, or the {@code caller thread}
	 *                                isn't the thread of the given {@code loop}.
	 */
	public Instructor tick(Loop loop) {
		Objects.requireNonNull(loop, "loop");
		if (!this.loops.contains(loop))
			throw new IllegalThreadException("loop is not in this instructor");
		if (!loop.isCurrentThread())
			throw new IllegalThreadException("loop thread isn't the thread of the given loop");

		synchronized (this.posts) {
			this.posts.removeIf(post -> !post.post(this, loop, true));
		}
		return this;
	}

	/**
	 * A post that can be executed from a loop in an instructor.
	 */
	@FunctionalInterface
	public interface Post {
		/**
		 * Perform this post. Get called when this post is posted at an instructor and one of that instructor's loops
		 * start executing this post.
		 *
		 * @param instructor    that this post have been posted at.
		 * @param loop          the loop executing this code.
		 * @param loopingThread true, if this post is executed by the thread of a loop this post is posted at, false
		 *                      otherwise.
		 * @return true, to not remove the post from the instructor after it get executed.
		 * @throws NullPointerException if the given {@code instructor} or {@code loop} is null while the given {@code
		 *                              loopingThread} is true.
		 */
		boolean post(Instructor instructor, Loop loop, boolean loopingThread);
	}

	/**
	 * A post that should be invoked synchronously between the requester-thread and the loop-thread.
	 * <br>
	 * Note: the synchronization stuff is depends on the instructor not the post. This is just an interface to
	 * categorize the types of posts.
	 */
	@FunctionalInterface
	public interface SynchronizedPost extends Post {
		@Override
		default boolean post(Instructor instructor, Loop loop, boolean loopingThread) {
			this.onPost(instructor, loop, loopingThread);
			return false;
		}

		/**
		 * Perform this post. It should be invoked synchronously between the requester thread and the looping thread.
		 * Get called when this post is posted at an instructor one of that instructor's loops start executing this
		 * post.
		 *
		 * @param instructor    that this post have been posted at.
		 * @param loop          the loop executing this code.
		 * @param loopingThread true, if this post is executed by the thread of a loop this post is posted at, false
		 *                      otherwise.
		 * @throws NullPointerException if the given {@code instructor} or {@code loop} is null while the given {@code
		 *                              loopingThread} is true.
		 */
		void onPost(Instructor instructor, Loop loop, boolean loopingThread);
	}
}
