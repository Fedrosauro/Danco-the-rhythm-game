package gameplay;

import java.util.ArrayList;

public class Spawn {

    private Handler handler;
    private ArrayList<Coordinate>[] lettersTiming;

    private PhantomKey GENERAL;

    private int row, col, calcDelay;

    private boolean CAM;

    public Spawn(Handler handler, ArrayList<Coordinate>[] lettersTiming, boolean checkAutoMode){
        this.handler = handler;
        this.lettersTiming = lettersTiming;
        this.CAM = checkAutoMode;
        calcDelay = - 1915 - 4000;
    }

    public void tick(){
        row = 1; col = 3;
        for(int i = 0; i < lettersTiming.length; i++) {
            if(row == 14) {
                col += 3;
                row = 1;
            }
            if (!lettersTiming[i].isEmpty()) {
                /*switch (Game.gameLaunched){ // maybe we don't need this
                    case 0 : { calcDelay = - 1915 - 4000; break; }
                    default : { calcDelay = - 1915 - 4000; break; }
                }*/
                if (Game.stopWatch.getTime() >= lettersTiming[i].get(0).getStart() + calcDelay) {
                    GENERAL = new PhantomKey(Game.ArrayLetters[i].getX(),- 50,
                            ID_PHA.values()[i], ID_PHY.values()[i], handler, 3, (char)(i + 65), row, col, CAM);
                    handler.addPhantomObject(GENERAL, i);
                    lettersTiming[i].remove(0);
                }
            }
            row++;
        }
    }
}
