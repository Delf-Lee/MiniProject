package panel.game;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;

import panel.BasePanel;
import panel.PanelManager;

public class PausePanel extends BasePanel {
	private PanelManager panel;
	// ���̺�
	JLabel stringBoxPause;
	// ��ư
	JButton btnStart;
	JButton btnRestart;
	JButton btnLevelChoice;
	JButton btnMenu;
	
	// ��ġ ��������
	private static final int X = 70;
	private static final int Y = 100;

	public PausePanel(int x, int y, int width, int height, PanelManager panel) {
		super(/*�̹��� ���*/);
		this.panel = panel;
		setBounds(x, y, width, height);
		setBackground(Color.CYAN); // ���� ���� ����

		setComponent(); // ��ư ����
		setListener();
	}
	
	/** ������ ���� */
	private void setListener() {
//		btnStart.addMouseListener(new );
//		btnMenu.addMouseListener(new );
	}
	
	/** ������Ʈ ���� �� ��ġ */
	private void setComponent() {
		stringBoxPause = new JLabel("Pause", JLabel.CENTER);
		stringBoxPause.setFont(new Font("���� ���", Font.BOLD, 30));
		stringBoxPause.setBounds(50, 10, 200, 100);

		btnStart = new JButton("���� �簳");
		btnStart.setFont(new Font("���� ���", Font.BOLD, 30));
		btnStart.setBounds(50, 100, 200, 50);
		
		btnRestart = new JButton("�����");
		btnRestart.setFont(new Font("���� ���", Font.BOLD, 30));
		btnRestart.setBounds(50, 170, 200, 50);
		
		btnLevelChoice = new JButton("���� ����");
		btnLevelChoice.setFont(new Font("���� ���", Font.BOLD, 30));
		btnLevelChoice.setBounds(50, 240, 200, 50);
		
		btnMenu = new JButton("�޴�");
		btnMenu.setFont(new Font("���� ���", Font.BOLD, 30));
		btnMenu.setBounds(50, 310, 200, 50);
		
		add(stringBoxPause);
		add(btnStart);
		add(btnRestart);
		add(btnLevelChoice);
		add(btnMenu);
	}

	
	@Override
	public void initPanel() {
		// TODO Auto-generated method stub
		
	}
}
