<%--
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
--%>

<%@ include file="/init.jsp" %>

<script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="https://cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.7/css/jquery.dataTables.css">

<portlet:resourceURL id="serveResource" var="serveResource"/>

Table here.

<script>
Liferay.Service(
      '/iam.token/get-token',
      function(obj) {
     	 //$('#addButton').click(function(e) {
     	    var token = obj.token;
     	    $.ajax({
     	        type: "POST",
     	        data: {"<portlet:namespace />token" : token},
     	        url: "<%=serveResource%>",
     	        success: function(data){
     	            alert("Hello");
     	        }
     	    });
     	//});
      }
 );
</script>
<div class="panel-footer"></div>
