package automail;

import java.util.HashMap;
import java.util.Properties;

import strategies.IMailPool;
import strategies.IRobotBehaviour;
import strategies.MyRobotBehaviour;

//Class with helper functions for processing the properties config file
public class ProcessProperties {
	
	private static int DEFAULT_FLOOR = 14;
	private static double DEFAULT_PENALTY =1.1;
	private static int DEFAULT_DELIVERY_TIME = 300;
	private static int DEFAULT_MAIL_TO_CREATE = 180;
	private static String WEAK_BOT = "weak";
	private static String STRONG_BOT = "strong";
	private static String BIG_BOT = "big";
	private static boolean weak = false;
	private static boolean strong = true;
	
	//Processes seed on both command line and properties file, used to set seedMap in Simulation.java
	public static HashMap<Boolean, Integer> SeedProp(Properties prop, String[] args) {
		
		HashMap<Boolean, Integer> seedMap = new HashMap<>();
		int seed;
		
		try {
			seed = Integer.parseInt(prop.getProperty("Seed"));
			seedMap.put(true, seed);
		}
		catch(NumberFormatException e) {
			if(args.length != 0){
	        	seed = Integer.parseInt(args[0]);
	        	seedMap.put(true, seed);
	        } else{
	        	seedMap.put(false, 0);
	        }
		}
		
		return seedMap;
	}
	
	//Process number of floors property, assign default 14 if none
	public static void FloorProp(Properties prop) {
		try {
			Building.setFloor(Integer.parseInt(prop.getProperty("Number_of_Floors")));
		}	
		catch (NumberFormatException e){
			Building.setFloor(DEFAULT_FLOOR);
		}
	}
	
	
	//Process Delivery penalty, used to set PENALTY in Simulation.java
	public static double DeliveryProp(Properties prop) {
		double i;
		try {
			i = Double.parseDouble(prop.getProperty("Delivery_Penalty"));
		}	
		catch (NumberFormatException e){
			i = DEFAULT_PENALTY;
		}
		
		return i;
	}
	
	public static void TimeProp(Properties prop) {
		try {
			Clock.setDeliveryTime(Integer.parseInt(prop.getProperty("Last_Delivery_Time")));
		}	
		catch (NumberFormatException e){
			Clock.setDeliveryTime(DEFAULT_DELIVERY_TIME);
		}
	}
	
	//Process amount mail to be created, used to set MAIL_TO_CREATE in Simulation.java
	public static int MailProp(Properties prop) {
		int i;
		try {
			i = Integer.parseInt(prop.getProperty("Mail_to_Create"));
		}	
		catch (NumberFormatException e){
			i = DEFAULT_MAIL_TO_CREATE;
		}
		return i;
	}
	
	public static Robot RobotProps(Properties prop, int RobotNum, IMailPool MailPool, IMailDelivery delivery) {
		String RobotType;
		if(RobotNum == 1) {
			RobotType = prop.getProperty("Robot_Type_1");
			System.out.println(RobotType);
		}
		else {
			RobotType = prop.getProperty("Robot_Type_2");
			System.out.println(RobotType);
		}
		
		if(RobotType.equals(WEAK_BOT)) {
			IRobotBehaviour robotBehaviourW = new MyRobotBehaviour(weak);
			return new WeakRobot(robotBehaviourW, delivery, MailPool, weak,10);
			
		}
		else if(RobotType.equals(STRONG_BOT)) {
			IRobotBehaviour robotBehaviourS = new MyRobotBehaviour(strong);
			return new StrongRobot(robotBehaviourS, delivery, MailPool, strong,10);
		}
		else if(RobotType.equals(BIG_BOT)){
			System.out.println("ISSUE");
			return null;
		}
		else {
			System.out.println("Invalid Robot configuration");
			System.exit(1);
			return null;
		}

		
	}
	
	
}
