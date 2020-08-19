import javax.swing.*;
import java.util.Scanner;

public class MyFrame extends JFrame {

    public static final int WIDTH = 1000, HEIGHT = 600;

    public MyFrame(){
        initUI();
    }

    public void initUI(){

        OverlayPanel overlayPanel = new OverlayPanel(this);
        setContentPane(overlayPanel);

        setTitle("Game Test");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        overlayPanel.doSetup();

        setVisible(true);
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
