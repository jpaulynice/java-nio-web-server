package com.nio.http.handler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Map;

import com.nio.http.Response;

/**
 * Simple object to provide reading and writing between the client and the
 * server.
 *
 * @author Jay Paulynice (jay.paulynice@gmail.com)
 */
public class SelectorHandler {
    /** Current suppported http version */
    private static final String VERSION = "HTTP/1.1";

    /** Encoder to translate string into bytes */
    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    private final SocketChannel channel;
    private final ByteBuffer buffer;
    private final StringBuilder linesRead;
    private int mark;

    public SelectorHandler(final SocketChannel channel) {
        this.channel = channel;
        buffer = ByteBuffer.allocate(8192);
        linesRead = new StringBuilder();
    }

    /**
     * @return the linesRead
     */
    public StringBuilder getLinesRead() {
        return linesRead;
    }

    public String readLine() {
        final StringBuilder sb = new StringBuilder();
        int l = -1;
        while (buffer.hasRemaining()) {
            final char c = (char) buffer.get();
            sb.append(c);
            if (c == '\n' && l == '\r') {
                mark = buffer.position();
                linesRead.append(sb);
                return sb.substring(0, sb.length() - 2);
            }
            l = c;
        }
        return null;
    }

    public void readData() throws IOException {
        buffer.limit(buffer.capacity());
        final int read = channel.read(buffer);
        if (read == -1) {
            throw new IOException("End of stream");
        }
        buffer.flip();
        buffer.position(mark);
    }

    public void writeData(final Response response) {
        response.defaultHeaders();
        try {
            writeLine(VERSION + " " + response.getStatus().getStatusCode()
                    + " " + response.getStatus().getReason());
            for (final Map.Entry<String, String> header : response.getHeaders()
                    .entrySet()) {
                writeLine(header.getKey() + ": " + header.getValue());
            }
            writeLine("");
            if (response.getContent() != null) {
                channel.write(ByteBuffer.wrap(response.getContent()));
            }
        } catch (final IOException e) {
            throw new RuntimeException("Exception while sending response.", e);
        }
    }

    private void writeLine(final String line) {
        try {
            channel.write(UTF8_CHARSET.newEncoder().encode(
                    CharBuffer.wrap(line + "\r\n")));
        } catch (final IOException e) {
            throw new RuntimeException("Exception writing to client.", e);
        }
    }

    /**
     * close the channel
     */
    public void close() {
        try {
            channel.close();
        } catch (final IOException e) {
            throw new RuntimeException("Exception closing the channel.", e);
        }
    }
}
