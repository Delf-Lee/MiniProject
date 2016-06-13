package panel.home;

import panel.BasePanel;
import panel.PanelManager;

public class HomePanel extends BasePanel {

	private PanelManager panel;
	private LoginPanel login;
	private SignupPanel signup;

	public HomePanel(PanelManager panel) {
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