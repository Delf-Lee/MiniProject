package thread;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class AnimationThread extends Thread {

	private final int READY_AND_START = 1;
	private int flag = 0;
	private JLabel levelLabel = new JLabel();
	private JLabel score = new JLabel();
	private JLabel combo = new JLabel();

	private JPanel panel;
	private int level;

	public void readyAndStart(JPanel pnl, int lvl) {
		flag = READY_AND_START;
		level = lvl;
		panel = pnl;
		start();
	}

	private void readyAndStart() {
		System.out.println("¡¯¿‘");
		levelLabel.setText("Level " + level);
		levelLabel.setForeground(Color.WHITE);
		levelLabel.setFont(new Font("Silkscreen", Font.BOLD, 40));
		//int width = (panel.getWidth() / 2) - (levelLabel.getWidth());
		//int height= (panel.getHeight() / 2) - (levelLabel.getHeight());
		int width = 420;
		int height = 340;
		
		

		levelLabel.setBounds(width, height, 200, 40);
		panel.add(levelLabel);

		try {
			sleep(1000);
			System.out.println("for");
			for (int i = 0; i < 255; i++) {
				levelLabel.setForeground(new Color(255, 255, 255, 255 - i));
				System.out.println(i);
				sleep(5);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		panel.remove(levelLabel);
	}

	public void run() {
		switch (flag) {
		case READY_AND_START:
			readyAndStart();
			System.out.println("qq");
			break;
		case 0:
		default:
			break;
		}
	}

}
