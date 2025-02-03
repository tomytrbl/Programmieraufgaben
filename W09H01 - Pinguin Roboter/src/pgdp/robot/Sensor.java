package pgdp.robot;

import java.util.function.Consumer;

public abstract class Sensor<T> {
	protected Consumer<T> processor;
	protected String name;
	protected double reliability;
	protected Robot owner;

	public Sensor(String name, double reliability) {
		this.name = name;
		this.reliability = reliability;
	}

	public Sensor<T> setProcessor(Consumer<T> processor) {
		this.processor = processor;
		return this;
	}

	public void setOwner(Robot owner) {
		this.owner = owner;
	}

	public abstract T getData() throws MalfunctionException;
	public abstract Sensor<T> createNewSensor();
}
