	package view.rules;

	import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import components.Component;
import components.InputXMLDocument;
import controller.CreateController;
import controller.GrammarDevController;
import controller.listener.grammardev.SelectComponentActionListener;

	import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.JSplitPane;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;

	import view.grammardevelopment.DisplayScreen;
import view.grammardevelopment.InputXMLDocumentPanel;
import view.grammardevelopment.editsemantics.ComponentPaletteScrollPane;
import view.grammardevelopment.editsemantics.CreationRightPanel;

	import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;

import module3.rules.Rule;
import net.miginfocom.swing.MigLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

	public class StructuralAdjustmentPanel extends JPanel {
		
		DisplayScreen inputScreen;
		DisplayScreen outputScreen;
		
		CreationRightPanel rightPanel;
		CreateController rightController;
		
		GrammarDevController grammarController;
		
		InputXMLDocumentPanel inputXMLPanel;
		InputXMLDocumentPanel outputXMLPanel;
		Rule rule;
		
//		public static void main (String args[])
//		{
//			try{
//				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//			}catch(Exception e){}
//			
//			Font oldLabelFont = UIManager.getFont("Label.font");
//			UIManager.put("Label.font", oldLabelFont.deriveFont(Font.PLAIN,(float)14));
//			
//			StructuralAdjustmentPanel sap = new StructuralAdjustmentPanel(new Rule());
//			JFrame frame = new JFrame();
//			frame.getContentPane().add(sap);
//			frame.setVisible(true);
//			Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
//			frame.setBounds(0, 0, (int) screenSize.getWidth(), (int)screenSize.getHeight());
//		}
		
//		take parameter Rule which has input and output blah
		public StructuralAdjustmentPanel(Rule rule) 
		{
			initializeUI();
			this.rule = rule;
			
			initializeComponentContent();
			addListenersToCompPalette();
			
			grammarController = new GrammarDevController(this);
			rightController = new CreateController(grammarController, rightPanel);
		}
		
		public void initializeComponentContent()
		{
			//add input components
			if (rule.getInput() != null)
			{
				inputXMLPanel.getXMLDocument().addClauseAt(0, rule.getInput());
				inputXMLPanel.refreshDisplay();
			}
			
			setSelectComponentListeners();
			//add output components
		}
		
		public void initializeUI()
		{
			setLayout(null);
			inputXMLPanel = new InputXMLDocumentPanel(new InputXMLDocument(null, null, "Input Structure", null, null));
			
			JPanel actionsPanel = new JPanel();
			actionsPanel.setBounds(815, 11, 549, 525);
			add(actionsPanel);
			actionsPanel.setLayout(null);
			rightPanel = new CreationRightPanel();
			rightPanel.setBounds(0, 0, 537, 511);
			actionsPanel.add(rightPanel);
			rightPanel.addDnDListenerForAllButtons(new MouseAdapter(){
	            public void mousePressed(MouseEvent e){
	                JButton button = (JButton)e.getSource();
	                TransferHandler handle = button.getTransferHandler();
	                handle.exportAsDrag(button, e, TransferHandler.COPY);
	            }
	        });
			
			outputXMLPanel = new InputXMLDocumentPanel(new InputXMLDocument(null, null, "Output Structure", null, null));
			
			
			
			JPanel buttonsPanel = new JPanel();
			buttonsPanel.setBounds(1209, 531, 133, 28);
			add(buttonsPanel);
			buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			JButton btnOk = new JButton("OK");
			btnOk.addActionListener(new OKButtonListener(this));
			buttonsPanel.add(btnOk);
			
			JButton btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(new CancelButtonListener(this));
			buttonsPanel.add(btnCancel);
			
			inputScreen = new DisplayScreen();
			inputScreen.setBounds(10, 11, 795, 257);
			add(inputScreen);
			
			outputScreen = new DisplayScreen();
			outputScreen.setBounds(10, 273, 795, 239);
			add(outputScreen);
			outputScreen.display(outputXMLPanel);
			inputScreen.display(inputXMLPanel);
		}
		//parameters: InputXMLDocumentPanel input, InputXMLDocumentPanel output
		public void setDisplay()
		{
			//call DisplayScreen.display()
		}
		
		public void setSelectComponentListeners()
		{
			inputXMLPanel.setSelectComponentPanelListener(new SelectComponentActionListener(this));
			outputXMLPanel.setSelectComponentPanelListener(new SelectComponentActionListener(this));
		}
		
		public void setComponent(Component component)
		{
			rightPanel.setComponent(component);
		}
		
		public void setSelectListener(SelectComponentActionListener listener)
		{
			grammarController.setSelectListener(listener);
		}
		
		private void addListenersToCompPalette() {
			rightPanel.addListenersForAllButtons(new MouseAdapter(){
	            public void mousePressed(MouseEvent e){
	                JButton button = (JButton)e.getSource();
	                TransferHandler handle = button.getTransferHandler();
	                handle.exportAsDrag(button, e, TransferHandler.COPY);
	            }
	        });
		}
		
		class OKButtonListener implements ActionListener
		{
			StructuralAdjustmentPanel panel;
			
			public OKButtonListener(StructuralAdjustmentPanel panel)
			{
				this.panel = panel;
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
				frame.dispose();
			}
			
		}
		
		class CancelButtonListener implements ActionListener
		{
			StructuralAdjustmentPanel panel;
			
			public CancelButtonListener(StructuralAdjustmentPanel panel)
			{
				this.panel = panel;
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
				frame.dispose();
			}
			
		}
	}