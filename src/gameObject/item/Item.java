package gameObject.item;

import java.awt.Point;

import javax.swing.ImageIcon;

import gameObject.GameObject;

public class Item extends GameObject {

	public static final int ITEMS = 5; // ������ ����
	// ������ ����
	public static final int SLOW = 0; // ������
	public static final int UNBEATABLE = 1; // ����
	public static final int NET_MODE = 2; // �׹�
	public static final int ALL_SAVE = 3; // ��� ����
	public static final int ADD_LIFE = 4; // ������ �߰�

	private static ImageIcon SLOW_IMAGE = new ImageIcon("images/���ο�.png");
	private static ImageIcon UNBEATABLE_IMAGE = new ImageIcon("images/����.png");
	private static ImageIcon NET_MODE_IMAGE = new ImageIcon("images/�׹�.png");
	private static ImageIcon ALL_SAVE_IMAGE = new ImageIcon("images/����.png");

	public Item(String word, int speed, double slope, Point start, int endX) {
		super(word, speed, slope, start, endX);
		objectType = ITEM;
		type = (int) (Math.random() * ITEMS); // �������� ���� ����
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
		}
		return null;
	}
}
