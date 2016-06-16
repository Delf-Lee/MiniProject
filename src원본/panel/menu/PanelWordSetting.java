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
	// 리스트
	private DefaultListModel<String> wordListModel = new DefaultListModel<String>();
	private JList<String> wordList = new JList<String>();
	// 스크롤
	private JScrollPane wordScroll = new JScrollPane();
	// 텍스트 필드
	private JTextField textInputBox; // 단어 입력창
	// 버튼
	private JButton btnWordAdd;
	public JButton btnBack;
	// 파일 입출력
	private FileWriter fout = null;
	private FileReader fin = null;

	/** 생성자 */
	public PanelWordSetting(PanelManager panel) {
		this.panel = panel;

		setWindow();
		setComponent();
		setListener();
	}

	/** 창 기본 설정 */
	private void setWindow() {
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		setLocation(0, 25);
		wordScroll.setBounds(400, 257, 300, 300);
		setBackground(Color.CYAN); // 삭제 예정 라인
	}

	/** 리스터 세팅 */
	private void setListener() {
		btnWordAdd.addActionListener(new WordSettingListener());
		btnBack.addActionListener(new WordSettingListener());
	}

	/** 패널 내 컴포넌트 세팅 */
	private void setComponent() {
		addWordFileToList(wordListModel);

		wordList.setModel(wordListModel);
		wordList.setFont(new Font("맑은 고딕", Font.BOLD, 20));

		wordScroll.setViewportView(wordList);

		textInputBox = new JTextField();
		textInputBox.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		textInputBox.setBounds(400, 660, 200, 55);

		btnWordAdd = new JButton("단어 추가");
		btnWordAdd.setBounds(600, 660, 100, 55);

		btnBack = new JButton("뒤로");
		btnBack.setBounds(900, 580, 100, 55);

		add(textInputBox);
		add(btnWordAdd);
		add(wordScroll);
		add(btnBack);
	}

	/** 단어 중복체크 */
	public boolean checkDuplicate() {
		try {
			fin = new FileReader(MainFrame.FILEROOT + "\\word.txt"); // 경로 수정!
			/* 나중에 이 경로를 고정 */
			BufferedReader reader = new BufferedReader(fin);

			String word = null;

			while ((word = reader.readLine()) != null) { // 한 줄 단위로 읽어옴
				if (word.equals(textInputBox.getText().replaceAll(" ", ""))) { // 입력 단어의 " "를 ""로 대체
					MsgWinow.error("이미 존재하는 단어입니다.");
					return false;
				}
			}
			reader.close();

		} catch (IOException e) {
			System.out.println("입출력 오류 (중복단어 체크)");
			System.exit(1);
		}
		return true;
	}

	/** 단어 추가 */
	public void addWord() {
		try {
			if (textInputBox.getText().length() == 0) { // 아무것도 입력하지 않으면
				MsgWinow.error("단어를 입력하세요.");
				return;
			}
			fout = new FileWriter(MainFrame.FILEROOT + "\\word.txt", true); // 경로 수정!
			fout.write(textInputBox.getText().replaceAll(" ", "") + "\n"); // 입력 단어의 " "를 ""로 대체
			wordListModel.addElement(textInputBox.getText().replaceAll(" ", "")); // 입력 단어의 " "를 ""로 대체
			fout.close();
		} catch (IOException e) {
			System.out.println("입출력 오류 (단어 추가)");
			System.exit(1);
		}
	}

	/** 파일에 저장되어 있던 단어듣을 List에 담는다. */
	private void addWordFileToList(DefaultListModel<String> wordListModel) {
		try {
			fin = new FileReader(MainFrame.FILEROOT + "\\word.txt"); // 경로 수정!
			BufferedReader reader = new BufferedReader(fin);
			String word = null;
			while ((word = reader.readLine()) != null) { // 한 줄 단위로 읽어옴
				wordListModel.addElement(word);
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("입출력 오류 addwordList");
			System.exit(1);
		}
	}

	/** 리스너 */
	class WordSettingListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton pressedBtn = (JButton) e.getSource();
			switch (pressedBtn.getText()) {
			case "단어 추가":
				if (checkDuplicate()) {// 중복된 단어 체크
					addWord(); // 단어 추가
				}
				initPanel(); // 초기화
				break;
			case "뒤로":
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