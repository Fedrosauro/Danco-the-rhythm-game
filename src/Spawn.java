import java.awt.*;
import java.util.ArrayList;

public class Spawn {

    private Handler handler;
    private ArrayList<Coordinate>[] lettersTiming;

    private PhantomKey GENERAL;

    private int row, col;

    public Spawn(Handler handler, ArrayList<Coordinate>[] lettersTiming){
        this.handler = handler;
        this.lettersTiming = lettersTiming;
    }

    public void tick(){
        row = 1; col = 3;
        for(int i = 0; i < lettersTiming.length; i++) {
            if(row == 14) {
                col += 3;
                row = 1;
            }
            if (!lettersTiming[i].isEmpty()) {
                if (Game.stopWatch.getTime() >= lettersTiming[i].get(0).getStart() - 1300 - 4000) {
                    GENERAL = new PhantomKey(Game.ArrayLetters[i].getX(),-50,
                            ID_PHA.values()[i], ID_PHY.values()[i], handler, 3, (char)(i + 65), row, col);
                    handler.addPhantomObject(GENERAL, i);
                    lettersTiming[i].remove(0);
                }
            }
            row++;
        } // 1350 delay speed = 3; (1.5 factor) with no opengl acceleration
          // 1350 delay speed = 3 with opengl acceleration
    }

    public void render(Graphics2D g2d){

    }
}
