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
	// ���ڿ�
	private JLabel stringBoxID = new JLabel("ID");
	private JLabel stringBoxPassword = new JLabel("PW");
	private JLabel stringBoxCheckPassword = new JLabel("PWȮ��");
	// �ؽ�Ʈ �ʵ�
	private JTextField IDFeild = new JTextField();
	private JPasswordField PassWordFeild = new JPasswordField();
	private JPasswordField checkPasswordFeild = new JPasswordField();
	// ��ư
	private JButton btnOK = new JButton("Ȯ��");
	private JButton btnCancel = new JButton("���");

	/** ������ */
	public SignupPanel(int x, int y, int width, int height, PanelManager panel) {
		super(/*�̹��� ���*/);
		this.panel = panel;
		setBounds(x, y, width, height); // ��ǥ, ũ��
		setBackground(Color.LIGHT_GRAY); // ���� ���� ����
		setComponent();
		setListener();
	}

	/** �г� ���� ������Ʈ�� �����ϰ� �гο� ���� */
	private void setComponent() {
		stringBoxID.setFont(new Font("���� ���", Font.BOLD, 30));
		stringBoxID.setBounds(80, 20, 100, 100);

		IDFeild.setFont(new Font("���� ���", Font.BOLD, 20));
		IDFeild.setBounds(160, 55, 270, 40);

		stringBoxPassword.setFont(new Font("���� ���", Font.BOLD, 30));
		stringBoxPassword.setBounds(70, 70, 100, 100);

		PassWordFeild.setFont(new Font("���� ���", Font.BOLD, 20));
		PassWordFeild.setBounds(160, 105, 270, 40);

		stringBoxCheckPassword.setFont(new Font("���� ���", Font.BOLD, 30));
		stringBoxCheckPassword.setBounds(40, 120, 150, 100);

		checkPasswordFeild.setFont(new Font("���� ���", Font.BOLD, 20));
		checkPasswordFeild.setBounds(160, 155, 270, 40);

		btnOK.setFont(new Font("���� ���", Font.BOLD, 20));
		btnOK.setBounds(160, 220, 130, 40);

		btnCancel.setFont(new Font("���� ���", Font.BOLD, 20));
		btnCancel.setBounds(300, 220, 130, 40);

		addTwo(stringBoxID, IDFeild);
		addTwo(stringBoxPassword, PassWordFeild);
		addTwo(stringBoxCheckPassword, checkPasswordFeild);
		addTwo(btnOK, btnCancel);
	}

	/** �ĳ� ���� ��ư�� ���� ������ ���� */
	private void setListener() {
		btnOK.addActionListener(new SignupListener());
		btnCancel.addActionListener(new SignupListener());
	}

	/** ���� ������ �Է¹޾� ���Ͽ� write */
	public void writeUserInfo() {
		// å����: UserManager ����
		UserManager.userList.add(new User(IDFeild.getText(), PassWordFeild.getText(), 1, 0));
	}

	public boolean checkInput() {

		String inputID = getID();
		String inputPassWord = getPassword();
		String inputPassWordConfirm = getCheckPassword();

		if (!UserManager.checkID(inputID)) { // ���̵� üŷ
			return false;
		}
		else if (!UserManager.checkPassword(inputPassWord, inputPassWordConfirm)) { // �н����� üŷ
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
			case "Ȯ��":
				if (checkInput()) { // �Է� üũ
					writeUserInfo(); // ���� ���� ����
					UserManager.saveUserData();
					backToLogin();
				}
				break;
			case "���":
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