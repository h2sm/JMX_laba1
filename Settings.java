package jmxtest;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Settings implements HelloMXBean{
    private ClassLoader loader;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    @Override
    public void submit(String name, String classpath, String mainClass, int period) throws Exception {
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(new TaskManager(classpath, mainClass), 0, period, TimeUnit.SECONDS);
    }

    @Override
    public void cancel(String name) {
        scheduler.shutdown();
    }

    @Override
    public String status(String name) {
        return null;
    }
    private ClassLoader newLoader(String dir) throws Exception {
        var path = Path.of(dir);
        if (!Files.isDirectory(path))
            throw new RuntimeException();
        return new URLClassLoader(new URL[] { path.toUri().toURL() });
    }

}
