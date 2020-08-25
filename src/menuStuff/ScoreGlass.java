package menuStuff;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ScoreGlass extends JPanel {

    private JFrame jFrame;
    private String selectedSong;

    private GlassPane glassPane;
    private ScoreView scoreView;
    private SubScoreView subScoreView;

    public ScoreGlass(JFrame jFrame, String selectedSong) throws IOException {
        this.jFrame = jFrame;
        this.selectedSong = selectedSong;
        setup();
    }

    public void setup() throws IOException {
        setLayout(new BorderLayout());
        glassPane = new GlassPane();
        add(glassPane, BorderLayout.CENTER);

        scoreView = new ScoreView(selectedSong);
        subScoreView = new SubScoreView(jFrame);

        glassPane.addPanel(subScoreView);
        glassPane.addPanel(scoreView);
    }

}
