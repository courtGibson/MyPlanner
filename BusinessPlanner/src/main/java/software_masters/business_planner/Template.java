package software_masters.business_planner;

/**
 * This class can be both a representation of a business plan or a business plan outline.
 * 
 * 
 * @author Wesley Murray
 * @author Lee Kendall
 * 
 * @since 2019-02-23
 * 
 * 
 */

public class Template {
	private String developerTemplateName;
	private String userTemplateName;
	private TemplateSection root;
	
	/**
	 * This constructor creates a default object for use by XML encoder
	 */
	public Template() {
		this(null,null,null);
	}
	
	/**
	 * @param developerTemplateName
	 * @param userTemplateName
	 * @param root
	 * 
	 * Constructor for creating Template object.
	 */
	public Template(String developerTemplateName, String userTemplateName, TemplateSection root) {
		this.developerTemplateName = developerTemplateName;
		this.userTemplateName = userTemplateName;
		this.root = root;
	}

	/**
	 * @return the name of the developer template
	 */
	public String getDeveloperTemplateName() {
		return developerTemplateName;
	}

	/**
	 * @param developerTemplateName the name of the developer template being used
	 */
	public void setDeveloperTemplateName(String developerTemplateName) {
		this.developerTemplateName = developerTemplateName;
	}

	/**
	 * @return the name of the business plan
	 */
	public String getUserTemplateName() {
		return userTemplateName;
	}

	/**
	 * @param userTemplateName the name of the business plan
	 */
	public void setUserTemplateName(String userTemplateName) {
		this.userTemplateName = userTemplateName;
	}

	/**
	 * @return the start section for the template
	 */
	public TemplateSection getRoot() {
		return root;
	}

	/**
	 * @param root the start TemplateSection for the template 
	 */
	public void setRoot(TemplateSection root) {
		this.root = root;
	}
	
	/**
	 * This method returns a deepCopy of the template object
	 * 
	 * @return a copy of the template object
	 */
	public Template deepCopy() {
		Template copy = new Template(this.developerTemplateName, this.userTemplateName, this.root.deepCopy());
		return copy;
		int 2;
	}
	
	/**
	 * This method serializes the object.
	 */
	public void save() {
		
	}
	
	/**
	 * This method is used to load a design based on a provided filepath
	 * 
	 * @param filepath the filepath to the xml file representation of the object 
	 * 
	 * @return a template object from memory
	 */
	public static Template load(String filepath) {
		return null;
	}

}
