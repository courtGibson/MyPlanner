/**
 * 
 */
package software_masters.business_planner;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
/**
 * @author Courtney
 *
 */


public interface ServerInterface extends Remote 
{

	Hashtable<String, User> users = new Hashtable<String, User>();
	Hashtable<String, User> admins = new Hashtable<String, User>();
	Hashtable<String, Department> dept = new Hashtable<String, Department>();
	BusinessPlanner bp = new BusinessPlanner();
	
	public void addUsers(String name, String newUserName, String newPassword, String deptName, boolean admin) throws RemoteException;

	public String[] adminLogin(String username, String password) throws RemoteException;

	public String userLogin(String username, String password) throws RemoteException;

	public Template getPlan(String planName, String deptName) throws RemoteException;

	public void updatePlan(Template plan, String deptName) throws RemoteException;
	
	String sayHello() throws RemoteException;
}

