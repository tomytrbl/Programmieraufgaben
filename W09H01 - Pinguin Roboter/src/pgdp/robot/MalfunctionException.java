package pgdp.robot;

@SuppressWarnings("serial")
public class MalfunctionException extends Exception {
	private Sensor<?> sensor;

	public MalfunctionException(Sensor<?> sensor) {
		this.sensor = sensor;
	}

	public Sensor<?> getSensor() {
		return sensor;
	}

	public String toString(){ // evtl falsch;
		return sensor+sensor.name+" malfunctioned and needs to be replaced";
	}




}
