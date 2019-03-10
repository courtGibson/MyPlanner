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
	 * @param deptName
	 */
	Department(String deptName)
	{
		this.departmentName = deptName;
	}
	
	Department()
	{
		this("hello");
	}

	
	/**
	 * @param user
	 */
	public void addUser(User user)
	{
		users.add(user);
	}
	
	/**
	 * @param planName
	 * @return Template
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
	 * @param plan
	 */
	public void addPlan(Template plan)
	{
		plans.add(plan);
	}
	
	
	
	/**
	 * @param plan
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
	 * @return
	 */
	public String getDeptName()
	{
		return departmentName;
	}

	/**
	 * @return
	 */
	public ArrayList<User> getAdmins()
	{
		return admins;
	}

	/**
	 * @return
	 */
	public ArrayList<User> getUsers()
	{
		return users;
	}

	/**
	 * @return
	 */
	public ArrayList<Template> getPlans()
	{
		return plans;
	}

	/**
	 * @return
	 */
	public BusinessPlanner getBp()
	{
		return bp;
	}
	
	
}
