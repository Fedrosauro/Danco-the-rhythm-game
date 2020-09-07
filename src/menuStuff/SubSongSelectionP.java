package menuStuff;

import gameplay.Game;
import gameplay.MusicPlayer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SubSongSelectionP extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    private JFrame jFrame;
    private SongSelectionP songSelectionP;

    private final int DELAY = 5;
    private Timer timer;

    private MusicPlayer buttonAudio;

    private BufferedImageLoader loader;

    private BufferedImage background;
    private BufferedImage[] goBackImages, playSImages, viewSImages;
    private Animation goBackAnim, playSAnim, viewSAnim;

    private Rectangle2D rect1; //go back
    private int x1, y1;
    private int width1, height1;
    private boolean change1;

    private Rectangle2D rect2; //play the song
    private int x2;
    private boolean change2;

    private Rectangle2D rect3; //view scores of that song
    private int x3;
    private boolean change3;

    private int backX, playX, scoresX;

    private JCheckBox jCheckBox;
    private JTextField jTextField;

    private String oldText, newText;

    public SubSongSelectionP(SongSelectionP songSelectionP, JFrame jFrame){
        this.jFrame = jFrame;
        this.songSelectionP = songSelectionP;
        setup();
        initTimer();
    }

    public void setup(){
        addMouseListener(this);
        addMouseMotionListener(this);

        setLayout(null);
        setOpaque(true);
        setBounds(0, 0, MyFrame.WIDTH,  MyFrame.HEIGHT);

        loader = new BufferedImageLoader();

        background = loader.loadImage("res/images/backgrounds/background_songs.png");

        backX = 57;
        width1 = 275;
        height1 = 50;
        x1 = backX + 45;
        y1 = 480;
        rect1= new Rectangle2D.Float(x1, y1 + 19, width1, height1);

        playX = backX + 300;
        x2 = playX + 45;
        rect2= new Rectangle2D.Float(x2, y1 + 19, width1, height1);

        scoresX = playX + 300;
        x3 = scoresX + 45;
        rect3= new Rectangle2D.Float(x3, y1 + 19, width1, height1);

        change1 = false;
        change2 = false;
        change3 = false;

        goBackImages = new BufferedImage[7];
        for(int i = 0; i < 7; i++) {
            goBackImages[i] = loader.loadImage("res/images/goBackButton/goback000" + i + ".png");
        }

        goBackAnim = new Animation(1, this
                , goBackImages[0],  goBackImages[1], goBackImages[2], goBackImages[3]
                , goBackImages[4],  goBackImages[5], goBackImages[6]);

        playSImages = new BufferedImage[7];
        for(int i = 0; i < 7; i++) {
            playSImages[i] = loader.loadImage("res/images/playSongButton/playsong_button000" + i + ".png");
        }

        playSAnim = new Animation(1, this
                , playSImages[0],  playSImages[1], playSImages[2], playSImages[3]
                , playSImages[4],  playSImages[5], playSImages[6]);

        viewSImages = new BufferedImage[7];
        for(int i = 0; i < 7; i++) {
            viewSImages[i] = loader.loadImage("res/images/viewScoresButton/viewscore_button000" + i + ".png");
        }

        viewSAnim = new Animation(1, this
                , viewSImages[0],  viewSImages[1], viewSImages[2], viewSImages[3]
                , viewSImages[4],  viewSImages[5], viewSImages[6]);

        buttonAudio = new MusicPlayer("res/audioButton.wav");

        jCheckBox = new JCheckBox("", false);
        jCheckBox.setBounds(x2 + 65, 446, 150, 29);
        jCheckBox.setOpaque(false);
        jCheckBox.setIcon(new ImageIcon("res/images/checkBoxIm/unchecked.png"));
        jCheckBox.setSelectedIcon(new ImageIcon("res/images/checkBoxIm/checked.png"));
        add(jCheckBox);

        jTextField = new JTextField();
        jTextField.setBounds((MyFrame.WIDTH - SongSelectionP.WIDTH) / 2 + 550
                ,(MyFrame.HEIGHT - SongSelectionP.HEIGHT) / 2 - 97
                , ((MyFrame.WIDTH - SongSelectionP.WIDTH) / 2 + SongSelectionP.WIDTH) - ((MyFrame.WIDTH - SongSelectionP.WIDTH) / 2 + 550), 30);
        jTextField.setBackground(Color.black);
        jTextField.setForeground(Color.white);
        jTextField.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
        Border border = BorderFactory.createLineBorder(Color.white);
        jTextField.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(1, 10, 1, 1)));
        jTextField.setHorizontalAlignment(JLabel.LEFT);

        oldText = "";
        add(jTextField);
    }

    public void initTimer(){
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        playSAnim.runAnimation(change2);
        goBackAnim.runAnimation(change1);
        viewSAnim.runAnimation(change3);
        newText = jTextField.getText();
        if(!oldText.equals(newText)) {
            songSelectionP.updateList(newText, true);
        }
        oldText = newText;
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

        goBackAnim.drawAnimation(g2d, backX, y1);
        /*g2d.setColor(Color.red);
        g2d.draw(rect1);*/

        playSAnim.drawAnimation(g2d, playX, y1);
        /*g2d.setColor(Color.blue);
        g2d.draw(rect2);*/

        viewSAnim.drawAnimation(g2d, scoresX, y1);
        /*g2d.setColor(Color.magenta);
        g2d.draw(rect3);*/

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

        if(songSelectionP.getjList().getSelectedValue() != null) {
            if (rect2.contains(x, y)) {
                timer.stop();
                Game game = new Game(jFrame, (String) songSelectionP.getjList().getSelectedValue(), jCheckBox.isSelected());
                jFrame.setContentPane(game);
                jFrame.revalidate();
                game.start();
            }

            if (rect3.contains(x, y)) {
                ScoreGlass scoreGlass = null;
                try {
                    scoreGlass = new ScoreGlass(jFrame, (String) songSelectionP.getjList().getSelectedValue());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                timer.stop();
                jFrame.setContentPane(scoreGlass);
                jFrame.revalidate();
            }
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

        if (rect3.contains(x, y)) {
            if(!change3){
                try {
                    buttonAudio.createAudio();
                    buttonAudio.playTrack();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            change3 = true;
        } else change3 = false;
    }

    ////////////////////////////////////////////////////
    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    @Override public void mouseDragged(MouseEvent e) { }
    ////////////////////////////////////////////////////

}
