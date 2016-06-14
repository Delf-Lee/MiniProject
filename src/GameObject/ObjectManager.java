package GameObject;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import GameObject.Word.Word;
import main.MainFrame;
import thread.GameThread;

public class ObjectManager {
	private Vector<Word> list = new Vector<Word>(); // ������ �ܾ���� �����ϴ� �迭
	private Vector<String> entry = new Vector<String>(); // �ؽ�Ʈ ���Ͽ� �ִ� �ܾ� ����
	private GameThread thr;

	public boolean isCreate = true; // �ܾ ������ �� �ִ��� Ȯ���ϴ� ����

	// �ܾ�� �ϰ��� �ʿ��� ��ǥ�� ���� ����
	private static final int BOUND_X = 1017;
	//private static final int BOUND_Y = 740;
	private static final int APPEARANCE_X1 = 737;
	private static final int APPEARANCE_X2 = 1117;
	private static final int LIMIT_X1 = 110;
	private static final int LIMIT_X2 = 515;
	private static final int START_Y = 224;
	private static final int END_Y = 721;
	private static final double BOUND_SLOPE = 182.0 / 418.0;
	// ���� �б�
	private FileReader fin = null;

	public ObjectManager(GameThread thr) {
		this.thr = thr;
		try {
			fin = new FileReader(MainFrame.FILEROOT + "\\word.txt"); // ��� ����!
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
		System.out.println(slope);

		Word newWord = new Word(nextWord, speed, slope, appearanceCoordinate, endX - 100);
		list.add(newWord); // list�� �ܾ� �߰�
		return newWord;
	}
	
	/** �ܾ� ��ü�� ����Ʈ ������ ���� */
	public Word removeWord(String word) {
		Iterator<Word> it = list.iterator();
		while (it.hasNext()) {
			Word tmpWord = it.next();
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
		Iterator<Word> it = list.iterator();
		while (it.hasNext()) { // ����Ʈ �� �ܾ� ��ü ��ȯ
			if (list.size() == 0) {
				break;
			}
			Word cursor = it.next();
			cursor.setLocation(); // �ܾ� ��ġ ����
			if (cursor.isEnd()) { // ���� �����ϸ� ����Ʈ���� ����
				//list.remove(cursor); // ConcurrentModificationException �߻�...�Ф�
				it.remove();
				thr.removeWord(cursor); // �гο��� ��ü ����
				thr.lostLife();
				it = list.iterator(); // Vector������ fail-fast �߻�? �ϴ� �ѹ� �� �ʱ�ȭ... 
			}
		}
	}

	/** @return entry���� ������ �ܾ list�� ���� �� true, �׷��� ������ false */
	public boolean isContain(String word) {
		Iterator<Word> it = list.iterator();
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
		Iterator<Word> it = list.iterator();
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
}
