package pgdp.robot;

import java.util.*;
import java.util.function.Function;

public class Robot {
	/// Attributes
	private List<Sensor<?>> sensors = new ArrayList<>();
	private List<Memory<?>> memory = new ArrayList<>();
	private Function<Robot, List<Command>> program = robot -> new ArrayList<>();
	private Queue<Command> todo = new LinkedList<>();


	// final attributes
	private final String name;
	private final double size;

	protected final FailureSimulator failureSimulator;

	// internal states
	private Position position = new Position();
	private double direction;
	private World world;

	private String key;
	private World.Scepter scepter;

	/// Methods
	public Robot(String name, double direction, double size, FailureSimulator failureSimulator) {
		this.name = name;
		this.direction = direction;
		this.size = Math.min(Math.max(0.5, size), 1); // between 0.5 and 1
		this.failureSimulator = failureSimulator;
	}

	public FailureSimulator getFailureSimulator() {
		return failureSimulator;
	}

	public void attachSensor(Sensor<?> sensor){
		sensor.setOwner(this);
		sensors.add(sensor);
	}

	//T statt ?
	public <T> Memory<T> createMemory(Memory<T> memoryUnit){
		this.memory.add(memoryUnit);
		return memoryUnit;
	}

	public String memoryToString(){
		StringBuilder s = new StringBuilder();
		for(Memory<?> obejct : memory){
			s.append("[").append(obejct.toString()).append("]");
		}
		return s.toString();
	}

	public void setProgram(Function<Robot, List<Command>> program) {
		this.program = program;
	}

	private <T> void processSensor(Sensor<T> sensor) throws MalfunctionException{
		try{
			sensor.processor.accept(sensor.getData());
		}
		catch (MalfunctionException malfunctionException){
			throw malfunctionException;
		}
	}
	private void sense() {
		for (int i = 0; i < sensors.size(); i++) {
			if (sensors.get(i) != null) {
				try {
					processSensor(sensors.get(i));
				} catch (MalfunctionException malfunctionException){
					Sensor<?> old = sensors.remove(i);
					Sensor<?> now = old.createNewSensor();
					now.setOwner(this);
					if(i == 0){
						sensors.add(0, now);
					} else sensors.add(i-1, now);
					sense();
					return;
				}
			}
		}
	}
	private void think(){
		todo.addAll(program.apply(this));

	}
	private void act(){
		while(!todo.isEmpty()){
			Command command = todo.poll();
			if(!command.execute(this)){
				break;}
		}
	}
	public void work(){
		if(todo.isEmpty()){
			sense();
			think();
		}
		act();
	}


	/// Pre-programmed Commands
	public boolean go(double distance) {
		// step can be negative if the penguin walks backwards
		double sign = Math.signum(distance);
		distance = Math.abs(distance);
		// penguin walks, each step being 0.2m
		while (distance > 0) {
			position.moveBy(sign * Math.min(distance, 0.2), direction);
			world.resolveCollision(this, position);
			distance -= 0.2;
		}
		return true;
	}

	public boolean turnBy(double deltaDirection) {
		direction += deltaDirection;
		return true;
	}

	public boolean turnTo(double newDirection) {
		direction = newDirection;
		return true;
	}

	public boolean say(String text) {
		world.say(this, text);
		return true;
	}

	public boolean paintWorld(Position pos, char blockType) {
		world.setTerrain(pos, blockType);
		return true;
	}

	/// Getters and Setters
	public String getName() {
		return name;
	}

	public double getSize() {
		return size;
	}

	public Position getPosition() {
		return new Position(position);
	}

	public int getRoundedX() {
		return (int) Math.floor(position.x);
	}

	public int getRoundedY() {
		return (int) Math.floor(position.y);
	}

	public double getDirection() {
		return direction;
	}

	public World getWorld() {
		return world;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public World.Scepter getScepter() {
		return scepter;
	}

	public void setScepter(World.Scepter scepter) {
		this.scepter = scepter;
	}

	public void spawnInWorld(World world, char spawnMarker) {
		this.world = world;
		this.position = new Position(world.spawnRobotAt(this, spawnMarker));
	}

	@Override
	public String toString() {
		return "\"" + name + "\" position=" + position + " direction="
				+ (((int) (Math.toDegrees(direction) * 100) / 100.0));
	}

	public static interface FailureSimulator {
		double getNextRandom();
	}

	public static class SimpleFailureSimulator implements FailureSimulator {

		private Random random;

		public SimpleFailureSimulator(int seed) {
			this.random = new Random(seed);
		}

		@Override
		public double getNextRandom() {
			return random.nextDouble();
		}
	}
}
