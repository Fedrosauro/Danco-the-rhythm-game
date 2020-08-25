package menuStuff;

import javax.swing.*;
import java.awt.*;
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
        if(dir.isDirectory()){
            File[] dirListing = dir.listFiles();
            for(File f : dirListing){
                list.addElement("  " + f.getName().substring(0, f.getName().length() - 4));
            }
        } else{
            System.out.println(dirSongs + "is not a directory");
        }

        jList = new MyJList(list);
        jScrollPane = new MyJScrollPane(jList.getList(), 0, 0, WIDTH, HEIGHT);

        add(jScrollPane);
    }

    class MyJList{

        private JList<String> list;

        public MyJList(DefaultListModel<String> list) {
            this.list = new JList<>(list);
            UIManager.put("List.focusCellHighlightBorder", BorderFactory.createEmptyBorder());
            this.list.setBackground(new Color(29, 29, 29));
            this.list.setForeground(Color.white); //text background
            this.list.setFont((new Font("Arial", Font.PLAIN, 25)));
            this.list.setSelectionBackground(Color.white);
            this.list.setSelectionForeground(Color.black);
            this.list.setFixedCellHeight(30);
            this.list.setSelectedIndex(0);
            this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }

        public JList<String> getList() {
            return list;
        }
    }

    class MyJScrollPane extends JScrollPane {

        private int x, y, width, height;

        public MyJScrollPane(JList<String> list, int x, int y, int width, int height) {
            super(list);
            this.x = x;
            this.y = y;
            this.height = height;
            this.width = width;
            setBounds(x, y, width, height);
            setBorder(javax.swing.BorderFactory.createEmptyBorder());
            getVerticalScrollBar().setUI(new MyScrollBarUI());
            getHorizontalScrollBar().setUI(new MyScrollBarUI());
            JPanel cornerBg = new JPanel();
            cornerBg.setBackground(Color.black);
            setCorner(JScrollPane.LOWER_RIGHT_CORNER, cornerBg);
        }
    }
}
