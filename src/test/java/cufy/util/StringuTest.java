package cufy.util;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({"JavaDoc", "SpellCheckingInspection"})
public class StringuTest {
	@Test
	public void all() {
		String s = "Sulaiman";

		Assert.assertNull("Contains not detected", Stringu.all(s, "Su", "ma", "n", "ai", "im"));
		Assert.assertEquals("Missing query not detected", "Rx", Stringu.all(s, "Su", "ma", "Rx", "er"));
	}

	@Test
	public void any() {
		String s = "Sulaiman";

		Assert.assertEquals("Query not detected", "la", Stringu.any(s, "la", "ma", "Cha"));
		Assert.assertNull("Shouldn't detected anything", Stringu.any(s, "Ra", "Ka", "e3", "po"));
	}

	@Test
	public void repeat() {
		String str = Stringu.repeat("abc", " ", 3);
		Assert.assertEquals("Wrong string build", "abc abc abc", str);

		Assert.assertEquals("Wrong string build", "", Stringu.repeat("a", "x", 0));
		Assert.assertEquals("Wrong string build", "a", Stringu.repeat("a", "x", 1));
		Assert.assertEquals("Wrong string build", "axa", Stringu.repeat("a", "x", 2));
		Assert.assertEquals("Wrong string build", "axaxa", Stringu.repeat("a", "x", 3));

		Assert.assertEquals("Wrong string build", "", Stringu.repeat("a", 0));
		Assert.assertEquals("Wrong string build", "a", Stringu.repeat("a", 1));
		Assert.assertEquals("Wrong string build", "aa", Stringu.repeat("a", 2));
		Assert.assertEquals("Wrong string build", "aaa", Stringu.repeat("a", 3));
	}
}
