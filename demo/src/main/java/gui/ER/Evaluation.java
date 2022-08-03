package gui.ER;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import data.Asset;
import data.ProcessedData;
import data.SecReq;
import data.Threat;
import gui.GridBagPanel;

public class Evaluation extends GridBagPanel{
    private JTable setTable =new JTable(new DefaultTableModel(new String[]{"Asset", "SR", "Evidence", "Evaluation", "Score"},0));
    private JLabel label;
    private DetailArea detailArea;
    public Evaluation(JTabbedPane tPane){
        super(tPane);
        init();
    }

    private void init() {
        label = new JLabel("");
        addGBLComponent(label, 0, 0);


        detailArea = new DetailArea();

        addGBLComponent(detailArea, 0, 1, 2, 1);

        JButton button = new JButton("Next");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex()+1);
            }
        });
        JLabel blankLabel1 = new JLabel();
        blankLabel1.setPreferredSize(new Dimension(10, 10));
        addGBLComponent(blankLabel1, 0, 2);
        addGBLComponent(button, 1, 3, 0, 0, "NONE", GridBagConstraints.LINE_END);

        addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                // TODO Auto-generated method stub
                ArrayList<Asset> assetList=ProcessedData.getThreatAffectedAssets();
                DefaultTableModel model=(DefaultTableModel) setTable.getModel();
                for (Asset asset:assetList){
                    for (Threat th:asset.getThreatList()){
                        model.addRow(new String[]{
                            asset.getName(),
                            String.format("%s-SRDT",th.getName()),
                            th.getSecReq().getEvidence("SRDT").getName(),
                            "X",
                            ""
                        });
                        model.addRow(new String[]{
                            asset.getName(),
                            String.format("%s-SRRP",th.getName()),
                            th.getSecReq().getEvidence("SRRP").getName(),
                            "X",
                            ""
                        });
                        model.addRow(new String[]{
                            asset.getName(),
                            String.format("%s-SRPD",th.getName()),
                            th.getSecReq().getEvidence("SRPD").getName(),
                            "X",
                            ""
                        });
                        model.addRow(new String[]{
                            asset.getName(),
                            String.format("%s-SRPV",th.getName()),
                            th.getSecReq().getEvidence("SRPV").getName(),
                            "X",
                            ""
                        });
                    }
                }
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
                // TODO Auto-generated method stub

            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
                // TODO Auto-generated method stub

            }
        });
    }

    private class DetailArea extends GridBagPanel {
        private JScrollPane setTabScPane = new JScrollPane(setTable);
        private JButton editButton=new JButton("Edit");

        public DetailArea() {
            setPreferredSize(new Dimension(1060,580));

            setTabScPane.setPreferredSize(new Dimension(900,450));
            addGBLComponent(setTabScPane, 0, 1,2,1,0,0,"BOTH");

            editButton.setEnabled(false);
            addGBLComponent(editButton, 1, 0,1,1,0,0,"NONE",GridBagConstraints.LINE_END);

            setTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            setTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(!editButton.isEnabled())
                        editButton.setEnabled(true);
                }
            });

            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // String assetName=(String)setTable.getValueAt(setTable.getSelectedRow(), 0);
                    // String srName=(String)setTable.getValueAt(setTable.getSelectedRow(), 1);
                    // String evName=(String)setTable.getValueAt(setTable.getSelectedRow(), 2);

                    // JDialog dialog = new JDialog();
                    // dialog.setPreferredSize(new Dimension(800,600));
                    
                    // GridBagPanel pane = new GridBagPanel();
                    // dialog.add(pane);

                    

                    // ////////////////////////
                    // JLabel assetHeader=makeHeader("Asset");
                    // JTextArea assetText=new JTextArea();
                    // pane.addGBLComponent(assetHeader, 0, 0);



                }
            });
            
        }


      
    }
}
