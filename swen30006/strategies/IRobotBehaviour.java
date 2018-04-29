package strategies;

import automail.Clock;
import automail.Robot;
import automail.Robot.RobotState;
import automail.StorageTube;
import exceptions.ItemTooHeavyException;

public interface IRobotBehaviour {
	
	/**
	 * startDelivery() provides the robot the opportunity to initialise state
	 * in support of the other methods below. 
	 */
	
	public void startDelivery();
	
	/** 
	 * @param tube refers to the pack the robot uses to deliver mail.
	 * @return When this is true, the robot is returned to the mail room.
	 * The robot will always return to the mail room when the tube is empty.
	 * This method allows the robot to return with items still in the tube,
	 * if circumstances make this desirable.
	 */
    public boolean returnToMailRoom(StorageTube tube);
    
    /**
     * @param priority is that of the priority mail item which just arrived.
     * @param weight is that of the same item.
     * The automail system broadcasts this information to all robots
     * when a new priority mail items arrives at the building.
     */
    public void priorityArrival(int priority, int weight);

	/**
	 * Sets the route for the robot
	 * @param robot TODO
	 */
	public default void setRoute(Robot robot) throws ItemTooHeavyException{
	    /** Pop the item from the StorageUnit */
	    robot.deliveryItem = robot.tube.pop();
	    if (!robot.strong && robot.deliveryItem.weight > 2000) throw new ItemTooHeavyException(); 
	    /** Set the destination floor */
	    robot.destination_floor = robot.deliveryItem.getDestFloor();
	}

	/**
	 * Generic function that moves the robot towards the destination
	 * @param robot TODO
	 * @param destination the floor towards which the robot is moving
	 */
	public default void moveTowards(Robot robot, int destination){
	    if(robot.current_floor < destination){
	        robot.current_floor++;
	    }
	    else{
	        robot.current_floor--;
	    }
	}

	/**
	 * Prints out the change in state
	 * @param robot TODO
	 * @param nextState the state to which the robot is transitioning
	 */
	public default void changeState(Robot robot, RobotState nextState){
		if (robot.current_state != nextState) {
	        System.out.printf("T: %3d > %11s changed from %s to %s%n", Clock.Time(), robot.id, robot.current_state, nextState);
		}
		robot.current_state = nextState;
		if(nextState == RobotState.DELIVERING){
	        System.out.printf("T: %3d > %11s-> [%s]%n", Clock.Time(), robot.id, robot.deliveryItem.toString());
		}
	}
    
}
