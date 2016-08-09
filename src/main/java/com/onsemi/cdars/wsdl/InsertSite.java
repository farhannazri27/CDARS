//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.09 at 06:19:39 PM SGT 
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
 *         &lt;element name="siteName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ldapHost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="searchBase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="webServicesHTTP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="replicatedWebServicesHTTP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remoteOnReplication" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isMainSite" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="allowedSitePKIDList" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "siteName",
    "ldapHost",
    "searchBase",
    "webServicesHTTP",
    "replicatedWebServicesHTTP",
    "remoteOnReplication",
    "isMainSite",
    "allowedSitePKIDList"
})
@XmlRootElement(name = "InsertSite")
public class InsertSite {

    protected String siteName;
    protected String ldapHost;
    protected String searchBase;
    protected String webServicesHTTP;
    protected String replicatedWebServicesHTTP;
    protected boolean remoteOnReplication;
    protected boolean isMainSite;
    protected String allowedSitePKIDList;

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
     */
    public boolean isRemoteOnReplication() {
        return remoteOnReplication;
    }

    /**
     * Sets the value of the remoteOnReplication property.
     * 
     */
    public void setRemoteOnReplication(boolean value) {
        this.remoteOnReplication = value;
    }

    /**
     * Gets the value of the isMainSite property.
     * 
     */
    public boolean isIsMainSite() {
        return isMainSite;
    }

    /**
     * Sets the value of the isMainSite property.
     * 
     */
    public void setIsMainSite(boolean value) {
        this.isMainSite = value;
    }

    /**
     * Gets the value of the allowedSitePKIDList property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllowedSitePKIDList() {
        return allowedSitePKIDList;
    }

    /**
     * Sets the value of the allowedSitePKIDList property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllowedSitePKIDList(String value) {
        this.allowedSitePKIDList = value;
    }

}
