package menuStuff;

import gameplay.OverlayPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class SubSongSelectionP extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    private JFrame jFrame;
    private SongSelectionP songSelectionP;

    private Font buttonsFont;

    private final int DELAY = 10;
    private Timer timer;

    private Stroke defaultStroke;

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

    public SubSongSelectionP(SongSelectionP songSelectionP, JFrame jFrame){
        this.jFrame = jFrame;
        this.songSelectionP = songSelectionP;
        setup();
        initTimer();
    }

    public void setup(){
        addMouseListener(this);
        addMouseMotionListener(this);

        setBackground(Color.black);
        setOpaque(true);
        setBounds(0, 0, MyFrame.WIDTH,  MyFrame.HEIGHT);

        buttonsFont = new Font("Arial", Font.PLAIN, 20);

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

        drawButtons(g2d);

        g2d.setColor(Color.white);

        g2d.drawString("Song list :", (MyFrame.WIDTH - SongSelectionP.WIDTH) / 2, (MyFrame.HEIGHT - SongSelectionP.HEIGHT) / 2 - 75);

        g2d.setStroke(defaultStroke);
    }

    public void drawButtons(Graphics2D g2d){
        g2d.setFont(buttonsFont);
        int button1W = g2d.getFontMetrics().stringWidth("GO BACK");
        int button2W = g2d.getFontMetrics().stringWidth("PLAY SONG");
        int button3W = g2d.getFontMetrics().stringWidth("VIEW SCORES");

        if(change1){
            g2d.setColor(Color.white);
            g2d.fillRect(x1, y1, width1, height1);
            g2d.setColor(Color.black);
            g2d.drawString("GO BACK",x1 + (width1 - button1W)/2, y1 + height1/2 + 8);
        } else{
            g2d.setColor(Color.white);
            g2d.drawRect(x1, y1, width1, height1);
            g2d.setColor(Color.white);
            g2d.drawString("GO BACK",x1 + (width1 - button1W)/2, y1 + height1/2 + 8);
        }

        if(change2){
            g2d.setColor(Color.white);
            g2d.fillRect(x2, y1, width1, height1);
            g2d.setColor(Color.black);
            g2d.drawString("PLAY SONG",x2 + (width1 - button2W)/2, y1 + height1/2 + 8);
        } else{
            g2d.setColor(Color.white);
            g2d.drawRect(x2, y1, width1, height1);
            g2d.setColor(Color.white);
            g2d.drawString("PLAY SONG",x2 + (width1 - button2W)/2, y1 + height1/2 + 8);
        }

        if(change3){
            g2d.setColor(Color.white);
            g2d.fillRect(x3, y1, width1, height1);
            g2d.setColor(Color.black);
            g2d.drawString("VIEW SCORES",x3 + (width1 - button3W)/2, y1 + height1/2 + 8);
        } else{
            g2d.setColor(Color.white);
            g2d.drawRect(x3, y1, width1, height1);
            g2d.setColor(Color.white);
            g2d.drawString("VIEW SCORES",x3 + (width1 - button3W)/2, y1 + height1/2 + 8);
        }
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

        if(rect2.contains(x, y)){
            timer.stop();
            OverlayPanel overlayPanel = new OverlayPanel(jFrame, (String)songSelectionP.getjList().getSelectedValue());
            jFrame.setContentPane(overlayPanel);
            overlayPanel.doSetup();
            jFrame.revalidate();
            overlayPanel.startGame();
        }

        if(rect3.contains(x, y)){
            ScoreGlass scoreGlass = null;
            try {
                scoreGlass = new ScoreGlass(jFrame, (String)songSelectionP.getjList().getSelectedValue());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            timer.stop();
            jFrame.setContentPane(scoreGlass);
            jFrame.revalidate();
        }
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