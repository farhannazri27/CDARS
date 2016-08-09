/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.cdars.tools;

import com.onsemi.cdars.wsdl.GetItemByParam;
import com.onsemi.cdars.wsdl.GetItemByParamResponse;
import javax.xml.datatype.XMLGregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

/**
 *
 * @author fg79cj
 */
public class SptsClient extends WebServiceGatewaySupport {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SptsClient.class);
    
    public GetItemByParamResponse getItemByParamResponse(Integer pkid, String itemID, String itemName, Integer onHandQty,
            Integer status, Integer minQty, Integer maxQty, String unit, String rack, String shelf, String model,
            String equipmentType, String stressType, Boolean isCritical, Boolean isConsumeable, String itemType,
            String cardType, Integer bibPKID, Integer bibCardPKID, String remarks, Boolean checkAlert, Integer itemStatus,
            Integer cdarsStatus, Integer expiryB4Day, XMLGregorianCalendar expiryStartDate, XMLGregorianCalendar expiryEndDate, Boolean excludeScrapped){
        
        GetItemByParam item = new GetItemByParam();
        item.setPkID(pkid);
        item.setItemID(itemID);
        item.setItemName(itemName);
        item.setOnHandQty(onHandQty);
        item.setStatus(status);
        item.setMinQty(minQty);
        item.setMaxQty(maxQty);
        item.setUnit(unit);
        item.setRack(rack);
        item.setShelf(shelf);
        item.setModel(model);
        item.setEquipmentType(equipmentType);
        item.setStressType(stressType);
        item.setIsCritical(isCritical);
        item.setIsConsumeable(isConsumeable);
        item.setItemType(itemType);
        item.setCardType(cardType);
        item.setBibPKID(bibPKID);
        item.setBibCardPKID(bibCardPKID);
        item.setRemarks(remarks);
        item.setCheckAlert(checkAlert);
        item.setItemStatus(itemStatus);
        item.setCdarsStatus(cdarsStatus);
        item.setExpiryB4Day(expiryB4Day);
        item.setExpiryStartDate(expiryStartDate);
        item.setExpiryEndDate(expiryEndDate);
        item.setExcludeScrapped(excludeScrapped);
        
        LOGGER.info("Requesting forecast for " + itemName);
        
        GetItemByParamResponse response = (GetItemByParamResponse) getWebServiceTemplate()
				.marshalSendAndReceive(
						"http://mysed-rel-spv2/SPTSServices/SPTSServices.asmx",
						item,
						new SoapActionCallback("http://tempuri.org/GetItemByParam"));

		return response;
        
    }
    
    
}
