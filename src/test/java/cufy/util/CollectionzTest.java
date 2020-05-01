package cufy.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

@SuppressWarnings("JavaDoc")
public class CollectionzTest {
	@Test
	public void asList() {
		Map map = new HashMap();

		map.put("fake", 0);
		map.put("fake", 1);
		map.put("fake", 2);

		map.put(0, "zero");
		map.put(3, "three");
		map.put(7, "seven");

		List list = Collectionz.asList(map);

		list.add("four"); //eight
		list.add(9, "ten"); //nine
		list.add("eleven"); //ten
		list.add(9, "nine");
		list.set(8, "eight");

		Assert.assertEquals("wrong size calc", 12, list.size());

		Assert.assertEquals("Element has changed while it shouldn't", "zero", map.get(0));
		Assert.assertNull("Element should not exist", map.get(1));
		Assert.assertNull("Element should not exist", map.get(2));
		Assert.assertEquals("Element has changed while it shouldn't", "three", map.get(3));
		Assert.assertNull("Element should not exist", map.get(4));
		Assert.assertNull("Element should not exist", map.get(5));
		Assert.assertNull("Element should not exist", map.get(6));
		Assert.assertEquals("Element has changed while it shouldn't", "seven", map.get(7));
		Assert.assertEquals("Element has not changed", "eight", map.get(8));
		Assert.assertEquals("Element has not changed", "nine", map.get(9));
		Assert.assertEquals("Shift indexes error", "ten", map.get(10));
		Assert.assertEquals("Shift indexes error", "eleven", map.get(11));

		Assert.assertEquals("Wrong element", "zero", list.get(0));
		Assert.assertNull("Wrong element", list.get(1));
		Assert.assertNull("Wrong element", list.get(1));
		Assert.assertEquals("Wrong element", "three", list.get(3));
		Assert.assertNull("Wrong element", list.get(4));
		Assert.assertNull("Wrong element", list.get(5));
		Assert.assertNull("Wrong element", list.get(6));
		Assert.assertEquals("Wrong element", "seven", list.get(7));
		Assert.assertEquals("Wrong element", "eight", list.get(8));
		Assert.assertEquals("Wrong element", "nine", list.get(9));
		Assert.assertEquals("Wrong element", "ten", list.get(10));
		Assert.assertEquals("Wrong element", "eleven", list.get(11));

		try {
			list.add(13, null);
			Assert.fail("IndexOutOfBounds not thrown");
		} catch (IndexOutOfBoundsException ignored) {
		}

		try {
			list.remove(12);
			Assert.fail("IndexOutOfBounds not thrown");
		} catch (IndexOutOfBoundsException ignored) {
		}

		try {
			list.set(12, null);
			Assert.fail("IndexOutOfBounds not thrown");
		} catch (IndexOutOfBoundsException ignored) {
		}
	}

	@Test
	public void asMap() {
		class TestObject {
			final public int fin = 5;
			public int pub = 3;
		}

		TestObject instance = new TestObject();
		Map<String, Object> remote = Collectionz.asMap(instance);

		Assert.assertEquals("Can't get final fields", 3, remote.get("pub"));
		Assert.assertEquals("Can't get public fields", 3, remote.get("pub"));

		remote.put("pub", 20);

		Assert.assertEquals("Can't replace public fields", 20, remote.get("pub"));

		try {
			remote.remove("pub");
			Assert.fail("Remove should not work");
		} catch (UnsupportedOperationException ignored) {
		}
		try {
			remote.clear();
			Assert.fail("Clear should not work");
		} catch (UnsupportedOperationException ignored) {
		}
		try {
			remote.put("x", 1);
			Assert.fail("No such field!");
		} catch (IllegalArgumentException ignored) {
		}
		try {
			remote.put("pub", "abc def");
			Assert.fail("IllegalArgumentException for different type is expected");
		} catch (IllegalArgumentException ignored) {
		}
	}

