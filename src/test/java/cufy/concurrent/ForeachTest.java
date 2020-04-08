package cufy.concurrent;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("JavaDoc")
public class ForeachTest {
	@Test(timeout = 50)
	public void start() {
		StringBuilder builder = new StringBuilder(3);
		List<String> list = Arrays.asList("A", "B", "C");
		List<String> list1 = new ArrayList<>(3);
		new Foreach<>(list, (loop, s) -> builder.append(s)).append((loop, s) -> list1.add(s)).start();

		Assert.assertEquals("Foreach not looped correctly", "ABC", builder.toString());
		Assert.assertEquals("Foreach not looped correctly", list, list1);
	}
}
