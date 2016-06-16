package panel.home;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import panel.BasePanel;
import panel.PanelManager;
import user.User;
import user.UserManager;

public class PanelLogin extends BasePanel {
	private PanelManager panel;
	// 문자열
	private JLabel stringBoxID = new JLabel("ID");
	private JLabel stringBoxPassWord = new JLabel("PW");
	// 텍스트 필드
	private JTextField IDFeild = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	// 버튼
	private JButton loginButton = new JButton("로그인");
	private JButton signUpButton = new JButton("회원가입");

	/** 생성자 */
	public PanelLogin(int x, int y, int width, int height, PanelManager panel) {
		super(/*이미지 경로*/);
		this.panel = panel;
		setBounds(x, y, width, height);
		setBackground(Color.CYAN); // 삭제 예정 라인

		setComponent(); // 각 컴포넌트 배치
		setListener();
	}

	/** 리스너 설정 */
	private void setListener() {
		loginButton.addActionListener(new LoginListener());
		signUpButton.addActionListener(new LoginListener());
	}

	/** 컴포넌트 설정 및 배치 */
	private void setComponent() {
		stringBoxID.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		stringBoxID.setBounds(60, 40, 100, 100);

		IDFeild.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		IDFeild.setBounds(130, 75, 270, 40);

		stringBoxPassWord.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		stringBoxPassWord.setBounds(50, 90, 100, 100);

		passwordField.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		passwordField.setBounds(130, 125, 270, 40);

		loginButton.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		loginButton.setBounds(130, 190, 130, 40);

		signUpButton.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		signUpButton.setBounds(270, 190, 130, 40);

		addTwo(stringBoxID, IDFeild);
		addTwo(stringBoxPassWord, passwordField);
		addTwo(loginButton, signUpButton);
	}

	public boolean acceptUser() {

		String inputID = getID();
		String inputPassWord = getPassword();

		if (!UserManager.acceptUser(inputID, inputPassWord)) {
			return false;
		}
		return true;
	}

	/** 패널 내 버튼에 대한 리스너 */
	class LoginListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton pressedBtn = (JButton) e.getSource();
			switch (pressedBtn.getText()) {
			case "로그인": // 메인 -> 메뉴
				if (acceptUser()) { // 입력 체크
					initPanel();
					panel.setContentPane(PanelManager.MENU);
					panel.getSignupPanel().setFocus();
					repaint();
				}
				break;
			case "회원가입": // 메뉴 -> 회원가입
				setVisible(false);
				panel.getSignupPanel().setVisible(true);
				panel.getSignupPanel().setFocus();
				break;
			}
		}
	}

	public String getID() {
		return IDFeild.getText().trim();

	}

	public String getPassword() {
		return passwordField.getText().trim(); // 왜 경고?
	}

	@Override
	public void initPanel() {
		IDFeild.setText("");
		passwordField.setText("");
	}

	public void setFocus() {
		IDFeild.requestFocus(true);
	}
}
