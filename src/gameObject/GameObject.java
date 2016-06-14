package gameObject;

import java.awt.Point;

public class GameObject {

	public static final int WORD = 0;
	public static final int ITEM = 1;

	protected String word;
	protected BaseObject baseObject;
	protected Point start = new Point(0, 0); // ��� ��ǥ
	protected int type = 0;
	protected int endX; // ������ ���� x��ǥ
	protected int speed; // �ӵ�
	protected int tmpSpeed;
	protected double slope = 0.0; // ����

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

	/** �ܾ��� ��ǥ�� ���� ������ ����� ��ǥ�� ���� */
	public void setLocation(int n) {
		int x = baseObject.getX() - (speed / n);
		int y = getY(x);
		baseObject.setLocation(x, y);
		//wordObject.repaint();
	}

	/** @return �ܾ� ��ü�� x��ǥ�� ���� y��ǥ�� ��ȯ */
	public int getY(int x) {
		int y = (int) (slope * (x - start.x) + start.y);
		return y;
	}

	public void setEndX(int x) {
		this.endX = x;
	}

	/**
	 * �ܾ� ��ü�� ��輱�� �����ߴ��� Ȯ��
	 * 
	 * @return this.getX()�� endX�� �����ϸ� true, �׷��� ������ false
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
	 * ��ü�� Word��ü���� Item��ü���� �˷��ش�.
	 * 
	 * @return ��ü�� �ش��ϴ� ���� ��
	 */
	public int getObjecType() {
		return objectType;
	}

	/** @return ���� ��ü�� speed ���� ��ȯ */
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