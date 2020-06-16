package cufy.meta;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;

@SuppressWarnings("JavaDoc")
public class TypeTest {
	@Test
	@Type(
			value = ArrayList.class,
			family = Set.class
	)
	public void docs() {
		//this is clazz of an ArrayList that should be treated as a Set an its
		//component type is String
	}
}
