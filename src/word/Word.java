package word;

import java.awt.Point;

import javax.annotation.Generated;

public class Word {

	private String word;
	private WordObject wordObject;
	private Point start = new Point(0, 0); // ��� ��ǥ
	private int type = 0;
	private int endX; // ������ ���� x��ǥ
	private int speed; // �ӵ�
	private double slope = 0.0; // ����

	public Word(String word, int speed, double slope, Point start, int endX) {
		
		this.word = word; 
		this.speed = speed;
		this.slope = slope;
		this.start = start;
		this.endX = endX;
		
		int type = (int) (Math.random() * 3);
		
		wordObject = new WordObject(word, start, type);
	}

	public boolean equals(String word) {
		if (this.word.equals(word)) {
			return true;
		}
		return false;
	}

	/** �ܾ��� ��ǥ�� ���� ������ ����� ��ǥ�� ���� */
	public void setLocation() {
		int x = wordObject.getX() - speed;
		int y = getY(x);
		wordObject.setLocation(x, y);
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
	 * @return this.getX()�� endX�� �����ϸ� true, �׷��� ������ false
	 */
	public boolean isEnd() {
		if (wordObject.getX() > endX) {
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

	public WordObject getWordObject() {
		return wordObject;
	}
}