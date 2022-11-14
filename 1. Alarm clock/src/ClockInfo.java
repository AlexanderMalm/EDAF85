import java.util.concurrent.Semaphore;

import clock.io.ClockOutput;

public class ClockInfo {

	private int second, minute, hour;
	private ClockOutput output;
	private boolean alarmStatus = false;
	private int alarmSecond, alarmMinute, alarmHour;
	private Semaphore mutex = new Semaphore(1);

	public ClockInfo(ClockOutput output) {
		this.output = output;

	}

	public void changeAlarmStatus() throws InterruptedException {
		
		mutex.acquire();
		alarmStatus = !alarmStatus;
		output.setAlarmIndicator(alarmStatus);
		mutex.release();
		
	}

	public void setTime(int h, int m, int s) throws InterruptedException {

		mutex.acquire();
		second = s;
		minute = m;
		hour = h;
		mutex.release();
	}

	public void setAlarm(int h, int m, int s) throws InterruptedException {

		mutex.acquire();
		alarmSecond = s;
		alarmMinute = m;
		alarmHour = h;
		mutex.release();

	}

	public boolean checkAlarm() throws InterruptedException {

		if (second == alarmSecond && minute == alarmMinute && hour == alarmHour && alarmStatus) {
			return true;
		}
		return false;
	}

	public void tick() throws InterruptedException {
		
		mutex.acquire();
		if (second == 59) {
			if (minute == 59) {
				hour += 1;
				minute = 0;
				second = 0;
			}
			minute += 1;
			second = 0;
		} else {
			second += 1;
		}

		output.displayTime(hour, minute, second);
		mutex.release();
		// TODO Auto-generated method stub

	}

	public void alarmBlink() {
		output.alarm();
	}

}
