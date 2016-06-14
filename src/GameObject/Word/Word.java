package GameObject.Word;

import java.awt.Point;

import javax.annotation.Generated;

import GameObject.GameObject;

public class Word extends GameObject {

	public static final int WORDD = 3;

	public Word(String word, int speed, double slope, Point start, int endX) {
		super(word, speed, slope, start, endX);
		int type = (int) (Math.random() * WORDD);
		baseObject = new WordObject(word, start, type);
	}
}