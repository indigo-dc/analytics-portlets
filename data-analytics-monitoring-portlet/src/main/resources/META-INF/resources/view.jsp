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
<!-- <link rel="stylesheet" href="https://cdn.datatables.net/1.10.7/css/jquery.dataTables.css"> -->

<portlet:resourceURL id="serveResource" var="serveResource"/>

<style type="text/css">

.portletPanel {
	height: 500px;
  	overflow-y: auto;
  	width: 100%;
}

.table-fixed2 thead {
	width: 100%;
}
.table-fixed2 tbody {
	height: 500px;
  	overflow-y: auto;
  	width: 100%;
}
.table-fixed2 thead, .table-fixed2 tbody, .table-fixed2 tr, .table-fixed2 td, .table-fixed2 th {
  	display: block;
}
.table-fixed2 tbody td, .table-fixed2 thead > tr> th {
  	float: left;
  	border-bottom-width: 0;
}
</style>

<script>

setInterval(function timedRefresh(timeoutPeriod) {
	{Liferay.Service(
		      '/iam.token/get-token',
		      function(obj) {
		     	    var token = obj.token;
		     	    $.ajax({
		     	        type: "POST",
		     	        data: {"<portlet:namespace />token" : token},
		     	        url: "<%=serveResource%>",
		     	        success: function (response) {
		  	        			//alert(response);
		  	           			var trHTML = '';
		  	           			response = $.parseJSON(response);
		  	          			$.each(response, function (i, item) {
		  	          				//trHTML += '<tr><td class="col-xs-2">' + item.status + '</td><td class="col-xs-2">' + item.description + '</td><td class="col-xs-2">' + item.date + '</td><td class="col-xs-2">' + item.id + '</td><td class="col-xs-2">' + item.application + '</td><td class="col-xs-2">' + item.last_change + '</td></tr>';
		  	          				trHTML = '<tr><td class="col-xs-1">' + item.id + '</td><td class="col-xs-1">' + item.application + '</td><td class="col-xs-2">' + item.date + '</td><td class="col-xs-7">' + item.description + '</td><td class="col-xs-1">' + item.status + '</td></tr>' + trHTML;
		  	          			});
		  	          			
		  	          			$('#tasksList').append(trHTML);
		  	      			 }
		     	    });

		      }
		 )};
}, 5000);
</script>

<div class="portletPanel">
     <table class="table table-fixed2" id="tasksList">
         <thead>
             <tr class="info">
             	 <th class="col-xs-1">Id</th>
                 <th class="col-xs-1">Application #</th>
                 <th class="col-xs-2">Date</th>
                 <th class="col-xs-7">Description</th>
                 <th class="col-xs-1">Status</th>
             </tr>
         </thead>
      </table>
 </div>

<div class="panel-footer"></div>
