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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import data.Group;
import data.Software;
import data.TabbedPaneInfo;
import data.Technique;
import gui.AC.AttackScenario;



public class MainFrame extends JFrame{

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
        
        tPane.addTab("Security Requirements", new GridBagPanel());
        tPane.addTab("Collection of Evidence", new GridBagPanel());
        tPane.addTab("Risks Assessment", new GridBagPanel());
        tPane.addTab("Risk Analysis", new GridBagPanel());
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

        setVisible(true);
    }

}

