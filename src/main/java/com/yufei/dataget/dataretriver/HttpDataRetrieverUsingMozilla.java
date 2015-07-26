package com.yufei.dataget.dataretriver;


import com.yufei.dataget.entity.ProxyServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.net.URL;




public class HttpDataRetrieverUsingMozilla
 extends AbstractHttpDataRetriever{
	Display display=null;
	
protected String content;
private Runnable runnable=null;


public HttpDataRetrieverUsingMozilla(DataRetrieverFeatures dataRetrieverFeatures) {
	super(dataRetrieverFeatures);
}

private class 	ContentGetter implements ProgressListener{

	public void changed(ProgressEvent paramProgressEvent) {
		// TODO Auto-generated method stub
		
	}

	public void completed(ProgressEvent paramProgressEvent) {
		// TODO Auto-generated method stub
		Browser b=(Browser) paramProgressEvent.getSource();
		content=b.getText();
		String reallyUrl=null;
		/*try {
		reallyUrl=(String) b.evaluate("return window.location.href;");
	
			url=new URL(reallyUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//do  nothing
		}*/
		display.timerExec(-1, runnable);

		
		b.getShell().dispose();
	}
	
}




	/* (non-Javadoc)
	 * @see com.yufei.infoExtractor.io.AbstractHttpDataRetriever#connect()
	 */
	public void connect()  {
		// TODO Auto-generated method stub
	
		if(this.dataRetrieverFeatures!=null){
			Long connectintervalTime=null;

			if(this.dataRetrieverFeatures.getConnectIntervalTime()!=null){
				connectintervalTime=dataRetrieverFeatures.getConnectIntervalTime();
			/*	try {
					Thread.currentThread().sleep(1000*connectintervalTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					
				}	*/
			}
			
		}
		
		display = new Display(); 
	    runnable=new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				display.dispose();
			}
		};
		//毫秒
		int timeout=dataRetrieverFeatures.getRequestTimeout();
		display.timerExec(timeout, runnable);
		final Shell shell = new Shell(display);
	    Browser b=new Browser(shell, SWT.WEBKIT);
	   
	   b.addProgressListener(new ContentGetter());
	   b.setJavascriptEnabled(true);
	   b.setUrl(this.url.toString());
	   
	  
	   //shell.setVisible(false);
	   shell.setSize(0, 0);
	   shell.open(); 
	   //browser.setUrl("http://oreilly.com");  
	 
	
	 
	   while (!shell.isDisposed()) {  
		  
	         if (!display.readAndDispatch())  
	       display.sleep();  
	   
	   }
	}

	public void disconnect()  {
		// TODO Auto-generated method stub
	display.dispose();
		
	}

	public long getContentLenght() {
		// TODO Auto-generated method stub
		return 0;
	}




	

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}


public static void main(String[] args) throws Exception{
	 final Log mLog = LogFactory.getLog(HttpDataRetrieverUsingMozilla.class);

	/*String xULRunnerPath_key="org.eclipse.swt.browser.XULRunnerPath";
	String xULRunnerPath_value="C:\\Users\\jasstion\\Downloads" +
			"";

	System.setProperty(xULRunnerPath_key, xULRunnerPath_value);

	ProxyServer proxy=null;
	HttpDataRetriever	dataRetriever=new HttpDataRetrieverUsingWebkit(new DataRetrieverFeatures(false, proxy) );
	//<DIV class=goods_fr[\n\s\r]{1,}([\S\n\s\r]{0,}?)</OL>
		//for(int i=0;i<100;i++){pageBtnhref="(.*?)"> msgCnt>(.*?)</div> http://www.baidu.com/s?wd=%E9%92%93%E9%B1%BC%E5%B2%9B&pn=30&tn=baiduhome_pg&ie=utf-8&rsv_page=1
			dataRetriever.setUrl(new URL("" +
					"" +
					"http://www.baidu.com/baidu?word=swt+browser+timeout&se=ichuner_1_"+					""));					
					
					dataRetriever.connect();
			dataRetriever.disconnect();
		//}
		String str=(dataRetriever).getHtmlContent();
		str=CommonUtil.removeSpace(str);
		System.out.print(str);
	   System.out.print(URLEncoder.encode("diaoyudao", "UTF-8")+"\n");*/
	ProxyServer proxy=null;
	HttpDataRetriever	dataRetriever=new HttpDataRetrieverUsingMozilla(new DataRetrieverFeatures(true, proxy) );
	dataRetriever.setUrl(new URL("http://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8"));
	dataRetriever.connect();
System.out.print(dataRetriever.getHtmlContent());
	


}
@Override
public String getHtmlContent() {
	// TODO Auto-generated method stub
	return content;
}






}

