package cufy.io;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

@SuppressWarnings("JavaDoc")
public class BufferedReaderTest {
	@SuppressWarnings("StringConcatenationInLoop")
	@Test(timeout = 200)
	public void mark_remark() throws IOException {
		String text = "ABC" + "DEF";
		Reader reader = new BufferedReader(new StringReader(text));

		reader.mark(10);

		String s = "";
		int i;
		char c;
		while ((i = reader.read()) != -1 && (c = (char) i) != 'D') {
			s += c;
		}

		reader.reset();
		reader.mark(10);

		String s1 = "";
		int i1;
		char c1;
		while ((i1 = reader.read()) != -1 && (c1 = (char) i1) != 'D') {
			s1 += c1;
		}

		Assert.assertEquals("Can't read at all!", "ABC", s);
		Assert.assertEquals("Remark don't work!", "ABC", s1);
	}

	@SuppressWarnings({"StringConcatenationInLoop", "SpellCheckingInspection"})
	@Test
	public void read_mark_skip_reset_followup() throws IOException {
		String str = "ABCD" + "EFGH" + "IJKL" + "MNOP" + "QRST" + "UVWX" + "YZ01";

		Reader reader = new StringReader(str);
		BufferedReader mask = new BufferedReader(reader);
		//reader & mask CURSOR before 'A'

		mask.mark(4);
		//mask MARK before 'A'

		String str_init = "";
		for (int i : new int[4])
			str_init += (char) mask.read();
		//reader & mask CURSOR before 'E'

		Assert.assertEquals("Wrong sequence", "ABCD", str_init);

		mask.reset();
		//reader CURSOR before 'E'; mask CURSOR before 'A'

		String str_reset = "";
		for (int i : new int[4])
			str_reset += (char) mask.read();
		//reader & mask CURSOR before 'E'

		Assert.assertEquals("Mask maybe not reset", "ABCD", str_reset);

		mask.skip(4);
		//reader & mask CURSOR before 'I'

		String str_skip = "";
		for (int i : new int[4])
			str_skip += (char) mask.read();
		//reader & mask CURSOR before 'M'

		Assert.assertEquals("Mask maybe not skipped", "IJKL", str_skip);

		mask.mark(4);
		//mask MARK before 'M'
		mask.skip(4);
		//reader & mask CURSOR before 'Q'
		mask.reset();
		//reader CURSOR before 'Q'; mask CURSOR before 'M'

		String str_remark = "";
		for (int i : new int[4])
			str_remark += (char) mask.read();
		//reader & mask CURSOR before 'Q'

		Assert.assertEquals("Mask not reset to latest mark", "MNOP", str_remark);

		String str_continue_raw = "";
		for (int i : new int[4])
			str_continue_raw += (char) reader.read();
		//reader CURSOR before 'U'; mask CURSOR before 'Q'

		Assert.assertEquals("Reader should continue as where mask stopped at", "QRST", str_continue_raw);

		mask.mark(4);
		//mask MARK before 'Q'
		mask.getBuffer().write(str_continue_raw.toCharArray(), 0, str_continue_raw.length());
		//reader & mask CURSOR before 'U'

		mask.reset();
		//reader CURSOR before 'U'; mask CURSOR before 'Q'

		String str_followup = "";
		for (int i : new int[4])
			str_followup += (char) mask.read();
		//reader & mask CURSOR before 'U'

		Assert.assertEquals("Followup maybe not working", "QRST", str_followup);
	}
}
