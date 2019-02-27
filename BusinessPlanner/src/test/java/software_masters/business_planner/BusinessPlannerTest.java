package software_masters.business_planner;

import junit.framework.Assert;
import junit.framework.TestCase;

public class BusinessPlannerTest extends TestCase {

	/**
	 * This method tests:
	 * 	load from xml
	 * 	get and set current template section
	 * 	demonstrates structure navigation
	 * 	demonstrates add and remove constraints
	 * 	demonstrates adding a branch
	 * 
	 */
	public void testVMOSA()
	{
		BusinessPlanner planner = new BusinessPlanner();
		
		//this method loads template from xml
		planner.chooseTemplate("VMOSA","myVMOSA");
		
		//tests adding an extra vision. should return null
		Assert.assertNull(planner.getCurrent().deepCopy());
		
		//tests removing mission
		Assert.assertFalse(planner.getCurrent().removeChild(planner.getCurrent().getChild(0)));
		
		String[] cats= new String[] {"Mission","Objectives","Strategies","Action Plans"};
		Assert.assertEquals("Vision", planner.getCurrent().getCategory());
		for(String cat:cats) {
			planner.setCurrent(planner.getCurrent().getChild(0));
			Assert.assertEquals(cat, planner.getCurrent().getCategory());
		}
		
		//tests the addition of a new branch
		planner.setCurrent(planner.getCurrent().getParent().getParent());//objective level
		TemplateSection curCopy = planner.addBranch();
		curCopy.setName("Objective 2");
		planner.getCurrent().setName("Objective 1");
		planner.setCurrent(planner.getCurrent().getParent());
		Assert.assertEquals(planner.getCurrent().getChild(0).getName(),"Objective 1");
		Assert.assertEquals(planner.getCurrent().getChild(1).getName(),"Objective 2");
		
	}
	
	public void testCentreAssessment()
	{
		
	}
	
	public void testOKR()
	{
		
	}
}
