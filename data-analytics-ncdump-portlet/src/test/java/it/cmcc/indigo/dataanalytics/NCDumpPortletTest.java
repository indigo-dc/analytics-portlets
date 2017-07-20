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

import static org.junit.Assert.assertTrue;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NCDumpPortletTest {

   @Mock
   private RenderRequest renderRequest;

   @Mock
   private RenderResponse renderResponse;

   @Before
   public final void setUp() {
      Mockito.when(renderRequest.getParameter("url")).
      thenReturn("http://remotetest.unidata.ucar.edu/dts/test.01.das");
   }

   @Test
   public final void testDoView() {
      String value = renderRequest.getParameter("url");

      String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?"
      + "=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
      assertTrue((value.matches(regex)));
   }
}
