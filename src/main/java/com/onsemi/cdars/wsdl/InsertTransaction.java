//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.09 at 06:19:39 PM SGT 
//


package com.onsemi.cdars.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="dateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="itemsPKID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="transType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="transQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="remarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "dateTime",
    "itemsPKID",
    "transType",
    "transQty",
    "remarks"
})
@XmlRootElement(name = "InsertTransaction")
public class InsertTransaction {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateTime;
    protected int itemsPKID;
    protected int transType;
    protected int transQty;
    protected String remarks;

    /**
     * Gets the value of the dateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateTime() {
        return dateTime;
    }

    /**
     * Sets the value of the dateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateTime(XMLGregorianCalendar value) {
        this.dateTime = value;
    }

    /**
     * Gets the value of the itemsPKID property.
     * 
     */
    public int getItemsPKID() {
        return itemsPKID;
    }

    /**
     * Sets the value of the itemsPKID property.
     * 
     */
    public void setItemsPKID(int value) {
        this.itemsPKID = value;
    }

    /**
     * Gets the value of the transType property.
     * 
     */
    public int getTransType() {
        return transType;
    }

    /**
     * Sets the value of the transType property.
     * 
     */
    public void setTransType(int value) {
        this.transType = value;
    }

    /**
     * Gets the value of the transQty property.
     * 
     */
    public int getTransQty() {
        return transQty;
    }

    /**
     * Sets the value of the transQty property.
     * 
     */
    public void setTransQty(int value) {
        this.transQty = value;
    }

    /**
     * Gets the value of the remarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets the value of the remarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarks(String value) {
        this.remarks = value;
    }

}
