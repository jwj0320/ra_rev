package gui.SR;

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
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Desktop;
import java.net.URI;

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

import org.apache.commons.lang3.ArrayUtils;
import org.apache.jena.sparql.function.library.leviathan.sec;
import org.jdesktop.swingx.prompt.PromptSupport;

import api.CSVFunc;
import data.ProcessedData;
import data.SecReq;
import data.Threat;
import gui.GridBagPanel;
import gui.AC.AttackScenario;

public class SecurityRequirement extends GridBagPanel {
    private JLabel label;
    private DetailArea detailArea;
    private GridBagPanel self = this;
    // private 

    public SecurityRequirement(JTabbedPane tPane) {
        super(tPane);
        init();

        addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                tPane.setEnabledAt(1, true);
                // TODO Auto-generated method stub
                System.out.println("added");
                System.out.println(this.getClass() +" size "+getPreferredSize());
                DefaultTableModel threatTableModel=(DefaultTableModel)detailArea.getThreatTable().getModel();
                
                detailArea.getThreatTable().clearSelection();
                threatTableModel.setRowCount(0);
                for(Threat th:ProcessedData.getThreatList()){
                    threatTableModel.addRow(new String[]{th.getId()});
                }
                // detailArea.getThreatTable().setRowSelectionInterval(0, 0);
                // 리스너가 왜 하나만 작동할까????
                DefaultTableModel model = (DefaultTableModel)detailArea.getSrInput().getSrArea().getSrTable().getModel();
                model.setRowCount(0);
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
        label = new JLabel("Elicitation of Security Requirements");
        addGBLComponent(label, 0, 0,0,0,"NONE",GridBagConstraints.LAST_LINE_START);

        detailArea = new DetailArea();

        addGBLComponent(detailArea, 0, 1, 4, 1);

        JButton button = new JButton("Next");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex()+1);
            }
        });
        JLabel blankLabel1 = new JLabel();
        blankLabel1.setPreferredSize(new Dimension(10, 10));
        addGBLComponent(blankLabel1, 0, 2,1,1,0.1,0);
        addGBLComponent(button, 3, 3, 0, 0, "NONE", GridBagConstraints.LINE_END);

        JButton downLoadButton=new JButton("Download");
        addGBLComponent(downLoadButton, 2, 3,0,0,"NONE",GridBagConstraints.LINE_END);
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
                    String[] header=new String[]{"Threat ID", "Security Requirement ID", "SR Type", "SR Content"};
                    ArrayList<String[]> data=new ArrayList<String[]>();
                    Vector vector=((DefaultTableModel)detailArea.getThreatTable().getModel()).getDataVector();
                    String threatString=null;
                    Threat threat=null;
                    ArrayList<SecReq> srList=null;
                    for(Object o:vector){
                        threatString=(String)(((Vector)o).toArray(new String[0]))[0];
                        threat=ProcessedData.getThreat(threatString);
                        srList=threat.getSrList();
                        for(SecReq sr:srList){
                            data.add(new String[]{threatString, sr.getId(),sr.getText()});
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
        public JTable getThreatTable() {
            return threatTable;
        }


        private JScrollPane threatTabScPane;
        private SRInput srInput = new SRInput();

        public SRInput getSrInput() {
            return srInput;
        }


        public DetailArea() {
            setPreferredSize(new Dimension(1060,580));

            threatTable=new JTable(new DefaultTableModel(new String[]{"Threat ID"},0));
            threatTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            threatTabScPane=new JScrollPane(threatTable);
            threatTabScPane.setPreferredSize(new Dimension(150,160));
            addGBLComponent(threatTabScPane, 0, 0,1,1,0,0,"BOTH");

            ThreatInfo threatInfo = new ThreatInfo();
            addGBLComponent(threatInfo, 0, 1,2,1);

            
            
            addGBLComponent(srInput, 1, 0,3,1);
            threatTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    // TODO Auto-generated method stub
                    srInput.getSrArea().getInputArea().setText("");
                    DefaultTableModel model = (DefaultTableModel)srInput.getSrArea().getSrTable().getModel();
                    model.setRowCount(0);

                    if(threatTable.getSelectedRow()==-1){
                        return;
                    }

                    String selectedValue=(String)threatTable.getModel().getValueAt(threatTable.getSelectedRow(), 0);
                    // model.addRow(new String[]{selectedValue+"-SRDT"});
                    // model.addRow(new String[]{selectedValue+"-SRRP"});
                    // model.addRow(new String[]{selectedValue+"-SRPD"});
                    // model.addRow(new String[]{selectedValue+"-SRPV"});
                    for(SecReq sr:ProcessedData.getThreat(selectedValue).getSrList()){
                        model.addRow(new String[]{sr.getId()});
                    }

                }
            });
            srInput.getSrArea().getSrTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(threatTable.getSelectedRow()==-1||srInput.getSrArea().getSrTable().getSelectedRow()==-1){
                        srInput.getSrArea().getInputArea().setText("");
                        return;
                    }

                    String threatName = (String)threatTable.getValueAt(threatTable.getSelectedRow(),0);
                    int index = srInput.getSrArea().getSrTable().getSelectedRow();
                    SecReq sr =ProcessedData.getSr((String)srInput.getSrArea().getSrTable().getValueAt(index, 0));
                    srInput.getSrArea().getInputArea().setText(sr.getText());

                    
                }
            });
            System.out.println("sr: "+srInput.getPreferredSize());
            
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
                CAPECTabSc.setPreferredSize(new Dimension(900,55));
                CWETabSc.setPreferredSize(new Dimension(900,55));
                CVETabSc.setPreferredSize(new Dimension(900,55));
                mitiTabSc.setPreferredSize(new Dimension(900,55));
                addGBLComponent(techContent, 1, 0,1,1,0,0,"BOTH");
                addGBLComponent(tacticContent, 1, 1,1,1,0,0,"BOTH");
                addGBLComponent(CAPECTabSc, 1, 2,1,1,0,0,"BOTH");
                addGBLComponent(CWETabSc, 1, 3,1,1,0,0,"BOTH");
                addGBLComponent(CVETabSc, 1, 4,1,1,0,0,"BOTH");
                addGBLComponent(mitiTabSc, 1, 5,1,1,0,0,"BOTH");

                JTable CAPECTable=(JTable)(CAPECTabSc.getViewport().getView());
                CAPECTable.addMouseListener(new MouseAdapter(){
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(e.getClickCount()==2){
                            System.out.println("Mouse clicked");
                            String text=(String)CAPECTable.getValueAt(CAPECTable.getSelectedRow(), 0);
                            text=text.split("-")[1].split(":")[0];
                            openBrowser("https://capec.mitre.org/data/definitions/"+text+".html");
                        }
                    }
                });

                JTable CWETable=(JTable)(CWETabSc.getViewport().getView());
                CWETable.addMouseListener(new MouseAdapter(){
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(e.getClickCount()==2){
                            System.out.println("Mouse clicked");
                            String text=(String)CWETable.getValueAt(CWETable.getSelectedRow(), 0);
                            text=text.split("-")[1].split(":")[0];
                            openBrowser("https://cwe.mitre.org/data/definitions/"+text+".html");
                        }
                    }
                });

                JTable CVETable=(JTable)(CVETabSc.getViewport().getView());
                CVETable.addMouseListener(new MouseAdapter(){
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(e.getClickCount()==2){
                            System.out.println("Mouse clicked");
                            String text=(String)CVETable.getValueAt(CVETable.getSelectedRow(), 0);
                            openBrowser("https://cve.mitre.org/cgi-bin/cvename.cgi?name="+text);
                        }
                    }
                });


                threatTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        DefaultTableModel mitiModel =(DefaultTableModel)(((JTable)(mitiTabSc.getViewport().getView())).getModel());
                        DefaultTableModel CAPECModel =(DefaultTableModel)(((JTable)(CAPECTabSc.getViewport().getView())).getModel());
                        DefaultTableModel CWEModel=(DefaultTableModel)(((JTable)(CWETabSc.getViewport().getView())).getModel());
                        DefaultTableModel CVEModel=(DefaultTableModel)(((JTable)(CVETabSc.getViewport().getView())).getModel());
                        mitiModel.setRowCount(0);
                        CAPECModel.setRowCount(0);
                        CWEModel.setRowCount(0);
                        CVEModel.setRowCount(0);

                        if(threatTable.getSelectedRow()==-1){
                            techContent.setText("");
                            tacticContent.setText("");
                            return;
                        }

                        String threatID = (String)threatTable.getValueAt(threatTable.getSelectedRow(), 0);
                        Threat threat = ProcessedData.getThreat(threatID);
                        techContent.setText(threat.getTechnique());
                        tacticContent.setText(threat.getTactic());

                        for(String CAPEC:threat.getCAPEC()){
                            CAPECModel.addRow(
                                new String[]{ CAPEC }
                            );
                        }
                        for(String CWE:threat.getCWE()){
                            CWEModel.addRow(
                                new String[]{ CWE }
                            );
                        }
                        for(String CVE:threat.getCVE()){
                            CVEModel.addRow(
                                new String[]{ CVE }
                            );
                        }
                        // ((DefaultTableModel)(((JTable)(CAPECTabSc.getViewport().getView())).getModel())).addRow(rowData);
                        for (String miti:threat.getMitigationList()){
                            System.out.println(miti);
                            mitiModel.addRow(new String[]{miti});

                        }

                    }
                });

            }

            private void openBrowser(String URI){
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        Desktop.getDesktop().browse(new URI(URI));
                        
                    } catch (Exception exc) {
                        // TODO: handle exception
                    }
                }
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
                
                label=new JLabel("Write security requirements.");
                addGBLComponent(label, 0, 0,3,1,0,0,"NONE",GridBagConstraints.LINE_START);
                
                JLabel blankLabel = new JLabel("");
                blankLabel.setPreferredSize(new Dimension(600,10));
                addGBLComponent(blankLabel, 0, 1);

                saveButton=new JButton("Save");
                addGBLComponent(saveButton, 2, 1);
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String id = (String)srArea.getSrTable().getValueAt(srArea.getSrTable().getSelectedRow(), 0);
                        String text=srArea.getInputArea().getText();
                        SecReq secReq = ProcessedData.getSr(id);
                        secReq.setText(text);
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

                    srTable=new JTable(new DefaultTableModel(new String[]{"SR ID"},0));
                    // ((DefaultTableModel)srTable.getModel()).addRow(new String[]{""});
                    // ((DefaultTableModel)srTable.getModel()).addRow(new String[]{""});
                    // ((DefaultTableModel)srTable.getModel()).addRow(new String[]{""});
                    // ((DefaultTableModel)srTable.getModel()).addRow(new String[]{""});
                    srTable.setTableHeader(null);
                    srTable.setShowGrid(false);
                    srTable.setBorder(BorderFactory.createEmptyBorder());
                    srTable.setBackground(AjouBlue);
                    srTable.setForeground(Color.white);
                    srTable.setSelectionBackground(Color.white);
                    srTable.setSelectionForeground(AjouBlue);
                    srTable.setRowHeight(50);
                    srTable.setDefaultRenderer(String.class, new DefaultTableCellRenderer(){

                        @Override
                        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                boolean hasFocus, int row, int column) {
                            
                            setBorder(noFocusBorder);
                            return super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
                        }
                    });
                    srTable.setFocusable(false);
                    srTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                    srTabSc=new JScrollPane(srTable);
                    srTabSc.setPreferredSize(new Dimension(200,200));
                    srTabSc.setBorder(BorderFactory.createEmptyBorder());
                    srTabSc.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
                    addGBLComponent(srTabSc, 0, 0);
                    
                    inputArea=new JTextArea();
                    PromptSupport.setPrompt("Security requirement details..", inputArea);
                    inputArea.setPreferredSize(new Dimension(600,200));
                    addGBLComponent(inputArea, 1, 0);
                    
                }
            }
        }

    }
}
