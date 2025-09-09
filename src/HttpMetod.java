public enum HttpMetod {
    GET,
    POST,
    PUT,
    DELETE,
    PATCH,
    HEAD,
    OPTIONS;

    public static boolean isValid(String method) {
        for (HttpMetod m : values()) {
            if (m.name().equalsIgnoreCase(method)) {
                return true;
            }

        }
        return false;
    }
}
