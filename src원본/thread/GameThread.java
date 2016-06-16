package thread;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import panel.game.PanelGame;
import user.Player;
import user.User;
import word.Word;
import word.WordManager;

public class GameThread extends Thread {
	WordManager wordList;
	PanelGame screen;
	Player player;
	boolean pause = false;
	int stage;

	/** 생성자 */
	public GameThread(PanelGame screen) {
		wordList = new WordManager(this);
		this.screen = screen;
	}

	/** 플레이어와 게임레벨을 설정 */
	public void setGame(User user, int stage) {
		player = new Player(user);
		this.stage = stage;
	}

	public void run() {
		try {
			while (true) {
				createWord(); // 단어 객체 생성
				wordList.flowWord(); // 단어 이동
				screen.repaint();
				checkPause();
				sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("끝남");
		return;
	}

	/** 키 리스너 */
	class MyKeyListener extends KeyAdapter {
		public int combo = 0; // 단어를 맞추면 콤보 증가, 콤보가 높을수록 더 높은점수 획득 

		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER: // 엔터
				System.out.println("enter");
				Word findWord = wordList.removeWord(screen.getWordString());
				if (findWord != null) {
					removeWord(findWord); // 패널에서 단어객체 제거
					plusScore(combo++); // 점수 증가
				}
				else {
					combo = 0; // 단어가 입력이 틀리면 콤보 초기화
				}
				screen.initTextField();
				break;
			case KeyEvent.VK_ESCAPE: // ECS
				if (pause) {
					continueGame();
				}
				else {
					pause = true;
				}
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
			int appearRatio = (int) (Math.random() * 100); // 출현 확률

			if (appearRatio < 10) {
				Word test1 = wordList.createWord(stage);
				addWordToPanel(test1);
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

	public void plusScore(int combo) {

	}

	public void checkPause() throws InterruptedException {
		synchronized (this) {
			while (pause) {
				wait();
			}
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

}
