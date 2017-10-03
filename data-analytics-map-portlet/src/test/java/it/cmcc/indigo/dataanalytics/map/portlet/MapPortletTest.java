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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLStreamHandlerFactory;

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
        /*InputStream is = new ByteArrayInputStream("tasks: [test data]"
                .getBytes());
        Mockito.when(con.getInputStream()).thenReturn(is);*/
        
        MapPortlet mp = new MapPortlet();
        mp.serveResource(resourceRequest, resourceResponse);
    }

}
