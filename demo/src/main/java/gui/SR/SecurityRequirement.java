package gui.SR;

import java.awt.Dimension;
import java.awt.event.ActionListener;
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
        label = new JLabel("Elicitation of Security Requirements");
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

            ThreatInfo threatInfo = new ThreatInfo();
            addGBLComponent(threatInfo, 0, 1,2,1);

            
            SRInput srInput = new SRInput();
            addGBLComponent(srInput, 1, 0,3,1);
            
            threatTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    // TODO Auto-generated method stub
                    srInput.getSrArea().getInputArea().setText("");

                    String selectedValue=(String)threatTable.getModel().getValueAt(threatTable.getSelectedRow(), 0);
                    DefaultTableModel model = (DefaultTableModel)srInput.getSrArea().getSrTable().getModel();
                    model.setRowCount(0);
                    model.addRow(new String[]{selectedValue+"-SRDT"});
                    model.addRow(new String[]{selectedValue+"-SRRP"});
                    model.addRow(new String[]{selectedValue+"-SRPD"});
                    model.addRow(new String[]{selectedValue+"-SRPV"});

                    srInput.getSrArea().getSrTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent e) {
                            String threatName = (String)threatTable.getValueAt(threatTable.getSelectedRow(),0);
                            int index = srInput.getSrArea().getSrTable().getSelectedRow();
                            SecReq secReq =ProcessedData.getThreat(threatName).getSecReq();
                            switch (index) {
                                case 0:
                                    srInput.getSrArea().getInputArea().setText(secReq.getDT());
                                    break;
                                case 1:
                                    srInput.getSrArea().getInputArea().setText(secReq.getRP());
                                    break;
                                case 2:
                                    srInput.getSrArea().getInputArea().setText(secReq.getPD());
                                    break;
                                case 3:
                                    srInput.getSrArea().getInputArea().setText(secReq.getPV());
                                    break;
                                default:
                                    break;
                            }
                            
                        }
                    });
                }
            });
            
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
                        String threatName = (String)threatTable.getValueAt(threatTable.getSelectedRow(), 0);
                        int index = srArea.getSrTable().getSelectedRow();
                        String text=srArea.getInputArea().getText();
                        SecReq secReq = ProcessedData.getThreat(threatName).getSecReq();
                        
                        switch (index) {
                            case 0:
                                secReq.setDT(text);
                                break;
                            case 1:
                                secReq.setRP(text);
                                break;
                            case 2:
                                secReq.setPD(text);
                                break;
                            case 3:
                                secReq.setPV(text);
                                break;
                            default:
                                break;
                        }
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
