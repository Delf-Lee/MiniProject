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
	// ������ ���� ��� �� ����

	private static boolean itemFlag[] = { false, false, false };

	private ObjectManager wordList;
	private Player player; // �ʿ���ڳ�

	private PanelManager panel;
	private GamePanel screen;
	private PausePanel pauseMenu;

	private MyKeyListener listener = new MyKeyListener();

	// �ΰ��� ����
	private int preTime; // ���� �ð�
	private int limitTime; // ���� ���� �ð�
	private int level; // ���� �÷��� �ϰ��ִ� ����
	private int score = 0; // ����
	private int combo; // �޺� ��

	private boolean pause = false; // puase ����
	private boolean keyAccept = true;

	// ������
	private int itemTime; // ������ ���� �ð�
	private int item[] = { 0, 0, 0, 0, 0 }; // ���� ������ ����

	/** ������ */
	public GameThread(PanelManager panel) {
		this.panel = panel; // �гο� ���� ���۷��� ����
		screen = panel.getGamePanel();
		pauseMenu = panel.getPausePanel();

		wordList = new ObjectManager(this);
		panel.getPausePanel().setThread(this); // ���� ���� ������ ���⼭ ������ ���۷��� ����

		screen.add(pauseMenu); // puase �г��� �̸� �ٿ����� (��ġ ����������)
		pauseMenu.setVisible(false);
	}

	/** �÷��̾�� ���ӷ����� ���� */
	public void setGame(User user, int level) {
		player = new Player(user); // ������ �÷��̾� ����
		this.level = level; // �÷��̾ �÷��� �� ����

		//new ReadyAnimation(screen, level, this); // �غ� �ִϸ��̼�
		preTime = (int) System.currentTimeMillis(); // ���� �ð� ���� (���ѽð��� ����)
		screen.setInfo(level, preTime); // ����ȭ�鿡�� ����â ����
	}

	public void run() {
		initGame();
		try {
			while (true) {
				createWord(); // �ܾ� ��ü ����
				wordList.flowWord(); // �ܾ� �̵�

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

	/** Ű ������ */
	class MyKeyListener extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			if (!keyAccept) {
				return;
			}
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER: // ����
				Word findWord = wordList.removeWord(screen.getWordString());
				if (findWord != null) {
					removeWord(findWord); // �гο��� �ܾü ����
					plusScore(); // ���� ����
				}
				else {
					combo = 0; // �ܾ �Է��� Ʋ���� �޺� �ʱ�ȭ
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
			case KeyEvent.VK_BACK_SPACE: // �齺���̽�
				combo = 0; // �齺���̽� �Է� �� �޺� �ʱ�ȭ
				break;
			default: // 
				if (e.getKeyChar() >= 0 && e.getKeyChar() >= 4) {
					useItem(e.getKeyCode()); // ������ ���
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

	/** ������ escŰ�� ���� ��, �Ͻ� �����Ѵ�. */
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
		screen.getTextField().addKeyListener(listener);
	}

	/** ������ ������ ���� �����Ѵ�. */
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

	/** �ܾ� ��ü�� �гηκ��� ���� */
	public void removeWord(Word word) {
		screen.remove(word.getWordObject());
	}

	/** �ܾ� ���� ��, ���� ����ó�� */
	private void plusScore() {
		int getScore = 10 + combo * 7;// �޺� �޼� ��, �߰�����
		score += getScore;
		screen.updateScore(score);
		if (combo < 10) { // �޺��� 10�޺�����
			combo++;
		}
	}

	/** �ش� ������ Ŭ���� ��, ���� ������ �����Ѵ�. */
	public void nextLevel() {
		if (level < 10) {
			level++;
			//initGame();
			wordList.initwordList();
			screen.initTextField();
			screen.repaint();
			preTime = (int) System.currentTimeMillis();
			screen.setInfo(level, preTime); // ����ȭ�鿡�� ����â ����
		}
		else {
			// ���Ѹ��
		}
	}

	/** ������ �ʱ�ȭ */
	public void initGame() {
		wordList.initwordList(); // �гο��� ��� �ܾ �����, ����Ʈ �ʱ�ȭ
		screen.initGame();
		//screen.getTextField().removeKeyListener(listener);
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
		//screen.lostLife();
	}

	private void checkLife() {
		if (screen.getLife() == 0) {
			// ���ӿ���
			gameOver();
			//�ܾ� ����
			wordList.initwordList();
			panel.setContentPane(PanelManager.MENU);
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
		pauseMenu.setVisible(true); // ����ȭ
	}

	public void setKeyAccpet(boolean i) {
		keyAccept = i;
	}

	public int getLevel() {
		return level;
	}
}