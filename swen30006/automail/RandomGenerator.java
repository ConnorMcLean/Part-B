package automail;

import java.util.Random;

public class RandomGenerator {

	public int randomDestinationFloor;
	public int randomPriorityLevel;
	public int randomWeight;
	public int randomTimeArrival;
	private final Random rand;
	
	
	public RandomGenerator(Random random) {
		rand = random;
		randomDestinationFloor=Building.LOWEST_FLOOR + rand.nextInt(Building.getBuildingFloor());
		randomPriorityLevel = rand.nextInt(4) > 0 ? 10 : 100;
		randomWeight = generateWeight(rand);
		randomTimeArrival = 1 + random.nextInt(Clock.LAST_DELIVERY_TIME);
		
	}
	/**
	 * @param mailGenerator TODO
	 * @return a random weight
	 */
	int generateWeight(Random random){
		final double mean = 200.0; // grams for normal item
		final double stddev = 700.0; // grams
		double base = random.nextGaussian();
		if (base < 0) base = -base;
		int weight = (int) (mean + base * stddev);
	    return weight > 5000 ? 5000 : weight;
	}
	
	
	
}
