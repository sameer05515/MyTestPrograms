
package com.ist.iagent.core.service.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getWSServicesResponse", namespace = "http://service.core.iagent.ist.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getWSServicesResponse", namespace = "http://service.core.iagent.ist.com/")
public class GetWSServicesResponse {

    @XmlElement(name = "return", namespace = "")
    private List<com.ist.iagent.admin.db.pojo.WSService> _return;

    /**
     * 
     * @return
     *     returns List<WSService>
     */
    public List<com.ist.iagent.admin.db.pojo.WSService> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<com.ist.iagent.admin.db.pojo.WSService> _return) {
        this._return = _return;
    }

}
