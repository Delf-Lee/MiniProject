package panel.game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {
	int score;
	JLabel display;

	public ScorePanel() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBounds(10, 100, 300, 100);
		display = new JLabel(Integer.toString(score));
		display.setFont(new Font("Silkscreen", Font.BOLD, 50));
		display.setForeground(Color.WHITE);

		add(display);
		setOpaque(false);
	}

	/** ���� ���� �� ǥ�� */
	public void updateScore(int score) {
		this.score += score;
		display.setText(Integer.toString(this.score));
	}

	/** ���ھ� �ʱ�ȭ */
	public void initScore() {
		score = 0;
		display.setText("0");
	}
}