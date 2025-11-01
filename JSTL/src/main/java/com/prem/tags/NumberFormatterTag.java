package com.prem.tags;

import java.io.IOException;
import java.text.DecimalFormat;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.SkipPageException;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

public class NumberFormatterTag extends SimpleTagSupport {

    private String format;
    private String number;

    public void setFormat(String format) {
        this.format = format;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            double amount = Double.parseDouble(number);
            DecimalFormat formatter = new DecimalFormat(format);
            String formattedNumber = formatter.format(amount);
            getJspContext().getOut().write(formattedNumber);
        } catch (Exception ex) {
            throw new SkipPageException("Exception in formatting " + number + " with format " + format, ex);
        }
    }
}