	@Test
	public void combine() {
		Iterator<String> a = Arrayz.asList("a", "B", "c").iterator();
		Iterator<String> b = Arrayz.asList("a", "B", "c").iterator();
		Iterator<String> c = Arrayz.asList("a", "B", "c").iterator();

		Iterator<String> x = Collectionz.combine(a, b, c);

		StringBuilder string = new StringBuilder();

		while (x.hasNext())
			string.append(x.next());

		Assert.assertEquals("Some elements are missing", "aBcaBcaBc", string.toString());

		try {
			x.next();
			Assert.fail("No such element!");
		} catch (NoSuchElementException ignored) {
		}
	}

	@SuppressWarnings("MessageMissingOnJUnitAssertion")
	@Test
	public void docs_asList() {
		Map map = new HashMap();

		map.put(8, "value");

		List list = Collectionz.asList(map);

		Assert.assertEquals(9, list.size());
		Assert.assertEquals("value", list.get(8));

		list.add("another");

		Assert.assertEquals("another", map.get(9));

		map.put(10, "different");

		Assert.assertEquals(11, list.size());
		Assert.assertEquals("different", list.get(10));
	}

	@SuppressWarnings("MessageMissingOnJUnitAssertion")
	@Test
	public void docs_asMap() {
		class Test {
			public String key = "value";
		}

		Test instance = new Test();

		Map map = Collectionz.asMap(instance);

		Assert.assertEquals(1, map.size());
		Assert.assertEquals("value", map.get("key"));

		map.put("key", "another");

		Assert.assertEquals("another", instance.key);

		instance.key = "different";

		Assert.assertEquals("different", map.get("key"));
	}

	@SuppressWarnings("MessageMissingOnJUnitAssertion")
	@Test
	public void docs_asMap1() {
		List list = new ArrayList(Arrayz.asList("value"));

		Map map = Collectionz.asMap(list);

		Assert.assertEquals(1, map.size());
		Assert.assertEquals("value", map.get(0));

		map.put(2, "another");

		Assert.assertEquals(3, list.size());
		Assert.assertNull(list.get(1));
		Assert.assertEquals("another", list.get(2));

		list.add("different");

		Assert.assertEquals(4, map.size());
		Assert.assertEquals("different", map.get(3));
	}

	@SuppressWarnings({"StringConcatenationInLoop", "MessageMissingOnJUnitAssertion"})
	@Test
	public void docs_combine() {
		Iterator first = Arrayz.asList("abc-".toCharArray()).iterator();
		Iterator second = Arrayz.asList("-def".toCharArray()).iterator();

		Iterator combine = Collectionz.combine(first, second);

		String s = "";
		while (combine.hasNext())
			s += (char) combine.next();

		Assert.assertEquals("abc--def", s);
	}

	@Test
	public void unmodifiableGroup() {
		String[] strings = {"my abc", "my def", "my ghi", "abc", "def", "ghi"};
		Group<String> constants = Collectionz.unmodifiableGroup(new HashGroup<>(Arrayz.asList(strings)));
		Group<String> my = constants.subGroup("my", s -> s.startsWith("my"));
		Group<String> abc = constants.subGroup("abc", s -> s.contains("abc"));

		Assert.assertEquals("Wrong size", 3, my.size());
		Assert.assertEquals("Wrong size", 2, abc.size());
		Assert.assertTrue("Should contains all", my.containsAll(java.util.Arrays.asList("my abc", "my def", "my ghi")));
		Assert.assertTrue("Should contains all", abc.containsAll(java.util.Arrays.asList("my abc", "abc")));

		Group<String> myAgain = constants.subGroup("my", s -> false);
		Group<String> abcAgain = constants.subGroup("abc", s -> false);

		boolean w = my.equals(myAgain);
		boolean x = abc.equals(abcAgain);

		Assert.assertEquals("Didn't returned the already resolved object", my, myAgain);
		Assert.assertEquals("Didn't returned the already resolved object", abc, abcAgain);
	}
}
