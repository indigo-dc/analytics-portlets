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

package it.cmcc.indigo.dataanalytics.monitoring.portlet.action;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLStreamHandlerFactory;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.liferay.portal.kernel.util.ParamUtil;

/**
 * Main class for GetOutputPathMVCActionCommand action.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetOutputPathMVCActionCommandTest {

    /**
     * Fake request for doProcessAction method.
     */
    @Mock
    private ActionRequest actionRequest;

    /**
     * Fake response for doProcessAction method.
     */
    @Mock
    private ActionResponse actionResponse;

    /**
     * Fake httpUrlStreamHandler for doProcessAction method.
     */
    private static HttpUrlStreamHandler httpUrlStreamHandler;

    /**
     * Setup of the mocking URL connections.
     */
    @BeforeClass
    public static void setupURLStreamHandlerFactory() {
        // Allows for mocking URL connections
        URLStreamHandlerFactory urlStreamHandlerFactory =
                Mockito.mock(URLStreamHandlerFactory.class);
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
     * Test the action portlet.
     * @throws Exception In case of problem
     */
    @Test
    public final void testDoProcessAction() throws Exception {
        Mockito.when(ParamUtil.getString(actionRequest, "token")
                ).thenReturn("token");
        Mockito.when(ParamUtil.getString(actionRequest, "taskid")
                ).thenReturn("0");

        String href = "https://fgw01.ncg.ingrid.pt/apis/v1.0/tasks/0";
        HttpURLConnection con = Mockito.mock(HttpURLConnection.class);
        httpUrlStreamHandler.addConnection(new URL(href), con);

        GetOutputPathMVCActionCommand action =
                new GetOutputPathMVCActionCommand();
        action.doProcessAction(actionRequest, actionResponse);
    }
}
