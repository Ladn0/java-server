package org.webserver.http;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {
    private static final Pattern httpVersionRegexPattern = Pattern.compile("^HTTP/(?<major>\\d+)\\.(?<minor>\\d+)");
    private HttpMethod method;
    private String requestTarget;
    private SupportedHttpVersion httpVersion;

    public HttpRequest() {
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(String method) throws HttpParsingException {
        for(HttpMethod httpMethod : HttpMethod.values()){
                if (method.equals(httpMethod.name())){
                    this.method = httpMethod;
                    return;
                }
        }
        throw new HttpParsingException(
                HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED
        );
    }

    public String getRequestTarget() {
        return requestTarget;
    }

    public void setRequestTarget(String requestTarget) {
        this.requestTarget = requestTarget;
    }

    public SupportedHttpVersion getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) throws HttpParsingException {
        Matcher matcher = httpVersionRegexPattern.matcher(httpVersion);
        if (!matcher.find()){
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_505_NOT_SUPPORTED);
        }
        int major = Integer.parseInt(matcher.group("major"));
        for (SupportedHttpVersion sVersion : SupportedHttpVersion.values()){
            if (httpVersion.equals(sVersion.VERSION)){
                this.httpVersion = sVersion;
                return;
            }else if (major == sVersion.MAJOR){
                this.httpVersion = sVersion;
                return;
            }
        }
        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_505_NOT_SUPPORTED);
    }
}
