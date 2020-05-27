package cufy.text.json;

import cufy.beans.Bean;
import cufy.text.ParseException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.*;

@SuppressWarnings({"JavaDoc", "SpellCheckingInspection"})
public class JSONTest {
	@Test
	public void ao() {
		class B implements Bean {
			@Property
			public X[] beans = new X[]{
					new X(0),
					new X(1),
					new X(2),
					new X(3)
			};

			class X implements Bean {
				@Property
				public int x;

				X(int x) {
					this.x = x;
				}
			}
		}

		B b = new B();

		JSON.global.format(b);
	}

	@Test
	public void commentTest() {
		String s = "{\n" +
				   "\t\"list\":[\n" +
				   "\t\t/*abcddffqefa*/\"Success\"//wadefcwacesfeqaescd\n" +
				   "\t]\n" +
				   "}";

		Map obj = JSON.global.cparse(s);
		List arr = (List) obj.get("list");

		Assert.assertEquals("incorrect object size", 1, obj.size());
		Assert.assertEquals("incorrect array size", 1, arr.size());
		Assert.assertEquals("incorrect value", "Success", arr.get(0));
	}

	@Test
	public void comment_array_object_nested() {
		String source = "[3, 5, {9/*{],23myComment*/=//myMultiLineComment\n\"abc\"}]";

		//List<Object> list = JSONConverter.global.convert(source, ArrayList.class);
		List<Object> list = JSON.global.cparse(source);

		Assert.assertEquals("Wrong size", 3, list.size());
		Assert.assertEquals("Wrong 1st element", new BigDecimal(3), list.get(0));
		Assert.assertEquals("Wrong 2nd element", new BigDecimal(5), list.get(1));

		Map<Object, Object> map = (Map<Object, Object>) list.get(2);

		Assert.assertEquals("Wrong element in the key 9", "abc", map.get(new BigDecimal(9)));
	}

	@Test
	public void directParse() throws IOException {
		String source = "{\"a\"=3, \"b\"=4}";

		Map map = new HashMap();

		JSON.global.cparse(new StringReader(source), map);

		Assert.assertEquals("Unexpected length", 2, map.size());
		Assert.assertEquals("the key 'a' stores unexpected value", new BigDecimal(3), map.get("a"));
		Assert.assertEquals("the key 'b' stores unexpected value", new BigDecimal(4), map.get("b"));
	}

	@Test
	public void empty() {
		Collection collection = JSON.global.cparse("[]");
		Map map = JSON.global.cparse("{}");

		Assert.assertTrue("expected empty collection!", collection.isEmpty());
		Assert.assertTrue("expected empty map!", map.isEmpty());

		collection = JSON.global.cparse("[0,]");
		map = JSON.global.cparse("{0:0,}");

		Assert.assertEquals("expected singleton collection!", 1, collection.size());
		Assert.assertEquals("expected singleton map!", 1, map.size());
		Assert.assertEquals("wrong member value", new BigDecimal(0), collection.iterator().next());
		Assert.assertEquals("wrong member value", new BigDecimal(0), map.get(new BigDecimal(0)));

		try {
			JSON.global.cparse("{,}");
			Assert.fail("expected \"No equation symbol\" exception!");
		} catch (ParseException ignored) {
		}
		try {
			JSON.global.cparse("{:}");
			Assert.fail("expected \"can't parse Empty.class\" exception!");
		} catch (ParseException ignored) {
		}
		try {
			JSON.global.cparse("[,]");
			Assert.fail("expected \"Elements can't be empty\" exception!");
		} catch (ParseException ignored) {
		}
	}

	@Test
	public void escape() {
		String str = "{\"\\\"} is a way to destroy\": \"hi\"}";

		HashMap map = JSON.global.parse(str, new HashMap());

		assert map.size() == 1;
		assert map.get("\"} is a way to destroy").equals("hi");
	}

	@Test
	public void format_object_array_nested() {
		Map<Object, Object> base = new HashMap<>(3);
		Map<Object, Object> map = new HashMap<>(3);
		base.put("map", map);
		map.put("number", Arrays.asList(9, 3, 5));

		String expected = "{\n" +
						  "\t\"map\":{\n" +
						  "\t\t\"number\":[\n" +
						  "\t\t\t9,\n" +
						  "\t\t\t3,\n" +
						  "\t\t\t5\n" +
						  "\t\t]\n" +
						  "\t}\n" +
						  "}";
		String actual = JSON.global.format(base);
		Assert.assertEquals("Wrong format", expected, actual);
	}

	@Test
	public void parse() {
		String text = "{/*beast is a beast\nwhy? just becouse!*/\"beast\"=[//How ? just watch\n\"beast\", \"is a\", \"beast\"]}";

		Map map = JSON.global.cparse(text);

		Assert.assertTrue("missing key", map.containsKey("beast"));

		List beast = (List) map.get("beast");

		Assert.assertEquals("wrong size", 3, beast.size());
		Assert.assertEquals("wrong element", "beast", beast.get(0));
		Assert.assertEquals("wrong element", "is a", beast.get(1));
		Assert.assertEquals("wrong element", "beast", beast.get(2));
	}

	@Test
	public void parse_array_keep_instance() throws IOException {
		ArrayList list = new ArrayList();
		ArrayList nestedOver = new ArrayList();
		String nestedNon = "abc";

		list.add(nestedOver);
		list.add(nestedNon);

		nestedOver.add(nestedNon);

		String source = "[[\"def\", [0]], \"def\", [0]]";

		JSON.global.cparse(new StringReader(source), list);

		Assert.assertEquals("Wrong base size", 3, list.size());

		List nestedOverAfter = (List) list.get(0);

		Assert.assertSame("Instance not overwritten", nestedOver, nestedOverAfter);
		Assert.assertEquals("Wrong member value", "def", list.get(1));
		Assert.assertEquals("Wrong member value", new ArrayList<>(Collections.singletonList(new BigDecimal(0))), list.get(2));

		Assert.assertEquals("Wrong over size", 2, nestedOverAfter.size());

		Assert.assertEquals("Wrong member value", "def", nestedOverAfter.get(0));
		Assert.assertEquals("Wrong member value", new ArrayList<>(Collections.singletonList(new BigDecimal(0))), nestedOverAfter.get(1));

		source = "[[\"jhi\"]]";

		JSON.global.cparse(new StringReader(source), list);

		Assert.assertEquals("Wrong base size", 1, list.size());

		nestedOverAfter = (List) list.get(0);

		Assert.assertSame("Instance not overwritten", nestedOver, nestedOverAfter);

		Assert.assertEquals("Wrong over size", 1, nestedOverAfter.size());

		Assert.assertEquals("Wrong member value", "jhi", nestedOverAfter.get(0));
	}

	@Test
	public void parse_object_array_nested() {
		Map<String, Map<String, List>> val = JSON.global.cparse("{\"map\":{\"number\":[9, 3, 5]}}");
		Map<String, List> map = val.get("map");
		List number = map.get("number");
		Assert.assertEquals("first number not detected", new BigDecimal(9), number.get(0));
		Assert.assertEquals("second number not detected", new BigDecimal(3), number.get(1));
		Assert.assertEquals("third number not detected", new BigDecimal(5), number.get(2));
	}

	@Test
	public void primitiveArray() {
		String s = "[0, 1, 2, 3, 4, 5]";
		int[] array = new int[6];

		JSON.global.parse(s, array);

		for (int i = 0; i < array.length; i++)
			Assert.assertSame("Wrong value", i, array[i]);
	}
}
