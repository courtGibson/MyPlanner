package software_masters.business_planner;
import java.util.Scanner;

/**
 * This class represents a content section that is all text
 * 
 * 
 * @author Wesley Murray
 * @author Lee Kendall
 * 
 * @since 2019-02-23
 * 
 * 
 */

public class Text implements Content {

	private String textString;

	/**
	 * @return the textString
	 */
	private String getTextString() {
		return textString;
	}

	/**
	 * @param textString the textString to set
	 */
	private void setTextString(String textString) {
		this.textString = textString;
	}

	/**
	 * this method demonstrates how a user might interact with the content of TemplateSection
	 */
	public void display() {
		Scanner input = new Scanner(System.in);
		setTextString(input.next());
		input.close();
		System.out.println(textString);
	}
	
	/**
	 * @return copy of this content object
	 */
	public Content copy()
	{
		Text copy = new Text();
		copy.setTextString(this.textString);
		return copy;
	}

}
