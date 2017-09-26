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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
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
    @Rule
    public TemporaryFolder folder= new TemporaryFolder();
    
    @Test
    public void testAddFilePart() throws IOException {
        MultipartUtility multipart = new MultipartUtility("http://www.google.it", "UTF-8", "token"); 
        
        //File uploadFile = Mockito.mock(File.class);
        File uploadFile = folder.newFile("fileName.txt");
        
        //Mockito.when(uploadFile.getName()).thenReturn("filename");
        //Mockito.when(URLConnection.guessContentTypeFromName(uploadFile.getName())).thenReturn("Content-Type");
        Mockito.mock(FileInputStream.class);
        
        multipart.addFilePart("fieldName", uploadFile);
    }
    
    @Test
    public void testReadResponse() throws IOException {
        MultipartUtility multipart = new MultipartUtility("http://www.google.it", "UTF-8", "token"); 
        InputStream is = new ByteArrayInputStream("test data".getBytes());
        
        multipart.readResponse(is);
         
    }

}
