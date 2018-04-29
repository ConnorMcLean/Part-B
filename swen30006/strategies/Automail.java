package strategies;

import java.util.Properties;
import automail.IMailDelivery;
import automail.ProcessProperties;
import automail.Robot;
import automail.StrongRobot;
import automail.WeakRobot;

public class Automail {
	      
    public Robot robot1, robot2;
    public IMailPool mailPool;
    

    public Automail(IMailDelivery delivery, Properties prop) {

    	// Swap between simple provided strategies and your strategies here
    	    	
    	/** Initialize the MailPool */
    	
    	//// Swap the next line for the one below
    	mailPool = new WeakStrongMailPool();
    	
       
    	
    	//// Swap the next two lines for the two below those
    	
    	
    	    	
    	/** Initialize robot */
    	robot1 = ProcessProperties.RobotProps(prop, 1, mailPool, delivery);/* shared behaviour because identical and stateless */
    	robot2 = ProcessProperties.RobotProps(prop, 2, mailPool, delivery);

    }
    
}
