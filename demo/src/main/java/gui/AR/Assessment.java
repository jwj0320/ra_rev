package gui.AR;

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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import api.RadarChartData;
import api.RadarChart;
import data.Asset;
import data.Evidence;
import data.ProcessedData;
import data.Threat;
import gui.GridBagPanel;
import gui.ER.Evaluation;

public class Assessment extends GridBagPanel {
    private JLabel label;
    private DetailArea detailArea;
    private GridBagPanel self = this;
        
    public Assessment(JTabbedPane tPane){
        super(tPane);
        init();
        
        addAncestorListener(new AncestorListener(){

            @Override
            public void ancestorAdded(AncestorEvent event) {
                // TODO Auto-generated method stub
                RadarChart radarChart = new RadarChart();
                ArrayList<RadarChartData> tacticDataList=new ArrayList<RadarChartData>();
                ArrayList<RadarChartData> srDataList=new ArrayList<RadarChartData>();


                // for(Threat th:ProcessedData.getThreatList()){
                //     tacticDataList.add(new ChartData(th.getStep()+"", th.getTactic(), th.getScore()));
                // }
                for(Asset as:ProcessedData.getAssetList()){
                    for(Evidence ev:as.getEvidenceList()){
                        tacticDataList.add(new RadarChartData(
                            ev.getSr().getThreat().getStep()+"",
                            ev.getSr().getThreat().getTactic() ,
                            ev.getScore()
                        ));
                        srDataList.add(new RadarChartData(
                            as.getName(),
                            ev.getSr().getType(),
                            ev.getScore()
                        ));
                    }
                }

                CategoryDataset tacticDataset=radarChart.makeDataset(tacticDataList);
                JFreeChart tacticChart=radarChart.createChart("Tactic",tacticDataset);
                ChartPanel tacticChartPanel=new ChartPanel(tacticChart);
                tacticChartPanel.setPreferredSize(new Dimension(250,250));
                
                detailArea.getTacticPart().addGBLComponent(tacticChartPanel, 0, 0);


                CategoryDataset srDataset=radarChart.makeDataset(srDataList);
                JFreeChart srChart=radarChart.createChart("Security Requirement",srDataset);
                ChartPanel srChartPanel=new ChartPanel(srChart);
                srChartPanel.setPreferredSize(new Dimension(250,250));

                detailArea.getSrPart().addGBLComponent(srChartPanel, 0, 0);

                
                DefaultPieDataset assetDataset= new DefaultPieDataset();
                
                for(Asset as:ProcessedData.getAssetList()){
                
                }
                
                
                revalidate();
                
                
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

        // JButton button = new JButton("Next");
        // button.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex()+1);
        //     }
        // });
        // JLabel blankLabel1 = new JLabel();
        // blankLabel1.setPreferredSize(new Dimension(10, 10));
        // addGBLComponent(blankLabel1, 0, 2);
        // addGBLComponent(button, 1, 3, 0, 0, "NONE", GridBagConstraints.LINE_END);

        
    }
    private class DetailArea extends GridBagPanel {
        private TacticPart tacticPart=new TacticPart();
        private SRPart srPart=new SRPart();
        public SRPart getSrPart() {
            return srPart;
        }
        public void setSrPart(SRPart srPart) {
            this.srPart = srPart;
        }
        public TacticPart getTacticPart() {
            return tacticPart;
        }
        public DetailArea(){
            setPreferredSize(new Dimension(1060,580));
            addGBLComponent(tacticPart, 0, 0);
            addGBLComponent(srPart, 1, 0);

        }
        private class TacticPart extends GridBagPanel{
            public TacticPart(){
                setPreferredSize(new Dimension(300,300));
                
            }
        }
        private class SRPart extends GridBagPanel{
            public SRPart(){
                setPreferredSize(new Dimension(300,300));


            }
        }
    }
}
