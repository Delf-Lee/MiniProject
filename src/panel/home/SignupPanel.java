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
import java.io.FileWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import panel.BasePanel;
import panel.PanelManager;
import panel.home.LoginPanel.LoginKeyListener;
import user.User;
import user.UserManager;

public class SignupPanel extends BasePanel {
	private PanelManager panel;
	// 문자열
	private ImageIcon IDIcon = new ImageIcon("images/ID.png");
	private JLabel stringBoxID = new JLabel(IDIcon);
	private ImageIcon PWIcon = new ImageIcon("images/PW.png");
	private JLabel stringBoxPassWord = new JLabel(PWIcon);
	private ImageIcon PWCheckIcon = new ImageIcon("images/PW확인.png");
	private JLabel stringBoxCheckPassword = new JLabel(PWCheckIcon);
	// 텍스트 필드
	private JTextField IDFeild = new JTextField();
	private JPasswordField PassWordFeild = new JPasswordField();
	private JPasswordField checkPasswordFeild = new JPasswordField();
	// 버튼
	private ImageIcon OKIcon = new ImageIcon("images/확인.png");
	private ImageIcon OKIcon_ = new ImageIcon("images/확인2.png");
	private JButton btnOK = new JButton("확인", OKIcon);
	private ImageIcon CnacelIcon = new ImageIcon("images/취소.png");
	private ImageIcon CnacelIcon_ = new ImageIcon("images/취소2.png");
	private JButton btnCancel = new JButton("취소", CnacelIcon);

	/** 생성자 */
	public SignupPanel(int x, int y, int width, int height, PanelManager panel) {
		super("images/소패널.png");
		this.panel = panel;
		setBounds(x, y, width, height); // 좌표, 크기
		setBackground(Color.LIGHT_GRAY); // 삭제 예정 라인
		setComponent();
		setListener();
	}

	/** 패널 내의 컴포넌트를 설정하고 패널에 부착 */
	private void setComponent() {
		//stringBoxID.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		stringBoxID.setBounds(50, 25, 100, 100);

		IDFeild.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		IDFeild.setBounds(160, 55, 270, 40);

		//stringBoxPassWord.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		stringBoxPassWord.setBounds(50, 75, 100, 100);

		PassWordFeild.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		PassWordFeild.setBounds(160, 105, 270, 40);

		//stringBoxCheckPassword.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		stringBoxCheckPassword.setBounds(15, 130, 150, 100);

		checkPasswordFeild.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		checkPasswordFeild.setBounds(160, 155, 270, 40);

		btnOK.setBorderPainted(false);
		btnOK.setFocusPainted(false);
		btnOK.setContentAreaFilled(false);
		//btnOK.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnOK.setBounds(160, 220, 140, 40);

		btnCancel.setBorderPainted(false);
		btnCancel.setFocusPainted(false);
		btnCancel.setContentAreaFilled(false);
		//btnCancel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnCancel.setBounds(300, 220, 140, 40);

		addTwo(stringBoxID, IDFeild);
		addTwo(stringBoxPassWord, PassWordFeild);
		addTwo(stringBoxCheckPassword, checkPasswordFeild);
		addTwo(btnOK, btnCancel);
	}

	/** 파낼 내의 버튼에 대한 리스너 설정 */
	private void setListener() {
		btnOK.addActionListener(new SignupListener());
		btnCancel.addActionListener(new SignupListener());
		btnOK.addMouseListener(new SignupMouseEvent());
		btnCancel.addMouseListener(new SignupMouseEvent());
		checkPasswordFeild.addKeyListener(new SignupKeyListener());
	}

	/** 가입 정보를 입력받아 파일에 write */
	public void writeUserInfo() {
		// 책갈피: UserManager 수정
		UserManager.userList.add(new User(IDFeild.getText(), PassWordFeild.getText(), 1, 0));
	}

	public boolean checkInput() {

		String inputID = getID();
		String inputPassWord = getPassword();
		String inputPassWordConfirm = getCheckPassword();

		if (!UserManager.checkID(inputID)) { // 아이디 체킹
			return false;
		}
		else if (!UserManager.checkPassword(inputPassWord, inputPassWordConfirm)) { // 패스워드 체킹
			return false;
		}
		System.out.println("check input true");
		return true;
	}

	class SignupListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton pressedBtn = (JButton) e.getSource();
			switch (pressedBtn.getText()) {
			case "확인":
				if (checkInput()) { // 입력 체크
					writeUserInfo(); // 유저 정보 저장
					UserManager.saveUserData();
					backToLogin();
				}
				break;
			case "취소":
				backToLogin();
				initPanel();
				break;
			}
			
		}

		public void backToLogin() {
			setVisible(false);
			panel.getLoninPanel().setVisible(true);
			panel.getLoninPanel().setFocus();
		}
	}
	
	/** 회원가입패널에 대한 버튼들의 마우스리스너 */
	class SignupMouseEvent extends MouseAdapter {
		// 아무것도 안할 때,
		public void mouseExited(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			switch (eventBtn.getText()) {
			case "확인":
				btnOK.setIcon(OKIcon);
				break;
			case "취소":
				btnCancel.setIcon(CnacelIcon);
				break;
			}
		}

		// 버튼 위에 마우스를 올릴 때,
		public void mouseEntered(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			switch (eventBtn.getText()) {
			case "확인":
				btnOK.setIcon(OKIcon_);
				break;
			case "취소":
				btnCancel.setIcon(CnacelIcon_);
				break;
			}
		}
	}
	
	class SignupKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (checkInput()) { // 입력 체크
					writeUserInfo(); // 유저 정보 저장
					UserManager.saveUserData();
					backToLogin();
				}
			}
		}
		
		public void backToLogin() {
			setVisible(false);
			panel.getLoninPanel().setVisible(true);
			panel.getLoninPanel().setFocus();
		}
	}

	@Override
	public void initPanel() {
		IDFeild.setText("");
		PassWordFeild.setText("");
		checkPasswordFeild.setText("");
	}
	
	public String getID() {
		return IDFeild.getText().trim();
	}

	@SuppressWarnings("deprecation")
	public String getPassword() {
		return PassWordFeild.getText().trim();
	}

	@SuppressWarnings("deprecation")
	public String getCheckPassword() {
		return checkPasswordFeild.getText().trim();

	}

	public void setFocus() {
		IDFeild.requestFocus(true);
	}

}