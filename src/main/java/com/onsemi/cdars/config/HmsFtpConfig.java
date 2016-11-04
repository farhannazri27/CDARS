/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.cdars.config;

import com.onsemi.cdars.dao.WhInventoryDAO;
import com.onsemi.cdars.dao.WhRequestDAO;
import com.onsemi.cdars.dao.WhRetrievalDAO;
import com.onsemi.cdars.dao.WhShippingDAO;
import com.onsemi.cdars.dao.WhStatusLogDAO;
import com.onsemi.cdars.model.WhInventory;
import com.onsemi.cdars.model.WhInventoryTemp;
import com.onsemi.cdars.model.WhRequest;
import com.onsemi.cdars.model.WhRetrieval;
import com.onsemi.cdars.model.WhRetrievalTemp;
import com.onsemi.cdars.model.WhShipping;
import com.onsemi.cdars.model.WhStatusLog;
import com.onsemi.cdars.tools.QueryResult;
import com.onsemi.cdars.tools.SPTSResponse;
import com.onsemi.cdars.tools.SPTSWebService;
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author fg79cj
 */
@Configuration
@EnableScheduling
public class HmsFtpConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(HmsFtpConfig.class);

//    @Scheduled(fixedRate = 60000)
//    hold for now
//    @Scheduled(cron = "0 5 * * * *") //every hour after 5 minute every day
    @Scheduled(cron = "0 */1 * * * *") //every 1 minutes
    public void DownloadCsv() throws IOException {

        //utk inventory
        CSVReader csvReader = null;
        LOGGER.info("cron detected!!!");

        try {
            /**
             * Reading the CSV File Delimiter is comma Start reading from line 1
             *
             */

            String username = System.getProperty("user.name");
            File file = new File("C:\\Users\\" + username + "\\Documents\\HMS\\hms_inventory.csv");

            if (file.exists()) {
                csvReader = new CSVReader(new FileReader("C:\\Users\\" + username + "\\Documents\\HMS\\hms_inventory.csv"), ',', '"', 1);

                String[] inventory = null;
                //Create List for holding Employee objects
                List<WhInventoryTemp> empList = new ArrayList<WhInventoryTemp>();

                while ((inventory = csvReader.readNext()) != null) {
                    //Save the employee details in Employee object
                    WhInventoryTemp emp = new WhInventoryTemp(inventory[0],
                            inventory[1], inventory[2],
                            inventory[3], inventory[4],
                            inventory[5], inventory[6],
                            inventory[7], inventory[8],
                            inventory[9], inventory[10],
                            inventory[11], inventory[12],
                            inventory[13], inventory[14],
                            inventory[15], inventory[16],
                            inventory[17], inventory[18],
                            inventory[19], inventory[20],
                            inventory[21], inventory[22],
                            inventory[23]);
                    empList.add(emp);
                }

                //Lets print the Inventory List
                for (WhInventoryTemp e : empList) {

                    // check id exist or not
                    WhInventoryDAO checkidDao = new WhInventoryDAO();
                    int countinventoryid = checkidDao.getCountInventoryiId(e.getRequestId());
                    if (countinventoryid > 0) {

                        //check if exist, same data or not
                        WhInventoryDAO inDao = new WhInventoryDAO();
                        int countdata = inDao.getCountInventoryiIdAndLocation(e.getRequestId(), e.getInventoryRack(), e.getInventoryShelf());
                        if (countdata == 0) {
                            WhInventoryDAO countid = new WhInventoryDAO();
                            String id = countid.getId(e.getRequestId());
                            WhInventory updateftp = new WhInventory();

                            updateftp.setId(id);
                            updateftp.setRequestId(e.getRequestId());
                            updateftp.setInventoryDate(e.getInventoryDate());
                            updateftp.setInventoryBy(e.getInventoryBy());
                            updateftp.setInventoryRack(e.getInventoryRack());
                            updateftp.setInventoryShelf(e.getInventoryShelf());
                            updateftp.setFlag("0");
                            WhInventoryDAO updateDao = new WhInventoryDAO();
                            QueryResult update = updateDao.updateWhInventoryLocation(updateftp);
                            if (update.getResult() == 1) {

                                //update statusLog
                                WhStatusLog stat = new WhStatusLog();
                                stat.setRequestId(e.getRequestId());
                                stat.setModule("cdars_wh_inventory");
                                stat.setStatus("SF Inventory Updated");
                                stat.setCreatedBy("-");
                                stat.setFlag("0");
                                WhStatusLogDAO statD = new WhStatusLogDAO();
                                QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                                if (queryResultStat.getGeneratedKey().equals("0")) {
                                    LOGGER.info("[HmsFtpConfig] - insert status log failed");
                                } else {
                                    LOGGER.info("[HmsFtpConfig] - insert status log done");
                                }
                                LOGGER.info("update file");

                                //update spts location
                                //get item pkid and version
                                if ("PCB".equals(e.getEquipmentType())) {
                                    if (!"0".equals(e.getPcbAQty())) {
                                        System.out.println("GET SFITEM PCB QUAL A BY PARAM...");
                                        JSONObject paramsQualA = new JSONObject();
                                        String itemID = e.getPcbA();
                                        paramsQualA.put("itemID", itemID);
                                        JSONArray getItemByParamA = SPTSWebService.getSFItemByParam(paramsQualA);
                                        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamA.length());
                                        int itemApkid = getItemByParamA.getJSONObject(0).getInt("PKID");
                                        String versionA = getItemByParamA.getJSONObject(0).getString("Version");
                                        LOGGER.info("itemApkid............." + itemApkid);

                                        JSONObject params3 = new JSONObject();
                                        params3.put("pkID", itemApkid);
                                        params3.put("version", versionA);
                                        params3.put("sfItemStatus", "0");
                                        params3.put("sfRack", e.getInventoryRack());
                                        params3.put("sfShelf", e.getInventoryShelf());
                                        SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                        System.out.println("status: " + sfPkid.getStatus());

                                        if (sfPkid.getStatus()) {
                                            LOGGER.info("done update spts PCB A");
                                        } else {
                                            LOGGER.info("failed to update spts PCB A");
                                        }
                                    }
                                    if (!"0".equals(e.getPcbBQty())) {
                                        System.out.println("GET SFITEM PCB QUAL B BY PARAM...");
                                        JSONObject paramsQualB = new JSONObject();
                                        String itemID = e.getPcbB();
                                        paramsQualB.put("itemID", itemID);
                                        JSONArray getItemByParamB = SPTSWebService.getSFItemByParam(paramsQualB);
                                        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamB.length());
                                        int itemBpkid = getItemByParamB.getJSONObject(0).getInt("PKID");
                                        String versionB = getItemByParamB.getJSONObject(0).getString("Version");
                                        LOGGER.info("itemBpkid............." + itemBpkid);

                                        JSONObject params3 = new JSONObject();
                                        params3.put("pkID", itemBpkid);
                                        params3.put("version", versionB);
                                        params3.put("sfItemStatus", "0");
                                        params3.put("sfRack", e.getInventoryRack());
                                        params3.put("sfShelf", e.getInventoryShelf());
                                        SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                        System.out.println("status: " + sfPkid.getStatus());

                                        if (sfPkid.getStatus()) {
                                            LOGGER.info("done update spts PCB B");
                                        } else {
                                            LOGGER.info("failed to update spts PCB B");
                                        }
                                    }
                                    if (!"0".equals(e.getPcbCQty())) {
                                        System.out.println("GET SFITEM PCB QUAL C BY PARAM...");
                                        JSONObject paramsQualC = new JSONObject();
                                        String itemID = e.getPcbC();
                                        paramsQualC.put("itemID", itemID);
                                        JSONArray getItemByParamC = SPTSWebService.getSFItemByParam(paramsQualC);
                                        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamC.length());
                                        int itemCpkid = getItemByParamC.getJSONObject(0).getInt("PKID");
                                        String versionC = getItemByParamC.getJSONObject(0).getString("Version");
                                        LOGGER.info("itemCpkid............." + itemCpkid);

                                        JSONObject params3 = new JSONObject();
                                        params3.put("pkID", itemCpkid);
                                        params3.put("version", versionC);
                                        params3.put("sfItemStatus", "0");
                                        params3.put("sfRack", e.getInventoryRack());
                                        params3.put("sfShelf", e.getInventoryShelf());
                                        SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                        System.out.println("status: " + sfPkid.getStatus());

                                        if (sfPkid.getStatus()) {
                                            LOGGER.info("done update spts PCB C");
                                        } else {
                                            LOGGER.info("failed to update spts PCB C");
                                        }
                                    }
                                    if (!"0".equals(e.getPcbCtrQty())) {
                                        System.out.println("GET SFITEM PCB CONTROL BY PARAM...");
                                        JSONObject paramsQualCtr = new JSONObject();
                                        String itemID = e.getPcbCtr();
                                        paramsQualCtr.put("itemID", itemID);
                                        JSONArray getItemByParamCtr = SPTSWebService.getSFItemByParam(paramsQualCtr);
                                        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamCtr.length());
                                        int itemCtrpkid = getItemByParamCtr.getJSONObject(0).getInt("PKID");
                                        String versionCtr = getItemByParamCtr.getJSONObject(0).getString("Version");
                                        LOGGER.info("itemCtrpkid............." + itemCtrpkid);

                                        JSONObject params3 = new JSONObject();
                                        params3.put("pkID", itemCtrpkid);
                                        params3.put("version", versionCtr);
                                        params3.put("sfItemStatus", "0");
                                        params3.put("sfRack", e.getInventoryRack());
                                        params3.put("sfShelf", e.getInventoryShelf());
                                        SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                        System.out.println("status: " + sfPkid.getStatus());

                                        if (sfPkid.getStatus()) {
                                            LOGGER.info("done update spts PCB Ctr");
                                        } else {
                                            LOGGER.info("failed to update spts PCB Ctr");
                                        }
                                    }
                                } else {
                                    System.out.println("GET SFITEM BY PARAM...");
                                    JSONObject paramsSfItem = new JSONObject();
                                    String itemID = e.getEquipmentId();
                                    paramsSfItem.put("itemID", itemID);
                                    JSONArray getItemByParam = SPTSWebService.getSFItemByParam(paramsSfItem);
//                                    for (int i = 0; i < getItemByParam.length(); i++) {
//                                        System.out.println(getItemByParam.getJSONObject(i));
//                                    }
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                                    int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                                    String version = getItemByParam.getJSONObject(0).getString("Version");
                                    LOGGER.info("itempkid............." + itempkid);

//                                public bool UpdateSFItemLocation(int pkID, string version, int sfItemStatus, string sfRack, string sfShelf)
                                    JSONObject params3 = new JSONObject();
                                    params3.put("pkID", itempkid);
                                    params3.put("version", version);
                                    params3.put("sfItemStatus", "0");
                                    params3.put("sfRack", e.getInventoryRack());
                                    params3.put("sfShelf", e.getInventoryShelf());
                                    SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                    System.out.println("status: " + sfPkid.getStatus());

                                    if (sfPkid.getStatus()) {
                                        LOGGER.info("done update spts");
                                    } else {
                                        LOGGER.info("failed to update spts");
                                    }
                                }

                                //update shipping table
                                WhShippingDAO shipD = new WhShippingDAO();

                                int count = shipD.getCountRequestId(e.getRequestId());
                                if (count == 1) {
                                    shipD = new WhShippingDAO();
                                    WhShipping ship = shipD.getWhShippingNyRequestId(e.getRequestId());
                                    ship.setStatus("In SF Inventory");
                                    ship.setFlag("1");
                                    shipD = new WhShippingDAO();
                                    QueryResult updateShip = shipD.updateWhShipping(ship);
                                    if (updateShip.getResult() == 1) {
                                        LOGGER.info("[UPDATE Inventory] - update ship status and flag");
                                    } else {
                                        LOGGER.info("[UPDATE Inventory] - failed to update ship status and flag");
                                    }
                                } else {
                                    LOGGER.info("[UPDATE Inventory] - no request id");
                                }

                                //update status at master table request
                                WhRequestDAO reqD = new WhRequestDAO();
                                int countReq = reqD.getCountRequestId(e.getRequestId());
                                if (countReq == 1) {
                                    reqD = new WhRequestDAO();
                                    WhRequest req = reqD.getWhRequest(e.getRequestId());
                                    WhRequest reqUpdate = new WhRequest();
                                    reqUpdate.setModifiedBy(req.getModifiedBy());
                                    reqUpdate.setStatus("In SF Inventory");
                                    reqUpdate.setId(e.getRequestId());
                                    reqD = new WhRequestDAO();
                                    QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                                    if (ru.getResult() == 1) {
                                        LOGGER.info("[CREATE Inventory] - update status at request table done");
                                    } else {
                                        LOGGER.info("[CREATE Inventory] - update status at request table failed");
                                    }
                                } else {
                                    LOGGER.info("[CREATE Inventory] - requestId not found");
                                }

                            } else {
                                LOGGER.info("failed to update");
                            }

                        }
                    } else {
                        WhInventoryDAO insertDao = new WhInventoryDAO();
                        WhInventory insertftp = new WhInventory();
                        insertftp.setRequestId(e.getRequestId());
                        insertftp.setMpNo(e.getMpNo());
                        insertftp.setMpExpiryDate(e.getMpExpiryDate());
                        insertftp.setEquipmentType(e.getEquipmentType());
                        insertftp.setEquipmentId(e.getEquipmentId());
                        insertftp.setPcbA(e.getPcbA());
                        insertftp.setPcbAQty(e.getPcbAQty());
                        insertftp.setPcbB(e.getPcbB());
                        insertftp.setPcbBQty(e.getPcbBQty());
                        insertftp.setPcbC(e.getPcbC());
                        insertftp.setPcbCQty(e.getPcbCQty());
                        insertftp.setPcbCtr(e.getPcbCtr());
                        insertftp.setPcbCtrQty(e.getPcbCtrQty());
                        insertftp.setQuantity(e.getQuantity());
                        insertftp.setRequestedBy(e.getRequestedBy());
                        insertftp.setRequestedDate(e.getRequestedDate());
                        insertftp.setRemarks(e.getRemarks());
                        insertftp.setVerifiedDate(e.getVerifiedDate());
                        insertftp.setReceivalDate(e.getReceivalDate());
                        insertftp.setInventoryDate(e.getInventoryDate());
                        insertftp.setInventoryBy(e.getInventoryBy());
                        insertftp.setInventoryRack(e.getInventoryRack());
                        insertftp.setInventoryShelf(e.getInventoryShelf());
                        insertftp.setStatus(e.getStatus());
                        insertftp.setFlag("0");
                        QueryResult add = insertDao.insertWhInventory(insertftp);
                        if (add.getResult() == 1) {
                            LOGGER.info("insert file");

                            //update statusLog
                            WhStatusLog stat = new WhStatusLog();
                            stat.setRequestId(e.getRequestId());
                            stat.setModule("cdars_wh_inventory");
                            stat.setStatus("In SF Inventory");
                            stat.setCreatedBy("-");
                            stat.setFlag("0");
                            WhStatusLogDAO statD = new WhStatusLogDAO();
                            QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                            if (queryResultStat.getGeneratedKey().equals("0")) {
                                LOGGER.info("[HmsFtpConfig] - insert status log failed");
                            } else {
                                LOGGER.info("[HmsFtpConfig] - insert status log done");
                            }

                            //update spts location
                            //get item pkid and version
                            if ("PCB".equals(e.getEquipmentType())) {
                                if (!"0".equals(e.getPcbAQty())) {
                                    System.out.println("GET SFITEM PCB QUAL A BY PARAM...");
                                    JSONObject paramsQualA = new JSONObject();
                                    String itemID = e.getPcbA();
                                    paramsQualA.put("itemID", itemID);
                                    JSONArray getItemByParamA = SPTSWebService.getSFItemByParam(paramsQualA);
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamA.length());
                                    int itemApkid = getItemByParamA.getJSONObject(0).getInt("PKID");
                                    String versionA = getItemByParamA.getJSONObject(0).getString("Version");
                                    LOGGER.info("itemApkid............." + itemApkid);

                                    JSONObject params3 = new JSONObject();
                                    params3.put("pkID", itemApkid);
                                    params3.put("version", versionA);
                                    params3.put("sfItemStatus", "0");
                                    params3.put("sfRack", e.getInventoryRack());
                                    params3.put("sfShelf", e.getInventoryShelf());
                                    SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                    System.out.println("status: " + sfPkid.getStatus());

                                    if (sfPkid.getStatus()) {
                                        LOGGER.info("done update spts PCB A");
                                    } else {
                                        LOGGER.info("failed to update spts PCB A");
                                    }
                                }
                                if (!"0".equals(e.getPcbBQty())) {
                                    System.out.println("GET SFITEM PCB QUAL B BY PARAM...");
                                    JSONObject paramsQualB = new JSONObject();
                                    String itemID = e.getPcbB();
                                    paramsQualB.put("itemID", itemID);
                                    JSONArray getItemByParamB = SPTSWebService.getSFItemByParam(paramsQualB);
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamB.length());
                                    int itemBpkid = getItemByParamB.getJSONObject(0).getInt("PKID");
                                    String versionB = getItemByParamB.getJSONObject(0).getString("Version");
                                    LOGGER.info("itemApkid............." + itemBpkid);

                                    JSONObject params3 = new JSONObject();
                                    params3.put("pkID", itemBpkid);
                                    params3.put("version", versionB);
                                    params3.put("sfItemStatus", "0");
                                    params3.put("sfRack", e.getInventoryRack());
                                    params3.put("sfShelf", e.getInventoryShelf());
                                    SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                    System.out.println("status: " + sfPkid.getStatus());

                                    if (sfPkid.getStatus()) {
                                        LOGGER.info("done update spts PCB B");
                                    } else {
                                        LOGGER.info("failed to update spts PCB B");
                                    }
                                }
                                if (!"0".equals(e.getPcbCQty())) {
                                    System.out.println("GET SFITEM PCB QUAL C BY PARAM...");
                                    JSONObject paramsQualC = new JSONObject();
                                    String itemID = e.getPcbC();
                                    paramsQualC.put("itemID", itemID);
                                    JSONArray getItemByParamC = SPTSWebService.getSFItemByParam(paramsQualC);
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamC.length());
                                    int itemCpkid = getItemByParamC.getJSONObject(0).getInt("PKID");
                                    String versionC = getItemByParamC.getJSONObject(0).getString("Version");
                                    LOGGER.info("itemCpkid............." + itemCpkid);

                                    JSONObject params3 = new JSONObject();
                                    params3.put("pkID", itemCpkid);
                                    params3.put("version", versionC);
                                    params3.put("sfItemStatus", "0");
                                    params3.put("sfRack", e.getInventoryRack());
                                    params3.put("sfShelf", e.getInventoryShelf());
                                    SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                    System.out.println("status: " + sfPkid.getStatus());

                                    if (sfPkid.getStatus()) {
                                        LOGGER.info("done update spts PCB C");
                                    } else {
                                        LOGGER.info("failed to update spts PCB C");
                                    }
                                }
                                if (!"0".equals(e.getPcbCtrQty())) {
                                    System.out.println("GET SFITEM PCB CONTROL BY PARAM...");
                                    JSONObject paramsQualCtr = new JSONObject();
                                    String itemID = e.getPcbCtr();
                                    paramsQualCtr.put("itemID", itemID);
                                    JSONArray getItemByParamCtr = SPTSWebService.getSFItemByParam(paramsQualCtr);
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamCtr.length());
                                    int itemCtrpkid = getItemByParamCtr.getJSONObject(0).getInt("PKID");
                                    String versionCtr = getItemByParamCtr.getJSONObject(0).getString("Version");
                                    LOGGER.info("itemCtrpkid............." + itemCtrpkid);

                                    JSONObject params3 = new JSONObject();
                                    params3.put("pkID", itemCtrpkid);
                                    params3.put("version", versionCtr);
                                    params3.put("sfItemStatus", "0");
                                    params3.put("sfRack", e.getInventoryRack());
                                    params3.put("sfShelf", e.getInventoryShelf());
                                    SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                    System.out.println("status: " + sfPkid.getStatus());

                                    if (sfPkid.getStatus()) {
                                        LOGGER.info("done update spts PCB Ctr");
                                    } else {
                                        LOGGER.info("failed to update spts PCB Ctr");
                                    }
                                }
                            } else {
                                System.out.println("GET SFITEM BY PARAM...");
                                JSONObject paramsSfItem = new JSONObject();
                                String itemID = e.getEquipmentId();
                                paramsSfItem.put("itemID", itemID);
                                JSONArray getItemByParam = SPTSWebService.getSFItemByParam(paramsSfItem);
//                                    for (int i = 0; i < getItemByParam.length(); i++) {
//                                        System.out.println(getItemByParam.getJSONObject(i));
//                                    }
                                System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                                int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                                String version = getItemByParam.getJSONObject(0).getString("Version");
                                LOGGER.info("itempkid............." + itempkid);

//                                public bool UpdateSFItemLocation(int pkID, string version, int sfItemStatus, string sfRack, string sfShelf)
                                JSONObject params3 = new JSONObject();
                                params3.put("pkID", itempkid);
                                params3.put("version", version);
                                params3.put("sfItemStatus", "0");
                                params3.put("sfRack", e.getInventoryRack());
                                params3.put("sfShelf", e.getInventoryShelf());
                                SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                System.out.println("status: " + sfPkid.getStatus());

                                if (sfPkid.getStatus()) {
                                    LOGGER.info("done update spts");
                                } else {
                                    LOGGER.info("failed to update spts");
                                }
                            }

                            //update shipping table
                            WhShippingDAO shipD = new WhShippingDAO();

                            int count = shipD.getCountRequestId(e.getRequestId());
                            if (count == 1) {
                                shipD = new WhShippingDAO();
                                WhShipping ship = shipD.getWhShippingNyRequestId(e.getRequestId());
                                ship.setStatus("In SF Inventory");
                                ship.setFlag("1");
                                shipD = new WhShippingDAO();
//                                QueryResult updateShip = shipD.updateWhShipping(ship);
                                QueryResult updateShip = shipD.updateWhShippingStatusAndInventoryDate(ship);
                                if (updateShip.getResult() == 1) {
                                    LOGGER.info("[CREATE Inventory] - update ship status and flag");
                                } else {
                                    LOGGER.info("[CREATE Inventory] - failed for update ship status and flag");
                                }
                            } else {
                                LOGGER.info("[CREATE Inventory] - no request id");
                            }

                            //update status at master table request
                            WhRequestDAO reqD = new WhRequestDAO();
                            int countReq = reqD.getCountRequestId(e.getRequestId());
                            if (countReq == 1) {
                                reqD = new WhRequestDAO();
                                WhRequest req = reqD.getWhRequest(e.getRequestId());
                                WhRequest reqUpdate = new WhRequest();
                                reqUpdate.setModifiedBy(req.getModifiedBy());
                                reqUpdate.setStatus("In SF Inventory");
                                reqUpdate.setId(e.getRequestId());
                                reqD = new WhRequestDAO();
                                QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                                if (ru.getResult() == 1) {
                                    LOGGER.info("[CREATE Inventory] - update status at request table done");
                                } else {
                                    LOGGER.info("[CREATE Inventory] - update status at request table failed");
                                }
                            } else {
                                LOGGER.info("[CREATE Inventory] - requestId not found");
                            }

                        } else {
                            LOGGER.info("failed to insert");
                        }

                    }

                }
            } else {
                LOGGER.info("csv file not found!");
            }

            //for retrieval from sg gadut
