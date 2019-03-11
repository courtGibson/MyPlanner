/**
 * 
 */
package software_masters.business_planner;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * @author Courtney
 *
 */

public class Client
{

	String name;
	String username;
	String password;
	String departmentName;
	boolean admin;
	Server server;
	Template plan;
	
	private Client(String name, String username, String password)
	{
		this.name = name;
		this.username = username;
		this.password = password;
		this.departmentName = null;
		this.admin = false;
		this.server = server;
		this.plan = null; 
		
	}
	
	private Client(String name, String newUsername, String newPassword, String deptName, boolean admin)
	{
		this.name = name;
		this.username = newUsername;
		this.password = newPassword;
		this.departmentName = deptName;
		this.admin = admin;
		this.server = server;
		this.plan = null;
		
	}

	public void addUser(String name, String newUsername, String newPassword, String deptName, boolean admin)
	{
		server.addUsers(name, newUsername, newPassword, deptName, admin);
	}
	
	public void adminLogin(String enteredUsername, String enteredPassword)
	{
		String[] returnedInfo = server.adminLogin(enteredUsername, enteredPassword);
		this.departmentName = returnedInfo[0];
		this.admin = Boolean.valueOf(returnedInfo[1]);
	}
	
	public void userLogin(String enteredUsername, String enteredPassword)
	{
		String returnedInfo = server.userLogin(enteredUsername, enteredPassword);
		this.departmentName = returnedInfo;
		
	}

	public Template getPlan(String planName) 
	{
		return server.getPlan(planName, departmentName);
	}

	public ArrayList<Template> getDeptPlans()
	{
		Department currentDepartment = server.dept.get(departmentName);
		return currentDepartment.getPlans();
	}
	
	//Need help figuring out how to make a new Template with their code
	public void makePlan(Template t)
	{
		//Template newPlan = 
	}
	
	public void savePlan()
	{
		server.updatePlan(plan, departmentName);
	}

	public void changeEditStatus(boolean editable)
	{
		plan.setEditable(editable);
		savePlan();
	}
	
	//Not sure if this will add the section in the right spot or not
	public void editAdd(TemplateSection s)
	{
		plan.getRoot().addChild(s);
		savePlan();
		
	}

	//Same here
	public void editRemove(TemplateSection s)
	{
		plan.getRoot().removeChild(s);
		savePlan();
		
	}
	
	//Not sure how to access past the root again
	public void editContent(TemplateSection s, String content)
	{
		ArrayList<String> contents = new ArrayList<String>();
		contents.add(content);
		//plan.getRoot().setContents(contents);
	}
	
	
	
	public static void main(String[] args)
	{

		String host = (args.length < 1) ? null : args[0];
		try
		{
			Registry registry = LocateRegistry.getRegistry(host);
			ServerInterface stub = (ServerInterface) registry.lookup("Hello");
			//String response = stub.sayHello();
			//System.out.println("response: " + response);
		} 
		catch (Exception e)
		{
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}

	public String getName()
	{
	
		return name;
	}

	public void setName(String name)
	{
	
		this.name = name;
	}

	public String getUsername()
	{
	
		return username;
	}

	public void setUsername(String username)
	{
	
		this.username = username;
	}

	public String getPassword()
	{
	
		return password;
	}

	public void setPassword(String password)
	{
	
		this.password = password;
	}

	public String getDepartmentName()
	{
	
		return departmentName;
	}

	public void setDepartmentName(String departmentName)
	{
	
		this.departmentName = departmentName;
	}

	public boolean isAdmin()
	{
	
		return admin;
	}

	public void setAdmin(boolean admin)
	{
	
		this.admin = admin;
	}

	public Server getServer()
	{
	
		return server;
	}

	public void setServer(Server server)
	{
	
		this.server = server;
	}

	public Template getPlan()
	{
	
		return plan;
	}

	public void setPlan(Template plan)
	{
	
		this.plan = plan;
	}
}

