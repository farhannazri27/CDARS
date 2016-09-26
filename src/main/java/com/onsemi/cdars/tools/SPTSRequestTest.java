package com.onsemi.cdars.tools;

import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;

public class SPTSRequestTest {

    public static void main(String[] args) throws IOException {
        //GetItemAll
//        System.out.println("GET ITEM ALL...");
//        JSONArray getItemAll = SPTSWebService.getItemAll();
//        for (int i = 0; i < getItemAll.length(); i++) {
//            System.out.println(getItemAll.getJSONObject(i));
//        }

        //GetItemByParam
        /*
         -- KEYS --
         pkID,//int
         itemID,//string
         itemName,//string
         onHandQty,//int
         status,//int
         minQty,//int
         maxQty,//int
         unit,//string
         rack,//string
         shelf,//string
         model,//string
         equipmentType,//string
         stressType,//string
         isCritical,//boolean
         isConsumeable,//boolean
         itemType,//string
         cardType,//string
         bibPKID,//int
         bibCardPKID,//int
         remarks,//string
         checkAlert,//boolean
         itemStatus,//int
         cdarsStatus,//int
         expiryB4Day,//int
         expiryStartDate,//dateTime
         expiryEndDate,//dateTime
         excludeScrapped//boolean
         */
        System.out.println("GET ITEM BY PARAM...");
        JSONObject params0 = new JSONObject();
        params0.put("itemType", "PCB%");
        params0.put("itemStatus", "0");
        params0.put("status", "1");
        params0.put("itemID", "%QUAL A");
//        params0.put("itemID", "AHWH3-4");
        JSONArray getItemByParam = SPTSWebService.getItemByParam(params0);
        for (int i = 0; i < getItemByParam.length(); i++) {
            System.out.println(getItemByParam.getJSONObject(i));
        }
        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
        //InsertItem
        /*
         -- KEYS --
         itemID
         itemName
         onHandQty
         prodQty
         repairQty
         otherQty
         quarantineQty
         externalCleaningQty
         externalRecleaningQty
         internalCleaningQty
         internalRecleaningQty
         storageFactoryQty
         minQty
         maxQty
         unit
         unitCost
         rack
         shelf
         model
         equipmentType
         stressType
         isCritical
         isConsumeable
         itemType
         cardType
         bibPKID
         bibCardPKID
         remarks
         downtimeValue
         downtimeUnit
         implementationCost
         manpowerValue
         manpowerUnit
         complexityScore
         expirationDate
         */
 /*
         -- SAMPLE DATA --
         "ItemStatus": 0,
         "TotalQty": 1,
         "SiteItemType": "Seremban-BIB",
         "msdata:rowOrder": 0,
         "SiteName": "Seremban",
         "SiteRack": "Seremban-AC15",
         "ItemType": "BIB",
         "Shelf": 18,
         "StressType": "HTFB",
         "ProductionQty": 0,
         "Unit": "pcs",
         "IsNew": false,
         "EquipmentType": "Motherboard  (NO ASSY#)",
         "IsConsumeable": false,
         "TotalCost": "4000.00",
         "Rack": "AC15",
         "SiteStressType": "Seremban-HTFB",
         "Version": "afbf98aa-9a84-4a0b-8320-05dc77fca6a2",
         "Remarks": "PM:WW24,WW50 - BOARD IN GOOD CONDITION",
         "SiteItemID": "Seremban-AHWFB-1",
         "SiteEquipmentType": "Seremban-Motherboard  (NO ASSY#)",
         "ItemName": "M_AHWFB-ACBH-48B22-00L00-0P00H31000T150-001",
         "ComplexityScore": 0,
         "Status": 0,
         "MaxQty": 1,
         "LowQty": 1,
         "diffgr:id": "ITEMS1",
         "CDARSStatus": 0,
         "StatusName": "Good",
         "SiteModel": "Seremban-ACS 22way 0.156in Connector (No resistor )",
         "SitePKID": 1,
         "OnHandQty": 1,
         "ItemID": "AHWFB-1",
         "IsCritical": false,
         "UnitCost": "4000.00",
         "SiteItemName": "Seremban-M_AHWFB-ACBH-48B22-00L00-0P00H31000T150-001",
         "PKID": 3896,
         "Model": "ACS 22way 0.156in Connector (No resistor )",
         "SiteShelf": "Seremban-18"
         */
//        System.out.println("INSERT ITEM...");
//        String itemID = "AHWFB-18";
//        JSONObject params1 = new JSONObject();
//        params1.put("itemID", itemID);
//        params1.put("itemName", "M_AHWFB-ACBH-48B22-00L00-0P00H31000T150-001");
//        params1.put("onHandQty", "1");
//        params1.put("prodQty", "0");
//        params1.put("repairQty", "0");
//        params1.put("otherQty", "0");
//        params1.put("quarantineQty", "0");
//        params1.put("externalCleaningQty", "0");
//        params1.put("externalRecleaningQty", "0");
//        params1.put("internalCleaningQty", "0");
//        params1.put("internalRecleaningQty", "0");
//        params1.put("storageFactoryQty", "0");
//        params1.put("minQty", "1");
//        params1.put("maxQty", "1");
//        params1.put("unit", "pcs");
//        params1.put("unitCost", "4000.00");
//        params1.put("rack", "AC15");
//        params1.put("shelf", "18");
//        params1.put("model", "ACS 22way 0.156in Connector (No resistor )");
//        params1.put("equipmentType", "Motherboard  (NO ASSY#)");
//        params1.put("stressType", "HTFB");
//        params1.put("isCritical", "false");
//        params1.put("isConsumeable", "false");
//        params1.put("itemType", "BIB");
//        params1.put("cardType", "N/A");
//        params1.put("bibPKID", "1");
//        params1.put("bibCardPKID", "1");
//        params1.put("remarks", "Test");
//        params1.put("downtimeValue", "1");
//        params1.put("downtimeUnit", "N/A");
//        params1.put("implementationCost", "200.00");
//        params1.put("manpowerValue", "1000.00");
//        params1.put("manpowerUnit", "N/A");
//        params1.put("complexityScore", "0");
//        params1.put("expirationDate", "2017-08-30");
//        Integer pkID = SPTSWebService.insertItem(params1);
//        System.out.println("pkID: " + pkID);
//        if (pkID != 0) {
//            System.out.println("GET ITEM BY PKID...");
//            JSONObject jsonObject1 = SPTSWebService.getItemByPKID(pkID.toString());
////        JSONObject jsonObject1 = SPTSWebService.getItemByPKID("6085");
////
//            System.out.println(jsonObject1.toString());
//
//            String version1 = jsonObject1.getString("Version");
//            System.out.println("Version 1: " + version1);
//
//            System.out.println("UPDATE ITEM...");
//            JSONObject params2 = new JSONObject();
//            params2.put("pkID", pkID.toString());
//////        params2.put("pkID", "6085");
//            params2.put("version", version1);
////        params2.put("itemID", itemID);
//            params2.put("itemID", "AHWFB-18");
//            params2.put("itemName", "M_AHWFB-ACBH-48B22-00L00-0P00H31000T150-001");
////            params2.put("itemName", "Test");
//            params2.put("onHandQty", "1");
//            params2.put("prodQty", "0");
//            params2.put("repairQty", "0");
//            params2.put("otherQty", "0");
//            params2.put("quarantineQty", "0");
//            params2.put("externalCleaningQty", "0");
//            params2.put("externalRecleaningQty", "0");
//            params2.put("internalCleaningQty", "0");
//            params2.put("internalRecleaningQty", "0");
//            params2.put("storageFactoryQty", "0");
//            params2.put("minQty", "1");
//            params2.put("maxQty", "1");
//            params2.put("unit", "pcs");
//            params2.put("unitCost", "4000.00");
//            params2.put("rack", "AC15");
//            params2.put("shelf", "18");
//            params2.put("model", "ACS 22way 0.156in Connector (No resistor )");
//            params2.put("equipmentType", "Motherboard  (NO ASSY#)");
//            params2.put("stressType", "HTFB");
//            params2.put("isCritical", "false");
//            params2.put("isConsumeable", "false");
//            params2.put("itemType", "BIB");
//            params2.put("cardType", "N/A");
//            params2.put("bibPKID", "1");
//            params2.put("bibCardPKID", "1");
//            params2.put("remarks", "Test");
//            params2.put("itemStatus", "0");
//            params2.put("cdarsStatus", "0");
//            params2.put("downtimeValue", "1");
//            params2.put("downtimeUnit", "N/A");
//            params2.put("implementationCost", "200.00");
//            params2.put("manpowerValue", "1000.00");
//            params2.put("manpowerUnit", "N/A");
//            params2.put("complexityScore", "0");
//            params2.put("expirationDate", "2017-08-30");
//
//            String version2 = "";
//
//            Boolean update = SPTSWebService.updateItem(params2);
//            if (update) {
//                JSONObject jsonObject2 = SPTSWebService.getItemByPKID(pkID.toString());
////            JSONObject jsonObject2 = SPTSWebService.getItemByPKID("6085");
//                System.out.println(jsonObject2.toString());
//                version2 = jsonObject2.getString("Version");
//                System.out.println("Version 1: " + version1);
//            } else {
//                System.out.println("Update Failed: " + pkID.toString());
////                System.out.println("Update Failed: " + "6085");
//            }
//
//            System.out.println("DELETE ITEM...");
//            JSONObject params3 = new JSONObject();
//            params3.put("pkID", pkID.toString());
////            params3.put("pkID", "6085");
//            params3.put("version", version2);
//            params3.put("forceDelete", "false");
//
//            Boolean delete = SPTSWebService.deleteItem(params3);
//            if (delete) {
//                System.out.println("Delete Success: " + pkID.toString());
////                System.out.println("Delete Success: " + "6085");
//            } else {
//                System.out.println("Delete Failed: " + pkID.toString());
////                System.out.println("Delete Failed: " + "6085");
//            }
//        }
    }
}
