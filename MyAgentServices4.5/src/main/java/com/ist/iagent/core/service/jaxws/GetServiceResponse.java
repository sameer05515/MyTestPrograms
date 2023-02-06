
package com.ist.iagent.core.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getServiceResponse", namespace = "http://service.core.iagent.ist.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getServiceResponse", namespace = "http://service.core.iagent.ist.com/")
public class GetServiceResponse {

    @XmlElement(name = "return", namespace = "")
    private com.ist.iagent.admin.db.pojo.IAgentService _return;

    /**
     * 
     * @return
     *     returns IAgentService
     */
    public com.ist.iagent.admin.db.pojo.IAgentService getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(com.ist.iagent.admin.db.pojo.IAgentService _return) {
        this._return = _return;
    }

}
