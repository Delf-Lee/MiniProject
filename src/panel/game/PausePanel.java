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
	// 레이블
	JLabel stringBoxPause;
	// 버튼
	JButton btnStart;
	JButton btnRestart;
	JButton btnLevelChoice;
	JButton btnMenu;
	JButton btnLogout;
	JButton btnExit;
	
	
	// 위치 정적변수
	private static final int X = 75;
	private static final int Y = 100;

	public PausePanel(int x, int y, int width, int height, PanelManager panel) {
		super(/*이미지 경로*/);
		this.panel = panel;
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
		btnLevelChoice.setBounds(X, Y + (60*2), 200, 50);
		
		btnMenu = new JButton("메뉴");
		btnMenu.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		btnMenu.setBounds(X, Y + (60*3), 200, 50);
		
		btnLogout = new JButton("로그아웃");
		btnLogout.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		btnLogout.setBounds(X, Y + (60*4), 200, 50);
		
		btnExit = new JButton("종료");
		btnExit.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		btnExit.setBounds(X, Y + (60*5), 200, 50);
		
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
				
				break;
			case "재시작":
				thrd.initGame();
				break;
			case "레벨 선택":
				panel.setContentPane(PanelManager.LEVELCHOICE);
				thrd.initGame();
				break;
			case "메뉴":
				panel.setContentPane(PanelManager.MENU);
				thrd.initGame();
				break;
			case "로그아웃":
				boolean confirm = MsgWinow.confirm("로그아웃 하시겠습니까?");
				if (confirm) {
					panel.setContentPane(PanelManager.HOME);
				}
				thrd.initGame();
				break;
			case "종료":
				confirm = MsgWinow.confirm("종료하시겠습니까?");
				if (confirm) {
					UserManager.saveUserData(); // User 벡터 파일에 쓰기
					System.exit(0);
				}
				thrd.initGame();
				break;
			}
			setVisible(false);
		}
	}
	
	public void setThread(GameThread thrd) {
		this.thrd = thrd;
	}
	
	@Override
	public void initPanel() {
		// TODO Auto-generated method stub
		
	}
}
