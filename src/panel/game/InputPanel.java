package panel.game;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPanel extends JPanel {
	JTextField inputField = new JTextField();
	JLabel label = new JLabel("�Է�");

	public InputPanel(int x, int y, int width, int height) {
		// �ش� �г��� ��ġ, ũ��, ��ġ������ ����
		this.setBounds(x, y, width, height);
		this.setLayout(null);

		// �Է�â ���� �� ��ġ, ũ�� ����
		inputField.setBounds(45, 10, 140, 30); // ũ��, ��ġ ����
		add(inputField); // ����

		// ���̺� ���� �� ��ġ, ũ�� ����
		label.setBounds(10, 10, 130, 30); // ũ��, ��ġ ����
		add(label); // ����
	}

	public void initTextField() {
		inputField.setText("");
	}

	public JTextField getTextField() {
		return inputField;
	}

	public String getText() {
		return inputField.getText();
	}

	public String typeWord() {
		/* �Է��� �ܾ ����Ʈ�� ����*/
		return null;
	}

}
