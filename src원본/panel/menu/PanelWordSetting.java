package panel.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import main.MainFrame;
import panel.BasePanel;
import panel.MsgWinow;
import panel.PanelManager;

public class PanelWordSetting extends BasePanel {
	private PanelManager panel;
	// ����Ʈ
	private DefaultListModel<String> wordListModel = new DefaultListModel<String>();
	private JList<String> wordList = new JList<String>();
	// ��ũ��
	private JScrollPane wordScroll = new JScrollPane();
	// �ؽ�Ʈ �ʵ�
	private JTextField textInputBox; // �ܾ� �Է�â
	// ��ư
	private JButton btnWordAdd;
	public JButton btnBack;
	// ���� �����
	private FileWriter fout = null;
	private FileReader fin = null;

	/** ������ */
	public PanelWordSetting(PanelManager panel) {
		this.panel = panel;

		setWindow();
		setComponent();
		setListener();
	}

	/** â �⺻ ���� */
	private void setWindow() {
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		setLocation(0, 25);
		wordScroll.setBounds(400, 257, 300, 300);
		setBackground(Color.CYAN); // ���� ���� ����
	}

	/** ������ ���� */
	private void setListener() {
		btnWordAdd.addActionListener(new WordSettingListener());
		btnBack.addActionListener(new WordSettingListener());
	}

	/** �г� �� ������Ʈ ���� */
	private void setComponent() {
		addWordFileToList(wordListModel);

		wordList.setModel(wordListModel);
		wordList.setFont(new Font("���� ���", Font.BOLD, 20));

		wordScroll.setViewportView(wordList);

		textInputBox = new JTextField();
		textInputBox.setFont(new Font("���� ���", Font.BOLD, 20));
		textInputBox.setBounds(400, 660, 200, 55);

		btnWordAdd = new JButton("�ܾ� �߰�");
		btnWordAdd.setBounds(600, 660, 100, 55);

		btnBack = new JButton("�ڷ�");
		btnBack.setBounds(900, 580, 100, 55);

		add(textInputBox);
		add(btnWordAdd);
		add(wordScroll);
		add(btnBack);
	}

	/** �ܾ� �ߺ�üũ */
	public boolean checkDuplicate() {
		try {
			fin = new FileReader(MainFrame.FILEROOT + "\\word.txt"); // ��� ����!
			/* ���߿� �� ��θ� ���� */
			BufferedReader reader = new BufferedReader(fin);

			String word = null;

			while ((word = reader.readLine()) != null) { // �� �� ������ �о��
				if (word.equals(textInputBox.getText().replaceAll(" ", ""))) { // �Է� �ܾ��� " "�� ""�� ��ü
					MsgWinow.error("�̹� �����ϴ� �ܾ��Դϴ�.");
					return false;
				}
			}
			reader.close();

		} catch (IOException e) {
			System.out.println("����� ���� (�ߺ��ܾ� üũ)");
			System.exit(1);
		}
		return true;
	}

	/** �ܾ� �߰� */
	public void addWord() {
		try {
			if (textInputBox.getText().length() == 0) { // �ƹ��͵� �Է����� ������
				MsgWinow.error("�ܾ �Է��ϼ���.");
				return;
			}
			fout = new FileWriter(MainFrame.FILEROOT + "\\word.txt", true); // ��� ����!
			fout.write(textInputBox.getText().replaceAll(" ", "") + "\n"); // �Է� �ܾ��� " "�� ""�� ��ü
			wordListModel.addElement(textInputBox.getText().replaceAll(" ", "")); // �Է� �ܾ��� " "�� ""�� ��ü
			fout.close();
		} catch (IOException e) {
			System.out.println("����� ���� (�ܾ� �߰�)");
			System.exit(1);
		}
	}

	/** ���Ͽ� ����Ǿ� �ִ� �ܾ���� List�� ��´�. */
	private void addWordFileToList(DefaultListModel<String> wordListModel) {
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
			System.exit(1);
		}
	}

	/** ������ */
	class WordSettingListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton pressedBtn = (JButton) e.getSource();
			switch (pressedBtn.getText()) {
			case "�ܾ� �߰�":
				if (checkDuplicate()) {// �ߺ��� �ܾ� üũ
					addWord(); // �ܾ� �߰�
				}
				initPanel(); // �ʱ�ȭ
				break;
			case "�ڷ�":
				panel.setContentPane(PanelManager.MENU);
				break;
			}
		}
	}

	@Override
	public void initPanel() {
		textInputBox.setText("");
	}
}