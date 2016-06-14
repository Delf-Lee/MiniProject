package panel.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import panel.BasePanel;
import panel.PanelManager;
import thread.GameThread;
import user.UserManager;

public class LevelChoicePanel extends BasePanel {

	public static final int MENU = 0;
	public static final int PAUSE = 1;

	private PanelManager panel;
	private GameThread thrd;
	// 레이블
	JLabel stringBoxLevelChoice;
	
	private ImageIcon backIcon = new ImageIcon("images/back.png");
	
	private ImageIcon level1Icon = new ImageIcon("images/1.png");
	private ImageIcon level2Icon = new ImageIcon("images/2.png");
	// 버튼
	JButton[] level;
	JButton btnBack;
	// 위치 정적변수
	private static final int X = 20;
	private static final int Y = 60;
	private int nowPanel;

	public LevelChoicePanel(int x, int y, int width, int height, PanelManager panel) {
		super("images/levelchoiceBG.png");
		this.panel = panel;
		setBounds(x, y, width, height);
		setBackground(Color.CYAN); // 삭제 예정 라인

		setComponent(); // 버튼 세팅
		setListener();
	}

	/** 리스너 설정 */
	private void setListener() {
		for (int i = 0; i < level.length; i++) {
			level[i].addActionListener(new LevelChoiceListener());
		}
		btnBack.addActionListener(new LevelChoiceListener());
	}

	/** 컴포넌트 설정 및 배치 */
	private void setComponent() {
//		stringBoxLevelChoice = new JLabel("Level 선택", JLabel.CENTER);
//		stringBoxLevelChoice.setFont(new Font("맑은 고딕", Font.BOLD, 30));
//		stringBoxLevelChoice.setBounds(100, 10, 200, 100);

		level = new JButton[10];
		for (int i = 2; i < level.length; i++) {
			level[i] = new JButton("Level." + Integer.toString(i + 1));
			level[i].setFont(new Font("맑은 고딕", Font.BOLD, 20));
			if (i < 5) {
				level[i].setBounds(X, Y + (50 * i), 115, 40);
			}
			else {
				level[i].setBounds(X + 150, Y + (50 * (i - 5)), 115, 40);
			}
			add(level[i]);
		}
		
		level[0] = new JButton("1", level1Icon);
		level[0].setBorderPainted(false);
		level[0].setFocusPainted(false);
		level[0].setContentAreaFilled(false);
		level[0].setBounds(X, Y, 185, 80);
		
		level[1] = new JButton("2", level2Icon);
		level[1].setBorderPainted(false);
		level[1].setFocusPainted(false);
		level[1].setContentAreaFilled(false);
		level[1].setBounds(X, Y + 70, 185, 80);
		
		btnBack = new JButton("back", backIcon);
		btnBack.setBorderPainted(false);
		btnBack.setFocusPainted(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setBounds(170, 370, 80, 70);

		add(level[0]);
		add(level[1]);
		//add(stringBoxLevelChoice);
		add(btnBack);
	}

	/** 유저가 플레이할 수 없는 레벨 버튼 비활성화 */
	public void setButtonEnable() {
		System.out.println(UserManager.user.getLastStage());
		for (int i = UserManager.user.getLastStage(); i < level.length; i++) {
			level[i].setEnabled(false);
		}
	}

	/** 비활성화 해제(초기화) */
	private void InitButton() {
		for (int i = UserManager.user.getLastStage(); i < level.length; i++) {
			level[i].setEnabled(true);
		}
	}

	public void setNowPanel(int flag) {
		this.nowPanel = flag;
	}

	/** 패널 내 버튼에 대한 리스너 */
	class LevelChoiceListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton pressedBtn = (JButton) e.getSource();

			switch (pressedBtn.getText()) {
			case "back":
				// 뒤로가기 버튼 누를 시,
				if (nowPanel == MENU) { // 메뉴에서 레벨선택
					panel.setContentPane(PanelManager.MENU);
				}
				else if (nowPanel == PAUSE) { // pause에서 레벨선택
					//panel.getGamePanel().add(panel.getLevelChoicePanel());
					panel.getPausePanel().setVisible(true);
					setVisible(false);
				}
				break;

			default: // 그 외에 레벨을 선택 시,
				setVisible(false);
				if (nowPanel == PAUSE) {
					thrd.initGame(); // pause에서 레벨 선택 시, 게임 재시작을 위해 초기화
				}
				String tmp = pressedBtn.getText();
				int selectedLevel = Integer.parseInt(tmp.substring(tmp.length() - 1));
				panel.setContentPane(PanelManager.GAME);
				gameStart(selectedLevel);
				break;
			}
			InitButton();
		}
	}

	/** 게임 구동 스레드를 생성하고 게임을 실행 */
	public void gameStart(int level) {
		GameThread newGame = new GameThread(panel);
		newGame.setGame(UserManager.user, level);
		newGame.start();
		newGame.setFocus();
	}

	public void setThread(GameThread thrd) {
		this.thrd = thrd;
	}

	@Override
	public void initPanel() {
		// TODO Auto-generated method stub
	}
}