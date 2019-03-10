/**
 * 
 */
package software_masters.business_planner;

/**
 * @author Courtney
 *
 */
public class User
{
	String name;
	String username;
	String password;
	String deptName;
	boolean admin;
	
	/**
	 * @param name
	 * @param username
	 * @param password
	 * @param deptName
	 * @param admin
	 */
	public User(String name, String username, String password, String deptName, boolean admin)
	{
		this.name = name;
		this.username = username;
		this.password = password;
		this.deptName = deptName;
		this.admin = admin;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName()
	{
		return deptName;
	}

	/**
	 * @return the admin
	 */
	public boolean isAdmin()
	{
		return admin;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(boolean admin)
	{
		this.admin = admin;
	}
	
	
	
	
}
