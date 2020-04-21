package cufy.util.function;

import org.junit.Test;

import java.io.IOException;

@SuppressWarnings("JavaDoc")
public class ThrowRunnableTest {
	@SuppressWarnings("Convert2MethodRef")
	@Test
	public void docs() {
		try {
			Runnable runnable = (ThrowRunnable<Exception>) () -> throwingMethod();
		} catch (Exception e) {
		}
	}

	@Test(expected = IOException.class)
	public void run() {
		//noinspection RedundantCast
		((Runnable) (ThrowRunnable<IOException>) () -> {
			throw new IOException();
		}).run();
	}

	public void throwingMethod() throws Exception {
		//the exception will be cached by the catch statement below and will
		//be the exact exception thrown from the throwingMethod
	}
}
