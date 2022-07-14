package gui.AC;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;

import gui.GridBagPanel;

public class MakeScenario extends JDialog{
    public MakeScenario(){
        setTitle("In progress...");
        File imageFile = new File(this.getClass().getResource("").getPath(),"../../../../icon.png");
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

        GridBagPanel pane = new GridBagPanel();
        add(pane);

        
        setVisible(true);
    }
}
