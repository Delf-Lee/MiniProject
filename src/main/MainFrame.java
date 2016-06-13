package main;

import javax.swing.JFrame;

import panel.PanelManager;
import panel.home.HomePanel;
import user.UserManager;

public class MainFrame extends JFrame {
	// 패널
	public ControllTower controller = new ControllTower(this);
	private PanelManager panel = controller.getPanelManager();
	private MenuBar menuBar = new MenuBar(panel);
	private HomePanel home = panel.getHomePanel();

	// 스태틱 변수
	public static int WIDTH = 1024;
	public static int HEIGHT = 788;
	//public static String FILEROOT = "C:\\Users\\delf\\Desktop"; //상훈
	//public static String FILEROOT = "C:\\Users\\Administrator\\Desktop"; //학교
	public static String FILEROOT = "C:\\Users\\TEST\\Desktop\\JP_ing"; //도연노트북

	/** 생성자 */
	public MainFrame() {
		setWindow(); // 창 설정
		setContentPane(panel.getHomePanel());

		setJMenuBar(menuBar);
		home.add(panel.getLoninPanel()); // Home에 로그인패널 부착
		home.add(panel.getSignupPanel());
		panel.getSignupPanel().setVisible(false);
		panel.setContentPane(home);

		System.out.println(menuBar.getHeight());
		setVisible(true);
	}

	/** 창 설정 */
	private void setWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		setTitle("Pass Me a Tube!");
		setContentPane(panel.getHomePanel());
		setResizable(false); // 창 크기 조절불가 설정
		this.setLayout(null);
	}

	public void backHome() {
		setContentPane(home);
	}

	public void run() {

	}

}