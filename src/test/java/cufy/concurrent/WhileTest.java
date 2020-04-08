package cufy.concurrent;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("JavaDoc")
public class WhileTest {
	@Test(timeout = 50)
	public void start() {
		StringBuilder builder = new StringBuilder(3);
		List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));
		Iterator<String> iterator = list.iterator();
		new While(iterator::hasNext, loop -> builder.append(iterator.next())).append(loop -> iterator.remove()).start();
		Assert.assertEquals("While not worked correctly", "ABC", builder.toString());
		Assert.assertTrue("While not worked correctly", list.isEmpty());
	}
}
