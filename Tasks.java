package jmxtest;

import java.util.concurrent.ScheduledFuture;

public class Tasks {
    private final String name;
    private final ScheduledFuture<?> scheduledFuture;
    private String status;

    public Tasks(String name, ScheduledFuture<?> sF, String status){
        this.name=name;
        this.scheduledFuture=sF;
        this.status = status;
    }

    public String showPreview(){
       return name + " " + status;
    }

    public String getStatus() {
        return status;
    }

    public ScheduledFuture<?> getScheduledFuture() {
        return scheduledFuture;
    }

    public String getName() {
        return name;
    }


    public void setStatus(String status) {
        this.status = status;
    }
    
}
