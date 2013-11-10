package managers;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import ontology.PartOfSpeech;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import components.ComponentInfo;
import features.DBFeatureValues;

public class ComponentManager {
	
	private static final String COMPONENTS_DB_FILE_PATH = "Databases\\ComponentsDB.xml";
	
	//Singleton
	private static ComponentManager instance;
	
	public static ComponentManager getInstance(){
		if(instance == null)
			instance = new ComponentManager();
		return instance;
	}
	
	
	//Attributes and Methods
	private  ArrayList<ComponentInfo> componentsInfo;
	
	public ComponentManager(){
		loadComponentsInfo();
	}
	
	private void loadComponentsInfo(){
		File xmlFile = new File(COMPONENTS_DB_FILE_PATH);
		SAXBuilder builder = new SAXBuilder();
		componentsInfo = new ArrayList<ComponentInfo>();
		try{
			Document document = (Document) builder.build(xmlFile);
			Element rootElement = document.getRootElement();
			List<Element> componentElements = rootElement.getChildren("component");
			for(Element componentElement: componentElements){
				String name = componentElement.getAttributeValue("name");
				String description = componentElement.getAttributeValue("description");
				String type = componentElement.getAttributeValue("type");
				componentsInfo.add(new ComponentInfo(name, description, type));
			}
		}catch(Exception e){e.printStackTrace();}
	}
	
	public  ArrayList<ComponentInfo> getComponentsInfo(){
		return componentsInfo;
	}
	
	public ComponentInfo getComponentInfo(String componentName){
		for(ComponentInfo info: getComponentsInfo())
			if(info.getName().equalsIgnoreCase(componentName))
				return info.getCopy();
		
		return new ComponentInfo(componentName, "", "") ;
	}
	
	public boolean isLeaf(String componentName){
		ComponentInfo info = getComponentInfo(componentName);
		
		return info.isLeaf();
	}

	public String getDescription(String posName){
		ComponentInfo ci = getComponentInfo(posName);
		if(ci != null)
			return ci.getDescription();
		return "";
	}
	
	public ArrayList<PartOfSpeech> getPartsOfSpeech(){
		ArrayList<PartOfSpeech> partsOfSpeech = new ArrayList<PartOfSpeech>();
		
		for(ComponentInfo ci: componentsInfo)
			partsOfSpeech.add(new PartOfSpeech(ci.getName(), ci.getDescription()));
		
		return partsOfSpeech;
	}
}