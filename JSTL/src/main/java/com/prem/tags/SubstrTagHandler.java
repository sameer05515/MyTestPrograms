package com.prem.tags;

import java.io.IOException;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

public class SubstrTagHandler extends TagSupport {

    private String input;
    private int start;
    private int end;

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.println(input.substring(start, end));
        } catch (IOException ex) {
            throw new JspException("Failed to render substring", ex);
        }
        return SKIP_BODY;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}

