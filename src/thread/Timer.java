package thread;

public class Timer extends Thread {

	private int limitTime;
	private int passTime;
	private int standatdTime;
	private int stopTime;
	private int idealTime;
	private int resumeTime;

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
		resumeTime = (int) System.currentTimeMillis();
		idealTime += (resumeTime - stopTime);
		System.out.println(idealTime);
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

	public void plusTime(int n) {
		idealTime += n * 1000;
	}

	public void run() {
		try {
			standatdTime = (int) System.currentTimeMillis();
			while (true) {
				passTime = (int) System.currentTimeMillis() - standatdTime - idealTime;
				//System.out.println(getRemainTime() / 1000);
				sleep(100);
			}
		} catch (Exception e) {
		}
	}
}
