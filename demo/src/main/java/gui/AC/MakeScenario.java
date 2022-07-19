package gui.AC;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;              // 패널크기 고정으로 지정하자

import gui.GridBagPanel;
import api.OntologyFunc;
import data.ProcessedData;

public class MakeScenario extends JDialog {
    private GridBagPanel dialogPane=new GridBagPanel();
    private OntologyFunc ontologyFunc = ProcessedData.ontologyFunc;
    private JButton nextButton = new JButton("Next");

    public MakeScenario() {
        setTitle("In progress...");
        File imageFile = new File(this.getClass().getResource("").getPath(), "../../../../icon.png");
        try {
            setIconImage(ImageIO.read(imageFile));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        setModal(true);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        
        JComponent nextPane=new Org();
        changePane(nextPane, (Class<JComponent>)nextPane.getClass());
        add(dialogPane);

        dialogPane.addGBLComponent(nextButton,1,1, 1, 1,0,2,"NONE",GridBagConstraints.LINE_END);

        setVisible(true);
    }

    private void changePane(JComponent cur,Class<JComponent> nextClass) {
        // if(dialogPane.getComponents().length!=0)
        //     dialogPane.remove(0);
        if(dialogPane.getComponents().length!=0)
            dialogPane.remove(cur);
        System.out.println(cur.toString());
        try {
            dialogPane.addGBLComponent(nextClass.getDeclaredConstructor().newInstance(),0,0,2,1,0,8);
            dialogPane.revalidate();
            dialogPane.repaint();
            
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    private void changeButtonListener(JComponent cur,Class<JComponent> nextClass){
        for (ActionListener al:nextButton.getActionListeners()){
            nextButton.removeActionListener(al);
        }
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    changePane(cur,nextClass);
                    
                } catch (Exception exc) {
                    //TODO: handle exception
                }
                
            }
        });
    }

    private class Org extends GridBagPanel {


        public Org() {
            setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#005BAC")),"Organization"));

            GridBagPanel labelPanel = new GridBagPanel();
            
            JLabel infoLabel = new JLabel();
            infoLabel.setText("Select an organizaion to assess security risks for an attack.");

            JLabel blankLabel = new JLabel();
            blankLabel.setPreferredSize(new Dimension(10,10));

            labelPanel.addGBLComponent(infoLabel, 0, 0);
            labelPanel.addGBLComponent(blankLabel, 0, 1);
            
            addGBLComponent(labelPanel, 0, 0,5,1,0,0,"NONE",GridBagConstraints.LINE_START);
            
            
            
            JLabel orgLabel = new JLabel();
            orgLabel.setText("Organizaion: ");
            addGBLComponent(orgLabel, 0, 1);
            
            JComboBox<Object> orgComboBox = new JComboBox<>(); 
            for (String org: ontologyFunc.LoadAllOrg()){
                orgComboBox.addItem(org);
                System.out.println(org);
            }
            orgComboBox.setSelectedIndex(-1);
            orgComboBox.setPreferredSize(new Dimension(250, 22));
            
            addGBLComponent(orgComboBox, 1, 1,4,1,0,0,"NONE",GridBagConstraints.LINE_START);


            JTable bpTable=new JTable(new DefaultTableModel(new String[]{"Business Process"},0));
            JScrollPane bpTableScPane= new JScrollPane(bpTable);
            bpTableScPane.setPreferredSize(new Dimension(130,300));

            JTable humanTable=new JTable(new DefaultTableModel(new String[]{"Role","Person"},0));
            JScrollPane humanTableScPane= new JScrollPane(humanTable);
            humanTableScPane.setPreferredSize(new Dimension(210,300));

            JTable itsTable=new JTable(new DefaultTableModel(new String[]{"Type","Content"},0));
            JScrollPane itsTableScPane= new JScrollPane(itsTable);
            itsTableScPane.setPreferredSize(new Dimension(210,300));

            JTable peTable=new JTable(new DefaultTableModel(new String[]{"Type","Content"},0));
            JScrollPane peTableScPane= new JScrollPane(peTable);
            peTableScPane.setPreferredSize(new Dimension(210,300));

            
            addGBLComponent(bpTableScPane, 0, 2,2,1);
            addGBLComponent(humanTableScPane, 2, 2);
            addGBLComponent(itsTableScPane, 3, 2);
            addGBLComponent(peTableScPane, 4, 2);

            changeButtonListener(this, (Class<JComponent>)((JComponent)new Tactic()).getClass());
        }

    }

    private class Tactic extends GridBagPanel {
        public Tactic() {
            
            setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#005BAC")),"Tactics"));

            GridBagPanel labelPanel = new GridBagPanel();
            
            JLabel infoLabel = new JLabel();
            infoLabel.setText("Select tatics.");

            JLabel blankLabel = new JLabel();
            blankLabel.setPreferredSize(new Dimension(10,10));

            labelPanel.addGBLComponent(infoLabel, 0, 0);
            labelPanel.addGBLComponent(blankLabel, 0, 1);
            
            addGBLComponent(labelPanel, 0, 0,5,1,0,0,"NONE",GridBagConstraints.LINE_START);
            
        

            JTable bpTable=new JTable(new DefaultTableModel(new String[]{"Stage 1"},0));
            JScrollPane bpTableScPane= new JScrollPane(bpTable);
            bpTableScPane.setPreferredSize(new Dimension(190,300));

            JTable humanTable=new JTable(new DefaultTableModel(new String[]{"Stage 2"},0));
            JScrollPane humanTableScPane= new JScrollPane(humanTable);
            humanTableScPane.setPreferredSize(new Dimension(190,300));

            JTable itsTable=new JTable(new DefaultTableModel(new String[]{"Stage 3"},0));
            JScrollPane itsTableScPane= new JScrollPane(itsTable);
            itsTableScPane.setPreferredSize(new Dimension(190,300));

            JTable peTable=new JTable(new DefaultTableModel(new String[]{"Stage 4"},0));
            JScrollPane peTableScPane= new JScrollPane(peTable);
            peTableScPane.setPreferredSize(new Dimension(190,300));

            
            addGBLComponent(bpTableScPane, 0, 2,2,1);
            addGBLComponent(humanTableScPane, 2, 2);
            addGBLComponent(itsTableScPane, 3, 2);
            addGBLComponent(peTableScPane, 4, 2);

            JButton button1=new JButton("Add");
            JButton button2=new JButton("Add");
            JButton button3=new JButton("Add");
            JButton button4=new JButton("Add");

            addGBLComponent(button1, 0, 3,2,1,"BOTH");
            addGBLComponent(button2, 2, 3,1,1,"BOTH");
            addGBLComponent(button3, 3, 3,1,1,"BOTH");
            addGBLComponent(button4, 4, 3,1,1,"BOTH");

            changeButtonListener(this, (Class<JComponent>)((JComponent)new Tech()).getClass());
            
        }
    }

    private class Tech extends GridBagPanel {

        public Tech() {
            // JLabel label = new JLabel(this.getClass().getName());
            // addGBLComponent(label, 0, 0);
            // addGBLComponent(button, 0, 1);
        
            setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createEmptyBorder(10, 10, 10, 10),
                            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#005BAC")), "Group")));
            setPreferredSize(new Dimension(700, 500));

            // JLabel nameLabel = new JLabel();
            // nameLabel.setText("Group Name");
            // addGBLComponent(nameLabel, 0,
            // 0,0.1,0.1,"NONE",GridBagConstraints.LINE_START);

            String[] nameHeader = { "Group Name" };

            ArrayList<String[]> groupList = ontologyFunc.LoadAllGroup();
            String[][] groupAry = new String[groupList.size()][1];
            for (int i = 0; i < groupList.size(); i++) {
                groupAry[i] = groupList.get(i);
            }

            JTable nameTable = new JTable(groupAry, nameHeader);
            // nameTable.setTableHeader(null);

            JScrollPane nameTableScPane = new JScrollPane(nameTable);

            nameTableScPane.setPreferredSize(new Dimension(150, 470));
            addGBLComponent(nameTableScPane, 0, 1, 0.2, 1.0);

            GridBagPanel detailPane = new GridBagPanel();
            JLabel label0 = makeHeader("Group Name");
            JLabel label1 = makeContent("");
            JLabel label2 = makeHeader("Techniques");
            JLabel label3 = makeHeader("Software");
            JTable techTable = makeContentTable();
            JScrollPane techSc = new JScrollPane(techTable);
            techSc.setPreferredSize(new Dimension(300, 420));
            JTable softTable = makeContentTable();
            JScrollPane softSc = new JScrollPane(softTable);
            softSc.setPreferredSize(new Dimension(300, 420));

            detailPane.addGBLComponent(label0, 0, 0, 1, 1, "BOTH");
            detailPane.addGBLComponent(label1, 1, 0, 2, 1, "BOTH");
            detailPane.addGBLComponent(label2, 0, 1, 2, 1, "BOTH");
            detailPane.addGBLComponent(label3, 2, 1, 1, 1, "BOTH");
            detailPane.addGBLComponent(techSc, 0, 2, 2, 1, "BOTH");
            detailPane.addGBLComponent(softSc, 2, 2, 1, 1, "BOTH");

            addGBLComponent(detailPane, 2, 1, 0.4, 0.8, "BOTH");

            nameTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent event) {
                    String value = nameTable.getValueAt(nameTable.getSelectedRow(), 0).toString();

                    ArrayList<String> techList = ontologyFunc.LoadGroupTechnique(value);
                    System.out.println("size: " + techList.size());
                    String[][] techs = new String[techList.size()][1];
                    for (int i = 0; i < techList.size(); i++) {
                        techs[i] = new String[] { techList.get(i) };
                    }
                    ((DefaultTableModel) techTable.getModel()).setRowCount(0);
                    for (String[] row : techs) {
                        ((DefaultTableModel) techTable.getModel()).addRow(row);
                        System.out.println(row);
                    }

                }
            });

            techTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    String group = nameTable.getValueAt(nameTable.getSelectedRow(), 0).toString();
                    String tech = techTable.getValueAt(techTable.getSelectedRow(), 0).toString();
                    ArrayList<String> groupSWList = ontologyFunc.LoadGroupSW(group);

                    ((DefaultTableModel) softTable.getModel()).setRowCount(0);
                    for (String software : ontologyFunc.LoadTechSW(tech)) {
                        for (String groupSW : groupSWList) {
                            if (groupSW.equals(software)) {

                                ((DefaultTableModel) softTable.getModel()).addRow(new String[] { software });

                            }
                        }
                    }
                }

            });

            JButton applyButton = new JButton("Apply");

            // applyButton.addActionListener(new ActionListener() {
            //     @Override
            //     public void actionPerformed(ActionEvent e) {
            //         String group = nameTable.getValueAt(nameTable.getSelectedRow(), 0).toString();
            //         ArrayList<String> techList = ontologyFunc.LoadGroupTechnique(group);
            //         System.out.println("size: " + techList.size());
            //         String[][] techs = new String[techList.size()][1];
            //         for (int i = 0; i < techList.size(); i++) {
            //             techs[i] = new String[] { techList.get(i) };
            //         }
            //         ArrayList<String> groupSWList = ontologyFunc.LoadGroupSW(group);
            //         String tech;
            //         ArrayList<String[]> swList = new ArrayList<String[]>();
            //         for (String[] t : techs) {
            //             tech = t[0];
            //             ProcessedData.techniqueList.add(new Technique(tech));
            //             for (String software : ontologyFunc.LoadTechSW(tech)) {
            //                 for (String groupSW : groupSWList) {
            //                     if (groupSW.equals(software)) {
            //                         // swList.add(new String[]{software});
            //                         if (!ProcessedData.containsSoftware(software)) {
            //                             ProcessedData.softwareList.add(new Software(software));

            //                         }
            //                     }
            //                 }
            //             }
            //         }
            //         // techniques=techs;
            //         // softwares=(String[][])swList.stream().toArray(String[][]::new);
            //         updateTable();
            //         groupDialog.dispose();
            //     }
            // });

            addGBLComponent(applyButton, 2, 2, 0.0, 0.0, "NONE", GridBagConstraints.LINE_END);
        }
    }
}
