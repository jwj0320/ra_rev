package gui.AC;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import data.ProcessedData;
import data.SecReq;
import data.Threat;
import gui.GridBagPanel;

public class Progress extends JDialog{

    private JProgressBar progressBar = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
    private JTextArea textArea = new JTextArea();
    private JButton confirmButton = new JButton("Confirm");
    private JButton cancelButton = new JButton("Cancel");
    private JDialog self = this;

    public Progress(AttackScenario upper){
        setTitle("In progress...");
        File imageFile = new File(this.getClass().getResource("").getPath(),"../../../../icon.png");
        try {
            setIconImage(ImageIO.read(imageFile));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        setModal(true);
        setSize(320, 240);
        setResizable(false);
        setLocationRelativeTo(null);
        
        GridBagPanel detailPane = new GridBagPanel();
        detailPane.addGBLComponent(progressBar, 0, 0);
        detailPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#005BAC")),"progress"));
        
        progressBar.setPreferredSize(new Dimension(280,20));
        
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        scrollPane.setPreferredSize(new Dimension(290,100));
        
        JLabel blankLabel1 = new JLabel();
        blankLabel1.setPreferredSize(new Dimension(10,10));
        JLabel blankLabel2 = new JLabel();
        blankLabel2.setPreferredSize(new Dimension(10,10));
        
        
        
        GridBagPanel pane = new GridBagPanel();
        add(pane);

        
        pane.addGBLComponent(detailPane, 0, 0,3,1);
        pane.addGBLComponent(blankLabel1, 0, 1);
        pane.addGBLComponent(scrollPane, 0, 2,3,1);
        pane.addGBLComponent(blankLabel2, 0,3);



        JLabel blankLabel3 = new JLabel();
        blankLabel3.setPreferredSize(new Dimension(130,10));

        pane.addGBLComponent(blankLabel3, 0, 4);

        confirmButton.setEnabled(false);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Threat> threatList=new ArrayList<Threat>();
                ProcessedData.setThreatList(threatList);
                Vector vector = ((DefaultTableModel)(upper.getDetailArea().getTable().getModel())).getDataVector();
                Threat threat;
                Object[] rowData;
                int index=1;
                SecReq sr;
                for (Object row:vector){
                    rowData=(Object[])(((Vector)row).toArray());
                    threat = new Threat(index++);
                    threat.setStep(Integer.parseInt((String)rowData[0]));
                    threat.setTactic((String)rowData[1]);
                    threat.setTechnique((String)rowData[2]);
                    threat.setMitigationList(new ArrayList<String>());
                    threat.setMitigationList(ProcessedData.ontologyFunc.LoadMitigationFromTech(threat.getTechnique()));
                    sr=new SecReq(SecReq.DT,threat.getName()+"-SRDT","");
                    threat.getSrList().add(sr);
                    ProcessedData.getSrList().add(sr);
                    sr=new SecReq(SecReq.PD,threat.getName()+"-SRPD","");
                    threat.getSrList().add(sr);
                    ProcessedData.getSrList().add(sr);
                    sr=new SecReq(SecReq.PV,threat.getName()+"-SRPV","");
                    threat.getSrList().add(sr);
                    ProcessedData.getSrList().add(sr);
                    sr=new SecReq(SecReq.RP,threat.getName()+"-SRRP","");
                    threat.getSrList().add(sr);
                    ProcessedData.getSrList().add(sr);

                    
                    threatList.add(threat);
                }



                upper.tabbedPane.setSelectedIndex(upper.tabbedPane.getSelectedIndex()+1);
                self.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.dispose();
            }
        });

        

        pane.addGBLComponent(confirmButton, 1, 4,0,0,"NONE",GridBagConstraints.LINE_END);
        pane.addGBLComponent(cancelButton, 2, 4,0,0,"NONE",GridBagConstraints.LINE_END);

        
        SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // TODO Auto-generated method stub
                setProgressValue("Starting to investigate the attack scenario.");
                setProgressValue("Acquiring Atack components.");
                setProgressValue("Identifying risk components.");
                setProgressValue("Analyzing assets.");
                setProgressValue("Completing to analyze the attack scenario.");
                return null;
            }
        };
        worker.execute();
        
        setVisible(true);
    }

    private void setProgressValue(String message){
        int value = 20;
        
        textArea.append(message+"\n");
        if (progressBar.getValue()<100){            
            for (int v=0;v<value;v++){
                try {
                    Thread.sleep(25);
                    progressBar.setValue(progressBar.getValue()+1);
                } catch (Exception e) {
                    //TODO: handle exception
                    System.out.println("progress error");
                    return;
                }
            }
        }

        if (progressBar.getValue() >= 100){
            confirmButton.setEnabled(true);
        }

        return;
    }
}
