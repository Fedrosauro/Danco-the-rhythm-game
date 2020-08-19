import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage sprite;

    public SpriteSheet(BufferedImage ss){
        this.sprite = ss;
    }

    public BufferedImage grabImage(int row, int col, int width, int height){
        BufferedImage img = sprite.getSubimage((col * 64) - 64, (row * 64) - 64, width, height);
        return img;
    }
}
