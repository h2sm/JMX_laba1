package jmxtest;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Settings implements HelloMXBean{
    private HashMap<String,ScheduledFuture> scheduledList = new HashMap<>();//список заданий
    private ClassLoader loader;

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
        return "is cancelled " + scheduledList.get(name).isCancelled();//доделать!
    }
    private ClassLoader newLoader(String dir) throws Exception {
        var path = Path.of(dir);
        if (!Files.isDirectory(path))
            throw new RuntimeException();
        return new URLClassLoader(new URL[] { path.toUri().toURL() });
    }

}
