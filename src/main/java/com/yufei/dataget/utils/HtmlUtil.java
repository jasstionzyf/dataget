package com.yufei.dataget.utils;

import com.yufei.dataget.dataretriver.DataRetrieverFactory;
import com.yufei.dataget.dataretriver.DataRetrieverFeatures;
import com.yufei.dataget.dataretriver.HttpDataRetriever;
import com.yufei.dataget.entity.PaginationRule;
import com.yufei.dataget.entity.ProxyServer;
import com.yufei.dataget.entity.UrlParameter;
import com.yufei.utils.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;


/**
 * created by @author jasstion at 2012-10-27
 */
public class HtmlUtil {

    final static String[] picturePostfix = {"png", "jpg", "gif", "bmp", "pcx", "dcx", "emf", "JIF", "JPE", "JFIF", "EPS", "TIF", "JPEG", "RLE", "DIB", "PCD", "DXF", "ICO", "WMF", "TIFF", "TGA"};
    private static Log mLog = LogFactory.getLog(HtmlUtil.class);

    public static String removeJSAndCss(String str) {
        if (str == null) {
            throw new IllegalArgumentException();
        }

        str = str.replaceAll("<STYLE.{0,}?>", " ");
        str = str.replaceAll("<style.{0,}?>", " ");

        str = str.replaceAll("<SCRIPT.{0,}?>", " ");
        str = str.replaceAll("<script.{0,}?>", " ");
        //str=str.replaceAll("<[\\S\\s\\r\\n]{0,}?>", "");

        return str;

    }

    public static String cleanHtml(String str) {
        String result = null;
        result = HtmlUtil.removeJSAndCss(str);
        result = HtmlUtil.removeHtmlTag(result);
        return result;

    }

    public static String removeHtmlTag(String str) {
        if (str == null) {
            throw new IllegalArgumentException();
        }
        String[] specialHtmlChars = {"&amp;", "nbsp;", "quot;", "gt;"};
//remove htmlTag
        str = str.replaceAll("<.{0,}?>", " ");
        str = str.replaceAll("[\\r\\n]", " ");
        //对于一些特殊字符的过滤
        for (String tempStr : specialHtmlChars) {
            str = str.replaceAll(tempStr, " ");
        }
        return str;

    }

    public static String removeChineseChar(String source) {
        if (source == null) {
            throw new IllegalArgumentException();
        }

        return source.replaceAll("[\\u4E00-\\u9FA5]", "");
    }

    /**
     * @param htmlContent
     * @return 注意：返回的结果字符串可能含有单双引号或者不是完整的链接（需要补域名）
     */
    public static List<String> getImageUrls(String htmlContent) {
        List<String> imagesUrls = new ArrayList<String>();
        //<img src=http://g1.ykimg.com/0100401F4650B2406016CD06E0B90FE94359C6-4FD2-473C-1FA7-C1B5470900C7replace="false">   
        String imageUrlPattern = "<img[\\s\\S\\r\\n]{0,}src=(.*?)\\s";
        imagesUrls = PatternUtils.getListStrByRegex(htmlContent, imageUrlPattern);
        String tempPostfix = null;
        for (String imageUrl : imagesUrls) {

            tempPostfix = getPostfixOfPictureUrl(imageUrl);
            if (CommonUtil.isEmptyOrNull(tempPostfix)) {
                continue;
            }
            imageUrl = imageUrl.substring(0, imageUrl.lastIndexOf(tempPostfix));

        }
        return imagesUrls;

    }

    public static String encoderUrl(String url) {
        String result = null;
        try {
            result = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            ExceptionUtil.getExceptionDetailsMessage(e);
        }
        return result;

    }

    public static String decodeUrl(String url) {
        String result = null;
        try {
            result = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            ExceptionUtil.getExceptionDetailsMessage(e);
        }
        return result;

    }

    public static String detectEncoding(InputStream inputStream) {
        String encoding = null;
        //int readBytes=
        return encoding;

    }

    private static String getPostfixOfPictureUrl(String pictureUrl) {
        String pictureUrlPostfix = null;
        for (String postfix : picturePostfix) {
            if (pictureUrl.toLowerCase().contains(postfix.toLowerCase())) {
                pictureUrlPostfix = postfix;
                break;
            }

        }
        return pictureUrlPostfix;
    }

