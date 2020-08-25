package menuStuff;

import javax.swing.*;
import java.awt.*;

public class SelectGlass extends JPanel {

    private JFrame jFrame;

    private GlassPane glassPane;
    private SongSelectionP songSelectionP;
    private SubSongSelectionP subSongSelectionP;

    public SelectGlass(JFrame jFrame){
        this.jFrame = jFrame;
        setup();
    }

    public void setup(){
        setLayout(new BorderLayout());
        glassPane = new GlassPane();
        add(glassPane, BorderLayout.CENTER);

        songSelectionP = new SongSelectionP();
        subSongSelectionP = new SubSongSelectionP(songSelectionP, jFrame);

        glassPane.addPanel(subSongSelectionP);
        glassPane.addPanel(songSelectionP);
    }

}
