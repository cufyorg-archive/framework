package cufy.meta;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("JavaDoc")
public class FilterTest {
	@Test(timeout = 50)
	@Filter(
			in = Map.class,
			out = HashMap.class,
			subIn = List.class,
			subOut = CharSequence.class
	)
	public void test() throws NoSuchMethodException {
		Filter type = this.getClass().getMethod("test").getAnnotation(Filter.class);

		//in
		Assert.assertTrue("Map is absolute included", Filter.util.test(type, Map.class));
		Assert.assertFalse("Number is not included anywhere", Filter.util.test(type, Number.class));
		//out
		Assert.assertFalse("HashMap is absolute excluded", Filter.util.test(type, HashMap.class));
		//subin
		Assert.assertTrue("List is absolute included", Filter.util.test(type, List.class));
		Assert.assertTrue("ArrayList is a sub included", Filter.util.test(type, ArrayList.class));
		//subout
		Assert.assertFalse("CharSequence is absolute excluded", Filter.util.test(type, CharSequence.class));
		Assert.assertFalse("String is sub excluded", Filter.util.test(type, String.class));
	}
}
