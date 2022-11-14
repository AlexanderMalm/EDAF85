package lift;

public class PassangerThread extends Thread  {
	
	private Monitor monitor; 
	private LiftView view; 
	private int startFloor;
	private int exitFloor; 
	private Passenger passanger; 
	
	
	public PassangerThread(LiftView view, Monitor monitor){
		this.view=view; 
		this.monitor=monitor; 
		passanger = view.createPassenger();
		startFloor = passanger.getStartFloor();
	    exitFloor = passanger.getDestinationFloor();
	   
	    
	}
	
	public void run() {
		passanger.begin();
		try {
			
			monitor.enterLift(startFloor);
			passanger.enterLift();
			
		
			monitor.waitExitLift(startFloor, exitFloor);
			passanger.exitLift();
			sleep(50);	
			//monitor.exitLift();
			
			passanger.end();
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
			
			
		}

}
