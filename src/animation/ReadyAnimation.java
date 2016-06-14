package animation;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import thread.GameThread;

/**
 * ���� ���� �� ���� ������ ��� ��, ���̵� �ƿ��ϸ� ������� ���̺��� ����� ������
 */
public class ReadyAnimation extends Thread {
	private JLabel levelLabel = new JLabel();
	private JPanel panel;
	private int level;
	private GameThread thrd;

	public ReadyAnimation(JPanel pnl, int lvl, GameThread thrd) {
		this.level = lvl;
		this.panel = pnl;
		this.thrd = thrd;
		start();
	}

	private void readyAndStart() {
		thrd.pause();
		levelLabel.setText("Level " + level);
		levelLabel.setForeground(Color.WHITE);
		levelLabel.setFont(new Font("Silkscreen", Font.BOLD, 40));
		//int width = (panel.getWidth() / 2) - (levelLabel.getWidth());
		//int height= (panel.getHeight() / 2) - (levelLabel.getHeight());
		int width = 430;
		int height = 340;

		levelLabel.setBounds(width, height, 200, 40); // �ڵ����� �����ϰ� ������ �ϵ�� ��������..
		panel.add(levelLabel);

		try {
			sleep(1000);
			for (int i = 0; i < 255; i++) { // ������ ������ ����
				levelLabel.setForeground(new Color(255, 255, 255, 255 - i));
				sleep(5);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		panel.remove(levelLabel);
		thrd.continueGame();
	}

	public void run() {
		readyAndStart();
	}
}
