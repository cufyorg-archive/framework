package cufy.beans;

import cufy.meta.Recipe;
import cufy.meta.Type;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("ALL")
public class AnonymousBeanTest {
	@Test
	public void put_get_size() throws ReflectiveOperationException {
		Object object = new Object() {
			@Bean.Property
			public int p;
			@Bean.Property(key = @Recipe(value = "false", type = @Type(Boolean.class)), type = @Type(Integer.class))
			private int property0 = 90;
		};

		Bean<Object, Object> bean = new AnonymousBean(object);

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
	public void remove() {
		Bean bean = new AnonymousBean(new BeanTest.TestBean());

		Assert.assertEquals("get(): don't work on pre-existing properties", 0, bean.get("i"));
		Assert.assertEquals("size(): don't work on pre-existing properties", 1, bean.size());
		Assert.assertFalse("isEmpty(): don't work properly", bean.isEmpty());

		bean.remove("i");

		Assert.assertNull("clear(): don't work on fields", bean.get("i"));
		Assert.assertEquals("size(): don't work after removing fields", 0, bean.size());
		Assert.assertTrue("isEmpty(): don't work properly", bean.isEmpty());

		bean.put("i", 13);

		bean.entrySet().forEach(entry -> Assert.assertTrue("Not field-entry!", entry instanceof Bean.PropertyEntry));

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
	public void struct_put_get_size() {
		Object object = new Object() {
			@Bean.Property(key = @Recipe(value = "false", type = @Type(Boolean.class)))
			private Integer integer = 45;
		};

		Bean<Object, Object> bean = new AnonymousBean(object);

		bean.put("A", "B");
		bean.put("A", "R");

		//state
		Assert.assertEquals("Wrong size calc", 2, bean.size());

		bean.put(false, 67);

		//key = false
		Assert.assertEquals("Field value stored wrongly", 67, bean.get(false));

		Assert.assertEquals("Non-field value stored wrongly", "R", bean.get("A"));
	}
}
