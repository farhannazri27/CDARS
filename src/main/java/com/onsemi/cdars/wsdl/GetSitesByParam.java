//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.23 at 03:30:02 PM SGT 
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
 *         &lt;element name="siteName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ldapHost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="searchBase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="webServicesHTTP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="replicatedWebServicesHTTP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remoteOnReplication" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isMainSite" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "siteName",
    "ldapHost",
    "searchBase",
    "webServicesHTTP",
    "replicatedWebServicesHTTP",
    "remoteOnReplication",
    "isMainSite"
})
@XmlRootElement(name = "GetSitesByParam")
public class GetSitesByParam {

    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer pkID;
    protected String siteName;
    protected String ldapHost;
    protected String searchBase;
    protected String webServicesHTTP;
    protected String replicatedWebServicesHTTP;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean remoteOnReplication;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean isMainSite;

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
     * Gets the value of the siteName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * Sets the value of the siteName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteName(String value) {
        this.siteName = value;
    }

    /**
     * Gets the value of the ldapHost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLdapHost() {
        return ldapHost;
    }

    /**
     * Sets the value of the ldapHost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLdapHost(String value) {
        this.ldapHost = value;
    }

    /**
     * Gets the value of the searchBase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchBase() {
        return searchBase;
    }

    /**
     * Sets the value of the searchBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchBase(String value) {
        this.searchBase = value;
    }

    /**
     * Gets the value of the webServicesHTTP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebServicesHTTP() {
        return webServicesHTTP;
    }

    /**
     * Sets the value of the webServicesHTTP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebServicesHTTP(String value) {
        this.webServicesHTTP = value;
    }

    /**
     * Gets the value of the replicatedWebServicesHTTP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReplicatedWebServicesHTTP() {
        return replicatedWebServicesHTTP;
    }

    /**
     * Sets the value of the replicatedWebServicesHTTP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReplicatedWebServicesHTTP(String value) {
        this.replicatedWebServicesHTTP = value;
    }

    /**
     * Gets the value of the remoteOnReplication property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRemoteOnReplication() {
        return remoteOnReplication;
    }

    /**
     * Sets the value of the remoteOnReplication property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRemoteOnReplication(Boolean value) {
        this.remoteOnReplication = value;
    }

    /**
     * Gets the value of the isMainSite property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsMainSite() {
        return isMainSite;
    }

    /**
     * Sets the value of the isMainSite property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsMainSite(Boolean value) {
        this.isMainSite = value;
    }

}
