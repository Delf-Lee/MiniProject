package panel.home;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import panel.BasePanel;
import panel.PanelManager;
import user.User;
import user.UserManager;

public class SignupPanel extends BasePanel {
	private PanelManager panel;
	// 문자열
	private JLabel stringBoxID = new JLabel("ID");
	private JLabel stringBoxPassword = new JLabel("PW");
	private JLabel stringBoxCheckPassword = new JLabel("PW확인");
	// 텍스트 필드
	private JTextField IDFeild = new JTextField();
	private JPasswordField PassWordFeild = new JPasswordField();
	private JPasswordField checkPasswordFeild = new JPasswordField();
	// 버튼
	private JButton btnOK = new JButton("확인");
	private JButton btnCancel = new JButton("취소");

	/** 생성자 */
	public SignupPanel(int x, int y, int width, int height, PanelManager panel) {
		super(/*이미지 경로*/);
		this.panel = panel;
		setBounds(x, y, width, height); // 좌표, 크기
		setBackground(Color.LIGHT_GRAY); // 삭제 예정 라인
		setComponent();
		setListener();
	}

	/** 패널 내의 컴포넌트를 설정하고 패널에 부착 */
	private void setComponent() {
		stringBoxID.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		stringBoxID.setBounds(80, 20, 100, 100);

		IDFeild.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		IDFeild.setBounds(160, 55, 270, 40);

		stringBoxPassword.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		stringBoxPassword.setBounds(70, 70, 100, 100);

		PassWordFeild.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		PassWordFeild.setBounds(160, 105, 270, 40);

		stringBoxCheckPassword.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		stringBoxCheckPassword.setBounds(40, 120, 150, 100);

		checkPasswordFeild.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		checkPasswordFeild.setBounds(160, 155, 270, 40);

		btnOK.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnOK.setBounds(160, 220, 130, 40);

		btnCancel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnCancel.setBounds(300, 220, 130, 40);

		addTwo(stringBoxID, IDFeild);
		addTwo(stringBoxPassword, PassWordFeild);
		addTwo(stringBoxCheckPassword, checkPasswordFeild);
		addTwo(btnOK, btnCancel);
	}

	/** 파낼 내의 버튼에 대한 리스너 설정 */
	private void setListener() {
		btnOK.addActionListener(new SignupListener());
		btnCancel.addActionListener(new SignupListener());
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
				break;
			}
			initPanel();
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