package menuStuff;

import gameplay.Game;
import gameplay.MusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.stream.IntStream;

public class ResultPane extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    private JFrame jFrame;
    private int score;
    private int[] scoreCounts;
    private double acc;
    private char vote;
    private String songName;

    private BufferedImageLoader loader;
    private BufferedImage background, backgroundText;

    private boolean CAM;

    private final int DELAY = 5;
    private Timer timer;

    private BufferedImage[] goBackImages, saveScoreImages, retryImages;
    private Animation goBackAnim, saveScoreAnim, retryAnim;

    private MusicPlayer buttonAudio;

    private int backX, saveX, retryX;

    private Font textFont;

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

    private OutputStreamWriter output;

    private LocalDateTime ldt;
    private String dateNow;

    public ResultPane(JFrame jFrame, String songName, boolean CAM, int score, int[] scoreCounts){
        this.jFrame = jFrame;
        this.score = score;
        this.scoreCounts = scoreCounts;
        this.songName = songName;
        this.CAM = CAM;
        setup();
        initTimer();
    }

    public void setup(){
        addMouseListener(this);
        addMouseMotionListener(this);

        setBounds(0, 0, MyFrame.WIDTH, MyFrame.HEIGHT); //for the real game

        setOpaque(true);

        loader = new BufferedImageLoader();

        background = loader.loadImage("res/images/backgrounds/background_ResultPane.png");
        backgroundText = loader.loadImage("res/images/backgrounds/background_resultPane_text.png");

        textFont = new Font("Arial Rounded MT Bold", Font.PLAIN, 30);

        backX = 57;
        width1 = 275;
        height1 = 50;
        x1 = backX + 45;
        y1 = 480;
        rect1= new Rectangle2D.Float(x1, y1 + 19, width1, height1);

        saveX = backX + 300;
        x2 = saveX + 45;
        rect2= new Rectangle2D.Float(x2, y1 + 19, width1, height1);

        retryX = saveX + 300;
        x3 = retryX + 45;
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

        saveScoreImages = new BufferedImage[7];
        for(int i = 0; i < 7; i++) {
            saveScoreImages[i] = loader.loadImage("res/images/saveScoreButton/savescore_button000" + i + ".png");
        }

        saveScoreAnim = new Animation(1, this
                , saveScoreImages[0],  saveScoreImages[1], saveScoreImages[2], saveScoreImages[3]
                , saveScoreImages[4],  saveScoreImages[5], saveScoreImages[6]);

        retryImages = new BufferedImage[7];
        for(int i = 0; i < 7; i++) {
            retryImages[i] = loader.loadImage("res/images/retryButton/retry_button000" + i + ".png");
        }

        retryAnim = new Animation(1, this
                , retryImages[0],  retryImages[1], retryImages[2], retryImages[3]
                , retryImages[4],  retryImages[5], retryImages[6]);

        buttonAudio = new MusicPlayer("res/audioButton.wav");

        acc = (((scoreCounts[3] * 500) + (scoreCounts[2] * 250) + (scoreCounts[1] * 100)) /
                (IntStream.of(3, 2, 1, 0).map(i -> scoreCounts[i]).sum())) * 0.2;

        vote = acc == 100 ? 'P' : acc < 100 && acc >= 97 ? 'A' : acc < 97 && acc >= 94 ? 'B'
                : acc < 94 && acc >= 90 ? 'C' : acc < 90 && acc >= 50 ? 'D' : 'E';

        ldt = LocalDateTime.now();
        dateNow = ldt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
    }

    public void initTimer(){
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        saveScoreAnim.runAnimation(change2);
        goBackAnim.runAnimation(change1);
        retryAnim.runAnimation(change3);
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
        g2d.drawImage(backgroundText,0, 0, null);

        g2d.setFont(textFont);
        g2d.setColor(Color.white);

        int songNameWidth = g2d.getFontMetrics().stringWidth(songName);
        if(x1 + songNameWidth >= MyFrame.WIDTH) {
            g2d.drawString(songName.substring(0, songName.length() - 42) + "...", 173, 51);
        } else g2d.drawString(songName, x1, 60);

        g2d.drawString(score + "", 193, 123);
        int xPos = 190;
        for(int i = 3; i >= 0; i--){
            g2d.drawString(scoreCounts[i] + "", xPos, 226);
            xPos += 94;
        }

        g2d.drawString("" + new BigDecimal(String.valueOf(acc)).setScale(2, BigDecimal.ROUND_FLOOR)
                + "%", 260, 296);

        g2d.drawString("" + dateNow + "", 195, 430);

        if(vote == 'P') g2d.setColor(new Color(255, 0, 244));
        if(vote == 'A') g2d.setColor(new Color(246, 255, 21));
        if(vote == 'B') g2d.setColor(new Color(33, 224, 122));
        if(vote == 'C') g2d.setColor(new Color(0, 102, 255));
        if(vote == 'D') g2d.setColor(new Color(255, 140, 0));
        if(vote == 'E') g2d.setColor(new Color(255, 0, 0));
        g2d.drawString("" + vote, 193, 363);

        goBackAnim.drawAnimation(g2d, backX, y1);
        /*g2d.setColor(Color.red);
        g2d.draw(rect1);*/

        saveScoreAnim.drawAnimation(g2d, saveX, y1);
        /*g2d.setColor(Color.blue);
        g2d.draw(rect2);*/

        retryAnim.drawAnimation(g2d, retryX, y1);
        /*g2d.setColor(Color.magenta);
        g2d.draw(rect3);*/

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if(!CAM) {
            if (rect1.contains(x, y)) { //ACCEPT
                try {
                    saveScore();
                } catch (IOException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                SelectGlass selectGlass = new SelectGlass(jFrame);
                timer.stop();
                jFrame.setContentPane(selectGlass);
                jFrame.revalidate();
            }
        }

        if(!CAM) {
            if (rect2.contains(x, y)) { //RETRY / RESTART
                timer.stop();
                Game game = new Game(jFrame, songName, false);
                jFrame.setContentPane(game);
                jFrame.revalidate();
                game.start();
            }
        }

        if(rect3.contains(x, y)){ //BACK TO SONG SELECTION = REFUSE
            SelectGlass selectGlass = new SelectGlass(jFrame);
            timer.stop();
            jFrame.setContentPane(selectGlass);
            jFrame.revalidate();
        }
    }

    public void saveScore() throws IOException {
        output = new OutputStreamWriter(new FileOutputStream("res/scores/scores_" + songName + ".txt", true));
        output.write(score + "|" + scoreCounts[3] + "," + scoreCounts[2] + "," + scoreCounts[1] + ","
                + scoreCounts[0] + "#" + vote + "@" + new BigDecimal(String.valueOf(acc)).setScale(2, BigDecimal.ROUND_FLOOR) + "[" + dateNow + "-" + "\n");
        output.close();
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
