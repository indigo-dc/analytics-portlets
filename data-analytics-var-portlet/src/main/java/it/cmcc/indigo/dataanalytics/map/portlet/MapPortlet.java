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

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.portlet.Portlet;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.xml.bind.DatatypeConverter;

import org.osgi.service.component.annotations.Component;


/**
 * Main portlet for the Map visualization.
 */
@Component(
    immediate = true,
    property = {
        "com.liferay.portlet.display-category=Data Analytics",
        "com.liferay.portlet.instanceable=true",
        "javax.portlet.name=varmap",
        "javax.portlet.display-name=Variance Map",
        "javax.portlet.init-param.template-path=/",
        "javax.portlet.init-param.view-template=/view.jsp",
        "javax.portlet.resource-bundle=content.Language",
        "javax.portlet.security-role-ref=power-user,user"
    },
    service = Portlet.class
)

public class MapPortlet extends MVCPortlet {

    /**
     * The reference to the token.
     */
    private String token = null;

    /**
     * The reference to the taskid.
     */
    private String taskid = null;

    @Override
    public final void serveResource(final ResourceRequest resourceRequest,
            final ResourceResponse resourceResponse) throws IOException {

        token = resourceRequest.getParameter("token");
        taskid = resourceRequest.getParameter("taskid");

        String filename = "&name=var.png";

        String b64 = "";
        String url = "";

        URL obj = new URL("https://fgw01.ncg.ingrid.pt/apis/v1.0/tasks/"
                + taskid);
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

        String myObject = response.toString();
        int start = myObject.indexOf("file?path=");
        int end = myObject.indexOf("&name=avg.png");
        url = myObject.substring(start, end);

        URL obj2 = new URL("https://fgw01.ncg.ingrid.pt/apis/v1.0/"
             + url + filename);
        HttpURLConnection con2 = (HttpURLConnection) obj2.openConnection();
        con2.setRequestMethod("GET");
        con2.setRequestProperty("Authorization", "Bearer " + token);
        int responseCode2 = con2.getResponseCode();
        System.out.println("\nSending 'GET' request to URL: "
             + obj2.toString());
        System.out.println("Response Code : " + responseCode2);

        BufferedImage image = ImageIO.read(con2.getInputStream());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        byte[] imageInByteArray = baos.toByteArray();
        baos.close();
        b64 = DatatypeConverter.printBase64Binary(imageInByteArray);

        resourceResponse.getWriter().write(b64);
    }
}
