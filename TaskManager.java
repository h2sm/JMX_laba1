package jmxtest;

public class TaskManager implements Runnable {
    String classpath;
    String mainClass;

    public TaskManager(String cp, String mC) {
        this.classpath=cp;
        this.mainClass=mC;
    }

    @Override
    public void run() {

    }
}
