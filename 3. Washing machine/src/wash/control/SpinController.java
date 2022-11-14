package wash.control;

import actor.ActorThread;
import wash.io.WashingIO;

public class SpinController extends ActorThread<WashingMessage> {
	private WashingIO io; 
	private int timeToSpin; 
	private int dirr; // 0 = noSpin 1 - left 2 - right
	//private Boolean washing = false; 
	private ActorThread<WashingMessage> sender;
    // TODO: add attributes
	boolean spinFast = false; 

    public SpinController(WashingIO io) {
        this.io = io; 
    }

    @Override
    public void run() {
        try {

            // ... TODO ...

            while (true) {

            	/*
            	 * 
            	 WashingMessage msg = receiveWithTimeout(60000 / Settings.SPEEDUP); 
            	 */
                // wait for up to a (simulated) minute for a WashingMessage
            	WashingMessage msg = receiveWithTimeout((60 * 1000) / Settings.SPEEDUP); 
                
                // if m is null, it means a minute passed and no message was received
            	
                if (msg != null) {
                    System.out.println("got " + msg);
                     sender = msg.getSender();
                     
                     
                     switch(msg.getOrder()) {
               
                	case SPIN_SLOW:

                		dirr = 1;
                		timeToSpin = 5;

                		io.setSpinMode(2); 
                		timeToSpin--;

                		sender.send(new WashingMessage(this, WashingMessage.Order.ACKNOWLEDGMENT));

                		
                		break;
                		
                	case SPIN_FAST:
                		io.setSpinMode(4);
                		spinFast = true; 
                		sender.send(new WashingMessage(this, WashingMessage.Order.ACKNOWLEDGMENT));
                		
                		
                		break;
                		
                	case SPIN_OFF:
                		io.setSpinMode(1);
                		timeToSpin = 0; 
                		System.out.println("sending acc from spin - spin off");

                		sender.send(new WashingMessage(this, WashingMessage.Order.ACKNOWLEDGMENT));
                		
                		break;
                		
                	default: 
                		System.out.println("oops");
                		break;
                
                	}
                }
                else if((msg == null&&timeToSpin > 0)&&!spinFast) {
                	
                
                	
                	if(dirr==1) {
                		dirr = 2; 
                	//	System.out.println(timeToSpin + "times to spin dirr1");

                		timeToSpin--;

                		io.setSpinMode(3);
                  
                		
                	} else if(dirr==2) {
                		dirr = 1; 
                	//	System.out.println(timeToSpin + "times to spin dirr2");

                		timeToSpin--;

                		io.setSpinMode(2); 
                		
                	
                	}
                
            	
                } if(timeToSpin == 0) {
                	timeToSpin = 5;
                }
            }
        } catch (InterruptedException unexpected) {
            // we don't expect this thread to be interrupted,
            // so throw an error if it happens anyway
    		

            throw new Error(unexpected);
        }
    }
}
