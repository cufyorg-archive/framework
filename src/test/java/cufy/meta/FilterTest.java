package cufy.meta;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

@SuppressWarnings("JavaDoc")
public class FilterTest {
	@Test
	@Filter(
			include = String.class,
			exclude = HashSet.class,
			includeAll = Collection.class,
			excludeAll = List.class
	)
	public void docs() {
		//this filter will include String.class, any Collection,
		//but not any class extends List.class or the class HashSet
	}

	@Test(timeout = 50)
	@Filter(
			include = Map.class,
			exclude = HashMap.class,
			includeAll = List.class,
			excludeAll = CharSequence.class
	)
	public void test() throws NoSuchMethodException {
		Filter type = this.getClass().getMethod("test").getAnnotation(Filter.class);

		//in
		Assert.assertTrue("Map is absolute included", Filter.Util.test(type, Map.class));
		Assert.assertFalse("Number is not included anywhere", Filter.Util.test(type, Number.class));
		//out
		Assert.assertFalse("HashMap is absolute excluded", Filter.Util.test(type, HashMap.class));
		//subIn
		Assert.assertTrue("List is absolute included", Filter.Util.test(type, List.class));
		Assert.assertTrue("ArrayList is a sub included", Filter.Util.test(type, ArrayList.class));
		//subOut
		Assert.assertFalse("CharSequence is absolute excluded", Filter.Util.test(type, CharSequence.class));
		Assert.assertFalse("String is sub excluded", Filter.Util.test(type, String.class));
	}
}
