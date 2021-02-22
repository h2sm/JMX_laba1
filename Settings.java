package jmxtest;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Settings implements HelloMXBean{
    private HashMap<String,ScheduledFuture> scheduledList = new HashMap<>();//список заданий

    @Override
    public void submit(String name, String classpath, String mainClass, int period) throws Exception {
        ScheduledFuture schedule = Executors.newScheduledThreadPool(1).
                scheduleAtFixedRate(new TaskManager(classpath, mainClass), 0, period, TimeUnit.SECONDS);//планировщик
        scheduledList.put(name, schedule);

    }

    @Override
    public void cancel(String name) {
        scheduledList.get(name).cancel(true);
    }

    @Override
    public String status(String name) {
        System.out.println(scheduledList.get(name));
        if (scheduledList.get(name).isCancelled()){
            return name + " was cancelled";
        }
        if (scheduledList.get(name).isDone()){
            return name + " is done";
        }
        return  scheduledList.get(name).toString();
    }

}
