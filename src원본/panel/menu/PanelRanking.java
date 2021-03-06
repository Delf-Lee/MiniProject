package panel.menu;

import user.UserManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import main.MainFrame;
import panel.BasePanel;
import thread.*;

public class PanelRanking extends BasePanel {
	// 테이블
	private JTable rankingTable;
	// 테이블 컬럼
	private String rankingTableColumn[] = { "순위", "ID", "score" };
	// 테이블 목록
	private String rankingTableContents[][] = new String[UserManager.userList.size()][3];
	// 스크롤
	private JScrollPane rankingScroll;
	// 콤보
	private JComboBox<String> searchCombo;
	// 텍스트 필드
	private JTextField IDInputBox; // 아이디 입력창
	// 버튼
	private JButton btnSearch;
	public JButton btnBack;

	public PanelRanking() {
		// 창 설정
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		setBackground(Color.lightGray); // 삭제 예정 라인
		setLocation(0, 25);

		UserManager.sortUserList(); // vector score 내림차순
		for (int i = 0; i < rankingTableContents.length; i++) { // contents에 순위,ID,score 넣기 
			rankingTableContents[i][0] = Integer.toString(i + 1);
			rankingTableContents[i][1] = UserManager.userList.get(i).getUserName();
			rankingTableContents[i][2] = Integer.toString(UserManager.userList.get(i).getBestScore());
		}

		rankingTable = new JTable(rankingTableContents, rankingTableColumn) {
			public boolean isCellEditable(int i, int c) { // 셀 더블클릭 했을 때 수정 방지
				return false;
			}
		};

		// 열 너비 조절
		rankingTable.getColumnModel().getColumn(0).setPreferredWidth(10);
		rankingTable.getColumnModel().getColumn(1).setPreferredWidth(90);
		rankingTable.getColumnModel().getColumn(2).setPreferredWidth(90);

		// rankingTableContents 높이 조절
		rankingTable.setRowHeight(40);
		rankingTable.setFont(new Font("맑은 고딕", Font.BOLD, 30));

		// 이동과 길이조절 여러개 선택 되는 것을 방지한다
		rankingTable.getTableHeader().setReorderingAllowed(false);
		rankingTable.getTableHeader().setResizingAllowed(false);
		rankingTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		// 가운데 정렬
		contentsSortingCenter();

		// rankingTable에 Scroll 붙임
		rankingScroll = new JScrollPane(rankingTable);
		rankingScroll.setBounds(250, 100, 500, 425);

		searchCombo = new JComboBox<String>();
		searchCombo.addItem("ID");
		searchCombo.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		searchCombo.setBounds(250, 550, 115, 40);

		IDInputBox = new JTextField();
		IDInputBox.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		IDInputBox.setBounds(365, 550, 285, 40);

		btnSearch = new JButton("검색");
		btnSearch.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		btnSearch.setBounds(650, 550, 100, 40);

		btnBack = new JButton("뒤로");
		btnBack.setBounds(900, 680, 100, 55);

		add(rankingScroll);
		add(searchCombo);
		add(IDInputBox);
		add(btnSearch);
		add(btnBack);

		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				for (int i = 0; i < rankingTableContents.length; i++) {
					if (rankingTableContents[i][1].equals(IDInputBox.getText())) {
						rankingScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
							public void adjustmentValueChanged(AdjustmentEvent e) {
								JScrollBar src = (JScrollBar) e.getSource();
								src.setValue(src.getMaximum());
							}
						});
						return;
					}
				}

				//            // 왜!!!오류!!!!!!떠!!!!!!
				//            if (IDInputBox.getText().length() == 0) {
				//               JOptionPane.showMessageDialog(this, "아이디를 입력하세요.", "Message", JOptionPane.ERROR_MESSAGE);
				//            }
				IDInputBox.setText(""); // 검색을 누루면 아이디입력필드를 ""로 초기화
			}
		});
	}

	public void contentsSortingCenter() {
		// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcmSchedule = rankingTable.getColumnModel();
		// 반복문을 이용하여 테이블을 가운데 정렬로 지정
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
	}

	@Override
	public void initPanel() {
		IDInputBox.setText("");
	}
}