package panel.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import panel.BasePanel;
import panel.PanelManager;
import thread.GameThread;
import user.UserManager;

public class LevelChoicePanel extends BasePanel {

	public static final int MENU = 0;
	public static final int PAUSE = 1;

	private PanelManager panel;
	private GameThread thrd;
	// ���̺�
	JLabel stringBoxLevelChoice;
	
	private ImageIcon backIcon = new ImageIcon("images/back.png");
	// ��ư
	JButton[] level;
	JButton btnBack;
	// ������
	private LevelMouseEvent mouseEvent = new LevelMouseEvent();
	// ��ġ ��������
	private static final int X = 22;
	private static final int Y = 75;
	private int nowPanel;
	
	private static final int EXITED = 0;
	private static final int ENTERD = 1;
	private static final int PRESSED = 2;
	
	private ImageIcon btnImages[][] = new ImageIcon[10][2];
	private String[][] btnImageName = {{"1","1_2"}, {"2","2_2"}, {"3","3_2"} ,{"4","4_2"}, {"5","5_2"}, {"6","6_2"}, {"7","7_2"}, {"8","8_2"}, {"9","9_2"}, {"10","10_2"}};

	public LevelChoicePanel(int x, int y, int width, int height, PanelManager panel) {
		super("images/levelchoiceBG.png");
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
			level[i].addMouseListener(mouseEvent);
		}
		btnBack.addActionListener(new LevelChoiceListener());
	}

	/** ������Ʈ ���� �� ��ġ */
	private void setComponent() {
		// ��ư �̹��� ��������
		for (int i = 0; i < btnImages.length; i++) {
			for (int j = 0; j < btnImages[i].length; j++) {
			btnImages[i][j] = new ImageIcon("images/" + btnImageName[i][j] + ".png");
			}
		}
		
		level = new JButton[10];
		for (int i = 0; i < level.length; i++) {
			level[i] = new JButton(Integer.toString(i+1), btnImages[i][0]);
			level[i].setName(Integer.toString(i));
			if (i < 5) {
				level[i].setBounds(X, Y + (65 * i), 185, 80);
			}
			else {
				level[i].setBounds(X + 180, Y + (65 * (i - 5)), 185, 80);
			}
			level[i].setBorderPainted(false);
			level[i].setFocusPainted(false);
			level[i].setContentAreaFilled(false);
			add(level[i]);
		}

		btnBack = new JButton("back", backIcon);
		btnBack.setBorderPainted(false);
		btnBack.setFocusPainted(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setBounds(170, 410, 80, 70);

		add(btnBack);
	}
	
	/** �޴��гο� ���� ��ư���� ���콺������ */
	class LevelMouseEvent extends MouseAdapter {
		// ���콺 ��
		public void mouseExited(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			int btnName = Integer.parseInt(eventBtn.getName());
			level[btnName].setIcon(btnImages[btnName][EXITED]);
		}

		// ���콺 ����
		public void mouseEntered(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			int btnName = Integer.parseInt(eventBtn.getName());
			level[btnName].setIcon(btnImages[btnName][ENTERD]);
		}
	}

	/** ������ �÷����� �� ���� ���� ��ư ��Ȱ��ȭ */
	public void setButtonEnable() {
		System.out.println(UserManager.user.getLastStage());
		for (int i = UserManager.user.getLastStage(); i < level.length; i++) {
			level[i].setEnabled(false);
		}
	}

	/** ��Ȱ��ȭ ����(�ʱ�ȭ) */
	private void InitButton() {
		for (int i = UserManager.user.getLastStage(); i < level.length; i++) {
			level[i].setEnabled(true);
		}
	}

	public void setNowPanel(int flag) {
		this.nowPanel = flag;
	}

	/** �г� �� ��ư�� ���� ������ */
	class LevelChoiceListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton pressedBtn = (JButton) e.getSource();

			switch (pressedBtn.getText()) {
			case "back":
				// �ڷΰ��� ��ư ���� ��,
				if (nowPanel == MENU) { // �޴����� ��������
					panel.setContentPane(PanelManager.MENU);
					setVisible(false);
				}
				else if (nowPanel == PAUSE) { // pause���� ��������
					//panel.getGamePanel().add(panel.getLevelChoicePanel());
					panel.getPausePanel().setVisible(true);
					setVisible(false);
				}
				break;

			default: // �� �ܿ� ������ ���� ��,
				setVisible(false);
				if (nowPanel == PAUSE) {
					thrd.initGame(); // pause���� ���� ���� ��, ���� ������� ���� �ʱ�ȭ
				}
				String tmp = pressedBtn.getText();
				int selectedLevel = Integer.parseInt(tmp.substring(tmp.length() - 1));
				panel.setContentPane(PanelManager.GAME);
				gameStart(selectedLevel);
				break;
			}
			InitButton();
		}
	}

	/** ���� ���� �����带 �����ϰ� ������ ���� */
	public void gameStart(int level) {
		GameThread newGame = new GameThread(panel);
		newGame.setGame(UserManager.user, level);
		newGame.start();
		newGame.setFocus(); 
	}

	public void setThread(GameThread thrd) {
		this.thrd = thrd;
	}

	@Override 
	public void initPanel() {
		// TODO Auto-generated method stub
	}
}