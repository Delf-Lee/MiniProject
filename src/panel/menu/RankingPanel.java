package panel.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	// �޺�
	//private JComboBox<String> searchCombo;
	private JLabel searchStringBoxID;
	// �ؽ�Ʈ �ʵ�
	private JTextField IDInputBox; // ���̵� �Է�â
	// �̹���������
	private ImageIcon rankIcon = new ImageIcon("images/����.png");
	private ImageIcon IDIcon = new ImageIcon("images/ID.png");
	private ImageIcon scoreIcon = new ImageIcon("images/score.png");
	private ImageIcon backIcon = new ImageIcon("images/back.png");
	private ImageIcon searchIcon = new ImageIcon("images/�˻�.png");
	private ImageIcon searchIcon_ = new ImageIcon("images/�˻�2.png");
	// ��ư
	private JButton btnSearch;
	public JButton btnBack;
	
	
	// ���ڿ�
	private JLabel stringBoxRank;
	private JLabel stringBoxID;
	private JLabel stringBoxScore;

	private JLabel stringBoxRanks[];
	private JLabel stringBoxIDs[];
	private JLabel stringBoxScores[];

	// ��ġ ��������
	private static final int X = 50;
	private static final int Y = 90;
	

	public RankingPanel(PanelManager panel) {
		// â ����
		super("images/��ŷBG.png");
		this.panel = panel;
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		setLocation(0, 25);
		setBackground(Color.lightGray); // ���� ���� ����

		setComponent(); // ��ư ����
		setListener();

		IDInputBox.requestFocus(true);
	}

	/** ������ ���� */
	private void setListener() {
		btnSearch.addActionListener(new SearchActionListnener());
		IDInputBox.addKeyListener(new SearchKeyListnener());
		btnBack.addActionListener(new BackActionListnener());
		btnSearch.addMouseListener(new SearchMouseListener());
	}

	/** ������Ʈ ���� �� ��ġ */
	private void setComponent() {

		stringBoxRank = new JLabel(rankIcon);
		stringBoxRank.setBounds(X, Y, 100, 100);

		stringBoxID = new JLabel(IDIcon);
		stringBoxID.setBounds(X + 200, Y, 100, 100);

		stringBoxScore = new JLabel(scoreIcon);
		stringBoxScore.setBounds(X + 370, Y, 170, 100);

		UserManager.sortUserList(); // vector score ��������

		if (UserManager.userList.size() < 10) {
			setArrayRanks(UserManager.userList.size());
		}
		else {
			setArrayRanks(10);
		}
		
		searchStringBoxID = new JLabel(IDIcon);
		searchStringBoxID.setBounds(X + 10, Y + 500, 100, 100);

		IDInputBox = new JTextField();
		IDInputBox.setFont(new Font("���� ���", Font.BOLD, 30));
		IDInputBox.setBounds(X + 105, Y + 530, 285, 40);

		btnSearch = new JButton("�˻�", searchIcon);
		btnSearch.setBorderPainted(false);
		btnSearch.setFocusPainted(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBounds(X + 400, Y + 530, 110, 40);

		btnBack = new JButton(backIcon);
		btnBack.setBorderPainted(false);
		btnBack.setFocusPainted(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setBounds(910, 600, 80, 70);

		add(stringBoxRank);
		add(stringBoxID);
		add(stringBoxScore);

//		add(searchCombo);
		add(searchStringBoxID);
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
			stringBoxRanks[i].setFont(new Font("���� ���", Font.BOLD, 30));
			stringBoxRanks[i].setBounds(X, Y + 60 + 40 * i, 100, 100);
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
			stringBoxIDs[i].setFont(new Font("���� ���", Font.BOLD, 30));
			stringBoxIDs[i].setBounds(X + 155, Y + 60 + 40 * i, 200, 100);
			stringBoxScores[i] = new JLabel(Integer.toString(UserManager.userList.get(i).getBestScore()), JLabel.CENTER);
			stringBoxScores[i].setFont(new Font("���� ���", Font.BOLD, 30));
			stringBoxScores[i].setBounds(X + 400, Y + 60 + 40 * i, 100, 100);
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
	
	class SearchMouseListener extends MouseAdapter {
		// �ƹ��͵� ���� ��,
		public void mouseExited(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			switch (eventBtn.getText()) {
			case "�˻�":
				btnSearch.setIcon(searchIcon);
				break;

			}
		}

		// ��ư ���� ���콺�� �ø� ��,
		public void mouseEntered(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			switch (eventBtn.getText()) {
			case "�˻�":
				btnSearch.setIcon(searchIcon_);
				break;

			}
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
					stringBoxRanks[i].setForeground(Color.RED);
					stringBoxIDs[i].setForeground(Color.RED);
					stringBoxScores[i].setForeground(Color.RED);

					IDInputBox.setText(""); // �˻��� ����� ���̵��Է��ʵ带 ""�� �ʱ�ȭ
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
					stringBoxRanks[i].setForeground(Color.RED);
					stringBoxIDs[i].setForeground(Color.RED);
					stringBoxScores[i].setForeground(Color.RED);

					IDInputBox.setText(""); // �˻��� ����� ���̵��Է��ʵ带 ""�� �ʱ�ȭ
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
					stringBoxRanks[9 - (UserManager.userList.size() - (i + 1))].setForeground(Color.RED);
					stringBoxIDs[9 - (UserManager.userList.size() - (i + 1))].setForeground(Color.RED);
					stringBoxScores[9 - (UserManager.userList.size() - (i + 1))].setForeground(Color.RED);
					IDInputBox.setText(""); // �˻��� ����� ���̵��Է��ʵ带 ""�� �ʱ�ȭ
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
					stringBoxRanks[4].setForeground(Color.RED);
					stringBoxIDs[4].setForeground(Color.RED);
					stringBoxScores[4].setForeground(Color.RED);
					IDInputBox.setText(""); // �˻��� ����� ���̵��Է��ʵ带 ""�� �ʱ�ȭ
					return;
				}
			}
		}
		if (IDInputBox.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "���̵� �Է��ϼ���.", "Message", JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(null, "�������� �ʴ� ���̵� �Դϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
		IDInputBox.setText(""); // �˻��� ����� ���̵��Է��ʵ带 ""�� �ʱ�ȭ
	}

	@Override
	public void initPanel() {
		UserManager.sortUserList(); // vector score ��������
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