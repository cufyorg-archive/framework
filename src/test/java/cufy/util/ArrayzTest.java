package cufy.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

@SuppressWarnings("JavaDoc")
public class ArrayzTest {
	@Test
	public void all() {
		char[] chars = "Sulaiman".toCharArray();

		Assert.assertEquals("X should be missing", 3, Arrayz.all(chars, 'm', 'a', 'S', 'X', 'n'));
		Assert.assertEquals("No char should be missing", -1, Arrayz.all(chars, 'S', 'u', 'n', 'a'));
	}

	@Test
	public void any() {
		Object[] array = {'A', 'B', 'C'};

		Assert.assertEquals("'A' is contained in the array", 1, Arrayz.any(array, 'R', 'X', 'a', 'B'));
		Assert.assertEquals("All elements are not in the array", -1, Arrayz.any(array, 'y', 'b', 'P', '}'));
		Assert.assertEquals("An array should contains it's elements", 0, Arrayz.any(array, array));
	}

	@Test
	public void asList() {
		char[] chars = "Sulaiman".toCharArray();
		List<Character> characters = Arrayz.asList(chars);

		for (int i = 0; i < chars.length; i++)
			Assert.assertEquals("Wrong character", chars[i], (char) characters.get(i));
	}

	@Test
	public void copyOf() {
		char[] chars = "Sulaiman".toCharArray();
		int[] ints = (int[]) Arrayz.copyOf0(chars, 4, int[].class);

		Assert.assertEquals("Wrong length", 4, ints.length);

		for (int i = 0; i < ints.length; i++)
			Assert.assertEquals("Wrong letter", ints[i], chars[i]);
	}

	@Test
	public void docs_asList() {
		int[] array = {};
		Object object = array;

		//if you are a syntax user, this will be the best deal for you!
		List<Integer> arrayAsList = Arrayz.asList(array);

		//but if you are a reflection user, the array you have could be unsigned
		//and this method will save your time.
		List objectAsList = Arrayz.asList0(object);
	}

	@Test
	public void hardcopy() {
		char[] array = {'A', 'B', 'C'};
		char[] array1 = {'a', 'b', 'c'};

		Arrayz.hardcopy(array, 1, array1, 1, 1);

		Assert.assertEquals("Out of range", 'a', array1[0]);
		Assert.assertEquals("Out of range", 'c', array1[2]);
		Assert.assertEquals("not copied", 'B', array1[1]);
	}

	@SuppressWarnings("SpellCheckingInspection")
	@Test
	public void merge() {
		char[] array = {'A', 'B', 'C'};
		char[] array0 = {'D', 'E', 'F'};
		char[] product0 = Arrayz.merge(array, array0, new char[]{'D', 'E', 'F'});
		Assert.assertEquals("Wrong product0 array", "ABCDEFDEF", String.copyValueOf(product0));
	}

	@Test
	public void subarray() {
		String name = "Sulaiman";
		char[] chars = name.toCharArray();
		int length = name.length();

		for (int i = 0; i < length / 2; i++) {
			int l = length - i;
			char[] subarray = Arrayz.copyOfRange(chars, i, l);
			char[] substring = name.substring(i, l).toCharArray();
			Assert.assertArrayEquals("invalid subarray on i=" + i + " l=" + l, substring, subarray);
		}

		char[] c = Arrayz.copyOfRange(chars, chars.length, chars.length);
		char[] cc = name.substring(name.length()).toCharArray();
		Assert.assertEquals("Invalid subarray on bounds edges", String.copyValueOf(cc), String.copyValueOf(c));
	}

	@Test
	public void sum() {
		Assert.assertEquals("Wrong sum", 12, Arrayz.sum(new int[]{5, 4, 3}, 0, Integer::sum), 0);
	}
}
