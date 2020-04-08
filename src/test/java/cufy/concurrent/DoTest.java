package cufy.concurrent;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("JavaDoc")
public class DoTest {
	@Test
	public void start() {
		AtomicInteger i = new AtomicInteger();
		new Do(d -> i.set(1)).start();
		Assert.assertEquals("Do not started", 1, i.get());
	}
}
