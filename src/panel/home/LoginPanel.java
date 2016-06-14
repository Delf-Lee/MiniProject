package panel.home;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileReader;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import panel.BasePanel;
import panel.PanelManager;
import panel.home.SignupPanel.SignupMouseEvent;
import user.User;
import user.UserManager;

public class LoginPanel extends BasePanel {
	private PanelManager panel;
	// 문자열
	private ImageIcon IDIcon = new ImageIcon("images/ID.png");
	private JLabel stringBoxID = new JLabel(IDIcon);
	private ImageIcon PWIcon = new ImageIcon("images/PW.png");
	private JLabel stringBoxPassWord = new JLabel(PWIcon);
	// 텍스트 필드
	private JTextField IDFeild = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	// 버튼
	private ImageIcon loginIcon = new ImageIcon("images/로그인.png");
	private ImageIcon loginIcon_ = new ImageIcon("images/로그인2.png");
	private JButton loginButton = new JButton("로그인", loginIcon);
	private ImageIcon signupIcon = new ImageIcon("images/회원가입.png");
	private ImageIcon signupIcon_ = new ImageIcon("images/회원가입2.png");
	private JButton signUpButton = new JButton("회원가입", signupIcon);

	/** 생성자 */
	public LoginPanel(int x, int y, int width, int height, PanelManager panel) {
		super("images/소패널.png");
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
		passwordField.addKeyListener(new LoginKeyListener());
		loginButton.addMouseListener(new LoginMouseEvent());
		signUpButton.addMouseListener(new LoginMouseEvent());
	}

	/** 컴포넌트 설정 및 배치 */
	private void setComponent() {
		//stringBoxID.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		stringBoxID.setBounds(25, 50, 100, 100);

		IDFeild.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		IDFeild.setBounds(130, 75, 270, 40);

		//stringBoxPassWord.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		stringBoxPassWord.setBounds(25, 100, 100, 100);

		passwordField.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		passwordField.setBounds(130, 125, 270, 40);

		loginButton.setBorderPainted(false);
		loginButton.setFocusPainted(false);
		loginButton.setContentAreaFilled(false);
		loginButton.setBounds(130, 190, 140, 40);

		signUpButton.setBorderPainted(false);
		signUpButton.setFocusPainted(false);
		signUpButton.setContentAreaFilled(false);
		signUpButton.setBounds(270, 190, 140, 40);

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
		public void actionPerformed(ActionEvent e) {
			JButton pressedBtn = (JButton) e.getSource();
			switch (pressedBtn.getText()) {
			case "로그인": // 메인 -> 메뉴
				if (acceptUser()) { // 입력 체크
					panel.setContentPane(PanelManager.MENU);
					panel.getSignupPanel().setFocus();
					repaint();
				}
				break;
			case "회원가입": // 메뉴 -> 회원가입
				setVisible(false);
				panel.getSignupPanel().setVisible(true);
				panel.getSignupPanel().setFocus();
				initPanel();
				break;
			}
			
		}
	}
	
	class LoginKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (acceptUser()) { // 입력 체크
					panel.setContentPane(PanelManager.MENU);
					panel.getSignupPanel().setFocus();
					repaint();
				}
			}
		}
	}
	
	/** 회원가입패널에 대한 버튼들의 마우스리스너 */
	class LoginMouseEvent extends MouseAdapter {
		// 아무것도 안할 때,
		public void mouseExited(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			switch (eventBtn.getText()) {
			case "로그인":
				loginButton.setIcon(loginIcon);
				break;
			case "회원가입":
				signUpButton.setIcon(signupIcon);
				break;
			}
		}

		// 버튼 위에 마우스를 올릴 때,
		public void mouseEntered(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			switch (eventBtn.getText()) {
			case "로그인":
				loginButton.setIcon(loginIcon_);
				break;
			case "회원가입":
				signUpButton.setIcon(signupIcon_);
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
