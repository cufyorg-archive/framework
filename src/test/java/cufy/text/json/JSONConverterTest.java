package cufy.text.json;

import cufy.lang.Clazz;
import cufy.util.Arrayu;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@SuppressWarnings("JavaDoc")
public class JSONConverterTest {
	@Test
	public void convert() {
		List<Object> list = new ArrayList<>();
		list.add(5L);
		list.add(Arrays.asList(0L, 1L, 2L));

		int length = list.size();

		String listAsSource = JSONConverter.global.convert(list, Clazz.of(String.class));
		HashSet<Object> set = JSONConverter.global.convert(listAsSource, Clazz.of(HashSet.class));
		String setAsSource = JSONConverter.global.convert(set, Clazz.of(String.class));
		Object[] array = JSONConverter.global.convert(setAsSource, Clazz.of(Object[].class));

		Assert.assertEquals("map->string->set Haven't included all items", length, set.size());
		Assert.assertEquals("map->string->set Items don't match", new HashSet<>(list), set);
		Assert.assertEquals("set->string->array Haven't included all items", length, array.length);
		Assert.assertEquals("Haven't translate all items", -1, Arrayu.all(list.toArray(), array));
	}
}
