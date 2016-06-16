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
	// ���ڿ�
	private JLabel stringBoxID = new JLabel("ID");
	private JLabel stringBoxPassWord = new JLabel("PW");
	// �ؽ�Ʈ �ʵ�
	private JTextField IDFeild = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	// ��ư
	private JButton loginButton = new JButton("�α���");
	private JButton signUpButton = new JButton("ȸ������");

	/** ������ */
	public PanelLogin(int x, int y, int width, int height, PanelManager panel) {
		super(/*�̹��� ���*/);
		this.panel = panel;
		setBounds(x, y, width, height);
		setBackground(Color.CYAN); // ���� ���� ����

		setComponent(); // �� ������Ʈ ��ġ
		setListener();
	}

	/** ������ ���� */
	private void setListener() {
		loginButton.addActionListener(new LoginListener());
		signUpButton.addActionListener(new LoginListener());
	}

	/** ������Ʈ ���� �� ��ġ */
	private void setComponent() {
		stringBoxID.setFont(new Font("���� ���", Font.BOLD, 30));
		stringBoxID.setBounds(60, 40, 100, 100);

		IDFeild.setFont(new Font("���� ���", Font.BOLD, 20));
		IDFeild.setBounds(130, 75, 270, 40);

		stringBoxPassWord.setFont(new Font("���� ���", Font.BOLD, 30));
		stringBoxPassWord.setBounds(50, 90, 100, 100);

		passwordField.setFont(new Font("���� ���", Font.BOLD, 20));
		passwordField.setBounds(130, 125, 270, 40);

		loginButton.setFont(new Font("���� ���", Font.BOLD, 20));
		loginButton.setBounds(130, 190, 130, 40);

		signUpButton.setFont(new Font("���� ���", Font.BOLD, 20));
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

	/** �г� �� ��ư�� ���� ������ */
	class LoginListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton pressedBtn = (JButton) e.getSource();
			switch (pressedBtn.getText()) {
			case "�α���": // ���� -> �޴�
				if (acceptUser()) { // �Է� üũ
					initPanel();
					panel.setContentPane(PanelManager.MENU);
					panel.getSignupPanel().setFocus();
					repaint();
				}
				break;
			case "ȸ������": // �޴� -> ȸ������
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
		return passwordField.getText().trim(); // �� ���?
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
