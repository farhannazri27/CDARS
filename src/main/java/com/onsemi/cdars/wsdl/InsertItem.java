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
 *         &lt;element name="itemID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="onHandQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="prodQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="repairQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="otherQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="quarantineQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="externalCleaningQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="externalRecleaningQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="internalCleaningQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="internalRecleaningQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="storageFactoryQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="minQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="maxQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="unit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unitCost" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="rack" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shelf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="model" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="equipmentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stressType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isCritical" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isConsumeable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="itemType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cardType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bibPKID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="bibCardPKID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="remarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="downtimeValue" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="downtimeUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="implementationCost" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="manpowerValue" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="manpowerUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="complexityScore" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="expirationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
    "itemID",
    "itemName",
    "onHandQty",
    "prodQty",
    "repairQty",
    "otherQty",
    "quarantineQty",
    "externalCleaningQty",
    "externalRecleaningQty",
    "internalCleaningQty",
    "internalRecleaningQty",
    "storageFactoryQty",
    "minQty",
    "maxQty",
    "unit",
    "unitCost",
    "rack",
    "shelf",
    "model",
    "equipmentType",
    "stressType",
    "isCritical",
    "isConsumeable",
    "itemType",
    "cardType",
    "bibPKID",
    "bibCardPKID",
    "remarks",
    "downtimeValue",
    "downtimeUnit",
    "implementationCost",
    "manpowerValue",
    "manpowerUnit",
    "complexityScore",
    "expirationDate"
})
@XmlRootElement(name = "InsertItem")
public class InsertItem {

    protected String itemID;
    protected String itemName;
    protected int onHandQty;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer prodQty;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer repairQty;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer otherQty;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer quarantineQty;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer externalCleaningQty;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer externalRecleaningQty;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer internalCleaningQty;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer internalRecleaningQty;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer storageFactoryQty;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer minQty;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer maxQty;
    protected String unit;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal unitCost;
    protected String rack;
    protected String shelf;
    protected String model;
    protected String equipmentType;
    protected String stressType;
    protected boolean isCritical;
    protected boolean isConsumeable;
    protected String itemType;
    protected String cardType;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer bibPKID;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer bibCardPKID;
    protected String remarks;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal downtimeValue;
    protected String downtimeUnit;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal implementationCost;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal manpowerValue;
    protected String manpowerUnit;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer complexityScore;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expirationDate;

    /**
     * Gets the value of the itemID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemID() {
        return itemID;
    }

    /**
     * Sets the value of the itemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemID(String value) {
        this.itemID = value;
    }

    /**
     * Gets the value of the itemName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the value of the itemName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemName(String value) {
        this.itemName = value;
    }

    /**
     * Gets the value of the onHandQty property.
     * 
     */
    public int getOnHandQty() {
        return onHandQty;
    }

    /**
     * Sets the value of the onHandQty property.
     * 
     */
    public void setOnHandQty(int value) {
        this.onHandQty = value;
    }

