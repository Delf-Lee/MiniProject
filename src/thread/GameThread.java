package thread;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;

import GameObject.ObjectManager;
import GameObject.Item.Item;
import GameObject.Word.Word;
import panel.MsgWinow;
import panel.PanelManager;
import panel.game.GamePanel;
import panel.game.PausePanel;
import user.Player;
import user.User;
import user.UserManager;

public class GameThread extends Thread {
	// 아이템 관련 상수 및 변수

	private static boolean itemFlag[] = { false, false, false };

	private ObjectManager wordList;
	private Player player; // 필요없겠네

	private PanelManager panel;
	private GamePanel screen;
	private PausePanel pauseMenu;

	private MyKeyListener listener = new MyKeyListener();

	// 인게임 정보
	private int preTime; // 현재 시간
	private int limitTime; // 게임 제한 시간
	private int level; // 현재 플레이 하고있는 레벨
	private int score = 0; // 점수
	private int combo; // 콤보 수

	private boolean pause = false; // puase 여부
	private boolean keyAccept = true;

	// 아이템
	private int itemTime; // 아이템 제한 시간
	private int item[] = { 0, 0, 0, 0, 0 }; // 소유 아이템 개수

	/** 생성자 */
	public GameThread(PanelManager panel) {
		this.panel = panel; // 패널에 대한 레퍼런스 설정
		screen = panel.getGamePanel();
		pauseMenu = panel.getPausePanel();

		wordList = new ObjectManager(this);
		panel.getPausePanel().setThread(this); // 선언 순서 때문에 여기서 스레드 레퍼런스 전달

		screen.add(pauseMenu); // puase 패널을 미리 붙여놓음 (배치 문제때문에)
		pauseMenu.setVisible(false);
	}

	/** 플레이어와 게임레벨을 설정 */
	public void setGame(User user, int level) {
		player = new Player(user); // 게임할 플레이어 설정
		this.level = level; // 플레이어가 플레이 할 레벨

		//new ReadyAnimation(screen, level, this); // 준비 애니메이션
		preTime = (int) System.currentTimeMillis(); // 현재 시간 저장 (제한시간을 위함)
		screen.setInfo(level, preTime); // 게임화면에서 정보창 설정
	}

	public void run() {
		initGame();
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
			if (!keyAccept) {
				return;
			}
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER: // 엔터
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
					TimerThread th = new TimerThread();
					th.start();
				}
				break;
			case KeyEvent.VK_BACK_SPACE: // 백스페이스
				combo = 0; // 백스페이스 입력 시 콤보 초기화
				break;
			default: // 
				if (e.getKeyChar() >= 0 && e.getKeyChar() >= 4) {
					useItem(e.getKeyCode()); // 아이템 사용
				}
				break;
			}
		}
	}

	class TimerThread extends Thread {
		JLabel timerLabel = new JLabel("3");

		public TimerThread() {
			timerLabel.setFont(new Font("Silkscreen", Font.BOLD, 150));
			timerLabel.setBounds(490, 300, 150, 150);
			screen.add(timerLabel);
			screen.setComponentZOrder(timerLabel, 0);

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
					continueGame();
					return;
				}
				timerLabel.setText(Integer.toString(n));
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

	/** 게임을 끝내고 점술 갱신한다. */
	private void gameOver() {
		UserManager.user.setBestScore(score);
		for (int i = 0; i < UserManager.userList.size(); i++) {
			if (UserManager.user.getUserName().equals(UserManager.userList.get(i).getUserName())) {
				UserManager.userList.get(i).setBestScore(score);
				break;
			}
		}
		UserManager.sortUserList();
		UserManager.saveUserData();
	}

	/** 단어 객체를 패널로부터 삭제 */
	public void removeWord(Word word) {
		screen.remove(word.getWordObject());
	}

	/** 단어 맞출 시, 점수 증가처리 */
	private void plusScore() {
		int getScore = 10 + combo * 7;// 콤보 달성 시, 추가점수
		score += getScore;
		screen.updateScore(score);
		if (combo < 10) { // 콤보는 10콤보까지
			combo++;
		}
	}

	/** 해당 레벨을 클리어 시, 다음 레벨로 진입한다. */
	public void nextLevel() {
		if (level < 10) {
			level++;
			//initGame();
			wordList.initwordList();
			screen.initTextField();
			screen.repaint();
			preTime = (int) System.currentTimeMillis();
			screen.setInfo(level, preTime); // 게임화면에서 정보창 설정
		}
		else {
			// 무한모드
		}
	}

	/** 게임을 초기화 */
	public void initGame() {
		wordList.initwordList(); // 패널에서 모든 단어를 떼어내고, 리스트 초기화
		screen.initGame();
		//screen.getTextField().removeKeyListener(listener);
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
		//screen.lostLife();
	}

	private void checkLife() {
		if (screen.getLife() == 0) {
			// 게임오버
			gameOver();
			//단어 떼줘
			wordList.initwordList();
			panel.setContentPane(PanelManager.MENU);
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
		case Item.SLOW:
			itemFlag[Item.SLOW] = true;
			break;

		case Item.UNBEATABLE:
			itemFlag[Item.UNBEATABLE] = true;
			break;

		case Item.NET_MODE:
			itemFlag[Item.NET_MODE] = true;
			break;

		case Item.ADD_LIFE:
			break;

		case Item.ALL_SAVE:
			break;
		}
	}

	private void checkItem() {
		int tmpTime = ((int) System.currentTimeMillis() - itemTime) / 1000;

		if (itemFlag[Item.SLOW] == true && tmpTime > 5) {
			itemFlag[Item.SLOW] = false;
		}
		if (itemFlag[Item.UNBEATABLE] = true) {

		}
		if (itemFlag[Item.NET_MODE] = true) {

		}

	}

	private void popPausePanel() {
		pauseMenu.setVisible(true); // 가시화
	}

	public void setKeyAccpet(boolean i) {
		keyAccept = i;
	}

	public int getLevel() {
		return level;
	}
}