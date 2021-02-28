package jmxtest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Settings implements HelloMXBean {
    private ArrayList<Tasks> tasks = new ArrayList<>();
    private String[] args;

    public Settings(String[] args) {
        this.args = args;
    }

    @Override
    public void submit(String name, String classpath, String mainClass, int period) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        try {
            ScheduledFuture<?> future = scheduledExecutorService.scheduleAtFixedRate(new TaskManager(classpath, mainClass, args), 0, period, SECONDS);
            tasks.add(new Tasks(name, future, "running"));
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            String err = errors.toString();
            System.out.println("error: " + err);
        }
    }

    @Override
    public String cancel(String name) {
        for (Tasks task : tasks) {
            if (task.getName().equals(name)) {
                task.getScheduledFuture().cancel(true);
                task.setStatus("cancelled");
                return "task was cancelled";
            }
        }
        return "no such a task";
}
    @Override
    public String status(String name) {
        for (Tasks task : tasks){
            if (task.getName().equals(name)) return task.getStatus();
        }
        return "not found";
    }

    @Override
    public void showAllTasks() {
        for (Tasks task : tasks) {
            System.out.println(task.showPreview());
        }
    }

}
