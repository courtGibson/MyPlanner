package software_masters.business_planner;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TemplateTester extends TestCase {
	/**
	 * Verifies the develop template object is properly serialized and can be read in.
	 */
	public void testSaveAndLoadDeveloper() {
		Template VMOSA=VMOSA_builder.generateTemplate();
		VMOSA.save();
		Template VMOSA_clone=null; //Template.load("VMOSA.dev");
		
		//verify loaded object and serialized object contain the same content
		Assert.assertEquals(VMOSA, VMOSA_clone);
		
		//verify changing one does not change the other
		makeChange(VMOSA,VMOSA_clone);
	}
	
	/**
	 * Verifies the user template can be serialized and read in. This is done separately because this
	 * can be significantly more complicated that the developer template.
	 */
	public void testSaveAndLoadUser() {
		//developer template
		Template VMOSA=VMOSA_builder.generateTemplate();
		
		//user template
		Template userVMOSA=VMOSA_builder.generateTemplate();
		userVMOSA.setUserTemplateName("MyBusinessPlan");
		
		/* Add extra child branch and test serialization process */
		TemplateSection vision=userVMOSA.getRoot();
		Assert.assertNotNull(vision);
		TemplateSection mission=vision.getChild(0);
		Assert.assertNotNull(mission);
		TemplateSection objective=mission.getChild(0).deepCopy();	
		mission.addChild(objective);
		objective.setParent(mission);
		
		
		userVMOSA.save();
		Template userCopy=null;//Template.load("MyBusinessPlan.user");
		
		//verify loaded object and serialized object contain the same content
		Assert.assertEquals(userVMOSA, userCopy);
		
		//verify changing one does not change the other
		makeChange(userVMOSA,userCopy);
	}

	/**
	 * Verifies template object can return it's affiliated developer template
	 */
	public void testGetDeveloperTemplate() {
		fail("not yet implemented");
	}
	
	
/* this method is pointless because there is no point in creating a deep copy. plus can copy restrictions make a template
 * copy impossible. */
//	/**
//	 * Verifies that the deepCopy of a template is effective.
//	 */
//	public void testDeepCopy() {
//		Template VMOSA=VMOSA_builder.generateTemplate();
//		Template VMOSA_clone=null;//VMOSA.deepCopy();
//		
//		//verify objects have the same content
//		Assert.assertEquals(VMOSA, VMOSA_clone);
//		
//		//verify changing one does not change the other
//		makeChange(VMOSA,VMOSA_clone);
//		
//	}
	
	/**
	 * 
	 * @param base
	 * @param copy
	 * Helper method that verifies changing one template object does not impact the other.
	 * In other words it makes sure objects do not point to the same object.
	 */
	private void makeChange(Template base,Template copy) {
		base.getRoot().setCategory("Object");
		Assert.assertNotSame(base, base); //change one to copy
	}
	
}
