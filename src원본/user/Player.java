package user;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Player extends JPanel {

	User user; // 유저 정보
	JLabel nameCard;
	int Life; // 라이프
	int nowScore; // 현재 점수
	int countHitCharecter; // 맞춘 글자 수(타수를 계산하기 위함)
	int typingVelocity; // 타수

	public Player(User user) {
		this.user = user;
		setBounds(20, 40, 200, 50);
		setLayout(new FlowLayout(FlowLayout.CENTER));

		nameCard = new JLabel(user.userName);
		nameCard.setFont(new Font("맑은 고딕", Font.BOLD, 30));
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
//	User user; // 유저 정보
//	JLabel nameCard;
//	int Life; // 라이프
//	int nowScore; // 현재 점수
//	int countHitCharecter; // 맞춘 글자 수(타수를 계산하기 위함)
//	int typingVelocity; // 타수
//
//	public Player(User user) {
//		this.user = user;
//		setBounds(20, 40, 200, 50);
//		setLayout(new FlowLayout(FlowLayout.CENTER));
//
//		nameCard = new JLabel(user.userName);
//		nameCard.setFont(new Font("맑은 고딕", Font.BOLD, 30));
//		add(nameCard);
//
//		setOpaque(false);
//	}
//
//	public void setScore(int score) {
//		nowScore = score;
//	}
//}
