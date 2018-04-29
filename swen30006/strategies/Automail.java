package strategies;

import automail.IMailDelivery;
import automail.Robot;
import automail.StrongRobot;
import automail.WeakRobot;

public class Automail {
	      
    public Robot robot1, robot2;
    public IMailPool mailPool;
    
    public Automail(IMailDelivery delivery) {
    	// Swap between simple provided strategies and your strategies here
    	    	
    	/** Initialize the MailPool */
    	
    	//// Swap the next line for the one below
    	mailPool = new WeakStrongMailPool();
    	
        /** Initialize the RobotAction */
    	boolean weak = false;  // Can't handle more than 2000 grams
    	boolean strong = true; // Can handle any weight that arrives at the building
    	
    	//// Swap the next two lines for the two below those
    	IRobotBehaviour robotBehaviourW = new MyRobotBehaviour(weak);
    	IRobotBehaviour robotBehaviourS = new MyRobotBehaviour(strong);
    	    	
    	/** Initialize robot */
    	robot1 = new WeakRobot(robotBehaviourW, delivery, mailPool, weak,10); /* shared behaviour because identical and stateless */
    	robot2 = new StrongRobot(robotBehaviourS, delivery, mailPool, weak,10);
    }
    
}
