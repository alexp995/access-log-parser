import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class LogEntry {
    private final String ip;
    private final LocalDateTime dateTime;

    private final HttpMetod method;
    private final String path;
    private final int codeResponce;
    private final long dataSize;
    private final String referer;
    private final UserAgent userAgent;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

    public LogEntry(String logLine) {
        try {
            String[] parts = logLine.split(" ");
            this.ip = parts[0];

            int start = logLine.indexOf("[") + 1;
            int end = logLine.indexOf("]");
            String dateTime = logLine.substring(start, end);
            this.dateTime = LocalDateTime.parse(dateTime, formatter);

            int firstQuote = logLine.indexOf('"');
            int secondQuote = logLine.indexOf('"', firstQuote + 1);
            String request = logLine.substring(firstQuote + 1, secondQuote);
            String[] parts2 = request.split(" ");
            this.method = HttpMetod.valueOf(parts2[0]);
            this.path = parts2[1];

            String codeAndDataSize = logLine.substring(secondQuote + 2);
            String[] parts3 = codeAndDataSize.split(" ");
            this.codeResponce = Integer.parseInt(parts3[0]);
            this.dataSize = Integer.parseInt(parts3[1]);

            int firstRef = logLine.indexOf('"', secondQuote + 1);
            int secondRef = logLine.indexOf('"', firstRef + 1);
            this.referer = logLine.substring(firstRef + 1, secondRef);

            int firstAgent = logLine.indexOf('"', secondRef + 1);
            int secondAgent = logLine.indexOf('"', firstAgent + 1);
            String userAgentString = logLine.substring(firstAgent + 1, secondAgent);
            this.userAgent = new UserAgent(userAgentString);

        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка при парсинге строки" + logLine, e);
        }
    }

    public String getIp() {
        return ip;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getPath() {
        return path;
    }

    public int getCodeResponce() {
        return codeResponce;
    }

    public double getDataSize() {
        return dataSize;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }
}
