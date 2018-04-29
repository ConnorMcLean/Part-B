package automail;

import automail.Robot.RobotState;
import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;


/**
 * a MailDelivery is used by the Robot to deliver mail once it has arrived at the correct location
 */
public interface IMailDelivery {


/**

	/**

     * Delivers an item at its floor
     * @param mailItem the mail item being delivered.
     */
	void deliver(MailItem mailItem);


	/**
	 * This is called on every time step
	 * @param robot TODO
	 * @throws ExcessiveDeliveryException if robot delivers more than the capacity of the tube without refilling
	 */
	default void step(Robot robot) throws ExcessiveDeliveryException, ItemTooHeavyException{
		
		switch(robot.current_state) {
			/** This state is triggered when the robot is returning to the mailroom after a delivery */
			case RETURNING:
				/** If its current position is at the mailroom, then the robot should change state */
	            if(robot.current_floor == Building.getMailroom_Location()){
	            	while(!robot.tube.isEmpty()) {
	            		MailItem mailItem = robot.tube.pop();
	            		robot.mailPool.addToPool(mailItem);
	                    System.out.printf("T: %3d > old addToPool [%s]%n", Clock.Time(), mailItem.toString());
	            	}
	            	robot.behaviour.changeState(robot, RobotState.WAITING);
	            } else {
	            	/** If the robot is not at the mailroom floor yet, then move towards it! */
	                robot.behaviour.moveTowards(robot, Building.getMailroom_Location());
	            	break;
	            }
			case WAITING:
				/** Tell the sorter the robot is ready */
				robot.mailPool.fillStorageTube(robot.tube, robot.strong);
	            // System.out.println("Tube total size: "+tube.getTotalOfSizes());
	            /** If the StorageTube is ready and the Robot is waiting in the mailroom then start the delivery */
	            if(!robot.tube.isEmpty()){
	            	robot.deliveryCounter = 0; // reset delivery counter
	    			robot.behaviour.startDelivery();
	    			robot.behaviour.setRoute(robot);
	            	robot.behaviour.changeState(robot, RobotState.DELIVERING);
	            }
	            break;
			case DELIVERING:
				/** Check whether or not the call to return is triggered manually **/
				boolean wantToReturn = robot.behaviour.returnToMailRoom(robot.tube);
				if(robot.current_floor == robot.destination_floor){ // If already here drop off either way
	                /** Delivery complete, report this to the simulator! */
	                deliver(robot.deliveryItem);
	                robot.deliveryCounter++;
	                if(robot.deliveryCounter > robot.tube.MAXIMUM_CAPACITY){
	                	throw new ExcessiveDeliveryException();
	                }
	                /** Check if want to return or if there are more items in the tube*/
	                if(wantToReturn || robot.tube.isEmpty()){
	                // if(tube.isEmpty()){
	                	robot.behaviour.changeState(robot, RobotState.RETURNING);
	                }
	                else{
	                    /** If there are more items, set the robot's route to the location to deliver the item */
	                    robot.behaviour.setRoute(robot);
	                    robot.behaviour.changeState(robot, RobotState.DELIVERING);
	                }
				} else
				{/*
	    			if(wantToReturn){
	    				// Put the item we are trying to deliver back
	    				try {
							tube.addItem(deliveryItem);
						} catch (TubeFullException e) {
							e.printStackTrace();
						}
	    				changeState(RobotState.RETURNING);
	    			}
	    			else{*/
	        			/** The robot is not at the destination yet, move towards it! */
	                        robot.behaviour.moveTowards(robot, robot.destination_floor);
	                /*
	    			}
	    			*/
				}
	            break;
		}
	}   
}