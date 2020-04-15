package cufy.beans;

import cufy.meta.Recipe;
import cufy.meta.Type;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({"JavaDoc", "RedundantOperationOnEmptyContainer"})
public class AbstractBeanTest {
	@Test
	public void _struct_put_get_size() {
		AbstractBean<Object, Object> bean = new AbstractBean<Object, Object>() {
			@Bean.Property(key = @Recipe(value = "false", type = @Type(Boolean.class)))
			private Integer integer = 45;
		};

		bean.put("A", "B");
		bean.put("A", "R");
		bean.put("Y", 7);
		bean.remove("Y");

		Assert.assertFalse("Remove does not work!", bean.containsKey("Y"));

		//state
		Assert.assertEquals("Wrong size calc", 2, bean.size());

		bean.put(false, 67);

		//key = false
		Assert.assertNotNull("Field value can't be reached", bean.get(false));
		Assert.assertNotEquals("Field value not update", 45, bean.get(false));
		Assert.assertEquals("Field value stored wrongly", 67, bean.get(false));

		//key = "A"
		Assert.assertNotNull("Non-field value can't be reached or not stored", bean.get("A"));
		Assert.assertNotEquals("Non-field value not updated", "B", bean.get("A"));
		Assert.assertEquals("Non-field value stored wrongly", "R", bean.get("A"));
	}

	@Test
	public void remove() {
		Bean bean = new AbstractBean() {
			@Property
			int i = 0;
		};

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
}
