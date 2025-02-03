package pgdp.robot;

// import static pgdp.robot.RobotFactory.*;

import static pgdp.robot.RobotFactory.*;

public class User {

	private static final String PLAY_GROUND_1 = """
################
#  0   #   ##  #
#$ #   #   #   #
####   # ### ###
#    ###       #
#  #      ##   #
################
			""";
//				#############
//						#  0    v<< #
//			#Don't  v ^ #
//			#PANIC  v1^ #
//			#       v ^ #
//			#    2  >>^ #
//			#############

	// Execute this main to launch the game
	public static void main(String[] args) {
		createExample();
	}

	public static void createExample() {
		World world = new World(PLAY_GROUND_1);
		
//		Robot newPanic = makePanicPenguin("Panic!", Math.toRadians(5), new Robot.SimpleFailureSimulator(69));
//		newPanic.spawnInWorld(world, '0');
//		makeLineFollower("Line Follower", 0, new Robot.SimpleFailureSimulator(42)).spawnInWorld(world, '0');
		makeMazeRunner("Maze Runner", 0, new Robot.SimpleFailureSimulator(1337)).spawnInWorld(world, '0');
//		Robot car = makeCar("Car",new Robot.SimpleFailureSimulator(69));
//		car.spawnInWorld(world, '0'); ;
		world.run();
	}
}
