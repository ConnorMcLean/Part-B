package automail;

import strategies.IMailPool;
import strategies.IRobotBehaviour;

public class WeakRobot extends Robot{
	public WeakRobot(IRobotBehaviour behaviour, IMailDelivery delivery, IMailPool mailPool, boolean strong) {
		super(behaviour, delivery, mailPool, strong);
		// TODO Auto-generated constructor stub
		this.strong = false;
	}

	public WeakRobot(IRobotBehaviour behaviour, IMailDelivery delivery, IMailPool mailPool, boolean strong,
			int tubeCapacity) {
		super(behaviour, delivery, mailPool, strong, tubeCapacity);
		// TODO Auto-generated constructor stub
		this.strong = false;
	}

	

	

	
	
}
