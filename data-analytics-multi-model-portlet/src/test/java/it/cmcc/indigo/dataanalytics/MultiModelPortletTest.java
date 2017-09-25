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

package it.cmcc.indigo.dataanalytics;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import it.cmcc.indigo.dataanalytics.portlet.MultiModelPortlet;

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
    
    /**
     * Fake request for http.
     */
    @Mock
    private HttpServletRequest httpRequest;
    
    /**
     * Fake portal util.
     */
    @Mock
    private Portal portalImpl;
    
    /**
     * Prepare the environment.
     * @throws Exception in case of a problem to replicate Liferay context
     */
    @Before
    public final void setUp() throws Exception {
        Mockito.when(portalImpl.getHttpServletRequest(
           Mockito.any(PortletRequest.class))).thenReturn(httpRequest);
    }
    
     /**
     * Test the portlet.
     * @throws Exception in case of problem
     */
    @Test
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
        
        MultiModelPortlet multiModelPortlet = new MultiModelPortlet(); 
        multiModelPortlet.submitExperiment(request, response);
                        
    }
    
/*    @Test
    public final void testgetAppID() throws Exception {
        multiModelPortlet = new MultiModelPortlet();

        Mockito.when(multiModelPortlet.getAppID("kepler-batch")).thenReturn(Mockito.anyInt());
        
        //assertEquals(multiModelPortlet.getAppID("kepler-batch"), 201);
    }*/
    
/*    @Test
    public final void testNewFGTask() throws Exception {
        multiModelPortlet = new MultiModelPortlet();
        //Integer appid = org.mockito.Mockito.mock(Integer.class);
        //multiModelPortlet.newFGTask(appid);
       
        //Mockito.when(multiModelPortlet.newFGTask(idapp)).thenReturn(idtask);
    }*/
    
/*    @Test
    public final void testCreateParametersFile() throws Exception {
        multiModelPortlet = new MultiModelPortlet();
        
        //multiModelPortlet.createParametersFile(multiModelPortlet.getAppID("appname"), tmpPath);
        
        //Mockito.when(multiModelPortlet.createParametersFile(multiModelPortlet.getAppID("appname"), tmpPath)).thenReturn(uploadFile1);
    }*/
    
/*    @Test
    public final void testSendTaskInputFile() throws Exception {
        multiModelPortlet = new MultiModelPortlet();
        
    }*/
    
}
