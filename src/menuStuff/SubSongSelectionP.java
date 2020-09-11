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

    private BufferedImage background, goBack_h, goBack_nh, playSong_h, playSong_nh, viewScores_h, viewScores_nh;
    private BufferedImage[] songList_b;
    private Animation songListAnim;

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

        background = loader.loadImage("res/images/backgrounds/background_menu.png");
        goBack_h = loader.loadImage("res/images/buttons/GoBackB/goBackB1.png");
        goBack_nh = loader.loadImage("res/images/buttons/GoBackB/goBackB0.png");
        playSong_h = loader.loadImage("res/images/buttons/PlaySongB/playSongB1.png");
        playSong_nh = loader.loadImage("res/images/buttons/PlaySongB/playSongB0.png");
        viewScores_h = loader.loadImage("res/images/buttons/ViewScoresB/viewScoresB1.png");
        viewScores_nh = loader.loadImage("res/images/buttons/ViewScoresB/viewScoresB0.png");

        backX = 57;
        width1 = 271;
        height1 = 50;
        x1 = backX + 42;
        y1 = 490;
        rect1= new Rectangle2D.Float(x1, y1 + 19, width1, height1);

        playX = backX + 300;
        x2 = playX + 42;
        rect2= new Rectangle2D.Float(x2, y1 + 19, width1, height1);

        scoresX = playX + 300;
        x3 = scoresX + 42;
        rect3= new Rectangle2D.Float(x3, y1 + 19, width1, height1);

        change1 = false;
        change2 = false;
        change3 = false;

        songList_b = new BufferedImage[40];
        for(int i = 0; i < 40; i++) {
            if(i <= 9) songList_b[i] = loader.loadImage("res/images/backgrounds/b_songList/b_songlist000" + i + ".png");
            else songList_b[i] = loader.loadImage("res/images/backgrounds/b_songList/b_songlist00" + i + ".png");
        }

        songListAnim = new Animation(1, this
                , songList_b[0],  songList_b[1], songList_b[2], songList_b[3]
                , songList_b[4],  songList_b[5], songList_b[6], songList_b[7]
                , songList_b[8],  songList_b[9], songList_b[10], songList_b[11]
                , songList_b[12],  songList_b[13], songList_b[14], songList_b[15]
                , songList_b[16],  songList_b[17], songList_b[18], songList_b[19]
                , songList_b[20],  songList_b[21], songList_b[22], songList_b[23]
                , songList_b[24],  songList_b[25], songList_b[26], songList_b[27]
                , songList_b[28],  songList_b[29], songList_b[30], songList_b[31]
                , songList_b[32],  songList_b[33], songList_b[34], songList_b[35]
                , songList_b[36],  songList_b[37], songList_b[38], songList_b[39]);

        buttonAudio = new MusicPlayer("res/audioButton.wav");

        jCheckBox = new JCheckBox("", false);
        setJBoxParameters();
        add(jCheckBox);

        oldText = "";

        jTextField = new JTextField();
        setJTextParameters();
        add(jTextField);
    }

    public void initTimer(){
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        songListAnim.runAnimation();
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

        songListAnim.drawAnimation(g2d, 0, -1);

        if(change1) g2d.drawImage(goBack_h, backX, y1, null);
        else g2d.drawImage(goBack_nh, backX, y1, null);

        g2d.setColor(Color.red);
        g2d.draw(rect1);

        if(change2) g2d.drawImage(playSong_h, playX, y1, null);
        else g2d.drawImage(playSong_nh, playX, y1, null);

        g2d.setColor(Color.blue);
        g2d.draw(rect2);

        if(change3) g2d.drawImage(viewScores_h, scoresX, y1, null);
        else g2d.drawImage(viewScores_nh, scoresX, y1, null);

        g2d.setColor(Color.magenta);
        g2d.draw(rect3);
    }

    public void setJTextParameters(){
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
    }

    public void setJBoxParameters(){
        jCheckBox.setBounds(x2 + 65, 446, 150, 29);
        jCheckBox.setOpaque(false);
        jCheckBox.setIcon(new ImageIcon("res/images/checkBoxImages/unchecked.png"));
        jCheckBox.setSelectedIcon(new ImageIcon("res/images/checkBoxImages/checked.png"));
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
