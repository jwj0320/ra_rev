package gui;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GridBagPanel extends JPanel {

    public static final Color AjouBlue = Color.decode("#005BAC");

    public JTabbedPane tabbedPane;
    public JPanel panel;

    public GridBagPanel() {
        panel = this;
        panel.setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
    }
    

    public GridBagPanel(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
        panel = this;
        panel.setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
    }

    public void addGBLComponent(JComponent component, int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        this.add(component, gbc);
        return;
    }

    public void addGBLComponent(JComponent component, int x, int y, int width, int height) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        this.add(component, gbc);
        return;
    }

    public void addGBLComponent(JComponent component, int x, int y, double weightx, double weighty) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        this.add(component, gbc);
        return;
    }

    public void addGBLComponent(JComponent component, int x, int y, double weightx, double weighty, String option) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = weightx;
        gbc.weighty = weighty;

        switch (option) {
            case "NONE":
                gbc.fill = GridBagConstraints.NONE;
                break;
            case "HORIZONTAL":
                gbc.fill = GridBagConstraints.HORIZONTAL;
                break;
            case "VERTICAL":
                gbc.fill = GridBagConstraints.VERTICAL;
                break;
            case "BOTH":
                gbc.fill = GridBagConstraints.BOTH;
                break;
            default:
                System.err.println("Warning: Unidentified Option");
                break;
        }

        this.add(component, gbc);
        return;
    }

    public void addGBLComponent(JComponent component, int x, int y, double weightx, double weighty, String option,
            int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = weightx;
        gbc.weighty = weighty;

        switch (option) {
            case "NONE":
                gbc.fill = GridBagConstraints.NONE;
                break;
            case "HORIZONTAL":
                gbc.fill = GridBagConstraints.HORIZONTAL;
                break;
            case "VERTICAL":
                gbc.fill = GridBagConstraints.VERTICAL;
                break;
            case "BOTH":
                gbc.fill = GridBagConstraints.BOTH;
                break;
            default:
                System.err.println("Warning: Unidentified Option");
                break;
        }

        gbc.anchor = anchor;

        this.add(component, gbc);
        return;
    }

    public void addGBLComponent(JComponent component, int x, int y, int width, int height, double weightx,
            double weighty) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        this.add(component, gbc);
        return;
    }

    public void addGBLComponent(JComponent component, int x, int y, int width, int height, String option) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;

        switch (option) {
            case "NONE":
                gbc.fill = GridBagConstraints.NONE;
                break;
            case "HORIZONTAL":
                gbc.fill = GridBagConstraints.HORIZONTAL;
                break;
            case "VERTICAL":
                gbc.fill = GridBagConstraints.VERTICAL;
                break;
            case "BOTH":
                gbc.fill = GridBagConstraints.BOTH;
                break;
            default:
                System.err.println("Warning: Unidentified Option");
                break;
        }

        this.add(component, gbc);
        return;
    }

    public void addGBLComponent(JComponent component, int x, int y, int width, int height, double weightx,
            double weighty, String option) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = weightx;
        gbc.weighty = weighty;

        switch (option) {
            case "NONE":
                gbc.fill = GridBagConstraints.NONE;
                break;
            case "HORIZONTAL":
                gbc.fill = GridBagConstraints.HORIZONTAL;
                break;
            case "VERTICAL":
                gbc.fill = GridBagConstraints.VERTICAL;
                break;
            case "BOTH":
                gbc.fill = GridBagConstraints.BOTH;
                break;
            default:
                System.err.println("Warning: Unidentified Option");
                break;
        }

        this.add(component, gbc);
        return;
    }

    public void addGBLComponent(JComponent component, int x, int y, int width, int height, double weightx,
            double weighty, String option, int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = weightx;
        gbc.weighty = weighty;

        switch (option) {
            case "NONE":
                gbc.fill = GridBagConstraints.NONE;
                break;
            case "HORIZONTAL":
                gbc.fill = GridBagConstraints.HORIZONTAL;
                break;
            case "VERTICAL":
                gbc.fill = GridBagConstraints.VERTICAL;
                break;
            case "BOTH":
                gbc.fill = GridBagConstraints.BOTH;
                break;
            default:
                System.err.println("Warning: Unidentified Option");
                break;
        }
        gbc.anchor = anchor;

        this.add(component, gbc);
        return;
    }

    public void disableOtherTabs() {
        int index = tabbedPane.getSelectedIndex();
        int count = tabbedPane.getTabCount();
        for (int i = 0; i < count; i++) {
            if (i != index) {
                tabbedPane.setEnabledAt(i, false);
            }
        }
    }

    public JLabel makeHeader(String text){
        JLabel label = new JLabel(" "+text+" ");
        label.setOpaque(true);
        // label.setBorder(BorderFactory.createLineBorder(Color.gray));
        label.setForeground(Color.white);
        label.setBackground(AjouBlue);
        // label.setPreferredSize(new Dimension(100,30));
        
        return label;
    }

    public JLabel makeContent(String text){
        JLabel label = new JLabel(" "+text+" ");
        label.setBorder(BorderFactory.createLineBorder(Color.gray));
        label.setPreferredSize(new Dimension(300,20));
        label.setOpaque(true);
        label.setBackground(Color.white);
        
        return label;
    }
    
    public JTable makeContentTable(){
        JTable table = new JTable(new DefaultTableModel(new String[]{""},0));
        table.setTableHeader(null);
        table.setOpaque(true);
        table.setBackground(Color.white);
        return table;
    }

}
