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

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<portlet:resourceURL id="serveResource" var="serveResource"/>

<portlet:actionURL name="/enes/getOutputPath" var="getOutputPath" />

<style type="text/css">

.portletPanel {
	height: 650px;
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

.table-fixed3 thead {
	width: 100%;
}
.table-fixed3 tbody {
	height: 10px;
  	overflow-y: auto;
  	width: 100%;
}
.table-fixed3 thead, .table-fixed3 tbody, .table-fixed3 tr, .table-fixed3 td, .table-fixed3 th {
  	display: block;
}
.table-fixed3 tbody td, .table-fixed3 thead > tr> th {
  	float: left;
  	border-bottom-width: 0;
}

tr.highlight td{
	background: #B0C4DE;
	font-weight: bold;
}

tr.simple td{
	background: #FFFFFF;
	font-weight: normal;
}

</style>

<script>

{Liferay.Service(
      '/iam.token/get-token',
      function(obj) {
     	    var token = obj.token;
     	    $.ajax({
     	        type: "POST",
     	        data: {"<portlet:namespace />token" : token},
     	        url: "<%=serveResource%>",
      	        success: function (response) {
  	           			var trHTML = '';
  	           			response = $.parseJSON(response);
  	          			$.each(response, function (i, item) {
  	          				trHTML = '<tr><td class="col-lg-1">' + item.id + '</td><td class="col-lg-2">' + item.date + '</td><td class="col-lg-8">' + item.description + '</td><td class="col-lg-1">' + item.status + '</td></tr>' + trHTML;
  	          			});
  	          			
  	          			$('#tasksList').append(trHTML);
  	          			highlight('tasksList');
  	      	    }  
  	      	    
/*      	        success: function (response) {
     	        	response = '';
           			var trHTML = '';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
              			
          			$('#tasksList').append(trHTML);
          			highlight('tasksList');
      			} */
     	    });
     	    
     	    var previousSelection = null;
     	    
     	    function highlight(tableid){
     	    	
     	        var row = tableid+" tbody tr";
     	        $("#"+row).unbind().on('click', (function(){
     	        	
     	            var currentSelection = $(this);
     	            var rowString = $(this)[0].innerHTML;
     	            
     	            rowString = rowString.substring(21);
     	            var index = rowString.indexOf("<");
     	            
     	            var taskId = rowString.substr(0,index);
     	            
     	            if (previousSelection)
     	            	previousSelection.toggleClass("simple");
     	            	
     	            currentSelection.toggleClass("highlight");
     	            previousSelection = currentSelection;
     	            
     	     	    $.ajax({
     	     	        type: "POST",
     	     	        data: {"<portlet:namespace />token" : token, "<portlet:namespace />taskid" : taskId},
  	    	            url: "<%=getOutputPath%>",
     		     	    success: function (response) {
							//alert("evento!!!");
     		      		}
     	     	    });
     	            
     	        }
     	    ))};
      }
)};

function refresh() {
	{Liferay.Service(
		      '/iam.token/get-token',
		      function(obj) {
		     	    var token = obj.token;
		     	    $.ajax({
		     	        type: "POST",
		     	        data: {"<portlet:namespace />token" : token},
		     	        url: "<%=serveResource%>",
  		     	        success: function (response) {
								$('#bodyId').empty();
								
		  	           			var trHTML = '';
		  	           			response = $.parseJSON(response);
		  	          			$.each(response, function (i, item) {
		  	          				trHTML = '<tr><td class="col-lg-1">' + item.id + '</td><td class="col-lg-2">' + item.date + '</td><td class="col-lg-8">' + item.description + '</td><td class="col-lg-1">' + item.status + '</td></tr>' + trHTML;
		  	          			});
		  	          			
		  	          			$('#tasksList').append(trHTML);
		  	          			highlight('tasksList');
		  	      		} 
/* 		     	        success: function (response) {
		     	        	$('#bodyId').empty();
		     	        	response = '';
		           			var trHTML = '';
		              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
		              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
		              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
		              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
		              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
		              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
		              			trHTML += '<tr><td class="col-lg-1">' + "id" + '</td><td class="col-lg-2">' + "date" + '</td><td class="col-lg-8">' + "description" + '</td><td class="col-lg-1">' + "status" + '</td></tr>';
		              			
		          			$('#tasksList').append(trHTML);
		          			highlight('tasksList');
		      			} */
		     	    });
		     	    
		     	    var previousSelection = null;
		     	    
		     	   function highlight(tableid){
		     	    	
		     	        var row = tableid+" tbody tr";
		     	        $("#"+row).unbind().on('click', (function(){
		     	        	
		     	            var currentSelection = $(this);
		     	            var rowString = $(this)[0].innerHTML;
		     	            
		     	            rowString = rowString.substring(21);
		     	            var index = rowString.indexOf("<");
		     	            
		     	            var taskId = rowString.substr(0,index);
		     	            
		     	            if (previousSelection)
		     	            	previousSelection.toggleClass("simple");
		     	            	
		     	            currentSelection.toggleClass("highlight");
		     	            previousSelection = currentSelection;
		     	            
		     	     	    $.ajax({
		     	     	        type: "POST",
		     	     	        data: {"<portlet:namespace />token" : token, "<portlet:namespace />taskid" : taskId},
		  	    	            url: "<%=getOutputPath%>",
		     		     	    success: function (response) {
									//alert("evento!!!");
		     		      		}
		     	     	    });
		     	            
		     	        }
		     	    ))};
		      }
		)};
}

</script>

<div class="portletPanel">
	 <table style="width: 100%;">
	 	<tr>
	 		<td class="col-xs-11"></td>
	 		<td class="col-xs-1"><input id="refresh" type="button" value="Refresh" onclick="refresh();"/></td>
	 	</tr>
	 </table>
	 <br>
	 <table class="table table-fixed2" id="tasksList">
         <thead>
             <tr class="info">
             	 <th class="col-xs-1" id="id" >Id</th>
                 <th class="col-xs-2" id="date">Date</th>
                 <th class="col-xs-8" id="description">Description</th>
                 <th class="col-xs-1" id="status">Status</th>
             </tr>
         </thead>
         <tbody id="bodyId"></tbody>
      </table>
 </div>

<div class="panel-footer"></div>
