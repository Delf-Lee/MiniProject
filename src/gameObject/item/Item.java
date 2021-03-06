package gameObject.item;

import java.awt.Point;

import javax.swing.ImageIcon;

import gameObject.GameObject;

public class Item extends GameObject {

	public static final int ITEMS = 4; // 아이템 개수
	// 아이템 종류
	public static final int SLOW = 0; // 느려짐
	public static final int UNBEATABLE = 1; // 무적
	public static final int ALL_SAVE = 2; // 모두 구조
	public static final int ADD_LIFE = 3; // 라이프 추가
	public static final int NET_MODE = 4; // 그물

	private static ImageIcon SLOW_IMAGE = new ImageIcon("images/슬로우.png");
	private static ImageIcon UNBEATABLE_IMAGE = new ImageIcon("images/무적.png");
	private static ImageIcon NET_MODE_IMAGE = new ImageIcon("images/그물.png");
	private static ImageIcon ALL_SAVE_IMAGE = new ImageIcon("images/구조.png");
	private static ImageIcon ADD_LIFE_IMAGE = new ImageIcon("images/회복.png");

	public Item(String word, int speed, double slope, Point start, int endX) {
		super(word, speed, slope, start, endX);
		objectType = ITEM;
		type = (int) (Math.random() * ITEMS); // 아이템의 종류 설정
		baseObject = new ItemObject(word, start);
	}

	public int getItemType() {
		return type;
	}

	public static ImageIcon getItemImage(int item) {
		switch (item) {
		case SLOW:
			return SLOW_IMAGE;
		case UNBEATABLE:
			return UNBEATABLE_IMAGE;
		case NET_MODE:
			return NET_MODE_IMAGE;
		case ALL_SAVE:
			return ALL_SAVE_IMAGE;
		case ADD_LIFE:
			return ADD_LIFE_IMAGE;
		}
		return null;
	}
}
