package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;
import exceptions.MailAlreadyDeliveredException;
import strategies.Automail;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import java.util.Enumeration;


/**
 * This class simulates the behaviour of AutoMail
 */
public class Simulation {

    /** Constant for the mail generator */

    private static int MAIL_TO_CREATE;
    private static double PENALTY;


    private static ArrayList<MailItem> MAIL_DELIVERED;
    private static double total_score = 0;


    public static void main(String[] args) throws IOException { 
    	// Should probably be using properties here
    	Properties automailProperties = new Properties();
		// Defaults
		automailProperties.setProperty("Name_of_Property", "20");  // Property value may need to be converted from a string to the appropriate type

		FileReader inStream = null;
		
		try {
			inStream = new FileReader("automail.properties");
			automailProperties.load(inStream);

		} finally {
			 if (inStream != null) {
	                inStream.close();
	            }
		}
		

		
        /** Used to see whether a seed is initialized or not */
		HashMap<Boolean, Integer> seedMap = new HashMap<>();
		
		//Process properties file, if fields are empty set to default
		seedMap = ProcessProperties.SeedProp(automailProperties, args);
		ProcessProperties.FloorProp(automailProperties);
		PENALTY = ProcessProperties.DeliveryProp(automailProperties);
		ProcessProperties.TimeProp(automailProperties);
		MAIL_TO_CREATE = ProcessProperties.MailProp(automailProperties);
		
		
		
        MAIL_DELIVERED = new ArrayList<MailItem>();     
        
        /** Read the first argument and save it as a seed if it exists */
        
        Automail automail = new Automail(new ReportDelivery(), automailProperties);

        MailGenerator generator = new MailGenerator(MAIL_TO_CREATE, automail.mailPool, seedMap);
        
        /** Initiate all the mail */
        generator.generateAllMail();
        PriorityMailItem priority;
        while(MAIL_DELIVERED.size() != generator.MAIL_TO_CREATE) {
        	//System.out.println("-- Step: "+Clock.Time());
            priority = generator.step();
            if (priority != null) {
            	automail.robot1.behaviour.priorityArrival(priority.getPriorityLevel(), priority.weight);
            	automail.robot2.behaviour.priorityArrival(priority.getPriorityLevel(), priority.weight);
            }
            try {

				automail.robot1.step();
				automail.robot2.step();

			} catch (ExcessiveDeliveryException|ItemTooHeavyException e) {
				e.printStackTrace();
				System.out.println("Simulation unable to complete.");
				System.exit(0);
			}
            Clock.Tick();
        }
        printResults();
    }
    
    static class ReportDelivery implements IMailDelivery {
    	
    	/** Confirm the delivery and calculate the total score */
    	public void deliver(MailItem deliveryItem){
    		if(!MAIL_DELIVERED.contains(deliveryItem)){
                System.out.printf("T: %3d > Delivered     [%s]%n", Clock.Time(), deliveryItem.toString());
    			MAIL_DELIVERED.add(deliveryItem);
    			// Calculate delivery score
    			total_score += calculateDeliveryScore(deliveryItem);
    		}
    		else{
    			try {
    				throw new MailAlreadyDeliveredException();
    			} catch (MailAlreadyDeliveredException e) {
    				e.printStackTrace();
    			}
    		}
    	}

    }
    
    private static double calculateDeliveryScore(MailItem deliveryItem) {
    	// Penalty for longer delivery times

    	double priority_weight = 0;
        // Take (delivery time - arrivalTime)**penalty * (1+sqrt(priority_weight))
    	if(deliveryItem instanceof PriorityMailItem){
    		priority_weight = ((PriorityMailItem) deliveryItem).getPriorityLevel();
    	}
        return Math.pow(Clock.Time() - deliveryItem.getArrivalTime(),PENALTY)*(1+Math.sqrt(priority_weight));

    }

    public static void printResults(){
        System.out.println("T: "+Clock.Time()+" | Simulation complete!");
        System.out.println("Final Delivery time: "+Clock.Time());
        System.out.printf("Final Score: %.2f%n", total_score);
    }
}
