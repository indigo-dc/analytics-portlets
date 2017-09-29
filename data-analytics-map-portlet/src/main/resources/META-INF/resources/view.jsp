<%
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
%>

<%@ include file="/init.jsp"%>

<%@page import="javax.portlet.PortletSession"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="javax.xml.bind.DatatypeConverter" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
	String b64 = "";
	// String urltoken = ParamUtil.getString(request, "urltoken", null);
	String urltoken = (String)request.getAttribute("urltoken");
	System.out.println("view urltoken: " + urltoken);
	if (urltoken != null) {
		int index = urltoken.indexOf("|");
		String token = urltoken.substring(index + 1);
		String url = urltoken.substring(0, index);

		URL obj = new URL("https://fgw01.ncg.ingrid.pt/apis/v1.0/" + url + "&name=min.png");
		HttpURLConnection con = (HttpURLConnection)obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", "Bearer " + token);
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL: " + obj.toString());
		System.out.println("Response Code : " + responseCode);

		if(responseCode==201 || responseCode==200) {
	    	BufferedImage image = ImageIO.read(con.getInputStream());
	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    	ImageIO.write(image, "png", baos);
	    	baos.flush();
	    	byte[] imageInByteArray = baos.toByteArray();
	    	baos.close();
	    	b64 = DatatypeConverter.printBase64Binary(imageInByteArray);
	    	System.out.println("b46: " + b64);
		}
		else
			System.out.println("Unable to connect to the URL " + obj.toString());
	}
%>

<div class="panel panel-default">
	<div class="panel-body">
		<p>
			<!-- <img src="<!%=request.getContextPath()%>/images/var.png" style="margin-left: auto; margin-right: auto; display: block;"/> -->
			<img src="data:image/png;base64, <%=b64%>" />
		</p>
	</div>
</div>
<div class="panel-footer"></div>
