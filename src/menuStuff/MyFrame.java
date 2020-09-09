package menuStuff;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    public static final int WIDTH = 1100, HEIGHT = 625;

    public MyFrame(){
        initUI();
    }

    public void initUI(){
        //MainPage mainPage = new MainPage(this);

        String selectedSong = "akldsflaskdfjlaskjflskajfkasSDFSDFSFDDFSFDSFDSFDSFSFDFDSDFSfaskfdlsd";
        int score = 70000;
        int[] scoreCounts = {0, 0, 0, 123};
        ResultPane resultPane = new ResultPane(this, selectedSong, false, score, scoreCounts);
        resultPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        setContentPane(resultPane);

        setTitle("DANCO");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String args[]) {
        MyFrame myFrame = new MyFrame();
    }
}
