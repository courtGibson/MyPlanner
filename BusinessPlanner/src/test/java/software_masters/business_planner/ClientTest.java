package software_masters.business_planner;

import static org.junit.Assert.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.BeforeClass;
import org.junit.Test;

public class ClientTest
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

	@Test
	public static void test() throws RemoteException, NotBoundException
	{
		//Make a new client to match the default client info created when the 
		// server is made, and login in using the admin login
		Client client = new Client("Steve", "Steve.user", "Steve.pass", serverProxy);
		client.adminLogin("Steve.user", "Steve.pass");
		
		// make a new department and check to see if added to server's dept hash
		client.addDept("Biology");
		assertEquals("Biology", server.dept.get("Biology").getDeptName());
		
		//add a new user and check to see if added to server's user hash
		client.addUser("Sarah", "Sarah.user", "Sarah.pass", "Biology", false);
		assertEquals("Sarah", server.users.get("Sarah.user").getName());
		
		//make a new client for the new user that was just added
		// and login using user login
		Client userClient = new Client("Sarah", "Sarah.user", "Sarah.pass", serverProxy);
		userClient.userLogin("Sarah.user", "Sarah.pass");
		
		// try to add a department through userClient (not an admin)
		try
		{
			userClient.addDept("History");
			fail("Should not have been able to add department");
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
