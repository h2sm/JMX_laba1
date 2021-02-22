package jmxtest;

import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception {
        var settings = new Settings();
       ManagementFactory.getPlatformMBeanServer().registerMBean(
               settings, new ObjectName("jmxtest:type=Test"));
       while (true){
           for (int i = 0; i <1 ; i++) {
               System.out.println("Жду команду");
           }
       }
    }
}
