/**
 * 
 */
package software_masters.business_planner;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Courtney
 *
 */

public class Server implements ServerInterface
{

	public Server()
	{
	}

	public String sayHello()
	{
		return "Hello, world!";
	}

	public static void main(String args[])
	{

		try
		{
			Server obj = new Server();
			ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(obj, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Hello", stub);

			System.err.println("Server ready");
		} catch (Exception e)
		{
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}