    /**
     * @param urls:must be validate url
     * @return return a collection that hold the pictureName ,if url download
     * failure ,then the pictureName is null
     */
    public static List<String> batchDowloadPcituresFormUrls(List<String> urls, String mediaDirectory) {
        List<String> pictureNames = new ArrayList<String>();
        ProxyServer proxy = null;
        HttpDataRetriever dataRetriever = DataRetrieverFactory.createDataRetriever(new DataRetrieverFeatures(false, proxy));
        String pictureName = null;

        for (String picturerl : urls) {
            /*pictureName = SequenceUUID.getInstance().getUUID()
             + picturerl.substring(picturerl.lastIndexOf("."));*/
            //这里生成的图片名称和原始网页中的图片名称保持一致
            //如果url路径中含有引号的话则直接替换
            picturerl = picturerl.replace("\"", "");
            picturerl = picturerl.replace("\'", "");

            pictureName = picturerl.substring(picturerl.lastIndexOf("/"));
            try {
                dataRetriever.setUrl(new URL(picturerl));

                dataRetriever.connect();
                InputStream input = dataRetriever.getContent();
                FileUtil.makeFile(mediaDirectory, pictureName, input);
                dataRetriever.disconnect();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                mLog.info("wrong when download the image file from url:" + picturerl);
                mLog.debug(ExceptionUtil.getExceptionDetailsMessage(e));
                pictureName = null;
            }
        }
        return pictureNames;
    }

    public static void downLoadPicture(String mediaUrl, String mediaSaveName, String mediaDirectory)
            throws MalformedURLException, IOException {

        ProxyServer proxy = null;
        HttpDataRetriever dataRetriever = DataRetrieverFactory.createDataRetriever(new DataRetrieverFeatures(false, proxy));
        dataRetriever.setUrl(new URL(mediaUrl));
        dataRetriever.connect();
        InputStream input = dataRetriever.getContent();
        FileUtil.makeFile(mediaDirectory, mediaSaveName, input);
        dataRetriever.disconnect();
    }

    /**
     * @param url
     * @param visitCount
     * @param connectIntervalTime:s
     * @param isRequireJs
     * @return
     */
    public static String getHtmlContent(String url, int visitCount, long connectIntervalTime, boolean isRequireJs) {
        String htmlContent = null;
        if (visitCount == 0) {
            mLog.info("针对链接:" + url + "的访问失败！");
            return null;
        } else {
            try {
                htmlContent = getHtmlContent(url, isRequireJs, 10 * 1000, connectIntervalTime);

            } catch (Exception e) {
                htmlContent = HtmlUtil.getHtmlContent(url, visitCount - 1, connectIntervalTime, isRequireJs);
            }
            if (CommonUtil.isEmptyOrNull(htmlContent)) {
                htmlContent = HtmlUtil.getHtmlContent(url, visitCount - 1, connectIntervalTime, isRequireJs);
            } else {
                return htmlContent;
            }
        }

        return htmlContent;
    }
  public static String getHtmlContent(String url, int visitCount, long connectIntervalTime, boolean isRequireJs,Map<String,String> headers) {
        String htmlContent = null;
        if (visitCount == 0) {
            mLog.info("针对链接:" + url + "的访问失败！");
            return null;
        } else {
            try {
                htmlContent = getHtmlContent(url, isRequireJs, 10 * 1000, connectIntervalTime,headers);

            } catch (Exception e) {
                htmlContent = HtmlUtil.getHtmlContent(url, visitCount - 1, connectIntervalTime, isRequireJs);
            }
            if (CommonUtil.isEmptyOrNull(htmlContent)) {
                htmlContent = HtmlUtil.getHtmlContent(url, visitCount - 1, connectIntervalTime, isRequireJs);
            } else {
                return htmlContent;
            }
        }

        return htmlContent;
    }
    public static String getHtmlContent(DataRetrieverFeatures dataRetrieverFeatures, String url) {
        String htmlContent = null;
        int visitCount = dataRetrieverFeatures.getRetryCount();
        if (visitCount == 0) {
            mLog.info("针对链接:" + url + "的访问失败！");
            return null;
        } else {
            try {
                String htmlStr = null;
                ProxyServer proxy = null;
                HttpDataRetriever dataRetriever = DataRetrieverFactory
                        .createDataRetriever(dataRetrieverFeatures);

                dataRetriever.setUrl(new URL(url));
                dataRetriever.connect();
                htmlStr = dataRetriever.getHtmlContent();
                dataRetriever.disconnect();

                return htmlStr;

            } catch (IOException e) {
                // TODO Auto-generated catch block
                htmlContent = HtmlUtil.getHtmlContent(dataRetrieverFeatures, url);
            }
            if (CommonUtil.isEmptyOrNull(htmlContent)) {
                htmlContent = HtmlUtil.getHtmlContent(dataRetrieverFeatures, url);
            } else {
                return htmlContent;
            }
        }

        return htmlContent;
    }

