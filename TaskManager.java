package jmxtest;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;

public class TaskManager implements Runnable {
    private String classpath;
    private String mainClass;
    private ClassLoader loader;

    public TaskManager(String cp, String mC) {
        this.classpath=cp;
        this.mainClass=mC;
    }

    @Override
    public void run() {
        try {
            var loader = newLoader(classpath);
            var clazz =loader.loadClass(mainClass);
            clazz.getMethod(mainClass,String[].class).invoke(null);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    private ClassLoader newLoader(String dir) throws Exception {
        var path = Path.of(dir);
        if (!Files.isDirectory(path))
            throw new RuntimeException();
        return new URLClassLoader(new URL[]{path.toUri().toURL()});
    }

}


