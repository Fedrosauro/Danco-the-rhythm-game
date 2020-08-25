package menuStuff;

import javax.swing.*;
import java.io.File;

public class SongSelectionP extends JPanel {

    public static int WIDTH = 700, HEIGHT = 350;

    private MyJList jList;
    private MyJScrollPane jScrollPane;

    private DefaultListModel<String> list;
    private final String dirSongs = "res/songs";

    public SongSelectionP() {
        setup();
    }

    public void setup() {
        setLayout(null);
        setOpaque(true);
        setBounds((MyFrame.WIDTH - WIDTH) / 2, (MyFrame.HEIGHT - HEIGHT) / 2 - 50, WIDTH, HEIGHT);

        list = new DefaultListModel<>();

        File dir = new File(dirSongs);
        if (dir.isDirectory()) {
            File[] dirListing = dir.listFiles();
            for (File f : dirListing) {
                list.addElement(f.getName().substring(0, f.getName().length() - 4));
            }
        } else {
            System.out.println(dirSongs + "is not a directory");
        }

        jList = new MyJList(list);
        jScrollPane = new MyJScrollPane(jList, 0, 0, WIDTH, HEIGHT);

        add(jScrollPane);
    }

    public MyJList getjList() {
        return jList;
    }
}
