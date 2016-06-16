package panel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MsgWinow extends JOptionPane {
	public static JFrame screen = PanelManager.main;

	public static void error(String text) {
		showMessageDialog(screen, text, "오류", ERROR_MESSAGE);
	}

	public static boolean confirm(String text) {
		int result = showConfirmDialog(null, text, "확인", YES_NO_OPTION);
		if (result == CLOSED_OPTION) {
			return false;
		}
		else if (result == YES_OPTION) {
			return true;
		}
		else {
			return false;
		}
	}
}