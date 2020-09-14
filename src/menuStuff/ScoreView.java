package menuStuff;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ScoreView extends JPanel {

    private InputStreamReader input;

    private String scoresPath;

    public static int WIDTH = MyFrame.WIDTH - 100, HEIGHT = 430;

    public Font font22, font25;

    private MyJScrollPane jScrollPane;
    private String[] columns = {"SCORE", "NOTES", "VOTE", "ACCURACY", "DATE"};
    private ArrayList<String> preData;
    private String[][] data;

    public ScoreView(String song) throws IOException {
        scoresPath = "res/scores/scores_" + song + ".txt";
        setup();
    }

    public void setup() throws IOException {
        setBackground(new Color(113, 113, 113));
        setOpaque(true);
        setLayout(null);
        setBounds((MyFrame.WIDTH - WIDTH) / 2, 15, WIDTH, HEIGHT);

        font22 = null;
        font25 = null;

        try {
            font22 = YoureGoneFont.createFont(22);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            font25 = YoureGoneFont.createFont(25);
        } catch (Exception e) {
            e.printStackTrace();
        }

        preData = new ArrayList<>();
        readScores();

        MyJTable table = new MyJTable(data, columns);
        table.setFillsViewportHeight(true);
        jScrollPane = new MyJScrollPane(table, 0, 0, WIDTH, HEIGHT);
        add(jScrollPane);
    }

    public void readScores() throws IOException {
        input = new InputStreamReader(new FileInputStream(scoresPath));
        int dataR = input.read();
        String word = "";
        while(dataR != -1){
            if((char) dataR != '\n') {
                word += (char) dataR;
            } else{
                preData.add(word);
                word = "";
            }
            dataR = input.read();
        } input.close();

        orderScores();
        data = new String[preData.size()][columns.length];
        fillData();
    }

    public void fillData(){
        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < data[i].length; j++){
                if(preData.get(i) != null) {
                    switch (j) {
                        case 0: {
                            data[i][j] = getNumber(preData.get(i)) + "";
                            break;
                        }
                        case 1: {
                            data[i][j] = getSequence(preData.get(i), '|', '#');
                            break;
                        }
                        case 2: {
                            data[i][j] = getSequence(preData.get(i), '#', '@');
                            break;
                        }
                        case 3: {
                            data[i][j] = getSequence(preData.get(i), '@', '[') + "%";
                            break;
                        }
                        case 4: {
                            data[i][j] = getSequence(preData.get(i), '[', '-');
                            break;
                        }
                    }
                }
            }
        }
    }

    public void orderScores(){
        for (int i = 1; i < preData.size(); ++i) {
            int key = getNumber(preData.get(i));
            String keyString = preData.get(i);
            int j = i - 1;
            while (j >= 0 && getNumber(preData.get(j)) < key) {
                preData.set(j + 1, preData.get(j));
                j = j - 1;
            }
            preData.set(j + 1, keyString);
        }
    }

    public Integer getNumber(String s){
        if(s.equals("")) return null;
        String n = "";
        for(int i = 0; i < s.length() && s.charAt(i) != '|'; i++){
            n += s.charAt(i);
        } return Integer.parseInt(n);
    }

    public String getSequence(String s, char c1, char c2){
        String n = "";
        int indS = s.indexOf(c1);
        int indF = s.indexOf(c2);
        for(int i = indS + 1; i < indF; i++){
            n += s.charAt(i);
        } return n;
    }

    class MyJTable extends JTable{

        public MyJTable(String[][] data, String[] columns){
            super(data, columns);
            setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));

            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            dtcr.setHorizontalAlignment(JLabel.CENTER);
            for(int i = 0; i < 5; i++) {
                getColumnModel().getColumn(i).setCellRenderer(dtcr);
            }

            setBackground(Color.black);
            setForeground(Color.white);
            setShowVerticalLines(false);
            setRowHeight(45);
            setCellSelectionEnabled(false);

            getTableHeader().setOpaque(false);
            getTableHeader().setForeground(Color.white);
            getTableHeader().setBackground(Color.black);
            getTableHeader().setReorderingAllowed(false);
            getTableHeader().setResizingAllowed(false);
            getTableHeader().setFont(font22);
            ((DefaultTableCellRenderer)getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

            DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
            dtcr1.setHorizontalAlignment(JLabel.CENTER);
            getColumnModel().getColumn(2).setCellRenderer(dtcr1);
            getColumnModel().getColumn(2).setMaxWidth(100);
        }

        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
            Component comp = super.prepareRenderer(renderer, row, col);
            Object value = getModel().getValueAt(row, col);
            if(value.equals("P") || value.equals("A") || value.equals("B") || value.equals("C") ||
                    value.equals("D") || value.equals("E"))
                comp.setFont(font25);
            if(value.equals("P")) comp.setForeground(new Color(255, 0, 244));
            if(value.equals("A")) comp.setForeground(new Color(246, 255, 21));
            if(value.equals("B")) comp.setForeground(new Color(33, 224, 122));
            if(value.equals("C")) comp.setForeground(new Color(0, 102, 255));
            if(value.equals("D")) comp.setForeground(new Color(255, 140, 0));
            if(value.equals("E")) comp.setForeground(new Color(255, 0, 0));
            return comp;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
