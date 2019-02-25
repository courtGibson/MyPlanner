package software_masters.business_planner;

/**
 * This class handles user interaction with this application.
 * 
 * 
 * @author Wesley Murray
 * @author Lee Kendall
 * 
 * @since 2019-02-23
 * 
 * 
 */


class BusinessPlanMaker {

	private Template userTemplate;
	private Template developerTemplate;
	private TemplateSection current;
	
	/**
	 * Sets developerTemplate to the chosen XML file, clones developerTemplate to make a userTemplate object
	 * @param filepath of chosen developerTemplate XML file
	 */
	public void chooseTemplate(String filepath)
	{
		developerTemplate = Template.load(filepath);
		userTemplate = developerTemplate.deepCopy();
	}
	
	/**
	 * Allows the user to load a previously edited user template from memory.
	 * @param filepath
	 */
	public void loadUserTemplate(String filepath)
	{
		userTemplate = Template.load(filepath);
		developerTemplate = Template.load(userTemplate.getDeveloperTemplateName()+".dev");
	}
	/**
	 * Adds a copy of the type of TemplateSection currently accessed by the user. Only works if the section is allowed
	 * to be copied.
	 */
	public boolean addSection()
	{
		if (current.canCopy())
		{
			String currCategory = current.getCategory();
			
			TemplateSection sectionToCopy = this.findTemplateSection(developerTemplate.getRoot(), currCategory);
			TemplateSection copy = sectionToCopy.deepCopy();
			current.getParent().addChild(copy);
			copy.setParent(current);
			return true;
		}
		return false;
		
	}
	/**
	 * Removes the currently access section if allowed, sets current section to parent of removed section.
	 * @return boolean indicating if section actually removed
	 */
	public boolean removeSection()
	{
		if (current.canRemove())
		{
			current.getParent().removeChild(current);
			current = current.getParent();
			return true;
		}
		return false;
	}
	
	/**
	 * Recursively finds a templateSection with a given category.
	 * @param section
	 * @param targetCategory
	 * @return TemplateSection of developerTemplate which  user wants to add
	 */
	public TemplateSection findTemplateSection(TemplateSection section, String targetCategory)
	{
		/*Base case - category found */
		if(section.getCategory() == targetCategory)
		{
			return section;
		}
		/*Recursive case - category not found */
		else
		{
			for(TemplateSection t: section.getChildren())
			{
				section = findTemplateSection(t, targetCategory);
				if(section != null)
				{
					return section;
				}
			}
		}
		return null;
	}
	
	/**
	 * Allows user to navigate to parent of the currently accessed template section
	 */
	public void accessParent()
	{
		current = current.getParent();
	}
	
	/**
	 * Allows user to navigate to the child of the currently accessed template section, given its position in the children array
	 * @param index
	 */
	public void accessChild(int index)
	{
		current = current.getChildren().get(index);
	}
	
	public static void main(String[] args) {
		
	}
	
}
