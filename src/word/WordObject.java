package word;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WordObject extends BaseObject {

	private static final int RABBIT = 0;
	private static final int SQUIRREL = 1;
	private static final int DEER = 2;

	private static final String RABBIT_IMG = "images\\�䳢��Ǫ.gif";
	private static final String SQUIRREL_IMG = "images\\�ٶ����Ǫ.gif";
	private static final String DEER_IMG = "images\\�罿��Ǫ.gif";

	public WordObject(String word, Point start, int type) {
		super(word, start);
		this.setImageLabel(type);
		arrangeLabel();
		setSize(width, height + 10);
	}

	/** �̹��� ���̺� ���� */
	private void setImageLabel(int type) {
		// ������ �̹��� ����
		System.out.println("setimage..");
		switch (type) {
		case RABBIT:
			objectImage = new ImageIcon(RABBIT_IMG);
			break;
		case SQUIRREL:
			objectImage = new ImageIcon(SQUIRREL_IMG);
			break;
		case DEER:
			objectImage = new ImageIcon(DEER_IMG);
			break;
		}
		super.setImageLabel();
	}
}
