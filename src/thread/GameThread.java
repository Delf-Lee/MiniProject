package thread;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JLabel;

import gameObject.GameObject;
import gameObject.ObjectManager;
import gameObject.item.Item;
import gameObject.word.Word;
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

	private ObjectManager objectList;
	private Player player; // 필요없겠네
	private Timer gameTimer = new Timer();
	private Timer slowItem;
	private Timer unbeatableItem;
	private Timer netModeItem;

	private PanelManager panel;
	private GamePanel screen;
	private PausePanel pauseMenu;

	private MyKeyListener listener = new MyKeyListener();

	// 인게임 정보
	private int preTime; // 현재 시간
	private int level; // 현재 플레이 하고있는 레벨
	private int life = 5;
	private int score = 0; // 점수
	private int combo; // 콤보 수

	private boolean pause = false; // puase 여부
	private boolean keyAccept = true;

	// 아이템
	private int totalItem = 0;

	public static final int ITEM_MAX = 5;
	private Vector<Integer> ownedItem = new Vector<Integer>(ITEM_MAX); // 최대 아이템 개수만큼

	/** 생성자 */
	public GameThread(PanelManager panel) {
		this.panel = panel; // 패널에 대한 레퍼런스 설정
		screen = panel.getGamePanel();
		pauseMenu = panel.getPausePanel();

		objectList = new ObjectManager(this);
		panel.getPausePanel().setThread(this); // 선언 순서 때문에 여기서 스레드 레퍼런스 전달

		screen.add(pauseMenu); // puase 패널을 미리 붙여놓음 (배치 문제때문에)
		pauseMenu.setVisible(false);
	}

	/** 플레이어와 게임레벨을 설정 */
	public void setGame(User user, int level) {
		player = new Player(user); // 게임할 플레이어 설정
		this.level = level; // 플레이어가 플레이 할 레벨
		gameTimer.setLimitTime(30);

		//new ReadyAnimation(screen, level, this); // 준비 애니메이션
		preTime = (int) System.currentTimeMillis(); // 현재 시간 저장 (제한시간을 위함)
		screen.setInfo(level, preTime); // 게임화면에서 정보창 설정
	}

	public void run() {
		initGame();
		try {
			gameTimer.start();
			while (true) {
				createWord(); // 단어 객체 생성
				createItem();
				objectList.flowWord(); // 단어 이동

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
				GameObject findWord = objectList.removeWord(screen.getWordString());
				if (findWord != null) { // 패널 내에 입력한 단어가 존재함
					int objectType = findWord.getObjecType();
					if (objectType == GameObject.ITEM) {
						storeItem((Item) findWord);
					}
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
					gameTimer.stopTimer();
				}
				else {
					panel.getPausePanel().setVisible(false);
					CountDown th = new CountDown();
					th.start();
				}
				break;
			case KeyEvent.VK_BACK_SPACE: // 백스페이스
				combo = 0; // 백스페이스 입력 시 콤보 초기화
				break;
			case KeyEvent.VK_1:
			case KeyEvent.VK_2:
			case KeyEvent.VK_3:
			case KeyEvent.VK_4:
			case KeyEvent.VK_5:
				int selectedItem = e.getKeyChar() - KeyEvent.VK_0;
				System.out.println(selectedItem);
				useItem(selectedItem - 1); // 아이템 사용
				screen.initTextField();
				break;
			}
		}

		// 숫자키 입력 시, 무시
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_1:
			case KeyEvent.VK_2:
			case KeyEvent.VK_3:
			case KeyEvent.VK_4:
			case KeyEvent.VK_5:
			case KeyEvent.VK_6:
			case KeyEvent.VK_7:
			case KeyEvent.VK_8:
			case KeyEvent.VK_9:
			case KeyEvent.VK_0:
				screen.initTextField();
				break;
			}
		}
	}

	/** puase 이후 재개 시, 3초 카운트 다운 후 게임 재개 */
	class CountDown extends Thread {
		JLabel timerLabel = new JLabel("3");

		public CountDown() {
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
					gameTimer.resumeTimer();
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
		if (objectList.isCreate) {
			int appearRatio = (int) (Math.random() * 200 - (level * 1)); // 출현 확률

			if (appearRatio < 10) {
				Word newWord = objectList.createWord(level); // 생성
				addWordToPanel(newWord); // 패널에 부착
			}
		}
		objectList.isCreate();
	}

	/** 랜덤한 확률로 아이템 생성 */
	private void createItem() {
		if (objectList.isCreate) {
			int appearRatio = (int) (Math.random() * 200 - (level * 1)); // 출현 확률

			if (appearRatio < 10) {
				Item newItem = objectList.createItem(); // 생성
				addWordToPanel(newItem); // 패널에 부착
			}
		}
		objectList.isCreate();
	}

	/** 게임오브젝트 객체를 패널에 부착 */
	private void addWordToPanel(GameObject word) {
		screen.add(word.getWordObject());
	}

	/** 텍스트필드에 포커스 설정, 리스너 설정 */
	public void setFocus() {
		screen.getTextField().requestFocus(true);
		screen.getTextField().addKeyListener(listener);
	}

	/** 게임을 끝내고 점수를 갱신한다. */
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

	/** 게임오브젝트 객체를 패널로부터 삭제 */
	public void removeWord(GameObject word) {
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
			objectList.initwordList();
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
		objectList.initwordList(); // 패널에서 모든 단어를 떼어내고, 리스트 초기화
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
		if (life > 0) {
			screen.updateLife(life--);
		}
	}

	private void checkLife() {
		if (screen.getLife() == 0) {
			// 게임오버
			gameOver();
			//단어 떼줘
			objectList.initwordList();
			panel.setContentPane(PanelManager.MENU);
			interrupt(); // 일단 임시로 종료시킴
		}
	}

	/** 제한시간에 도달했는지 확인하고, 게임패널에 시간을 표시한다. */
	public void checkTime() {

		// 남은시간 표시
		screen.printTime(gameTimer.getRemainTime());

		// 제한시간 확인
		if (gameTimer.isTerminate()) { // 클리어
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

	private void useItem(int index) {
		if (index >= ownedItem.size()) {
			return;
		}
		int usedItem = ownedItem.remove(index);
		screen.useItem(index);
		if (totalItem > 0) {
			totalItem--;
		}

		switch (usedItem) {
		case Item.SLOW: // 감속 아이템 사용
			if (itemFlag[Item.SLOW] == false) {
				itemFlag[Item.SLOW] = true;
				objectList.slowDownObject();
				slowItem = new Timer(5);
			}
			break;

		case Item.UNBEATABLE: // 무적 아이템 사용
			if (itemFlag[Item.UNBEATABLE] == false) {
				itemFlag[Item.UNBEATABLE] = true;
				unbeatableItem = new Timer(5);
			}
			break;

		case Item.NET_MODE: // 그물모드 아이템 사용
			if (itemFlag[Item.NET_MODE] == false) {
				itemFlag[Item.NET_MODE] = true;
				netModeItem = new Timer(5);
			}
			break;

		case Item.ADD_LIFE: // 회복 아이템 사용
			if (life < 5) {
				screen.updateLife(life++);
			}
			break;

		case Item.ALL_SAVE: // 모두 구조 아이템 사용
			objectList.initwordList();
			break;
		}
	}

	private void checkItem() {

		if (itemFlag[Item.SLOW] == true) { // 아이템 사용중이고
			System.out.println(slowItem.getRemainTime());
			if (slowItem.isTerminate()) { // 시간이 다되었으면,
				objectList.setOriginalSpeed();
				itemFlag[Item.SLOW] = false; // 해제
			}
		}
		if (itemFlag[Item.UNBEATABLE] == true) {
			if (unbeatableItem.isTerminate()) {
				itemFlag[Item.UNBEATABLE] = false;
			}
		}
		if (itemFlag[Item.NET_MODE] == true) {
			if (netModeItem.isTerminate()) {
				itemFlag[Item.NET_MODE] = false;
			}
		}
	}

	/** 게임 패널에 일시정지 패널을 띄운다. */
	private void popPausePanel() {
		pauseMenu.setVisible(true); // 가시화
	}

	/**
	 * 게임패널에서 키입력을 막거나 해제한다.
	 * 게임패널 내의 키리스너의 사용을 막는다.
	 */
	public void setKeyAccpet(boolean i) {
		keyAccept = i;
	}

	/** @return 현재 플레이하고 있는 레벨을 반환 */
	public int getLevel() {
		return level;
	}

	/**
	 * 획득한 아이템이 무엇인지 확인 후, 획득한 아이템을 저장한다.
	 * 획득한 아이템을 게임패널에 그린다.
	 */
	private void storeItem(Item item) {
		int newItem = item.getItemType();
		ownedItem.add(newItem);
		if (this.totalItem++ < 5) { // 아이템은 5개가 최대
			screen.addItem(newItem);
		}
	}
}