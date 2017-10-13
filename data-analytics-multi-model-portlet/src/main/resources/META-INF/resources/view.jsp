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

<portlet:actionURL name="submitExperiment" var="submitExperiment" />


<style type="text/css">
.wrapper {
    text-align: center;
}
</style>

<div class="panel panel-default">
    <div>
        <form>
            <input name="<portlet:namespace/>token" type="hidden" id="<portlet:namespace/>token" value=""/>
            <br>
            
            <table>
                <tr>
                    <td><br><br><div id="mapDiv" style="width:600px;height:400px"></div></td>
                    <td style="padding-left: 1cm;">
		            Latitude interval
		            <br>
		            <input style="width:210px;" id="latmin" disabled="true" name="<portlet:namespace/>latmin" type="text" disabled="true" value="-90" id="latmin" label="Lat min" />
		            <input style="width:210px;" id="latmax" disabled="true" name="<portlet:namespace/>latmax" type="text" disabled="true" value="90" id="latmax" label="Lat max" />
		            <br>
		            <br>
		           
		            Longitude interval
		            <br>
		            <input style="width:210px;" id="lonmin" disabled="true" name="<portlet:namespace/>lonmin" type="text" disabled="true" value="0" id="lonmin" label="Lon min" />
		            <input style="width:210px;" id="lonmax" disabled="true" name="<portlet:namespace/>lonmax" type="text" disabled="true" value="360" id="lonmax" label="Lon max" />
		            <br>
		            <br>
		            
		            Select the models to compare
		            <br>      
		            <table>
		                <tr>
		                    <td style="width:150px;"><input id="model1" type="checkbox" name="<portlet:namespace/>model1" value="CCSM4"/> CCSM4</td>
		                    <td style="width:150px;"><input id="model2" type="checkbox" name="<portlet:namespace/>model2" value="CMCC-CM"/> CMCC-CM</td>
		                    <td style="width:150px;"><input id="model3" type="checkbox" name="<portlet:namespace/>model3" value="CMCC-CMS"/> CMCC-CMS</td>
		                    <td style="width:150px;"><input id="model4" type="checkbox" name="<portlet:namespace/>model4" value="CNRM-CM5"/> CNRM-CM5</td>
		                  </tr>
		                  <tr>
		                    <td style="width:150px;"><input id="model5" type="checkbox" name="<portlet:namespace/>model5" value="CanESM2"/> CanESM2</td>
		                    <td style="width:150px;"><input id="model6" type="checkbox" name="<portlet:namespace/>model6" value="INM-CM4"/> INM-CM4</td>
		                    <td style="width:150px;"><input id="model7" type="checkbox" name="<portlet:namespace/>model7" value="IPSL-CM5A-MR"/> IPSL-CM5A-MR</td>
		                    <td style="width:150px;"><input id="model8" type="checkbox" name="<portlet:namespace/>model8" value="MIROC5"/> MIROC5</td>
		                  </tr>
		                  <tr>
		                    <td style="width:150px;"><input id="model9" type="checkbox" name="<portlet:namespace/>model9" value="MPI-ESM-MR"/> MPI-ESM-MR</td>
		                    <td style="width:150px;"><input id="model10" type="checkbox" name="<portlet:namespace/>model10" value="MRI-CGCM3"/> MRI-CGCM3</td>
		                    <td style="width:150px;"><input id="model11" type="checkbox" name="<portlet:namespace/>model11" value="NorESM1-M"/> NorESM1-M</td>
		                    <td style="width:150px;"></td>
		                  </tr>	           
		            </table>
		            <br>
		           
		            Select a scenario
		            <br>
		            <select style="width:210px;" name="<portlet:namespace/>scenario" id="scenario" label="Select a scenario">
		                <option value="rcp45">RCP 4.5</option>
		                <option value="rcp85">RCP 8.5</option>
		            </select>
		            <br>
		            <br>
		               
		            Select a time frequency
		            <br>           
		            <select style="width:210px;" name="<portlet:namespace/>time_frequency" id="time_frequency" label="Select a time frequency">time_frequency">
		                <option value="day">day</option>
		                <option value="mon">mon</option>
		            <select>
		            </td>
	            </tr>
	            <tr>
                    <td></td>
                    <td style="padding-left: 1cm;">
	                    Select a percentile
			            <br>
			            <input type="range" style="width:210px;" min="0" max="1" value="0.5" id="percentileValue" step="0.1" oninput="outputPercentile(value)">
			            <output id="percentile" for="percentileValue">0.5</output>
			            <br>
			            <br>
			                           
			            Insert the historical time period
			            <br>       
			            <p><input style="width:210px;" name="<portlet:namespace/>historical_time_min" type="text" value="1976" id="historical_time_min" label="Historical time min" />
			            <input style="width:210px;" name="<portlet:namespace/>historical_time_max" type="text" value="2006" id="historical_time_max" label="Historical time max" /></p>
			            <br>
			           
			            Insert the scenario time period
			            <br>
			            <p><input style="width:210px;" name="<portlet:namespace/>scenario_time_min" type="text" value="2071" id="scenario_time_min" label="Scenario time min" />
			            <input style="width:210px;" name="<portlet:namespace/>scenario_time_max" type="text" value="2101" id="scenario_time_max" label="Scenario time max" /></p>
			            <br>
 					</td>
 				</tr>

            </table>
            <div class="wrapper">
    			<input type="button" value="Submit" onclick="formSubmit()" name="<portlet:namespace/>submit"/>
			</div>    
        <form>
    </div>      
