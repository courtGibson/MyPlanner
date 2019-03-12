/**
 * 
 */
package software_masters.business_planner;

import static org.junit.Assert.*;

import java.rmi.Naming;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Courtney
 *
 */
public class ServerTest
{

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		java.rmi.registry.LocateRegistry.createRegistry(1099);
        System.out.println("RMI registry ready.");
        

        // client set up

        Client c = null;
        c = (Client) Naming.lookup("//Server");

	}

	@Test
	public void test()
	{
		fail("Not yet implemented"); // TODO
	}

}
