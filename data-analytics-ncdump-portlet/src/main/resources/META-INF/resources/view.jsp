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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/init.jsp" %>
<script type="text/javascript">
	var token = null;
	Liferay.Service(
         '/iam.token/get-token',
         function(obj) {
        	 token = obj;
         }
    );
</script>

<div class="panel panel-default">
    <div class="panel-body">
    	<c:import url="<%=(String)request.getAttribute("url") %>" />
    </div>        
</div>
<div class="panel-footer"></div>
