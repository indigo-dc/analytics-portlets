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

package it.cmcc.indigo.dataanalytics.portlet;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Main class for MonitoringPortlet test.
 */
@RunWith(MockitoJUnitRunner.class)
public class MonitoringPortletTest {

    /**
     * Fake request for serveResource method.
     */
    @Mock
    private ResourceRequest request;

    /**
     * Fake response for serveResource method.
     */
    @Mock
    private ResourceResponse response;

    /**
     * Fake portlet class for serveResource method.
     */
    @Mock
    private MonitoringPortlet mp;

    /**
     * Prepare the environment.
     * @throws Exception In case of a problem to replicate Liferay context.
     */
    @Before
    public final void setUp() throws Exception {
        Mockito.when(request.getParameter("token")).thenReturn("token");
    }

    /**
     * Test the portlet.
     * @throws PortletException 
     * @throws Exception In case of problem
     */
    @Test
    public final void testResourceRequest() throws IOException, PortletException {
        Mockito.when(request.getParameter("token")).thenReturn(Mockito.anyString());
        mp = new MonitoringPortlet();
        mp.serveResource(request, response);
    }
}
