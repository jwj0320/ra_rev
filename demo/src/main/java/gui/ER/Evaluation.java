package gui.ER;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JTextField;
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
import data.Evidence;
import data.ProcessedData;
import data.SecReq;
import data.Threat;
import gui.GridBagPanel;

public class Evaluation extends GridBagPanel{
    private JTable setTable =new JTable(
        new DefaultTableModel(
            new String[]{"Asset", "SR", "Evidence", "Evaluation"},0
        ){
            public boolean isCellEditable(int row, int column) {
                return false;
            };
    });
    public JTable getSetTable() {
        return setTable;
    }

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
                model.setRowCount(0);
                for (Asset asset:assetList){
                    for(Evidence ev:asset.getEvidenceList()){
                        model.addRow(new String[]{
                            asset.getName(),
                            ev.getSr().getId(),
                            ev.getId(),
                            ev.isEvaluated() ? ""+ev.getScore() : ""
                        });
                    }
                }
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
                // TODO Auto-generated method stub
                DefaultTableModel model=(DefaultTableModel) setTable.getModel();
                Vector vector=model.getDataVector();
                Object[] data;
                String assetId=null;
                String evId=null;
                double score=0.0;
                // for(Object o:vector){
                //     data=((Vector)o).toArray();
                //     assetId=(String)data[0];
                //     evId=(String)data[2];
                //     score=Double.parseDouble((String)data[3]);
                //     ProcessedData.getAsset(assetId).getEvidence(evId).setScore(score);

                // }
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

            // editButton.setEnabled(false);
            addGBLComponent(editButton, 1, 0,1,1,0,0,"NONE",GridBagConstraints.LINE_END);

            setTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            setTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(!editButton.isEnabled())
                        editButton.setEnabled(true);
                }
            });

            setTable.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount()==2){
                        editButton.doClick();
                    }
                }
            });

            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String assetName=(String)setTable.getValueAt(setTable.getSelectedRow(), 0);
                    String srName=(String)setTable.getValueAt(setTable.getSelectedRow(), 1);
                    String evName=(String)setTable.getValueAt(setTable.getSelectedRow(), 2);

                    JDialog dialog = new JDialog();
                    dialog.setSize(new Dimension(800,600));
                    dialog.setResizable(false);
                    dialog.setLocationRelativeTo(null);
                    
                    GridBagPanel pane = new GridBagPanel();
                    dialog.add(pane);

                    

                    // ////////////////////////
                    JLabel assetHeader=makeHeader("Asset");
                    JTextArea assetText=new JTextArea();
                    JScrollPane assetTextSc= new JScrollPane(assetText);
                    assetTextSc.setPreferredSize(new Dimension(250,300));
                    pane.addGBLComponent(assetHeader, 0, 0,1,1,"HORIZONTAL");
                    pane.addGBLComponent(assetTextSc, 0, 1);

                    // assetText.setText(ProcessedData.getSr(srName).getText());
                    assetText.setEditable(false);

                    JLabel srHeader=makeHeader("Security Requirement");
                    JTextArea srText=new JTextArea();
                    JScrollPane srTextSc= new JScrollPane(srText);
                    srTextSc.setPreferredSize(new Dimension(250,300));
                    pane.addGBLComponent(srHeader, 1, 0,1,1,"HORIZONTAL");
                    pane.addGBLComponent(srTextSc, 1, 1);

                    srText.setText(ProcessedData.getSr(srName).getText());
                    srText.setEditable(false);

                    JLabel evHeader=makeHeader("Evidence");
                    JTextArea evText=new JTextArea();
                    JScrollPane evTextSc= new JScrollPane(evText);
                    evTextSc.setPreferredSize(new Dimension(250,300));
                    pane.addGBLComponent(evHeader, 2, 0,2,1,"HORIZONTAL");
                    pane.addGBLComponent(evTextSc, 2, 1,2,1);

                    evText.setText(ProcessedData.getAsset(assetName).getEvidence(evName).getContent());
                    evText.setEditable(false);

                    JLabel blankLabel = new JLabel();
                    blankLabel.setPreferredSize(new Dimension(10,10));
                    pane.addGBLComponent(blankLabel, 3, 2);

                    JLabel scoreHeader=makeHeader("Score");
                    JTextField scoreText=new JTextField();

                    scoreHeader.setPreferredSize(new Dimension(50,18));
                    scoreText.setPreferredSize(new Dimension(50,32));
                    pane.addGBLComponent(scoreHeader, 3, 3,1,1,0,0,"NONE",GridBagConstraints.LINE_END);
                    pane.addGBLComponent(scoreText, 3, 4,1,1,0,0,"NONE",GridBagConstraints.LINE_END);
                    
                    JLabel blankLabel1 = new JLabel();
                    blankLabel1.setPreferredSize(new Dimension(10,10));
                    pane.addGBLComponent(blankLabel1, 3, 5);

                    JButton applyButton = new JButton("Apply");
                    pane.addGBLComponent(applyButton, 3, 6,1,1,0,0,"NONE",GridBagConstraints.LINE_END);

                    applyButton.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            DefaultTableModel model = (DefaultTableModel)(setTable.getModel());
                            double score=Double.parseDouble(scoreText.getText());
                            ProcessedData.getAsset(assetName).getEvidence(evName).setScore(score);
                            model.setValueAt(scoreText.getText(), setTable.getSelectedRow(), 3);
                            
                            dialog.dispose();
                        }
                    });

                    dialog.addWindowListener(new WindowAdapter(){
                        @Override
                        public void windowOpened(WindowEvent e) {
                            // TODO Auto-generated method stub
                            scoreText.requestFocus();
                        }
                    });

                    scoreText.addKeyListener(new KeyListener(){
                        @Override
                        public void keyPressed(KeyEvent e) {
                            // TODO Auto-generated method stub
                            if(e.getKeyCode()==KeyEvent.VK_ENTER){
                                applyButton.doClick();
                            }
                        }
                        @Override
                        public void keyReleased(KeyEvent e) {
                            // TODO Auto-generated method stub
                            
                        }
                        @Override
                        public void keyTyped(KeyEvent e) {
                            // TODO Auto-generated method stub
                            
                        }
                    });

                    

                    dialog.setVisible(true);

                }
            });
            

        }


      
    }
}
