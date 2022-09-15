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
import javax.swing.JFileChooser;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.prompt.PromptSupport;

import api.CSVFunc;
import data.Asset;
import data.Evidence;
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
                tPane.setEnabledAt(2, true);
                // TODO Auto-generated method stub
                System.out.println("added");
                DefaultTableModel threatTableModel=(DefaultTableModel)detailArea.getThreatTable().getModel();
                
                threatTableModel.setRowCount(0);
                for(Threat th:ProcessedData.getThreatList()){
                    threatTableModel.addRow(new String[]{th.getId()});
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
        addGBLComponent(button, 2, 3, 0, 0, "NONE", GridBagConstraints.LINE_END);

        JButton downLoadButton=new JButton("Download");
        addGBLComponent(downLoadButton, 1, 3,0,0,"NONE",GridBagConstraints.LINE_END);
        downLoadButton.addActionListener(new ActionListener(){
            JFileChooser chooser=new JFileChooser();
        
            public void actionPerformed(ActionEvent e){
                FileNameExtensionFilter filter=new FileNameExtensionFilter(
                    ".csv", "csv");
                chooser.setFileFilter(filter);
        
                int ret=chooser.showSaveDialog(null);
                if(ret==JFileChooser.APPROVE_OPTION){
                    String filePath=chooser.getSelectedFile().getPath();
                    if (filePath.lastIndexOf(".")==-1&&
                        !filePath.substring(filePath.lastIndexOf(".")+1).equalsIgnoreCase("csv")){
                        filePath=filePath+".csv";
                    }
                    String[] header=new String[]{"Asset","Asset Criticality","Threat ID", "Threat Exposure"};
                    ArrayList<String[]> data=new ArrayList<String[]>();
                    Vector vector=((DefaultTableModel)detailArea.getThreatTable().getModel()).getDataVector();
                    String threatString=null;
                    SecReq secReq=null;
                    for(Object o:vector){
                        threatString=(String)(((Vector)o).toArray(new String[0]))[0];
                        // secReq=ProcessedData.getThreat(threatString).getSecReq();

                        for(Asset as:ProcessedData.getThreatAffectedAssets()){
                            data.add(new String[]{as.getId(),"",threatString,""+as.getThreatList().size()});

                        }
                        
                    }
                    CSVFunc csv=new CSVFunc(header, data);
                    csv.saveToFile(filePath);
                }
            }
        });

    }

    private class DetailArea extends GridBagPanel {
        private JTable threatTable;
        private AssetInfo assetInfo = new AssetInfo();
        public JTable getThreatTable() {
            return threatTable;
        }


        private JScrollPane threatTabScPane;

        public DetailArea() {
            setPreferredSize(new Dimension(1060,580));

            threatTable=new JTable(new DefaultTableModel(new String[]{"Threat ID"},0));
            threatTabScPane=new JScrollPane(threatTable);
            threatTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            threatTabScPane.setPreferredSize(new Dimension(150,160));
            addGBLComponent(threatTabScPane, 0, 0,1,1,0,0,"BOTH");
            
            AssetInput srInput = new AssetInput();
            addGBLComponent(srInput, 1, 0,3,1);
            
            ThreatInfo threatInfo = new ThreatInfo();
            addGBLComponent(threatInfo, 0, 1,2,1);
            
            
            threatTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    DefaultTableModel srModel =((DefaultTableModel)(srInput.getSrArea().getAssetTable().getModel()));
                    srInput.getSrArea().getAssetTable().clearSelection();
                    srModel.setRowCount(0);
                    String selectedThreat=(String)threatTable.getValueAt(threatTable.getSelectedRow(), 0);
                    for (Asset asset:ProcessedData.getAssetList()){
                        if (ProcessedData.getThreat(selectedThreat).getAssetList().contains(asset)){
                            srModel.addRow(new Object[]{
                                asset.getTypeName(),
                                asset.getId(),
                                true
                            });
                        }
                        else{
                            srModel.addRow(new Object[]{
                                asset.getTypeName(),
                                asset.getId(),
                                false
                            });
                        }
                    }
                }
            });

            addGBLComponent(assetInfo, 2, 1,1,1,0,0,"NONE",GridBagConstraints.PAGE_START);
            
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
            private JLabel IDHeader=makeHeader("Asset ID");
            private JLabel nameHeader=makeHeader("Name");
            private JLabel descHeader=makeHeader("Description");
            private JLabel typeHeader=makeHeader("Type");
            private JLabel raHeader=makeHeader("Related Assets");

            private JLabel IDContent=makeContent("");
            private JLabel nameContent=makeContent("");
            // private JLabel descContent=makeContent("");
            private JTextArea descContent=makeTextArea("",false);
            private JLabel typeContent=makeContent("");
            private JLabel raContent=makeContent("");

            public JLabel getIDContent() {
                return IDContent;
            }

            public void setIDContent(JLabel iDContent) {
                IDContent = iDContent;
            }

            public JLabel getNameContent() {
                return nameContent;
            }

            public void setNameContent(JLabel nameContent) {
                this.nameContent = nameContent;
            }

            public JTextArea getDescContent() {
                return descContent;
            }

            public void setDescContent(JTextArea descContent) {
                this.descContent = descContent;
            }

            public JLabel getTypeContent() {
                return typeContent;
            }

            public void setTypeContent(JLabel typeContent) {
                this.typeContent = typeContent;
            }


            public JLabel getRaContent() {
                return raContent;
            }

            public void setRaContent(JLabel raContent) {
                this.raContent = raContent;
            }

            public AssetInfo(){

                setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(AjouBlue),"Asset Info"));
                

                addGBLComponent(IDHeader, 0, 0,1,1,"BOTH");
                addGBLComponent(nameHeader, 0, 1,1,1,"BOTH");
                addGBLComponent(typeHeader,0, 2,1,1,"BOTH");
                addGBLComponent(descHeader, 0, 3,1,1,"BOTH");
                addGBLComponent(raHeader, 0, 4,1,1,"BOTH");


                IDContent.setPreferredSize(new Dimension(180,18));
                nameContent.setPreferredSize(new Dimension(180,18));
                descContent.setPreferredSize(new Dimension(180,190));
                typeContent.setPreferredSize(new Dimension(180,18));
                raContent.setPreferredSize(new Dimension(180,18));
                
                addGBLComponent(IDContent, 1, 0);
                addGBLComponent(nameContent, 1, 1);
                addGBLComponent(typeContent,1, 2);
                addGBLComponent(descContent, 1, 3);
                addGBLComponent(raContent, 1, 4);

                

            }
        }


        private class AssetInput extends GridBagPanel{
            private JLabel label;
            private JButton saveButton;
            private AssetArea assetArea;

            public AssetArea getSrArea() {
                return assetArea;
            }

            public AssetInput(){
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
                        String threatId=(String)threatTable.getValueAt(threatTable.getSelectedRow(), 0);
                        Threat selectedThreat = ProcessedData.getThreat(threatId);
                        Asset selectedAsset=null;
                        Vector vector = ((DefaultTableModel)(assetArea.getAssetTable().getModel())).getDataVector();
                        Object[] data;
                        ArrayList<Asset> assetList=selectedThreat.getAssetList();
                        Evidence evidence=null;
                        for (Object obj:vector){
                            data=((Vector)obj).toArray();
                            selectedAsset=ProcessedData.getAsset((String)data[1]);
                            if(((Boolean)data[2])==true &&
                            !selectedAsset.getThreatList().contains(selectedThreat)){
                                selectedAsset.getThreatList().add(selectedThreat);
                                
                                for(SecReq sr: selectedThreat.getSrList()){
                                    evidence=new Evidence(((String)data[1])+"-"+sr.getId()+"-EV");
                                    System.out.println(evidence.getId());
                                    evidence.setSr(sr);
                                    selectedAsset.getEvidenceList().add(evidence);

                                }
                                
                                assetList.add(selectedAsset);
                            }
                        }
                       
                    }
                });

                assetArea=new AssetArea();
                addGBLComponent(assetArea, 0, 2,3,1);
                
                
            }

            private class AssetArea extends GridBagPanel{
                private JTable assetTable;
                public JTable getAssetTable() {
                    return assetTable;
                }

                private JScrollPane assetTabSc;
                private JTextArea inputArea;
                
                public void setInputArea(JTextArea inputArea) {
                    this.inputArea = inputArea;
                }

                public JTextArea getInputArea() {
                    return inputArea;
                }

                public AssetArea(){
                    setBorder(BorderFactory.createLineBorder(AjouBlue));

                    assetTable=new JTable(new DefaultTableModel(new String[]{"Type","Name","Checked"},0){
                        public java.lang.Class<?> getColumnClass(int columnIndex) {
                            switch (columnIndex) {
                                case 2:
                                    return Boolean.class;
                                default:
                                    return String.class;
                            }
                        };
                    });
                    assetTable.setShowGrid(false);
                    assetTable.setBorder(BorderFactory.createEmptyBorder());
                    assetTable.setBackground(Color.white);
                    assetTable.setForeground(AjouBlue);
                    assetTable.getTableHeader().setBackground(AjouBlue);
                    assetTable.getTableHeader().setForeground(Color.white);
                    // srTable.setRowHeight(50);
                    assetTable.getColumnModel().getColumn(0).setPreferredWidth(300);
                    assetTable.getColumnModel().getColumn(1).setPreferredWidth(400);
                    assetTable.getColumnModel().getColumn(2).setPreferredWidth(100);
                    assetTable.setDefaultRenderer(String.class, new DefaultTableCellRenderer(){

                        @Override
                        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                boolean hasFocus, int row, int column) {
                            
                            setBorder(noFocusBorder);
                            return super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
                        }
                    });
                    assetTable.setFocusable(false);


                    assetTabSc=new JScrollPane(assetTable);
                    assetTabSc.setPreferredSize(new Dimension(800,200));
                    assetTabSc.setBorder(BorderFactory.createEmptyBorder());
                    assetTabSc.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
                    addGBLComponent(assetTabSc, 0, 0);

                    assetTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent e) {
                            if(assetTable.getSelectedRow()==-1){
                                return;
                            }
                            String assetId=(String)assetTable.getValueAt(assetTable.getSelectedRow(), 1);
                            Asset asset = ProcessedData.getAsset(assetId);
                            
                            assetInfo.getIDContent().setText(assetId);
                            assetInfo.getNameContent().setText(asset.getName());
                            assetInfo.getDescContent().setText(asset.getDescription());
                            assetInfo.getTypeContent().setText(asset.getTypeName());
                            // related asset 필요
    
                        }
                    });

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
