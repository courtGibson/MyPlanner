/**
 * 
 */
package software_masters.business_planner;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
/**
 * @author Courtney
 *
 */


public interface ServerInterface extends Remote 
{

	public void addUsers(String name, String newUserName, String newPassword, String deptName, boolean admin);

	public String[] adminLogin(String username, String password);

	public String userLogin(String username, String password);

	public Template getPlan(String planName, String deptName);

	public void updatePlan(Template plan, String deptName);
}

