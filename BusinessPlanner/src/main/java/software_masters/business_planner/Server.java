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
	BusinessPlanner bp = new BusinessPlanner();
	
	/**
	 * 
	 */
	public Server()
	{
		
		User u = new User("Steve", "Steve.user", "Steve.pass", "Init", true);
		admins.put("Steve.user", u);
		Department d = new Department("Init");
		dept.put("Init", d);
		
	}

	
	/* 
	 * creates and adds a user to either the admins or users hash based on given information
	 * 
	 * 
	 * (non-Javadoc)
	 * @see software_masters.business_planner.ServerInterface#addUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public void addUser(String name, String newUserName, String newPassword, String deptName, boolean admin)
	{
		User u = new User(name, newUserName, newPassword, deptName, admin);
		Department d = dept.get(deptName);
		//System.out.println(d);
		if (admin == true)
		{
			admins.put(newUserName, u);
			d.addAdmin(u);
		}

		else
		{
			users.put(newUserName, u);
			d.addUser(u);
		}
	}


	/* 
	 * Looks up the client's given username in the admins hashTable, which returns a user object
	 * Checks client's password to see if it matches
	 * If so, returns the department name and string of admin boolean
	 * 
	 * If username or password do not match, throw exception
	 * 
	 * (non-Javadoc)
	 * @see software_masters.business_planner.ServerInterface#adminLogin(java.lang.String, java.lang.String)
	 */
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

	/* 
	 * Looks up the client's given username in the users hashTable, which returns a user object
	 * Checks client's password to see if it matches
	 * If so, returns the department name
	 * 
	 * If username or password do not match, throw exception
	 * 
	 * (non-Javadoc)
	 * @see software_masters.business_planner.ServerInterface#userLogin(java.lang.String, java.lang.String)
	 */
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

	/* 
	 * calls getPlan in the user's department
	 * 
	 * (non-Javadoc)
	 * @see software_masters.business_planner.ServerInterface#getPlan(java.lang.String, java.lang.String)
	 */
	public Template getPlan(String planName, String deptName)
	{
		Department userDept = dept.get(deptName);
		Template plan = userDept.getPlan(planName);

		return plan;
	}

	/* 
	 * calls update plan in the user's department
	 * 
	 * (non-Javadoc)
	 * @see software_masters.business_planner.ServerInterface#updatePlan(software_masters.business_planner.Template, java.lang.String)
	 */
	public void updatePlan(Template plan, String deptName)
	{
		Department userDept = dept.get(deptName);
		userDept.updatePlan(plan);
		plan.save();

	}
	
	public void addDept(String deptName)
	{
		Department d = new Department(deptName);
		dept.put(deptName, d);
		
	}

	/**
	 * Main to start the server
	 * @param args
	 */
	public static void main(String args[])
	{

		try
		{
			Server server = new Server();
			ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(server, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Server", stub);

			System.err.println("Server ready");
		} 
		catch (Exception e)
		{
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see software_masters.business_planner.ServerInterface#sayHello()
	 */
	public String sayHello() throws RemoteException
	{
		
		return "Hello";
	}
}
