package lift;

public class Monitor  {
	private LiftView view; 
	
	private int currentFloor = 0; 
	private int[] toEnter = new int[7];
	private int[] toExit = new int[7];
 
	private int finished = 0; 
	private int newPickup = 0;
	private int maxPassangers; 
	private int passangersInLift;
	
 
	private Boolean liftMoving = true; 
	private Boolean doorsOpen = false; 
	private int concMoving = 0; 
	
	//private LiftThread lift;
	
	 
	public Monitor(LiftView view, int maxPassangers) {
	this.view= view; 
	this.maxPassangers = maxPassangers;
	
	//this.lift=lift; 
		
	}
	

public synchronized void moveLift() throws InterruptedException {
	
	
	while(((toEnter[currentFloor] > 0 && passangersInLift != 4 ) || toExit[currentFloor] > 0  ) || morePassangers()) {
		
		liftMoving = false;
		
		if(!doorsOpen) {
			view.openDoors(currentFloor);
			doorsOpen = !doorsOpen;
		}
		wait();
	}
	
	if(doorsOpen) {
		view.closeDoors();
		doorsOpen = !doorsOpen;
	}
	
	liftMoving = true;
}
	
	public synchronized void enterLift(int startFloor) throws InterruptedException {
		
		toEnter[startFloor]++;
		newPickup++;
		notifyAll();
		
		while(currentFloor != startFloor || !canEnter()) {
		wait();
			
		}
		
		concMoving++;
		passangersInLift++;
		newPickup--;
		
	}
 

public synchronized void waitExitLift(int startFloor, int exitFloor) throws InterruptedException {
	
	
	concMoving--; 
	
	 toEnter[startFloor]--;
	 toExit[exitFloor]++;
	 
	 notifyAll();
	
		while(exitFloor != currentFloor || liftMoving) {
			wait();
		}
		
		passangersInLift--;
		toExit[exitFloor]--;
		finished++;
		notifyAll();
}

public synchronized void exitLift() throws InterruptedException {
	concMoving--; 
	
	
	
	finished++;
	notifyAll();
	
}

public  synchronized void setCurrentFloor(int floor) {
currentFloor = floor;
notifyAll(); 

}

private boolean canEnter() {
	
	if( concMoving < 4 && passangersInLift  < 4  && !liftMoving)  {
	return true; 
}
return false; 
}


private Boolean morePassangers() {
	if((newPickup != 0 && finished < 20) || passangersInLift >0) {
	return false; 
	}
	return true; 
}
}
