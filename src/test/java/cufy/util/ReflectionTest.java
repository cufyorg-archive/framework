package cufy.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("JavaDoc")
public class ReflectionTest {
	@Test
	public void asArrayClass() {
		Assert.assertEquals("Wrong array class", int[][].class, Reflection.asArrayClass(int[].class));
	}

	@Test
	public void asObjectClass() {
		Assert.assertEquals("Wrong object class", Integer[][][][][].class, Reflection.asObjectClass(int[][][][][].class));
	}

	@Test
	public void asPrimitiveClass() {
		Assert.assertEquals("Wrong object class", int[][][][][].class, Reflection.asPrimitiveClass(Integer[][][][][].class));
	}

	@Test
	public void getAllFields() {
		class A {
			public int a;
			protected int b;
			int c;
			private int d;
		}
		final int standardFieldsCount = 1; //this$0
		final int fieldsCount = 4;
		final int totalFieldsCount = standardFieldsCount + fieldsCount;

		List<Field> fields = Reflection.getAllFields(A.class);

		Assert.assertEquals("Wrong fields size", totalFieldsCount, fields.size());
		Assert.assertTrue("public field not found", fields.removeIf(f -> f.getName().equals("a")));
		Assert.assertTrue("protected field not found", fields.removeIf(f -> f.getName().equals("b")));
		Assert.assertTrue("package-private field not found", fields.removeIf(f -> f.getName().equals("c")));
		Assert.assertTrue("private field not found", fields.removeIf(f -> f.getName().equals("d")));
	}

	@Test
	public void getAllMethods() {
		class A {
			public void a() {
			}

			protected void b() {
			}

			void c() {
			}

			private void d() {

			}
		}
		final int standardMethodsCount = Object.class.getDeclaredMethods().length;
		final int methodsCount = 4;
		final int totalMethodsCount = standardMethodsCount + methodsCount;

		List<Method> methods = Reflection.getAllMethods(A.class);

		Assert.assertEquals("Wrong methods size", totalMethodsCount, methods.size());
		Assert.assertTrue("public method not found", methods.removeIf(m -> m.getName().equals("a")));
		Assert.assertTrue("protected method not found", methods.removeIf(m -> m.getName().equals("b")));
		Assert.assertTrue("package-private method not found", methods.removeIf(m -> m.getName().equals("c")));
		Assert.assertTrue("private method not found", methods.removeIf(m -> m.getName().equals("d")));
	}

	@Test(expected = IOException.class)
	public void ignite() {
		try {
			//noinspection TrivialFunctionalExpressionUsage
			((Supplier<Object>) () -> {
				throw Reflection.ignite(new IOException());
			}).get();
		} catch (Exception e) {
			throw Reflection.ignite(e);
		}

		Assert.fail("The exception haven't been caught");
	}

	@Test
	public void overrides() throws NoSuchMethodException {
		class A {
			public boolean equals(Object obj) {
				return super.equals(obj);
			}

			public void equals(Object obj, Object obj2) {
			}
		}

		Method base = Object.class.getMethod("equals", Object.class);
		Method doOverride = A.class.getMethod("equals", Object.class);
		Method dontOverride = A.class.getMethod("equals", Object.class, Object.class);

		Assert.assertTrue("Do override in reality", Reflection.overrides(base, doOverride));
		Assert.assertFalse("Don't override in reality", Reflection.overrides(base, dontOverride));
	}

	@Test
	public void primitiveCast() {
		Integer i = 413;
		Long l = (Long) Reflection.primitiveCast(Long.class, i);

		Assert.assertEquals("Wrong casting value", (int) i, (int) (long) l);
	}
}
