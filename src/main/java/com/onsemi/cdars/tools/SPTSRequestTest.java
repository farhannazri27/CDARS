package com.onsemi.cdars.tools;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
//        System.out.println("GET ITEM BY PARAM...");
//        JSONObject params0 = new JSONObject();
////        params0.put("itemType", "PCB%");
////        params0.put("itemStatus", "0");
////        params0.put("status", "1");
////        params0.put("itemID", "%QUAL A");
//        params0.put("itemID", "CDARS-MB_1");
//        params0.put("version", "07f86952-7c90-4bb8-90ba-696ca589851d");
//
//        JSONArray getItemByParam = SPTSWebService.getItemByParam(params0);
//        for (int i = 0; i < getItemByParam.length(); i++) {
//            System.out.println(getItemByParam.getJSONObject(i));
//        }
//        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
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
////        String itemID = "AHWFB-18";
//        String itemID = "19999A_CDARS-pcbTesting - QUAL C";
//        JSONObject params1 = new JSONObject();
//        params1.put("itemID", itemID);
//        params1.put("itemName", "CDARS-pcbTesting - QUAL C");
//        params1.put("onHandQty", "50");
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
//        params1.put("maxQty", "100");
//        params1.put("unit", "pcs");
//        params1.put("unitCost", "4000.00");
//        params1.put("rack", "PCB");
//        params1.put("shelf", "1");
//        params1.put("isCritical", "false");
//        params1.put("isConsumeable", "false");
//        params1.put("itemType", "PCB (PRODUCTION)");
//        params1.put("remarks", "Test CDARS");
//        params1.put("downtimeValue", "1");
//        params1.put("downtimeUnit", "N/A");
//        params1.put("implementationCost", "200.00");
//        params1.put("manpowerValue", "1000.00");
//        params1.put("manpowerUnit", "N/A");
//        params1.put("complexityScore", "0");
//        params1.put("expirationDate", "2017-08-30");
//        SPTSResponse pkID = SPTSWebService.insertItem(params1);
//
//        System.out.println("status: " + pkID.getStatus());
//        System.out.println("pkID: " + pkID.getResponseId());
//        System.out.println("result: " + pkID.getResponseCode());
////      
//        
// public DataSet GetSFItemByParam(int? pkID, int? itemPKID, string itemID, string itemName, int? transactionPKID, int? sfItemStatus, string sfRack, string sfShelf, int? onHandQty, int? status, int? minQty, int? maxQty, string unit, string rack, string shelf, string model, string equipmentType, string stressType, bool? isCritical, bool? isConsumeable, string itemType, string cardType, int? bibPKID, int? bibCardPKID, string remarks, bool? checkAlert, int? itemStatus, int? cdarsStatus, int? expiryB4Day, DateTime? expiryStartDate, DateTime? expiryEndDate, bool? excludeScrapped)
//         JSONObject params0 = new JSONObject();
////        params0.put("itemPKID", "7419");
//        params0.put("itemID", "19999A_CDARS-pcbTesting - CONTROL");
////        params0.put("transactionPKID", "70192");
//        JSONArray getItemByParam = SPTSWebService.getSFItemByParam(params0);
//        for (int i = 0; i < getItemByParam.length(); i++) {
//            System.out.println(getItemByParam.getJSONObject(i));
//        }
//        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
//            System.out.println("pkid..." + getItemByParam.getJSONObject(0).getInt("PKID"));
//                System.out.println("version..." + getItemByParam.getJSONObject(0).getString("Version"));

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
//                JSONObject jsonObject2 = SPTSWebService.getItemByPKID("7694");
////            JSONObject jsonObject2 = SPTSWebService.getItemByPKID("6085");
//                System.out.println(jsonObject2.toString());
//                version2 = jsonObject2.getString("Version");
//                System.out.println("Version 1: " + version1);
//            } else {
//                System.out.println("Update Failed: " + pkID.toString());
////                System.out.println("Update Failed: " + "6085");
//            }
//
//        JSONObject jsonObject2 = SPTSWebService.getItemByPKID("7687");
//                System.out.println(jsonObject2.toString());
//                String version2 = jsonObject2.getString("Version");
//                System.out.println("Version 2: " + version2);
//                 public int InsertTransaction(DateTime dateTime, int itemsPKID, int transType, int transQty, string remarks);
//        JSONObject params2 = new JSONObject();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        //get current date time with Date()
//        Date date = new Date();
//        System.out.println(dateFormat.format(date));
//
//        String formattedDate = dateFormat.format(date);
//
//        params2.put("dateTime", formattedDate);
//        params2.put("itemsPKID", "7688");
//        params2.put("transType", "20");
//        params2.put("transQty", "1");
//        params2.put("remarks", "test from cdars - return");
//        SPTSResponse TransPkid = SPTSWebService.insertTransaction(params2);
//        System.out.println("update: " + TransPkid.getResponseId());
//         public int InsertSFItem(int itemPKID, int transactionPKID, int sfItemStatus, string sfRack, string sfShelf)
//        JSONObject params3 = new JSONObject();
//
//        params3.put("itemPKID", "7687");
//        params3.put("transactionPKID", "70566");
//        params3.put("sfItemStatus", "0");
//        params3.put("sfRack", "");
//        params3.put("sfShelf", "");
//        SPTSResponse sfPkid = SPTSWebService.insertSFItem(params3);
//        
//        System.out.println("update: " + sfPkid.getResponseId());
//        public DataSet GetSFItemByParam(int? pkID, int? itemPKID, string itemID, string itemName, int? transactionPKID, int? sfItemStatus, string sfRack, string sfShelf, int? onHandQty, int? status, int? minQty, int? maxQty, string unit, string rack, string shelf, string model, string equipmentType, string stressType, bool? isCritical, bool? isConsumeable, string itemType, string cardType, int? bibPKID, int? bibCardPKID, string remarks, bool? checkAlert, int? itemStatus, int? cdarsStatus, int? expiryB4Day, DateTime? expiryStartDate, DateTime? expiryEndDate, bool? excludeScrapped)
//         JSONObject params0 = new JSONObject();
//        params0.put("itemPKID", "7687");
////        params0.put("pkID", "2");
////        params0.put("transactionPKID", "70192");
//        JSONArray getItemByParam = SPTSWebService.getSFItemByParam(params0);
//        for (int i = 0; i < getItemByParam.length(); i++) {
//            System.out.println(getItemByParam.getJSONObject(i));
//        }
//        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
// public bool UpdateSFItemLocation(int pkID, string version, int sfItemStatus, string sfRack, string sfShelf)
//        JSONObject params3 = new JSONObject();
////
//        params3.put("pkID", "2");
//        params3.put("version", "cdf17cf8-adc8-428c-8b34-088c195b6d78");
//        params3.put("sfItemStatus", "0");
//        params3.put("sfRack", "HA-01");
//        params3.put("sfShelf", "HA-01-01");
//        SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);
//
//        System.out.println("status: " + sfPkid.getStatus());
//        System.out.println("DELETE SFITEM...");
//        JSONObject params3 = new JSONObject();
//        params3.put("pkID", "1");
//        params3.put("version", "de02cc41-6912-4383-93f1-e54c0fdd16bb");
//
//        SPTSResponse delete = SPTSWebService.DeleteSFItem(params3);
//        if (delete.getStatus()) {
//            System.out.println("Delete Success: 4");
////                System.out.println("Delete Success: " + "6085");
//        } else {
//            System.out.println("Delete Failed: 4");
////                System.out.println("Delete Failed: " + "6085");
//        }
//        aa22ee6d-94d5-449b-8825-a2c0df40bd39
        //getSFItemByParam
