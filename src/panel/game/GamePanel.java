package panel.game;

import javax.sound.midi.MidiDevice.Info;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.MainFrame;
import panel.BasePanel;

public class GamePanel extends BasePanel {
	// 메인프레임
	private MainFrame main;
	private InfoPanel gameInfoBox; // 게임에 대한 정보를 출력(점수, 라이프, 아이템, 레벨 등)
	private LifeBarPanel lifeBar;
	private InputPanel textInputBox; // 단어 입력창
	private ScorePanel scoreBox;

	public GamePanel(/*Player player*/) { // 수정 필요
		super("images/gameBG.png"); // 배경 삽입
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		setLocation(0, 25);

		setComponent();
	}

	private void setComponent() {
		textInputBox = new InputPanel(780, 660, 200, 50);
		scoreBox = new ScorePanel();
		gameInfoBox = new InfoPanel();
		lifeBar = new LifeBarPanel();

		add(textInputBox);
		add(gameInfoBox);
		add(scoreBox);
		add(lifeBar);
	}

	public void showInfoPanelImage() {
		ImageIcon scoreBoard = new ImageIcon("images/score_board.png");
		JLabel board = new JLabel(scoreBoard);
		board.setBounds(0, 0, 356, 274);
		add(board);
	}

	@Override
	public void initPanel() {

	}

	public JTextField getTextField() {
		return textInputBox.getTextField();
	}

	public String getWordString() {
		return textInputBox.getTextField().getText();
	}

	public void initTextField() {
		textInputBox.initTextField();
	}

	public void updateScore(int score) {
		scoreBox.updateScore(score);
	}

	public void setLevel(int lvl) {
		gameInfoBox.setLevel(lvl);
	}

	public void initTime(int time) {

	}

	public boolean updateTime() {


		if (gameInfoBox.updateTime()) {
			return true;
		}
		return false;
	}

	public void setInfo(int lvl, int time) {
		gameInfoBox.setInfo(lvl, time);
	}

	public void lostLife() {
		lifeBar.lostLife();
	}

	public int getLife() {
		return lifeBar.getLife();
	}

	public void initGame() {
		initTextField();
		lifeBar.initLife();
		scoreBox.initScore();

	}

	public int getLevel() {
		return gameInfoBox.level;
	}
}
