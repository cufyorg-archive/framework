package cufy.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

@SuppressWarnings({"JavaDoc", "SpellCheckingInspection"})
public class ReaderzTest {
	@Test
	public void getRemaining() throws IOException {
		final String STRING = "ABCDEF";
		Reader reader = new StringReader(STRING);
		String collected = Readerz.getRemaining(reader, 50, 50);

		Assert.assertEquals("Not collected correctly", STRING, collected);
	}

	@SuppressWarnings("SpellCheckingInspection")
	@Test
	public void isRemainingEquals() throws IOException {
		StringReader reader = new StringReader("AbCdEf");

		reader.mark(0);

		//no trim & no fullRead & no ignoreCase
		Assert.assertTrue("Should equals", Readerz.isRemainingEquals(reader, false, false, false, "AB", "X", "AbC", "ABCDEF"));
		reader.reset();
		Assert.assertFalse("Should not equals", Readerz.isRemainingEquals(reader, false, false, false, "AbCD", "  AbCdEf  ", "E", "ABCDEF"));
		reader.reset();

		//no trim & no fullRead & ignoreCase
		Assert.assertTrue("should equals", Readerz.isRemainingEquals(reader, false, false, true, "x", "Y", "E", "ABCDEF", "AbCx"));
		reader.reset();

		//no trim & fullRead & no ignoreCase

		//no trim & fullRead & ignoreCase
		Assert.assertTrue("Should equals", Readerz.isRemainingEquals(reader, false, true, true, "A", "X", "ABC", "ABCDEF"));
		reader.reset();
		Assert.assertFalse("Should not equals", Readerz.isRemainingEquals(reader, false, true, true, "A", "  AbCdEf  ", "E", "R"));
		reader.reset();

		//trim & no fullRead & no ignoreCase

		//trim & no fullRead & ignoreCase

		//trim & fullRead & no ignoreCase

		//trim & fullRead & ignoreCase
	}
}
