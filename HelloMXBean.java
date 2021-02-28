package jmxtest;
import java.util.HashSet;

public interface HelloMXBean {
    void submit(String name, String classpath, String mainClass, int period) throws Exception;
    String cancel(String name);
    String status(String name);
    void showAllTasks();
}
