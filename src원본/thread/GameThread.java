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

	/** ������ */
	public GameThread(PanelGame screen) {
		wordList = new WordManager(this);
		this.screen = screen;
	}

	/** �÷��̾�� ���ӷ����� ���� */
	public void setGame(User user, int stage) {
		player = new Player(user);
		this.stage = stage;
	}

	public void run() {
		try {
			while (true) {
				createWord(); // �ܾ� ��ü ����
				wordList.flowWord(); // �ܾ� �̵�
				screen.repaint();
				checkPause();
				sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("����");
		return;
	}

	/** Ű ������ */
	class MyKeyListener extends KeyAdapter {
		public int combo = 0; // �ܾ ���߸� �޺� ����, �޺��� �������� �� �������� ȹ�� 

		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER: // ����
				System.out.println("enter");
				Word findWord = wordList.removeWord(screen.getWordString());
				if (findWord != null) {
					removeWord(findWord); // �гο��� �ܾü ����
					plusScore(combo++); // ���� ����
				}
				else {
					combo = 0; // �ܾ �Է��� Ʋ���� �޺� �ʱ�ȭ
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
			int appearRatio = (int) (Math.random() * 100); // ���� Ȯ��

			if (appearRatio < 10) {
				Word test1 = wordList.createWord(stage);
				addWordToPanel(test1);
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
