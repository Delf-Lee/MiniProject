package panel.game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {
	private static final int[] LVL_LOCATION = { 10, 30 };
	private static final int[] TIME_LOCATION = { 230, 10 };
	private static final int X = 0;
	private static final int Y = 1;
	JLabel levelLabel = new JLabel();
	JLabel timeLabel = new JLabel();
	ImageIcon clock = new ImageIcon("images/시계.png");
	JLabel clockLabel = new JLabel(clock);
	int level;
	int preTime;
	int limitTime;

	public InfoPanel() {
		setComponent();
		limitTime = 30;
	}

	private void setComponent() {
		setBounds(10, 0, 1000, 300);
		setLayout(null);
		levelLabel = new JLabel();
		levelLabel.setFont(new Font("Silkscreen", Font.BOLD, 40));
		levelLabel.setForeground(Color.WHITE);
		levelLabel.setBounds(LVL_LOCATION[X], LVL_LOCATION[Y], 300, 50);

		clockLabel.setBounds(TIME_LOCATION[X], TIME_LOCATION[Y], clock.getIconWidth(), clock.getIconHeight());

		timeLabel = new JLabel("time " + Integer.toString(preTime));
		timeLabel.setFont(new Font("Silkscreen", Font.BOLD, 40));
		timeLabel.setForeground(Color.WHITE);
		timeLabel.setBounds(TIME_LOCATION[X] + 60, TIME_LOCATION[Y] - 10, 300, 100);

		add(levelLabel);
		add(clockLabel);
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
		levelLabel.setText("level " + Integer.toString(level));
		timeLabel.setText(Integer.toString(time));
	}

	public boolean updateTime() {
		int printTime = limitTime - (((int) System.currentTimeMillis() - preTime) / 1000);
		if (printTime < 0) {
			timeLabel.setText("0");
			return false; // 시간초과
		}
		timeLabel.setText(Integer.toString(printTime));
		
		return true;
	}
}
