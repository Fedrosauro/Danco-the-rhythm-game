package menuStuff;

import gameplay.Game;
import gameplay.OverlayPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
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

    private boolean CAM;

    private final int DELAY = 10;
    private Timer timer;

    public static Stroke defaultStroke;

    private Font buttonsFont;
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

        setBackground(Color.black);
        setOpaque(true);

        buttonsFont = new Font("Arial", Font.PLAIN, 25);
        textFont = new Font("Arial", Font.PLAIN, 30);

        width1 = 200;
        height1 = 50;
        x1 = 190;
        y1 = 500;
        rect1= new Rectangle2D.Float(x1, y1, width1, height1);

        x2 = x1 + width1 + 20;
        rect2= new Rectangle2D.Float(x2, y1, width1, height1);

        x3 = x2 + width1 + 20;
        rect3= new Rectangle2D.Float(x3, y1, width1, height1);

        change1 = false;
        change2 = false;
        change3 = false;

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
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        defaultStroke = g2d.getStroke();

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        g2d.setFont(textFont);

        g2d.setColor(Color.white);

        int songNameWidth = g2d.getFontMetrics().stringWidth(songName);
        if(x1 + songNameWidth >= MyFrame.WIDTH) {
            g2d.drawString("Song : " + songName.substring(0, songName.length() - 18) + "...", x1, 60);
        } else g2d.drawString("Song : " + songName, x1, 60);

        g2d.drawString("Your score : " + score, x1, 100);
        int xPos = x1;
        for(int i = 3; i >= 0; i--){
            g2d.drawString((i == 3 ? 500 : i == 2 ? 250 : i == 1 ? 100 : "X") + "", xPos, 150);
            g2d.drawString(scoreCounts[i] + "", xPos, 200);
            xPos += 120;
        }

        g2d.drawString("Accuracy : "
                + new BigDecimal(String.valueOf(acc)).setScale(2, BigDecimal.ROUND_FLOOR)
                + "%", x1, 240);

        g2d.drawString("Vote : " + vote, x1, 280);
        g2d.drawString("Date : " + dateNow + "", x1, 330);

        drawButtons(g2d);

        g2d.setStroke(defaultStroke);
    }

    public void drawButtons(Graphics2D g2d){
        g2d.setFont(buttonsFont);
        int button1W = g2d.getFontMetrics().stringWidth("SAVE SCORE");
        int button2W = g2d.getFontMetrics().stringWidth("RETRY");
        int button3W = g2d.getFontMetrics().stringWidth("GO BACK");

        if(!CAM) {
            if (change1) {
                g2d.setColor(Color.white);
                g2d.fillRect(x1, y1, width1, height1);
                g2d.setColor(Color.black);
                g2d.drawString("SAVE SCORE", x1 + (width1 - button1W) / 2, y1 + height1 / 2 + 8);
            } else {
                g2d.setColor(Color.white);
                g2d.drawRect(x1, y1, width1, height1);
                g2d.setColor(Color.white);
                g2d.drawString("SAVE SCORE", x1 + (width1 - button1W) / 2, y1 + height1 / 2 + 8);
            }
        }

        if(!CAM) {
            if (change2) {
                g2d.setColor(Color.white);
                g2d.fillRect(x2, y1, width1, height1);
                g2d.setColor(Color.black);
                g2d.drawString("RETRY", x2 + (width1 - button2W) / 2, y1 + height1 / 2 + 8);
            } else {
                g2d.setColor(Color.white);
                g2d.drawRect(x2, y1, width1, height1);
                g2d.setColor(Color.white);
                g2d.drawString("RETRY", x2 + (width1 - button2W) / 2, y1 + height1 / 2 + 8);
            }
        }

        if(change3){
            g2d.setColor(Color.white);
            g2d.fillRect(x3, y1, width1, height1);
            g2d.setColor(Color.black);
            g2d.drawString("GO BACK",x3 + (width1 - button3W)/2, y1 + height1/2 + 8);
        } else{
            g2d.setColor(Color.white);
            g2d.drawRect(x3, y1, width1, height1);
            g2d.setColor(Color.white);
            g2d.drawString("GO BACK",x3 + (width1 - button3W)/2, y1 + height1/2 + 8);
        }
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
                OverlayPanel overlayPanel = new OverlayPanel(jFrame, songName, false);
                jFrame.setContentPane(overlayPanel);
                overlayPanel.doSetup();
                jFrame.revalidate();
                overlayPanel.startGame();
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
                + scoreCounts[0] + "#" + vote + "@" + acc + "[" + dateNow + "-" + "\n");
        output.close();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rect1.contains(x, y)) {
            change1 = true;
        } else change1 = false;

        if (rect2.contains(x, y)) {
            change2 = true;
        } else change2 = false;

        if (rect3.contains(x, y)) {
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
