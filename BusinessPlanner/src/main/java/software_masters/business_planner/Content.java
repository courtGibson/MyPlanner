package software_masters.business_planner;

/**
 * This interface represents any content that may be added to a business plan section.
 * 
 * 
 * @author Wesley Murray
 * @author Lee Kendall
 * 
 * @since 2019-02-23
 * 
 * 
 */

public interface Content {
	/**
	 * This is a method for the user to edit data within content
	 */
	public void edit();
	/**
	 * this method visualizes content 
	 */
	public void display();
}
