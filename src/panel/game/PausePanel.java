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
		stringBoxPause.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		stringBoxPause.setBounds(X, 10, 200, 100);

		btnStart = new JButton("게임 재개");
		btnStart.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		btnStart.setBounds(X, Y, 200, 50);

		btnRestart = new JButton("재시작");
		btnRestart.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		btnRestart.setBounds(X, Y + 60, 200, 50);

		btnLevelChoice = new JButton("레벨 선택");
		btnLevelChoice.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		btnLevelChoice.setBounds(X, Y + (60 * 2), 200, 50);

		btnMenu = new JButton("메뉴");
		btnMenu.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		btnMenu.setBounds(X, Y + (60 * 3), 200, 50);

		btnLogout = new JButton("로그아웃");
		btnLogout.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		btnLogout.setBounds(X, Y + (60 * 4), 200, 50);

		btnExit = new JButton("종료");
		btnExit.setFont(new Font("맑은 고딕", Font.BOLD, 30));
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
			case "게임 재개":
				setVisible(false);
				thrd.continueGame();

				break;
			case "재시작":
				boolean confirm = MsgWinow.confirm("재시작 하시겠습니까?");
				if (confirm) {
					thrd.initGame();
					thrd.interrupt();
					panel.setContentPane(PanelManager.GAME);
					panel.getLevelChoicePanel().gameStart(screen.getLevel());
					setVisible(false);
				}
				break;

			case "레벨 선택":
				panel.getLevelChoicePanel().setNowPanel(1);
				screen.add(panel.getLevelChoicePanel());
				panel.getLevelChoicePanel().setVisible(true);
				panel.getLevelChoicePanel().setButtonEnable(); // 버튼 비활성화 설정
				setVisible(false);
				break;

			case "메뉴":
				confirm = MsgWinow.confirm("메뉴화면으로 돌아가시겠습니까?");
				if (confirm) {
					panel.setContentPane(PanelManager.MENU);
					thrd.initGame();
					thrd.interrupt();
					setVisible(false);
				}
				else {

				}
				break;

			case "로그아웃":
				confirm = MsgWinow.confirm("로그아웃 하시겠습니까?");
				if (confirm) {
					panel.setContentPane(PanelManager.HOME);
					thrd.initGame();
					thrd.interrupt();
				}
				else {

				}
				setVisible(false);
				break;

			case "종료":
				confirm = MsgWinow.confirm("종료 하시겠습니까?");
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
