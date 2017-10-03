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

package it.cmcc.indigo.dataanalytics.monitoring.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

/**
 * Main class for the Future Gateway tasks monitoring.
 */
@Component(
    immediate = true,
    property = {
        "com.liferay.portlet.display-category=Data Analytics",
        "com.liferay.portlet.instanceable=true",
        "javax.portlet.name=monitoring",
        "javax.portlet.display-name=Experiments monitoring",
        "javax.portlet.init-param.template-path=/",
        "javax.portlet.init-param.view-template=/view.jsp",
        "javax.portlet.resource-bundle=content.Language",
        "javax.portlet.security-role-ref=power-user,user"
    },
    service = Portlet.class
)

public class MonitoringPortlet extends MVCPortlet {

    /**
     * The reference to the token.
     */
    private String token = null;

    @Override
    public final void serveResource(final ResourceRequest resourceRequest,
        final ResourceResponse resourceResponse) throws IOException,
        PortletException {

        token = resourceRequest.getParameter("token");

        URL obj = new URL("https://fgw01.ncg.ingrid.pt/apis/v1.0/tasks");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + token);
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL: " + obj.toString());
        System.out.println("Response Code : " + responseCode);
        
        BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String jsonResponse = response.toString();
        int index = jsonResponse.indexOf("[");
        jsonResponse = jsonResponse.substring(index);
        jsonResponse = jsonResponse.substring(0, jsonResponse.length() - 1);
        resourceResponse.getWriter().write(jsonResponse);
    }
}
