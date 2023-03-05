
package com.ist.iagent.core.service.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getAllServicesResponse", namespace = "http://service.core.iagent.ist.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAllServicesResponse", namespace = "http://service.core.iagent.ist.com/")
public class GetAllServicesResponse {

    @XmlElement(name = "return", namespace = "")
    private List<com.ist.iagent.admin.db.pojo.IAgentService> _return;

    /**
     * 
     * @return
     *     returns List<IAgentService>
     */
    public List<com.ist.iagent.admin.db.pojo.IAgentService> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<com.ist.iagent.admin.db.pojo.IAgentService> _return) {
        this._return = _return;
    }

}
