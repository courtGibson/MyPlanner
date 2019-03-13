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
	 * @throws RemoteException
	 */
	public String[] adminLogin(String username, String password) throws RemoteException;

	/**
	 * @param username
	 * @param password
	 * @return
	 * @throws RemoteException
	 */
	public String userLogin(String username, String password) throws RemoteException;

	/**
	 * @param planName
	 * @param deptName
	 * @return
	 * @throws RemoteException
	 */
	public Template getPlan(String planName, String deptName) throws RemoteException;

	/**
	 * @param plan
	 * @param deptName
	 * @throws RemoteException
	 */
	public void updatePlan(Template plan, String deptName) throws RemoteException;
	
	public void addDept(String deptName) throws RemoteException;
	
	/**
	 * @return
	 * @throws RemoteException
	 */
	String sayHello() throws RemoteException;
}

