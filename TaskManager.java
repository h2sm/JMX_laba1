package jmxtest;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;

public class TaskManager implements Runnable {
    private String classpath;
    private String mainClass;
    private String[] args;

    public TaskManager(String cp, String mC, String[] args) {
        this.classpath=cp;
        this.mainClass=mC;
        this.args=args;
    }

    private ClassLoader newLoader(String dir) throws Exception {
        var path = Path.of(dir);
        if (!Files.isDirectory(path))
            throw new RuntimeException();
        return new URLClassLoader(new URL[]{path.toUri().toURL()});
    }
    @Override
    public void run() {
        try {
            var loader = newLoader(classpath);
            var clazz =loader.loadClass(mainClass);
            clazz.getMethod("main",String[].class).invoke(null, (Object) args);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }
}


