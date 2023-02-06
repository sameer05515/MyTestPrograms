
package com.ist.iagent.core.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "gggResponse", namespace = "http://service.core.iagent.ist.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "gggResponse", namespace = "http://service.core.iagent.ist.com/")
public class GggResponse {

    @XmlElement(name = "return", namespace = "")
    private Object _return;

    /**
     * 
     * @return
     *     returns Object
     */
    public Object getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(Object _return) {
        this._return = _return;
    }

}
