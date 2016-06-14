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
	// ���ڿ�
	private ImageIcon IDIcon = new ImageIcon("images/ID.png");
	private JLabel stringBoxID = new JLabel(IDIcon);
	private ImageIcon PWIcon = new ImageIcon("images/PW.png");
	private JLabel stringBoxPassWord = new JLabel(PWIcon);
	// �ؽ�Ʈ �ʵ�
	private JTextField IDFeild = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	// ��ư
	private ImageIcon loginIcon = new ImageIcon("images/�α���.png");
	private ImageIcon loginIcon_ = new ImageIcon("images/�α���2.png");
	private JButton loginButton = new JButton("�α���", loginIcon);
	private ImageIcon signupIcon = new ImageIcon("images/ȸ������.png");
	private ImageIcon signupIcon_ = new ImageIcon("images/ȸ������2.png");
	private JButton signUpButton = new JButton("ȸ������", signupIcon);

	/** ������ */
	public LoginPanel(int x, int y, int width, int height, PanelManager panel) {
		super("images/���г�.png");
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
		passwordField.addKeyListener(new LoginKeyListener());
		loginButton.addMouseListener(new LoginMouseEvent());
		signUpButton.addMouseListener(new LoginMouseEvent());
	}

	/** ������Ʈ ���� �� ��ġ */
	private void setComponent() {
		//stringBoxID.setFont(new Font("���� ���", Font.BOLD, 30));
		stringBoxID.setBounds(25, 50, 100, 100);

		IDFeild.setFont(new Font("���� ���", Font.BOLD, 20));
		IDFeild.setBounds(130, 75, 270, 40);

		//stringBoxPassWord.setFont(new Font("���� ���", Font.BOLD, 30));
		stringBoxPassWord.setBounds(25, 100, 100, 100);

		passwordField.setFont(new Font("���� ���", Font.BOLD, 20));
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

	/** �г� �� ��ư�� ���� ������ */
	class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton pressedBtn = (JButton) e.getSource();
			switch (pressedBtn.getText()) {
			case "�α���": // ���� -> �޴�
				if (acceptUser()) { // �Է� üũ
					panel.setContentPane(PanelManager.MENU);
					panel.getSignupPanel().setFocus();
					repaint();
				}
				break;
			case "ȸ������": // �޴� -> ȸ������
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
				if (acceptUser()) { // �Է� üũ
					panel.setContentPane(PanelManager.MENU);
					panel.getSignupPanel().setFocus();
					repaint();
				}
			}
		}
	}
	
	/** ȸ�������гο� ���� ��ư���� ���콺������ */
	class LoginMouseEvent extends MouseAdapter {
		// �ƹ��͵� ���� ��,
		public void mouseExited(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			switch (eventBtn.getText()) {
			case "�α���":
				loginButton.setIcon(loginIcon);
				break;
			case "ȸ������":
				signUpButton.setIcon(signupIcon);
				break;
			}
		}

		// ��ư ���� ���콺�� �ø� ��,
		public void mouseEntered(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			switch (eventBtn.getText()) {
			case "�α���":
				loginButton.setIcon(loginIcon_);
				break;
			case "ȸ������":
				signUpButton.setIcon(signupIcon_);
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
