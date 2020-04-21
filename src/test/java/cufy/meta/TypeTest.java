package cufy.meta;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;

/**
 * TODO javadoc
 * <br/>
 *
 * @author LSaferSE
 * @version 1 alpha (21-Apr-2020)
 * @since 21-Apr-2020
 */
@SuppressWarnings("JavaDoc")
public class TypeTest {
	@Test
	@Type(
			value = ArrayList.class,
			family = Set.class,
			componentTypes = String.class
	)
	public void docs() {
		//this is clazz of an ArrayList that should be treated as a Set an its
		//component type is String
	}
}
