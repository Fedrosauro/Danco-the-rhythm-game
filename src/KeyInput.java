import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class KeyInput implements ActionListener {

    private final static String PRESSED = "pressed ";
    private final static String RELEASED = "released ";

    public Map<String, Boolean> pressedKeys;

    private JPanel jPanel;

    public KeyInput(JPanel jPanel){
        this.jPanel = jPanel;
        pressedKeys = new HashMap<String, Boolean>();
    }

    public void addAction(String keyStroke){

        int offset = keyStroke.lastIndexOf(" ");
        String key = offset == -1 ? keyStroke :  keyStroke.substring( offset + 1 );
        String modifiers = keyStroke.replace(key, "");

        InputMap inputMap = jPanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = jPanel.getActionMap();

        //Create Action and add binding for the pressed key
        Action pressedAction = new KeyboardAction(key, true);
        String pressedKey = modifiers + PRESSED + key;
        KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(pressedKey);
        inputMap.put(pressedKeyStroke, pressedKey);
        actionMap.put(pressedKey, pressedAction);

        //Create Action and add binding for the released key
        Action releasedAction = new KeyboardAction(key, false);
        String releasedKey = modifiers + RELEASED + key;
        KeyStroke releasedKeyStroke = KeyStroke.getKeyStroke(releasedKey);
        inputMap.put(releasedKeyStroke, releasedKey);
        actionMap.put(releasedKey, releasedAction);
    }

    private void handleKeyEvents(String key, boolean value){
        if(!value) pressedKeys.remove(key);
        else pressedKeys.put(key, true);
    }

    @Override public void actionPerformed(ActionEvent e) { }

    private class KeyboardAction extends AbstractAction implements ActionListener {

        private boolean value;

        public KeyboardAction(String key, boolean value){
            super(key);
            this.value = value;
        }

        @Override
        public void actionPerformed(ActionEvent e) { handleKeyEvents((String)getValue(NAME), value); }
    }
}
