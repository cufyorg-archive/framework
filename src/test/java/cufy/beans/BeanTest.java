package cufy.beans;

import cufy.meta.Recipe;
import cufy.meta.Type;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

@SuppressWarnings("ALL")
public class BeanTest {
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
	public void constancy() {
		class TestBean implements Bean {
			@Property
			public final List constant = new ArrayList();

			@Property
			public List variable = new ArrayList();
		}

		TestBean bean = new TestBean();

		bean.put("constant", Arrays.asList("HI"));
		bean.put("variable", Arrays.asList("BYE"));

		Assert.assertEquals("Final element not updated", "HI", bean.constant.get(0));
		Assert.assertFalse("Variable element not changed", bean.variable instanceof ArrayList);
		Assert.assertEquals("Non-final element not updated", "BYE", bean.variable.get(0));
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

		Map map = new HashMap();
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

	static class TestBean {
		@Bean.Property
		static int i;
	}
}
