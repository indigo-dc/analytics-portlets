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

package it.cmcc.indigo.dataanalytics.portlet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

import it.cmcc.indigo.utility.HttpDownloadUtility;
import it.cmcc.indigo.utility.MultipartUtility;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
		"javax.portlet.display-name=Data analytics multi model portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)

public class MultiModelPortlet extends MVCPortlet {
	
	private String fgURL = "http://cloud144.ncg.ingrid.pt/apis";
	
	private String token = null;
	
	private String model1 = null;
	private String model2 = null;
	
	private String scenario = null;
	private String time_frequency = null;
	private String percentile = null;
	private String historical_time = null;
	private String scenario_time = null;
	
	private String latmin = null;
	private String latmax = null;
	private String lonmin = null;
	private String lonmax = null;
	
	public void submitExperiment(ActionRequest request, ActionResponse response) throws IOException, JSONException {
		
		token = ParamUtil.getString(request, "token");
		System.out.println("token: " + token);
		
		model1 = "CMCC-CM";
		model2 = "CMCC-CMS";
		
		scenario = "rcp85";
		time_frequency = "day";
		percentile = "0.9";
		historical_time = "1976_2006";
		scenario_time = "2071_2101";
		
		latmin = "-90";
		latmax = "90";
		lonmin = "0";
		lonmax = "360";
		
		int idapp = getAppID("kepler-batch");
		int idtask = -1;
		
		if (idapp != -1) {
			// new FG task creation
			idtask = newFGTask(idapp);  
			
			// temp dir creation
			String tmp_dir_prefix = "dataanalytics_";
	        Path tmp_path = Files.createTempDirectory(tmp_dir_prefix);

//	        HttpDownloadUtility.downloadFile("https://raw.githubusercontent.com/indigo-dc/tosca-templates/master/kepler-batch.yaml", tmp_path.toString());
//			System.out.println(tmp_path.toString() + "/tosca_template.yaml");
//			File uploadFile1 = new File(tmp_path.toString() + "/tosca_template.yaml");
//			File uploadFile2 = createParametersFile(idtask, tmp_path);
			File uploadFile1 = new File("/home/futuregateway/kepler-batch/example/tosca_template.yaml");
			File uploadFile2 = new File("/home/futuregateway/kepler-batch/example/parameters.json");
			sendTaskInputFile(idtask, uploadFile1, uploadFile2);
		}
		else
			System.out.println("Application is not present in the database!");
	}
	
	/** First step: get application ID 
	 * @throws IOException 
	 * @throws JSONException **/
	public int getAppID(String appname) throws IOException, JSONException {
		int idapp = -1;
		URL obj = new URL(fgURL + "/v1.0/applications");
		HttpURLConnection con = (HttpURLConnection)obj.openConnection();
		
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", "Bearer " + token);
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL: " + obj.toString());
		System.out.println("Response Code : " + responseCode);
		
		if(responseCode==201 || responseCode==200) {
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			JSONObject myObject = JSONFactoryUtil.createJSONObject(response.toString());
			JSONArray myArray = myObject.getJSONArray("applications");
			
			for (int i = 0; i < myArray.length(); i++) {
				JSONObject appobj = myArray.getJSONObject(i);
				String currentapp = appobj.getString("name");
				
				if (currentapp.equals(appname))
						idapp = appobj.getInt("id");
			}
			System.out.println("id " + appname + ": " + idapp);
		}
		else
			System.out.println("Unable to connect to the URL " + obj.toString());
		return idapp;
	}
	
	/** Second step: set a new Future Gateway task 
	 * @throws IOException 
	 * @throws JSONException **/
	public int newFGTask(int idapp) throws IOException, JSONException {
		URL obj2 = new URL(fgURL + "/v1.0/tasks");
		HttpURLConnection con2 = (HttpURLConnection)obj2.openConnection();

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
		System.out.println("\nSending 'POST' request to URL: " + obj2.toString());
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode2);

		BufferedReader in2 = new BufferedReader(
		        new InputStreamReader(con2.getInputStream()));
		String inputLine2;
		StringBuffer response2 = new StringBuffer();

		while ((inputLine2 = in2.readLine()) != null) {
			response2.append(inputLine2);
		}
		in2.close();

		JSONObject myObject2 = JSONFactoryUtil.createJSONObject(response2.toString());
		int idtask = myObject2.getInt("id");
		
		return idtask;
	}
	
	public File createParametersFile(int taskid, Path tmp_path) throws JSONException {
		
		File file = null;
				
		String jsonString = "{";
		jsonString += "\"parameters\": {";
		jsonString += "\"futuregateway_uri\": \"" + fgURL + "\",";
		jsonString += "\"authorization_token\": \"" + token + "\",";
		jsonString += "\"task_id\": \"" + taskid + "\",";
		jsonString += "\"config_json\": \"[";
        jsonString += "{\\\"host\\\":\\\"193.204.199.174\\\",\\\"port\\\":\\\"11732\\\",\\\"login\\\":\\\"indigo\\\",\\\"password\\\":\\\"1nD1g0_de\\\",\\\"argument\\\":\\\"8 " + model1 + " " + scenario + " " + time_frequency + " " + percentile + " " + historical_time + " " + scenario_time + " " + latmin + ":" + latmax + "|" + lonmin + ":" + lonmax + " r360x180\\\"},";
        jsonString += "{\\\"host\\\":\\\"193.204.199.174\\\",\\\"port\\\":\\\"11732\\\",\\\"login\\\":\\\"indigo\\\",\\\"password\\\":\\\"1nD1g0_de\\\",\\\"argument\\\":\\\"8 " + model2 + " " + scenario + " " + time_frequency + " " + percentile + " " + historical_time + " " + scenario_time + " " + latmin + ":" + latmax + "|" + lonmin + ":" + lonmax + " r360x180\\\"}]\"";
		jsonString += "}";
		jsonString += "}";
//		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(jsonString);
		
		try {  

            // Writing to a file  
            file = new File(tmp_path + "/parameters.json");  
            file.createNewFile();  
            FileWriter fileWriter = new FileWriter(file);  
            fileWriter.write(jsonString);  
            fileWriter.flush();  
            fileWriter.close();  

        } catch (IOException e) {  
            e.printStackTrace();  
        }
		return file;
	}
	
	public void sendTaskInputFile(int idtask, File uploadFile1, File uploadFile2) throws IOException {
		String requestURL = fgURL + "/tasks/" + idtask + "/input";
		String charset = "UTF-8";
        System.out.println("\nSending POST request to " + requestURL);
 
        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset, token);
             
            multipart.addFilePart("file[]", uploadFile1);
            System.out.println("uploadFile1");
            multipart.addFilePart("file[]", uploadFile2);
            System.out.println("uploadFile2");
 
            List<String> response3 = multipart.finish();
             
            System.out.println("SERVER REPLIED:");
             
            for (String line : response3) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
	}
}
