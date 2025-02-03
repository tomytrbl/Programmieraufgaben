package pgdp.robot;

import java.util.ArrayList;
import java.util.List;

import static java.awt.event.KeyEvent.*;

public class RobotFactory {
//String name, double direction, double size, FailureSimulator failureSimulator

    public static Robot makePanicPenguin(String name, double rotation, Robot.FailureSimulator failureSimulator){
        Robot neu = new Robot(name, 0, 0.8, failureSimulator);
        TerrainSensor sensor =  new TerrainSensor("TerriSensor", 0.97);
        Memory<Character> memory = new Memory<>("First", 'c');
        neu.createMemory(memory);
        sensor.setProcessor(character -> memory.set(character));
        neu.attachSensor(sensor);

        neu.setProgram(robot ->{

            List<Command> commands = new ArrayList<>();
            commands.add(Robot1 -> Robot1.say(memory.get()+""));
            commands.add(Robot1 -> Robot1.turnBy(rotation));
            commands.add(Robot1 -> Robot1.go(0.1));

            return commands;
        });
        return neu;
    }
    public static Robot makeLineFollower(String name,double direction, Robot.FailureSimulator failureSimulator){
        Robot neu = new Robot(name, direction, 0.8, failureSimulator);
        TerrainSensor newsensor = new TerrainSensor("PanicSensor", 0.97);
        Memory<Character> mem = new Memory<>("first", 'c');
        neu.createMemory(mem);
        newsensor.setProcessor(character -> mem.set(character));
        neu.attachSensor(newsensor);

        neu.setProgram(Robot -> {
            List<Command> commands = new ArrayList<>();
            commands.add(Robot1 -> {
                Robot1.go(0.2);
                if(mem.get() == '>'){
                    Robot1.turnTo(0);
                } else if (mem.get() == 'v') {
                    Robot1.turnTo(Math.PI / 2);
                } else if (mem.get() == '<') {
                    Robot1.turnTo(Math.PI);
                } else if (mem.get() == '^') {
                    Robot1.turnTo(1.5 * Math.PI);
                }else Robot1.go(0.1);
                return true;}
            );
            commands.add(Robot1 -> Robot1.go(0.1));
            return commands;
        });

        return neu;
    }
    public static Robot makeCar(String name, Robot.FailureSimulator failureSimulator){
        Robot car = new Robot(name, Math.PI /2, 0.8, failureSimulator);
        ControllerReceiver csensor1 = new ControllerReceiver("controller", 0.97);
        ControllerReceiver csensor2 = new ControllerReceiver("controller", 0.97);
        ControllerReceiver csensor3 = new ControllerReceiver("controller", 0.97);
        ControllerReceiver csensor4 = new ControllerReceiver("controller", 0.97);
        Memory<Integer> mem = new Memory<>("f", 0);
        car.createMemory(mem);
        car.createMemory(mem);
        car.createMemory(mem);
        car.createMemory(mem);
        csensor1.setProcessor(controller -> mem.get());
        csensor2.setProcessor(controller -> mem.get());
        csensor3.setProcessor(controller -> mem.get());
        csensor4.setProcessor(controller -> mem.get());
        car.attachSensor(csensor1);
        car.attachSensor(csensor2);
        car.attachSensor(csensor3);
        car.attachSensor(csensor4);
        car.setProgram(robot ->{

            List<Command> commands = new ArrayList<>();
            commands.add(Robot1 -> {
                if(car.getWorld().getController().isKeyPressed(VK_W)){
                    Robot1.go(0.1);}
                if (car.getWorld().getController().isKeyPressed(VK_A)) {
                    Robot1.turnBy(-0.1);
                }
                if (car.getWorld().getController().isKeyPressed(VK_S)) {
                    Robot1.go(-0.1);
                }
                if (car.getWorld().getController().isKeyPressed(VK_D)) {
                    Robot1.turnBy(0.1);}
                return true;
            });
            return commands;
        });
        return car;
    }
    public static Robot makeMazeRunner(String name, double direction, Robot.FailureSimulator failureSimulator){
        Robot neu = new Robot(name, direction, 0.8,failureSimulator);
        ProximitySensor newsensor = new ProximitySensor("ProxSensor", 0,0.1 ,3.5 , 0.97);
        ProximitySensor newsensorside = new ProximitySensor("ProxSensor", Math.PI/2,0.1 ,3.5 , 0.97);
        Memory<Double> frontmem = new Memory<>("FrontSensor", 0d);
        Memory<Double> rightmem = new Memory<>("RightSensor", 0d);
        neu.createMemory(frontmem);
        neu.createMemory(rightmem);

        newsensor.setProcessor(doubl -> frontmem.set(doubl));
        newsensorside.setProcessor(doubl -> rightmem.set(doubl));

        neu.attachSensor(newsensor);
        neu.attachSensor(newsensorside);

        neu.setProgram(robot ->{
            //wenn geradeaus eine Wand ist lauf geradeaus
            //wenn vor dir eine Wand ist
            //drehe dich nach links
            //wenn rechts der sensor eine Wand sieht gehe geradeaus
            //wenn vorne und rechts eine wand ist dreh dich nach links
            List<Command> commands = new ArrayList<>();
            commands.add(robot1 -> {
                System.out.println("FRONT: " +frontmem.get());
                System.out.println("RIGHT: " +rightmem.get());
                if (frontmem.get() <= 0.11 && rightmem.get() <= 0.11) {
                    robot1.turnBy(Math.PI*1.5);
                    robot1.go(1);
                    System.out.println("I turned left bcs front = wall and right == wall");
                } else if (frontmem.get() <= 0.11) {
                    if(rightmem.get() >= 0.31){
                        robot1.turnBy(Math.PI*0.5);
                        System.out.println("wall infront, but rihgt has space, so I turn right");
                    }else {robot1.turnBy(Math.PI*1.5);
                        System.out.println("I turned left bcs front is wall");}
                    robot1.go(1);

                } else if (rightmem.get() >= 0.3) {
                    robot1.turnBy(Math.PI*0.5);
                    robot1.go(1);
                    System.out.println("I turned right bcs right has space");
                } else {robot1.go(1);
                    System.out.println("went straight");}
                System.out.println();
                return true;
            });
            return commands;
        });
        return neu;
    }
    public static Robot makeTempleRunner(String name, double direction, Robot.FailureSimulator failureSimulator){
        return null;
    }
}