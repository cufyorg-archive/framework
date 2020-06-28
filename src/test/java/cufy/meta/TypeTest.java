package cufy.meta;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;

@SuppressWarnings("ALL")
public class TypeTest {
	@Test
	@Type(value = ArrayList.class, family = Set.class, components = String.class)
	public void docs() {
		//this is clazz of an ArrayList that should be treated as a Set and its
		//component type is String
	}
}
