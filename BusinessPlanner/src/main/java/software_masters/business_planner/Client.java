/**
 * 
 */
package software_masters.business_planner;

import java.rmi.RemoteException;
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
	static Server server;
	Template plan;
	
	/**
	 * @param name of client
	 * @param username of client
	 * @param password of client
	 */
	private Client(String name, String username, String password)
	{
		this.name = name;
		this.username = username;
		this.password = password;
		this.departmentName = null;
		this.admin = false;
		this.plan = null; 	
	}
	

	/**
	 * @param name of new user
	 * @param newUsername of new user
	 * @param newPassword of new user
	 * @param deptName new user belongs to
	 * @param admin value of new user
	 */
	public void addUser(String name, String newUsername, String newPassword, String deptName, boolean admin) throws RemoteException
	{
		if(admin == true) 
		{
			server.addUsers(name, newUsername, newPassword, deptName, admin);
		}
		else
		{
			throw new IllegalArgumentException("Only admins can add users.");
		}
	}
	
	/**
	 * @param enteredUsername of admin client logging in
	 * @param enteredPassword of admin client logging in
	 */
	public void adminLogin(String enteredUsername, String enteredPassword) throws RemoteException
	{
		String[] returnedInfo = server.adminLogin(enteredUsername, enteredPassword);
		this.departmentName = returnedInfo[0];
		this.admin = Boolean.valueOf(returnedInfo[1]);
	}
	
	/**
	 * @param enteredUsername of user client logging in
	 * @param enteredPassword of user client logging in
	 */
	public void userLogin(String enteredUsername, String enteredPassword) throws RemoteException
	{
		String returnedInfo = server.userLogin(enteredUsername, enteredPassword);
		this.departmentName = returnedInfo;
		
	}

	/**
	 * @param planName requested by client
	 * @return Template plan with that name if it exists, 
	 *               if not, throw exception
	 */
	public Template getPlan(String planName) throws RemoteException
	{
		
		Template plan = server.getPlan(planName, departmentName);
		this.setPlan(plan);
		return plan;
	}

	/**
	 * @return ArrayList of plans in the department
	 */
	public ArrayList<Template> getDeptPlans() throws RemoteException
	{
		Department currentDepartment = server.dept.get(departmentName);
		return currentDepartment.getPlans();
	}
	
	
	/**
	 * @param templateName of the template the client wants to create a plan based on
	 * @param newPlanName of the new plan **must include year in name
	 * 
	 * If not an admin, throw exception
	 * If the given template name does not exist, throw exception
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
		else if(admin == false)
		{
			throw new IllegalArgumentException("Only admins can make plans.");
		}
		else
		{
			throw new IllegalArgumentException("No template with that name exists.");
		}
	
	}
	
	/**
	 * Calls update plan method in the server 
	 * If not an admin, throw exception
	 */
	public void savePlan() throws RemoteException
	{
		if(admin == true || plan.isEditable() == true) 
		{
			server.updatePlan(plan, departmentName);
		}
		else
		{
			throw new IllegalArgumentException("Plan cannot be saved.");
		}
		
	}

	/**
	 * @param editable boolean to be set for the client's current plan
	 * If not an admin, throw exception
	 */
	public void changeEditStatus(boolean editable) throws RemoteException
	{
		if(admin == true) 
		{
			plan.setEditable(editable);
			savePlan();
		}
		else
		{
			throw new IllegalArgumentException("Only admins can change the editable status of a plan.");
		}
		
	}
	
	/**
	 * @param s TemplateSection to add a branch to
	 * 
	 * If not admin or plan is not currently editable, throw exception
	 */
	public void editAdd(TemplateSection s) throws RemoteException
	{
		if(admin == true || plan.isEditable() == true) 
		{
			server.bp.setUserTemplate(plan);
			server.bp.setCurrent(s);
			server.bp.addBranch();
			savePlan();
		}
		else
		{
			throw new IllegalArgumentException("Cannot add plan sections.");
		}
	}

	/**
	 * @param s TemplateSection to remove
	 * 
	 * If not admin or plan is not currently editable, throw exception
	 */
	public void editRemove(TemplateSection s) throws RemoteException
	{
		if(admin == true || plan.isEditable() == true) 
		{
			server.bp.setUserTemplate(plan);
			server.bp.setCurrent(s);
			server.bp.removeSection();
			savePlan();
		}
		else
		{
			throw new IllegalArgumentException("Cannot remove plan sections.");
		}

		
	}
	

	/**
	 * @param s TemplateSection to add content to
	 * @param content to be added
	 * 
	 * If not admin or plan is not currently editable, throw exception
	 */
	public void editAddContent(TemplateSection s, Content content) throws RemoteException
	{
		if(admin == true || plan.isEditable() == true) 
		{
			server.bp.setUserTemplate(plan);
			server.bp.setCurrent(s);
			server.bp.getCurrent().addContent(content);
			savePlan();
		}
		else
		{
			throw new IllegalArgumentException("Cannot add plan content.");
		}
	}
	
	public void editSetContent(TemplateSection s, ArrayList<Content> contents) throws RemoteException
	{		
		if(admin == true || plan.isEditable() == true) 
		{
			server.bp.setUserTemplate(plan);
			server.bp.setCurrent(s);
			server.bp.getCurrent().setContents(contents);
			savePlan();
		}
		else
		{
			throw new IllegalArgumentException("Cannot edit plan content.");
		}
		
	}
	
	
	
	/**
	 * Main function that starts the Client
	 * @param args
	 */
	public static void main(String[] args)
	{

		String host = (args.length < 1) ? null : args[0];
		try
		{
			Registry registry = LocateRegistry.getRegistry(host);
			server = (Server) registry.lookup("Server");
			String response = server.sayHello();
			System.out.println("response: " + response);
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

