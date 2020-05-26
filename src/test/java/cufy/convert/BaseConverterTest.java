package cufy.convert;

import cufy.lang.Clazz;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

@SuppressWarnings("JavaDoc")
public class BaseConverterTest {
	@Test
	public void applyTest() {
		Object[] arr = {0, 1};

		BaseConverter.global.apply(arr, Clazz.of(Object[].class, Clazz.of(Long.class)));

		Assert.assertSame("wrong type", arr[0].getClass(), Long.class);
		Assert.assertEquals("wrong value", arr[0], 0L);
		Assert.assertSame("wrong type", arr[1].getClass(), Long.class);
		Assert.assertEquals("wrong value", arr[1], 1L);
	}

	@Test
	public void array_array() {
		//deep conversion
		{
			int[][] input = {{1, 2}, {3, 6}, {0}, {7}, {1}};
			double[][] output = {{7, 3}, {7, 3}, {2}, {6}, {0}};

			BaseConverter.global.convert(new ConvertToken<>(input, output, Clazz.ofi(input), Clazz.ofi(output)));

			Assert.assertEquals("Value not changed", input[0][0], output[0][0], 0);
			Assert.assertEquals("Value not changed", input[0][1], output[0][1], 0);
			Assert.assertEquals("Value not changed", input[1][0], output[1][0], 0);
			Assert.assertEquals("Value not changed", input[1][1], output[1][1], 0);
			Assert.assertEquals("Value not changed", input[2][0], output[2][0], 0);
			Assert.assertEquals("Value not changed", input[3][0], output[3][0], 0);
			Assert.assertEquals("Value not changed", input[4][0], output[4][0], 0);
		}
		//recurse test
		{
			Object[] input = {null};
			input[0] = input;

			Object[] output = {null};

			BaseConverter.global.convert(new ConvertToken<>(input, output, Clazz.ofi(input), Clazz.ofi(output)));

			Assert.assertSame("recursion not converted", output, output[0]);
		}
	}

	@Test
	public void array_collection() {
		//place then clear then place all components in the source
		{
			double[][] input1 = {{7, 3}, {7, 3}, {2}, {6}, {0}};
			int[][] input2 = {{1, 2}, {3, 6}, {0}, {7}, {1}};
			Collection output = new HashSet();

			BaseConverter.global.convert(new ConvertToken<>(input1, output, Clazz.ofi(input1), Clazz.ofi(output)));
			BaseConverter.global.convert(new ConvertToken<>(output, null, Clazz.of(HashSet.class), Clazz.of(double[][].class)));
			BaseConverter.global.convert(new ConvertToken<>(input2, output, Clazz.ofi(input2), Clazz.ofi(output)));
		}
		//recurse test
		{
			Object[] input = {null};
			input[0] = input;

			Collection output = new HashSet();

			BaseConverter.global.convert(new ConvertToken<>(input, output, Clazz.ofi(input), Clazz.ofi(output)));

			Assert.assertSame("recursion not converted", output, output.iterator().next());
		}
	}

	@Test
	public void array_list() {
		//basic test
		{
			double[][] input0 = {{7, 3}, {7, 3}, {2}, {6}, {0}};
			int[][] input1 = {{1, 2}, {3, 6}, {0}, {7}, {1}};
			List output = new ArrayList(Arrays.asList(input0));

			BaseConverter.global.convert(new ConvertToken<>(input1, output, Clazz.ofi(input1), Clazz.ofi(output, Clazz.of(double[].class))));

			Assert.assertEquals("Value not changed", input1[0][0], input0[0][0], 0);
			Assert.assertEquals("Value not changed", input1[0][1], input0[0][1], 0);
			Assert.assertEquals("Value not changed", input1[1][0], input0[1][0], 0);
			Assert.assertEquals("Value not changed", input1[1][1], input0[1][1], 0);
			Assert.assertEquals("Value not changed", input1[2][0], input0[2][0], 0);
			Assert.assertEquals("Value not changed", input1[3][0], input0[3][0], 0);
			Assert.assertEquals("Value not changed", input1[4][0], input0[4][0], 0);
		}
		//don't copy by reference test
		{
			double[][] input0 = {{0}};

			List output = new ArrayList();

			BaseConverter.global.convert(new ConvertToken<>(input0, output, Clazz.ofi(input0), Clazz.ofi(output, Clazz.of(double[].class))));

			Assert.assertNotSame("Copied by reference witch is illegal", input0[0], output.get(0));
			Assert.assertArrayEquals("Not converted right", input0[0], (double[]) output.get(0), 0);
		}
		//recurse test
		{
			Object[] input = {null};
			input[0] = input;

			List output = new ArrayList();

			BaseConverter.global.convert(new ConvertToken<>(input, output, Clazz.ofi(input), Clazz.ofi(output)));

			Assert.assertSame("recursion not converted", output, output.get(0));
		}
	}

	@Test
	public void clonePrimitive() {
		{
			int i = 5;
			int x = BaseConverter.global.clone(i);
			assert i == x;
		}
		{
			boolean b = true;
			boolean x = BaseConverter.global.clone(b);
			assert b == x;
		}
		{
			char c = 'e';
			char x = BaseConverter.global.clone(c);
			assert c == x;
		}
	}

	@Test
	public void cloneTest() {
		int[][] arr = {{0, 1}, {2, 3}};

		int[][] clone = BaseConverter.global.clone(arr);

		//the arrays are different
		Assert.assertNotSame("Not cloning", arr, clone);
		Assert.assertNotSame("Not cloning", arr[0], clone[0]);
		Assert.assertNotSame("Not cloning", arr[1], clone[1]);

		//but the values are the same
		Assert.assertSame("wrong value", arr[0][0], clone[0][0]);
		Assert.assertSame("wrong value", arr[0][1], clone[0][1]);
		Assert.assertSame("wrong value", arr[1][0], clone[1][0]);
		Assert.assertSame("wrong value", arr[1][1], clone[1][1]);
	}

	@Test
	public void condTransTest() {
		int[] arr = {0, 1};
		Object[] objs = new Object[2];

		BaseConverter.global.convert(arr, objs, Clazz.of(Object[].class, Clazz.of(Long.class)));

		Assert.assertSame("Wrong type", objs[0].getClass(), Long.class);
		Assert.assertSame("Wrong type", objs[1].getClass(), Long.class);

		Assert.assertEquals("Wrong value", objs[0], 0L);
		Assert.assertEquals("Wrong value", objs[1], 1L);
	}

	@Test
	public void objToSame() {
		class Test {
		}

		Test t = new Test();
		ConvertToken token = new ConvertToken(t, null, Clazz.of(Test.class), Clazz.of(Test.class));

		try {
			BaseConverter.global.convert(token);
			Assert.fail("The conversion should attempt cloning and fail");
		} catch (CloneException ignored) {
			Assert.assertNull("The conversion should have no results", token.output);
		}
	}

	@Test
	public void transTest() {
		int[][] arr = {{0, 1}, {2, 3}};
		long[][] lng = new long[2][2];

		BaseConverter.global.convert(arr, lng);

		Assert.assertSame("wrong value", (int) lng[0][0], arr[0][0]);
		Assert.assertSame("wrong value", (int) lng[0][1], arr[0][1]);
		Assert.assertSame("wrong value", (int) lng[1][0], arr[1][0]);
		Assert.assertSame("wrong value", (int) lng[1][1], arr[1][1]);
	}
}
