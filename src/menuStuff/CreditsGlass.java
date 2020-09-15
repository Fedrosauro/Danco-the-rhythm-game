package menuStuff;

import javax.swing.*;
import java.awt.*;

public class CreditsGlass extends JPanel {

    private JFrame jFrame;

    private GlassPane glassPane;
    private CreditsPanel creditsPanel;
    private SubCreditsPanel subCreditsPanel;

    public CreditsGlass(JFrame jFrame){
        this.jFrame = jFrame;
        setup();
    }

    public void setup(){
        setLayout(new BorderLayout());
        glassPane = new GlassPane();
        add(glassPane, BorderLayout.CENTER);

        creditsPanel = new CreditsPanel();
        subCreditsPanel = new SubCreditsPanel(jFrame);

        glassPane.addPanel(subCreditsPanel);
        glassPane.addPanel(creditsPanel);
    }
}
