package wash.control;

import actor.ActorThread;

/**
 * Class used for messaging
 * - from washing programs to spin controller (SPIN_xxx)
 * - from washing programs to temperature controller (TEMP_xxx)
 * - from washing programs to water controller (WATER_xxx)
 * - from controllers to washing programs (ACKNOWLEDGMENT)
 */
public class WashingMessage {

    // possible values for the 'order' attribute
    public enum Order {
        SPIN_OFF,		//spincontroller						from- washingProgramX	
        SPIN_SLOW,		//spincontroller								-||-
        SPIN_FAST,		//spincontroller
        TEMP_IDLE,		//temperaturecontroller
        TEMP_SET_40,	//temperaturecontroller		
        TEMP_SET_60,	//temperaturecontroller
        WATER_IDLE,		//waterController
        WATER_FILL,		//waterController
        WATER_DRAIN,	//waterController								-||-
        ACKNOWLEDGMENT
    }

    private final ActorThread<WashingMessage> sender;
    private final Order order;

    /**
     * @param sender   the thread that sent the message
     * @param order    an order, such as SPIN_FAST or WATER_DRAIN
     */
    public WashingMessage(ActorThread<WashingMessage> sender, Order order) {
        this.sender = sender;
        this.order = order;
    }
    
    public ActorThread<WashingMessage> getSender() {
        return sender;
    }
    
    public Order getOrder() {
        return order;
    }
    
    public String toString() {
        return new StringBuilder(order.toString())
                .append(" from ")
                .append(sender == null ? "(null)" : sender.getClass().getSimpleName())
                .toString();
    }
}
