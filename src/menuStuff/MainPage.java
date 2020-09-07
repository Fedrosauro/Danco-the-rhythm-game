package menuStuff;

import gameplay.MusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class MainPage extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    private JFrame jFrame;

    private final int DELAY = 5;
    private Timer timer;

    private MusicPlayer buttonAudio;

    private BufferedImageLoader loader;

    private BufferedImage background;
    private BufferedImage[] playImages, exitImages, titleImages;
    private Animation playAnim, exitAnim, titleAnim;

    private Rectangle2D rect1;
    private int x1, y1;
    private int width1, height1;
    private boolean change1;

    private Rectangle2D rect2;
    private int y2;
    private boolean change2;

    private int playY, exitY;

    public MainPage(JFrame jFrame){
        this.jFrame = jFrame;
        setup();
        initTimer();
    }

    public void setup(){
        addMouseListener(this);
        addMouseMotionListener(this);

        setPreferredSize(new Dimension(MyFrame.WIDTH, MyFrame.HEIGHT));
        setLayout(null);

        loader = new BufferedImageLoader();

        background = loader.loadImage("res/images/backgrounds/background_menu.png");

        playY = 340;
        width1 = 150; //things for buttons
        height1 = 80;
        x1 = (MyFrame.WIDTH - width1)/2;
        y1 = playY + 19;
        rect1= new Rectangle2D.Float(x1, y1, width1, height1);
        change1 = false;

        exitY = playY + 120;
        y2 = exitY + 19;
        rect2= new Rectangle2D.Float(x1, y2, width1, height1);
        change2 = false;

        playImages = new BufferedImage[7];
        for(int i = 0; i < 7; i++) {
            playImages[i] = loader.loadImage("res/images/playButton/play_button_white000" + i + ".png");
        }

        playAnim = new Animation(1, this
                , playImages[0],  playImages[1], playImages[2], playImages[3]
                , playImages[4],  playImages[5], playImages[6]);

        exitImages = new BufferedImage[7];
        for(int i = 0; i < 7; i++) {
            exitImages[i] = loader.loadImage("res/images/exitButton/exit_button_white000" + i + ".png");
        }

        exitAnim = new Animation(1, this
                , exitImages[0],  exitImages[1], exitImages[2], exitImages[3]
                , exitImages[4],  exitImages[5], exitImages[6]);

        titleImages = new BufferedImage[31];
        for(int i = 0; i < 31; i++){
            if(i <= 9) titleImages[i] = loader.loadImage("res/images/title/title_anim000" + i + ".png");
            else titleImages[i] = loader.loadImage("res/images/title/title_anim00" + i + ".png");
        }

        titleAnim = new Animation(2, this
                , titleImages[0], titleImages[1], titleImages[2], titleImages[3], titleImages[4], titleImages[5],
                titleImages[6], titleImages[7], titleImages[8], titleImages[9], titleImages[10], titleImages[11],
                titleImages[12], titleImages[13], titleImages[14], titleImages[15], titleImages[16], titleImages[17],
                titleImages[18], titleImages[19], titleImages[20], titleImages[21], titleImages[22], titleImages[23],
                titleImages[24], titleImages[25], titleImages[26], titleImages[27], titleImages[28], titleImages[29],
                titleImages[30]);

        buttonAudio = new MusicPlayer("res/audioButton.wav");
    }

    public void initTimer(){
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        playAnim.runAnimation(change1);
        exitAnim.runAnimation(change2);
        titleAnim.runAnimationOnce();
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

        g2d.drawImage(background, 0, 0, null);

        titleAnim.drawAnimation(g2d, (MyFrame.WIDTH - titleImages[0].getWidth()) / 2 - 16, 80);

        playAnim.drawAnimation(g2d, 0, playY);
        /*g2d.setColor(Color.green);
        g2d.draw(rect1);*/

        exitAnim.drawAnimation(g2d, 0, exitY);
        /*g2d.setColor(Color.cyan);
        g2d.draw(rect2);*/
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

        if (rect2.contains(x, y)) {
            timer.stop();
            jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING)); //X with custom button
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

        if (rect2.contains(x, y)) {
            if(!change2){
                try {
                    buttonAudio.createAudio();
                    buttonAudio.playTrack();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            change2 = true;
        } else change2 = false;
    }

    ////////////////////////////////////////////////////
    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    @Override public void mouseDragged(MouseEvent e) { }
    ////////////////////////////////////////////////////
}
