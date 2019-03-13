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
    Server server;
	Template plan;
	
	/**
	 * @param name of client
	 * @param username of client
	 * @param password of client
	 * @param serverProxy 
	 */
    public Client(String name, String username, String password, Server server)
	{
		this.name = name;
		this.username = username;
		this.password = password;
		this.departmentName = null;
		this.admin = false;
		this.plan = null; 	
		this.server = server;
	}
	

	/**
	 * @param name of new user
	 * @param newUsername of new user
	 * @param newPassword of new user
	 * @param deptName new user belongs to
	 * @param admin value of new user
	 */
	public void addUser(String name, String newUsername, String newPassword, String deptName, boolean adminVal) throws RemoteException
	{
		if(this.isAdmin() == true) 
		{
			server.addUser(name, newUsername, newPassword, deptName, adminVal);
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
		//System.out.println(this.admin);
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
		if (this.isAdmin() == true && (templateName.equals("VMOSA") || templateName.equals("Centre_Assessment") || templateName.equals("OKR")))
		{
			server.bp.chooseTemplate(templateName, newPlanName);
			Template newPlan = server.bp.getUserTemplate();
			Department d = server.dept.get(departmentName);
			d.addPlan(newPlan);
			newPlan.save();
		}
		else if(this.isAdmin() == false)
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
		if(this.isAdmin() == true || plan.isEditable() == true) 
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
		if(this.isAdmin() == true) 
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
		if(this.admin == true || plan.isEditable() == true) 
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
		if(this.admin == true || plan.isEditable() == true) 
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
	
	
	public void addDept(String newDeptName)
	{
		if (this.admin == true)
		{
			server.addDept(newDeptName);
		}
		else
		{
			throw new IllegalArgumentException("Only admins can make departments.");
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
		if(this.admin == true || plan.isEditable() == true) 
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
		if(this.admin == true || plan.isEditable() == true) 
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
	/*public static void main(String[] args)
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
	}*/

	
	
	
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
	
	public void setServer(Server s)
	{
		this.server = s;
	}


	public Template getClientsPlan()
	{
	
		return plan;
	}

	public void setPlan(Template plan)
	{
	
		this.plan = plan;
	}
}

