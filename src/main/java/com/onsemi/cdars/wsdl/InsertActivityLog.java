//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.23 at 02:38:22 PM SGT 
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
 *         &lt;element name="userID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="logModule" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="logAction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="logInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "userID",
    "logModule",
    "logAction",
    "logInfo"
})
@XmlRootElement(name = "InsertActivityLog")
public class InsertActivityLog {

    protected String userID;
    protected String logModule;
    protected String logAction;
    protected String logInfo;

    /**
     * Gets the value of the userID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the value of the userID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserID(String value) {
        this.userID = value;
    }

    /**
     * Gets the value of the logModule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogModule() {
        return logModule;
    }

    /**
     * Sets the value of the logModule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogModule(String value) {
        this.logModule = value;
    }

    /**
     * Gets the value of the logAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogAction() {
        return logAction;
    }

    /**
     * Sets the value of the logAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogAction(String value) {
        this.logAction = value;
    }

    /**
     * Gets the value of the logInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogInfo() {
        return logInfo;
    }

    /**
     * Sets the value of the logInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogInfo(String value) {
        this.logInfo = value;
    }

}
