package gameObject.item;

import java.awt.Point;

import javax.swing.ImageIcon;

import gameObject.BaseObject;

public class ItemObject extends BaseObject {

	public ItemObject(String word, Point start) {
		super(word, start);
		objectImage = new ImageIcon("images/���ڵεս�.gif");
		
		setImageLabel();
		arrangeLabel();
		setSize(width, height + 10);
	}
}
