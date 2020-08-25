package menuStuff;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ScoreView extends JPanel implements ActionListener {

    private InputStreamReader input;

    private String scoresPath;

    public static int WIDTH = 300, HEIGHT = 350;

    private MyJList jList;
    private MyJScrollPane jScrollPane;

    private DefaultListModel<String> list;

    private final int DELAY = 10;
    private Timer timer;

    public ScoreView(String song) throws IOException {
        scoresPath = "res/scores/scores_" + song + ".txt";
        setup();
        initTimer();
    }

    public void setup() throws IOException {
        setBackground(new Color(29, 29, 29));
        setOpaque(true);
        setBounds((MyFrame.WIDTH - WIDTH) / 2, (MyFrame.HEIGHT - HEIGHT) / 2 - 50, WIDTH, HEIGHT);

        list = new DefaultListModel<>();

        readScores();
    }

    public void readScores() throws IOException {
        input = new InputStreamReader(new FileInputStream(scoresPath));
        int data = input.read();
        String word = "";
        while(data != -1){
            if((char)data != '\n') {
                word += (char) data;
            } else{
                list.addElement(word);
                word = "";
            }
        } input.close();
    }

    public void initTimer(){
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
