package jmxtest;

public interface HelloMXBean {
    void submit(String name, String classpath, String mainClass, int period) throws Exception;
    void cancel(String name);
    String status(String name);
}
