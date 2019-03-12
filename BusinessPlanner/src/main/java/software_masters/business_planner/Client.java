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
	
	/**
	 * @param name
	 * @param username
	 * @param password
	 */
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
	
	/**
	 * @param name
	 * @param newUsername
	 * @param newPassword
	 * @param deptName
	 * @param admin
	 */
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

	/**
	 * @param name
	 * @param newUsername
	 * @param newPassword
	 * @param deptName
	 * @param admin
	 */
	public void addUser(String name, String newUsername, String newPassword, String deptName, boolean admin)
	{
		server.addUsers(name, newUsername, newPassword, deptName, admin);
	}
	
	/**
	 * @param enteredUsername
	 * @param enteredPassword
	 */
	public void adminLogin(String enteredUsername, String enteredPassword)
	{
		String[] returnedInfo = server.adminLogin(enteredUsername, enteredPassword);
		this.departmentName = returnedInfo[0];
		this.admin = Boolean.valueOf(returnedInfo[1]);
	}
	
	/**
	 * @param enteredUsername
	 * @param enteredPassword
	 */
	public void userLogin(String enteredUsername, String enteredPassword)
	{
		String returnedInfo = server.userLogin(enteredUsername, enteredPassword);
		this.departmentName = returnedInfo;
		
	}

	/**
	 * @param planName
	 * @return
	 */
	public Template getPlan(String planName) 
	{
		return server.getPlan(planName, departmentName);
	}

	/**
	 * @return
	 */
	public ArrayList<Template> getDeptPlans()
	{
		Department currentDepartment = server.dept.get(departmentName);
		return currentDepartment.getPlans();
	}
	
	//Need help figuring out how to make a new Template with their code
	/**
	 * @param t
	 */
	public void makePlan(String templateName, String newPlanName)
	{
		if (admin == true && (templateName.equals("VMOSA") || templateName.equals("Centre") || templateName.equals("OKR")))
		{
			server.bp.chooseTemplate(templateName, newPlanName);
			Template newPlan = server.bp.getUserTemplate();
			Department d = server.dept.get(departmentName);
			d.addPlan(newPlan);
		}
		else
		{
			throw new IllegalArgumentException("This template does not exist.");
		}
	
	}
	
	/**
	 * 
	 */
	public void savePlan()
	{
		server.updatePlan(plan, departmentName);
	}

	/**
	 * @param editable
	 */
	public void changeEditStatus(boolean editable)
	{
		plan.setEditable(editable);
		savePlan();
	}
	
	//Not sure if this will add the section in the right spot or not
	//    //s is node to be added to
	/**
	 * @param s
	 */
	public void editAdd(TemplateSection s)
	{
		///////// need to figure out how to build branches
		server.bp.setUserTemplate(plan);
		server.bp.setCurrent(s);
		server.bp.addBranch();
		savePlan();
		
	}

	//Same here
	//   // maybe fixed it??
	/**
	 * @param s
	 */
	public void editRemove(TemplateSection s)
	{
		server.bp.setUserTemplate(plan);
		server.bp.setCurrent(s);
		server.bp.removeSection();
		savePlan();
		
	}
	
	//Not sure how to access past the root again
	//   //should be fine since adding directly to the "node"
	/**
	 * @param s
	 * @param content
	 */
	public void editAddContent(TemplateSection s, Content content)
	{
		server.bp.setUserTemplate(plan);
		server.bp.setCurrent(s);
		
		server.bp.getCurrent().addContent(content);
		savePlan();
	}
	
	public void editSetContent(TemplateSection s, ArrayList<Content> contents)
	{
		server.bp.setUserTemplate(plan);
		server.bp.setCurrent(s);
		
		server.bp.getCurrent().setContents(contents);
		savePlan();
	}
	
	
	
	/**
	 * @param args
	 */
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


	public String getUsername()
	{
	
		return username;
	}


	public String getPassword()
	{
	
		return password;
	}


	public String getDepartmentName()
	{
	
		return departmentName;
	}


	public boolean isAdmin()
	{
	
		return admin;
	}


	public Server getServer()
	{
	
		return server;
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

