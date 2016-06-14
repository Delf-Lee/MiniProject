package user;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.Vector;

import main.MainFrame;
import panel.*;

public class UserManager {
	// user ����
	public static Vector<User> userList = new Vector<User>();
	public static User user = new User();
	private PanelManager panel;
	// ���� �б�
	private static FileWriter fout = null;
	private FileReader fin = null;

	// UserManager�����ϸ� ���� �о� vector�� userInfo �߰�
	public UserManager() {
		try {
			fin = new FileReader(MainFrame.FILEROOT + "\\UserInfo.txt");
			BufferedReader reader = new BufferedReader(fin);
			String userInfo = null;
			while ((userInfo = reader.readLine()) != null) {
				StringTokenizer token = new StringTokenizer(userInfo, " ");
				String ID = token.nextToken();
				String PW = token.nextToken();
				int stage = Integer.parseInt(token.nextToken());
				int score = Integer.parseInt(token.nextToken());
				userList.add(new User(ID, PW, stage, score));
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("����� ����");
			System.exit(1);
		}
	}

	// vector score ��������
	public static void sortUserList() {
		Collections.sort(userList, new NoDescCompare());
	}

	static class NoDescCompare implements Comparator<User> {
		/**
		 * ��������(DESC)
		 */
		@Override
		public int compare(User arg0, User arg1) {
			// TODO Auto-generated method stub
			return arg0.getBestScore() > arg1.getBestScore() ? -1 : arg0.getBestScore() < arg1.getBestScore() ? 1 : 0;
		}

	}

	//   public static boolean removeUser(String ID, String pwd) {
	//      /* ���� ���� */
	//      return true;
	//   }
	//
	//   public static boolean confirmUser(String ID, String pwd) {
	//      /* ������ �ؽð��� DB�� �ؽ� �� �� ��, ���� ���� */
	//      return true;
	//   }
	//   
	//   public static boolean saveBestScore(User user, int score) {
	//      user.setBestScore(score);
	//      saveUserData(user);
	//      return true;
	//   }
	//   
	//   public static boolean saveLastStage(User user, int stage) {
	//      user.setLastStage(stage);
	//      saveUserData(user);
	//      return true;
	//   }

	public static void saveUserData() {
		/* ���Ͽ� ���� */
		try {
			fout = new FileWriter(MainFrame.FILEROOT + "\\UserInfo.txt"); // ��� ����!
			for (int i = 0; i < userList.size(); i++) {
				fout.write(userList.get(i).getUserName() + " " + userList.get(i).getPassword() + " " + userList.get(i).getLastStage() + " "
						+ userList.get(i).getBestScore() + "\n"); // ID PW stage score
			}
			fout.close();
		} catch (IOException e) {
			System.out.println("����� ���� writeUserInfo()");
			System.exit(1);
		}
	}

	/** ���̵� ����, ���̵�� ��й�ȣ ��ġ Ȯ�� */
	public static boolean acceptUser(String inputID, String password) {
		// å����: UserManager ����
		if (inputID.length() == 0) {
			MsgWinow.error("���̵� �Է��ϼ���");
			
			return false;
		}
		/* �̺κп� ���̵� �ߺ�üũ ��ſ� ���̵� �´� �н����� ���� */
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getUserName().equals(inputID)) {
				User tmpUser = userList.get(i);
				if (tmpUser.getPassword().equals(password)) {
					user = tmpUser; // �ʷ� ���ߴ�!
					System.out.println("�α��� ���ε�. ��������: " + user.getUserName());
					return true;
				}
				else if (password.length() == 0) {
					MsgWinow.error("��й�ȣ�� �Է��ϼ���.");
					return false; // �н����� ����
				}
				else {
					MsgWinow.error("���̵�� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
					return false;
				}
			}
		}
		if (password.length() == 0) {
			MsgWinow.error("��й�ȣ�� �Է��ϼ���.");
			return false; // �н����� ����
		}
		MsgWinow.error("�������� �ʴ� ���̵��Դϴ�.");
		return false;
	}

	public static boolean checkID(String inputID) {
		// ���� üũ
		if (inputID.length() == 0) {
			MsgWinow.error("���̵� �Է��ϼ���");
			return false;
		}

		// ���̵� �ߺ� üũ
		for (int i = 0; i < UserManager.userList.size(); i++) {
			if (UserManager.userList.get(i).getUserName().equals(inputID)) {
				MsgWinow.error("�̹� �����ϴ� ���̵��Դϴ�.");
				return false;
			}
		}
		return true;
	}

	// �н����� üŷ
	public static boolean checkPassword(String inputPassWord, String inputPassWordConfirm) {
		if (inputPassWord.length() == 0 || inputPassWordConfirm.length() == 0) {
			MsgWinow.error("��й�ȣ�� �Է��ϼ���");
			return false; // �н����� ����
		}
		else if (!inputPassWord.equals(inputPassWordConfirm)) {
			MsgWinow.error("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			return false; /// �н����� ����ġ
		}
		return true;
	}

}
//package tmp;
//
//public class UserManager {
//	/* �ӽ� Ŭ���� 
//	 * ���� ���ɼ� ����*/
//
//	public static boolean createUser(String ID, String pwd) {
//		/* �ؽ��Լ��� ���ڵ� �� DB�� ���� */
//		/* ����� DB�� ��θ� ���� �����Ͽ� ���� */
//		return true;
//	}
//
//	public static boolean removeUser(String ID, String pwd) {
//		/* ���� ���� */
//		return true;
//	}
//
//	public static boolean confirmUser(String ID, String pwd) {
//		/* ������ �ؽð��� DB�� �ؽ� �� �� ��, ���� ���� */
//		return true;
//	}
//	
//	public static boolean saveBestScore(User user, int score) {
//		user.setBestScore(score);
//		saveUserData(user);
//		return true;
//	}
//	
//	public static boolean saveLastStage(User user, int stage) {
//		user.setLastStage(stage);
//		saveUserData(user);
//		return true;
//	}
//
//	public static boolean saveUserData(User user) {
//		/* ���Ͽ� ���� */
//		return true;
//	}
//}
