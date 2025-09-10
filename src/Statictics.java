
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;

public class Statictics {
    private int totalTraffic = 0;
    private LocalDateTime minTime = null;
    private LocalDateTime maxTime = null;

    private HashSet<String> existPages = new HashSet<>();
    private HashMap<String, Integer> statOs = new HashMap<>();


    public void addEntry(LogEntry entry) {
        totalTraffic += entry.getDataSize();
        LocalDateTime time = entry.getDateTime();

        if (minTime == null || time.isBefore(minTime)) {
            minTime = time;
        }
        if (maxTime == null || time.isAfter(maxTime)) {
            maxTime = time;
        }

        if (entry.getCodeResponce() == 200) {
            existPages.add(entry.getPath());
        }

        String os = entry.getUserAgent().getOsType();
        statOs.put(os, statOs.getOrDefault(os, 0) + 1);
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

    public HashSet<String> getExistPages() {
        return existPages;
    }

    public HashMap<String, Double> getProportionOs() {
        HashMap<String, Double> result = new HashMap<>();
        int totalCountAllOs = 0;
        for (int countValue : statOs.values()) {
            totalCountAllOs += countValue;
        }
        if (totalCountAllOs == 0) {
            return result;
        }

        for (String osValue : statOs.keySet()) {
            int countOs = statOs.get(osValue);
            double finalResult = (double) countOs / totalCountAllOs;
            result.put(osValue, finalResult);
        }
        return result;
    }

}
