/**
 * 
 */
package software_masters.business_planner;

/**
 * This class builds a VMOSA developer template and saves it to XML.
 * 
 * 
 * @author Wesley Murray
 * @author Lee Kendall
 * 
 * @since 2019-02-24
 * 
 * 
 */
public class VMOSA_builder {

	/**
	 * @param args
	 */
	private static TemplateSection makeSection(String category, String name, boolean canCopy)
	{
		TemplateSection section = new TemplateSection(category, name, canCopy);
		Content textContent = new Text();
		section.addContent(textContent);		
		return section;
	}
	public static void main(String[] args) {
		System.out.println("Start");
		TemplateSection Vision = makeSection("Vision", "Vision", false);
		TemplateSection Mission = makeSection("Mission", "Mission", false);
		TemplateSection Objective = makeSection("Objectives", null, true);
		TemplateSection Strategy = makeSection("Strategies", null, true);
		TemplateSection ActionPlan = makeSection("Action Plans", null, true);
		
		Vision.addChild(Mission);
		Mission.addChild(Objective);
		Objective.addChild(Strategy);
		Strategy.addChild(ActionPlan);
		
		ActionPlan.setParent(Strategy);
		Strategy.setParent(Objective);
		Objective.setParent(Mission);
		Mission.setParent(Vision);
		
		Template VMOSA = new Template("VMOSA", null, Vision);
		VMOSA.save("VMOSA_dev.xml");
		System.out.println("end");
	}

}
