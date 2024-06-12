package org.webserver.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    private HttpParser httpParser;

    @BeforeAll
    public void beforeClass(){
        httpParser = new HttpParser();
    }

    @Test
    void parseHttpRequest(){
        HttpRequest httpRequest = null;
        try {
            httpRequest = httpParser.parseHttpRequest(generateValidGetTestCase());
        } catch (HttpParsingException e) {
            fail(e);
        }

        assertEquals(HttpMethod.GET, httpRequest.getMethod());
        assertEquals("/", httpRequest.getRequestTarget());
        assertEquals(SupportedHttpVersion.HTTP_1_1, httpRequest.getHttpVersion());
    }

    @Test
    void methodNotImplementedRequest(){
        HttpRequest httpRequest = null;
        try {
            httpRequest = httpParser.parseHttpRequest(generateMethodNotImplementedTestCase());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED, e.getStatusCode());
        }
    }

    @Test
    void httpVersionNotSupportedRequest(){
        HttpRequest httpRequest = null;
        try {
            httpRequest = httpParser.parseHttpRequest(generateHttpVersionNotSupportedTestCase());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(HttpStatusCode.SERVER_ERROR_505_NOT_SUPPORTED, e.getStatusCode());
        }
    }

    private InputStream generateValidGetTestCase(){
        String rawData = "GET / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Accept-Encoding: gzip, deflate, br, zstd\r\n" +
                "Accept-Language: en-GB,en;q=0.9,en-US;q=0.8,ru;q=0.7,de;q=0.6\r\n" + "\r\n";

        return new ByteArrayInputStream(rawData.getBytes(
                StandardCharsets.US_ASCII
        ));
    }

    private InputStream generateMethodNotImplementedTestCase(){
        String rawData = "GETer / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n";

        return new ByteArrayInputStream(rawData.getBytes(
                StandardCharsets.US_ASCII
        ));
    }

    private InputStream generateHttpVersionNotSupportedTestCase(){
        String rawData = "GET / HTTP/2.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n";

        return new ByteArrayInputStream(rawData.getBytes(
                StandardCharsets.US_ASCII
        ));
    }
}