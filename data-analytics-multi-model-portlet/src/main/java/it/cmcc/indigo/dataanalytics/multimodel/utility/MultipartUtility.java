package it.cmcc.indigo.dataanalytics.multimodel.utility;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * This utility class provides an abstraction layer for sending multipart HTTP
 * POST requests to a web server.
 * @author www.codejava.net
 *
 */
public class MultipartUtility {
    /**
    * Reference to the boundary.
    */
    private final String boundary;

    /**
    * Reference to the tLINE_FEED.
    */
    private static final String LINE_FEED = "\r\n";

    /**
    * Reference to the HTTP connection.
    */
    private HttpURLConnection httpConn;

    /**
    * Reference to the charset.
    */
    private String charset;

    /**
    * Reference to the outputStream.
    */
    private OutputStream outputStream;

    /**
    * Reference to the writer.
    */
    private PrintWriter writer;

    /**
    * Reference to the BUFFER_SIZE.
    */
    private static final int BUFFER_SIZE = 4096;

    /**
     * This constructor initializes a new HTTP POST request with content type.
     * is set to multipart/form-data
     * @param requestURL The request URL
     * @param charSet The charset
     * @param token The token of the session
     * @throws IOException on input error
     */
    public MultipartUtility(final String requestURL,
            final String charSet, final String token)
            throws IOException {
        this.charset = charSet;

        // creates a unique boundary based on time stamp
        boundary = "===" + System.currentTimeMillis() + "===";

        URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true); // indicates POST method
        httpConn.setDoInput(true);
        httpConn.setRequestProperty("Authorization", "Bearer " + token);
        httpConn.setRequestProperty("Content-Type",
                "multipart/form-data; boundary=" + boundary);
        outputStream = httpConn.getOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                true);
    }

    /**
     * Adds a upload file section to the request.
     * @param fieldName name attribute in <input type="file" name="..." />
     * @param uploadFile a File to be uploaded
     * @throws IOException on input error
     */
    public final void addFilePart(final String fieldName, final File uploadFile)
            throws IOException {
        String fileName = uploadFile.getName();
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append(
                "Content-Disposition: form-data; name=\"" + fieldName
                        + "\"; filename=\"" + fileName + "\"")
                .append(LINE_FEED);
        writer.append(
                "Content-Type: "
                        + URLConnection.guessContentTypeFromName(fileName))
                .append(LINE_FEED);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();

        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        inputStream.close();

        writer.flush();
    }

    /**
     * Completes the request and receives response from the server.
     * @return a list of Strings as response in case the server returned
     * status OK, otherwise an exception is thrown.
     * @throws IOException on input error
     */
    public final List<String> finish() throws IOException {
        try {
            writer.append(LINE_FEED).flush();
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();

            // checks server's status code first
            int status = httpConn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                return readResponse(httpConn.getInputStream());
            } else {
                final StringBuilder builder = new StringBuilder();
                builder.append("Server returned non-OK status: ").
                    append(status).append('\n');
                for (final String line : readResponse(
                        httpConn.getErrorStream())) {
                    builder.append(line).append('\n');
                }
                throw new IOException(builder.toString());
            }
            } finally {
                httpConn.disconnect();
        }
    }
    /**
     * Reads the response of the Future Gateway.
     * @param stream Input stream received from the server
     * @return a list of Strings as response
     * @throws IOException on input error
     */
    public final List<String> readResponse(final InputStream stream)
            throws IOException {
        List<String> response = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream));
        String line = null;
        while ((line = reader.readLine()) != null) {
            response.add(line);
        }
        reader.close();
        return response;
    }
}
