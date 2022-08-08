package gui.AC.Card;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel; // 패널크기 고정으로 지정하자
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import api.OntologyFunc;
import data.Asset;
import data.ProcessedData;
import gui.GridBagPanel;
import gui.AC.MakeScenario;

public class TacticCard extends GridBagPanel {
    private JPanel cardPanel;
    private OntologyFunc ontologyFunc=ProcessedData.ontologyFunc;

    public TacticCard(MakeScenario upperDialog) {

        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#005BAC")),
                "Tactics"));

        

        GridBagPanel labelPanel = new GridBagPanel();

        JLabel infoLabel = new JLabel();
        infoLabel.setText("Select tatics.");

        JLabel blankLabel = new JLabel();
        blankLabel.setPreferredSize(new Dimension(10, 10));

        labelPanel.addGBLComponent(infoLabel, 0, 0);
        labelPanel.addGBLComponent(blankLabel, 0, 1);

        addGBLComponent(labelPanel, 0, 0, 5, 1, 0, 0, "NONE", GridBagConstraints.LINE_START);

        JTable step1Table = new JTable(new DefaultTableModel(new String[] { "Step 1" }, 0));
        JScrollPane step1TableScPane = new JScrollPane(step1Table);
        step1TableScPane.setPreferredSize(new Dimension(190, 300));

        JTable step2Table = new JTable(new DefaultTableModel(new String[] { "Step 2" }, 0));
        JScrollPane step2TableScPane = new JScrollPane(step2Table);
        step2TableScPane.setPreferredSize(new Dimension(190, 300));

        JTable step3Table = new JTable(new DefaultTableModel(new String[] { "Step 3" }, 0));
        JScrollPane step3TableScPane = new JScrollPane(step3Table);
        step3TableScPane.setPreferredSize(new Dimension(190, 300));

        JTable step4Table = new JTable(new DefaultTableModel(new String[] { "Step 4" }, 0));
        JScrollPane step4TableScPane = new JScrollPane(step4Table);
        step4TableScPane.setPreferredSize(new Dimension(190, 300));

        addGBLComponent(step1TableScPane, 0, 2, 2, 1);
        addGBLComponent(step2TableScPane, 2, 2);
        addGBLComponent(step3TableScPane, 3, 2);
        addGBLComponent(step4TableScPane, 4, 2);

        JButton button1 = new JButton("Add");
        JButton button2 = new JButton("Add");
        JButton button3 = new JButton("Add");
        JButton button4 = new JButton("Add");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TacticSelection tacticSelection=new TacticSelection(step1Table);
                tacticSelection.setVisible(true);
                revalidate();
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TacticSelection tacticSelection=new TacticSelection(step2Table);
                tacticSelection.setVisible(true);
                revalidate();
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TacticSelection tacticSelection=new TacticSelection(step3Table);
                tacticSelection.setVisible(true);
                revalidate();
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TacticSelection tacticSelection=new TacticSelection(step4Table);
                tacticSelection.setVisible(true);
                revalidate();
            }
        });

        // JButton applyButton = new JButton("Apply");
        // addGBLComponent(applyButton, 4, 4,0,0,"NONE",GridBagConstraints.LINE_END);

        // applyButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         cardPanel.add(makeTech(step1Table,1));
        //         cardPanel.add(makeTech(step2Table,2));
        //         cardPanel.add(makeTech(step3Table,3));
        //         cardPanel.add(makeTech(step4Table,4));
        //         cardPanel.add(new CompletePanel());
        //         nextButton.setEnabled(true);
        //         applyButton.setEnabled(false);

        //     }

        //     private Tech makeTech(JTable table, int step){
        //         Vector tableVector=((DefaultTableModel)(table.getModel())).getDataVector();
        //         ArrayList<Object> data=new ArrayList<Object>();
        //         for(Object row:tableVector){
        //             data.add(((String)((Vector)row).toArray()[0]));
        //         }

        //         return new Tech(data.toArray(),step);
        //     }
        // });

        addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                // TODO Auto-generated method stub
                System.out.println("tacticcard added");
            }
            
            @Override
            public void ancestorRemoved(AncestorEvent event) {
                // TODO Auto-generated method stub
                for(Object[] obj:getDataFromTable(step1Table)){
                    ProcessedData.getStep(1).getTacticList().add((String)obj[0]);
                }
                for(Object[] obj:getDataFromTable(step2Table)){
                    ProcessedData.getStep(2).getTacticList().add((String)obj[0]);
                }
                for(Object[] obj:getDataFromTable(step3Table)){
                    ProcessedData.getStep(3).getTacticList().add((String)obj[0]);
                }
                for(Object[] obj:getDataFromTable(step4Table)){
                    ProcessedData.getStep(4).getTacticList().add((String)obj[0]);
                }
                System.out.println("tacticcard Removed");

            }
                        
            @Override
            public void ancestorMoved(AncestorEvent event) {
                // TODO Auto-generated method stub
                System.out.println("tacticcard Moved");
                
            }

            
        });        
        

        addGBLComponent(button1, 0, 3, 2, 1, "BOTH");
        addGBLComponent(button2, 2, 3, 1, 1, "BOTH");
        addGBLComponent(button3, 3, 3, 1, 1, "BOTH");
        addGBLComponent(button4, 4, 3, 1, 1, "BOTH");

        // changeButtonListener(this, new Tech());
    }

    private class TacticSelection extends JDialog {
        private JTable tacticTable = new JTable(new DefaultTableModel(new String[] { "V", "Tactics" }, 0){
            public java.lang.Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return Boolean.class;
                    default:
                        return String.class;
                }
            };
        });
        private JPanel searchLayout;

        private JTextField searchField;
        private JLabel searchLabel;

        private JButton confirmButton;

        public TacticSelection(JTable table) {
            setModal(true);
            setSize(400, 300);
            setResizable(false);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            tacticTable.getColumnModel().getColumn(0).setPreferredWidth(10);
            tacticTable.getColumnModel().getColumn(1).setPreferredWidth(350);

            searchField = new JTextField();
            searchField.setColumns(50);

            searchLabel = new JLabel("Search: ");

            searchLayout = new JPanel();
            searchLayout.setLayout(new BorderLayout());
            searchLayout.add(searchLabel, BorderLayout.WEST);
            searchLayout.add(searchField, BorderLayout.CENTER);

            add(searchLayout, BorderLayout.NORTH);

            ArrayList<String[]> tacticList = ontologyFunc.LoadAllTactics();

            Vector tableVector=((DefaultTableModel)(table.getModel())).getDataVector();
            Object[] rowData=tableVector.toArray();
            boolean b=false;
            for (String[] tactic : tacticList) {
                if (rowData.length!=0){
                    for(Object row:tableVector){
                        if(((String)((Vector)row).toArray()[0]).equals(tactic[0]))
                            b=true;
                    }
                }
                if(b){
                    ((DefaultTableModel) (tacticTable.getModel())).addRow(new Object[] { true, tactic[0] });
                }
                else{
                    ((DefaultTableModel) (tacticTable.getModel())).addRow(new Object[] { false, tactic[0] });
                }
                b=false;
            }

            tacticTable.setDefaultEditor(Object.class, null);
            tacticTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tacticTable.getModel());
            tacticTable.setRowSorter(sorter);

            searchField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchField.getText()));
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchField.getText()));
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchField.getText()));
                }

            });

            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JScrollPane scrollPane = new JScrollPane(tacticTable);
            scrollPane.setPreferredSize(new Dimension(150, 180));
            ;

            confirmButton = new JButton("Confirm");
            confirmButton.setEnabled(false);

            tacticTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (tacticTable.getSelectedRow() != -1)
                        confirmButton.setEnabled(true);
                    else
                        confirmButton.setEnabled(false);
                }
            });

            JPanel tempPanel = new JPanel();

            tempPanel.setLayout(new BorderLayout());
            // tempPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            tempPanel.add(scrollPane, BorderLayout.NORTH);
            tempPanel.add(confirmButton, BorderLayout.CENTER);
            add(tempPanel, BorderLayout.CENTER);

            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    ArrayList<Object> selectedData=new ArrayList<Object>();
                    ((DefaultTableModel)(table.getModel())).setRowCount(0);
                    for(Object vector:((DefaultTableModel)(tacticTable.getModel())).getDataVector())
                    {
                        Object[] rowData=((Vector)vector).toArray();
                        if ((Boolean)rowData[0]==true){
                            ((DefaultTableModel)(table.getModel())).addRow(new Object[]{rowData[1]});
                            selectedData.add(rowData[1]);
                        }
                    }
                    
                    dispose();
                }
            });
        }

    }
}