    /**
     * @param url
     * @param isRequireJS
     * @param requestTimeOut：毫秒 0表示没有限制，表示无限长的时间可以超时
     * @param connectIntervalTime:秒
     * @return
     * @throws IOException
     */
    @Deprecated
    public static String getHtmlContent(String url, boolean isRequireJS, int requestTimeOut, long connectIntervalTime) throws IOException {
        String htmlStr = null;
        ProxyServer proxy = null;
        DataRetrieverFeatures dataRetrieverFeatures = new DataRetrieverFeatures(isRequireJS, proxy);
        HttpDataRetriever dataRetriever = DataRetrieverFactory
                .createDataRetriever(dataRetrieverFeatures);
        dataRetrieverFeatures.setRequestTimeout(requestTimeOut);
        
        dataRetrieverFeatures.setConnectIntervalTime(connectIntervalTime);

        dataRetriever.setUrl(new URL(url));
        dataRetriever.connect();
        htmlStr = dataRetriever.getHtmlContent();
        dataRetriever.disconnect();

        return htmlStr;

    }
     @Deprecated
    public static String getHtmlContent(String url, boolean isRequireJS, int requestTimeOut, long connectIntervalTime,Map<String,String> headers) throws IOException {
        String htmlStr = null;
        ProxyServer proxy = null;
        DataRetrieverFeatures dataRetrieverFeatures = new DataRetrieverFeatures(isRequireJS, proxy);
        HttpDataRetriever dataRetriever = DataRetrieverFactory
                .createDataRetriever(dataRetrieverFeatures);
        dataRetrieverFeatures.setRequestTimeout(requestTimeOut);
        
        dataRetrieverFeatures.setConnectIntervalTime(connectIntervalTime);

        dataRetriever.setUrl(new URL(url));
         for (Map.Entry<String, String> entry : headers.entrySet()) {
             String string = entry.getKey();
             String string1 = entry.getValue();
             dataRetriever.addHeader(string, string1);
             
         }
        dataRetriever.connect();
        htmlStr = dataRetriever.getHtmlContent();
        dataRetriever.disconnect();

        return htmlStr;

    }


    @Deprecated
    public static String getHtmlContent(ProxyServer proxyIdentity, boolean isRequestJs, String url)
            throws IOException {

        if (CommonUtil.isEmptyOrNull(url)) {
            throw new IllegalArgumentException("url 不能为空后者null!");
        }
        String htmlStr = null;
        HttpDataRetriever dataRetriever = DataRetrieverFactory
                .createDataRetriever(new DataRetrieverFeatures(isRequestJs, proxyIdentity));

        dataRetriever.setUrl(new URL(url));
        dataRetriever.connect();
        htmlStr = dataRetriever.getHtmlContent();
        dataRetriever.disconnect();

        return htmlStr;

    }

    @Deprecated
    public static String getHtmlContent(String url, boolean isRequireJS) throws IOException {
        String htmlStr = null;
        ProxyServer proxy = null;
        HttpDataRetriever dataRetriever = DataRetrieverFactory
                .createDataRetriever(new DataRetrieverFeatures(isRequireJS, proxy));
        dataRetriever.setUrl(new URL(url));
        dataRetriever.connect();
        htmlStr = dataRetriever.getHtmlContent();
        dataRetriever.disconnect();

        return htmlStr;

    }

