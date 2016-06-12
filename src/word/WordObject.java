package word;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WordObject extends JPanel {

	public static final int RABBIT = 0;
	public static final String RABBIT_IMG = "images\\�䳢��Ǫ.gif";

	private WordLabel wordLabel;
	private JLabel imageLabel;
	private ImageIcon animalImage;
	private int width = 0;
	private int height = 0;

	public WordObject(String word, Point start, int type) {
		setLocation(start);
		setTextLabel(word);
		setImageLabel(type);
		arrangeLabel();
		setLayout(null);
		setSize(width, height + 10);

		setOpaque(false);
	}

	/** �ܾ� ���̺� ���� */
	private void setTextLabel(String word) {
		wordLabel = new WordLabel(word);
		setTextLabelSize();
		add(wordLabel);
		height += wordLabel.getHeight();
		if (width < wordLabel.getWidth()) {
			width = wordLabel.getWidth();
		}
		wordLabel.setVisible(true);
	}

	/** �̹��� ���̺� ���� */
	private void setImageLabel(int type) {
		// ������ �̹��� ����
		switch (type) {
		case RABBIT:
			animalImage = new ImageIcon(RABBIT_IMG);
			break;
		}
		// �̹��� ���̺� ����
		imageLabel = new JLabel(animalImage);
		imageLabel.setSize(64, 74); // �̱���
		add(imageLabel);
		height += imageLabel.getHeight();
		if (width < imageLabel.getWidth()) {
			width = imageLabel.getWidth();
		}
		imageLabel.setVisible(true);
	}

	/** �ܾ� ��ġ ���� */
	private void arrangeLabel() { // ������� �ʴ����...
		// ��ġ�����ڰ� ������� �ʾ� �ӽù������� ���� �޼ҵ�
		imageLabel.setLocation(width / 2 - imageLabel.getWidth() / 2, 0);
		wordLabel.setLocation(width / 2 - wordLabel.getWidth() / 2, imageLabel.getHeight() + 10);
	}
	//(start.x + 32) - (wordLabel.getWidth() / 2)

	/** �ܾ� ���̺��� ũ�⸦ �ܾ �µ��� ���� */
	private void setTextLabelSize() {
		Font currentFont = wordLabel.getFont(); // text�� ���� ��Ʈ ��ü
		FontMetrics fm = wordLabel.getFontMetrics(currentFont); // ��Ʈ�� ���� �Ӽ��� ���� ��ü
		int w = fm.stringWidth(wordLabel.getText()) + 20; // �ؽ�Ʈ�� ��Ʈ�� ����(�ȼ�)
		wordLabel.setSize(w, fm.getHeight() + 5);
		if (w > width) {
			width = w;
		}
	}
}
