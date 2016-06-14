package panel.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import main.MainFrame;
import panel.BasePanel;
import panel.PanelManager;

public class MenuPanel extends BasePanel {
	private PanelManager panel;
	private LevelChoicePanel levelChoice;
	// Á¤Àûº¯¼ö
	private static final String GAME_START = "°ÔÀÓ½ÃÀÛ";
	private static final String WORD_SETTING = "´Ü¾îÀÔ·Â";
	private static final String RANKING = "·©Å·º¸±â";
	private static final String LOGOUT = "·Î±×¾Æ¿ô";
	// ¹öÆ° ÀÌ¹ÌÁö
	private ImageIcon[][] btnImages = new ImageIcon[4][2];

	private ImageIcon gameIcon = new ImageIcon("images/°ÔÀÓÇÏ±â.png");
	private ImageIcon wordIcon = new ImageIcon("images/´Ü¾î¼³Á¤.png");
	private ImageIcon rankingIcon = new ImageIcon("images/·©Å·º¸±â.png");
	private ImageIcon logoutIcon = new ImageIcon("images/·Î±×¾Æ¿ô.png");
	private ImageIcon gameIcon_ = new ImageIcon("images/°ÔÀÓÇÏ±â2.png");
	private ImageIcon wordIcon_ = new ImageIcon("images/´Ü¾î¼³Á¤2.png");
	private ImageIcon rankingIcon_ = new ImageIcon("images/·©Å·º¸±â2.png");
	private ImageIcon logoutIcon_ = new ImageIcon("images/·Î±×¾Æ¿ô2.png");

	/*ÀÓ½ÃÄÚµå*/
	//private JButton[] menuButns;

	private JButton btnGameStart = new JButton(GAME_START, gameIcon);
	private JButton btnWordSetting = new JButton(WORD_SETTING, wordIcon);
	private JButton btnRanking = new JButton(RANKING, rankingIcon);
	private JButton btnLogout = new JButton(LOGOUT, logoutIcon);

	/*ÀÓ½ÃÄÚµå*/
	//private String[][] btnImageName = { { "°ÔÀÓÇÏ±â", "°ÔÀÓÇÏ±â2" }, { "´Ü¾îÀÔ·Â", "´Ü¾îÀÔ·Â2" }, { "·©Å·º¸±â", "·©Å·º¸±â2" }, { "·Î±×¾Æ¿ô", "·Î±×¾Æ¿ô2" } };
	// ¸®½º³Ê
	private MenuActionEvent menuAction = new MenuActionEvent();
	private MenuMouseEvent menuEvent = new MenuMouseEvent();

	@Override
	public void initPanel() {

	}

	public MenuPanel(PanelManager panel) {
		super("images/menuBG.png");
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		setLocation(0, 25);
		this.panel = panel;
		levelChoice = panel.getLevelChoicePanel();
		
		
		setMenu(); // ¹öÆ° ¼¼ÆÃ
		setButtonListener(); // ¸®½º³Ê ¼¼ÆÃ
	}

	/** ÆÐ³Î ³»ÀÇ ¸Þ´º ¹öÆ° ¸®½º³Ê ¼¼ÆÃ */
	private void setButtonListener() {
		btnGameStart.addActionListener(menuAction);
		btnWordSetting.addActionListener(menuAction);
		btnRanking.addActionListener(menuAction);
		btnLogout.addActionListener(menuAction);

		btnGameStart.addMouseListener(menuEvent);
		btnWordSetting.addMouseListener(menuEvent);
		btnRanking.addMouseListener(menuEvent);
		btnLogout.addMouseListener(menuEvent);
	}
	
