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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

import it.cmcc.indigo.dataanalytics.multimodel.utility.MultipartUtility;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * Main class for the multi-model analysis submission.
 */
@Component(
    immediate = true,
    property = {
        "com.liferay.portlet.display-category=Data Analytics",
        "com.liferay.portlet.instanceable=true",
        "javax.portlet.display-name=Multi Model Analysis Submission",
        "javax.portlet.init-param.template-path=/",
        "javax.portlet.init-param.view-template=/view.jsp",
        "javax.portlet.resource-bundle=content.Language",
        "javax.portlet.security-role-ref=power-user,user"
    },
    service = Portlet.class
)

public class MultiModelPortlet extends MVCPortlet {

//  private String fgURL = "http://cloud144.ncg.ingrid.pt/apis";
    private String fgURL = "https://fgw01.ncg.ingrid.pt/apis";

    private String token = null;
    private String modelsString = null;
    private String[] models = null;
    private String scenario = null;
    private String timeFrequency = null;
    private String percentile = null;
    private String historicalTimeMin = null;
    private String historicalTimeMax = null;
    private String scenarioTimeMin = null;
    private String scenarioTimeMax = null;
    private String latmin = null;
    private String latmax = null;
    private String lonmin = null;
    private String lonmax = null;

    private static final int CODE1 = 200;
    private static final int CODE2 = 201;

    public final void submitExperiment(final ActionRequest request,
            final ActionResponse response)
                throws JSONException, IOException {

        token = ParamUtil.getString(request, "token");

        modelsString = ParamUtil.getString(request,"modelsString");
        
        System.out.println("models =  " + modelsString);
        
        models = modelsString.split("\\|");;

        for (int i = 0; i < models.length; i++) {
            System.out.println("models " + i + ": " + models[i]);
        }
        
        scenario = ParamUtil.getString(request, "scenario");
        System.out.println("scenario =  " + scenario);

        timeFrequency = ParamUtil.getString(request, "time_frequency");
        System.out.println("time_frequency =  " + timeFrequency);

        percentile = ParamUtil.getString(request, "percentile");
        System.out.println("percentile =  " + percentile);

        historicalTimeMin = ParamUtil.getString(request, "historical_time_min");
        System.out.println("historical_time_min =  " + historicalTimeMin);

        historicalTimeMax = ParamUtil.getString(request, "historical_time_max");
        System.out.println("historical_time_max =  " + historicalTimeMax);

        scenarioTimeMin = ParamUtil.getString(request, "scenario_time_min");
        System.out.println("scenario_time_min =  " + scenarioTimeMin);

        scenarioTimeMax = ParamUtil.getString(request, "scenario_time_max");
        System.out.println("scenario_time_max =  " + scenarioTimeMax);

        latmin = ParamUtil.getString(request, "latmin");
        System.out.println("latmin =  " + latmin);

        latmax = ParamUtil.getString(request, "latmax");
        System.out.println("latmax =  " + latmax);

        lonmin = ParamUtil.getString(request, "lonmin");
        System.out.println("lonmin =  " + lonmin);

        lonmax = ParamUtil.getString(request, "lonmax");
        System.out.println("lonmax =  " + lonmax);

        int idapp = getAppID("kepler-batch");
        int idtask = -1;

        //if (idapp != -1) {
            // new FG task creation
            idtask = newFGTask(idapp);

            // temp dir creation
            String tmpDirPrefix = "dataanalytics_";
            Path tmpPath = Files.createTempDirectory(tmpDirPrefix);

//          HttpDownloadUtility.downloadFile(
//          "https://raw.githubusercontent.com/indigo-dc/tosca-templates/
//          master/kepler-batch.yaml", tmp_path.toString());
//          System.out.println(tmp_path.toString() + "/tosca_template.yaml");
//          File uploadFile1 = new File(tmp_path.toString() +
//          "/tosca_template.yaml");
            File uploadFile1 = createParametersFile(idtask, tmpPath);
            File uploadFile2 = new File("/home/futuregateway/FutureGateway/"
                + "fgAPIServer/apps/kepler-batch/tosca_template.yaml");
            sendTaskInputFile(idtask, uploadFile1, uploadFile2);
/*        } else {
            System.out.println("Application is not present in the database!");
        }*/
    }

    /** First step: get application ID
     * @throws IOException
     * @throws JSONException **/
    public final int getAppID(final String appname)
            throws IOException, JSONException {
        int idapp = -1;
        URL obj = new URL(fgURL + "/v1.0/applications");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + token);
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL: " + obj.toString());
        System.out.println("Response Code : " + responseCode);

        if (responseCode == CODE2 || responseCode == CODE1) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject myObject = JSONFactoryUtil.createJSONObject(
                    response.toString());
            JSONArray myArray = myObject.getJSONArray("applications");

