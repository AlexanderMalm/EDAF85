package lift;

public class LiftThread extends Thread{
	private LiftView view; 
	private int nbrFloors; 
	private Monitor monitor;
	private int currentFloor = 0;
	private boolean goUp = true; 
	
	public LiftThread(LiftView view, Monitor monitor, int nBR_FLOORS) {
		this.nbrFloors = nBR_FLOORS;
		this.view=view; 
		this.monitor = monitor;
	}
	
	
	public void run() {
		
		
		while(true) {
			
			try {
				monitor.moveLift();
				view.moveLift(currentFloor, nextFloor());
				monitor.setCurrentFloor(currentFloor) ;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
	
	private int nextFloor() {
		int nextFloor = 0; 
		
		if(goUp && (currentFloor==nbrFloors-1)) {
			
			goUp = false; 
			
		}
		else if(!goUp && (currentFloor==0)){
			goUp = true;
		}
		
		if(goUp) {
			nextFloor = ++currentFloor%nbrFloors;
			
		} else if( !goUp) {
			nextFloor = --currentFloor%nbrFloors;
		}
		
		return nextFloor;
		
	}
	
	
	

}
