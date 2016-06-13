package thread;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;

import Animation.ReadyAnimation;
import panel.MsgWinow;
import panel.PanelManager;
import panel.game.PanelGame;
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
	private PanelGame screen;
	private Player player;
	private PanelManager panel;

	private boolean pause = false;
	private boolean terminate = false;
	private int item[] = { 0, 0, 0, 0, 0 };
	private int level;
	private int preTime;
	private int combo;

	/** ������ */
	public GameThread(PanelManager panel) {
		this.panel = panel;
		this.screen = panel.getGamePanel();
		wordList = new WordManager(this);
	}

	/** �÷��̾�� ���ӷ����� ���� */
	public void setGame(User user, int level) {
		player = new Player(user); // ������ �÷��̾� ����
		this.level = level; // �÷��̾ �÷��� ������ ���� ����
		preTime = (int) System.currentTimeMillis(); // ���� �ð� ���� (���ѽð��� ����)
		screen.setInfo(level, preTime); // ����ȭ�鿡�� ����â ����
		new ReadyAnimation(screen, level, this); // �غ� �ִϸ��̼�
	}

	public void run() {
		try {
			while (true) {
				createWord(); // �ܾ� ��ü ����
				wordList.flowWord(); // �ܾ� �̵�

				checkTime();
				checkLife();
				checkPause();

				screen.repaint();
				sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
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
				if (e.getKeyCode() >= SLOW && e.getKeyCode() >= ALL_SAVE) {
					//useItem();
				}
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

	/** �ش� ������ Ŭ���� ��, ���� ������ �����Ѵ�. */
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

	/** ������ ������ pause�� �����Ѵ�. */
	public void checkPause() throws InterruptedException {
		synchronized (this) {
			while (pause) {
				wait();
			}
		}
	}

	/** ������ escŰ�� ���� ��, �Ͻ� �����Ѵ�. */
	public void pause() {
		if (pause) {
			continueGame();
			panel.getPausePanel().setVisible(false);
		}
		else {
			pause = true;
			screen.add(panel.getPausePanel());
		}
	}

	/** pause���� resume�� ������ ������ �簳�Ѵ�. */
	public void continueGame() {
		synchronized (this) {
			while (pause) {
				pause = false;
				notify();
			}
		}
	}

	/** ������ �ϳ� �Ҹ� */
	public void lostLife() {
		screen.lostLife();
	}

	private void checkLife() {
		if (screen.getLife() == 0) {
			// ���ӿ���
			interrupt(); // �ϴ� �ӽ÷� �����Ŵ
		}
	}

	public void checkTime() {
		if (!screen.updateTime()) {
			boolean confirm = MsgWinow.confirm("level " + level + " CLEAR!\n���� ������ �����Ͻðڽ��ϱ�?");
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
		switch (item) {
		case SLOW:
			itemFlag[SLOW] = true;
			break;
		case UNBEATABLE:
			itemFlag[UNBEATABLE] = true;
			break;
		case NET_MODE:
			break;
		case ADD_LIFE:
			break;
		case ALL_SAVE:
			break;
		}
	}
}