            for (int i = 0; i < myArray.length(); i++) {
                JSONObject appobj = myArray.getJSONObject(i);
                String currentapp = appobj.getString("name");

                if (currentapp.equals(appname)) {
                    idapp = appobj.getInt("id");
                }
            }
            System.out.println("id " + appname + ": " + idapp);
            } else {
                System.out.println("Unable to connect to the URL "
                    + obj.toString());
            }

        return idapp;
    }

    /** Second step: set a new Future Gateway task
    * @throws IOException
    * @throws JSONException **/
    public final int newFGTask(final int idapp) throws IOException,
            JSONException {
        URL obj2 = new URL(fgURL + "/v1.0/tasks");
        HttpURLConnection con2 = (HttpURLConnection) obj2.openConnection();

        //add request header
        con2.setRequestMethod("POST");
        con2.setRequestProperty("Authorization", "Bearer " + token);
        con2.setRequestProperty("Content-Type", "application/json");

        String urlParameters = "{\"application\":\"" + idapp + "\"}";

        // Send post request
        con2.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con2.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode2 = con2.getResponseCode();
        System.out.println("\nSending 'POST' request to URL: "
            + obj2.toString());
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode2);

        final int code1 = 200;
        final int code2 = 201;
        int idtask = -1;
        if (responseCode2 == code1 || responseCode2 == code2) {
            InputStream in = con2.getInputStream();
            BufferedReader in2 = new BufferedReader(new InputStreamReader(in));
            
            String inputLine2;
            StringBuffer response2 = new StringBuffer();

            while ((inputLine2 = in2.readLine()) != null) {
                response2.append(inputLine2);
            }
            in2.close();

            JSONObject myObject2 = JSONFactoryUtil.createJSONObject(
                    response2.toString());
            idtask = myObject2.getInt("id");
        }
       
        return idtask;
    }

    public final File createParametersFile(final int taskid,
            final Path tmpPath) {
        File file = null;

        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader("/home/futuregateway/FutureGateway/"
                + "fgAPIServer/apps/kepler-batch/models-mapping.json");
            br = new BufferedReader(fr);

            String jsonsmapping = "";
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                jsonsmapping += sCurrentLine;
            }
            JSONObject jm = JSONFactoryUtil.createJSONObject(jsonsmapping);
            JSONArray modelsmapping = jm.getJSONArray("models");

            String jsonString = "{";
            jsonString += "\"parameters\": {";
            jsonString += "\"futuregateway_uri\": \"" + fgURL + "\",";
            jsonString += "\"authorization_token\": \"" + token + "\",";
            jsonString += "\"task_id\": \"" + taskid + "\",";
            jsonString += "\"config_json\": \"[";

            for (int i = 0; i < models.length; i++) {
                String modelname = models[i];
                for (int j = 0; j < modelsmapping.length(); j++) {
                    JSONObject modelmapping = modelsmapping.getJSONObject(j);
                    String name = modelmapping.getString("model");
                    if (name.equals(modelname)) {
                        String host = modelmapping.getString("host");
                        String port = modelmapping.getString("port");
                        String username = modelmapping.getString("username");
                        String password = modelmapping.getString("password");
                        if (i == models.length - 1) {
                            jsonString += "{\\\"host\\\":\\\"" + host
                                    + "\\\",\\\"port\\\":\\\"" + port
                                    + "\\\",\\\"login\\\":\\\""
                                    + username + "\\\",\\\"password\\\":\\\""
                                    + password + "\\\",\\\"argument\\\":\\\"8 "
                                    + modelname + " " + scenario + " "
                                    + timeFrequency + " " + percentile + " "
                                    + historicalTimeMin + "_"
                                    + historicalTimeMax
                                    + " " + scenarioTimeMin + "_"
                                    + scenarioTimeMax
                                    + " " + latmin + ":" + latmax + "|"
                                    + lonmin
                                    + ":" + lonmax + " r360x180\\\"}";
                        } else {
                            jsonString += "{\\\"host\\\":\\\"" + host
                                    + "\\\",\\\"port\\\":\\\"" + port
                                    + "\\\",\\\"login\\\":\\\""
                                    + username + "\\\",\\\"password\\\":\\\""
                                    + password
                                    + "\\\",\\\"argument\\\":\\\"8 "
                                    + modelname + " " + scenario + " "
                                    + timeFrequency + " " + percentile + " "
                                    + historicalTimeMin + "_"
                                    + historicalTimeMax
                                    + " " + scenarioTimeMin + "_"
                                    + scenarioTimeMax
                                    + " " + latmin + ":" + latmax
                                    + "|" + lonmin
                                    + ":" + lonmax + " r360x180\\\"},";
                        }
                    }
                }
            }
            jsonString += "]\",";
            jsonString += "\"token_service_uri\": \"https://fgw01.ncg.ingrid"
                + ".pt/api/jsonws/iam.token/get-token\",";
            jsonString += "\"token_service_user\": \"admin@fgapiserver"
                + ".indigo.eu\",";
            jsonString += "\"token_service_password\": \"3t>145.v9u+CtVv\"";
            jsonString += "}";
            jsonString += "}";
            jsonString += "\n";
            System.out.println("json: " + jsonString);

            file = new File(tmpPath + "/parameters.json");
            file.createNewFile();
            System.out.println("file: " + tmpPath + "/parameters.json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonString);
            fileWriter.flush();
            fileWriter.close();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        }
        return file;
    }

    public final void sendTaskInputFile(final int idtask,
            final File uploadFile1,
            final File uploadFile2) throws IOException {
        String requestURL = fgURL + "/v1.0/tasks/" + idtask + "/input";
        String charset = "UTF-8";
        System.out.println("\nSending POST request to " + requestURL);

        try {
            MultipartUtility multipart = new MultipartUtility(requestURL,
                    charset, token);

            multipart.addFilePart("file[]", uploadFile1);
            multipart.addFilePart("file[]", uploadFile2);

            List<String> response3 = multipart.finish();

            System.out.println("SERVER REPLIED:");

            for (String line : response3) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println("Failed to send task input files");
            ex.printStackTrace();
        }
    }
}