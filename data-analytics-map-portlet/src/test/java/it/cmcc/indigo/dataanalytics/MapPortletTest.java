/**
 * *********************************************************************
 * Copyright (c) 2017: Euro Mediterranean Center on Climate Change (CMCC) Foundation -
 * INDIGO-DataCloud
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
 * 
 **********************************************************************
 */

package it.cmcc.indigo.dataanalytics;

import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MapPortletTest {
	
	@Mock
	private EventRequest request;
	
	@Mock
	private EventResponse response;
	
	@Mock
	private Event event;
	
	/**
     * Prepare the environment.
     * @throws Exception In case of a problem to replicate Liferay context
     */
    @Before
    public final void setUp() throws Exception {
    	Mockito.when(request.getEvent()).thenReturn(event);
    }
    
    /**
     * Test the portlet.
     * @throws Exception In case of problem
     */
    @Test
    public final void testGetPath() throws Exception {
    	Mockito.when(event.getValue()).thenReturn("url|token");
    	MapPortlet map = new MapPortlet();
    	map.getPath(request, response);
    }
}
