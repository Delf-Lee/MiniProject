package panel;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;

import panel.game.PanelGame;
import panel.game.PausePanel;
import panel.home.HomePanel;
import panel.home.LoginPanel;
import panel.home.SignupPanel;
import panel.menu.LevelChoicePanel;
import panel.menu.MenuPanel;
import panel.menu.RankingPanel;
import panel.menu.WordSettingPanel;
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
	public static final int PAUSE = 8;

	public static final String HOME_BACKGROUND_IMG = "";
	public static final String MENU_BACKGROUND_IMG = "";
	public static final String GAME_BACKGROUND_IMG = "";
	public static final String WORD_SETTING_BACKGROUND_IMG = "";

	public UserManager user = new UserManager(); // user를 벡터로
	public static JFrame main;
	private HomePanel home;
	private LoginPanel login;
	private SignupPanel signup;
	private MenuPanel menu;
	private LevelChoicePanel levelChoice;
	private WordSettingPanel wordSetting;
	private RankingPanel ranking;
	private PanelGame game;
	private PausePanel pause;

	public PanelManager(JFrame main) {
		PanelManager.main = main;
		home = new HomePanel(this);
		login = new LoginPanel(262, 350, 500, 300, this);
		signup = new SignupPanel(262, 350, 500, 300, this);
		menu = new MenuPanel(this);
		levelChoice = new LevelChoicePanel(300, 200, 400, 450, this);
		wordSetting = new WordSettingPanel(this);
		ranking = new RankingPanel(this);
		game = new PanelGame();
		pause = new PausePanel(340, 110, 350, 500, this);
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
		case PAUSE:
			main.setContentPane(pause);
			break;
		}
	}

	public Container getNowPanel() {
		return main.getContentPane();
	}

	public void add(BasePanel panel, Component c) {
		panel.add(c);
	}

	public HomePanel getHomePanel() {
		return home;
	}

	public LoginPanel getLoninPanel() {
		return login;
	}

	public SignupPanel getSignupPanel() {
		return signup;
	}

	public MenuPanel getMenuPanel() {
		return menu;
	}

	public LevelChoicePanel getLevelChoicePanel() {
		return levelChoice;
	}

	public PanelGame getGamePanel() {
		return game;
	}

	public WordSettingPanel getWordSettingPanel() {
		return wordSetting;
	}
	public PausePanel getPausePanel() {
		return pause; 
	}
}