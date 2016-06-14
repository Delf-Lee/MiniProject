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
	private Vector<Word> list = new Vector<Word>(); // 출현된 단어들을 관리하는 배열
	private Vector<String> entry = new Vector<String>(); // 텍스트 파일에 있는 단어 복사
	private GameThread thr;

	public boolean isCreate = true; // 단어를 생성할 수 있는지 확인하는 변수

	// 단어들 하강에 필요한 좌표와 기울기 정보
	private static final int BOUND_X = 1017;
	//private static final int BOUND_Y = 740;
	private static final int APPEARANCE_X1 = 737;
	private static final int APPEARANCE_X2 = 1117;
	private static final int LIMIT_X1 = 110;
	private static final int LIMIT_X2 = 515;
	private static final int START_Y = 224;
	private static final int END_Y = 721;
	private static final double BOUND_SLOPE = 182.0 / 418.0;
	// 파일 읽기
	private FileReader fin = null;

	public ObjectManager(GameThread thr) {
		this.thr = thr;
		try {
			fin = new FileReader(MainFrame.FILEROOT + "\\word.txt"); // 경로 수정!
			BufferedReader reader = new BufferedReader(fin);
			String word = null;
			while ((word = reader.readLine()) != null) {
				entry.add(word);
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("입출력 오류 WordManager()");
			System.exit(1);
		}
	}

	/** @retrun entry 내에 단어 중 하나를 무작위로 반환 */
	public String getWordInEntry() {
		int next = (int) (Math.random() * entry.size()); // entry 내 랜덤하게 단어 선출
		return entry.get(next);
	}

	/** 후보에 있는 단어가 모두 화면에 있으면 더 이상 단어객체를 만들지 못함 */
	public void isCreate() {
		if (list.size() == entry.size()) {
			isCreate = false;
		}
		else {
			isCreate = true;
		}
	}

	/**
	 * 단어 객체를 생성하여 list에 추가한다. list에서 관리되는 단어객체는 모두 화면에 나타나는 객체이다.
	 */
	public Word createWord(int stage) {
		String nextWord;
		while (true) {
			nextWord = getWordInEntry();
			if (!isContain(nextWord)) { // list내에 랜덤선택된 단어가 없으면
				break;
			}
		}
		Point appearanceCoordinate = createRandomCoordinate(); // 랜덤 좌표 생성
		int speed = setSpeed(stage);
		int endX = getEndX(appearanceCoordinate.x);
		double slope = getSlope(appearanceCoordinate.x, endX); // 출현 위치에 따른 하강 기울기 결정
		System.out.println(slope);

		Word newWord = new Word(nextWord, speed, slope, appearanceCoordinate, endX - 100);
		list.add(newWord); // list에 단어 추가
		return newWord;
	}
	
	/** 단어 객체를 리스트 내에서 삭제 */
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

	/** list를 순환하며 그 안의 모든 단어객체의 좌표를 변경한다. */
	public void flowWord() {
		Iterator<Word> it = list.iterator();
		while (it.hasNext()) { // 리스트 내 단어 전체 순환
			if (list.size() == 0) {
				break;
			}
			Word cursor = it.next();
			cursor.setLocation(); // 단어 위치 변경
			if (cursor.isEnd()) { // 끝에 도달하면 리스트에서 제거
				//list.remove(cursor); // ConcurrentModificationException 발생...ㅠㅠ
				it.remove();
				thr.removeWord(cursor); // 패널에서 객체 삭제
				thr.lostLife();
				it = list.iterator(); // Vector에서도 fail-fast 발생? 일단 한번 더 초기화... 
			}
		}
	}

	/** @return entry에서 선별한 단어가 list에 존재 시 true, 그렇지 않으면 false */
	public boolean isContain(String word) {
		Iterator<Word> it = list.iterator();
		while (it.hasNext()) {
			if (it.next().equals(word)) {
				return true; // 단어 들어있음
			}
		}
		return false; // 단어 없음
	}

	/**
	 * 화면 상에 단어객체가 생성될 좌표를 무작위로 생성한다.
	 * 
	 * @return 단어 객체가 생성될 좌표
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
			thr.removeWord(it.next()); // 모든 단어레이블을 패널로 부터 떼어냄 
			System.out.println("땜");
		}
		list.removeAll(list); // 리스트에서 모든 단어들을 삭제
	}

	/**
	 * 각 단어 객체는 생성 좌표에 따라 기울기가 다르다.
	 * 생성된 좌표에 따라서 해당 단어의 기울기를 결정한다.
	 * 
	 * @return 출현 위치에 따른 기울기 값
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

	/** @return 단어 객체 이동 경로의 x좌표에 따른 y좌표 반환 */
	private int getAppearanceY(int x) {
		int appearanceY = (int) (BOUND_SLOPE * (x - BOUND_X));
		return appearanceY;
	}

	public boolean isListEmpty() {
		return list.isEmpty();
	}
}
