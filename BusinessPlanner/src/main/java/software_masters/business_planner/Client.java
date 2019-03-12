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
	static ServerInterface server;
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
	public void addUser(String name, String newUsername, String newPassword, String deptName, boolean admin)
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
	 * 
	 */
	public void savePlan()
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
	 * @param editable
	 */
	public void changeEditStatus(boolean editable)
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
	
	//Not sure if this will add the section in the right spot or not
	//    //s is node to be added to
	/**
	 * @param s
	 */
	public void editAdd(TemplateSection s)
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
	 * @param s
	 */
	public void editRemove(TemplateSection s)
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
	 * @param s
	 * @param content
	 */
	public void editAddContent(TemplateSection s, Content content)
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
	
	public void editSetContent(TemplateSection s, ArrayList<Content> contents)
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
	 * @param args
	 */
	public static void main(String[] args)
	{

		String host = (args.length < 1) ? null : args[0];
		try
		{
			Registry registry = LocateRegistry.getRegistry(host);
			server = (ServerInterface) registry.lookup("Server");
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

