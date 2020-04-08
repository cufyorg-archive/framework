package cufy.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

@SuppressWarnings("JavaDoc")
public class UnmodifiableGroupTest {
	@Test
	public void iterator() {
		//TODO
	}

	@Test
	public void size() {
		//TODO
	}

	@Test
	public void subgroup() {
		String[] strings = {"my abc", "my def", "my ghi", "abc", "def", "ghi"};
		UnmodifiableGroup<String> constants = new UnmodifiableGroup<>(strings);
		UnmodifiableGroup<String> my = constants.subGroup("my", s -> s.startsWith("my"));
		UnmodifiableGroup<String> abc = constants.subGroup("abc", s -> s.contains("abc"));

		Assert.assertEquals("Wrong size", 3, my.size());
		Assert.assertEquals("Wrong size", 2, abc.size());
		Assert.assertTrue("Should contains all", my.containsAll(Arrays.asList("my abc", "my def", "my ghi")));
		Assert.assertTrue("Should contains all", abc.containsAll(Arrays.asList("my abc", "abc")));

		UnmodifiableGroup<String> myAgain = constants.subGroup("my", s -> false);
		UnmodifiableGroup<String> abcAgain = constants.subGroup("abc", s -> false);

		Assert.assertSame("Didn't returned the already resolved object", my, myAgain);
		Assert.assertSame("Didn't returned the already resolved object", abc, abcAgain);

		//TODO
	}

	@Test
	public void toArray() {
		//TODO
	}
}
