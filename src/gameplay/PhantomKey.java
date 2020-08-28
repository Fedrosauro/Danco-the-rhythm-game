package gameplay;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class PhantomKey extends GamePhantomObject{

    private Handler handler;

    private Line2D phline;

    private BufferedImage PH_Key_img;

    private float velY;

    private ID_PHY id_phy;

    private MusicPlayer clickSound;

    private int RCoordinateY, rectHeight, heightR, heightY, heightG, heightB;

    private char c;

    public PhantomKey(float x, float y, ID_PHA id_p, ID_PHY id_r, Handler handler, float velY, char c, int rb, int cb) {
        super(x, y, id_p);
        this.id_phy = id_r;
        this.handler = handler;
        this.c = c;
        this.velY = velY;

        phline = new Line2D.Double(x, y + 64 / 2, x + 64, y + 64 / 2);

        SpriteSheet ss = new SpriteSheet(Game.spriteSheet);
        PH_Key_img = ss.grabImage(rb, cb, 64, 64);

        for(int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);
            if (tempObject.getId() == id_phy) {
                RCoordinateY = (int)tempObject.getLine().getP2().getY();
            }
        }

        clickSound = new MusicPlayer("res/clickSoundReal.wav");

        rectHeight = 48;
        heightR = 10;
        heightY = 5;
        heightG = 5;
        heightB = rectHeight - heightR - heightY - heightG;

    }

    @Override
    public void tick() {
        y += velY;
        updateLine();
        if (getLine().getY1() >= RCoordinateY) checkScoreHIT();
    }

    public void checkScoreHIT(){
        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);
            if (tempObject.getId() == id_phy) {
                if (getLine().getY1() >= tempObject.getLine().getY1()) scoreHIT = 0;
                /*if (getLine().getY1() >= RCoordinateY + heightR + heightY + heightG + heightB/2
                        && getLine().getY1() < RCoordinateY + heightR + heightY + heightG + heightB){
                    scoreHIT = 500; //this if was made only for NPC tests
                }*/
                    if (OverlayPanel.keyInput.pressedKeys.get(c + "") != null && OverlayPanel.keyInput.pressedKeys.get(c + "")) {
                        if (getLine().getY1() >= RCoordinateY
                                && getLine().getY1() < RCoordinateY + heightR) scoreHIT = 0;
                        else if (getLine().getY1() >= RCoordinateY + heightR
                                && getLine().getY1() < RCoordinateY + heightR + heightY) scoreHIT = 100;
                        else if (getLine().getY1() >= RCoordinateY + heightR + heightY
                                && getLine().getY1() < RCoordinateY + heightR + heightY + heightG) scoreHIT = 250;
                        else if (getLine().getY1() >= RCoordinateY + heightR + heightY + heightG
                                && getLine().getY1() < RCoordinateY + heightR + heightY + heightG + heightB) scoreHIT = 500;
                        try {
                            clickSound.createAudio();
                            clickSound.playTrack();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            }
        }
    }


    public void updateLine(){
        phline.setLine(x, y + 64 / 2, x + 64, y + 64 / 2);
    }

    @Override
    public void render(Graphics2D g2d) {
        /*g2d.setColor(Color.green);
        g2d.drawRect((int)x - 1, (int)y - 1, 65, 65);*/

        g2d.drawImage(PH_Key_img, (int)x, (int)y, null);

        g2d.setColor(Color.magenta);

        g2d.setColor(Color.magenta);
        //g2d.draw(getLine());
    }

    @Override
    public Line2D getLine() {
        return phline;
    }

}
