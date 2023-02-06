
package com.ist.iagent.core.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "main", namespace = "http://service.core.iagent.ist.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "main", namespace = "http://service.core.iagent.ist.com/")
public class Main {

    @XmlElement(name = "arg0", namespace = "", nillable = true)
    private String[] arg0;

    /**
     * 
     * @return
     *     returns String[]
     */
    public String[] getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(String[] arg0) {
        this.arg0 = arg0;
    }

}
