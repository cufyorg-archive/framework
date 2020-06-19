package cufy.concurrent;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("JavaDoc")
public class LoopTest {
	@Test(timeout = 700)
	public void join() {
		Boolean[] results = new Boolean[4];
		Forever forever = (Forever) new Forever().thread().pair();

		forever.post((loop, loopingThread) -> {
			try {
				//a working loop should do its posts
				results[0] = true;
				Thread.sleep(300);
				loop.notify(Loop.BREAK);
			} catch (InterruptedException ignored) {
			}
			return false;
		});

		//a 300millis sleeping loop should be sleeping, even after 100millis
		forever.join(loop -> results[1] = true, 100);
		//a 300millis sleeping loop should be done sleeping after 600millis
		forever.join(loop -> results[2] = false, 500);

		//a successfully joined loop should be dead
		results[3] = !forever.isAlive();

		Assert.assertTrue("A working loop should do its posts", results[0]);
		Assert.assertTrue("A 300millis sleeping loop should be sleeping, even after 100millis", results[1]);
		Assert.assertNull("A 300millis sleeping loop should be done sleeping after 600millis", results[2]);
		Assert.assertTrue("A successfully joined loop should be dead", results[3]);
	}

	@Test(timeout = 5000)
	public void lifecycle() {
		Forever forever = new Forever();
		//there is a loop
		Assert.assertFalse("The loop started while it shouldn't be", forever.isAlive());
		forever.thread();
		//there is maybe a running loop
		forever.pair();
		//sure there is a running loop
		Assert.assertTrue("The loop not started while it should be", forever.isAlive());
		forever.notify(Loop.BREAK);
		//there is maybe a running loop
		forever.join();
		//sure there is no running loop
		Assert.assertFalse("The loop not started", forever.isAlive());
	}

	@Test(timeout = 700)
	public void post() throws InterruptedException {
		Boolean[] results = new Boolean[7];
		Forever forever = (Forever) new Forever().thread().pair();

		//a loop should be alive when threaded then paired
		results[0] = forever.isAlive();

		Thread thread = forever.getThread().get();

		forever.post((loop, loopingThread) -> {
			//a loop should execute posts with its running thread
			results[1] = thread == Thread.currentThread();
			return false;
		});

		forever.notify(Loop.BREAK).join();

		//the loop should be dead when broke then joined
		results[2] = !forever.isAlive();

		forever.thread(Loop.CONTINUE).pair();

		forever.postIfAlive((loop, loopingThread) -> {
			//a working loop should execute its posts and not ignore them
			results[3] = true;
			return false;
		});
		forever.postWithin(100, (loop, loopingThread) -> {
			//an empty healthy loop should execute any post within 100millis
			results[4] = loopingThread;
			return false;
		});

		forever.notify(Loop.BREAK).join();

		forever.postIfAlive((loop, loopingThread) -> {
			//a dead loop should reject any post
			results[5] = !loopingThread;
			return false;
		});
		forever.postWithin(100, ((loop, loopingThread) -> {
			//a dead loop should not be able to execute timeout-posts even if infinite amount of seconds passed
			results[6] = !loopingThread;
			return false;
		}));

		//wait for the timed posts to be finished, wait yeah wait
		Thread.sleep(300);

		Assert.assertTrue("A loop should be alive when threaded then paired", results[0]);
		Assert.assertTrue("A loop should execute posts with its running thread", results[1]);
		Assert.assertTrue("A loop should be dead when broke then joined", results[2]);
		Assert.assertTrue("A working loop should execute its posts and not ignore them", results[3]);
		Assert.assertTrue("An empty healthy loop should execute any post within 100millis", results[4]);
		Assert.assertTrue("A dead loop should reject any post", results[5]);
		Assert.assertTrue("A dead loop should not be able to execute timeout-posts even if infinite amount of seconds passed", results[6]);
	}

	@Test(timeout = 400)
	public void synchronously() throws InterruptedException {
		Boolean[] results = new Boolean[3];
		Forever forever = (Forever) new Forever().thread().pair();

		forever.synchronously((loop, loopingThread) -> {
			//a threaded then joined loop should be able to do its posts
			results[0] = true;
		});

		forever.notify(Loop.SLEEP);
		Thread.sleep(100);

		forever.synchronouslyWithin(100, ((loop, loopingThread) -> {
			//a sleeping loop shouldn't be able to do its posts before it wakes up
			results[1] = !loopingThread;
		}));

		forever.notify(Loop.BREAK).join();

		forever.synchronouslyIfAlive(((loop, loopingThread) -> {
			//a broken then joined loop shouldn't be able to do its posts
			results[2] = !loopingThread;
		}));

		Assert.assertTrue("A threaded then joined loop should be able to do its posts'", results[0]);
		Assert.assertTrue("A sleeping loop shouldn't be able to do its posts before it wakes up", results[1]);
		Assert.assertTrue("A broken then joined loop shouldn't be able to do its posts", results[2]);
	}
}
