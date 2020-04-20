package cufy.util;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({"JavaDoc", "SpellCheckingInspection"})
public class StringsTest {
	@Test
	public void all() {
		String s = "Sulaiman";

		Assert.assertNull("Contains not detected", Strings.all(s, "Su", "ma", "n", "ai", "im"));
		Assert.assertEquals("Missing query not detected", "Rx", Strings.all(s, "Su", "ma", "Rx", "er"));
	}

	@Test
	public void any() {
		String s = "Sulaiman";

		Assert.assertEquals("Query not detected", "la", Strings.any(s, "la", "ma", "Cha"));
		Assert.assertNull("Shouldn't detected anything", Strings.any(s, "Ra", "Ka", "e3", "po"));
	}

	@Test
	public void repeat() {
		String str = Strings.repeat("abc", " ", 3);
		Assert.assertEquals("Wrong string build", "abc abc abc", str);

		Assert.assertEquals("Wrong string build", "", Strings.repeat("a", "x", 0));
		Assert.assertEquals("Wrong string build", "a", Strings.repeat("a", "x", 1));
		Assert.assertEquals("Wrong string build", "axa", Strings.repeat("a", "x", 2));
		Assert.assertEquals("Wrong string build", "axaxa", Strings.repeat("a", "x", 3));

		Assert.assertEquals("Wrong string build", "", Strings.repeat("a", 0));
		Assert.assertEquals("Wrong string build", "a", Strings.repeat("a", 1));
		Assert.assertEquals("Wrong string build", "aa", Strings.repeat("a", 2));
		Assert.assertEquals("Wrong string build", "aaa", Strings.repeat("a", 3));
	}
}
