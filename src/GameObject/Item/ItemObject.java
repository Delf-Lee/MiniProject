package GameObject.Item;

import java.awt.Point;

import javax.swing.ImageIcon;

import GameObject.BaseObject;

public class ItemObject extends BaseObject {
	

	public ItemObject(String word, Point start) {
		super(word, start);
		objectImage = new ImageIcon("images/���ڵεս�.gif");
		
		setImageLabel();
		arrangeLabel();
		setSize(width, height + 10);
	}
}
