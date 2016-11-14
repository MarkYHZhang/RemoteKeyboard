import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Mark on 2016-11-13.
 */
public class KeyboardListener implements KeyListener{


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        RemoteKeyboard.pressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        RemoteKeyboard.released(e.getKeyCode());
    }

}
