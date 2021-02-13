package org.sus.task;

import lombok.Getter;
import org.sus.Main;

import java.util.Timer;
import java.util.TimerTask;

public class ClearLogTask {

    @Getter private static Timer timer;

    public static void start() {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Main.getCachedMessageDAO().deleteAll();
            }
        };

        timer = new Timer();
        long delay = 0;
        long interval = 3600 * 1000;

        timer.scheduleAtFixedRate(timerTask, delay, interval);
    }

}
