package panel.game;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LifeBarPanel extends JPanel {
	ImageIcon filledHeart = new ImageIcon("images/filled_heart.png");
	ImageIcon emptyHeart = new ImageIcon("images/empty_heart.png");
	public static int maxLife;
	int nowLife;

	public LifeBarPanel() {
		initLife();
		setLayout(new FlowLayout());
		setBounds(780, 620, 200, 35); // 200, 30
		paintHeart();
		setOpaque(false);
	}

	/** 라이프를 초기화 */
	public void initLife() {
		maxLife = 5; // 임시
		nowLife = maxLife;
		paintHeart();
	}

	public void updateLife(int life) {
		nowLife = life;
		paintHeart();
	}

	private void paintHeart() {
		removeAll();

		// 빨간색 하트
		for (int i = 0; i < nowLife; i++) {
			JLabel oneLife = new JLabel(filledHeart);
			add(oneLife);
		}

		// 회색 하트
		for (int i = 0; i < maxLife - nowLife; i++) {
			JLabel oneLife = new JLabel(emptyHeart);
			add(oneLife);
		}
		revalidate();
	}

	public int getLife() {
		return nowLife;
	}

	public void updateLifeBar(/* fail 신호 */) {
		/*
		 * 라이프를 하나 줄이고 다시 출력
		 * */
	}
}
