package cufy.meta;

import cufy.convert.BaseConverter;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("JavaDoc")
public class MetaObjectTest {
	@Test
	@MetaObject(
			value = "3",
			type = @MetaClazz(Integer.class),
			converter = @MetaReference(type = BaseConverter.class)
	)
	public void get() throws NoSuchMethodException {
		MetaObject object = this.getClass().getMethod("get").getAnnotation(MetaObject.class);
		int i = MetaObject.util.get(object);

		Assert.assertEquals("Wrong value", 3, i, 0);

		//TODO
	}
}
