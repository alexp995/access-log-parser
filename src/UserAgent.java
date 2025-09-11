public class UserAgent {
    private final String osType;
    private final String browserType;

    private final boolean isGoogleBot;
    private final boolean isYandexBot;

    public UserAgent(String userAgentString) {
        userAgentString = userAgentString.toLowerCase();
        isGoogleBot = userAgentString.contains("googlebot");
        isYandexBot = userAgentString.contains("yandexbot");


        if (userAgentString.contains("windows")) {
            osType = "Windows";
        } else if (userAgentString.contains("mac")) {
            osType = "MacOS";
        } else if (userAgentString.contains("linux")) {
            osType = "Linux";
        } else {
            osType = "Unknown";
        }

        if (userAgentString.contains("edg")) {
            browserType = "Edge";
        } else if (userAgentString.contains("chrome")) {
            browserType = "Chrome";
        } else if (userAgentString.contains("firefox")) {
            browserType = "Firefox";
        } else if (userAgentString.contains("opera") || userAgentString.contains("opr")) {
            browserType = "Opera";
        } else {
            browserType = "Unknown";
        }
    }

    public String getOsType() {
        return osType;
    }

    public String getBrowserType() {
        return browserType;
    }


    public boolean isGoogleBot() {
        return isGoogleBot;
    }

    public boolean isYandexBot() {
        return isYandexBot;
    }
}
