package cufy.lang;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;

@SuppressWarnings("JavaDoc")
public class ClazzTest {
	@Test
	public void docs_of() {
		//the clazz below should be treated as if it was a Set. and if a new instance
		//needed it should be a new instance of ArrayList. And anyone should really
		//respect that this clazz only allow String elements.
		Clazz klazz = Clazz.of(Set.class, ArrayList.class, Clazz.of(String.class));
	}
}
