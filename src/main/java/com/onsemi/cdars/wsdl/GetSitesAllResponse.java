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
 *         &lt;element name="GetSitesAllResult" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
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
    "getSitesAllResult"
})
@XmlRootElement(name = "GetSitesAllResponse")
public class GetSitesAllResponse {

    @XmlElement(name = "GetSitesAllResult")
    protected Object getSitesAllResult;

    /**
     * Gets the value of the getSitesAllResult property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getGetSitesAllResult() {
        return getSitesAllResult;
    }

    /**
     * Sets the value of the getSitesAllResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setGetSitesAllResult(Object value) {
        this.getSitesAllResult = value;
    }

}
