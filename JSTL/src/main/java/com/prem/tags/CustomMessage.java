package com.prem.tags;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.jsp.tagext.BodyTagSupport;

public class CustomMessage extends BodyTagSupport {

    private static final long serialVersionUID = 1L;

    private String pname;

    public void setParamName(String paramName) {
        this.pname = paramName;
    }

    public String getParamName() {
        return pname;
    }

    @Override
    public int doStartTag() {
        ServletRequest request = pageContext.getRequest();
        String parameterValue = request.getParameter(pname);
        if (parameterValue != null && "japan".equalsIgnoreCase(parameterValue)) {
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }

    @Override
    public int doAfterBody() {
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}

