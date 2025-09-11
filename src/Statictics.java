
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

    private HashSet<String> uniqueRealUserIPs = new HashSet<>();

    private int totalRequests = 0;
    private int googleBotCount = 0;
    private int yandexBotCount = 0;
    private int isNotBotCount = 0;
    private int errorRequestCount = 0;


    public void addEntry(LogEntry entry) {
        totalRequests++;
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

        UserAgent ua = entry.getUserAgent();

        if (ua.isGoogleBot()) {
            googleBotCount++;
        }
        if (ua.isYandexBot()) {
            yandexBotCount++;
        }
        if (!ua.getOriginal().toLowerCase().contains("bot")) {
            isNotBotCount++;
        }

        int code = entry.getCodeResponce();
        if (code >= 400 || code < 600) {
            errorRequestCount++;

            String ip = entry.getIp();
            uniqueRealUserIPs.add(ip);
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

    public int getGoogleBotCount() {
        return googleBotCount;
    }

    public int getYandexBotCount() {
        return yandexBotCount;
    }

    public int getTotalRequests() {
        return totalRequests;
    }


    public double getAvgVisitsHour() {
        if (minTime == null || maxTime == null) {
            return 0;
        }
        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        if (hours == 0) {
            hours = 1;
        }
        return isNotBotCount / (double) hours;
    }

    public double getAvgErrorRequestsPerHour() {
        if (minTime == null || maxTime == null) {
            return 0;
        }
        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        if (hours == 0) {
            hours = 1;
        }
        return errorRequestCount / (double) hours;
    }

    public double getAvgRequestOneUser() {
        if (uniqueRealUserIPs.isEmpty()) {
            return 0;
        }
        return (double) isNotBotCount / uniqueRealUserIPs.size();
    }


    public void printStatistics() {
        System.out.println("Общее число запросов: " + totalRequests);
        System.out.printf("Доля Googlebot: %.2f%%\n", (googleBotCount * 100.0 / totalRequests));
        System.out.printf("Доля YandexBot: %.2f%%\n", (yandexBotCount * 100.0 / totalRequests));
        System.out.printf("Средний трафик в час: %.2f байт\n", getTrafficRate());

        System.out.println("Доля ОС в общем количестве:");
        getProportionOs().forEach((key, value) -> System.out.println(key + ": " + String.format("%.2f", value)));

        System.out.println("Доля браузеров в общем количестве:");
        getProportionBrowser().forEach((key, value) -> System.out.println(key + ": " + String.format("%.2f", value)));
        System.out.println("Среднее количество пользователей в час: " + String.format("%.2f", getAvgVisitsHour()));
        System.out.println("Среднее количество неуспешных запросов в час: " + String.format("%.2f", getAvgErrorRequestsPerHour()));
        System.out.println("Средняя посещаемость одним пользователем: " + String.format("%.2f", getAvgRequestOneUser()));

    }
}
