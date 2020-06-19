package cufy.concurrent;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("JavaDoc")
public class InstructorTest {
	@Test(timeout = 1000)
	public void join() {
		Instructor instructor = new Instructor();
		Forever loop0 = new Forever(instructor::tick);
		Forever loop1 = new Forever(instructor::tick);

		instructor.thread(loop0);
		instructor.thread(loop1);

		instructor.pair();

		Assert.assertFalse("haven't paired correctly!", instructor.getLoops().isEmpty());

		loop0.pair();
		loop1.pair();

		Assert.assertTrue("Loops should be alive", loop0.isAlive() & loop1.isAlive());

		instructor.notify(Loop.BREAK);

		instructor.join();

		Assert.assertEquals("Group still have loops", 0, instructor.getLoops().size());
		Assert.assertFalse("Loops should be dead", loop0.isAlive() | loop1.isAlive());
	}

	@Test(timeout = 100)
	public void synchronously() {
		Instructor instructor = new Instructor();
		Forever forever = new Forever(instructor::tick);
		boolean[] results = new boolean[3];

		instructor.thread(forever).pair();

		//an instructor that did thread a loop should have a loops' size be more than 1
		results[0] = instructor.getLoops().size() == 1;
		//a loop threaded by an instructor then got paired should be alive
		results[1] = forever.isAlive();

		instructor.synchronously((ins, loop, loopingThread) -> {
			//an instructor with a working and ticking loop should be able to do its posts
			results[2] = true;
		});

		Assert.assertTrue("An instructor that did thread a loop should have a loops' size be more than 1", results[0]);
		Assert.assertTrue("A loop threaded by an instructor then got paired should be alive", results[1]);
		Assert.assertTrue("An instructor with a working and ticking loop should be able to do its posts", results[2]);

		//release resources
		instructor.notify(Loop.BREAK).join();
	}
}
