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
        <form action="<%=submitExperiment%>" method="Post">
            <input name="<portlet:namespace/>token" type="hidden" id="<portlet:namespace/>token"/>
            <br>
            
            <table>
                <tr>
                    <td><br><br><div id="mapDiv" style="width:600px;height:400px"></div></td>
                    <td style="padding-left: 1cm;">
		            Latitude interval
		            <br>
		            <input style="width:210px;" name="<portlet:namespace/>latmin" type="text" value="-90" id="latmin" label="Lat min" />
		            <input style="width:210px;" name="<portlet:namespace/>latmax" type="text" value="90" id="latmax" label="Lat max" />
		            <br>
		            <br>
		           
		            Longitude interval
		            <br>
		            <input style="width:210px;" name="<portlet:namespace/>lonmin" type="text" value="0" id="lonmin" label="Lon min" />
		            <input style="width:210px;" name="<portlet:namespace/>lonmax" type="text" value="360" id="lonmax" label="Lon max" />
		            <br>
		            <br>
		            
		            Select the models to compare
		            <br>      
		            <table>
		                <tr>
		                    <td style="width:150px;"><input type="checkbox" name="<portlet:namespace/>model" value="CCSM4"/> CCSM4</td>
		                    <td style="width:150px;"><input type="checkbox" name="<portlet:namespace/>model" value="CMCC-CM"/> CMCC-CM</td>
		                    <td style="width:150px;"><input type="checkbox" name="<portlet:namespace/>model" value="CMCC-CMS"/> CMCC-CMS</td>
		                    <td style="width:150px;"><input type="checkbox" name="<portlet:namespace/>model" value="CNRM-CM5"/> CNRM-CM5</td>
		                  </tr>
		                  <tr>
		                    <td style="width:150px;"><input type="checkbox" name="<portlet:namespace/>model" value="CanESM2"/> CanESM2</td>
		                    <td style="width:150px;"><input type="checkbox" name="<portlet:namespace/>model" value="INM-CM4"/> INM-CM4</td>
		                    <td style="width:150px;"><input type="checkbox" name="<portlet:namespace/>model" value="IPSL-CM5A-MR"/> IPSL-CM5A-MR</td>
		                    <td style="width:150px;"><input type="checkbox" name="<portlet:namespace/>model" value="MIROC5"/> MIROC5</td>
		                  </tr>
		                  <tr>
		                    <td style="width:150px;"><input type="checkbox" name="<portlet:namespace/>model" value="MPI-ESM-MR"/> MPI-ESM-MR</td>
		                    <td style="width:150px;"><input type="checkbox" name="<portlet:namespace/>model" value="MRI-CGCM3"/> MRI-CGCM3</td>
		                    <td style="width:150px;"><input type="checkbox" name="<portlet:namespace/>model" value="NorESM1-M"/> NorESM1-M</td>
		                    <td style="width:150px;"></td>
		                  </tr>	           
		            </table>
		            <br>
		           
		            Select a scenario
		            <br>
		            <select style="width:210px;" name="<portlet:namespace/>scenario" id="<portlet:namespace/>scenario" label="Select a scenario">
		                <option value="rcp45">RCP 4.5</option>
		                <option value="rcp85">RCP 8.5</option>
		            </select>
		            <br>
		            <br>
		               
		            Select a time frequency
		            <br>           
		            <select style="width:210px;" name="<portlet:namespace/>time_frequency" label="Select a time frequency" id="<portlet:namespace/>time_frequency">
		                <option value="day">day</option>
		                <option value="mon">mon</option>
		            <select>
		            </td>
	            </tr>
	            <tr>
                    <td></td>
                    <td style="padding-left: 1cm;">
	                    Insert a percentile
			            <br>
			            <input style="width:210px;" name="<portlet:namespace/>percentile" type="text" value="Values between 0.0 and 1.0" id="<portlet:namespace/>percentile" label="Percentile" />
			            <br>
			            <br>
			                           
			            Insert the historical time period
			            <br>       
			            <p><input style="width:210px;" name="<portlet:namespace/>historical_time_min" type="text" value="1970" id="<portlet:namespace/>historical_time_min" label="Historical time min" />
			            <input style="width:210px;" name="<portlet:namespace/>historical_time_max" type="text" value="2005" id="<portlet:namespace/>historical_time_max" label="Historical time max" /></p>
			            <br>
			           
			            Insert the scenario time period
			            <br>
			            <p><input style="width:210px;" name="<portlet:namespace/>scenario_time_min" type="text" value="2020" id="<portlet:namespace/>scenario_time_min" label="Scenario time min" />
			            <input style="width:210px;" name="<portlet:namespace/>scenario_time_max" type="text" value="2100" id="<portlet:namespace/>scenario_time_max" label="Scenario time max" /></p>
			            <br>
 					</td>
 				</tr>

            </table>
            <div class="wrapper">
    			<input type="submit" value="Submit" name="<portlet:namespace/>submit"/>
			</div>    
        <form>
    </div>      
</div>

<script>
var gMap = null;
var drawingManager = null;
var drawingrectangle = null;

function myMap() {
	
	    gMap = new google.maps.Map(document.getElementById('mapDiv'), {
	        center: {lat: 0, lng: 0},
	        zoom: 2     
	       
	/*         border      : false,
	        gmapType    : 'terrain',
	        mapConfOpts : ['enableScrollWheelZoom','enableDoubleClickZoom','enableDragging'],
	        mapControls : ['GSmallMapControl','GMapTypeControl','NonExistantControl'],
	        mapOptions  : {
	            zoom: 4
	        },
	        center      : {
	            geoCodeAddr: 'Brazil'
	        } */
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
    var lonmin = southWest.lng() + 180;
    var lonmax = northEast.lng() + 180;
    
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