package com.yufei.dataget.entity;
/**
 * 
 */



/**
 * @author zhaoyufei zhao-0244@qq.com
 *
 * created at 2012-8-19 上午12:26:50
 */
public class UrlParameter  {
	
	 /**
	 * @param parameterIndex
	 * @param parameterType
	 * @param parameterValue
	 * 对于字符串类型的三个参数已经完全满足程序处理的需要
	 */
	public UrlParameter(Integer parameterIndex, String parameterType,
			String parameterValue) {
		super();
		this.parameterIndex = parameterIndex;
		this.parameterType = parameterType;
		this.parameterValue = parameterValue;
	}
	public UrlParameter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getParameterIndex() {
		return parameterIndex;
	}
	public void setParameterIndex(Integer parameterIndex) {
		this.parameterIndex = parameterIndex;
	}
	public String getParameterType() {
		return parameterType;
	}
	@Override
	public String toString() {
		return "UrlParameter [parameterIndex=" + parameterIndex
				+ ", parameterType=" + parameterType + ", parameterValue="
				+ parameterValue + "]";
	}
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	private Integer parameterIndex=1;
	 //String or Integer(分页数目设置)
     private String parameterType=null;
     //如果是集合的话用,隔开
  
     private String parameterValue=null;
     //分页参数的起始值
     private Integer  begainPagNumber;
     //分页参数之间的间距即跳跃大小
     private Integer pagNumberSpace;
	/**
	 * @param parameterIndex
	 * @param parameterType
	 * @param parameterValue:pageNumber
	 * @param begainPagNumber
	 * @param pagNumberSpace
	 * 针对分页参数之间递增的维度不是1的情况，如果是null的话默认起始值是1，递增维度是1
	 */
	public UrlParameter(Integer parameterIndex, String parameterType,
			String parameterValue, Integer begainPagNumber,
			Integer pagNumberSpace) {
		super();
		this.parameterIndex = parameterIndex;
		this.parameterType = parameterType;
		this.parameterValue = parameterValue;
		this.begainPagNumber = begainPagNumber;
		this.pagNumberSpace = pagNumberSpace;
	}
	public Integer getBegainPagNumber() {
		return begainPagNumber;
	}
	public void setBegainPagNumber(Integer begainPagNumber) {
		this.begainPagNumber = begainPagNumber;
	}
	public Integer getPagNumberSpace() {
		return pagNumberSpace;
	}
	public void setPagNumberSpace(Integer pagNumberSpace) {
		this.pagNumberSpace = pagNumberSpace;
	}
}
