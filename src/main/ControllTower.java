package main;

import java.awt.Container;

import javax.swing.JFrame;

import panel.PanelManager;
import thread.GameThread;
import user.UserManager;

public class ControllTower {
	private JFrame main;
	private PanelManager panel;
	private UserManager user;
	private GameThread thread;

	public ControllTower(JFrame main) {
		this.main = main;
		this.panel = new PanelManager(this, main);
		this.user = new UserManager();
		// a¹°À½Ç¥
	}

	public PanelManager getPanelManager() {
		return panel;
	}

	public void getContentPane(Container c) {
		panel.setContentPane(c);
	}

	public void setGameThread(GameThread th) {
		this.thread = th;
	}
}
