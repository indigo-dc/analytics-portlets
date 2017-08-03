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
package it.cmcc.indigo.dataanalytics;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SingleModelPortletTest {

	@Test
	public final void testDoViewRenderRequestRenderResponse() {
		String url = "http://opendap.knmi.nl/knmi/thredds/dodsC/CLIPC/cmcc/SWE/SWE_ophidia-0-10-1_CMCC_GlobSnow-SWE-L3B_monClim_19791001-20080701_1979-2008.nc.dds";
		
		String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		
		assertTrue(url.matches(regex));
	}

}
