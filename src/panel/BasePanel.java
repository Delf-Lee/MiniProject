package panel;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

abstract public class BasePanel extends JPanel {
	ImageIcon background;
	Image img;

	public BasePanel() {
		setLayout(null);

		//setLayout(new BorderLayout());
	}

	// ��� �̹��� ����
	public BasePanel(String file) {
		setLayout(null);
		background = new ImageIcon(file);
		setOpaque(false);
		img = background.getImage();
	}

	// �̹��� ���
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this);
	}

	public void addTwo(Component c1, Component c2) {
		add(c1);
		add(c2);
	}

	public void addThree(Component c1, Component c2, Component c3) {
		add(c1);
		add(c2);
		add(c3);
	}

	/** �г��� ��� �̹����� ���� */
	public void setBackGroundImage(String file) {
		background = new ImageIcon(file);
		img = background.getImage();
		repaint();
	}

	abstract public void initPanel();
}