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
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sitePKID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="siteName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localWebServiceURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="centralWebServiceURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="planFilePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mailboxPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="smtpMailServer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mailAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mailAccountLogin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mailAccountPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="domainName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="portNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="lowQtyIntervalMins" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="emailStartTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="alertLowQtyEmailList" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expiryNotifyStartTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expiryIntervalMins" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "sitePKID",
    "siteName",
    "localWebServiceURL",
    "centralWebServiceURL",
    "planFilePath",
    "mailboxPath",
    "smtpMailServer",
    "mailAccount",
    "mailAccountLogin",
    "mailAccountPassword",
    "domainName",
    "displayName",
    "portNumber",
    "lowQtyIntervalMins",
    "emailStartTime",
    "alertLowQtyEmailList",
    "expiryNotifyStartTime",
    "expiryIntervalMins"
})
@XmlRootElement(name = "UpdateSystemSetting")
public class UpdateSystemSetting {

    protected int pkID;
    protected String version;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer sitePKID;
    protected String siteName;
    protected String localWebServiceURL;
    protected String centralWebServiceURL;
    protected String planFilePath;
    protected String mailboxPath;
    protected String smtpMailServer;
    protected String mailAccount;
    protected String mailAccountLogin;
    protected String mailAccountPassword;
    protected String domainName;
    protected String displayName;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer portNumber;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer lowQtyIntervalMins;
    protected String emailStartTime;
    protected String alertLowQtyEmailList;
    protected String expiryNotifyStartTime;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer expiryIntervalMins;

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
     * Gets the value of the sitePKID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSitePKID() {
        return sitePKID;
    }

    /**
     * Sets the value of the sitePKID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSitePKID(Integer value) {
        this.sitePKID = value;
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
     * Gets the value of the localWebServiceURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalWebServiceURL() {
        return localWebServiceURL;
    }

    /**
     * Sets the value of the localWebServiceURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalWebServiceURL(String value) {
        this.localWebServiceURL = value;
    }

    /**
     * Gets the value of the centralWebServiceURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCentralWebServiceURL() {
        return centralWebServiceURL;
    }

    /**
     * Sets the value of the centralWebServiceURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCentralWebServiceURL(String value) {
        this.centralWebServiceURL = value;
    }

    /**
     * Gets the value of the planFilePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanFilePath() {
        return planFilePath;
    }

    /**
     * Sets the value of the planFilePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanFilePath(String value) {
        this.planFilePath = value;
    }

    /**
     * Gets the value of the mailboxPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailboxPath() {
        return mailboxPath;
    }

    /**
     * Sets the value of the mailboxPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailboxPath(String value) {
        this.mailboxPath = value;
    }

    /**
     * Gets the value of the smtpMailServer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmtpMailServer() {
        return smtpMailServer;
    }

    /**
     * Sets the value of the smtpMailServer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmtpMailServer(String value) {
        this.smtpMailServer = value;
    }

    /**
     * Gets the value of the mailAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailAccount() {
        return mailAccount;
    }

    /**
     * Sets the value of the mailAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailAccount(String value) {
        this.mailAccount = value;
    }

    /**
     * Gets the value of the mailAccountLogin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailAccountLogin() {
        return mailAccountLogin;
    }

    /**
     * Sets the value of the mailAccountLogin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailAccountLogin(String value) {
        this.mailAccountLogin = value;
    }

    /**
     * Gets the value of the mailAccountPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailAccountPassword() {
        return mailAccountPassword;
    }

    /**
     * Sets the value of the mailAccountPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailAccountPassword(String value) {
        this.mailAccountPassword = value;
    }

    /**
     * Gets the value of the domainName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     * Sets the value of the domainName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomainName(String value) {
        this.domainName = value;
    }

    /**
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

    /**
     * Gets the value of the portNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPortNumber() {
        return portNumber;
    }

    /**
     * Sets the value of the portNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPortNumber(Integer value) {
        this.portNumber = value;
    }

    /**
     * Gets the value of the lowQtyIntervalMins property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLowQtyIntervalMins() {
        return lowQtyIntervalMins;
    }

    /**
     * Sets the value of the lowQtyIntervalMins property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLowQtyIntervalMins(Integer value) {
        this.lowQtyIntervalMins = value;
    }

    /**
     * Gets the value of the emailStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailStartTime() {
        return emailStartTime;
    }

    /**
     * Sets the value of the emailStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailStartTime(String value) {
        this.emailStartTime = value;
    }

    /**
     * Gets the value of the alertLowQtyEmailList property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertLowQtyEmailList() {
        return alertLowQtyEmailList;
    }

    /**
     * Sets the value of the alertLowQtyEmailList property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertLowQtyEmailList(String value) {
        this.alertLowQtyEmailList = value;
    }

    /**
     * Gets the value of the expiryNotifyStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpiryNotifyStartTime() {
        return expiryNotifyStartTime;
    }

    /**
     * Sets the value of the expiryNotifyStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpiryNotifyStartTime(String value) {
        this.expiryNotifyStartTime = value;
    }

    /**
     * Gets the value of the expiryIntervalMins property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getExpiryIntervalMins() {
        return expiryIntervalMins;
    }

    /**
     * Sets the value of the expiryIntervalMins property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setExpiryIntervalMins(Integer value) {
        this.expiryIntervalMins = value;
    }

}
