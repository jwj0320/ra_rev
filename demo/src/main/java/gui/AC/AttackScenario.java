package gui.AC;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

import data.ProcessedData;
import data.SecReq;
import data.Threat;
import gui.GridBagPanel;

public class AttackScenario extends GridBagPanel{
    private JLabel label;
    private DetailArea detailArea;
    private AttackScenario self = this;
    
    public AttackScenario(JTabbedPane tPane){
        super(tPane);
        
        // setPreferredSize(new Dimension(1060,620));

        addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {

                // TODO Auto-generated method stub
                System.out.println("as added");

                for(Threat th:ProcessedData.getThreatList()){
                    ((DefaultTableModel)detailArea.getTable().getModel()).addRow(new String[]{
                        th.getStep()+"",
                        th.getTactic(),
                        th.getTactic()
                    });
                }
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
                // TODO Auto-generated method stub
                System.out.println("as removed");

                // ArrayList<Threat> threatList=new ArrayList<Threat>();
                // ProcessedData.setThreatList(threatList);
                ArrayList<Threat> threatList=ProcessedData.getThreatList();
                Vector vector = ((DefaultTableModel)(detailArea.getTable().getModel())).getDataVector();
                Threat threat;
                Object[] rowData;
                SecReq sr;
                for (Object row:vector){
                    rowData=(Object[])(((Vector)row).toArray());
                    threat = new Threat();
                    threat.setStep(Integer.parseInt((String)rowData[0]));
                    threat.setTactic((String)rowData[1]);
                    threat.setTechnique((String)rowData[2]);
                    threat.setMitigationList(new ArrayList<String>());
                    threat.setMitigationList(ProcessedData.ontologyFunc.LoadMitigationFromTech(threat.getTechnique()));
                    sr=new SecReq(SecReq.DT,threat.getId()+"-SRDT","");
                    sr.setThreat(threat);
                    threat.getSrList().add(sr);
                    ProcessedData.getSrList().add(sr);
                    sr=new SecReq(SecReq.PD,threat.getId()+"-SRPD","");
                    sr.setThreat(threat);
                    threat.getSrList().add(sr);
                    ProcessedData.getSrList().add(sr);
                    sr=new SecReq(SecReq.PV,threat.getId()+"-SRPV","");
                    sr.setThreat(threat);
                    threat.getSrList().add(sr);
                    ProcessedData.getSrList().add(sr);
                    sr=new SecReq(SecReq.RP,threat.getId()+"-SRRP","");
                    sr.setThreat(threat);
                    threat.getSrList().add(sr);
                    ProcessedData.getSrList().add(sr);

                    
                    threatList.add(threat);
                }

            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
                // TODO Auto-generated method stub
                System.out.println("as moved");

            }
        });
        
        init();
        
    }

    private void init(){
        label=new JLabel("Composition of Attack Scenario.");
        addGBLComponent(label, 0, 0);

        detailArea = new DetailArea();

        addGBLComponent(detailArea, 0, 1,2,1);
        
        JButton button = new JButton("Next");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Progress progress = new Progress(self);
            }
        });
        JLabel blankLabel1 = new JLabel();
        blankLabel1.setPreferredSize(new Dimension(10,10));
        addGBLComponent(blankLabel1, 0, 2);
        addGBLComponent(button, 1, 3,0,0,"NONE",GridBagConstraints.LINE_END);

        
    }
    public DetailArea getDetailArea(){
        return detailArea;
    }

    public class DetailArea extends GridBagPanel{
        private JTable table;
        private JScrollPane tableScPane;
        private JButton button=new JButton("Make attack scenario");
    
        public DetailArea(){
            setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#005BAC")),"Attack Scenario"));
            setPreferredSize(new Dimension(1060,580));

            table = new JTable(new DefaultTableModel(new String[]{"Step","Tactic","Technique"},0));
            table.getColumnModel().getColumn(0).setPreferredWidth(50);
            table.getColumnModel().getColumn(1).setPreferredWidth(300);
            table.getColumnModel().getColumn(2).setPreferredWidth(700);
            
            tableScPane = new JScrollPane(table);
            tableScPane.setPreferredSize(new Dimension(1050,500));
            addGBLComponent(tableScPane, 0, 0);
            JLabel blankLabel1 = new JLabel();
            blankLabel1.setPreferredSize(new Dimension(10,10));
            addGBLComponent(blankLabel1, 0, 1);
            addGBLComponent(button, 0, 2,0,0,"BOTH",GridBagConstraints.LINE_END);
    
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MakeScenario makeScenario = new MakeScenario(table);
                }
            });
    
        }
        public JTable getTable(){
            return table;
        }

    
    }
}

