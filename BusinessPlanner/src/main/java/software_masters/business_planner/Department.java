package software_masters.business_planner;

import java.util.ArrayList;

/**
 * @author Courtney
 *
 */
public class Department
{
	String departmentName;
	ArrayList<User> admins;
	ArrayList<User> users;
	ArrayList<Template> plans;
	BusinessPlanner bp;
	
	/**
	 * @param deptName name of the department
	 */
	Department(String deptName)
	{
		this.departmentName = deptName;
		this.admins = new ArrayList<User>();
		this.users = new ArrayList<User>();
		this.plans = new ArrayList<Template>();
	}
	
	// blank constructor for XML
	Department()
	{
		this("hello");
	}

	
	/**
	 * @param user to add to department's user list
	 */
	public void addUser(User user)
	{
		users.add(user);
	}
	
	/**
	 * @param user to add to department's user list
	 */
	public void addAdmin(User user)
	{
		admins.add(user);
	}
	
	/**
	 * @param planName name of a plan the user wants
	 * @return Template plan with the given plan name
	 * 
	 * 
	 * If plan name does not match any in the list, exception is thrown
	 */
	public Template getPlan(String planName) 
	{
		Template plan;
		Template tempPlan;
		
		for (int i = 0; i < plans.size(); i++)
		{
			tempPlan = plans.get(i);
			
			if (tempPlan.getUserTemplateName().equals(planName))
			{
				plan = tempPlan;
				return plan;
			}
		}
		
		throw new IllegalArgumentException("No plan exists with that name");		

	}
	
	/**
	 * @param plan to add to list of plans
	 */
	public void addPlan(Template plan)
	{
		plans.add(plan);
	}
	
	
	
	/**
	 * @param plan to update in list after changes are made
	 * 
	 * If plan does not exist, exception is thrown
	 */
	public void updatePlan(Template plan)
	{
		Template tempPlan;
		
		for (int i = 0; i < plans.size(); i++)
		{
			tempPlan = plans.get(i);
			
			if (tempPlan.getUserTemplateName().equals(plan.getUserTemplateName()))
			{
				plans.remove(tempPlan);
				plans.add(plan);
			}
		}
		throw new IllegalArgumentException("This plan does not exist in this department");		

	}
	
	
	
	
	/**
	 * @return String department name
	 */
	public String getDeptName()
	{
		return departmentName;
	}

	/**
	 * @return User list of admin users
	 */
	public ArrayList<User> getAdmins()
	{
		return admins;
	}

	/**
	 * @return User list of users
	 */
	public ArrayList<User> getUsers()
	{
		return users;
	}

	/**
	 * @return Template list of plans for the department
	 */
	public ArrayList<Template> getPlans()
	{
		return plans;
	}

	/**
	 * @return BusinessPlanner business planner object
	 * 
	 * ??????? NEED THIS ??????
	 */
	public BusinessPlanner getBp()
	{
		return bp;
	}
	
	
}
