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
	// ������ ���� ��� �� ����

	private static boolean itemFlag[] = { false, false, false };

	private ObjectManager objectList;
	private Player player; // �ʿ���ڳ�
	private Timer gameTimer = new Timer();
	private Timer slowItem;
	private Timer unbeatableItem;
	private Timer netModeItem;

	private PanelManager panel;
	private GamePanel screen;
	private PausePanel pauseMenu;

	private MyKeyListener listener = new MyKeyListener();

	// �ΰ��� ����
	private int preTime; // ���� �ð�
	private int level; // ���� �÷��� �ϰ��ִ� ����
	private int life = 5;
	private int score = 0; // ����
	private int combo; // �޺� ��

	private boolean pause = false; // puase ����
	private boolean keyAccept = true;

	// ������
	private int totalItem = 0;

	public static final int ITEM_MAX = 5;
	private Vector<Integer> ownedItem = new Vector<Integer>(ITEM_MAX); // �ִ� ������ ������ŭ

	/** ������ */
	public GameThread(PanelManager panel) {
		this.panel = panel; // �гο� ���� ���۷��� ����
		screen = panel.getGamePanel();
		pauseMenu = panel.getPausePanel();

		objectList = new ObjectManager(this);
		panel.getPausePanel().setThread(this); // ���� ���� ������ ���⼭ ������ ���۷��� ����

		screen.add(pauseMenu); // puase �г��� �̸� �ٿ����� (��ġ ����������)
		pauseMenu.setVisible(false);
	}

	/** �÷��̾�� ���ӷ����� ���� */
	public void setGame(User user, int level) {
		player = new Player(user); // ������ �÷��̾� ����
		this.level = level; // �÷��̾ �÷��� �� ����
		gameTimer.setLimitTime(30);

		//new ReadyAnimation(screen, level, this); // �غ� �ִϸ��̼�
		preTime = (int) System.currentTimeMillis(); // ���� �ð� ���� (���ѽð��� ����)
		screen.setInfo(level, preTime); // ����ȭ�鿡�� ����â ����
	}

	public void run() {
		initGame();
		try {
			gameTimer.start();
			while (true) {
				createWord(); // �ܾ� ��ü ����
				createItem();
				objectList.flowWord(); // �ܾ� �̵�

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
				GameObject findWord = objectList.removeWord(screen.getWordString());
				if (findWord != null) { // �г� ���� �Է��� �ܾ ������
					int objectType = findWord.getObjecType();
					if (objectType == GameObject.ITEM) {
						storeItem((Item) findWord);
					}
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
					gameTimer.stopTimer();
				}
				else {
					panel.getPausePanel().setVisible(false);
					CountDown th = new CountDown();
					th.start();
				}
				break;
			case KeyEvent.VK_BACK_SPACE: // �齺���̽�
				combo = 0; // �齺���̽� �Է� �� �޺� �ʱ�ȭ
				break;
			case KeyEvent.VK_1:
			case KeyEvent.VK_2:
			case KeyEvent.VK_3:
			case KeyEvent.VK_4:
			case KeyEvent.VK_5:
				int selectedItem = e.getKeyChar() - KeyEvent.VK_0;
				System.out.println(selectedItem);
				useItem(selectedItem - 1); // ������ ���
				screen.initTextField();
				break;
			}
		}

		// ����Ű �Է� ��, ����
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

	/** puase ���� �簳 ��, 3�� ī��Ʈ �ٿ� �� ���� �簳 */
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
		if (objectList.isCreate) {
			int appearRatio = (int) (Math.random() * 200 - (level * 1)); // ���� Ȯ��

			if (appearRatio < 10) {
				Word newWord = objectList.createWord(level); // ����
				addWordToPanel(newWord); // �гο� ����
			}
		}
		objectList.isCreate();
	}

	/** ������ Ȯ���� ������ ���� */
	private void createItem() {
		if (objectList.isCreate) {
			int appearRatio = (int) (Math.random() * 200 - (level * 1)); // ���� Ȯ��

			if (appearRatio < 10) {
				Item newItem = objectList.createItem(); // ����
				addWordToPanel(newItem); // �гο� ����
			}
		}
		objectList.isCreate();
	}

	/** ���ӿ�����Ʈ ��ü�� �гο� ���� */
	private void addWordToPanel(GameObject word) {
		screen.add(word.getWordObject());
	}

	/** �ؽ�Ʈ�ʵ忡 ��Ŀ�� ����, ������ ���� */
	public void setFocus() {
		screen.getTextField().requestFocus(true);
		screen.getTextField().addKeyListener(listener);
	}

	/** ������ ������ ������ �����Ѵ�. */
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

	/** ���ӿ�����Ʈ ��ü�� �гηκ��� ���� */
	public void removeWord(GameObject word) {
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
			objectList.initwordList();
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
		objectList.initwordList(); // �гο��� ��� �ܾ �����, ����Ʈ �ʱ�ȭ
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
		if (life > 0) {
			screen.updateLife(life--);
		}
	}

	private void checkLife() {
		if (screen.getLife() == 0) {
			// ���ӿ���
			gameOver();
			//�ܾ� ����
			objectList.initwordList();
			panel.setContentPane(PanelManager.MENU);
			interrupt(); // �ϴ� �ӽ÷� �����Ŵ
		}
	}

	/** ���ѽð��� �����ߴ��� Ȯ���ϰ�, �����гο� �ð��� ǥ���Ѵ�. */
	public void checkTime() {

		// �����ð� ǥ��
		screen.printTime(gameTimer.getRemainTime());

		// ���ѽð� Ȯ��
		if (gameTimer.isTerminate()) { // Ŭ����
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
		case Item.SLOW: // ���� ������ ���
			if (itemFlag[Item.SLOW] == false) {
				itemFlag[Item.SLOW] = true;
				objectList.slowDownObject();
				slowItem = new Timer(5);
			}
			break;

		case Item.UNBEATABLE: // ���� ������ ���
			if (itemFlag[Item.UNBEATABLE] == false) {
				itemFlag[Item.UNBEATABLE] = true;
				unbeatableItem = new Timer(5);
			}
			break;

		case Item.NET_MODE: // �׹���� ������ ���
			if (itemFlag[Item.NET_MODE] == false) {
				itemFlag[Item.NET_MODE] = true;
				netModeItem = new Timer(5);
			}
			break;

		case Item.ADD_LIFE: // ȸ�� ������ ���
			if (life < 5) {
				screen.updateLife(life++);
			}
			break;

		case Item.ALL_SAVE: // ��� ���� ������ ���
			objectList.initwordList();
			break;
		}
	}

	private void checkItem() {

		if (itemFlag[Item.SLOW] == true) { // ������ ������̰�
			System.out.println(slowItem.getRemainTime());
			if (slowItem.isTerminate()) { // �ð��� �ٵǾ�����,
				objectList.setOriginalSpeed();
				itemFlag[Item.SLOW] = false; // ����
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

	/** ���� �гο� �Ͻ����� �г��� ����. */
	private void popPausePanel() {
		pauseMenu.setVisible(true); // ����ȭ
	}

	/**
	 * �����гο��� Ű�Է��� ���ų� �����Ѵ�.
	 * �����г� ���� Ű�������� ����� ���´�.
	 */
	public void setKeyAccpet(boolean i) {
		keyAccept = i;
	}

	/** @return ���� �÷����ϰ� �ִ� ������ ��ȯ */
	public int getLevel() {
		return level;
	}

	/**
	 * ȹ���� �������� �������� Ȯ�� ��, ȹ���� �������� �����Ѵ�.
	 * ȹ���� �������� �����гο� �׸���.
	 */
	private void storeItem(Item item) {
		int newItem = item.getItemType();
		ownedItem.add(newItem);
		if (this.totalItem++ < 5) { // �������� 5���� �ִ�
			screen.addItem(newItem);
		}
	}
}