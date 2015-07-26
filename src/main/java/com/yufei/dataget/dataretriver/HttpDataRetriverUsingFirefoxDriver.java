/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yufei.dataget.dataretriver;

import com.yufei.utils.TimeOutUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author jasstion
 */
public class HttpDataRetriverUsingFirefoxDriver extends AbstractHttpDataRetriever {
	private static Log mLog = LogFactory
			.getLog(HttpDataRetriverUsingFirefoxDriver.class);
    private FirefoxDriver driver = null;

    public HttpDataRetriverUsingFirefoxDriver(DataRetrieverFeatures dataRetrieverFeatures) {
        super(dataRetrieverFeatures);
    }

    @Override
    public String getHtmlContent() {
        return driver.getPageSource();
    }

    @Override
    public void connect() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("permissions.default.stylesheet", 2);
        profile.setPreference("permissions.default.image", 2);
        profile.setPreference("dom.ipc.plugins.enabled.libflashplayer.so", "false");
        //profile.setPreference("DISPLAY", ":10");
        FirefoxBinary fb=new FirefoxBinary();
        fb.setEnvironmentProperty("DISPLAY", ":10");
        // sudo Xvfb :10 -ac 
        driver = new FirefoxDriver(fb,profile);
        driver.get(this.url.toString());

    }

    @Override
    public void disconnect() {
        driver.quit();
    }
    public static void main(String[] args) throws Exception {
     
        
        
          final long startTime = System.currentTimeMillis();
    mLog.info("calling runWithTimeout!");
    HttpDataRetriverUsingFirefoxDriver hdrufd =new HttpDataRetriverUsingFirefoxDriver(null);

    try {
    String htmlContent= TimeOutUtils.runWithTimeout(new Callable<String>() {

          @Override
          public String call() throws Exception {
              String url="http://www.baidu.com/tools?url=http%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DO0urTq_fyCkG3Rd2veZDQm7TLJ50XyUOeeoybddPUG6zBjpgh37XHtMM_oXKe4nQxM-q5IEVjldslw0tbkMfvK&jump=http%3A%2F%2Fkoubei.baidu.com%2Fwomc%2Fp%2Fsentry%3Ftitle%3D%012014%01-%012015%01%E5%B9%B4%E5%BA%A6%01QS%01%E4%B8%96%E7%95%8C%01%E6%8E%92%E5%90%8D%01%3A%01%E6%BE%B3%E5%A4%A7%E5%88%A9%E4%BA%9A%01%E5%A4%A7%E5%AD%A6%0123%01%E6%89%80%01%E9%AB%98%E6%A0%A1%01%E8%BF%9B%E5%85%A5%02TOP%01500%03-%01%E7%95%99%E5%AD%A6%01-%01...%26q%3Dtop%20500%20university&key=surl";
        hdrufd.setUrl(new URL(url));
        hdrufd.connect();

        System.out.print(hdrufd.getHtmlContent());
              return hdrufd.getHtmlContent();

          }
      }, 10, TimeUnit.SECONDS);
    }
    catch (TimeoutException e) {
      mLog.info("got timeout!");
    }finally{
                              hdrufd.disconnect();

    }
    mLog.info("end of main method!");
        
    }

}
