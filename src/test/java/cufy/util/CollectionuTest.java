package cufy.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

@SuppressWarnings("JavaDoc")
public class CollectionuTest {
	@Test
	public void asList() {
		Map map = new HashMap();

		map.put("fake", 0);
		map.put("fake", 1);
		map.put("fake", 2);

		map.put(0, "zero");
		map.put(3, "three");
		map.put(7, "seven");

		List list = Collectionu.asList(map);

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
		Map<String, Object> remote = Collectionu.asMap(instance);

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
		Iterator<String> a = Arrayu.asList("a", "B", "c").iterator();
		Iterator<String> b = Arrayu.asList("a", "B", "c").iterator();
		Iterator<String> c = Arrayu.asList("a", "B", "c").iterator();

		Iterator<String> x = Collectionu.combine(a, b, c);

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
}
