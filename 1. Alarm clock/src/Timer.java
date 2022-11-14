
public class Timer extends Thread {
	ClockInfo clockInfo;
//	private TimeUnit time = TimeUnit.MILLISECONDS;
	long t, diff;
	int alarmBlink = 0;

	public Timer(ClockInfo info) {
		clockInfo = info;
	}

	public void run() {
		t = System.currentTimeMillis();

		while (true) {
			t += 1000;

			diff = t - System.currentTimeMillis();

			if (diff > 0) {
				try {

					Thread.sleep(diff);
					clockInfo.tick();

					if (clockInfo.checkAlarm()) {
						alarmBlink = 20;
					}
					if (alarmBlink > 0) {
						clockInfo.alarmBlink();
						alarmBlink--;
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
