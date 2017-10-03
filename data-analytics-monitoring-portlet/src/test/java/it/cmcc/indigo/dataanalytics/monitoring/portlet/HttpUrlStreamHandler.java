package it.cmcc.indigo.dataanalytics.monitoring.portlet;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link URLStreamHandler} that allows us to control the {@link URLConnection
 * URLConnections} that are returned by {@link URL URLs} in the code under
 * test.
 */
public class HttpUrlStreamHandler extends URLStreamHandler {

    /**
     * Map reference between URL and HttpURLConnection.
     */
    private Map<URL, HttpURLConnection> connections =
            new HashMap<URL, HttpURLConnection>();

    /**
     * openConnection method.
     */
    @Override
    protected final HttpURLConnection openConnection(final URL url)
            throws IOException {
        return connections.get(url);
    }

    /**
     * resetConnection method.
     */
    public final void resetConnections() {
        connections = new HashMap<URL, HttpURLConnection>();
    }

    /**
     * addConnection method.
     * @param url The URL of the connection
     * @param urlConnection The HttpURLConnection
     * @return HttpUrlStreamHandler
     */
    public final HttpUrlStreamHandler addConnection(final URL url,
            final HttpURLConnection urlConnection) {
        connections.put(url, urlConnection);
        return this;
    }
}
