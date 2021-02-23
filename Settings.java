package jmxtest;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Settings implements HelloMXBean{
    private HashMap<String,ScheduledFuture> scheduledList = new HashMap<>();//список заданий
    private String[] args;
    public Settings(String[] args){
        this.args = args;
    }
    @Override
    public void submit(String name, String classpath, String mainClass, int period) {
        ScheduledFuture schedule = Executors.newScheduledThreadPool(1).
                scheduleAtFixedRate(new TaskManager(classpath, mainClass, args), 0, period, TimeUnit.SECONDS);//планировщик
        scheduledList.put(name, schedule);
    }

    @Override
    public String cancel(String name) {
        scheduledList.get(name).cancel(true);
        if (scheduledList.containsKey(name)){
            scheduledList.remove(name);
            return "task was deleted";
        }
        else {
            return "no such a task";
        }
    }

    @Override
    public String status(String name) {
//        if ((scheduledList.get(name).isCancelled() | scheduledList.get(name).isDone() | !scheduledList.containsKey(name))){//я не понимаю почему эта хрень не работает
//            return "task wasn't found";
//        }
//        if (!scheduledList.get(name).isDone()|!scheduledList.get(name).isCancelled()){
//            return "task is running";
//        }
//        else {
            String bufer = scheduledList.get(name).toString();
            bufer.split(".");
            System.out.println(bufer);

            return scheduledList.get(name).toString();
        //}
    }

    @Override
    public String showAllTasks() {
        return scheduledList.toString();
    }

}
