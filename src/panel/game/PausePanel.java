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
	// 스레드
	private GameThread thrd;

	// 패널
	private GamePanel screen;
	private PanelManager panel;
	private LevelChoicePanel levelChoice;

	// 레이블
	private JLabel stringBoxPause;

	// 버튼
	private JButton[] pauseBtns;
	private JButton btnStart;
	private JButton btnRestart;
	private JButton btnLevelChoice;
	private JButton btnMenu;
	private JButton btnLogout;
	private JButton btnExit;

	// 리스너
	private PauseListener pasuseListener = new PauseListener();
	private MenuMouseEvent mouseEvent = new MenuMouseEvent();

	// 위치 정적변수
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
	private String[][] btnImageName = { { "게임재개1", "게임재개2", "게임재개3" }, { "재시작1", "재시작2", "재시작3" }, { "레벨선택1", "레벨선택2", "레벨선택3" },
			{ "메뉴1", "메뉴2", "메뉴3" }, { "로그아웃_1", "로그아웃_2", "로그아웃_3" }, { "종료1", "종료2", "종료3" } };

	public PausePanel(int x, int y, int width, int height, PanelManager panel) {
		super("images/일시정지.png");
		this.panel = panel;
		this.screen = panel.getGamePanel();
		this.levelChoice = panel.getLevelChoicePanel();
		setBounds(x, y, width, height);
		setBackground(Color.CYAN); // 삭제 예정 라인
		setComponent(); // 버튼 세팅
		setListener();
	}

	/** 리스너 설정 */
	private void setListener() {
		for (int i = 0; i < pauseBtns.length; i++) {
			pauseBtns[i].addActionListener(pasuseListener);
			pauseBtns[i].addMouseListener(mouseEvent);
		}
	}

	/** 컴포넌트 설정 및 배치 */
	private void setComponent() {

		// 버튼 이미지 가져오기
		for (int i = 0; i < btnImages.length; i++) {
			for (int j = 0; j < btnImages[i].length; j++) {
				btnImages[i][j] = new ImageIcon("images/" + btnImageName[i][j] + ".png");
			}
		}

		// 레이블에 버튼 이미지 삽입		
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

	/** 메뉴패널에 대한 버튼들의 마우스리스너 */
	class MenuMouseEvent extends MouseAdapter {
		// 마우스 땜
		public void mouseExited(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			int btnName = Integer.parseInt(eventBtn.getName());
			pauseBtns[btnName].setIcon(btnImages[btnName][EXITED]);
		}

		// 마우스 얹음
		public void mouseEntered(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			int btnName = Integer.parseInt(eventBtn.getName());
			pauseBtns[btnName].setIcon(btnImages[btnName][ENTERD]);
		}

		// 마우스 클릭
		public void mousePressed(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			int btnName = Integer.parseInt(eventBtn.getName());
			pauseBtns[btnName].setIcon(btnImages[btnName][PRESSED]);
		}
	}

	/** 패널 내 버튼에 대한 리스너 */
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
				boolean confirm = MsgWinow.confirm("재시작 하시겠습니까?");
				if (confirm) {
					thrd.initGame(); // 게임 초기화
					thrd.interrupt(); // 스레드 종료
					panel.setContentPane(PanelManager.GAME); // 화면 준비
					levelChoice.gameStart(screen.getLevel()); // 게임 재시작
					setVisible(false); // 퍼즈 패널 비가시화
				}
				break;

			case LEVEL:
				levelChoice.setNowPanel(LevelChoicePanel.PAUSE); // 현재 패널은 pause
				//setEnable(false); //puase패널 비활성화
				setVisible(false);
				screen.add(levelChoice); // 레벨 선택 패널 부착
				screen.setComponentZOrder(levelChoice, 0); // 레벨 선택 패널 맨 앞으로
				levelChoice.setVisible(true); // 레벨 선택 패널 가시화
				levelChoice.setButtonEnable(); // 버튼 비활성화 설정
				break;

			case MENU:
				confirm = MsgWinow.confirm("메뉴화면으로 돌아가시겠습니까?");
				if (confirm) {
					panel.setContentPane(PanelManager.MENU);
					thrd.initGame();
					thrd.interrupt();
					setVisible(false);
				}
				break;

			case LOGOUT:
				confirm = MsgWinow.confirm("로그아웃 하시겠습니까?");
				if (confirm) {
					panel.setContentPane(PanelManager.HOME);
					thrd.initGame();
					thrd.interrupt();
					setVisible(false);
				}
				break;

			case EXIT:
				confirm = MsgWinow.confirm("종료 하시겠습니까?");
				if (confirm) {
					UserManager.saveUserData();
					System.exit(0);
				}
				break;
				
			}
			pauseBtns[btnName].setIcon(btnImages[btnName][EXITED]);
		}
	}

	/** 일시정시에서 게임 재시작 시, 3초 카운트 다운 시행 */
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
	 * 스레드 설정
	 * 해당 패널에서 스레드를 제어하기 위해 스레드 객체를 가짐
	 * 스레드는 매번 바뀔 수 있기 때문에 따로 초기화 메소드를 제작
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
