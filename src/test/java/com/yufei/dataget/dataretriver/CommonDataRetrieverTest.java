/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.yufei.dataget.dataretriver;

import com.yufei.dataget.entity.ProxyServer;
import com.yufei.utils.PatternUtils;
import org.junit.*;

import java.net.URL;

/**
 *
 * @author jasstion
 */
public class CommonDataRetrieverTest {
    
    public CommonDataRetrieverTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getHtmlContent method, of class CommonDataRetriever.
     */
    @Test
    public void testGetHtmlContent() {
    }

    /**
     * Test of disconnect method, of class CommonDataRetriever.
     */
    @Test
    public void testDisconnect() {
    }

    /**
     * Test of connect method, of class CommonDataRetriever.
     */
    @Test
    public void testConnect() throws Exception {
        
        
        String str="http://api.elsevier.com/content/search/scidir?start=900&count=100&content=all&query=ISSN(%2200033170%22)&apikey="
                + "c08c34da05f02d5df148799020ea29ef&field=url,issn,doi,pii,scopus-id,scopus-eid,pubmed-id,teaser,description,authKeywords,"
                + "openAccess,openAccessFlag,title,publicationName,issn,volume,issueIdentifier,issueName,startingPage,endingPage,coverDate,coverDisplayDate,creator,authors,pubType,aggregationType,copyright";
      ProxyServer proxy = null;
        DataRetrieverFeatures dataRetrieverFeatures = new DataRetrieverFeatures(false, proxy);
        dataRetrieverFeatures.setRequestTimeout(100* 1000);
        dataRetrieverFeatures.setConnectionTimeout(50 * 1000);
        HttpDataRetriever dataRetriever = DataRetrieverFactory
                .createDataRetriever(dataRetrieverFeatures);

        dataRetriever.setUrl(new URL(str));
        dataRetriever.addHeader("Accept", "application/xml");
        dataRetriever.connect();
       String  htmlStr = dataRetriever.getHtmlContent();
       String totalStr = PatternUtils.getStrByRegex(htmlStr, "totalResults>(.*?)</opensearch");
     //  Assert.assertTrue(1053==Integer.valueOf(totalStr));
    }

    /**
     * Test of getContentLenght method, of class CommonDataRetriever.
     */
    @Test
    public void testGetContentLenght() {
    }

    /**
     * Test of main method, of class CommonDataRetriever.
     */
    @Test
    public void testMain() throws Exception {
    }

    /**
     * Test of addHeader method, of class CommonDataRetriever.
     */
    @Test
    public void testAddHeader() {
    }
    
}
