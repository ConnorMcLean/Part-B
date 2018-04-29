package automail;

import strategies.IMailPool;
import strategies.IRobotBehaviour;

/**
 * The robot delivers mail!
 */
public class Robot {

	public StorageTube tube;
    IRobotBehaviour behaviour;
    IMailDelivery delivery;
    public final String id;
    /** Possible states the robot can be in */
    public enum RobotState { DELIVERING, WAITING, RETURNING }
    public RobotState current_state;
    public int current_floor;
    public int destination_floor;
    IMailPool mailPool;
    public boolean strong;
    
    public MailItem deliveryItem;
    
    int deliveryCounter;
    

    /**
     * Initiates the robot's location at the start to be at the mailroom
     * also set it to be waiting for mail.
     * @param behaviour governs selection of mail items for delivery and behaviour on priority arrivals
     * @param delivery governs the final delivery
     * @param mailPool is the source of mail items
     * @param strong is whether the robot can carry heavy items
     */
    public Robot(IRobotBehaviour behaviour, IMailDelivery delivery, IMailPool mailPool, boolean strong){
    	id = "R" + hashCode();
        // current_state = RobotState.WAITING;
    	current_state = RobotState.RETURNING;
        current_floor = Building.getMailroom_Location();
        tube = new StorageTube();
        this.behaviour = behaviour;
        this.delivery = delivery;
        this.mailPool = mailPool;
        this.strong = strong;
        this.deliveryCounter = 0;
    }
    
    public Robot(IRobotBehaviour behaviour, IMailDelivery delivery, IMailPool mailPool, boolean strong, int tubeCapacity){
    	id = "R" + hashCode();
        // current_state = RobotState.WAITING;
    	current_state = RobotState.RETURNING;
        current_floor = Building.getMailroom_Location();
        tube = new StorageTube(tubeCapacity);
        this.behaviour = behaviour;
        this.delivery = delivery;
        this.mailPool = mailPool;
        this.strong = strong;
        this.deliveryCounter = 0;
    }
    

}
