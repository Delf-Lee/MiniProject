package gameObject;

import java.awt.Point;

public class GameObject {

	public static final int WORD = 0;
	public static final int ITEM = 1;

	protected String word;
	protected BaseObject baseObject;
	protected Point start = new Point(0, 0); // 출발 좌표
	protected int type = 0;
	protected int endX; // 도착할 때의 x좌표
	protected int speed; // 속도
	protected int tmpSpeed;
	protected double slope = 0.0; // 기울기

	protected int objectType;

	public GameObject(String word, int speed, double slope, Point start, int endX) {

		this.word = word;
		this.speed = speed;
		this.slope = slope;
		this.start = start;
		this.endX = endX;

		//baseObject = new WordObject(word, start, type);
	}

	public boolean equals(String word) {
		if (this.word.equals(word)) {
			return true;
		}
		return false;
	}

	/** 단어의 좌표가 다음 정해진 경로의 좌표로 변경 */
	public void setLocation(int n) {
		int x = baseObject.getX() - (speed / n);
		int y = getY(x);
		baseObject.setLocation(x, y);
		//wordObject.repaint();
	}

	/** @return 단어 객체의 x좌표에 따른 y좌표를 반환 */
	public int getY(int x) {
		int y = (int) (slope * (x - start.x) + start.y);
		return y;
	}

	public void setEndX(int x) {
		this.endX = x;
	}

	/**
	 * 단어 객체가 경계선에 도달했는지 확인
	 * 
	 * @return this.getX()가 endX에 도달하면 true, 그렇지 않으면 false
	 */
	public boolean isEnd() {
		if (baseObject.getX() > endX) {
			return false;
		}
		return true;
	}

	public int getSpeed() {
		return speed;
	}

	public String getString() {
		return word;
	}

	public BaseObject getWordObject() {
		return baseObject;
	}

	/**
	 * 객체가 Word객체인지 Item객체인지 알려준다.
	 * 
	 * @return 객체에 해당하는 정수 값
	 */
	public int getObjecType() {
		return objectType;
	}

	/** @return 현재 객체의 speed 값을 반환 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void slowDownSpeed() {
		tmpSpeed = speed;
		if (speed < 1) {
			speed /= 2;
		}
	}

	public void setOriginalSpeed() {
		speed = tmpSpeed;
	}
	
	public Point getLocation() {
		return baseObject.getLocation();
	}
}