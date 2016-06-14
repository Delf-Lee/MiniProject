package thread;

public class Timer extends Thread {

	private int limitTime;
	private int passTime;
	private int standatdTime;
	private int stopTime;
	private int idealTime;

	public Timer() {
	}

	public Timer(int limitTime) {
		this.limitTime = limitTime * 1000;
		start();
	}

	public void setLimitTime(int limitTime) {
		this.limitTime = limitTime * 1000;
	}

	public void stopTimer() {
		stopTime = (int) System.currentTimeMillis();
	}

	public void resumeTimer() {
		int resumeTime = (int) System.currentTimeMillis();
		idealTime += resumeTime - stopTime;
	}

	public int getPassTime() {
		return passTime;
	}

	public int getRemainTime() {
		return (limitTime - passTime);
	}

	public boolean isTerminate() {
		if (getRemainTime() < 0) {
			return true;
		}
		return false;
	}

	public void run() {
		try {
			standatdTime = (int) System.currentTimeMillis();
			while (true) {
				passTime = (int) System.currentTimeMillis() - standatdTime - idealTime;
				sleep(100);
			}
		} catch (Exception e) {
		}
	}
}
