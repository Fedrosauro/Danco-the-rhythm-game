package gameplay;

import menuStuff.*;
import javax.swing.*;
import java.util.stream.Stream;

public class OverlayPanel extends JPanel {

    private Game game;
    private JFrame jFrame;

    public static KeyInput keyInput;

    private String selectedSong;
    private boolean CAM;

    public static int gameLaunched = -1;

    public OverlayPanel(JFrame jFrame, String selectedSong, boolean checkedAutoMode){
        this.jFrame = jFrame;
        this.selectedSong = selectedSong;
        this.CAM = checkedAutoMode;
        setBounds(0, 0, MyFrame.WIDTH, MyFrame.HEIGHT);
        gameLaunched++;
    }

    public void doSetup(){
        setup();
    }

    public void startGame(){
        game.start();
    }

    private void setup(){
        game = new Game(jFrame, selectedSong, CAM);

        keyInput = new KeyInput(this);
        Stream.iterate(0, i -> i + 1).limit(65).forEach(i -> keyInput.addAction("" + (char)(i + 65)));
        keyInput.addAction("SPACE");

        jFrame.setContentPane(game);
        jFrame.revalidate();
    }
}
