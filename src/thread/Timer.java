package thread;

public class Timer extends Thread {

	private int limitTime;
	private int preTime;
	private int standatdTime;
	private int stopTime;
	private int resumeTime;

	public Timer() {
		standatdTime = (int) System.currentTimeMillis();
	}

	public void stopTimer() {
		stopTime = (int) System.currentTimeMillis();
	}

	public void resumeTimer() {
		resumeTime = (int) System.currentTimeMillis();
		//preTime = 
	}

	public int preTime() {
		return (preTime / 1000);
	}

}