//            String username = System.getProperty("user.name");
            File fileRetrieval = new File("C:\\Users\\" + username + "\\Documents\\HMS\\hms_shipping.csv");
//            File fileRetrieval = new File("C:\\Users\\fg79cj\\Documents\\HMS\\hms_shipping.csv");

            if (fileRetrieval.exists()) {

                CSVReader csvReaderForRetrieval = new CSVReader(new FileReader("C:\\Users\\" + username + "\\Documents\\HMS\\hms_shipping.csv"), ',', '"', 1);

                String[] retrieval = null;

                List<WhRetrievalTemp> empList = new ArrayList<WhRetrievalTemp>();

                while ((retrieval = csvReaderForRetrieval.readNext()) != null) {
                    //Save the employee details in Employee object
                    WhRetrievalTemp emp = new WhRetrievalTemp(retrieval[0],
                            retrieval[1], retrieval[2],
                            retrieval[3], retrieval[4],
                            retrieval[5], retrieval[6]);
                    empList.add(emp);
                }

                for (WhRetrievalTemp e : empList) {

                    WhRetrievalDAO retriveDao = new WhRetrievalDAO();

                    int count = retriveDao.getCountRequestIdAndMpNo(e.getRequestId(), e.getMpNo());

                    if (count == 1) {

                        WhRetrievalDAO idDao = new WhRetrievalDAO();
                        String id = idDao.getId(e.getRequestId(), e.getMpNo());

                        WhRetrievalDAO check = new WhRetrievalDAO();
                        String checkstatus = check.getWhRetrieval(id).getStatus();

//                        if (!"Ship".equals(checkstatus) && !"Closed".equals(checkstatus) && !"Barcode Verified".equals(checkstatus)) {
                        if ("Requested".equals(checkstatus)) {
                            WhRetrieval update = new WhRetrieval();
                            update.setId(id);
                            update.setRequestId(e.getRequestId());
                            update.setMpNo(e.getMpNo());
                            update.setVerifiedBy(e.getVerifiedBy());
                            update.setVerifiedDate(e.getVerifiedDate());
                            update.setShippingBy(e.getShippingBy());
                            update.setShippingDate(e.getShippingDate());
//                            update.setStatus(e.getStatus()); //original 3/11/16
                            update.setStatus("Shipped"); //as requested 2/11/16
                            update.setFlag("0");

                            WhRetrievalDAO updateDao = new WhRetrievalDAO();
                            QueryResult updateRetrieval = updateDao.updateWhRetrievalFromCsv(update);
                            if (updateRetrieval.getResult() == 1) {
                                LOGGER.info("Update Done!");

                                //update statusLog
                                WhStatusLog stat = new WhStatusLog();
                                stat.setRequestId(e.getRequestId());
                                stat.setModule("cdars_wh_retrieval");
                                stat.setStatus("Shipped From Seremban Factory");
                                stat.setCreatedBy("-");
                                stat.setFlag("0");
                                WhStatusLogDAO statD = new WhStatusLogDAO();
                                QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                                if (queryResultStat.getGeneratedKey().equals("0")) {
                                    LOGGER.info("[HmsFtpConfig] - insert status log failed");
                                } else {
                                    LOGGER.info("[HmsFtpConfig] - insert status log done");
                                }

                                //update status at master table request
                                WhRequestDAO reqD = new WhRequestDAO();
                                int countReq = reqD.getCountRequestId(e.getRequestId());
                                if (countReq == 1) {
                                    reqD = new WhRequestDAO();
                                    WhRequest req = reqD.getWhRequest(e.getRequestId());
                                    WhRequest reqUpdate = new WhRequest();
                                    reqUpdate.setModifiedBy(req.getModifiedBy());
                                    reqUpdate.setStatus("Shipped From Seremban Factory");
                                    reqUpdate.setId(e.getRequestId());
                                    reqD = new WhRequestDAO();
                                    QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                                    if (ru.getResult() == 1) {
                                        LOGGER.info("[From SF] - update status at request table done");
                                    } else {
                                        LOGGER.info("[From SF] - update status at request table failed");
                                    }
                                } else {
                                    LOGGER.info("[From SF] - requestId not found");
                                }
                            } else {
                                LOGGER.info("Update Failed!");
                            }
                        }

                    } else {
                        LOGGER.info("No data found");
                    }
                }

            } else {
                LOGGER.info("retrieval csv file not found!");
            }

        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

}
