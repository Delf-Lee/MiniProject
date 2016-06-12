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

	/** ������ */
	public GameThread(PanelGame screen) {
		wordList = new WordManager(this);
		this.screen = screen;

	}

	/** �÷��̾�� ���ӷ����� ���� */
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
				createWord(); // �ܾ� ��ü ����
				wordList.flowWord(); // �ܾ� �̵�

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

	/** Ű ������ */
	class MyKeyListener extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER: // ����
				Word findWord = wordList.removeWord(screen.getWordString());
				if (findWord != null) {
					removeWord(findWord); // �гο��� �ܾü ����
					plusScore(); // ���� ����
				}
				else {
					lostLife(); // for test
					combo = 0; // �ܾ �Է��� Ʋ���� �޺� �ʱ�ȭ
				}
				screen.initTextField();
				break;
			case KeyEvent.VK_ESCAPE: // ECS
				pause();
				break;
			case KeyEvent.VK_BACK_SPACE: // �齺���̽�
				combo = 0; // �齺���̽� �Է� �� �޺� �ʱ�ȭ
				break;
			default:
				break;
			}
		}
	}

	/** ������ Ȯ���� �ܾ� ���� */
	private void createWord() {
		if (wordList.isCreate) {
			int appearRatio = (int) (Math.random() * 200 - (level * 1)); // ���� Ȯ��

			if (appearRatio < 10) {
				Word newWord = wordList.createWord(level); // ����
				addWordToPanel(newWord); // �гο� ����
			}
		}
		wordList.isCreate();
	}

	/** �ܾ� ��ü�� �гο� ���� */
	private void addWordToPanel(Word word) {
		screen.add(word.getWordObject());
	}

	/** �ؽ�Ʈ�ʵ忡 ��Ŀ�� ����, ������ ���� */
	public void setFocus() {
		screen.getTextField().requestFocus(true);
		screen.getTextField().addKeyListener(new MyKeyListener());
	}

	private void gameOver() {
		/*���� ������ ����*/
	}

	/** �ܾ� ��ü�� �гηκ��� ���� */
	public void removeWord(Word word) {
		screen.remove(word.getWordObject());
	}

	/** �ܾ� ���� ��, ���� ����ó�� */
	private void plusScore() {
		int score = 10 + combo * 7; // �޺� �޼� ��, �߰�����
		screen.updateScore(score);
		if (combo < 10) { // �޺��� 10�޺�����
			combo++;
		}
	}

	public void nextLevel() {
		if (level < 10) {
			level++;
			initGame();
		}
		else {
			// ���Ѹ��
		}
	}

	/** ������ �ʱ�ȭ */
	public void initGame() {
		wordList.initwordList(); // �гο��� ��� �ܾ �����, ����Ʈ �ʱ�ȭ
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
			System.out.println("�׳� �㷹��");
			/*���� ���� �˸� */
			nextLevel();
		}

	}
}