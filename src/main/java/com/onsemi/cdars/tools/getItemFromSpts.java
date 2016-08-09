/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.cdars.tools;

import com.onsemi.cdars.config.WebServiceConfig;
import com.onsemi.cdars.dao.WsdlDao;
import javax.xml.datatype.XMLGregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author fg79cj
 */
public class getItemFromSpts {

    private static final Logger LOGGER = LoggerFactory.getLogger(getItemFromSpts.class);

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(WebServiceConfig.class);
        WsdlDao getItem = context.getBean(WsdlDao.class);

        Integer pkid = null;
        String itemID = null;
        String itemName = null;
        Integer onHandQty = null;
        Integer status = null;
        Integer minQty = null;
        Integer maxQty = null;
        String unit = null;
        String rack = null;
        String shelf = null;
        String model = null;
        String equipmentType = null;
        String stressType = null;
        Boolean isCritical = null;
        Boolean isConsumeable = null;
        String itemType = null;
        String cardType = null;
        Integer bibPKID = null;
        Integer bibCardPKID = null;
        String remarks = null;
        Boolean checkAlert = null;
        Integer itemStatus = null;
        Integer cdarsStatus = null;
        Integer expiryB4Day = null;
        XMLGregorianCalendar expiryStartDate = null;
        XMLGregorianCalendar expiryEndDate = null;
        Boolean excludeScrapped = null;

        Object getItemByParam = getItem.getItemByParam(pkid, itemID, itemName, onHandQty, status, minQty, maxQty, unit, rack, shelf, model, equipmentType, stressType, isCritical, isConsumeable, itemType, cardType, bibPKID, bibCardPKID, remarks, checkAlert, itemStatus, cdarsStatus, expiryB4Day, expiryStartDate, expiryEndDate, excludeScrapped);

//        LOGGER.info(String.format("The conversion rate from %s to %s is %s.", fromCurrency, toCurrency, conversionRate));

 LOGGER.info(getItemByParam.toString());
    }

}
