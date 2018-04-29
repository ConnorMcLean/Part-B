package automail;

import strategies.IMailPool;
import strategies.IRobotBehaviour;

public class StrongRobot extends Robot{

	public StrongRobot(IRobotBehaviour behaviour, IMailDelivery delivery, IMailPool mailPool, boolean strong) {
		super(behaviour, delivery, mailPool, strong);
		// TODO Auto-generated constructor stub
		this.strong = true;
	}


	public StrongRobot(IRobotBehaviour behaviour, IMailDelivery delivery, IMailPool mailPool, boolean strong,
			int tubeCapacity) {
		super(behaviour, delivery, mailPool, strong, tubeCapacity);
		this.strong = true;
		// TODO Auto-generated constructor stub
	}


}
