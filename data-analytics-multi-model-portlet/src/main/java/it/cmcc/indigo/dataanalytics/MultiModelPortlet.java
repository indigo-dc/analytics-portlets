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
import com.liferay.portal.security.sso.iam.IAM;

import it.cmcc.indigo.utility.MultipartUtility;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
	
	/**
     * The IAM service.
     */
	private IAM iam;
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {
		
		String url = "http://cloud144.ncg.ingrid.pt/apis/v1.0";
		String token = "eyJraWQiOiJyc2ExIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJmZDUxYzZmNi01NTk4LTQ0ZTYtYTU1NC05Zjg3NTJkZGVhMTIiLCJpc3MiOiJodHRwczpcL1wvaWFtLXRlc3QuaW5kaWdvLWRhdGFjbG91ZC5ldVwvIiwiZXhwIjoxNDk4MjE0MzIyLCJpYXQiOjE0OTgyMTA3MjIsImp0aSI6IjRiODI1MjdkLTA5Y2UtNDc2ZC1hZjJhLWQ1YjQzNWEzYTg3MCJ9.drxXBuEO5T1RkDcKSYbYU62g5JhFmGWlDXp798pUqhARlxXV0Vejdo0F8-o4-fOV9PI_KWFo1vOoNULXuESsqdDe71Re9l44QX-ql14UeoQHoD5wQJmHuV5mmAV9DetKPTIEkXXrIZ504h0ZFT25jzGKrA-v5rGiHjLrwMRby88";
//		String token = iam.getUserToken(userId);
		/** First step: get applications **/
		URL obj = new URL(url + "/applications");
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
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			try {
				JSONObject myObject = JSONFactoryUtil.createJSONObject(response.toString());
				JSONArray myArray = myObject.getJSONArray("applications");
				
				int idapp = -1;
				
				for (int i = 0; i < myArray.length(); i++) {
					JSONObject appobj = myArray.getJSONObject(i);
					String appname = appobj.getString("name");
					
					if (appname.equals("kepler-batch"))
							idapp = appobj.getInt("id");
				}
				System.out.println("id kepler-batch: " + idapp);
				
				if (idapp != -1) {
					/** Second step: new task creation **/
					URL obj2 = new URL(url + "/tasks");
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
					System.out.println("\nSending 'POST' request to URL : " + url + "/tasks");
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
					
					/** Third step: three input files sending **/
					String charset = "UTF-8";
			        File uploadFile1 = new File("/home/futuregateway/kepler-batch/example/tosca_template.yaml");
			        File uploadFile2 = new File("/home/futuregateway/kepler-batch/example/parameters.json");
			        String requestURL = url + "/tasks/" + idtask + "/input";
			        System.out.println("Sending POST request to " + requestURL);
			 
			        try {
			            MultipartUtility multipart = new MultipartUtility(requestURL, charset, token);
			             
			            multipart.addFilePart("file[]", uploadFile1);
			            multipart.addFilePart("file[]", uploadFile2);
			 
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
					System.out.println("Application is not present in the database!");
				
			} catch (JSONException e) {
				e.printStackTrace();
				System.out.println("Unable to create a JSONObject from the server response.");
			}
        }
		super.doView(renderRequest, renderResponse);
	}
	
	/**
     * Sets the iam component.
     *
     * @param iamComp The iam component
     */
    @Reference(unbind = "-")
    protected final void setIam(final IAM iamComp) {
        this.iam = iamComp;
}
}
