package cufy.meta;

import cufy.convert.BaseConverter;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("JavaDoc")
public class RecipeTest {
	@Test
	@Recipe(
			value = "3",
			type = @Type(Integer.class),
			converter = @Where(BaseConverter.class)
	)
	public void get() throws NoSuchMethodException {
		Recipe object = this.getClass().getMethod("get").getAnnotation(Recipe.class);
		int i = Recipe.util.get(object);

		Assert.assertEquals("Wrong value", 3, i, 0);
	}
}
