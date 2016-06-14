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
	// user 벡터
	public static Vector<User> userList = new Vector<User>();
	public static User user = new User();
	private PanelManager panel;
	// 파일 읽기
	private static FileWriter fout = null;
	private FileReader fin = null;

	// UserManager생성하면 파일 읽어 vector에 userInfo 추가
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
			System.out.println("입출력 오류");
			System.exit(1);
		}
	}

	// vector score 내림차순
	public static void sortUserList() {
		Collections.sort(userList, new NoDescCompare());
	}

	static class NoDescCompare implements Comparator<User> {
		/**
		 * 내림차순(DESC)
		 */
		@Override
		public int compare(User arg0, User arg1) {
			// TODO Auto-generated method stub
			return arg0.getBestScore() > arg1.getBestScore() ? -1 : arg0.getBestScore() < arg1.getBestScore() ? 1 : 0;
		}

	}

	//   public static boolean removeUser(String ID, String pwd) {
	//      /* 위와 동일 */
	//      return true;
	//   }
	//
	//   public static boolean confirmUser(String ID, String pwd) {
	//      /* 생성된 해시값과 DB의 해시 값 비교 후, 접속 승인 */
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
		/* 파일에 쓰기 */
		try {
			fout = new FileWriter(MainFrame.FILEROOT + "\\UserInfo.txt"); // 경로 수정!
			for (int i = 0; i < userList.size(); i++) {
				fout.write(userList.get(i).getUserName() + " " + userList.get(i).getPassword() + " " + userList.get(i).getLastStage() + " "
						+ userList.get(i).getBestScore() + "\n"); // ID PW stage score
			}
			fout.close();
		} catch (IOException e) {
			System.out.println("입출력 오류 writeUserInfo()");
			System.exit(1);
		}
	}

	/** 아이디 공백, 아이디와 비밀번호 일치 확인 */
	public static boolean acceptUser(String inputID, String password) {
		// 책갈피: UserManager 수정
		if (inputID.length() == 0) {
			MsgWinow.error("아이디를 입력하세요");
			
			return false;
		}
		/* 이부분에 아이디 중복체크 대신에 아이디에 맞는 패스워드 대조 */
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getUserName().equals(inputID)) {
				User tmpUser = userList.get(i);
				if (tmpUser.getPassword().equals(password)) {
					user = tmpUser; // 너로 정했다!
					System.out.println("로그인 승인됨. 접속유저: " + user.getUserName());
					return true;
				}
				else if (password.length() == 0) {
					MsgWinow.error("비밀번호를 입력하세요.");
					return false; // 패스워드 공백
				}
				else {
					MsgWinow.error("아이디와 비밀번호가 일치하지 않습니다.");
					return false;
				}
			}
		}
		if (password.length() == 0) {
			MsgWinow.error("비밀번호를 입력하세요.");
			return false; // 패스워드 공백
		}
		MsgWinow.error("존재하지 않는 아이디입니다.");
		return false;
	}

	public static boolean checkID(String inputID) {
		// 길이 체크
		if (inputID.length() == 0) {
			MsgWinow.error("아이디를 입력하세요");
			return false;
		}

		// 아이디 중복 체크
		for (int i = 0; i < UserManager.userList.size(); i++) {
			if (UserManager.userList.get(i).getUserName().equals(inputID)) {
				MsgWinow.error("이미 존재하는 아이디입니다.");
				return false;
			}
		}
		return true;
	}

	// 패스워드 체킹
	public static boolean checkPassword(String inputPassWord, String inputPassWordConfirm) {
		if (inputPassWord.length() == 0 || inputPassWordConfirm.length() == 0) {
			MsgWinow.error("비밀번호를 입력하세요");
			return false; // 패스워드 공백
		}
		else if (!inputPassWord.equals(inputPassWordConfirm)) {
			MsgWinow.error("비밀번호가 일치하지 않습니다.");
			return false; /// 패스워드 불일치
		}
		return true;
	}

}
//package tmp;
//
//public class UserManager {
//	/* 임시 클래스 
//	 * 삭제 가능성 있음*/
//
//	public static boolean createUser(String ID, String pwd) {
//		/* 해시함수로 인코딩 후 DB에 저장 */
//		/* 사용자 DB는 경로를 직접 설정하여 생성 */
//		return true;
//	}
//
//	public static boolean removeUser(String ID, String pwd) {
//		/* 위와 동일 */
//		return true;
//	}
//
//	public static boolean confirmUser(String ID, String pwd) {
//		/* 생성된 해시값과 DB의 해시 값 비교 후, 접속 승인 */
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
//		/* 파일에 쓰기 */
//		return true;
//	}
//}
