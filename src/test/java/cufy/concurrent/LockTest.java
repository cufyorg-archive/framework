package cufy.concurrent;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("JavaDoc")
public class LockTest {
	@Test(timeout = 50)
	public void lock_release_close() throws InterruptedException {
		AtomicInteger integer = new AtomicInteger(0);
		Lock<Object> lock = new Lock<>(integer);

		Forever parallel = new Forever(loop -> {
			synchronized (integer) {
				integer.addAndGet(1);
			}
		});

		parallel.thread();
		parallel.pair();

		lock.lock();

		int current = integer.get();

		Thread.sleep(5);

		Assert.assertEquals("Lock not locked", current, integer.get());

		lock.unlock();

		Thread.sleep(5);

		synchronized (integer) {
			Assert.assertNotEquals("Lock not released", current, integer.get());
		}

		parallel.notify(Loop.BREAK);
		parallel.join();
		lock.close();

		Assert.assertFalse("Lock not closed", lock.isAlive());

		try {
			lock.lock();
			Assert.fail("Already closed");
		} catch (Exception ignored) {
		}
	}

	@Test(timeout = 50)
	public void wrong_caller() throws InterruptedException {
		Lock[] lock = new Lock[1];

		Thread thread = new Thread(() -> lock[0] = new Lock<>());
		thread.start();
		thread.join();

		try {
			//noinspection CallToThreadRun
			lock[0].run();
			Assert.fail("Wrong caller test fail");
		} catch (Exception ignored) {
		}
		try {
			lock[0].lock();
			Assert.fail("Wrong caller test fail");
		} catch (Exception ignored) {
		}
		try {
			lock[0].unlock();
			Assert.fail("Wrong caller test fail");
		} catch (Exception ignored) {
		}
	}
}
