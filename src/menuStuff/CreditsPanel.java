package menuStuff;

import javax.swing.*;
import java.awt.*;

public class CreditsPanel extends JPanel {

    public static int WIDTH = 1000, HEIGHT = 460;

    private JLabel jLabel;
    private MyJScrollPane jScrollPane;

    public CreditsPanel(){
        setup();
        jLabel = new JLabel(new ImageIcon("res/redPlanet.png"));
        jScrollPane = new MyJScrollPane(jLabel, 0, 0, WIDTH, HEIGHT);
        jScrollPane.setBackground(Color.red);
        add(jScrollPane);
    }

    public void setup(){
        setLayout(null);
        setOpaque(true);
        setBounds((MyFrame.WIDTH - WIDTH) / 2, 20, WIDTH, HEIGHT);
    }
}
