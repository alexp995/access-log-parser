import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Statictics {
    private int totalTraffic = 0;
    private LocalDateTime minTime = null;
    private LocalDateTime maxTime = null;

    public void addEntry(LogEntry entry) {
        totalTraffic += entry.getDataSize();
        LocalDateTime time = entry.getDateTime();

        if (minTime == null || time.isBefore(minTime)) {
            minTime = time;
        }
        if (maxTime == null || time.isAfter(maxTime)) {
            maxTime = time;

        }
    }

    public double getTrafficRate() {
        if (minTime == null || maxTime == null) {
            return 0;
        }
        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        if (hours == 0) {
            hours = 1;
        }
        return totalTraffic / (double) hours;
    }
}
