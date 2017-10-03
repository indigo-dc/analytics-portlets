/**
 * *********************************************************************
 * Copyright (c) 2017: Euro Mediterranean Center on Climate Change (CMCC)
 * Foundation - INDIGO-DataCloud
 *
 * See http://www.cmcc.it for details on the copyright holders.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 **********************************************************************
 */

package it.cmcc.indigo.dataanalytics.map.portlet;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLStreamHandlerFactory;

import javax.imageio.ImageIO;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Main class for MapPortlet test.
 */
@RunWith(MockitoJUnitRunner.class)
public class MapPortletTest {

	/**
     * Fake request for serveResource method.
     */
    @Mock
    private ResourceRequest resourceRequest;

    /**
     * Fake response for serveResource method.
     */
    @Mock
    private ResourceResponse resourceResponse;

    /**
     * Fake httpUrlStreamHandler for serveResource method.
     */
    private static HttpUrlStreamHandler httpUrlStreamHandler;
    
    /**
     * Setup of the mocking URL connections.
     */
    @BeforeClass
    public static void setupURLStreamHandlerFactory() {
        // Allows for mocking URL connections
        URLStreamHandlerFactory urlStreamHandlerFactory = Mockito
                .mock(URLStreamHandlerFactory.class);
        URL.setURLStreamHandlerFactory(urlStreamHandlerFactory);

        httpUrlStreamHandler = new HttpUrlStreamHandler();
        Mockito.when(urlStreamHandlerFactory.createURLStreamHandler("https")
                ).thenReturn(httpUrlStreamHandler);
    }

    /**
     * Prepare the environment.
     */
    @Before
    public final void reset() {
        httpUrlStreamHandler.resetConnections();
    }
    
    /**
     * Test the portlet.
     * @throws IOException On input error
     */
    @Test
    public final void testServeResource() throws IOException {
    	Mockito.when(resourceRequest.getParameter("token")).thenReturn("token");
    	Mockito.when(resourceRequest.getParameter("taskid")).thenReturn("taskid");
    	
    	String href = "https://fgw01.ncg.ingrid.pt/apis/v1.0/tasks/taskid";
        HttpURLConnection con = Mockito.mock(HttpURLConnection.class);
        httpUrlStreamHandler.addConnection(new URL(href), con);
        String st = "{";
        st += "\"status\": \"DONE\",";
        st += "\"description\": \"precip_trend_analysis_ensemble\",";
        st += "\"creation\": \"2017-09-19T11:33:41Z\",";
        st += "\"iosandbox\": \"/tmp/62ddd314-9d2e-11e7-82b9-fa163ef43b50\",";
        st += "\"user\": \"fd51c6f6-5598-44e6-a554-9f8752ddea12\",";
        st += "\"id\": \"675\",";
        st += "\"output_files\": [";
        st += "{";
        st += "\"url\": \"file?path=%2Ftmp%2F62ddd314-9d2e-11e7-82b9-fa163ef43b50%2F675tmp62ddd3149d2e11e782b9fa163ef43b50_446&name=avg.png\",";
     
        InputStream is = new ByteArrayInputStream(st.getBytes());
        Mockito.when(con.getInputStream()).thenReturn(is);
        
        String href2 = "https://fgw01.ncg.ingrid.pt/apis/v1.0/file?path=%2Ftmp%2F62ddd314-9d2e-11e7-82b9-fa163ef43b50%2F675tmp62ddd3149d2e11e782b9fa163ef43b50_446&name=avg.png";
        HttpURLConnection con2 = Mockito.mock(HttpURLConnection.class);
        httpUrlStreamHandler.addConnection(new URL(href2), con2);
        
        InputStream is2 = new URL("http://www.eticamente.net/wp-content/uploads/2016/11/gatto-1024x768.png").openStream();
        Mockito.when(con2.getInputStream()).thenReturn(is2);
        
        PrintWriter pw = Mockito.mock(PrintWriter.class);
        Mockito.when(resourceResponse.getWriter()).thenReturn(pw);
        
        MapPortlet mp = new MapPortlet();
        mp.serveResource(resourceRequest, resourceResponse);
    }

}
