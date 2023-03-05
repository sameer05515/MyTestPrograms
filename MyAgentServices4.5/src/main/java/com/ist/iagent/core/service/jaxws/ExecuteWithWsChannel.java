
package com.ist.iagent.core.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "executeWithWsChannel", namespace = "http://service.core.iagent.ist.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "executeWithWsChannel", namespace = "http://service.core.iagent.ist.com/")
public class ExecuteWithWsChannel {

    @XmlElement(name = "arg0", namespace = "")
    private com.ist.iagent.core.service.message.IAgentServiceRequest arg0;

    /**
     * 
     * @return
     *     returns IAgentServiceRequest
     */
    public com.ist.iagent.core.service.message.IAgentServiceRequest getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(com.ist.iagent.core.service.message.IAgentServiceRequest arg0) {
        this.arg0 = arg0;
    }

}
