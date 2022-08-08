package gui.AC;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel; // 패널크기 고정으로 지정하자
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.jena.base.Sys;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;

import com.github.jsonldjava.utils.Obj;

import gui.GridBagPanel;
import gui.AC.Card.OrgCard;
import gui.AC.Card.TacticCard;
import gui.AC.Card.TechCard;
import api.OntologyFunc;
import data.Asset;
import data.ProcessedData;

public class MakeScenario extends JDialog {
    private GridBagPanel dialogPane = new GridBagPanel();
    private OntologyFunc ontologyFunc = ProcessedData.ontologyFunc;
    private JButton nextButton = new JButton("Next");
    private JPanel cardPanel = new JPanel();
    private JTable scenarioTable;
    
    public JTable getScenarioTable() {
        return scenarioTable;
    }


    public JButton getNextButton() {
        return nextButton;
    }


    public JPanel getCardPanel() {
        return cardPanel;
    }




    public MakeScenario(JTable scenarioTable) {
        this.scenarioTable=scenarioTable;
        setTitle("In progress...");
        File imageFile = new File(this.getClass().getResource("").getPath(), "../../../../icon.png");
        try {
            setIconImage(ImageIO.read(imageFile));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        setModal(true);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        // changePane(new Org(), new Org());
        add(dialogPane);

        cardPanel.setLayout(new CardLayout());
        cardPanel.add(new OrgCard(this));
        cardPanel.add(new TacticCard(this));

        cardPanel.add(new TechCard(this,1));
        cardPanel.add(new TechCard(this,2));
        cardPanel.add(new TechCard(this,3));
        cardPanel.add(new TechCard(this,4));

        dialogPane.addGBLComponent(cardPanel, 0, 0, 2, 1, 0, 8);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //마지막 카드 판별 필요
                ((CardLayout) (cardPanel.getLayout())).next(cardPanel);
                // nextButton.setEnabled(false); //  알고리즘 생각해 보자
            }
        });

        dialogPane.addGBLComponent(nextButton, 1, 1, 1, 1, 0, 2, "NONE", GridBagConstraints.LINE_END);

        setVisible(true);
    }



    private class CompletePanel extends GridBagPanel{
        public CompletePanel(){
            JLabel label = new JLabel("Completed!");
            // for (ActionListener al: nextButton.getActionListeners()){
            //     nextButton.removeActionListener(al);
            // }
            // nextButton.addActionListener(new ActionListener(){
            //     @Override
            //     public void actionPerformed(ActionEvent e) {
            //         // TODO Auto-generated method stub
            //         dispose();
            //     }
            // });
        }
    }
}
