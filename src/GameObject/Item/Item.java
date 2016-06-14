package GameObject.Item;

import java.awt.Point;

import GameObject.GameObject;

public class Item extends GameObject {

	public static final int ITEMS = 5; // ������ ����
	// ������ ����
	public static final int SLOW = 0; // ������
	public static final int UNBEATABLE = 1; // ����
	public static final int NET_MODE = 2; // �׹�
	public static final int ADD_LIFE = 3; // ������ �߰�
	public static final int ALL_SAVE = 4; // ��� ����

	public Item(String word, int speed, double slope, Point start, int endX) {
		super(word, speed, slope, start, endX);
		objectType = ITEM;
		type = (int) (Math.random() * ITEMS); // �������� ���� ����
		baseObject = new ItemObject(word, start);
	}
}
