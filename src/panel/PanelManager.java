package panel;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;

import panel.game.PanelGame;
import panel.home.PanelHome;
import panel.home.PanelLogin;
import panel.home.PanelSignup;
import panel.menu.PanelLevelChoice;
import panel.menu.PanelMenu;
import panel.menu.PanelRanking;
import panel.menu.PanelWordSetting;
import user.UserManager;

public class PanelManager {

	public static final int HOME = 0;
	public static final int LOGIN = 1;
	public static final int SIGNUP = 2;
	public static final int MENU = 3;
	public static final int GAME = 4;
	public static final int WORD_SETTING = 5;
	public static final int RANKING = 6;
	public static final int LEVELCHOICE = 7;

	public static final String HOME_BACKGROUND_IMG = "";
	public static final String MENU_BACKGROUND_IMG = "";
	public static final String GAME_BACKGROUND_IMG = "";
	public static final String WORD_SETTING_BACKGROUND_IMG = "";

	public UserManager user = new UserManager(); // user를 벡터로
	public static JFrame main;
	private PanelHome home;
	private PanelLogin login;
	private PanelSignup signup;
	private PanelMenu menu;
	private PanelLevelChoice levelChoice;
	private PanelWordSetting wordSetting;
	private PanelRanking ranking;
	private PanelGame game;

	public PanelManager(JFrame main) {
		PanelManager.main = main;
		home = new PanelHome(this);
		login = new PanelLogin(262, 350, 500, 300, this);
		signup = new PanelSignup(262, 350, 500, 300, this);
		menu = new PanelMenu(this);
		levelChoice = new PanelLevelChoice(300, 200, 400, 400, this);
		wordSetting = new PanelWordSetting(this);
		ranking = new PanelRanking(this);
		game = new PanelGame();
	}

	public static void setContentPane(Container c) {
		main.setContentPane(c);
		// 코드의 유연성 증가
	}

	public void setContentPane(int panel) {
		// 기존코드 가독성 증가
		switch (panel) {
		case HOME:
			main.setContentPane(home);
			break;
		case LOGIN:
			main.setContentPane(login);
			break;
		case SIGNUP:
			main.setContentPane(signup);
			break;
		case MENU:
			main.setContentPane(menu);
			break;
		case GAME:
			main.setContentPane(game);
			break;
		case WORD_SETTING:
			main.setContentPane(wordSetting);
			break;
		case RANKING:
			main.setContentPane(ranking);
			break;
		case LEVELCHOICE:
			main.setContentPane(levelChoice);
			break;
		}
	}

	public Container getNowPanel() {
		return main.getContentPane();
	}

	public void add(BasePanel panel, Component c) {
		panel.add(c);
	}

	public PanelHome getHomePanel() {
		return home;
	}

	public PanelLogin getLoninPanel() {
		return login;
	}

	public PanelSignup getSignupPanel() {
		return signup;
	}

	public PanelMenu getMenuPanel() {
		return menu;
	}

	public PanelLevelChoice getLevelChoicePanel() {
		return levelChoice;
	}

	public PanelGame getGamePanel() {
		return game;
	}

	public PanelWordSetting getWordSettingPanel() {
		return wordSetting;
	}
}