import clock.AlarmClockEmulator;
import java.util.concurrent.Semaphore;
import clock.io.ClockInput;
import clock.io.ClockInput.UserInput;
import clock.io.ClockOutput;

public class ClockMain {

	public static void main(String[] args) throws InterruptedException {
		AlarmClockEmulator emulator = new AlarmClockEmulator();

		ClockInput in = emulator.getInput();
		ClockOutput out = emulator.getOutput();
		ClockInfo clockInfo = new ClockInfo(out);

		Timer clock = new Timer(clockInfo);

		clock.start();

		while (true) {

			in.getSemaphore().acquire();
			UserInput userInput = in.getUserInput();

			int choice = userInput.getChoice();
			int h = userInput.getHours();
			int m = userInput.getMinutes();
			int s = userInput.getSeconds();

			switch (choice) {
			case 1:
				clockInfo.setTime(h, m, s);

				break;
			case 2:
				clockInfo.setAlarm(h, m, s);

				break;
			case 3:
				clockInfo.changeAlarmStatus();

			default:

			}
			System.out.println("choice=" + choice + " h=" + h + " m=" + m + " s=" + s);

		}
	}

}
