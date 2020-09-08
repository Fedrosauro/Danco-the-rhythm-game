package menuStuff;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Animation {

    private int speed, frames;  //frames = numbers of images that we have
    private int index = 0, count = 0; //index = what we're currently at
    //count = what we need to be at

    private BufferedImage images[]; //how many images we have
    private BufferedImage currentImg;

    private JPanel jPanel;

    public Animation(int speed, JPanel jPanel, BufferedImage... images){
        this.speed = speed;
        this.jPanel = jPanel;
        this.images = new BufferedImage[images.length];
        for(int i = 0; i < images.length; i++){
            this.images[i] = images[i];
        }
        this.frames = images.length;
    }

    public void runAnimation(){
        index++;
        if(index > speed) {
            index = 0;
            nextFrame();
        }
    }

    public void runAnimation(boolean change){
        index++;
        if(index > speed) {
            index = 0;
            nextFrame(change);
        }
    }

    public void runAnimationOnce(){ //just for the title
        index++;
        if(index > speed) {
            index = 0;
            nextFrametoFinal();
        }
    }

    private void nextFrametoFinal(){
        if (count > frames) { currentImg = images[images.length - 1]; return; }
        for (int j = 0; j < frames; j++){
            if (count == j) currentImg = images[j];
        } count ++;
    }

    private void nextFrame(boolean change){ //repeating the animation once it's over
        if(change) {
            for (int i = 0; i < frames; i++) if (count == i) currentImg = images[i];
            if (count < frames) count++;
        } else{
            for (int j = frames - 1; j >= 0; j--) if (count == j) currentImg = images[j];
            if (count > 0) count--;
        }
    }

    private void nextFrame(){ //repeating the animation once it's over
        for (int i = 0; i < frames; i++) if (count == i) currentImg = images[i];
        count++;
        if(count > frames) count = 0;
    }

    public void drawAnimation(Graphics2D g2d, int x, int y){
        g2d.drawImage(currentImg, x, y, null);
    }
}

