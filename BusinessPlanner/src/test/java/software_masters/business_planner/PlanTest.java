package software_masters.business_planner;

import static org.junit.Assert.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

public class PlanTest
{

	static Registry registry;
	static Server server;
	static Server serverProxy;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		registry = LocateRegistry.createRegistry(1099);
		server = new Server();
		registry.rebind("server", server);
		serverProxy = (Server) registry.lookup("server");
	}

	@SuppressWarnings("restriction")
	@Test
	public static void test() throws RemoteException
	{
		// not testing getter/setters because they are generated by eclipse
		
		//Makes a new client to match the default client info created when
		//the server is made, and login in using the admin login
		Client client = new Client("Steve", "Steve.user", "Steve.pass", serverProxy);
		client.adminLogin("Steve.user", "Steve.pass");
		
		//Makes a new plan to work with in the test
		client.makePlan("VMOSA", "testPlan");
		
		//Checks to see if the plan has been made in the department arrayList by getting it
		//and then comparing the template to what we wanted to make
		Template testPlan = client.getDeptPlans().get(0);
		assertEquals("testPlan", testPlan.getUserTemplateName());
		
		//Adds a couple more plans to the department to continue checking
		client.makePlan("Centre_Assessment", "testPlan2");
		client.makePlan("OKR", "testPlan3");
		client.makePlan("VMOSA", "testPlan4");
		
		//Gets the arrayList of plans for the department that the client is
		//associated with and checks to make sure the plans added properly
		ArrayList<Template> listOfPlans = client.getDeptPlans();
		Template testPlan2 = listOfPlans.get(1);
		assertEquals("testPlan2", testPlan2.getUserTemplateName());
		Template testPlan3 = listOfPlans.get(2);
		assertEquals("testPlan3", testPlan3.getUserTemplateName());
		Template testPlan4 = listOfPlans.get(3);
		assertEquals("testPlan4", testPlan4.getUserTemplateName());

		//Now we are going to test for getting a plan 
		Template thisPlan = client.getPlan("testPlan2");
		assertEquals("testPlan2", thisPlan.getUserTemplateName());
		
		//Testing changing the editStatus and then saving 
		client.getPlan("testPlan2");
		client.changeEditStatus(true);
		assertEquals(true, client.getClientsPlan().isEditable());
		

				
				
		//Now we are testing to make sure that a non-admin cannot do things it shouldnt be allowed to do
		//add a new user and check to see if added to server's user hash
		client.addUser("Sarah", "Sarah.user", "Sarah.pass", "Init", false);
		assertEquals("Sarah", server.users.get("Sarah.user").getName());
		
		//make a new client for the new user that was just added
		// and login using user login
		Client userClient = new Client("Sarah", "Sarah.user", "Sarah.pass", serverProxy);
		userClient.userLogin("Sarah.user", "Sarah.pass");
		
		userClient.getPlan("testPlan2");
		
		try
		{
			userClient.makePlan("VMOSA", "ThisShouldntWork");
			fail("Should not have been able to make a plan.");
		}
		catch(IllegalArgumentException e)
		{
		  e.getMessage();
		}
		
		userClient.savePlan();
		
		client.changeEditStatus(false);
		
		try
		{
			userClient.savePlan();
			fail("Should not have been able to save a plan.");
		}
		catch(IllegalArgumentException e)
		{
		  e.getMessage();
		}
		
		try
		{
			userClient.changeEditStatus(true);
			fail("Should not have been able to change the editable status of a plan.");
		}
		catch(IllegalArgumentException e)
		{
		  e.getMessage();
		}
		
		
		//////////////// editing admin
		// set plan to testPlan
		// add another strategy to the objective with admin
		// check to see that objective has two children
		client.getPlan("testPlan");
		assertEquals(1, client.getClientsPlan().getRoot().getChild(0).getChildren().size());
		client.editAdd(client.getClientsPlan().getRoot().getChild(0).getChild(0));
		assertEquals(2, client.getClientsPlan().getRoot().getChild(0).getChildren().size());
		
		// remove the node that was just added with admin
		client.editRemove(client.getClientsPlan().getRoot().getChild(0));
		assertEquals(1, client.getClientsPlan().getRoot().getChildren().size());
		
		//add content with admin
		Text c = new Text();
		c.setTextString("hello");
		
		
		client.editAddContent(client.getClientsPlan().getRoot(), c);
		assertEquals("hello", c.getTextString());
		
		
		//set content with admin
		ArrayList<Content> c2 = new ArrayList<Content>();
		Text t = new Text();
		t.setTextString("hello");
		Text t1 = new Text();
		t.setTextString("goodbye");
		c2.add(t);
		c2.add(t1);
		
		client.editSetContent(client.getClientsPlan().getRoot(), c2);
		assertEquals(2, client.getClientsPlan().getRoot().getContents().size());
		
		//////////////// try editing, non admin
		
		
		userClient.getPlan("testPlan");
		//try to add a node with non admin user
		try
		{
			userClient.editAdd(userClient.getClientsPlan().getRoot().getChild(0).getChild(0));
			fail("should not have added a node");
		}
		catch(IllegalArgumentException e)
		{
		  e.getMessage();
		}
		
		// try to remove a node with non admin user
		try
		{
			userClient.editRemove(client.getClientsPlan().getRoot().getChild(0));
			fail("should not have added a node");
		}
		catch(IllegalArgumentException e)
		{
		  e.getMessage();
		}
		
		// try to add content with non admin user
		try
		{
			userClient.editAddContent(client.getClientsPlan().getRoot(), c);
			fail("should not have added content");
		}
		catch(IllegalArgumentException e)
		{
		  e.getMessage();
		}
		
		// try to set content with non admin user
		try
		{
			userClient.editSetContent(userClient.getClientsPlan().getRoot(), c2);
			fail("should not have added content");
		}
		catch(IllegalArgumentException e)
		{
		  e.getMessage();
		}
		
		//////////////// try editing, non admin, editable
		client.changeEditStatus(true);
		userClient.getPlan("testPlan");
		// try adding a node with non admin user while editable
		try
		{
			// since same plan that client is editing, should be three nodes 
			
			// also shows that if a plan is saved by someone else, it is changed
			userClient.editAdd(userClient.getClientsPlan().getRoot().getChild(0).getChild(0));
			assertEquals(3, userClient.getClientsPlan().getRoot().getChild(0).getChildren().size());
		}
		catch(IllegalArgumentException e)
		{
		  e.getMessage();
		}
		
		// try to remove a node with non admin user while editable
		try
		{
			userClient.editRemove(userClient.getClientsPlan().getRoot().getChild(0));
			assertEquals(1, userClient.getClientsPlan().getRoot().getChildren().size());
		}	
		catch(IllegalArgumentException e)
		{
		  e.getMessage();
		}
		
		// try to add content with non admin user while editable
		try
		{
			userClient.editAddContent(userClient.getClientsPlan().getRoot().getChild(0), c);
			assertEquals("hello", c.getTextString());
		}
		catch(IllegalArgumentException e)
		{
		  e.getMessage();
		}
		
		
		// try to set content with non admin user while editable
		try
		{
			userClient.editSetContent(userClient.getClientsPlan().getRoot().getChild(0), c2);
			assertEquals(2, client.getClientsPlan().getRoot().getChild(0).getContents().size());
		}
		catch(IllegalArgumentException e)
		{
		  e.getMessage();
		}
	}

	public static void main(String[] args) throws Exception
	{
		setUpBeforeClass();
		test();
	}
	
	
}