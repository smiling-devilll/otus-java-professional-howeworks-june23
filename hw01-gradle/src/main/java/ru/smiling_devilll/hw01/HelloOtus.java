package ru.smiling_devilll.hw01;

import com.google.common.base.Stopwatch;
import java.time.Duration;

public class HelloOtus {
    public static void main(String[] args) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        System.out.println("doing print");
        stopwatch.stop();

        Duration duration = stopwatch.elapsed();

        System.out.println("time (nanos): " + duration.getNano());
    }
}
