package org.webserver.http;

public enum SupportedHttpVersion {
    HTTP_1_1("HTTP/1.1", 1, 1);

    public final String VERSION;
    public final int MAJOR;
    public final int MINOR;

    SupportedHttpVersion(String VERSION, int MAJOR, int MINOR) {
        this.VERSION = VERSION;
        this.MAJOR = MAJOR;
        this.MINOR = MINOR;
    }
}