</div>

<script>
var gMap = null;
var drawingManager = null;
var drawingrectangle = null;

function outputPercentile(vol) {
	document.querySelector('#percentile').value = vol;
}

function formSubmit() {
	var token = document.getElementById('<portlet:namespace/>token').value;

	var latmin = document.getElementById("latmin").value;
	var latmax = document.getElementById("latmax").value;
	var lonmin = document.getElementById("lonmin").value;
	var lonmax = document.getElementById("lonmax").value;
	
	var models = "";
	
	if (document.getElementById("model1").checked) {
		models += document.getElementById("model1").value +  "|";
	}
	if (document.getElementById("model2").checked) {
		models += document.getElementById("model2").value +  "|";
	}
	if (document.getElementById("model3").checked) {
		models += document.getElementById("model3").value +  "|";
	}
	if (document.getElementById("model4").checked) {
		models += document.getElementById("model4").value +  "|";
	}
	if (document.getElementById("model5").checked) {
		models += document.getElementById("model5").value +  "|";
	}
	if (document.getElementById("model6").checked) {
		models += document.getElementById("model6").value +  "|";
	}
	if (document.getElementById("model7").checked) {
		models += document.getElementById("model7").value +  "|";
	}
	if (document.getElementById("model8").checked) {
		models += document.getElementById("model8").value +  "|";
	}
	if (document.getElementById("model9").checked) {
		models += document.getElementById("model9").value +  "|";
	}
	if (document.getElementById("model10").checked) {
		models += document.getElementById("model10").value +  "|";
	}
	if (document.getElementById("model11").checked) {
		models += document.getElementById("model11").value +  "|";
	}
	
	var scenario = document.getElementById("scenario").value;
	var time_frequency = document.getElementById("time_frequency").value;
	var percentile = document.getElementById("percentile").value;
	
	var historical_time_min = document.getElementById("historical_time_min").value;
	var historical_time_max = document.getElementById("historical_time_max").value;
	
	var scenario_time_min = document.getElementById("scenario_time_min").value;
	var scenario_time_max = document.getElementById("scenario_time_max").value;
	
	$.ajax({
		type: "POST",
		data: {"<portlet:namespace />token" : token,
			   "<portlet:namespace />modelsString" : models,
			   "<portlet:namespace />latmin" : latmin, 
			   "<portlet:namespace />latmax" : latmax,
			   "<portlet:namespace />lonmin" : lonmin,
			   "<portlet:namespace />lonmax" : lonmax,
			   
			   "<portlet:namespace />scenario" : scenario,
			   "<portlet:namespace />time_frequency" : time_frequency,
			   "<portlet:namespace />percentile" : percentile,
			   
			   "<portlet:namespace />historical_time_min" : historical_time_min,
			   "<portlet:namespace />historical_time_max" : historical_time_max,
			   "<portlet:namespace />scenario_time_min" : scenario_time_min,
			   "<portlet:namespace />scenario_time_max" : scenario_time_max,
	    },
        url: "<%=submitExperiment%>",
    	success: function (response) {
    		refreshtable();
     	}
	});	
}

function myMap() {
	
	    gMap = new google.maps.Map(document.getElementById('mapDiv'), {
	        center: {lat: 0, lng: 0},
	        zoom: 2     
	    });
	    
	    drawingManager = new google.maps.drawing.DrawingManager({
	        drawingControl: true,
	        drawingControlOptions: {
	          position: google.maps.ControlPosition.TOP_CENTER,
	          drawingModes: [
	            google.maps.drawing.OverlayType.RECTANGLE
	          ]},
	        rectangleOptions: {
	            strokeColor: '#990033',
	            strokeOpacity: 0.8,
	            strokeWeight: 2,
	            fillColor: '#990033',
	            fillOpacity: 0.15
	        }
	    });
	       
	    drawingManager.addListener('rectanglecomplete', drawNewRect);
	    drawingManager.setMap(gMap);  
}

function drawNewRect(newRect) {
    if (drawingrectangle)
        drawingrectangle.setMap(null);

    drawingrectangle = newRect;
    var rectangleBounds = drawingrectangle.getBounds();
   
    var northEast = rectangleBounds.getNorthEast();
    var southWest = rectangleBounds.getSouthWest();
    var latrange = southWest.lat() + ':' + northEast.lat();
    var lonrange = southWest.lng() + ':' + northEast.lng();
    
    var latmin = southWest.lat();
    var latmax = northEast.lat();
    
    var lonmin = southWest.lng();
    var lonmax_temp = northEast.lng();
    var lonmax = null;
    
    if (lonmin > 0 && lonmax_temp < 0) {
    	lonmax = 360 + lonmax_temp;
    }
    else {
    	lonmax = lonmax_temp;
    }
    
    document.getElementById("latmin").value = latmin.toFixed(2);
    document.getElementById("latmax").value = latmax.toFixed(2);
    document.getElementById("lonmin").value = lonmin.toFixed(2);
    document.getElementById("lonmax").value = lonmax.toFixed(2); 
}
</script>

<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCP9_o8xTeQmJYQgAP4b_sCaFvbhkOcE3k&callback=myMap&libraries=drawing"></script>

<script type="text/javascript">
    Liferay.Service(
         '/iam.token/get-token',
         function(obj) {
        	 document.getElementById('<portlet:namespace/>token').value = obj.token;             
         }
    );
</script>