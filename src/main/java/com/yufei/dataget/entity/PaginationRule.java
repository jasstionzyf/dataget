package com.yufei.dataget.entity;
/**
 * 
 */


import com.yufei.annotation.CollectionElementType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;



/**
 * @author zhaoyufei zhao-0244@qq.com
 *
 * created at 2012-8-18 下午11:57:30
 */
@XmlRootElement
public class PaginationRule {
	private String paginationTemplate=null;
	@CollectionElementType(type=UrlParameter.class)
	private List<UrlParameter> urlParameters=new ArrayList<UrlParameter>();
	
	public String getPaginationTemplate() {
		return paginationTemplate;
	}
	@XmlElement
	public void setPaginationTemplate(String paginationTemplate) {
		this.paginationTemplate = paginationTemplate;
	}


	@Override
	public String toString() {
	/*	StringBuffer str=new StringBuffer();
		str.append("[");
		if(urlParameters==null){
			return "";
		}
		for(UrlParameter urlParameter:urlParameters){
			str.append(urlParameter.toString()+",");
			
		}
		str.append("]");*/
		return "PaginationRule [paginationTemplate=" + paginationTemplate
				+ ", urlParameters=" + "" + "]";
	}
	public List<UrlParameter> getUrlParameters() {
		return urlParameters;
	}
	@XmlElementWrapper(name="parameters")
	@XmlElement(name="parameter")
	public void setUrlParameters(List<UrlParameter> urlParameters) {
		this.urlParameters = urlParameters;
		
	}

}
