import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

public class MainFrame extends JFrame {
	private boolean mainThreadFlag;
	private KeyMonitor km = new KeyMonitor();
	private boolean mainUIThreadFlag;

	public MainFrame() {
		this.setLocation(600, 100);
		this.setSize(1024, 768);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		GamePanel jPanel = new GamePanel();
		this.add(jPanel);
		this.setVisible(true);
		this.addKeyListener(km);
		mainThreadFlag = true;
		mainUIThreadFlag = true;

	}


}
