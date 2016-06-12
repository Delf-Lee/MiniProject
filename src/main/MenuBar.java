package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import panel.BasePanel;
import panel.MsgWinow;
import panel.PanelManager;
import user.UserManager;

public class MenuBar extends JMenuBar {

	private static final int MENU_NAME = 0;
	// 배열의 첫번 째 요소는 메뉴이름, 그 이하로는 아이템 이름 
	private PanelManager panel;
	private String[][] menuName = { { "File", "Logout", "Exit" }, { "Edit", "Change Background" }, { "Show", "Ranking", "Panel" } };
	private JMenu[] menu;

	private MenuBarActionListener listener = new MenuBarActionListener();

	/** 생성자 */
	public MenuBar(PanelManager panel) {
		this.panel = panel;
		menu = new JMenu[menuName.length];

		for (int i = 0; i < menuName.length; i++) {
			menu[i] = new JMenu(menuName[i][MENU_NAME]); // 각각의 메뉴 생성
			for (int j = 1; j < menuName[i].length; j++) {
				menu[i].add(new JMenuItem(menuName[i][j])); // 아이템 생성
				menu[i].getItem(j - 1).addActionListener(listener); // 아이템에 리스너 설정
			}
			add(menu[i]); // 메뉴바에 메뉴 부착
		}
	}

	/** @return 선택한 파일의 파일 경로 */
	private String selectFile() {
		JFileChooser explorer = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("이미지 파일", "jpg", "png", "gif");
		explorer.setFileFilter(filter);

		int ret = explorer.showOpenDialog(null);

		if (ret != JFileChooser.APPROVE_OPTION) {
			/*경고창*/
			return null;
		}
		String file = explorer.getSelectedFile().getPath();
		return file;
	}

	/** 각 메뉴의 아이템에 대한 리스너 */
	class MenuBarActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String selected = e.getActionCommand();
			switch (selected) {
			// Files
			case "Logout":
				// 홈패널이 아니라면 홈페널로 이동
				if (panel.getNowPanel() != panel.getHomePanel()) {
					boolean confirm = MsgWinow.confirm("로그아웃 하시겠습니까?");
					if (confirm) {
						panel.setContentPane(PanelManager.HOME);
					}
				}
				break;
			case "Exit":
				boolean confirm = MsgWinow.confirm("종료하시겠습니까?");
				if (confirm) {
					UserManager.saveUserData(); // User 벡터 파일에 쓰기
					System.exit(0);
				}
				break;
			// Edie
			case "Change Background":
				BasePanel bp = (BasePanel) panel.getNowPanel(); // 현재 패널 알아오기
				String selectedFile = selectFile(); // 파일 선택
				if (selectedFile == null) {
					return; // 이벤트를 빠져 나옴
				}
				bp.setBackGroundImage(selectedFile); // 배경이미지 변경
				break;
			// Show
			case "Show Ranking":
				break;
			case "Help":
				break;
			}
		}
	}
}
