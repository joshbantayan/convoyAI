import java.util.ArrayList;
import java.util.Scanner;

class State {
	ArrayList<Truck> truckList = new ArrayList<>();
    private double time;
    private State parent;
    private int depth;
    
    public State(ArrayList<Truck> truckList, double time, int depth, State parent) {
    	this.truckList = truckList;
        this.time = time;
        this.parent = parent;
        this.depth = depth;
    }

    public State getParent() {
        return parent;
    }

    public boolean isGoal(){
    	return truckList.isEmpty();
    }

    public ArrayList<State> expand() {
    	double slowestTime = 0;
		int totalWeight = 0;
    	ArrayList<State> successors = new ArrayList<>();

    	for (int i = 0; i < truckList.size(); i++){
    		totalWeight += truckList.get(0).getWeight();
    		slowestTime = Math.max(slowestTime,truckList.remove(0).getTruckTime());

    		if (totalWeight <= Convoy.maxLoad){
    			successors.add(new State(truckList, time += slowestTime, depth++, this));
    		} else {
    			break;
    		}
    	}
    	return successors;
    }


    // @Override
    // public String toString() {
    // 	System.out.println(time);
    // }
}

class Truck {
	private int weight;
	private int speed;
	private double truckTime;

	public Truck(int weight, int speed, double truckTime) {
		this.weight = weight;
		this.speed = speed;
		this.truckTime = truckTime;
	}

	public int getWeight() {
		return weight;
	}

	public int getSpeed(){
		return speed;
	}

	public double getTruckTime() {
		return truckTime;
	}
}
public class Convoy {
	static int maxLoad;
	public static void main(String[] args) {
		ArrayList<Truck> truckList = new ArrayList<>();
		Scanner kbd = new Scanner(System.in);
		maxLoad = kbd.nextInt();
		int bridgeLength = kbd.nextInt();
		int numVehicles = kbd.nextInt();

		for (int x = 0; x < numVehicles; x++){
			int weight = kbd.nextInt();
			int speed = kbd.nextInt();

			double truckTime = bridgeLength / speed;

			Truck truck = new Truck(weight, speed, truckTime);
			truckList.add(truck);
		}
		State initialState = new State(truckList, 0.0, 0, null);

		ArrayList<State> frontier = new ArrayList<>();
		frontier.add(initialState);

		int totalStatesVisited = 0;
		int maxFrontierSize = 1;

		while (frontier.size() > 0) {
			State currentState = frontier.remove(0);
			totalStatesVisited++;

			if (currentState.isGoal()) {
				System.out.println("success");
				return;
			} else {
				ArrayList<State> successorStates = currentState.expand();
				frontier.addAll(successorStates);

				maxFrontierSize = Math.max(maxFrontierSize, frontier.size());
			}
		}
}

public static void showSolution(double time){
System.out.println(time);
}
	}