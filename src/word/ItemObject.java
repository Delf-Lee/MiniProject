package word;

import java.awt.Point;

import javax.swing.ImageIcon;

public class ItemObject extends BaseObject {
	public static final int SLOW = 0;
	public static final int UNBEATABLE = 1;
	public static final int NET_MODE = 2;
	public static final int ADD_LIFE = 3;
	public static final int ALL_SAVE = 4;

	private int item;

	public ItemObject(String word, Point start) {
		super(word, start);
		objectImage = new ImageIcon("images/»óÀÚµÎµÕ½Ç.gif");
		item = (int) (Math.random() * 4);
		setImageLabel();
		arrangeLabel();
		setSize(width, height + 10);
	}
}
