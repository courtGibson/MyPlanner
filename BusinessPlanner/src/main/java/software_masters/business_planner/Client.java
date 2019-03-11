/**
 * 
 */
package software_masters.business_planner;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Courtney
 *
 */

public class Client
{

	private Client()
	{

	}

	public static void main(String[] args)
	{

		String host = (args.length < 1) ? null : args[0];
		try
		{
			Registry registry = LocateRegistry.getRegistry(host);
			ServerInterface stub = (ServerInterface) registry.lookup("Hello");
			String response = stub.sayHello();
			System.out.println("response: " + response);
		} catch (Exception e)
		{
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}

