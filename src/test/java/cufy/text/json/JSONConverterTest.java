package cufy.text.json;

import cufy.convert.ConvertToken;
import cufy.lang.Clazz;
import cufy.util.Arrayz;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@SuppressWarnings("JavaDoc")
public class JSONConverterTest {
	@Test
	public void convert() {
		List<Object> list = new ArrayList<>();
		list.add(Arrayz.asList(0L, 1L, 2L));

		int length = list.size();

		String listAsSource = JSONConverter.global.convert(new ConvertToken<>(list, null, Clazz.ofi(list), Clazz.of(String.class)));

		HashSet<Object> set = JSONConverter.global.convert(new ConvertToken<>(listAsSource, null, Clazz.of(String.class), Clazz.of(HashSet.class, Clazz.of(List.class, Clazz.of(Long.class)))));

		String setAsSource = JSONConverter.global.convert(new ConvertToken<>(set, null, Clazz.ofi(set), Clazz.of(String.class)));

		Object[] array = JSONConverter.global.convert(new ConvertToken<>(setAsSource, null, Clazz.ofi(setAsSource), Clazz.of(Object[].class, Clazz.of(List.class, Clazz.of(Long.class)))));

		Assert.assertEquals("map->string->set Haven't included all items", length, set.size());
		Assert.assertEquals("map->string->set Items don't match", new HashSet<>(list), set);
		Assert.assertEquals("set->string->array Haven't included all items", length, array.length);
		Assert.assertEquals("Haven't translate all items", -1, Arrayz.all(list.toArray(), array));
	}
}
