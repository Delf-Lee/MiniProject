package user;

public class User {
	public String userName; /* = "user";*/
	private String password;
	private int lastStage; // ������ Ŭ���� ��������
	private int bestScore; // ������ �ְ� ����

	public User() {
	}

	public User(String userName, String password, int lastStage, int bestScore) {
		this.userName = userName;
		this.password = password;
		this.lastStage = lastStage;
		this.bestScore = bestScore;
	}

	public void setBestScore(int score) {
		bestScore = score;
	}

	public void setLastStage(int stage) {
		// �������� �Է� ��� ����ұ�...
		lastStage = stage;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public int getLastStage() {
		return lastStage;
	}

	public int getBestScore() {
		return bestScore;
	}
}

//package tmp;
//
//public class User {
//	public String userName = "user";
//	private int lastStage; // ������ Ŭ���� ��������
//	private int bestScore; // ������ �ְ� ����3
//
//	public void setBestScore(int score) {
//		bestScore = score;
//	}
//
//	public void setLastStage(int stage) {
//		// �������� �Է� ��� ����ұ�...
//		lastStage = stage;
//	}
//
//	public int getBestScore() {
//		return bestScore;
//	}
//
//	public int getLastStage() {
//		return lastStage;
//	}
//}