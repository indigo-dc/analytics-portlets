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

<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<portlet:resourceURL id="serveResource" var="serveResource"/>

<style type="text/css">

.portletPanel {
	height: 650px;
  	overflow-y: auto;
  	width: 100%;
}

.tasksList {
    width: 100%;
    height: 500px;
}

.tasksList th {
    height: 36px;
}

.tasksList tr {
    height: 36px;
}
 
.tasksList tbody tr {
    cursor: pointer;
    border-top: 1px solid #ddd;
    padding: 8px;
    vertical-align: middle;
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
  	          			
				var pickedup;
          			
          		$("#tasksList tbody tr").on("click", function(event) {
        			var rowString = $(this)[0].innerHTML;
               	    rowString = rowString.substring(21);
               	    var index = rowString.indexOf("<");
               	    var taskId = rowString.substr(0,index);
               	          
               	    if(rowString.includes("ensemble") && rowString.includes("DONE")) {
          	        	if (pickedup != null) {
          	            	pickedup.css( "background-color", "white" );
          	              	pickedup.css( "font-weight", "normal" );
          	            }
        	 
          	        	avgVisualization(taskId);
          	        	maxVisualization(taskId);
          	        	minVisualization(taskId);
          	        	stdVisualization(taskId);
          	        	varVisualization(taskId);
             	          
          	        	$(this).css("background-color", "#b0c4de");
             	        $(this).css("font-weight", "bold");
                      	 
             	        pickedup = $(this);
         	        }
   	                else {
   	            		alert("Only ensamble tasks contain map results.");
   	              	}
          	    });
  	      	}  
		});
	}
)};

function refreshtable() {
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
  	          			
  	          		var pickedup;
          			
          		    $("#tasksList tbody tr").on("click", function(event) {
          		    	
          		    	var rowString = $(this)[0].innerHTML;
	           	        rowString = rowString.substring(21);
	           	        var index = rowString.indexOf("<");
	           	        var taskId = rowString.substr(0,index);
	           	          
	           	        if(rowString.includes("ensemble") && rowString.includes("DONE")) {
          		    	 
          	          		if (pickedup != null) {
          	              		pickedup.css( "background-color", "white" );
          	              		pickedup.css( "font-weight", "normal" );
          	          		}
          	          
           	        	  	avgVisualization(taskId);
           	        	  	maxVisualization(taskId);
           	        	  	minVisualization(taskId);
           	        	  	stdVisualization(taskId);
           	        	  	varVisualization(taskId);
              	          
           	        	  	$(this).css("background-color", "#b0c4de");
              	          	$(this).css("font-weight", "bold");
                       	 
              	          	pickedup = $(this);
           	          	}
     	              	else {
     	            		alert("Only ensamble tasks contain map results.");
     	              	}
          	    	});
  	      		} 
     	    });
	  	}
	)};
}
</script>

<div class="portletPanel">
    <table style="width: 100%;">
        <tr>
 		    <td class="col-xs-11"></td>
 		    <td class="col-xs-1"><input id="refresh" type="button" value="Refresh" onclick="refreshtable();"/></td>
 	    </tr>
    </table>
    <br>
    <table class="tasksList" id="tasksList">
        <thead>
            <tr style="background-color: #d9edf7;">
            	 <th class="col-xs-1" id="id" >Id</th>
                <th class="col-xs-2" id="date">Date</th>
                <th class="col-xs-8" id="description">Description</th>
                <th class="col-xs-1" id="status">Status</th>
            </tr>
        </thead>
        <tbody id="bodyId"></tbody>
     </table>
</div>
