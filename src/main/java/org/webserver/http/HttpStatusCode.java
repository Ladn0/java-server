package org.webserver.http;

public enum HttpStatusCode {
    CLIENT_ERROR_400_BAD_REQUEST(400, "Bad Request"),
    CLIENT_ERROR_401_METHOD_NOT_ALLOWED(401, "Method Not Allowed"),

    SERVER_ERROR_501_NOT_IMPLEMENTED(501,"Method Not Implemented"),
    SERVER_ERROR_505_NOT_SUPPORTED(505,"HTTP Version Not Supported");

    public final int STATUS_CODE;
    public final String MESSAGE;

    HttpStatusCode(int STATUS_CODE, String MESSAGE) {
        this.STATUS_CODE = STATUS_CODE;
        this.MESSAGE = MESSAGE;
    }
}
