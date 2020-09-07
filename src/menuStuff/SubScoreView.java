package menuStuff;

import gameplay.MusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class SubScoreView extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    private JFrame jFrame;

    private final int DELAY = 5;
    private Timer timer;

    private MusicPlayer buttonAudio;

    private BufferedImageLoader loader;

    private BufferedImage[] goBackImages;
    private Animation goBackAnim;

    private Rectangle2D rect1; //go back
    private int x1, y1;
    private int width1, height1;
    private boolean change1;

    private int backX;

    public SubScoreView(JFrame jFrame){
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

        backX = 357;
        width1 = 275;
        height1 = 50;
        x1 = backX + 45;
        y1 = 480;
        rect1= new Rectangle2D.Float(x1, y1 + 19, width1, height1);

        change1 = false;

        goBackImages = new BufferedImage[7];
        for(int i = 0; i < 7; i++) {
            goBackImages[i] = loader.loadImage("res/images/goBackButton/goback000" + i + ".png");
        }

        goBackAnim = new Animation(1, this
                , goBackImages[0],  goBackImages[1], goBackImages[2], goBackImages[3]
                , goBackImages[4],  goBackImages[5], goBackImages[6]);

        buttonAudio = new MusicPlayer("res/audioButton.wav");

    }

    public void initTimer(){
        timer = new Timer(DELAY, this);
        timer.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        goBackAnim.runAnimation(change1);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        goBackAnim.drawAnimation(g2d, backX, y1);
        /*g2d.setColor(Color.red);
        g2d.draw(rect1);*/
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rect1.contains(x, y)) {
            SelectGlass selectGlass = new SelectGlass(jFrame);
            timer.stop();
            jFrame.setContentPane(selectGlass);
            jFrame.revalidate();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rect1.contains(x, y)) {
            if(!change1){
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
