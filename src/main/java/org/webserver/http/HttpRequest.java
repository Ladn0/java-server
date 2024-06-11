package org.webserver.http;

import java.util.Objects;

public class HttpRequest {
    private HttpMethod method;
    private String requestTarget;
    private String httpVersion;

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

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }
}
