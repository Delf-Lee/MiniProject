package panel.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.attribute.UserPrincipalLookupService;

import javax.swing.JButton;
import javax.swing.JLabel;

import main.MainFrame;
import panel.BasePanel;
import panel.PanelManager;
import thread.GameThread;
import user.User;
import user.UserManager;

public class PanelLevelChoice extends BasePanel {
	private PanelManager panel;
	// ���̺�
	JLabel stringBoxLevelChoice;
	// ��ư
	JButton[] level;
	// ��ġ ��������
	private static final int X = 70;
	private static final int Y = 100;

	public PanelLevelChoice(int x, int y, int width, int height, PanelManager panel) {
		super(/*�̹��� ���*/);
		this.panel = panel;
		setBounds(x, y, width, height);
		setBackground(Color.CYAN); // ���� ���� ����

		setComponent(); // ��ư ����
		setListener();
	}

	/** ������ ���� */
	private void setListener() {
		for (int i = 0; i < level.length; i++) {
			level[i].addActionListener(new LevelChoiceListener());
		}
	}

	/** ������Ʈ ���� �� ��ġ */
	@SuppressWarnings("deprecation")
	private void setComponent() {
		stringBoxLevelChoice = new JLabel("Level ����", JLabel.CENTER);
		stringBoxLevelChoice.setFont(new Font("���� ���", Font.BOLD, 30));
		stringBoxLevelChoice.setBounds(100, 10, 200, 100);

		level = new JButton[10];
		for (int i = 0; i < level.length; i++) {
			level[i] = new JButton("Level." + Integer.toString(i + 1));
			level[i].setFont(new Font("���� ���", Font.BOLD, 20));
			if (i < 5) {
				level[i].setBounds(X, Y + (50 * i), 115, 40);
			}
			else {
				level[i].setBounds(X + 150, Y + (50 * (i - 5)), 115, 40);
			}
			add(level[i]);
		}
		add(stringBoxLevelChoice);
	}

	public void setButtonEnable() {
		for (int i = UserManager.user.getLastStage(); i < level.length; i++) {
			level[i].setEnabled(false);
		}
	}

	/** �г� �� ��ư�� ���� ������ */
	class LevelChoiceListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton pressedBtn = (JButton) e.getSource();

			switch (pressedBtn.getText()) {
			case "back":
				// �ڷΰ��� ó��
				break;
			default:
				String tmp = pressedBtn.getText();
				int selectedLevel = Integer.parseInt(tmp.substring(tmp.length() - 1));
				panel.setContentPane(PanelManager.GAME);
				gameStart(selectedLevel);
				// ���ӿ��� �ٽ� ���θ޴��� ���ƿö��� ���� setVisivle(false)�� �ؾ��ϳ�?
				break;
			}

		}
	}

	/** ���� ���� �����带 �����ϰ� ������ ���� */
	public void gameStart(int level) {
		repaint(); // �־�� ���� �ٽ� �����غ���
		GameThread newGame = new GameThread(panel);
		newGame.setGame(UserManager.user, level);
		newGame.start();
		newGame.setFocus();
	}

	@Override
	public void initPanel() {
		// TODO Auto-generated method stub
	}

}
