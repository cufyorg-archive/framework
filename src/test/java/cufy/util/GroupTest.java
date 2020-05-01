package cufy.util;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("JavaDoc")
public class GroupTest {
	@SuppressWarnings("MessageMissingOnJUnitAssertion")
	@Test
	public void docs() {
		Group food = new HashGroup(Arrayz.asList("pizza", "potato", "apple", "orange"));
		Group healthy = food.subGroup("healthy", f -> !f.equals("pizza"));
		Group h = food.subGroup("healthy");

		//even if the group 'food' is changed, the subgroups will remain equal forever
		Assert.assertEquals(healthy, h);
	}
}
