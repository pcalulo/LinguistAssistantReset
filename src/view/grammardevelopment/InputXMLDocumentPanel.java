package view.grammardevelopment;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;
import java.util.ArrayList;
import java.util.TooManyListenersException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import components.Component;
import components.InputXMLDocument;
import controller.listener.SelectComponentActionListener;
import controller.listener.editsemantics.DnDListener;
import controller.listener.editsemantics.InputXMLDocPanelDNDListener;

public class InputXMLDocumentPanel extends JPanel{
	
	private InputXMLDocument doc;
	private ArrayList<ComponentPanel> sentencePanels;

	private static final int VERTICAL_MARGIN = 20;
	
	private SelectComponentActionListener selectListener;
	
	public InputXMLDocumentPanel(InputXMLDocument doc, SelectComponentActionListener selectListener){
		this.doc = doc;
		DropTarget dt = new DropTarget();
		try {
			dt.addDropTargetListener(new InputXMLDocPanelDNDListener());
			this.setDropTarget(dt);
			
		} catch (TooManyListenersException e) {}
		this.selectListener = selectListener;
		

		setLayout(null);
		refreshDisplay();
		refreshTitle();
	}
	
	public void refreshTitle(){
		String titleString = "";
		if(!doc.getCategory().isEmpty())
			titleString += doc.getCategory()+" / ";
		setBorder(BorderFactory.createTitledBorder(titleString + doc.getName()));
	}
	
	public InputXMLDocumentPanel getCopy(){
		return new InputXMLDocumentPanel(doc, selectListener);
	}
	
	public void refreshDisplay(){
		createSentencePanels();
		adjustPositioning();
	}
	
	public void refreshPanelToolTips(){
		for(ComponentPanel sentence: sentencePanels)
			sentence.refreshLabelToolTip();
	}
	
	private void createSentencePanels(){
		sentencePanels = new ArrayList<ComponentPanel>();
		
		for(Component sentence: doc.getClauses())
			addSentencePanel(ComponentPanel.CreateInstance(sentence, selectListener));
	}
	
	private void addSentencePanel(ComponentPanel newPanel){
		sentencePanels.add(newPanel);
		add(newPanel);
	}
	
	public void addSentencePanelAt(int index, ComponentPanel newPanel){
		sentencePanels.add(index, newPanel);
		add(newPanel);
	}
	
	public void adjustPositioning(){
		int newDesiredHeight = 0;
		for(int i=0;i<sentencePanels.size();i++){
			ComponentPanel sentencePanel = sentencePanels.get(i);
			
			if(i > 0)
				sentencePanel.adjustPositioning(0, sentencePanels.get(i-1).getBottomY() + VERTICAL_MARGIN);
			else
				sentencePanel.adjustPositioning(0, VERTICAL_MARGIN*2);	
			
			newDesiredHeight += sentencePanel.getDesiredHeight() + VERTICAL_MARGIN;
		}
		
		//needed to let the parent scroll pane know that scrollbar is needed
		setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), newDesiredHeight + VERTICAL_MARGIN*4)); 
		revalidate();
		repaint();
	}
	
	public InputXMLDocument getXMLDocument(){
		return doc;
	}
	
	public String toSentence(){
		StringBuilder sb = new StringBuilder();
		for(ComponentPanel sentencePanel: sentencePanels){
			sb.append(sentencePanel.toSentence());
			sb.append(" ");
		}
		
		return sb.toString();
	}
	
	public void setGenerated(boolean isGenerateMode){
		for(ComponentPanel sentencePanel: sentencePanels)
			sentencePanel.setGenerateMode(isGenerateMode);
	}
	
	public SelectComponentActionListener getSelectListener(){
		return selectListener;
	}
	
	//For Drag and Drop
	public int determineInsertIndex(Point p){
		//first
		if(sentencePanels.size() == 0)
			return 0;
		
		if(p.y <= sentencePanels.get(0).getY())
			return 0;
		
		//middle
		for(int i = 0; i < sentencePanels.size(); i++){
			ComponentPanel child = sentencePanels.get(i);
			if(p.y > child.getY() && p.y < child.getBottomY()) //click isn't between any panels and it's "inside"  a panel
				return i;
			if( i+1 < sentencePanels.size() && p.y >= child.getBottomY() && p.y <= sentencePanels.get(i+1).getY())
				return i+1;
		}
				
		return sentencePanels.size(); //last
	}
	
	public void removeChild(ComponentPanel child){
		if(child != null){
			sentencePanels.remove(child);
			remove(child);
		}
		adjustPositioning();
	}
	
}
