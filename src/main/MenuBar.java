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
	// �迭�� ù�� ° ��Ҵ� �޴��̸�, �� ���Ϸδ� ������ �̸� 
	private PanelManager panel;
	private String[][] menuName = { { "File", "Logout", "Exit" }, { "Edit", "Change Background" }, { "Show", "Ranking", "Panel" } };
	private JMenu[] menu;

	private MenuBarActionListener listener = new MenuBarActionListener();

	/** ������ */
	public MenuBar(PanelManager panel) {
		this.panel = panel;
		menu = new JMenu[menuName.length];

		for (int i = 0; i < menuName.length; i++) {
			menu[i] = new JMenu(menuName[i][MENU_NAME]); // ������ �޴� ����
			for (int j = 1; j < menuName[i].length; j++) {
				menu[i].add(new JMenuItem(menuName[i][j])); // ������ ����
				menu[i].getItem(j - 1).addActionListener(listener); // �����ۿ� ������ ����
			}
			add(menu[i]); // �޴��ٿ� �޴� ����
		}
	}

	/** @return ������ ������ ���� ��� */
	private String selectFile() {
		JFileChooser explorer = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("�̹��� ����", "jpg", "png", "gif");
		explorer.setFileFilter(filter);

		int ret = explorer.showOpenDialog(null);

		if (ret != JFileChooser.APPROVE_OPTION) {
			/*���â*/
			return null;
		}
		String file = explorer.getSelectedFile().getPath();
		return file;
	}

	/** �� �޴��� �����ۿ� ���� ������ */
	class MenuBarActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String selected = e.getActionCommand();
			switch (selected) {
			// Files
			case "Logout":
				// Ȩ�г��� �ƴ϶�� Ȩ��η� �̵�
				if (panel.getNowPanel() != panel.getHomePanel()) {
					boolean confirm = MsgWinow.confirm("�α׾ƿ� �Ͻðڽ��ϱ�?");
					if (confirm) {
						panel.setContentPane(PanelManager.HOME);
					}
				}
				break;
			case "Exit":
				boolean confirm = MsgWinow.confirm("�����Ͻðڽ��ϱ�?");
				if (confirm) {
					UserManager.saveUserData(); // User ���� ���Ͽ� ����
					System.exit(0);
				}
				break;
			// Edie
			case "Change Background":
				BasePanel bp = (BasePanel) panel.getNowPanel(); // ���� �г� �˾ƿ���
				String selectedFile = selectFile(); // ���� ����
				if (selectedFile == null) {
					return; // �̺�Ʈ�� ���� ����
				}
				bp.setBackGroundImage(selectedFile); // ����̹��� ����
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
