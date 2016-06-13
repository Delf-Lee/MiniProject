package panel.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
   // 버튼
   private JButton btnSearch;
   public JButton btnBack;
   // 문자열
   private JLabel stringBoxLank;
   private JLabel stringBoxID;
   private JLabel stringBoxScore;

   private JLabel stringBoxLanks[];
   private JLabel stringBoxIDs[];
   private JLabel stringBoxScores[];

   public RankingPanel(PanelManager panel) {
      // 창 설정
      setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
      setBackground(Color.lightGray); // 삭제 예정 라인
      setLocation(0, 25);

      UserManager.sortUserList(); // vector score 내림차순

      stringBoxLank = new JLabel("순위", JLabel.CENTER);
      stringBoxLank.setFont(new Font("맑은 고딕", Font.BOLD, 30));
      stringBoxLank.setBounds(250, 80, 100, 100);

      stringBoxID = new JLabel("I  D", JLabel.CENTER);
      stringBoxID.setFont(new Font("맑은 고딕", Font.BOLD, 30));
      stringBoxID.setBounds(450, 80, 100, 100);

      stringBoxScore = new JLabel("SCORE", JLabel.CENTER);
      stringBoxScore.setFont(new Font("맑은 고딕", Font.BOLD, 30));
      stringBoxScore.setBounds(650, 80, 100, 100);

      if(UserManager.userList.size() < 10) {
    	  int size = UserManager.userList.size();
    	  stringBoxLanks = new JLabel[size];
    	  stringBoxIDs = new JLabel[size];
    	  stringBoxScores = new JLabel[size]; 
    	  for (int i = 0; i < size; i++) {
    		  //stringBoxLanks[i] = new JLabel(((i+1) + "\t\t" + UserManager.userList.get(i).getUserName() + "\t\t" + Integer.toString(UserManager.userList.get(i).getBestScore())));
    		  stringBoxLanks[i] = new JLabel(Integer.toString(i + 1), JLabel.CENTER);
    		  stringBoxLanks[i].setFont(new Font("맑은 고딕", Font.BOLD, 30));
    		  stringBoxLanks[i].setBounds(250, 130 + 40 * i, 100, 100);
    		  stringBoxIDs[i] = new JLabel(UserManager.userList.get(i).getUserName(), JLabel.CENTER);
    		  stringBoxIDs[i].setFont(new Font("맑은 고딕", Font.BOLD, 30));
    		  stringBoxIDs[i].setBounds(405, 130 + 40 * i, 200, 100);
    		  stringBoxScores[i] = new JLabel(Integer.toString(UserManager.userList.get(i).getBestScore()), JLabel.CENTER);
    		  stringBoxScores[i].setFont(new Font("맑은 고딕", Font.BOLD, 30));
    		  stringBoxScores[i].setBounds(650, 130 + 40 * i, 100, 100);
    		  add(stringBoxLanks[i]);
    		  add(stringBoxIDs[i]);
    		  add(stringBoxScores[i]);
    	  }
      }
      else {
    	  stringBoxLanks = new JLabel[10];
    	  stringBoxIDs = new JLabel[10];
    	  stringBoxScores = new JLabel[10];
    	  for (int i = 0; i < 10; i++) {
    		  // stringBoxLanks[i] = new JLabel(((i+1) + "\t\t" + UserManager.userList.get(i).getUserName() + "\t\t" + Integer.toString(UserManager.userList.get(i).getBestScore())));
    		  stringBoxLanks[i] = new JLabel(Integer.toString(i + 1), JLabel.CENTER);
    		  stringBoxLanks[i].setFont(new Font("맑은 고딕", Font.BOLD, 30));
    		  stringBoxLanks[i].setBounds(250, 130 + 40 * i, 100, 100);
    		  stringBoxIDs[i] = new JLabel(UserManager.userList.get(i).getUserName(), JLabel.CENTER);
    		  stringBoxIDs[i].setFont(new Font("맑은 고딕", Font.BOLD, 30));
    		  stringBoxIDs[i].setBounds(405, 130 + 40 * i, 200, 100);
    		  stringBoxScores[i] = new JLabel(Integer.toString(UserManager.userList.get(i).getBestScore()), JLabel.CENTER);
    		  stringBoxScores[i].setFont(new Font("맑은 고딕", Font.BOLD, 30));
    		  stringBoxScores[i].setBounds(650, 130 + 40 * i, 100, 100);
    		  add(stringBoxLanks[i]);
    		  add(stringBoxIDs[i]);
    		  add(stringBoxScores[i]);
    	  }
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

      btnBack = new JButton("뒤로");
      btnBack.setBounds(900, 680, 100, 55);

      add(stringBoxLank);
      add(stringBoxID);
      add(stringBoxScore);

      add(searchCombo);
      add(IDInputBox);
      add(btnSearch);
      add(btnBack);
		
      requestFocus();
      
      btnSearch.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent arg) {
    		  for (int i = 0; i < UserManager.userList.size(); i++) {
    			  if (UserManager.userList.get(i).getUserName().equals(IDInputBox.getText())) {
    				  if(UserManager.userList.size() < 10) {
    					  for (int j = 0; j < UserManager.userList.size(); j++) {
    						 //stringBoxLanks[j].setText(((j+1) + "\t\t" + UserManager.userList.get(j).getUserName() + "\t\t" + Integer.toString(UserManager.userList.get(j).getBestScore())));
    						  stringBoxLanks[j].setText(Integer.toString(j + 1));
    						  stringBoxIDs[j].setText(UserManager.userList.get(j).getUserName());
    						  stringBoxScores[j].setText(Integer.toString(UserManager.userList.get(j).getBestScore()));
    						  stringBoxLanks[j].setForeground(Color.BLACK);
    						  stringBoxIDs[j].setForeground(Color.BLACK);
    						  stringBoxScores[j].setForeground(Color.BLACK);
    					  }
    					  stringBoxLanks[i].setForeground(Color.CYAN);
    					  stringBoxIDs[i].setForeground(Color.CYAN);
    					  stringBoxScores[i].setForeground(Color.CYAN);	
    					  
    					  IDInputBox.setText(""); // 검색을 누루면 아이디입력필드를 ""로 초기화
    					  return;
						}
						else if (i < 4) {
							for (int j = 0; j < 10; j++) {
								//stringBoxLanks[j].setText(((j+1) + "\t\t" + UserManager.userList.get(j).getUserName() + "\t\t" + Integer.toString(UserManager.userList.get(j).getBestScore())));
								stringBoxLanks[j].setText(Integer.toString(j + 1));
								stringBoxIDs[j].setText(UserManager.userList.get(j).getUserName());
								stringBoxScores[j].setText(Integer.toString(UserManager.userList.get(j).getBestScore()));
								stringBoxLanks[j].setForeground(Color.BLACK);
								stringBoxIDs[j].setForeground(Color.BLACK);
								stringBoxScores[j].setForeground(Color.BLACK);
							}
							stringBoxLanks[i].setForeground(Color.CYAN);
							stringBoxIDs[i].setForeground(Color.CYAN);
							stringBoxScores[i].setForeground(Color.CYAN);

							IDInputBox.setText(""); // 검색을 누루면 아이디입력필드를 ""로 초기화
							return;
						}
						else if (i >= UserManager.userList.size() - 5) {
							int k = 0;
							for (int j = UserManager.userList.size() - 10; j < UserManager.userList.size(); j++) {
								//stringBoxLanks[k].setText(((j+1) + "\t\t" + UserManager.userList.get(j).getUserName() + "\t\t" + Integer.toString(UserManager.userList.get(j).getBestScore())));
								stringBoxLanks[k].setText(Integer.toString(j + 1));
								stringBoxIDs[k].setText(UserManager.userList.get(j).getUserName());
								stringBoxScores[k].setText(Integer.toString(UserManager.userList.get(j).getBestScore()));

								stringBoxLanks[k].setForeground(Color.BLACK);
								stringBoxIDs[k].setForeground(Color.BLACK);
                    	   stringBoxScores[k].setForeground(Color.BLACK);
                    	   k++;
						}
							stringBoxLanks[9 - (UserManager.userList.size() - (i + 1))].setForeground(Color.CYAN);
							stringBoxIDs[9 - (UserManager.userList.size() - (i + 1))].setForeground(Color.CYAN);
							stringBoxScores[9 - (UserManager.userList.size() - (i + 1))].setForeground(Color.CYAN);
							IDInputBox.setText(""); // 검색을 누루면 아이디입력필드를 ""로 초기화
							return;
						}
						else {
							int k = 0;
							for (int j = i - 4; j <= i + 5; j++) {
								//stringBoxLanks[k].setText(((j+1) + "\t\t" + UserManager.userList.get(j).getUserName() + "\t\t" + Integer.toString(UserManager.userList.get(j).getBestScore())));
								stringBoxLanks[k].setText(Integer.toString(j + 1));
								stringBoxIDs[k].setText(UserManager.userList.get(j).getUserName());
								stringBoxScores[k].setText(Integer.toString(UserManager.userList.get(j).getBestScore()));
								stringBoxLanks[k].setForeground(Color.BLACK);
								stringBoxIDs[k].setForeground(Color.BLACK);
								stringBoxScores[k].setForeground(Color.BLACK);
								k++;
							}
							stringBoxLanks[4].setForeground(Color.CYAN);
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
		});
		
		IDInputBox.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					for (int i = 0; i < UserManager.userList.size(); i++) {
						if (UserManager.userList.get(i).getUserName().equals(IDInputBox.getText())) {
							if(UserManager.userList.size() < 10) {
								for (int j = 0; j < UserManager.userList.size(); j++) {
									//stringBoxLanks[j].setText(((j+1) + "\t\t" + UserManager.userList.get(j).getUserName() + "\t\t" + Integer.toString(UserManager.userList.get(j).getBestScore())));
									stringBoxLanks[j].setText(Integer.toString(j + 1));
									stringBoxIDs[j].setText(UserManager.userList.get(j).getUserName());
									stringBoxScores[j].setText(Integer.toString(UserManager.userList.get(j).getBestScore()));
									stringBoxLanks[j].setForeground(Color.BLACK);
									stringBoxIDs[j].setForeground(Color.BLACK);
									stringBoxScores[j].setForeground(Color.BLACK);
								}
								stringBoxLanks[i].setForeground(Color.CYAN);
								stringBoxIDs[i].setForeground(Color.CYAN);
								stringBoxScores[i].setForeground(Color.CYAN);

								IDInputBox.setText(""); // 검색을 누루면 아이디입력필드를 ""로 초기화
								return;
							}
							else if (i < 4) {
								for (int j = 0; j < 10; j++) {
									//stringBoxLanks[j].setText(((j+1) + "\t\t" + UserManager.userList.get(j).getUserName() + "\t\t" + Integer.toString(UserManager.userList.get(j).getBestScore())));
									stringBoxLanks[j].setText(Integer.toString(j + 1));
									stringBoxIDs[j].setText(UserManager.userList.get(j).getUserName());
									stringBoxScores[j].setText(Integer.toString(UserManager.userList.get(j).getBestScore()));
									stringBoxLanks[j].setForeground(Color.BLACK);
									stringBoxIDs[j].setForeground(Color.BLACK);
									stringBoxScores[j].setForeground(Color.BLACK);
								}
								stringBoxLanks[i].setForeground(Color.CYAN);
								stringBoxIDs[i].setForeground(Color.CYAN);
								stringBoxScores[i].setForeground(Color.CYAN);

								IDInputBox.setText(""); // 검색을 누루면 아이디입력필드를 ""로 초기화
								return;
							}
							else if (i >= UserManager.userList.size() - 5) {
								int k = 0;
								for (int j = UserManager.userList.size() - 10; j < UserManager.userList.size(); j++) {
									//stringBoxLanks[k].setText(((j+1) + "\t\t" + UserManager.userList.get(j).getUserName() + "\t\t" + Integer.toString(UserManager.userList.get(j).getBestScore())));
									stringBoxLanks[k].setText(Integer.toString(j + 1));
									stringBoxIDs[k].setText(UserManager.userList.get(j).getUserName());
									stringBoxScores[k].setText(Integer.toString(UserManager.userList.get(j).getBestScore()));

									stringBoxLanks[k].setForeground(Color.BLACK);
									stringBoxIDs[k].setForeground(Color.BLACK);
									stringBoxScores[k].setForeground(Color.BLACK);
									k++;
								}
								stringBoxLanks[9 - (UserManager.userList.size() - (i + 1))].setForeground(Color.CYAN);
								stringBoxIDs[9 - (UserManager.userList.size() - (i + 1))].setForeground(Color.CYAN);
								stringBoxScores[9 - (UserManager.userList.size() - (i + 1))].setForeground(Color.CYAN);
								IDInputBox.setText(""); // 검색을 누루면 아이디입력필드를 ""로 초기화
								return;
							}
							else {
								int k = 0;
								for (int j = i - 4; j <= i + 5; j++) {
									//stringBoxLanks[k].setText(((j+1) + "\t\t" + UserManager.userList.get(j).getUserName() + "\t\t" + Integer.toString(UserManager.userList.get(j).getBestScore())));
									stringBoxLanks[k].setText(Integer.toString(j + 1));
									stringBoxIDs[k].setText(UserManager.userList.get(j).getUserName());
									stringBoxScores[k].setText(Integer.toString(UserManager.userList.get(j).getBestScore()));

									stringBoxLanks[k].setForeground(Color.BLACK);
									stringBoxIDs[k].setForeground(Color.BLACK);
									stringBoxScores[k].setForeground(Color.BLACK);
									k++;
								}
								stringBoxLanks[4].setForeground(Color.CYAN);
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
			}

		});

		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setContentPane(PanelManager.MENU);
				repaint();
			}
		});
	}
	@Override
	public void initPanel() {
		for (int i = 0; i < 10; i++) {
			//stringBoxLanks[i] = new JLabel(((i+1) + "\t\t" + UserManager.userList.get(i).getUserName() + "\t\t" + Integer.toString(UserManager.userList.get(i).getBestScore())));
			stringBoxLanks[i] = new JLabel(Integer.toString(i + 1), JLabel.CENTER);
			stringBoxLanks[i].setFont(new Font("맑은 고딕", Font.BOLD, 30));
			stringBoxLanks[i].setBounds(250, 130 + 40 * i, 100, 100);
			stringBoxIDs[i] = new JLabel(UserManager.userList.get(i).getUserName(), JLabel.CENTER);
			stringBoxIDs[i].setFont(new Font("맑은 고딕", Font.BOLD, 30));
			stringBoxIDs[i].setBounds(405, 130 + 40 * i, 200, 100);
			stringBoxScores[i] = new JLabel(Integer.toString(UserManager.userList.get(i).getBestScore()), JLabel.CENTER);
			stringBoxScores[i].setFont(new Font("맑은 고딕", Font.BOLD, 30));
			stringBoxScores[i].setBounds(650, 130 + 40 * i, 100, 100);
			add(stringBoxLanks[i]);
			add(stringBoxIDs[i]);
			add(stringBoxScores[i]);
		}
		IDInputBox.setText("");
	}
}