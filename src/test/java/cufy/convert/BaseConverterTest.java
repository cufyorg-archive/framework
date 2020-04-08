package cufy.convert;

import cufy.lang.Clazz;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

@SuppressWarnings("JavaDoc")
public class BaseConverterTest {
	@Test
	public void array_array() {
		//deep conversion
		{
			int[][] input = {{1, 2}, {3, 6}, {0}, {7}, {1}};
			double[][] output = {{7, 3}, {7, 3}, {2}, {6}, {0}};

			BaseConverter.global.convert(input, output);

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

			BaseConverter.global.convert(input, output);

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

			BaseConverter.global.convert(input1, output);
			BaseConverter.global.convert(input2, output, Clazz.of(HashSet.class, Clazz.of(double[].class)));

			//TODO
		}
		//recurse test
		{
			Object[] input = {null};
			input[0] = input;

			Collection output = new HashSet();

			BaseConverter.global.convert(input, output);

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

			BaseConverter.global.convert(input1, output, Clazz.of(output, Clazz.of(double[].class)));

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

			BaseConverter.global.convert(input0, output, Clazz.of(output, Clazz.of(double[].class)));

			Assert.assertNotSame("Copied by reference witch is illegal", input0[0], output.get(0));
			Assert.assertArrayEquals("Not converted right", input0[0], (double[]) output.get(0), 0);
		}
		//recurse test
		{
			Object[] input = {null};
			input[0] = input;

			List output = new ArrayList();

			BaseConverter.global.convert(input, output);

			Assert.assertSame("recursion not converted", output, output.get(0));
		}
	}

	@Test
	public void collection_array() {
		//TODO
	}

	@Test
	public void collection_collection() {
		//TODO
	}

	@Test
	public void collection_list() {
		//TODO
	}

	@Test
	public void map_map() {
		//TODO
	}

	@Test
	public void number__() {
		//TODO
	}

	@Test
	public void object_string() {
		//TODO
	}

	@Test
	public void recurse_object() {
		//TODO
	}

	@Test
	public void string_object() {
		//TODO
	}
}
