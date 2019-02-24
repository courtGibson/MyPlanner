package software_masters.business_planner;

/**
 * This class represents the data in a given business plan section.
 * 
 * 
 * @author Wesley Murray
 * @author Lee Kendall
 * 
 * @since 2019-02-23
 * 
 * 
 */

import java.util.ArrayList;

public class TemplateSection {
	
	private String category;
	private String name;
	
	private TemplateSection parent;
	private double childLim;
	
	private ArrayList<TemplateSection> children;
	private ArrayList<Content> contents;
	
	/**
	 * constructor for serialization
	 */
	public TemplateSection() {
		this(null,null,0);
	}
	
	/**
	 * @param category
	 * @param name
	 * @param childLim
	 */
	public TemplateSection(String category, String name, double childLim) {
		this.category = category;
		this.name = name;
		this.childLim = childLim;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the parent
	 */
	public TemplateSection getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(TemplateSection parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public ArrayList<TemplateSection> getChildren() {
		return children;
	}
	
	/**
	 * This method adds a child TemplateSection object
	 * 
	 * @params child a TemplateSection object
	 */
	public void addChild(TemplateSection child) {
		this.children.add(child);
	}

	/**
	 * @return the contents
	 */
	public ArrayList<Content> getContents() {
		return contents;
	}

	/**
	 * This method adds a content object to section
	 * 
	 * @params c1 a content object to be added to list of contents
	 */
	public void addContent(Content c1) {
		this.contents.add(c1);
	}
	
	/**
	 * @return a clone of the TemplateSection
	 */
	public TemplateSection deepCopy() {
		TemplateSection copy=new TemplateSection(this.category,this.name,this.childLim);
		for(Content c1: this.contents) {
			copy.addContent(c1.copy());
		}
		return null;
	}
	
	
}
