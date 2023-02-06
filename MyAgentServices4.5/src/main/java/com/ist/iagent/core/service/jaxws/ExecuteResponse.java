
package com.ist.iagent.core.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "executeResponse", namespace = "http://service.core.iagent.ist.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "executeResponse", namespace = "http://service.core.iagent.ist.com/")
public class ExecuteResponse {

    @XmlElement(name = "return", namespace = "")
    private com.ist.iagent.core.service.message.IAgentServiceResponse _return;

    /**
     * 
     * @return
     *     returns IAgentServiceResponse
     */
    public com.ist.iagent.core.service.message.IAgentServiceResponse getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(com.ist.iagent.core.service.message.IAgentServiceResponse _return) {
        this._return = _return;
    }

}
