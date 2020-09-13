package gameplay;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ShowHitScores {

    private Handler handler;

    private static int DELAY = 200;

    public int[] showHitScore;
    public int[] Xvalues;

    private BufferedImageLoader loader;
    private BufferedImage[] scoreImage;

    private long[] timeShow;

    public ShowHitScores(Handler handler){
        this.handler = handler;

        showHitScore = new int[26];
        Xvalues = new int[26];
        timeShow = new long[26];
        scoreImage = new BufferedImage[26];

        for(int i = 0; i < 26; i++){
            showHitScore[i] = -1;
            Xvalues[i] = (int)Game.ArrayLetters[i].getX();
        }

        loader = new BufferedImageLoader();
    }

    public void tick() {
        for(int i = 0; i < handler.phatomObjects.length; i++) {
            if (!handler.phatomObjects[i].isEmpty()) {
                GamePhantomObject tempObject = handler.phatomObjects[i].get(0);
                if (tempObject.scoreHIT != -1) {
                    showHitScore[tempObject.getId().toString().charAt(0) - 65] = tempObject.scoreHIT;
                    timeShow[tempObject.getId().toString().charAt(0) - 65] = Game.stopWatch.getTime() + DELAY;
                    switch (tempObject.scoreHIT){
                        case 0 : {
                            scoreImage[tempObject.getId().toString().charAt(0) - 65] =
                                    loader.loadImageV2("res/images/hud/X.png");
                            break; }
                        case 100 : {
                            scoreImage[tempObject.getId().toString().charAt(0) - 65] =
                                    loader.loadImageV2("res/images/hud/100.png");
                            break; }
                        case 250 : {
                            scoreImage[tempObject.getId().toString().charAt(0) - 65] =
                                    loader.loadImageV2("res/images/hud/250.png");
                            break; }
                        case 500 : {
                            scoreImage[tempObject.getId().toString().charAt(0) - 65] =
                                    loader.loadImageV2("res/images/hud/500.png");
                            break; }
                    }
                }
            }
        }
    }

    public void render(Graphics2D g2d) {
        for(int i = 0; i < 26; i++){
            if(showHitScore[i] != -1){
                if(timeShow[i] - Game.stopWatch.getTime() >= 0){
                    g2d.drawImage(scoreImage[i], Xvalues[i], Game.REDLINESY + 60, null);
                } else{
                    showHitScore[i] = -1;
                }
            }
        }
    }

}
