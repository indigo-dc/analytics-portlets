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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.xml.namespace.QName;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

/**
 * Main class for the Future Gateway tasks monitoring.
 */
@Component(
    immediate = true,
    property = {
        "javax.portlet.name=monitoring",
        "mvc.command.name=/enes/getOutputPath",
        "javax.portlet.supported-publishing-event=getpath;"
        + "http://cloud144.ncg.ingrid.pt"
    },
    service = MVCActionCommand.class
)

public class GetOutputPathMVCActionCommand extends BaseMVCActionCommand {

    /**
     * The reference to the token.
     */
    private String token = null;

    /**
     * The reference to the selected ensemble task ID.
     */
    private Integer taskid = null;

    @Override
    protected final void doProcessAction(final ActionRequest actionRequest,
        final ActionResponse actionResponse) throws Exception {
        System.out.println("action!!!");
        token = ParamUtil.getString(actionRequest, "token");
        taskid = ParamUtil.getInteger(actionRequest, "taskid");
        System.out.println("taskid: " + taskid);

        URL obj = new URL("https://fgw01.ncg.ingrid.pt/apis/v1.0/tasks/"
            + taskid);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + token);
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL: " + obj.toString());
        System.out.println("Response Code : " + responseCode);

        final int code1 = 200;
        final int code2 = 201;
        if (responseCode == code1 || responseCode == code2) {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject myObject;
            try {
                myObject = JSONFactoryUtil.createJSONObject(
                		response.toString());
                String status = myObject.getString("status");
                if (status.equals("DONE")) {
                    JSONArray myArray = myObject.getJSONArray("output_files");
                    if (myArray.length() != 0) {
                        JSONObject fileobj = myArray.getJSONObject(0);
                        String filename = fileobj.getString("name");
//                        if (filename.contains(".png")) {
                            String completeurl = fileobj.getString("url");
                            int index = completeurl.indexOf("&");
                            String url = completeurl.substring(0, index);

                            QName qName = new QName(
                                "http://cloud144.ncg.ingrid.pt",
                                "getpath");
                            actionResponse.setEvent(qName, url + "|" + token);
                            System.out.println("finished.");
//                        } else {
//                            System.out.println("Output png files not found");
//                        }
                    } else {
                        System.out.println("Output files not found");
                    }
                } else {
                    System.out.println("Unable to retrieve the outputs"
                            + "until the status is done.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Unable to connect to the URL "
            + obj.toString());
        }
    }
}
