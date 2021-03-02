package jmxtest;


import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Settings implements HelloMXBean {
    private ArrayList<Tasks> tasks = new ArrayList<>();
    private final String[] args;

    public Settings(String[] args) {
        this.args = args;
    }

    @Override
    public void submit(String name, String classpath, String mainClass, int period) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        try {
            TaskManager taskManager = new TaskManager(classpath,mainClass,args);//создаем новое задание
            ScheduledFuture<?> future = scheduledExecutorService.scheduleAtFixedRate(taskManager, 0, period, SECONDS);//создаем новый executor
            tasks.add(new Tasks(name, future, "running", taskManager));//присваиваем статус
        } catch (Exception e) {
            String exc = e.getClass().getSimpleName();
            for (Tasks task : tasks){
                if (task.getName().equals(name)) {
                    task.getScheduledFuture().cancel(true);//отменяем
                    task.setStatus(exc);//устанавливаем статус если что-то пошло не так во время запуска
                }
            }
        }
    }

    @Override
    public String cancel(String name) {
        for (Tasks task : tasks) {
            if (task.getName().equals(name)) {
                task.getScheduledFuture().cancel(true);
                if (!task.getStatusOfRunnable().equals("OK")){//если что-то пошло не так при завершении
                    task.setStatus(task.getStatusOfRunnable());
                    return "task was cancelled with some errors";
                }
                task.setStatus("cancelled");
                return "task was cancelled";
            }
        }
        return "no such a task";
}
    @Override
    public String status(String name) {
        for (Tasks task : tasks){
            if (task.getName().equals(name)) {//если что-то пошло не так во время запуска
                return task.getStatus();
            }
            if (!task.getStatusOfRunnable().equals("OK")){//если что-то пошло не так во время выполнения приложения
                return task.getStatusOfRunnable();
            }
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
