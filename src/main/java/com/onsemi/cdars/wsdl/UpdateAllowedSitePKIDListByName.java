//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.15 at 11:10:13 AM SGT 
//


package com.onsemi.cdars.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="sitesDS" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="acceptChanges" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "sitesDS",
    "acceptChanges"
})
@XmlRootElement(name = "UpdateAllowedSitePKIDListByName")
public class UpdateAllowedSitePKIDListByName {

    protected Object sitesDS;
    protected boolean acceptChanges;

    /**
     * Gets the value of the sitesDS property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getSitesDS() {
        return sitesDS;
    }

    /**
     * Sets the value of the sitesDS property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setSitesDS(Object value) {
        this.sitesDS = value;
    }

    /**
     * Gets the value of the acceptChanges property.
     * 
     */
    public boolean isAcceptChanges() {
        return acceptChanges;
    }

    /**
     * Sets the value of the acceptChanges property.
     * 
     */
    public void setAcceptChanges(boolean value) {
        this.acceptChanges = value;
    }

}
