package panel.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import panel.BasePanel;
import panel.MsgWinow;
import panel.PanelManager;
import panel.menu.LevelChoicePanel;
import thread.GameThread;
import user.UserManager;

public class PausePanel extends BasePanel {
	// ������
	private GameThread thrd;

	// �г�
	private GamePanel screen;
	private PanelManager panel;
	private LevelChoicePanel levelChoice;

	// ���̺�
	private JLabel stringBoxPause;

	// ��ư
	private JButton[] pauseBtns;
	private JButton btnStart;
	private JButton btnRestart;
	private JButton btnLevelChoice;
	private JButton btnMenu;
	private JButton btnLogout;
	private JButton btnExit;

	// ������
	private PauseListener pasuseListener = new PauseListener();
	private MenuMouseEvent mouseEvent = new MenuMouseEvent();

	// ��ġ ��������
	private static final int X = 100;
	private static final int Y = 20;

	private static final int EXITED = 0;
	private static final int ENTERD = 1;
	private static final int PRESSED = 2;

	private static final int RESUME = 0;
	private static final int RESTART = 1;
	private static final int LEVEL = 2;
	private static final int MENU = 3;
	private static final int LOGOUT = 4;
	private static final int EXIT = 5;

	private ImageIcon btnImages[][] = new ImageIcon[6][3];
	private String[][] btnImageName = { { "�����簳1", "�����簳2", "�����簳3" }, { "�����1", "�����2", "�����3" }, { "��������1", "��������2", "��������3" },
			{ "�޴�1", "�޴�2", "�޴�3" }, { "�α׾ƿ�_1", "�α׾ƿ�_2", "�α׾ƿ�_3" }, { "����1", "����2", "����3" } };

	public PausePanel(int x, int y, int width, int height, PanelManager panel) {
		super("images/�Ͻ�����.png");
		this.panel = panel;
		this.screen = panel.getGamePanel();
		this.levelChoice = panel.getLevelChoicePanel();
		setBounds(x, y, width, height);
		setBackground(Color.CYAN); // ���� ���� ����
		setComponent(); // ��ư ����
		setListener();
	}

	/** ������ ���� */
	private void setListener() {
		for (int i = 0; i < pauseBtns.length; i++) {
			pauseBtns[i].addActionListener(pasuseListener);
			pauseBtns[i].addMouseListener(mouseEvent);
		}
	}

	/** ������Ʈ ���� �� ��ġ */
	private void setComponent() {

		// ��ư �̹��� ��������
		for (int i = 0; i < btnImages.length; i++) {
			for (int j = 0; j < btnImages[i].length; j++) {
				btnImages[i][j] = new ImageIcon("images/" + btnImageName[i][j] + ".png");
			}
		}

		// ���̺� ��ư �̹��� ����		
		pauseBtns = new JButton[6];
		for (int i = 0; i < pauseBtns.length; i++) {
			pauseBtns[i] = new JButton(btnImages[i][0]);
			pauseBtns[i].setName(Integer.toString(i));
			pauseBtns[i].setBounds(X, Y + (60 * (i + 1)), 150, 50);

			pauseBtns[i].setBorderPainted(false);
			pauseBtns[i].setFocusPainted(false);
			pauseBtns[i].setContentAreaFilled(false);

			add(pauseBtns[i]);
		}

		stringBoxPause = new JLabel("PAUSE", JLabel.CENTER);
		stringBoxPause.setForeground(Color.WHITE);
		stringBoxPause.setFont(new Font("Silkscreen", Font.BOLD, 50));
		stringBoxPause.setBounds(X - 20, 10, 200, 50);

		add(stringBoxPause);
	}

	/** �޴��гο� ���� ��ư���� ���콺������ */
	class MenuMouseEvent extends MouseAdapter {
		// ���콺 ��
		public void mouseExited(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			int btnName = Integer.parseInt(eventBtn.getName());
			pauseBtns[btnName].setIcon(btnImages[btnName][EXITED]);
		}

		// ���콺 ����
		public void mouseEntered(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			int btnName = Integer.parseInt(eventBtn.getName());
			pauseBtns[btnName].setIcon(btnImages[btnName][ENTERD]);
		}

