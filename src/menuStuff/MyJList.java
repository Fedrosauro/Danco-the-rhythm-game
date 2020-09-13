package menuStuff;

import javax.swing.*;
import java.awt.*;

public class MyJList extends JList{

    public MyJList(DefaultListModel<String> list) {
        super(list);
        UIManager.put("List.focusCellHighlightBorder", BorderFactory.createEmptyBorder());
        setBackground(new Color(29, 29, 29));
        setForeground(Color.white); //text background
        setFont((new Font("Arial Rounded MT Bold", Font.PLAIN, 25)));
        setSelectionBackground(Color.white);
        setSelectionForeground(Color.black);
        setFixedCellHeight(30);
        setSelectedIndex(0);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

}
