package cufy.concurrent;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("JavaDoc")
public class ForTest {
	@Test(timeout = 50)
	public void start() {
		String[] strings = {"A", "B", "C"};
		StringBuilder string = new StringBuilder(3);
		new For<>(0, i -> i < strings.length, i -> i + 1, (loop, i) -> string.append(strings[i])).append((loop, i) -> strings[i] = null).start();
		Assert.assertEquals("For not worked correctly", "ABC", string.toString());
		Assert.assertArrayEquals("For not worked correctly", new String[3], strings);
	}
}
