//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.15 at 11:10:13 AM SGT 
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
 *         &lt;element name="UpdateCDARSStatusResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "updateCDARSStatusResult"
})
@XmlRootElement(name = "UpdateCDARSStatusResponse")
public class UpdateCDARSStatusResponse {

    @XmlElement(name = "UpdateCDARSStatusResult")
    protected boolean updateCDARSStatusResult;

    /**
     * Gets the value of the updateCDARSStatusResult property.
     * 
     */
    public boolean isUpdateCDARSStatusResult() {
        return updateCDARSStatusResult;
    }

    /**
     * Sets the value of the updateCDARSStatusResult property.
     * 
     */
    public void setUpdateCDARSStatusResult(boolean value) {
        this.updateCDARSStatusResult = value;
    }

}
