//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.08 at 02:44:38 PM SGT 
//


package com.onsemi.cdars.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UpdateUserPasswordResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "updateUserPasswordResult"
})
@XmlRootElement(name = "UpdateUserPasswordResponse")
public class UpdateUserPasswordResponse {

    @XmlElement(name = "UpdateUserPasswordResult")
    protected boolean updateUserPasswordResult;

    /**
     * Gets the value of the updateUserPasswordResult property.
     * 
     */
    public boolean isUpdateUserPasswordResult() {
        return updateUserPasswordResult;
    }

    /**
     * Sets the value of the updateUserPasswordResult property.
     * 
     */
    public void setUpdateUserPasswordResult(boolean value) {
        this.updateUserPasswordResult = value;
    }

}
