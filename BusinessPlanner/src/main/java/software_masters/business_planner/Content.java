package software_masters.business_planner;
 

/**
 * This interface represents any content that may be added to a business plan
 * section.
 * 
 * 
 * @author Wesley Murray
 * @author Lee Kendall
 * 
 * @since 2019-02-23
 * 
 * 
 */

public interface Content
{
	/**
	 * this method visualizes content
	 */
	public void display();

	/**
	 * 
	 * @return copy of the content object
	 */
	public Content copy();
}
