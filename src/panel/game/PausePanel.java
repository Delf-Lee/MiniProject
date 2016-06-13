package panel.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import panel.BasePanel;
import panel.MsgWinow;
import panel.PanelManager;
import thread.GameThread;
import user.UserManager;

public class PausePanel extends BasePanel {
	private PanelManager panel;
	private GameThread thrd;
	private GamePanel screen;
	// ���̺�
	private JLabel stringBoxPause;
	// ��ư
	private JButton btnStart;
	private JButton btnRestart;
	private JButton btnLevelChoice;
	private JButton btnMenu;
	private JButton btnLogout;
	private JButton btnExit;

	// ��ġ ��������
	private static final int X = 75;
	private static final int Y = 100;

	public PausePanel(int x, int y, int width, int height, PanelManager panel) {
		super(/*�̹��� ���*/);
		this.panel = panel;
		this.screen = panel.getGamePanel();
		setBounds(x, y, width, height);
		setBackground(Color.CYAN); // ���� ���� ����

		setComponent(); // ��ư ����
		setListener();
	}

	/** ������ ���� */
	private void setListener() {
		btnStart.addActionListener(new PauseListener());
		btnRestart.addActionListener(new PauseListener());
		btnLevelChoice.addActionListener(new PauseListener());
		btnMenu.addActionListener(new PauseListener());
		btnLogout.addActionListener(new PauseListener());
		btnExit.addActionListener(new PauseListener());
	}

	/** ������Ʈ ���� �� ��ġ */
	private void setComponent() {
		stringBoxPause = new JLabel("Pause", JLabel.CENTER);
		stringBoxPause.setFont(new Font("���� ���", Font.BOLD, 30));
		stringBoxPause.setBounds(X, 10, 200, 100);

		btnStart = new JButton("���� �簳");
		btnStart.setFont(new Font("���� ���", Font.BOLD, 30));
		btnStart.setBounds(X, Y, 200, 50);

		btnRestart = new JButton("�����");
		btnRestart.setFont(new Font("���� ���", Font.BOLD, 30));
		btnRestart.setBounds(X, Y + 60, 200, 50);

		btnLevelChoice = new JButton("���� ����");
		btnLevelChoice.setFont(new Font("���� ���", Font.BOLD, 30));
		btnLevelChoice.setBounds(X, Y + (60 * 2), 200, 50);

		btnMenu = new JButton("�޴�");
		btnMenu.setFont(new Font("���� ���", Font.BOLD, 30));
		btnMenu.setBounds(X, Y + (60 * 3), 200, 50);

		btnLogout = new JButton("�α׾ƿ�");
		btnLogout.setFont(new Font("���� ���", Font.BOLD, 30));
		btnLogout.setBounds(X, Y + (60 * 4), 200, 50);

		btnExit = new JButton("����");
		btnExit.setFont(new Font("���� ���", Font.BOLD, 30));
		btnExit.setBounds(X, Y + (60 * 5), 200, 50);

		add(stringBoxPause);
		add(btnStart);
		add(btnRestart);
		add(btnLevelChoice);
		add(btnMenu);
		add(btnLogout);
		add(btnExit);
	}

	/** �г� �� ��ư�� ���� ������ */
	class PauseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton pressedBtn = (JButton) e.getSource();

			switch (pressedBtn.getText()) {
			case "���� �簳":
				setVisible(false);
				thrd.continueGame();

				break;
			case "�����":
				boolean confirm = MsgWinow.confirm("����� �Ͻðڽ��ϱ�?");
				if (confirm) {
					thrd.initGame();
					thrd.interrupt();
					panel.setContentPane(PanelManager.GAME);
					panel.getLevelChoicePanel().gameStart(screen.getLevel());
					setVisible(false);
				}
				break;

			case "���� ����":
				panel.getLevelChoicePanel().setNowPanel(1);
				screen.add(panel.getLevelChoicePanel());
				panel.getLevelChoicePanel().setVisible(true);
				panel.getLevelChoicePanel().setButtonEnable(); // ��ư ��Ȱ��ȭ ����
				setVisible(false);
				break;

			case "�޴�":
				confirm = MsgWinow.confirm("�޴�ȭ������ ���ư��ðڽ��ϱ�?");
				if (confirm) {
					panel.setContentPane(PanelManager.MENU);
					thrd.initGame();
					thrd.interrupt();
					setVisible(false);
				}
				else {

				}
				break;

			case "�α׾ƿ�":
				confirm = MsgWinow.confirm("�α׾ƿ� �Ͻðڽ��ϱ�?");
				if (confirm) {
					panel.setContentPane(PanelManager.HOME);
					thrd.initGame();
					thrd.interrupt();
				}
				else {

				}
				setVisible(false);
				break;

			case "����":
				confirm = MsgWinow.confirm("���� �Ͻðڽ��ϱ�?");
				if (confirm) {
					UserManager.saveUserData();
					System.exit(0);
				}
				else {

				}
				break;
			}
		}
	}

	public void setThread(GameThread thrd) {
		this.thrd = thrd;
	}

	@Override
	public void initPanel() {
		// TODO Auto-generated method stub

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
