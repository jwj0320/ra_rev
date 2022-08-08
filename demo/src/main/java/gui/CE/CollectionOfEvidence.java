package gui.CE;

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

import api.MakeCSV;
import data.Asset;
import data.Evidence;
import data.ProcessedData;
import data.SecReq;
import data.Threat;
import gui.GridBagPanel;

public class CollectionOfEvidence extends GridBagPanel {
    private JLabel label;
    private DetailArea detailArea;
    private GridBagPanel self = this;
    private SRInfo srInfo = new SRInfo();

    public CollectionOfEvidence(JTabbedPane tPane){
        super(tPane);
        label = new JLabel("Collection of Evidence");
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
                    String[] header=new String[]{"Asset ID", "Security Requirement ID", "Evidence"};
                    ArrayList<String[]> data=new ArrayList<String[]>();
                    Vector vector=((DefaultTableModel)detailArea.getAssetTable().getModel()).getDataVector();
                    String threatString=null;
                    for(Asset as: ProcessedData.getThreatAffectedAssets()){
                        for(Threat th:as.getThreatList()){
                            threatString=th.getName();
                            for(SecReq sr:th.getSrList()){
                                data.add(new String[]{as.getName(), threatString+"-SRDT",sr.getText(),sr.getEvidence().getId()});

                            }
                        }
                    }
                    
                    MakeCSV.toCSV(filePath, header, data);
                }
            }
        });

        addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                // TODO Auto-generated method stub
                DefaultTableModel assetTableModel=(DefaultTableModel) detailArea.getAssetTable().getModel();
                assetTableModel.setRowCount(0);
                for (Asset as: ProcessedData.getThreatAffectedAssets()){
                    assetTableModel.addRow(new String[]{
                        as.getName()
                    });
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

    private class DetailArea extends GridBagPanel{
        private JTable assetTable;
        public JTable getAssetTable() {
            return assetTable;
        }

        public void setAssetTable(JTable assetTable) {
            this.assetTable = assetTable;
        }

        private JScrollPane assetTabScPane;
        private JTable srTable;
        private JScrollPane srTabScPane;
        

        public DetailArea() {
            setPreferredSize(new Dimension(1060,580));
            assetTable=new JTable(new DefaultTableModel(new String[]{"Asset"},0));
            assetTabScPane=new JScrollPane(assetTable);
            assetTabScPane.setPreferredSize(new Dimension(150,160));
            addGBLComponent(assetTabScPane, 0, 0,1,1,0,0,"BOTH");

            srTable=new JTable(new DefaultTableModel(new String[]{"SR ID"},0));
            srTabScPane=new JScrollPane(srTable);
            srTabScPane.setPreferredSize(new Dimension(150,160));
            addGBLComponent(srTabScPane, 1, 0,1,1,0,0,"BOTH");
            

            AssetInput assetInput = new AssetInput();
            addGBLComponent(assetInput, 2, 0,2,1);

            addGBLComponent(srInfo, 3, 1,1,1);
            AssetInfo assetInfo = new AssetInfo();
            addGBLComponent(assetInfo, 0, 1,2,1,0,0,"NONE",GridBagConstraints.PAGE_START);

            assetTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            assetTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    DefaultTableModel srTableModel=(DefaultTableModel) srTable.getModel();
                    String assetName = (String)assetTable.getValueAt(assetTable.getSelectedRow(), 0);
                    Asset asset = ProcessedData.getAsset(assetName);
                    srTableModel.setRowCount(0);
                    for (Threat th: asset.getThreatList()){
                        srTableModel.addRow(new String[]{
                            String.format("%s-SRDT",th.getName())
                        });
                        srTableModel.addRow(new String[]{
                            String.format("%s-SRRP",th.getName())
                        });
                        srTableModel.addRow(new String[]{
                            String.format("%s-SRPD",th.getName())
                        });
                        srTableModel.addRow(new String[]{
                            String.format("%s-SRPV",th.getName())
                        });
                    }
                }
            });

            srTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    String srName = (String)srTable.getValueAt(srTable.getSelectedRow(), 0);
                    String[] splited= srName.split("-");
                    String srText=ProcessedData.getSr(srName).getText();
                    srInfo.getSrHeader().setText(srName);
                    srInfo.getSrText().setText(srText);
                    Evidence evidence = ProcessedData.getSr(srName).getEvidence();
                    assetInput.getAssetArea().getEvHeader().setText(
                        String.format("%s-EV",srName)
                    );
                    assetInput.getAssetArea().getInputArea().setText(evidence.getContent());
                }
            });
        }

        private class AssetInput extends GridBagPanel{
            private JLabel label;
            private JButton saveButton;
            private AssetArea assetArea;

            public AssetArea getAssetArea() {
                return assetArea;
            }

            public AssetInput(){
                setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(AjouBlue),"Evidence"));
                
                label=new JLabel("Write evidence details.");
                addGBLComponent(label, 0, 0,3,1,0,0,"NONE",GridBagConstraints.LINE_START);
                
                JLabel blankLabel = new JLabel("");
                blankLabel.setPreferredSize(new Dimension(600,10));
                addGBLComponent(blankLabel, 0, 1);

                saveButton=new JButton("Save");
                addGBLComponent(saveButton, 2, 1);
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String evidenceId=assetArea.getEvHeader().getText();
                        String[] splited=evidenceId.split("-");
                        Evidence evidence=ProcessedData.getSr(splited[0]+"-"+splited[1]).getEvidence();
                        evidence.setId(evidenceId);
                        evidence.setContent(assetArea.getInputArea().getText());
                    }
                });

                assetArea=new AssetArea();
                addGBLComponent(assetArea, 0, 2,3,1);
                
                
            }

            private class AssetArea extends GridBagPanel{
                private JLabel evHeader = makeHeader("");
                public JLabel getEvHeader() {
                    return evHeader;
                }

                public void setEvHeader(JLabel evHeader) {
                    this.evHeader = evHeader;
                }

                private JTextArea inputArea;
                
                public void setInputArea(JTextArea inputArea) {
                    this.inputArea = inputArea;
                }

                public JTextArea getInputArea() {
                    return inputArea;
                }

                public AssetArea(){
                    setBorder(BorderFactory.createLineBorder(AjouBlue));

                    evHeader.setPreferredSize(new Dimension(150,10));
                    addGBLComponent(evHeader, 0, 0,1,1,0,0,"BOTH");
                    
                    inputArea=new JTextArea();
                    PromptSupport.setPrompt("Evidence details..", inputArea);
                    inputArea.setPreferredSize(new Dimension(450,200));
                    addGBLComponent(inputArea, 1, 0);
                    
                }
            }
        }
    }
    private class AssetInfo extends GridBagPanel{
         // private JLabel label = new JLabel("Asset INFO");

         public AssetInfo(){
            // setPreferredSize(new Dimension(300,300));
            // label.setBackground(AjouBlue);
            // label.setForeground(Color.white);
            // label.setOpaque(true);
            // label.setPreferredSize(new Dimension(280,280));
            // addGBLComponent(label, 0, 0,0,0,0,0,"BOTH");

            setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(AjouBlue),"Asset Info"));
            JLabel IDHeader=makeHeader("Asset ID");
            JLabel nameHeader=makeHeader("Name");
            JLabel descHeader=makeHeader("Description");
            JLabel typeHeader=makeHeader("Type");
            JLabel raHeader=makeHeader("Related Assets");
            
            System.out.println(raHeader.getPreferredSize());

            addGBLComponent(IDHeader, 0, 0,1,1,"BOTH");
            addGBLComponent(nameHeader, 0, 1,1,1,"BOTH");
            addGBLComponent(typeHeader,0, 2,1,1,"BOTH");
            addGBLComponent(descHeader, 0, 3,1,1,"BOTH");
            addGBLComponent(raHeader, 0, 4,1,1,"BOTH");

            JLabel IDContent=makeContent("");
            JLabel nameContent=makeContent("");
            JLabel descContent=makeContent("");
            JLabel typeContent=makeContent("");
            JLabel raContent=makeContent("");

            System.out.println(raHeader.getPreferredSize());
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


    private class SRInfo extends GridBagPanel{
        private JLabel srHeader = makeHeader("");
        public JLabel getSrHeader() {
            return srHeader;
        }
        public void setSrHeader(JLabel srHeader) {
            this.srHeader = srHeader;
        }
        private JTextArea srText= new JTextArea();
        public JTextArea getSrText() {
            return srText;
        }
        public void setSrText(JTextArea srText) {
            this.srText = srText;
        }
        private JScrollPane srSc=new JScrollPane(srText);
        public SRInfo(){
            srSc.setPreferredSize(new Dimension(300,200));
            addGBLComponent(srHeader, 0, 0,1,1,0,0,"BOTH");
            addGBLComponent(srSc, 0, 1);

            

        }
    }
}
