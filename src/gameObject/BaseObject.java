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

	/** 단어 레이블 세팅 */
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

	/** 이미지 레이블 세팅 */
	protected void setImageLabel() {
		// 이미지 레이블 세팅
		imageLabel = new JLabel(objectImage);
		imageLabel.setSize(objectImage.getIconWidth(), objectImage.getIconHeight());
		add(imageLabel);
		height += imageLabel.getHeight();
		if (width < imageLabel.getWidth()) {
			width = imageLabel.getWidth();
		}
		imageLabel.setVisible(true);
	}

	/** 단어 위치 정렬 */
	protected void arrangeLabel() { // 쓰고싶지 않던방법...
		// 배치관리자가 적용되지 않아 임시방편으로 만든 메소드
		imageLabel.setLocation(width / 2 - imageLabel.getWidth() / 2, 0);
		wordLabel.setLocation(width / 2 - wordLabel.getWidth() / 2, imageLabel.getHeight() + 10);
	}
	//(start.x + 32) - (wordLabel.getWidth() / 2)

	/** 단어 레이블의 크기를 단어에 맞도록 조정 */
	protected void setTextLabelSize() {
		Font currentFont = wordLabel.getFont(); // text의 현재 폰트 객체
		FontMetrics fm = wordLabel.getFontMetrics(currentFont); // 폰트의 여러 속성을 가진 객체
		int w = fm.stringWidth(wordLabel.getText()) + 20; // 텍스트의 스트링 길이(픽셀)
		wordLabel.setSize(w, fm.getHeight() + 5);
		if (w > width) {
			width = w;
		}
	}
	public String getString() {
		return wordLabel.getText();
	}

}
