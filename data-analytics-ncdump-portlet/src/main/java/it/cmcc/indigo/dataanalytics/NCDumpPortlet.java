/**
 * *********************************************************************
 * Copyright (c) 2017: Euro Mediterranean Center on Climate Change (CMCC)
 * Foundation - INDIGO-DataCloud
 *
 * See http://www.cmcc.it for details on the copyright holders.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **********************************************************************
 **/

package it.cmcc.indigo.dataanalytics;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

@Component(
   immediate = true,
   property = {
      "com.liferay.portlet.display-category=Data Analytics",
      "com.liferay.portlet.instanceable=true",
      "javax.portlet.display-name=Data analytics ncdump portlet",
      "javax.portlet.init-param.template-path=/",
      "javax.portlet.init-param.view-template=/view.jsp",
      "javax.portlet.resource-bundle=content.Language",
      "javax.portlet.security-role-ref=power-user,user"
   },
   service = Portlet.class
)

public class NCDumpPortlet extends MVCPortlet {
   public final void doView(final RenderRequest renderRequest,
      final RenderResponse renderResponse)
      throws IOException, PortletException {
         renderRequest.setAttribute("url",
            "http://remotetest.unidata.ucar.edu/dts/test.01.das");
         super.doView(renderRequest, renderResponse);
   }
}
