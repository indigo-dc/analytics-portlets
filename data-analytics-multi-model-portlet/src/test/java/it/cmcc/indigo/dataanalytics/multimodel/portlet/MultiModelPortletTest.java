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

package it.cmcc.indigo.dataanalytics.multimodel.portlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLStreamHandlerFactory;
import java.nio.file.Path;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import it.cmcc.indigo.dataanalytics.multimodel.portlet.MultiModelPortlet;
import it.cmcc.indigo.dataanalytics.multimodel.utility.HttpUrlStreamHandler;

/**
 * Main class for multi-model submission portlet test.
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class MultiModelPortletTest { 
        
     /**
     * Fake request for submitExperiment method.
     */
    @Mock
    private ActionRequest request;

     /**
     * Fake response for submitExperiment method.
     */
    @Mock
    private ActionResponse response; 
    
    private static HttpUrlStreamHandler httpUrlStreamHandler;
    
    @BeforeClass
    public static void setupURLStreamHandlerFactory() {
        // Allows for mocking URL connections
        URLStreamHandlerFactory urlStreamHandlerFactory = Mockito.mock(URLStreamHandlerFactory.class);
        URL.setURLStreamHandlerFactory(urlStreamHandlerFactory);
     
        httpUrlStreamHandler = new HttpUrlStreamHandler();
        Mockito.when(urlStreamHandlerFactory.createURLStreamHandler("https")).thenReturn(httpUrlStreamHandler);
    }
    
    @Before
    public void reset() {
        httpUrlStreamHandler.resetConnections();
    }
    
    /**
     * Test the portlet.
     * @throws Exception in case of problem
     */
    @Test(expected=Exception.class)
    public final void testSubmitExperiment() throws Exception {
        Mockito.when(request.getParameter("token")).thenReturn("token"); 
        Mockito.when(request.getParameter("modelsString")).thenReturn("modelsString");
        Mockito.when(request.getParameter("scenario")).thenReturn("scenario");
        Mockito.when(request.getParameter("time_frequency")).thenReturn("timeFrequency");
        Mockito.when(request.getParameter("historical_time_min")).thenReturn("historicalTimeMin");
        Mockito.when(request.getParameter("historical_time_max")).thenReturn("historicalTimeMax");
        Mockito.when(request.getParameter("scenario_time_min")).thenReturn("scenarioTimeMin");
        Mockito.when(request.getParameter("scenario_time_max")).thenReturn("scenarioTimeMax");
        Mockito.when(request.getParameter("latmin")).thenReturn("latmin");
        Mockito.when(request.getParameter("latmax")).thenReturn("latmax");
        Mockito.when(request.getParameter("lonmin")).thenReturn("lonmin");
        Mockito.when(request.getParameter("lonmax")).thenReturn("lonmax");
        
        String href = "https://fgw01.ncg.ingrid.pt/apis/v1.0/tasks";
        HttpURLConnection con = Mockito.mock(HttpURLConnection.class);
        httpUrlStreamHandler.addConnection(new URL(href), con);
        InputStream is = new ByteArrayInputStream("tasks: [test data]".getBytes());
        Mockito.when(con.getInputStream()).thenReturn(is);
        
        MultiModelPortlet multiModelPortlet = new MultiModelPortlet(); 
        
        multiModelPortlet.submitExperiment(request, response);                       
    }
    
/*    @Test
    public final void testgetAppID() throws Exception {
        MultiModelPortlet multiModelPortlet = new MultiModelPortlet(); 
        
        String href = "https://fgw01.ncg.ingrid.pt/apis/v1.0/tasks";
        HttpURLConnection con = Mockito.mock(HttpURLConnection.class);
        httpUrlStreamHandler.addConnection(new URL(href), con);
        InputStream is = new ByteArrayInputStream("tasks: [test data]".getBytes());
        Mockito.when(con.getInputStream()).thenReturn(is);
        

        multiModelPortlet.getAppID("kepler-batch");
    }*/
    
/*    @Test
    public final void testgetAppIDWithIncorrectValue() throws Exception {
        MultiModelPortlet multiModelPortlet = new MultiModelPortlet(); 
        HttpURLConnection con =  Mockito.mock(HttpURLConnection.class);
        Mockito.when(con.getResponseCode()).thenReturn(500);
        multiModelPortlet.getAppID("kepler-batch");
    }*/
    
/*    @Test
    public final void testNewFGTask() throws Exception {
        
        MultiModelPortlet multiModelPortlet = new MultiModelPortlet();
        HttpURLConnection con2 =  Mockito.mock(HttpURLConnection.class);
        InputStream in = Mockito.mock(InputStream.class);
        
        Mockito.when(con2.getResponseCode()).thenReturn(200);
        Mockito.when(con2.getInputStream()).thenReturn(in);  
        
        multiModelPortlet.newFGTask(3);
    }*/
    
/*    @Test
    public final void testCreateParametersFile() {
       
        MultiModelPortlet multiModelPortlet = new MultiModelPortlet();
        Path tmpPath = Mockito.mock(Path.class);
        
        multiModelPortlet.createParametersFile(300, tmpPath);    
    }
    
    @Rule
    public TemporaryFolder folder= new TemporaryFolder();
    
    @Test
    public final void testSendTaskInputFile() throws Exception {
        MultiModelPortlet multiModelPortlet = new MultiModelPortlet();
        
        File uploadFile1 = folder.newFile("fileName1.txt");
        File uploadFile2 = folder.newFile("fileName2.txt");
            
        multiModelPortlet.sendTaskInputFile(300, uploadFile1, uploadFile2);     
    }  */ 
}
