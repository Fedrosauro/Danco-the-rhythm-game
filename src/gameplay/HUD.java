package gameplay;

import menuStuff.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD {

    private Handler handler;

    private BufferedImageLoader loader;

    private BufferedImage score_panel;

    private int score;

    private static int DELAY = 200;

    private int[] scoreCounts;

    public HUD(Handler handler){
        this.handler = handler;
        scoreCounts = new int[4];
        loader = new BufferedImageLoader();

        score_panel = loader.loadImage("res/images/backgrounds/background_HUD.png");
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
        g2d.drawImage(score_panel, 5, 0, null);
        g2d.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 21));
        g2d.setColor(Color.white);
        g2d.drawString(score + "", 22, 41);

        g2d.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
        int xPos = 21;
        for(int i = 3; i >= 0; i--){
            g2d.drawString(scoreCounts[i] + "", xPos, 167);
            xPos += 42;
        }
    }

    public int getScore() {
        return score;
    }

    public int[] getScoreCounts() {
        return scoreCounts;
    }
}
