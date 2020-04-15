package cufy.beans;

import cufy.meta.Recipe;
import cufy.meta.Type;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

@SuppressWarnings({"JavaDoc", "RedundantOperationOnEmptyContainer"})
public class BeanTest {
	@Test
	public void _forInstance_put_get_size() throws ReflectiveOperationException {
		Object object = new Object() {
			@Bean.Property
			public int p;
			@Bean.Property(key = @Recipe(value = "false", type = @Type(Boolean.class)), type = @Type(Integer.class))
			private int property0 = 90;
		};

		Bean<Object, Object> bean = Bean.forInstance(object);

		bean.put(false, "700");
		bean.put("p", 10);

		//state
		Assert.assertEquals("Wrong size calc", 2, bean.size());

		//key = false
		Assert.assertEquals("Field value stored wrongly", 700, bean.get(false));
		Assert.assertEquals("Default key not recognized", 10, bean.get("p"));
		Assert.assertEquals("Default key not recognized", 10, object.getClass().getDeclaredField("p").get(object));
	}

	@Test
	public void _forInstance_remove() {
		Bean bean = Bean.forInstance(new Object() {
			@Bean.Property
			int i = 0;
		});

		Assert.assertEquals("get(): don't work on pre-existing properties", 0, bean.get("i"));
		Assert.assertEquals("size(): don't work on pre-existing properties", 1, bean.size());
		Assert.assertFalse("isEmpty(): don't work properly", bean.isEmpty());

		bean.remove("i");

		Assert.assertNull("clear(): don't work on fields", bean.get("i"));
		Assert.assertEquals("size(): don't work after removing fields", 0, bean.size());
		Assert.assertTrue("isEmpty(): don't work properly", bean.isEmpty());

		bean.put("i", 13);

		bean.entrySet().forEach(entry -> Assert.assertTrue("Not field-entry!", entry instanceof Bean.FieldEntry));

		Assert.assertEquals("put(): don't work on removed fields", 13, bean.get("i"));

		Assert.assertTrue("containsKey(): can't detected the re-putted field", bean.containsKey("i"));
		Assert.assertTrue("containsValue(): don't work properly", bean.containsValue(13));
		Assert.assertFalse("containsValue(): don't work properly", bean.containsValue(0));
		Assert.assertEquals("size(): don't work after re-putting back the field", 1, bean.size());
		Assert.assertFalse("isEmpty(): don't work properly", bean.isEmpty());

		bean.put("ii", 14);

		Assert.assertEquals("put(): don't work on non-field key", 14, bean.get("ii"));
		Assert.assertEquals("size(): don't work when putting a non-field key", 2, bean.size());
		Assert.assertFalse("isEmpty(): don't work properly", bean.isEmpty());

		bean.remove("ii");

		Assert.assertNull("remove(): don't work on non-field keys", bean.get("ii"));
		Assert.assertEquals("size(): don't work when removing non-field keys", 1, bean.size());
		Assert.assertFalse("isEmpty(): don't work properly", bean.isEmpty());

		bean.clear();

		Assert.assertNull("clear(): don't work", bean.get("i"));
		Assert.assertNull("clear(): don't work", bean.get("ii"));
		Assert.assertEquals("size(): don't work properly after clearing", 0, bean.size());
		Assert.assertTrue("isEmpty(): don't work properly", bean.isEmpty());
	}

	@Test
	public void _forInstance_struct_put_get_size() {
		Object object = new Object() {
			@Bean.Property(key = @Recipe(value = "false", type = @Type(Boolean.class)))
			private Integer integer = 45;
		};

		Bean<Object, Object> bean = Bean.forInstance(object);

		bean.put("A", "B");
		bean.put("A", "R");

		//state
		Assert.assertEquals("Wrong size calc", 2, bean.size());

		bean.put(false, 67);

		//key = false
		Assert.assertEquals("Field value stored wrongly", 67, bean.get(false));

		Assert.assertEquals("Non-field value stored wrongly", "R", bean.get("A"));
	}

	@Test
	public void _getNotExist() {
		Bean bean = new Bean() {
		};

		Assert.assertNull("Not equals", bean.get(null));
		Assert.assertTrue("It is empty", bean.isEmpty());
	}

	@Test
	public void _struct_put_get_size() {
		Bean<Object, Object> bean = new Bean<Object, Object>() {
			@Bean.Property(key = @Recipe(value = "false", type = @Type(Boolean.class)), type = @Type(Integer.class))
			private int property0 = 90;
		};

		try {
			bean.put("property0", 9);
			Assert.fail("The key have been overridden");
		} catch (UnsupportedOperationException ignored) {
		}
		try {
			bean.put("false", 9);
			Assert.fail("The key is boolean not a string");
		} catch (UnsupportedOperationException ignored) {
		}

		bean.put(false, "700");

		try {
			bean.remove(false);
			Assert.fail("remove() should not be supported");
		} catch (UnsupportedOperationException ignored) {
		}
		try {
			bean.clear();
			Assert.fail("clear() should not be supported");
		} catch (UnsupportedOperationException ignored) {
		}

		Assert.assertFalse("The bean is not empty", bean.isEmpty());
		Assert.assertTrue("The bean has the key false", bean.containsKey(false));
		Assert.assertFalse("The bean don't have a string key", bean.containsKey("false"));

		Assert.assertTrue("The bean has the value 700", bean.containsValue(700));
		Assert.assertFalse("The bean don't have the value 90", bean.containsValue(90));

		//state
		Assert.assertEquals("Wrong size calc", 1, bean.size());

		//key = false
		Assert.assertEquals("Field value stored wrongly", 700, bean.get(false));
	}

	@Test
	public void putAll() {
		class TestBean implements Bean {
			@Property(key = @Recipe("y"))
			public int x = 0;

			@Property(key = @Recipe("x"))
			public int y = 1;
		}

		TestBean bean = new TestBean();

		HashMap map = new HashMap();
		map.put("x", 19);
		map.put("y", 21);

		bean.putAll(map);

		Assert.assertEquals("Value not changed", 19, bean.y);
		Assert.assertEquals("Value not changed", 21, bean.x);

		map.put("z", 23);

		try {
			bean.putAll(map);
			Assert.fail("Can't store all the keys");
		} catch (UnsupportedOperationException ignored) {
		}
	}
}
