package SoftwareMasters.BusinessPlanner;

import junit.framework.TestCase;

public class TemplateSectionTester extends TestCase {
	/**
	 * Verifies method can add children to TemplateSection
	 */
	public void testAddChild() {
		fail("not yet implemented");
	}
	
	/**
	 * Verifies the removeChild method will only remove child objects if and only if at least one child still 
	 * remains afterwards.
	 */
	public void testRemoveChild() {
		fail("not yet implemented");
	}
	
	/**
	 * Verifies content objects can be added to a templateSection
	 */
	public void testAddContent() {
		fail("not yet implemented");
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
