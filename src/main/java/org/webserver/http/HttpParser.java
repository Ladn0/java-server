package org.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {

    private static final int SP = 0x20;
    private static final int CR = 0x0D;
    private static final int LF = 0x0A;

    public HttpRequest parseHttpRequest(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        HttpRequest httpRequest = new HttpRequest();

        parseRequestLine(inputStreamReader, httpRequest);
        return httpRequest;
    }

    private void parseRequestLine(InputStreamReader streamReader, HttpRequest httpRequest) throws IOException {
        StringBuilder sb = new StringBuilder();

        boolean methodParsed = false;
        boolean targetParsed = false;

        int _byte;
        while ((_byte = streamReader.read()) >= 0){
            if(_byte == CR){
                if ((_byte = streamReader.read()) == LF){
                    httpRequest.setHttpVersion(sb.toString());
                    return;
                }
            }
            if(_byte == SP){
                if (!methodParsed){
                    httpRequest.setMethod(sb.toString());
                    methodParsed = true;
                }else if(!targetParsed){
                    httpRequest.setRequestTarget(sb.toString());
                    targetParsed = true;
                }
                sb.delete(0, sb.length());
            }else{
                sb.append((char) _byte);
            }
        }

    }

}
