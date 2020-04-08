package cufy.concurrent;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({"JavaDoc"})
public class LoopTest {
	@Test(timeout = 700)
	public void join() {
		Boolean[] results = new Boolean[3];
		Forever parallel = new Forever();
		parallel.thread();
		parallel.pair();

		parallel.post(loop -> {
			try {
				results[0] = true;
				Thread.sleep(300);
				loop.notify(Loop.BREAK);
			} catch (InterruptedException ignored) {
			}
			return false;
		});

		parallel.join(loop -> results[1] = true, 100);
		parallel.join(loop -> results[2] = false, 500);

		Assert.assertEquals("State not changed. Not joined the loop", Loop.BREAK, parallel.getState().get());

		Assert.assertTrue("The loop not started", results[0]);
		Assert.assertTrue("The 1st join 'alter' not invoked while it should", results[1]);
		Assert.assertNull("The 2nd join 'alter' got invoked while it shouldn't", results[2]);
	}

	@Test(timeout = 5000)
	public void lifecycle() {
		Forever parallel = new Forever(loop -> {/*looping code*/});
		//there is a loop
		Assert.assertFalse("The loop started while it shouldn't be", parallel.isAlive());
		parallel.thread();
		//there is maybe a running loop
		parallel.pair();
		//sure there is a running loop
		Assert.assertTrue("The loop not started while it should be", parallel.isAlive());
		parallel.notify(Loop.BREAK);
		//there is maybe a running loop
		parallel.join();
		//sure there is no running loop
		Assert.assertFalse("The loop not started", parallel.isAlive());
	}

	@Test(timeout = 500)
	public void post() throws InterruptedException {
		Boolean[] results = new Boolean[6];
		Forever parallel = new Forever();
		parallel.thread();
		parallel.pair();

		results[0] = parallel.isAlive();

		Thread thread = parallel.getThread().get();

		parallel.post(loop1 -> {
			Assert.assertEquals("Wrong thread", thread, Thread.currentThread());
			return false;
		});

		parallel.notify(Loop.BREAK);
		parallel.join();

		results[1] = parallel.isAlive();

		parallel.thread(Loop.CONTINUE).pair();

		parallel.post(loop -> {
			results[2] = true;
			return false;
		}, loop -> Assert.fail("shouldn't be invoked"), 100);
		parallel.post(loop -> {
			results[3] = true;
			return false;
		}, loop -> Assert.fail("shouldn't be invoked"));

		parallel.notify(Loop.BREAK);
		parallel.join();

		parallel.post(loop -> {
			Assert.fail("shouldn't be invoked");
			return false;
		}, loop -> results[4] = true, 100);
		parallel.post(loop -> {
			Assert.fail("shouldn't be invoked");
			return false;
		}, loop -> results[5] = true);

		Thread.sleep(300);

		Assert.assertTrue("The loop not started", results[0]);
		Assert.assertFalse("The loop should be dead", results[1]);
		Assert.assertTrue("1st post's 'alter' not invoked", results[2]);
		Assert.assertTrue("2nd post's 'alter' not invoked", results[3]);
		Assert.assertTrue("3nd post's 'alter' not invoked while it should", results[4]);
		Assert.assertTrue("4th post's 'alter' not invoked while it should", results[5]);
	}

	@Test(timeout = 400)
	public void synchronously() throws InterruptedException {
		Boolean[] results = new Boolean[3];
		Forever parallel = new Forever();
		parallel.thread();
		parallel.pair();

		parallel.synchronously(loop -> results[0] = true);

		Assert.assertTrue("The post not invoked'", results[0]);

		parallel.notify(Loop.SLEEP);
		Thread.sleep(100);

		parallel.synchronously(loop -> Assert.fail("Shouldn't be invoked"), loop -> results[1] = true, 100);
		Assert.assertTrue("The 'alter' not invoked", results[1]);

		parallel.notify(Loop.BREAK).join();

		parallel.synchronously(loop -> Assert.fail("shouldn't be invoked"), loop -> results[2] = true);

		Assert.assertTrue("The 'alter' not invoked", results[2]);
	}
}
