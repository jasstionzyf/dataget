package com.yufei.dataget.entity;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author jasstion
   2012-12-26
 *
 */
@XmlRootElement
public class ProxyServer {
private String proxyIdentify=null; 
public ProxyServer() {
	super();
	// TODO Auto-generated constructor stub
}
private String host=null;
private String port=null;
private String user=null;
private String passwd=null;
public ProxyServer(String proxyIdentify, String host, String port, String user,
		String passwd) {
	super();
	this.proxyIdentify = proxyIdentify;
	this.host = host;
	this.port = port;
	this.user = user;
	this.passwd = passwd;
}
public String getProxyIdentify() {
	return proxyIdentify;
}
@XmlElement
public void setProxyIdentify(String proxyIdentify) {
	this.proxyIdentify = proxyIdentify;
}
public String getHost() {
	return host;
}
@XmlElement
public void setHost(String host) {
	this.host = host;
}
public String getPort() {
	return port;
}
@XmlElement
public void setPort(String port) {
	this.port = port;
}
public String getUser() {
	return user;
}
@XmlElement
public void setUser(String user) {
	this.user = user;
}
public String getPasswd() {
	return passwd;
}
@XmlElement
public void setPasswd(String passwd) {
	this.passwd = passwd;
}
}
