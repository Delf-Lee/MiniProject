package gameObject.item;

import java.awt.Point;

import javax.swing.ImageIcon;

import gameObject.BaseObject;

public class ItemObject extends BaseObject {

	public ItemObject(String word, Point start) {
		super(word, start);
		objectImage = new ImageIcon("images/상자두둥실.gif");
		
		setImageLabel();
		arrangeLabel();
		setSize(width, height + 10);
	}
}
