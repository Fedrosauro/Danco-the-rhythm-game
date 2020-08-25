package menuStuff;

import gameplay.*;

import javax.swing.*;

public class MyFrame extends JFrame {

    public static final int WIDTH = 1000, HEIGHT = 600;

    public MyFrame(){
        initUI();
    }

    public void initUI(){

        MainPage mainPage = new MainPage(this);
        setContentPane(mainPage);

        /*OverlayPanel overlayPanel = new OverlayPanel(this);
        setContentPane(overlayPanel);*/

        /*int[] results = new int[4];
        results[0] = 1;
        results[1] = 5;
        results[2] = 10;
        results[3] = 144;

        ResultPane resultPane = new ResultPane(this, "Granat - Drop", 75500, results);
        setContentPane(resultPane);*/

        setTitle("Game Test");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        //overlayPanel.doSetup();

    }

    public static void main(String args[]) {
        System.setProperty("sun.java2d.opengl", "True");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e){
            e.printStackTrace();
        }
        MyFrame myFrame = new MyFrame();
    }
}
