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
	// ���ڿ�
	private ImageIcon IDIcon = new ImageIcon("images/ID.png");
	private JLabel stringBoxID = new JLabel(IDIcon);
	private ImageIcon PWIcon = new ImageIcon("images/PW.png");
	private JLabel stringBoxPassWord = new JLabel(PWIcon);
	private ImageIcon PWCheckIcon = new ImageIcon("images/PWȮ��.png");
	private JLabel stringBoxCheckPassword = new JLabel(PWCheckIcon);
	// �ؽ�Ʈ �ʵ�
	private JTextField IDFeild = new JTextField();
	private JPasswordField PassWordFeild = new JPasswordField();
	private JPasswordField checkPasswordFeild = new JPasswordField();
	// ��ư
	private ImageIcon OKIcon = new ImageIcon("images/Ȯ��.png");
	private ImageIcon OKIcon_ = new ImageIcon("images/Ȯ��2.png");
	private JButton btnOK = new JButton("Ȯ��", OKIcon);
	private ImageIcon CnacelIcon = new ImageIcon("images/���.png");
	private ImageIcon CnacelIcon_ = new ImageIcon("images/���2.png");
	private JButton btnCancel = new JButton("���", CnacelIcon);

	/** ������ */
	public SignupPanel(int x, int y, int width, int height, PanelManager panel) {
		super("images/���г�.png");
		this.panel = panel;
		setBounds(x, y, width, height); // ��ǥ, ũ��
		setBackground(Color.LIGHT_GRAY); // ���� ���� ����
		setComponent();
		setListener();
	}

	/** �г� ���� ������Ʈ�� �����ϰ� �гο� ���� */
	private void setComponent() {
		//stringBoxID.setFont(new Font("���� ���", Font.BOLD, 30));
		stringBoxID.setBounds(50, 25, 100, 100);

		IDFeild.setFont(new Font("���� ���", Font.BOLD, 20));
		IDFeild.setBounds(160, 55, 270, 40);

		//stringBoxPassWord.setFont(new Font("���� ���", Font.BOLD, 30));
		stringBoxPassWord.setBounds(50, 75, 100, 100);

		PassWordFeild.setFont(new Font("���� ���", Font.BOLD, 20));
		PassWordFeild.setBounds(160, 105, 270, 40);

		//stringBoxCheckPassword.setFont(new Font("���� ���", Font.BOLD, 30));
		stringBoxCheckPassword.setBounds(15, 130, 150, 100);

		checkPasswordFeild.setFont(new Font("���� ���", Font.BOLD, 20));
		checkPasswordFeild.setBounds(160, 155, 270, 40);

		btnOK.setBorderPainted(false);
		btnOK.setFocusPainted(false);
		btnOK.setContentAreaFilled(false);
		//btnOK.setFont(new Font("���� ���", Font.BOLD, 20));
		btnOK.setBounds(160, 220, 140, 40);

		btnCancel.setBorderPainted(false);
		btnCancel.setFocusPainted(false);
		btnCancel.setContentAreaFilled(false);
		//btnCancel.setFont(new Font("���� ���", Font.BOLD, 20));
		btnCancel.setBounds(300, 220, 140, 40);

		addTwo(stringBoxID, IDFeild);
		addTwo(stringBoxPassWord, PassWordFeild);
		addTwo(stringBoxCheckPassword, checkPasswordFeild);
		addTwo(btnOK, btnCancel);
	}

	/** �ĳ� ���� ��ư�� ���� ������ ���� */
	private void setListener() {
		btnOK.addActionListener(new SignupListener());
		btnCancel.addActionListener(new SignupListener());
		btnOK.addMouseListener(new SignupMouseEvent());
		btnCancel.addMouseListener(new SignupMouseEvent());
		checkPasswordFeild.addKeyListener(new SignupKeyListener());
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
	
	/** ȸ�������гο� ���� ��ư���� ���콺������ */
	class SignupMouseEvent extends MouseAdapter {
		// �ƹ��͵� ���� ��,
		public void mouseExited(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			switch (eventBtn.getText()) {
			case "Ȯ��":
				btnOK.setIcon(OKIcon);
				break;
			case "���":
				btnCancel.setIcon(CnacelIcon);
				break;
			}
		}

		// ��ư ���� ���콺�� �ø� ��,
		public void mouseEntered(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			switch (eventBtn.getText()) {
			case "Ȯ��":
				btnOK.setIcon(OKIcon_);
				break;
			case "���":
				btnCancel.setIcon(CnacelIcon_);
				break;
			}
		}
	}
	
	class SignupKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (checkInput()) { // �Է� üũ
					writeUserInfo(); // ���� ���� ����
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