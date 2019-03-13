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
 * @author Keenan
 *
 */


public interface ServerInterface extends Remote 
{
	
	/**
	 * @param name of the user
	 * @param newUserName  of the user
	 * @param newPassword  of the user
	 * @param deptName the user belongs to
	 * @param admin value of the user
	 * @throws RemoteException
	 */
	public void addUser(String name, String newUserName, String newPassword, String deptName, boolean admin) throws RemoteException;

	/**
	 * @param username of the admin client
	 * @param password of the admin client
	 * @return String[] information required by client, String deptName and String of boolean admin value
	 * @throws RemoteException if user with that information does not exist
	 */
	public String[] adminLogin(String username, String password) throws RemoteException;

	/**
	 * @param username of the user client
	 * @param password of the user client
	 * @return String department name
	 * @throws RemoteException if user with that info does not exist
	 */
	public String userLogin(String username, String password) throws RemoteException;

	/**
	 * @param planName name of the plan
	 * @param deptName name of the department
	 * @return Template plan
	 * @throws RemoteException if a plan with that name does not exist
	 */
	public Template getPlan(String planName, String deptName) throws RemoteException;

	/**
	 * @param plan Template plan to update
	 * @param deptName name of the department
	 * @throws RemoteException if plan does not exist
	 */
	public void updatePlan(Template plan, String deptName) throws RemoteException;
	
	/**
	 * @param deptName name of the department
	 * @throws RemoteException if department name does not match a department
	 */
	public void addDept(String deptName) throws RemoteException;
	
	/**
	 * @return String
	 * @throws RemoteException if it doesn't exist
	 */
	String sayHello() throws RemoteException;
}

