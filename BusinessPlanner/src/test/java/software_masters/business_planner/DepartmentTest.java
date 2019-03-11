package software_masters.business_planner;

import static org.junit.Assert.*;

import junit.framework.TestCase;

public class DepartmentTest extends TestCase
{
	public void test()
	{
		// ******ability to make changes to a department 
		// ******   is controlled in client
		
		//make department
		Department d1 = new Department("Biology");
		//make a user
		User u1 = new User("bob person", "bob.person", "hello123", "Biology", false);
		// check user list is empty, add user, and re-check
		assertEquals(0, d1.getUsers().size());
		d1.addUser(u1);
		assertEquals(1, d1.getUsers().size());
		//check if this user was added
		assertEquals("bob person", d1.getUsers().get(0).getName());
		
		//make an admin user
		User u2 = new User("steve person", "steve.person", "aPassword", "Biology", true);
		// check admin list is empty, add admin, and re-check
		assertEquals(0, d1.getAdmins().size());
		d1.addAdmin(u2);
		assertEquals(1, d1.getAdmins().size());
		// check this admin was added
		assertEquals("steve person", d1.getAdmins().get(0).getName());
		try 
		{
			d1.getPlan("plan");
			fail( "My method didn't throw when I expected it to" );
		}
		catch(IllegalArgumentException e)
		{
		  e.getMessage();
		}
		
		assertEquals(0, d1.getPlans().size());
		TemplateSection root = new TemplateSection("Vision", "Vision", false);
		TemplateSection mission = new TemplateSection("Mission", "Mission", false);
		TemplateSection obj = new TemplateSection("Objective", "Objective", false);
		root.addChild(mission);
		mission.addChild(obj);
		
		
		Template plan1 = new Template("VMOSA", "Biology 2016", root);
		d1.addPlan(plan1);
		assertEquals(1, d1.getPlans().size());
		assertEquals("Biology 2016", d1.getPlans().get(0).getUserTemplateName());
		
		try
		{
			assertEquals("Biology 2016", d1.getPlan("Biology 2016").getUserTemplateName());
		}
		catch(IllegalArgumentException e)
		{
		  e.getMessage();
		}
		
		//// Not sure how to test this
		TemplateSection obj2 = new TemplateSection("Objective", "Objective2", false);

		plan1.getRoot().getChildren().get(0).addChild(obj2);
		assertEquals(2, plan1.getRoot().getChildren().get(0).getChildren().size());
		
		d1.updatePlan(plan1);
		assertEquals(2, plan1.getRoot().getChildren().get(0).getChildren().size());
	}
		

		
		
		
	
}
