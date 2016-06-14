package panel.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.MainFrame;
import panel.BasePanel;
import panel.MsgWinow;
import panel.PanelManager;

public class WordSettingPanel extends BasePanel {
	private PanelManager panel;
	// ����Ʈ
	private DefaultListModel<String> wordListModel;
	private JList<String> wordList;
	// ��ũ��
	private JTextArea txtLog = new JTextArea();
	private JScrollPane wordScroll = new JScrollPane(txtLog);
	// �ؽ�Ʈ �ʵ�
	private JTextField textInputBox; // �ܾ� �Է�â
	// �̹���������
	private ImageIcon wordAddIcon = new ImageIcon("images/plus.png");
	private ImageIcon wordDeleteIcon = new ImageIcon("images/minus.png");
	private ImageIcon backIcon = new ImageIcon("images/back.png");
	// ��ư
	private JButton btnWordAdd;
	private JButton btnWordDelete;
	public JButton btnBack;
	// ���� �����
	private FileWriter fout = null;
	private FileReader fin = null;
	//������
	private ButtonListener buttonListener = new ButtonListener();
	private WordSettingKeyListener wordKeyListener = new WordSettingKeyListener();

	private int cnt = 0; // ���� �� �ܾ� ��

	public WordSettingPanel(PanelManager panel) {
		// â ����
		super("images/�ܾ��.png");
		this.panel = panel;
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		setLocation(0, 25);
		setBackground(Color.CYAN); // ���� ���� ����

		setComponent(); // ��ư ����
		setListener();

		requestFocus();
		//wordList.requestFocus();// JList�� ��Ŀ��
	}

	/** ������ ���� */
	private void setListener() {
		textInputBox.addKeyListener(wordKeyListener); // �Է�
		wordList.addKeyListener(wordKeyListener);

		btnWordAdd.addMouseListener(buttonListener); // �߰� ��ư
		btnWordDelete.addMouseListener(buttonListener); // ���� ��ư
		btnBack.addMouseListener(buttonListener); // �ڷΰ���
	}

	/** ������Ʈ ���� �� ��ġ */
	private void setComponent() {
		wordScroll.setBounds(100, 50, 300, 600);

		// list�� ���Ͼ��� �ܾ� ��� �߰�
		wordListModel = new DefaultListModel<String>();
		addWordList(wordListModel);

		wordList = new JList<String>();
		wordList.setModel(wordListModel);
		wordList.setFont(new Font("���� ���", Font.BOLD, 20));
		wordScroll.setViewportView(wordList);

		textInputBox = new JTextField();
		textInputBox.setFont(new Font("���� ���", Font.BOLD, 20));
		textInputBox.setBounds(670, 200, 300, 55);

		btnWordAdd = new JButton("+", wordAddIcon);
		btnWordAdd.setBorderPainted(false);
		btnWordAdd.setFocusPainted(false);
		btnWordAdd.setContentAreaFilled(false);
		btnWordAdd.setBounds(830, 260, 70, 70);

		btnWordDelete = new JButton("-", wordDeleteIcon);
		btnWordDelete.setBorderPainted(false);
		btnWordDelete.setFocusPainted(false);
		btnWordDelete.setContentAreaFilled(false);
		btnWordDelete.setBounds(910, 260, 70, 70);

		btnBack = new JButton("��", backIcon);
		btnBack.setBorderPainted(false);
		btnBack.setFocusPainted(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setBounds(910, 600, 80, 70);

		add(textInputBox);
		add(btnWordAdd);
		add(btnWordDelete);
		add(wordScroll);
		add(btnBack);
	}

	public void addWordList(DefaultListModel<String> wordListModel) {
		try {
			fin = new FileReader(MainFrame.FILEROOT + "\\word.txt"); // ��� ����!
			BufferedReader reader = new BufferedReader(fin);

			String word = null;
			while ((word = reader.readLine()) != null) { // �� �� ������ �о��
				wordListModel.addElement(word);
				cnt++;
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("����� ���� addwordList");
			//System.exit(1);
		}
	}

	//	class WordAddActionListnener implements ActionListener {
	//		@Override
	//		public void actionPerformed(ActionEvent arg) {
	//			btnWordAddEvent();
	//		}
	//	}

	class ButtonListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			JButton pressedBtn = (JButton) e.getSource();
			System.out.println(pressedBtn.getText());
			switch (pressedBtn.getText()) {
			case "+":
				addWordEvent();

				break;

			case "-":
				deleteWordEvent();
				break;

			case "��":
				writeWordDate();
				break;
			}
		}
	}

	class WordSettingKeyListener extends KeyAdapter {
		private boolean focusTextField = true;

