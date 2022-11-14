import lift.LiftThread;
import lift.LiftView;
import lift.Monitor;
import lift.PassangerThread;
import lift.Passenger;

public class MultiPassangersRideLift {

	
    public static void main(String[] args) {

        final int NBR_FLOORS = 7, MAX_PASSENGERS = 4;

        LiftView  view = new LiftView(NBR_FLOORS, MAX_PASSENGERS);
        Monitor monitor = new Monitor(view, MAX_PASSENGERS);
        
        LiftThread lift = new LiftThread(view, monitor, NBR_FLOORS);
        int n = 0; 
        
       
        lift.start();
        while(n < 20) {
        	PassangerThread passanger = new PassangerThread(view, monitor);
        	passanger.start(); 
        	
        	n++;
        	
        }
        
    }
}