package main;

import javax.swing.JFrame;

import panel.PanelManager;
import panel.home.HomePanel;
import user.UserManager;

public class MainFrame extends JFrame {
	// �г�
	public ControllTower controller = new ControllTower(this);
	private PanelManager panel = controller.getPanelManager();
	private MenuBar menuBar = new MenuBar(panel);
	private HomePanel home = panel.getHomePanel();

	// ����ƽ ����
	public static int WIDTH = 1024;
	public static int HEIGHT = 788;
	//public static String FILEROOT = "C:\\Users\\delf\\Desktop"; //����
	//public static String FILEROOT = "C:\\Users\\Administrator\\Desktop"; //�б�
	public static String FILEROOT = "C:\\Users\\TEST\\Desktop\\JP_ing"; //������Ʈ��

	/** ������ */
	public MainFrame() {
		setWindow(); // â ����
		setContentPane(panel.getHomePanel());

		setJMenuBar(menuBar);
		home.add(panel.getLoninPanel()); // Home�� �α����г� ����
		home.add(panel.getSignupPanel());
		panel.getSignupPanel().setVisible(false);
		panel.setContentPane(home);

		System.out.println(menuBar.getHeight());
		setVisible(true);
	}

	/** â ���� */
	private void setWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		setTitle("Pass Me a Tube!");
		setContentPane(panel.getHomePanel());
		setResizable(false); // â ũ�� �����Ұ� ����
		this.setLayout(null);
	}

	public void backHome() {
		setContentPane(home);
	}

	public void run() {

	}

}