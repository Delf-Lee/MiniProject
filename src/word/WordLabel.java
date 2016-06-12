package word;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class WordLabel extends JLabel {
	static ImageIcon label = new ImageIcon("images//textLabel.png");

	public WordLabel(String word) {
		super(word, CENTER);
		setHorizontalTextPosition(CENTER);
		setVerticalTextPosition(CENTER);
		//setIcon(new ImageIcon("images//textLabel.png"));

	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		//System.out.println(getWidth() + ", " + getHeight());
		for (int i = 0; i < getHeight(); i++) {
			for (int j = i % 2; j < getWidth(); j += 2) {
				g.drawLine(j, i, j, i);
			}
		}
		super.paintComponent(g);
	}
}
