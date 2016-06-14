package panel.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.MainFrame;
import panel.BasePanel;
import panel.PanelManager;
import user.UserManager;

public class RankingPanel extends BasePanel {
	private PanelManager panel;
	// 콤보
	private JComboBox<String> searchCombo;
	// 텍스트 필드
	private JTextField IDInputBox; // 아이디 입력창
	// 이미지아이콘
	private ImageIcon backIcon = new ImageIcon("images/back.png");
	// 버튼
	private JButton btnSearch;
	public JButton btnBack;
	// 문자열
	private JLabel stringBoxRank;
	private JLabel stringBoxID;
	private JLabel stringBoxScore;

	private JLabel stringBoxRanks[];
	private JLabel stringBoxIDs[];
	private JLabel stringBoxScores[];

	public RankingPanel(PanelManager panel) {
		// 창 설정
		super(/*이미지 경로*/);
		this.panel = panel;
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		setLocation(0, 25);
		setBackground(Color.lightGray); // 삭제 예정 라인

		setComponent(); // 버튼 세팅
		setListener();

		IDInputBox.requestFocus(true);
	}

	/** 리스너 설정 */
	private void setListener() {
		btnSearch.addActionListener(new SearchActionListnener());
		IDInputBox.addKeyListener(new SearchKeyListnener());
		btnBack.addActionListener(new BackActionListnener());
	}

