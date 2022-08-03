package gui.TA;

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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.prompt.PromptSupport;

import data.Asset;
import data.ProcessedData;
import data.SecReq;
import data.Threat;
import gui.GridBagPanel;
import gui.AC.AttackScenario;

public class ThreatAffectedAsset extends GridBagPanel {
    private JLabel label;
    private DetailArea detailArea;
    private GridBagPanel self = this;
    // private 

    public ThreatAffectedAsset(JTabbedPane tPane) {
        super(tPane);
        init();

        addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                // TODO Auto-generated method stub
                System.out.println("added");
                DefaultTableModel threatTableModel=(DefaultTableModel)detailArea.getThreatTable().getModel();
                
                threatTableModel.setRowCount(0);
                for(Threat th:ProcessedData.getThreatList()){
                    threatTableModel.addRow(new String[]{th.getName()});
                }
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
                // TODO Auto-generated method stub
                System.out.println("removed");

            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
                // TODO Auto-generated method stub
                System.out.println("moved");

            }
        });

    }


    private void init() {
        label = new JLabel("Threat-affected Asset");
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
    }

    private class DetailArea extends GridBagPanel {
        private JTable threatTable;
        public JTable getThreatTable() {
            return threatTable;
        }


        private JScrollPane threatTabScPane;

        public DetailArea() {
            setPreferredSize(new Dimension(1060,580));

            threatTable=new JTable(new DefaultTableModel(new String[]{"Threat ID"},0));
            threatTabScPane=new JScrollPane(threatTable);
            threatTabScPane.setPreferredSize(new Dimension(150,160));
            addGBLComponent(threatTabScPane, 0, 0,1,1,0,0,"BOTH");
            
            SRInput srInput = new SRInput();
            addGBLComponent(srInput, 1, 0,3,1);
            
            ThreatInfo threatInfo = new ThreatInfo();
            addGBLComponent(threatInfo, 0, 1,2,1);
            
            
            threatTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    DefaultTableModel srModel =((DefaultTableModel)(srInput.getSrArea().getSrTable().getModel()));
                    srModel.setRowCount(0);
                    String selectedThreat=(String)threatTable.getValueAt(threatTable.getSelectedRow(), 0);
                    for (Asset asset:ProcessedData.getAssetList()){
                        if (ProcessedData.getThreat(selectedThreat).getAssetList().contains(asset)){
                            srModel.addRow(new Object[]{
                                asset.getTypeName(),
                                asset.getName(),
                                true
                            });
                        }
                        else{
                            srModel.addRow(new Object[]{
                                asset.getTypeName(),
                                asset.getName(),
                                false
                            });
                        }
                    }
                }
            });

            AssetInfo assetInfo = new AssetInfo();
            addGBLComponent(assetInfo, 2, 1,1,1,0,0,"BOTH");
            
        }

        private class ThreatInfo extends GridBagPanel{
            public ThreatInfo(){
                setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(AjouBlue),"Threat Info"));

                JLabel techHeader=makeHeader("Technique : ");
                JLabel tacticHeader=makeHeader("Tactic");
                JLabel CAPECHeader=makeHeader("CAPEC : ");
                JLabel CWEHeader=makeHeader("CWE : ");
                JLabel CVEHeader=makeHeader("CVE : ");
                JLabel mitiHeader=makeHeader("Mitigation : ");
                addGBLComponent(techHeader, 0, 0,1,1,0,0,"BOTH");
                addGBLComponent(tacticHeader, 0, 1,1,1,0,0,"BOTH");
                addGBLComponent(CAPECHeader, 0, 2,1,1,0,0,"BOTH");
                addGBLComponent(CWEHeader, 0, 3,1,1,0,0,"BOTH");
                addGBLComponent(CVEHeader, 0, 4,1,1,0,0,"BOTH");
                addGBLComponent(mitiHeader, 0, 5,1,1,0,0,"BOTH");

                JLabel techContent=makeContent("");
                JLabel tacticContent=makeContent("");
                System.out.println(techContent.getPreferredSize());
                JScrollPane CAPECTabSc=new JScrollPane(makeContentTable());
                JScrollPane CWETabSc=new JScrollPane(makeContentTable());
                JScrollPane CVETabSc=new JScrollPane(makeContentTable());
                JScrollPane mitiTabSc=new JScrollPane(makeContentTable());
                CAPECTabSc.setPreferredSize(new Dimension(600,55));
                CWETabSc.setPreferredSize(new Dimension(600,55));
                CVETabSc.setPreferredSize(new Dimension(600,55));
                mitiTabSc.setPreferredSize(new Dimension(600,55));
                addGBLComponent(techContent, 1, 0,1,1,0,0,"BOTH");
                addGBLComponent(tacticContent, 1, 1,1,1,0,0,"BOTH");
                addGBLComponent(CAPECTabSc, 1, 2,1,1,0,0,"BOTH");
                addGBLComponent(CWETabSc, 1, 3,1,1,0,0,"BOTH");
                addGBLComponent(CVETabSc, 1, 4,1,1,0,0,"BOTH");
                addGBLComponent(mitiTabSc, 1, 5,1,1,0,0,"BOTH");

                threatTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        String threatID = (String)threatTable.getValueAt(threatTable.getSelectedRow(), 0);
                        Threat threat = ProcessedData.getThreat(threatID);
                        techContent.setText(threat.getTechnique());
                        tacticContent.setText(threat.getTactic());

                        // CAPEC 등 데이터 추가시
                        // ((DefaultTableModel)(((JTable)(CAPECTabSc.getViewport().getView())).getModel())).addRow(rowData);
                        DefaultTableModel mitiModel =(DefaultTableModel)(((JTable)(mitiTabSc.getViewport().getView())).getModel());
                        mitiModel.setRowCount(0);
                        for (String miti:threat.getMitigationList()){
                            System.out.println(miti);
                            mitiModel.addRow(new String[]{miti});

                        }

                    }
                });
                

            }

        }

        private class AssetInfo extends GridBagPanel{
            private JLabel label = new JLabel("Asset INFO");

            public AssetInfo(){
                setPreferredSize(new Dimension(300,300));
                label.setBackground(AjouBlue);
                label.setForeground(Color.white);
                label.setOpaque(true);
                label.setPreferredSize(new Dimension(280,280));
                addGBLComponent(label, 0, 0,0,0,0,0,"BOTH");
            }
        }


        private class SRInput extends GridBagPanel{
            private JLabel label;
            private JButton saveButton;
            private SRArea srArea;

            public SRArea getSrArea() {
                return srArea;
            }

            public SRInput(){
                setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(AjouBlue),"Security Requirement"));
                
                label=new JLabel("Select threat-affected assets.");
                addGBLComponent(label, 0, 0,3,1,0,0,"NONE",GridBagConstraints.LINE_START);
                
                JLabel blankLabel = new JLabel("");
                blankLabel.setPreferredSize(new Dimension(600,10));
                addGBLComponent(blankLabel, 0, 1);

                saveButton=new JButton("Save");
                addGBLComponent(saveButton, 2, 1);
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String selectedThreat=(String)threatTable.getValueAt(threatTable.getSelectedRow(), 0);
                        Asset selectedAsset=null;
                        Vector vector = ((DefaultTableModel)(srArea.getSrTable().getModel())).getDataVector();
                        Object[] data;
                        ArrayList<Asset> assetList=new ArrayList<Asset>();
                        for (Object obj:vector){
                            data=((Vector)obj).toArray();
                            if(((Boolean)data[2])==true){
                                selectedAsset=ProcessedData.getAsset((String)data[1]);
                                assetList.add(selectedAsset);
                                selectedAsset.getThreatList().add(ProcessedData.getThreat(selectedThreat));
                            }
                        }
                        ProcessedData.getThreat(selectedThreat).setAssetList(assetList);
                    }
                });

                srArea=new SRArea();
                addGBLComponent(srArea, 0, 2,3,1);
                
                
            }

            private class SRArea extends GridBagPanel{
                private JTable srTable;
                public JTable getSrTable() {
                    return srTable;
                }

                private JScrollPane srTabSc;
                private JTextArea inputArea;
                
                public void setInputArea(JTextArea inputArea) {
                    this.inputArea = inputArea;
                }

                public JTextArea getInputArea() {
                    return inputArea;
                }

                public SRArea(){
                    setBorder(BorderFactory.createLineBorder(AjouBlue));

                    srTable=new JTable(new DefaultTableModel(new String[]{"Type","Name","Checked"},0){
                        public java.lang.Class<?> getColumnClass(int columnIndex) {
                            switch (columnIndex) {
                                case 2:
                                    return Boolean.class;
                                default:
                                    return String.class;
                            }
                        };
                    });
                    srTable.setShowGrid(false);
                    srTable.setBorder(BorderFactory.createEmptyBorder());
                    srTable.setBackground(Color.white);
                    srTable.setForeground(AjouBlue);
                    srTable.getTableHeader().setBackground(AjouBlue);
                    srTable.getTableHeader().setForeground(Color.white);
                    // srTable.setRowHeight(50);
                    srTable.getColumnModel().getColumn(0).setPreferredWidth(300);
                    srTable.getColumnModel().getColumn(1).setPreferredWidth(400);
                    srTable.getColumnModel().getColumn(2).setPreferredWidth(100);
                    srTable.setDefaultRenderer(String.class, new DefaultTableCellRenderer(){

                        @Override
                        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                boolean hasFocus, int row, int column) {
                            
                            setBorder(noFocusBorder);
                            return super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
                        }
                    });
                    srTable.setFocusable(false);


                    srTabSc=new JScrollPane(srTable);
                    srTabSc.setPreferredSize(new Dimension(800,200));
                    srTabSc.setBorder(BorderFactory.createEmptyBorder());
                    srTabSc.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
                    addGBLComponent(srTabSc, 0, 0);

                    // addAncestorListener(new AncestorListener() {
                    //     @Override
                    //     public void ancestorAdded(AncestorEvent event) {
                    //         // TODO Auto-generated method stub
                            
                            
                    //     }
                    //     @Override
                    //     public void ancestorMoved(AncestorEvent event) {
                    //         // TODO Auto-generated method stub
                            
                    //     }
                    //     @Override
                    //     public void ancestorRemoved(AncestorEvent event) {
                    //         // TODO Auto-generated method stub
                            
                    //     }
                    // });
                    
                    
                }
            }
        }

    }
}
