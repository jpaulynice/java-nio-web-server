package com.nio.http.parser;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.stereotype.Component;

import com.nio.http.Request;

/**
 * Parser to take a raw string request and turn it into a {@link Request}
 * object.
 *
 * @author Jay Paulynice (jay.paulynice@gmail.com)
 */
@Component
public class RequestParser {
    /** split request headers on carriage return and new line */
    private static final String SPLIT_REGEX = "\r\n";

    /** split header values with the colon */
    private static final String COLON_REGEX = ":";

    /** split header strings in 2 substrings after the first semicolon */
    private static final int COLON_REGEX_LIMIT = 2;

    /**
     * Parse given string into request object
     *
     * @param rawRequest the raw string
     * @return request object
     */
    public Request parse(final String rawRequest) {
        final StringTokenizer tokenizer = new StringTokenizer(rawRequest);

        final Request req = new Request();

        // these tokens refer to the example: GET /abc/index.html HTTP/1.1
        req.setMethod(Request.Method.lookup(tokenizer.nextToken()));
        req.setUri(tokenizer.nextToken());
        req.setVersion(tokenizer.nextToken());

        // parse the headers
        req.getHeaders().putAll(parseHeaders(rawRequest));

        return req;
    }

    private Map<String, String> parseHeaders(final String rawRequest) {
        final Map<String, String> headers = new LinkedHashMap<>();

        final String[] lines = rawRequest.split(SPLIT_REGEX);

        // we don't care about the first line since it was parsed already
        for (int i = 1; i < lines.length; i++) {
            // example - Host: localhost:9000
            final String[] keyVal = lines[i].split(COLON_REGEX,
                    COLON_REGEX_LIMIT);

            // string 1: Host
            final String header = keyVal[0];

            // string 2: localhost:9000
            final String value = keyVal[1];

            headers.put(header.trim(), value.trim());
        }

        return headers;
    }
}