	/** 컴포넌트 설정 및 배치 */
	private void setComponent() {

		stringBoxRank = new JLabel("순위", JLabel.CENTER);
		stringBoxRank.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		stringBoxRank.setBounds(250, 80, 100, 100);

		stringBoxID = new JLabel("I  D", JLabel.CENTER);
		stringBoxID.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		stringBoxID.setBounds(450, 80, 100, 100);

		stringBoxScore = new JLabel("SCORE", JLabel.CENTER);
		stringBoxScore.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		stringBoxScore.setBounds(650, 80, 100, 100);

		UserManager.sortUserList(); // vector score 내림차순

		if (UserManager.userList.size() < 10) {
			setArrayRanks(UserManager.userList.size());
		}
		else {
			setArrayRanks(10);
		}

		searchCombo = new JComboBox<String>();
		searchCombo.addItem("ID");
		searchCombo.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		searchCombo.setBounds(250, 570, 115, 40);

		IDInputBox = new JTextField();
		IDInputBox.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		IDInputBox.setBounds(365, 570, 285, 40);

		btnSearch = new JButton("검색");
		btnSearch.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		btnSearch.setBounds(650, 570, 100, 40);

		btnBack = new JButton(backIcon);
		btnBack.setBorderPainted(false);
		btnBack.setFocusPainted(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setBounds(910, 600, 80, 70);

		add(stringBoxRank);
		add(stringBoxID);
		add(stringBoxScore);

		add(searchCombo);
		add(IDInputBox);
		add(btnSearch);
		add(btnBack);
	}

	public void setArrayRanks(int userListSize) {
		int size = userListSize;
		stringBoxRanks = new JLabel[size];
		stringBoxIDs = new JLabel[size];
		stringBoxScores = new JLabel[size];
		int rank = 1;
		for (int i = 0; i < size; i++) {
			stringBoxRanks[i] = new JLabel(Integer.toString(i + 1), JLabel.CENTER);
			stringBoxRanks[i].setFont(new Font("맑은 고딕", Font.BOLD, 30));
			stringBoxRanks[i].setBounds(250, 130 + 40 * i, 100, 100);
			if(i > 0) {
				if(UserManager.userList.get(i-1).getBestScore() == UserManager.userList.get(i).getBestScore()) {
					stringBoxRanks[i].setText(Integer.toString(rank));
					rank = i;
					rank++;
				}
				else {
					rank++;
				}
			}
			stringBoxIDs[i] = new JLabel(UserManager.userList.get(i).getUserName(), JLabel.CENTER);
			stringBoxIDs[i].setFont(new Font("맑은 고딕", Font.BOLD, 30));
			stringBoxIDs[i].setBounds(405, 130 + 40 * i, 200, 100);
			stringBoxScores[i] = new JLabel(Integer.toString(UserManager.userList.get(i).getBestScore()), JLabel.CENTER);
			stringBoxScores[i].setFont(new Font("맑은 고딕", Font.BOLD, 30));
			stringBoxScores[i].setBounds(650, 130 + 40 * i, 100, 100);
			add(stringBoxRanks[i]);
			add(stringBoxIDs[i]);
			add(stringBoxScores[i]);
		}
	}

	class SearchActionListnener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			search();
		}
	}

	class SearchKeyListnener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				search();
			}
		}
	}

	class BackActionListnener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			panel.setContentPane(PanelManager.MENU);
			initPanel();
			repaint();
		}

	}

	public void search() {
		for (int i = 0; i < UserManager.userList.size(); i++) {
			if (UserManager.userList.get(i).getUserName().equals(IDInputBox.getText())) {
				if (UserManager.userList.size() < 10) {
					int rank = 1;
					for (int j = 0; j < UserManager.userList.size(); j++) {
						stringBoxRanks[j].setText(Integer.toString(j + 1));
						if(j > 0) {
							if(UserManager.userList.get(j-1).getBestScore() == UserManager.userList.get(j).getBestScore()) {
								stringBoxRanks[j].setText(Integer.toString(rank));
							}
							else {
								rank = j;
								rank++;
							}
						}
						stringBoxIDs[j].setText(UserManager.userList.get(j).getUserName());
						stringBoxScores[j].setText(Integer.toString(UserManager.userList.get(j).getBestScore()));
						stringBoxRanks[j].setForeground(Color.BLACK);
						stringBoxIDs[j].setForeground(Color.BLACK);
						stringBoxScores[j].setForeground(Color.BLACK);
					}
					stringBoxRanks[i].setForeground(Color.CYAN);
					stringBoxIDs[i].setForeground(Color.CYAN);
					stringBoxScores[i].setForeground(Color.CYAN);

					IDInputBox.setText(""); // 검색을 누루면 아이디입력필드를 ""로 초기화
					return;
				}
				else if (i < 4) {
					int rank = 1;
					for (int j = 0; j < 10; j++) {
						System.out.println(i + " " + j);
						stringBoxRanks[j].setText(Integer.toString(j + 1));
						if(j > 0) {
							if(UserManager.userList.get(j-1).getBestScore() == UserManager.userList.get(j).getBestScore()) {
								stringBoxRanks[j].setText(Integer.toString(rank));
							}
							else {
								rank = j;
								rank++;
							}
						}
						stringBoxIDs[j].setText(UserManager.userList.get(j).getUserName());
						stringBoxScores[j].setText(Integer.toString(UserManager.userList.get(j).getBestScore()));
						stringBoxRanks[j].setForeground(Color.BLACK);
						stringBoxIDs[j].setForeground(Color.BLACK);
						stringBoxScores[j].setForeground(Color.BLACK);
					}
					stringBoxRanks[i].setForeground(Color.CYAN);
					stringBoxIDs[i].setForeground(Color.CYAN);
					stringBoxScores[i].setForeground(Color.CYAN);

					IDInputBox.setText(""); // 검색을 누루면 아이디입력필드를 ""로 초기화
					return;
				}
				else if (i >= UserManager.userList.size() - 5) {
					int k = 0;
					int rank = UserManager.userList.size() - 9;
					for (int j = UserManager.userList.size() - 10; j < UserManager.userList.size(); j++) {
						stringBoxRanks[k].setText(Integer.toString(j + 1));
						if(k > 0) {
							if(UserManager.userList.get(j-1).getBestScore() == UserManager.userList.get(j).getBestScore()) {
								stringBoxRanks[k].setText(Integer.toString(rank));
							}
							else {
								rank = j;
								rank++;
							}
						}
						stringBoxIDs[k].setText(UserManager.userList.get(j).getUserName());
						stringBoxScores[k].setText(Integer.toString(UserManager.userList.get(j).getBestScore()));

						stringBoxRanks[k].setForeground(Color.BLACK);
						stringBoxIDs[k].setForeground(Color.BLACK);
						stringBoxScores[k].setForeground(Color.BLACK);
						k++;
					}
					stringBoxRanks[9 - (UserManager.userList.size() - (i + 1))].setForeground(Color.CYAN);
					stringBoxIDs[9 - (UserManager.userList.size() - (i + 1))].setForeground(Color.CYAN);
					stringBoxScores[9 - (UserManager.userList.size() - (i + 1))].setForeground(Color.CYAN);
					IDInputBox.setText(""); // 검색을 누루면 아이디입력필드를 ""로 초기화
					return;
				}
				else {
					int k = 0;
					int rank = i-3;
					for (int j = i - 4; j <= i + 5; j++) {
						stringBoxRanks[k].setText(Integer.toString(j + 1));
						if(j > i-4) {
							if(UserManager.userList.get(j-1).getBestScore() == UserManager.userList.get(j).getBestScore()) {
								stringBoxRanks[k].setText(Integer.toString(rank));
							}
							else {
								rank = j;
								rank++;
							}
						}
						stringBoxIDs[k].setText(UserManager.userList.get(j).getUserName());
						stringBoxScores[k].setText(Integer.toString(UserManager.userList.get(j).getBestScore()));
						stringBoxRanks[k].setForeground(Color.BLACK);
						stringBoxIDs[k].setForeground(Color.BLACK);
						stringBoxScores[k].setForeground(Color.BLACK);
						k++;
					}
					stringBoxRanks[4].setForeground(Color.CYAN);
					stringBoxIDs[4].setForeground(Color.CYAN);
					stringBoxScores[4].setForeground(Color.CYAN);
					IDInputBox.setText(""); // 검색을 누루면 아이디입력필드를 ""로 초기화
					return;
				}
			}
		}
		if (IDInputBox.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "아이디를 입력하세요.", "Message", JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(null, "존재하지 않는 아이디 입니다.", "Message", JOptionPane.ERROR_MESSAGE);
		IDInputBox.setText(""); // 검색을 누루면 아이디입력필드를 ""로 초기화
	}

	@Override
	public void initPanel() {
		UserManager.sortUserList(); // vector score 내림차순
		int rank = 1;
		for (int i = 0; i < 10; i++) {
			stringBoxRanks[i].setText(Integer.toString(i + 1));
			if (i > 0) {
				if (UserManager.userList.get(i - 1).getBestScore() == UserManager.userList.get(i).getBestScore()) {
					stringBoxRanks[i].setText(Integer.toString(rank));
				} else {
					rank = i;
					rank++;
				}
			}
			stringBoxIDs[i].setText(UserManager.userList.get(i).getUserName());
			stringBoxScores[i].setText(Integer.toString(UserManager.userList.get(i).getBestScore()));
			stringBoxRanks[i].setForeground(Color.BLACK);
			stringBoxIDs[i].setForeground(Color.BLACK);
			stringBoxScores[i].setForeground(Color.BLACK);
		}
	}
}