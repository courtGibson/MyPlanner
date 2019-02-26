package software_masters.business_planner;

import junit.framework.TestCase;
import junit.framework.Assert;

public class TextContentTester extends TestCase {
	
	/**
	 * This method tests if you can add and get text from the content object.
	 */
	public void testTextString() {
		Text t1=new Text();
		t1.setTextString("Hello World");
		Assert.assertEquals("Hello World",t1.getTextString());
	}
	
	/**
	 * This method tests the display method for content. This test essentially does nothing because the application
	 * development is not at this level yet.
	 */
	public void testDisplay() {
		Text t1=new Text();
		t1.display();
		Assert.assertEquals("display", t1.getTextString()); // expect "display"
	}
	
	/**
	 * This method tests that the text content object is effectively copied.
	 */
	public void testCopy() {
		//create text objects
		Text t1=new Text();
		t1.setTextString("Hello World");
		Text t2=(Text) t1.copy();
		
		//verify content is the same
		String expected="Hello World"; //expect "Hello World"
		Assert.assertEquals(expected,t1.getTextString());
		Assert.assertEquals(expected,t2.getTextString());
		
		//verify changing one does not change another
		t2.setTextString("Goodbye World");
		Assert.assertEquals("Hello World",t1.getTextString());
		Assert.assertEquals("Goodbye World",t2.getTextString());
		
	}
	

}