	/** ÆÐ³Î ³»ÀÇ ¸Þ´º ¹öÆ°µé ¼³Á¤ ¹× ºÎÂø */
	private void setMenu() {

		/*ÀÓ½ÃÄÚµå*/
//		for (int i = 0; i < btnImages.length; i++) {
//			for (int j = 0; j < btnImages[i].length; j++) {
//				btnImages[i][j] = new ImageIcon("images/" + btnImageName[i][j] + ".png");
//			}
//		}

		btnGameStart.setBorderPainted(false);
		btnGameStart.setFocusPainted(false);
		btnGameStart.setContentAreaFilled(false);
		btnGameStart.setBounds(445, 100, 370, 100);

		btnWordSetting.setBorderPainted(false);
		btnWordSetting.setFocusPainted(false);
		btnWordSetting.setContentAreaFilled(false);
		btnWordSetting.setBounds(445, 220, 370, 100);

		btnRanking.setBorderPainted(false);
		btnRanking.setFocusPainted(false);
		btnRanking.setContentAreaFilled(false);
		btnRanking.setBounds(445, 340, 370, 100);

		btnLogout.setBorderPainted(false);
		btnLogout.setFocusPainted(false);
		btnLogout.setContentAreaFilled(false);
		btnLogout.setBounds(445, 460, 370, 100);

		add(levelChoice);
		levelChoice.setVisible(false);
		
		add(btnGameStart); // ºÙ¿©! ­S
		add(btnWordSetting); // ­S
		add(btnRanking); // ­S
		add(btnLogout); // ­S

		
		
	}

	public BasePanel getBasePanel() {
		return this; // ¿Ö¸¸µé¾ú´õ¶ó¤¾¤¾
	}

	/** ¸Þ´ºÆÐ³Î¿¡ ´ëÇÑ ¹öÆ°µéÀÇ ¾×¼Ç¸®½º³Ê */
	class MenuActionEvent implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton pressedBtn = (JButton) e.getSource();
			switch (pressedBtn.getText()) {
			case GAME_START:
				levelChoice.setNowPanel(LevelChoicePanel.MENU);
				levelChoice.setVisible(true);
				//add(levelChoice);
				
				
				//panel.setContentPane(PanelManager.LEVELCHOICE);
				levelChoice.setButtonEnable(); // ¹öÆ° ºñÈ°¼ºÈ­ ¼³Á¤
				break;

			case WORD_SETTING:
				panel.getWordSettingPanel().initPanel();
				panel.setContentPane(PanelManager.WORD_SETTING);
				panel.getWordSettingPanel().setFocus(); // Æ÷Ä¿½º
				break;
			case RANKING:
				panel.getRankingPanel().initPanel();
				panel.setContentPane(PanelManager.RANKING);
				break;
			case LOGOUT:
				panel.getLoninPanel().initPanel();
				panel.setContentPane(PanelManager.HOME);
				break;
			default:
				break;
			}
		}
	}

	//	/** °ÔÀÓ ±¸µ¿ ½º·¹µå¸¦ »ý¼ºÇÏ°í °ÔÀÓÀ» ½ÇÇà */
	//	public void gameStart() {
	//		GameThread newGame = new GameThread(panel.getGamePanel());
	//		int stage = 1; /* ½ºÅ×ÀÌÁö¸¦ °áÁ¤ÇÒ ¼ö ÀÖ´Â ÆÐ³Î ¸¸µé±â! */
	//		newGame.setGame(UserManager.user, stage);
	//		newGame.start();
	//		newGame.setFocus();
	//	}

	/** ¸Þ´ºÆÐ³Î¿¡ ´ëÇÑ ¹öÆ°µéÀÇ ¸¶¿ì½º¸®½º³Ê */
	class MenuMouseEvent extends MouseAdapter {
		// ¾Æ¹«°Íµµ ¾ÈÇÒ ¶§,
		public void mouseExited(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			switch (eventBtn.getText()) {
			case GAME_START:
				btnGameStart.setIcon(gameIcon);
				break;
			case WORD_SETTING:
				btnWordSetting.setIcon(wordIcon);
				break;
			case RANKING:
				btnRanking.setIcon(rankingIcon);
				break;
			case LOGOUT:
				btnLogout.setIcon(logoutIcon);
				break;
			}
		}

		// ¹öÆ° À§¿¡ ¸¶¿ì½º¸¦ ¿Ã¸± ¶§,
		public void mouseEntered(MouseEvent e) {
			JButton eventBtn = (JButton) e.getSource();
			switch (eventBtn.getText()) {
			case GAME_START:
				btnGameStart.setIcon(gameIcon_);
				break;
			case WORD_SETTING:
				btnWordSetting.setIcon(wordIcon_);
				break;
			case RANKING:
				btnRanking.setIcon(rankingIcon_);
				break;
			case LOGOUT:
				btnLogout.setIcon(logoutIcon_);
				break;
			}
		}
	}
}