		public void keyPressed(KeyEvent arg) {
			switch (arg.getKeyCode()) {
			case KeyEvent.VK_ENTER: // �߰� ����Ű
				if (focusTextField)
					addWordEvent();
				break;

			case KeyEvent.VK_D: // ���� ����Ű
				if (!focusTextField)
					deleteWordEvent();
				break;

			case KeyEvent.VK_LEFT:
				wordList.requestFocus();// JList�� ��Ŀ��
				focusTextField = false;
				break;

			case KeyEvent.VK_RIGHT:
				textInputBox.requestFocus(); // �ؽ�Ʈ�ʵ�� ��Ŀ��
				focusTextField = true;
				break;

			case KeyEvent.VK_ESCAPE: // �ڷΰ���
				writeWordDate();
				break;
			}
		}
	}

	private void deleteWordEvent() {
		int index = wordList.getSelectedIndex(); //���õ� �׸��� �ε����� �����´�.

		wordListModel.remove(index); //����Ʈ�𵨿��� ���õ� �׸��� �����.
		if (wordListModel.getSize() == 0) { //����Ʈ���� ����� 0�̵Ǹ� ������ư�� ���� �� ���� �Ѵ�.
			btnWordDelete.setEnabled(false);
		}
		if (index == wordListModel.getSize()) { //�ε����� ����Ʈ���� �������׸��� ������
			index--; //��,���õ� �ε����� ����Ʈ�� ������ �׸��̾����� �ε����� -1�ؼ� �ε����� �Ű��ش�.
		}
		wordList.setSelectedIndex(index);
		wordList.ensureIndexIsVisible(index);
	}

	private void addWordEvent() {
		// �ߺ��� �ܾ� üũ
		try {
			fin = new FileReader(MainFrame.FILEROOT + "\\word.txt"); // ��� ����!
			/* ���߿� �� ��θ� ���� */
			BufferedReader reader = new BufferedReader(fin);

			String word = null;

			while ((word = reader.readLine()) != null) { // �� �� ������ �о��
				if (word.equals(textInputBox.getText())) { // �Է� �ܾ��� " "�� ""�� ��ü
					MsgWinow.error("�̹� �����ϴ� �ܾ��Դϴ�");
					return;
				}
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("����� ���� (�ߺ��ܾ� üũ)");
			System.exit(1);
		}
		// �ܾ� �߰�

		if (textInputBox.getText().length() == 0) { // �ƹ��͵� �Է����� ������
			MsgWinow.error("�ܾ �Է��ϼ���.");
			return;
		}

		wordListModel.addElement(textInputBox.getText()/*.replaceAll(" ", "")*/); // �Է� �ܾ��� " "�� ""�� ��ü
		textInputBox.setText("");

		/* �ӽ��ڵ� ��*/
		txtLog.append(textInputBox.getText() + "\n"); // �α� ������ JTextArea ���� �ٿ��ְ�
		txtLog.setCaretPosition(txtLog.getDocument().getLength()); // �ǾƷ��� ��ũ���Ѵ�.
		/* �ӽ��ڵ� ��*/

		/* �����ڵ� */

		//JScrollBar e = wordScroll.getVerticalScrollBar();
		//e.getMaximum();
		//wordScroll.getVerticalScrollBar().setValue(e.getMaximum());
		//e.setValue(e.getMinimum());
		//wordScroll.getVerticalScrollBarPolicy();
		// ��ũ�� �Ʒ��� ������.. �ٵ� �Ʒ��� ������ �� ���� �ȿö󰨤���������

		wordScroll.getVerticalScrollBar().setValue(wordScroll.getVerticalScrollBar().getMaximum() + 100);

		//		wordScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
		//			public void adjustmentValueChanged(AdjustmentEvent e) {
		//				JScrollBar src = (JScrollBar) e.getSource();
		//				src.setValue(src.getMaximum());
		//			}
		//		});
		//wordScroll.getVerticalScrollBar().removeAdjustmentListener();

	}

	private void writeWordDate() {
		try {
			fout = new FileWriter(MainFrame.FILEROOT + "\\word.txt"); // ��� ����!
			for (int i = 0; i < wordListModel.getSize(); i++) {
				fout.write(wordListModel.getElementAt(i)/*.replaceAll(" ", "")*/ + "\n"); // �Է� �ܾ��� " "�� ""�� ��ü
			}
			fout.close();
		} catch (IOException e) {
			System.out.println("����� ���� (�ܾ� �߰�)");
			System.exit(1);
		}
		panel.setContentPane(PanelManager.MENU);
		repaint();
	}

	public void setFocus() {
		textInputBox.requestFocus();
	}

	@Override
	public void initPanel() {
		textInputBox.setText("");
	}
}
