/**
 * 
 */
package software_masters.business_planner;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author Courtney
 *
 */

public class Server implements ServerInterface
{

	Hashtable<String, User> users = new Hashtable<String, User>();
	Hashtable<String, User> admins = new Hashtable<String, User>();
	Hashtable<String, Department> dept = new Hashtable<String, Department>();
	BusinessPlanner bp;
	
	public Server()
	{
	}

	public void addUsers(String name, String newUserName, String newPassword, String deptName, boolean admin)
	{
		User u = new User(name, newUserName, newPassword, deptName, admin);

		if (admin == true)
		{
			admins.put(newUserName, u);
		}

		else
		{
			users.put(newUserName, u);
		}
	}

	public String[] adminLogin(String username, String password)
	{

		User u = admins.get(username);

		if (u.getPassword() == password)
		{

			String[] information = { u.deptName, String.valueOf(u.isAdmin()) };

			return information;
		}

		else
		{
			throw new IllegalArgumentException("Username and password do not match a user.");
		}

	}

	public String userLogin(String username, String password)
	{

		User u = users.get(username);

		if (u.getPassword() == password)
		{

			return u.getDeptName();
		}

		else
		{
			throw new IllegalArgumentException("Username and password do not match a user.");
		}

	}

	public Template getPlan(String planName, String deptName)
	{
		Department userDept = dept.get(deptName);
		Template plan = userDept.getPlan(planName);

		return plan;
	}

	public void updatePlan(Template plan, String deptName)
	{
		Department userDept = dept.get(deptName);
		userDept.updatePlan(plan);

	}

	public static void main(String args[])
	{

		try
		{
			Server obj = new Server();
			ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(obj, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Sprint 2", stub);

			System.err.println("Server ready");
		} 
		catch (Exception e)
		{
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
