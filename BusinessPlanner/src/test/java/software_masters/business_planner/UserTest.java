package software_masters.business_planner;

import junit.framework.TestCase;

public class UserTest extends TestCase
{
	public void test()
	{
		// ******ability to make a user
		// ******   is controlled in client
		// ****** an admins boolean admin variable is set on successful 
		// ******    admin login
		//                      ????????? set back to false after log out????
		
		// Create new user
		User p1 = new User("bob person", "bob.person", "hello123", "Biology", false);
		// Check users information
		assertEquals("bob person", p1.getName());
		assertEquals("bob.person", p1.getUsername());
		assertEquals("hello123", p1.getPassword());
		assertEquals("Biology", p1.getDeptName());
		assertEquals(false, p1.isAdmin());
		// Set admin value and check
		p1.setAdmin(true);
		assertEquals(true, p1.isAdmin());
		
		// Make new user and check to see if different from first user
		User p2 = new User("steve person", "steve.person", "aPassword", "History", false);
		assertEquals(false, p1.getName().equals(p2.getName()));
		assertEquals(false, p1.equals(p2));
	}
}
