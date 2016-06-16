package user;

public class User {
	public String userName; /* = "user";*/
	private String password;
	private int lastStage; // 마지막 클리어 스테이지
	private int bestScore; // 유저의 최고 점수

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
		// 스테이지 입력 제어를 어디서할까...
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
//	private int lastStage; // 마지막 클리어 스테이지
//	private int bestScore; // 유저의 최고 점수3
//
//	public void setBestScore(int score) {
//		bestScore = score;
//	}
//
//	public void setLastStage(int stage) {
//		// 스테이지 입력 제어를 어디서할까...
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