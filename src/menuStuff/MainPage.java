package menuStuff;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class MainPage extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    private JFrame jFrame;

    private final int DELAY = 10;
    private Timer timer;

    public static Stroke defaultStroke;

    private Rectangle2D rect1;
    private int x1, y1;
    private int width1, height1;
    private boolean change1;

    private Rectangle2D rect2;
    private int y2;
    private boolean change2;

    private Font titleFont;
    private Font buttonsFont;

    private int alpha;
    private int decrement = - 5;

    public MainPage(JFrame jFrame){
        this.jFrame = jFrame;
        setup();
        initTimer();
    }

    public void setup(){
        addMouseListener(this);
        addMouseMotionListener(this);

        setPreferredSize(new Dimension(MyFrame.WIDTH, MyFrame.HEIGHT));

        titleFont = new Font("Arial", Font.PLAIN, 30);
        buttonsFont = new Font("Arial", Font.PLAIN, 25);

        alpha = 255;

        width1 = 250; //things for buttons
        height1 = 80;
        x1 = (MyFrame.WIDTH - width1)/2;
        y1 = 160;
        rect1= new Rectangle2D.Float(x1, y1, width1, height1);
        change1 = false;

        y2 = y1 + height1 + 60;
        rect2= new Rectangle2D.Float(x1, y2, width1, height1);
        change2 = false;
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

        setBackground(new Color(0, 0, 0, alpha));

        g2d.setColor(Color.white);

        drawStuff(g2d);
        drawButtons(g2d);

        g2d.setStroke(defaultStroke);
    }

    public void drawStuff(Graphics2D g2d){
        g2d.setFont(titleFont);
        int titleWidth = g2d.getFontMetrics().stringWidth("GAME TEST V1");
        g2d.drawString("GAME TEST V1", (MyFrame.WIDTH - titleWidth) / 2, 100);
    }

    public void drawButtons(Graphics2D g2d){
        g2d.setFont(buttonsFont);
        int button1W = g2d.getFontMetrics().stringWidth("PLAY");
        int button2W = g2d.getFontMetrics().stringWidth("EXIT");
        if(change1){
            g2d.setColor(Color.white);
            g2d.fillRect(x1, y1, width1, height1);
            g2d.setColor(Color.black);
            g2d.drawString("PLAY",x1 + (width1 - button1W)/2, y1 + height1/2 + 8);
        } else{
            g2d.setColor(Color.white);
            g2d.drawRect(x1, y1, width1, height1);
            g2d.setColor(Color.white);
            g2d.drawString("PLAY",x1 + (width1 - button1W)/2, y1 + height1/2 + 8);
        }

        if(change2){
            g2d.setColor(Color.white);
            g2d.fillRect(x1, y2, width1, height1);
            g2d.setColor(Color.black);
            g2d.drawString("EXIT",x1 + (width1 - button2W)/2, y2 + height1/2 + 8);
        } else{
            g2d.setColor(Color.white);
            g2d.drawRect(x1, y2, width1, height1);
            g2d.setColor(Color.white);
            g2d.drawString("EXIT",x1 + (width1 - button2W)/2, y2 + height1/2 + 8);
        }
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
            change1 = true;
        } else change1 = false;

        if (rect2.contains(x, y)) {
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
