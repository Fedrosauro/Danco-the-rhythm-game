package gameplay;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class PhysicalKey extends GameObject{

    private Line2D pyline;

    private BufferedImage PY_Key_img_b;
    private BufferedImage PY_Key_img_h;

    private char c;

    private int RCoordinateX, RCoordinateY, rectHeight, heightR, heightY, heightG, heightB; //for drawing stuff


    public PhysicalKey(float x, float y, ID_PHY idPHY, char c, int Rowbase, int Colbase, int Rowhover, int Colhover) {
        super(x, y, idPHY);
        this.c = c;

        SpriteSheet ss = new SpriteSheet(Game.spriteSheet);

        PY_Key_img_b = ss.grabImage(Rowbase, Colbase, 64, 64);
        PY_Key_img_h = ss.grabImage(Rowhover, Colhover, 64, 64);

        //just for drawing stuff
        rectHeight = 48;
        heightR = 10;
        heightY = 5;
        heightG = 5;
        heightB = rectHeight - heightR - heightY - heightG;

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g2d) {
        //real gameplay
        if(Game.keyInput.pressedKeys.get(c + "") != null && Game.keyInput.pressedKeys.get(c + ""))
            g2d.drawImage(PY_Key_img_h, (int)x, (int)y, null);
        else g2d.drawImage(PY_Key_img_b, (int)x, (int)y, null);

        //useful part for testing
        if(c == 'Q') {
            /*g2d.setColor(Color.white);
            g2d.drawRect(RCoordinateX - 1, RCoordinateY - 1, 11, rectHeight + 1); //160 height 10 width
*/
            g2d.setColor(Color.red);
            g2d.fillRect(RCoordinateX, RCoordinateY, 10, heightR);

            g2d.setColor(Color.yellow);
            g2d.fillRect(RCoordinateX, RCoordinateY + heightR, 10, heightY);

            g2d.setColor(new Color(56, 194, 0));
            g2d.fillRect(RCoordinateX, RCoordinateY + heightR + heightY, 10, heightG);

            g2d.setColor(Color.cyan);
            g2d.fillRect(RCoordinateX, RCoordinateY + heightR + heightY + heightG, 10, heightB);
        }

        g2d.setColor(Color.red);
        g2d.draw(getLine());
    }

    @Override
    public Line2D getLine() {
        return pyline;
    }

    public void setLine(int x, int y){
        pyline = new Line2D.Double(x + 64 / 2, Game.REDLINESY + 64 / 2, x + 64 / 2, Game.REDLINESY - 64 / 4);
        RCoordinateX = (int)getLine().getP2().getX() - 60; //this is not relevant
        RCoordinateY = (int)getLine().getP2().getY();
    }

    public char getC() {
        return c;
    }
}
