package menuStuff;

import javax.swing.*;

public class MyFrame extends JFrame {

    public static final int WIDTH = 1100, HEIGHT = 625;

    public MyFrame(){
        initUI();
    }

    public void initUI(){
        MainPage mainPage = new MainPage(this);
        setContentPane(mainPage);

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
