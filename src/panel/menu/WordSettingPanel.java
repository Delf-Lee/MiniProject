package panel.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
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
	// ��ư
	private JButton btnWordAdd;
	private JButton btnWordDelete;
	public JButton btnBack;
	// ���� �����
	private FileWriter fout = null;
	private FileReader fin = null;

	public WordSettingPanel(PanelManager panel) {
		// â ����
		super(/*�̹��� ���*/);
		this.panel = panel;
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		setLocation(0, 25);
		setBackground(Color.CYAN); // ���� ���� ����

		setComponent(); // ��ư ����
		setListener();

		requestFocus();
	}

	public void addWordList(DefaultListModel<String> wordListModel) {
		try {
			fin = new FileReader(MainFrame.FILEROOT + "\\word.txt"); // ��� ����!
			BufferedReader reader = new BufferedReader(fin);

			String word = null;

			while ((word = reader.readLine()) != null) { // �� �� ������ �о��
				wordListModel.addElement(word);
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("����� ���� addwordList");
			//System.exit(1);
		}
	}

	/** ������Ʈ ���� �� ��ġ */
	private void setComponent() {
		wordScroll.setBounds(400, 257, 300, 300);

		// list�� ���Ͼ��� �ܾ� ��� �߰�
		wordListModel = new DefaultListModel<String>();
		addWordList(wordListModel);

		wordList = new JList<String>();
		wordList.setModel(wordListModel);
		wordList.setFont(new Font("���� ���", Font.BOLD, 20));

		wordScroll.setViewportView(wordList);

		textInputBox = new JTextField();
		textInputBox.setFont(new Font("���� ���", Font.BOLD, 20));
		textInputBox.setBounds(400, 560, 300, 55);

		btnWordAdd = new JButton("�ܾ� �߰�");
		btnWordAdd.setBounds(400, 620, 150, 55);

		btnWordDelete = new JButton("�ܾ� ����");
		btnWordDelete.setBounds(550, 620, 150, 55);

		btnBack = new JButton("�ڷ�");
		btnBack.setBounds(900, 680, 100, 55);

		add(textInputBox);
		add(btnWordAdd);
		add(btnWordDelete);
		add(wordScroll);
		add(btnBack);
	}

	public void btnWordAddEvent() {
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
			MsgWinow.error("�ܾ �Է��ϼ�.��");
			return;
		}

		wordListModel.addElement(textInputBox.getText()/*.replaceAll(" ", "")*/); // �Է� �ܾ��� " "�� ""�� ��ü
		textInputBox.setText("");

		// ��ũ�� �Ʒ��� ������.. �ٵ� �Ʒ��� ������ �� ���� �ȿö󰨤���������
		txtLog.append(textInputBox.getText() + "\n"); // �α� ������ JTextArea ���� �ٿ��ְ�
		txtLog.setCaretPosition(txtLog.getDocument().getLength()); // �ǾƷ��� ��ũ���Ѵ�.

		//		wordScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
		//			public void adjustmentValueChanged(AdjustmentEvent e) {
		//				JScrollBar src = (JScrollBar) e.getSource();
		//				src.setValue(src.getMaximum());
		//			}
		//		});

	}

	/** ������ ���� */
	private void setListener() {
		btnWordAdd.addActionListener(new WordAddActionListnener());
		textInputBox.addKeyListener(new WordAddKeyListener());
		btnWordDelete.addActionListener(new WordDeleteActionListnener());
		btnBack.addActionListener(new BackActionListnener());
	}

	class WordAddActionListnener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg) {
			btnWordAddEvent();
		}
	}

	class WordAddKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent arg) {
			if (arg.getKeyCode() == KeyEvent.VK_ENTER) {
				btnWordAddEvent();
			}
		}
	}

	class WordDeleteActionListnener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg) {
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
	}

	class BackActionListnener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg) {
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
	}

	public void setFocus() {
		textInputBox.requestFocus();
	}

	@Override
	public void initPanel() {
		textInputBox.setText("");
	}
}