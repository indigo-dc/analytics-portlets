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

<%@ page contentType="text/html; charset=ISO-8859-1"%>
<%@ include file="/init.jsp" %>
<portlet:actionURL name="submitCommand" var="submitCommand" />

<div class="panel panel-default">
    <div>
		<form action="<%=submitCommand%>" method="Post">
			<input type="hidden" name="<portlet:namespace/>token" id="<portlet:namespace/>token"/>
			<input type="text" name="<portlet:namespace/>test" id="<portlet:namespace/>test" value="test_mio"/>
	    	<input type="submit" value="Submit"/>
		</form>
	</div>       
</div>
<script type="text/javascript">
	Liferay.Service(
         '/iam.token/get-token',
         function(obj) {
        	 document.getElementById('<portlet:namespace/>token').value = obj.token;
        	 document.getElementById('<portlet:namespace/>test').value = "test_token";
         }
    );
</script>
