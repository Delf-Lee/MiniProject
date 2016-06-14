package panel.game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gameObject.item.Item;

public class InfoPanel extends JPanel {
	private static final int[] LVL_LOCATION = { 20, 30 }; // ���� ǥ�� ��ġ
	private static final int[] TIME_LOCATION = { 230, 10 }; // �ð� ǥ�� ��ġ
	private static final int[] ITEM_LOCATION = { 790, 580 }; // ������ ǥ�� ��ġ
	private static final int X = 0; // ��ǥ �ε���
	private static final int Y = 1;

	JLabel levelLabel = new JLabel(); // ���� ǥ��
	JLabel timeLabel = new JLabel(); // �ð� ǥ��
	ImageIcon clock = new ImageIcon("images/�ð�.png");
	JLabel clockLabel = new JLabel(clock);
	JPanel item = new JPanel();

	int level;
	int preTime;
	int limitTime;

	public InfoPanel() {
		setComponent();
		limitTime = 10000;
	}

	/** �г� ���� ������Ʈ ��ġ */
	private void setComponent() {
		setBounds(0, 0, 1018, 740);
		setLayout(null);
		levelLabel = new JLabel();
		levelLabel.setFont(new Font("Silkscreen", Font.BOLD, 40));
		levelLabel.setForeground(Color.WHITE);
		levelLabel.setBounds(LVL_LOCATION[X], LVL_LOCATION[Y], 300, 50);

		clockLabel.setBounds(TIME_LOCATION[X], TIME_LOCATION[Y], clock.getIconWidth(), clock.getIconHeight());

		timeLabel = new JLabel("time " + Integer.toString(preTime));
		timeLabel.setFont(new Font("Silkscreen", Font.BOLD, 40));
		timeLabel.setForeground(Color.WHITE);
		timeLabel.setBounds(TIME_LOCATION[X] + 60, TIME_LOCATION[Y] - 10, 300, 100);

		setItemPanel();
		
		add(levelLabel);
		add(clockLabel);
		add(timeLabel);

		this.setOpaque(false);
	}

	public void setLevel(int lvl) {
		this.level = lvl;
		levelLabel.setText(Integer.toString(level));
	}

	public void setInfo(int lvl, int time) {
		this.preTime = time;
		this.level = lvl;
		levelLabel.setText("level " + Integer.toString(level));
		timeLabel.setText(Integer.toString(time));
	}

	public boolean updateTime() {
		int printTime = limitTime - (((int) System.currentTimeMillis() - preTime) / 1000);
		if (printTime < 0) {
			timeLabel.setText("0");
			return false; // �ð��ʰ�
		}
		timeLabel.setText(Integer.toString(printTime));

		return true;
	}

	private void setItemPanel() {
		item.setBounds(ITEM_LOCATION[X], ITEM_LOCATION[Y], 300, 100);
		item.setLayout(new FlowLayout());
	}

	public void addItem(int type) {
		ImageIcon itemImage = Item.getItemImage(type);
		JLabel item = new JLabel(itemImage);
		item.setSize(itemImage.getIconWidth(), itemImage.getIconHeight());
		item.setLocation(0, 0);
		this.item.add(item);
		System.out.println("�߰���");
	}
}
