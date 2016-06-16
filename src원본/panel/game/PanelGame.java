package panel.game;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.MainFrame;
import panel.BasePanel;

public class PanelGame extends BasePanel {
	// ����������
	private MainFrame main;
	private InfoPanel gameInfoBox; // ���ӿ� ���� ������ ���(����, ������, ������, ���� ��)
	private LifeBarPanel lifeBar;
	private InputPanel textInputBox; // �ܾ� �Է�â
	private ScorePanel scoreBox;
	
	public PanelGame(/*Player player*/) { // ���� �ʿ�
		super("images/gameBG.png"); // ��� ����
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		setLocation(0, 25);
		textInputBox = new InputPanel(780, 660, 200, 50);
		add(textInputBox);
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
}
