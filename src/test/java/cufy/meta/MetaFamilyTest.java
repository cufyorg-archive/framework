package cufy.meta;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("JavaDoc")
public class MetaFamilyTest {
	@Test(timeout = 50)
	@MetaFamily(
			in = Map.class,
			out = HashMap.class,
			subIn = List.class,
			subOut = CharSequence.class
	)
	public void test() throws NoSuchMethodException {
		MetaFamily type = this.getClass().getMethod("test").getAnnotation(MetaFamily.class);

		//in
		Assert.assertTrue("Map is absolute included", MetaFamily.util.test(type, Map.class));
		Assert.assertFalse("Number is not included anywhere", MetaFamily.util.test(type, Number.class));
		//out
		Assert.assertFalse("HashMap is absolute excluded", MetaFamily.util.test(type, HashMap.class));
		//subin
		Assert.assertTrue("List is absolute included", MetaFamily.util.test(type, List.class));
		Assert.assertTrue("ArrayList is a sub included", MetaFamily.util.test(type, ArrayList.class));
		//subout
		Assert.assertFalse("CharSequence is absolute excluded", MetaFamily.util.test(type, CharSequence.class));
		Assert.assertFalse("String is sub excluded", MetaFamily.util.test(type, String.class));
	}
}
