/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yufei.dataget.utils;

import com.google.common.collect.Lists;
import com.yufei.dataget.entity.PaginationRule;
import com.yufei.dataget.entity.UrlParameter;
import com.yufei.utils.CommonUtil;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * @author jasstion
 */
public class HtmlUtilTest {

    public HtmlUtilTest() {
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
     * Test of removeJSAndCss method, of class HtmlUtil.
     */
    @Test
    public void testRemoveJSAndCss() {
    }

    /**
     * Test of cleanHtml method, of class HtmlUtil.
     */
    @Test
    public void testCleanHtml() {
    }

    /**
     * Test of removeHtmlTag method, of class HtmlUtil.
     */
    @Test
    public void testRemoveHtmlTag() {
    }

    /**
     * Test of removeChineseChar method, of class HtmlUtil.
     */
    @Test
    public void testRemoveChineseChar() {
    }

    /**
     * Test of getImageUrls method, of class HtmlUtil.
     */
    @Test
    public void testGetImageUrls() {
    }

    /**
     * Test of encoderUrl method, of class HtmlUtil.
     */
    @Test
    public void testEncoderUrl() {
    }

    /**
     * Test of decodeUrl method, of class HtmlUtil.
     */
    @Test
    public void testDecodeUrl() {
    }

    /**
     * Test of detectEncoding method, of class HtmlUtil.
     */
    @Test
    public void testDetectEncoding() {
    }

    /**
     * Test of batchDowloadPcituresFormUrls method, of class HtmlUtil.
     */
    @Test
    public void testBatchDowloadPcituresFormUrls() {
    }

    /**
     * Test of downLoadPicture method, of class HtmlUtil.
     */
    @Test
    public void testDownLoadPicture() throws Exception {
    }

    /**
     * Test of getHtmlContent method, of class HtmlUtil.
     */
    @Test
    public void testGetHtmlContent_4args_1() {
    }

    /**
     * Test of getHtmlContent method, of class HtmlUtil.
     */
    @Test
    public void testGetHtmlContent_DataRetrieverFeatures_String() {
    }

    /**
     * Test of getHtmlContent method, of class HtmlUtil.
     */
    @Test
    public void testGetHtmlContent_4args_2() throws Exception {
    }

    /**
     * Test of getHtmlContent method, of class HtmlUtil.
     */
    @Test
    public void testGetHtmlContent_3args() throws Exception {
    }

    /**
     * Test of getHtmlContent method, of class HtmlUtil.
     */
    @Test
    public void testGetHtmlContent_String_boolean() throws Exception {
    }

    /**
     * Test of getResponseCode method, of class HtmlUtil.
     */
    @Test
    public void testGetResponseCode() {
    }

    /**
     * Test of generateUrlsByPaginationRule method, of class HtmlUtil.
     */
    @Test
    public void testGenerateUrlsByPaginationRule() {
        List<UrlParameter> urlParameters = new ArrayList();

        String urlTemplate = "test{}hello{}";
        PaginationRule paginationRule = new PaginationRule();
        paginationRule.setPaginationTemplate(urlTemplate);
        UrlParameter parameter1 = new UrlParameter();
        parameter1.setParameterType("String");
        parameter1.setParameterValue(CommonUtil.LinkStringWithSpecialSymbol(Lists.newArrayList("a", "b"), ","));
        parameter1.setParameterIndex(1);
        urlParameters.add(parameter1);
        UrlParameter parameter2 = new UrlParameter();
        parameter2.setParameterType("Integer");
        parameter2.setBegainPagNumber(0);
        parameter2.setPagNumberSpace(100);
        parameter2.setParameterValue("12");
        urlParameters.add(parameter2);
        paginationRule.setUrlParameters(urlParameters);
        List<String> res = HtmlUtil.generateUrlsByPaginationRule(paginationRule);

        for (String string : res) {
            System.out.print(string + "\n");
        }
        urlParameters.remove(parameter2);

        parameter2 = new UrlParameter();
        parameter2.setParameterType("Integer");
        parameter2.setParameterValue("12");
        urlParameters.add(parameter2);
        paginationRule.setUrlParameters(urlParameters);
        res = HtmlUtil.generateUrlsByPaginationRule(paginationRule);

        for (String string : res) {
            System.out.print(string + "\n");
        }

    }

    /**
     * Test of makeUrlsByPaginationTemplate method, of class HtmlUtil.
     */
    @Test
    public void testMakeUrlsByPaginationTemplate() {
    }

    /**
     * Test of main method, of class HtmlUtil.
     */
    @Test
    public void testMain() throws Exception{

        String url="http://d.g.wanfangdata.com.cn/Periodical_nmgzyy201503001.aspx";
        String htmlContent=HtmlUtil.getHtmlContent(url,false);
        System.out.print(htmlContent);

    }
    public static void  main(String[] args)throws Exception{

        String url="http://d.g.wanfangdata.com.cn/Periodical_nmgzyy201503001.aspx";
        String htmlContent=HtmlUtil.getHtmlContent(url,false);
        System.out.print(htmlContent);
    }

}
