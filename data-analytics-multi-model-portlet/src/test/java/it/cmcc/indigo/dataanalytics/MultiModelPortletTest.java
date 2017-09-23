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


import java.io.File;
import java.nio.file.Path;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.liferay.portal.kernel.util.ParamUtil;

import it.cmcc.indigo.dataanalytics.portlet.MultiModelPortlet;

/**
 * Main class for multi-model submission portlet test.
 */
@RunWith(MockitoJUnitRunner.class)
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
     * Fake token for submitExperiment method.
     */
    @Mock
    private String token;
    
    /**
     * Fake models array for submitExperiment method.
     */   
    @Mock
    private String[] models;
    
    /**
     * Fake scenario array for submitExperiment method.
     */   
    @Mock
    private String scenario;
    
    /**
     * Fake timeFrequency array for submitExperiment method.
     */   
    @Mock
    private String timeFrequency;
    
    /**
     * Fake percentile array for submitExperiment method.
     */   
    @Mock
    private String percentile;
    
    /**
     * Fake historicalTimeMin array for submitExperiment method.
     */   
    @Mock
    private String historicalTimeMin;
    
    /**
     * Fake historicalTimeMax array for submitExperiment method.
     */   
    @Mock
    private String historicalTimeMax;
    
    /**
     * Fake scenarioTimeMin array for submitExperiment method.
     */   
    @Mock
    private String scenarioTimeMin;
    
    /**
     * Fake scenarioTimeMax array for submitExperiment method.
     */   
    @Mock
    private String scenarioTimeMax;
    
    /**
     * Fake latmin array for submitExperiment method.
     */   
    @Mock
    private String latmin;
    
    /**
     * Fake latmax array for submitExperiment method.
     */   
    @Mock
    private String latmax;
    
    /**
     * Fake lonmin array for submitExperiment method.
     */   
    @Mock
    private String lonmin;
    
    /**
     * Fake lonmax array for submitExperiment method.
     */   
    @Mock
    private String lonmax;
    
    /**
     * Fake portlet class for submitExperiment method.
     */   
    @Mock
    private MultiModelPortlet multiModelPortlet;
    
    /**
     * Fake idapp for submitExperiment method.
     */   
    @Mock
    private int idapp;
    
    /**
     * Fake idtask for submitExperiment method.
     */   
    @Mock
    private int idtask;
    
    /**
     * Fake path for createParametersFile method.
     */   
    @Mock
    private Path tmpPath;
    
    /**
     * Fake uploadFile1 for createParametersFile method.
     */   
    @Mock
    private File uploadFile1;
    
    /**
     * Fake uploadFile2 for createParametersFile method.
     */   
    @Mock
    private File uploadFile2;
    
    /**
     * Prepare the environment.
     * @throws Exception in case of a problem to replicate Liferay context
     */
    @Before
    public final void setUp() throws Exception {
        Mockito.when(ParamUtil.getString(request, "token")).thenReturn(token);
        Mockito.when(ParamUtil.getParameterValues(request, "model")).thenReturn(models);
        Mockito.when(ParamUtil.getString(request, "scenario")).thenReturn(scenario);
        Mockito.when(ParamUtil.getString(request, "time_frequency")).thenReturn(timeFrequency);
        Mockito.when(ParamUtil.getString(request, "historical_time_min")).thenReturn(historicalTimeMin);
        Mockito.when(ParamUtil.getString(request, "historical_time_max")).thenReturn(historicalTimeMax);
        Mockito.when(ParamUtil.getString(request, "scenario_time_min")).thenReturn(scenarioTimeMin);
        Mockito.when(ParamUtil.getString(request, "scenario_time_max")).thenReturn(scenarioTimeMax);
        Mockito.when(ParamUtil.getString(request, "latmin")).thenReturn(latmin);
        Mockito.when(ParamUtil.getString(request, "latmax")).thenReturn(latmax);
        Mockito.when(ParamUtil.getString(request, "lonmin")).thenReturn(lonmin);
        Mockito.when(ParamUtil.getString(request, "lonmax")).thenReturn(lonmax);
    }
    
    /**
     * Test the portlet.
     * @throws Exception in case of problem
     */
    @Test
    public final void testSubmitExperiment() throws Exception {
        multiModelPortlet = new MultiModelPortlet();
        
        Mockito.when(multiModelPortlet.getAppID("kepler-batch")).thenReturn(idapp);
        Mockito.when(multiModelPortlet.newFGTask(idapp)).thenReturn(idtask);
        Mockito.when(multiModelPortlet.createParametersFile(idtask, tmpPath)).thenReturn(uploadFile1);
        //Mockito.when(multiModelPortlet.sendTaskInputFile(idtask, uploadFile1, uploadFile2)).thenReturn();
    }
    
    @Test
    public final void testgetAppID() throws Exception {
        multiModelPortlet = new MultiModelPortlet();
        
        Mockito.when(multiModelPortlet.getAppID("kepler-batch")).thenReturn(idapp);
    }
    
    @Test
    public final void testNewFGTask() throws Exception {
        multiModelPortlet = new MultiModelPortlet();
        
        Mockito.when(multiModelPortlet.newFGTask(idapp)).thenReturn(idtask);
    }
    
    @Test
    public final void testCreateParametersFile() throws Exception {
        multiModelPortlet = new MultiModelPortlet();
        
        Mockito.when(multiModelPortlet.createParametersFile(idtask, tmpPath)).thenReturn(uploadFile1);
    }
}
