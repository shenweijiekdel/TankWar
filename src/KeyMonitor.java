import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyMonitor extends KeyAdapter {
	private Tank t;
	public static boolean leftButtonPressed;
	public static boolean rightButtonPressed;
	public static boolean upButtonPressed;
	public static boolean downButtonPressed;
	public static boolean fireButtonPressed;
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 37) {
			leftButtonPressed = true;
		}

		if (e.getKeyCode() == 38) {
			upButtonPressed = true;
		}

		if (e.getKeyCode() == 39) {
			rightButtonPressed = true;
		}

		if (e.getKeyCode() == 40){
			downButtonPressed = true;
		}
		if (e.getKeyCode() == 32){
			fireButtonPressed = true;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		super.keyTyped(e);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 37)
			leftButtonPressed = false;
		if (e.getKeyCode() == 38)
			upButtonPressed = false;
		if (e.getKeyCode() == 39)
			rightButtonPressed = false;
		if (e.getKeyCode() == 40)
			downButtonPressed = false;
		if (e.getKeyCode() == 32)
			fireButtonPressed = false;
		
	}

}
