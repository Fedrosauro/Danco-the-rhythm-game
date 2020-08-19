import java.awt.*;

public class HUD {

    private Handler handler;

    private int score;

    private static int DELAY = 200;

    public int[] scoreCounts;

    public HUD(Handler handler){
        this.handler = handler;
        scoreCounts = new int[4];
    }

    public void tick(){
        updateScore();
    }

    public void updateScore(){
        for(int i = 0; i < handler.phatomObjects.length; i++) {
            if (!handler.phatomObjects[i].isEmpty()) {
                GamePhantomObject tempObject = handler.phatomObjects[i].get(0);
                if (tempObject.scoreHIT != -1) {
                    switch (tempObject.scoreHIT){
                        case 0 : { scoreCounts[0]++; break; }
                        case 100 : { scoreCounts[1]++; break; }
                        case 250 : { scoreCounts[2]++; break; }
                        case 500 : { scoreCounts[3]++; break; }
                    }
                    switch (tempObject.getId()) {
                        case B_PA: case A_PA: case C_PA: case D_PA: case E_PA: case F_PA: case G_PA:
                        case H_PA: case I_PA: case J_PA: case K_PA: case L_PA: case M_PA: case N_PA:
                        case O_PA: case P_PA: case Q_PA: case R_PA: case S_PA: case T_PA: case U_PA:
                        case V_PA: case W_PA: case X_PA: case Y_PA: case Z_PA:{
                            score += tempObject.scoreHIT;
                            handler.removePhantomObject(tempObject, i);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void render(Graphics2D g2d){
        g2d.setFont(new Font("Arial", Font.PLAIN, 18));
        g2d.setColor(Color.white);
        g2d.drawString("Total score : " + score, 5, 20);

        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.drawString(500 + "", 20, 70);
        g2d.drawString(scoreCounts[3] + "", 25, 90);
        g2d.drawString(250 + "", 50, 70);
        g2d.drawString(scoreCounts[2] + "", 55, 90);
        g2d.drawString(100 + "", 80, 70);
        g2d.drawString(scoreCounts[1] + "", 85, 90);
        g2d.drawString("  X  ", 110, 70);
        g2d.drawString(scoreCounts[0] + "", 115, 90);

        g2d.setFont(new Font("Arial", Font.PLAIN, 18));
    }

}