//        JSONObject params0 = new JSONObject();
//        params0.put("itemID", "CDARS-MB_1");
////        params0.put("pkID", "70571");
////        params0.put("transType", "19");
////         params0.put("itemType", "BIB");
////         params0.put("transQty", "1");
//        JSONArray getItemByParam = SPTSWebService.getSFItemByParam(params0);
//        for (int i = 0; i < getItemByParam.length(); i++) {
//            System.out.println(getItemByParam.getJSONObject(i));
//        }
//        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
//        System.out.println("DELETE Transaction...");
//        JSONObject params3 = new JSONObject();
//        params3.put("pkID", "70571");
//        params3.put("version", "3b5b36bd-340f-49ba-8729-10b5bb49ee7f");
//
//        SPTSResponse delete = SPTSWebService.DeleteTransaction(params3);
//        if (delete.getStatus()) {
//            System.out.println("Delete Success: 70571");
////                System.out.println("Delete Success: " + "6085");
//        } else {
//            System.out.println("Delete Failed: 70571");
////                System.out.println("Delete Failed: " + "6085");
//        }
//        public int InsertActivityLog(string userID, string logModule, string logAction, string logInfo)
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
////        //get current date time with Date()
//        Date date = new Date();
//        System.out.println(dateFormat.format(date));
//
//        String formattedDate = dateFormat.format(date);
//        System.out.println("insert Transaction log...");
//        JSONObject params3 = new JSONObject();
//        params3.put("userID", "fg79cj");
//        params3.put("logModule", "TRACKING");
//        params3.put("logAction", "UPDATE");
//        params3.put("logInfo", "<DATA BEFORE > DateTime:[{2016-10-04}], ItemID:[{7687}], ItemName:[{CDARS-MB-Testing_2}], Tracking:[{Out for Storage Factory}], Qty:[{1}], Remarks:[] <DATA AFTER > DateTime:[{2016-10-04}], ItemID:[{7687}], ItemName:[{CDARS-MB-Testing_2}], Tracking:[{Return From Storage Factory}], Qty:[{1}], Remarks:[]");
//
//        SPTSResponse delete = SPTSWebService.insertActivityLog(params3);
//        System.out.println("insertTransLog: " + delete.getResponseId());

