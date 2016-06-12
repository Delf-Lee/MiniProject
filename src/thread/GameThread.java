package thread;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;

import Animation.ReadyAnimation;
import panel.game.PanelGame;
import user.Player;
import user.User;
import word.Word;
import word.WordManager;

public class GameThread extends Thread {
	WordManager wordList;
	PanelGame screen;
	Player player;
	Calendar cal = Calendar.getInstance();

	int score;
	boolean pause = false;
	int level;
	int preTime;
	int combo;

	/** 생성자 */
	public GameThread(PanelGame screen) {
		wordList = new WordManager(this);
		this.screen = screen;

	}

	/** 플레이어와 게임레벨을 설정 */
	public void setGame(User user, int level) {
		player = new Player(user);
		this.level = level;
		preTime = (int) System.currentTimeMillis();
		screen.setInfo(level, preTime);
		new ReadyAnimation(screen, level, this);
	}

	public void run() {
		try {
			while (true) {
				createWord(); // 단어 객체 생성
				wordList.flowWord(); // 단어 이동

				checkTime();
				checkPause();

				screen.repaint();
				sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return;
	}

	/** 키 리스너 */
	class MyKeyListener extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER: // 엔터
				Word findWord = wordList.removeWord(screen.getWordString());
				if (findWord != null) {
					removeWord(findWord); // 패널에서 단어객체 제거
					plusScore(); // 점수 증가
				}
				else {
					lostLife(); // for test
					combo = 0; // 단어가 입력이 틀리면 콤보 초기화
				}
				screen.initTextField();
				break;
			case KeyEvent.VK_ESCAPE: // ECS
				pause();
				break;
			case KeyEvent.VK_BACK_SPACE: // 백스페이스
				combo = 0; // 백스페이스 입력 시 콤보 초기화
				break;
			default:
				break;
			}
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
		screen.getTextField().addKeyListener(new MyKeyListener());
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
		screen.repaint();
	}

	public void checkPause() throws InterruptedException {
		synchronized (this) {
			while (pause) {
				wait();
			}
		}
	}
	
	public void pause() {
		if (pause) {
			continueGame();
		}
		else {
			pause = true;
		}
	}

	public void continueGame() {
		synchronized (this) {
			while (pause) {
				pause = false;
				notify();
			}
		}
	}

	public void lostLife() {
		screen.lostLife();
	}

	public void checkTime() {
		if (!screen.updateTime()) {
			System.out.println("겜끝 담레벨");
			/*게임 끝을 알림 */
			nextLevel();
		}

	}
}