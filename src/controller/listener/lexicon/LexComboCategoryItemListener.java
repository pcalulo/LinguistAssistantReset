package controller.listener.lexicon;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.table.DefaultTableModel;

import view.lexicon.LexiconPanel;
import view.lexicon.LexiconTable;

import lexicon.Lexicon;
import lexicon.LexiconList;
import managers.LexiconManager;

public class LexComboCategoryItemListener implements ItemListener{

	LexiconList lexList;
	LexiconTable lexTable;
	LexiconPanel lexPanel;
	
	public LexComboCategoryItemListener(LexiconPanel lexPanel){
		this.lexTable = lexPanel.getTable();
		this.lexPanel = lexPanel;
	}
	
	public void itemStateChanged(ItemEvent arg0) {
		lexList = LexiconManager.getInstance().getLexiconList(lexPanel.getCodeFromSelectedPOS());
		lexPanel.setLexiconList(lexList);
		lexPanel.clearTable();
		DefaultTableModel model = (DefaultTableModel)lexTable.getModel();
		
		 model.setColumnIdentifiers(new String[] {"Stems","Gloss","Comments","Sample Sentences","Mapped Concept"}); // <-- column headings
		 for(Lexicon lex : lexList.getLexiconList()){ 
			 String[] data = {lex.getName(),lex.getGloss(),lex.getComments(),lex.getSampleSentence(),lex.getMappedConcept()};
			 model.addRow(data);
		 }
		lexTable.setModel(model);
	}
	

}
