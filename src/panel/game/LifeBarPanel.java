package panel.game;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LifeBarPanel extends JPanel {
	ImageIcon filledHeart = new ImageIcon("images/filled_heart.png");
	ImageIcon emptyHeart = new ImageIcon("images/empty_heart.png");
	int maxLife;
	int nowLife;

	public LifeBarPanel() {
		maxLife = 5; // �ӽ�
		nowLife = 3; // �ӽ�
		setLayout(new FlowLayout());
		setBounds(780, 620, 200, 35); // 200, 30
		paintHeart();
		setOpaque(false);
	}

	public void initLife() {
		maxLife = 5; // �ӽ�
		nowLife = maxLife;
	}

	public void fillList() {
		if (nowLife < maxLife) {
			nowLife++;
		}
	}

	public void lostLife() {
		if (nowLife > 0) {
			nowLife--;
		}
		paintHeart();
		System.out.println("lost one heart " + nowLife);
	}

	private void paintHeart() {
		removeAll();
		
		// ������ ��Ʈ
		for (int i = 0; i < nowLife; i++) {
			JLabel oneLife = new JLabel(filledHeart);
			add(oneLife);
		}

		// ȸ�� ��Ʈ
		for (int i = 0; i < maxLife - nowLife; i++) {
			JLabel oneLife = new JLabel(emptyHeart);
			add(oneLife);
		}
		
		revalidate();
	}
	
	public void updateLifeBar(/* fail ��ȣ */) {
		/*
		 * �������� �ϳ� ���̰� �ٽ� ���
		 * */
	}
}
