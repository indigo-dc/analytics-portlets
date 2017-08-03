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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

import it.cmcc.indigo.utility.MultipartUtility;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=Data Analytics",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Data analytics single model portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)

public class SingleModelPortlet extends MVCPortlet {
	
	private String token = null;
	
	public void submitCommand(ActionRequest request, ActionResponse response) throws IOException {
		token = ParamUtil.getString(request, "token");
		System.out.println("token: " + token);
		
		String fgurl = "http://cloud144.ncg.ingrid.pt/apis/v1.0";
//		String token = "eyJraWQiOiJyc2ExIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJmZDUxYzZmNi01NTk4LTQ0ZTYtYTU1NC05Zjg3NTJkZGVhMTIiLCJpc3MiOiJodHRwczpcL1wvaWFtLXRlc3QuaW5kaWdvLWRhdGFjbG91ZC5ldVwvIiwiZXhwIjoxNTAxNTc4MDY2LCJpYXQiOjE1MDE1NzQ0NjYsImp0aSI6IjYzM2U0Y2VlLTNhZjUtNDdkOS1iNmZmLTM0MTBiMTAyMzkwOSJ9.L66QWg2HIBfCthtOqENzDxhY29PIT095POBTIVNVklV4sbYM-vk9PfkFlMmmuyEnD8ifRiITXbM1HI6YZIXekTh_4rfDkJODVteHNEKrFlGAiUZAUej7nDNPHCbiSvZggLYhzG-hFnFMl_YdQdDL3xv3Ix8Fe52H7GdO77pmVyk";
		
		/** First step: get applications **/
		URL obj = new URL(fgurl + "/applications");
		HttpURLConnection con = (HttpURLConnection)obj.openConnection();
		
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", "Bearer " + token);
		
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : http://cloud144.ncg.ingrid.pt/apis/v1.0/applications");
		System.out.println("Response Code : " + responseCode);
		
		if(responseCode==201 || responseCode==200) {
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer bufferresponse = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				bufferresponse.append(inputLine);
			}
			in.close();
//			System.out.println(bufferresponse.toString());
			
			try {
				JSONObject myObject = JSONFactoryUtil.createJSONObject(bufferresponse.toString());
				JSONArray myArray = myObject.getJSONArray("applications");
				
				int idapp = -1;
				
				for (int i = 0; i < myArray.length(); i++) {
					JSONObject appobj = myArray.getJSONObject(i);
					String appname = appobj.getString("name");
					
					if (appname.equals("oph-term"))
							idapp = appobj.getInt("id");
				}
				System.out.println("id oph-term: " + idapp);
				
				if (idapp != -1) {
					/** Second step: new task creation **/
					URL obj2 = new URL(fgurl + "/tasks");
					HttpURLConnection con2 = (HttpURLConnection)obj2.openConnection();

					//add request header
					con2.setRequestMethod("POST");
					con2.setRequestProperty("Authorization", "Bearer " + token);
					con2.setRequestProperty("Content-Type", "application/json");
					
					String urlParameters = "{\"application\":\"" + idapp + "\", \"arguments\":[\"193.204.199.174 11732 \\\"8 CMCC-CM rcp85 day 0.9 1976_2006 2071_2101 -90:90|0:360 r360x180\\\"\"]}";

					// Send post request
					con2.setDoOutput(true);
					DataOutputStream wr = new DataOutputStream(con2.getOutputStream());
					wr.writeBytes(urlParameters);
					wr.flush();
					wr.close();

					int responseCode2 = con2.getResponseCode();
					System.out.println("\nSending 'POST' request to URL : " + fgurl + "/tasks");
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

					//print result
//					System.out.println(response2.toString());
					
					JSONObject myObject2 = JSONFactoryUtil.createJSONObject(response2.toString());
					int idtask = myObject2.getInt("id");
					System.out.println("idtask: " + idtask);
					
					/** Third step: three input files sending **/
					String requestURL = fgurl + "/tasks/" + idtask + "/input";
					
					File uploadFile1 = new File("/home/futuregateway/FutureGateway/fgAPIServer/apps/oph-term/script.sh");
			        File uploadFile2 = new File("/home/futuregateway/FutureGateway/fgAPIServer/apps/oph-term/workflow.json");
			        File uploadFile3 = new File("/home/futuregateway/FutureGateway/fgAPIServer/apps/oph-term/oph-credentials.txt");
			        
//			        URL url = new URL(requestURL);
//			        final MultipartUtility http = new MultipartUtility(url, token);
//			        http.addFilePart("file[]", uploadFile1);
//			        http.addFilePart("file[]", uploadFile2);
//			        http.addFilePart("file[]", uploadFile3);
//			        final byte[] bytes = http.finish();
//			        final OutputStream os = new FileOutputStream("someoutput.txt");
//			        os.write(bytes);
			        
					String charset = "UTF-8";
			        System.out.println("Sending POST request to " + requestURL);
			 
			        try {
			            MultipartUtility multipart = new MultipartUtility(requestURL, charset, token);
			             
//			            multipart.addHeaderField("User-Agent", "CodeJava");
//			            multipart.addHeaderField("Test-Header", "Header-Value");
			             
			            multipart.addFilePart("file[]", uploadFile1);
			            multipart.addFilePart("file[]", uploadFile2);
			            multipart.addFilePart("file[]", uploadFile3);
			 
			            List<String> response3 = multipart.finish();
			             
			            System.out.println("SERVER REPLIED:");
			             
			            for (String line : response3) {
			                System.out.println(line);
			            }
			        } catch (IOException ex) {
			            System.err.println(ex);
			        }
					
				}
				else
					System.out.println("The application doesn't exist.");
				
			} catch (JSONException e) {
				e.printStackTrace();
				System.out.println("Unable to create a JSONObject from the server response.");
			}
        }
	}
}
