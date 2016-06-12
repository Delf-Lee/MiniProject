package user;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Player extends JPanel {

	User user; // ���� ����
	JLabel nameCard;
	int Life; // ������
	int nowScore; // ���� ����
	int countHitCharecter; // ���� ���� ��(Ÿ���� ����ϱ� ����)
	int typingVelocity; // Ÿ��

	public Player(User user) {
		this.user = user;
		setBounds(20, 40, 200, 50);
		setLayout(new FlowLayout(FlowLayout.CENTER));

		nameCard = new JLabel(user.userName);
		nameCard.setFont(new Font("���� ���", Font.BOLD, 30));
		add(nameCard);

		setOpaque(false);
	}

	public void setScore(int score) {
		nowScore = score;
	}
}
//package tmp;
//
//import java.awt.FlowLayout;
//import java.awt.Font;
//
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//
//public class Player extends JPanel {
//
//	User user; // ���� ����
//	JLabel nameCard;
//	int Life; // ������
//	int nowScore; // ���� ����
//	int countHitCharecter; // ���� ���� ��(Ÿ���� ����ϱ� ����)
//	int typingVelocity; // Ÿ��
//
//	public Player(User user) {
//		this.user = user;
//		setBounds(20, 40, 200, 50);
//		setLayout(new FlowLayout(FlowLayout.CENTER));
//
//		nameCard = new JLabel(user.userName);
//		nameCard.setFont(new Font("���� ���", Font.BOLD, 30));
//		add(nameCard);
//
//		setOpaque(false);
//	}
//
//	public void setScore(int score) {
//		nowScore = score;
//	}
//}
