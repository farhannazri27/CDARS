//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.08 at 02:44:38 PM SGT 
//


package com.onsemi.cdars.wsdl;

import java.math.BigDecimal;
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
 *         &lt;element name="scorePKID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="scoreCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scoreName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scoreDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="minLimit" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="maxLimit" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="scoreValue" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="unitMeasure" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "scorePKID",
    "scoreCategory",
    "scoreName",
    "scoreDescription",
    "description",
    "minLimit",
    "maxLimit",
    "scoreValue",
    "unitMeasure"
})
@XmlRootElement(name = "GetScoreDetailsByParam")
public class GetScoreDetailsByParam {

    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer pkID;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer scorePKID;
    protected String scoreCategory;
    protected String scoreName;
    protected String scoreDescription;
    protected String description;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal minLimit;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal maxLimit;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer scoreValue;
    protected String unitMeasure;

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
     * Gets the value of the scorePKID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getScorePKID() {
        return scorePKID;
    }

    /**
     * Sets the value of the scorePKID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setScorePKID(Integer value) {
        this.scorePKID = value;
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

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the minLimit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMinLimit() {
        return minLimit;
    }

    /**
     * Sets the value of the minLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMinLimit(BigDecimal value) {
        this.minLimit = value;
    }

    /**
     * Gets the value of the maxLimit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaxLimit() {
        return maxLimit;
    }

    /**
     * Sets the value of the maxLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaxLimit(BigDecimal value) {
        this.maxLimit = value;
    }

    /**
     * Gets the value of the scoreValue property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getScoreValue() {
        return scoreValue;
    }

    /**
     * Sets the value of the scoreValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setScoreValue(Integer value) {
        this.scoreValue = value;
    }

    /**
     * Gets the value of the unitMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitMeasure() {
        return unitMeasure;
    }

    /**
     * Sets the value of the unitMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitMeasure(String value) {
        this.unitMeasure = value;
    }

}
