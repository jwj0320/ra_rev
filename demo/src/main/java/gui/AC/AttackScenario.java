package gui.AC;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import gui.GridBagPanel;

public class AttackScenario extends GridBagPanel{
    private InputArea inputArea;
    private SelectArea selectArea;
    private ScenarioArea scenarioArea;
    private TargetArea targetArea;
    private GridBagPanel self = this;
    
    public AttackScenario(JTabbedPane tPane){
        super(tPane);
        init();

        
        
    }

    private void init(){
        inputArea = new InputArea();
        selectArea = new SelectArea();
        scenarioArea = new ScenarioArea();
        targetArea = new TargetArea();

        addGBLComponent(inputArea, 0, 0);
        addGBLComponent(selectArea, 1, 0);
        addGBLComponent(scenarioArea, 0, 1);
        addGBLComponent(targetArea, 1, 1);
        
        JButton button = new JButton("temp");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Progress progress = new Progress(self);
            }
        });
        addGBLComponent(button, 1, 2);
    }
}

class InputArea extends GridBagPanel{
    public InputArea(){
        JLabel label = new JLabel("Input");
        addGBLComponent(label, 0, 0);

        JLabel[] headers = {
            new JLabel("The attack has purpose such as",SwingConstants.LEFT),
            new JLabel("This attack has tactics such as",SwingConstants.LEFT),
            new JLabel("The attacker uses techniques such as",SwingConstants.LEFT),
            new JLabel("The attacker uses software such as",SwingConstants.LEFT),
            new JLabel("This attack targets organizations such as",SwingConstants.LEFT)
        };
        for (int i=0;i<headers.length;i++){
            addGBLComponent(headers[i], 0, i+1);
        }

        JLabel[] contents = {
            new JLabel("           ",SwingConstants.RIGHT),
            new JLabel("           ",SwingConstants.RIGHT),
            new JLabel("           ",SwingConstants.RIGHT),
            new JLabel("           ",SwingConstants.RIGHT),
            new JLabel("           ",SwingConstants.RIGHT)  
        };
        for (int i=0;i<headers.length;i++){
            addGBLComponent(headers[i], 1, i+1);
        }
    }

}

class SelectArea extends GridBagPanel{
    public SelectArea(){
        JLabel label = new JLabel("SelectArea");
        add(label);
    }
}

class ScenarioArea extends GridBagPanel{
    public ScenarioArea(){
        JLabel label = new JLabel("Attack Scenario");
        addGBLComponent(label, 0, 0);

        JTextArea textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(300,200));
        textArea.setEditable(false);
        
        JScrollPane textAreaSc = new JScrollPane(textArea);
        textAreaSc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        addGBLComponent(textAreaSc, 0, 1);
    }
    
}

class TargetArea extends GridBagPanel{
    public TargetArea(){
        JLabel label = new JLabel("Target");
        addGBLComponent(label, 0, 0);

        JTextArea textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(300,200));
        textArea.setEditable(false);
        
        JScrollPane textAreaSc = new JScrollPane(textArea);
        addGBLComponent(textAreaSc, 0, 1);
    }
}
