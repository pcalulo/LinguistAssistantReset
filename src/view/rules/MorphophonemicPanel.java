package view.rules;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class MorphophonemicPanel extends JPanel{
	
	JComboBox box,prefixChoice,stemChoice,suffixChoice,infixPrefixChoice,infixSuffixChoice;
	String type;
	private JTextField tf;
	private JButton buttontype; 
	private JPanel panel, prefixPane, suffixPane, stemPane,infixPrefixPane, infixSuffixPane;
	private JCheckBox cb_reduplication,cb_epenthesis,stem_reduplication;
	int checker;
	private JTextField textField;
	
	public MorphophonemicPanel()
	{	
		setForeground(Color.LIGHT_GRAY);
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		MorphemeTypes();
	}
	
	public void MorphemeTypes()
	{
		JPanel temp = new JPanel();
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(1,4,10,4));
		add(panel, BorderLayout.LINE_START);
		
		box = new JComboBox(new Type[]{
			new Type("Prefix","Prefixes"),
			new Type("Infix as Prefix","Infixes"),
			new Type("Infix as Suffix","Infixes"),
			new Type("Suffix","Suffixes")
			});
		panel.add(box);
		box.setRenderer(new ItemCellRenderer());
		
		box.setSelectedIndex(0);
		buttontype = new JButton("Included Prefixes");
		panel.add(buttontype);
		
		tf = new JTextField();
		panel.add(tf);
		tf.setColumns(10);
		
		PrefixPane();
		InfixasPrefix();
		SuffixPane();
		InfixasSuffix();
		StemPane();
		checker = 0;
		
		box.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
            	if(e.getStateChange() == ItemEvent.SELECTED)
            		{
            		Type type = (Type)box.getSelectedItem();
                    buttontype.setText("Included "+type.getValue());
                    
	                    if(type.getText().equals("Prefix"))
	                    {
	                    	checker = 0;
	                    }
	                    if(type.getText().equals("Infix as Prefix"))
	                    {
	                    	checker = 1;
	                    }
	                    if(type.getText().equals("Suffix"))
	                    {
	                    	checker = 2;
	                    }
	                    if(type.getText().equals("Infix as Suffix"))
	                    {
	                    	checker = 3;
	                    }
            		}
            	if(checker == 0)
        		{
            		prefixPane.setVisible(true);
            		infixPrefixPane.setVisible(false);
            		suffixPane.setVisible(false);
            		infixSuffixPane.setVisible(false);
        		}
            	else if(checker == 1)
            	{
            		prefixPane.setVisible(false);
            		infixPrefixPane.setVisible(true);
            		suffixPane.setVisible(false);
            		infixSuffixPane.setVisible(false);
            	}
            	else if(checker == 2)
            	{
            		prefixPane.setVisible(false);
            		infixPrefixPane.setVisible(false);
            		suffixPane.setVisible(true);
            		infixSuffixPane.setVisible(false);
            	}
            	else if(checker == 3)
            	{
            		prefixPane.setVisible(false);
            		infixPrefixPane.setVisible(false);
            		suffixPane.setVisible(false);
            		infixSuffixPane.setVisible(true);
            	}
            }
        });
	}
	
	public void PrefixPane()
	{
		prefixPane = new JPanel();
    	GridBagLayout gridbag= new GridBagLayout();
		gridbag.columnWeights = new double[]{1.0};
		gridbag.rowHeights = new int[]{50};
		gridbag.columnWidths = new int[]{100};
	    prefixPane.setLayout(gridbag);
		prefixPane.setBorder(new TitledBorder("Prefix"));
		
		prefixChoice = new JComboBox(new Type[]{
				new Type("Prefix doesn't change","No Change"),
				new Type("End of Prefix changes","End Changes"),
				new Type("Entire Prefix changes","Entire Prefix")
				});
		prefixChoice.setMaximumRowCount(4);
		GridBagConstraints gbc_jcb = new GridBagConstraints();
		gbc_jcb.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc_jcb.gridx = 0;
		gbc_jcb.gridy = 0;
		prefixPane.add(prefixChoice, gbc_jcb);
		
		prefixChoice.setRenderer(new ItemCellRenderer());
		prefixChoice.setSelectedIndex(0);
		
		cb_reduplication = new JCheckBox("Reduplication");
		GridBagConstraints gbc_cb_reduplication = new GridBagConstraints();
		gbc_cb_reduplication.anchor = GridBagConstraints.FIRST_LINE_END;
		gbc_cb_reduplication.gridx = 1;
		gbc_cb_reduplication.gridy = 0;
		prefixPane.add(cb_reduplication, gbc_cb_reduplication);
		
		cb_epenthesis = new JCheckBox("Epenthesis");
		GridBagConstraints gbc_cb_epenthesis = new GridBagConstraints();
		gbc_cb_epenthesis.anchor = GridBagConstraints.SOUTHWEST;
		gbc_cb_epenthesis.gridx = 1;
		gbc_cb_epenthesis.gridy = 0;
		prefixPane.add(cb_epenthesis, gbc_cb_epenthesis);

    	cb_reduplication.setEnabled(false);
    	cb_epenthesis.setEnabled(false);
    	
		prefixChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Type opt = (Type)prefixChoice.getSelectedItem();
                
                if(opt.getValue().equals("End Changes"))
                {
                	cb_reduplication.setEnabled(true);
                	cb_epenthesis.setEnabled(true);
                }
                if(opt.getValue().equals("No Change"))
                {
                	cb_reduplication.setEnabled(false);
                	cb_epenthesis.setEnabled(false);

            		textField = new JTextField();
            		add(textField);
            		textField.setColumns(10);
                }
                if(opt.getValue().equals("Entire Prefix"))
                {
                	cb_reduplication.setEnabled(false);
                	cb_epenthesis.setEnabled(false);
                }
            }
        });
		add(prefixPane);
	
	}
	public void InfixasPrefix()
	{
		infixPrefixPane = new JPanel();
		infixPrefixPane.setVisible(false);
    	GridBagLayout gridbag= new GridBagLayout();
		gridbag.columnWeights = new double[]{1.0};
		gridbag.rowHeights = new int[]{50};
		gridbag.columnWidths = new int[]{100};
		infixPrefixPane.setLayout(gridbag);
		infixPrefixPane.setBorder(new TitledBorder("Prefix"));
		
		infixPrefixChoice = new JComboBox(new Type[]{
				new Type("Infix doesn't change","No Change"),
				new Type("End of Infix changes","End Infix Changes"),
				new Type("Entire Infix changes","Entire Infix")
				});
		infixPrefixChoice.setMaximumRowCount(4);
		GridBagConstraints gbc_jcb = new GridBagConstraints();
		gbc_jcb.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc_jcb.gridx = 0;
		gbc_jcb.gridy = 0;
		infixPrefixPane.add(infixPrefixChoice, gbc_jcb);
		
		infixPrefixChoice.setRenderer(new ItemCellRenderer());
		infixPrefixChoice.setSelectedIndex(0);
		
		cb_reduplication = new JCheckBox("Reduplication");
		GridBagConstraints gbc_cb_reduplication = new GridBagConstraints();
		gbc_cb_reduplication.anchor = GridBagConstraints.FIRST_LINE_END;
		gbc_cb_reduplication.gridx = 1;
		gbc_cb_reduplication.gridy = 0;
		infixPrefixPane.add(cb_reduplication, gbc_cb_reduplication);
		
		cb_epenthesis = new JCheckBox("Epenthesis");
		GridBagConstraints gbc_cb_epenthesis = new GridBagConstraints();
		gbc_cb_epenthesis.anchor = GridBagConstraints.SOUTHWEST;
		gbc_cb_epenthesis.gridx = 1;
		gbc_cb_epenthesis.gridy = 0;
		infixPrefixPane.add(cb_epenthesis, gbc_cb_epenthesis);

    	cb_reduplication.setEnabled(false);
    	cb_epenthesis.setEnabled(false);
    	
    	infixPrefixChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Type opt = (Type)infixPrefixChoice.getSelectedItem();
                
                if(opt.getValue().equals("End Infix Changes"))
                {
                	cb_reduplication.setEnabled(true);
                	cb_epenthesis.setEnabled(true);
                }
                if(opt.getValue().equals("No Change") || (opt.getValue().equals("Entire Infix")))
                {
                	cb_reduplication.setEnabled(false);
                	cb_epenthesis.setEnabled(false);
                }
            }
        });
		add(infixPrefixPane);
	}
	public void InfixasSuffix()
	{
		infixSuffixPane = new JPanel();
		infixSuffixPane.setVisible(false);
		GridBagLayout gridbag= new GridBagLayout();
		gridbag.columnWeights = new double[]{1.0};
		gridbag.rowHeights = new int[]{50};
		gridbag.columnWidths = new int[]{100};
		infixSuffixPane.setLayout(gridbag);
		infixSuffixPane.setBorder(new TitledBorder("Suffix"));
		
		infixSuffixChoice = new JComboBox(new Type[]{
				new Type("Infix doesn't change","No Change"),
				new Type("Beginning of Infix changes","Begin Infix Changes"),
				new Type("Entire Infix changes","Entire Infix")
				});
		infixSuffixChoice.setMaximumRowCount(4);
		GridBagConstraints gbc_jcb = new GridBagConstraints();
		gbc_jcb.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc_jcb.gridx = 0;
		gbc_jcb.gridy = 0;
		infixSuffixPane.add(infixSuffixChoice, gbc_jcb);
		
		infixSuffixChoice.setRenderer(new ItemCellRenderer());
		infixSuffixChoice.setSelectedIndex(0);
		
		cb_reduplication = new JCheckBox("Reduplication");
		cb_reduplication.setEnabled(false);
		GridBagConstraints gbc_cb_reduplication = new GridBagConstraints();
		gbc_cb_reduplication.anchor = GridBagConstraints.FIRST_LINE_END;
		gbc_cb_reduplication.gridx = 1;
		gbc_cb_reduplication.gridy = 0;
		infixSuffixPane.add(cb_reduplication, gbc_cb_reduplication);
		
		cb_epenthesis = new JCheckBox("Epenthesis");
		cb_epenthesis.setEnabled(false);
		GridBagConstraints gbc_cb_epenthesis = new GridBagConstraints();
		gbc_cb_epenthesis.anchor = GridBagConstraints.SOUTHWEST;
		gbc_cb_epenthesis.gridx = 1;
		gbc_cb_epenthesis.gridy = 0;
		infixSuffixPane.add(cb_epenthesis, gbc_cb_epenthesis);
		
		infixSuffixChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Type opt = (Type)infixSuffixChoice.getSelectedItem();
                
                if(opt.getValue().equals("Begin Infix Changes"))
                {
                	cb_reduplication.setEnabled(true);
                	cb_epenthesis.setEnabled(true);
                }
                if(opt.getValue().equals("No Change") || (opt.getValue().equals("Entire Infix")))
                {
                	cb_reduplication.setEnabled(false);
                	cb_epenthesis.setEnabled(false);
                }
            }
        });
		
		add(infixSuffixPane);
	}
	public void SuffixPane()
	{
		suffixPane = new JPanel();
		suffixPane.setVisible(false);
		GridBagLayout gridbag= new GridBagLayout();
		gridbag.columnWeights = new double[]{1.0};
		gridbag.rowHeights = new int[]{50};
		gridbag.columnWidths = new int[]{100};
		suffixPane.setLayout(gridbag);
		suffixPane.setBorder(new TitledBorder("Suffix"));
		
		suffixChoice = new JComboBox(new Type[]{
				new Type("Suffix doesn't change","No Change"),
				new Type("Beginning of Suffix changes","Begin Changes"),
				new Type("Entire Suffix changes","Entire Infix")
				});
		suffixChoice.setMaximumRowCount(4);
		GridBagConstraints gbc_jcb = new GridBagConstraints();
		gbc_jcb.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc_jcb.gridx = 0;
		gbc_jcb.gridy = 0;
		suffixPane.add(suffixChoice, gbc_jcb);
		
		suffixChoice.setRenderer(new ItemCellRenderer());
		suffixChoice.setSelectedIndex(0);
		
		cb_reduplication = new JCheckBox("Reduplication");
		cb_reduplication.setEnabled(false);
		GridBagConstraints gbc_cb_reduplication = new GridBagConstraints();
		gbc_cb_reduplication.anchor = GridBagConstraints.FIRST_LINE_END;
		gbc_cb_reduplication.gridx = 1;
		gbc_cb_reduplication.gridy = 0;
		suffixPane.add(cb_reduplication, gbc_cb_reduplication);
		
		cb_epenthesis = new JCheckBox("Epenthesis");
		cb_epenthesis.setEnabled(false);
		GridBagConstraints gbc_cb_epenthesis = new GridBagConstraints();
		gbc_cb_epenthesis.anchor = GridBagConstraints.SOUTHWEST;
		gbc_cb_epenthesis.gridx = 1;
		gbc_cb_epenthesis.gridy = 0;
		suffixPane.add(cb_epenthesis, gbc_cb_epenthesis);
		
		suffixChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Type opt = (Type)suffixChoice.getSelectedItem();
                
                if(opt.getValue().equals("Begin Infix Changes"))
                {
                	cb_reduplication.setEnabled(true);
                	cb_epenthesis.setEnabled(true);
                	
                }
                if(opt.getValue().equals("No Change") || (opt.getValue().equals("Entire Infix")))
                {
                	cb_reduplication.setEnabled(false);
                	cb_epenthesis.setEnabled(false);
                }
            }
        });
		
		add(suffixPane);
	}
	
	public void StemPane()
	{
		stemPane = new JPanel();
		
		GridBagLayout gridbag= new GridBagLayout();
		gridbag.columnWeights = new double[]{0.1};
		gridbag.rowHeights = new int[]{50};
		gridbag.columnWidths = new int[]{100};
		stemPane.setLayout(gridbag);
		stemPane.setBorder(new TitledBorder("Stem"));
		
		stemChoice = new JComboBox(new Type[]{
				new Type("Stem doesn't change","No Change"),
				new Type("Stem changes","Changes"),
				});
		stemChoice.setMaximumRowCount(4);
		GridBagConstraints gbc_jcb = new GridBagConstraints();
		gbc_jcb.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc_jcb.gridx = 0;
		gbc_jcb.gridy = 0;
		stemPane.add(stemChoice, gbc_jcb);
		stemChoice.setRenderer(new ItemCellRenderer());
		stemChoice.setSelectedIndex(0);
		
		stem_reduplication = new JCheckBox("Reduplication");
		stem_reduplication.setEnabled(false);
		GridBagConstraints gbc_cb_reduplication = new GridBagConstraints();
		gbc_cb_reduplication.anchor = GridBagConstraints.FIRST_LINE_END;
		gbc_cb_reduplication.gridx = 1;
		gbc_cb_reduplication.gridy = 0;
		stemPane.add(stem_reduplication, gbc_cb_reduplication);
		
		stemChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Type opt2 = (Type)stemChoice.getSelectedItem();
                
                if(opt2.getValue().equals("Changes"))
                {
                	stem_reduplication.setEnabled(true);
                }
                if(opt2.getValue().equals("No Change"))
                {
                	stem_reduplication.setEnabled(false);
                }
                
            }
        });
		
		add(stemPane);
		
	}
	
	 public class Type {
	        private String value;
	        private String text;

	        public Type(String text, String value) {
	            this.text = text;
	            this.value = value;
	        }

	        public String getText() {
	            return text;
	        }

	        public String getValue() {
	            return value;
	        }
	    }
	 
	 	public class ItemCellRenderer extends DefaultListCellRenderer {
	        @Override
	        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean 

isSelected, boolean cellHasFocus) {
	            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); 
	            if (value instanceof Type) {
	                setText(((Type)value).getText());
	            }
	            return this;
	        }

	    }
}