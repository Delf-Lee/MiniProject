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
	// 리스트
	private DefaultListModel<String> wordListModel;
	private JList<String> wordList;
	// 스크롤
	private JTextArea txtLog = new JTextArea();
	private JScrollPane wordScroll = new JScrollPane(txtLog);
	// 텍스트 필드
	private JTextField textInputBox; // 단어 입력창
	// 이미지아이콘
	private ImageIcon wordAddIcon = new ImageIcon("images/plus.png");
	private ImageIcon wordDeleteIcon = new ImageIcon("images/minus.png");
	private ImageIcon backIcon = new ImageIcon("images/back.png");
	// 버튼
	private JButton btnWordAdd;
	private JButton btnWordDelete;
	public JButton btnBack;
	// 파일 입출력
	private FileWriter fout = null;
	private FileReader fin = null;
	//리스너
	private ButtonListener buttonListener = new ButtonListener();
	private WordSettingKeyListener wordKeyListener = new WordSettingKeyListener();

	private int cnt = 0; // 파일 내 단어 수

	public WordSettingPanel(PanelManager panel) {
		// 창 설정
		super("images/단어설정.png");
		this.panel = panel;
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		setLocation(0, 25);
		setBackground(Color.CYAN); // 삭제 예정 라인

		setComponent(); // 버튼 세팅
		setListener();

		requestFocus();
		//wordList.requestFocus();// JList로 포커스
	}

	/** 리스너 설정 */
	private void setListener() {
		textInputBox.addKeyListener(wordKeyListener); // 입력
		wordList.addKeyListener(wordKeyListener);

		btnWordAdd.addMouseListener(buttonListener); // 추가 버튼
		btnWordDelete.addMouseListener(buttonListener); // 삭제 버튼
		btnBack.addMouseListener(buttonListener); // 뒤로가기
	}

	/** 컴포넌트 설정 및 배치 */
	private void setComponent() {
		wordScroll.setBounds(100, 50, 300, 600);

		// list에 파일안의 단어 목록 추가
		wordListModel = new DefaultListModel<String>();
		addWordList(wordListModel);

		wordList = new JList<String>();
		wordList.setModel(wordListModel);
		wordList.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		wordScroll.setViewportView(wordList);

		textInputBox = new JTextField();
		textInputBox.setFont(new Font("맑은 고딕", Font.BOLD, 20));
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

		btnBack = new JButton("뒤", backIcon);
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
			fin = new FileReader(MainFrame.FILEROOT + "\\word.txt"); // 경로 수정!
			BufferedReader reader = new BufferedReader(fin);

			String word = null;
			while ((word = reader.readLine()) != null) { // 한 줄 단위로 읽어옴
				wordListModel.addElement(word);
				cnt++;
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("입출력 오류 addwordList");
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

			case "뒤":
				writeWordDate();
				break;
			}
		}
	}

	class WordSettingKeyListener extends KeyAdapter {
		private boolean focusTextField = true;

		public void keyPressed(KeyEvent arg) {
			switch (arg.getKeyCode()) {
			case KeyEvent.VK_ENTER: // 추가 단축키
				if (focusTextField)
					addWordEvent();
				break;

			case KeyEvent.VK_D: // 삭제 단축키
				if (!focusTextField)
					deleteWordEvent();
				break;

			case KeyEvent.VK_LEFT:
				wordList.requestFocus();// JList로 포커스
				focusTextField = false;
				break;

			case KeyEvent.VK_RIGHT:
				textInputBox.requestFocus(); // 텍스트필드로 포커스
				focusTextField = true;
				break;

			case KeyEvent.VK_ESCAPE: // 뒤로가기
				writeWordDate();
				break;
			}
		}
	}

	private void deleteWordEvent() {
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

	private void addWordEvent() {
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
			MsgWinow.error("단어를 입력하세요.");
			return;
		}

		wordListModel.addElement(textInputBox.getText()/*.replaceAll(" ", "")*/); // 입력 단어의 " "를 ""로 대체
		textInputBox.setText("");

		/* 임시코드 ↓*/
		txtLog.append(textInputBox.getText() + "\n"); // 로그 내용을 JTextArea 위에 붙여주고
		txtLog.setCaretPosition(txtLog.getDocument().getLength()); // 맨아래로 스크롤한다.
		/* 임시코드 ↑*/

		/* 오류코드 */

		//JScrollBar e = wordScroll.getVerticalScrollBar();
		//e.getMaximum();
		//wordScroll.getVerticalScrollBar().setValue(e.getMaximum());
		//e.setValue(e.getMinimum());
		//wordScroll.getVerticalScrollBarPolicy();
		// 스크롤 아래로 내리기.. 근데 아래로 내려간 후 위로 안올라감ㅎㅎㅎㅎㅎ

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

	public void setFocus() {
		textInputBox.requestFocus();
	}

	@Override
	public void initPanel() {
		textInputBox.setText("");
	}
}
