package panel.game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {
	JLabel levelLabel = new JLabel();
	JLabel timeLabel = new JLabel();
	int level;
	int preTime;
	int limitTime;

	public InfoPanel() {
		setComponent();
		limitTime = 30;
	}

	private void setComponent() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBounds(10, 20, 500, 500);

		levelLabel = new JLabel();
		levelLabel.setFont(new Font("Silkscreen", Font.BOLD, 50));
		levelLabel.setForeground(Color.WHITE);

		timeLabel.setLocation(0, 0);
		timeLabel.setLocation(300, 100);
		timeLabel = new JLabel("time " + Integer.toString(preTime));
		timeLabel.setFont(new Font("Silkscreen", Font.BOLD, 50));
		timeLabel.setForeground(Color.WHITE);

		//add(levelLabel);
		add(timeLabel);

		this.setOpaque(false);
	}

	public void setLevel(int lvl) {
		this.level = lvl;
		levelLabel.setText(Integer.toString(level));
	}

	public void setInfo(int lvl, int time) {
		this.preTime = time;
		this.level = lvl;
		System.out.println("this is " + level);
		levelLabel.setText("level " + Integer.toString(level));
		timeLabel.setText(Integer.toString(time));
	}

	public boolean updateTime() {
		int printTime = limitTime - (((int) System.currentTimeMillis() - preTime) / 1000);
		timeLabel.setText(Integer.toString(printTime));
		if (printTime > limitTime) {
			return false; // 시간초과
		}
		return true;
	}
}
