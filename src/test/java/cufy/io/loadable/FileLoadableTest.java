package cufy.io.loadable;

import cufy.beans.AbstractBean;
import cufy.meta.Recipe;
import cufy.text.Format;
import cufy.text.json.JSON;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;

@SuppressWarnings("JavaDoc")
public class FileLoadableTest {
	@Test
	public void url_format_bean_json_load() throws IOException {
		URL target = new URL("https://api.fixer.io/");

		try {
			target.openStream();
		} catch (UnknownHostException e) {
			//Possible: no internet
			System.err.println("TEST failed: " + e.getMessage());
			return;
		}

		class TestBean extends AbstractBean implements FormatLoadable, URLLoadable {
			@Property(key = @Recipe("1"))
			public String one;
			@Property(key = @Recipe("7"))
			public String seven;
			@Property(key = @Recipe("0"))
			public String zero;

			public Format getFormat() {
				return JSON.global;
			}

			public URL getURL() {
				return target;
			}
		}

		TestBean bean = new TestBean();

		bean.load();

		Assert.assertNotNull("Property missing, Or website changed it's content", bean.zero);
		Assert.assertNotNull("Property missing, Or website changed it's content", bean.one);
		Assert.assertNotNull("Property missing, Or website changed it's content", bean.seven);
	}
}
