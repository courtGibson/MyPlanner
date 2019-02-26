package software_masters.business_planner;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TemplateSectionTester extends TestCase {
	/**
	 * Verifies method can add children to TemplateSection
	 */
	public void testAddChild() {
		
		//Creates TemplateSections, makes ts2 child of ts1
		TemplateSection ts1 = new TemplateSection("Vision","Vision", false);
		TemplateSection ts2 = new TemplateSection("Mission","Mission", false);
		ts1.addChild(ts2);
		ts2.setParent(ts1);
		
		Assert.assertEquals(ts2, ts1.getChild(0));
	}
	
	/**
	 * Verifies the removeChild method will only remove child objects if and only if at least one child still 
	 * remains afterwards.
	 */
	public void testRemoveChild() {
		
		//Creates TemplateSections, makes ts2 and ts3 children of ts1
		TemplateSection ts1 = new TemplateSection("Mission","Mission", false);
		TemplateSection ts2 = new TemplateSection("Objectives","Objective 1", false);
		TemplateSection ts3 = new TemplateSection("Objectives","Objective 2", false);
		ts1.addChild(ts2);
		ts1.addChild(ts3);
		ts2.setParent(ts1);
		ts3.setParent(ts1);
		
		//remove ts2
		ts1.removeChild(ts2);
		Assert.assertEquals(ts3, ts1.getChild(0));
		
		//try to remove ts3
		Assert.assertEquals(false, ts1.removeChild(ts3));
	}
	
	/**
	 * Verifies content objects can be added to a templateSection
	 */
	public void testAddContent() {
		TemplateSection ts1 = new TemplateSection("Vision","Vision", false);
		Text t1=new Text();
	}
	
	/* we do not not find it necessary to remove content since the developer creates that object once and can
	 * fix a mistake by serializing their new template over the old one.
	 */
	
	/**
	 * Verifies that the deepCopy of a templateSection is effective.
	 * It should only clone object if operation is allowed.
	 * It should clone all connected children.
	 */
	public void testDeepCopy() {
		fail("not yet implemented");
	}
}
