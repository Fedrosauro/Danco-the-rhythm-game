package menuStuff;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class SubScoreView extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    private JFrame jFrame;

    private Font buttonsFont;

    private final int DELAY = 10;
    private Timer timer;

    private Stroke defaultStroke;

    private Rectangle2D rect1; //go back
    private int x1, y1;
    private int width1, height1;
    private boolean change1;

    public SubScoreView(JFrame jFrame){
        this.jFrame = jFrame;
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
        x1 = (MyFrame.WIDTH - width1) / 2;
        y1 = 500;
        rect1= new Rectangle2D.Float(x1, y1, width1, height1);

        change1 = false;
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
    }

    public void drawButtons(Graphics2D g2d) {
        g2d.setFont(buttonsFont);
        int button1W = g2d.getFontMetrics().stringWidth("GO BACK");

        if (change1) {
            g2d.setColor(Color.white);
            g2d.fillRect(x1, y1, width1, height1);
            g2d.setColor(Color.black);
            g2d.drawString("GO BACK", x1 + (width1 - button1W) / 2, y1 + height1 / 2 + 8);
        } else {
            g2d.setColor(Color.white);
            g2d.drawRect(x1, y1, width1, height1);
            g2d.setColor(Color.white);
            g2d.drawString("GO BACK", x1 + (width1 - button1W) / 2, y1 + height1 / 2 + 8);
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
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rect1.contains(x, y)) {
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
