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
import javax.swing.SingleSelectionModel;
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
import data.ProcessedData;
import gui.GridBagPanel;
import gui.AC.MakeScenario;

public class TechCard extends GridBagPanel {
    private OntologyFunc ontologyFunc=ProcessedData.ontologyFunc;

    private ArrayList<TechData> techDataList=new ArrayList<TechData>();

    private class TechData {
        private String tacticName;
        private ArrayList<String> dataList=new ArrayList<String>();
        public TechData(String tacticName){
            this.tacticName=tacticName;
        }

        public String gettacticName(){
            return tacticName;
        }

        public ArrayList<String> getDataList(){
            return dataList;
        }
    }

    private ArrayList<String> findDataList(String tacticName){
        for (TechData td:techDataList){
            if(td.gettacticName().equals(tacticName)){
                return td.getDataList();
            }
        }

        return null;
    }

    public TechCard(MakeScenario upperDialog, int step) {
        setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#005BAC")),
                                "Tactics Step "+step+".")));

        
        JTable tacticTable = new JTable(new DefaultTableModel(new String[] { "Tactics" }, 0));
        tacticTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // nameTable.setTableHeader(null);

        JScrollPane tacticTabSc = new JScrollPane(tacticTable);

        tacticTabSc.setPreferredSize(new Dimension(220, 470));
        addGBLComponent(tacticTabSc, 0, 1, 1,1);

        GridBagPanel detailPane = new GridBagPanel();
        JLabel label2 = makeHeader("Selected Techniques");
        JTable techTable = makeContentTable();
        JScrollPane techSc = new JScrollPane(techTable);
        techSc.setPreferredSize(new Dimension(400, 420));
        
        JButton addButton = new JButton("Add ..");
        detailPane.addGBLComponent(addButton, 0, 2,0,0,"BOTH");
        
        detailPane.addGBLComponent(label2, 0, 0, 1, 1, "BOTH");
        detailPane.addGBLComponent(techSc, 0, 1, 1, 1, "BOTH");
        
        addGBLComponent(detailPane, 2, 1, 1,1, "BOTH");

        tacticTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                String tacticString = tacticTable.getValueAt(tacticTable.getSelectedRow(), 0).toString();
                ArrayList<String> techList=findDataList(tacticString);
                String[] techs = new String[techList.size()];
                for (int i = 0; i < techList.size(); i++) {
                techs[i] = techList.get(i) ;
                }
                ((DefaultTableModel) techTable.getModel()).setRowCount(0);
                for (String row : techs) {
                    ((DefaultTableModel) techTable.getModel()).addRow(new String[]{row});
                }

                for (ActionListener al : addButton.getActionListeners()) {
                    addButton.removeActionListener(al);
                }

                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TechSelection tacticSelection=new TechSelection(techTable, techList);
                        tacticSelection.setVisible(true);
                        revalidate();
                    }
                });

               

            }
        });

        addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                // TODO Auto-generated method stub
                System.out.println("tech added");
                for (Object o:ProcessedData.getStep(step).getTacticList()){
                    ((DefaultTableModel)(tacticTable.getModel())).addRow(new Object[]{o});
                    techDataList.add(new TechData((String)o));
                }
                tacticTable.addRowSelectionInterval(0, 0);
                if(step==4){
                    upperDialog.getNextButton().setText("Apply");
                    for(ActionListener al: upperDialog.getNextButton().getActionListeners()){
                        upperDialog.getNextButton().removeActionListener(al);
                    }
                    upperDialog.getNextButton().addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e) {
                            upperDialog.dispose();
                        };
                    });

                }
            }
            
            @Override
            public void ancestorRemoved(AncestorEvent event) {
                // TODO Auto-generated method stub
               
                System.out.println("tech Removed");
                for (TechData td:techDataList){
                    for (String tech:td.getDataList()){
                        ((DefaultTableModel)(upperDialog.getScenarioTable().getModel())).addRow(
                            new Object[]{
                                ""+step,
                                td.gettacticName(),
                                tech,
                                false
                            });
                    }
                }

            }
                        
            @Override
            public void ancestorMoved(AncestorEvent event) {
                // TODO Auto-generated method stub
                System.out.println("tech Moved");
                
            }

            
        });        

      

    }
    // public static void main(String[] args){
    //     TechCard techCard=new TechCard(null, 1);
    //     JDialog dialog = new JDialog();
    //     dialog.setSize(800,600);
    //     GridBagPanel panel=new GridBagPanel();
    //     dialog.add(panel);
    //     panel.addGBLComponent(techCard, 0, 0,2,1,0,0);
        
    //     JButton button = new JButton("Next");
    //     panel.addGBLComponent(button, 1,1,1,1,0,2,"NONE",GridBagConstraints.LINE_END);
    //     dialog.setVisible(true);
        
    // }

    private class TechSelection extends JDialog {
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

        public TechSelection(JTable table, ArrayList<String> techList) {
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

            ArrayList<String[]> tacticList = ontologyFunc.LoadAllTech();

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

            JScrollPane scrollPane = new JScrollPane(tacticTable);
            scrollPane.setPreferredSize(new Dimension(150, 180));
            ;

            confirmButton = new JButton("Confirm");
            confirmButton.setEnabled(false);

            tacticTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    tacticTable.setValueAt(true, tacticTable.getSelectedRow(), 0);
                    confirmButton.setEnabled(true);
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
                    techList.clear();
                    ArrayList<Object> selectedData=new ArrayList<Object>();
                    ((DefaultTableModel)(table.getModel())).setRowCount(0);
                    for(Object vector:((DefaultTableModel)(tacticTable.getModel())).getDataVector())
                    {
                        Object[] rowData=((Vector)vector).toArray();
                        if ((Boolean)rowData[0]==true){
                            ((DefaultTableModel)(table.getModel())).addRow(new Object[]{rowData[1]});
                            selectedData.add(rowData[1]);
                            techList.add((String)rowData[1]);
                        }
                    }
                    
                    dispose();
                }
            });
        }

    }
}
