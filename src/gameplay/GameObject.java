package gameplay;

import java.awt.*;
import java.awt.geom.Line2D;

public abstract class GameObject {

    protected float x, y;
    protected ID_PHY id_phy; //for physical objects

    public GameObject(float x, float y, ID_PHY id_phy){
        this.x = x;
        this.y = y;
        this.id_phy = id_phy;
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

    public void setId(ID_PHY id) {
        this.id_phy = id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public ID_PHY getId() {
        return id_phy;
    }
}
