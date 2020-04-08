package cufy.concurrent;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("JavaDoc")
public class InstructorTest {
	@Test(timeout = 50)
	public void join() {
		Instructor group = new Instructor();
		Forever parallel1 = new Forever(group::tick);
		Forever forever2 = new Forever(group::tick);

		group.thread(parallel1);
		group.thread(forever2);
		group.pair();

		Assert.assertEquals("Group should have 2 loops", 2, group.getLoops().size());
		Assert.assertTrue("Loops should be alive", parallel1.isAlive() & forever2.isAlive());

		group.notify(Loop.BREAK);

		group.join();

		Assert.assertEquals("Group still have loops", 0, group.getLoops().size());
		Assert.assertFalse("Loops should be dead", parallel1.isAlive() | forever2.isAlive());
	}

	@Test(timeout = 20)
	public void synchronously() {
		Instructor group = new Instructor();
		Forever parallel = new Forever(group::tick);
		boolean[] w = {false};

		group.thread(parallel);
		group.pair();

		Assert.assertEquals("Group should have 1 loop", 1, group.getLoops().size());
		Assert.assertTrue("loop should be alive", parallel.isAlive());

		group.synchronously((g, loop) -> w[0] = true);

		Assert.assertTrue("Value not changed synchronously", w[0]);

		group.notify(Loop.BREAK);
	}
}
