import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainApp extends JFrame {
	public JPanel mainPanel = new JPanel(); // �����г�
	public JPanel loginPanel;
	public JPanel signupPanel;
	public JPanel MenuPanel = new MenuPanel(this);
	
	public MainApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Main Panel");
		setSize(1024, 768);
		setContentPane(mainPanel);
		this.setLayout(null);
		
		setLoginPanel();
		
		setVisible(true);
	}

	public static void main(String[] args) {
		new MainApp();
	}

	public void setLoginPanel() {
		loginPanel = new JPanel();
		loginPanel.setLayout(null);
		loginPanel.setBounds(262, 350, 500, 300); // ��ǥ/ũ��
		loginPanel.setBackground(Color.CYAN);
		
		JLabel j1 = new JLabel("ID");
		j1.setFont(new Font("���� ����", Font.BOLD, 30));
		j1.setBounds(60, 40, 100, 100);
		
		JTextField id = new JTextField();
		id.setFont(new Font("���� ����", Font.BOLD, 20));
		id.setBounds(130, 75, 270, 40);
				
		JLabel j2 = new JLabel("PW");
		j2.setFont(new Font("���� ����", Font.BOLD, 30));
		j2.setBounds(50, 90, 100, 100);
		
		JPasswordField pw = new JPasswordField();
		pw.setFont(new Font("���� ����", Font.BOLD, 20));
		pw.setBounds(130, 125, 270, 40);
		
		JButton login = new JButton("�α���");
		login.setFont(new Font("���� ����", Font.BOLD, 20));
		login.setBounds(130, 190, 130, 40);
		
		JButton signup = new JButton("ȸ������");
		signup.setFont(new Font("���� ����", Font.BOLD, 20));
		signup.setBounds(270, 190, 130, 40);
		
		loginPanel.add(j1);
		loginPanel.add(id);
		loginPanel.add(j2);
		loginPanel.add(pw);
		loginPanel.add(login);
		loginPanel.add(signup);
		
		mainPanel.add(loginPanel);
		
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				setContentPane(MenuPanel);
				revalidate();   // ���ΰ�ħ ���� 
				repaint();		// �ٽñ׸�
			}
		});
		
		signup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				loginPanel.setVisible(false);
				setSignupPanel();
			}
		});
	}

	public void setSignupPanel() {
		signupPanel = new JPanel();
		signupPanel.setLayout(null);
		signupPanel.setBounds(262, 350, 500, 300); // ��ǥ/ũ��
		signupPanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel j1 = new JLabel("ID");
		j1.setFont(new Font("���� ����", Font.BOLD, 30));
		j1.setBounds(80, 20, 100, 100);
		
		JTextField id = new JTextField();
		id.setFont(new Font("���� ����", Font.BOLD, 20));
		id.setBounds(160, 55, 270, 40);
				
		JLabel j2 = new JLabel("PW");
		j2.setFont(new Font("���� ����", Font.BOLD, 30));
		j2.setBounds(70, 70, 100, 100);
		
		JPasswordField pw = new JPasswordField();
		pw.setFont(new Font("���� ����", Font.BOLD, 20));
		pw.setBounds(160, 105, 270, 40);
		
		JLabel j3 = new JLabel("PWȮ��");
		j3.setFont(new Font("���� ����", Font.BOLD, 30));
		j3.setBounds(40, 120, 150, 100);
		
		JPasswordField checkPw = new JPasswordField();
		checkPw.setFont(new Font("���� ����", Font.BOLD, 20));
		checkPw.setBounds(160, 155, 270, 40);
		
		JButton signup = new JButton("ȸ������");
		signup.setFont(new Font("���� ����", Font.BOLD, 20));
		signup.setBounds(220, 220, 130, 40);
		
		signupPanel.add(j1);
		signupPanel.add(id);
		signupPanel.add(j2);
		signupPanel.add(pw);
		signupPanel.add(j3);
		signupPanel.add(checkPw);
		signupPanel.add(signup);
		
		mainPanel.add(signupPanel);
		
		signup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				signupPanel.setVisible(false);
				setLoginPanel();
			}
		});
	}
}