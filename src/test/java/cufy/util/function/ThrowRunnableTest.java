package cufy.util.function;

import org.junit.Test;

import java.io.IOException;

@SuppressWarnings("JavaDoc")
public class ThrowRunnableTest {
	@Test(expected = IOException.class)
	public void run() {
		//noinspection RedundantCast
		((Runnable) (ThrowRunnable<IOException>) () -> {
			throw new IOException();
		}).run();
	}
}
