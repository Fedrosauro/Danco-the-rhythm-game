package gameplay;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BufferedImageLoader {

    //this is where we load our spritesheet
    public BufferedImage img;

    public BufferedImage loadImage(String path){
        try {
            img = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        } return img;
    }

    public BufferedImage loadImageV2(String path){
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        } return img;
    }
}
