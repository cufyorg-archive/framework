package cufy.util;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({"JavaDoc", "SpellCheckingInspection"})
public class StringzTest {
	@Test
	public void all() {
		String s = "Sulaiman";

		Assert.assertNull("Contains not detected", Stringz.all(s, "Su", "ma", "n", "ai", "im"));
		Assert.assertEquals("Missing query not detected", "Rx", Stringz.all(s, "Su", "ma", "Rx", "er"));
	}

	@Test
	public void any() {
		String s = "Sulaiman";

		Assert.assertEquals("Query not detected", "la", Stringz.any(s, "la", "ma", "Cha"));
		Assert.assertNull("Shouldn't detected anything", Stringz.any(s, "Ra", "Ka", "e3", "po"));
	}

	@Test
	public void repeat() {
		String str = Stringz.repeat("abc", " ", 3);
		Assert.assertEquals("Wrong string build", "abc abc abc", str);

		Assert.assertEquals("Wrong string build", "", Stringz.repeat("a", "x", 0));
		Assert.assertEquals("Wrong string build", "a", Stringz.repeat("a", "x", 1));
		Assert.assertEquals("Wrong string build", "axa", Stringz.repeat("a", "x", 2));
		Assert.assertEquals("Wrong string build", "axaxa", Stringz.repeat("a", "x", 3));

		Assert.assertEquals("Wrong string build", "", Stringz.repeat("a", 0));
		Assert.assertEquals("Wrong string build", "a", Stringz.repeat("a", 1));
		Assert.assertEquals("Wrong string build", "aa", Stringz.repeat("a", 2));
		Assert.assertEquals("Wrong string build", "aaa", Stringz.repeat("a", 3));
	}
}
