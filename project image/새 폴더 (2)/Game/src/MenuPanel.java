import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuPanel extends JPanel {
	MainApp pane;
	
	public MenuPanel(MainApp mf) {
		pane = mf;
		setBackground(Color.CYAN);
		this.setLayout(null);
		
		JButton btn = new JButton("�����ϱ�");
		btn.setFont(new Font("����������", Font.BOLD, 30));
		btn.setBounds(400, 200, 200, 70);
		
		JButton btn2 = new JButton("�ܾ��Է�");
		btn2.setFont(new Font("���� ���", Font.BOLD, 30));
		btn2.setBounds(400, 300, 200, 70);
		
		JButton btn3 = new JButton("��ŷ����");
		btn3.setFont(new Font("���� ���", Font.BOLD, 30));
		btn3.setBounds(400, 400, 200, 70);
		
		JButton btn4 = new JButton("�α׾ƿ�");
		btn4.setFont(new Font("���� ���", Font.BOLD, 30));
		btn4.setBounds(400, 500, 200, 70);
		
		add(btn);
		add(btn2);
		add(btn3);
		add(btn4);
		
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pane.setContentPane(pane.mainPanel);
			}
		});
	}
}
