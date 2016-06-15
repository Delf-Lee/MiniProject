package gameObject;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.text.Position;

import gameObject.item.Item;
import gameObject.word.Word;
import panel.menu.WordSettingPanel;
import thread.GameThread;

public class ObjectManager {
	private Vector<GameObject> list = new Vector<GameObject>(); // ������ �ܾ���� �����ϴ� �迭
	private Vector<String> entry = new Vector<String>(); // �ؽ�Ʈ ���Ͽ� �ִ� �ܾ� ����
	private GameThread thr;

	public boolean isCreate = true; // �ܾ ������ �� �ִ��� Ȯ���ϴ� ����
	private int adjustSpeed = 1;

	// �ܾ�� �ϰ��� �ʿ��� ��ǥ�� ���� ����
	private static final int BOUND_X = 1017;
	private static final int APPEARANCE_X1 = 737;
	private static final int APPEARANCE_X2 = 1117;
	private static final int LIMIT_X1 = 110;
	private static final int LIMIT_X2 = 515;
	private static final int START_Y = 224;
	private static final int END_Y = 721;
	private static final double BOUND_SLOPE = 182.0 / 418.0;

	private static final int ITEM_SPEED = 10;
	// ���� �б�
	private FileReader fin = null;

	public ObjectManager(GameThread thr) {
		this.thr = thr;
		try {
			fin = new FileReader(WordSettingPanel.openFilePath); // ��� ����!
			BufferedReader reader = new BufferedReader(fin);
			String word = null;
			while ((word = reader.readLine()) != null) {
				entry.add(word);
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("����� ���� WordManager()");
			System.exit(1);
		}
	}

	/** @retrun entry ���� �ܾ� �� �ϳ��� �������� ��ȯ */
	public String getWordInEntry() {
		int next = (int) (Math.random() * entry.size()); // entry �� �����ϰ� �ܾ� ����
		return entry.get(next);
	}

	/** �ĺ��� �ִ� �ܾ ��� ȭ�鿡 ������ �� �̻� �ܾü�� ������ ���� */
	public void isCreate() {
		if (list.size() == entry.size()) {
			isCreate = false;
		}
		else {
			isCreate = true;
		}
	}

	/**
	 * �ܾ� ��ü�� �����Ͽ� list�� �߰��Ѵ�. list���� �����Ǵ� �ܾü�� ��� ȭ�鿡 ��Ÿ���� ��ü�̴�.
	 */
	public Word createWord(int stage) {
		String nextWord;
		while (true) {
			nextWord = getWordInEntry();
			if (!isContain(nextWord)) { // list���� �������õ� �ܾ ������
				break;
			}
		}
		Point appearanceCoordinate = createRandomCoordinate(); // ���� ��ǥ ����
		int speed = setSpeed(stage);
		int endX = getEndX(appearanceCoordinate.x);
		double slope = getSlope(appearanceCoordinate.x, endX); // ���� ��ġ�� ���� �ϰ� ���� ����

		Word newWord = new Word(nextWord, speed, slope, appearanceCoordinate, endX - 100);
		list.add(newWord); // list�� �ܾ� �߰�
		return newWord;
	}

	public Item createItem() {
		String nextWord;
		while (true) {
			nextWord = getWordInEntry();
			if (!isContain(nextWord)) { // list���� �������õ� �ܾ ������
				break;
			}
		}
		Point appearanceCoordinate = createRandomCoordinate(); // ���� ��ǥ ����
		int speed = ITEM_SPEED;
		int endX = getEndX(appearanceCoordinate.x);
		double slope = getSlope(appearanceCoordinate.x, endX); // ���� ��ġ�� ���� �ϰ� ���� ����

		Item newItem = new Item(nextWord, speed, slope, appearanceCoordinate, endX - 100);
		list.add(newItem); // list�� �ܾ� �߰�
		return newItem;
	}

	/** �ܾ� ��ü�� ����Ʈ ������ ���� */
	public GameObject removeWord(String word) {
		Iterator<GameObject> it = list.iterator();
		while (it.hasNext()) {
			GameObject tmpWord = it.next();
			if (tmpWord.equals(word)) {
				list.remove(tmpWord);
				return tmpWord;
			}
		}
		return null;
	}

	public Vector<GameObject> removeManyWord(String word) {
		GameObject targetWord = searchWord(word); // Ÿ���� ã�Ƴ���
		if (targetWord == null) { // ���� ����...
			return null;
		}
		else { // Ÿ���� �����Ѵٸ�,
			Point targetLocation = targetWord.getLocation(); // ��ǥ�� ���� ����
			Vector<GameObject> tmpList = new Vector<GameObject>();
			// (�������� �ƴ�) �ܾ� �̸鼭
			Iterator<GameObject> it = list.iterator();
			while (it.hasNext()) { // ����Ʈ�� ����
				GameObject tmpWord = it.next();
				if (tmpWord.getObjecType() == GameObject.WORD) { // �ܾ�� �߿�

					if (wordInArea(targetLocation, tmpWord)) { // ��ǥ �������� ���
						System.out.println("�ɷȴ�");
						tmpList.add(tmpWord); // �гο��� ��� ��ü�� �ӽ������ϰ�
						list.remove(tmpWord); // ����Ʈ���� �ܾ� ��ü�� ����
					}
				}
				list.remove(targetWord);
				tmpList.add(targetWord);

				return tmpList; // �ӽ������� ��ü���͸� ��ȯ 
			}
		}
		return null;
	}

	private boolean wordInArea(Point target, GameObject word) {
		int range = 500;
		Point p = word.getLocation();
		System.out.println("�˻���");
		if (p.x > target.x - range && p.x < target.x + range) {
			if (p.y > target.y - range && p.y < target.y + range) {
				return true;
			}
		}
		return false;
	}

	public GameObject searchWord(String word) {
		Iterator<GameObject> it = list.iterator();
		while (it.hasNext()) {
			GameObject tmpWord = it.next();
			if (tmpWord.equals(word)) {
				list.remove(tmpWord);
				return tmpWord;
			}
		}
		return null;
	}

	public int setSpeed(int stage) {
		int speed = (int) (Math.random() * 2 + stage);
		return speed;
	}

	/** list�� ��ȯ�ϸ� �� ���� ��� �ܾü�� ��ǥ�� �����Ѵ�. */
	public void flowWord() {
		Iterator<GameObject> it = list.iterator();
		while (it.hasNext()) { // ����Ʈ �� �ܾ� ��ü ��ȯ
			if (list.size() == 0) {
				break;
			}
			GameObject cursor = it.next();
			cursor.setLocation(adjustSpeed); // �ܾ� ��ġ ����

			if (cursor.isEnd()) { // ���� �����ϸ� ����Ʈ���� ����
				//list.remove(cursor); // ConcurrentModificationException �߻�...�Ф�
				it.remove();
				thr.removeWord(cursor); // �гο��� ��ü ����
				if (cursor.getObjecType() == GameObject.WORD) {
					thr.lostLife();
				}
				it = list.iterator(); // Vector������ fail-fast �߻�? �ϴ� �ѹ� �� �ʱ�ȭ... 
			}
		}
	}

	/** @return entry���� ������ �ܾ list�� ���� �� true, �׷��� ������ false */
	public boolean isContain(String word) {
		Iterator<GameObject> it = list.iterator();
		while (it.hasNext()) {
			if (it.next().equals(word)) {
				return true; // �ܾ� �������
			}
		}
		return false; // �ܾ� ����
	}

	/**
	 * ȭ�� �� �ܾü�� ������ ��ǥ�� �������� �����Ѵ�.
	 * 
	 * @return �ܾ� ��ü�� ������ ��ǥ
	 */
	public Point createRandomCoordinate() {
		//int x = (int) (Math.random() * (APPEARANCE_X2 - APPEARANCE_X1) + APPEARANCE_X1);
		int x = (int) (Math.random() * (APPEARANCE_X2 - APPEARANCE_X1) + APPEARANCE_X1);
		int y = getAppearanceY(x);
		return new Point(x, y);
	}

	public void initwordList() {
		Iterator<GameObject> it = list.iterator();
		while (it.hasNext()) {
			thr.removeWord(it.next()); // ��� �ܾ�̺��� �гη� ���� ��� 
			System.out.println("��");
		}
		list.removeAll(list); // ����Ʈ���� ��� �ܾ���� ����
	}

	/**
	 * �� �ܾ� ��ü�� ���� ��ǥ�� ���� ���Ⱑ �ٸ���.
	 * ������ ��ǥ�� ���� �ش� �ܾ��� ���⸦ �����Ѵ�.
	 * 
	 * @return ���� ��ġ�� ���� ���� ��
	 */
	public int getEndX(int x) {
		int limitX = (x - APPEARANCE_X1) * (LIMIT_X2 - LIMIT_X1) / (APPEARANCE_X2 - APPEARANCE_X1) + LIMIT_X1;
		//int limitX = 
		return limitX;
	}

	public double getSlope(int x, int limitX) {
		double slope = (double) (START_Y - END_Y) / (x - limitX);
		return slope;
	}

	/** @return �ܾ� ��ü �̵� ����� x��ǥ�� ���� y��ǥ ��ȯ */
	private int getAppearanceY(int x) {
		int appearanceY = (int) (BOUND_SLOPE * (x - BOUND_X));
		return appearanceY;
	}

	public boolean isListEmpty() {
		return list.isEmpty();
	}

	/** ������ ���ȿ���ν�, �ܾ� ��ü�� �̵� �ӵ��� ��������. */
	public void slowDownObject() {
		adjustSpeed = 2;
		//		Iterator<GameObject> it = list.iterator();
		//		while (it.hasNext()) {
		//			GameObject tmpWord = it.next();
		//			if (tmpWord.objectType == GameObject.WORD) {
		//				tmpWord.slowDownSpeed();
		//			}
		//		}
	}

	/** �������� �ܾ� ��ü�� �ӵ��� ������� �ǵ��� ���´�. */
	public void setOriginalSpeed() {
		adjustSpeed = 1;
	}
}