    public static int getResponseCode(String url) {
        int rCode = 0;
        HttpClient httpclient = new DefaultHttpClient();
        //HttpClient httpclient = new DecompressingHttpClient(httpclient1);
        String userAgent = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:21.0) Gecko/20100101 Firefox/21.0";
        httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, userAgent);
        Integer connection_timeout = 6 * 1000;
        httpclient.getParams().
                setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connection_timeout == null ? HttpDataRetriever.CONNECTION_TIMEOUT : connection_timeout);

        //请求超时
        Integer waitResponse_timeout = 10 * 1000;
        httpclient.getParams().
                setIntParameter(CoreConnectionPNames.SO_TIMEOUT, waitResponse_timeout == null ? HttpDataRetriever.REQUEST_TIMEOUT : waitResponse_timeout);
        HttpGet httpPost;
        try {
            httpPost = new HttpGet(new URI(url));

            HttpResponse httpResponse = httpclient.execute(httpPost);

            rCode = httpResponse.getStatusLine().getStatusCode();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rCode;
    }

    public static List<String> generateUrlsByPaginationRule(
            PaginationRule paginationRule) {
        if (paginationRule == null
                || paginationRule.getPaginationTemplate() == null) {
            return null;

        }
        List<String> urls = new ArrayList<String>();
        String paginationTemplate = paginationRule.getPaginationTemplate();
        List<UrlParameter> urlParameters = paginationRule.getUrlParameters();
        Collections.sort(urlParameters, new Comparator<UrlParameter>() {

            public int compare(UrlParameter o1, UrlParameter o2) {
                // TODO Auto-generated method stub
                if (o1.getParameterIndex() > o2.getParameterIndex()) {
                    return 1;
                }
                return -1;
            }
        });

        String[] strs = new String[urlParameters.size()];
        List<String[]> strses = new ArrayList<String[]>();
        // 每个urlParameter对应的值得数组
        List<String[]> strses1 = new ArrayList<String[]>();
        String[] temp = null;

        for (UrlParameter urlParameter : urlParameters) {
            if (urlParameter.getParameterType().equals("String")) {
                temp = urlParameter.getParameterValue().split(Constants.stringSplitSymbol);
            }
            if (urlParameter.getParameterType().equals("Integer")) {
                String maxpageNumber = urlParameter.getParameterValue();
                // 根据分页数差生一个页数数组
                //simple by pageNumber
                if (urlParameter.getBegainPagNumber() == null && urlParameter.getPagNumberSpace() == null) {
                    temp = new String[Integer.valueOf(maxpageNumber)];
                    for (int i = 1; i <= Integer.valueOf(maxpageNumber); i++) {
                        temp[i - 1] = String.valueOf(i);
                    }
                } //by recoredNumber
                else {
                    temp = new String[Integer.valueOf(maxpageNumber)];
                    for (int i = 1; i <= Integer.valueOf(maxpageNumber); i++) {
                        temp[i - 1] = String.valueOf(urlParameter.getBegainPagNumber() + (i - 1) * urlParameter.getPagNumberSpace());
                    }
                }

            }
            for (int i = 0; i < temp.length; i++) {
                temp[i] = HtmlUtil.encoderUrl(temp[i]);
            }
            strses1.add(temp);

        }
        strses = CommonUtil.getDKL(strses1);
        String url = null;
        for (String[] stres : strses) {
            url = CommonUtil.replaceByIndex(paginationTemplate, "\\{\\}", stres);
            urls.add(url);
        }
        return urls;
    }

    public static List<String> makeUrlsByPaginationTemplate(String paginationTemplate, int begainPagNumber, int pageNumberSpace, int paraValue) {
        if (!paginationTemplate.contains("{}")) {
            throw new IllegalArgumentException("参数有误");
        }
        PaginationRule paginationRule = new PaginationRule();
        paginationRule.setPaginationTemplate(paginationTemplate);
        UrlParameter urlParameter = new UrlParameter();
        urlParameter.setBegainPagNumber(begainPagNumber);
        urlParameter.setPagNumberSpace(pageNumberSpace);
        urlParameter.setParameterType("Integer");
        urlParameter.setParameterValue(String.valueOf(paraValue));
        paginationRule.getUrlParameters().add(urlParameter);
        List<String> urls = generateUrlsByPaginationRule(paginationRule);
        return urls;
    }

    public static void main(String[] args) {
        String url = "https://www.google.com.sg/search?q=top+500+university&oq=top+500+&aqs=chrome.0.69i59j0j69i57j0l3.6004j0j4&sourceid=chrome&es_sm=93&ie=UTF-8";
        url = "http://item.jd.com/1217499.html";
	String htmlContent=HtmlUtil.getHtmlContent(url, 2, 0, false);
	System.out.print(htmlContent);
//        FirefoxDriver driver = new FirefoxDriver();
//   
//        driver.get("http://www.baidu.com/");
//       
//      System.out.print(driver.getPageSource());
//      driver.quit();
        // PhantomJSDriverService.Builder bulid=new Builder();
       

    }
}
