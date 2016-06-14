package word;

import java.awt.Point;

import javax.annotation.Generated;

public class Word {

	private String word;
	private WordObject wordObject;
	private Point start = new Point(0, 0); // 출발 좌표
	private int type = 0;
	private int endX; // 도착할 때의 x좌표
	private int speed; // 속도
	private double slope = 0.0; // 기울기

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

	/** 단어의 좌표가 다음 정해진 경로의 좌표로 변경 */
	public void setLocation() {
		int x = wordObject.getX() - speed;
		int y = getY(x);
		wordObject.setLocation(x, y);
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
	 * @return this.getX()가 endX에 도달하면 true, 그렇지 않으면 false
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