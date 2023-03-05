
package com.ist.iagent.core.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;

@XmlRootElement(name = "setGreeting", namespace = "http://service.core.iagent.ist.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "setGreeting", namespace = "http://service.core.iagent.ist.com/")
public class SetGreeting {

    @XmlElement(name = "arg0", namespace = "")
    private ElementNSImpl arg0;

    /**
     * 
     * @return
     *     returns ElementNSImpl
     */
    public ElementNSImpl getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(ElementNSImpl arg0) {
        this.arg0 = arg0;
    }

}
