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

public class OrgCard extends GridBagPanel {
    private OntologyFunc ontologyFunc=ProcessedData.ontologyFunc;

    public OrgCard(MakeScenario upperDialog) {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#005BAC")),
                "Organization"));

        GridBagPanel labelPanel = new GridBagPanel();

        JLabel infoLabel = new JLabel();
        infoLabel.setText("Select an organizaion to assess security risks for an attack.");

        JLabel blankLabel = new JLabel();
        blankLabel.setPreferredSize(new Dimension(10, 10));

        labelPanel.addGBLComponent(infoLabel, 0, 0);
        labelPanel.addGBLComponent(blankLabel, 0, 1);

        addGBLComponent(labelPanel, 0, 0, 5, 1, 0, 0, "NONE", GridBagConstraints.LINE_START);

        JLabel orgLabel = new JLabel();
        orgLabel.setText("Organizaion: ");
        addGBLComponent(orgLabel, 0, 1);

        JComboBox<Object> orgComboBox = new JComboBox<>();
        for (String org : ontologyFunc.LoadAllOrg()) {
            orgComboBox.addItem(org);
        }
        orgComboBox.setSelectedIndex(-1);
        orgComboBox.setPreferredSize(new Dimension(250, 22));

        addGBLComponent(orgComboBox, 1, 1, 4, 1, 0, 0, "NONE", GridBagConstraints.LINE_START);

        JTable bpTable = new JTable(new DefaultTableModel(new String[] { "Business Process" }, 0));
        JScrollPane bpTableScPane = new JScrollPane(bpTable);
        bpTableScPane.setPreferredSize(new Dimension(130, 300));

        JTable humanTable = new JTable(new DefaultTableModel(new String[] { "Role", "Person" }, 0));
        JScrollPane humanTableScPane = new JScrollPane(humanTable);
        humanTableScPane.setPreferredSize(new Dimension(210, 300));

        JTable itsTable = new JTable(new DefaultTableModel(new String[] { "Type", "Content" }, 0));
        JScrollPane itsTableScPane = new JScrollPane(itsTable);
        itsTableScPane.setPreferredSize(new Dimension(210, 300));

        JTable peTable = new JTable(new DefaultTableModel(new String[] { "Type", "Content" }, 0));
        JScrollPane peTableScPane = new JScrollPane(peTable);
        peTableScPane.setPreferredSize(new Dimension(210, 300));

        bpTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() || bpTable.getSelectedRowCount() == 0) {
                    return;
                }
                humanTable.clearSelection();
                for (String role : ontologyFunc
                        .LoadRoleFromBP(bpTable.getValueAt(bpTable.getSelectedRow(), 0).toString())) {
                    int index = findRow(humanTable, role, 0);
                    humanTable.addRowSelectionInterval(index, index);
                }
            }
        });

        humanTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() || humanTable.getSelectedRowCount() == 0) {
                    return;
                }
                itsTable.clearSelection();
                for (String software : ontologyFunc
                        .LoadSWFromRole(humanTable.getValueAt(humanTable.getSelectedRow(), 0).toString())) {
                    int index = findRow(itsTable, software, 1);
                    itsTable.addRowSelectionInterval(index, index);
                }
            }
        });
        // 수정작업 하자
        // 수정작업 하자
        // 수정작업 하자
        // 수정작업 하자
        // 수정작업 하자
        // 수정작업 하자

        // itsTable.getSelectionModel().addListSelectionListener(new
        // ListSelectionListener(){
        // @Override
        // public void valueChanged(ListSelectionEvent e){
        // if (e.getValueIsAdjusting() || itsTable.getSelectedRowCount()==0){
        // return;
        // }
        // dataTable.clearSelection();
        // for (String data :
        // ontologyFunc2.LoadDataFromSW(softwareTable.getValueAt(roleTable.getSelectedRow(),0).toString()))
        // {
        // int index = findRow(dataTable, data);
        // dataTable.addRowSelectionInterval(index,index);
        // }
        // }
        // });

        JTabbedPane tabbedPane = new JTabbedPane();
        addGBLComponent(tabbedPane, 0, 2,5,1);

        GridBagPanel assetPanel = new GridBagPanel();
        
        assetPanel.addGBLComponent(bpTableScPane, 0, 2, 2, 1);
        assetPanel.addGBLComponent(humanTableScPane, 2, 2);
        assetPanel.addGBLComponent(itsTableScPane, 3, 2);
        assetPanel.addGBLComponent(peTableScPane, 4, 2);

        tabbedPane.addTab("Asset", assetPanel);
        tabbedPane.addTab("0", new GridBagPanel());

        // addGBLComponent(bpTableScPane, 0, 2, 2, 1);
        // addGBLComponent(humanTableScPane, 2, 2);
        // addGBLComponent(itsTableScPane, 3, 2);
        // addGBLComponent(peTableScPane, 4, 2);

        orgComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((DefaultTableModel) bpTable.getModel()).setRowCount(0);
                ArrayList<String> bps = ontologyFunc.LoadBPFromOrg(orgComboBox.getSelectedItem().toString());
                for (String bp : bps) {
                    ((DefaultTableModel) bpTable.getModel()).addRow(new String[] { bp });
                    ArrayList<String> roles = ontologyFunc.LoadRoleFromBP(bp);
                    for (String role : roles) {
                        ArrayList<String> people = ontologyFunc.LoadPersonFromRole(role);
                        for (String person : people) {

                            ((DefaultTableModel) humanTable.getModel()).addRow(new String[] { role, person });
                        }
                        ArrayList<String> softwares = ontologyFunc.LoadSWFromRole(role);
                        for (String software : softwares) {
                            if (isIn(itsTable, software)) {
                                continue;
                            }
                            ((DefaultTableModel) itsTable.getModel()).addRow(new String[] { "Software", software });
                            ArrayList<String> datas = ontologyFunc.LoadDataFromSW(software);
                            for (String data : datas) {
                                if (isIn(itsTable, data)) {
                                    continue;
                                }
                                ((DefaultTableModel) itsTable.getModel()).addRow(new String[] { "Data", data });
                            }
                            ArrayList<String> platforms = ontologyFunc.LoadPlatformFromSW(software);
                            for (String platform : platforms) {
                                if (isIn(itsTable, platform)) {
                                    continue;
                                }
                                ((DefaultTableModel) itsTable.getModel())
                                        .addRow(new String[] { "Platform", platform });
                            }
                            ArrayList<String> hardwares = ontologyFunc.LoadHardwareFromSW(software);
                            for (String hardware : hardwares) {
                                if (isIn(itsTable, hardware)) {
                                    continue;
                                }
                                ((DefaultTableModel) itsTable.getModel())
                                        .addRow(new String[] { "Hardware", hardware });
                                ArrayList<String> das = ontologyFunc.LoadDAFromHW(hardware);
                                for (String da : das) {
                                    if (isIn(peTable, da)) {
                                        continue;
                                    }
                                    ((DefaultTableModel) peTable.getModel()).addRow(new String[] { "DA", da });
                                }
                            }
                            ArrayList<String> mds = ontologyFunc.LoadMDFromSW(software);
                            for (String md : mds) {
                                if (isIn(peTable, md)) {
                                    continue;
                                }
                                ((DefaultTableModel) peTable.getModel()).addRow(new String[] { "MD", md });
                            }
                        }
                    }
                }
                // ArrayList<Asset> assetList=new ArrayList<Asset>();
                // Vector vector =((DefaultTableModel)(itsTable.getModel())).getDataVector();
                // String type="";
                // String name="";
                // for (Object obj:vector){
                //     type=((String)((Vector)obj).toArray()[0]);
                //     name=((String)((Vector)obj).toArray()[1]);
                //     assetList.add(new Asset(type, name));
                // }
                // ProcessedData.setAssetList(assetList);


            }
        });
        addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                // TODO Auto-generated method stub
            }
            
            @Override
            public void ancestorRemoved(AncestorEvent event) {
                ArrayList<Asset> assetList=new ArrayList<Asset>();
                Vector vector =((DefaultTableModel)(itsTable.getModel())).getDataVector();
                String type="";
                String name="";
                for (Object obj:vector){
                    type=((String)((Vector)obj).toArray()[0]);
                    name=((String)((Vector)obj).toArray()[1]);
                    assetList.add(new Asset(type, name));
                }
                ProcessedData.setOrganization(orgComboBox.getSelectedItem().toString());
                ProcessedData.setAssetList(assetList);
                
                
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
                

            }
        });
    }

    private boolean isIn(JTable table, String element) {
        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 1).toString().equals(element)) {
                return true;
            }
        }
        return false;
    }

    private int findRow(JTable table, String element, int index) {
        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, index).toString().equals(element)) {
                return i;
            }
        }
        System.out.println("Couldnt find " + element);
        return -1;
    }

}