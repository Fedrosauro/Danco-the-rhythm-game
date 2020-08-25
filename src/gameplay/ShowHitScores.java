package gameplay;

import java.awt.*;

public class ShowHitScores {

    private Handler handler;

    private static int DELAY = 200;

    public int[] showHitScore;
    public int[] Xvalues;

    private long[] timeShow;

    private Color[] colors;

    public ShowHitScores(Handler handler){
        this.handler = handler;

        showHitScore = new int[26];
        Xvalues = new int[26];
        timeShow = new long[26];
        colors = new Color[26];

        for(int i = 0; i < 26; i++){
            showHitScore[i] = -1;
            Xvalues[i] = (int)Game.ArrayLetters[i].getX();
        }
    }

    public void tick() {
        for(int i = 0; i < handler.phatomObjects.length; i++) {
            if (!handler.phatomObjects[i].isEmpty()) {
                GamePhantomObject tempObject = handler.phatomObjects[i].get(0);
                if (tempObject.scoreHIT != -1) {
                    showHitScore[tempObject.getId().toString().charAt(0) - 65] = tempObject.scoreHIT;
                    timeShow[tempObject.getId().toString().charAt(0) - 65] = Game.stopWatch.getTime() + DELAY;
                    switch (tempObject.scoreHIT){
                        case 0 : { colors[tempObject.getId().toString().charAt(0) - 65] = Color.red; break; }
                        case 100 : { colors[tempObject.getId().toString().charAt(0) - 65] = Color.yellow; break; }
                        case 250 : { colors[tempObject.getId().toString().charAt(0) - 65] = Color.green; break; }
                        case 500 : { colors[tempObject.getId().toString().charAt(0) - 65] = Color.cyan; break; }
                    }
                }
            }
        }
    }

    public void render(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 18));
        g2d.setColor(Color.white);
        for(int i = 0; i < 26; i++){
            if(showHitScore[i] != -1){
                if(timeShow[i] - Game.stopWatch.getTime() >= 0){
                    g2d.setColor(colors[i]);
                    g2d.drawString(showHitScore[i] != 0 ? showHitScore[i] + "" : "  X  ", Xvalues[i] + 15, Game.REDLINESY + 90);
                } else{
                    showHitScore[i] = -1;
                    colors[i] = Color.black;
                }
            }
        }
    }

}
