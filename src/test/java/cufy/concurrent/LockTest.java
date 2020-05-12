package cufy.concurrent;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("JavaDoc")
public class LockTest {
	@Test(timeout = 5000)
	public void lock_release_close() throws InterruptedException {
		//a thing to lock
		AtomicInteger integer = new AtomicInteger(0);
		//the lock to be tested
		Lock<Object> lock = new Lock<>(integer);

		//a thread competing on the lock
		Forever forever = new Forever(loop -> {
			synchronized (integer) {
				integer.addAndGet(1);
			}
		});

		//gain the lock
		lock.lock();
		//start the thread
		forever.thread();
		Assert.assertEquals("Lock not locked: after thread()", 0, integer.get());
		//even after sometime
		forever.synchronously(l -> {}, l -> {}, 50);
		Assert.assertEquals("Lock not locked: after pair()", 0, integer.get());

		//release the lock
		lock.unlock();
		Assert.assertNotEquals("Lock not released", 0, integer.get());

		lock.lock();
		int i = integer.get();

		Thread.sleep(50);

		Assert.assertEquals("Lock not locked", i, integer.get());

		//first close the lock to be able to notify the loop
		lock.close();
		forever.notify(Loop.BREAK).join();

		Assert.assertFalse("Lock not closed", lock.isAlive());

		try {
			lock.lock();
			Assert.fail("Already closed");
		} catch (Exception ignored) {
		}
	}

	@Test
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
