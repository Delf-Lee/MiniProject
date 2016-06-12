package panel.home;

import panel.BasePanel;
import panel.PanelManager;

public class PanelHome extends BasePanel {

	private PanelManager panel;
	private PanelLogin login;
	private PanelSignup signup;

	public PanelHome(PanelManager panel) {
		super(/*¿ÃπÃ¡ˆ*/);
		setLocation(0, 25);
		this.panel = panel;
		login = panel.getLoninPanel();
		signup = panel.getSignupPanel();
	}

	@Override
	public void initPanel() {
		login.initPanel();
		signup.initPanel();
	}
}