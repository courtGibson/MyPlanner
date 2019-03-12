/**
 * 
 */
package software_masters.business_planner;

import java.io.Serializable;

/**
 * @author Courtney
 *
 */
public class User implements Serializable
{
	String name;
	String username;
	String password;
	String deptName;
	boolean admin;
	
	/**
	 * @param name name of the user
	 * @param username username of the user
	 * @param password password of the user
	 * @param deptName department name of the user
	 * @param admin is the user an admin
	 */
	public User(String name, String username, String password, String deptName, boolean admin)
	{
		this.name = name;
		this.username = username;
		this.password = password;
		this.deptName = deptName;
		this.admin = admin;
	}
	
	// blank constructor of XML
	public User()
	{
		this("name", "name.name", "namePass", "dept", false);
	}

	/**
	 * @return the name of the user
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the username of the user
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @return the password of the user
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @return the deptName of the user
	 */
	public String getDeptName()
	{
		return deptName;
	}

	/**
	 * @return the admin value of the user
	 */
	public boolean isAdmin()
	{
		return admin;
	}

	/**
	 * @param admin the admin value to set
	 */
	public void setAdmin(boolean admin)
	{
		this.admin = admin;
	}
	
	
	
	
}
