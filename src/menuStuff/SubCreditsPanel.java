package menuStuff;

import gameplay.MusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class SubCreditsPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    private JFrame jFrame;

    private final int DELAY = 5;
    private Timer timer;

    private MusicPlayer buttonAudio;

    private BufferedImageLoader loader;

    private BufferedImage goBack_h, goBack_nh, background, credits_panel;

    private Rectangle2D rect1; //go back
    private int x1, y1;
    private int width1, height1;
    private boolean change1;

    private int backX;

    public SubCreditsPanel(JFrame jFrame){
        this.jFrame = jFrame;
        setup();
        initTimer();
    }

    public void setup(){
        addMouseListener(this);
        addMouseMotionListener(this);

        setBackground(Color.blue);
        setOpaque(true);
        setLayout(null);
        setBounds(0, 0, MyFrame.WIDTH,  MyFrame.HEIGHT);

        loader = new BufferedImageLoader();

        background = loader.loadImage("res/images/backgrounds/background_credits.png");
        credits_panel = loader.loadImage("res/images/backgrounds/panel_credits.png");
        goBack_h = loader.loadImage("res/images/buttons/GoBackB/goBackB1.png");
        goBack_nh = loader.loadImage("res/images/buttons/GoBackB/goBackB0.png");

        backX = 357;
        width1 = 271;
        height1 = 50;
        x1 = backX + 42;
        y1 = 490;
        rect1= new Rectangle2D.Float(x1, y1 + 19, width1, height1);

        change1 = false;

        buttonAudio = new MusicPlayer("res/audioButton.wav");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    public void initTimer(){
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void doDrawing(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        g2d.drawImage(background, 0, 0, null);
        g2d.drawImage(credits_panel, 0, 0, null);

        if(change1) g2d.drawImage(goBack_h, backX, y1, null);
        else g2d.drawImage(goBack_nh, backX, y1, null);

        /*g2d.setColor(Color.red);
        g2d.draw(rect1);*/

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rect1.contains(x, y)) {
            MainPage mainPage = new MainPage(jFrame);
            timer.stop();
            jFrame.setContentPane(mainPage);
            jFrame.revalidate();
        }
    }

    @Override
    public void mouseMoved (MouseEvent e){
        int x = e.getX();
        int y = e.getY();

        if (rect1.contains(x, y)) {
            if (!change1) {
                try {
                    buttonAudio.createAudio();
                    buttonAudio.playTrack();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            change1 = true;
        } else change1 = false;
    }

    ////////////////////////////////////////////////////
    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    @Override public void mouseDragged(MouseEvent e) { }
    ////////////////////////////////////////////////////
}
