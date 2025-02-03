package pgdp.pingu.netz;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class Place {
    private final String label;
    private final Object lock = new Object();
    private Thread lockholder = null;



    private int tokens;
    public Place(String label, int tokens){
        this.label = label;
        this.tokens = tokens;
    }

    public boolean hasEnoughTokens(int amount) {
        // TODO: Do your programming magic! 🐧
        synchronized (lock) {
            Thread t = Thread.currentThread();
            if (lockholder == null || !lockholder.equals(t)) {
                throw new IllegalStateException();
            }
            return tokens >= amount;

        }
    }

    public void consumeToken(int amount){
        // TODO: Do your programming magic! 🐧
        Thread t = Thread.currentThread();
        synchronized (lock){
        if(lockholder == null || !lockholder.equals(t) || tokens < amount ){
            throw new IllegalStateException();
        }
        tokens = tokens - amount;
    }
}

    public void addToken(int amount) {
        // TODO: Do your programming magic! 🐧
        Thread t = Thread.currentThread();
        synchronized (lock){
        if(lockholder == null || !lockholder.equals(t)){
            throw new IllegalStateException();
        }
        tokens = tokens + amount;
        }
    }

    public String getLabel() {
        return label;
    }

    public void lockPlace() {
        // TODO: Do your programming magic! 🐧
        Thread t = Thread.currentThread();
        synchronized (lock) {
            while(lockholder != null){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
                lockholder = t;
            }
    }

    public void unlockPlace() {
        // TODO: Do your programming magic! 🐧
        Thread t = Thread.currentThread();
        synchronized (lock) {
            if (lockholder == null || !lockholder.equals(t)) {
                throw new IllegalStateException();}
            lockholder = null;
            lock.notify();

        }
    }

    public static void main (String[] args){
        String currentDir = System.getProperty("user.dir");
        System.out.println(currentDir);

        String content = "Running the Code\n" +
                "If you run this program from an IDE, it will print the working directory of the IDE's runtime configuration (usually the project folder).\n" +
                "If you run it from the command line, it will show the directory where the java command was executed.";

        try {
//            Files.createDirectories(Path.of(currentDir).resolve("Hello.txt"));
//            Files.createFile(Path.of(currentDir).resolve("Hello.txt").resolve("Hello.txt"));
            FileWriter w = new FileWriter();
            w.write("Hi");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
