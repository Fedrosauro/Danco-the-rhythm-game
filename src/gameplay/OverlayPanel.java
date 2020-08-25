package gameplay;

import menuStuff.*;
import javax.swing.*;
import java.awt.*;
import java.util.stream.Stream;

public class OverlayPanel extends JPanel {

    private Game game;
    private JFrame jFrame;

    public static KeyInput keyInput;

    public OverlayPanel(JFrame jFrame){
        this.jFrame = jFrame;
        setPreferredSize(new Dimension(MyFrame.WIDTH, MyFrame.HEIGHT));
    }

    public void doSetup(){
        setup();
    }

    public void setup(){
        game = new Game();

        keyInput = new KeyInput(this);
        Stream.iterate(0, i -> i + 1).limit(65).forEach(i -> keyInput.addAction("" + (char)(i + 65)));

        setLayout(new BorderLayout());
        add(game);

        game.start();
    }
}
