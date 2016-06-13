package panel.game;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;

import panel.BasePanel;
import panel.PanelManager;

public class PausePanel extends BasePanel {
	private PanelManager panel;
	// 레이블
	JLabel stringBoxPause;
	// 버튼
	JButton btnStart;
	JButton btnMenu;
	// 위치 정적변수
	private static final int X = 70;
	private static final int Y = 100;

	public PausePanel(int x, int y, int width, int height, PanelManager panel) {
		super(/*이미지 경로*/);
		this.panel = panel;
		setBounds(x, y, width, height);
		setBackground(Color.CYAN); // 삭제 예정 라인

		setComponent(); // 버튼 세팅
		setListener();
	}
	
	/** 리스너 설정 */
	private void setListener() {
//		btnStart.addMouseListener(new );
//		btnMenu.addMouseListener(new );
	}
	
	/** 컴포넌트 설정 및 배치 */
	private void setComponent() {
		stringBoxPause = new JLabel("Pause", JLabel.CENTER);
		stringBoxPause.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		stringBoxPause.setBounds(100, 10, 200, 100);

		btnStart = new JButton("게임 재개");
		btnStart.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		btnStart.setBounds(100, 50, 200, 100);
		
		btnMenu = new JButton("메뉴");
		btnMenu.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		btnMenu.setBounds(100, 100, 200, 100);
		
		add(stringBoxPause);
		add(btnStart);
		add(btnMenu);
	}

	
	@Override
	public void initPanel() {
		// TODO Auto-generated method stub
		
	}
}
