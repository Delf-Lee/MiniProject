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
	// 레이블
	private JLabel stringBoxPause;
	// 버튼
	private JButton btnStart;
	private JButton btnRestart;
	private JButton btnLevelChoice;
	private JButton btnMenu;
	private JButton btnLogout;
	private JButton btnExit;

	// 위치 정적변수
	private static final int X = 75;
	private static final int Y = 100;

	public PausePanel(int x, int y, int width, int height, PanelManager panel) {
		super(/*이미지 경로*/);
		this.panel = panel;
		this.screen = panel.getGamePanel();
		setBounds(x, y, width, height);
		setBackground(Color.CYAN); // 삭제 예정 라인
		setComponent(); // 버튼 세팅
		setListener();
	}

	/** 리스너 설정 */
	private void setListener() {
		btnStart.addActionListener(new PauseListener());
		btnRestart.addActionListener(new PauseListener());
		btnLevelChoice.addActionListener(new PauseListener());
		btnMenu.addActionListener(new PauseListener());
		btnLogout.addActionListener(new PauseListener());
		btnExit.addActionListener(new PauseListener());
	}

	/** 컴포넌트 설정 및 배치 */
	private void setComponent() {
		stringBoxPause = new JLabel("Pause", JLabel.CENTER);
		stringBoxPause.setFont(new Font("Silkscreen", Font.BOLD, 50));
		stringBoxPause.setBounds(X, 10, 200, 100);

		btnStart = new JButton("RESUME");
		btnStart.setFont(new Font("Silkscreen", Font.BOLD, 30));
		btnStart.setBounds(X, Y, 200, 50);

		btnRestart = new JButton("RESTART");
		btnRestart.setFont(new Font("Silkscreen", Font.BOLD, 30));
		btnRestart.setBounds(X, Y + 60, 200, 50);

		btnLevelChoice = new JButton("LEVEL CHOICE");
		btnLevelChoice.setFont(new Font("Silkscreen", Font.BOLD, 30));
		btnLevelChoice.setBounds(X-50, Y + (60 * 2), 300, 50);

		btnMenu = new JButton("MENU");
		btnMenu.setFont(new Font("Silkscreen", Font.BOLD, 30));
		btnMenu.setBounds(X, Y + (60 * 3), 200, 50);

		btnLogout = new JButton("LOGOUT");
		btnLogout.setFont(new Font("Silkscreen", Font.BOLD, 30));
		btnLogout.setBounds(X, Y + (60 * 4), 200, 50);

		btnExit = new JButton("EXIT");
		btnExit.setFont(new Font("Silkscreen", Font.BOLD, 30));
		btnExit.setBounds(X, Y + (60 * 5), 200, 50);

		add(stringBoxPause);
		add(btnStart);
		add(btnRestart);
		add(btnLevelChoice);
		add(btnMenu);
		add(btnLogout);
		add(btnExit);
	}

	/** 패널 내 버튼에 대한 리스너 */
	class PauseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton pressedBtn = (JButton) e.getSource();

			switch (pressedBtn.getText()) {
			case "RESUME":
				setVisible(false);
				JLabel timerLabel = new JLabel("3");
				timerLabel.setFont(new Font("Silkscreen", Font.BOLD, 100));
				timerLabel.setBounds(490, 300, 100, 100);
				panel.getGamePanel().add(timerLabel);
				TimerThread th = new TimerThread(timerLabel);
				th.start();
				break;
			case "RESTART":
				boolean confirm = MsgWinow.confirm("재시작 하시겠습니까?");
				if (confirm) {
					thrd.initGame();
					thrd.interrupt();
					panel.setContentPane(PanelManager.GAME);
					panel.getLevelChoicePanel().gameStart(screen.getLevel());
					setVisible(false);
				}
				break;
			case "LEVEL CHOICE":
				panel.getLevelChoicePanel().setNowPanel(1);
				screen.add(panel.getLevelChoicePanel());
				panel.getLevelChoicePanel().setVisible(true);
				panel.getLevelChoicePanel().setButtonEnable(); // 버튼 비활성화 설정
				setVisible(false);
				break;
			case "MENU":
				confirm = MsgWinow.confirm("메뉴화면으로 돌아가시겠습니까?");
				if (confirm) {
					panel.setContentPane(PanelManager.MENU);
					thrd.initGame();
					thrd.interrupt();
					setVisible(false);
				}
				break;
			case "LOGOUT":
				confirm = MsgWinow.confirm("로그아웃 하시겠습니까?");
				if (confirm) {
					panel.setContentPane(PanelManager.HOME);
					thrd.initGame();
					thrd.interrupt();
					setVisible(false);
				}
				break;
			case "EXIT":
				confirm = MsgWinow.confirm("종료 하시겠습니까?");
				if (confirm) {
					UserManager.saveUserData();
					System.exit(0);
				}
				break;
			}
		}
	}
	
	class TimerThread extends Thread {
		JLabel la;
		public TimerThread(JLabel la) {
			this.la = la;
		}
		public void run() {
			while (true) {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					return;
				}
				int n = Integer.parseInt(la.getText());
				n--;
				if (n == 0) {
					thrd.continueGame();
					la.setVisible(false);
					return;
				}
				la.setText(Integer.toString(n));
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
