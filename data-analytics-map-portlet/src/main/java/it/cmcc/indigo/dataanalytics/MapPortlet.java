/**
 * *********************************************************************
 * Copyright (c) 2017: Euro Mediterranean Center on Climate Change (CMCC)
 * Foundation - INDIGO-DataCloud
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
 **********************************************************************
 */

package it.cmcc.indigo.dataanalytics;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.Portlet;
import javax.portlet.ProcessEvent;

import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=Data Analytics",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.name=varmap",
		"javax.portlet.display-name=Variance Map",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supported-processing-event=getpath;http://cloud144.ncg.ingrid.pt"
	},
	service = Portlet.class
)

public class MapPortlet extends MVCPortlet {
	
	@ProcessEvent(qname="{http://cloud144.ncg.ingrid.pt}getpath")
	public void getPath(EventRequest request, EventResponse response) {
		System.out.println("consumer!!!");
		Event event = request.getEvent();
		String urltoken = (String)event.getValue();
		System.out.println("consumer urltoken: " + urltoken);
		//response.setRenderParameter("urltoken", urltoken);
		request.setAttribute("urltoken", urltoken);
	}
}
