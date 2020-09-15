package menuStuff;

import javax.swing.*;

public class CreditsPanel extends JPanel {

    public static int WIDTH = 1000, HEIGHT = 460;

    private JLabel jLabel;
    private MyJScrollPane jScrollPane;

    public CreditsPanel(){
        setup();
        jLabel = new JLabel(new ImageIcon("res/images/credits/credits.png"));
        jScrollPane = new MyJScrollPane(jLabel, 0, 0, WIDTH, HEIGHT);
        jScrollPane.getViewport().setOpaque(false);
        jScrollPane.setOpaque(false);
        jLabel.setOpaque(false);

        add(jScrollPane);
    }

    public void setup(){
        setLayout(null);
        setBounds((MyFrame.WIDTH - WIDTH) / 2, 20, WIDTH, HEIGHT);
    }
}
