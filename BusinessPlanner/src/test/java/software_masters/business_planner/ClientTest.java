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
		Client client = new Client("Steve", "Steve.user", "Steve.pass", serverProxy);
		client.adminLogin("Steve.user", "Steve.pass");
		
		client.addDept("Biology");
		assertEquals("Biology", server.dept.get("Biology").getDeptName());
		client.addUser("Sarah", "Sarah.user", "Sarah.pass", "Biology", false);
		
		
		
		/*try
		{*/
			
		/*}
		catch(IllegalArgumentException e)
		{
		  e.getMessage();
		}*/
		
	}
	
	public static void main(String[] args) throws Exception
	{
		setUpBeforeClass();
		test();
	}

}
