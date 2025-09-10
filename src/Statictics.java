
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
    private HashSet<String> notExistPages = new HashSet<>();

    private HashMap<String, Integer> browserCount = new HashMap<>();


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

        if (entry.getCodeResponce() == 404) {
            notExistPages.add(entry.getPath());
        }

        String os = entry.getUserAgent().getOsType();
        statOs.put(os, statOs.getOrDefault(os, 0) + 1);

        String browser = entry.getUserAgent().getBrowserType();
        browserCount.put(browser, browserCount.getOrDefault(browser, 0) + 1);

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

    public HashSet<String> getNotExistPages() {
        return notExistPages;
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

    public HashMap<String, Double> getProportionBrowser() {
        HashMap<String, Double> browserResult = new HashMap<>();
        int totalAllBrowser = 0;
        for (int countAllBrowserValue : browserCount.values()) {
            totalAllBrowser += countAllBrowserValue;
        }
        if (totalAllBrowser == 0) {
            return browserResult;
        }

        for (String browserKey : browserCount.keySet()) {
            int countBrowserKey = browserCount.get(browserKey);
            double finalBrowserResult = (double) countBrowserKey / totalAllBrowser;
            browserResult.put(browserKey, finalBrowserResult);
        }
        return browserResult;
    }
}