		// ���콺 Ŭ��
		public void mousePressed(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			int btnName = Integer.parseInt(eventBtn.getName());
			pauseBtns[btnName].setIcon(btnImages[btnName][PRESSED]);
		}
	}

	/** �г� �� ��ư�� ���� ������ */
	class PauseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton pressedBtn = (JButton) e.getSource();
			int btnName = Integer.parseInt(pressedBtn.getName());
			switch (btnName) {
			case RESUME:
				setVisible(false);
				TimerThread th = new TimerThread();
				th.start();
				break;

			case RESTART:
				boolean confirm = MsgWinow.confirm("����� �Ͻðڽ��ϱ�?");
				if (confirm) {
					thrd.initGame(); // ���� �ʱ�ȭ
					thrd.interrupt(); // ������ ����
					panel.setContentPane(PanelManager.GAME); // ȭ�� �غ�
					levelChoice.gameStart(screen.getLevel()); // ���� �����
					setVisible(false); // ���� �г� �񰡽�ȭ
				}
				break;

			case LEVEL:
				levelChoice.setNowPanel(LevelChoicePanel.PAUSE); // ���� �г��� pause
				//setEnable(false); //puase�г� ��Ȱ��ȭ
				setVisible(false);
				screen.add(levelChoice); // ���� ���� �г� ����
				screen.setComponentZOrder(levelChoice, 0); // ���� ���� �г� �� ������
				levelChoice.setVisible(true); // ���� ���� �г� ����ȭ
				levelChoice.setButtonEnable(); // ��ư ��Ȱ��ȭ ����
				break;

			case MENU:
				confirm = MsgWinow.confirm("�޴�ȭ������ ���ư��ðڽ��ϱ�?");
				if (confirm) {
					panel.setContentPane(PanelManager.MENU);
					thrd.initGame();
					thrd.interrupt();
					setVisible(false);
				}
				break;

			case LOGOUT:
				confirm = MsgWinow.confirm("�α׾ƿ� �Ͻðڽ��ϱ�?");
				if (confirm) {
					panel.setContentPane(PanelManager.HOME);
					thrd.initGame();
					thrd.interrupt();
					setVisible(false);
				}
				break;

			case EXIT:
				confirm = MsgWinow.confirm("���� �Ͻðڽ��ϱ�?");
				if (confirm) {
					UserManager.saveUserData();
					System.exit(0);
				}
				break;
				
			}
			pauseBtns[btnName].setIcon(btnImages[btnName][EXITED]);
		}
	}

	/** �Ͻ����ÿ��� ���� ����� ��, 3�� ī��Ʈ �ٿ� ���� */
	class TimerThread extends Thread {
		JLabel timerLabel = new JLabel("3");

		public TimerThread() {
			timerLabel.setFont(new Font("Silkscreen", Font.BOLD, 150));
			timerLabel.setBounds(490, 300, 150, 150);
			screen.add(timerLabel);
			
		}

		public void run() {
			while (true) {
				try {
					//repaint();
					sleep(1000);
				} catch (InterruptedException e) {
					return;
				}
				int n = Integer.parseInt(timerLabel.getText());
				n--;
				if (n == 0) {
					screen.remove(timerLabel);
					thrd.continueGame();
					return;
				}
				timerLabel.setText(Integer.toString(n));
			}
		}
	}

	/**
	 * ������ ����
	 * �ش� �гο��� �����带 �����ϱ� ���� ������ ��ü�� ����
	 * ������� �Ź� �ٲ� �� �ֱ� ������ ���� �ʱ�ȭ �޼ҵ带 ����
	 */
	public void setThread(GameThread thrd) {
		this.thrd = thrd;
		levelChoice.setThread(thrd);
	}

	@Override
	public void initPanel() {

	}

	public void setEnable(boolean i) {
		btnStart.setEnabled(i);
		btnRestart.setEnabled(i);
		btnLevelChoice.setEnabled(i);
		btnMenu.setEnabled(i);
		btnLogout.setEnabled(i);
		btnExit.setEnabled(i);
	}
}
