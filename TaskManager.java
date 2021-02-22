package jmxtest;

public class TaskManager implements Runnable {
    private String classpath;
    private String mainClass;

    public TaskManager(String cp, String mC) {
        this.classpath=cp;
        this.mainClass=mC;
    }

    @Override
    public void run() {
        System.out.println("Hello bitch");
    }

}
