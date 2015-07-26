package com.yufei.dataget.dataretriver;
/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * $Id$
 * Created on 26.02.2007
 */


import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * @author jasstion
 *
 */
public interface HttpDataRetriever {
    public static Integer CONNECTION_TIMEOUT=5*1000;
    public static Integer REQUEST_TIMEOUT=5*1000;

	public URL getUrl();
	public void setUrl(URL url);

	
    public void addHeader(String key,String value);
	public void connect() ;

	public void disconnect() ;

	public long getContentLenght() ;
   
	public InputStream getContent();
	public String getHtmlContent();
	public Long getConnectIntervalTime();
	public  void setFormParams(List<BasicNameValuePair> formParams);
}