package cufy.beans;

import cufy.meta.MetaClazz;
import cufy.meta.MetaObject;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({"JavaDoc"})
public class AbstractBeanTest {
	@Test
	public void _struct_put_get_size() {
		AbstractBean<Object, Object> bean = new AbstractBean<Object, Object>() {
			@Bean.Property(key = @MetaObject(value = "false", type = @MetaClazz(Boolean.class)))
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
	public void clear() {
		//TODO
	}

	@Test
	public void containsKey() {
		//TODO
	}

	@Test
	public void containsValue() {
		//TODO
	}

	@Test
	public void entrySet() {
		//TODO
	}

	@Test
	public void forInstance() {
		//TODO
	}

	@Test
	public void get() {
		//TODO
	}

	@Test
	public void isEmpty() {
		//TODO
	}

	@Test
	public void keySet() {
		//TODO
	}

	@Test
	public void put() {
		//TODO
	}

	@Test
	public void putAll() {
		//TODO
	}

	@Test
	public void remove() {
		//TODO
	}

	@Test
	public void size() {
		//TODO
	}

	@Test
	public void values() {
		//TODO
	}
}
