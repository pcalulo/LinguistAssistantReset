package managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import module3.rules.Rule;
import module3.rules.RuleTree;
import module3.rules.UniMap;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import components.Component;
import components.InputXMLDocument;

public class RulesManager {
	
	public static RuleTree initializeRules(File xmlFile){
		SAXBuilder builder = new SAXBuilder();
		
		String rulesetName = "";
		
		try{
			Document document = (Document) builder.build(xmlFile);
			Element verseNode = document.getRootElement();
			
			//get name and category
			rulesetName = verseNode.getAttributeValue("name");
			
			//comments
			Element commentNode = verseNode.getChild("comments");
			String comments = "";
			if(commentNode != null)
				comments = commentNode.getText().trim();
			
			//get rules
			List<Element> nodes = (List<Element>) verseNode.getChildren("rule");
			ArrayList<Rule> rulesList = new ArrayList<Rule>();
			for(Element node: nodes)
				rulesList.add(Rule.createInstance(node));
			
			return new RuleTree(rulesetName, comments, rulesList);
		}catch(Exception e){e.printStackTrace();}
		
		return null;
	}
	
	public static void main(String args[]) {
		// get test components
		InputXMLDocument doc = SemanticsManager.readSemanticsDocumentFromFile(new File("inputxml/testfiles/TestComponent.xml"));
		ArrayList<Component> comp = doc.getClauses();
		// get test rules
		ArrayList<RuleTree> rts = new ArrayList<RuleTree>();
		for (int i = 1; i <= 4; i++) {
			rts.add(RulesManager.initializeRules(new File("inputxml/testfiles/Rule " + i + ".xml")));
			System.out.println("File " + i + " successfully loaded");
		}
		
		for (int i = 0; i < comp.size(); i++) {
			System.out.println("Component " + i + ":");
			for (RuleTree rt : rts) {
				for (Rule r : rt.getChildren()) {
					System.out.println("Processing Rule " + r.getName());
					if (r.apply(comp.get(i))) {
						System.out.println("Rule name - " + r.unify(comp.get(i), r.getInput()));
					}
				}
			}
			XMLManager.getInstance().writeToXML("inputxml/testfiles/Component" + i + "_generated.xml", comp.get(i).generateXMLElement());
		}
	}
}
