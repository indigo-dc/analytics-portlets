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

<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>

<portlet:resourceURL id="serveResource" var="serveResource"/>

<script>
function maxVisualization(taskid) {
	{Liferay.Service(
		'/iam.token/get-token',
		function(obj) {
			var token = obj.token;
			$.ajax({
				type: "POST",
			    data: {"<portlet:namespace />token" : token, "<portlet:namespace />taskid" : taskid},
		        url: "<%=serveResource%>",
		   	    success: function (response) {
		   	    	$("#max").html('<img src="data:image/png;base64,' + response + '" />');
		        }
		    });
		}
	)};
}
</script>

<div class="panel panel-default">
	<div class="panel-body" id="max">
			<p>Select an ensemble task from the Experiment monitoring table to visualise the maximum map.</p>
	</div>
</div>
