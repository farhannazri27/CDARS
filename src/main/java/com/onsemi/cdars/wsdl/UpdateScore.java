//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.26 at 06:42:12 AM SGT 
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
 *         &lt;element name="pkID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scoreCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scoreName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scoreDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "version",
    "scoreCategory",
    "scoreName",
    "scoreDescription"
})
@XmlRootElement(name = "UpdateScore")
public class UpdateScore {

    protected int pkID;
    protected String version;
    protected String scoreCategory;
    protected String scoreName;
    protected String scoreDescription;

    /**
     * Gets the value of the pkID property.
     * 
     */
    public int getPkID() {
        return pkID;
    }

    /**
     * Sets the value of the pkID property.
     * 
     */
    public void setPkID(int value) {
        this.pkID = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the scoreCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScoreCategory() {
        return scoreCategory;
    }

    /**
     * Sets the value of the scoreCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScoreCategory(String value) {
        this.scoreCategory = value;
    }

    /**
     * Gets the value of the scoreName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScoreName() {
        return scoreName;
    }

    /**
     * Sets the value of the scoreName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScoreName(String value) {
        this.scoreName = value;
    }

    /**
     * Gets the value of the scoreDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScoreDescription() {
        return scoreDescription;
    }

    /**
     * Sets the value of the scoreDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScoreDescription(String value) {
        this.scoreDescription = value;
    }

}