//        System.out.println("DELETE ITEM...");
//        JSONObject params3 = new JSONObject();
//        params3.put("pkID", "7686");
////            params3.put("pkID", "6085");
////            params3.put("version", version2);
//        params3.put("version", "a8fb66b4-7bd2-481a-b072-ba1dbbfbc235");
//        params3.put("forceDelete", "false");
//
//        SPTSResponse delete = SPTSWebService.deleteItem(params3);
//        if (delete.getStatus()) {
//            System.out.println("Delete Success: 7686");
////                System.out.println("Delete Success: " + "6085");
//        } else {
//            System.out.println("Delete Failed: 7686");
////                System.out.println("Delete Failed: " + "6085");
//        }
//        }
//        System.out.println("GET TRANSACTION BY PARAM...");
//        JSONObject params0 = new JSONObject();
////        params0.put("itemType", "PCB%");
////        params0.put("itemStatus", "0");
////        params0.put("status", "1");
////        params0.put("itemID", "%QUAL A");
//        params0.put("itemsPKID", "7419");
////        params0.put("version", "07f86952-7c90-4bb8-90ba-696ca589851d");
//
//        JSONArray getItemByParam = SPTSWebService.getTransactionByParam(params0);
//        for (int i = 0; i < getItemByParam.length(); i++) {
//            System.out.println(getItemByParam.getJSONObject(i));
//        }
//        System.out.println("COUNT GET TRANSACTION BY PARAM..." + getItemByParam.length());

//get item from sfitem
                        System.out.println("GET SFITEM PCB QUAL A BY PARAM...");
                        JSONObject paramsQualA = new JSONObject();
                        paramsQualA.put("itemID", "19999A_CDARS-pcbTesting - CONTROL");
                        JSONArray getItemByParamA = SPTSWebService.getSFItemByParam(paramsQualA);
                        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamA.length());
                        int itemSfApkid = getItemByParamA.getJSONObject(0).getInt("PKID");
                        String versionSfA = getItemByParamA.getJSONObject(0).getString("Version");
                        
                          //delete sfitem
                        JSONObject paramsdeleteSfA = new JSONObject();
                        paramsdeleteSfA.put("pkID", itemSfApkid);
                        paramsdeleteSfA.put("version", versionSfA);
                        SPTSResponse deleteA = SPTSWebService.DeleteSFItem(paramsdeleteSfA);
                        if (deleteA.getStatus()) {
                            System.out.println("Delete Success pcb A: " + itemSfApkid);
                        } else {
                            System.out.println("Delete Failed pcb A: " + itemSfApkid);
                        }
    }
}
