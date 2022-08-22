package gui;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import api.OntologyFunc;
import data.ProcessedData;
import gui.AC.AttackScenario;
import gui.AR.Assessment;
import gui.CE.CollectionOfEvidence;
import gui.ER.Evaluation;
import gui.SR.SecurityRequirement;
import gui.TA.ThreatAffectedAsset;



public class MainFrame extends JFrame{
    OntologyFunc ontologyFunc = ProcessedData.ontologyFunc;
    Container contentPane;

    public MainFrame(){
        super("Threat-driven Risk Assessment");
        File imageFile = new File(this.getClass().getResource("").getPath(),"../../../icon.png");
        try {
            setIconImage(ImageIO.read(imageFile));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setSize(1280, 720);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JTabbedPane tPane = new JTabbedPane(JTabbedPane.LEFT){
            public Color getForegroundAt(int index) {
                if (getSelectedIndex() == index)
                    return Color.decode("#005BAC");
                return Color.WHITE;
            }

        };
        // tPane.selected
        tPane.setBackground(Color.decode("#005BAC"));
        // tPane.setForeground(Color.WHITE);
        
        tPane.setOpaque(true);
        OffsetTabbedPaneUI ui = new OffsetTabbedPaneUI();
        ui.setMinHeight(100);
        ui.setMinWidth(100);
        // ui.setLeadingOffset(10);
        // ui.setTrailingOffset(10);

        // <html>  <br/>  </html>
        
        UIManager.put("TabbedPane.selected",Color.WHITE);
        
        Insets insets = UIManager.getInsets("TabbedPane.contentBorderInsets");
        insets.top = -1;
        insets.bottom= -1;
        insets.left=-1;
        insets.right = -1;

        UIManager.put("TabbedPane.contentBorderInsets", insets);

        tPane.setUI(ui);

        tPane.addTab("Attack Scenario", new AttackScenario(tPane));

        // JLabel scLabel = new JLabel("Attack Scenario");
        // try {
        //     Image image = ImageIO.read(new File(this.getClass().getResource("").getPath(),"../../../img/scenario.png"));
        //     image.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        //     ImageIcon icon = new ImageIcon(image);
            
        //     scLabel.setIcon(icon);
            
        // } catch (IOException e) {
        //     System.out.println("error");
        //     //TODO: handle exception
        // }
        // tPane.setTabComponentAt(0, scLabel);
        
        tPane.addTab("Security Requirements", new SecurityRequirement(tPane));
        tPane.addTab("Threat-affected Assets", new ThreatAffectedAsset(tPane));
        tPane.addTab("Collection of Evidence", new CollectionOfEvidence(tPane));
        tPane.addTab("Risk Evaluation", new Evaluation(tPane));
        tPane.addTab("Risk Assessment", new Assessment(tPane));
        
        tPane.validate();
        
        add(tPane);


        // contentPane = this.getContentPane();
        // TabbedPaneInfo tPane = new TabbedPaneInfo();
        // tPane.setBorder(BorderFactory.createEmptyBorder(10,20,20,20));
        // add(tPane);

        // OrgPanel orgPanel = new OrgPanel(tPane);
        // tPane.addTab("Organizaion", orgPanel);

        // AtkPanel atkPanel = new AtkPanel(tPane);
        // tPane.addTab("Attack Component", atkPanel);

        // orgPanel.disableOtherTabs();


        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newWindowItem=new JMenuItem("Open New window");
        JMenuItem saveDataItem= new JMenuItem("Save data");
        JMenuItem loadDataItem=new JMenuItem("Load data");

        newWindowItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainFrame();
                // 하나 종료할 때 같이 종료되는 현상
            }
        });

        saveDataItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser=new JFileChooser();
                FileNameExtensionFilter filter=new FileNameExtensionFilter(".json", "json");
                chooser.setFileFilter(filter);

                int ret=chooser.showSaveDialog(null);
                if(ret==JFileChooser.APPROVE_OPTION){
                    String filePath=chooser.getSelectedFile().getPath();
                    if (filePath.lastIndexOf(".")==-1&&
                    !filePath.substring(filePath.lastIndexOf(".")+1).equalsIgnoreCase("json")){
                    filePath=filePath+".json";
                    }
                    ProcessedData.saveDataToJSON(filePath);

                }
            }
        });

        fileMenu.add(newWindowItem);
        fileMenu.add(saveDataItem);
        fileMenu.add(loadDataItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        setVisible(true);
    }

}

