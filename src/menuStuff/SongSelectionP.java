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
        updateList("", false);
        jScrollPane = new MyJScrollPane(jList, 0, 0, WIDTH, HEIGHT);
        add(jScrollPane);
    }

    public void updateList(String s, boolean updating){
        list = new DefaultListModel<>();

        File dir = new File(dirSongs);
        if (dir.isDirectory()) {
            File[] dirListing = dir.listFiles();
            for (File f : dirListing) {
                if(s.isEmpty()){
                    list.addElement(f.getName().substring(0, f.getName().length() - 4));
                } else if((f.getName().substring(0, f.getName().length() - 4)).contains(s)){
                    list.addElement(f.getName().substring(0, f.getName().length() - 4));
                }
            }
        } else {
            System.out.println(dirSongs + "is not a directory");
        }

       if(!updating)jList = new MyJList(list);
       else jList.setModel(list);
    }

    public void setup() {
        setLayout(null);
        setOpaque(true);
        setBounds((MyFrame.WIDTH - WIDTH) / 2, (MyFrame.HEIGHT - HEIGHT) / 2 - 50, WIDTH, HEIGHT);
    }

    public MyJList getjList() {
        return jList;
    }

    public MyJScrollPane getjScrollPane() {
        return jScrollPane;
    }
}
