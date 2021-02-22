package jmxtest;

import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        var settings = new Settings();
       ManagementFactory.getPlatformMBeanServer().registerMBean(
               settings, new ObjectName("jmxtest:type=Test"));
        System.out.println("Жду команды");
       while (true){

       }
    }
}
