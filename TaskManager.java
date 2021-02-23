package jmxtest;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;

public class TaskManager implements Runnable {
    private String classpath;
    private String mainClass;
    private ClassLoader loader;
    private String[] args;
    String e;

    public TaskManager(String cp, String mC, String[] args) {
        this.classpath=cp;
        this.mainClass=mC;
        this.args=args;
    }

    @Override
    public void run() {
        try {
            var loader = newLoader(classpath);
            var clazz =loader.loadClass(mainClass);
            clazz.getMethod("main",String[].class).invoke(null, (Object) args);

        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println(exception.getCause());
        }
    }
    private ClassLoader newLoader(String dir) throws Exception {
        var path = Path.of(dir);
        if (!Files.isDirectory(path))
            throw new RuntimeException();
        return new URLClassLoader(new URL[]{path.toUri().toURL()});
    }

}


