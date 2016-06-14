package thread;

public class Timer extends Thread {

	private int limitTime;
	private int passTime;
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
	}

	public int preTime() {
		return (passTime / 1000);
	}

	public void run() {
		try {
			passTime = (int) System.currentTimeMillis() - standatdTime;
		} catch (Exception e) {
		}
	}

}
