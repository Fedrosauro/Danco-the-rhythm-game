package gameplay;

import java.awt.*;
import java.awt.geom.Line2D;

public abstract class GamePhantomObject {

    protected float x, y;
    protected int scoreHIT;
    protected ID_PHA id_pha;

    public GamePhantomObject(float x, float y, ID_PHA id_pha){
        this.x = x;
        this.y = y;
        this.id_pha = id_pha;
        scoreHIT = -1;
    }

    public abstract void tick();
    public abstract void render(Graphics2D g2d);
    public abstract Line2D getLine();

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setId(ID_PHA id) { this.id_pha = id; }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public ID_PHA getId() {
        return id_pha;
    }

}
