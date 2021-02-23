package jmxtest;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Settings implements HelloMXBean{
    private HashMap<String,ScheduledFuture> scheduledList = new HashMap<>();//список заданий
    String[] args;
    public Settings(String[] args){
        this.args = args;
    }
    @Override
    public void submit(String name, String classpath, String mainClass, int period) throws Exception {
        ScheduledFuture schedule = Executors.newScheduledThreadPool(1).
                scheduleAtFixedRate(new TaskManager(classpath, mainClass,args), 0, period, TimeUnit.SECONDS);//планировщик
        scheduledList.put(name, schedule);
    }

    @Override
    public void cancel(String name) {
        scheduledList.get(name).cancel(true);
    }

    @Override
    public String status(String name) {
        System.out.println(scheduledList.get(name));
        if (scheduledList.get(name).isCancelled() | scheduledList.get(name).isDone() ){
            return "task wasn't found";
        }
        if (!scheduledList.get(name).isDone()|!scheduledList.get(name).isCancelled()){
            return "task is running";
        }
        else {
            return scheduledList.get(name).toString();
        }
    }

    @Override
    public String showAllTasks() {
        return scheduledList.toString();
    }

}
