package gameplay;

import menuStuff.*;
import javax.swing.*;
import java.util.stream.Stream;

public class OverlayPanel extends JPanel {

    private Game game;
    private JFrame jFrame;

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

    public void doSetup() {
        game = new Game(jFrame, selectedSong, CAM);

        jFrame.setContentPane(game);
        jFrame.revalidate();
    }

    public void startGame(){
        game.start();
    }

}
