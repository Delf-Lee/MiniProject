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
	// 리스트
	private DefaultListModel<String> wordListModel;
	private JList<String> wordList;
	// 스크롤
	private JTextArea txtLog = new JTextArea();
	private JScrollPane wordScroll = new JScrollPane(txtLog);
	// 텍스트 필드
	private JTextField textInputBox; // 단어 입력창
	// 버튼
	private JButton btnWordAdd;
	private JButton btnWordDelete;
	public JButton btnBack;
	// 파일 입출력
	private FileWriter fout = null;
	private FileReader fin = null;

	public WordSettingPanel(PanelManager panel) {
		// 창 설정
		super(/*이미지 경로*/);
		this.panel = panel;
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		setLocation(0, 25);
		setBackground(Color.CYAN); // 삭제 예정 라인

		setComponent(); // 버튼 세팅
		setListener();

		requestFocus();
	}

	public void addWordList(DefaultListModel<String> wordListModel) {
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
			//System.exit(1);
		}
	}

	/** 컴포넌트 설정 및 배치 */
	private void setComponent() {
		wordScroll.setBounds(400, 257, 300, 300);

		// list에 파일안의 단어 목록 추가
		wordListModel = new DefaultListModel<String>();
		addWordList(wordListModel);

		wordList = new JList<String>();
		wordList.setModel(wordListModel);
		wordList.setFont(new Font("맑은 고딕", Font.BOLD, 20));

		wordScroll.setViewportView(wordList);

		textInputBox = new JTextField();
		textInputBox.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		textInputBox.setBounds(400, 560, 300, 55);

		btnWordAdd = new JButton("단어 추가");
		btnWordAdd.setBounds(400, 620, 150, 55);

		btnWordDelete = new JButton("단어 삭제");
		btnWordDelete.setBounds(550, 620, 150, 55);

		btnBack = new JButton("뒤로");
		btnBack.setBounds(900, 680, 100, 55);

		add(textInputBox);
		add(btnWordAdd);
		add(btnWordDelete);
		add(wordScroll);
		add(btnBack);
	}

	public void btnWordAddEvent() {
		// 중복된 단어 체크
		try {
			fin = new FileReader(MainFrame.FILEROOT + "\\word.txt"); // 경로 수정!
			/* 나중에 이 경로를 고정 */
			BufferedReader reader = new BufferedReader(fin);

			String word = null;

			while ((word = reader.readLine()) != null) { // 한 줄 단위로 읽어옴
				if (word.equals(textInputBox.getText())) { // 입력 단어의 " "를 ""로 대체
					MsgWinow.error("이미 존재하는 단어입니다");
					return;
				}
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("입출력 오류 (중복단어 체크)");
			System.exit(1);
		}
		// 단어 추가

		if (textInputBox.getText().length() == 0) { // 아무것도 입력하지 않으면
			MsgWinow.error("단어를 입력하세.요");
			return;
		}

		wordListModel.addElement(textInputBox.getText()/*.replaceAll(" ", "")*/); // 입력 단어의 " "를 ""로 대체
		textInputBox.setText("");

		// 스크롤 아래로 내리기.. 근데 아래로 내려간 후 위로 안올라감ㅎㅎㅎㅎㅎ
		txtLog.append(textInputBox.getText() + "\n"); // 로그 내용을 JTextArea 위에 붙여주고
		txtLog.setCaretPosition(txtLog.getDocument().getLength()); // 맨아래로 스크롤한다.

		//		wordScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
		//			public void adjustmentValueChanged(AdjustmentEvent e) {
		//				JScrollBar src = (JScrollBar) e.getSource();
		//				src.setValue(src.getMaximum());
		//			}
		//		});

	}

	/** 리스너 설정 */
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
			int index = wordList.getSelectedIndex(); //선택된 항목의 인덱스를 가져온다.

			wordListModel.remove(index); //리스트모델에서 선택된 항목을 지운다.
			if (wordListModel.getSize() == 0) { //리스트모델의 사이즈가 0이되면 삭제버튼을 누를 수 없게 한다.
				btnWordDelete.setEnabled(false);
			}
			if (index == wordListModel.getSize()) { //인덱스와 리스트모델의 마지막항목이 같으면
				index--; //즉,선택된 인덱스가 리스트의 마지막 항목이었으면 인덱스를 -1해서 인덱스를 옮겨준다.
			}
			wordList.setSelectedIndex(index);
			wordList.ensureIndexIsVisible(index);
		}
	}

	class BackActionListnener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg) {
			try {
				fout = new FileWriter(MainFrame.FILEROOT + "\\word.txt"); // 경로 수정!
				for (int i = 0; i < wordListModel.getSize(); i++) {
					fout.write(wordListModel.getElementAt(i)/*.replaceAll(" ", "")*/ + "\n"); // 입력 단어의 " "를 ""로 대체
				}
				fout.close();
			} catch (IOException e) {
				System.out.println("입출력 오류 (단어 추가)");
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