package com.nio.http.handler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nio.http.Request;
import com.nio.http.Response;
import com.nio.http.parser.RequestParser;

/**
 * Handler to handle http requests. When a request for a page or url is
 * received, we parse the raw string request into a request object and serve the
 * request accordingly. If the page is not found, then return a 404 default
 * page. If the user does not have access to the page, then return a 403
 * response. Otherwise try to honor the request
 *
 * @author Jay Paulynice (jay.paulynice@gmail.com)
 */
@Component
public class RequestHandler {
    /** base path for the public folder */
    private static final String BASE_SERVER_URI = "htdocs";

    /** page not found url */
    private static final String PAGE_NOT_FOUND_URI = "htdocs/404.html";

    /** parser to transform raw string request into request object */
    private final RequestParser requestParser;

    @Autowired
    public RequestHandler(final RequestParser requestParser) {
        this.requestParser = requestParser;
    }

    /**
     * Handle the request by first parsing the string then evaluating the
     * request method and uri
     *
     * @param rawRequest the raw request string
     * @return corresponding response
     */
    public Response handle(final String rawRequest) {
        final Request req = requestParser.parse(rawRequest);

        return handleRequest(req);
    }

    private Response handleRequest(final Request req) {
        Response res = new Response(Response.Status.OK);

        switch (req.getMethod()) {
        case GET:
            res = handleGetReq(req);
        case CONNECT:
            // not implemented yet
            break;
        case DELETE:
            // not implemented yet
            break;
        case HEAD:
            // not implemented yet
            break;
        case OPTIONS:
            // not implemented yet
            break;
        case PATCH:
            // not implemented yet
            break;
        case POST:
            // not implemented yet
            break;
        case PUT:
            // not implemented yet
            break;
        case TRACE:
            // not implemented yet
            break;
        default:
            break;
        }

        return res;
    }

    private Response handleGetReq(final Request req) {
        Response res = new Response(Response.Status.OK);
        try {
            res.setContent(Files.readAllBytes(Paths.get(BASE_SERVER_URI
                    + req.getUri())));
        } catch (final IOException e) {
            // should return 404 only if the page is not found. could be also be
            // a 500 internal server error
            res = get404Response();
        }

        return res;
    }

    private Response get404Response() {
        final Response res = new Response(Response.Status.NOT_FOUND);
        try {
            Files.readAllBytes(Paths.get(PAGE_NOT_FOUND_URI));
        } catch (final IOException e1) {
            // something is really wrong return 500 internal server error then
        }

        return res;
    }
}
