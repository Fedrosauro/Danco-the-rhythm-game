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

    private BufferedImage goBack_h, goBack_nh;
    private BufferedImage[] scorePanel;
    private Animation scorePanelAnim;

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

        goBack_h = loader.loadImage("res/images/buttons/GoBackB/goBackB1.png");
        goBack_nh = loader.loadImage("res/images/buttons/GoBackB/goBackB0.png");

        backX = 357;
        width1 = 271;
        height1 = 50;
        x1 = backX + 42;
        y1 = 490;
        rect1= new Rectangle2D.Float(x1, y1 + 19, width1, height1);

        change1 = false;

        scorePanel = new BufferedImage[24];
        for(int i = 0; i < 24; i++) {
            if(i <= 9) scorePanel[i] = loader.loadImage("res/images/backgrounds/b_viewScores/b_viewscore000" + i + ".png");
            else scorePanel[i] = loader.loadImage("res/images/backgrounds/b_viewScores/b_viewscore00" + i + ".png");
        }

        scorePanelAnim = new Animation(1, this
                , scorePanel[0], scorePanel[1], scorePanel[2], scorePanel[3], scorePanel[4]
                , scorePanel[5], scorePanel[6], scorePanel[7], scorePanel[8], scorePanel[9]
                , scorePanel[10], scorePanel[11], scorePanel[12], scorePanel[13], scorePanel[14]
                , scorePanel[15], scorePanel[16], scorePanel[17], scorePanel[18], scorePanel[19]
                , scorePanel[20], scorePanel[21], scorePanel[22], scorePanel[23]);

        buttonAudio = new MusicPlayer("res/audioButton.wav");
    }

    public void initTimer(){
        timer = new Timer(DELAY, this);
        timer.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        scorePanelAnim.runAnimation();
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

        scorePanelAnim.drawAnimation(g2d, 0, 0);

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
