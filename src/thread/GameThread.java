package thread;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;

import Animation.ReadyAnimation;
import panel.MsgWinow;
import panel.PanelManager;
import panel.game.GamePanel;
import panel.game.PausePanel;
import user.Player;
import user.User;
import word.Word;
import word.WordManager;

public class GameThread extends Thread {
	private static final int SLOW = 0;
	private static final int UNBEATABLE = 1;
	private static final int NET_MODE = 2;
	private static final int ADD_LIFE = 3;
	private static final int ALL_SAVE = 4;

	private static final boolean itemFlag[] = { false, false, false };

	private WordManager wordList;
	private Player player;
	private PanelManager panel;
	private GamePanel screen;
	private PausePanel pauseMenu;

	private MyKeyListener listener = new MyKeyListener();

	private boolean pause = false;
	private boolean terminate = false;
	private int item[] = { 0, 0, 0, 0, 0 };
	private int level;
	private int preTime;
	private int combo;
	private int itemTime;

	/** 생성자 */
	public GameThread(PanelManager panel) {
		this.panel = panel; // 패널에 대한 레퍼런스 설정
		screen = panel.getGamePanel();
		pauseMenu = panel.getPausePanel();
		
		wordList = new WordManager(this);
		
		panel.getPausePanel().setThread(this); // 선언 순서 때문에 여기서 스레드 레퍼런스 전달
		
		screen.add(pauseMenu); // puase 패널을 미리 붙여놓음 (배치 문제때문에)
		pauseMenu.setVisible(false);
	}

	/** 플레이어와 게임레벨을 설정 */
	public void setGame(User user, int level) {
		player = new Player(user); // 게임할 플레이어 설정
		this.level = level; // 플레이어가 플레이 가능한 게임 레벨

		new ReadyAnimation(screen, level, this); // 준비 애니메이션
		preTime = (int) System.currentTimeMillis(); // 현재 시간 저장 (제한시간을 위함)
		screen.setInfo(level, preTime); // 게임화면에서 정보창 설정
	}

	public void run() {
		try {
			while (true) {
				createWord(); // 단어 객체 생성
				wordList.flowWord(); // 단어 이동

				checkTime();
				checkLife();
				checkPause();
				checkItem();

				screen.repaint();
				sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/** 키 리스너 */
	class MyKeyListener extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER: // 엔터
				System.out.println("엔터");
				Word findWord = wordList.removeWord(screen.getWordString());
				if (findWord != null) {
					removeWord(findWord); // 패널에서 단어객체 제거
					plusScore(); // 점수 증가
				}
				else {
					combo = 0; // 단어가 입력이 틀리면 콤보 초기화
				}
				screen.initTextField();
				break;
			case KeyEvent.VK_ESCAPE: // ECS
				if (pause()) {
					popPausePanel();
				}
				else {
					panel.getPausePanel().setVisible(false);
					JLabel timerLabel = new JLabel("3");
					timerLabel.setFont(new Font("Silkscreen", Font.BOLD, 100));
					timerLabel.setBounds(490, 300, 100, 100);
					panel.getGamePanel().add(timerLabel);
					TimerThread th = new TimerThread(timerLabel);
					th.start();
				}
				break;
			case KeyEvent.VK_BACK_SPACE: // 백스페이스
				combo = 0; // 백스페이스 입력 시 콤보 초기화
				break;
			default:
				if (e.getKeyChar() >= SLOW && e.getKeyChar() >= ALL_SAVE) {
					useItem(e.getKeyCode());
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
					continueGame();
					la.setVisible(false);
					return;
				}
				la.setText(Integer.toString(n));
			}
		}
	}

	/** 게임중 esc키를 누를 시, 일시 정지한다. */
	public boolean pause() {
		if (pause) {
			//continueGame();
			return false;
		}
		else {
			pause = true;
			return true;
		}
	}

	/** 랜덤한 확률로 단어 생성 */
	private void createWord() {
		if (wordList.isCreate) {
			int appearRatio = (int) (Math.random() * 200 - (level * 1)); // 출현 확률

			if (appearRatio < 10) {
				Word newWord = wordList.createWord(level); // 생성
				addWordToPanel(newWord); // 패널에 부착
			}
		}
		wordList.isCreate();
	}

	/** 단어 객체를 패널에 부착 */
	private void addWordToPanel(Word word) {
		screen.add(word.getWordObject());
	}

	/** 텍스트필드에 포커스 설정, 리스너 설정 */
	public void setFocus() {
		screen.getTextField().requestFocus(true);
		screen.getTextField().addKeyListener(listener);
	}

	private void gameOver() {
		/*게임 데이터 저장*/
	}

	/** 단어 객체를 패널로부터 삭제 */
	public void removeWord(Word word) {
		screen.remove(word.getWordObject());
	}

	/** 단어 맞출 시, 점수 증가처리 */
	private void plusScore() {
		int score = 10 + combo * 7; // 콤보 달성 시, 추가점수
		screen.updateScore(score);
		if (combo < 10) { // 콤보는 10콤보까지
			combo++;
		}
	}

	/** 해당 레벨을 클리어 시, 다음 레벨로 진입한다. */
	public void nextLevel() {
		if (level < 10) {
			level++;
			initGame();
		}
		else {
			// 무한모드
		}
	}

	/** 게임을 초기화 */
	public void initGame() {
		wordList.initwordList(); // 패널에서 모든 단어를 떼어내고, 리스트 초기화
		screen.initGame();
		screen.getTextField().removeKeyListener(listener);
		screen.repaint();
	}

	/** 스레드 내에서 pause를 감지한다. */
	public void checkPause() throws InterruptedException {
		synchronized (this) {
			while (pause) {
				wait();
			}
		}
	}

	/** pause에서 resume을 누를시 게임을 재개한다. */
	public void continueGame() {
		synchronized (this) {
			while (pause) {
				pause = false;
				notify();
			}
		}
	}

	/** 라이프 하나 소멸 */
	public void lostLife() {
		screen.lostLife();
	}

	private void checkLife() {
		if (screen.getLife() == 0) {
			// 게임오버
			interrupt(); // 일단 임시로 종료시킴
		}
	}

	public void checkTime() {
		if (!screen.updateTime()) {
			boolean confirm = MsgWinow.confirm("level " + level + " CLEAR!\n다음 레벨을 진행하시겠습니까?");
			if (confirm) {
				nextLevel();
			}
			else {
				panel.setContentPane(PanelManager.MENU);
				interrupt();
			}
		}
	}

	private void useItem(int item) {
		itemTime = (int) System.currentTimeMillis();

		switch (item) {
		case SLOW:
			itemFlag[SLOW] = true;
			break;

		case UNBEATABLE:
			itemFlag[UNBEATABLE] = true;
			break;

		case NET_MODE:
			itemFlag[NET_MODE] = true;
			break;

		case ADD_LIFE:
			break;

		case ALL_SAVE:
			break;
		}
	}

	private void checkItem() {
		int tmpTime = ((int) System.currentTimeMillis() - itemTime) / 1000;

		if (itemFlag[SLOW] == true && tmpTime > 5) {
			itemFlag[SLOW] = false;
		}
		if (itemFlag[UNBEATABLE] = true) {

		}
		if (itemFlag[NET_MODE] = true) {

		}

	}

	private void popPausePanel() {
		pauseMenu.setVisible(true); // 가시화
		screen.repaint();
	}

	public int getLevel() {
		return level;
	}
}