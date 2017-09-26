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

package it.cmcc.indigo.dataanalytics.multimodel.utility;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MultipartUtilityTest {
    
    @Before
    public final void setUp() throws Exception {
    }  

    /**
     * Test the class.
     * @throws Exception in case of problem
     */
    @Test
    public final void testMultipartUtility() throws IOException {
        MultipartUtility multipart = new MultipartUtility("http://www.google.it", "UTF-8", "token"); 
        
    }
    
/*    @Test
    public List<String> testFinish() {
        return null;
        
    }*/
    
    @Test
    public void testReadResponse() throws IOException {
        MultipartUtility multipart = new MultipartUtility("http://www.google.it", "UTF-8", "token"); 
        
        //InputStream is = Mockito.mock(InputStream.class);
        InputStream is = new ByteArrayInputStream("test data".getBytes());
        //InputStreamReader isreader = new InputStreamReader(is);
        //BufferedReader reader = new BufferedReader(isreader);
       
        //Mockito.when(reader.readLine()).thenReturn("firstline");
        
        multipart.readResponse(is);
         
    }

}
