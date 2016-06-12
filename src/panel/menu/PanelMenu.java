package panel.menu;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import main.MainFrame;
import panel.BasePanel;
import panel.PanelManager;
import panel.menu.PanelLevelChoice;
import panel.home.PanelSignup;
import thread.GameThread;
import user.UserManager;

public class PanelMenu extends BasePanel {
	private PanelManager panel;
	
	// ��������
	private static final String GAME_START = "���ӽ���";
	private static final String WORD_SETTING = "�ܾ��Է�";
	private static final String RANKING = "��ŷ����";
	private static final String LOGOUT = "�α׾ƿ�";
	// ��ư �̹���
	private ImageIcon gameIcon = new ImageIcon("images/�����ϱ�.png");
	private ImageIcon wordIcon = new ImageIcon("images/�ܾ��Է�.png");
	private ImageIcon rankingIcon = new ImageIcon("images/��ŷ����.png");
	private ImageIcon logoutIcon = new ImageIcon("images/�α׾ƿ�.png");
	private ImageIcon gameIcon_ = new ImageIcon("images/�����ϱ�2.png");
	private ImageIcon wordIcon_ = new ImageIcon("images/�ܾ��Է�2.png");
	private ImageIcon rankingIcon_ = new ImageIcon("images/��ŷ����2.png");
	private ImageIcon logoutIcon_ = new ImageIcon("images/�α׾ƿ�2.png");
	// ��ư
	private JButton btnGameStart = new JButton(GAME_START, gameIcon);
	private JButton btnWordSetting = new JButton(WORD_SETTING, wordIcon);
	private JButton btnRanking = new JButton(RANKING, rankingIcon);
	private JButton btnLogout = new JButton(LOGOUT, logoutIcon);
	// ������
	private MenuActionEvent menuAction = new MenuActionEvent();
	private MenuMouseEvent menuEvent = new MenuMouseEvent();

	@Override
	public void initPanel() {

	}

	public PanelMenu(PanelManager panel) {
		super("images/menuBG.png");
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		setLocation(0, 25);
		this.panel = panel;

		setMenu(); // ��ư ����
		setButtonListener(); // ������ ����
	}

	/** �г� ���� �޴� ��ư ������ ���� */
	private void setButtonListener() {
		btnGameStart.addActionListener(menuAction);
		btnWordSetting.addActionListener(menuAction);
		btnRanking.addActionListener(menuAction);
		btnLogout.addActionListener(menuAction);

		btnGameStart.addMouseListener(menuEvent);
		btnWordSetting.addMouseListener(menuEvent);
		btnRanking.addMouseListener(menuEvent);
		btnLogout.addMouseListener(menuEvent);
	}

	/** �г� ���� �޴� ��ư�� ���� �� ���� */
	private void setMenu() {
		btnGameStart.setBorderPainted(false);
		btnGameStart.setFocusPainted(false);
		btnGameStart.setContentAreaFilled(false);
		btnGameStart.setBounds(445, 100, 370, 100);

		btnWordSetting.setBorderPainted(false);
		btnWordSetting.setFocusPainted(false);
		btnWordSetting.setContentAreaFilled(false);
		btnWordSetting.setBounds(445, 220, 370, 100);

		btnRanking.setBorderPainted(false);
		btnRanking.setFocusPainted(false);
		btnRanking.setContentAreaFilled(false);
		btnRanking.setBounds(445, 340, 370, 100);

		btnLogout.setBorderPainted(false);
		btnLogout.setFocusPainted(false);
		btnLogout.setContentAreaFilled(false);
		btnLogout.setBounds(445, 460, 370, 100);

		add(btnGameStart); // �ٿ�! �S
		add(btnWordSetting); // �S
		add(btnRanking); // �S
		add(btnLogout); // �S
	}

	public BasePanel getBasePanel() {
		return this; // �ָ�������󤾤�
	}

	/** �޴��гο� ���� ��ư���� �׼Ǹ����� */
	class MenuActionEvent implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton pressedBtn = (JButton) e.getSource();
			switch (pressedBtn.getText()) {
			case GAME_START:
				panel.setContentPane(PanelManager.LEVELCHOICE);
				panel.getLevelChoicePanel().setButtonEnable(); // ��ư ��Ȱ��ȭ ����
				break;
			case WORD_SETTING:
				panel.setContentPane(PanelManager.WORD_SETTING);
				panel.getWordSettingPanel().setFocus(); // ��Ŀ��
				break;
			case RANKING:
				panel.setContentPane(PanelManager.RANKING);
				break;
			case LOGOUT:
				panel.setContentPane(PanelManager.HOME);
				break;
			default:
				break;
			}
		}
	}

//	/** ���� ���� �����带 �����ϰ� ������ ���� */
//	public void gameStart() {
//		GameThread newGame = new GameThread(panel.getGamePanel());
//		int stage = 1; /* ���������� ������ �� �ִ� �г� �����! */
//		newGame.setGame(UserManager.user, stage);
//		newGame.start();
//		newGame.setFocus();
//	}

	/** �޴��гο� ���� ��ư���� ���콺������ */
	class MenuMouseEvent extends MouseAdapter {
		// �ƹ��͵� ���� ��,
		public void mouseExited(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			switch (eventBtn.getText()) {
			case GAME_START:
				btnGameStart.setIcon(gameIcon);
				break;
			case WORD_SETTING:
				btnWordSetting.setIcon(wordIcon);
				break;
			case RANKING:
				btnRanking.setIcon(rankingIcon);
				break;
			case LOGOUT:
				btnLogout.setIcon(logoutIcon);
				break;
			}
		}

		// ��ư ���� ���콺�� �ø� ��,
		public void mouseEntered(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			switch (eventBtn.getText()) {
			case GAME_START:
				btnGameStart.setIcon(gameIcon_);
				break;
			case WORD_SETTING:
				btnWordSetting.setIcon(wordIcon_);
				break;
			case RANKING:
				btnRanking.setIcon(rankingIcon_);
				break;
			case LOGOUT:
				btnLogout.setIcon(logoutIcon_);
				break;
			}
		}
	}
}
