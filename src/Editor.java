import enigma.console.TextAttributes;
import enigma.console.TextWindow;
import enigma.core.Enigma;
import enigma.event.TextMouseListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Editor {

    public static enigma.console.Console cn = Enigma.getConsole("Text Editor", 100, 30, 16);
    public static TextWindow cnt = cn.getTextWindow();
    public static TextAttributes att0 = new TextAttributes(Color.white, Color.black); // foreground, background color
    public static TextAttributes att1 = new TextAttributes(Color.black, Color.white);
    public static TextAttributes att2 = new TextAttributes(Color.black, Color.red);
    public TextMouseListener tmlis;
    public static KeyListener klis;

    public Editor() {
        cn.getTextWindow();

        // ------ Standard code for mouse and keyboard ------ Do not change
        klis = new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (Settings.keypr == 0) {
                    Settings.keypr = 1;
                    Settings.rkey = e.getKeyCode();
                    Settings.rkeymod = e.getModifiersEx();
                    if (Settings.rkey == KeyEvent.VK_CAPS_LOCK) {
                        if (Settings.capslock == 0)
                            Settings.capslock = 1;
                        else
                            Settings.capslock = 0;
                    }
                }
            }
            public void keyReleased(KeyEvent e) {}
        };
        cn.getTextWindow().addKeyListener(klis);
        // --------------------------------------------------------------------------

        cnt.setCursorType(1); // 1 -> cursor visible
        cn.setTextAttributes(att0);
    } // editor config


}