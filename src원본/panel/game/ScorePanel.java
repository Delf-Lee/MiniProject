package panel.game;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {
	int score = 2954000;
	JLabel display;

	public ScorePanel() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBounds(10, 150, 300, 100);
		display = new JLabel(Integer.toString(score));
		display.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 50));
		display.setForeground(new Color(79, 46, 0));
		//display.setBounds(r);
		
		add(display);
		setOpaque(false);
	}

	public void updateScore(int score) {
		this.score += score;
		repaint(); // ÀÌ·¸°Ô ÇÏ³ª?
	}
}