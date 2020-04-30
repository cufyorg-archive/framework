package cufy.io.loadable;

import cufy.beans.AbstractBean;
import cufy.meta.Recipe;
import cufy.text.Format;
import cufy.text.json.JSON;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("JavaDoc")
public class FileLoadableTest {
	@Test
	public void file_format_bean_json_load_save() throws IOException {
		File temp = new File("/projects/test/files/file-format-loadable-bean-test-temp");
		temp.delete();

		class TestBean extends AbstractBean implements FileLoadable, FormatLoadable {
			@Property(key = @Recipe("za list ta"))
			final public List<String> list = new ArrayList<>();

			public File getFile() {
				return temp;
			}

			public Format getFormat() {
				return JSON.global;
			}
		}

		{
			TestBean bean = new TestBean();

			bean.list.add("Success");

			while (true)
				try {
					bean.save();
					break;
				} catch (IOException e) {
					if (temp.isDirectory()) {
						temp.delete();
					} else if (!temp.exists()) {
						temp.createNewFile();
					} else {
						throw e;
					}
				}
		}
		{
			TestBean bean = new TestBean();

			bean.load();

			Assert.assertEquals("Wrong value stored", "Success", bean.list.get(0));
		}

		temp.delete();
	}
}
