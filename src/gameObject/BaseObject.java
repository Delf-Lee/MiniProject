package gameObject;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BaseObject extends JPanel {
	protected ObjectLabel wordLabel;
	protected JLabel imageLabel;
	protected ImageIcon objectImage;
	protected int width = 0;
	protected int height = 0;

	public BaseObject(String word, Point start) {
		setLocation(start);
		setTextLabel(word);
		
		setLayout(null);

		setOpaque(false);
	}

	/** �ܾ� ���̺� ���� */
	protected void setTextLabel(String word) {
		wordLabel = new ObjectLabel(word);
		setTextLabelSize();
		add(wordLabel);
		height += wordLabel.getHeight();
		if (width < wordLabel.getWidth()) {
			width = wordLabel.getWidth();
		}
		wordLabel.setVisible(true);
	}

	/** �̹��� ���̺� ���� */
	protected void setImageLabel() {
		// �̹��� ���̺� ����
		imageLabel = new JLabel(objectImage);
		imageLabel.setSize(objectImage.getIconWidth(), objectImage.getIconHeight());
		add(imageLabel);
		height += imageLabel.getHeight();
		if (width < imageLabel.getWidth()) {
			width = imageLabel.getWidth();
		}
		imageLabel.setVisible(true);
	}

	/** �ܾ� ��ġ ���� */
	protected void arrangeLabel() { // ������� �ʴ����...
		// ��ġ�����ڰ� ������� �ʾ� �ӽù������� ���� �޼ҵ�
		imageLabel.setLocation(width / 2 - imageLabel.getWidth() / 2, 0);
		wordLabel.setLocation(width / 2 - wordLabel.getWidth() / 2, imageLabel.getHeight() + 10);
	}
	//(start.x + 32) - (wordLabel.getWidth() / 2)

	/** �ܾ� ���̺��� ũ�⸦ �ܾ �µ��� ���� */
	protected void setTextLabelSize() {
		Font currentFont = wordLabel.getFont(); // text�� ���� ��Ʈ ��ü
		FontMetrics fm = wordLabel.getFontMetrics(currentFont); // ��Ʈ�� ���� �Ӽ��� ���� ��ü
		int w = fm.stringWidth(wordLabel.getText()) + 20; // �ؽ�Ʈ�� ��Ʈ�� ����(�ȼ�)
		wordLabel.setSize(w, fm.getHeight() + 5);
		if (w > width) {
			width = w;
		}
	}
	public String getString() {
		return wordLabel.getText();
	}

}
