package wash.control;

import static wash.control.WashingMessage.Order.WATER_DRAIN;

import actor.ActorThread;
import wash.io.WashingIO;

public class TemperatureController extends ActorThread<WashingMessage> {
	private WashingIO io;  


	private boolean tempReached = false; 
	private final double mUpper = 0.678;
	private final double mLower = (10 * Math.pow(9.52, -4))+0.2;
	private ActorThread<WashingMessage> sender; 
	
	private int goalTemp; 
	

    public TemperatureController(WashingIO io) {
        this.io = io; 
    }

    @Override
    public void run() {
        // TODO
    	
    	while(true) {

    		try {
    			
    			WashingMessage msg =receiveWithTimeout((10 * 1000) / Settings.SPEEDUP);
				
    			checkTemperature();
				if(msg != null) {
					
					 sender = msg.getSender();
				switch(msg.getOrder()) {
				case TEMP_IDLE:
					io.heat(false);
					goalTemp = 0;
					
					System.out.println("removing heater element \n");
				
					sender.send(new WashingMessage(this, WashingMessage.Order.ACKNOWLEDGMENT));
					
					
				break; 
				
				case TEMP_SET_40:
					
					goalTemp = 40; 
					tempReached = false; 
					break; 
					
				case TEMP_SET_60:
					goalTemp = 60;
					System.out.println("		GOALTEMP SET TO " + goalTemp);

					tempReached = false; 
					break;
				
				default:
					System.out.println("couldn't make sense of " + msg.getOrder());
	    		
				}
				}
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    		
    		
    	}
    }

	private void checkTemperature() throws InterruptedException {

		switch(goalTemp) {
		
		case 40:	
			if(io.getTemperature() < 38+mLower) {

				io.heat(true);
				
			}else if(io.getTemperature() > 39-mUpper) {
				//System.out.println(" Shutting off at: " + (io.getTemperature()));
				io.heat(false);
				
				if(!tempReached) {
					tempReached = true; 
					
						System.out.println("sending acc from temp, goaltemp reached");
						sender.send(new WashingMessage(this, WashingMessage.Order.ACKNOWLEDGMENT));
	            		
					
				}
				
			}
			break; 
			
		case 60:
			if(io.getTemperature() < 58+mLower+0.2) {
				
				io.heat(true);
				//
			}else if(io.getTemperature() > 59- mUpper) {
				io.heat(false);	
				
				if(!tempReached) {
					tempReached = true; 
					
						System.out.println("	SENDING FROM TEMP ---- GOALTEMP REACHED");
						sender.send(new WashingMessage(this, WashingMessage.Order.ACKNOWLEDGMENT));
			}
			
			}
			break; 
			
		default: 
			io.heat(false);
		}
		
		
		
		// TODO Auto-generated method stub

	}
}
