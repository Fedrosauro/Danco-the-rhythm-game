package menuStuff;

import javax.swing.*;
import java.awt.*;

public class MyJScrollPane extends JScrollPane {

    private int x, y, width, height;

    public MyJScrollPane(JList<String> list, int x, int y, int width, int height) {
        super(list);
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        setBounds(x, y, width, height);
        setBorder(javax.swing.BorderFactory.createEmptyBorder());
        getVerticalScrollBar().setUI(new MyScrollBarUI());
        getHorizontalScrollBar().setUI(new MyScrollBarUI());
        JPanel cornerBg = new JPanel();
        cornerBg.setBackground(Color.black);
        setCorner(JScrollPane.LOWER_RIGHT_CORNER, cornerBg);
    }

    public MyJScrollPane(ScoreView.MyJTable myJTable, int x, int y, int width, int height){
        super(myJTable);
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        setBounds(x, y, width, height);
        setBorder(javax.swing.BorderFactory.createEmptyBorder());
        getVerticalScrollBar().setUI(new MyScrollBarUI());
        getHorizontalScrollBar().setUI(new MyScrollBarUI());
        JPanel cornerBg = new JPanel();
        cornerBg.setBackground(Color.black);
        setCorner(JScrollPane.LOWER_RIGHT_CORNER, cornerBg);
    }
}
