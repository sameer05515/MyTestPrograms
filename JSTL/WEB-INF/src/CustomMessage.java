package com.prem.tags;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class CustomMessage extends BodyTagSupport{
	private static final long serialVersionUID=1L;
	private String pname;
	public void setParamName(String s){
		pname=s;
	}
	public String getParamName(){
	
		return pname;
	}
	public int doStartTag(){
	
		ServletRequest req=pageContext.getRequest();
		
		String pvalue=req.getParameter(pname);
		
		if(pvalue!=null && pvalue.toLowerCase().equals("japan")){
			return EVAL_BODY_INCLUDE;
		}else{
			return SKIP_BODY;		
		}	
	}
	
	public int doAfterBody(){return SKIP_BODY;}
	
	public int doEndTag(){return EVAL_PAGE;}


}