    /**
     * Gets the value of the prodQty property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProdQty() {
        return prodQty;
    }

    /**
     * Sets the value of the prodQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProdQty(Integer value) {
        this.prodQty = value;
    }

    /**
     * Gets the value of the repairQty property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRepairQty() {
        return repairQty;
    }

    /**
     * Sets the value of the repairQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRepairQty(Integer value) {
        this.repairQty = value;
    }

    /**
     * Gets the value of the otherQty property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOtherQty() {
        return otherQty;
    }

    /**
     * Sets the value of the otherQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOtherQty(Integer value) {
        this.otherQty = value;
    }

    /**
     * Gets the value of the quarantineQty property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getQuarantineQty() {
        return quarantineQty;
    }

    /**
     * Sets the value of the quarantineQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setQuarantineQty(Integer value) {
        this.quarantineQty = value;
    }

    /**
     * Gets the value of the externalCleaningQty property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getExternalCleaningQty() {
        return externalCleaningQty;
    }

    /**
     * Sets the value of the externalCleaningQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setExternalCleaningQty(Integer value) {
        this.externalCleaningQty = value;
    }

    /**
     * Gets the value of the externalRecleaningQty property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getExternalRecleaningQty() {
        return externalRecleaningQty;
    }

    /**
     * Sets the value of the externalRecleaningQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setExternalRecleaningQty(Integer value) {
        this.externalRecleaningQty = value;
    }

    /**
     * Gets the value of the internalCleaningQty property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInternalCleaningQty() {
        return internalCleaningQty;
    }

    /**
     * Sets the value of the internalCleaningQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInternalCleaningQty(Integer value) {
        this.internalCleaningQty = value;
    }

    /**
     * Gets the value of the internalRecleaningQty property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInternalRecleaningQty() {
        return internalRecleaningQty;
    }

    /**
     * Sets the value of the internalRecleaningQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInternalRecleaningQty(Integer value) {
        this.internalRecleaningQty = value;
    }

    /**
     * Gets the value of the storageFactoryQty property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStorageFactoryQty() {
        return storageFactoryQty;
    }

    /**
     * Sets the value of the storageFactoryQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStorageFactoryQty(Integer value) {
        this.storageFactoryQty = value;
    }

    /**
     * Gets the value of the minQty property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinQty() {
        return minQty;
    }

    /**
     * Sets the value of the minQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinQty(Integer value) {
        this.minQty = value;
    }

    /**
     * Gets the value of the maxQty property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxQty() {
        return maxQty;
    }

    /**
     * Sets the value of the maxQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxQty(Integer value) {
        this.maxQty = value;
    }

    /**
     * Gets the value of the unit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnit(String value) {
        this.unit = value;
    }

    /**
     * Gets the value of the unitCost property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUnitCost() {
        return unitCost;
    }

    /**
     * Sets the value of the unitCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUnitCost(BigDecimal value) {
        this.unitCost = value;
    }

    /**
     * Gets the value of the rack property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRack() {
        return rack;
    }

    /**
     * Sets the value of the rack property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRack(String value) {
        this.rack = value;
    }

    /**
     * Gets the value of the shelf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShelf() {
        return shelf;
    }

    /**
     * Sets the value of the shelf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShelf(String value) {
        this.shelf = value;
    }

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Gets the value of the equipmentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEquipmentType() {
        return equipmentType;
    }

    /**
     * Sets the value of the equipmentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEquipmentType(String value) {
        this.equipmentType = value;
    }

    /**
     * Gets the value of the stressType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStressType() {
        return stressType;
    }

    /**
     * Sets the value of the stressType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStressType(String value) {
        this.stressType = value;
    }

    /**
     * Gets the value of the isCritical property.
     * 
     */
    public boolean isIsCritical() {
        return isCritical;
    }

    /**
     * Sets the value of the isCritical property.
     * 
     */
    public void setIsCritical(boolean value) {
        this.isCritical = value;
    }

    /**
     * Gets the value of the isConsumeable property.
     * 
     */
    public boolean isIsConsumeable() {
        return isConsumeable;
    }

    /**
     * Sets the value of the isConsumeable property.
     * 
     */
    public void setIsConsumeable(boolean value) {
        this.isConsumeable = value;
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
     * Gets the value of the cardType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * Sets the value of the cardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardType(String value) {
        this.cardType = value;
    }

    /**
     * Gets the value of the bibPKID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBibPKID() {
        return bibPKID;
    }

    /**
     * Sets the value of the bibPKID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBibPKID(Integer value) {
        this.bibPKID = value;
    }

    /**
     * Gets the value of the bibCardPKID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBibCardPKID() {
        return bibCardPKID;
    }

    /**
     * Sets the value of the bibCardPKID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBibCardPKID(Integer value) {
        this.bibCardPKID = value;
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

    /**
     * Gets the value of the downtimeValue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDowntimeValue() {
        return downtimeValue;
    }

    /**
     * Sets the value of the downtimeValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDowntimeValue(BigDecimal value) {
        this.downtimeValue = value;
    }

    /**
     * Gets the value of the downtimeUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDowntimeUnit() {
        return downtimeUnit;
    }

    /**
     * Sets the value of the downtimeUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDowntimeUnit(String value) {
        this.downtimeUnit = value;
    }

    /**
     * Gets the value of the implementationCost property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImplementationCost() {
        return implementationCost;
    }

    /**
     * Sets the value of the implementationCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImplementationCost(BigDecimal value) {
        this.implementationCost = value;
    }

    /**
     * Gets the value of the manpowerValue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getManpowerValue() {
        return manpowerValue;
    }

    /**
     * Sets the value of the manpowerValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setManpowerValue(BigDecimal value) {
        this.manpowerValue = value;
    }

    /**
     * Gets the value of the manpowerUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManpowerUnit() {
        return manpowerUnit;
    }

    /**
     * Sets the value of the manpowerUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManpowerUnit(String value) {
        this.manpowerUnit = value;
    }

    /**
     * Gets the value of the complexityScore property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getComplexityScore() {
        return complexityScore;
    }

    /**
     * Sets the value of the complexityScore property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setComplexityScore(Integer value) {
        this.complexityScore = value;
    }

    /**
     * Gets the value of the expirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpirationDate(XMLGregorianCalendar value) {
        this.expirationDate = value;
    }

}
