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
 *         &lt;element name="pkID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="itemType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="emailB4Day" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "pkID",
    "itemType",
    "emailB4Day"
})
@XmlRootElement(name = "GetExpiryNoticeByParam")
public class GetExpiryNoticeByParam {

    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer pkID;
    protected String itemType;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer emailB4Day;

    /**
     * Gets the value of the pkID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPkID() {
        return pkID;
    }

    /**
     * Sets the value of the pkID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPkID(Integer value) {
        this.pkID = value;
    }

    /**
     * Gets the value of the itemType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * Sets the value of the itemType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemType(String value) {
        this.itemType = value;
    }

    /**
     * Gets the value of the emailB4Day property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEmailB4Day() {
        return emailB4Day;
    }

    /**
     * Sets the value of the emailB4Day property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEmailB4Day(Integer value) {
        this.emailB4Day = value;
    }

}