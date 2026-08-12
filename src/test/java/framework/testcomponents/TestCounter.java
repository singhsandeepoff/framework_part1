package framework.testcomponents;

import java.util.concurrent.atomic.AtomicInteger;

public class TestCounter {
    private static final AtomicInteger counter = new AtomicInteger(1);

    // Returns the next formatted Test Case ID: "TC01", "TC02", "TC10", etc.
    public static String getNextId() {
        return String.format("TC%02d", counter.getAndIncrement());
    }

    // Optional: Reset counter if running multiple test suites back-to-back
    public static void reset() {
        counter.set(1);
    }
}