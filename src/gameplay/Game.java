package gameplay;

import menuStuff.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.commons.lang3.time.StopWatch;

import javax.swing.*;

public class Game extends Canvas implements Runnable {

    private JFrame jFrame;

    private Thread thread;
    private boolean running = false;
    private int frames;

    private BufferStrategy bs;

    public Handler handler;

    public static BufferedImage spriteSheet;

    private ArrayList<Coordinate>[] LettersTiming;

    private HUD hud;

    private Spawn spawn;

    private ShowHitScores showHitScores;

    private InputStreamReader input;

    public static PhysicalKey[] ArrayLetters;
    private char[] positionLetters;
    private int ColBase, Row, ColHover;
    private int posX, posY, posINX;

    public static int REDLINESY;

    public static StopWatch stopWatch;
    public static StopWatch stopWatch2;
    private boolean started;

    private MusicPlayer song;

    private int checkFPS;

    private String selectedSong;

    public Game(JFrame jFrame, String selectedSong){
        BufferedImageLoader loader = new BufferedImageLoader();
        spriteSheet = loader.loadImage("/spriteSheet.png");

        this.jFrame = jFrame;

        handler = new Handler();

        stopWatch = new StopWatch();
        stopWatch2 = new StopWatch();
        started = false;

        LettersTiming = new ArrayList[26];

        this.selectedSong = selectedSong;

        REDLINESY = 210;

        for(int i = 0; i < 26; i++){
            LettersTiming[i] = new ArrayList<>();
        }

        try { //gets notes from a file.txt
            getNotes("res/notes/notes_" + selectedSong + ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        physicalLettersCreation();

        hud = new HUD(handler);

        spawn = new Spawn(handler, LettersTiming);

        showHitScores = new ShowHitScores(handler);

        song = new MusicPlayer("res/songs/" + selectedSong + ".wav");
        try {
            song.createAudio();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
        stopWatch2.start();
    }

    public synchronized void stop() {
        try{
            thread.join();
            running = false;
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime(); // get current time to the nanosecond
        double amountOfTicks = 60.0; // set the number of ticks
        double ns = 1000000000 / amountOfTicks; // this determines how many times we can devide 60 into 1e9 of nano seconds or about 1 second
        double delta = 0; // change in time (delta always means a change like delta v is change in velocity)
        long timer = System.currentTimeMillis(); // get current time
        frames = 0; // set frame variable
        while(running){
            long now = System.nanoTime(); // get current time in nonoseconds durring current loop
            delta += (now - lastTime) / ns; // add the amount of change since the last loop
            lastTime = now;  // set lastTime to now to prepare for next loop
            while(delta >= 1){
                // one tick of time has passed in the game this
                //ensures that we have a steady pause in our game loop
                //so we don't have a game that runs way too fast
                //basically we are waiting for  enough time to pass so we
                // have about 60 frames per one second interval determined to the nanosecond (accuracy)
                // once this pause is done we render the next frame
                tick();
                delta--;  // lower our delta back to 0 to start our next frame wait
            }
            if(running) render(); // render the visuals of the game
            frames++; // note that a frame has passed
            if(System.currentTimeMillis() - timer > 1000 ){ // if one second has passed
                timer+= 1000; // add a thousand to our timer for next time
                checkFPS = frames;
                //System.out.println("FPS: " + frames); // print out how many frames have happend in the last second
                frames = 0; // reset the frame count for the next second
            }
        }
        stop(); // no longer running stop the thread
    }

    private void tick(){
        if(stopWatch2.getTime() >= 2000 && !started) {
            stopWatch.start();
            try {
                song.playTrack();
            } catch (Exception e) {
                e.printStackTrace();
            }
            started = true;
            stopWatch2.stop();
        }

        handler.tick();
        showHitScores.tick();
        hud.tick();
        spawn.tick();

        if(stopWatch.getTime() >= 10000 && song.clip != null && !song.clip.isActive()){ //the game ends
            stopWatch.stop();
            ResultPane resultPane = new ResultPane(jFrame, selectedSong, hud.getScore(), hud.getScoreCounts());
            jFrame.setContentPane(resultPane);
            jFrame.revalidate();
            running = false;
        }
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);


        g2d.setRenderingHints(rh);

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, MyFrame.WIDTH, MyFrame.HEIGHT);

        handler.render(g2d);
        showHitScores.render(g2d);
        hud.render(g2d);

        g2d.setColor(new Color(45, 45, 45));
        g2d.drawLine(posINX, REDLINESY + 64 / 2 + 32, posINX + 70 * 10, REDLINESY + 64 / 2 + 32);
        g2d.drawLine(posINX, REDLINESY + 64 / 2 - 32, posINX + 70 * 10, REDLINESY + 64 / 2 - 32);

        g2d.setFont(new Font("Arial", Font.PLAIN, 18));
        g2d.setColor(Color.white);

        g2d.drawString("FPS : " + checkFPS, MyFrame.WIDTH - 93, MyFrame.HEIGHT - 10);
        g2d.drawString(stopWatch.toString() + "", 8, 45);
        g2d.drawString(stopWatch2.toString() + "", 20, 260);
        g2d.drawString(OverlayPanel.gameLaunched + "", 20, 280);

        g2d.drawLine(posINX, REDLINESY + 64 / 8 + 10, posINX + 70 * 10, REDLINESY + 64 / 8 + 10);

        if(stopWatch.getTime() <= 6000) {
            g2d.setFont(new Font("Arial", Font.BOLD, 25));
            g2d.drawString((((stopWatch.getTime() - 6000) / 1000) * -1) + "", 470, 100);
        }

        if(song.clip != null) g2d.drawString(song.clip.getLongFramePosition() + "", 20, 230);

        g2d.dispose();
        bs.show();
    }

    public void getNotes(String file) throws IOException {
        input = new InputStreamReader(new FileInputStream(file));
        int data = input.read();
        int indexRow = 0;
        int indexCol = 0;
        String word = "";
        while(data != -1){
            if((char)data != '\n') {
                if ((char) data == '-') {
                    indexRow++;
                    indexCol = 0;
                } else if ((char) data == '.') {
                    LettersTiming[indexRow].add(new Coordinate());
                    LettersTiming[indexRow].get(indexCol).setStart(Long.parseLong(word));
                    word = "";
                } else if ((char) data == '|') {
                    LettersTiming[indexRow].get(indexCol).setFinish(Long.parseLong(word));
                    indexCol++;
                    word = "";
                } else word += (char) data;
            }
            data = input.read();
        } input.close();
    }

    public void physicalLettersCreation(){
        ArrayLetters = new PhysicalKey[26];
        Row = 1; ColBase = 1; ColHover = 2;
        for(int i = 0; i < 26; i++){
            if(Row == 14) {
                ColBase += 3;
                ColHover += 3;
                Row = 1;
            }
            ArrayLetters[i] = new PhysicalKey(0, 0, ID_PHY.values()[i], (char)(i + 65), Row, ColBase, Row, ColHover);
            Row++; //hud and handler for physicalKey are just for testing "AUTOPLAY"
        }
        fillPosLetters();

        posX = 150; posY = 340;
        posINX = posX;
        for(int j = 0; j < 26; j++){
            if(positionLetters[j] == 'A'){
                posY += 70;
                posX = posINX + 25;
            }
            if(positionLetters[j] == 'Z'){
                posY += 70;
                posX = posINX + 55;
            }
            for(int k = 0; k < 26; k++){
                if(positionLetters[j] == ArrayLetters[k].getC()){
                    ArrayLetters[k].setX(posX);
                    ArrayLetters[k].setY(posY);
                    ArrayLetters[k].setLine(posX, posY);
                    handler.addObject(ArrayLetters[k]);
                    posX += 70;
                }
            }
        }

    }

    public void fillPosLetters(){
        positionLetters = new char[26];
        positionLetters[0] = 'Q'; positionLetters[1] = 'W'; positionLetters[2] = 'E'; positionLetters[3] = 'R';
        positionLetters[4] = 'T'; positionLetters[5] = 'Y'; positionLetters[6] = 'U'; positionLetters[7] = 'I';
        positionLetters[8] = 'O'; positionLetters[9] = 'P'; positionLetters[10] = 'A'; positionLetters[11] = 'S';
        positionLetters[12] = 'D'; positionLetters[13] = 'F'; positionLetters[14] = 'G'; positionLetters[15] = 'H';
        positionLetters[16] = 'J'; positionLetters[17] = 'K'; positionLetters[18] = 'L'; positionLetters[19] = 'Z';
        positionLetters[20] = 'X'; positionLetters[21] = 'C'; positionLetters[22] = 'V'; positionLetters[23] = 'B';
        positionLetters[24] = 'N'; positionLetters[25] = 'M';
    }
}
