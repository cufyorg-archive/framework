package cufy.concurrent;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("JavaDoc")
public class ForeverTest {
	@Test(timeout = 110)
	public void start() throws InterruptedException {
		Thread main = Thread.currentThread();
		Forever parallel = new Forever();
		AtomicBoolean state = new AtomicBoolean(true);
		Thread thread = new Thread(() -> {
			parallel.start();
			state.set(false);
		});
		thread.start();
		Thread.sleep(50);
		parallel.notify(Loop.BREAK);
		parallel.join();
		Thread.sleep(10);
		Assert.assertFalse("Thread death took too long after the death of the loop", state.get());
	}
}
