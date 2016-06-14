package GameObject.Item;

import java.awt.Point;

import GameObject.GameObject;

public class Item extends GameObject {

	public static final int ITEMS = 5; // 아이템 개수
	// 아이템 종류
	public static final int SLOW = 0; // 느려짐
	public static final int UNBEATABLE = 1; // 무적
	public static final int NET_MODE = 2; // 그물
	public static final int ADD_LIFE = 3; // 라이프 추가
	public static final int ALL_SAVE = 4; // 모두 구조

	public Item(String word, int speed, double slope, Point start, int endX) {
		super(word, speed, slope, start, endX);
		objectType = ITEM;
		type = (int) (Math.random() * ITEMS); // 아이템의 종류 설정
		baseObject = new ItemObject(word, start);
	}
}
