package cufy.io;

import cufy.concurrent.Instructor;
import cufy.concurrent.Loop;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

@SuppressWarnings("JavaDoc")
public class RemoteReaderTest {
	@SuppressWarnings({"StringConcatenationInLoop", "SpellCheckingInspection"})
	@Test
	public void stop() throws IOException {
		String str = "ABCD" + "EFGH" + "IJKL" + "MNOP" + "QRST" + "UVWX" + "YZ01";
		Instructor instructor = new Instructor();
		RemoteReader reader = new RemoteReader(instructor, new StringReader(str));

		String s = "";
		boolean w = false;

		while (true) {
			char i;
			try {
				i = (char) reader.read();
				if (w)
					Assert.fail("Not broken: even when it have been notified to stop");
			} catch (IOException e) {
				if (!w)
					Assert.fail("broken before reaching the letter 'E'");
				break;
			}

			if (i == 'E') {
				w = true;
				instructor.notify(Loop.BREAK);
			}

			s += i;
		}

		Assert.assertEquals("Source don't match", "ABCDE", s);
	}
}
